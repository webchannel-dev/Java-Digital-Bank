/*    */ package com.bright.assetbank.actiononasset.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bn2web.common.service.GlobalApplication;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import org.apache.avalon.framework.component.ComponentException;
/*    */ import org.apache.avalon.framework.component.ComponentManager;
/*    */ 
/*    */ public class UnrestrictAssetAction extends ActionOnAsset
/*    */ {
/*    */   public void performOnAssetAfterSave(DBTransaction a_dbTransaction, long a_lAssetId, long a_lUserId)
/*    */     throws Bn2Exception
/*    */   {
/*    */     try
/*    */     {
/* 40 */       if (AssetBankSettings.getRestrictChildAssetsWithParent())
/*    */       {
/* 42 */         AssetRelationshipManager assetRelationshipManager = (AssetRelationshipManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetRelationshipManager");
/* 43 */         assetRelationshipManager.restrictChildAssets(a_dbTransaction, a_lAssetId, true);
/*    */       }
/*    */     }
/*    */     catch (ComponentException e)
/*    */     {
/* 48 */       throw new Bn2Exception("UnrestrictAssetAction.performOnAssetAfterSave() : ComponentException whilst getting manager : " + e.getLocalizedMessage(), e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void performOnAssetBeforeSave(DBTransaction a_dbTransaction, Asset a_asset) throws Bn2Exception
/*    */   {
/* 54 */     a_asset.setIsRestricted(false);
/*    */   }
/*    */ 
/*    */   public boolean actionRequiresReindex()
/*    */   {
/* 59 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.actiononasset.action.UnrestrictAssetAction
 * JD-Core Version:    0.6.0
 */