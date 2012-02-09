/*     */ package com.bright.framework.common.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.service.LanguageManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewLanguageAction extends BTransactionAction
/*     */   implements FrameworkConstants
/*     */ {
/*  51 */   private LanguageManager m_languageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  83 */     ActionForward afForward = null;
/*  84 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  87 */     long lId = getLongParameter(a_request, "language");
/*     */ 
/*  90 */     userProfile.setCurrentLanguage(this.m_languageManager.getLanguage(a_dbTransaction, lId));
/*     */ 
/*  92 */     int iLogin = getIntParameter(a_request, "login");
/*     */ 
/*  94 */     if (iLogin > 0)
/*     */     {
/*  96 */       afForward = a_mapping.findForward("LoginForward");
/*     */     }
/*  98 */     else if (a_request.getSession().getAttribute("lastGetRequestUri") != null)
/*     */     {
/* 101 */       afForward = new ActionForward((String)a_request.getSession().getAttribute("lastGetRequestUri"), true);
/*     */     }
/*     */     else
/*     */     {
/* 105 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 108 */     return afForward;
/*     */   }
/*     */ 
/*     */   public boolean getAvailableNotLoggedIn()
/*     */   {
/* 113 */     return true;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager a_languageManager)
/*     */   {
/* 118 */     this.m_languageManager = a_languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.action.ViewLanguageAction
 * JD-Core Version:    0.6.0
 */