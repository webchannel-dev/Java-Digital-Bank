/*     */ package com.bright.framework.mail.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.mail.service.EmailTemplateManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public abstract class RunEmailTemplateTaskAction extends BTransactionAction
/*     */ {
/*  44 */   protected EmailTemplateManager m_emailTemplateManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  67 */     ActionForward afForward = null;
/*  68 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  71 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  73 */       this.m_logger.error("DeleteEmailTemplateAction.execute : User must be an admin.");
/*  74 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  77 */     long lTypeId = getLongParameter(a_request, "typeId");
/*  78 */     String sTextId = a_request.getParameter("textId");
/*     */ 
/*  80 */     if (validate(sTextId, lTypeId))
/*     */     {
/*  82 */       if ((StringUtils.isNotEmpty(sTextId)) && (lTypeId > 0L))
/*     */       {
/*  84 */         runTask(a_dbTransaction, sTextId, lTypeId);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  89 */       return a_mapping.findForward("SystemFailure");
/*     */     }
/*     */ 
/*  92 */     afForward = a_mapping.findForward("Success");
/*     */ 
/*  94 */     return afForward;
/*     */   }
/*     */   protected abstract void runTask(DBTransaction paramDBTransaction, String paramString, long paramLong) throws Bn2Exception;
/*     */ 
/*     */   protected abstract boolean validate(String paramString, long paramLong);
/*     */ 
/* 102 */   public void setEmailTemplateManager(EmailTemplateManager a_emailTemplateManager) { this.m_emailTemplateManager = a_emailTemplateManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.action.RunEmailTemplateTaskAction
 * JD-Core Version:    0.6.0
 */