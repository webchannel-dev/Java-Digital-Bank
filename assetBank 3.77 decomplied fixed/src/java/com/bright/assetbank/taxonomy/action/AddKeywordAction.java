/*     */ package com.bright.assetbank.taxonomy.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.category.form.CategoryAdminForm;
/*     */ import com.bright.assetbank.category.util.CategoryUtil;
/*     */ import com.bright.assetbank.taxonomy.constant.KeywordConstants;
/*     */ import com.bright.assetbank.taxonomy.service.TaxonomyManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class AddKeywordAction extends BTransactionAction
/*     */   implements AssetBankConstants, CategoryConstants, MessageConstants, KeywordConstants
/*     */ {
/*     */   private static final String c_ksClassName = "AddKeywordAction";
/*  58 */   private TaxonomyManager m_taxonomyManager = null;
/*     */ 
/* 147 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setTaxonomyManager(TaxonomyManager a_taxonomyManager)
/*     */   {
/*  61 */     this.m_taxonomyManager = a_taxonomyManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  84 */     String ksMethodName = "execute";
/*  85 */     ActionForward afForward = null;
/*  86 */     CategoryAdminForm form = (CategoryAdminForm)a_form;
/*     */ 
/*  88 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  90 */     long lParentCatId = getLongParameter(a_request, "categoryId");
/*     */ 
/*  92 */     if (lParentCatId <= 0L)
/*     */     {
/*  94 */       lParentCatId = form.getCategoryId();
/*     */     }
/*     */ 
/*  98 */     Category newCategory = form.getNewCategory();
/*  99 */     boolean bValid = CategoryUtil.validateCategory(newCategory.getName(), form, this.m_listManager, userProfile, a_dbTransaction);
/*     */ 
/* 102 */     long lTreeId = form.getCategoryTreeId();
/*     */ 
/* 104 */     if (lTreeId <= 0L)
/*     */     {
/* 106 */       this.m_logger.error("AddKeywordAction.execute : No tree id supplied");
/* 107 */       throw new Bn2Exception("AddKeywordAction.execute : No tree id supplied");
/*     */     }
/*     */ 
/* 110 */     if (bValid)
/*     */     {
/* 112 */       if (!this.m_taxonomyManager.newKeyword(a_dbTransaction, newCategory, lParentCatId, lTreeId))
/*     */       {
/* 114 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "keywordErrorDuplicateName", userProfile.getCurrentLanguage()).getBody());
/* 115 */         bValid = false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 120 */     String sQueryString = "filter=";
/* 121 */     String sStartLetter = newCategory.getStartLetter();
/* 122 */     if (StringUtil.stringIsPopulated(sStartLetter))
/*     */     {
/* 124 */       sQueryString = "filter=" + sStartLetter;
/*     */     }
/*     */ 
/* 127 */     if (lParentCatId > 0L)
/*     */     {
/* 129 */       sQueryString = sQueryString + "&categoryId=" + lParentCatId;
/*     */     }
/*     */ 
/* 132 */     sQueryString = sQueryString + "&categoryTypeId=" + lTreeId;
/*     */ 
/* 134 */     if (!bValid)
/*     */     {
/* 136 */       a_request.setAttribute("categoryTypeId", new Long(lTreeId));
/* 137 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     else
/*     */     {
/* 141 */       afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */     }
/*     */ 
/* 144 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 150 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.taxonomy.action.AddKeywordAction
 * JD-Core Version:    0.6.0
 */