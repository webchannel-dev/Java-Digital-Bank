/*    */ package com.bright.assetbank.attribute.util;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*    */ import com.bright.framework.database.sql.ApplicationSql;
/*    */ import com.bright.framework.database.sql.SQLGenerator;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class ListAttributeValueDBUtil
/*    */ {
/*    */   public static final String c_ksListAttributeValueBeanFields = "lav.Id lavId, lav.IsEditable, lav.Value, lav.AdditionalValue, lav.ActionOnAssetId, lav.MapToFieldValue ";
/*    */ 
/*    */   public static void populateListAttributeValueFromRS(AttributeValue a_attVal, ResultSet a_rs)
/*    */     throws SQLException, Bn2Exception
/*    */   {
/* 71 */     a_attVal.setId(a_rs.getLong("lavId"));
/*    */ 
/* 73 */     a_attVal.setEditable(a_rs.getBoolean("IsEditable"));
/* 74 */     a_attVal.setValue(a_rs.getString("Value"));
/* 75 */     a_attVal.setAdditionalValue(SQLGenerator.getInstance().getStringFromLargeTextField(a_rs, "AdditionalValue"));
/* 76 */     a_attVal.setActionOnAssetId(a_rs.getLong("ActionOnAssetId"));
/* 77 */     a_attVal.setMapToFieldValue(a_rs.getString("MapToFieldValue"));
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.util.ListAttributeValueDBUtil
 * JD-Core Version:    0.6.0
 */