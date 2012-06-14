/*      */ package com.bright.framework.category.util;
/*      */ 
/*      */// import J;
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.GlobalApplication;
/*      */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*      */ import com.bright.framework.category.bean.Category;
/*      */ import com.bright.framework.category.bean.Category.Translation;
/*      */ import com.bright.framework.category.bean.CategoryImpl;
/*      */ import com.bright.framework.category.bean.CategoryWithLanguage;
/*      */ import com.bright.framework.category.bean.FlatCategoryList;
/*      */ import com.bright.framework.category.constant.CategoryConstants;
/*      */ import com.bright.framework.category.service.CategoryManager;
/*      */ import com.bright.framework.constant.FrameworkConstants;
/*      */ import com.bright.framework.constant.FrameworkSettings;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.constant.LanguageConstants;
/*      */ import com.bright.framework.language.service.LanguageManager;
/*      */ import com.bright.framework.message.constant.MessageConstants;
/*      */ import com.bright.framework.search.constant.SearchConstants;
/*      */ import com.bright.framework.user.bean.UserProfile;
/*      */ import com.bright.framework.util.CollectionUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import com.bright.framework.util.UnicodeUtil;
/*      */ import java.util.Collection;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import org.apache.avalon.framework.component.ComponentException;
/*      */ import org.apache.avalon.framework.component.ComponentManager;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class CategoryUtil
/*      */   implements SearchConstants, FrameworkConstants, MessageConstants, CategoryConstants, AssetBankConstants
/*      */ {
/*   77 */   private static final Object c_categoryManagerLock = new Object();
/*      */   private static CategoryManager c_categoryManager;
/*      */ 
/*      */   public static Category createRootCategory(DBTransaction a_transaction, long a_lTypeId, boolean a_bPopulateName)
/*      */   {
/*   82 */     Category rootCat = new CategoryImpl();
/*   83 */     rootCat.setId(-1L);
/*   84 */     rootCat.setCategoryTypeId(a_lTypeId);
/*      */ 
/*   87 */     if (a_bPopulateName)
/*      */     {
/*      */       try
/*      */       {
/*   91 */         rootCat.setName(getCategoryManager().getRootCategoryName(a_transaction, LanguageConstants.k_defaultLanguage, a_lTypeId));
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*   95 */         GlobalApplication.getInstance().getLogger().error("CategoryUtil.createRootCategory: Error populating category name: ", e);
/*      */       }
/*      */     }
/*      */ 
/*   99 */     rootCat.setDepth(0);
/*  100 */     rootCat.setIdentifier("Root");
/*  101 */     rootCat.setIsBrowsable(true);
/*  102 */     rootCat.setIsRestrictive(false);
/*      */ 
/*  105 */     rootCat.setSummary(rootCat.getName());
/*      */ 
/*  108 */     if ((FrameworkSettings.getSupportMultiLanguage()) && (a_bPopulateName))
/*      */     {
/*      */       try
/*      */       {
/*  112 */         LanguageManager langManager = (LanguageManager)(LanguageManager)GlobalApplication.getInstance().getComponentManager().lookup("LanguageManager");
/*  113 */         List<Language> langs = langManager.getLanguages(a_transaction);
                    
/*  114 */         for (Language lang : langs)
/*      */         {
/*  116 */           Category.Translation trans = rootCat.createTranslation(lang);
/*  117 */           String sName = getCategoryManager().getRootCategoryName(a_transaction, trans.getLanguage(), a_lTypeId);
/*  118 */           trans.setName(sName);
/*  119 */           trans.setSummary(sName);
/*  120 */           rootCat.getTranslations().add(trans);
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  125 */         GlobalApplication.getInstance().getLogger().error("CategoryUtil.createRootCategory: Error getting multilingual root category: ", e);
/*      */       }
/*      */     }
/*      */ 
/*  129 */     return rootCat;
/*      */   }
/*      */ 
/*      */   public static FlatCategoryList filterFlatCategoryListByAllowedIds(Set<Long> a_categoryIds, FlatCategoryList a_flatCategoryList, boolean a_bIncludeWholeBranches)
/*      */   {
/*  143 */     if ((a_categoryIds == null) || (a_flatCategoryList == null) || (a_flatCategoryList.getCategories() == null) || (a_flatCategoryList.getCategories().size() <= 0))
/*      */     {
/*  146 */       return a_flatCategoryList;
/*      */     }
/*  148 */     if (a_categoryIds.size() <= 0)
/*      */     {
/*  151 */       return new FlatCategoryList();
/*      */     }
/*      */ 
/*  154 */     FlatCategoryList fcl = new FlatCategoryList();
/*  155 */     Vector vecFiltered = new Vector();
/*      */ 
/*  157 */     Vector<Category> cats = a_flatCategoryList.getCategories();
/*  158 */     int iNewMaxDepth = 0;
/*  159 */     int iDepthOffset = 0;
/*      */ 
/*  161 */     for (Category cat : cats)
/*      */     {
/*  163 */       boolean bWantCat = false;
/*      */ 
/*  167 */       if (a_categoryIds.contains(Long.valueOf(cat.getId())))
/*      */       {
/*  169 */         bWantCat = true;
/*  170 */         iDepthOffset = cat.getDepth();
/*      */       }
/*      */ 
/*  175 */       if (!bWantCat)
/*      */       {
/*  177 */         for (long lAncId : cat.getAllAncestorIds())
/*      */         {
/*  179 */           if (!a_categoryIds.contains(Long.valueOf(lAncId)))
/*      */             continue;
/*  181 */           bWantCat = true;
/*  182 */           break;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  189 */       if ((!bWantCat) && (a_bIncludeWholeBranches))
/*      */       {
/*  191 */         for (long lDesId : cat.getAllDescendantsIds())
/*      */         {
/*  194 */           if (!a_categoryIds.contains(Long.valueOf(lDesId)))
/*      */             continue;
/*  196 */           bWantCat = true;
/*  197 */           break;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  203 */       if (bWantCat)
/*      */       {
/*  205 */         if (!a_bIncludeWholeBranches)
/*      */         {
/*  207 */           cat = cat.clone();
/*  208 */           cat.setDepth(cat.getDepth() - iDepthOffset);
/*      */         }
/*  210 */         vecFiltered.add(cat);
/*  211 */         iNewMaxDepth = Math.max(iNewMaxDepth, cat.getDepth());
/*      */       }
/*      */     }
/*      */ 
/*  215 */     fcl.setCategories(vecFiltered);
/*  216 */     fcl.setDepth(iNewMaxDepth + 1);
/*      */ 
/*  218 */     return fcl;
/*      */   }
/*      */ 
/*      */   public static FlatCategoryList filterFlatCategoryListByCategoryIds(Set<Long> a_categoryIds, FlatCategoryList a_flatCategoryList)
/*      */   {
/*  235 */     if ((a_categoryIds == null) || (a_flatCategoryList == null) || (a_flatCategoryList.getCategories() == null) || (a_flatCategoryList.getCategories().size() <= 0))
/*      */     {
/*  238 */       return a_flatCategoryList;
/*      */     }
/*  240 */     if (a_categoryIds.size() <= 0)
/*      */     {
/*  243 */       return new FlatCategoryList();
/*      */     }
/*      */ 
/*  246 */     FlatCategoryList fcl = new FlatCategoryList();
/*  247 */     Vector vecFiltered = new Vector();
/*  248 */     Category cat = null;
/*      */ 
/*  252 */     vecFiltered = filterCategoryVectorByCategoryIds(a_categoryIds, a_flatCategoryList.getCategories(), true);
/*      */ 
/*  254 */     int iNewMaxDepth = 0;
/*      */ 
/*  256 */     for (int i = 0; i < vecFiltered.size(); i++)
/*      */     {
/*  258 */       cat = (Category)vecFiltered.get(i);
/*      */ 
/*  261 */       cat.setAncestors(filterCategoryVectorByCategoryIds(a_categoryIds, cat.getAncestors(), true));
/*  262 */       cat.setChildCategories(filterCategoryVectorByCategoryIds(a_categoryIds, cat.getChildCategories(), true));
/*  263 */       cat.setAllDescendantsIds(filterCategoryIdArrayByCategoryIds(a_categoryIds, cat.getAllDescendantsIds()));
/*      */ 
/*  266 */       if (cat.getDepth() <= iNewMaxDepth)
/*      */         continue;
/*  268 */       iNewMaxDepth = cat.getDepth();
/*      */     }
/*      */ 
/*  271 */     fcl.setCategories(vecFiltered);
/*      */ 
/*  273 */     fcl.setDepth(iNewMaxDepth + 1);
/*      */ 
/*  275 */     return fcl;
/*      */   }
/*      */ 
/*      */   public static Vector<Category> filterCategoryVectorByCategoryIds(Set<Long> a_sCategoryIds, Vector<Category> a_vecCategories, boolean a_bClone)
/*      */   {
/*  296 */     if ((a_sCategoryIds == null) || (a_vecCategories == null) || (a_vecCategories.isEmpty()))
/*      */     {
/*  298 */       return new Vector();
/*      */     }
/*      */ 
/*  301 */     if (a_sCategoryIds.size() == 0)
/*      */     {
/*  304 */       if (a_bClone)
/*      */       {
/*  306 */         return new Vector();
/*      */       }
/*      */ 
/*  309 */       a_vecCategories.clear();
/*  310 */       return a_vecCategories;
/*      */     }
/*      */ 
/*  313 */     Vector vecResults = new Vector();
/*      */ 
/*  315 */     Category cat = null;
/*  316 */     Category clonedCat = null;
/*      */ 
/*  319 */     for (int iCat = 0; iCat < a_vecCategories.size(); iCat++)
/*      */     {
/*  321 */       cat = (Category)a_vecCategories.get(iCat);
/*      */ 
/*  323 */       if ((!a_sCategoryIds.contains(Long.valueOf(cat.getId()))) && (!containsRestrictiveAncestor(a_sCategoryIds, cat)))
/*      */       {
/*      */         continue;
/*      */       }
/*  327 */       if (a_bClone)
/*      */       {
/*  329 */         clonedCat = cat.clone();
/*  330 */         vecResults.add(clonedCat);
/*      */       }
/*      */       else
/*      */       {
/*  334 */         vecResults.add(cat);
/*      */       }
/*      */ 
/*  340 */       if (!a_bClone)
/*      */         continue;
/*  342 */       Vector vecChildCats = clonedCat.getChildCategories();
/*  343 */       if (vecChildCats == null)
/*      */         continue;
/*  345 */       int i = 0;
/*      */ 
/*  347 */       while (i < vecChildCats.size())
/*      */       {
/*  349 */         Category child = (Category)vecChildCats.get(i);
/*      */ 
/*  352 */         if (!a_sCategoryIds.contains(new Long(child.getId())))
/*      */         {
/*  355 */           vecChildCats.remove(i);
/*      */         }
/*      */         else
/*      */         {
/*  362 */           i++;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  371 */     return vecResults;
/*      */   }
/*      */ 
/*      */   private static boolean containsRestrictiveAncestor(Set<Long> a_sCategoryIds, Category a_cat)
/*      */   {
/*  376 */     Category restrictive = a_cat.getClosestRestrictiveAncestor();
/*  377 */     return (restrictive != null) && (a_cat.getId() != restrictive.getId()) && (a_sCategoryIds.contains(Long.valueOf(restrictive.getId())));
/*      */   }
/*      */ 
/*      */   public static Vector<Category> getCategoryIdsInVectorNotInSet(Set<Long> a_categoryIds, Vector<Category> a_vecCategories, boolean a_bClone, boolean a_bIncludeAncestorsInCheck)
/*      */   {
/*  397 */     if ((a_categoryIds == null) || (a_vecCategories == null))
/*      */     {
/*  399 */       return new Vector();
/*      */     }
/*  401 */     if ((a_categoryIds.size() <= 0) || (a_vecCategories.isEmpty()))
/*      */     {
/*  404 */       if (a_bClone)
/*      */       {
/*  406 */         return (Vector)a_vecCategories.clone();
/*      */       }
/*      */ 
/*  409 */       return a_vecCategories;
/*      */     }
/*      */ 
/*  413 */     Vector vecResults = new Vector();
/*      */ 
/*  416 */     for (int iCat = 0; iCat < a_vecCategories.size(); iCat++)
/*      */     {
/*  418 */       boolean bInSet = false;
/*  419 */       Category cat = (Category)a_vecCategories.get(iCat);
/*      */ 
/*  422 */       if (categoryOrAncestorIsInIdSet(cat, a_categoryIds, a_bIncludeAncestorsInCheck))
/*      */         continue;
/*  424 */       if (a_bClone)
/*      */       {
/*  426 */         vecResults.add(((Category)a_vecCategories.get(iCat)).clone());
/*      */       }
/*      */       else
/*      */       {
/*  430 */         vecResults.add(a_vecCategories.get(iCat));
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  435 */     return vecResults;
/*      */   }
/*      */ 
/*      */   public static boolean categoryOrAncestorIsInIdSet(Category a_cat, Set<Long> a_categoryIds, boolean a_bIncludeAncestorsInCheck)
/*      */   {
/*  450 */     boolean bInSet = false;
/*      */ 
/*  452 */     if (a_categoryIds.contains(Long.valueOf(a_cat.getId())))
/*      */     {
/*  454 */       bInSet = true;
/*      */     }
/*  456 */     else if (a_bIncludeAncestorsInCheck)
/*      */     {
/*  459 */       for (long lAncestorId : a_cat.getAllAncestorIds())
/*      */       {
/*  461 */         if (!a_categoryIds.contains(Long.valueOf(lAncestorId)))
/*      */           continue;
/*  463 */         bInSet = true;
/*  464 */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  469 */     return bInSet;
/*      */   }
/*      */ 
/*      */   public static String convertCategoryVectorToString(Vector a_vecCategories, String a_sDelim)
/*      */   {
/*  490 */     StringBuffer sb = new StringBuffer("");
/*      */ 
/*  492 */     if ((a_vecCategories != null) && (a_vecCategories.size() > 0))
/*      */     {
/*  494 */       sb.append("" + ((Category)a_vecCategories.get(0)).getId());
/*      */ 
/*  496 */       if (a_vecCategories.size() > 1)
/*      */       {
/*  498 */         for (int i = 1; i < a_vecCategories.size(); i++)
/*      */         {
/*  500 */           sb.append(a_sDelim + ((Category)a_vecCategories.get(i)).getId());
/*      */         }
/*      */       }
/*      */     }
/*  504 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public static Vector<Long> getCategoryIdVector(Vector<Category> a_vecCategories)
/*      */   {
/*  522 */     Vector vResult = null;
/*      */ 
/*  524 */     if (a_vecCategories != null)
/*      */     {
/*  526 */       vResult = new Vector(a_vecCategories.size());
/*      */ 
/*  528 */       for (int i = 0; i < a_vecCategories.size(); i++)
/*      */       {
/*  530 */         vResult.add(Long.valueOf(((Category)a_vecCategories.get(i)).getId()));
/*      */       }
/*      */     }
/*  533 */     return vResult;
/*      */   }
/*      */ 
/*      */   public static long[] filterCategoryIdArrayByCategoryIds(Set<Long> a_sCategoryIds, long[] a_alCategoryIds)
/*      */   {
/*  550 */     if ((a_sCategoryIds == null) || (a_alCategoryIds == null) || (a_alCategoryIds.length <= 0))
/*      */     {
/*  553 */       return (long[])a_alCategoryIds.clone();
/*      */     }
/*  555 */     if (a_sCategoryIds.size() <= 0)
/*      */     {
/*  558 */       return new long[0];
/*      */     }
/*      */ 
/*  562 */     Vector vecResults = new Vector();
/*      */ 
/*  565 */     for (int iCat = 0; iCat < a_alCategoryIds.length; iCat++)
/*      */     {
/*  567 */       if (!a_sCategoryIds.contains(new Long(a_alCategoryIds[iCat]))) {
/*      */         continue;
/*      */       }
/*  570 */       vecResults.add(new Long(a_alCategoryIds[iCat]));
/*      */     }
/*      */ 
/*  575 */     long[] alResults = new long[vecResults.size()];
/*      */ 
/*  578 */     for (int i = 0; i < vecResults.size(); i++)
/*      */     {
/*  581 */       alResults[i] = ((Long)vecResults.get(i)).longValue();
/*      */     }
/*      */ 
/*  584 */     return alResults;
/*      */   }
/*      */ 
/*      */   public static String getListboxCategoryString(HttpServletRequest a_request)
/*      */   {
/*  599 */     String sCategoryIds = "";
/*      */ 
/*  602 */     Enumeration e = a_request.getParameterNames();
/*  603 */     String sName = null;
/*      */ 
/*  605 */     while (e.hasMoreElements())
/*      */     {
/*  607 */       sName = (String)e.nextElement();
/*      */ 
/*  609 */       if ((!sName.startsWith("listboxcategory_")) || 
/*  612 */         (a_request.getParameter(sName) == null) || (a_request.getParameter(sName).length() <= 0))
/*      */         continue;
/*  614 */       sCategoryIds = sCategoryIds + "," + a_request.getParameter(sName);
/*      */     }
/*      */ 
/*  619 */     return sCategoryIds;
/*      */   }
/*      */ 
/*      */   public static Vector<Category> createSelectedCategoryVector(DBTransaction a_dbTransaction, Vector<String> a_vCatIds, CategoryManager a_categoryManager, long a_lTreeId)
/*      */     throws Bn2Exception
/*      */   {
/*  633 */     Vector vCats = new Vector(a_vCatIds.size());
/*  634 */     Category category = null;
/*  635 */     long lId = 0L;
/*  636 */     for (int i = 0; i < a_vCatIds.size(); i++)
/*      */     {
/*  638 */       lId = Long.parseLong((String)a_vCatIds.get(i));
/*  639 */       category = a_categoryManager.getCategory(a_dbTransaction, a_lTreeId, lId);
/*  640 */       vCats.add(category);
/*      */     }
/*      */ 
/*  643 */     return vCats;
/*      */   }
/*      */ 
/*      */   public static Vector<Category> createTranslations(List<Category> a_categories, Language a_language)
/*      */   {
/*  653 */     Vector result = new Vector(a_categories.size());
/*      */ 
/*  655 */     for (Category category : a_categories)
/*      */     {
/*  657 */       result.add(new CategoryWithLanguage(category, a_language));
/*      */     }
/*      */ 
/*  660 */     return result;
/*      */   }
/*      */ 
/*      */   public static boolean namesMatch(String a_sName1, String a_sName2)
/*      */   {
/*  679 */     return a_sName1.trim().equalsIgnoreCase(a_sName2.trim());
/*      */   }
/*      */ 
/*      */   public static Vector ignoreSingleCategories(CategoryManager a_catManager, DBTransaction a_transaction, UserProfile a_userprofile, Vector a_vecCategories)
/*      */     throws Bn2Exception
/*      */   {
/*  697 */     while (a_vecCategories.size() == 1)
/*      */     {
/*  699 */       Category tempcat = (Category)a_vecCategories.firstElement();
/*      */ 
/*  701 */       a_vecCategories = a_catManager.getCategories(a_transaction, tempcat.getCategoryTypeId(), tempcat.getId(), a_userprofile.getCurrentLanguage());
/*      */     }
/*      */ 
/*  707 */     return a_vecCategories;
/*      */   }
/*      */ 
/*      */   public static String getCategoryDescriptionString(Vector a_vecCategories)
/*      */   {
/*  712 */     return getCategoryDescriptionString(a_vecCategories, ",", "\\");
/*      */   }
/*      */ 
/*      */   public static String getCategoryDescriptionString(Vector a_vecCategories, String a_sDelimiter, String a_sHierarchySeparator)
/*      */   {
/*  717 */     String sDescription = "";
/*      */ 
/*  719 */     for (int x = 0; x < a_vecCategories.size(); x++)
/*      */     {
/*  721 */       Category cat = (Category)a_vecCategories.elementAt(x);
/*  722 */       String sName = cat.getName();
/*      */ 
/*  724 */       if ((cat.getAncestors() != null) && (cat.getAncestors().size() > 0))
/*      */       {
/*  726 */         String sAncestors = "";
/*  727 */         for (int i = 0; i < cat.getAncestors().size(); i++)
/*      */         {
/*  729 */           Category temp = (Category)cat.getAncestors().elementAt(i);
/*  730 */           sAncestors = sAncestors + temp.getName() + a_sHierarchySeparator;
/*      */         }
/*  732 */         sName = sAncestors + sName;
/*      */       }
/*      */ 
/*  735 */       if (!sDescription.equals(""))
/*      */       {
/*  737 */         sDescription = sDescription + a_sDelimiter;
/*      */       }
/*      */ 
/*  740 */       sDescription = sDescription + sName;
/*      */     }
/*  742 */     return sDescription;
/*      */   }
/*      */ 
/*      */   public static Set<Long> uniqueIdsFromCategories(Collection<Category> a_categories)
/*      */   {
/*  747 */     Set ids = new HashSet();
/*  748 */     for (Category category : a_categories)
/*      */     {
/*  750 */       ids.add(Long.valueOf(category.getId()));
/*      */     }
/*  752 */     return ids;
/*      */   }
/*      */ 
/*      */   public static Vector<Category> getCategoriesFromIds(CategoryManager a_categoryManager, long a_lTreeId, String a_sIds)
/*      */   {
/*  759 */     Vector vecCategories = StringUtil.convertToVectorOfLongs(a_sIds, ",");
/*  760 */     return getCategoriesFromIds(a_categoryManager, a_lTreeId, vecCategories);
/*      */   }
/*      */ 
/*      */   public static Vector<Category> getCategoriesFromIds(long a_lTreeId, Collection<Long> a_ids)
/*      */   {
/*  766 */     return getCategoriesFromIds(getCategoryManager(), a_lTreeId, a_ids);
/*      */   }
/*      */ 
/*      */   public static Vector<Category> getCategoriesFromIds(CategoryManager a_categoryManager, long a_lTreeId, Collection<Long> a_ids)
/*      */   {
/*      */     Vector vecBlankCats;
/*      */     try
/*      */     {
/*  776 */       vecBlankCats = new Vector();
/*  777 */       if (a_ids != null)
/*      */       {
/*  779 */         for (Long longId : a_ids)
/*      */         {
/*  781 */           Category cat = a_categoryManager.getCategory(null, a_lTreeId, longId.longValue());
/*  782 */           vecBlankCats.add(cat);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/*  788 */       throw new RuntimeException(e);
/*      */     }
/*  790 */     return vecBlankCats;
/*      */   }
/*      */ 
/*      */   private static CategoryManager getCategoryManager()
/*      */   {
/*  795 */     synchronized (c_categoryManagerLock)
/*      */     {
/*  797 */       if (c_categoryManager == null)
/*      */       {
/*      */         try
/*      */         {
/*  801 */           c_categoryManager = (CategoryManager)GlobalApplication.getInstance().getComponentManager().lookup("CategoryManager");
/*      */         }
/*      */         catch (ComponentException ce)
/*      */         {
/*  806 */           throw new RuntimeException(ce);
/*      */         }
/*      */       }
/*      */     }
/*  810 */     return c_categoryManager;
/*      */   }
/*      */ 
/*      */   public static Vector<Category> getMatchingCategories(Vector<Category> a_vecCategories, CategoryPredicate a_catPredicate)
/*      */   {
/*  816 */     Vector vecCats = new Vector();
/*  817 */     if (a_vecCategories != null)
/*      */     {
/*  819 */       for (int i = 0; i < a_vecCategories.size(); i++)
/*      */       {
/*  821 */         Category cat = (Category)a_vecCategories.elementAt(i);
/*  822 */         if (!a_catPredicate.catMatches(cat))
/*      */           continue;
/*  824 */         vecCats.add(cat);
/*      */       }
/*      */     }
/*      */ 
/*  828 */     return vecCats;
/*      */   }
/*      */ 
/*      */   public static Category getCategoryById(Collection<Category> a_categories, long a_lId)
/*      */   {
/*  833 */     if (a_categories != null)
/*      */     {
/*  835 */       for (Category cat : a_categories)
/*      */       {
/*  837 */         if ((cat != null) && (cat.getId() == a_lId))
/*      */         {
/*  839 */           return cat;
/*      */         }
/*      */       }
/*      */     }
/*  843 */     return null;
/*      */   }
/*      */ 
/*      */   public static int getCategoryPosition(List<Category> a_categories, long a_lId)
/*      */   {
/*  855 */     if (a_categories != null)
/*      */     {
/*  857 */       for (int i = 0; i < a_categories.size(); i++)
/*      */       {
/*  859 */         Category cat = (Category)a_categories.get(i);
/*  860 */         if ((cat != null) && (cat.getId() == a_lId))
/*      */         {
/*  862 */           return i;
/*      */         }
/*      */       }
/*      */     }
/*  866 */     return -1;
/*      */   }
/*      */ 
/*      */   public static String getJSUnicodeName(String a_sName)
/*      */   {
/*  882 */     String sJSUnicodeName = UnicodeUtil.convertHTML2Unicode(a_sName);
/*  883 */     sJSUnicodeName = sJSUnicodeName.replaceAll("\"", "\\\\u0022");
/*  884 */     sJSUnicodeName = StringUtil.getJavascriptLiteralString(sJSUnicodeName);
/*      */ 
/*  886 */     return sJSUnicodeName;
/*      */   }
/*      */ 
/*      */   public static String getFullName(Category a_category)
/*      */   {
/*  891 */     StringBuilder sbFullName = new StringBuilder();
/*      */ 
/*  893 */     List <Category> ancestors = a_category.getAncestors();
/*  894 */     if (ancestors != null)
/*      */     {
/*  896 */       for (Category catAncestor : ancestors)
/*      */       {
/*  898 */         sbFullName.append(catAncestor.getName()).append("/");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  904 */     sbFullName.append(a_category.getName());
/*      */ 
/*  906 */     return sbFullName.toString();
/*      */   }
/*      */ 
/*      */   public static String getJSUnicodeFullName(Category a_category)
/*      */   {
/*  911 */     StringBuilder sbFullName = new StringBuilder();
/*      */ 
/*  913 */     Vector<Category> ancestors = a_category.getAncestors();
/*  914 */     if (ancestors != null)
/*      */     {
/*  916 */       for (Category catAncestor : ancestors)
/*      */       {
/*  918 */         sbFullName.append(catAncestor.getJSUnicodeName());
/*  919 */         sbFullName.append("/");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  924 */     sbFullName.append(a_category.getJSUnicodeName());
/*      */ 
/*  926 */     return sbFullName.toString();
/*      */   }
/*      */ 
/*      */   public static int compareTo(Category a_a, Object a_b)
/*      */   {
/*  931 */     if ((a_b instanceof Category))
/*      */     {
/*  933 */       return a_a.getName().compareToIgnoreCase(((Category)a_b).getName());
/*      */     }
/*  935 */     return -1;
/*      */   }
/*      */ 
/*      */   public static String getNameWithEscapedQuotes(Category a_category)
/*      */   {
/*  944 */     String sQuotedName = a_category.getName();
/*  945 */     if (StringUtil.stringIsPopulated(sQuotedName))
/*      */     {
/*  948 */       sQuotedName = sQuotedName.replaceAll("'", "\\\\'");
/*      */     }
/*      */ 
/*  951 */     return sQuotedName;
/*      */   }
/*      */ 
/*      */   public static void addInvisibleCategoryIds(Vector<Category> a_vExistingCategoriesInAsset, Set<Long> a_visIds, Vector<Category> a_vecNewCategoriesInAsset, boolean a_bImplySameForAllDescendants)
/*      */     throws Bn2Exception
/*      */   {
/*  969 */     Vector vInvisibleCategories = getCategoryIdsInVectorNotInSet(a_visIds, a_vExistingCategoriesInAsset, true, a_bImplySameForAllDescendants);
/*      */ 
/*  974 */     if ((vInvisibleCategories != null) && (vInvisibleCategories.size() > 0))
/*      */     {
/*  977 */       for (Object oCategory : vInvisibleCategories)
/*      */       {
/*  979 */         Category category = (Category)oCategory;
/*  980 */         a_vecNewCategoriesInAsset.add(category);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean getIsDescendantOfCategories(long a_lCatId, long a_lTreeId, Set<Long> a_categoryIds)
/*      */     throws Bn2Exception
/*      */   {
/*      */     Iterator i$;
/*  999 */     if (a_categoryIds != null)
/*      */     {
/* 1001 */       for (i$ = a_categoryIds.iterator(); i$.hasNext(); ) { long lId = ((Long)i$.next()).longValue();
/*      */ 
/* 1003 */         Category cat = getCategoryManager().getCategory(null, a_lTreeId, lId);
/* 1004 */         if (CollectionUtil.longArrayContains(cat.getAllDescendantsIds(), a_lCatId))
/*      */         {
/* 1006 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1011 */     return false;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.util.CategoryUtil
 * JD-Core Version:    0.6.0
 */