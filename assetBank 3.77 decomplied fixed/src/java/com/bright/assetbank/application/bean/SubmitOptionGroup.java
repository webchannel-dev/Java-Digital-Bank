/*    */ package com.bright.assetbank.application.bean;
/*    */ 
/*    */ public class SubmitOptionGroup
/*    */ {
/* 22 */   private SubmitOptions m_options = null;
/* 23 */   private String m_sTitle = null;
/* 24 */   private String m_sIdentifier = null;
/*    */ 
/*    */   public void setOptions(SubmitOptions a_options)
/*    */   {
/* 28 */     this.m_options = a_options;
/*    */   }
/*    */ 
/*    */   public SubmitOptions getOptions()
/*    */   {
/* 33 */     return this.m_options;
/*    */   }
/*    */ 
/*    */   public void setTitle(String a_sTitle)
/*    */   {
/* 38 */     this.m_sTitle = a_sTitle;
/*    */   }
/*    */ 
/*    */   public String getTitle()
/*    */   {
/* 43 */     return this.m_sTitle;
/*    */   }
/*    */ 
/*    */   public void setIdentifier(String a_sIdentifier)
/*    */   {
/* 48 */     this.m_sIdentifier = a_sIdentifier;
/*    */   }
/*    */ 
/*    */   public String getIdentifier()
/*    */   {
/* 53 */     return this.m_sIdentifier;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.SubmitOptionGroup
 * JD-Core Version:    0.6.0
 */