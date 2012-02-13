/*     */ package com.bright.assetbank.search.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.action.ViewHomeAction;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.filter.bean.Filter;
/*     */ import com.bright.assetbank.attribute.filter.service.FilterManager;
/*     */ import com.bright.assetbank.category.util.CategoryUtil;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.bean.AssetEntityRetreivalCriteria;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.externalfilter.service.ExternalFilterManager;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.constant.AssetBankSearchConstants;
/*     */ import com.bright.assetbank.search.form.BaseSearchForm;
/*     */ import com.bright.assetbank.search.util.SearchUtil;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.category.bean.FlatCategoryList;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.category.form.CategorySelectionForm;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewSearchAction extends BTransactionAction
/*     */   implements CategoryConstants, AssetBankConstants, AssetBankSearchConstants
/*     */ {
/*  91 */   protected IAssetManager m_assetManager = null;
/*  92 */   private ABUserManager m_userManager = null;
/*  93 */   private AssetCategoryManager m_categoryManager = null;
/*  94 */   private FilterManager m_filterManager = null;
/*  95 */   private LanguageManager m_languageManager = null;
/*  96 */   private AssetEntityManager m_assetEntityManager = null;
/*     */   private ExternalFilterManager m_externalFilterManager;
/*     */ 
/*     */   public void setExternalFilterManager(ExternalFilterManager a_externalFilterManager)
/*     */   {
/* 101 */     this.m_externalFilterManager = a_externalFilterManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 111 */     ActionForward afForward = null;
/* 112 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 114 */     BaseSearchForm form = (BaseSearchForm)a_form;
/*     */ 
/* 116 */     a_request.getSession().setAttribute("searchBuilder", String.valueOf(false));
/*     */ 
/* 118 */     if ((userProfile.getSelectedFilters() != null) && (userProfile.getSelectedFilters().size() == 1))
/*     */     {
/* 120 */       form.setSelectedFilter((Filter)userProfile.getSelectedFilters().firstElement());
/*     */     }
/* 122 */     form.setFilters(this.m_filterManager.getGlobalFilters(a_dbTransaction, userProfile));
/*     */ 
/* 126 */     Vector<Attribute> attributes = getSearchAttributes(a_dbTransaction, userProfile);
/* 127 */     form.setAssetAttributes(attributes);
/* 128 */     LanguageUtils.setLanguageOnAll(form.getAssetAttributes(), userProfile.getCurrentLanguage());
/*     */ 
/* 131 */     boolean bShowDescriptiveCategories = false;
/* 132 */     boolean bShowPermissionCategories = false;
/* 133 */     for (Attribute attribute : attributes)
/*     */     {
/* 141 */       if ((attribute.getFieldName() != null) && (attribute.getFieldName().equals("categories")) && (attribute.getIsVisible()) && (shouldShowAttribute(attribute)))
/*     */       {
/* 146 */         bShowDescriptiveCategories = true;
/*     */       }
/*     */ 
/* 149 */       if ((attribute.getFieldName() != null) && (attribute.getFieldName().equals("accessLevels")) && (attribute.getIsVisible()) && (shouldShowAttribute(attribute)))
/*     */       {
/* 154 */         bShowPermissionCategories = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 162 */     a_request.setAttribute("hideDescriptiveCategories", Boolean.valueOf(!bShowDescriptiveCategories));
/* 163 */     a_request.setAttribute("hidePermissionCategories", Boolean.valueOf(!bShowPermissionCategories));
/*     */ 
/* 166 */     HashMap hmExternalFieldAttributes = getExternalFilterAttributes(null, userProfile);
/* 167 */     form.setExternalFieldToAttributeMap(hmExternalFieldAttributes);
/*     */ 
/* 172 */     FlatCategoryList flatCategoryList = this.m_categoryManager.getFlatCategoryList(a_dbTransaction, 1L, userProfile.getCurrentLanguage());
/* 173 */     Vector vFlatCategories = flatCategoryList.getCategories();
/* 174 */     Vector vecTopLevelCats = this.m_categoryManager.getCategories(a_dbTransaction, 1L, CategoryUtil.getDescriptiveBrowseTopLevelCatId(), userProfile.getCurrentLanguage());
/*     */ 
/* 176 */     if ((!userProfile.getIsAdmin()) && (!AssetBankSettings.getShowEmptyCategories()))
/*     */     {
/* 178 */       vecTopLevelCats = this.m_categoryManager.removeEmptyCategories(vecTopLevelCats, userProfile);
/* 179 */       vFlatCategories = this.m_categoryManager.removeEmptyCategories(vFlatCategories, userProfile);
/*     */     }
/*     */ 
/* 182 */     form.getDescriptiveCategoryForm().setMaxNumOfSubCats(flatCategoryList.getDepth() - 1);
/* 183 */     form.getDescriptiveCategoryForm().setTopLevelCategories(vecTopLevelCats);
/* 184 */     form.getDescriptiveCategoryForm().setFlatCategoryList(vFlatCategories);
/*     */ 
/* 187 */     boolean bCanSearchAllAccessLevels = AssetBankSettings.getCanSearchAllAccessLevels();
/* 188 */     FlatCategoryList flatPermissionCategoryList = this.m_categoryManager.getFlatCategoryList(a_dbTransaction, 2L, userProfile.getCurrentLanguage());
/* 189 */     Vector vecPermissionTopLevelCats = this.m_categoryManager.getCategories(a_dbTransaction, 2L, -1L, userProfile.getCurrentLanguage());
/*     */ 
/* 191 */     if ((!bCanSearchAllAccessLevels) && (!userProfile.getIsAdmin()))
/*     */     {
/* 193 */       Set visIds = ViewHomeAction.getFilteredVisibleAccessLevelIds(this.m_categoryManager, a_dbTransaction, userProfile);
/*     */ 
/* 195 */       flatPermissionCategoryList = CategoryUtil.filterFlatCategoryListByCategoryIds(visIds, flatPermissionCategoryList);
/* 196 */       vecPermissionTopLevelCats = ViewHomeAction.getTopCategories(this.m_categoryManager, a_dbTransaction, userProfile);
/*     */     }
/*     */ 
/* 199 */     Vector vFlatPermissionCategories = flatPermissionCategoryList.getCategories();
/*     */ 
/* 201 */     form.getPermissionCategoryForm().setMaxNumOfSubCats(flatPermissionCategoryList.getDepth() - 1);
/* 202 */     form.getPermissionCategoryForm().setTopLevelCategories(vecPermissionTopLevelCats);
/* 203 */     form.getPermissionCategoryForm().setFlatCategoryList(vFlatPermissionCategories);
/*     */ 
/* 206 */     int iRefineSearch = getIntParameter(a_request, "refineSearch");
/*     */ 
/* 209 */     if (userProfile.getSearchCriteria() == null)
/*     */     {
/* 211 */       iRefineSearch = 0;
/* 212 */       form.setRefineSearch(false);
/*     */     }
/*     */ 
/* 215 */     if (iRefineSearch > 0)
/*     */     {
/* 217 */       form.setRefineSearch(true);
/* 218 */       form.setLanguage(userProfile.getSearchCriteria().getLanguageCode());
/* 219 */       Vector<Long> vecEntityIds = userProfile.getSearchCriteria().getAssetEntityIdsToInclude();
/* 220 */       if ((vecEntityIds != null) && (vecEntityIds.size() > 0))
/*     */       {
/* 222 */         long[] aIds = new long[vecEntityIds.size()];
/* 223 */         int iCount = 0;
/* 224 */         for (Long longId : vecEntityIds)
/*     */         {
/* 226 */           aIds[iCount] = longId.longValue();
/* 227 */           iCount++;
/*     */         }
/* 229 */         form.setSelectedEntities(aIds);
/*     */       }
/*     */     }
/* 232 */     else if (a_request.getParameter("searchThisCat") != null)
/*     */     {
/* 235 */       long lSearchThisCatType = Long.parseLong(a_request.getParameter("searchThisCatType"));
/* 236 */       form.setSearchingCategory(true);
/*     */ 
/* 238 */       if (userProfile.getSearchCriteria() == null)
/*     */       {
/* 240 */         userProfile.setSearchCriteria(new SearchCriteria());
/*     */       }
/*     */ 
/* 243 */       if (lSearchThisCatType == 1L)
/*     */       {
/* 245 */         userProfile.getSearchCriteria().setPermissionCategoriesToRefine(new Vector());
/* 246 */         userProfile.getSearchCriteria().setDescriptiveCategoriesToRefine(new Vector());
/* 247 */         userProfile.getSearchCriteria().getDescriptiveCategoriesToRefine().add(a_request.getParameter("searchThisCat"));
/*     */       }
/* 249 */       else if (lSearchThisCatType == 2L)
/*     */       {
/* 251 */         userProfile.getSearchCriteria().setDescriptiveCategoriesToRefine(new Vector());
/* 252 */         userProfile.getSearchCriteria().setPermissionCategoriesToRefine(new Vector());
/* 253 */         userProfile.getSearchCriteria().getPermissionCategoriesToRefine().add(a_request.getParameter("searchThisCat"));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 258 */     if (AssetBankSettings.getAssetEntitiesEnabled())
/*     */     {
/* 260 */       loadSearchableAssetEntities(a_dbTransaction, userProfile, form);
/*     */     }
/*     */ 
/* 263 */     if (userProfile.getIsAdmin())
/*     */     {
/* 266 */       List lanuages = this.m_languageManager.getLanguages(a_dbTransaction, true, true);
/* 267 */       lanuages = new ArrayList(lanuages);
/* 268 */       lanuages.remove(userProfile.getCurrentLanguage());
/* 269 */       form.setLanguages(lanuages);
/*     */     }
/*     */ 
/* 273 */     if ((!form.getRefineSearch()) && (a_request.getParameter("newSearch") != null))
/*     */     {
/* 275 */       SearchUtil.populateSelectAssetSessionAttributes(form, a_request);
/*     */     }
/* 277 */     else if (a_request.getParameter("newSearch") == null)
/*     */     {
/* 279 */       SearchUtil.populateSelectAssetFormData(form, a_request);
/* 280 */       form.setEntityPreSelected(form.getEntityName() != null);
/*     */     }
/*     */     else
/*     */     {
/* 284 */       SearchUtil.resetSelectAssetSessionAttributes(a_request);
/*     */     }
/*     */ 
/* 288 */     afForward = a_mapping.findForward("Success");
/* 289 */     return afForward;
/*     */   }
/*     */ 
/*     */   protected boolean shouldShowAttribute(Attribute attribute)
/*     */   {
/* 294 */     return attribute.isSearchField();
/*     */   }
/*     */ 
/*     */   protected Vector<Attribute> getSearchAttributes(DBTransaction a_dbTransaction, ABUserProfile a_userProfile) throws Bn2Exception
/*     */   {
/* 299 */     if (a_userProfile.getIsAdmin())
/*     */     {
/* 302 */       return this.m_assetManager.getAssetAttributes(a_dbTransaction, null, true, false);
/*     */     }
/*     */ 
/* 306 */     return this.m_assetManager.getAssetAttributes(a_dbTransaction, a_userProfile.getVisibleAttributeIds(), true, false);
/*     */   }
/*     */ 
/*     */   protected HashMap<String, Attribute> getExternalFilterAttributes(DBTransaction a_dbTransaction, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 321 */     HashMap hmAttributeToFieldMap = this.m_externalFilterManager.getAttributeToFieldNameMap();
/* 322 */     Vector vecAllAttributes = null;
/* 323 */     HashMap hmAttributes = null;
/*     */ 
/* 325 */     if (hmAttributeToFieldMap.size() > 0)
/*     */     {
/* 327 */       hmAttributes = new HashMap();
/*     */ 
/* 330 */       if (a_userProfile.getIsAdmin())
/*     */       {
/* 332 */         vecAllAttributes = this.m_assetManager.getAssetAttributes(null, null, false, false);
/*     */       }
/*     */       else
/*     */       {
/* 336 */         vecAllAttributes = this.m_assetManager.getAssetAttributes(null, a_userProfile.getVisibleAttributeIds(), false, false);
/*     */       }
/*     */ 
/* 340 */       Iterator it = vecAllAttributes.iterator();
/* 341 */       while (it.hasNext())
/*     */       {
/* 343 */         Attribute attr = (Attribute)it.next();
/* 344 */         Long olId = new Long(attr.getId());
/* 345 */         String sField = (String)hmAttributeToFieldMap.get(olId);
/*     */ 
/* 348 */         if (StringUtil.stringIsPopulated(sField))
/*     */         {
/* 350 */           hmAttributes.put(sField, attr);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 356 */     return hmAttributes;
/*     */   }
/*     */ 
/*     */   protected void loadSearchableAssetEntities(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, BaseSearchForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 368 */     AssetEntityRetreivalCriteria criteria = new AssetEntityRetreivalCriteria();
/* 369 */     criteria.setSearchableOnly(!a_userProfile.getIsAdmin());
/* 370 */     Vector entities = this.m_assetEntityManager.getEntities(a_dbTransaction, criteria);
/* 371 */     LanguageUtils.setLanguageOnAll(entities, a_userProfile.getCurrentLanguage());
/*     */ 
/* 373 */     if ((entities != null) && (entities.size() == 1) && (!a_userProfile.getIsAdmin()))
/*     */     {
/* 375 */       AssetEntity entity = (AssetEntity)entities.get(0);
/* 376 */       long[] aIds = new long[1];
/* 377 */       aIds[0] = entity.getId();
/* 378 */       a_form.setSelectedEntities(aIds);
/* 379 */       a_form.setEntityPreSelected(true);
/*     */     }
/*     */ 
/* 382 */     a_form.setEntities(entities);
/*     */ 
/* 385 */     if ((AssetBankSettings.getSelectFirstAssetEntityInAdvancedSearch()) && (entities != null) && (entities.size() > 0))
/*     */     {
/* 387 */       long[] aIds = new long[1];
/* 388 */       aIds[0] = ((AssetEntity)entities.firstElement()).getId();
/* 389 */       a_form.setSelectedEntities(aIds);
/*     */     }
/*     */   }
/*     */ 
/*     */   public ABUserManager getUserManager()
/*     */   {
/* 397 */     return this.m_userManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 403 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public CategoryManager getCategoryManager()
/*     */   {
/* 409 */     return this.m_categoryManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_sCategoryManager)
/*     */   {
/* 415 */     this.m_categoryManager = a_sCategoryManager;
/*     */   }
/*     */ 
/*     */   public IAssetManager getAssetManager()
/*     */   {
/* 421 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 427 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setFilterManager(FilterManager a_filterManager)
/*     */   {
/* 432 */     this.m_filterManager = a_filterManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager languageManager)
/*     */   {
/* 437 */     this.m_languageManager = languageManager;
/*     */   }
/*     */ 
/*     */   public void setAssetEntityManager(AssetEntityManager assetEntityManager)
/*     */   {
/* 442 */     this.m_assetEntityManager = assetEntityManager;
/*     */   }
/*     */ 
/*     */   public AssetEntityManager getAssetEntityManager()
/*     */   {
/* 447 */     return this.m_assetEntityManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.ViewSearchAction
 * JD-Core Version:    0.6.0
 */