/*    */ package com.bright.assetbank.user.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.StringDataBean;
/*    */ 
/*    */ public class Brand extends StringDataBean
/*    */ {
/* 34 */   private String m_sCssFile = null;
/* 35 */   private String m_sLogoFile = null;
/* 36 */   private long m_lLogoHeight = 0L;
/* 37 */   private long m_lLogoWidth = 0L;
/* 38 */   private String m_sLogoAlt = null;
/* 39 */   private String m_sContentListIdentifier = null;
/* 40 */   private String m_sCode = null;
/*    */ 
/*    */   public long getLogoHeight()
/*    */   {
/* 44 */     return this.m_lLogoHeight;
/*    */   }
/*    */ 
/*    */   public void setLogoHeight(long a_sLogoHeight) {
/* 48 */     this.m_lLogoHeight = a_sLogoHeight;
/*    */   }
/*    */ 
/*    */   public long getLogoWidth() {
/* 52 */     return this.m_lLogoWidth;
/*    */   }
/*    */ 
/*    */   public void setLogoWidth(long a_sLogoWidth) {
/* 56 */     this.m_lLogoWidth = a_sLogoWidth;
/*    */   }
/*    */ 
/*    */   public String getContentListIdentifier() {
/* 60 */     return this.m_sContentListIdentifier;
/*    */   }
/*    */ 
/*    */   public void setContentListIdentifier(String a_sContentListIdentifier) {
/* 64 */     this.m_sContentListIdentifier = a_sContentListIdentifier;
/*    */   }
/*    */ 
/*    */   public String getCssFile() {
/* 68 */     return this.m_sCssFile;
/*    */   }
/*    */ 
/*    */   public void setCssFile(String a_sCssFile) {
/* 72 */     this.m_sCssFile = a_sCssFile;
/*    */   }
/*    */ 
/*    */   public String getLogoAlt() {
/* 76 */     return this.m_sLogoAlt;
/*    */   }
/*    */ 
/*    */   public void setLogoAlt(String a_sLogoAlt) {
/* 80 */     this.m_sLogoAlt = a_sLogoAlt;
/*    */   }
/*    */ 
/*    */   public String getLogoFile() {
/* 84 */     return this.m_sLogoFile;
/*    */   }
/*    */ 
/*    */   public void setLogoFile(String a_sLogoFile) {
/* 88 */     this.m_sLogoFile = a_sLogoFile;
/*    */   }
/*    */ 
/*    */   public String getCode()
/*    */   {
/* 93 */     return this.m_sCode;
/*    */   }
/*    */ 
/*    */   public void setCode(String a_sCode) {
/* 97 */     this.m_sCode = a_sCode;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.bean.Brand
 * JD-Core Version:    0.6.0
 */