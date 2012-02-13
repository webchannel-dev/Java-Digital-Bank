/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.user.action.RegisterUserAction;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.form.RegisterForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class OfflineRegisterAction extends RegisterUserAction
/*     */ {
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  58 */     ActionForward afForward = null;
/*  59 */     RegisterForm form = (RegisterForm)a_form;
/*     */ 
/*  62 */     form.setRegisteringForPurchase(true);
/*     */ 
/*  65 */     afForward = super.execute(a_mapping, form, a_request, a_response, a_dbTransaction);
/*     */ 
/*  70 */     ABUser user = form.getUser();
/*     */ 
/*  72 */     String sName = user.getFullName();
/*  73 */     if (!StringUtil.stringIsPopulated(sName))
/*     */     {
/*  75 */       sName = user.getUsername();
/*     */     }
/*     */ 
/*  78 */     HashMap hmParams = new HashMap();
/*     */ 
/*  80 */     hmParams.put("template", "ecommerce_user_registered");
/*  81 */     hmParams.put("email", user.getEmailAddress());
/*  82 */     hmParams.put("name", sName);
/*  83 */     hmParams.put("username", user.getUsername());
/*  84 */     hmParams.put("password", user.getPassword());
/*     */     try
/*     */     {
/*  88 */       this.m_emailManager.sendTemplatedEmail(hmParams, user.getLanguage());
/*     */     }
/*     */     catch (Bn2Exception be)
/*     */     {
/*     */     }
/*     */ 
/*  96 */     if ((StringUtil.stringIsPopulated(form.getUser().getPassword())) && (AssetBankSettings.getEcommerceOfflineForceLogin()))
/*     */     {
/*  98 */       DBTransaction dbLoginTransaction = getNewTransaction();
/*     */ 
/* 100 */       this.m_userManager.login(dbLoginTransaction, a_request, a_response, form.getUser().getEmailAddress(), form.getUser().getPassword(), false);
/*     */ 
/* 107 */       if (dbLoginTransaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 111 */           dbLoginTransaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 115 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 120 */       return a_mapping.findForward("Login");
/*     */     }
/*     */ 
/* 125 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.OfflineRegisterAction
 * JD-Core Version:    0.6.0
 */