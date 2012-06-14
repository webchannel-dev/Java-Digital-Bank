/*    */ package com.bright.assetbank.user.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class GroupExclusionForm extends Bn2Form
/*    */ {
/* 31 */   private long m_lGroupId = 0L;
/* 32 */   private Vector m_vecExcludedList = null;
/* 33 */   private Vector m_vecExcludableItems = null;
/*    */ 
/*    */   public long getGroupId()
/*    */   {
/* 37 */     return this.m_lGroupId;
/*    */   }
/*    */ 
/*    */   public void setGroupId(long a_lGroupId) {
/* 41 */     this.m_lGroupId = a_lGroupId;
/*    */   }
/*    */ 
/*    */   public Vector getExcludedList()
/*    */   {
/* 46 */     return this.m_vecExcludedList;
/*    */   }
/*    */ 
/*    */   public void setExcludedList(Vector a_vecExcludedList) {
/* 50 */     this.m_vecExcludedList = a_vecExcludedList;
/*    */   }
/*    */ 
/*    */   public void setExcludableItems(Vector a_vecExcludableItems)
/*    */   {
/* 55 */     this.m_vecExcludableItems = a_vecExcludableItems;
/*    */   }
/*    */ 
/*    */   public Vector getExcludableItems()
/*    */   {
/* 60 */     return this.m_vecExcludableItems;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.form.GroupExclusionForm
 * JD-Core Version:    0.6.0
 */