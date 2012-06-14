/*    */ package com.bright.assetbank.entity.relationship.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.action.ViewDownloadAssetsAction;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.application.service.IAssetManager;
/*    */ import com.bright.assetbank.approval.bean.AssetInList;
/*    */ import com.bright.assetbank.assetbox.form.AssetBoxDownloadForm;
/*    */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class ViewDownloadChildAssetsAction extends ViewDownloadAssetsAction
/*    */ {
/* 77 */   protected AssetRelationshipManager m_assetRelationshipManager = null;
/*    */ 
/*    */   protected Vector<AssetInList> getAssetsToDownload(DBTransaction a_dbTransaction, ABUserProfile userProfile, AssetBoxDownloadForm form, HttpServletRequest a_request)
/*    */     throws Bn2Exception
/*    */   {
/* 43 */     long lAssetId = getLongParameter(a_request, "id");
/*    */ 
/* 45 */     form.setDownloadingChildAssets(true);
/* 46 */     form.setParentId(lAssetId);
/*    */ 
/* 49 */     Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, userProfile.getVisibleAttributeIds(), true, userProfile.getCurrentLanguage(), false);
/*    */ 
/* 52 */     Vector<Long> vChildren = this.m_assetRelationshipManager.getRelatedAssetIds(a_dbTransaction, asset.getId(), 2L);
/*    */ 
/* 54 */     Vector vAssetsInList = new Vector();
/*    */ 
/* 56 */     for (Long lChildAssetId : vChildren)
/*    */     {
/* 58 */       AssetInList ail = new AssetInList(userProfile.getCurrentLanguage());
/* 59 */       Asset childAsset = this.m_assetManager.getAsset(a_dbTransaction, lChildAssetId.longValue(), null, false, false);
/*    */ 
/* 62 */       ail.setAsset(childAsset);
/*    */ 
/* 64 */       ail.setIsDownloadable(this.m_assetManager.userCanDownloadAsset(userProfile, childAsset));
/* 65 */       ail.setIsDownloadableWithApproval(this.m_assetManager.userCanDownloadWithApprovalAsset(userProfile, childAsset));
/* 66 */       ail.setUserCanDownloadOriginal(this.m_assetManager.userCanDownloadOriginal(userProfile, childAsset));
/* 67 */       ail.setUserCanDownloadAdvanced(this.m_assetManager.userCanDownloadAdvanced(userProfile, childAsset));
/*    */ 
/* 69 */       vAssetsInList.add(ail);
/*    */     }
/*    */ 
/* 73 */     return vAssetsInList;
/*    */   }
/*    */ 
/*    */   public void setAssetRelationshipManager(AssetRelationshipManager a_assetRelationshipManager)
/*    */   {
/* 80 */     this.m_assetRelationshipManager = a_assetRelationshipManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.action.ViewDownloadChildAssetsAction
 * JD-Core Version:    0.6.0
 */