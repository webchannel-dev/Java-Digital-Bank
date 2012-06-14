/*    */ package com.bright.assetbank.application.bean;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileFilter;
/*    */ 
/*    */ public class DirectLinkCacheAssetFileFilter
/*    */   implements FileFilter
/*    */ {
/* 33 */   long m_lAssetId = -1L;
/*    */ 
/*    */   public DirectLinkCacheAssetFileFilter(long a_lAssetId)
/*    */   {
/* 37 */     this.m_lAssetId = a_lAssetId;
/*    */   }
/*    */ 
/*    */   public boolean accept(File file)
/*    */   {
/* 47 */     return (file != null) && (file.getName().toLowerCase().startsWith(String.valueOf(this.m_lAssetId) + "-"));
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.DirectLinkCacheAssetFileFilter
 * JD-Core Version:    0.6.0
 */