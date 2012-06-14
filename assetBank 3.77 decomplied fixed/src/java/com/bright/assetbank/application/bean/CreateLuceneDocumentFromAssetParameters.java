/*    */ package com.bright.assetbank.application.bean;
/*    */ 
/*    */ import com.bright.framework.language.bean.Language;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class CreateLuceneDocumentFromAssetParameters
/*    */   implements Serializable
/*    */ {
/*    */   private String[] m_asSortFieldNames;
/*    */   private Language m_language;
/*    */ 
/*    */   public CreateLuceneDocumentFromAssetParameters(String[] a_asSortFieldNames, Language a_language)
/*    */   {
/* 34 */     this.m_asSortFieldNames = a_asSortFieldNames;
/* 35 */     this.m_language = a_language;
/*    */   }
/*    */ 
/*    */   public String[] getSortFieldNames()
/*    */   {
/* 40 */     return this.m_asSortFieldNames;
/*    */   }
/*    */ 
/*    */   public Language getLanguage()
/*    */   {
/* 45 */     return this.m_language;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.CreateLuceneDocumentFromAssetParameters
 * JD-Core Version:    0.6.0
 */