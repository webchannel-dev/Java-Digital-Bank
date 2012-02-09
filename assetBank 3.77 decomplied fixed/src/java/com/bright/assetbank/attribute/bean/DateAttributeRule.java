/*    */ package com.bright.assetbank.attribute.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.StringDataBean;
/*    */ 
/*    */ public abstract class DateAttributeRule extends StringDataBean
/*    */ {
/*    */   private StringDataBean m_attributeRef;
/* 33 */   private boolean m_bEnabled = false;
/*    */ 
/*    */   public DateAttributeRule()
/*    */   {
/* 40 */     this.m_attributeRef = new StringDataBean();
/*    */   }
/*    */ 
/*    */   public StringDataBean getAttributeRef()
/*    */   {
/* 46 */     return this.m_attributeRef;
/*    */   }
/*    */ 
/*    */   public void setAttributeRef(StringDataBean a_sAttributeRef) {
/* 50 */     this.m_attributeRef = a_sAttributeRef;
/*    */   }
/*    */ 
/*    */   public boolean getEnabled()
/*    */   {
/* 56 */     return this.m_bEnabled;
/*    */   }
/*    */ 
/*    */   public void setEnabled(boolean a_sEnabled) {
/* 60 */     this.m_bEnabled = a_sEnabled;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.bean.DateAttributeRule
 * JD-Core Version:    0.6.0
 */