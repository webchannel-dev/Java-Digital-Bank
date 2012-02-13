/*    */ package com.bright.assetbank.synchronise.bean;
/*    */ 
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class ImportResult
/*    */ {
/* 29 */   private int m_iImportCount = 0;
/* 30 */   private Vector m_vecImportedAssets = null;
/*    */ 
/*    */   public void setImportCount(int a_iImportCount)
/*    */   {
/* 34 */     this.m_iImportCount = a_iImportCount;
/*    */   }
/*    */ 
/*    */   public int getImportCount()
/*    */   {
/* 39 */     return this.m_iImportCount;
/*    */   }
/*    */ 
/*    */   public void setImportedAssets(Vector a_vecImportedAssets)
/*    */   {
/* 44 */     this.m_vecImportedAssets = a_vecImportedAssets;
/*    */   }
/*    */ 
/*    */   public Vector getImportedAssets()
/*    */   {
/* 49 */     return this.m_vecImportedAssets;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.ImportResult
 * JD-Core Version:    0.6.0
 */