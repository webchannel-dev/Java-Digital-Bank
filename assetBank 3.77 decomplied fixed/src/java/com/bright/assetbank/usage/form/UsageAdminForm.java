/*    */ package com.bright.assetbank.usage.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.assetbank.usage.bean.UsageType;
/*    */ import com.bright.framework.constant.FrameworkSettings;
/*    */ import com.bright.framework.language.util.LanguageUtils;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class UsageAdminForm extends Bn2Form
/*    */ {
/* 37 */   private Vector m_vUsageTypes = null;
/*    */ 
/* 39 */   private UsageType m_UsageType = null;
/*    */ 
/* 41 */   private UsageType m_parentUsageType = null;
/*    */ 
/*    */   public UsageAdminForm()
/*    */   {
/* 46 */     this.m_parentUsageType = new UsageType();
/*    */   }
/*    */ 
/*    */   public Vector getUsageTypes()
/*    */   {
/* 52 */     return this.m_vUsageTypes;
/*    */   }
/*    */ 
/*    */   public void setUsageTypes(Vector a_sUsageTypes)
/*    */   {
/* 58 */     this.m_vUsageTypes = a_sUsageTypes;
/*    */   }
/*    */ 
/*    */   public UsageType getUsageType()
/*    */   {
/* 64 */     if (this.m_UsageType == null)
/*    */     {
/* 66 */       this.m_UsageType = new UsageType();
/*    */ 
/* 69 */       if (FrameworkSettings.getSupportMultiLanguage())
/*    */       {
/* 71 */         LanguageUtils.createEmptyTranslations(this.m_UsageType, 20);
/*    */       }
/*    */     }
/*    */ 
/* 75 */     return this.m_UsageType;
/*    */   }
/*    */ 
/*    */   public void setUsageType(UsageType a_sUsageType)
/*    */   {
/* 81 */     this.m_UsageType = a_sUsageType;
/*    */   }
/*    */ 
/*    */   public UsageType getParentUsageType()
/*    */   {
/* 87 */     return this.m_parentUsageType;
/*    */   }
/*    */ 
/*    */   public void setParentUsageType(UsageType a_sParentUsageType) {
/* 91 */     this.m_parentUsageType = a_sParentUsageType;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.form.UsageAdminForm
 * JD-Core Version:    0.6.0
 */