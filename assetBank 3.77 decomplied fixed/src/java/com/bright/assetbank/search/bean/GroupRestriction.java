/*    */ package com.bright.assetbank.search.bean;
/*    */ 
/*    */ public class GroupRestriction
/*    */ {
/* 28 */   private long m_lGroupId = -1L;
/* 29 */   private boolean m_bImplicit = false;
/*    */ 
/*    */   public GroupRestriction(long a_lGroupId, boolean a_bImplicit)
/*    */   {
/* 33 */     this.m_lGroupId = a_lGroupId;
/* 34 */     this.m_bImplicit = a_bImplicit;
/*    */   }
/*    */ 
/*    */   public long getGroupId()
/*    */   {
/* 39 */     return this.m_lGroupId;
/*    */   }
/*    */ 
/*    */   public boolean isImplicit()
/*    */   {
/* 44 */     return this.m_bImplicit;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.bean.GroupRestriction
 * JD-Core Version:    0.6.0
 */