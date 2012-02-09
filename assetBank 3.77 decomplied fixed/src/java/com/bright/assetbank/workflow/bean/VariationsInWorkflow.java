/*    */ package com.bright.assetbank.workflow.bean;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class VariationsInWorkflow
/*    */ {
/* 28 */   private String m_workflowName = "";
/*    */ 
/* 33 */   private Vector m_variationList = new Vector();
/*    */ 
/*    */   public int getNumberStates()
/*    */   {
/* 43 */     int iNum = 0;
/* 44 */     if (this.m_variationList != null)
/*    */     {
/* 46 */       Iterator it = this.m_variationList.iterator();
/* 47 */       while (it.hasNext())
/*    */       {
/* 49 */         StatesInVariation var = (StatesInVariation)it.next();
/* 50 */         iNum += var.getStateList().size();
/*    */       }
/*    */     }
/*    */ 
/* 54 */     return iNum;
/*    */   }
/*    */ 
/*    */   public String getWorkflowName()
/*    */   {
/* 62 */     return this.m_workflowName;
/*    */   }
/*    */ 
/*    */   public void setWorkflowName(String a_sName)
/*    */   {
/* 70 */     this.m_workflowName = a_sName;
/*    */   }
/*    */ 
/*    */   public Vector getVariationList()
/*    */   {
/* 78 */     return this.m_variationList;
/*    */   }
/*    */ 
/*    */   public void setVariationList(Vector a_sList)
/*    */   {
/* 86 */     this.m_variationList = a_sList;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.bean.VariationsInWorkflow
 * JD-Core Version:    0.6.0
 */