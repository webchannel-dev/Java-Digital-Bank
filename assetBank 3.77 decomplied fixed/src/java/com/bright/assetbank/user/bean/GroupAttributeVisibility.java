/*    */ package com.bright.assetbank.user.bean;
/*    */ 
/*    */ public class GroupAttributeVisibility
/*    */ {
/* 27 */   private long m_lGroupId = 0L;
/* 28 */   private long m_lAttributeId = 0L;
/* 29 */   private boolean m_bIsWriteable = false;
/*    */ 
/*    */   public long getAttributeId()
/*    */   {
/* 34 */     return this.m_lAttributeId;
/*    */   }
/*    */ 
/*    */   public void setAttributeId(long a_lAttributeId)
/*    */   {
/* 39 */     this.m_lAttributeId = a_lAttributeId;
/*    */   }
/*    */ 
/*    */   public long getGroupId()
/*    */   {
/* 44 */     return this.m_lGroupId;
/*    */   }
/*    */ 
/*    */   public void setGroupId(long a_lGroupId)
/*    */   {
/* 49 */     this.m_lGroupId = a_lGroupId;
/*    */   }
/*    */ 
/*    */   public boolean getIsWriteable()
/*    */   {
/* 54 */     return this.m_bIsWriteable;
/*    */   }
/*    */ 
/*    */   public void setIsWriteable(boolean a_bIsWriteable)
/*    */   {
/* 59 */     this.m_bIsWriteable = a_bIsWriteable;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.bean.GroupAttributeVisibility
 * JD-Core Version:    0.6.0
 */