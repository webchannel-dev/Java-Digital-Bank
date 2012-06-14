/*     */ package com.bright.assetbank.taxonomy.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.category.form.ChangeCategoryParentForm;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
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
/*     */ public class ChangeKeywordParentAction extends BTransactionAction
/*     */   implements AssetBankConstants, CategoryConstants, MessageConstants
/*     */ {
/*     */   private static final String c_ksClassName = "ChangeKeywordParentAction";
/*  51 */   private AssetCategoryManager m_categoryManager = null;
/*     */ 
/* 119 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_categoryManager)
/*     */   {
/*  54 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  77 */     String ksMethodName = "execute";
/*  78 */     ActionForward afForward = null;
/*  79 */     ChangeCategoryParentForm form = (ChangeCategoryParentForm)a_form;
/*     */ 
/*  81 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  84 */     long lTreeId = form.getCategory().getCategoryTypeId();
/*     */ 
/*  86 */     if (lTreeId <= 0L)
/*     */     {
/*  88 */       this.m_logger.error("ChangeKeywordParentAction.execute : No tree id supplied");
/*  89 */       throw new Bn2Exception("ChangeKeywordParentAction.execute : No tree id supplied");
/*     */     }
/*     */ 
/*  92 */     boolean bValid = this.m_categoryManager.changeCategoryParent(a_dbTransaction, lTreeId, form.getCategory(), form.getNewParentId());
/*     */ 
/*  98 */     String sQueryString = "categoryTypeId=" + lTreeId;
/*  99 */     long lParentCatId = getLongParameter(a_request, "parentId");
/* 100 */     if (lParentCatId > 0L)
/*     */     {
/* 102 */       sQueryString = sQueryString + "&categoryId=" + lParentCatId;
/*     */     }
/*     */ 
/* 105 */     if (bValid)
/*     */     {
/* 107 */       afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */     }
/*     */     else
/*     */     {
/* 111 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "keywordErrorDuplicateName", userProfile.getCurrentLanguage()).getBody());
/*     */ 
/* 113 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 116 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 122 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.taxonomy.action.ChangeKeywordParentAction
 * JD-Core Version:    0.6.0
 */