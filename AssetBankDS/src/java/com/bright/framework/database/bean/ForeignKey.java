/*     */ package com.bright.framework.database.bean;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ForeignKey
/*     */   implements Serializable
/*     */ {
/*     */   private String m_catalog;
/*     */   private String m_schema;
/*     */   private String m_fkConstraintName;
/*     */   private String m_fkTableName;
/*     */   private String m_fkColumnName;
/*     */   private String m_pkTableName;
/*     */   private String m_pkColumnName;
/*     */ 
/*     */   public ForeignKey()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ForeignKey(String a_catalog, String a_schema, String a_fkConstraintName, String a_fkTableName, String a_fkColumnName, String a_pkTableName, String a_pkColumnName)
/*     */   {
/*  60 */     this.m_catalog = a_catalog;
/*  61 */     this.m_schema = a_schema;
/*  62 */     this.m_fkConstraintName = a_fkConstraintName;
/*  63 */     this.m_fkTableName = a_fkTableName;
/*  64 */     this.m_fkColumnName = a_fkColumnName;
/*  65 */     this.m_pkTableName = a_pkTableName;
/*  66 */     this.m_pkColumnName = a_pkColumnName;
/*     */   }
/*     */ 
/*     */   public String getCatalog()
/*     */   {
/*  71 */     return this.m_catalog;
/*     */   }
/*     */ 
/*     */   public void setCatalog(String a_catalog)
/*     */   {
/*  76 */     this.m_catalog = a_catalog;
/*     */   }
/*     */ 
/*     */   public String getSchema()
/*     */   {
/*  81 */     return this.m_schema;
/*     */   }
/*     */ 
/*     */   public void setSchema(String a_schema)
/*     */   {
/*  86 */     this.m_schema = a_schema;
/*     */   }
/*     */ 
/*     */   public String getFKConstraintName()
/*     */   {
/*  91 */     return this.m_fkConstraintName;
/*     */   }
/*     */ 
/*     */   public void setFKConstraintName(String a_fkConstraintName)
/*     */   {
/*  96 */     this.m_fkConstraintName = a_fkConstraintName;
/*     */   }
/*     */ 
/*     */   public String getFKTableName()
/*     */   {
/* 101 */     return this.m_fkTableName;
/*     */   }
/*     */ 
/*     */   public void setFKTableName(String a_fkTableName)
/*     */   {
/* 106 */     this.m_fkTableName = a_fkTableName;
/*     */   }
/*     */ 
/*     */   public String getFKColumnName()
/*     */   {
/* 111 */     return this.m_fkColumnName;
/*     */   }
/*     */ 
/*     */   public void setFKColumnName(String a_fkColumnName)
/*     */   {
/* 116 */     this.m_fkColumnName = a_fkColumnName;
/*     */   }
/*     */ 
/*     */   public String getPKTableName()
/*     */   {
/* 121 */     return this.m_pkTableName;
/*     */   }
/*     */ 
/*     */   public void setPKTableName(String a_pkTableName)
/*     */   {
/* 126 */     this.m_pkTableName = a_pkTableName;
/*     */   }
/*     */ 
/*     */   public String getPKColumnName()
/*     */   {
/* 131 */     return this.m_pkColumnName;
/*     */   }
/*     */ 
/*     */   public void setPKColumnName(String a_pkColumnName)
/*     */   {
/* 136 */     this.m_pkColumnName = a_pkColumnName;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.database.bean.ForeignKey
 * JD-Core Version:    0.6.0
 */