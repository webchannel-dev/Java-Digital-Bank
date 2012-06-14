/*    */ package com.bright.assetbank.workflow.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.application.form.AssetForm;
import com.bright.assetbank.application.service.AssetManager;
/*    */ //import com.bright.assetbank.application.service.AssetManager1;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.assetbank.workflow.util.WorkflowUtil;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.workflow.service.WorkflowManager;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewInitialiseWorkflowAction extends BTransactionAction
/*    */ {
/* 44 */   private AssetManager m_assetManager = null;
/* 45 */   private WorkflowManager m_workflowManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 56 */     ABUserProfile userProfile = (ABUserProfile)ABUserProfile.getUserProfile(a_request);
/*    */ 
/* 59 */     if (!userProfile.getIsLoggedIn())
/*    */     {
/* 61 */       this.m_logger.debug("This user does not have permission to view the admin page");
/* 62 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 65 */     long lAssetId = getLongParameter(a_request, "id");
/* 66 */     Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, null, false, false);
/*    */ 
/* 69 */     if ((!userProfile.getIsAdmin()) && (!this.m_assetManager.userCanUpdateAsset(userProfile, asset)))
/*    */     {
/* 71 */       this.m_logger.debug("This user does not have permission to update asset id=" + lAssetId);
/* 72 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 75 */     AssetForm form = (AssetForm)a_form;
/*    */ 
/* 78 */     Vector vecMissingWorkflows = asset.getMissingWorkflows();
/*    */ 
/* 81 */     Vector vecSubmitOptions = WorkflowUtil.getSubmitOptionsFromWorkflowNames(a_dbTransaction, asset, vecMissingWorkflows, userProfile, this.m_assetManager, true, form, this.m_workflowManager);
/* 82 */     form.setWorkflowOptions(vecSubmitOptions);
/* 83 */     form.setAsset(asset);
/*    */ 
/* 85 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setAssetManager(AssetManager a_assetManager)
/*    */   {
/* 90 */     this.m_assetManager = a_assetManager;
/*    */   }
/*    */ 
/*    */   public void setWorkflowManager(WorkflowManager a_workflowManager)
/*    */   {
/* 95 */     this.m_workflowManager = a_workflowManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.action.ViewInitialiseWorkflowAction
 * JD-Core Version:    0.6.0
 */