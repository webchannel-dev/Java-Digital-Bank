/*     */ package com.bright.assetbank.attribute.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeType;
/*     */ import com.bright.assetbank.attribute.constant.AttributeStorageType;
/*     */ import com.bright.assetbank.database.AssetBankSql;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.exception.RowSizeTooLargeException;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.util.CollectionUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class AttributeStorageManager extends Bn2Manager
/*     */ {
/*     */   private AttributeManager m_attributeManager;
/*     */   private DBTransactionManager m_transactionManager;
/*     */ 
/*     */   public void createStorageForAttribute(DBTransaction a_transaction, Attribute a_attribute)
/*     */     throws Bn2Exception
/*     */   {
/*  77 */     String ksMethodName = "createStorageForAttribute";
/*     */ 
/*  80 */     if (a_attribute.getStatic())
/*     */     {
/*  82 */       return;
/*     */     }
/*     */ 
/*  85 */     AttributeType attributeType = this.m_attributeManager.getAttributeTypeById(a_attribute.getTypeId());
/*     */ 
/*  88 */     if (attributeType.getAttributeStorageType() != AttributeStorageType.VALUE_PER_ASSET)
/*     */     {
/*  90 */       return;
/*     */     }
/*     */ 
/*  93 */     AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*     */ 
/*  95 */     DBTransaction transaction = a_transaction;
/*  96 */     if (transaction == null)
/*     */     {
/*  98 */       transaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */     try
/*     */     {
/* 102 */       Connection con = transaction.getConnection();
/*     */ 
/* 105 */       Set existingValueColumnNames = getAndLockValueColumnNames(con);
/*     */ 
/* 110 */       existingValueColumnNames.add("assetid");
/*     */ 
/* 112 */       int iMaxColumnNameLength = con.getMetaData().getMaxColumnNameLength();
/*     */ 
/* 115 */       iMaxColumnNameLength = iMaxColumnNameLength == 0 ? 30 : iMaxColumnNameLength;
/*     */ 
/* 118 */       String sNewValueColumnName = columnNameFromAttributeLabel(existingValueColumnNames, a_attribute.getLabel(), iMaxColumnNameLength);
/*     */ 
/* 120 */       String sDBColumnType = sqlGenerator.dbTypeNameForAttributeType(attributeType, a_attribute.getMaxDecimalPlaces());
/*     */ 
/* 126 */       a_attribute.setValueColumnName(sNewValueColumnName);
/* 127 */       this.m_attributeManager.saveAttribute(transaction, a_attribute);
/*     */       try
/*     */       {
/* 134 */         addColumn(con, "AssetAttributeValues", sNewValueColumnName, sDBColumnType);
/*     */       }
/*     */       catch (SQLException e)
/*     */       {
/* 138 */         if (sqlGenerator.isRowSizeTooLarge(e))
/*     */         {
/* 143 */           a_attribute.setValueColumnName(null);
/* 144 */           this.m_attributeManager.saveAttribute(transaction, a_attribute);
/*     */ 
/* 150 */           con.commit();
/* 151 */           throw new RowSizeTooLargeException(e);
/*     */         }
/*     */ 
/* 156 */         throw e;
/*     */       }
/*     */ 
/* 160 */       if (a_attribute.getIsTranslatable())
/*     */       {
/* 167 */         addColumn(con, "TranslatedAssetAttributeValues", sNewValueColumnName, sDBColumnType);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 174 */       throw new Bn2Exception("SQL Exception in createStorageForAttribute", e);
/*     */     }
/*     */     finally
/*     */     {
/* 178 */       if (a_transaction == null)
/*     */       {
/*     */         try
/*     */         {
/* 182 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 186 */           this.m_logger.error("SQL Exception whilst trying to close connection ", sqle);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteStorageForAttribute(DBTransaction a_transaction, Attribute a_attribute)
/*     */     throws Bn2Exception
/*     */   {
/* 201 */     String ksMethodName = "deleteStorageForAttribute";
/*     */ 
/* 204 */     if (a_attribute.getStatic())
/*     */     {
/* 206 */       return;
/*     */     }
/*     */ 
/* 209 */     AttributeType attributeType = this.m_attributeManager.getAttributeTypeById(a_attribute.getTypeId());
/*     */ 
/* 212 */     if (attributeType.getAttributeStorageType() != AttributeStorageType.VALUE_PER_ASSET)
/*     */     {
/* 214 */       return;
/*     */     }
/*     */ 
/* 217 */     String sValueColumnName = a_attribute.getValueColumnName();
/*     */ 
/* 222 */     if (StringUtils.isEmpty(sValueColumnName))
/*     */     {
/* 224 */       this.m_logger.error("deleteStorageForAttribute: ValueColumnName is empty - no storage to delete");
/* 225 */       return;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 230 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 235 */       dropColumn(con, "AssetAttributeValues", sValueColumnName);
/* 236 */       if (a_attribute.getIsTranslatable())
/*     */       {
/* 238 */         dropColumn(con, "TranslatedAssetAttributeValues", sValueColumnName);
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*     */       throw new Bn2Exception("SQL Exception in deleteStorageForAttribute", e);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 258 */         a_transaction.commit();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 262 */         this.m_logger.error("SQL Exception whilst trying to close connection ", sqle);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addColumn(Connection a_con, String a_sTableName, String a_sValueColumnName, String a_sDBColumnType)
/*     */     throws SQLException
/*     */   {
/* 280 */     this.m_logger.info("Creating column " + a_sTableName + "." + a_sValueColumnName + " of type " + a_sDBColumnType);
/*     */ 
/* 286 */     String sSQL = "ALTER TABLE " + a_sTableName + " ADD " + a_sValueColumnName + " " + a_sDBColumnType + " NULL";
/*     */ 
/* 293 */     this.m_logger.debug(sSQL);
/* 294 */     PreparedStatement psql = a_con.prepareStatement(sSQL);
/*     */ 
/* 296 */     psql.execute();
/*     */   }
/*     */ 
/*     */   private void dropColumn(Connection a_con, String a_sTableName, String a_sValueColumnName)
/*     */     throws SQLException
/*     */   {
/* 309 */     this.m_logger.info("Dropping column " + a_sTableName + "." + a_sValueColumnName);
/*     */ 
/* 315 */     PreparedStatement psql = a_con.prepareStatement("ALTER TABLE " + a_sTableName + " DROP COLUMN " + a_sValueColumnName);
/*     */ 
/* 321 */     psql.execute();
/*     */   }
/*     */ 
/*     */   private Set<String> getAndLockValueColumnNames(Connection a_con)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/* 332 */     ApplicationSql sqlGenerator = SQLGenerator.getInstance();
/* 333 */     String sSQL = sqlGenerator.getAsSelectForUpdate("SELECT ValueColumnName FROM Attribute");
/*     */ 
/* 336 */     PreparedStatement stmt = a_con.prepareStatement(sSQL);
/*     */ 
/* 338 */     ResultSet rs = stmt.executeQuery();
/* 339 */     Set columnNames = new HashSet();
/* 340 */     while (rs.next())
/*     */     {
/* 342 */       String sColumnName = rs.getString("ValueColumnName");
/* 343 */       if ((StringUtils.isNotEmpty(sColumnName)) && (!rs.wasNull()))
/*     */       {
/* 346 */         columnNames.add(sColumnName);
/*     */       }
/*     */     }
/* 349 */     rs.close();
/*     */ 
/* 351 */     return columnNames;
/*     */   }
/*     */ 
/*     */   static String columnNameFromAttributeLabel(Set<String> a_existingColumnNames, String a_sLabel, int a_iMaxColumnNameLength)
/*     */   {
/* 358 */     char[] labelChars = a_sLabel.toCharArray();
/* 359 */     StringBuilder sbColumnName = new StringBuilder();
/* 360 */     int i = 0;
/* 361 */     while ((i < labelChars.length) && (sbColumnName.length() < a_iMaxColumnNameLength))
/*     */     {
/* 364 */       char labelChar = labelChars[i];
/*     */ 
/* 366 */       if (labelChar < '')
/*     */       {
/* 368 */         if ((Character.isDigit(labelChar)) && (i != 0))
/*     */         {
/* 370 */           sbColumnName.append(labelChar);
/*     */         }
/* 372 */         else if (Character.isLetter(labelChar))
/*     */         {
/* 374 */           sbColumnName.append(Character.toLowerCase(labelChar));
/*     */         }
/*     */       }
/* 362 */       i++;
/*     */     }
/*     */ 
/* 380 */     if (sbColumnName.length() == 0)
/*     */     {
/* 382 */       sbColumnName.append("untitled");
/*     */     }
/*     */ 
/* 387 */     String sColumnName = sbColumnName.toString();
/* 388 */     String sOriginalColumnName = sColumnName;
/* 389 */     int iUniquifier = 1;
/* 390 */     while ((a_existingColumnNames.contains(sColumnName)) || (DBReservedWords.c_kAllReservedWords.contains(sColumnName)))
/*     */     {
/* 393 */       iUniquifier++;
/* 394 */       String sUniquifier = String.valueOf(iUniquifier);
/* 395 */       int iUniquifierLen = sUniquifier.length();
/*     */ 
/* 398 */       if (iUniquifierLen == a_iMaxColumnNameLength)
/*     */       {
/* 400 */         throw new RuntimeException("Couldn't construct a unique column name for label \"" + a_sLabel + "\".  " + "sOriginalColumnName=\"" + sOriginalColumnName + "\",  " + "iUniquifier=" + iUniquifier + ",  " + "a_iMaxColumnNameLength=" + a_iMaxColumnNameLength + ",  " + "a_existingColumnNames=" + CollectionUtil.join(a_existingColumnNames, ","));
/*     */       }
/*     */ 
/* 407 */       if (sOriginalColumnName.length() + iUniquifierLen > a_iMaxColumnNameLength)
/*     */       {
/* 409 */         sColumnName = sOriginalColumnName.substring(0, a_iMaxColumnNameLength - iUniquifierLen) + sUniquifier;
/*     */       }
/*     */       else
/*     */       {
/* 414 */         sColumnName = sOriginalColumnName + sUniquifier;
/*     */       }
/*     */     }
/* 417 */     return sColumnName;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 425 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_transactionManager)
/*     */   {
/* 430 */     this.m_transactionManager = a_transactionManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.service.AttributeStorageManager
 * JD-Core Version:    0.6.0
 */