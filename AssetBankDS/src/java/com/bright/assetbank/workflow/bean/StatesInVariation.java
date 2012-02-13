/*    */ package com.bright.assetbank.workflow.bean;
/*    */ 
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class StatesInVariation
/*    */ {
/* 27 */   private String m_variationName = "";
/*    */ 
/* 52 */   private Vector m_stateList = new Vector();
/*    */ 
/*    */   public String getVariationName()
/*    */   {
/* 36 */     return this.m_variationName;
/*    */   }
/*    */ 
/*    */   public void setVariationName(String a_sVariationName)
/*    */   {
/* 46 */     this.m_variationName = a_sVariationName;
/*    */   }
/*    */ 
/*    */   public Vector getStateList()
/*    */   {
/* 61 */     return this.m_stateList;
/*    */   }
/*    */ 
/*    */   public void setStateList(Vector a_sStateList)
/*    */   {
/* 71 */     this.m_stateList = a_sStateList;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.bean.StatesInVariation
 * JD-Core Version:    0.6.0
 */