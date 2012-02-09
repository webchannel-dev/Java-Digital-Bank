/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.ImageAsset;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.HomepageForm;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.category.util.CategoryUtil;
/*     */ import com.bright.assetbank.repurposing.bean.RepurposedSlideshow;
/*     */ import com.bright.assetbank.repurposing.service.AssetRepurposingManager;
/*     */ import com.bright.assetbank.search.service.SavedSearchManager;
/*     */ import com.bright.assetbank.taxonomy.service.TaxonomyManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.bean.Brand;
/*     */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*     */ import com.bright.assetbank.workset.service.AssetWorksetManager;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.service.RefDataManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewHomeAction extends BTransactionAction
/*     */   implements CategoryConstants, AssetBankConstants
/*     */ {
/*  83 */   private AssetCategoryManager m_categoryManager = null;
/*  84 */   private RefDataManager m_refDataManager = null;
/*  85 */   protected IAssetManager m_assetManager = null;
/*  86 */   private SavedSearchManager m_savedSearchManager = null;
/*  87 */   private AssetWorksetManager m_assetWorksetManager = null;
/*  88 */   private AssetWorkflowManager m_assetWorkflowManager = null;
/*  89 */   private TaxonomyManager m_taxonomyManager = null;
/*  90 */   protected AssetRepurposingManager m_assetRepurposingManager = null;
/*     */ 
/*     */   public final ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 109 */     this.m_logger.debug("In ViewHomeAction.execute");
/* 110 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 111 */     HomepageForm form = (HomepageForm)a_form;
/*     */ 
/* 113 */     Vector vecDescriptiveCategories = this.m_categoryManager.getCategories(a_dbTransaction, 1L, CategoryUtil.getDescriptiveBrowseTopLevelCatId(), userProfile.getCurrentLanguage());
/*     */ 
/* 118 */     Vector vecPermissionCategories = getTopCategories(this.m_categoryManager, a_dbTransaction, userProfile);
/*     */ 
/* 121 */     if ((!userProfile.getIsAdmin()) && (!AssetBankSettings.getShowEmptyCategories()))
/*     */     {
/* 123 */       vecDescriptiveCategories = this.m_categoryManager.removeEmptyCategories(vecDescriptiveCategories, userProfile);
/*     */     }
/*     */ 
/* 126 */     Category cat = null;
/*     */ 
/* 129 */     for (int i = 0; i < vecDescriptiveCategories.size(); i++)
/*     */     {
/* 131 */       cat = (Category)vecDescriptiveCategories.get(i);
/* 132 */       if (cat.getImageUrl() == null)
/*     */         continue;
/* 134 */       form.setDescriptiveCategoriesHaveImages(true);
/* 135 */       break;
/*     */     }
/*     */ 
/* 140 */     for (int i = 0; i < vecPermissionCategories.size(); i++)
/*     */     {
/* 142 */       cat = (Category)vecPermissionCategories.get(i);
/* 143 */       if (cat.getImageUrl() == null)
/*     */         continue;
/* 145 */       form.setPermissionCategoriesHaveImages(true);
/* 146 */       break;
/*     */     }
/*     */ 
/* 150 */     if (AssetBankSettings.getAutoBrowseIntoSingleCategories())
/*     */     {
/* 152 */       vecDescriptiveCategories = CategoryUtil.ignoreSingleCategories(this.m_categoryManager, a_dbTransaction, userProfile, vecDescriptiveCategories);
/*     */     }
/*     */ 
/* 156 */     Vector vecBrowsableDescriptiveCategories = new Vector();
/* 157 */     Iterator itDescriptiveCategories = vecDescriptiveCategories.iterator();
/* 158 */     while (itDescriptiveCategories.hasNext())
/*     */     {
/* 160 */       Category category = (Category)itDescriptiveCategories.next();
/*     */ 
/* 162 */       if (category.getIsBrowsable())
/*     */       {
/* 164 */         vecBrowsableDescriptiveCategories.add(category);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 169 */     Vector vecBrowsablePermissionCategories = new Vector();
/* 170 */     Iterator itPermissionCategories = vecPermissionCategories.iterator();
/* 171 */     while (itPermissionCategories.hasNext())
/*     */     {
/* 173 */       Category category = (Category)itPermissionCategories.next();
/*     */ 
/* 175 */       if (category.getIsBrowsable())
/*     */       {
/* 177 */         vecBrowsablePermissionCategories.add(category);
/*     */       }
/*     */     }
/*     */ 
/* 181 */     form.setDescriptiveCategories(vecBrowsableDescriptiveCategories);
/* 182 */     form.setPermissionCategories(vecBrowsablePermissionCategories);
/*     */ 
/* 185 */     if (AssetBankSettings.getNumberRecentImages() > 0)
/*     */     {
/* 187 */       SearchResults srRecentImages = this.m_assetManager.getRecentAssets(a_dbTransaction, AssetBankSettings.getNumberRecentImages(), userProfile, -1, -1, true);
/*     */ 
/* 196 */       Vector vRecentImages = srRecentImages.getSearchResults();
/*     */ 
/* 198 */       form.setRecentImages(vRecentImages);
/*     */     }
/*     */ 
/* 202 */     LightweightAsset[] aPromotedAssets = null;
/* 203 */     if (AssetBankSettings.getPromotedImagesOnHomepage() > 0)
/*     */     {
/* 205 */       aPromotedAssets = this.m_assetManager.getPromotedAssets(a_dbTransaction, AssetBankSettings.getPromotedImagesOnHomepage(), userProfile.getSelectedFilters(), userProfile);
/*     */     }
/*     */     else
/*     */     {
/* 213 */       aPromotedAssets = new Asset[0];
/*     */     }
/*     */ 
/* 216 */     form.setPromotedAssets(aPromotedAssets);
/*     */ 
/* 219 */     if (AssetBankSettings.getFeaturedImageWidth() > 0)
/*     */     {
/* 221 */       long lUserId = -1L;
/* 222 */       if (userProfile.getUser() != null)
/*     */       {
/* 224 */         lUserId = userProfile.getUser().getId();
/*     */       }
/* 226 */       ImageAsset feature = (ImageAsset)this.m_assetManager.getFeaturedImage(a_dbTransaction, userProfile.getBrand().getId(), userProfile.getSelectedFilters(), userProfile.getCurrentLanguage().getCode(), lUserId);
/*     */ 
/* 232 */       if ((feature != null) && (!userProfile.getIsAdmin()) && (AssetBankSettings.getCanRestrictAssets()) && (AssetBankSettings.getHideRestrictedImages()))
/*     */       {
/* 234 */         feature.setOverrideRestriction(this.m_assetManager.userCanViewRestrictedAsset(userProfile, feature));
/*     */       }
/* 236 */       form.setFeaturedAsset(feature);
/*     */     }
/*     */ 
/* 239 */     long lCategoryId = getLongParameter(a_request, "categoryId");
/* 240 */     long lCategoryTreeId = getLongParameter(a_request, "categoryTypeId");
/*     */ 
/* 243 */     if (AssetBankSettings.getUseCategoryExplorer())
/*     */     {
/* 245 */       if (((lCategoryId > 0L) || (lCategoryId == -1L)) && (lCategoryTreeId > 0L))
/*     */       {
/* 247 */         form.setBrowseCategoryId(lCategoryId);
/* 248 */         form.setBrowseCategoryTreeId(lCategoryTreeId);
/*     */       }
/*     */       else
/*     */       {
/* 252 */         form.setBrowseCategoryId(userProfile.getSelectedCategoryId());
/* 253 */         form.setBrowseCategoryTreeId(userProfile.getCategoryTypeId());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 258 */     int iPageIndex = getIntParameter(a_request, "page");
/* 259 */     int iPageSize = getIntParameter(a_request, "pageSize");
/*     */ 
/* 261 */     if (iPageIndex >= 0)
/*     */     {
/* 263 */       userProfile.setSelectedPage(iPageIndex);
/*     */     }
/*     */ 
/* 266 */     if (iPageSize >= 0)
/*     */     {
/* 268 */       userProfile.setSelectedPageSize(iPageSize);
/*     */     }
/*     */ 
/* 271 */     if ((userProfile != null) && (userProfile.getUser() != null))
/*     */     {
/* 273 */       form.setUserPromotedSearches(this.m_savedSearchManager.getSavedSearches(a_dbTransaction, userProfile.getUser().getId(), true));
/*     */     }
/* 275 */     form.setGlobalPromotedSearches(this.m_savedSearchManager.getGlobalPromotedSavedSearches(a_dbTransaction));
/*     */ 
/* 282 */     long lNumUnsubmittedAssets = this.m_assetWorksetManager.getNumUnsubmittedAssets(userProfile);
/* 283 */     form.setNumUnsubmittedAssets(lNumUnsubmittedAssets);
/*     */ 
/* 285 */     long lNumUnsubmittedWorkflowedAssets = this.m_assetWorkflowManager.getNumUnsubmittedWorkflowedAssets(a_dbTransaction, userProfile);
/* 286 */     form.setNumUnsubmittedWorkflowedAssets(lNumUnsubmittedWorkflowedAssets);
/*     */ 
/* 289 */     if ((AssetBankSettings.getCategoryImageUsedForLogo()) && (!userProfile.getIsAdmin()))
/*     */     {
/* 291 */       Category categoryForWelcomeText = null;
/*     */ 
/* 293 */       if (userProfile.getCategoryIdForLogo() > 0L)
/*     */       {
/* 295 */         categoryForWelcomeText = this.m_categoryManager.getCategory(a_dbTransaction, 1L, userProfile.getCategoryIdForLogo());
/* 296 */         form.setCustomWelcomeText(categoryForWelcomeText.getDescription());
/*     */       }
/* 298 */       else if (userProfile.getAccessLevelIdForLogo() > 0L)
/*     */       {
/* 300 */         categoryForWelcomeText = this.m_categoryManager.getCategory(a_dbTransaction, 2L, userProfile.getAccessLevelIdForLogo());
/* 301 */         form.setCustomWelcomeText(categoryForWelcomeText.getDescription());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 306 */     String sBrowseType = a_request.getParameter("browseType");
/* 307 */     if ((sBrowseType != null) && (sBrowseType.equals("keyword")))
/*     */     {
/* 309 */       long lId = getLongParameter(a_request, "id");
/* 310 */       form.setKeywordList(this.m_taxonomyManager.getKeywordsForFilter(a_dbTransaction, null, lId, false, true, userProfile.getCurrentLanguage()));
/*     */     }
/*     */ 
/* 314 */     if ((AssetBankSettings.getSlideshowEnabled()) && (AssetBankSettings.getSlideshowRepurposingEnabled()))
/*     */     {
/* 317 */       ArrayList slideShows = this.m_assetRepurposingManager.getRepurposedSlideshows(a_dbTransaction, false, true, a_request);
/* 318 */       a_request.setAttribute("slideShowList", slideShows);
/* 319 */       a_request.setAttribute("numSlideShows", Integer.valueOf(slideShows.size()));
/*     */ 
/* 322 */       RepurposedSlideshow slideshowForHomepage = null;
/*     */ 
/* 324 */       long lRequestedSlideShowId = RequestUtil.getLongParameterOrAttribute(a_request, "slideshowId");
/*     */ 
/* 327 */       if (lRequestedSlideShowId > 0L)
/*     */       {
/* 329 */         slideshowForHomepage = this.m_assetRepurposingManager.getRepurposedSlideshow(a_dbTransaction, lRequestedSlideShowId, a_request);
/*     */ 
/* 332 */         if (slideshowForHomepage == null)
/*     */         {
/* 334 */           a_request.setAttribute("slideShowMissing", Boolean.valueOf(true));
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 340 */         Iterator itRepurposedSlideshows = slideShows.iterator();
/* 341 */         while (itRepurposedSlideshows.hasNext())
/*     */         {
/* 343 */           RepurposedSlideshow repurposedSlideshow = (RepurposedSlideshow)itRepurposedSlideshows.next();
/* 344 */           if (repurposedSlideshow.getDefaultOnHomepage())
/*     */           {
/* 346 */             slideshowForHomepage = repurposedSlideshow;
/* 347 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 352 */       if (slideshowForHomepage != null)
/*     */       {
/* 355 */         a_request.setAttribute("slideShow", slideshowForHomepage);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 360 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public static Set getFilteredVisibleAccessLevelIds(AssetCategoryManager a_categoryManager, DBTransaction a_dbTransaction, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 377 */     Set fullIds = a_userProfile.getPermissionCategoryIds(1);
/* 378 */     Set reducedIds = new HashSet();
/* 379 */     Iterator it = fullIds.iterator();
/* 380 */     while (it.hasNext())
/*     */     {
/* 382 */       Long ol = (Long)it.next();
/* 383 */       reducedIds.add(ol);
/*     */     }
/*     */ 
/* 388 */     Vector vecPermissionCategories = a_categoryManager.getCategories(a_dbTransaction, 2L, -1L);
/*     */ 
/* 391 */     boolean bGotAccessLevel = false;
/* 392 */     Vector vecVisibleSubLevels = null;
/*     */ 
/* 394 */     while (!bGotAccessLevel)
/*     */     {
/* 396 */       bGotAccessLevel = true;
/* 397 */       if (!a_userProfile.getIsAdmin())
/*     */       {
/* 399 */         vecPermissionCategories = CategoryUtil.filterCategoryVectorByCategoryIds(fullIds, vecPermissionCategories, true);
/*     */       }
/*     */ 
/* 403 */       if (vecPermissionCategories.size() != 1)
/*     */         continue;
/* 405 */       Category firstCat = (Category)vecPermissionCategories.get(0);
/*     */ 
/* 408 */       vecVisibleSubLevels = a_categoryManager.getCategories(a_dbTransaction, 2L, firstCat.getId());
/* 409 */       if (vecVisibleSubLevels.size() > 0)
/*     */       {
/* 411 */         vecPermissionCategories = vecVisibleSubLevels;
/*     */ 
/* 414 */         reducedIds.remove(new Long(firstCat.getId()));
/*     */ 
/* 417 */         bGotAccessLevel = false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 422 */     return reducedIds;
/*     */   }
/*     */ 
/*     */   public static Vector getTopCategories(AssetCategoryManager a_categoryManager, DBTransaction a_dbTransaction, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 439 */     Vector vecPermissionCategories = a_categoryManager.getCategories(a_dbTransaction, 2L, -1L, a_userProfile.getCurrentLanguage());
/*     */ 
/* 444 */     boolean bGotAccessLevel = false;
/* 445 */     Vector vecVisibleSubLevels = null;
/* 446 */     Set fullIds = a_userProfile.getPermissionCategoryIds(1);
/*     */ 
/* 448 */     while (!bGotAccessLevel)
/*     */     {
/* 450 */       bGotAccessLevel = true;
/* 451 */       if (!a_userProfile.getIsAdmin())
/*     */       {
/* 453 */         vecPermissionCategories = CategoryUtil.filterCategoryVectorByCategoryIds(fullIds, vecPermissionCategories, true);
/*     */       }
/*     */ 
/* 457 */       if (vecPermissionCategories.size() != 1)
/*     */         continue;
/* 459 */       Category firstCat = (Category)vecPermissionCategories.get(0);
/*     */ 
/* 462 */       vecVisibleSubLevels = a_categoryManager.getCategories(a_dbTransaction, 2L, firstCat.getId());
/* 463 */       if (vecVisibleSubLevels.size() > 0)
/*     */       {
/* 465 */         vecPermissionCategories = vecVisibleSubLevels;
/*     */ 
/* 468 */         bGotAccessLevel = false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 473 */     return vecPermissionCategories;
/*     */   }
/*     */ 
/*     */   public AssetCategoryManager getCategoryManager()
/*     */   {
/* 480 */     return this.m_categoryManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_sCategoryManager)
/*     */   {
/* 486 */     this.m_categoryManager = a_sCategoryManager;
/*     */   }
/*     */ 
/*     */   public RefDataManager getRefDataManager()
/*     */   {
/* 492 */     return this.m_refDataManager;
/*     */   }
/*     */ 
/*     */   public void setRefDataManager(RefDataManager a_sRefDataManager)
/*     */   {
/* 498 */     this.m_refDataManager = a_sRefDataManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 503 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetWorksetManager(AssetWorksetManager a_aManager)
/*     */   {
/* 508 */     this.m_assetWorksetManager = a_aManager;
/*     */   }
/*     */ 
/*     */   public void setAssetWorkflowManager(AssetWorkflowManager a_aManager)
/*     */   {
/* 513 */     this.m_assetWorkflowManager = a_aManager;
/*     */   }
/*     */ 
/*     */   public void setSavedSearchManager(SavedSearchManager a_savedSearchManager)
/*     */   {
/* 518 */     this.m_savedSearchManager = a_savedSearchManager;
/*     */   }
/*     */ 
/*     */   public void setTaxonomyManager(TaxonomyManager a_taxonomyManager)
/*     */   {
/* 523 */     this.m_taxonomyManager = a_taxonomyManager;
/*     */   }
/*     */ 
/*     */   public void setAssetRepurposingManager(AssetRepurposingManager assetRepurposingManager)
/*     */   {
/* 528 */     this.m_assetRepurposingManager = assetRepurposingManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewHomeAction
 * JD-Core Version:    0.6.0
 */