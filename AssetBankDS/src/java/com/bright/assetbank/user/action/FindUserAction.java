/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.form.UserForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class FindUserAction extends ViewFindUserAction
/*     */ {
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  71 */     ActionForward afForward = null;
/*  72 */     UserForm form = (UserForm)a_form;
/*  73 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  76 */     super.execute(a_mapping, a_form, a_request, a_response, a_dbTransaction);
/*     */ 
/*  83 */     int iOrderId = getIntParameter(a_request, "orderId");
/*     */ 
/*  86 */     form.setUsers(getUserManager().findUsers(form.getSearchCriteria(), iOrderId));
/*  87 */     userProfile.setLastUserSearch(form.getUsers());
/*     */ 
/*  89 */     if (form.getUsers().size() == 0)
/*     */     {
/*  91 */       form.setEmptyResult(true);
/*     */     }
/*     */ 
/*  95 */     Iterator itUsers = form.getUsers().iterator();
/*     */ 
/*  97 */     while (itUsers.hasNext())
/*     */     {
/*  99 */       ABUser user = (ABUser)itUsers.next();
/* 100 */       if (user.getExpiryDate() != null)
/*     */       {
/* 102 */         form.setShowExpiryDate(true);
/* 103 */         break;
/*     */       }
/*     */     }
/*     */ 
/* 107 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 109 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.FindUserAction
 * JD-Core Version:    0.6.0
 */