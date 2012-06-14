/*    */ package com.bright.assetbank.user.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.assetbank.user.form.DivisionForm;
/*    */ import com.bright.assetbank.user.service.ABUserManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.common.bean.RefDataItem;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewDivisionAction extends BTransactionAction
/*    */ {
/* 43 */   private ABUserManager m_userManager = null;
/*    */ 
/*    */   public void setUserManager(ABUserManager a_userManager) {
/* 46 */     this.m_userManager = a_userManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 69 */     ActionForward afForward = null;
/* 70 */     DivisionForm form = (DivisionForm)a_form;
/* 71 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 74 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 76 */       this.m_logger.error("ViewDivisionAction.execute : User must be an admin.");
/* 77 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 81 */     long lId = getLongParameter(a_request, "id");
/* 82 */     RefDataItem division = this.m_userManager.getDivision(a_dbTransaction, lId);
/*    */ 
/* 84 */     form.setDivision(division);
/*    */ 
/* 86 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 88 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ViewDivisionAction
 * JD-Core Version:    0.6.0
 */