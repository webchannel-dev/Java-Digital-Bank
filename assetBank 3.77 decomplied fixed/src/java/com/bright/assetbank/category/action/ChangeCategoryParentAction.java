/*     */ package com.bright.assetbank.category.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.category.service.CategoryCountCacheManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
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
/*     */ public class ChangeCategoryParentAction extends BTransactionAction
/*     */   implements AssetBankConstants, CategoryConstants, MessageConstants
/*     */ {
/*  50 */   private AssetCategoryManager m_categoryManager = null;
/*     */ 
/*  56 */   protected ListManager m_listManager = null;
/*     */ 
/*  62 */   private CategoryCountCacheManager m_categoryCountCacheManager = null;
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_categoryManager)
/*     */   {
/*  53 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  59 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryCountCacheManager(CategoryCountCacheManager a_manager)
/*     */   {
/*  65 */     this.m_categoryCountCacheManager = a_manager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  88 */     ActionForward afForward = null;
/*  89 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  90 */     ChangeCategoryParentForm form = (ChangeCategoryParentForm)a_form;
/*     */ 
/*  93 */     if ((!userProfile.getIsLoggedIn()) || (!userProfile.getIsAdmin()))
/*     */     {
/*  95 */       this.m_logger.error("ChangeCategoryParentAction.execute : User does not have admin permission : " + userProfile);
/*  96 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 100 */     long lCategoryTypeId = form.getCategoryTypeId();
/*     */ 
/* 102 */     if (lCategoryTypeId <= 0L)
/*     */     {
/* 105 */       lCategoryTypeId = 1L;
/*     */     }
/*     */ 
/* 108 */     boolean bValid = this.m_categoryManager.changeCategoryParent(a_dbTransaction, lCategoryTypeId, form.getCategory(), form.getNewParentId());
/*     */ 
/* 113 */     this.m_categoryCountCacheManager.invalidateCache();
/*     */ 
/* 116 */     String sQueryString = "";
/* 117 */     long lParentCatId = getLongParameter(a_request, "parentId");
/* 118 */     if (lParentCatId > 0L)
/*     */     {
/* 120 */       sQueryString = sQueryString + "categoryId=" + lParentCatId;
/*     */     }
/*     */ 
/* 123 */     if (bValid)
/*     */     {
/* 125 */       String sSuccessActionName = null;
/*     */ 
/* 127 */       if (lCategoryTypeId == 1L)
/*     */       {
/* 129 */         sSuccessActionName = "SuccessCategories";
/*     */       }
/*     */       else
/*     */       {
/* 133 */         sSuccessActionName = "SuccessAccessLevels";
/*     */       }
/*     */ 
/* 136 */       afForward = createRedirectingForward(sQueryString, a_mapping, sSuccessActionName);
/*     */     }
/*     */     else
/*     */     {
/* 140 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "categoryErrorDuplicateName", userProfile.getCurrentLanguage()).getBody());
/* 141 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 144 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.action.ChangeCategoryParentAction
 * JD-Core Version:    0.6.0
 */