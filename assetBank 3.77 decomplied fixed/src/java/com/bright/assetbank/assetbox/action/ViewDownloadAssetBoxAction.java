/*    */ package com.bright.assetbank.assetbox.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.action.ViewDownloadAssetsAction;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.approval.bean.AssetInList;
/*    */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*    */ import com.bright.assetbank.assetbox.form.AssetBoxDownloadForm;
/*    */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class ViewDownloadAssetBoxAction extends ViewDownloadAssetsAction
/*    */ {
/* 86 */   protected AssetBoxManager m_assetBoxManager = null;
/*    */ 
/*    */   protected Vector<AssetInList> getAssetsToDownload(DBTransaction a_dbTransaction, ABUserProfile userProfile, AssetBoxDownloadForm form, HttpServletRequest a_request)
/*    */     throws Bn2Exception
/*    */   {
/* 57 */     form.setDownloadingLightbox(true);
/*    */ 
/* 60 */     this.m_assetBoxManager.refreshAssetBoxesInProfile(a_dbTransaction, userProfile);
/* 61 */     Collection assetBox = userProfile.getAssetBox().getAssetsInState(7);
/*    */ 
/* 63 */     Vector candidateAssets = new Vector();
/* 64 */     Iterator itBox = assetBox.iterator();
/*    */ 
/* 66 */     while (itBox.hasNext())
/*    */     {
/* 68 */       AssetInList ail = (AssetInList)itBox.next();
/* 69 */       Asset asset = ail.getAsset();
/* 70 */       if ((asset.getHasFile()) || (asset.getSurrogateAssetId() > 0L))
/*    */       {
/* 73 */         if ((form.getOnlyDownloadSelected()) && (!ail.getIsSelected()))
/*    */         {
/*    */           continue;
/*    */         }
/*    */ 
/* 78 */         candidateAssets.add(ail);
/*    */       }
/*    */     }
/*    */ 
/* 82 */     return candidateAssets;
/*    */   }
/*    */ 
/*    */   public void setAssetBoxManager(AssetBoxManager a_assetBoxManager)
/*    */   {
/* 89 */     this.m_assetBoxManager = a_assetBoxManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.ViewDownloadAssetBoxAction
 * JD-Core Version:    0.6.0
 */