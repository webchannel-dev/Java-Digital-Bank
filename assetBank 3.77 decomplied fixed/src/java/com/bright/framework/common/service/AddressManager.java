/*     */ package com.bright.framework.common.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.database.AssetBankSql;
/*     */ import com.bright.framework.common.bean.Address;
/*     */ import com.bright.framework.common.bean.Country;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.database.util.DBUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
/*     */ import org.apache.avalon.excalibur.datasource.DataSourceComponent;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class AddressManager extends Bn2Manager
/*     */ {
/*     */   private static final String c_ksClassName = "AddressManager";
/*  49 */   protected DataSourceComponent m_dataSource = null;
/*     */ 
/*     */   public void setDataSourceComponent(DataSourceComponent a_datasource) {
/*  52 */     this.m_dataSource = a_datasource;
/*     */   }
/*     */ 
/*     */   public Vector getCountryList()
/*     */     throws Bn2Exception
/*     */   {
/*  65 */     Connection cCon = null;
/*  66 */     Vector vec = new Vector();
/*     */     try
/*     */     {
/*  70 */       cCon = this.m_dataSource.getConnection();
/*     */ 
/*  72 */       PreparedStatement psql = null;
/*  73 */       psql = cCon.prepareStatement("SELECT Id, Name, NativeName FROM Country ORDER BY SequenceNumber, Name");
/*  74 */       ResultSet rs = psql.executeQuery();
/*     */ 
/*  77 */       while (rs.next())
/*     */       {
/*  80 */         Country country = new Country();
/*  81 */         country.setId(rs.getLong("Id"));
/*  82 */         country.setName(rs.getString("Name"));
/*  83 */         country.setNativeName(rs.getString("NativeName"));
/*  84 */         vec.add(country);
/*     */       }
/*     */ 
/*  87 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*     */       try
/*     */       {
/*  93 */         cCon.rollback();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/*     */       }
/*     */ 
/* 100 */       throw new Bn2Exception("AddressManager.getCountryList() : Exception occurred: " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 105 */       if (cCon != null)
/*     */       {
/*     */         try
/*     */         {
/* 109 */           cCon.commit();
/* 110 */           cCon.close();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 119 */     return vec;
/*     */   }
/*     */ 
/*     */   public Address getAddress(DBTransaction a_dbTransaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/* 133 */     String ksMethodName = "getAddress";
/*     */ 
/* 136 */     Address address = new Address();
/*     */     try
/*     */     {
/* 141 */       Connection con = a_dbTransaction.getConnection();
/*     */ 
/* 147 */       String sSql = "SELECT Address1, Address2, Town, County, Postcode, CountryId, c.Name cName, c.NativeName cNativeName FROM Address a LEFT JOIN Country c ON a.CountryId = c.Id WHERE a.Id=?";
/*     */ 
/* 152 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 153 */       psql.setLong(1, a_lId);
/* 154 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 157 */       if (rs.next())
/*     */       {
/* 159 */         address.setId(a_lId);
/* 160 */         address.setAddressLine1(rs.getString("Address1"));
/* 161 */         address.setAddressLine2(rs.getString("Address2"));
/* 162 */         address.setTown(rs.getString("Town"));
/* 163 */         address.setCounty(rs.getString("County"));
/* 164 */         address.setPostcode(rs.getString("Postcode"));
/*     */ 
/* 166 */         Country country = address.getCountry();
/* 167 */         country.setId(rs.getLong("CountryId"));
/* 168 */         country.setName(rs.getString("cName"));
/* 169 */         country.setNativeName(rs.getString("cNativeName"));
/*     */       }
/*     */ 
/* 172 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 177 */       throw new Bn2Exception("AddressManager.getAddress: Exception occurred: " + e, e);
/*     */     }
/*     */ 
/* 180 */     return address;
/*     */   }
/*     */ 
/*     */   public void saveAddress(DBTransaction a_dbTransaction, Address a_address)
/*     */     throws Bn2Exception
/*     */   {
/* 194 */     if (a_address.getId() <= 0L)
/*     */     {
/* 196 */       createAddress(a_dbTransaction, a_address);
/*     */ 
/* 199 */       this.m_logger.debug("Saved address id=" + a_address.getId());
/*     */     }
/*     */     else
/*     */     {
/* 203 */       updateAddress(a_dbTransaction, a_address);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void createAddress(DBTransaction a_dbTransaction, Address a_address)
/*     */     throws Bn2Exception
/*     */   {
/* 218 */     String ksMethodName = "createAddress";
/*     */ 
/* 220 */     long lNewId = 0L;
/*     */     try
/*     */     {
/* 224 */       Connection con = a_dbTransaction.getConnection();
/*     */ 
/* 229 */       String sSql = "INSERT INTO Address (";
/*     */ 
/* 231 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/* 232 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 234 */         lNewId = sqlGenerator.getUniqueId(con, "AddressSequence");
/* 235 */         sSql = sSql + "Id, ";
/*     */       }
/*     */ 
/* 238 */       sSql = sSql + "Address1, Address2, Town, County, Postcode, CountryId) VALUES (";
/*     */ 
/* 240 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 242 */         sSql = sSql + "?, ";
/*     */       }
/*     */ 
/* 245 */       sSql = sSql + "?,?,?,?,?,?)";
/*     */ 
/* 248 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 249 */       int iCol = 1;
/* 250 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 252 */         psql.setLong(iCol++, lNewId);
/*     */       }
/* 254 */       psql.setString(iCol++, a_address.getAddressLine1());
/* 255 */       psql.setString(iCol++, a_address.getAddressLine2());
/* 256 */       psql.setString(iCol++, a_address.getTown());
/* 257 */       psql.setString(iCol++, a_address.getCounty());
/* 258 */       psql.setString(iCol++, a_address.getPostcode());
/* 259 */       DBUtil.setFieldIdOrNull(psql, iCol++, a_address.getCountry());
/*     */ 
/* 261 */       psql.executeUpdate();
/* 262 */       psql.close();
/*     */ 
/* 264 */       if (sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 266 */         lNewId = sqlGenerator.getUniqueId(con, "Address");
/*     */       }
/* 268 */       a_address.setId(lNewId);
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 274 */       throw new Bn2Exception("AddressManager.createAddress: Exception occurred: " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void updateAddress(DBTransaction a_dbTransaction, Address a_address)
/*     */     throws Bn2Exception
/*     */   {
/* 295 */     String ksMethodName = "updateAddress";
/*     */     try
/*     */     {
/* 301 */       Connection con = a_dbTransaction.getConnection();
/*     */ 
/* 305 */       String sSql = "UPDATE Address SET Address1=?, Address2=?, Town=?, County=?, Postcode=?, CountryId=? WHERE Id=?";
/*     */ 
/* 308 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 309 */       int iCol = 1;
/* 310 */       psql.setString(iCol++, a_address.getAddressLine1());
/* 311 */       psql.setString(iCol++, a_address.getAddressLine2());
/* 312 */       psql.setString(iCol++, a_address.getTown());
/* 313 */       psql.setString(iCol++, a_address.getCounty());
/* 314 */       psql.setString(iCol++, a_address.getPostcode());
/* 315 */       DBUtil.setFieldIdOrNull(psql, iCol++, a_address.getCountry().getId());
/* 316 */       psql.setLong(iCol++, a_address.getId());
/*     */ 
/* 318 */       psql.executeUpdate();
/* 319 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 324 */       throw new Bn2Exception("AddressManager.updateAddress: Exception occurred: " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteAddress(DBTransaction a_dbTransaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/* 345 */     String ksMethodName = "deleteAddress";
/*     */     try
/*     */     {
/* 350 */       Connection con = a_dbTransaction.getConnection();
/*     */ 
/* 354 */       String sSql = "DELETE from Address WHERE Id=?";
/*     */ 
/* 356 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 357 */       psql.setLong(1, a_lId);
/* 358 */       psql.executeUpdate();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 363 */       throw new Bn2Exception("AddressManager.deleteAddress: Exception occurred: " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public long getCountryIdFromName(String a_sName)
/*     */     throws Bn2Exception
/*     */   {
/* 378 */     String sSql = "SELECT Id FROM Country WHERE Name=?";
/*     */ 
/* 380 */     long lId = RefDataManager.getIdForName(this.m_dataSource, a_sName, sSql);
/*     */ 
/* 382 */     return lId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.service.AddressManager
 * JD-Core Version:    0.6.0
 */