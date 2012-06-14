/*    */ package com.bright.assetbank.actiononasset.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ 
/*    */ public class MakeAssetInsensitiveAction extends ActionOnAsset
/*    */ {
/*    */   public void performOnAssetAfterSave(DBTransaction a_dbTransaction, long a_lAssetId, long a_lUserId)
/*    */     throws Bn2Exception
/*    */   {
/*    */   }
/*    */ 
/*    */   public void performOnAssetBeforeSave(DBTransaction a_dbTransaction, Asset a_asset)
/*    */     throws Bn2Exception
/*    */   {
/* 37 */     a_asset.setIsSensitive(false);
/*    */   }
/*    */ 
/*    */   public boolean actionRequiresReindex()
/*    */   {
/* 42 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.actiononasset.action.MakeAssetInsensitiveAction
 * JD-Core Version:    0.6.0
 */