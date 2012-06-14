/*    */ package com.bright.assetbank.user.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.framework.common.bean.RefDataItem;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class DivisionForm extends Bn2Form
/*    */ {
/* 32 */   private RefDataItem m_division = null;
/*    */ 
/* 34 */   private Vector m_listDivisions = null;
/*    */ 
/*    */   public DivisionForm()
/*    */   {
/* 43 */     this.m_division = new RefDataItem();
/*    */   }
/*    */ 
/*    */   public RefDataItem getDivision()
/*    */   {
/* 49 */     return this.m_division;
/*    */   }
/*    */ 
/*    */   public void setDivision(RefDataItem a_sDivision) {
/* 53 */     this.m_division = a_sDivision;
/*    */   }
/*    */ 
/*    */   public Vector getListDivisions()
/*    */   {
/* 59 */     return this.m_listDivisions;
/*    */   }
/*    */ 
/*    */   public void setListDivisions(Vector a_sListDivisions) {
/* 63 */     this.m_listDivisions = a_sListDivisions;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.form.DivisionForm
 * JD-Core Version:    0.6.0
 */