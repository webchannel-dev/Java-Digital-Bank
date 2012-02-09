/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.SortAttribute;
/*     */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*     */ import com.bright.assetbank.attribute.form.SortAttributeForm;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
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
/*     */ public class SaveSortAttributeAction extends BTransactionAction
/*     */   implements AttributeConstants, AssetBankConstants
/*     */ {
/*     */   private static final String c_ksClassName = "SaveSortAttributeAction";
/*  49 */   private AttributeManager m_attributeManager = null;
/*  50 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  78 */     ActionForward afForward = null;
/*  79 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  82 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  84 */       this.m_logger.error("SaveSortAttributeActionThis user does not have permission to view the admin pages");
/*  85 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  89 */     SortAttributeForm form = (SortAttributeForm)a_form;
/*     */ 
/*  91 */     if (form.getSortAttribute().getAttribute().getId() <= 0L)
/*     */     {
/*  93 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "sortAttributeNotSelected", userProfile.getCurrentLanguage()).getBody());
/*  94 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  99 */       boolean bNew = false;
/* 100 */       if (getIntParameter(a_request, "new") > 0)
/*     */       {
/* 102 */         bNew = true;
/*     */       }
/*     */ 
/* 106 */       this.m_attributeManager.saveSortAttribute(a_dbTransaction, form.getSortAttribute(), bNew);
/*     */ 
/* 108 */       String sQueryString = "";
/* 109 */       if (form.getSortAttribute().getSortAreaId() == 2L)
/*     */       {
/* 111 */         sQueryString = "browse=1";
/*     */       }
/*     */ 
/* 114 */       afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */     }
/*     */     catch (Bn2Exception bn2e)
/*     */     {
/* 118 */       this.m_logger.error("SaveSortAttributeAction" + bn2e.getMessage());
/* 119 */       throw bn2e;
/*     */     }
/*     */ 
/* 122 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 128 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 134 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.SaveSortAttributeAction
 * JD-Core Version:    0.6.0
 */