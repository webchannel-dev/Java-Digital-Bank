/*     */ package com.bright.assetbank.attribute.filter.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*     */ import com.bright.assetbank.attribute.filter.bean.Filter;
/*     */ import com.bright.assetbank.attribute.filter.form.FilterForm;
/*     */ import com.bright.assetbank.attribute.filter.service.FilterManager;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewFilterAction extends BTransactionAction
/*     */   implements AttributeConstants
/*     */ {
/*  54 */   private FilterManager m_filterManager = null;
/*  55 */   private AssetManager m_assetManager = null;
/*  56 */   private LanguageManager m_languageManager = null;
/*  57 */   private ABUserManager m_userManager = null;
/*  58 */   private CategoryManager m_categoryManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  86 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  89 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  91 */       this.m_logger.error("This user does not have permission to view the admin pages");
/*  92 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  96 */     int iFilterType = getIntParameter(a_request, "type");
/*     */ 
/*  99 */     long lFilterId = getLongParameter(a_request, "id");
/* 100 */     Filter filter = null;
/*     */ 
/* 102 */     if (lFilterId <= 0L)
/*     */     {
/* 105 */       if (a_request.getAttribute("filter") != null)
/*     */       {
/* 107 */         filter = (Filter)a_request.getAttribute("filter");
/* 108 */         lFilterId = filter.getId();
/*     */       }
/*     */     }
/*     */ 
/* 112 */     FilterForm form = (FilterForm)a_form;
/*     */ 
/* 114 */     form.setType(iFilterType);
/*     */ 
/* 116 */     if (!form.getHasErrors())
/*     */     {
/* 118 */       if (lFilterId > 0L)
/*     */       {
/* 120 */         if (filter == null)
/*     */         {
/* 122 */           filter = this.m_filterManager.getFilter(a_transaction, lFilterId);
/*     */         }
/* 124 */         form.setFilter(filter);
/*     */       }
/*     */       else
/*     */       {
/* 129 */         this.m_languageManager.createEmptyTranslations(a_transaction, form.getFilter());
/*     */       }
/*     */     }
/*     */ 
/* 133 */     form.setAvailableAttributes(this.m_assetManager.getAssetAttributes(a_transaction, null));
/* 134 */     form.setUserGroups(this.m_userManager.getGroups(null));
/* 135 */     form.setCategories(this.m_categoryManager.getFlatCategoryList(a_transaction, 1L));
/* 136 */     form.setAccessLevels(this.m_categoryManager.getFlatCategoryList(a_transaction, 2L));
/*     */ 
/* 139 */     long lFilterTypeId = getLongParameter(a_request, "typeId");
/* 140 */     form.setFilterGroups(this.m_filterManager.getFilterGroups(a_transaction, lFilterTypeId));
/*     */ 
/* 142 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setFilterManager(FilterManager a_filterManager)
/*     */   {
/* 147 */     this.m_filterManager = a_filterManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 152 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_categoryManager)
/*     */   {
/* 157 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/* 162 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager languageManager)
/*     */   {
/* 167 */     this.m_languageManager = languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.filter.action.ViewFilterAction
 * JD-Core Version:    0.6.0
 */