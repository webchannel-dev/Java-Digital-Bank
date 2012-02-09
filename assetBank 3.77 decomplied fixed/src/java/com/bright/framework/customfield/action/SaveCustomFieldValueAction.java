/*     */ package com.bright.framework.customfield.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.customfield.bean.CustomFieldValue;
/*     */ import com.bright.framework.customfield.form.CustomFieldForm;
/*     */ import com.bright.framework.customfield.service.CustomFieldManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveCustomFieldValueAction extends BTransactionAction
/*     */ {
/*  45 */   private CustomFieldManager m_fieldManager = null;
/*  46 */   private ListManager m_listManager = null;
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
/*  83 */     form.validateSaveFieldValue(this.m_listManager, userProfile, a_transaction);
/*     */ 
/*  85 */     if (!form.getHasErrors())
/*     */     {
/*  87 */       this.m_fieldManager.saveCustomFieldValue(a_transaction, form.getCustomFieldValue());
/*  88 */       String sQueryString = "id=" + form.getCustomFieldValue().getCustomFieldId();
/*  89 */       return createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */     }
/*     */ 
/*  92 */     a_request.setAttribute("id", new Long(form.getCustomFieldValue().getCustomFieldId()));
/*  93 */     return a_mapping.findForward("Failure");
/*     */   }
/*     */ 
/*     */   public void setCustomFieldManager(CustomFieldManager a_fieldManager)
/*     */   {
/*  98 */     this.m_fieldManager = a_fieldManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_listManager)
/*     */   {
/* 103 */     this.m_listManager = a_listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.customfield.action.SaveCustomFieldValueAction
 * JD-Core Version:    0.6.0
 */