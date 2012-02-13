/*    */ package com.bright.assetbank.marketing.bean;
/*    */ 
/*    */ import com.bright.framework.language.bean.Language;
/*    */ import com.bright.framework.language.bean.TranslatableWithLanguage;
/*    */ import com.bright.framework.language.bean.Translation;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class MarketingEmail extends BaseMarketingEmail
/*    */   implements TranslatableWithLanguage
/*    */ {
/*    */   private Vector m_vAssets;
/*    */   private Vector m_vTranslations;
/*    */ 
/*    */   public MarketingEmail()
/*    */   {
/* 31 */     this.m_vAssets = null;
/*    */ 
/* 34 */     this.m_vTranslations = null;
/*    */   }
/*    */ 
/*    */   public Translation createTranslation(Language a_language) {
/* 38 */     return new Translation(a_language);
/*    */   }
/*    */ 
/*    */   public Vector getTranslations()
/*    */   {
/* 43 */     if (this.m_vTranslations == null)
/*    */     {
/* 45 */       this.m_vTranslations = new Vector();
/*    */     }
/* 47 */     return this.m_vTranslations;
/*    */   }
/*    */ 
/*    */   public void setTranslations(Vector a_translations)
/*    */   {
/* 52 */     this.m_vTranslations = a_translations;
/*    */   }
/*    */ 
/*    */   public Vector getAssets()
/*    */   {
/* 65 */     if (this.m_vAssets == null)
/*    */     {
/* 67 */       this.m_vAssets = new Vector();
/*    */     }
/* 69 */     return this.m_vAssets;
/*    */   }
/*    */ 
/*    */   public void setAssets(Vector a_assets)
/*    */   {
/* 74 */     this.m_vAssets = a_assets;
/*    */   }
/*    */ 
/*    */   public class Translation extends BaseMarketingEmail
/*    */     implements com.bright.framework.language.bean.Translation
/*    */   {
/*    */     public Translation(Language a_language)
/*    */     {
/* 59 */       setLanguage(a_language);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.bean.MarketingEmail
 * JD-Core Version:    0.6.0
 */