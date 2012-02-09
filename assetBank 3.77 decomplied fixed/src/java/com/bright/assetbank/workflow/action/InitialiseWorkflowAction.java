/*     */ package com.bright.assetbank.workflow.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.workflow.bean.WorkflowUpdate;
/*     */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*     */ import com.bright.assetbank.workflow.util.WorkflowUtil;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class InitialiseWorkflowAction extends BTransactionAction
/*     */ {
/*  45 */   private AssetManager m_assetManager = null;
/*  46 */   private AssetWorkflowManager m_assetWorkflowManager = null;
/*  47 */   private CategoryManager m_categoryManager = null;
/*  48 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  58 */     ABUserProfile userProfile = (ABUserProfile)ABUserProfile.getUserProfile(a_request);
/*     */ 
/*  61 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  63 */       this.m_logger.debug("This user does not have permission to view the admin page");
/*  64 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  67 */     long lAssetId = getLongParameter(a_request, "id");
/*  68 */     Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, null, false, false);
/*  69 */     AssetForm form = (AssetForm)a_form;
/*  70 */     form.setAsset(asset);
/*     */ 
/*  73 */     if ((!userProfile.getIsAdmin()) && (!this.m_assetManager.userCanUpdateAsset(userProfile, asset)))
/*     */     {
/*  75 */       this.m_logger.debug("This user does not have permission to update asset id=" + lAssetId);
/*  76 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  79 */     form.validateSubmitOptions(a_dbTransaction, userProfile, a_request, this.m_categoryManager, this.m_listManager, true);
/*     */ 
/*  81 */     if (form.getHasErrors())
/*     */     {
/*  83 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  86 */     WorkflowUpdate update = WorkflowUtil.getWorkflowUpdateFromSubmitOptions(a_dbTransaction, userProfile, form.getSelectedSubmitOption(), a_request, null);
/*     */ 
/*  89 */     if ((update != null) && (update.getUpdateType() == 4))
/*     */     {
/*  91 */       this.m_assetWorkflowManager.updateAssetWorkflows(a_dbTransaction, asset, update);
/*     */     }
/*     */ 
/*  94 */     String sQueryString = "id=" + asset.getId();
/*  95 */     return createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/* 100 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetWorkflowManager(AssetWorkflowManager a_assetWorkflowManager)
/*     */   {
/* 105 */     this.m_assetWorkflowManager = a_assetWorkflowManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_categoryManager)
/*     */   {
/* 110 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_listManager)
/*     */   {
/* 115 */     this.m_listManager = a_listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.action.InitialiseWorkflowAction
 * JD-Core Version:    0.6.0
 */