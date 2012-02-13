/*     */ package com.bright.assetbank.entity.bean;
/*     */ 
/*     */ import com.bright.framework.common.bean.BaseTranslatableStringDataBean;
/*     */ 
/*     */ public class BaseAssetEntity extends BaseTranslatableStringDataBean
/*     */ {
/*  30 */   private String m_sTermForSiblings = null;
/*  31 */   private String m_sTermForSibling = null;
/*  32 */   private String m_sChildRelationshipToName = null;
/*  33 */   private String m_sChildRelationshipToNamePlural = null;
/*  34 */   private String m_sChildRelationshipFromName = null;
/*  35 */   private String m_sChildRelationshipFromNamePlural = null;
/*     */ 
/*  37 */   private String m_sPeerRelationshipToName = null;
/*  38 */   private String m_sPeerRelationshipToNamePlural = null;
/*     */ 
/*     */   public String getTermForSiblings()
/*     */   {
/*  42 */     return this.m_sTermForSiblings;
/*     */   }
/*     */ 
/*     */   public void setTermForSiblings(String termForSiblings)
/*     */   {
/*  47 */     this.m_sTermForSiblings = termForSiblings;
/*     */   }
/*     */ 
/*     */   public String getTermForSibling()
/*     */   {
/*  52 */     return this.m_sTermForSibling;
/*     */   }
/*     */ 
/*     */   public void setTermForSibling(String termForSibling)
/*     */   {
/*  57 */     this.m_sTermForSibling = termForSibling;
/*     */   }
/*     */ 
/*     */   public String getChildRelationshipToName()
/*     */   {
/*  62 */     return this.m_sChildRelationshipToName;
/*     */   }
/*     */ 
/*     */   public void setChildRelationshipToName(String a_sChildRelationshipToName)
/*     */   {
/*  67 */     this.m_sChildRelationshipToName = a_sChildRelationshipToName;
/*     */   }
/*     */ 
/*     */   public String getChildRelationshipToNamePlural()
/*     */   {
/*  72 */     return this.m_sChildRelationshipToNamePlural;
/*     */   }
/*     */ 
/*     */   public void setChildRelationshipToNamePlural(String a_sChildRelationshipToNamePlural)
/*     */   {
/*  77 */     this.m_sChildRelationshipToNamePlural = a_sChildRelationshipToNamePlural;
/*     */   }
/*     */ 
/*     */   public String getChildRelationshipFromName()
/*     */   {
/*  82 */     return this.m_sChildRelationshipFromName;
/*     */   }
/*     */ 
/*     */   public void setChildRelationshipFromName(String a_sChildRelationshipFromName)
/*     */   {
/*  87 */     this.m_sChildRelationshipFromName = a_sChildRelationshipFromName;
/*     */   }
/*     */ 
/*     */   public String getChildRelationshipFromNamePlural()
/*     */   {
/*  92 */     return this.m_sChildRelationshipFromNamePlural;
/*     */   }
/*     */ 
/*     */   public void setChildRelationshipFromNamePlural(String a_sChildRelationshipFromNamePlural)
/*     */   {
/*  97 */     this.m_sChildRelationshipFromNamePlural = a_sChildRelationshipFromNamePlural;
/*     */   }
/*     */ 
/*     */   public String getPeerRelationshipToName()
/*     */   {
/* 102 */     return this.m_sPeerRelationshipToName;
/*     */   }
/*     */ 
/*     */   public void setPeerRelationshipToName(String a_sPeerRelationshipToName)
/*     */   {
/* 107 */     this.m_sPeerRelationshipToName = a_sPeerRelationshipToName;
/*     */   }
/*     */ 
/*     */   public String getPeerRelationshipToNamePlural()
/*     */   {
/* 112 */     return this.m_sPeerRelationshipToNamePlural;
/*     */   }
/*     */ 
/*     */   public void setPeerRelationshipToNamePlural(String a_sPeerRelationshipToNamePlural)
/*     */   {
/* 117 */     this.m_sPeerRelationshipToNamePlural = a_sPeerRelationshipToNamePlural;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.bean.BaseAssetEntity
 * JD-Core Version:    0.6.0
 */