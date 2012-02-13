/*    */ package com.bright.assetbank.database;
/*    */ 
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.attribute.bean.AttributeType;
/*    */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*    */ import com.bright.assetbank.attribute.constant.AttributeStorageType;
/*    */ 
/*    */ public class OracleSQLGenerator extends com.bright.framework.database.sql.OracleSQLGenerator
/*    */   implements AssetBankSql, AttributeConstants
/*    */ {
/*    */   public String dbTypeNameForAttributeType(AttributeType a_attributeType, int a_iNumDecimalPlaces)
/*    */   {
/* 41 */     String ksMethodName = "dbTypeNameForAttributeType";
/*    */ 
/* 43 */     if (a_attributeType.getAttributeStorageType() != AttributeStorageType.VALUE_PER_ASSET)
/*    */     {
/* 45 */       throw new IllegalArgumentException("dbTypeNameForAttributeType called with non VALUE_PER_ASSET attribute type");
/*    */     }
/*    */ 
/* 48 */     long lAttributeTypeId = a_attributeType.getId();
/* 49 */     if ((lAttributeTypeId == 1L) || (lAttributeTypeId == 2L))
/*    */     {
/* 52 */       return "NCLOB";
/*    */     }
/* 54 */     if ((lAttributeTypeId == 14L) || (lAttributeTypeId == 15L))
/*    */     {
/* 57 */       return "NVARCHAR2(" + AssetBankSettings.getMaxVarcharAttributeLength() + ")";
/*    */     }
/* 59 */     if (lAttributeTypeId == 3L)
/*    */     {
/* 61 */       return "DATE";
/*    */     }
/* 63 */     if (lAttributeTypeId == 8L)
/*    */     {
/* 65 */       return "DATE";
/*    */     }
/* 67 */     if (lAttributeTypeId == 9L)
/*    */     {
/* 69 */       return "NVARCHAR2(2000)";
/*    */     }
/* 71 */     if (lAttributeTypeId == 11L)
/*    */     {
/* 73 */       return "NVARCHAR2(60)";
/*    */     }
/* 75 */     if (lAttributeTypeId == 12L)
/*    */     {
/* 77 */       return "NVARCHAR2(2000)";
/*    */     }
/* 79 */     if (lAttributeTypeId == 16L)
/*    */     {
/* 81 */       return "DECIMAL(38," + a_iNumDecimalPlaces + ")";
/*    */     }
/*    */ 
/* 85 */     throw new IllegalArgumentException("Unknown attribute type " + lAttributeTypeId);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.database.OracleSQLGenerator
 * JD-Core Version:    0.6.0
 */