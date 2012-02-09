/*     */ package com.bright.framework.customfield.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.util.UserUtil;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.customfield.bean.CustomField;
/*     */ import com.bright.framework.customfield.form.CustomFieldForm;
/*     */ import com.bright.framework.customfield.service.CustomFieldManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveCustomFieldAction extends BTransactionAction
/*     */ {
/*  49 */   private CustomFieldManager m_fieldManager = null;
/*  50 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  76 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  79 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  81 */       this.m_logger.error("This user does not have permission to view the admin pages");
/*  82 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  86 */     CustomFieldForm form = (CustomFieldForm)a_form;
/*  87 */     form.validateSaveField(this.m_listManager, userProfile, a_transaction);
/*     */ 
/*  89 */     if (!form.getHasErrors())
/*     */     {
/*  91 */       this.m_fieldManager.saveCustomField(a_transaction, form.getCustomField());
/*     */ 
/*  94 */       boolean bUserApproval = UserUtil.isUserApprovalEnabled(this.m_fieldManager);
/*  95 */       a_request.getSession().getServletContext().setAttribute("userApprovalEnabled", Boolean.valueOf(bUserApproval));
/*     */ 
/*  97 */       return a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 100 */     if (form.getCustomField().getId() > 0L)
/*     */     {
/* 102 */       return a_mapping.findForward("UpdateFailure");
/*     */     }
/* 104 */     return a_mapping.findForward("AddFailure");
/*     */   }
/*     */ 
/*     */   public void setCustomFieldManager(CustomFieldManager a_fieldManager)
/*     */   {
/* 109 */     this.m_fieldManager = a_fieldManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_listManager)
/*     */   {
/* 114 */     this.m_listManager = a_listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.customfield.action.SaveCustomFieldAction
 * JD-Core Version:    0.6.0
 */