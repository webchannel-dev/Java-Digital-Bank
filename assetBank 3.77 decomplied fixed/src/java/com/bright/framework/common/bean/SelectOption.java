/*    */ package com.bright.framework.common.bean;
/*    */ 
/*    */ public class SelectOption extends StringDataBean
/*    */ {
/* 32 */   private boolean m_bSelected = false;
/* 33 */   private boolean m_bLeaf = false;
/*    */ 
/*    */   public boolean getSelected()
/*    */   {
/* 37 */     return this.m_bSelected;
/*    */   }
/*    */ 
/*    */   public void setSelected(boolean a_sSelected) {
/* 41 */     this.m_bSelected = a_sSelected;
/*    */   }
/*    */ 
/*    */   public boolean getLeaf()
/*    */   {
/* 47 */     return this.m_bLeaf;
/*    */   }
/*    */ 
/*    */   public void setLeaf(boolean a_sLeaf) {
/* 51 */     this.m_bLeaf = a_sLeaf;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.bean.SelectOption
 * JD-Core Version:    0.6.0
 */