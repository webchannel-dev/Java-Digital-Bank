/*     */ package com.bright.assetbank.search.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.externalfilter.service.ExternalFilterManager;
/*     */ import com.bright.assetbank.search.bean.SearchBuilderClause;
/*     */ import com.bright.assetbank.search.bean.SearchBuilderQuery;
/*     */ import com.bright.assetbank.search.form.BaseSearchForm;
/*     */ import com.bright.assetbank.search.form.SearchBuilderForm;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.form.CategorySelectionForm;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.DateUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class SearchWithBuilderAction extends BaseSearchAction
/*     */ {
/*     */   protected SearchQuery getNewSearchCriteria(DBTransaction a_dbTransaction, BaseSearchForm a_form, HttpServletRequest a_request)
/*     */     throws ParseException, Bn2Exception
/*     */   {
/*  57 */     return createNewSearchBuilderQuery(a_form, a_request);
/*     */   }
/*     */ 
/*     */   public static SearchQuery createNewSearchBuilderQuery(BaseSearchForm a_form, HttpServletRequest a_request)
/*     */   {
/*  67 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  68 */     SearchBuilderForm form = (SearchBuilderForm)a_form;
/*  69 */     SearchBuilderQuery query = new SearchBuilderQuery();
/*  70 */     String sDefaultOperator = AssetBankSettings.getDefaultSearchOperator();
/*  71 */     if (StringUtil.stringIsPopulated(sDefaultOperator))
/*     */     {
/*  73 */       query.setDefaultOperator(sDefaultOperator);
/*     */     }
/*     */ 
/*  77 */     query.setMaxNoOfResults(FrameworkSettings.getMaxNoOfSearchResults());
/*     */ 
/*  80 */     Vector vClauses = (Vector)form.getClauses().clone();
/*  81 */     for (int i = vClauses.size() - 1; i >= 0; i--)
/*     */     {
/*  83 */       SearchBuilderClause clause = (SearchBuilderClause)vClauses.get(i);
/*     */ 
/*  85 */       if (clause.isVisible())
/*     */         continue;
/*  87 */       vClauses.remove(i);
/*     */     }
/*     */ 
/*  92 */     if (StringUtils.isNotEmpty(form.getKeywords()))
/*     */     {
/*  94 */       SearchBuilderClause firstClause = (SearchBuilderClause)vClauses.firstElement();
/*  95 */       firstClause.setAttributeId(0L);
/*  96 */       firstClause.setValue(form.getKeywords());
/*     */     }
/*     */ 
/*  99 */     query.setClauses(vClauses);
/*     */ 
/* 101 */     query.setupPermissions(userProfile);
/*     */ 
/* 103 */     query.setCategoryIds(a_form.getAllCategoryIds());
/* 104 */     String sDescriptiveCats = a_form.getDescriptiveCategoryForm().getCategoryIds();
/* 105 */     String sPermissionCats = a_form.getPermissionCategoryForm().getCategoryIds();
/* 106 */     query.setPermissionCategoriesToRefine(StringUtil.convertToVector(sPermissionCats, ","));
/* 107 */     query.setDescriptiveCategoriesToRefine(StringUtil.convertToVector(sDescriptiveCats, ","));
/*     */ 
/* 109 */     query.setOrCategories(a_form.getOrCategories());
/* 110 */     query.setWithoutCategory(a_form.getWithoutCategory());
/* 111 */     query.setIncludeImplicitCategoryMembers(a_form.getIncludeImplicitCategoryMembers());
/* 112 */     query.setIncludePreviousVersions(a_form.getIncludePreviousVersions());
/* 113 */     query.setLanguageCode(a_form.getLanguage());
/*     */ 
/* 115 */     query.setSelectedFilters(userProfile.getSelectedFilters());
/*     */ 
/* 117 */     if (a_form.getSelectedEntities() != null)
/*     */     {
/* 119 */       for (long lEntityId : a_form.getSelectedEntities())
/*     */       {
/* 121 */         query.addAssetEntityIdToInclude(lEntityId);
/*     */       }
/*     */     }
/*     */ 
/* 125 */     return query;
/*     */   }
/*     */ 
/*     */   protected void validateForm(BaseSearchForm a_form, HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/* 131 */     validateSearchBuilderForm(null, this.m_listManager, a_form, a_request);
/*     */   }
/*     */ 
/*     */   public static void validateSearchBuilderForm(DBTransaction a_transaction, ListManager a_listManager, BaseSearchForm a_form, HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/* 142 */     boolean bUseCache = getIntParameter(a_request, "cachedCriteria") == 1;
/*     */ 
/* 145 */     if (!bUseCache)
/*     */     {
/* 147 */       ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 148 */       SearchBuilderForm form = (SearchBuilderForm)a_form;
/* 149 */       DateFormat format = AssetBankSettings.getStandardDateFormat();
/*     */ 
/* 151 */       for (int i = 0; i < form.getClauses().size(); i++)
/*     */       {
/* 153 */         SearchBuilderClause clause = (SearchBuilderClause)form.getClauses().get(i);
/*     */ 
/* 155 */         if ((i > 0) && (clause.isVisible()) && (StringUtils.isEmpty(clause.getValue())))
/*     */         {
/* 157 */           a_form.addError(a_listManager.getListItem(a_transaction, "failedValidationEmptyClause", userProfile.getCurrentLanguage(), null));
/* 158 */           break;
/*     */         }
/*     */ 
/* 161 */         if ((i > 0) && (clause.isVisible()) && (clause.isDate()) && (!DateUtil.isCorrectFullNumericalDateFormat(format, clause.getValue())))
/*     */         {
/* 163 */           a_form.addError(a_listManager.getListItem(a_transaction, "failedValidationDateFormat", userProfile.getCurrentLanguage(), null));
/* 164 */           break;
/*     */         }
/*     */ 
/* 167 */         if ((i <= 0) || (!clause.isVisible()) || (!clause.isNumeric()) || (clause.getNumericValue() != null))
/*     */           continue;
/* 169 */         a_form.addError(a_listManager.getListItem(a_transaction, "failedValidationNumberFormat", userProfile.getCurrentLanguage(), null));
/* 170 */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void populateForm(SearchQuery a_query, BaseSearchForm a_form)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void prepareSearchCriteriaBeforeSearch(HttpServletRequest a_request, SearchQuery a_searchQuery)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected boolean searchCriteriaSpecified(BaseSearchForm a_searchForm, SearchQuery a_searchQuery)
/*     */   {
/* 193 */     boolean bExists = (searchCriteriaSpecified(a_searchForm)) || (!this.m_externalFilterManager.emptySearchCriteria(a_searchQuery));
/* 194 */     return bExists;
/*     */   }
/*     */ 
/*     */   public static boolean searchCriteriaSpecified(BaseSearchForm a_searchForm)
/*     */   {
/* 204 */     boolean bPopulated = false;
/* 205 */     SearchBuilderForm form = (SearchBuilderForm)a_searchForm;
/* 206 */     for (int i = 0; i < form.getClauses().size(); i++)
/*     */     {
/* 208 */       SearchBuilderClause clause = (SearchBuilderClause)form.getClauses().get(i);
/*     */ 
/* 210 */       if ((!clause.isVisible()) || (!StringUtils.isNotEmpty(clause.getValue())))
/*     */         continue;
/* 212 */       bPopulated = true;
/* 213 */       break;
/*     */     }
/*     */ 
/* 217 */     if (!bPopulated)
/*     */     {
/* 219 */       bPopulated = (StringUtils.isNotEmpty(form.getAllCategoryIds())) || (form.getWithoutCategory());
/*     */     }
/*     */ 
/* 222 */     if (!bPopulated)
/*     */     {
/* 224 */       bPopulated = (form.getSelectedEntities() != null) && (form.getSelectedEntities().length > 0);
/*     */     }
/*     */ 
/* 227 */     return bPopulated;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.SearchWithBuilderAction
 * JD-Core Version:    0.6.0
 */