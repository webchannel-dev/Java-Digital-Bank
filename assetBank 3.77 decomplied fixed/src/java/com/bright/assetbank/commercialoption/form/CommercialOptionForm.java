/*    */ package com.bright.assetbank.commercialoption.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.assetbank.commercialoption.bean.CommercialOption;
/*    */ import com.bright.framework.constant.FrameworkSettings;
/*    */ import com.bright.framework.language.util.LanguageUtils;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class CommercialOptionForm extends Bn2Form
/*    */ {
/* 36 */   private Vector m_commercialOptionList = null;
/* 37 */   private CommercialOption m_commercialOption = null;
/*    */ 
/*    */   public CommercialOptionForm()
/*    */   {
/* 42 */     this.m_commercialOption = new CommercialOption();
/*    */ 
/* 45 */     if (FrameworkSettings.getSupportMultiLanguage())
/*    */     {
/* 47 */       LanguageUtils.createEmptyTranslations(this.m_commercialOption, 20);
/*    */     }
/*    */   }
/*    */ 
/*    */   public CommercialOption getCommercialOption()
/*    */   {
/* 56 */     return this.m_commercialOption;
/*    */   }
/*    */ 
/*    */   public void setCommercialOption(CommercialOption a_sCommercialOption)
/*    */   {
/* 64 */     this.m_commercialOption = a_sCommercialOption;
/*    */   }
/*    */ 
/*    */   public Vector getCommercialOptionList()
/*    */   {
/* 72 */     return this.m_commercialOptionList;
/*    */   }
/*    */ 
/*    */   public void setCommercialOptionList(Vector a_sCommercialOptionList)
/*    */   {
/* 80 */     this.m_commercialOptionList = a_sCommercialOptionList;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.commercialoption.form.CommercialOptionForm
 * JD-Core Version:    0.6.0
 */