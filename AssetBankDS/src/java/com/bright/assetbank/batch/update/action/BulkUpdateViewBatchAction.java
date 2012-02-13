/*     */ package com.bright.assetbank.batch.update.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.batch.update.bean.BulkUpdateBatch;
/*     */ import com.bright.assetbank.batch.update.form.BulkUpdateBatchSelectForm;
/*     */ import com.bright.assetbank.batch.update.service.BatchUpdateController;
/*     */ import com.bright.assetbank.batch.update.service.UpdateManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
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
/*     */ public class BulkUpdateViewBatchAction extends Bn2Action
/*     */ {
/*  48 */   private UpdateManager m_updateManager = null;
/*     */ 
/*     */   public void setUpdateManager(UpdateManager a_manager) {
/*  51 */     this.m_updateManager = a_manager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  61 */     BulkUpdateBatchSelectForm form = (BulkUpdateBatchSelectForm)a_form;
/*     */ 
/*  64 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  65 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  67 */       this.m_logger.debug("Only logged in users can perform bulk uploads");
/*  68 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  72 */     BatchUpdateController controller = userProfile.getBatchUpdateController();
/*  73 */     if (controller == null)
/*     */     {
/*  75 */       throw new Bn2Exception("BulkUpdateStartAction.exceute: called without a valid BatchUpdateController");
/*     */     }
/*     */ 
/*  79 */     if (this.m_updateManager.isBatchInProgress(userProfile.getUser().getId()))
/*     */     {
/*  81 */       throw new Bn2Exception("BulkUpdateStartAction.exceute: called while bulk update in progress");
/*     */     }
/*     */ 
/*  85 */     Vector vecNotUpdated = new Vector();
/*  86 */     Vector vecAlreadyUpdated = new Vector();
/*     */ 
/*  88 */     BulkUpdateBatch batch = (BulkUpdateBatch)controller.getBatchUpdate();
/*     */ 
/*  90 */     Iterator it = batch.getImages().iterator();
/*  91 */     while (it.hasNext())
/*     */     {
/*  93 */       LightweightAsset asset = (LightweightAsset)it.next();
/*     */ 
/*  95 */       if (batch.getAssetIsAlreadyUpdated(asset.getId()))
/*     */       {
/*  97 */         vecAlreadyUpdated.add(asset);
/*     */       }
/*     */       else
/*     */       {
/* 101 */         vecNotUpdated.add(asset);
/*     */       }
/*     */     }
/*     */ 
/* 105 */     form.setListAlreadyUpdated(vecAlreadyUpdated);
/* 106 */     form.setListNotUpdated(vecNotUpdated);
/*     */ 
/* 108 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.BulkUpdateViewBatchAction
 * JD-Core Version:    0.6.0
 */