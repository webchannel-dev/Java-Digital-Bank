/*    */ package com.bright.assetbank.actiononasset.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bn2web.common.service.GlobalApplication;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.application.service.AssetManager;
/*    */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*    */ import com.bright.assetbank.repurposing.service.AssetRepurposingManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import org.apache.avalon.framework.component.ComponentException;
/*    */ import org.apache.avalon.framework.component.ComponentManager;
/*    */ 
/*    */ public class RestrictAssetAction extends ActionOnAsset
/*    */ {
/* 37 */   private AssetRelationshipManager m_assetRelationshipManager = null;
/*    */ 
/*    */   public void setAssetRelationshipManager(AssetRelationshipManager a_assetRelationshipManager) {
/* 40 */     this.m_assetRelationshipManager = a_assetRelationshipManager;
/*    */   }
/*    */ 
/*    */   public void performOnAssetAfterSave(DBTransaction a_dbTransaction, long a_lAssetId, long a_lUserId)
/*    */     throws Bn2Exception
/*    */   {
/*    */     try
/*    */     {
/* 48 */       if (AssetBankSettings.getAssetRepurposingEnabled())
/*    */       {
/* 50 */         AssetRepurposingManager m_repurposingManager = (AssetRepurposingManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetRepurposingManager");
/* 51 */         m_repurposingManager.removeRepurposedImages(a_dbTransaction, a_lAssetId);
/*    */       }
/*    */ 
/* 54 */       if (AssetBankSettings.getRestrictChildAssetsWithParent())
/*    */       {
/* 56 */         AssetManager m_assetManager = (AssetManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetManager");
/* 57 */         this.m_assetRelationshipManager.restrictChildAssets(a_dbTransaction, a_lAssetId, false);
/*    */       }
/*    */     }
/*    */     catch (ComponentException e)
/*    */     {
/* 62 */       throw new Bn2Exception("RestrictAssetAction.performOnAssetAfterSave() : ComponentException whilst getting manager : " + e.getLocalizedMessage(), e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void performOnAssetBeforeSave(DBTransaction a_dbTransaction, Asset a_asset) throws Bn2Exception
/*    */   {
/* 68 */     a_asset.setIsRestricted(true);
/*    */   }
/*    */ 
/*    */   public boolean actionRequiresReindex()
/*    */   {
/* 73 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.actiononasset.action.RestrictAssetAction
 * JD-Core Version:    0.6.0
 */