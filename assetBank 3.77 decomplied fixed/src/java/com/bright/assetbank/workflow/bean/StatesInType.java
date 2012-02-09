/*    */ package com.bright.assetbank.workflow.bean;
/*    */ 
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class StatesInType
/*    */ {
/* 27 */   private String m_typeName = "";
/*    */ 
/* 32 */   private Vector m_stateList = new Vector();
/*    */ 
/*    */   public String getTypeName()
/*    */   {
/* 43 */     return this.m_typeName;
/*    */   }
/*    */ 
/*    */   public void setTypeName(String a_sTypeName)
/*    */   {
/* 53 */     this.m_typeName = a_sTypeName;
/*    */   }
/*    */ 
/*    */   public Vector getStateList()
/*    */   {
/* 63 */     return this.m_stateList;
/*    */   }
/*    */ 
/*    */   public void setStateList(Vector a_sStateList)
/*    */   {
/* 73 */     this.m_stateList = a_sStateList;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.bean.StatesInType
 * JD-Core Version:    0.6.0
 */