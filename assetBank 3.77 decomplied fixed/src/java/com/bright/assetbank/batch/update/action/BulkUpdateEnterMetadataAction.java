/*     */ package com.bright.assetbank.batch.update.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.action.ViewAddAssetAction;
/*     */ import com.bright.assetbank.application.bean.SubmitOptions;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.batch.update.form.BulkUpdateForm;
/*     */ import com.bright.assetbank.batch.update.service.BatchUpdateController;
/*     */ import com.bright.assetbank.batch.update.service.UpdateManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class BulkUpdateEnterMetadataAction extends ViewAddAssetAction
/*     */ {
/*  50 */   private UpdateManager m_updateManager = null;
/*     */ 
/*  56 */   private AssetWorkflowManager m_assetWorkflowManager = null;
/*     */ 
/*     */   public void setUpdateManager(UpdateManager a_manager)
/*     */   {
/*  53 */     this.m_updateManager = a_manager;
/*     */   }
/*     */ 
/*     */   public void setAssetWorkflowManager(AssetWorkflowManager a_assetWorkflowManager)
/*     */   {
/*  59 */     this.m_assetWorkflowManager = a_assetWorkflowManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  82 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  83 */     BulkUpdateForm form = (BulkUpdateForm)a_form;
/*     */ 
/*  86 */     BatchUpdateController controller = userProfile.getBatchUpdateController();
/*  87 */     if (controller == null)
/*     */     {
/*  89 */       throw new Bn2Exception("BulkUpdateEnterMetadataAction.exceute: called without a valid BatchUpdateController");
/*     */     }
/*     */ 
/*  93 */     if (this.m_updateManager.isBatchInProgress(userProfile.getUser().getId()))
/*     */     {
/*  95 */       throw new Bn2Exception("BulkUpdateEnterMetadataAction.exceute: called while bulk update in progress");
/*     */     }
/*     */ 
/*  99 */     ActionForward afForward = super.execute(a_mapping, form, a_request, a_response, a_dbTransaction);
/*     */ 
/* 102 */     SubmitOptions options = form.getSubmitOptions();
/* 103 */     options.removeOption(2);
/*     */ 
/* 105 */     if (userProfile.getIsAdmin())
/*     */     {
/* 107 */       if (!options.getContains(1))
/*     */       {
/* 109 */         options.addOption(1);
/*     */       }
/*     */     }
/*     */ 
/* 113 */     form.setWorkflows(this.m_assetWorkflowManager.getApprovableWorkflowBeansForUser(a_dbTransaction, userProfile.getUser().getId(), userProfile.getIsAdmin()));
/*     */ 
/* 115 */     return afForward;
/*     */   }
/*     */ 
/*     */   protected boolean getHideSingleOptions()
/*     */   {
/* 121 */     return false;
/*     */   }
/*     */ 
/*     */   protected void populateMetadataDefaults(AssetForm a_assetForm)
/*     */     throws Bn2Exception
/*     */   {
/* 136 */     this.m_attributeManager.populateMetadataDefaults(a_assetForm.getAsset(), a_assetForm.getAssetAttributes(), false);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.BulkUpdateEnterMetadataAction
 * JD-Core Version:    0.6.0
 */