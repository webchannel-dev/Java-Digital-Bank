/*      */ package com.bright.assetbank.user.bean;
/*      */ 
/*      */ import com.bn2web.common.constant.CommonConstants;
/*      */ import com.bn2web.common.service.GlobalApplication;
/*      */ import com.bright.assetbank.application.bean.LightweightAsset;
/*      */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.approval.bean.AssetInList;
/*      */ import com.bright.assetbank.assetbox.bean.AssetBasket;
/*      */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*      */ import com.bright.assetbank.assetbox.bean.AssetBoxSummary;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ import com.bright.assetbank.attribute.filter.bean.Filter;
/*      */ import com.bright.assetbank.batch.update.service.BatchUpdateController;
/*      */ import com.bright.assetbank.cmsintegration.bean.CmsInfo;
/*      */ import com.bright.assetbank.ecommerce.bean.OrderSearchCriteria;
/*      */ import com.bright.assetbank.search.bean.RecentSearch;
/*      */ import com.bright.assetbank.search.bean.SavedSearch;
/*      */ import com.bright.assetbank.search.bean.SearchBuilderQuery;
/*      */ import com.bright.assetbank.user.constant.UserConstants;
/*      */ import com.bright.assetbank.user.service.ABUserManager;
/*      */ import com.bright.framework.category.bean.Category;
/*      */ import com.bright.framework.common.bean.BrightMoney;
/*      */ import com.bright.framework.constant.FrameworkSettings;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.util.LanguageUtils;
/*      */ import com.bright.framework.search.bean.SearchQuery;
/*      */ import com.bright.framework.user.bean.User;
/*      */ import com.bright.framework.user.bean.UserProfile;
/*      */ import com.bright.framework.util.FileUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import org.apache.avalon.framework.component.ComponentManager;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class ABUserProfile extends UserProfile
/*      */   implements CommonConstants, AssetBankConstants, UserConstants
/*      */ {
/*      */   private static final String k_sEditPermissionKeyValue = "edit";
/*  122 */   private Vector m_vUsageExclusions = null;
/*  123 */   private Vector m_vAttributeExclusions = null;
/*  124 */   private Vector m_vFilterExclusions = null;
/*      */ 
/*  126 */   private Vector m_vVisibleAttributeIds = null;
/*  127 */   private Vector m_vWriteableAttributeIds = null;
/*      */ 
/*  129 */   private boolean m_bHaveSetDefaultFilter = false;
/*      */ 
/*  131 */   private Set[] m_permissionCategoryIds = new HashSet[15];
/*      */ 
/*  133 */   private Set m_updateableOrUpdateableWithApprovalCategoryIds = null;
/*  134 */   private boolean m_bListView = AssetBankSettings.getListViewIsDefault();
/*      */ 
/*  139 */   private Vector<Long> m_vGroupIds = null;
/*      */ 
/*  142 */   private String m_sPermissionsKey = null;
/*      */ 
/*  144 */   private SearchQuery m_searchQuery = null;
/*  145 */   private SearchQuery m_browseQuery = null;
/*  146 */   private OrderSearchCriteria m_orderSearchCriteria = null;
/*      */ 
/*  148 */   private AssetBox m_assetBox = null;
/*  149 */   private List m_assetBoxes = null;
/*      */ 
/*  151 */   private long m_lRecentImagesMinId = 0L;
/*  152 */   private BatchUpdateController m_batchUpdateController = null;
/*      */ 
/*  155 */   private long m_lDownloadsLeft = 0L;
/*      */ 
/*  158 */   private boolean m_bUserHasValidSubscription = false;
/*      */ 
/*  161 */   private CmsInfo m_cmsInfo = null;
/*  162 */   private long m_lSelectedCategoryId = 0L;
/*  163 */   private long m_lSelectedAccessLevelId = 0L;
/*  164 */   private int m_iSelectedPage = 0;
/*  165 */   private int m_iSelectedPageSize = 0;
/*      */   private Brand m_brand;
/*  171 */   private String m_sRegisterEmailAddress = null;
/*      */ 
/*  174 */   private int m_iMaxDiscount = 0;
/*  175 */   private boolean m_bSetDiscount = false;
/*      */ 
/*  178 */   private long m_lBatchImportTimestamp = 0L;
/*      */ 
/*  181 */   private boolean m_bBatchImportUnsubmitted = false;
/*      */ 
/*  183 */   private HashMap m_hmRoles = null;
/*  184 */   private boolean m_bAllowedByRole = false;
/*      */ 
/*  186 */   private Vector<Filter> m_vecSelectedFilters = new Vector();
/*      */ 
/*  188 */   private boolean m_bIpPermitted = false;
/*  189 */   private long m_lDownloadId = -1L;
/*      */ 
/*  191 */   private LinkedList m_recentSearches = null;
/*  192 */   private Vector m_vecLastUserSearch = null;
/*  193 */   private HashSet m_hsClickedSensitiveImageIds = null;
/*  194 */   private int m_iSearchResultsPerPage = 0;
/*      */ 
/*  196 */   private String m_sUserOS = null;
/*      */ 
/*  198 */   private long m_lCategoryIdForLogo = 0L;
/*  199 */   private long m_lAccessLevelIdForLogo = 0L;
/*      */   private Set<Long> m_rootDescriptiveCategoryIds;
/*  204 */   private boolean m_bLargeImagesOnView = false;
/*  205 */   private long m_lLastAssetBoxSortAttributeId = -1L;
/*  206 */   private boolean m_bLastAssetBoxSortAscending = false;
/*      */ 
/*      */   public ABUserProfile()
/*      */   {
/*  220 */     if (AssetBankSettings.ecommerce())
/*      */     {
/*  222 */       this.m_assetBox = new AssetBasket();
/*      */     }
/*      */     else
/*      */     {
/*  226 */       this.m_assetBox = new AssetBox();
/*      */     }
/*      */ 
/*  230 */     if (AssetBankSettings.getLargeAssetToggle().equals("default"))
/*      */     {
/*  232 */       this.m_bLargeImagesOnView = true;
/*      */     }
/*      */ 
/*  236 */     this.m_assetBoxes = new Vector();
/*  237 */     this.m_assetBoxes.add(this.m_assetBox);
/*      */ 
/*  240 */     this.m_brand = new Brand();
/*      */   }
/*      */ 
/*      */   public long getLastAssetBoxSortAttributeId()
/*      */   {
/*  245 */     return this.m_lLastAssetBoxSortAttributeId;
/*      */   }
/*      */ 
/*      */   public void setLastAssetBoxSortAttributeId(long a_lLastAssetBoxSortAttributeId)
/*      */   {
/*  250 */     this.m_lLastAssetBoxSortAttributeId = a_lLastAssetBoxSortAttributeId;
/*      */   }
/*      */ 
/*      */   public boolean getLastAssetBoxSortAscending()
/*      */   {
/*  255 */     return this.m_bLastAssetBoxSortAscending;
/*      */   }
/*      */ 
/*      */   public void setLastAssetBoxSortAscending(boolean a_bLastAssetBoxSortAscending)
/*      */   {
/*  260 */     this.m_bLastAssetBoxSortAscending = a_bLastAssetBoxSortAscending;
/*      */   }
/*      */ 
/*      */   public void invalidate()
/*      */   {
/*  274 */     if (this.m_batchUpdateController != null)
/*      */     {
/*  276 */       this.m_batchUpdateController.cancelCurrentBatchUpdate();
/*      */     }
/*  278 */     this.m_bSetDiscount = false;
/*      */ 
/*  280 */     super.invalidate();
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/*  298 */     String sOutput = null;
/*      */ 
/*  300 */     if (getUser() == null)
/*      */     {
/*  302 */       sOutput = "User object is null.";
/*      */     }
/*      */     else
/*      */     {
/*  306 */       ABUser user = (ABUser)getUser();
/*  307 */       sOutput = "\nUser id = " + user.getId() + "\n";
/*  308 */       sOutput = sOutput + "Username = " + user.getUsername() + "\n";
/*  309 */       sOutput = sOutput + "Full name = " + user.getFullName() + "\n";
/*      */     }
/*      */ 
/*  312 */     return sOutput;
/*      */   }
/*      */ 
/*      */   public void setAllowedByRole(boolean a_bAllowedByRole)
/*      */   {
/*  317 */     this.m_bAllowedByRole = a_bAllowedByRole;
/*      */   }
/*      */ 
/*      */   public boolean checkForRolePermission()
/*      */   {
/*  322 */     if (this.m_bAllowedByRole)
/*      */     {
/*  325 */       setAllowedByRole(false);
/*  326 */       return true;
/*      */     }
/*  328 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean getIsAdmin()
/*      */   {
/*  333 */     if (getUser() != null)
/*      */     {
/*  335 */       return ((ABUser)getUser()).getIsAdmin();
/*      */     }
/*      */ 
/*  338 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean getIsOrgUnitAdmin()
/*      */   {
/*  343 */     if (getUser() != null)
/*      */     {
/*  345 */       return ((ABUser)getUser()).getIsOrgUnitAdmin();
/*      */     }
/*      */ 
/*  348 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean getIsInitialOrgUnitAdmin()
/*      */   {
/*  353 */     if (getUser() != null)
/*      */     {
/*  355 */       return ((ABUser)getUser()).getIsAdminOfOrgUnit(1L);
/*      */     }
/*      */ 
/*  358 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean getCanUpdateAssets()
/*      */   {
/*  375 */     return (getIsAdmin()) || ((getPermissionCategoryIds(3) != null) && (getPermissionCategoryIds(3).size() > 0));
/*      */   }
/*      */ 
/*      */   public boolean getCanDeleteAssets()
/*      */   {
/*  392 */     return (getIsAdmin()) || ((getPermissionCategoryIds(3) != null) && (getPermissionCategoryIds(4).size() > 0));
/*      */   }
/*      */ 
/*      */   public boolean getCanUploadWithApproval()
/*      */   {
/*  408 */     return (getIsAdmin()) || ((getPermissionCategoryIds(6) != null) && (getPermissionCategoryIds(6).size() > 0));
/*      */   }
/*      */ 
/*      */   public boolean getCanEditSubcategories(int a_lCatId)
/*      */   {
/*  426 */     return (getIsAdmin()) || (getPermissionCategoryIds(10).contains(new Long(a_lCatId)));
/*      */   }
/*      */ 
/*      */   public boolean getCanEditSubcategories(int a_lCatId, List a_ancestors)
/*      */   {
/*  443 */     if ((getIsAdmin()) || (getCanEditSubcategories(a_lCatId)))
/*      */     {
/*  445 */       return true;
/*      */     }
/*      */ 
/*  448 */     if ((a_ancestors != null) && (a_ancestors.size() > 0))
/*      */     {
/*  450 */       Iterator itAncestors = a_ancestors.iterator();
/*  451 */       while (itAncestors.hasNext())
/*      */       {
/*  453 */         if (getCanEditSubcategories((int)((Category)itAncestors.next()).getId()))
/*      */         {
/*  455 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  460 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean getHasEditSubcategoryPermissions()
/*      */   {
/*  476 */     boolean bHasEditCategories = false;
/*      */ 
/*  478 */     Set set = getPermissionCategoryIds(10);
/*  479 */     bHasEditCategories = (set != null) && (set.size() > 0);
/*      */ 
/*  481 */     return (getIsAdmin()) || (bHasEditCategories);
/*      */   }
/*      */ 
/*      */   public Vector getCategoryIdsAsVector(int a_iPermissionId)
/*      */   {
/*  496 */     return getVectorFromSet(getPermissionCategoryIds(a_iPermissionId));
/*      */   }
/*      */ 
/*      */   private static Vector getVectorFromSet(Set a_set)
/*      */   {
/*  512 */     if (a_set == null)
/*      */     {
/*  514 */       return null;
/*      */     }
/*      */ 
/*  517 */     return new Vector(a_set);
/*      */   }
/*      */ 
/*      */   public void setPermissionCategoryIds(int a_iPermissionId, Set a_categoryIds)
/*      */   {
/*  534 */     this.m_permissionCategoryIds[a_iPermissionId] = a_categoryIds;
/*      */   }
/*      */ 
/*      */   public Set getPermissionCategoryIds(int a_iPermissionId)
/*      */   {
/*  540 */     return this.m_permissionCategoryIds[a_iPermissionId];
/*      */   }
/*      */ 
/*      */   public void resetCategories()
/*      */   {
/*  546 */     for (int i = 0; i < 15; i++)
/*      */     {
/*  548 */       this.m_permissionCategoryIds[i] = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public HashMap getWriteableAttributeIdsAsHashMap()
/*      */   {
/*  555 */     if ((this.m_vWriteableAttributeIds == null) || (this.m_vWriteableAttributeIds.size() <= 0))
/*      */     {
/*  557 */       return new HashMap(0);
/*      */     }
/*  559 */     HashMap hmWritableAtts = new HashMap((int)Math.floor(this.m_vWriteableAttributeIds.size() / 0.75D) + 1);
/*  560 */     for (int i = 0; i < this.m_vWriteableAttributeIds.size(); i++)
/*      */     {
/*  562 */       hmWritableAtts.put(this.m_vWriteableAttributeIds.get(i), null);
/*      */     }
/*  564 */     return hmWritableAtts;
/*      */   }
/*      */ 
/*      */   public boolean getCanUpdateAssetsInAccessLevel(String a_slALId)
/*      */   {
/*  578 */     return getCanUpdateAssetsInAccessLevel(Long.parseLong(a_slALId));
/*      */   }
/*      */ 
/*      */   public boolean getCanUpdateAssetsInAccessLevel(long a_lALId)
/*      */   {
/*  592 */     Set ids = getUpdateableOrUpdateableWithApprovalCategoryIds();
/*      */ 
/*  595 */     return (ids != null) && (ids.contains(Long.valueOf(a_lALId)));
/*      */   }
/*      */ 
/*      */   public Set getUpdateableOrUpdateableWithApprovalCategoryIds()
/*      */   {
/*  613 */     if (this.m_updateableOrUpdateableWithApprovalCategoryIds == null)
/*      */     {
/*  615 */       this.m_updateableOrUpdateableWithApprovalCategoryIds = new HashSet();
/*      */ 
/*  618 */       if (getPermissionCategoryIds(3) != null)
/*      */       {
/*  620 */         this.m_updateableOrUpdateableWithApprovalCategoryIds.addAll(getPermissionCategoryIds(3));
/*      */       }
/*      */ 
/*  624 */       if (getPermissionCategoryIds(6) != null)
/*      */       {
/*  626 */         this.m_updateableOrUpdateableWithApprovalCategoryIds.addAll(getPermissionCategoryIds(6));
/*      */       }
/*      */     }
/*      */ 
/*  630 */     return this.m_updateableOrUpdateableWithApprovalCategoryIds;
/*      */   }
/*      */ 
/*      */   public void resetPermissionCaches()
/*      */   {
/*  644 */     this.m_updateableOrUpdateableWithApprovalCategoryIds = null;
/*  645 */     this.m_permissionCategoryIds = new HashSet[15];
/*  646 */     this.m_sPermissionsKey = null;
/*      */   }
/*      */ 
/*      */   public boolean getCanApproveImages()
/*      */   {
/*  660 */     return (getPermissionCategoryIds(7) != null) && (!getPermissionCategoryIds(7).isEmpty());
/*      */   }
/*      */ 
/*      */   public boolean getCanApproveImageUploads()
/*      */   {
/*  676 */     return (getPermissionCategoryIds(12) != null) && (!getPermissionCategoryIds(12).isEmpty());
/*      */   }
/*      */ 
/*      */   public SearchQuery getSearchCriteria()
/*      */   {
/*  687 */     return this.m_searchQuery;
/*      */   }
/*      */ 
/*      */   public void setSearchCriteria(SearchQuery a_searchQuery)
/*      */   {
/*  693 */     this.m_searchQuery = a_searchQuery;
/*      */   }
/*      */ 
/*      */   public SearchQuery getBrowseCriteria()
/*      */   {
/*  698 */     return this.m_browseQuery;
/*      */   }
/*      */ 
/*      */   public void setBrowseCriteria(SearchQuery a_browseQuery)
/*      */   {
/*  704 */     this.m_browseQuery = a_browseQuery;
/*      */   }
/*      */ 
/*      */   public Vector getAttributeExclusions()
/*      */   {
/*  710 */     return this.m_vAttributeExclusions;
/*      */   }
/*      */ 
/*      */   public Vector getVisibleAttributeIds()
/*      */   {
/*  716 */     return this.m_vVisibleAttributeIds;
/*      */   }
/*      */ 
/*      */   public void setAttributeExclusions(Vector a_sAttributeExclusions)
/*      */   {
/*  722 */     this.m_vAttributeExclusions = a_sAttributeExclusions;
/*      */   }
/*      */ 
/*      */   public void setVisibleAttributeIds(Vector a_sVisibleAttributes)
/*      */   {
/*  728 */     this.m_vVisibleAttributeIds = a_sVisibleAttributes;
/*      */   }
/*      */ 
/*      */   public Vector<Long> getGroupIds()
/*      */   {
/*  734 */     return this.m_vGroupIds;
/*      */   }
/*      */ 
/*      */   public void setGroupIds(Vector<Long> a_sGroupIds)
/*      */   {
/*  740 */     this.m_vGroupIds = a_sGroupIds;
/*      */   }
/*      */ 
/*      */   public Vector getWriteableAttributeIds()
/*      */   {
/*  746 */     return this.m_vWriteableAttributeIds;
/*      */   }
/*      */ 
/*      */   public void setWriteableAttributeIds(Vector a_sWriteableAttributeIds)
/*      */   {
/*  752 */     this.m_vWriteableAttributeIds = a_sWriteableAttributeIds;
/*      */   }
/*      */ 
/*      */   public AssetBox getAssetBox()
/*      */   {
/*  758 */     return this.m_assetBox;
/*      */   }
/*      */ 
/*      */   public void setAssetBox(AssetBox a_sAssetBox)
/*      */   {
/*  764 */     this.m_assetBox = a_sAssetBox;
/*      */   }
/*      */ 
/*      */   public AssetBasket getBasket()
/*      */   {
/*  772 */     if ((this.m_assetBox instanceof AssetBasket)) {
/*  773 */       return (AssetBasket)this.m_assetBox;
/*      */     }
/*  775 */     throw new UnsupportedOperationException("ABUserProfile.getBasket() is not supported unless eCommerce is enabled");
/*      */   }
/*      */ 
/*      */   public void setBasket(AssetBasket a_basket)
/*      */   {
/*  783 */     setAssetBox(a_basket);
/*      */   }
/*      */ 
/*      */   public List getAssetBoxes()
/*      */   {
/*  791 */     return this.m_assetBoxes;
/*      */   }
/*      */ 
/*      */   public void setAssetBoxes(List a_assetBoxes)
/*      */   {
/*  799 */     this.m_assetBoxes = a_assetBoxes;
/*      */   }
/*      */ 
/*      */   public int getNumOwnAssetBoxes()
/*      */   {
/*  808 */     int iCount = 0;
/*  809 */     Iterator itAssetBoxes = this.m_assetBoxes.iterator();
/*  810 */     while (itAssetBoxes.hasNext())
/*      */     {
/*  812 */       if (((AssetBoxSummary)itAssetBoxes.next()).isShared())
/*      */         continue;
/*  814 */       iCount++;
/*      */     }
/*      */ 
/*  817 */     return iCount;
/*      */   }
/*      */ 
/*      */   public boolean getCanClearAssetBox()
/*      */   {
/*  834 */     if (getAssetBox().isShared())
/*      */     {
/*  836 */       if (getIsLoggedIn())
/*      */       {
/*  838 */         Iterator itAssets = getAssetBox().getAssets().iterator();
/*  839 */         while (itAssets.hasNext())
/*      */         {
/*  841 */           long lAddedByUserId = ((AssetInList)itAssets.next()).getAddedToAssetBoxByUserId();
/*  842 */           if ((lAddedByUserId > 0L) && (lAddedByUserId != getUser().getId()))
/*      */           {
/*  844 */             return false;
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  850 */         return false;
/*      */       }
/*      */     }
/*  853 */     return true;
/*      */   }
/*      */ 
/*      */   public int getNumAssetBoxes()
/*      */   {
/*  861 */     return Math.max(this.m_assetBoxes.size(), 1);
/*      */   }
/*      */ 
/*      */   public void deleteAssetBox(long a_lId)
/*      */   {
/*  869 */     Iterator itAssetBoxes = this.m_assetBoxes.iterator();
/*  870 */     while (itAssetBoxes.hasNext())
/*      */     {
/*  872 */       if (((AssetBoxSummary)itAssetBoxes.next()).getId() != a_lId)
/*      */         continue;
/*  874 */       itAssetBoxes.remove();
/*      */     }
/*      */ 
/*  877 */     if ((this.m_assetBox != null) && (this.m_assetBox.getId() == a_lId))
/*      */     {
/*  879 */       this.m_assetBox = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector setInAssetBoxFlagOnAll(Vector a_vLightweightAssets)
/*      */   {
/*  888 */     if (this.m_assetBox != null)
/*      */     {
/*  890 */       for (int i = 0; i < a_vLightweightAssets.size(); i++)
/*      */       {
/*  892 */         LightweightAsset asset = (LightweightAsset)a_vLightweightAssets.get(i);
/*      */ 
/*  894 */         if (!this.m_assetBox.containsAsset(asset.getId()))
/*      */           continue;
/*  896 */         asset.setInAssetBox(true);
/*      */       }
/*      */     }
/*      */ 
/*  900 */     return a_vLightweightAssets;
/*      */   }
/*      */ 
/*      */   public Set<Long> getVisiblePermissionCategories()
/*      */   {
/*  905 */     return getPermissionCategoryIds(1);
/*      */   }
/*      */ 
/*      */   public long getRecentImagesMinId()
/*      */   {
/*  911 */     return this.m_lRecentImagesMinId;
/*      */   }
/*      */ 
/*      */   public void setRecentImagesMinId(long a_lRecentImagesMinId)
/*      */   {
/*  917 */     this.m_lRecentImagesMinId = a_lRecentImagesMinId;
/*      */   }
/*      */ 
/*      */   public BatchUpdateController getBatchUpdateController()
/*      */   {
/*  923 */     return this.m_batchUpdateController;
/*      */   }
/*      */ 
/*      */   public void setBatchUpdateController(BatchUpdateController a_sBatchUpdateController)
/*      */   {
/*  929 */     this.m_batchUpdateController = a_sBatchUpdateController;
/*      */   }
/*      */ 
/*      */   public long getDownloadsLeft()
/*      */   {
/*  935 */     return this.m_lDownloadsLeft;
/*      */   }
/*      */ 
/*      */   public void setDownloadsLeft(long a_lDownloadsLeft) {
/*  939 */     this.m_lDownloadsLeft = a_lDownloadsLeft;
/*      */   }
/*      */ 
/*      */   public boolean getUserHasValidSubscription()
/*      */   {
/*  945 */     return this.m_bUserHasValidSubscription;
/*      */   }
/*      */ 
/*      */   public void setUserHasValidSubscription(boolean a_sUserHasValidSubscription) {
/*  949 */     this.m_bUserHasValidSubscription = a_sUserHasValidSubscription;
/*      */   }
/*      */ 
/*      */   public int getMaxDiscount()
/*      */   {
/*  965 */     if (!this.m_bSetDiscount)
/*      */     {
/*  968 */       int iCurrentMax = 0;
/*      */ 
/*  970 */       if ((getUser() != null) && (((ABUser)getUser()).getGroups() != null))
/*      */       {
/*  972 */         for (int i = 0; i < ((ABUser)getUser()).getGroups().size(); i++)
/*      */         {
/*  975 */           Group group = (Group)((ABUser)getUser()).getGroups().elementAt(i);
/*      */ 
/*  977 */           if (group.getDiscountPercentage() <= iCurrentMax)
/*      */             continue;
/*  979 */           iCurrentMax = group.getDiscountPercentage();
/*      */         }
/*      */ 
/*  983 */         this.m_iMaxDiscount = iCurrentMax;
/*  984 */         this.m_bSetDiscount = true;
/*      */       }
/*      */     }
/*      */ 
/*  988 */     return this.m_iMaxDiscount;
/*      */   }
/*      */ 
/*      */   public boolean getUserCanOnlyEditOwnFiles()
/*      */   {
/* 1000 */     if (getIsAdmin())
/*      */     {
/* 1002 */       return false;
/*      */     }
/*      */ 
/* 1005 */     boolean bCanOnlyEditOwnFiles = true;
/*      */ 
/* 1008 */     if ((getUser() != null) && (((ABUser)getUser()).getGroups() != null))
/*      */     {
/* 1011 */       if (((ABUser)getUser()).getGroups().size() <= 0)
/*      */       {
/* 1013 */         bCanOnlyEditOwnFiles = false;
/*      */       }
/*      */ 
/* 1017 */       for (int i = 0; i < ((ABUser)getUser()).getGroups().size(); i++)
/*      */       {
/* 1020 */         Group group = (Group)((ABUser)getUser()).getGroups().elementAt(i);
/*      */ 
/* 1022 */         if (group.getUserCanOnlyEditOwnFiles())
/*      */           continue;
/* 1024 */         bCanOnlyEditOwnFiles = false;
/* 1025 */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1030 */     return bCanOnlyEditOwnFiles;
/*      */   }
/*      */ 
/*      */   public boolean getUserCanEmailAssets()
/*      */   {
/* 1040 */     if ((getUser() != null) && (getIsLoggedIn()))
/*      */     {
/* 1043 */       if (getIsAdmin())
/*      */       {
/* 1045 */         return true;
/*      */       }
/*      */ 
/* 1049 */       if (((ABUser)getUser()).getGroups() != null)
/*      */       {
/* 1052 */         for (int i = 0; i < ((ABUser)getUser()).getGroups().size(); i++)
/*      */         {
/* 1055 */           Group group = (Group)((ABUser)getUser()).getGroups().elementAt(i);
/*      */ 
/* 1057 */           if (group.getUserCanEmailAssets())
/*      */           {
/* 1059 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */       try
/*      */       {
/* 1069 */         ABUserManager userManager = (ABUserManager)GlobalApplication.getInstance().getComponentManager().lookup("UserManager");
/* 1070 */         return userManager.getCanPublicEmailAssets();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1074 */         GlobalApplication.getInstance().getLogger().error("ABUserProfile.getUserCanEmailAssets: Error: " + e.getMessage());
/*      */       }
/*      */     }
/*      */ 
/* 1078 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean getUserCanViewLargerSize()
/*      */   {
/* 1083 */     if ((getUser() != null) && (getIsLoggedIn()))
/*      */     {
/* 1086 */       if (getIsAdmin())
/*      */       {
/* 1088 */         return true;
/*      */       }
/*      */ 
/* 1092 */       if (((ABUser)getUser()).getGroups() != null)
/*      */       {
/* 1095 */         for (int i = 0; i < ((ABUser)getUser()).getGroups().size(); i++)
/*      */         {
/* 1098 */           Group group = (Group)((ABUser)getUser()).getGroups().elementAt(i);
/*      */ 
/* 1100 */           if (group.getUsersCanViewLargerSize())
/*      */           {
/* 1102 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1111 */       ABUserManager userManager = (ABUserManager)GlobalApplication.getInstance().getComponentManager().lookup("UserManager");
/* 1112 */       return userManager.getCanPublicViewLargerSize();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1116 */       GlobalApplication.getInstance().getLogger().error("ABUserProfile.getUserCanViewLargerSize: Error: " + e.getMessage());
/*      */     }
/*      */ 
/* 1119 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean getUserCanRepurposeAssets()
/*      */   {
/* 1129 */     if (getUser() != null)
/*      */     {
/* 1132 */       if (getIsAdmin())
/*      */       {
/* 1134 */         return true;
/*      */       }
/*      */ 
/* 1138 */       if (((ABUser)getUser()).getGroups() != null)
/*      */       {
/* 1141 */         for (int i = 0; i < ((ABUser)getUser()).getGroups().size(); i++)
/*      */         {
/* 1143 */           Group group = (Group)((ABUser)getUser()).getGroups().elementAt(i);
/*      */ 
/* 1145 */           if (group.getUserCanRepurposeAssets())
/*      */           {
/* 1147 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1153 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean getUserCanSeeSourcePath()
/*      */   {
/* 1164 */     if (getUser() != null)
/*      */     {
/* 1167 */       if (getIsAdmin())
/*      */       {
/* 1169 */         return true;
/*      */       }
/*      */ 
/* 1173 */       if (((ABUser)getUser()).getGroups() != null)
/*      */       {
/* 1176 */         for (int i = 0; i < ((ABUser)getUser()).getGroups().size(); i++)
/*      */         {
/* 1179 */           Group group = (Group)((ABUser)getUser()).getGroups().elementAt(i);
/*      */ 
/* 1181 */           if (group.getUsersCanSeeSourcePath())
/*      */           {
/* 1183 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1189 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean getUserCanInviteUsers()
/*      */   {
/* 1194 */     if (getUser() != null)
/*      */     {
/* 1197 */       if (getIsAdmin())
/*      */       {
/* 1199 */         return true;
/*      */       }
/*      */ 
/* 1203 */       if (((ABUser)getUser()).getGroups() != null)
/*      */       {
/* 1206 */         for (int i = 0; i < ((ABUser)getUser()).getGroups().size(); i++)
/*      */         {
/* 1209 */           Group group = (Group)((ABUser)getUser()).getGroups().elementAt(i);
/*      */ 
/* 1211 */           if (group.getUsersCanInviteUsers())
/*      */           {
/* 1213 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1219 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean getUserCanAddEmptyAssets()
/*      */   {
/* 1230 */     if (!AssetBankSettings.getAllowEmptyAssets())
/*      */     {
/* 1232 */       return false;
/*      */     }
/*      */ 
/* 1235 */     if (getUser() != null)
/*      */     {
/* 1238 */       if (getIsAdmin())
/*      */       {
/* 1240 */         return true;
/*      */       }
/*      */ 
/* 1244 */       if (((ABUser)getUser()).getGroups() != null)
/*      */       {
/* 1247 */         for (int i = 0; i < ((ABUser)getUser()).getGroups().size(); i++)
/*      */         {
/* 1249 */           Group group = (Group)((ABUser)getUser()).getGroups().elementAt(i);
/*      */ 
/* 1251 */           if (group.getUsersCanAddEmptyAssets())
/*      */           {
/* 1253 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1259 */     return false;
/*      */   }
/*      */ 
/*      */   public BrightMoney getTotalWithDiscount()
/*      */   {
/* 1264 */     return getBasket().getTotalWithDiscount(getMaxDiscount());
/*      */   }
/*      */ 
/*      */   public BrightMoney getTotalWithDiscountPriceBands()
/*      */   {
/* 1269 */     return getBasket().getTotalWithDiscountPriceBands(getMaxDiscount());
/*      */   }
/*      */ 
/*      */   public String getPermissionsKeyForCaching()
/*      */   {
/* 1285 */     String sKey = null;
/*      */ 
/* 1288 */     if (getIsAdmin())
/*      */     {
/* 1290 */       sKey = "admin";
/*      */     }
/*      */     else
/*      */     {
/* 1295 */       if (this.m_sPermissionsKey == null)
/*      */       {
/* 1298 */         this.m_sPermissionsKey = StringUtil.convertNumbersToString(getVisiblePermissionCategories(), ":");
/*      */ 
/* 1300 */         for (int i = 0; i < getAttributeExclusions().size(); i++)
/*      */         {
/* 1303 */           this.m_sPermissionsKey += "-";
/*      */ 
/* 1306 */           AttributeValue av = (AttributeValue)getAttributeExclusions().get(i);
/* 1307 */           this.m_sPermissionsKey = (this.m_sPermissionsKey + av.getAttribute().getId() + av.getValue(getCurrentLanguage()));
/*      */         }
/*      */ 
/* 1310 */         if (getCanUpdateAssets())
/*      */         {
/* 1312 */           this.m_sPermissionsKey += "-edit";
/*      */         }
/*      */       }
/*      */ 
/* 1316 */       sKey = this.m_sPermissionsKey;
/*      */     }
/*      */ 
/* 1320 */     if (getSelectedFilters() != null)
/*      */     {
/* 1322 */       for (int i = 0; i < getSelectedFilters().size(); i++)
/*      */       {
/* 1324 */         sKey = sKey + "-";
/* 1325 */         sKey = sKey + ((Filter)getSelectedFilters().elementAt(i)).getId();
/*      */       }
/*      */     }
/*      */ 
/* 1329 */     return sKey + getCurrentLanguage().getCode();
/*      */   }
/*      */ 
/*      */   public void resetPermissionsKey()
/*      */   {
/* 1334 */     this.m_sPermissionsKey = null;
/*      */   }
/*      */ 
/*      */   public CmsInfo getCmsInfo()
/*      */   {
/* 1339 */     return this.m_cmsInfo;
/*      */   }
/*      */ 
/*      */   public void setCmsInfo(CmsInfo a_cmsInfo) {
/* 1343 */     this.m_cmsInfo = a_cmsInfo;
/*      */   }
/*      */ 
/*      */   public boolean getIsFromCms()
/*      */   {
/* 1358 */     return this.m_cmsInfo != null;
/*      */   }
/*      */ 
/*      */   public void setSelectedCategoryId(long a_lSelectedCategoryId)
/*      */   {
/* 1363 */     this.m_lSelectedCategoryId = a_lSelectedCategoryId;
/*      */   }
/*      */ 
/*      */   public long getSelectedCategoryId()
/*      */   {
/* 1368 */     return this.m_lSelectedCategoryId;
/*      */   }
/*      */ 
/*      */   public void setSelectedAccessLevelId(long a_lSelectedAccessLevelId)
/*      */   {
/* 1373 */     this.m_lSelectedAccessLevelId = a_lSelectedAccessLevelId;
/*      */   }
/*      */ 
/*      */   public long getSelectedAccessLevelId()
/*      */   {
/* 1378 */     return this.m_lSelectedAccessLevelId;
/*      */   }
/*      */ 
/*      */   public long getCategoryTypeId()
/*      */   {
/* 1383 */     return 1L;
/*      */   }
/*      */ 
/*      */   public long getAccessLevelTypeId()
/*      */   {
/* 1388 */     return 2L;
/*      */   }
/*      */ 
/*      */   public int getSelectedPage()
/*      */   {
/* 1395 */     return this.m_iSelectedPage;
/*      */   }
/*      */ 
/*      */   public void setSelectedPage(int a_iSelectedPage)
/*      */   {
/* 1401 */     this.m_iSelectedPage = a_iSelectedPage;
/*      */   }
/*      */ 
/*      */   public int getSelectedPageSize()
/*      */   {
/* 1407 */     return this.m_iSelectedPageSize;
/*      */   }
/*      */ 
/*      */   public void setSelectedPageSize(int a_iSelectedPageSize)
/*      */   {
/* 1413 */     this.m_iSelectedPageSize = a_iSelectedPageSize;
/*      */   }
/*      */ 
/*      */   public OrderSearchCriteria getOrderSearchCriteria()
/*      */   {
/* 1421 */     return this.m_orderSearchCriteria;
/*      */   }
/*      */ 
/*      */   public void setOrderSearchCriteria(OrderSearchCriteria a_sOrderSearchCriteria)
/*      */   {
/* 1429 */     this.m_orderSearchCriteria = a_sOrderSearchCriteria;
/*      */   }
/*      */ 
/*      */   public Brand getBrand()
/*      */   {
/* 1434 */     return this.m_brand;
/*      */   }
/*      */ 
/*      */   public void setBrand(Brand a_sBrand)
/*      */   {
/* 1439 */     this.m_brand = a_sBrand;
/*      */   }
/*      */ 
/*      */   public String getRegisterEmailAddress()
/*      */   {
/* 1444 */     return this.m_sRegisterEmailAddress;
/*      */   }
/*      */ 
/*      */   public void setRegisterEmailAddress(String a_sRegisterEmailAddress)
/*      */   {
/* 1449 */     this.m_sRegisterEmailAddress = a_sRegisterEmailAddress;
/*      */   }
/*      */ 
/*      */   public Vector getUsageExclusions()
/*      */   {
/* 1454 */     return this.m_vUsageExclusions;
/*      */   }
/*      */ 
/*      */   public void setUsageExclusions(Vector a_vUsageExclusions)
/*      */   {
/* 1459 */     this.m_vUsageExclusions = a_vUsageExclusions;
/*      */   }
/*      */ 
/*      */   public Vector getFilterExclusions()
/*      */   {
/* 1464 */     return this.m_vFilterExclusions;
/*      */   }
/*      */ 
/*      */   public void setFilterExclusions(Vector a_vFilterExclusions)
/*      */   {
/* 1469 */     this.m_vFilterExclusions = a_vFilterExclusions;
/*      */   }
/*      */ 
/*      */   public long getBatchImportTimestamp()
/*      */   {
/* 1474 */     return this.m_lBatchImportTimestamp;
/*      */   }
/*      */ 
/*      */   public void setBatchImportTimestamp(long a_sBatchImportTimestamp)
/*      */   {
/* 1479 */     this.m_lBatchImportTimestamp = a_sBatchImportTimestamp;
/*      */   }
/*      */ 
/*      */   public boolean getBatchImportUnsubmitted()
/*      */   {
/* 1485 */     return this.m_bBatchImportUnsubmitted;
/*      */   }
/*      */ 
/*      */   public void setBatchImportUnsubmitted(boolean a_sBatchImportUnsubmitted)
/*      */   {
/* 1490 */     this.m_bBatchImportUnsubmitted = a_sBatchImportUnsubmitted;
/*      */   }
/*      */ 
/*      */   public void setRoles(HashMap a_hmRoles)
/*      */   {
/* 1495 */     this.m_hmRoles = a_hmRoles;
/*      */   }
/*      */ 
/*      */   public HashMap getRoles()
/*      */   {
/* 1500 */     return this.m_hmRoles;
/*      */   }
/*      */ 
/*      */   public boolean getHasRole(String a_sRoleIdentifier)
/*      */   {
/* 1505 */     if (getRoles() != null)
/*      */     {
/* 1507 */       return getRoles().containsKey(a_sRoleIdentifier);
/*      */     }
/* 1509 */     return false;
/*      */   }
/*      */ 
/*      */   public Vector<Filter> getSelectedFilters()
/*      */   {
/* 1514 */     return this.m_vecSelectedFilters;
/*      */   }
/*      */ 
/*      */   public Filter getFirstSelectedFilter()
/*      */   {
/* 1519 */     if (this.m_vecSelectedFilters != null)
/*      */     {
/* 1521 */       return (Filter)this.m_vecSelectedFilters.firstElement();
/*      */     }
/* 1523 */     return null;
/*      */   }
/*      */ 
/*      */   public void setSelectedFilters(Vector a_vecSelectedFilters)
/*      */   {
/* 1528 */     this.m_vecSelectedFilters = a_vecSelectedFilters;
/*      */   }
/*      */ 
/*      */   public void addSelectedFilter(Filter a_filter)
/*      */   {
/* 1533 */     getSelectedFilters().add(a_filter);
/*      */   }
/*      */ 
/*      */   public void clearSelectedFilters()
/*      */   {
/* 1538 */     getSelectedFilters().clear();
/*      */   }
/*      */ 
/*      */   public int getSelectedFilterCount()
/*      */   {
/* 1543 */     if (getSelectedFilters() != null)
/*      */     {
/* 1545 */       return getSelectedFilters().size();
/*      */     }
/* 1547 */     return 0;
/*      */   }
/*      */ 
/*      */   public boolean getIsSelectedFilter(String a_sFilterId)
/*      */   {
/* 1552 */     if ((getSelectedFilters() != null) && (getSelectedFilters().size() > 0))
/*      */     {
/* 1554 */       long lFilterId = Long.parseLong(a_sFilterId);
/*      */ 
/* 1556 */       for (int i = 0; i < getSelectedFilters().size(); i++)
/*      */       {
/* 1558 */         Filter filter = (Filter)getSelectedFilters().elementAt(i);
/* 1559 */         if (filter.getId() == lFilterId)
/*      */         {
/* 1561 */           return true;
/*      */         }
/*      */       }
/*      */     }
/* 1565 */     return false;
/*      */   }
/*      */ 
/*      */   public void setHaveSetDefaultFilter(boolean a_bHaveSetDefaultFilter)
/*      */   {
/* 1570 */     this.m_bHaveSetDefaultFilter = a_bHaveSetDefaultFilter;
/*      */   }
/*      */ 
/*      */   public boolean getHaveSetDefaultFilter()
/*      */   {
/* 1575 */     return this.m_bHaveSetDefaultFilter;
/*      */   }
/*      */ 
/*      */   public boolean isIpPermitted()
/*      */   {
/* 1580 */     return this.m_bIpPermitted;
/*      */   }
/*      */ 
/*      */   public void setIpPermitted(boolean a_sDoneIPCheck)
/*      */   {
/* 1585 */     this.m_bIpPermitted = a_sDoneIPCheck;
/*      */   }
/*      */ 
/*      */   public void setCurrentLanguage(Language a_currentLanguage)
/*      */   {
/* 1590 */     if (getSelectedFilters() != null)
/*      */     {
/* 1592 */       for (int i = 0; i < getSelectedFilters().size(); i++)
/*      */       {
/* 1595 */         ((Filter)getSelectedFilters().elementAt(i)).setLanguage(a_currentLanguage);
/*      */       }
/*      */     }
/* 1598 */     if (this.m_vAttributeExclusions != null)
/*      */     {
/* 1600 */       LanguageUtils.setLanguageOnAll(this.m_vAttributeExclusions, a_currentLanguage);
/*      */     }
/* 1602 */     super.setCurrentLanguage(a_currentLanguage);
/*      */   }
/*      */ 
/*      */   public void setDownloadId(long a_lDownloadId)
/*      */   {
/* 1607 */     this.m_lDownloadId = a_lDownloadId;
/*      */   }
/*      */ 
/*      */   public long getDownloadId()
/*      */   {
/* 1612 */     return this.m_lDownloadId;
/*      */   }
/*      */ 
/*      */   public List getRecentSearches()
/*      */   {
/* 1617 */     return this.m_recentSearches;
/*      */   }
/*      */ 
/*      */   public void addRecentSearch(SearchQuery a_query, String a_sHttpQueryString)
/*      */   {
/* 1622 */     if (this.m_recentSearches == null)
/*      */     {
/* 1624 */       this.m_recentSearches = new LinkedList();
/*      */     }
/*      */ 
/* 1628 */     if (a_sHttpQueryString != null)
/*      */     {
/* 1630 */       a_sHttpQueryString = a_sHttpQueryString.replace("&searchPage=savedSearches", "");
/* 1631 */       a_sHttpQueryString = a_sHttpQueryString.replaceAll("&sortAttributeId=[0-9]*", "");
/* 1632 */       a_sHttpQueryString = a_sHttpQueryString.replaceAll("&sortDescending=((true)|(false))?", "");
/*      */     }
/*      */ 
/* 1635 */     RecentSearch search = new RecentSearch();
/* 1636 */     search.setKeywords(a_query.getQueryDescription());
/* 1637 */     search.setQueryString(a_sHttpQueryString);
/* 1638 */     search.setTimeLastRun(new Date());
/* 1639 */     search.setBuilderSearch(getSearchCriteria() instanceof SearchBuilderQuery);
/* 1640 */     search.setDescending(a_query.isSortDescending());
/* 1641 */     search.setSortAttributeId(a_query.getSortAttributeId());
/*      */ 
/* 1643 */     if (!this.m_recentSearches.contains(search))
/*      */     {
/* 1646 */       if (this.m_recentSearches.size() >= AssetBankSettings.getMaxRecentSearches())
/*      */       {
/* 1648 */         this.m_recentSearches.removeLast();
/*      */       }
/*      */ 
/* 1652 */       this.m_recentSearches.addFirst(search);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateCurrentSearchSortAttribute()
/*      */   {
/* 1661 */     if (this.m_recentSearches != null)
/*      */     {
/* 1663 */       SavedSearch search = (SavedSearch)this.m_recentSearches.getFirst();
/* 1664 */       search.setSortAttributeId(this.m_searchQuery.getSortAttributeId());
/* 1665 */       search.setDescending(this.m_searchQuery.isSortDescending());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setLastUserSearch(Vector a_vecLastUserSearch)
/*      */   {
/* 1671 */     this.m_vecLastUserSearch = a_vecLastUserSearch;
/*      */   }
/*      */ 
/*      */   public Vector getLastUserSearch()
/*      */   {
/* 1676 */     return this.m_vecLastUserSearch;
/*      */   }
/*      */ 
/*      */   public boolean getClickedOnSensitiveImage(int a_iId)
/*      */   {
/* 1681 */     return getClickedSensitiveImageIds().contains(Long.valueOf(a_iId));
/*      */   }
/*      */ 
/*      */   public HashSet getClickedSensitiveImageIds()
/*      */   {
/* 1686 */     if (this.m_hsClickedSensitiveImageIds == null)
/*      */     {
/* 1688 */       this.m_hsClickedSensitiveImageIds = new HashSet();
/*      */     }
/* 1690 */     return this.m_hsClickedSensitiveImageIds;
/*      */   }
/*      */ 
/*      */   public String getSingleUploadDir()
/*      */   {
/* 1695 */     String sSingleUploadDir = "";
/*      */ 
/* 1698 */     if (FrameworkSettings.getUseRelativeDirectories())
/*      */     {
/* 1700 */       sSingleUploadDir = AssetBankSettings.getApplicationPath() + "/";
/*      */     }
/*      */ 
/* 1703 */     sSingleUploadDir = sSingleUploadDir + AssetBankSettings.getSingleUploadDirectory();
/*      */ 
/* 1705 */     sSingleUploadDir = sSingleUploadDir + "/" + FileUtil.getSafeFilename(getUser().getUsername(), true);
/*      */ 
/* 1707 */     return sSingleUploadDir;
/*      */   }
/*      */ 
/*      */   public int getMaxGroupDownloadWidth()
/*      */   {
/* 1712 */     int iMaxDownloadWidth = -1;
/* 1713 */     if (getUser() != null)
/*      */     {
/* 1715 */       Vector vecGroups = ((ABUser)getUser()).getGroups();
/* 1716 */       if (vecGroups != null)
/*      */       {
/* 1718 */         for (int i = 0; i < vecGroups.size(); i++)
/*      */         {
/* 1720 */           Group group = (Group)vecGroups.elementAt(i);
/* 1721 */           if (group.getMaxDownloadWidth() <= iMaxDownloadWidth)
/*      */             continue;
/* 1723 */           iMaxDownloadWidth = group.getMaxDownloadWidth();
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1728 */     return iMaxDownloadWidth;
/*      */   }
/*      */ 
/*      */   public int getMaxGroupDownloadHeight()
/*      */   {
/* 1733 */     int iMaxDownloadHeight = -1;
/* 1734 */     if (getUser() != null)
/*      */     {
/* 1736 */       Vector vecGroups = ((ABUser)getUser()).getGroups();
/* 1737 */       if (vecGroups != null)
/*      */       {
/* 1739 */         for (int i = 0; i < vecGroups.size(); i++)
/*      */         {
/* 1741 */           Group group = (Group)vecGroups.elementAt(i);
/* 1742 */           if (group.getMaxDownloadHeight() <= iMaxDownloadHeight)
/*      */             continue;
/* 1744 */           iMaxDownloadHeight = group.getMaxDownloadHeight();
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1749 */     return iMaxDownloadHeight;
/*      */   }
/*      */ 
/*      */   public int getSearchResultsPerPage()
/*      */   {
/* 1754 */     return this.m_iSearchResultsPerPage;
/*      */   }
/*      */ 
/*      */   public void setSearchResultsPerPage(int a_iSearchResultsPerPage)
/*      */   {
/* 1759 */     this.m_iSearchResultsPerPage = a_iSearchResultsPerPage;
/*      */   }
/*      */ 
/*      */   public String getUserOS()
/*      */   {
/* 1764 */     return this.m_sUserOS;
/*      */   }
/*      */ 
/*      */   public boolean getUserOsIsWindows()
/*      */   {
/* 1769 */     return (getUserOS() != null) && (getUserOS().indexOf("Windows") >= 0);
/*      */   }
/*      */ 
/*      */   public void setUserOS(String a_sUserOS)
/*      */   {
/* 1774 */     this.m_sUserOS = a_sUserOS;
/*      */   }
/*      */ 
/*      */   public Set<Long> getRootDescriptiveCategoryIds()
/*      */   {
/* 1779 */     if (this.m_rootDescriptiveCategoryIds == null)
/*      */     {
/* 1781 */       this.m_rootDescriptiveCategoryIds = new HashSet();
/*      */     }
/* 1783 */     return this.m_rootDescriptiveCategoryIds;
/*      */   }
/*      */ 
/*      */   public void setRootDescriptiveCategoryIds(Set<Long> a_descriptiveCategoryIds)
/*      */   {
/* 1788 */     this.m_rootDescriptiveCategoryIds = a_descriptiveCategoryIds;
/*      */   }
/*      */ 
/*      */   public boolean getHasRootCategoriesSet()
/*      */   {
/* 1793 */     return (AssetBankSettings.getOrgUnitUse()) && (!getIsAdmin()) && (getRootDescriptiveCategoryIds().size() > 0);
/*      */   }
/*      */ 
/*      */   public long getCategoryIdForLogo()
/*      */   {
/* 1798 */     return this.m_lCategoryIdForLogo;
/*      */   }
/*      */ 
/*      */   public void setCategoryIdForLogo(long a_lCategoryIdForLogo)
/*      */   {
/* 1803 */     this.m_lCategoryIdForLogo = a_lCategoryIdForLogo;
/*      */   }
/*      */ 
/*      */   public long getAccessLevelIdForLogo()
/*      */   {
/* 1808 */     return this.m_lAccessLevelIdForLogo;
/*      */   }
/*      */ 
/*      */   public void setAccessLevelIdForLogo(long a_lAccessLevelIdForLogo)
/*      */   {
/* 1813 */     this.m_lAccessLevelIdForLogo = a_lAccessLevelIdForLogo;
/*      */   }
/*      */ 
/*      */   public void setLargeImagesOnView(boolean a_bLargeImagesOnView)
/*      */   {
/* 1818 */     this.m_bLargeImagesOnView = a_bLargeImagesOnView;
/*      */   }
/*      */ 
/*      */   public boolean getLargeImagesOnView()
/*      */   {
/* 1823 */     return this.m_bLargeImagesOnView;
/*      */   }
/*      */ 
/*      */   public void setListView(boolean a_bListView)
/*      */   {
/* 1828 */     this.m_bListView = a_bListView;
/*      */   }
/*      */ 
/*      */   public boolean getListView()
/*      */   {
/* 1840 */     return this.m_bListView;
/*      */   }
/*      */ 
/*      */   public boolean hasAdvancedViewing()
/*      */   {
/* 1845 */     if ((getUser() != null) && (getUser().getGroups() != null))
/*      */     {
                  Vector<Group> grps = getUser().getGroups();
/* 1848 */       for (Group group :grps )
/*      */       {
/* 1850 */         if (group.getAdvancedViewing())
/*      */         {
/* 1852 */           return true;
/*      */         }
/*      */       }
/*      */     }
/* 1856 */     return false;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.bean.ABUserProfile
 * JD-Core Version:    0.6.0
 */