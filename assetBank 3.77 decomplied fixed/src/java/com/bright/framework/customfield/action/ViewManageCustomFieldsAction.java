/*     */ package com.bright.framework.customfield.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.customfield.form.CustomFieldForm;
/*     */ import com.bright.framework.customfield.service.CustomFieldManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.service.LanguageManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewManageCustomFieldsAction extends BTransactionAction
/*     */ {
/*  45 */   private CustomFieldManager m_fieldManager = null;
/*  46 */   private LanguageManager m_languageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  72 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  75 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  77 */       this.m_logger.error("This user does not have permission to view the admin pages");
/*  78 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  82 */     CustomFieldForm form = (CustomFieldForm)a_form;
/*  83 */     form.setCustomFields(this.m_fieldManager.getCustomFields(a_transaction, -1L, -1L, userProfile.getUser()));
/*  84 */     form.setUsageTypes(this.m_fieldManager.getCustomFieldUsageTypes(a_transaction));
/*  85 */     form.setTypes(this.m_fieldManager.getCustomFieldTypes(a_transaction));
/*     */ 
/*  87 */     if (FrameworkSettings.getSupportMultiLanguage())
/*     */     {
/*  90 */       this.m_languageManager.createEmptyTranslations(a_transaction, form.getCustomField());
/*     */     }
/*     */ 
/*  93 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setCustomFieldManager(CustomFieldManager a_fieldManager)
/*     */   {
/*  98 */     this.m_fieldManager = a_fieldManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager a_languageManager)
/*     */   {
/* 103 */     this.m_languageManager = a_languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.customfield.action.ViewManageCustomFieldsAction
 * JD-Core Version:    0.6.0
 */