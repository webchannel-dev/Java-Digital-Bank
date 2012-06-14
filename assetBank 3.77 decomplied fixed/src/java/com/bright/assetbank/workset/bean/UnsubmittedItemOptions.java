/*    */ package com.bright.assetbank.workset.bean;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class UnsubmittedItemOptions
/*    */ {
/* 29 */   private ArrayList m_listOptions = new ArrayList();
/*    */ 
/*    */   public void addOption(int a_iOption)
/*    */   {
/* 38 */     this.m_listOptions.add(new Integer(a_iOption));
/*    */   }
/*    */ 
/*    */   public void removeOption(int a_iOption)
/*    */   {
/* 48 */     if (getContains(a_iOption))
/*    */     {
/* 50 */       this.m_listOptions.remove(new Integer(a_iOption));
/*    */     }
/*    */   }
/*    */ 
/*    */   public boolean getContains(int a_iOption)
/*    */   {
/* 62 */     boolean bContains = this.m_listOptions.contains(new Integer(a_iOption));
/* 63 */     return bContains;
/*    */   }
/*    */ 
/*    */   public int getFirstOption()
/*    */   {
/* 73 */     int i = 0;
/* 74 */     if (this.m_listOptions.size() >= 1)
/*    */     {
/* 76 */       i = ((Integer)this.m_listOptions.get(0)).intValue();
/*    */     }
/* 78 */     return i;
/*    */   }
/*    */ 
/*    */   public boolean getOnlyContainsSingleOption()
/*    */   {
/* 88 */     boolean b = this.m_listOptions.size() == 1;
/* 89 */     return b;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workset.bean.UnsubmittedItemOptions
 * JD-Core Version:    0.6.0
 */