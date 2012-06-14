/*    */ package com.bright.assetbank.custom.bgs.bean;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class KeywordCriteria
/*    */ {
/* 29 */   private Collection<Long> m_keywordIds = new ArrayList();
/*    */   private String m_relatedKeywordType;
/*    */ 
/*    */   public KeywordCriteria()
/*    */   {
/*    */   }
/*    */ 
/*    */   public KeywordCriteria(Collection<Long> a_keywordIds, String a_relatedKeywordType)
/*    */   {
/* 43 */     this.m_keywordIds = a_keywordIds;
/* 44 */     this.m_relatedKeywordType = a_relatedKeywordType;
/*    */   }
/*    */ 
/*    */   public Collection<Long> getKeywordIds()
/*    */   {
/* 49 */     return this.m_keywordIds;
/*    */   }
/*    */ 
/*    */   public void setKeywordIds(Collection<Long> a_keywordIds)
/*    */   {
/* 54 */     this.m_keywordIds = a_keywordIds;
/*    */   }
/*    */ 
/*    */   public String getRelatedKeywordType()
/*    */   {
/* 59 */     return this.m_relatedKeywordType;
/*    */   }
/*    */ 
/*    */   public void setRelatedKeywordType(String a_relatedKeywordType)
/*    */   {
/* 64 */     this.m_relatedKeywordType = a_relatedKeywordType;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.bgs.bean.KeywordCriteria
 * JD-Core Version:    0.6.0
 */