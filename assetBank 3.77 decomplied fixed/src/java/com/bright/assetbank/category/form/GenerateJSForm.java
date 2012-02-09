/*    */ package com.bright.assetbank.category.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class GenerateJSForm extends Bn2Form
/*    */ {
/* 31 */   private Vector m_vecDescriptiveCategories = null;
/* 32 */   private Vector m_vecPermissionCategories = null;
/*    */ 
/*    */   public Vector getDescriptiveCategories()
/*    */   {
/* 36 */     return this.m_vecDescriptiveCategories;
/*    */   }
/*    */ 
/*    */   public void setDescriptiveCategories(Vector a_vecDescriptiveCategories) {
/* 40 */     this.m_vecDescriptiveCategories = a_vecDescriptiveCategories;
/*    */   }
/*    */ 
/*    */   public Vector getPermissionCategories()
/*    */   {
/* 45 */     return this.m_vecPermissionCategories;
/*    */   }
/*    */ 
/*    */   public void setPermissionCategories(Vector a_vecPermissionCategories)
/*    */   {
/* 50 */     this.m_vecPermissionCategories = a_vecPermissionCategories;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.form.GenerateJSForm
 * JD-Core Version:    0.6.0
 */