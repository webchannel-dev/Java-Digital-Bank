/*    */ package com.bright.assetbank.database;
/*    */ 
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.attribute.bean.AttributeType;
/*    */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*    */ import com.bright.assetbank.attribute.constant.AttributeStorageType;
/*    */ 
/*    */ public class MySqlSQLGenerator extends com.bright.framework.database.sql.MySqlSQLGenerator
/*    */   implements AssetBankSql, AttributeConstants
/*    */ {
/*    */   public String dbTypeNameForAttributeType(AttributeType a_attributeType, int a_iNumDecimalPlaces)
/*    */   {
/* 43 */     String ksMethodName = "dbTypeNameForAttributeType";
/*    */ 
/* 45 */     if (a_attributeType.getAttributeStorageType() != AttributeStorageType.VALUE_PER_ASSET)
/*    */     {
/* 47 */       throw new IllegalArgumentException("dbTypeNameForAttributeType called with non VALUE_PER_ASSET attribute type");
/*    */     }
/*    */ 
/* 50 */     long lAttributeTypeId = a_attributeType.getId();
/* 51 */     if ((lAttributeTypeId == 1L) || (lAttributeTypeId == 2L))
/*    */     {
/* 54 */       return "MEDIUMTEXT";
/*    */     }
/* 56 */     if ((lAttributeTypeId == 14L) || (lAttributeTypeId == 15L))
/*    */     {
/* 59 */       return "VARCHAR(" + AssetBankSettings.getMaxVarcharAttributeLength() + ")";
/*    */     }
/* 61 */     if (lAttributeTypeId == 3L)
/*    */     {
/* 63 */       return "DATE";
/*    */     }
/* 65 */     if (lAttributeTypeId == 8L)
/*    */     {
/* 67 */       return "DATETIME";
/*    */     }
/* 69 */     if (lAttributeTypeId == 9L)
/*    */     {
/* 75 */       return "TEXT";
/*    */     }
/* 77 */     if (lAttributeTypeId == 11L)
/*    */     {
/* 79 */       return "VARCHAR(60)";
/*    */     }
/* 81 */     if (lAttributeTypeId == 12L)
/*    */     {
/* 87 */       return "VARCHAR(2000)";
/*    */     }
/* 89 */     if (lAttributeTypeId == 16L)
/*    */     {
/* 91 */       return "DECIMAL(38," + a_iNumDecimalPlaces + ")";
/*    */     }
/*    */ 
/* 95 */     throw new IllegalArgumentException("Unknown attribute type " + lAttributeTypeId);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.database.MySqlSQLGenerator
 * JD-Core Version:    0.6.0
 */