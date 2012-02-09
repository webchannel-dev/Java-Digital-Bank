/*     */ package com.bright.assetbank.tax.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.tax.bean.TaxValue;
/*     */ import com.bright.assetbank.tax.bean.TaxablePrice;
/*     */ import com.bright.framework.common.bean.BrightDecimal;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.common.bean.TranslatableStringDataBean;
/*     */ import com.bright.framework.common.bean.TranslatableStringDataBean.Translation;
/*     */ import com.bright.framework.common.service.RefDataManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
/*     */ import org.apache.avalon.excalibur.datasource.DataSourceComponent;
/*     */ 
/*     */ public class TaxManager extends Bn2Manager
/*     */ {
/*     */   private static final String c_ksClassName = "TaxManager";
/*  51 */   protected DataSourceComponent m_dataSource = null;
/*     */ 
/*     */   public void setDataSourceComponent(DataSourceComponent a_datasource) {
/*  54 */     this.m_dataSource = a_datasource;
/*     */   }
/*     */ 
/*     */   public TaxablePrice getTaxablePriceForCountry(DBTransaction a_dbTransaction, long a_lSubtotal, long a_lCountryId, long a_lTaxTypeId)
/*     */     throws Bn2Exception
/*     */   {
/*  80 */     TaxValue tv = getTaxValueForCountry(a_dbTransaction, a_lCountryId, a_lTaxTypeId);
/*     */ 
/*  82 */     TaxablePrice price = new TaxablePrice(tv, a_lSubtotal);
/*  83 */     return price;
/*     */   }
/*     */ 
/*     */   public TaxValue getTaxValueForCountry(DBTransaction a_dbTransaction, long a_lCountryId, long a_lTaxTypeId)
/*     */     throws Bn2Exception
/*     */   {
/* 107 */     long lTaxRegionId = mapCountryToTaxRegion(a_dbTransaction, a_lCountryId);
/*     */ 
/* 109 */     TaxValue tv = getTaxValue(a_dbTransaction, a_lTaxTypeId, lTaxRegionId);
/* 110 */     return tv;
/*     */   }
/*     */ 
/*     */   public StringDataBean getTaxRegionForCountry(DBTransaction a_dbTransaction, long a_lCountryId)
/*     */     throws Bn2Exception
/*     */   {
/* 132 */     long lTaxRegionId = mapCountryToTaxRegion(a_dbTransaction, a_lCountryId);
/*     */ 
/* 134 */     StringDataBean tr = getTaxRegion(a_dbTransaction, lTaxRegionId);
/* 135 */     return tr;
/*     */   }
/*     */ 
/*     */   public Vector getTaxValueList(DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 154 */     String ksMethodName = "getTaxValueList";
/* 155 */     Connection con = null;
/* 156 */     String sSQL = null;
/* 157 */     PreparedStatement psql = null;
/* 158 */     Vector vecList = new Vector();
/*     */     try
/*     */     {
/* 162 */       con = a_dbTransaction.getConnection();
/*     */ 
/* 164 */       sSQL = "SELECT tt.Name ttName, tt.Id ttId, tr.Name trName, tr.Id trId, tv.Percent, tv.ZeroIfTaxNumberGiven FROM TaxValue tv INNER JOIN TaxType tt ON tv.TaxTypeId = tt.Id INNER JOIN TaxRegion tr ON tv.TaxRegionId = tr.Id ORDER BY tt.Id, tr.Id ";
/*     */ 
/* 172 */       psql = con.prepareStatement(sSQL);
/* 173 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 175 */       while (rs.next())
/*     */       {
/* 177 */         TaxValue tv = new TaxValue();
/*     */ 
/* 179 */         tv.getTaxPercent().setNumber(rs.getFloat("Percent"));
/* 180 */         tv.setZeroIfTaxNumberGiven(rs.getBoolean("ZeroIfTaxNumberGiven"));
/* 181 */         tv.getTaxType().setId(rs.getLong("ttId"));
/* 182 */         tv.getTaxType().setName(rs.getString("ttName"));
/* 183 */         tv.getTaxRegion().setId(rs.getLong("trId"));
/* 184 */         tv.getTaxRegion().setName(rs.getString("trName"));
/*     */ 
/* 186 */         vecList.add(tv);
/*     */       }
/* 188 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 194 */       throw new Bn2Exception("TaxManager.getTaxValueList: Exception occurred: " + e, e);
/*     */     }
/*     */ 
/* 197 */     return vecList;
/*     */   }
/*     */ 
/*     */   public TaxValue getTaxValue(DBTransaction a_dbTransaction, long a_lTaxTypeId, long a_lTaxRegionId)
/*     */     throws Bn2Exception
/*     */   {
/* 218 */     String ksMethodName = "getTaxValue";
/* 219 */     Connection con = null;
/* 220 */     String sSQL = null;
/* 221 */     PreparedStatement psql = null;
/* 222 */     TaxValue tv = null;
/*     */     try
/*     */     {
/* 226 */       con = a_dbTransaction.getConnection();
/*     */ 
/* 228 */       sSQL = "SELECT tt.Name ttName, ttt.Name tttName, tr.Name trName, ttr.Name ttrName, tv.Percent, tv.ZeroIfTaxNumberGiven, l.Id lId, l.Name lName, l.Code lCode FROM TaxValue tv INNER JOIN TaxType tt ON tv.TaxTypeId = tt.Id INNER JOIN TaxRegion tr ON tv.TaxRegionId = tr.Id LEFT JOIN TranslatedTaxType ttt ON ttt.TaxTypeId = tt.Id LEFT JOIN TranslatedTaxRegion ttr ON ttr.TaxRegionId = tr.Id LEFT JOIN Language l ON l.Id = ttt.LanguageId AND l.Id = ttr.LanguageId WHERE tt.Id = ? AND tr.Id = ? ";
/*     */ 
/* 246 */       psql = con.prepareStatement(sSQL);
/* 247 */       psql.setLong(1, a_lTaxTypeId);
/* 248 */       psql.setLong(2, a_lTaxRegionId);
/* 249 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 251 */       while (rs.next())
/*     */       {
/* 253 */         if (tv == null)
/*     */         {
/* 255 */           tv = new TaxValue();
/* 256 */           tv.getTaxPercent().setNumber(rs.getFloat("Percent"));
/* 257 */           tv.setZeroIfTaxNumberGiven(rs.getBoolean("ZeroIfTaxNumberGiven"));
/* 258 */           tv.getTaxType().setId(a_lTaxTypeId);
/* 259 */           tv.getTaxType().setName(rs.getString("ttName"));
/* 260 */           tv.getTaxRegion().setId(a_lTaxRegionId);
/* 261 */           tv.getTaxRegion().setName(rs.getString("trName"));
/*     */         }
/*     */ 
/* 264 */         if (rs.getLong("lId") <= 0L)
/*     */           continue;
/* 266 */          Language lang = new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode"));
/*     */         //TranslatableStringDataBean tmp232_229 = tv.getTaxType(); tmp232_229.getClass(); TranslatableStringDataBean.Translation translation = new TranslatableStringDataBean.Translation(tmp232_229, lang);
                   TranslatableStringDataBean outerobj = tv.getTaxType();
/* 269 */          TranslatableStringDataBean.Translation translation = outerobj.new Translation(lang);
                   translation.setName(rs.getString("tttName"));
/* 270 */          tv.getTaxType().getTranslations().add(translation);
/*     */         //TranslatableStringDataBean tmp281_278 = tv.getTaxRegion(); tmp281_278.getClass(); translation = new TranslatableStringDataBean.Translation(tmp281_278, lang);
/* 273 */         TranslatableStringDataBean outerobj2 = tv.getTaxRegion();
                  TranslatableStringDataBean.Translation translation2 = outerobj2.new Translation(lang);
                  translation.setName(rs.getString("ttrName"));
/* 274 */         tv.getTaxRegion().getTranslations().add(translation);
/*     */       }
/*     */ 
/* 278 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 284 */       throw new Bn2Exception("TaxManager.getTaxValue: Exception occurred: " + e, e);
/*     */     }
/*     */ 
/* 287 */     return tv;
/*     */   }
/*     */ 
/*     */   public StringDataBean getTaxRegion(DBTransaction a_dbTransaction, long a_lTaxRegionId)
/*     */     throws Bn2Exception
/*     */   {
/* 294 */     String ksMethodName = "getTaxRegion";
/* 295 */     Connection con = null;
/* 296 */     String sSQL = null;
/* 297 */     PreparedStatement psql = null;
/* 298 */     StringDataBean tr = new StringDataBean();
/*     */     try
/*     */     {
/* 302 */       con = a_dbTransaction.getConnection();
/*     */ 
/* 304 */       sSQL = "SELECT tr.Name FROM TaxRegion tr WHERE tr.Id = ? ";
/*     */ 
/* 308 */       psql = con.prepareStatement(sSQL);
/* 309 */       psql.setLong(1, a_lTaxRegionId);
/* 310 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 312 */       while (rs.next())
/*     */       {
/* 314 */         tr.setId(a_lTaxRegionId);
/* 315 */         tr.setName(rs.getString("Name"));
/*     */       }
/*     */ 
/* 318 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 324 */       throw new Bn2Exception("TaxManager.getTaxRegion: Exception occurred: " + e, e);
/*     */     }
/*     */ 
/* 327 */     return tr;
/*     */   }
/*     */ 
/*     */   public void saveTaxValue(DBTransaction a_dbTransaction, TaxValue a_tv)
/*     */     throws Bn2Exception
/*     */   {
/* 346 */     long lTaxTypeId = a_tv.getTaxType().getId();
/* 347 */     long lTaxRegionId = a_tv.getTaxRegion().getId();
/*     */ 
/* 350 */     TaxValue oldTV = getTaxValue(a_dbTransaction, lTaxTypeId, lTaxRegionId);
/*     */ 
/* 352 */     if ((oldTV != null) && (oldTV.getTaxType().getId() > 0L))
/*     */     {
/* 354 */       updateTaxValue(a_dbTransaction, a_tv);
/*     */     }
/*     */     else
/*     */     {
/* 358 */       newTaxValue(a_dbTransaction, a_tv);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void newTaxValue(DBTransaction a_dbTransaction, TaxValue a_tv)
/*     */     throws Bn2Exception
/*     */   {
/* 378 */     String ksMethodName = "newTaxValue";
/* 379 */     Connection con = null;
/* 380 */     String sSQL = null;
/* 381 */     PreparedStatement psql = null;
/*     */     try
/*     */     {
/* 385 */       con = a_dbTransaction.getConnection();
/*     */ 
/* 387 */       sSQL = "INSERT INTO TaxValue (TaxTypeId, TaxRegionId, Percent, ZeroIfTaxNumberGiven) VALUES (?, ? ,? ,?)";
/*     */ 
/* 390 */       psql = con.prepareStatement(sSQL);
/* 391 */       int iCol = 1;
/* 392 */       psql.setLong(iCol++, a_tv.getTaxType().getId());
/* 393 */       psql.setLong(iCol++, a_tv.getTaxRegion().getId());
/* 394 */       psql.setFloat(iCol++, a_tv.getTaxPercent().getNumber());
/* 395 */       psql.setBoolean(iCol++, a_tv.getZeroIfTaxNumberGiven());
/*     */ 
/* 397 */       psql.executeUpdate();
/* 398 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 403 */       throw new Bn2Exception("TaxManager.newTaxValue: Exception occurred: " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateTaxValue(DBTransaction a_dbTransaction, TaxValue a_tv)
/*     */     throws Bn2Exception
/*     */   {
/* 423 */     String ksMethodName = "updateTaxValue";
/* 424 */     Connection con = null;
/* 425 */     String sSQL = null;
/* 426 */     PreparedStatement psql = null;
/*     */     try
/*     */     {
/* 430 */       con = a_dbTransaction.getConnection();
/*     */ 
/* 432 */       sSQL = "UPDATE TaxValue SET Percent = ?, ZeroIfTaxNumberGiven = ? WHERE TaxTypeId = ? AND TaxRegionId = ? ";
/*     */ 
/* 435 */       psql = con.prepareStatement(sSQL);
/* 436 */       int iCol = 1;
/* 437 */       psql.setFloat(iCol++, a_tv.getTaxPercent().getNumber());
/* 438 */       psql.setBoolean(iCol++, a_tv.getZeroIfTaxNumberGiven());
/* 439 */       psql.setLong(iCol++, a_tv.getTaxType().getId());
/* 440 */       psql.setLong(iCol++, a_tv.getTaxRegion().getId());
/* 441 */       psql.executeUpdate();
/* 442 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 447 */       throw new Bn2Exception("TaxManager.updateTaxValue: Exception occurred: " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteTaxValue(DBTransaction a_dbTransaction, long a_lTaxTypeId, long a_lTaxRegionId)
/*     */     throws Bn2Exception
/*     */   {
/* 468 */     String ksMethodName = "deleteTaxValue";
/* 469 */     Connection con = null;
/* 470 */     String sSQL = null;
/* 471 */     PreparedStatement psql = null;
/*     */     try
/*     */     {
/* 475 */       con = a_dbTransaction.getConnection();
/*     */ 
/* 477 */       sSQL = "DELETE FROM TaxValue WHERE TaxTypeId = ? AND TaxRegionId = ? ";
/*     */ 
/* 479 */       psql = con.prepareStatement(sSQL);
/* 480 */       psql.setLong(1, a_lTaxTypeId);
/* 481 */       psql.setLong(2, a_lTaxRegionId);
/* 482 */       psql.executeUpdate();
/* 483 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 488 */       throw new Bn2Exception("TaxManager.deleteTaxValue: Exception occurred: " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Vector getTaxTypeList()
/*     */     throws Bn2Exception
/*     */   {
/* 503 */     Vector vec = RefDataManager.getNameList(this.m_dataSource, "SELECT Id, Name FROM TaxType ORDER BY Name");
/* 504 */     return vec;
/*     */   }
/*     */ 
/*     */   public Vector getTaxRegionList()
/*     */     throws Bn2Exception
/*     */   {
/* 517 */     Vector vec = RefDataManager.getNameList(this.m_dataSource, "SELECT Id, Name FROM TaxRegion ORDER BY Name");
/* 518 */     return vec;
/*     */   }
/*     */ 
/*     */   private long mapCountryToTaxRegion(DBTransaction a_dbTransaction, long a_lCountryId)
/*     */     throws Bn2Exception
/*     */   {
/* 539 */     String ksMethodName = "mapCountryToTaxRegion";
/* 540 */     Connection con = null;
/* 541 */     String sSQL = null;
/* 542 */     PreparedStatement psql = null;
/* 543 */     long lRegionId = 0L;
/*     */     try
/*     */     {
/* 547 */       con = a_dbTransaction.getConnection();
/*     */ 
/* 549 */       sSQL = "SELECT TaxRegionId FROM CountryInTaxRegion WHERE CountryId = ? ";
/*     */ 
/* 553 */       psql = con.prepareStatement(sSQL);
/* 554 */       psql.setLong(1, a_lCountryId);
/* 555 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 558 */       while (rs.next())
/*     */       {
/* 560 */         lRegionId = rs.getLong("TaxRegionId");
/*     */       }
/*     */ 
/* 563 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 569 */       throw new Bn2Exception("TaxManager.mapCountryToTaxRegion: Exception occurred: " + e, e);
/*     */     }
/*     */ 
/* 572 */     return lRegionId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.tax.service.TaxManager
 * JD-Core Version:    0.6.0
 */