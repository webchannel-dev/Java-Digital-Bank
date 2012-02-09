/*    */ package com.bright.framework.common.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class ListForm extends Bn2Form
/*    */ {
/* 31 */   private Vector m_vecList = null;
/*    */ 
/*    */   public Vector getList()
/*    */   {
/* 36 */     return this.m_vecList;
/*    */   }
/*    */ 
/*    */   public void setList(Vector a_vecList)
/*    */   {
/* 41 */     this.m_vecList = a_vecList;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.form.ListForm
 * JD-Core Version:    0.6.0
 */