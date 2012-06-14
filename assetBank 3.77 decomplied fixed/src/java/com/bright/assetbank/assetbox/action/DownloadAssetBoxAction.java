/*     */ package com.bright.assetbank.assetbox.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.action.DownloadAssetsAction;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.util.DownloadUtil;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.assetbox.form.AssetBoxDownloadForm;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.DownloadRestrictionManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class DownloadAssetBoxAction extends DownloadAssetsAction
/*     */   implements AssetBoxConstants, MessageConstants, AssetBankConstants, ImageConstants
/*     */ {
/*  66 */   private AssetBoxManager m_assetBoxManager = null;
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager) {
/*  69 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ 
/*     */   protected void prepareDownload(ABUserProfile a_userProfile, DBTransaction a_dbTransaction, AssetBoxDownloadForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/*  76 */     this.m_assetBoxManager.refreshAssetBoxesInProfile(a_dbTransaction, a_userProfile);
/*  77 */     Collection assetBox = a_userProfile.getAssetBox().getAssetsInState(7);
/*     */ 
/*  79 */     if (a_userProfile.getIsLoggedIn())
/*     */     {
/*  82 */       this.m_downloadRestrictionManager.validateAssetBoxDownload(a_dbTransaction, a_form, a_userProfile.getUser().getId(), assetBox, a_userProfile);
/*     */ 
/*  85 */       DownloadUtil.checkGroupDownloadSize(a_userProfile, a_form.getHeight(), a_form.getWidth(), this.m_listManager, a_dbTransaction, a_form);
/*     */     }
/*     */ 
/*  88 */     a_form.setFileName(a_userProfile.getAssetBox().getName());
/*     */ 
/*  90 */     if (assetBox.size() <= 0)
/*     */     {
/*  92 */       a_form.addError("Could not download asset box: there are no assets to download");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Vector getAssetsToDownload(DBTransaction a_dbTransaction, ABUserProfile userProfile, AssetBoxDownloadForm form, HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/*  99 */     Collection assetBox = userProfile.getAssetBox().getAssetsInState(7);
/*     */ 
/* 101 */     Iterator itBox = assetBox.iterator();
/*     */ 
/* 103 */     Vector vecAssets = new Vector();
/*     */ 
/* 105 */     while (itBox.hasNext())
/*     */     {
/* 107 */       vecAssets.add((AssetInList)itBox.next());
/*     */     }
/*     */ 
/* 110 */     return vecAssets;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.DownloadAssetBoxAction
 * JD-Core Version:    0.6.0
 */