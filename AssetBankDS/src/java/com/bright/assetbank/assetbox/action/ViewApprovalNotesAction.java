/*    */ package com.bright.assetbank.assetbox.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.approval.bean.AssetApproval;
/*    */ import com.bright.assetbank.approval.form.ApprovalNotesForm;
/*    */ import com.bright.assetbank.approval.service.AssetApprovalManager;
/*    */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*    */ import com.bright.assetbank.ecommerce.service.OrderManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.User;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewApprovalNotesAction extends BTransactionAction
/*    */   implements AssetBoxConstants, FrameworkConstants
/*    */ {
/* 50 */   private OrderManager m_orderManager = null;
/*    */ 
/* 57 */   private AssetApprovalManager m_approvalManager = null;
/*    */ 
/*    */   public void setOrderManager(OrderManager a_orderManager)
/*    */   {
/* 54 */     this.m_orderManager = a_orderManager;
/*    */   }
/*    */ 
/*    */   public void setAssetApprovalManager(AssetApprovalManager a_approvalManager)
/*    */   {
/* 60 */     this.m_approvalManager = a_approvalManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 71 */     ActionForward afForward = null;
/* 72 */     ApprovalNotesForm form = (ApprovalNotesForm)a_form;
/* 73 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 76 */     if (!userProfile.getIsLoggedIn())
/*    */     {
/* 78 */       this.m_logger.debug("This user does not have permission to view the approval page");
/* 79 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 83 */     long lAssetId = getLongParameter(a_request, "id");
/* 84 */     long lUserId = userProfile.getUser().getId();
/*    */ 
/* 87 */     AssetApproval approval = this.m_approvalManager.getAssetApproval(a_dbTransaction, lAssetId, lUserId);
/* 88 */     form.setAssetApproval(approval);
/*    */ 
/* 90 */     Vector vecOrderList = this.m_orderManager.getSuccessfulCommercialOrders(a_dbTransaction, lAssetId, lUserId);
/* 91 */     form.setOrderList(vecOrderList);
/*    */ 
/* 93 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 95 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.ViewApprovalNotesAction
 * JD-Core Version:    0.6.0
 */