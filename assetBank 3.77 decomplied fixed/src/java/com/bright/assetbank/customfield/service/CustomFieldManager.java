/*     */ package com.bright.assetbank.customfield.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnitMetadata;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.database.util.DBUtil;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class CustomFieldManager extends com.bright.framework.customfield.service.CustomFieldManager
/*     */ {
/*     */   protected String getCustomFieldSQLExtraFields()
/*     */   {
/*  42 */     return ", c.OrgUnitId";
/*     */   }
/*     */ 
/*     */   protected String getExtraUpdateFields()
/*     */   {
/*  47 */     return ", OrgUnitId=?";
/*     */   }
/*     */ 
/*     */   protected String getExtraAddFields()
/*     */   {
/*  52 */     return ", OrgUnitId";
/*     */   }
/*     */ 
/*     */   protected String getExtraAddFieldMarkers()
/*     */   {
/*  57 */     return ",?";
/*     */   }
/*     */ 
/*     */   protected void doExtraFields(PreparedStatement a_psql, int a_iIndex, com.bright.framework.customfield.bean.CustomField a_field)
/*     */     throws SQLException
/*     */   {
/*  65 */     com.bright.assetbank.customfield.bean.CustomField field = (com.bright.assetbank.customfield.bean.CustomField)a_field;
/*  66 */     DBUtil.setFieldIdOrNull(a_psql, a_iIndex, field.getOrgUnitId());
/*     */   }
/*     */ 
/*     */   protected com.bright.framework.customfield.bean.CustomField buildCustomField(ResultSet a_rs)
/*     */     throws SQLException
/*     */   {
/*  72 */     com.bright.assetbank.customfield.bean.CustomField newField = new com.bright.assetbank.customfield.bean.CustomField();
/*  73 */     super.populateCustomField(a_rs, newField);
/*  74 */     newField.setOrgUnitId(a_rs.getLong("OrgUnitId"));
/*     */ 
/*  76 */     return newField;
/*     */   }
/*     */ 
/*     */   protected String getOrderBy()
/*     */   {
/*  81 */     return "c.OrgUnitId, u.Description, c.SequenceNumber, c.FieldName, v.Value";
/*     */   }
/*     */ 
/*     */   protected String getExtraRestrictions(User a_user)
/*     */     throws Bn2Exception
/*     */   {
/*  87 */     ABUser temp = (ABUser)a_user;
/*  88 */     ApplicationSql sqlGenerator = SQLGenerator.getInstance();
/*     */ 
/*  91 */     String sExtra = "";
/*  92 */     if ((temp != null) && (temp.getOrgUnits() != null) && (temp.getOrgUnits().size() > 0))
/*     */     {
/*  94 */       sExtra = "AND (" + sqlGenerator.getNullCheckStatement("OrgUnitId") + " OR OrgUnitId IN (";
/*  95 */       for (int i = 0; i < temp.getOrgUnits().size(); i++)
/*     */       {
/*  97 */         long lUnitId = ((OrgUnitMetadata)temp.getOrgUnits().elementAt(i)).getId();
/*  98 */         sExtra = sExtra + lUnitId;
/*  99 */         if (i >= temp.getOrgUnits().size() - 1)
/*     */           continue;
/* 101 */         sExtra = sExtra + ",";
/*     */       }
/*     */ 
/* 104 */       sExtra = sExtra + "))";
/*     */     }
/* 106 */     return sExtra;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.customfield.service.CustomFieldManager
 * JD-Core Version:    0.6.0
 */