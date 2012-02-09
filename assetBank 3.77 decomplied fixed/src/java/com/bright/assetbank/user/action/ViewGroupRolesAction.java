/*    */ package com.bright.assetbank.user.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.assetbank.user.form.RolesForm;
/*    */ import com.bright.assetbank.user.service.ABUserManager;
/*    */ import com.bright.assetbank.user.service.RoleManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewGroupRolesAction extends BTransactionAction
/*    */ {
/* 44 */   private ABUserManager m_userManager = null;
/* 45 */   private RoleManager m_roleManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 66 */     ActionForward afForward = null;
/* 67 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 70 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*    */     {
/* 72 */       this.m_logger.error("ViewGroupRolesAction.execute : User is not an admin.");
/* 73 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 77 */     long lGroupId = getLongParameter(a_request, "id");
/*    */ 
/* 80 */     RolesForm form = (RolesForm)a_form;
/* 81 */     form.setRoles(this.m_roleManager.getRoles(a_dbTransaction, -1L));
/* 82 */     form.setGroup(this.m_userManager.getGroup(a_dbTransaction, lGroupId));
/*    */ 
/* 84 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 86 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setUserManager(ABUserManager a_userManager)
/*    */   {
/* 91 */     this.m_userManager = a_userManager;
/*    */   }
/*    */ 
/*    */   public void setRoleManager(RoleManager a_roleManager)
/*    */   {
/* 96 */     this.m_roleManager = a_roleManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ViewGroupRolesAction
 * JD-Core Version:    0.6.0
 */