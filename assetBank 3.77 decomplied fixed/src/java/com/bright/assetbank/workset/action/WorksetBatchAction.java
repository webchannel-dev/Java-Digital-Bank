/*     */ package com.bright.assetbank.workset.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.batch.update.action.CreateNewBatchAction;
/*     */ import com.bright.assetbank.batch.update.service.BatchUpdateControllerImpl;
/*     */ import com.bright.assetbank.batch.update.service.UpdateManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.workset.service.AssetWorksetManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class WorksetBatchAction extends BTransactionAction
/*     */ {
/*  45 */   private UpdateManager m_updateManager = null;
/*     */ 
/*  51 */   private AssetWorksetManager m_assetWorksetManager = null;
/*     */ 
/*     */   public void setUpdateManager(UpdateManager a_updateManager)
/*     */   {
/*  48 */     this.m_updateManager = a_updateManager;
/*     */   }
/*     */ 
/*     */   public void setAssetWorksetManager(AssetWorksetManager a_wm)
/*     */   {
/*  54 */     this.m_assetWorksetManager = a_wm;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  66 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  68 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  70 */       this.m_logger.debug("This user does not have permission to view the batch update page");
/*  71 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  75 */     long lLoggedInUserId = userProfile.getUser().getId();
/*  76 */     String sLangCode = userProfile.getCurrentLanguage().getCode();
/*     */ 
/*  78 */     String sFinishedUrl = "viewUnsubmittedAssets";
/*     */ 
/*  81 */     long lUserId = lLoggedInUserId;
/*     */ 
/*  83 */     long lUserIdParam = getLongParameter(a_request, "userId");
/*  84 */     if ((userProfile.getIsAdmin()) && (lUserIdParam > 0L))
/*     */     {
/*  86 */       lUserId = lUserIdParam;
/*  87 */       sFinishedUrl = "viewUnsubmittedAssetsAdmin?selectedUserId=" + lUserIdParam;
/*     */     }
/*     */ 
/*  91 */     BatchUpdateControllerImpl batchUpdateController = CreateNewBatchAction.createNewBatchUpdateController(this.m_updateManager, userProfile);
/*     */ 
/*  94 */     batchUpdateController.setFinishedUrl(sFinishedUrl);
/*  95 */     batchUpdateController.setCancelUrl(sFinishedUrl);
/*     */ 
/*  98 */     batchUpdateController.setUnsubmitted(true);
/*  99 */     batchUpdateController.setApproval(false);
/* 100 */     batchUpdateController.setOwner(false);
/*     */ 
/* 103 */     Vector assetsForUpdate = this.m_assetWorksetManager.getAssetsForBatchUpdate(lUserId, sLangCode);
/*     */ 
/* 105 */     Iterator itAssetsForUpdate = assetsForUpdate.iterator();
/* 106 */     Vector vecAssets = new Vector();
/* 107 */     while (itAssetsForUpdate.hasNext())
/*     */     {
/* 109 */       LightweightAsset asset = (LightweightAsset)itAssetsForUpdate.next();
/* 110 */       Long olId = new Long(asset.getId());
/*     */ 
/* 112 */       vecAssets.add(olId);
/*     */     }
/*     */ 
/* 116 */     batchUpdateController.startNewBatchUpdateWithoutLocks(vecAssets, lLoggedInUserId);
/*     */ 
/* 119 */     ActionForward afForward = createRedirectingForward("", a_mapping, "Success");
/* 120 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workset.action.WorksetBatchAction
 * JD-Core Version:    0.6.0
 */