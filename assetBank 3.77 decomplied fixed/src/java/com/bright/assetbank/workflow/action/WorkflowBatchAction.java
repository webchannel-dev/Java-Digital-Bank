/*     */ package com.bright.assetbank.workflow.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.batch.update.action.CreateNewBatchAction;
/*     */ import com.bright.assetbank.batch.update.constant.UpdateSettings;
/*     */ import com.bright.assetbank.batch.update.service.BatchUpdateControllerImpl;
/*     */ import com.bright.assetbank.batch.update.service.UpdateManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.workflow.bean.AssetInState;
/*     */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.workflow.bean.State;
/*     */ import com.bright.framework.workflow.bean.Workflow;
/*     */ import com.bright.framework.workflow.service.WorkflowManager;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public abstract class WorkflowBatchAction extends BTransactionAction
/*     */   implements MessageConstants
/*     */ {
/*  58 */   private UpdateManager m_updateManager = null;
/*     */ 
/*  64 */   protected AssetWorkflowManager m_assetWorkflowManager = null;
/*     */   private WorkflowManager m_workflowManager;
/*  76 */   protected AssetCategoryManager m_categoryManager = null;
/*     */ 
/*     */   public void setUpdateManager(UpdateManager a_updateManager)
/*     */   {
/*  61 */     this.m_updateManager = a_updateManager;
/*     */   }
/*     */ 
/*     */   public void setAssetWorkflowManager(AssetWorkflowManager a_assetWorkflowManager)
/*     */   {
/*  67 */     this.m_assetWorkflowManager = a_assetWorkflowManager;
/*     */   }
/*     */ 
/*     */   public void setWorkflowManager(WorkflowManager a_wm)
/*     */   {
/*  73 */     this.m_workflowManager = a_wm;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_sCategoryManager)
/*     */   {
/*  79 */     this.m_categoryManager = a_sCategoryManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  90 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  92 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  94 */       this.m_logger.debug("This user does not have permission to view the batch update page");
/*  95 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  98 */     long lLoggedInUserId = userProfile.getUser().getId();
/*  99 */     String sWorkflowName = a_request.getParameter("workflow");
/*     */ 
/* 102 */     BatchUpdateControllerImpl batchUpdateController = CreateNewBatchAction.createNewBatchUpdateController(this.m_updateManager, userProfile);
/* 103 */     Workflow workflow = this.m_workflowManager.getWorkflowBean(sWorkflowName);
/* 104 */     batchUpdateController.setWorkflow(workflow);
/* 105 */     setupBatchUpdateController(batchUpdateController, a_request);
/*     */ 
/* 108 */     String sStateName = a_request.getParameter("state");
/* 109 */     String sVariationName = a_request.getParameter("variation");
/*     */ 
/* 112 */     int iMaxBatchSize = UpdateSettings.getMaxBatchUpdateResults();
/* 113 */     Vector assetsForUpdate = getAssets(a_dbTransaction, sStateName, iMaxBatchSize, userProfile, a_request, sWorkflowName);
/*     */ 
/* 115 */     Iterator itAssetsForUpdate = assetsForUpdate.iterator();
/*     */ 
/* 117 */     Vector vecAssets = new Vector();
/* 118 */     while (itAssetsForUpdate.hasNext())
/*     */     {
/* 120 */       AssetInState ais = (AssetInState)itAssetsForUpdate.next();
/* 121 */       Long olId = new Long(ais.getAssetId());
/*     */ 
/* 123 */       vecAssets.add(olId);
/*     */     }
/*     */ 
/* 127 */     batchUpdateController.startNewBatchUpdateWithoutLocks(vecAssets, lLoggedInUserId);
/*     */ 
/* 131 */     State state = this.m_workflowManager.getState(sWorkflowName, sVariationName, sStateName);
/*     */ 
/* 135 */     batchUpdateController.setWorkflowTransitions(state.getTransitionList());
/*     */ 
/* 138 */     batchUpdateController.setState(state);
/*     */ 
/* 141 */     ActionForward afForward = createRedirectingForward("", a_mapping, "Success");
/* 142 */     return afForward;
/*     */   }
/*     */ 
/*     */   public abstract void setupBatchUpdateController(BatchUpdateControllerImpl paramBatchUpdateControllerImpl, HttpServletRequest paramHttpServletRequest);
/*     */ 
/*     */   public abstract Vector getAssets(DBTransaction paramDBTransaction, String paramString1, int paramInt, ABUserProfile paramABUserProfile, HttpServletRequest paramHttpServletRequest, String paramString2)
/*     */     throws Bn2Exception;
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.action.WorkflowBatchAction
 * JD-Core Version:    0.6.0
 */