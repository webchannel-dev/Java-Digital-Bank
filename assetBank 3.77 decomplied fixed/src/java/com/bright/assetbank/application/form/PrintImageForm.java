/*    */ package com.bright.assetbank.application.form;
/*    */ 
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class PrintImageForm extends AssetForm
/*    */ {
/* 30 */   private Vector m_vecAttributeList = null;
/* 31 */   private Vector m_vecVisibilityList = null;
/*    */ 
/* 33 */   private Vector m_assets = null;
/*    */ 
/* 35 */   private int m_iAssetsPerPage = 0;
/*    */ 
/* 37 */   private boolean m_bAttributesSelected = false;
/*    */ 
/*    */   public Vector getAttributeList()
/*    */   {
/* 42 */     return this.m_vecAttributeList;
/*    */   }
/*    */ 
/*    */   public void setAttributeList(Vector a_vecAttributeList)
/*    */   {
/* 47 */     this.m_vecAttributeList = a_vecAttributeList;
/*    */   }
/*    */ 
/*    */   public Vector getVisibilityList()
/*    */   {
/* 53 */     return this.m_vecVisibilityList;
/*    */   }
/*    */ 
/*    */   public void setVisibilityList(Vector a_vecVisibilityList)
/*    */   {
/* 58 */     this.m_vecVisibilityList = a_vecVisibilityList;
/*    */   }
/*    */ 
/*    */   public Vector getAssets()
/*    */   {
/* 63 */     return this.m_assets;
/*    */   }
/*    */ 
/*    */   public void setAssets(Vector a_sAssets) {
/* 67 */     this.m_assets = a_sAssets;
/*    */   }
/*    */ 
/*    */   public int getAssetsPerPage()
/*    */   {
/* 72 */     return this.m_iAssetsPerPage;
/*    */   }
/*    */ 
/*    */   public void setAssetsPerPage(int a_iAssetsPerPage) {
/* 76 */     this.m_iAssetsPerPage = a_iAssetsPerPage;
/*    */   }
/*    */ 
/*    */   public boolean getAttributesSelected()
/*    */   {
/* 81 */     return this.m_bAttributesSelected;
/*    */   }
/*    */ 
/*    */   public void setAttributesSelected(boolean a_bAttributesSelected)
/*    */   {
/* 86 */     this.m_bAttributesSelected = a_bAttributesSelected;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.form.PrintImageForm
 * JD-Core Version:    0.6.0
 */