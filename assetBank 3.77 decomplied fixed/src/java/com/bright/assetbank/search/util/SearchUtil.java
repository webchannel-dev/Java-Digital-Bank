/*     */ package com.bright.assetbank.search.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.application.service.FileAssetManagerImpl;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.batch.update.constant.UpdateSettings;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.constant.AssetEntityConstants;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.search.bean.BaseSearchQuery;
/*     */ import com.bright.assetbank.search.bean.SearchBuilderClause;
/*     */ import com.bright.assetbank.search.bean.SearchBuilderQuery;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.constant.AssetBankSearchConstants;
/*     */ import com.bright.assetbank.search.form.BaseSearchForm;
/*     */ import com.bright.assetbank.search.form.SearchForm;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.category.form.CategorySelectionForm;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.search.constant.SearchConstants;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.SQLException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.lucene.search.SortField;
/*     */ 
/*     */ public class SearchUtil
/*     */   implements SearchConstants, LanguageConstants, AssetEntityConstants, AssetBankConstants, AssetBankSearchConstants, AttributeConstants
/*     */ {
/*     */   private static final String c_ksClassName = "SearchUtil";
/*     */ 
/*     */   public static void populateSelectAssetSessionAttributes(BaseSearchForm a_form, HttpServletRequest a_request)
/*     */   {
/* 102 */     a_request.getSession().setAttribute("selectUrl", a_form.getSelectAssetUrl());
/* 103 */     a_request.getSession().setAttribute("paramNames", a_form.getSelectAssetParamNames());
/* 104 */     a_request.getSession().setAttribute("paramValues", a_form.getSelectAssetParamValues());
/* 105 */     a_request.getSession().setAttribute("entity", a_form.getEntityName());
/* 106 */     a_request.getSession().setAttribute("entityId", StringUtil.convertNumbersToString(a_form.getSelectedEntities(), ","));
/* 107 */     a_request.getSession().setAttribute("relationName", a_form.getRelationName());
/*     */   }
/*     */ 
/*     */   public static void resetSelectAssetSessionAttributes(HttpServletRequest a_request)
/*     */   {
/* 116 */     a_request.getSession().removeAttribute("selectUrl");
/* 117 */     a_request.getSession().removeAttribute("paramNames");
/* 118 */     a_request.getSession().removeAttribute("paramValues");
/* 119 */     a_request.getSession().removeAttribute("entityId");
/* 120 */     a_request.getSession().removeAttribute("entity");
/* 121 */     a_request.getSession().removeAttribute("relationName");
/*     */   }
/*     */ 
/*     */   public static void populateSelectAssetFormData(BaseSearchForm a_form, HttpServletRequest a_request)
/*     */   {
/* 131 */     a_form.setSelectAssetUrl((String)a_request.getSession().getAttribute("selectUrl"));
/* 132 */     a_form.setSelectAssetParamNames((String[])(String[])a_request.getSession().getAttribute("paramNames"));
/* 133 */     a_form.setSelectAssetParamValues((String[])(String[])a_request.getSession().getAttribute("paramValues"));
/* 134 */     String sVal = (String)a_request.getSession().getAttribute("entityId");
/* 135 */     if (StringUtil.stringIsPopulated(sVal))
/*     */     {
/* 137 */       a_form.setSelectedEntities(StringUtil.convertToArrayOfLongs(sVal, ","));
/*     */     }
/* 139 */     a_form.setEntityName((String)a_request.getSession().getAttribute("entity"));
/* 140 */     a_form.setRelationName((String)a_request.getSession().getAttribute("relationName"));
/*     */   }
/*     */ 
/*     */   public static void populateBulkUploads(DBTransaction a_dbTransaction, ABUserManager a_userManager, BaseSearchForm a_form, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 154 */     Vector vecBulkUploads = a_userManager.getUserBulkUploads(a_dbTransaction, a_userProfile.getUser().getId(), UpdateSettings.getBulkUploadsLimit());
/* 155 */     a_form.setBulkUploads(vecBulkUploads);
/*     */   }
/*     */ 
/*     */   public static void populateAssetEntities(DBTransaction a_dbTransaction, AssetEntityManager a_entityManager, ABUserProfile a_userProfile, BaseSearchForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 169 */     Vector entities = a_entityManager.getAllEntities(a_dbTransaction);
/* 170 */     LanguageUtils.setLanguageOnAll(entities, a_userProfile.getCurrentLanguage());
/*     */ 
/* 172 */     if ((entities != null) && (entities.size() == 1))
/*     */     {
/* 174 */       AssetEntity entity = (AssetEntity)entities.get(0);
/* 175 */       long[] aEntities = new long[1];
/* 176 */       aEntities[0] = entity.getId();
/* 177 */       a_form.setSelectedEntities(aEntities);
/* 178 */       a_form.setEntityPreSelected(true);
/*     */     }
/*     */ 
/* 181 */     a_form.setEntities(entities);
/*     */   }
/*     */ 
/*     */   public static void getAndSetAttributeSearches(Vector a_allAttributes, HttpServletRequest a_request, SearchCriteria a_searchCriteria)
/*     */     throws ParseException
/*     */   {
/* 193 */     Vector attributeSearches = new Vector();
/* 194 */     DateFormat appFormat = AssetBankSettings.getStandardDateFormat();
/*     */ 
/* 197 */     for (int i = 0; i < a_allAttributes.size(); i++)
/*     */     {
/* 199 */       Attribute att = (Attribute)a_allAttributes.get(i);
/*     */ 
/* 202 */       if ((att.getIsDatepicker()) || (att.getIsDateTime()))
/*     */       {
/* 205 */         Date dtLower = null;
/* 206 */         Date dtUpper = null;
/*     */ 
/* 209 */         String sParamVal = getBoundValue(a_request, att, true);
/*     */ 
/* 211 */         if (StringUtil.stringIsPopulated(sParamVal))
/*     */         {
/* 213 */           dtLower = appFormat.parse(sParamVal);
/*     */         }
/*     */ 
/* 217 */         sParamVal = getBoundValue(a_request, att, false);
/*     */ 
/* 219 */         if (StringUtil.stringIsPopulated(sParamVal))
/*     */         {
/* 221 */           dtUpper = appFormat.parse(sParamVal);
/*     */         }
/*     */ 
/* 224 */         if ((dtLower != null) || (dtUpper != null))
/*     */         {
/* 227 */           a_searchCriteria.addDateAttributeRange(att, dtLower, dtUpper);
/*     */         }
/*     */       }
/* 230 */       else if (att.getIsNumeric())
/*     */       {
/* 232 */         BigDecimal bdLower = null;
/* 233 */         BigDecimal bdUpper = null;
/*     */ 
/* 236 */         String sParamVal = getBoundValue(a_request, att, true);
/*     */ 
/* 238 */         if (StringUtils.isNotEmpty(sParamVal))
/*     */         {
/*     */           try
/*     */           {
/* 242 */             bdLower = new BigDecimal(sParamVal);
/*     */           }
/*     */           catch (NumberFormatException e)
/*     */           {
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 251 */         sParamVal = getBoundValue(a_request, att, false);
/*     */ 
/* 253 */         if (StringUtils.isNotEmpty(sParamVal))
/*     */         {
/*     */           try
/*     */           {
/* 257 */             bdUpper = new BigDecimal(sParamVal);
/*     */           }
/*     */           catch (NumberFormatException e)
/*     */           {
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 265 */         if ((bdLower != null) || (bdUpper != null))
/*     */         {
/* 267 */           a_searchCriteria.addNumericFlexibleAttributeRange(att, bdLower, bdUpper);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 273 */         String[] asParamVal = a_request.getParameterValues("attribute_" + att.getId());
/*     */ 
/* 276 */         for (int j = 0; (asParamVal != null) && (j < asParamVal.length); j++)
/*     */         {
/* 278 */           if ((asParamVal[j] == null) || (asParamVal[j].trim().length() <= 0))
/*     */             continue;
/* 280 */           AttributeValue attVal = new AttributeValue();
/* 281 */           attVal.setAttribute(att);
/* 282 */           attVal.setValue(asParamVal[j]);
/*     */ 
/* 285 */           String sAdditionalValue = a_request.getParameter("attribute_additional_" + att.getId());
/* 286 */           attVal.setAdditionalValue(sAdditionalValue);
/*     */ 
/* 288 */           attributeSearches.add(attVal);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 294 */     a_searchCriteria.setAttributeSearches(attributeSearches);
/*     */   }
/*     */ 
/*     */   private static String getBoundValue(HttpServletRequest a_request, Attribute a_att, boolean a_bLower)
/*     */   {
/* 299 */     String sThisSuffix = a_bLower ? "_lower" : "_upper";
/* 300 */     String sOtherSuffix = a_bLower ? "_upper" : "_lower";
/*     */ 
/* 302 */     String sParamVal = a_request.getParameter("attribute_" + a_att.getId() + sThisSuffix);
/*     */ 
/* 305 */     if ((sParamVal == null) && (a_request.getParameter("attribute_" + a_att.getId() + sOtherSuffix) == null))
/*     */     {
/* 307 */       sParamVal = a_request.getParameter("attribute_" + a_att.getId());
/*     */     }
/*     */ 
/* 310 */     if (StringUtils.isNotEmpty(sParamVal))
/*     */     {
/* 312 */       return sParamVal.trim();
/*     */     }
/*     */ 
/* 315 */     return null;
/*     */   }
/*     */ 
/*     */   public static void populateSortFieldCriteria(DBTransaction a_dbTransaction, AttributeManager a_attributeManager, SearchQuery a_searchQuery, long a_lSortArea)
/*     */     throws Bn2Exception
/*     */   {
/* 328 */     SortField[] aSortFields = null;
/*     */ 
/* 330 */     if (a_searchQuery.getSortAttributeId() == 0L)
/*     */     {
/* 333 */       aSortFields = a_attributeManager.getSortFields(a_dbTransaction, a_lSortArea, a_searchQuery.isSortDescending());
/*     */     }
/* 335 */     else if (a_searchQuery.getSortAttributeId() == -1L)
/*     */     {
/* 337 */       aSortFields = getPopularitySortFields(a_searchQuery, "f_views");
/*     */     }
/* 339 */     else if (a_searchQuery.getSortAttributeId() == -2L)
/*     */     {
/* 341 */       aSortFields = getPopularitySortFields(a_searchQuery, "f_downloads");
/*     */     }
/* 343 */     else if (a_searchQuery.getSortAttributeId() == -3L)
/*     */     {
/* 345 */       aSortFields = new SortField[1];
/* 346 */       aSortFields[0] = new SortField("f_long_feedbackCount_sort", 4, a_searchQuery.isSortDescending());
/*     */     }
/*     */     else
/*     */     {
/* 351 */       aSortFields = a_attributeManager.getSortFieldsForAttributeSort(a_dbTransaction, a_searchQuery.getSortAttributeId(), a_searchQuery.isSortDescending());
/*     */     }
/*     */ 
/* 355 */     if (AssetBankSettings.getSortByPresenceOfChildAssets())
/*     */     {
/* 357 */       SortField hasChildrenSortField = new SortField("f_hasChildren_sort", 4, true);
/* 358 */       if (aSortFields != null)
/*     */       {
/* 360 */         List sortFields = new ArrayList(Arrays.asList(aSortFields));
/* 361 */         sortFields.add(0, hasChildrenSortField);
/* 362 */         aSortFields = (SortField[])(SortField[])sortFields.toArray(new SortField[aSortFields.length + 1]);
/*     */       }
/*     */       else
/*     */       {
/* 366 */         aSortFields = new SortField[] { hasChildrenSortField };
/*     */       }
/*     */     }
/*     */ 
/* 370 */     a_searchQuery.setSortFields(aSortFields);
/*     */   }
/*     */ 
/*     */   private static SortField[] getPopularitySortFields(SearchQuery a_searchQuery, String a_sIndexField)
/*     */   {
/* 376 */     SortField[] aSortFields = new SortField[2];
/* 377 */     aSortFields[0] = new SortField(a_sIndexField + "_sort", 4, a_searchQuery.isSortDescending());
/* 378 */     aSortFields[1] = SortField.FIELD_SCORE;
/* 379 */     return aSortFields;
/*     */   }
/*     */ 
/*     */   public static SearchCriteria getSearchCriteriaFromRequest(HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/* 386 */     SearchForm form = new SearchForm();
/*     */ 
/* 389 */     form.setKeywords(a_request.getParameter("keywords"));
/* 390 */     form.setDateAddedLower(a_request.getParameter("dateAddedLower"));
/* 391 */     form.setDateAddedUpper(a_request.getParameter("dateAddedUpper"));
/* 392 */     form.setDateModLower(a_request.getParameter("dateModLower"));
/* 393 */     form.setDateModUpper(a_request.getParameter("dateModUpper"));
/* 394 */     form.setDateDownloadedLower(a_request.getParameter("dateDownloadedLower"));
/* 395 */     form.setDateDownloadedUpper(a_request.getParameter("dateDownloadedUpper"));
/* 396 */     form.setFilename(a_request.getParameter("filename"));
/* 397 */     form.setAssetIds(a_request.getParameter("assetIds"));
/* 398 */     form.getDescriptiveCategoryForm().setCategoryIds(a_request.getParameter("descriptiveCategoryForm.categoryIds"));
/* 399 */     form.getPermissionCategoryForm().setCategoryIds(a_request.getParameter("permissionCategoryForm.categoryIds"));
/*     */ 
/* 403 */     String sOrientation = a_request.getParameter("orientation");
/* 404 */     if (sOrientation != null)
/*     */     {
/* 406 */       form.setOrientation(Integer.parseInt(sOrientation));
/*     */     }
/*     */ 
/* 409 */     DBTransaction transaction = null;
/* 410 */     SearchCriteria criteria = null;
/*     */     try
/*     */     {
/* 414 */       DBTransactionManager transactionManager = (DBTransactionManager)GlobalApplication.getInstance().getComponentManager().lookup("DBTransactionManager");
/* 415 */       transaction = transactionManager.getNewTransaction();
/*     */ 
/* 418 */       criteria = form.populateSearchCriteria(a_request, null, transaction, null);
/*     */ 
/* 421 */       AssetManager assetManager = (AssetManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetManager");
/* 422 */       Vector allAttributes = assetManager.getAssetAttributes(transaction, null, false, false);
/* 423 */       getAndSetAttributeSearches(allAttributes, a_request, criteria);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 428 */       if (transaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 432 */           transaction.rollback();
/*     */         } catch (Exception ez) {
/*     */         }
/*     */       }
/* 436 */       GlobalApplication.getInstance().getLogger().error("Unable to get search criteria from request: " + e.getMessage());
/* 437 */       throw new Bn2Exception("SearchUtil.getSearchCriteriaFromRequest: Unable to get search criteria from request: " + e.getMessage(), e);
/*     */     }
/*     */     finally
/*     */     {
/* 442 */       if (transaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 446 */           transaction.commit();
/*     */         }
/*     */         catch (Exception ez)
/*     */         {
/* 450 */           GlobalApplication.getInstance().getLogger().error("Unable to commit transaction: " + ez.getMessage());
/* 451 */           throw new Bn2Exception("SearchUtil.getSearchCriteriaFromRequest: Unable to commit transaction: " + ez.getMessage());
/*     */         }
/*     */       }
/*     */     }
/* 455 */     return criteria;
/*     */   }
/*     */ 
/*     */   public static void addAddedByUserId(SearchQuery a_query, UserProfile a_userProfile)
/*     */   {
/* 461 */     if ((a_query instanceof SearchCriteria))
/*     */     {
/* 463 */       ((SearchCriteria)a_query).setAddedByUserId(a_userProfile.getUser().getId());
/*     */     }
/* 465 */     else if ((a_query instanceof SearchBuilderQuery))
/*     */     {
/* 467 */       SearchBuilderClause addedByClause = new SearchBuilderClause();
/* 468 */       addedByClause.setAttributeId(-4L);
/* 469 */       addedByClause.setValue(String.valueOf(a_userProfile.getUser().getId()));
/* 470 */       ((SearchBuilderQuery)a_query).getClauses().add(addedByClause);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void addAssetTypeIds(SearchQuery a_query, int[] a_aTypeIds)
/*     */   {
/* 478 */     if ((a_query instanceof SearchCriteria))
/*     */     {
/* 480 */       ((SearchCriteria)a_query).setTypeIds(a_aTypeIds);
/*     */     }
/* 482 */     else if ((a_query instanceof SearchBuilderQuery))
/*     */     {
/* 484 */       for (int iType : a_aTypeIds)
/*     */       {
/* 486 */         SearchBuilderClause clause = new SearchBuilderClause();
/* 487 */         clause.setAttributeId(-5L);
/* 488 */         clause.setValue(String.valueOf(iType));
/* 489 */         ((SearchBuilderQuery)a_query).getClauses().add(clause);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Vector<Asset> getAssetsFromSearchCriteria(SearchQuery a_criteria, String a_sLanguageCode)
/*     */     throws Bn2Exception
/*     */   {
/* 507 */     Vector vecAssets = null;
/*     */     try
/*     */     {
/* 512 */       int iPage = a_criteria.getPageIndex();
/* 513 */       int iPageSize = a_criteria.getPageSize();
/*     */ 
/* 515 */       if (a_criteria != null)
/*     */       {
/* 517 */         SearchResults results = ((MultiLanguageSearchManager)GlobalApplication.getInstance().getComponentManager().lookup("SearchManager")).searchByPageIndex(a_criteria, iPage, iPageSize, a_sLanguageCode);
/* 518 */         vecAssets = getAssetsFromSearchResults(results);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 523 */       throw new Bn2Exception("SearchUtil: Error: " + e.getMessage());
/*     */     }
/*     */ 
/* 526 */     return vecAssets;
/*     */   }
/*     */ 
/*     */   public static Vector<Asset> getAssetsFromSearchResults(SearchResults a_results)
/*     */     throws Bn2Exception
/*     */   {
/* 540 */     DBTransaction transaction = null;
/* 541 */     Vector vecOrderedAssets = null;
/* 542 */     String ksMethodName = "getAssetsFromSearchResults";
/*     */     try
/*     */     {
/* 547 */       DBTransactionManager transactionManager = (DBTransactionManager)(DBTransactionManager)GlobalApplication.getInstance().getComponentManager().lookup("DBTransactionManager");
/* 548 */       FileAssetManagerImpl fileAssetManager = (FileAssetManagerImpl)(FileAssetManagerImpl)GlobalApplication.getInstance().getComponentManager().lookup("FileAssetManagerImpl");
/* 549 */       transaction = transactionManager.getNewTransaction();
/*     */ 
/* 551 */       Vector vecIds = new Vector();
/* 552 */       for (int i = 0; i < a_results.getSearchResults().size(); i++)
/*     */       {
/* 555 */         LightweightAsset result = (LightweightAsset)a_results.getSearchResults().elementAt(i);
/* 556 */         vecIds.add(new Long(result.getId()));
/*     */       }
            /*     */
            /* 560 */
                //Vector vecAssets;
/*     */       Iterator i$;
/*     */       Long longId;
                Vector<Asset> vecAssets = fileAssetManager.getAssets(transaction, vecIds, null, false, false);
/* 561 */       vecOrderedAssets = new Vector();
/*     */ 
/* 564 */       for (i$ = vecIds.iterator(); i$.hasNext(); )
                    { longId = (Long)i$.next();
/*     */ 
/* 566 */         for (Asset tempAsset : vecAssets)
/*     */         {
/* 568 */           if (tempAsset.getId() == longId.longValue())
/*     */           {
/* 570 */             vecOrderedAssets.add(tempAsset);
/* 571 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */      
/* 578 */       if (transaction != null)
/*     */         try {
/* 580 */           transaction.rollback(); } catch (Exception ex) {
/*     */         }
/* 582 */       throw new Bn2Exception("SearchUtil.getAssetsFromSearchResults: Error formating results: " + e.getMessage(), e);
/*     */     }
/*     */     finally
/*     */     {
/* 586 */       if (transaction != null)
/*     */         try {
/* 588 */           transaction.commit(); } catch (Exception ex) {
/*     */         }
/*     */     }
/* 591 */     return vecOrderedAssets;
/*     */   }
/*     */ 
/*     */   public static boolean getSearchResultsContainImages(SearchResults a_results)
/*     */   {
/* 603 */     if ((a_results != null) && (a_results.getSearchResults() != null))
/*     */     {
/* 605 */       for (int i = 0; i < a_results.getSearchResults().size(); i++)
/*     */       {
/* 607 */         LightweightAsset asset = (LightweightAsset)a_results.getSearchResults().elementAt(i);
/* 608 */         if (asset.getIsImage())
/*     */         {
/* 610 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 614 */     return false;
/*     */   }
/*     */ 
/*     */   public static BaseSearchQuery getCriteria(String a_sAbsoluteFilePath)
/*     */     throws Bn2Exception, ClassNotFoundException, SQLException, FileNotFoundException, IOException
/*     */   {
/* 620 */     InputStream file = new FileInputStream(a_sAbsoluteFilePath);
/* 621 */     InputStream buffer = new BufferedInputStream(file);
/* 622 */     ObjectInput input = new ObjectInputStream(buffer);
/* 623 */     BaseSearchQuery criteria = (BaseSearchQuery)input.readObject();
/* 624 */     input.close();
/* 625 */     buffer.close();
/* 626 */     file.close();
/* 627 */     return criteria;
/*     */   }
/*     */ 
/*     */   public static String serializeSearch(FileStoreManager a_fileStoreManager, SearchQuery a_query)
/*     */     throws FileNotFoundException, Bn2Exception, IOException
/*     */   {
/* 635 */     String sFilepath = a_fileStoreManager.getUniqueFilepath("search.obj", StoredFileType.SEARCH);
/* 636 */     String sPath = a_fileStoreManager.getAbsolutePath(sFilepath);
/*     */ 
/* 638 */     FileOutputStream fout = new FileOutputStream(sPath);
/* 639 */     ObjectOutputStream oos = new ObjectOutputStream(fout);
/* 640 */     oos.writeObject(a_query);
/* 641 */     oos.close();
/* 642 */     fout.close();
/* 643 */     return sFilepath;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.util.SearchUtil
 * JD-Core Version:    0.6.0
 */