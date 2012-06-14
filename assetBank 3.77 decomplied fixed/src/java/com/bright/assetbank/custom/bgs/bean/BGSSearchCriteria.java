/*    */ package com.bright.assetbank.custom.bgs.bean;
/*    */ 
/*    */ public class BGSSearchCriteria
/*    */ {
/*    */   private PlacenameCriteria m_placenameCriteria;
/*    */   private PointCriteria m_pointCriteria;
/*    */   private BoundingBoxCriteria m_boundingBoxCriteria;
/*    */   private KeywordCriteria m_keywordCriteria;
/*    */ 
/*    */   public BGSSearchCriteria()
/*    */   {
/*    */   }
/*    */ 
/*    */   public BGSSearchCriteria(PlacenameCriteria a_placenameCriteria, PointCriteria a_pointCriteria, BoundingBoxCriteria a_boundingBoxCriteria, KeywordCriteria a_keywordCriteria)
/*    */   {
/* 41 */     this.m_placenameCriteria = a_placenameCriteria;
/* 42 */     this.m_pointCriteria = a_pointCriteria;
/* 43 */     this.m_boundingBoxCriteria = a_boundingBoxCriteria;
/* 44 */     this.m_keywordCriteria = a_keywordCriteria;
/*    */   }
/*    */ 
/*    */   public boolean isEmpty()
/*    */   {
/* 51 */     boolean bEmpty = (this.m_placenameCriteria == null) && (this.m_pointCriteria == null) && (this.m_boundingBoxCriteria == null) && (this.m_keywordCriteria == null);
/* 52 */     return bEmpty;
/*    */   }
/*    */ 
/*    */   public PlacenameCriteria getPlacenameCriteria()
/*    */   {
/* 57 */     return this.m_placenameCriteria;
/*    */   }
/*    */ 
/*    */   public void setPlacenameCriteria(PlacenameCriteria a_placenameCriteria)
/*    */   {
/* 62 */     this.m_placenameCriteria = a_placenameCriteria;
/*    */   }
/*    */ 
/*    */   public PointCriteria getPointCriteria()
/*    */   {
/* 67 */     return this.m_pointCriteria;
/*    */   }
/*    */ 
/*    */   public void setPointCriteria(PointCriteria a_pointCriteria)
/*    */   {
/* 72 */     this.m_pointCriteria = a_pointCriteria;
/*    */   }
/*    */ 
/*    */   public BoundingBoxCriteria getBoundingBoxCriteria()
/*    */   {
/* 77 */     return this.m_boundingBoxCriteria;
/*    */   }
/*    */ 
/*    */   public void setBoundingBoxCriteria(BoundingBoxCriteria a_boundingBoxCriteria)
/*    */   {
/* 82 */     this.m_boundingBoxCriteria = a_boundingBoxCriteria;
/*    */   }
/*    */ 
/*    */   public KeywordCriteria getKeywordCriteria()
/*    */   {
/* 87 */     return this.m_keywordCriteria;
/*    */   }
/*    */ 
/*    */   public void setKeywordCriteria(KeywordCriteria a_keywordCriteria)
/*    */   {
/* 92 */     this.m_keywordCriteria = a_keywordCriteria;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.bgs.bean.BGSSearchCriteria
 * JD-Core Version:    0.6.0
 */