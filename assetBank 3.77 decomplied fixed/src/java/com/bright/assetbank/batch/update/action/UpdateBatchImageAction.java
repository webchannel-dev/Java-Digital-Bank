/*     */ package com.bright.assetbank.batch.update.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.action.SaveAssetAction;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.batch.update.form.BatchAssetForm;
/*     */ import com.bright.assetbank.batch.update.service.BatchUpdateControllerImpl;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import com.bright.framework.workflow.bean.Workflow;
/*     */ import com.bright.framework.workflow.bean.WorkflowInfo;
/*     */ import com.bright.framework.workflow.service.WorkflowManager;
/*     */ import java.sql.SQLException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class UpdateBatchImageAction extends SaveAssetAction
/*     */ {
/*  53 */   private WorkflowManager m_workflowManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  61 */     ActionForward afForward = null;
/*  62 */     BatchAssetForm form = (BatchAssetForm)a_form;
/*  63 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  65 */     BatchUpdateControllerImpl controller = (BatchUpdateControllerImpl)userProfile.getBatchUpdateController();
/*  66 */     if (controller == null)
/*     */     {
/*  68 */       throw new Bn2Exception("UpdateBatchImageAction.execute: called without a valid BatchUpdateController");
/*     */     }
/*     */ 
/*  71 */     long lAssetId = form.getAsset().getId();
/*     */ 
/*  74 */     int iTransition = getIntParameter(a_request, "transition");
/*     */ 
/*  77 */     String sMessage = a_request.getParameter("message");
/*     */ 
/*  79 */     if ((!StringUtil.stringIsPopulated(sMessage)) && (iTransition >= 0))
/*     */     {
/*  81 */       DBTransaction dbTransaction = null;
/*     */       try
/*     */       {
/*  84 */         dbTransaction = this.m_transactionManager.getNewTransaction();
/*  85 */         WorkflowInfo wi = this.m_workflowManager.getWorkflowInfoForEntity(dbTransaction, lAssetId, controller.getWorkflow().getName());
/*  86 */         boolean bMandatoryMessage = this.m_assetWorkflowManager.transitionRequiresMessage(dbTransaction, wi.getId(), iTransition);
/*  87 */         if (bMandatoryMessage)
/*     */         {
/*  89 */           form.addError(this.m_listManager.getListItem(null, "workflowMessageMandatory", userProfile.getCurrentLanguage()).getBody());
/*     */         }
/*     */ 
/*     */       }
/*     */       finally
/*     */       {
/*     */         try
/*     */         {
/*  97 */           if (dbTransaction != null)
/*     */           {
/*  99 */             dbTransaction.commit();
/*     */           }
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 104 */           this.m_logger.error("Exception commiting transaction in UpdateBatchImageAction:", sqle);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 113 */       form.setSelectedTransition(iTransition);
/*     */     }
/*     */ 
/* 117 */     afForward = super.execute(a_mapping, a_form, a_request, a_response);
/*     */ 
/* 120 */     if ((!form.getHasErrors()) && (iTransition >= 0))
/*     */     {
/* 123 */       WorkflowInfo wi = this.m_workflowManager.getWorkflowInfoForEntity(null, lAssetId, controller.getWorkflow().getName());
/* 124 */       this.m_assetWorkflowManager.changeAssetState(null, lAssetId, wi.getId(), iTransition, userProfile.getUser().getId(), sMessage, controller.getIsOwner(), userProfile.getSessionId());
/*     */     }
/*     */ 
/* 127 */     return afForward;
/*     */   }
/*     */ 
/*     */   protected boolean doValidateMandatoryFields(AssetForm a_form)
/*     */   {
/* 140 */     BatchAssetForm form = (BatchAssetForm)a_form;
/*     */ 
/* 142 */     boolean bValidate = !form.getApprovalMode();
/* 143 */     return bValidate;
/*     */   }
/*     */ 
/*     */   public void setWorkflowManager(WorkflowManager a_workflowManager)
/*     */   {
/* 148 */     this.m_workflowManager = a_workflowManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.UpdateBatchImageAction
 * JD-Core Version:    0.6.0
 */