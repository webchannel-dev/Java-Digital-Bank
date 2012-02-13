/*    */ package com.bright.assetbank.entity.relationship.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.action.DownloadAssetsAction;
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
/*    */ public class DownloadChildAssetsAction extends DownloadAssetsAction
/*    */ {
/* 41 */   private AssetRelationshipManager m_assetRelationshipManager = null;
/*    */ 
/*    */   protected void prepareDownload(ABUserProfile a_userProfile, DBTransaction a_dbTransaction, AssetBoxDownloadForm a_form)
/*    */     throws Bn2Exception
/*    */   {
/*    */   }
/*    */ 
/*    */   protected Vector<AssetInList> getAssetsToDownload(DBTransaction a_dbTransaction, ABUserProfile userProfile, AssetBoxDownloadForm form, HttpServletRequest a_request)
/*    */     throws Bn2Exception
/*    */   {
/* 51 */     long lAssetId = getLongParameter(a_request, "id");
/*    */ 
/* 53 */     form.setDownloadingChildAssets(true);
/* 54 */     form.setParentId(lAssetId);
/*    */ 
/* 57 */     Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, userProfile.getVisibleAttributeIds(), true, userProfile.getCurrentLanguage(), false);
/*    */ 
/* 59 */     form.setFileName(asset.getName());
/*    */ 
/* 62 */     Vector<Long> vChildren = this.m_assetRelationshipManager.getRelatedAssetIds(a_dbTransaction, asset.getId(), 2L);
/* 63 */     Vector vAssetsInList = new Vector();
/*    */ 
/* 65 */     for (Long lChildAssetId : vChildren)
/*    */     {
/* 67 */       AssetInList ail = new AssetInList(userProfile.getCurrentLanguage());
/* 68 */       Asset childAsset = this.m_assetManager.getAsset(a_dbTransaction, lChildAssetId.longValue(), null, false, false);
/*    */ 
/* 71 */       ail.setAsset(childAsset);
/*    */ 
/* 73 */       ail.setIsDownloadable(this.m_assetManager.userCanDownloadAsset(userProfile, childAsset));
/* 74 */       ail.setIsDownloadableWithApproval(this.m_assetManager.userCanDownloadWithApprovalAsset(userProfile, childAsset));
/* 75 */       ail.setUserCanDownloadOriginal(this.m_assetManager.userCanDownloadOriginal(userProfile, childAsset));
/* 76 */       ail.setUserCanDownloadAdvanced(this.m_assetManager.userCanDownloadAdvanced(userProfile, childAsset));
/*    */ 
/* 78 */       vAssetsInList.add(ail);
/*    */     }
/*    */ 
/* 82 */     return vAssetsInList;
/*    */   }
/*    */ 
/*    */   public void setAssetRelationshipManager(AssetRelationshipManager a_assetRelationshipManager)
/*    */   {
/* 87 */     this.m_assetRelationshipManager = a_assetRelationshipManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.action.DownloadChildAssetsAction
 * JD-Core Version:    0.6.0
 */