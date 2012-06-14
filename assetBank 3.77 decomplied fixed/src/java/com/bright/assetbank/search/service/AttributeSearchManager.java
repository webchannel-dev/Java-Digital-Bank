/*    */ package com.bright.assetbank.search.service;
/*    */ 
/*    */ import com.bn2web.common.service.Bn2Manager;
/*    */ import com.bright.assetbank.attribute.service.AttributeManager;
/*    */ import com.bright.framework.search.lucene.BrightAnalyzerFactory;
/*    */ import com.bright.framework.search.service.AnalyzerFactory;
/*    */ 
/*    */ public class AttributeSearchManager extends Bn2Manager
/*    */ {
/*    */   private AttributeManager m_attributeManager;
/*    */ 
/*    */   public AnalyzerFactory analyzerFactoryForLanguage(String a_sLanguageCode)
/*    */   {
/* 36 */     return analyzerFactoryForLanguage(a_sLanguageCode, true);
/*    */   }
/*    */ 
/*    */   public AnalyzerFactory analyzerFactoryForLanguage(String a_sLanguageCode, boolean a_bAllowStemming)
/*    */   {
/* 42 */     return new BrightAnalyzerFactory(this.m_attributeManager, a_sLanguageCode, a_bAllowStemming);
/*    */   }
/*    */ 
/*    */   public void setAttributeManager(AttributeManager a_attributeManager)
/*    */   {
/* 47 */     this.m_attributeManager = a_attributeManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.service.AttributeSearchManager
 * JD-Core Version:    0.6.0
 */