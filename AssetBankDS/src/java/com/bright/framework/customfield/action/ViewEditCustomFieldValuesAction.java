/*     */ package com.bright.framework.customfield.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.customfield.bean.CustomField;
/*     */ import com.bright.framework.customfield.form.CustomFieldForm;
/*     */ import com.bright.framework.customfield.service.CustomFieldManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewEditCustomFieldValuesAction extends BTransactionAction
/*     */ {
/*  44 */   private CustomFieldManager m_fieldManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  71 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  74 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  76 */       this.m_logger.error("This user does not have permission to view the admin pages");
/*  77 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  81 */     long lId = getLongParameter(a_request, "id");
/*     */ 
/*  83 */     if (lId <= 0L)
/*     */     {
/*  86 */       if (a_request.getAttribute("id") != null)
/*     */       {
/*  88 */         lId = ((Long)a_request.getAttribute("id")).longValue();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  93 */     CustomFieldForm form = (CustomFieldForm)a_form;
/*  94 */     CustomField field = new CustomField();
/*  95 */     field.setId(lId);
/*  96 */     form.setCustomField(field);
/*  97 */     form.setValues(this.m_fieldManager.getAvailableCustomFieldValues(a_transaction, lId));
/*     */ 
/*  99 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setCustomFieldManager(CustomFieldManager a_fieldManager)
/*     */   {
/* 104 */     this.m_fieldManager = a_fieldManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.customfield.action.ViewEditCustomFieldValuesAction
 * JD-Core Version:    0.6.0
 */