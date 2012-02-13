/*    */ package com.bright.assetbank.application.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.framework.common.bean.NameValueBean;
/*    */ 
/*    */ public class ValidateHyperlinkForm extends Bn2Form
/*    */ {
/*    */   private NameValueBean[] m_aHyperlinks;
/*    */   private String m_sBaseUrl;
/*    */   private String m_sLinkName;
/*    */ 
/*    */   public NameValueBean[] getHyperlinks()
/*    */   {
/* 40 */     return this.m_aHyperlinks;
/*    */   }
/*    */ 
/*    */   public void setHyperlinks(NameValueBean[] a_sHyperlinks)
/*    */   {
/* 46 */     this.m_aHyperlinks = a_sHyperlinks;
/*    */   }
/*    */ 
/*    */   public String getBaseUrl()
/*    */   {
/* 51 */     return this.m_sBaseUrl;
/*    */   }
/*    */ 
/*    */   public void setBaseUrl(String a_sBaseUrl)
/*    */   {
/* 56 */     this.m_sBaseUrl = a_sBaseUrl;
/*    */   }
/*    */ 
/*    */   public String getLinkName()
/*    */   {
/* 61 */     return this.m_sLinkName;
/*    */   }
/*    */ 
/*    */   public void setLinkName(String a_sLinkName)
/*    */   {
/* 66 */     this.m_sLinkName = a_sLinkName;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.form.ValidateHyperlinkForm
 * JD-Core Version:    0.6.0
 */