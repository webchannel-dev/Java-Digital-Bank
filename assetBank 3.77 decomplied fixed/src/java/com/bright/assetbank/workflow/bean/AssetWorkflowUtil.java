/*    */ package com.bright.assetbank.workflow.bean;
/*    */ 
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.framework.category.bean.Category;
/*    */ import java.util.Iterator;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class AssetWorkflowUtil
/*    */ {
/*    */   public static String getDefaultWorkflowForAsset(Asset a_asset)
/*    */   {
/* 50 */     String sWorkflowName = "default";
/*    */ 
/* 52 */     Vector vecAccessLevels = a_asset.getPermissionCategories();
/* 53 */     Iterator itAccessLevels = vecAccessLevels.iterator();
/* 54 */     if (itAccessLevels.hasNext())
/*    */     {
/* 56 */       Category cat = (Category)itAccessLevels.next();
/* 57 */       sWorkflowName = cat.getWorkflowName();
/*    */     }
/*    */ 
/* 61 */     return sWorkflowName;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.bean.AssetWorkflowUtil
 * JD-Core Version:    0.6.0
 */