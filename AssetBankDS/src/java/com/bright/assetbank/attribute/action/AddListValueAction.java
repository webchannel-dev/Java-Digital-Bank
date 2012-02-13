/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.attribute.form.ListAttributeForm;
/*     */ import com.bright.assetbank.attribute.service.AttributeValueManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class AddListValueAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  49 */   private AttributeValueManager m_attributeValueManager = null;
/*  50 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  74 */     ActionForward afForward = null;
/*  75 */     ListAttributeForm form = (ListAttributeForm)a_form;
/*  76 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  79 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  81 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  82 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  85 */     if ((form.getValue().getValue() == null) || (form.getValue().getValue().trim().length() == 0))
/*     */     {
/*  87 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationListValue", userProfile.getCurrentLanguage()).getBody());
/*  88 */       afForward = a_mapping.findForward("ValidationFailure");
/*     */     }
/*     */     else
/*     */     {
/*  92 */       this.m_attributeValueManager.addListAttributeValue(a_dbTransaction, form.getValue());
/*     */ 
/*  95 */       String sQueryString = "attributeId=" + form.getValue().getAttribute().getId();
/*  96 */       afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */     }
/*     */ 
/*  99 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAttributeValueManager(AttributeValueManager a_attributeValueManager)
/*     */   {
/* 104 */     this.m_attributeValueManager = a_attributeValueManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 109 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.AddListValueAction
 * JD-Core Version:    0.6.0
 */