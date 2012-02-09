/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.attribute.form.ListAttributeForm;
/*     */ import com.bright.assetbank.attribute.service.AttributeValueManager;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
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
/*     */ public class UpdateListValueAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  52 */   private AttributeValueManager m_attributeValueManager = null;
/*  53 */   private MultiLanguageSearchManager m_searchManager = null;
/*  54 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  80 */     ActionForward afForward = null;
/*  81 */     ListAttributeForm form = (ListAttributeForm)a_form;
/*  82 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  85 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  87 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  88 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  91 */     if (a_request.getParameter("b_cancel") == null)
/*     */     {
/*  93 */       if ((form.getValue().getValue() == null) || (form.getValue().getValue().trim().length() == 0))
/*     */       {
/*  95 */         form.addError(this.m_listManager.getListItem(a_transaction, "failedValidationListValue", userProfile.getCurrentLanguage()).getBody());
/*  96 */         afForward = a_mapping.findForward("ValidationFailure");
/*     */       }
/*     */       else
/*     */       {
/* 101 */         this.m_attributeValueManager.updateListAttributeValue(a_transaction, form.getValue());
/*     */ 
/* 106 */         String sQueryString = "attributeId=" + form.getValue().getAttribute().getId();
/* 107 */         afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 113 */       String sQueryString = "attributeId=" + form.getValue().getAttribute().getId();
/* 114 */       afForward = createRedirectingForward(sQueryString, a_mapping, "Cancel");
/*     */     }
/*     */ 
/* 117 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAttributeValueManager(AttributeValueManager a_attributeValueManager)
/*     */   {
/* 122 */     this.m_attributeValueManager = a_attributeValueManager;
/*     */   }
/*     */ 
/*     */   public MultiLanguageSearchManager getSearchManager()
/*     */   {
/* 128 */     return this.m_searchManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_sSearchManager)
/*     */   {
/* 134 */     this.m_searchManager = a_sSearchManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 140 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.UpdateListValueAction
 * JD-Core Version:    0.6.0
 */