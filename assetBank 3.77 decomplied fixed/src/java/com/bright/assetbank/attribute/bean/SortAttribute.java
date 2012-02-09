/*    */ package com.bright.assetbank.attribute.bean;
/*    */ 
/*    */ public class SortAttribute
/*    */ {
/* 28 */   private Attribute m_attribute = new Attribute();
/* 29 */   private int m_iOrder = -1;
/* 30 */   private int m_iType = 0;
/* 31 */   private long m_lSortAreaId = 0L;
/* 32 */   private boolean m_bReverse = false;
/*    */ 
/*    */   public Attribute getAttribute()
/*    */   {
/* 38 */     return this.m_attribute;
/*    */   }
/*    */ 
/*    */   public void setAttribute(Attribute a_attribute)
/*    */   {
/* 44 */     this.m_attribute = a_attribute;
/*    */   }
/*    */ 
/*    */   public boolean getReverse()
/*    */   {
/* 50 */     return this.m_bReverse;
/*    */   }
/*    */ 
/*    */   public void setReverse(boolean a_bReverse)
/*    */   {
/* 56 */     this.m_bReverse = a_bReverse;
/*    */   }
/*    */ 
/*    */   public int getOrder()
/*    */   {
/* 62 */     return this.m_iOrder;
/*    */   }
/*    */ 
/*    */   public void setOrder(int a_iOrder)
/*    */   {
/* 68 */     this.m_iOrder = a_iOrder;
/*    */   }
/*    */ 
/*    */   public int getType()
/*    */   {
/* 74 */     return this.m_iType;
/*    */   }
/*    */ 
/*    */   public void setType(int a_iType)
/*    */   {
/* 80 */     this.m_iType = a_iType;
/*    */   }
/*    */ 
/*    */   public void setSortAreaId(long a_lSortAreaId)
/*    */   {
/* 85 */     this.m_lSortAreaId = a_lSortAreaId;
/*    */   }
/*    */ 
/*    */   public long getSortAreaId()
/*    */   {
/* 90 */     return this.m_lSortAreaId;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.bean.SortAttribute
 * JD-Core Version:    0.6.0
 */