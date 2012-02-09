/*    */ package com.bright.assetbank.approval.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.framework.category.bean.Category;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class MoveAssetsForm extends Bn2Form
/*    */ {
/* 34 */   private Category m_fromCategory = null;
/* 35 */   private Category m_toCategory = null;
/* 36 */   private Vector m_vecAssetsToMove = null;
/*    */ 
/*    */   public Category getFromCategory()
/*    */   {
/* 41 */     return this.m_fromCategory;
/*    */   }
/*    */ 
/*    */   public void setFromCategory(Category a_fromCategory)
/*    */   {
/* 47 */     this.m_fromCategory = a_fromCategory;
/*    */   }
/*    */ 
/*    */   public Category getToCategory()
/*    */   {
/* 53 */     return this.m_toCategory;
/*    */   }
/*    */ 
/*    */   public void setToCategory(Category a_toCategory)
/*    */   {
/* 59 */     this.m_toCategory = a_toCategory;
/*    */   }
/*    */ 
/*    */   public Vector getAssetsToMove()
/*    */   {
/* 65 */     return this.m_vecAssetsToMove;
/*    */   }
/*    */ 
/*    */   public void setAssetsToMove(Vector a_vecAssetsToMove)
/*    */   {
/* 71 */     this.m_vecAssetsToMove = a_vecAssetsToMove;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.approval.form.MoveAssetsForm
 * JD-Core Version:    0.6.0
 */