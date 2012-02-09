/*     */ package com.bright.assetbank.taxonomy.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.category.form.ABCategoryForm;
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
/*     */ public class UpdateKeywordAction extends BTransactionAction
/*     */   implements AssetBankConstants, CategoryConstants, MessageConstants, KeywordConstants
/*     */ {
/*     */   private static final String c_ksClassName = "UpdateKeywordAction";
/*  56 */   private TaxonomyManager m_taxonomyManager = null;
/*     */ 
/*  62 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setTaxonomyManager(TaxonomyManager a_taxonomyManager)
/*     */   {
/*  59 */     this.m_taxonomyManager = a_taxonomyManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  65 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  88 */     String ksMethodName = "execute";
/*  89 */     ActionForward afForward = null;
/*  90 */     ABCategoryForm form = (ABCategoryForm)a_form;
/*     */ 
/*  92 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  94 */     long lCatId = getLongParameter(a_request, "categoryId");
/*  95 */     long lParentId = getLongParameter(a_request, "parentId");
/*     */ 
/*  98 */     long lTreeId = getLongParameter(a_request, "categoryTypeId");
/*     */ 
/* 100 */     if (lTreeId <= 0L)
/*     */     {
/* 102 */       this.m_logger.error("UpdateKeywordAction.execute : No tree id supplied");
/* 103 */       throw new Bn2Exception("UpdateKeywordAction.execute : No tree id supplied");
/*     */     }
/*     */ 
/* 106 */     boolean bValid = true;
/* 107 */     Category cat = form.getCategory();
/*     */ 
/* 111 */     String sQueryString = "categoryTypeId=" + lTreeId;
/* 112 */     if (lCatId > 0L)
/*     */     {
/* 114 */       sQueryString = sQueryString + "&categoryId=" + lCatId;
/*     */     }
/*     */ 
/* 118 */     String sParentQueryString = "categoryTypeId=" + lTreeId;
/* 119 */     String sStartLetter = cat.getStartLetter();
/* 120 */     if (StringUtil.stringIsPopulated(sStartLetter))
/*     */     {
/* 122 */       sParentQueryString = sParentQueryString + "&filter=" + sStartLetter;
/*     */     }
/* 124 */     if (lParentId > 0L)
/*     */     {
/* 126 */       sParentQueryString = sParentQueryString + "&categoryId=" + lParentId;
/*     */     }
/*     */ 
/* 130 */     if (a_request.getParameter("cancel") != null)
/*     */     {
/* 132 */       afForward = createRedirectingForward(sParentQueryString, a_mapping, "Success");
/* 133 */       return afForward;
/*     */     }
/*     */ 
/* 137 */     cat.setId(lCatId);
/* 138 */     bValid = CategoryUtil.validateCategory(cat.getName(), form, this.m_listManager, userProfile, a_dbTransaction);
/*     */ 
/* 140 */     if (bValid)
/*     */     {
/* 142 */       if (!this.m_taxonomyManager.updateKeyword(a_dbTransaction, cat, lTreeId))
/*     */       {
/* 144 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "keywordErrorDuplicateName", userProfile.getCurrentLanguage()).getBody());
/* 145 */         bValid = false;
/*     */       }
/*     */     }
/*     */ 
/* 149 */     if (!bValid)
/*     */     {
/* 151 */       afForward = createForward(sQueryString, a_mapping, "Failure");
/*     */     }
/*     */     else
/*     */     {
/* 155 */       afForward = createRedirectingForward(sParentQueryString, a_mapping, "Success");
/*     */     }
/*     */ 
/* 158 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.taxonomy.action.UpdateKeywordAction
 * JD-Core Version:    0.6.0
 */