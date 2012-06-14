/*     */ package com.bright.assetbank.workflow.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.workflow.constant.AssetWorkflowConstants;
/*     */ import com.bright.assetbank.workflow.form.AssetApprovalForm;
/*     */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import com.bright.framework.workflow.bean.WorkflowInfo;
/*     */ import com.bright.framework.workflow.service.WorkflowManager;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public abstract class ChangeAssetStateAction extends BTransactionAction
/*     */   implements AssetWorkflowConstants
/*     */ {
/*  46 */   private AssetWorkflowManager m_assetWorkflowManager = null;
/*     */ 
/*  51 */   private WorkflowManager m_workflowManager = null;
/*     */ 
/*  56 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setAssetWorkflowManager(AssetWorkflowManager a_wm)
/*     */   {
/*  49 */     this.m_assetWorkflowManager = a_wm;
/*     */   }
/*     */ 
/*     */   public void setWorkflowManager(WorkflowManager a_wm)
/*     */   {
/*  54 */     this.m_workflowManager = a_wm;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  59 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   protected abstract boolean getIsOwner();
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  73 */     ActionForward afForward = null;
/*  74 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  75 */     AssetApprovalForm form = (AssetApprovalForm)a_form;
/*     */ 
/*  78 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  80 */       this.m_logger.debug("ChangeAssetStateAction: not logged in");
/*  81 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  85 */     long lAssetId = getLongParameter(a_request, "assetId");
/*  86 */     String sWorkflowName = a_request.getParameter("workflowName");
/*  87 */     long lWorkflowInfoId = getLongParameter(a_request, "workflowInfoId");
/*  88 */     int iTransition = getIntParameter(a_request, "transition");
/*     */ 
/*  90 */     if (lWorkflowInfoId <= 0L)
/*     */     {
/*  92 */       WorkflowInfo wi = this.m_workflowManager.getWorkflowInfoForEntity(a_dbTransaction, lAssetId, sWorkflowName);
/*  93 */       lWorkflowInfoId = wi.getId();
/*     */     }
/*     */ 
/*  96 */     if ((lAssetId <= 0L) || (iTransition < 0))
/*     */     {
/*  98 */       this.m_logger.debug("ChangeAssetStateAction: invalid parameters: " + lAssetId + ", " + iTransition);
/*  99 */       throw new Bn2Exception("ChangeAssetStateAction: invalid parameters: " + lAssetId + ", " + iTransition);
/*     */     }
/*     */ 
/* 103 */     String sMessage = a_request.getParameter("message");
/*     */ 
/* 106 */     if (!StringUtil.stringIsPopulated(sMessage))
/*     */     {
/* 108 */       boolean bMandatoryMessage = this.m_assetWorkflowManager.transitionRequiresMessage(a_dbTransaction, lWorkflowInfoId, iTransition);
/* 109 */       if (bMandatoryMessage)
/*     */       {
/* 111 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "workflowMessageMandatory", userProfile.getCurrentLanguage()).getBody());
/* 112 */         afForward = a_mapping.findForward("Failure");
/* 113 */         return afForward;
/*     */       }
/*     */     }
/*     */ 
/* 117 */     long lUserId = userProfile.getUser().getId();
/* 118 */     WorkflowInfo wi = this.m_assetWorkflowManager.changeAssetState(a_dbTransaction, lAssetId, lWorkflowInfoId, iTransition, lUserId, sMessage, getIsOwner(), userProfile.getSessionId());
/*     */ 
/* 121 */     this.m_logger.info("ChangeAssetStateAction: state of " + lAssetId + " for workflow " + wi.getId() + " changed to:" + wi.getStateName());
/*     */ 
/* 124 */     String sQueryString = "workflowName=" + wi.getWorkflowName();
/* 125 */     if (form.getSelectedUserId() > 0L)
/*     */     {
/* 127 */       sQueryString = "&selectedUserId=" + form.getSelectedUserId();
/*     */     }
/* 129 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/* 130 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.action.ChangeAssetStateAction
 * JD-Core Version:    0.6.0
 */