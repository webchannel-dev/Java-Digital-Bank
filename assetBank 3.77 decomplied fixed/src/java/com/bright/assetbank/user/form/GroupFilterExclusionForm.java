/*    */ package com.bright.assetbank.user.form;
/*    */ 
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class GroupFilterExclusionForm extends GroupExclusionForm
/*    */ {
/* 29 */   private Vector m_vecLinkedFilters = null;
/* 30 */   private Vector m_vecGlobalFilters = null;
/*    */ 
/*    */   public Vector getLinkedFilters()
/*    */   {
/* 34 */     return this.m_vecLinkedFilters;
/*    */   }
/*    */ 
/*    */   public void setLinkedFilters(Vector a_vecLinkedFilters)
/*    */   {
/* 39 */     this.m_vecLinkedFilters = a_vecLinkedFilters;
/*    */   }
/*    */ 
/*    */   public Vector getGlobalFilters()
/*    */   {
/* 44 */     return this.m_vecGlobalFilters;
/*    */   }
/*    */ 
/*    */   public void setGlobalFilters(Vector a_vecGlobalFilters)
/*    */   {
/* 49 */     this.m_vecGlobalFilters = a_vecGlobalFilters;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.form.GroupFilterExclusionForm
 * JD-Core Version:    0.6.0
 */