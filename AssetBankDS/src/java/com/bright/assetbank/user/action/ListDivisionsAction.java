/*    */ package com.bright.assetbank.user.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.assetbank.user.form.DivisionForm;
/*    */ import com.bright.assetbank.user.service.ABUserManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ListDivisionsAction extends BTransactionAction
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
/* 70 */     ActionForward afForward = null;
/* 71 */     DivisionForm form = (DivisionForm)a_form;
/* 72 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 75 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 77 */       this.m_logger.error("ListDivisionsAction.execute : User must be an admin.");
/* 78 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 82 */     Vector vec = this.m_userManager.getAllDivisions(a_dbTransaction);
/*    */ 
/* 84 */     form.setListDivisions(vec);
/*    */ 
/* 86 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 88 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ListDivisionsAction
 * JD-Core Version:    0.6.0
 */