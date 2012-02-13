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
/*    */ import com.bright.framework.util.StringUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class SaveDivisionAction extends BTransactionAction
/*    */ {
/* 44 */   private ABUserManager m_userManager = null;
/*    */ 
/*    */   public void setUserManager(ABUserManager a_userManager) {
/* 47 */     this.m_userManager = a_userManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 76 */     ActionForward afForward = null;
/* 77 */     DivisionForm form = (DivisionForm)a_form;
/* 78 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 81 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 83 */       this.m_logger.error("SaveDivisionAction.execute : User must be an admin.");
/* 84 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 88 */     RefDataItem division = form.getDivision();
/*    */ 
/* 90 */     if (StringUtil.stringIsPopulated(division.getDescription()))
/*    */     {
/* 92 */       this.m_userManager.saveDivision(a_dbTransaction, division);
/*    */     }
/*    */ 
/* 95 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 97 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.SaveDivisionAction
 * JD-Core Version:    0.6.0
 */