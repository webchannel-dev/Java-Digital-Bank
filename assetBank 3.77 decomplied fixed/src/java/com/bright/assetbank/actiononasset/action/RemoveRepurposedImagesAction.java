/*    */ package com.bright.assetbank.actiononasset.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bn2web.common.service.GlobalApplication;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.repurposing.service.AssetRepurposingManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import org.apache.avalon.framework.component.ComponentException;
/*    */ import org.apache.avalon.framework.component.ComponentManager;
/*    */ 
/*    */ public class RemoveRepurposedImagesAction extends ActionOnAsset
/*    */ {
/*    */   public void performOnAssetAfterSave(DBTransaction a_dbTransaction, long a_lAssetId, long a_lUserId)
/*    */     throws Bn2Exception
/*    */   {
/* 36 */     AssetRepurposingManager m_repurposingManager = null;
/*    */     try
/*    */     {
/* 40 */       m_repurposingManager = (AssetRepurposingManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetRepurposingManager");
/*    */ 
/* 42 */       m_repurposingManager.removeRepurposedImages(a_dbTransaction, a_lAssetId);
/*    */     }
/*    */     catch (ComponentException e)
/*    */     {
/* 46 */       throw new Bn2Exception("RemoveRepurposedImagesAction.performOnAsset() : ComponentException whilst getting manager : " + e.getLocalizedMessage(), e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void performOnAssetBeforeSave(DBTransaction a_dbTransaction, Asset a_asset)
/*    */     throws Bn2Exception
/*    */   {
/*    */   }
/*    */ 
/*    */   public boolean actionRequiresReindex()
/*    */   {
/* 57 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.actiononasset.action.RemoveRepurposedImagesAction
 * JD-Core Version:    0.6.0
 */