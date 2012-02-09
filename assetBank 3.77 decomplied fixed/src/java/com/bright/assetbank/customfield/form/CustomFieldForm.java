/*    */ package com.bright.assetbank.customfield.form;
/*    */ 
/*    */ import com.bright.framework.constant.FrameworkSettings;
/*    */ import com.bright.framework.language.util.LanguageUtils;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class CustomFieldForm extends com.bright.framework.customfield.form.CustomFieldForm
/*    */ {
/* 34 */   private Vector m_vecOrgUnits = null;
/*    */ 
/*    */   public void setOrgUnits(Vector a_vecOrgUnits)
/*    */   {
/* 38 */     this.m_vecOrgUnits = a_vecOrgUnits;
/*    */   }
/*    */ 
/*    */   public Vector getOrgUnits()
/*    */   {
/* 43 */     return this.m_vecOrgUnits;
/*    */   }
/*    */ 
/*    */   public com.bright.framework.customfield.bean.CustomField getCustomField()
/*    */   {
/* 48 */     if (this.m_customField == null)
/*    */     {
/* 50 */       this.m_customField = new com.bright.assetbank.customfield.bean.CustomField();
/*    */ 
/* 53 */       if (FrameworkSettings.getSupportMultiLanguage())
/*    */       {
/* 55 */         LanguageUtils.createEmptyTranslations(this.m_customField, 20);
/*    */       }
/*    */     }
/* 58 */     return this.m_customField;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.customfield.form.CustomFieldForm
 * JD-Core Version:    0.6.0
 */