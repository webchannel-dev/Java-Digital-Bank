/*     */ package com.bright.framework.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.user.constant.UserConstants;
/*     */ import com.bright.framework.user.service.UserManager;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public abstract class LoginWithAttributeAction extends BTransactionAction
/*     */   implements UserConstants
/*     */ {
/*  45 */   private UserManager m_userManager = null;
/*     */ 
/*  51 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public void setUserManager(UserManager a_userManager)
/*     */   {
/*  48 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_listManager)
/*     */   {
/*  55 */     this.m_listManager = a_listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  77 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  80 */     if (!checkPermissions(userProfile))
/*     */     {
/*  82 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  86 */     long lUserId = getUserId(a_request, userProfile);
/*  87 */     User user = this.m_userManager.getUser(a_dbTransaction, lUserId);
/*     */ 
/*  89 */     if (user != null)
/*     */     {
/*  92 */       a_request.setAttribute("username", user.getUsername());
/*  93 */       a_request.setAttribute("ignorePassword", new Boolean(true));
/*  94 */       setupOtherAttributes(a_request, userProfile);
/*     */ 
/*  97 */       return a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 101 */     ((Bn2Form)a_form).addError(this.m_listManager.getListItem(a_dbTransaction, "failedLoginAsUser", userProfile.getCurrentLanguage()).getBody());
/* 102 */     return a_mapping.findForward("Failure");
/*     */   }
/*     */ 
/*     */   protected abstract boolean checkPermissions(UserProfile paramUserProfile);
/*     */ 
/*     */   protected abstract void setupOtherAttributes(HttpServletRequest paramHttpServletRequest, UserProfile paramUserProfile);
/*     */ 
/*     */   protected abstract long getUserId(HttpServletRequest paramHttpServletRequest, UserProfile paramUserProfile);
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.action.LoginWithAttributeAction
 * JD-Core Version:    0.6.0
 */