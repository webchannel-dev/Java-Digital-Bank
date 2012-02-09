/*      */ package com.bright.assetbank.user.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.GlobalApplication;
/*      */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*      */ import com.bright.assetbank.application.service.AssetLogManager;
/*      */ import com.bright.assetbank.assetbox.bean.AssetBasket;
/*      */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*      */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.bean.AttributeType;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue.Translation;
/*      */ import com.bright.assetbank.attribute.constant.AttributeStorageType;
/*      */ import com.bright.assetbank.attribute.filter.bean.Filter;
/*      */ import com.bright.assetbank.attribute.service.AttributeManager;
/*      */ import com.bright.assetbank.category.service.CategoryCountCacheManager;
/*      */ import com.bright.assetbank.category.util.CategoryUtil;
/*      */ import com.bright.assetbank.customfield.service.CustomFieldManager;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.ecommerce.service.OrderManager;
/*      */ import com.bright.assetbank.feedback.service.AssetFeedbackManager;
/*      */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*      */ import com.bright.assetbank.orgunit.bean.OrgUnitMetadata;
/*      */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*      */ import com.bright.assetbank.subscription.service.SubscriptionManager;
/*      */ import com.bright.assetbank.usage.bean.UsageType;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.assetbank.user.bean.ABUserProfile;
/*      */ import com.bright.assetbank.user.bean.Brand;
/*      */ import com.bright.assetbank.user.bean.Group;
/*      */ import com.bright.assetbank.user.bean.GroupAttributeExclusion;
/*      */ import com.bright.assetbank.user.bean.GroupAttributeVisibility;
/*      */ import com.bright.assetbank.user.bean.GroupCategoryPermission;
/*      */ import com.bright.assetbank.user.bean.GroupExclusion;
/*      */ import com.bright.assetbank.user.bean.UserSearchCriteria;
/*      */ import com.bright.assetbank.user.constant.UserConstants;
/*      */ import com.bright.framework.category.bean.Category;
/*      */ import com.bright.framework.category.bean.CategoryImpl;
/*      */ import com.bright.framework.common.bean.Address;
/*      */ import com.bright.framework.common.bean.RefDataItem;
/*      */ import com.bright.framework.common.bean.StringDataBean;
/*      */ import com.bright.framework.common.service.AddressManager;
/*      */ import com.bright.framework.common.service.ScheduleManager;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.exception.SQLStatementException;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.ApplicationSql;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.bean.LanguageAwareComponent;
/*      */ import com.bright.framework.language.constant.LanguageConstants;
/*      */ import com.bright.framework.language.util.LanguageUtils;
/*      */ import com.bright.framework.mail.service.EmailManager;
/*      */ import com.bright.framework.search.bean.SearchResults;
/*      */ import com.bright.framework.simplelist.bean.ListItem;
/*      */ import com.bright.framework.simplelist.service.ListManager;
/*      */ import com.bright.framework.user.bean.User;
/*      */ import com.bright.framework.user.bean.UserProfile;
/*      */ import com.bright.framework.user.exception.AccountExpiredException;
/*      */ import com.bright.framework.user.exception.LoginException;
/*      */ import com.bright.framework.user.exception.UsernameInUseException;
/*      */ import com.bright.framework.user.service.UserManager;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.io.File;
/*      */ import java.io.FileFilter;
/*      */ import java.io.IOException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TimerTask;
/*      */ import java.util.Vector;
/*      */ import org.apache.avalon.excalibur.datasource.DataSourceComponent;
/*      */ import org.apache.commons.io.FileUtils;
/*      */ import org.apache.commons.lang.ArrayUtils;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class ABUserManager extends UserManager
/*      */   implements UserConstants, AssetBankConstants, LanguageAwareComponent
/*      */ {
/*      */   private static final String c_ksClassName = "ABUserManager";
/*      */   private static final String c_ksUserSql = "SELECT u.Id userId, u.Password, u.Surname, u.Forename, u.Username, u.EmailAddress, u.Title, u.Organisation, u.Address, u.RegistrationInfo, u.IsAdmin, u.IsSuspended, u.DisplayName, u.CN, u.DistinguishedName, u.Mailbox, u.NotActiveDirectory, u.Hidden, u.IsDeleted, u.NotApproved, u.RequiresUpdate, u.RequestedOrgUnitId, u.CanBeCategoryAdmin, u.ExpiryDate, u.RegisterDate, u.JobTitle, u.VATNumber, u.LDAPServerIndex, u.ReceiveAlerts, u.CanLoginFromCms, u.AdminNotes, u.LastModifiedById, u.InvitedByUserId, u.AdditionalApprovalName, u.SearchResultsPerPage, u.ReactivationPending, ug.Id ugId, ug.Name, ug.Description, ug.IsDefaultGroup, ug.Mapping, ug.DiscountPercentage, ug.CanOnlyEditOwn, ug.CanEmailAssets, ug.CanRepurposeAssets, ug.CanSeeSourcePath, ug.MaxDownloadHeight, ug.MaxDownloadWidth, ug.CanInviteUsers, ug.CanAddEmptyAssets, ug.CanViewLargerSize, ug.AutomaticBrandRegister, ug.AdvancedViewing, groupou.Id groupouId, groupou.DiskQuotaMb groupouDiskQuotaMb, cat.name groupouName, ou.Id ouId, ou.DiskQuotaMb ouDiskQuotaMb, oucat.name ouName, divis.Id divId, divis.Name divName, addr.Id addrId, br.Id brId, br.Name brName, l.Id lId, l.Code lCode, l.Name lName, l.IsDefault lIsDefault, l.IsRightToLeft lIsRightToLeft, l.UsesLatinAlphabet lUsesLatinAlphabet FROM AssetBankUser u  LEFT JOIN UserInGroup uig ON uig.UserId = u.Id LEFT JOIN UserGroup ug ON uig.UserGroupId = ug.Id LEFT JOIN OrgUnitGroup oug ON oug.UserGroupId = ug.Id LEFT JOIN OrgUnit groupou ON oug.OrgUnitId = groupou.Id LEFT JOIN CM_Category cat ON groupou.RootOrgUnitCategoryId = cat.Id LEFT JOIN Brand br ON ug.BrandId = br.Id LEFT JOIN OrgUnit ou ON ou.AdminGroupId = ug.Id LEFT JOIN CM_Category oucat ON ou.RootOrgUnitCategoryId = oucat.Id LEFT JOIN Division divis ON divis.Id = u.DivisionId LEFT JOIN Address addr ON addr.Id = u.AddressId LEFT JOIN Language l ON l.Id = u.LanguageId ";
/*  328 */   private boolean m_bEmptyAssetsSwitchSet = false;
/*  329 */   private boolean m_bEmptyAssetsSwitch = false;
/*  330 */   private Object m_objEmptyAssetsSwitchLock = new Object();
/*      */ 
/*  332 */   protected AssetBoxManager m_assetBoxManager = null;
/*      */ 
/*  334 */   private AddressManager m_addressManager = null;
/*      */ 
/*  340 */   private SubscriptionManager m_subscriptionManager = null;
/*      */ 
/*  346 */   private CategoryCountCacheManager m_categoryCountCacheManager = null;
/*      */ 
/*  352 */   private OrderManager m_orderManager = null;
/*      */ 
/*  358 */   private ListManager m_listManager = null;
/*      */ 
/*  364 */   private AttributeManager m_attributeManager = null;
/*      */ 
/*  370 */   private RoleManager m_roleManager = null;
/*      */ 
/*  376 */   private OrgUnitManager m_orgUnitManager = null;
/*      */ 
/*  382 */   private AssetFeedbackManager m_feedbackManager = null;
/*      */ 
/*  388 */   private AssetCategoryManager m_categoryManager = null;
/*      */ 
/*  394 */   private CustomFieldManager m_fieldManager = null;
/*      */ 
/*      */   public void setAddressManager(AddressManager a_sAddressManager)
/*      */   {
/*  337 */     this.m_addressManager = a_sAddressManager;
/*      */   }
/*      */ 
/*      */   public void setSubscriptionManager(SubscriptionManager a_subscriptionManager)
/*      */   {
/*  343 */     this.m_subscriptionManager = a_subscriptionManager;
/*      */   }
/*      */ 
/*      */   public void setCategoryCountCacheManager(CategoryCountCacheManager a_sCategoryCountCacheManager)
/*      */   {
/*  349 */     this.m_categoryCountCacheManager = a_sCategoryCountCacheManager;
/*      */   }
/*      */ 
/*      */   public void setOrderManager(OrderManager a_sManager)
/*      */   {
/*  355 */     this.m_orderManager = a_sManager;
/*      */   }
/*      */ 
/*      */   public void setListManager(ListManager a_sManager)
/*      */   {
/*  361 */     this.m_listManager = a_sManager;
/*      */   }
/*      */ 
/*      */   public void setAttributeManager(AttributeManager a_sManager)
/*      */   {
/*  367 */     this.m_attributeManager = a_sManager;
/*      */   }
/*      */ 
/*      */   public void setRoleManager(RoleManager a_roleManager)
/*      */   {
/*  373 */     this.m_roleManager = a_roleManager;
/*      */   }
/*      */ 
/*      */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*      */   {
/*  379 */     this.m_orgUnitManager = a_orgUnitManager;
/*      */   }
/*      */ 
/*      */   public void setAssetFeedbackManager(AssetFeedbackManager a_feedbackManager)
/*      */   {
/*  385 */     this.m_feedbackManager = a_feedbackManager;
/*      */   }
/*      */ 
/*      */   public void setCategoryManager(AssetCategoryManager a_categoryManager)
/*      */   {
/*  391 */     this.m_categoryManager = a_categoryManager;
/*      */   }
/*      */ 
/*      */   public void setCustomFieldManager(CustomFieldManager a_fieldManager)
/*      */   {
/*  397 */     this.m_fieldManager = a_fieldManager;
/*      */   }
/*      */ 
/*      */   public void startup() throws Bn2Exception
/*      */   {
/*  402 */     super.startup();
/*      */ 
/*  405 */     if (AssetBankSettings.getVerifyUsersInactiveFor() > 0)
/*      */     {
/*  407 */       TimerTask task = new TimerTask()
/*      */       {
/*      */         public void run()
/*      */         {
/*      */           try
/*      */           {
/*  413 */             ABUserManager.this.expireAndDeleteUsers();
/*      */           }
/*      */           catch (Bn2Exception be)
/*      */           {
/*      */           }
/*      */         }
/*      */       };
/*  422 */       this.m_scheduleManager.schedule(task, 0L, 86400000L, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   public User createUser()
/*      */   {
/*  431 */     ABUser user = new ABUser();
/*  432 */     user.setLanguage((Language)LanguageConstants.k_defaultLanguage.clone());
/*  433 */     user.setNotApproved(true);
/*  434 */     return new ABUser();
/*      */   }
/*      */ 
/*      */   protected void checkUserValidForLogin(User a_user)
/*      */     throws LoginException, Bn2Exception
/*      */   {
/*  457 */     ABUser abUser = (ABUser)a_user;
/*      */ 
/*  460 */     if (abUser.getIsDeleted())
/*      */     {
/*  462 */       throw new LoginException("This user has been deleted");
/*      */     }
/*      */ 
/*  466 */     if (abUser.getNotApproved())
/*      */     {
/*  468 */       throw new LoginException("This user has not been approved");
/*      */     }
/*      */ 
/*  472 */     if (abUser.getHidden())
/*      */     {
/*  474 */       throw new LoginException("This user is a disabled active directory user");
/*      */     }
/*      */ 
/*  477 */     if ((abUser.getExpiryDate() != null) && (abUser.getExpiryDate().before(new java.util.Date())))
/*      */     {
/*  480 */       throw new AccountExpiredException("The user's account has expired");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void processPostLogin(DBTransaction a_dbTransaction, UserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/*  512 */     ABUser user = (ABUser)getUser(a_dbTransaction, a_userProfile.getUser().getId());
/*      */ 
/*  514 */     ABUserProfile userProfile = (ABUserProfile)a_userProfile;
/*      */ 
/*  517 */     userProfile.setUser(user);
/*      */ 
/*  520 */     Vector vGroupIds = new Vector();
/*      */ 
/*  522 */     for (int i = 0; (user.getGroups() != null) && (i < user.getGroups().size()); i++)
/*      */     {
/*  524 */       vGroupIds.add(new Long(((Group)user.getGroups().get(i)).getId()));
/*      */     }
/*      */ 
/*  528 */     userProfile.setGroupIds(vGroupIds);
/*      */ 
/*  531 */     userProfile.setUsageExclusions(null);
/*  532 */     if (!userProfile.getIsAdmin())
/*      */     {
/*  534 */       userProfile.setUsageExclusions(getUsageExclusionsForUser(a_dbTransaction, user.getId()));
/*      */     }
/*      */ 
/*  538 */     userProfile.setFilterExclusions(null);
/*  539 */     if (!userProfile.getIsAdmin())
/*      */     {
/*  541 */       userProfile.setFilterExclusions(getFilterExclusionsForUser(a_dbTransaction, user.getId()));
/*      */     }
/*      */ 
/*  545 */     if (!userProfile.getIsAdmin())
/*      */     {
/*  547 */       Vector vAttExclusions = getAttributeExclusionsForUser(null, user.getId());
/*  548 */       LanguageUtils.setLanguageOnAll(vAttExclusions, userProfile.getCurrentLanguage());
/*  549 */       userProfile.setAttributeExclusions(vAttExclusions);
/*      */     }
/*      */     else
/*      */     {
/*  553 */       userProfile.setAttributeExclusions(null);
/*      */     }
/*      */ 
/*  557 */     if (!userProfile.getIsAdmin())
/*      */     {
/*  559 */       userProfile.setVisibleAttributeIds(getAttributeIdsForUser(null, user.getId(), false));
/*      */ 
/*  562 */       userProfile.setWriteableAttributeIds(getAttributeIdsForUser(null, user.getId(), true));
/*      */     }
/*      */     else
/*      */     {
/*  566 */       userProfile.setVisibleAttributeIds(null);
/*  567 */       userProfile.setWriteableAttributeIds(null);
/*      */     }
/*      */ 
/*  571 */     Connection cCon = null;
/*  572 */     if (a_dbTransaction != null)
/*      */     {
/*  574 */       cCon = a_dbTransaction.getConnection();
/*      */     }
/*  576 */     setCategoryPermissions(cCon, userProfile, user.getId());
/*      */ 
/*  579 */     if ((AssetBankSettings.getOrgUnitUse()) && (!user.getIsAdmin()))
/*      */     {
/*  581 */       Vector<OrgUnit> vOrgUnits = this.m_orgUnitManager.getOrgUnitsForUser(a_dbTransaction, user.getId());
/*      */ 
/*  583 */       if (vOrgUnits != null)
/*      */       {
/*  585 */         Set<Category>candidateCats = new HashSet();
/*  586 */         long lLastBranchRootCatId = 0L;
/*  587 */         boolean bAddWholeBranches = false;
/*  588 */         for (OrgUnit orgUnit : vOrgUnits)
/*      */         {
/*  590 */           Category orgRootCat = orgUnit.getRootDescriptiveCategory();
/*      */ 
/*  592 */           if (orgRootCat == null)
/*      */           {
/*  594 */             userProfile.getRootDescriptiveCategoryIds().clear();
/*  595 */             break;
/*      */           }
/*      */ 
/*  599 */           Category catRootAncestor = orgRootCat.getRootAncestor();
/*  600 */           if (catRootAncestor == null)
/*      */           {
/*  602 */             catRootAncestor = orgRootCat;
/*      */           }
/*      */ 
/*  605 */           if ((lLastBranchRootCatId > 0L) && (lLastBranchRootCatId != catRootAncestor.getId()))
/*      */           {
/*  607 */             bAddWholeBranches = true;
/*      */           }
/*      */           else
/*      */           {
/*  611 */             lLastBranchRootCatId = catRootAncestor.getId();
/*      */           }
/*      */ 
/*  615 */           candidateCats.add(orgRootCat);
/*      */         }
/*      */ 
/*  620 */         Set<Category> catsWeWant = new HashSet();
/*      */ 
/*  622 */         for (Category candidateCat : candidateCats)
/*      */         {
/*  625 */           boolean bWantThis = true;
/*  626 */           for (Category checkCatWeWant : catsWeWant)
/*      */           {
/*  629 */             if (ArrayUtils.contains(checkCatWeWant.getAllDescendantsIds(), candidateCat.getId()))
/*      */             {
/*  631 */               bWantThis = false;
/*  632 */               break;
/*      */             }
/*      */ 
/*  635 */             if (ArrayUtils.contains(checkCatWeWant.getAllAncestorIds(), candidateCat.getId()))
/*      */             {
/*  637 */               catsWeWant.remove(checkCatWeWant);
/*  638 */               break;
/*      */             }
/*      */           }
/*      */ 
/*  642 */           if (bWantThis)
/*      */           {
/*  644 */             Category catToAdd = null;
/*      */ 
/*  646 */             if (bAddWholeBranches)
/*      */             {
/*  648 */               catToAdd = candidateCat.getRootAncestor();
/*      */             }
/*      */ 
/*  651 */             if (catToAdd == null)
/*      */             {
/*  653 */               catToAdd = candidateCat;
/*      */             }
/*      */ 
/*  656 */             catsWeWant.add(catToAdd);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  662 */         for (Category cat : catsWeWant)
/*      */         {
/*  664 */           userProfile.getRootDescriptiveCategoryIds().add(Long.valueOf(cat.getId()));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  672 */     AssetBox assetBox = userProfile.getAssetBox();
/*  673 */     if (((assetBox instanceof AssetBasket)) && (assetBox.getNumAssets() > 0))
/*      */     {
/*  675 */       this.m_assetBoxManager.synchroniseAssetBox(a_dbTransaction, userProfile, user.getId(), 0L);
/*      */     }
/*      */ 
/*  679 */     this.m_assetBoxManager.refreshAssetBoxesInProfile(a_dbTransaction, (ABUserProfile)a_userProfile);
/*      */ 
/*  682 */     if (AssetBankSettings.getSubscription())
/*      */     {
/*  684 */       long lRemainingDownloads = this.m_subscriptionManager.getRemainingDownloadsTodayForUser(a_dbTransaction, user.getId());
/*  685 */       userProfile.setDownloadsLeft(lRemainingDownloads);
/*      */ 
/*  687 */       boolean bHasValidSubscription = this.m_subscriptionManager.getUserHasValidSubscription(a_dbTransaction, user.getId());
/*  688 */       userProfile.setUserHasValidSubscription(bHasValidSubscription);
/*      */     }
/*      */ 
/*  692 */     if (AssetBankSettings.getUseGroupRoles())
/*      */     {
/*  694 */       userProfile.setRoles(this.m_roleManager.getRolesForUserByIdentifier(a_dbTransaction, user.getId()));
/*      */     }
/*      */ 
/*  698 */     if ((AssetBankSettings.getUseBrands()) && (!user.getIsAdmin()))
/*      */     {
/*  701 */       Iterator itGroups = user.getGroups().iterator();
/*      */ 
/*  703 */       int iNumBrandGroups = 0;
/*  704 */       long lSelectedBrandId = 0L;
/*      */ 
/*  706 */       while (itGroups.hasNext())
/*      */       {
/*  708 */         Group g = (Group)itGroups.next();
/*      */ 
/*  710 */         long lBrandId = g.getBrandReference().getId();
/*  711 */         if (lBrandId > 0L)
/*      */         {
/*  713 */           lSelectedBrandId = lBrandId;
/*  714 */           iNumBrandGroups++;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  719 */       if (iNumBrandGroups > 1)
/*      */       {
/*  721 */         lSelectedBrandId = 0L;
/*      */       }
/*      */ 
/*  725 */       if (lSelectedBrandId > 0L)
/*      */       {
/*  727 */         Brand brand = getBrandById(a_dbTransaction, lSelectedBrandId);
/*  728 */         userProfile.setBrand(brand);
/*      */       }
/*      */     }
/*      */ 
/*  732 */     userProfile.setSearchResultsPerPage(user.getSearchResultsPerPage());
/*      */ 
/*  735 */     if (AssetBankSettings.getAuditLogEnabled())
/*      */     {
/*  737 */       this.m_assetLogManager.saveLoginLog(a_dbTransaction, a_userProfile.getUser().getId(), userProfile.getSessionId(), new java.util.Date());
/*      */     }
/*      */ 
/*  741 */     updateDateLastLoggedIn(a_dbTransaction, a_userProfile.getUser().getId());
/*      */ 
/*  743 */     clearSingleUploadDir(userProfile);
/*      */ 
/*  746 */     if ((AssetBankSettings.getCategoryImageUsedForLogo()) && (!userProfile.getIsAdmin()))
/*      */     {
/*  748 */       this.m_logger.debug("Processing category image for logo");
/*      */ 
/*  750 */       long lCategoryType = 2L;
/*      */ 
/*  752 */       Vector vecCategories = this.m_categoryManager.getCategories(a_dbTransaction, lCategoryType, -1L, userProfile.getCurrentLanguage());
/*      */ 
/*  761 */       Set fullIds = userProfile.getPermissionCategoryIds(1);
/*      */ 
/*  764 */       vecCategories = CategoryUtil.filterCategoryVectorByCategoryIds(fullIds, vecCategories, true);
/*      */ 
/*  767 */       Category lowestLevelCat = null;
/*      */ 
/*  770 */       Iterator itCategories = vecCategories.iterator();
/*  771 */       while (itCategories.hasNext())
/*      */       {
/*  773 */         this.m_logger.debug("Categories are:" + ((Category)itCategories.next()).getName());
/*      */       }
/*      */ 
/*  776 */       while (vecCategories.size() == 1)
/*      */       {
/*  778 */         lowestLevelCat = (Category)vecCategories.firstElement();
/*      */ 
/*  780 */         vecCategories = this.m_categoryManager.getCategories(a_dbTransaction, lowestLevelCat.getCategoryTypeId(), lowestLevelCat.getId(), userProfile.getCurrentLanguage());
/*      */ 
/*  785 */         vecCategories = CategoryUtil.filterCategoryVectorByCategoryIds(fullIds, vecCategories, true);
/*      */       }
/*      */ 
/*  789 */       if (lowestLevelCat != null)
/*      */       {
/*  791 */         this.m_logger.debug("Lowest level cat found:" + lowestLevelCat.getName());
/*      */       }
/*      */       else
/*      */       {
/*  795 */         this.m_logger.debug("Lowest level cat is null");
/*      */       }
/*      */ 
/*  799 */       if ((lowestLevelCat != null) && (StringUtil.stringIsPopulated(lowestLevelCat.getImageUrl())))
/*      */       {
/*  801 */         this.m_logger.debug("Setting ID for logo:" + lowestLevelCat.getId());
/*      */ 
/*  803 */         if (lCategoryType == 1L)
/*      */         {
/*  805 */           userProfile.setCategoryIdForLogo(lowestLevelCat.getId());
/*      */         }
/*      */         else
/*      */         {
/*  809 */           userProfile.setAccessLevelIdForLogo(lowestLevelCat.getId());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void clearSingleUploadDir(ABUserProfile a_userProfile)
/*      */   {
/*  821 */     String sSingleUploadDir = a_userProfile.getSingleUploadDir();
/*      */ 
/*  823 */     File dir = new File(sSingleUploadDir);
/*      */ 
/*  825 */     if (dir.exists())
/*      */     {
/*  827 */       FileFilter filter = new FileFilter()
/*      */       {
/*      */         public boolean accept(File file)
/*      */         {
/*  831 */           return (file.isDirectory()) && (System.currentTimeMillis() - file.lastModified() > 172800000L);
/*      */         }
/*      */       };
/*  835 */       File[] files = new File(sSingleUploadDir).listFiles(filter);
/*      */ 
/*  837 */       for (int i = 0; i < files.length; i++)
/*      */       {
/*      */         try
/*      */         {
/*  841 */           FileUtils.forceDelete(files[i]);
/*      */         }
/*      */         catch (IOException e)
/*      */         {
/*  845 */           GlobalApplication.getInstance().getLogger().error("ABUserManager.clearSingleUploadDir() : Could not delete old single upload directory : " + e.getMessage(), e);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setCategoryPermissions(Connection a_cCon, ABUserProfile a_userProfile, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/*  865 */     setCategoryPermissions(a_cCon, a_userProfile, a_lUserId, null, null);
/*      */   }
/*      */ 
/*      */   public void setCategoryPermissions(Connection a_cCon, ABUserProfile a_userProfile, String a_sIP, String a_sURL)
/*      */     throws Bn2Exception
/*      */   {
/*  884 */     setCategoryPermissions(a_cCon, a_userProfile, 0L, a_sIP, a_sURL);
/*      */   }
/*      */ 
/*      */   private void setCategoryPermissions(Connection a_cCon, ABUserProfile a_userProfile, long a_lUserId, String a_sIP, String a_sURL)
/*      */     throws Bn2Exception
/*      */   {
/*  916 */     Connection con = null;
/*  917 */     String sSql = null;
/*      */     try
/*      */     {
/*  921 */       if (a_cCon != null)
/*      */       {
/*  923 */         con = a_cCon;
/*      */       }
/*      */       else
/*      */       {
/*  927 */         con = this.m_dataSource.getConnection();
/*      */       }
/*      */ 
/*  931 */       a_userProfile.resetPermissionCaches();
/*      */ 
/*  934 */       if (a_userProfile.getIsAdmin())
/*      */       {
/*  937 */         a_userProfile.resetCategories();
/*      */       }
/*      */       else
/*      */       {
/*  946 */         sSql = "SELECT cvg.CategoryId, MAX(cvg.CanUpdateAssets) AS MaxCanUpdateAssets, MAX(cvg.CanDeleteAssets) AS MaxCanDeleteAssets, MAX(cvg.CanDownloadAssets) AS MaxCanDownloadAssets, MAX(cvg.CanDownloadWithApproval) AS MaxCanDownloadWithApproval, MAX(cvg.CanUpdateWithApproval) AS MaxCanUpdateWithApproval, MAX(cvg.CanApproveAssets) AS MaxCanApproveAssets, MAX(cvg.CanApproveAssetUploads) AS MaxCanApproveAssetUploads, MAX(cvg.CanViewRestrictedAssets) AS MaxCanViewRestrictedAssets, MIN(cvg.CantDownloadOriginal) AS MinCantDownloadOriginal, MIN(cvg.CantDownloadAdvanced) AS MinCantDownloadAdvanced, MIN(cvg.CantReviewAssets) AS MinCantReviewAssets, MIN(cvg.CanEditSubcategories) AS MinCanEditSubcategories, MIN(cvg.ApprRequiredForHighRes) AS MinApprRequiredForHighRes, c.BitPosition, c.IsRestrictive FROM UserGroup ug INNER JOIN CategoryVisibleToGroup cvg ON cvg.UserGroupId=ug.Id INNER JOIN CM_Category c ON cvg.CategoryId=c.Id LEFT JOIN UserInGroup uig ON uig.UserGroupId = ug.Id WHERE  uig.UserId=? OR " + getDefaultGroupSqlCriteria(a_sIP, a_sURL, con);
/*      */ 
/*  971 */         sSql = sSql + " GROUP BY cvg.CategoryId, c.IsRestrictive, c.BitPosition";
/*      */ 
/*  974 */         PreparedStatement psql = con.prepareStatement(sSql);
/*  975 */         psql.setLong(1, a_lUserId);
/*      */ 
/*  977 */         ResultSet rsVisibleIds = psql.executeQuery();
/*      */ 
/*  980 */         Vector[] avecCategoryIds = new Vector[15];
/*      */ 
/*  983 */         for (int i = 0; i < 15; i++)
/*      */         {
/*  985 */           avecCategoryIds[i] = new Vector();
/*      */         }
/*      */ 
/*  988 */         if (rsVisibleIds.next())
/*      */         {
/*      */           do
/*      */           {
/*  992 */             Long longId = new Long(rsVisibleIds.getLong("CategoryId"));
/*      */ 
/*  994 */             avecCategoryIds[1].add(longId);
/*      */ 
/*  996 */             if (rsVisibleIds.getBoolean("MaxCanDownloadAssets"))
/*      */             {
/*  998 */               avecCategoryIds[2].add(longId);
/*      */             }
/*      */ 
/* 1001 */             if (rsVisibleIds.getBoolean("MaxCanDownloadWithApproval"))
/*      */             {
/* 1003 */               avecCategoryIds[5].add(longId);
/*      */             }
/*      */ 
/* 1006 */             if (rsVisibleIds.getBoolean("MaxCanUpdateAssets"))
/*      */             {
/* 1008 */               avecCategoryIds[3].add(longId);
/*      */             }
/*      */ 
/* 1011 */             if (rsVisibleIds.getBoolean("MaxCanDeleteAssets"))
/*      */             {
/* 1013 */               avecCategoryIds[4].add(longId);
/*      */             }
/*      */ 
/* 1017 */             if ((rsVisibleIds.getBoolean("MaxCanUpdateWithApproval")) && (!rsVisibleIds.getBoolean("MaxCanUpdateAssets")))
/*      */             {
/* 1020 */               avecCategoryIds[6].add(longId);
/*      */             }
/*      */ 
/* 1023 */             if (rsVisibleIds.getBoolean("MaxCanApproveAssets"))
/*      */             {
/* 1025 */               avecCategoryIds[7].add(longId);
/*      */             }
/*      */ 
/* 1028 */             if (rsVisibleIds.getBoolean("MaxCanApproveAssetUploads"))
/*      */             {
/* 1030 */               avecCategoryIds[12].add(longId);
/*      */             }
/*      */ 
/* 1033 */             if (rsVisibleIds.getBoolean("MaxCanViewRestrictedAssets"))
/*      */             {
/* 1035 */               avecCategoryIds[13].add(longId);
/*      */             }
/*      */ 
/* 1038 */             if (!rsVisibleIds.getBoolean("MinCantDownloadOriginal"))
/*      */             {
/* 1040 */               avecCategoryIds[8].add(longId);
/*      */             }
/*      */ 
/* 1043 */             if (!rsVisibleIds.getBoolean("MinCantDownloadAdvanced"))
/*      */             {
/* 1045 */               avecCategoryIds[9].add(longId);
/*      */             }
/*      */ 
/* 1048 */             if (!rsVisibleIds.getBoolean("MinCantReviewAssets"))
/*      */             {
/* 1050 */               avecCategoryIds[11].add(longId);
/*      */             }
/*      */ 
/* 1053 */             if (rsVisibleIds.getBoolean("MinCanEditSubcategories"))
/*      */             {
/* 1055 */               avecCategoryIds[10].add(longId);
/*      */             }
/*      */ 
/* 1058 */             if (!rsVisibleIds.getBoolean("MinApprRequiredForHighRes"))
/*      */               continue;
/* 1060 */             avecCategoryIds[14].add(longId);
/*      */           }
/*      */ 
/* 1064 */           while (rsVisibleIds.next());
/*      */         }
/*      */ 
/* 1071 */         for (int i = 0; i < 15; i++)
/*      */         {
/* 1074 */           a_userProfile.setPermissionCategoryIds(i, new HashSet((int)Math.floor(avecCategoryIds[i].size() / 0.75D) + 1));
/*      */ 
/* 1077 */           for (int j = 0; j < avecCategoryIds[i].size(); j++)
/*      */           {
/* 1079 */             a_userProfile.getPermissionCategoryIds(i).add(avecCategoryIds[i].get(j));
/*      */           }
/*      */         }
/*      */ 
/* 1083 */         psql.close();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       try
/*      */       {
/* 1091 */         if (a_cCon == null)
/*      */         {
/* 1093 */           con.rollback();
/*      */         }
/*      */ 
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*      */       }
/*      */ 
/* 1101 */       throw new Bn2Exception("Exception occurred checking user login : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1106 */       if (a_cCon == null)
/*      */       {
/*      */         try
/*      */         {
/* 1110 */           if (con != null)
/*      */           {
/* 1112 */             con.commit();
/* 1113 */             con.close();
/*      */           }
/*      */ 
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1119 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean getEmptyAssetsAllowed()
/*      */     throws Bn2Exception
/*      */   {
/* 1136 */     if (this.m_bEmptyAssetsSwitchSet)
/*      */     {
/* 1138 */       return this.m_bEmptyAssetsSwitch;
/*      */     }
/*      */ 
/* 1141 */     Connection con = null;
/* 1142 */     PreparedStatement psql = null;
/* 1143 */     ResultSet rs = null;
/* 1144 */     DBTransaction transaction = getDBTransactionManager().getNewTransaction();
/* 1145 */     String sSQL = null;
/* 1146 */     boolean bEmptyAllowed = false;
/*      */     try
/*      */     {
/* 1150 */       con = transaction.getConnection();
/*      */ 
/* 1152 */       sSQL = "SELECT Id FROM UserGroup WHERE CanAddEmptyAssets=1";
/* 1153 */       psql = con.prepareStatement(sSQL);
/* 1154 */       rs = psql.executeQuery();
/* 1155 */       if (rs.next())
/*      */       {
/* 1157 */         bEmptyAllowed = true;
/*      */       }
/* 1159 */       psql.close();
/*      */ 
/* 1161 */       synchronized (this.m_objEmptyAssetsSwitchLock)
/*      */       {
/* 1163 */         this.m_bEmptyAssetsSwitch = bEmptyAllowed;
/* 1164 */         this.m_bEmptyAssetsSwitchSet = true;
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1169 */       this.m_logger.error("SQL Exception whilst checking for emtpy assets : " + e);
/* 1170 */       throw new Bn2Exception("SQL Exception whilst checking for emtpy assets : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1175 */       if (transaction != null)
/*      */       {
/*      */         try
/*      */         {
/* 1179 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1183 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1188 */     return bEmptyAllowed;
/*      */   }
/*      */ 
/*      */   protected void clearEmptyAssetSwitch()
/*      */   {
/* 1193 */     synchronized (this.m_objEmptyAssetsSwitchLock)
/*      */     {
/* 1195 */       this.m_bEmptyAssetsSwitch = false;
/* 1196 */       this.m_bEmptyAssetsSwitchSet = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   public User getUser(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 1234 */     Connection con = null;
/* 1235 */     ABUser user = null;
/* 1236 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1238 */     if (transaction == null)
/*      */     {
/* 1240 */       transaction = getDBTransactionManager().getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1245 */       if (a_lId >= 0L)
/*      */       {
/* 1247 */         con = transaction.getConnection();
/*      */ 
/* 1249 */         String sSql = "SELECT u.Id userId, u.Password, u.Surname, u.Forename, u.Username, u.EmailAddress, u.Title, u.Organisation, u.Address, u.RegistrationInfo, u.IsAdmin, u.IsSuspended, u.DisplayName, u.CN, u.DistinguishedName, u.Mailbox, u.NotActiveDirectory, u.Hidden, u.IsDeleted, u.NotApproved, u.RequiresUpdate, u.RequestedOrgUnitId, u.CanBeCategoryAdmin, u.ExpiryDate, u.RegisterDate, u.JobTitle, u.VATNumber, u.LDAPServerIndex, u.ReceiveAlerts, u.CanLoginFromCms, u.AdminNotes, u.LastModifiedById, u.InvitedByUserId, u.AdditionalApprovalName, u.SearchResultsPerPage, u.ReactivationPending, ug.Id ugId, ug.Name, ug.Description, ug.IsDefaultGroup, ug.Mapping, ug.DiscountPercentage, ug.CanOnlyEditOwn, ug.CanEmailAssets, ug.CanRepurposeAssets, ug.CanSeeSourcePath, ug.MaxDownloadHeight, ug.MaxDownloadWidth, ug.CanInviteUsers, ug.CanAddEmptyAssets, ug.CanViewLargerSize, ug.AutomaticBrandRegister, ug.AdvancedViewing, groupou.Id groupouId, groupou.DiskQuotaMb groupouDiskQuotaMb, cat.name groupouName, ou.Id ouId, ou.DiskQuotaMb ouDiskQuotaMb, oucat.name ouName, divis.Id divId, divis.Name divName, addr.Id addrId, br.Id brId, br.Name brName, l.Id lId, l.Code lCode, l.Name lName, l.IsDefault lIsDefault, l.IsRightToLeft lIsRightToLeft, l.UsesLatinAlphabet lUsesLatinAlphabet FROM AssetBankUser u  LEFT JOIN UserInGroup uig ON uig.UserId = u.Id LEFT JOIN UserGroup ug ON uig.UserGroupId = ug.Id LEFT JOIN OrgUnitGroup oug ON oug.UserGroupId = ug.Id LEFT JOIN OrgUnit groupou ON oug.OrgUnitId = groupou.Id LEFT JOIN CM_Category cat ON groupou.RootOrgUnitCategoryId = cat.Id LEFT JOIN Brand br ON ug.BrandId = br.Id LEFT JOIN OrgUnit ou ON ou.AdminGroupId = ug.Id LEFT JOIN CM_Category oucat ON ou.RootOrgUnitCategoryId = oucat.Id LEFT JOIN Division divis ON divis.Id = u.DivisionId LEFT JOIN Address addr ON addr.Id = u.AddressId LEFT JOIN Language l ON l.Id = u.LanguageId ";
/* 1250 */         sSql = sSql + " WHERE u.Id=? AND u.IsDeleted=? ORDER BY ug.Name";
/*      */ 
/* 1252 */         PreparedStatement psql = con.prepareStatement(sSql);
/*      */ 
/* 1254 */         psql.setLong(1, a_lId);
/* 1255 */         psql.setBoolean(2, false);
/*      */ 
/* 1257 */         ResultSet rs = psql.executeQuery();
/* 1258 */         Vector vecUsers = buildUsers(transaction, rs);
/* 1259 */         if ((vecUsers != null) && (vecUsers.size() > 0))
/*      */         {
/* 1261 */           user = (ABUser)(ABUser)vecUsers.firstElement();
/*      */         }
/* 1263 */         psql.close();
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1268 */       if (a_dbTransaction == null)
/*      */       {
/*      */         try
/*      */         {
/* 1272 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1280 */       this.m_logger.error("ABUserManager.getUser " + e.getMessage());
/* 1281 */       throw new Bn2Exception("ABUserManager.getUser " + e.getMessage(), e);
/*      */     }
/*      */     finally
/*      */     {
/* 1286 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1290 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1294 */           this.m_logger.error("Sorry, SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1299 */     return user;
/*      */   }
/*      */ 
/*      */   public Vector getAllAssetBankUsers(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 1309 */     Connection cCon = null;
/* 1310 */     Vector vecUsers = null;
/*      */     try
/*      */     {
/* 1314 */       cCon = a_dbTransaction.getConnection();
/*      */ 
/* 1317 */       String sSql = "SELECT u.Id userId, u.Password, u.Surname, u.Forename, u.Username, u.EmailAddress, u.Title, u.Organisation, u.Address, u.RegistrationInfo, u.IsAdmin, u.IsSuspended, u.DisplayName, u.CN, u.DistinguishedName, u.Mailbox, u.NotActiveDirectory, u.Hidden, u.IsDeleted, u.NotApproved, u.RequiresUpdate, u.RequestedOrgUnitId, u.CanBeCategoryAdmin, u.ExpiryDate, u.RegisterDate, u.JobTitle, u.VATNumber, u.LDAPServerIndex, u.ReceiveAlerts, u.CanLoginFromCms, u.AdminNotes, u.LastModifiedById, u.InvitedByUserId, u.AdditionalApprovalName, u.SearchResultsPerPage, u.ReactivationPending, ug.Id ugId, ug.Name, ug.Description, ug.IsDefaultGroup, ug.Mapping, ug.DiscountPercentage, ug.CanOnlyEditOwn, ug.CanEmailAssets, ug.CanRepurposeAssets, ug.CanSeeSourcePath, ug.MaxDownloadHeight, ug.MaxDownloadWidth, ug.CanInviteUsers, ug.CanAddEmptyAssets, ug.CanViewLargerSize, ug.AutomaticBrandRegister, ug.AdvancedViewing, groupou.Id groupouId, groupou.DiskQuotaMb groupouDiskQuotaMb, cat.name groupouName, ou.Id ouId, ou.DiskQuotaMb ouDiskQuotaMb, oucat.name ouName, divis.Id divId, divis.Name divName, addr.Id addrId, br.Id brId, br.Name brName, l.Id lId, l.Code lCode, l.Name lName, l.IsDefault lIsDefault, l.IsRightToLeft lIsRightToLeft, l.UsesLatinAlphabet lUsesLatinAlphabet FROM AssetBankUser u  LEFT JOIN UserInGroup uig ON uig.UserId = u.Id LEFT JOIN UserGroup ug ON uig.UserGroupId = ug.Id LEFT JOIN OrgUnitGroup oug ON oug.UserGroupId = ug.Id LEFT JOIN OrgUnit groupou ON oug.OrgUnitId = groupou.Id LEFT JOIN CM_Category cat ON groupou.RootOrgUnitCategoryId = cat.Id LEFT JOIN Brand br ON ug.BrandId = br.Id LEFT JOIN OrgUnit ou ON ou.AdminGroupId = ug.Id LEFT JOIN CM_Category oucat ON ou.RootOrgUnitCategoryId = oucat.Id LEFT JOIN Division divis ON divis.Id = u.DivisionId LEFT JOIN Address addr ON addr.Id = u.AddressId LEFT JOIN Language l ON l.Id = u.LanguageId ";
/* 1318 */       sSql = sSql + " WHERE u.IsDeleted=? ORDER BY ug.Name";
/*      */ 
/* 1321 */       PreparedStatement psql = cCon.prepareStatement(sSql);
/* 1322 */       psql.setBoolean(1, false);
/*      */ 
/* 1324 */       ResultSet rs = psql.executeQuery();
/* 1325 */       vecUsers = buildUsers(a_dbTransaction, rs);
/*      */ 
/* 1327 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1332 */       this.m_logger.debug("Exception occurred in UserMngr:getAllAssetBankUsers : " + e);
/* 1333 */       throw new Bn2Exception("Exception occurred in UserMngr:getAllAssetBankUsers : " + e, e);
/*      */     }
/*      */ 
/* 1336 */     return vecUsers;
/*      */   }
/*      */ 
/*      */   public Vector getUsers(DBTransaction a_dbTransaction, Vector<Long> a_vecUserIds)
/*      */     throws Bn2Exception
/*      */   {
/* 1352 */     Connection cCon = null;
/* 1353 */     Vector vecUsers = null;
/*      */     try
/*      */     {
/* 1357 */       if ((a_vecUserIds != null) && (a_vecUserIds.size() > 0))
/*      */       {
/* 1359 */         cCon = a_dbTransaction.getConnection();
/*      */ 
/* 1362 */         String sSql = "SELECT u.Id userId, u.Password, u.Surname, u.Forename, u.Username, u.EmailAddress, u.Title, u.Organisation, u.Address, u.RegistrationInfo, u.IsAdmin, u.IsSuspended, u.DisplayName, u.CN, u.DistinguishedName, u.Mailbox, u.NotActiveDirectory, u.Hidden, u.IsDeleted, u.NotApproved, u.RequiresUpdate, u.RequestedOrgUnitId, u.CanBeCategoryAdmin, u.ExpiryDate, u.RegisterDate, u.JobTitle, u.VATNumber, u.LDAPServerIndex, u.ReceiveAlerts, u.CanLoginFromCms, u.AdminNotes, u.LastModifiedById, u.InvitedByUserId, u.AdditionalApprovalName, u.SearchResultsPerPage, u.ReactivationPending, ug.Id ugId, ug.Name, ug.Description, ug.IsDefaultGroup, ug.Mapping, ug.DiscountPercentage, ug.CanOnlyEditOwn, ug.CanEmailAssets, ug.CanRepurposeAssets, ug.CanSeeSourcePath, ug.MaxDownloadHeight, ug.MaxDownloadWidth, ug.CanInviteUsers, ug.CanAddEmptyAssets, ug.CanViewLargerSize, ug.AutomaticBrandRegister, ug.AdvancedViewing, groupou.Id groupouId, groupou.DiskQuotaMb groupouDiskQuotaMb, cat.name groupouName, ou.Id ouId, ou.DiskQuotaMb ouDiskQuotaMb, oucat.name ouName, divis.Id divId, divis.Name divName, addr.Id addrId, br.Id brId, br.Name brName, l.Id lId, l.Code lCode, l.Name lName, l.IsDefault lIsDefault, l.IsRightToLeft lIsRightToLeft, l.UsesLatinAlphabet lUsesLatinAlphabet FROM AssetBankUser u  LEFT JOIN UserInGroup uig ON uig.UserId = u.Id LEFT JOIN UserGroup ug ON uig.UserGroupId = ug.Id LEFT JOIN OrgUnitGroup oug ON oug.UserGroupId = ug.Id LEFT JOIN OrgUnit groupou ON oug.OrgUnitId = groupou.Id LEFT JOIN CM_Category cat ON groupou.RootOrgUnitCategoryId = cat.Id LEFT JOIN Brand br ON ug.BrandId = br.Id LEFT JOIN OrgUnit ou ON ou.AdminGroupId = ug.Id LEFT JOIN CM_Category oucat ON ou.RootOrgUnitCategoryId = oucat.Id LEFT JOIN Division divis ON divis.Id = u.DivisionId LEFT JOIN Address addr ON addr.Id = u.AddressId LEFT JOIN Language l ON l.Id = u.LanguageId ";
/* 1363 */         sSql = sSql + " WHERE u.Id IN (";
/*      */ 
/* 1365 */         for (int i = 0; i < a_vecUserIds.size(); i++)
/*      */         {
/* 1367 */           sSql = sSql + ((Long)a_vecUserIds.elementAt(i)).longValue();
/*      */ 
/* 1369 */           if (i >= a_vecUserIds.size() - 1)
/*      */             continue;
/* 1371 */           sSql = sSql + ",";
/*      */         }
/*      */ 
/* 1375 */         sSql = sSql + ") AND u.IsDeleted=? ORDER BY ug.Name";
/*      */ 
/* 1378 */         PreparedStatement psql = cCon.prepareStatement(sSql);
/* 1379 */         psql.setBoolean(1, false);
/*      */ 
/* 1381 */         ResultSet rs = psql.executeQuery();
/* 1382 */         vecUsers = buildUsers(a_dbTransaction, rs);
/*      */ 
/* 1384 */         psql.close();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1390 */       this.m_logger.debug("Exception occurred in UserMngr:getUsers : " + e);
/* 1391 */       throw new Bn2Exception("Exception occurred in UserMngr:getUsers : " + e, e);
/*      */     }
/*      */ 
/* 1394 */     return vecUsers;
/*      */   }
/*      */ 
/*      */   public Vector getRecentlyRegisteredUsers(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 1411 */     int iRecentlyRegDays = AssetBankSettings.getRecentlyRegisteredDays();
/* 1412 */     int iDaysAgo = 0 - iRecentlyRegDays;
/*      */ 
/* 1415 */     GregorianCalendar now = new GregorianCalendar();
/* 1416 */     now.add(5, iDaysAgo);
/*      */ 
/* 1418 */     return getUsersRegisteredAfterDate(a_dbTransaction, now.getTime());
/*      */   }
/*      */ 
/*      */   public Vector<ABUser> getUsersRegisteredAfterDate(DBTransaction a_dbTransaction, java.util.Date a_dtAfter)
/*      */     throws Bn2Exception
/*      */   {
/* 1438 */     Connection cCon = null;
/* 1439 */     Vector vecUsers = null;
/*      */     try
/*      */     {
/* 1443 */       if (a_dtAfter != null)
/*      */       {
/* 1445 */         cCon = a_dbTransaction.getConnection();
/*      */ 
/* 1447 */         String sSql = "SELECT u.Id userId, u.Password, u.Surname, u.Forename, u.Username, u.EmailAddress, u.Title, u.Organisation, u.Address, u.RegistrationInfo, u.IsAdmin, u.IsSuspended, u.DisplayName, u.CN, u.DistinguishedName, u.Mailbox, u.NotActiveDirectory, u.Hidden, u.IsDeleted, u.NotApproved, u.RequiresUpdate, u.RequestedOrgUnitId, u.CanBeCategoryAdmin, u.ExpiryDate, u.RegisterDate, u.JobTitle, u.VATNumber, u.LDAPServerIndex, u.ReceiveAlerts, u.CanLoginFromCms, u.AdminNotes, u.LastModifiedById, u.InvitedByUserId, u.AdditionalApprovalName, u.SearchResultsPerPage, u.ReactivationPending, ug.Id ugId, ug.Name, ug.Description, ug.IsDefaultGroup, ug.Mapping, ug.DiscountPercentage, ug.CanOnlyEditOwn, ug.CanEmailAssets, ug.CanRepurposeAssets, ug.CanSeeSourcePath, ug.MaxDownloadHeight, ug.MaxDownloadWidth, ug.CanInviteUsers, ug.CanAddEmptyAssets, ug.CanViewLargerSize, ug.AutomaticBrandRegister, ug.AdvancedViewing, groupou.Id groupouId, groupou.DiskQuotaMb groupouDiskQuotaMb, cat.name groupouName, ou.Id ouId, ou.DiskQuotaMb ouDiskQuotaMb, oucat.name ouName, divis.Id divId, divis.Name divName, addr.Id addrId, br.Id brId, br.Name brName, l.Id lId, l.Code lCode, l.Name lName, l.IsDefault lIsDefault, l.IsRightToLeft lIsRightToLeft, l.UsesLatinAlphabet lUsesLatinAlphabet FROM AssetBankUser u  LEFT JOIN UserInGroup uig ON uig.UserId = u.Id LEFT JOIN UserGroup ug ON uig.UserGroupId = ug.Id LEFT JOIN OrgUnitGroup oug ON oug.UserGroupId = ug.Id LEFT JOIN OrgUnit groupou ON oug.OrgUnitId = groupou.Id LEFT JOIN CM_Category cat ON groupou.RootOrgUnitCategoryId = cat.Id LEFT JOIN Brand br ON ug.BrandId = br.Id LEFT JOIN OrgUnit ou ON ou.AdminGroupId = ug.Id LEFT JOIN CM_Category oucat ON ou.RootOrgUnitCategoryId = oucat.Id LEFT JOIN Division divis ON divis.Id = u.DivisionId LEFT JOIN Address addr ON addr.Id = u.AddressId LEFT JOIN Language l ON l.Id = u.LanguageId ";
/*      */ 
/* 1450 */         sSql = sSql + " WHERE u.RegisterDate >= ? AND u.IsDeleted=? AND u.Hidden=? AND u.NotApproved=? ORDER BY u.RegisterDate DESC, userId, ug.Name";
/*      */ 
/* 1452 */         PreparedStatement psql = cCon.prepareStatement(sSql);
/* 1453 */         DBUtil.setFieldDateOrNull(psql, 1, a_dtAfter);
/* 1454 */         psql.setBoolean(2, false);
/* 1455 */         psql.setBoolean(3, false);
/* 1456 */         psql.setBoolean(4, false);
/*      */ 
/* 1458 */         ResultSet rs = psql.executeQuery();
/* 1459 */         long lLastUserId = 0L;
/* 1460 */         ABUser user = null;
/* 1461 */         Vector vecGroups = null;
/*      */ 
/* 1465 */         while (rs.next())
/*      */         {
/* 1468 */           if (lLastUserId != rs.getLong("userId"))
/*      */           {
/* 1471 */             if (user != null)
/*      */             {
/* 1473 */               user.setGroups(vecGroups);
/* 1474 */               vecGroups = null;
/*      */ 
/* 1476 */               if (vecUsers == null)
/*      */               {
/* 1478 */                 vecUsers = new Vector();
/*      */               }
/*      */ 
/* 1481 */               vecUsers.add(user);
/*      */             }
/*      */ 
/* 1484 */             user = buildUser(rs);
/* 1485 */             lLastUserId = user.getId();
/*      */           }
/*      */ 
/* 1489 */           long lGroupId = rs.getLong("ugId");
/* 1490 */           if (lGroupId > 0L)
/*      */           {
/* 1493 */             Group group = buildGroup(rs);
/*      */ 
/* 1496 */             if (vecGroups == null)
/*      */             {
/* 1498 */               vecGroups = new Vector();
/*      */             }
/*      */ 
/* 1501 */             if (!vecGroups.contains(group))
/*      */             {
/* 1503 */               vecGroups.add(group);
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1509 */         if (user != null)
/*      */         {
/* 1511 */           user.setGroups(vecGroups);
/* 1512 */           if (vecUsers == null)
/*      */           {
/* 1514 */             vecUsers = new Vector();
/*      */           }
/* 1516 */           vecUsers.add(user);
/*      */         }
/*      */ 
/* 1519 */         psql.close();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1525 */       this.m_logger.debug("Exception occurred in UserManager:getUsersRegisteredAfterDate : " + e);
/* 1526 */       throw new Bn2Exception("Exception occurred in UserManager:getUsersRegisteredAfterDate : " + e, e);
/*      */     }
/*      */ 
/* 1529 */     return vecUsers;
/*      */   }
/*      */ 
/*      */   private Vector<ABUser> buildUsers(DBTransaction a_dbTransaction, ResultSet a_rs)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 1537 */     ABUser user = null;
/* 1538 */     Vector vecUsers = null;
/* 1539 */     Vector vecGroups = null;
/* 1540 */     Vector vecOrgUnits = new Vector();
/* 1541 */     long lLastId = -1L;
/*      */ 
/* 1544 */     while (a_rs.next())
/*      */     {
/* 1547 */       if (lLastId != a_rs.getLong("userId"))
/*      */       {
/* 1550 */         if (user != null)
/*      */         {
/* 1553 */           user.setGroups(vecGroups);
/* 1554 */           user.setOrgUnits(vecOrgUnits);
/*      */ 
/* 1556 */           if (vecUsers == null)
/*      */           {
/* 1558 */             vecUsers = new Vector();
/*      */           }
/* 1560 */           if (!vecUsers.contains(user))
/*      */           {
/* 1562 */             vecUsers.add(user);
/*      */           }
/*      */         }
/*      */ 
/* 1566 */         user = buildUser(a_rs);
/*      */ 
/* 1569 */         long lAddressId = a_rs.getLong("addrId");
/* 1570 */         if (lAddressId > 0L)
/*      */         {
/* 1572 */           Address address = this.m_addressManager.getAddress(a_dbTransaction, lAddressId);
/* 1573 */           user.setHomeAddress(address);
/*      */         }
/*      */ 
/* 1576 */         lLastId = a_rs.getLong("userId");
/*      */       }
/*      */ 
/* 1580 */       long lGroupId = a_rs.getLong("ugId");
/* 1581 */       if (lGroupId > 0L)
/*      */       {
/* 1584 */         Group group = buildGroup(a_dbTransaction, a_rs, true);
/*      */ 
/* 1587 */         if (vecGroups == null)
/*      */         {
/* 1589 */           vecGroups = new Vector();
/*      */         }
/*      */ 
/* 1592 */         if (!vecGroups.contains(group))
/*      */         {
/* 1594 */           vecGroups.add(group);
/*      */         }
/*      */ 
/* 1598 */         if (a_rs.getLong("ouId") > 0L)
/*      */         {
/* 1600 */           user.setIsOrgUnitAdmin(true);
/*      */ 
/* 1603 */           OrgUnitMetadata org = buildOrgUnitMetadata(a_dbTransaction, a_rs, true);
/*      */ 
/* 1605 */           if (!vecOrgUnits.contains(org))
/*      */           {
/* 1607 */             vecOrgUnits.add(org);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1614 */     if (user != null)
/*      */     {
/* 1617 */       user.setGroups(vecGroups);
/* 1618 */       user.setOrgUnits(vecOrgUnits);
/*      */ 
/* 1620 */       if (vecUsers == null)
/*      */       {
/* 1622 */         vecUsers = new Vector();
/*      */       }
/* 1624 */       vecUsers.add(user);
/*      */     }
/*      */ 
/* 1628 */     return vecUsers;
/*      */   }
/*      */ 
/*      */   public OrgUnitMetadata buildOrgUnitMetadata(DBTransaction a_dbTransaction, ResultSet a_rs, boolean a_bPopulateContent)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 1635 */     return buildOrgUnitMetadata(a_dbTransaction, a_rs, "", a_bPopulateContent);
/*      */   }
/*      */ 
/*      */   private OrgUnitMetadata buildOrgUnitMetadata(DBTransaction a_dbTransaction, ResultSet a_rs, String a_sPrefix, boolean a_bPopulateContent)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 1641 */     OrgUnitMetadata org = new OrgUnitMetadata();
/* 1642 */     org.setId(a_rs.getLong(a_sPrefix + "ouId"));
/* 1643 */     org.setDiskQuotaMb(a_rs.getInt(a_sPrefix + "ouDiskQuotaMb"));
/* 1644 */     org.setName(a_rs.getString(a_sPrefix + "ouName"));
/*      */ 
/* 1646 */     if (a_bPopulateContent)
/*      */     {
/* 1648 */       this.m_orgUnitManager.populateContent(a_dbTransaction, org);
/*      */     }
/*      */ 
/* 1651 */     return org;
/*      */   }
/*      */ 
/*      */   private ABUser buildUser(ResultSet a_rs)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 1657 */     ABUser user = new ABUser();
/* 1658 */     user.setId(a_rs.getLong("userId"));
/* 1659 */     user.setPassword(a_rs.getString("Password"));
/* 1660 */     user.setSurname(a_rs.getString("Surname"));
/* 1661 */     user.setForename(a_rs.getString("Forename"));
/* 1662 */     user.setUsername(a_rs.getString("Username"));
/* 1663 */     user.setEmailAddress(a_rs.getString("EmailAddress"));
/* 1664 */     user.setTitle(a_rs.getString("Title"));
/* 1665 */     user.setOrganisation(a_rs.getString("Organisation"));
/* 1666 */     user.setAddress(a_rs.getString("Address"));
/* 1667 */     user.setRegistrationInfo(SQLGenerator.getInstance().getStringFromLargeTextField(a_rs, "RegistrationInfo"));
/* 1668 */     user.setIsAdmin(a_rs.getBoolean("IsAdmin"));
/* 1669 */     user.setIsSuspended(a_rs.getBoolean("IsSuspended"));
/* 1670 */     user.setDisplayName(a_rs.getString("DisplayName"));
/* 1671 */     user.setCommonName(a_rs.getString("CN"));
/* 1672 */     user.setRemoteUsername(a_rs.getString("DistinguishedName"));
/* 1673 */     user.setMailbox(a_rs.getString("Mailbox"));
/* 1674 */     user.setRemoteUser(!a_rs.getBoolean("NotActiveDirectory"));
/* 1675 */     user.setHidden(a_rs.getBoolean("Hidden"));
/* 1676 */     user.setIsDeleted(a_rs.getBoolean("IsDeleted"));
/* 1677 */     user.setNotApproved(a_rs.getBoolean("NotApproved"));
/* 1678 */     user.setRequiresUpdate(a_rs.getBoolean("RequiresUpdate"));
/* 1679 */     user.setRequestedOrgUnitId(a_rs.getLong("RequestedOrgUnitId"));
/* 1680 */     user.setCanBeCategoryAdmin(a_rs.getBoolean("CanBeCategoryAdmin"));
/* 1681 */     user.setExpiryDate(a_rs.getDate("ExpiryDate"));
/* 1682 */     user.setRegisterDate(a_rs.getDate("RegisterDate"));
/* 1683 */     user.setJobTitle(a_rs.getString("JobTitle"));
/* 1684 */     user.setTaxNumber(a_rs.getString("VATNumber"));
/* 1685 */     user.setReceiveAlerts(a_rs.getBoolean("ReceiveAlerts"));
/* 1686 */     user.setCanLoginFromCms(a_rs.getBoolean("CanLoginFromCms"));
/* 1687 */     user.setAdminNotes(SQLGenerator.getInstance().getStringFromLargeTextField(a_rs, "AdminNotes"));
/* 1688 */     user.setLastModifiedBy(a_rs.getLong("LastModifiedById"));
/* 1689 */     user.setInvitedByUserId(a_rs.getLong("InvitedByUserId"));
/* 1690 */     user.setAdditionalApproverDetails(a_rs.getString("AdditionalApprovalName"));
/* 1691 */     user.setSearchResultsPerPage(a_rs.getInt("SearchResultsPerPage"));
/* 1692 */     user.setReactivationPending(a_rs.getBoolean("ReactivationPending"));
/*      */ 
/* 1694 */     Language language = new Language(a_rs.getLong("lId"), a_rs.getString("lName"), a_rs.getString("lCode"));
/* 1695 */     language.setDefault(a_rs.getBoolean("lIsDefault"));
/* 1696 */     language.setRightToLeft(a_rs.getBoolean("lIsRightToLeft"));
/* 1697 */     language.setUsesLatinAlphabet(a_rs.getBoolean("lUsesLatinAlphabet"));
/* 1698 */     user.setLanguage(language);
/*      */ 
/* 1701 */     long lDivisionId = a_rs.getLong("divId");
/* 1702 */     if (lDivisionId > 0L)
/*      */     {
/* 1704 */       user.getRefDivision().setId(lDivisionId);
/* 1705 */       user.getRefDivision().setDescription(a_rs.getString("divName"));
/*      */     }
/*      */ 
/* 1708 */     return user;
/*      */   }
/*      */ 
/*      */   private Group buildGroup(ResultSet a_rs)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 1715 */     return buildGroup(null, a_rs, false);
/*      */   }
/*      */ 
/*      */   private Group buildGroup(DBTransaction a_dbTransaction, ResultSet a_rs, boolean a_bPopulateOrgUnitContent)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 1721 */     Group group = new Group();
/* 1722 */     group.setId(a_rs.getLong("ugId"));
/* 1723 */     group.setDescription(a_rs.getString("Description"));
/* 1724 */     group.setName(a_rs.getString("Name"));
/* 1725 */     group.setIsDefaultGroup(a_rs.getBoolean("IsDefaultGroup"));
/* 1726 */     group.setMapping(a_rs.getString("Mapping"));
/* 1727 */     group.setDiscountPercentage(a_rs.getInt("DiscountPercentage"));
/* 1728 */     group.setUserCanOnlyEditOwnFiles(a_rs.getBoolean("CanOnlyEditOwn"));
/* 1729 */     group.setUserCanEmailAssets(a_rs.getBoolean("CanEmailAssets"));
/* 1730 */     group.setUserCanRepurposeAssets(a_rs.getBoolean("CanRepurposeAssets"));
/* 1731 */     group.setUsersCanSeeSourcePath(a_rs.getBoolean("CanSeeSourcePath"));
/* 1732 */     group.setMaxDownloadHeight(a_rs.getInt("MaxDownloadHeight"));
/* 1733 */     group.setMaxDownloadWidth(a_rs.getInt("MaxDownloadWidth"));
/* 1734 */     group.setUsersCanInviteUsers(a_rs.getBoolean("CanInviteUsers"));
/* 1735 */     group.setUsersCanAddEmptyAssets(a_rs.getBoolean("CanAddEmptyAssets"));
/* 1736 */     group.setUsersCanViewLargerSize(a_rs.getBoolean("CanViewLargerSize"));
/* 1737 */     group.setAutomaticBrandRegister(a_rs.getBoolean("AutomaticBrandRegister"));
/* 1738 */     group.setAdvancedViewing(a_rs.getBoolean("AdvancedViewing"));
/*      */ 
/* 1741 */     long lGroupOUId = a_rs.getLong("groupouId");
/* 1742 */     if (lGroupOUId > 0L)
/*      */     {
/* 1744 */       OrgUnitMetadata org = buildOrgUnitMetadata(a_dbTransaction, a_rs, "group", a_bPopulateOrgUnitContent);
/* 1745 */       group.setOrgUnitReference(org);
/*      */     }
/*      */ 
/* 1749 */     long lBrandId = a_rs.getLong("brId");
/* 1750 */     if (lBrandId > 0L)
/*      */     {
/* 1752 */       group.getBrandReference().setId(lBrandId);
/* 1753 */       group.getBrandReference().setName(a_rs.getString("brName"));
/*      */     }
/*      */ 
/* 1756 */     return group;
/*      */   }
/*      */ 
/*      */   public Vector<ABUser> findUsers(UserSearchCriteria a_criteria, int a_iOrderId)
/*      */     throws Bn2Exception
/*      */   {
/* 1788 */     Vector vUsers = null;
/* 1789 */     Connection cCon = null;
/* 1790 */     String sSql = null;
/* 1791 */     String sWhereClause = "";
/*      */     try
/*      */     {
/* 1795 */       cCon = this.m_dataSource.getConnection();
/*      */ 
/* 1797 */       sSql = "SELECT u.Id, u.DisplayName, u.NotApproved, u.RequiresUpdate, u.RequestedOrgUnitId, u.Password, u.Surname, u.Forename, u.Username, u.EmailAddress, u.IsAdmin, u.ReceiveAlerts, u.Address, u.Organisation, u.JobTitle, u.NotActiveDirectory, u.RegistrationInfo, u.RegisterDate, u.InvitedByUserId, u.AdditionalApprovalName, u.ExpiryDate, a.Address1, a.Address2, a.Town, a.County, a.Postcode, divis.Id divId, divis.Name divName FROM AssetBankUser u LEFT JOIN Division divis ON divis.Id = u.DivisionId LEFT JOIN UserInGroup uig ON uig.UserId = u.Id LEFT JOIN Address a ON a.Id = u.AddressId LEFT JOIN OrgUnitGroup oug ON oug.UserGroupId = uig.UserGroupId LEFT JOIN OrgUnit ou ON ou.Id = oug.OrgUnitId LEFT JOIN UserGroup admingroup ON admingroup.Id = ou.AdminGroupId LEFT JOIN UserInGroup uiag ON uiag.UserGroupId = admingroup.Id ";
/*      */ 
/* 1837 */       if (a_criteria.getAssetId() > 0L)
/*      */       {
/* 1839 */         sSql = sSql + "INNER JOIN CategoryVisibleToGroup cvg ON cvg.UserGroupId = uig.UserGroupId INNER JOIN CM_ItemInCategory aic ON aic.CategoryId = cvg.CategoryId ";
/*      */       }
/*      */ 
/* 1844 */       if (!StringUtil.isEmpty(a_criteria.getMarketingGroupIds()))
/*      */       {
/* 1846 */         sSql = sSql + "INNER JOIN UserInMarketingGroup uimg ON (uimg.UserId = u.Id AND uimg.MarketingGroupId IN (" + StringUtil.convertStringArrayToString(a_criteria.getMarketingGroupIds(), ",") + ")) ";
/*      */       }
/*      */ 
/* 1851 */       if (a_criteria.getCountryId() > 0L)
/*      */       {
/* 1853 */         sSql = sSql + "INNER JOIN Address addr ON (addr.Id=u.AddressId AND addr.CountryId=" + a_criteria.getCountryId() + ") ";
/*      */       }
/*      */ 
/* 1856 */       sSql = sSql + "WHERE u.IsDeleted=0 ";
/*      */ 
/* 1858 */       if (!a_criteria.getIgnoreHidden())
/*      */       {
/* 1861 */         sWhereClause = " AND Hidden=";
/*      */ 
/* 1863 */         if (a_criteria.getHidden())
/*      */         {
/* 1865 */           sWhereClause = sWhereClause + "1";
/*      */         }
/*      */         else
/*      */         {
/* 1869 */           sWhereClause = sWhereClause + "0";
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1874 */       if (a_criteria.getAssetId() > 0L)
/*      */       {
/* 1876 */         sWhereClause = sWhereClause + " AND aic.ItemId = " + a_criteria.getAssetId();
/*      */ 
/* 1879 */         switch (a_criteria.getPermissionInCategory())
/*      */         {
/*      */         case 7:
/* 1882 */           sWhereClause = sWhereClause + " AND cvg.CanApproveAssets = 1 ";
/* 1883 */           break;
/*      */         case 12:
/* 1885 */           sWhereClause = sWhereClause + " AND cvg.CanApproveAssetUploads = 1 ";
/* 1886 */           break;
/*      */         case 3:
/* 1888 */           sWhereClause = sWhereClause + " AND cvg.CanUpdateAssets = 1 ";
/* 1889 */           break;
/*      */         case 6:
/* 1891 */           sWhereClause = sWhereClause + " AND cvg.CanUpdateWithApproval = 1 ";
/* 1892 */           break;
/*      */         case 2:
/* 1894 */           sWhereClause = sWhereClause + " AND cvg.CanDownloadAssets = 1 ";
/* 1895 */           break;
/*      */         case 5:
/* 1897 */           sWhereClause = sWhereClause + " AND cvg.CanDownloadWithApproval = 1 ";
/* 1898 */           break;
/*      */         case 4:
/*      */         case 8:
/*      */         case 9:
/*      */         case 10:
/*      */         case 11:
/*      */         }
/*      */       }
/* 1905 */       if (a_criteria.getNotApproved() != null)
/*      */       {
/* 1907 */         sWhereClause = sWhereClause + " AND NotApproved=";
/* 1908 */         if (a_criteria.getNotApproved().booleanValue())
/*      */         {
/* 1910 */           sWhereClause = sWhereClause + "1";
/*      */         }
/*      */         else
/*      */         {
/* 1914 */           sWhereClause = sWhereClause + "0";
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1919 */       if (a_criteria.getRequiresUpdate() != null)
/*      */       {
/* 1921 */         sWhereClause = sWhereClause + " AND RequiresUpdate=";
/* 1922 */         if (a_criteria.getRequiresUpdate().booleanValue())
/*      */         {
/* 1924 */           sWhereClause = sWhereClause + "1";
/*      */         }
/*      */         else
/*      */         {
/* 1928 */           sWhereClause = sWhereClause + "0";
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1933 */       if (a_criteria.getIsAdmin() != null)
/*      */       {
/* 1935 */         sWhereClause = sWhereClause + " AND IsAdmin=";
/* 1936 */         if (a_criteria.getIsAdmin().booleanValue())
/*      */         {
/* 1938 */           sWhereClause = sWhereClause + "1";
/*      */         }
/*      */         else
/*      */         {
/* 1942 */           sWhereClause = sWhereClause + "0";
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1947 */       if (a_criteria.getDivisionId() > 0L)
/*      */       {
/* 1949 */         sWhereClause = sWhereClause + " AND divis.Id = " + a_criteria.getDivisionId() + " ";
/*      */       }
/* 1953 */       else if (a_criteria.getDivisionId() == -1L)
/*      */       {
/* 1955 */         sWhereClause = sWhereClause + " AND divis.Id IS NULL ";
/*      */       }
/*      */ 
/* 1960 */       if (a_criteria.getNotActiveDirectory() != null)
/*      */       {
/* 1962 */         sWhereClause = sWhereClause + " AND NotActiveDirectory=";
/* 1963 */         if (a_criteria.getNotActiveDirectory().booleanValue())
/*      */         {
/* 1965 */           sWhereClause = sWhereClause + "1";
/*      */         }
/*      */         else
/*      */         {
/* 1969 */           sWhereClause = sWhereClause + "0";
/*      */         }
/*      */       }
/*      */ 
/* 1973 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/* 1976 */       if ((a_criteria.getForename() != null) && (a_criteria.getForename().trim().length() > 0))
/*      */       {
/* 1978 */         sWhereClause = sWhereClause + " AND " + sqlGenerator.getLowerCaseFunction("u.Forename") + " LIKE ? ";
/*      */       }
/*      */ 
/* 1982 */       if ((a_criteria.getSurname() != null) && (a_criteria.getSurname().trim().length() > 0))
/*      */       {
/* 1984 */         sWhereClause = sWhereClause + " AND " + sqlGenerator.getLowerCaseFunction("u.Surname") + " LIKE ? ";
/*      */       }
/*      */ 
/* 1988 */       if ((a_criteria.getUsername() != null) && (a_criteria.getUsername().trim().length() > 0))
/*      */       {
/* 1990 */         sWhereClause = sWhereClause + " AND " + sqlGenerator.getLowerCaseFunction("u.Username") + " LIKE ? ";
/*      */       }
/*      */ 
/* 1994 */       if ((a_criteria.getEmailAddress() != null) && (a_criteria.getEmailAddress().trim().length() > 0))
/*      */       {
/* 1996 */         sWhereClause = sWhereClause + " AND " + sqlGenerator.getLowerCaseFunction("u.EmailAddress") + " LIKE ? ";
/*      */       }
/*      */ 
/* 1999 */       if ((a_criteria.getOrganisation() != null) && (a_criteria.getOrganisation().trim().length() > 0))
/*      */       {
/* 2001 */         sWhereClause = sWhereClause + " AND " + sqlGenerator.getLowerCaseFunction("u.Organisation") + " LIKE ? ";
/*      */       }
/*      */ 
/* 2005 */       if (a_criteria.getGroupId() > 0L)
/*      */       {
/* 2007 */         sWhereClause = sWhereClause + " AND uig.UserGroupId = " + a_criteria.getGroupId() + " ";
/*      */       }
/*      */ 
/* 2011 */       if (a_criteria.getAdminUserId() > 0L)
/*      */       {
/* 2013 */         sWhereClause = sWhereClause + " AND uiag.UserId = " + a_criteria.getAdminUserId();
/*      */       }
/*      */ 
/* 2016 */       if (a_criteria.getExcludedUserId() > 0L)
/*      */       {
/* 2018 */         sWhereClause = sWhereClause + " AND u.Id != " + a_criteria.getExcludedUserId();
/*      */       }
/*      */ 
/* 2021 */       if ((a_criteria.getGroupIds() != null) && (a_criteria.getGroupIds().size() > 0))
/*      */       {
/* 2023 */         sWhereClause = sWhereClause + " AND uig.UserGroupId IN (" + StringUtil.convertNumbersToString(a_criteria.getGroupIds(), ",") + ") ";
/*      */       }
/*      */ 
/* 2026 */       if (a_criteria.getExpired())
/*      */       {
/* 2028 */         sWhereClause = sWhereClause + " AND ExpiryDate<=?";
/*      */       }
/*      */       else
/*      */       {
/* 2032 */         sWhereClause = sWhereClause + " AND (ExpiryDate>? OR ExpiryDate IS NULL)";
/*      */       }
/*      */ 
/* 2035 */       sSql = sSql + sWhereClause + getSearchOrderSQL(a_iOrderId);
/*      */ 
/* 2037 */       int iCol = 1;
/* 2038 */       PreparedStatement psql = cCon.prepareStatement(sSql);
/*      */ 
/* 2040 */       if ((a_criteria.getForename() != null) && (a_criteria.getForename().trim().length() > 0))
/*      */       {
/* 2042 */         psql.setString(iCol++, a_criteria.getForename().toLowerCase() + "%");
/*      */       }
/* 2044 */       if ((a_criteria.getSurname() != null) && (a_criteria.getSurname().trim().length() > 0))
/*      */       {
/* 2046 */         psql.setString(iCol++, a_criteria.getSurname().toLowerCase() + "%");
/*      */       }
/* 2048 */       if ((a_criteria.getUsername() != null) && (a_criteria.getUsername().trim().length() > 0))
/*      */       {
/* 2050 */         psql.setString(iCol++, a_criteria.getUsername().toLowerCase() + "%");
/*      */       }
/* 2052 */       if ((a_criteria.getEmailAddress() != null) && (a_criteria.getEmailAddress().trim().length() > 0))
/*      */       {
/* 2054 */         psql.setString(iCol++, a_criteria.getEmailAddress().toLowerCase() + "%");
/*      */       }
/* 2056 */       if ((a_criteria.getOrganisation() != null) && (a_criteria.getOrganisation().trim().length() > 0))
/*      */       {
/* 2058 */         psql.setString(iCol++, a_criteria.getOrganisation().toLowerCase() + "%");
/*      */       }
/*      */ 
/* 2061 */       psql.setDate(iCol++, new java.sql.Date(new java.util.Date().getTime()));
/*      */ 
/* 2063 */       ResultSet rs = psql.executeQuery();
/* 2064 */       ABUser user = null;
/* 2065 */       vUsers = new Vector();
/* 2066 */       HashMap hmUser = new HashMap();
/*      */ 
/* 2069 */       while (rs.next())
/*      */       {
/* 2071 */         if (hmUser.containsKey(new Long(rs.getLong("Id"))))
/*      */           continue;
/* 2073 */         user = new ABUser();
/* 2074 */         user.setId(rs.getLong("Id"));
/* 2075 */         user.setDisplayName(rs.getString("DisplayName"));
/* 2076 */         user.setNotApproved(rs.getBoolean("NotApproved"));
/* 2077 */         user.setRequiresUpdate(rs.getBoolean("RequiresUpdate"));
/* 2078 */         user.setRequestedOrgUnitId(rs.getLong("RequestedOrgUnitId"));
/* 2079 */         user.setSurname(rs.getString("Surname"));
/* 2080 */         user.setForename(rs.getString("Forename"));
/* 2081 */         user.setEmailAddress(rs.getString("EmailAddress"));
/* 2082 */         user.setUsername(rs.getString("Username"));
/* 2083 */         user.setIsAdmin(rs.getBoolean("IsAdmin"));
/* 2084 */         user.setAddress(rs.getString("Address"));
/* 2085 */         user.setReceiveAlerts(rs.getBoolean("ReceiveAlerts"));
/* 2086 */         user.setOrganisation(rs.getString("Organisation"));
/* 2087 */         user.setJobTitle(rs.getString("JobTitle"));
/* 2088 */         user.setRemoteUser(!rs.getBoolean("NotActiveDirectory"));
/* 2089 */         user.setRegistrationInfo(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "RegistrationInfo"));
/* 2090 */         user.setRegisterDate(rs.getDate("RegisterDate"));
/* 2091 */         user.setInvitedByUserId(rs.getLong("InvitedByUserId"));
/* 2092 */         user.setAdditionalApproverDetails(rs.getString("AdditionalApprovalName"));
/* 2093 */         user.setExpiryDate(rs.getDate("ExpiryDate"));
/* 2094 */         user.getHomeAddress().setAddressLine1(rs.getString("Address1"));
/* 2095 */         user.getHomeAddress().setAddressLine2(rs.getString("Address2"));
/* 2096 */         user.getHomeAddress().setTown(rs.getString("Town"));
/* 2097 */         user.getHomeAddress().setCounty(rs.getString("County"));
/* 2098 */         user.getHomeAddress().setPostcode(rs.getString("Postcode"));
/*      */ 
/* 2101 */         long lDivisionId = rs.getLong("divId");
/* 2102 */         if (lDivisionId > 0L)
/*      */         {
/* 2104 */           user.getRefDivision().setId(lDivisionId);
/* 2105 */           user.getRefDivision().setDescription(rs.getString("divName"));
/*      */         }
/*      */ 
/* 2108 */         hmUser.put(new Long(user.getId()), null);
/* 2109 */         vUsers.add(user);
/*      */       }
/*      */ 
/* 2113 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       try
/*      */       {
/* 2119 */         cCon.rollback();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*      */       }
/*      */ 
/* 2125 */       this.m_logger.debug("Exception occurred in UserManager:findUsers : " + e);
/* 2126 */       throw new Bn2Exception("Exception occurred in UserManager:findUsers : ", e);
/*      */     }
/*      */     finally
/*      */     {
/* 2131 */       if (cCon != null)
/*      */       {
/*      */         try
/*      */         {
/* 2135 */           cCon.commit();
/* 2136 */           cCon.close();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2141 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2146 */     return vUsers;
/*      */   }
/*      */ 
/*      */   public ABUser getApplicationUser()
/*      */     throws Bn2Exception
/*      */   {
/* 2166 */     UserSearchCriteria criteria = new UserSearchCriteria();
/* 2167 */     criteria.setUsername("application");
/* 2168 */     criteria.setIgnoreHidden(true);
/*      */ 
/* 2170 */     Vector vResults = findUsers(criteria, 0);
/*      */ 
/* 2172 */     if (vResults.size() == 1)
/*      */     {
/* 2174 */       return (ABUser)vResults.get(0);
/*      */     }
/*      */ 
/* 2178 */     return null;
/*      */   }
/*      */ 
/*      */   private String getSearchOrderSQL(int a_iOrderId)
/*      */   {
/* 2198 */     String sOrderBy = " ORDER BY ";
/*      */ 
/* 2200 */     if (a_iOrderId == 2)
/*      */     {
/* 2202 */       sOrderBy = sOrderBy + " u.Surname DESC, u.Forename DESC, u.Username DESC ";
/*      */     }
/* 2204 */     else if (a_iOrderId == 3)
/*      */     {
/* 2206 */       sOrderBy = sOrderBy + " u.Username, u.Surname, u.Forename ";
/*      */     }
/* 2208 */     else if (a_iOrderId == 4)
/*      */     {
/* 2210 */       sOrderBy = sOrderBy + " u.Username DESC, u.Surname DESC, u.Forename DESC ";
/*      */     }
/* 2212 */     else if (a_iOrderId == 5)
/*      */     {
/* 2214 */       sOrderBy = sOrderBy + " u.EmailAddress, u.Surname, u.Forename ";
/*      */     }
/* 2216 */     else if (a_iOrderId == 6)
/*      */     {
/* 2218 */       sOrderBy = sOrderBy + " u.EmailAddress DESC, u.Surname DESC, u.Forename DESC ";
/*      */     }
/* 2220 */     else if (a_iOrderId == 7)
/*      */     {
/* 2222 */       sOrderBy = sOrderBy + " ug.Name, u.Surname, u.Forename ";
/*      */     }
/* 2224 */     else if (a_iOrderId == 8)
/*      */     {
/* 2226 */       sOrderBy = sOrderBy + " ug.Name DESC, u.Surname, u.Forename ";
/*      */     }
/* 2228 */     else if (a_iOrderId == 9)
/*      */     {
/* 2230 */       sOrderBy = sOrderBy + " u.Organisation, u.Surname, u.Forename ";
/*      */     }
/* 2232 */     else if (a_iOrderId == 10)
/*      */     {
/* 2234 */       sOrderBy = sOrderBy + " u.Organisation DESC, u.Surname, u.Forename ";
/*      */     }
/* 2236 */     else if (a_iOrderId == 11)
/*      */     {
/* 2238 */       sOrderBy = sOrderBy + " u.ExpiryDate, u.Surname, u.Forename ";
/*      */     }
/* 2240 */     else if (a_iOrderId == 12)
/*      */     {
/* 2242 */       sOrderBy = sOrderBy + " u.ExpiryDate DESC, u.Surname, u.Forename ";
/*      */     }
/* 2244 */     else if (a_iOrderId == 13)
/*      */     {
/* 2246 */       sOrderBy = sOrderBy + " u.RegisterDate, u.Surname, u.Forename ";
/*      */     }
/* 2248 */     else if (a_iOrderId == 14)
/*      */     {
/* 2250 */       sOrderBy = sOrderBy + " u.RegisterDate DESC, u.Surname, u.Forename ";
/*      */     }
/*      */     else
/*      */     {
/* 2254 */       sOrderBy = sOrderBy + " u.Surname, u.Forename, u.Username ";
/*      */     }
/*      */ 
/* 2257 */     return sOrderBy;
/*      */   }
/*      */ 
/*      */   public Vector getAllGroups()
/*      */     throws Bn2Exception
/*      */   {
/* 2268 */     return searchGroups(null, -1, -1).getSearchResults();
/*      */   }
/*      */ 
/*      */   public SearchResults<Group> searchGroups(String a_sNameFilter, int a_iStartIndex, int a_iMaxResults)
/*      */     throws Bn2Exception
/*      */   {
/* 2289 */     SearchResults results = null;
/* 2290 */     Connection con = null;
/*      */     try
/*      */     {
/* 2294 */       con = this.m_dataSource.getConnection();
/* 2295 */       results = searchGroups(con, false, false, null, a_sNameFilter, a_iStartIndex, a_iMaxResults, -1L, false);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       try
/*      */       {
/* 2301 */         con.rollback();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*      */       }
/*      */ 
/* 2307 */       this.m_logger.debug("Exception occurred in UserMngr:getAllGroups : " + e);
/* 2308 */       throw new Bn2Exception("Exception occurred in UserMngr:getAllGroups : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 2313 */       if (con != null)
/*      */       {
/*      */         try
/*      */         {
/* 2317 */           con.commit();
/* 2318 */           con.close();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2323 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2328 */     return results;
/*      */   }
/*      */ 
/*      */   public Vector<Group> getGroups(Vector<Long> a_groupIds)
/*      */     throws Bn2Exception
/*      */   {
/* 2344 */     Vector groups = null;
/* 2345 */     Connection con = null;
/*      */     try
/*      */     {
/* 2349 */       con = this.m_dataSource.getConnection();
/* 2350 */       SearchResults results = searchGroups(con, false, false, StringUtil.convertNumbersToString(a_groupIds, ","), null, -1, -1, -1L, false);
/* 2351 */       groups = results.getSearchResults();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       try
/*      */       {
/* 2357 */         con.rollback();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*      */       }
/*      */ 
/* 2363 */       this.m_logger.debug("Exception occurred in UserMngr:getAllGroups : " + e);
/* 2364 */       throw new Bn2Exception("Exception occurred in UserMngr:getAllGroups : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 2369 */       if (con != null)
/*      */       {
/*      */         try
/*      */         {
/* 2373 */           con.commit();
/* 2374 */           con.close();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2379 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2384 */     return groups;
/*      */   }
/*      */ 
/*      */   private SearchResults<Group> searchGroups(Connection a_con, boolean a_bBrandedOnly, boolean a_bPublic, String a_ids, String a_sNameFilter, int a_iStartIndex, int a_iMaxResults, long a_lBrandId, boolean a_bAutomaticBrandRegistration)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 2408 */     String sSql = "SELECT ug.Id, ug.Name, ug.IsDefaultGroup, ug.Mapping, ug.IPMapping, ug.URLMapping, ug.DiscountPercentage, ug.CanOnlyEditOwn, ug.CanEmailAssets, ug.CanRepurposeAssets, ug.CanSeeSourcePath, ug.MaxDownloadHeight, ug.MaxDownloadWidth, ug.CanInviteUsers, ug.CanAddEmptyAssets, ug.CanViewLargerSize, ug.AutomaticBrandRegister, ug.AdvancedViewing, ou.Id ouId, ou.DiskQuotaMb ouDiskQuotaMb, ou.AdminGroupId, ou.StandardGroupId, cat.Name ouName, br.Id brId, br.Name brName FROM UserGroup ug LEFT JOIN OrgUnitGroup oug ON oug.UserGroupId = ug.Id LEFT JOIN OrgUnit ou ON oug.OrgUnitId = ou.Id LEFT JOIN CM_Category cat ON ou.RootOrgUnitCategoryId = cat.Id LEFT JOIN Brand br ON ug.BrandId = br.Id WHERE 1=1 ";
/*      */ 
/* 2440 */     if (StringUtils.isNotEmpty(a_sNameFilter))
/*      */     {
/* 2442 */       a_sNameFilter = a_sNameFilter.replaceAll("\\*", "%");
/* 2443 */       a_sNameFilter = a_sNameFilter.replaceAll("'", "''");
/* 2444 */       sSql = sSql + " AND (" + SQLGenerator.getInstance().getLowerCaseFunction("ug.Name") + " LIKE '%" + a_sNameFilter.toLowerCase() + "%' ";
/*      */ 
/* 2446 */       if (AssetBankSettings.getOrgUnitUse())
/*      */       {
/* 2448 */         sSql = sSql + " OR " + SQLGenerator.getInstance().getLowerCaseFunction("cat.Name") + " LIKE '%" + a_sNameFilter.toLowerCase() + "%') ";
/*      */       }
/*      */       else
/*      */       {
/* 2452 */         sSql = sSql + ") ";
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2457 */     if (StringUtils.isNotEmpty(a_ids))
/*      */     {
/* 2459 */       sSql = sSql + " AND ug.Id IN (" + a_ids + ") ";
/*      */     }
/*      */ 
/* 2462 */     if (a_bBrandedOnly)
/*      */     {
/* 2464 */       sSql = sSql + "AND ug.BrandId IS NOT NULL ";
/*      */     }
/*      */ 
/* 2467 */     if (a_lBrandId > 0L)
/*      */     {
/* 2469 */       sSql = sSql + "AND ug.BrandId=" + a_lBrandId + " ";
/*      */     }
/*      */ 
/* 2472 */     if (a_bAutomaticBrandRegistration)
/*      */     {
/* 2474 */       sSql = sSql + "AND ug.AutomaticBrandRegister=1 ";
/*      */     }
/*      */ 
/* 2477 */     if (a_bPublic)
/*      */     {
/* 2479 */       sSql = sSql + "AND ug.IsDefaultGroup=1 ";
/*      */     }
/*      */ 
/* 2482 */     sSql = sSql + "ORDER BY cat.Name, ug.Name, ug.IsDefaultGroup";
/*      */ 
/* 2484 */     PreparedStatement psql = a_con.prepareStatement(sSql);
/*      */ 
/* 2486 */     ResultSet rs = psql.executeQuery();
/*      */ 
/* 2488 */     SearchResults results = new SearchResults();
/*      */ 
/* 2490 */     int iNumResults = 0;
/* 2491 */     int iTotalHits = 0;
/* 2492 */     int iIndex = 0;
/*      */ 
/* 2494 */     while (rs.next())
/*      */     {
/* 2496 */       iTotalHits++;
/*      */ 
/* 2499 */       if (((a_iStartIndex >= 0) && (iIndex++ < a_iStartIndex)) || ((a_iMaxResults > 0) && (iNumResults >= a_iMaxResults)))
/*      */       {
/*      */         continue;
/*      */       }
/*      */ 
/* 2504 */       Group group = new Group();
/*      */ 
/* 2506 */       group.setId(rs.getLong("Id"));
/* 2507 */       group.setName(rs.getString("Name"));
/* 2508 */       group.setIsDefaultGroup(rs.getBoolean("IsDefaultGroup"));
/* 2509 */       group.setMapping(rs.getString("Mapping"));
/* 2510 */       group.setIpMapping(rs.getString("IPMapping"));
/* 2511 */       group.setUrlMapping(rs.getString("URLMapping"));
/* 2512 */       group.setDiscountPercentage(rs.getInt("DiscountPercentage"));
/* 2513 */       group.setUserCanOnlyEditOwnFiles(rs.getBoolean("CanOnlyEditOwn"));
/* 2514 */       group.setUserCanEmailAssets(rs.getBoolean("CanEmailAssets"));
/* 2515 */       group.setUserCanRepurposeAssets(rs.getBoolean("CanRepurposeAssets"));
/* 2516 */       group.setUsersCanSeeSourcePath(rs.getBoolean("CanSeeSourcePath"));
/* 2517 */       group.setMaxDownloadHeight(rs.getInt("MaxDownloadHeight"));
/* 2518 */       group.setMaxDownloadWidth(rs.getInt("MaxDownloadWidth"));
/* 2519 */       group.setUsersCanInviteUsers(rs.getBoolean("CanInviteUsers"));
/* 2520 */       group.setUsersCanInviteUsers(rs.getBoolean("CanAddEmptyAssets"));
/* 2521 */       group.setUsersCanViewLargerSize(rs.getBoolean("CanViewLargerSize"));
/* 2522 */       group.setAutomaticBrandRegister(rs.getBoolean("AutomaticBrandRegister"));
/* 2523 */       group.setAdvancedViewing(rs.getBoolean("AdvancedViewing"));
/*      */ 
/* 2525 */       long lOrgUnitId = rs.getLong("ouId");
/* 2526 */       if (lOrgUnitId > 0L)
/*      */       {
/* 2528 */         OrgUnitMetadata org = buildOrgUnitMetadata(null, rs, false);
/* 2529 */         group.setOrgUnitReference(org);
/*      */ 
/* 2531 */         if ((rs.getLong("AdminGroupId") == group.getId()) || (rs.getLong("StandardGroupId") == group.getId()))
/*      */         {
/* 2534 */           group.setCanDelete(false);
/*      */         }
/*      */ 
/* 2537 */         if (rs.getLong("AdminGroupId") == group.getId())
/*      */         {
/* 2539 */           group.setOrgUnitAdminGroup(true);
/*      */         }
/*      */       }
/*      */ 
/* 2543 */       long lBrandId = rs.getLong("brId");
/* 2544 */       if (lBrandId > 0L)
/*      */       {
/* 2546 */         group.getBrandReference().setId(lBrandId);
/* 2547 */         group.getBrandReference().setName(rs.getString("brName"));
/*      */       }
/*      */ 
/* 2550 */       results.getSearchResults().add(group);
/*      */ 
/* 2552 */       iNumResults++;
/*      */     }
/*      */ 
/* 2555 */     psql.close();
/*      */ 
/* 2557 */     results.setNumResults(iTotalHits);
/* 2558 */     results.setPageSize(a_iMaxResults);
/* 2559 */     results.setPageIndex((int)Math.floor(a_iStartIndex / a_iMaxResults));
/*      */ 
/* 2561 */     return results;
/*      */   }
/*      */ 
/*      */   public void deleteUser(DBTransaction a_dbTransaction, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 2587 */     String ksMethodName = "deleteUser";
/* 2588 */     Connection con = null;
/*      */     try
/*      */     {
/* 2592 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2594 */       String sSql = null;
/*      */ 
/* 2596 */       PreparedStatement psql = null;
/* 2597 */       PreparedStatement psql2 = null;
/* 2598 */       ResultSet rs = null;
/*      */ 
/* 2603 */       sSql = "SELECT * FROM AssetBox WHERE UserId=?";
/* 2604 */       psql = con.prepareStatement(sSql);
/* 2605 */       psql.setLong(1, a_lUserId);
/* 2606 */       rs = psql.executeQuery();
/*      */ 
/* 2608 */       while (rs.next())
/*      */       {
/* 2610 */         long lAssetBoxId = rs.getLong("Id");
/*      */ 
/* 2612 */         String[] aSQL = { "DELETE FROM AssetBoxAsset WHERE AssetBoxId=?", "DELETE FROM AssetBoxPriceBand WHERE AssetBoxId=?", "DELETE FROM AssetBoxShare WHERE AssetBoxId=?" };
/*      */ 
/* 2616 */         for (int i = 0; i < aSQL.length; i++)
/*      */         {
/* 2618 */           psql2 = con.prepareStatement(aSQL[i]);
/* 2619 */           psql2.setLong(1, lAssetBoxId);
/* 2620 */           psql2.executeUpdate();
/* 2621 */           psql2.close();
/*      */         }
/*      */       }
/*      */ 
/* 2625 */       psql.close();
/*      */ 
/* 2629 */       sSql = "DELETE FROM AssetBox WHERE UserId=?";
/* 2630 */       psql = con.prepareStatement(sSql);
/* 2631 */       psql.setLong(1, a_lUserId);
/* 2632 */       psql.executeUpdate();
/* 2633 */       psql.close();
/*      */ 
/* 2635 */       sSql = "DELETE FROM AssetBoxShare WHERE UserId=?";
/* 2636 */       psql = con.prepareStatement(sSql);
/* 2637 */       psql.setLong(1, a_lUserId);
/* 2638 */       psql.executeUpdate();
/* 2639 */       psql.close();
/*      */ 
/* 2641 */       sSql = "UPDATE AssetBoxAsset SET AddedByUserId=null WHERE AddedByUserId=?";
/* 2642 */       psql = con.prepareStatement(sSql);
/* 2643 */       psql.setLong(1, a_lUserId);
/* 2644 */       psql.executeUpdate();
/* 2645 */       psql.close();
/*      */ 
/* 2648 */       sSql = "UPDATE AssetBankUser SET LastModifiedById=null WHERE LastModifiedById=?";
/* 2649 */       psql = con.prepareStatement(sSql);
/* 2650 */       psql.setLong(1, a_lUserId);
/* 2651 */       psql.executeUpdate();
/* 2652 */       psql.close();
/*      */ 
/* 2655 */       this.m_orderManager.deleteOrdersForUser(a_dbTransaction, a_lUserId);
/*      */ 
/* 2659 */       long lAddressId = 0L;
/* 2660 */       sSql = "SELECT AddressId FROM AssetBankUser WHERE Id=?";
/* 2661 */       psql = con.prepareStatement(sSql);
/* 2662 */       psql.setLong(1, a_lUserId);
/* 2663 */       rs = psql.executeQuery();
/* 2664 */       while (rs.next())
/*      */       {
/* 2666 */         lAddressId = rs.getLong("AddressId");
/*      */       }
/* 2668 */       psql.close();
/*      */ 
/* 2672 */       String[] aSQL = { "DELETE FROM AssetApproval WHERE UserId=?", "DELETE FROM UserInGroup WHERE UserId=?", "DELETE FROM UserInMarketingGroup WHERE UserId=?", "DELETE FROM Subscription WHERE UserID=?", "DELETE FROM SavedSearch WHERE UserId=?", "UPDATE SessionLog SET UserId = NULL WHERE UserId=?", "UPDATE Asset SET AddedByUserId = NULL WHERE AddedByUserId=?", "UPDATE Asset SET LastModifiedByUserId = NULL WHERE LastModifiedByUserId=?", "UPDATE AssetWorkflowAudit SET UserId = NULL WHERE UserId = ?", "UPDATE AssetBankUser SET InvitedByUserId = NULL WHERE InvitedByUserId = ?" };
/*      */ 
/* 2684 */       for (int i = 0; i < aSQL.length; i++)
/*      */       {
/* 2686 */         psql = con.prepareStatement(aSQL[i]);
/* 2687 */         psql.setLong(1, a_lUserId);
/* 2688 */         psql.executeUpdate();
/* 2689 */         psql.close();
/*      */       }
/*      */ 
/* 2693 */       if (!AssetBankSettings.getAuditLogEnabled())
/*      */       {
/* 2695 */         sSql = "DELETE FROM AssetView WHERE UserId=?";
/* 2696 */         psql = con.prepareStatement(sSql);
/* 2697 */         psql.setLong(1, a_lUserId);
/* 2698 */         psql.executeUpdate();
/* 2699 */         psql.close();
/*      */       }
/*      */ 
/* 2703 */       if (!AssetBankSettings.getAuditLogEnabled())
/*      */       {
/* 2705 */         sSql = "DELETE FROM AssetUse WHERE UserId=?";
/* 2706 */         psql = con.prepareStatement(sSql);
/* 2707 */         psql.setLong(1, a_lUserId);
/* 2708 */         psql.executeUpdate();
/* 2709 */         psql.close();
/*      */       }
/*      */ 
/* 2714 */       if (!AssetBankSettings.getAuditLogEnabled())
/*      */       {
/* 2716 */         sSql = "DELETE FROM AssetBankUser WHERE Id=?";
/*      */       }
/*      */       else
/*      */       {
/* 2720 */         sSql = "UPDATE AssetBankUser SET Hidden=1, AddressId=NULL WHERE Id=?";
/*      */       }
/*      */ 
/* 2723 */       psql = con.prepareStatement(sSql);
/* 2724 */       psql.setLong(1, a_lUserId);
/* 2725 */       psql.executeUpdate();
/* 2726 */       psql.close();
/*      */ 
/* 2729 */       if (AssetBankSettings.getAuditLogEnabled())
/*      */       {
/* 2731 */         String sUsername = null;
/*      */ 
/* 2734 */         sSql = "SELECT Username FROM AssetBankUser WHERE Id=?";
/* 2735 */         psql = con.prepareStatement(sSql);
/* 2736 */         psql.setLong(1, a_lUserId);
/* 2737 */         rs = psql.executeQuery();
/* 2738 */         if (rs.next())
/*      */         {
/* 2740 */           sUsername = rs.getString("Username");
/*      */         }
/* 2742 */         psql.close();
/*      */ 
/* 2745 */         String sNewUsername = sUsername + " (deleted on " + new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date()) + ")";
/*      */ 
/* 2748 */         sSql = "Update AssetBankUser SET Username=? WHERE Id=?";
/* 2749 */         psql = con.prepareStatement(sSql);
/* 2750 */         psql.setString(1, sNewUsername);
/* 2751 */         psql.setLong(2, a_lUserId);
/* 2752 */         psql.executeUpdate();
/* 2753 */         psql.close();
/*      */       }
/*      */ 
/* 2757 */       this.m_addressManager.deleteAddress(a_dbTransaction, lAddressId);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2762 */       this.m_logger.error("ABUserManager.deleteUser : SQL Exception whilst trying to delete user : " + e);
/* 2763 */       throw new Bn2Exception("ABUserManager.deleteUser : SQL Exception whilst trying delete user : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public User saveUser(DBTransaction a_dbTransaction, User a_user)
/*      */     throws Bn2Exception
/*      */   {
/* 2799 */     String ksMethodName = "saveUser";
/* 2800 */     Connection con = null;
/* 2801 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 2803 */     if (transaction == null)
/*      */     {
/* 2805 */       transaction = getDBTransactionManager().getNewTransaction();
/*      */     }
/*      */ 
/* 2808 */     ABUser user = (ABUser)a_user;
/*      */ 
/* 2811 */     user.setPasswordChangedDate(new java.util.Date());
/*      */     try
/*      */     {
/* 2815 */       con = transaction.getConnection();
/*      */ 
/* 2818 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/* 2820 */       String sSql = null;
/* 2821 */       long lNewId = 0L;
/*      */ 
/* 2823 */       PreparedStatement psql = null;
/*      */ 
/* 2826 */       if (isUsernameInUse(transaction, user.getUsername(), user.getId()))
/*      */       {
/* 2828 */         this.m_logger.debug("ABUserManager:saveUser: Username already taken - have not added user");
/* 2829 */         throw new UsernameInUseException("ABUserManager:saveUser: Username already taken - have not added user " + user.getUsername());
/*      */       }
/*      */ 
/* 2833 */       this.m_addressManager.saveAddress(transaction, user.getHomeAddress());
/*      */ 
/* 2836 */       if (user.getId() <= 0L)
/*      */       {
/* 2840 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 2842 */           lNewId = sqlGenerator.getUniqueId(con, "UserSequence");
/*      */         }
/*      */ 
/* 2847 */         sSql = "INSERT INTO AssetBankUser ( ";
/*      */ 
/* 2849 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 2851 */           sSql = sSql + "Id, ";
/*      */         }
/*      */ 
/* 2854 */         sSql = sSql + "Forename, Surname, Username, Password, IsAdmin, EmailAddress, Title, Organisation, Address, RegistrationInfo, IsSuspended, DisplayName, CN, DistinguishedName, Mailbox, NotActiveDirectory, Hidden, IsDeleted, NotApproved, RequiresUpdate, RequestedOrgUnitId, CanBeCategoryAdmin, ExpiryDate, RegisterDate, JobTitle, VATNumber, DivisionId, AddressId, LDAPServerIndex, ReceiveAlerts, CanLoginFromCms, DateChangedPassword, AdminNotes, LastModifiedById, LanguageId, InvitedByUserId, AdditionalApprovalName,DateLastLoggedIn) VALUES (";
/*      */ 
/* 2895 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 2897 */           sSql = sSql + "?,";
/*      */         }
/*      */ 
/* 2900 */         sSql = sSql + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */ 
/* 2902 */         psql = con.prepareStatement(sSql);
/*      */ 
/* 2904 */         int iField = 1;
/*      */ 
/* 2906 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 2908 */           psql.setLong(iField++, lNewId);
/*      */         }
/*      */ 
/* 2911 */         psql.setString(iField++, user.getForename());
/* 2912 */         psql.setString(iField++, user.getSurname());
/* 2913 */         psql.setString(iField++, user.getUsername());
/* 2914 */         psql.setString(iField++, hashIfNecessary(user.getPassword()));
/* 2915 */         psql.setBoolean(iField++, user.getIsAdmin());
/* 2916 */         psql.setString(iField++, user.getEmailAddress());
/* 2917 */         psql.setString(iField++, user.getTitle());
/* 2918 */         psql.setString(iField++, user.getOrganisation());
/* 2919 */         psql.setString(iField++, user.getAddress());
/* 2920 */         psql.setString(iField++, user.getRegistrationInfo());
/* 2921 */         psql.setBoolean(iField++, user.getIsSuspended());
/* 2922 */         psql.setString(iField++, user.getDisplayName());
/* 2923 */         psql.setString(iField++, user.getCommonName());
/* 2924 */         psql.setString(iField++, user.getRemoteUsername());
/* 2925 */         psql.setString(iField++, user.getMailbox());
/* 2926 */         psql.setBoolean(iField++, !user.isRemoteUser());
/* 2927 */         psql.setBoolean(iField++, user.getHidden());
/* 2928 */         psql.setBoolean(iField++, user.getIsDeleted());
/* 2929 */         psql.setBoolean(iField++, user.getNotApproved());
/* 2930 */         psql.setBoolean(iField++, user.getRequiresUpdate());
/* 2931 */         DBUtil.setFieldIdOrNull(psql, iField++, user.getRequestedOrgUnitId());
/* 2932 */         psql.setBoolean(iField++, user.getCanBeCategoryAdmin());
/* 2933 */         DBUtil.setFieldDateOrNull(psql, iField++, user.getExpiryDate());
/* 2934 */         DBUtil.setFieldDateOrNull(psql, iField++, user.getRegisterDate());
/* 2935 */         psql.setString(iField++, user.getJobTitle());
/* 2936 */         psql.setString(iField++, user.getTaxNumber());
/* 2937 */         DBUtil.setFieldIdOrNull(psql, iField++, user.getRefDivision().getId());
/* 2938 */         DBUtil.setFieldIdOrNull(psql, iField++, user.getHomeAddress().getId());
/* 2939 */         psql.setInt(iField++, user.getRemoteServerIndex());
/* 2940 */         psql.setBoolean(iField++, user.getReceiveAlerts());
/* 2941 */         psql.setBoolean(iField++, user.getCanLoginFromCms());
/* 2942 */         DBUtil.setFieldDateOrNull(psql, iField++, user.getPasswordChangedDate());
/* 2943 */         psql.setString(iField++, user.getAdminNotes());
/* 2944 */         psql.setNull(iField++, -5);
/* 2945 */         psql.setLong(iField++, user.getLanguage().getId());
/* 2946 */         DBUtil.setFieldIdOrNull(psql, iField++, user.getInvitedByUserId());
/* 2947 */         psql.setString(iField++, user.getAdditionalApproverDetails());
/* 2948 */         DBUtil.setFieldTimestampOrNull(psql, iField++, new java.util.Date());
/*      */ 
/* 2950 */         psql.executeUpdate();
/* 2951 */         psql.close();
/*      */ 
/* 2954 */         if (sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 2956 */           lNewId = sqlGenerator.getUniqueId(con, "AssetBankUser");
/*      */         }
/*      */ 
/* 2960 */         user.setId(lNewId);
/*      */ 
/* 2963 */         ListItem listItem = this.m_listManager.getListItem(transaction, "my-lightbox", a_user.getLanguage().getId());
/* 2964 */         long lNewAssetBoxId = this.m_assetBoxManager.createAssetBox(transaction, user.getId(), listItem.getBody());
/*      */ 
/* 2966 */         user.setNewAssetBoxId(lNewAssetBoxId);
/*      */ 
/* 2969 */         addUserToLoggedInUsersGroup(transaction, user.getId());
/*      */ 
/* 2972 */         if ((user.getIsLocalUser()) && (AssetBankSettings.getDefaultLocalGroupId() > 0L))
/*      */         {
/* 2974 */           addUserToGroup(con, lNewId, AssetBankSettings.getDefaultLocalGroupId());
/*      */         }
/* 2976 */         else if ((!user.getIsLocalUser()) && (AssetBankSettings.getDefaultExternalGroupId() > 0L))
/*      */         {
/* 2978 */           addUserToGroup(con, lNewId, AssetBankSettings.getDefaultExternalGroupId());
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 2983 */         int iField = 1;
/*      */ 
/* 2986 */         sSql = "UPDATE AssetBankUser SET Forename = ?, Surname = ?, Username = ?, IsAdmin = ?, EmailAddress = ?, Title=?, Organisation=?, Address=?, RegistrationInfo=?, IsSuspended = ?, DisplayName = ?, CN = ?, DistinguishedName = ?, Mailbox = ?, NotActiveDirectory = ?, Hidden = ?, IsDeleted = ?, NotApproved = ?, RequiresUpdate = ?, RequestedOrgUnitId = ?, CanBeCategoryAdmin = ?, ExpiryDate = ?, RegisterDate = ?, JobTitle = ?, VATNumber = ?, DivisionId = ?, AddressId = ?, LDAPServerIndex = ?, ReceiveAlerts = ?, CanLoginFromCms = ?, AdminNotes = ?, LastModifiedById = ?, LanguageId = ?, ReactivationPending = ? WHERE Id = ? ";
/*      */ 
/* 3022 */         psql = con.prepareStatement(sSql);
/* 3023 */         psql.setString(iField++, user.getForename());
/* 3024 */         psql.setString(iField++, user.getSurname());
/* 3025 */         psql.setString(iField++, user.getUsername());
/* 3026 */         psql.setBoolean(iField++, user.getIsAdmin());
/* 3027 */         psql.setString(iField++, user.getEmailAddress());
/* 3028 */         psql.setString(iField++, user.getTitle());
/* 3029 */         psql.setString(iField++, user.getOrganisation());
/* 3030 */         psql.setString(iField++, user.getAddress());
/* 3031 */         psql.setString(iField++, user.getRegistrationInfo());
/* 3032 */         psql.setBoolean(iField++, user.getIsSuspended());
/* 3033 */         psql.setString(iField++, user.getDisplayName());
/* 3034 */         psql.setString(iField++, user.getCommonName());
/* 3035 */         psql.setString(iField++, user.getRemoteUsername());
/* 3036 */         psql.setString(iField++, user.getMailbox());
/* 3037 */         psql.setBoolean(iField++, !user.isRemoteUser());
/* 3038 */         psql.setBoolean(iField++, user.getHidden());
/* 3039 */         psql.setBoolean(iField++, user.getIsDeleted());
/* 3040 */         psql.setBoolean(iField++, user.getNotApproved());
/* 3041 */         psql.setBoolean(iField++, user.getRequiresUpdate());
/* 3042 */         DBUtil.setFieldIdOrNull(psql, iField++, user.getRequestedOrgUnitId());
/* 3043 */         psql.setBoolean(iField++, user.getCanBeCategoryAdmin());
/* 3044 */         DBUtil.setFieldDateOrNull(psql, iField++, user.getExpiryDate());
/* 3045 */         DBUtil.setFieldDateOrNull(psql, iField++, user.getRegisterDate());
/* 3046 */         psql.setString(iField++, user.getJobTitle());
/* 3047 */         psql.setString(iField++, user.getTaxNumber());
/* 3048 */         DBUtil.setFieldIdOrNull(psql, iField++, user.getRefDivision().getId());
/* 3049 */         DBUtil.setFieldIdOrNull(psql, iField++, user.getHomeAddress().getId());
/* 3050 */         psql.setInt(iField++, user.getRemoteServerIndex());
/* 3051 */         psql.setBoolean(iField++, user.getReceiveAlerts());
/* 3052 */         psql.setBoolean(iField++, user.getCanLoginFromCms());
/* 3053 */         psql.setString(iField++, user.getAdminNotes());
/* 3054 */         DBUtil.setFieldIdOrNull(psql, iField++, user.getLastModifiedBy());
/* 3055 */         psql.setLong(iField++, user.getLanguage().getId());
/* 3056 */         psql.setBoolean(iField++, user.getReactivationPending());
/*      */ 
/* 3058 */         psql.setLong(iField++, user.getId());
/*      */ 
/* 3060 */         psql.executeUpdate();
/* 3061 */         psql.close();
/*      */ 
/* 3065 */         if (!this.m_assetBoxManager.getUserHasAssetBox(transaction, user.getId()))
/*      */         {
/* 3067 */           this.m_assetBoxManager.createAssetBox(transaction, user.getId(), "my-lightbox");
/*      */         }
/*      */ 
/* 3071 */         addUserToLoggedInUsersGroup(transaction, user.getId());
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 3077 */       if (a_dbTransaction == null)
/*      */       {
/*      */         try
/*      */         {
/* 3081 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3089 */       this.m_logger.error("ABUserManager.saveUser : SQL Exception whilst trying to save user : " + e);
/* 3090 */       throw new Bn2Exception("ABUserManager.saveUser : SQL Exception whilst trying save user : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 3095 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 3099 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 3103 */           this.m_logger.error("Sorry, SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/* 3107 */     return user;
/*      */   }
/*      */ 
/*      */   public boolean isUsernameInUse(DBTransaction a_dbTransaction, String a_sUsername)
/*      */     throws Bn2Exception
/*      */   {
/* 3126 */     return isUsernameInUse(a_dbTransaction, a_sUsername, 0L);
/*      */   }
/*      */ 
/*      */   private boolean isUsernameInUse(DBTransaction a_dbTransaction, String a_sUsername, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 3146 */     String ksMethodName = "isUsernameInUse";
/* 3147 */     Connection con = null;
/* 3148 */     boolean bInUse = false;
/*      */     try
/*      */     {
/* 3152 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 3154 */       String sSql = "SELECT Id FROM AssetBankUser WHERE Username = ? AND IsDeleted=0";
/*      */ 
/* 3156 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 3157 */       psql.setString(1, a_sUsername);
/* 3158 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 3161 */       if (rs.next())
/*      */       {
/* 3163 */         if (rs.getLong("Id") != a_lUserId)
/*      */         {
/* 3165 */           bInUse = true;
/*      */         }
/*      */       }
/*      */ 
/* 3169 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 3174 */       this.m_logger.error("ABUserManager.isUsernameInUse : SQL Exception  : " + e);
/* 3175 */       throw new Bn2Exception("ABUserManager.isUsernameInUse : SQL Exception : " + e, e);
/*      */     }
/*      */ 
/* 3178 */     return bInUse;
/*      */   }
/*      */ 
/*      */   public Vector getUpdateableCategoryIds(DBTransaction a_dbTransaction, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 3198 */     Connection con = null;
/* 3199 */     ResultSet rs = null;
/* 3200 */     DBTransaction transaction = a_dbTransaction;
/* 3201 */     String sSql = null;
/* 3202 */     PreparedStatement psql = null;
/* 3203 */     Vector vIds = null;
/*      */ 
/* 3205 */     if (transaction == null)
/*      */     {
/* 3207 */       transaction = getDBTransactionManager().getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 3212 */       con = transaction.getConnection();
/*      */ 
/* 3214 */       sSql = "SELECT cvg.CategoryId FROM CategoryVisibleToGroup cvg INNER JOIN UserInGroup uig ON (uig.UserId=? AND cvg.UserGroupId = uig.UserGroupId) WHERE cvg.CanUpdateAssets=1 GROUP BY cvg.CategoryId";
/*      */ 
/* 3221 */       psql = con.prepareStatement(sSql);
/* 3222 */       psql.setLong(1, a_lUserId);
/* 3223 */       rs = psql.executeQuery();
/*      */ 
/* 3225 */       vIds = new Vector();
/*      */ 
/* 3227 */       while (rs.next())
/*      */       {
/* 3229 */         vIds.add(new Long(rs.getLong("CategoryId")));
/*      */       }
/*      */ 
/* 3232 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       try
/*      */       {
/* 3238 */         con.rollback();
/*      */       }
/*      */       catch (SQLException rbe)
/*      */       {
/*      */       }
/*      */ 
/* 3244 */       this.m_logger.error("UserManager.getUpdateableCategories: SQL Exception whilst deleting user: " + e);
/* 3245 */       throw new Bn2Exception("UserManager.getUpdateableCategories: SQL Exception whilst deleting user: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 3250 */       if (con != null)
/*      */       {
/*      */         try
/*      */         {
/* 3254 */           con.commit();
/* 3255 */           con.close();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 3259 */           this.m_logger.error("UserManager.deleteUser: SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 3264 */     return vIds;
/*      */   }
/*      */ 
/*      */   public int getUserCountForGroups(DBTransaction a_dbTransaction, Vector a_vecGroupIds)
/*      */     throws Bn2Exception
/*      */   {
/* 3282 */     Connection cCon = null;
/* 3283 */     int iCount = 0;
/*      */ 
/* 3285 */     this.m_logger.debug("In UserManager.getUserCountForGroups");
/*      */     try
/*      */     {
/* 3289 */       if ((a_vecGroupIds != null) && (a_vecGroupIds.size() > 0))
/*      */       {
/* 3291 */         if (a_dbTransaction != null)
/*      */         {
/* 3293 */           cCon = a_dbTransaction.getConnection();
/*      */         }
/*      */         else
/*      */         {
/* 3297 */           cCon = this.m_dataSource.getConnection();
/*      */         }
/*      */ 
/* 3300 */         String sSql = "SELECT DISTINCT UserId FROM UserInGroup WHERE UserGroupId IN (";
/*      */ 
/* 3302 */         for (int i = 0; i < a_vecGroupIds.size(); i++)
/*      */         {
/* 3304 */           sSql = sSql + ((Long)a_vecGroupIds.elementAt(i)).longValue() + ",";
/*      */         }
/* 3306 */         sSql = sSql.substring(0, sSql.length() - 1);
/* 3307 */         sSql = sSql + ")";
/*      */ 
/* 3309 */         PreparedStatement psql = cCon.prepareStatement(sSql);
/* 3310 */         ResultSet rs = psql.executeQuery();
/*      */ 
/* 3312 */         while (rs.next())
/*      */         {
/* 3314 */           iCount++;
/*      */         }
/*      */ 
/* 3317 */         psql.close();
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       try
/*      */       {
/* 3324 */         if (a_dbTransaction == null)
/*      */         {
/* 3326 */           cCon.rollback();
/*      */         }
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*      */       }
/*      */ 
/* 3333 */       this.m_logger.debug("Exception occurred in UserManager.getUserCountForGroups : " + e);
/* 3334 */       throw new Bn2Exception("Exception occurred in UserManager.getUserCountForGroups : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 3339 */       if (a_dbTransaction == null)
/*      */       {
/* 3341 */         if (cCon != null)
/*      */         {
/*      */           try
/*      */           {
/* 3345 */             cCon.commit();
/* 3346 */             cCon.close();
/*      */           }
/*      */           catch (SQLException sqle)
/*      */           {
/* 3351 */             this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 3356 */     return iCount;
/*      */   }
/*      */ 
/*      */   public Group getGroup(long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 3369 */     return getGroup(null, a_lId);
/*      */   }
/*      */ 
/*      */   public Group getGroup(DBTransaction a_transaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 3396 */     this.m_logger.debug("In UserManager.getGroup");
/*      */ 
/* 3398 */     DBTransaction transaction = a_transaction;
/*      */ 
/* 3400 */     if (transaction == null)
/*      */     {
/* 3402 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/* 3405 */     String sSQL = null;
               CategoryImpl category;
/*      */     try
/*      */     {
/* 3408 */       Connection con = transaction.getConnection();
/*      */ 
/* 3410 */       sSQL = "SELECT ug.Id ugId, ug.Name ugName, ug.Description, ug.DailyDownloadLimit, ug.IsDefaultGroup, ug.Mapping ugMapping, ug.IPMapping ugIPMapping, ug.URLMapping ugURLMapping, ug.DiscountPercentage, ug.CanOnlyEditOwn, ug.CanEmailAssets, ug.CanRepurposeAssets, ug.CanSeeSourcePath, ug.MaxDownloadHeight, ug.MaxDownloadWidth, ug.CanInviteUsers, ug.CanAddEmptyAssets, ug.CanViewLargerSize, ug.AutomaticBrandRegister, ug.AdvancedViewing, c.Id cId, c.Name cName, c.Summary, c.CategoryTypeId, c.IsBrowsable, cvg.CanUpdateAssets, cvg.CanDownloadAssets, cvg.CanDownloadWithApproval, cvg.CanUpdateWithApproval, cvg.CanDeleteAssets, cvg.CanApproveAssets, cvg.CanApproveAssetUploads, cvg.CanViewRestrictedAssets, cvg.CantDownloadOriginal, cvg.CantDownloadAdvanced, cvg.CantReviewAssets, cvg.CanEditSubcategories, cvg.ApprRequiredForHighRes, ou.Id ouId, ou.DiskQuotaMb ouDiskQuotaMb, ou.AdminGroupId, cat.Name ouName, br.Id brId, br.Name brName FROM UserGroup ug LEFT JOIN CategoryVisibleToGroup cvg ON ug.Id = cvg.UserGroupId LEFT JOIN CM_Category c ON cvg.CategoryId = c.Id LEFT JOIN OrgUnitGroup oug ON oug.UserGroupId = ug.Id LEFT JOIN OrgUnit ou ON oug.OrgUnitId = ou.Id LEFT JOIN CM_Category cat ON ou.RootOrgUnitCategoryId = cat.Id LEFT JOIN Brand br ON ug.BrandId = br.Id WHERE ug.Id=?";
/*      */ 
/* 3463 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/* 3465 */       psql.setLong(1, a_lId);
/*      */ 
/* 3467 */       ResultSet rs = psql.executeQuery();
/* 3468 */       Vector vecPermissions = null;
/*      */ 
/* 3470 */       Group group = null;
/*      */ 
/* 3472 */       while (rs.next())
/*      */       {
/* 3475 */         if (group == null)
/*      */         {
/* 3477 */           group = new Group();
/* 3478 */           group.setId(rs.getLong("ugId"));
/* 3479 */           group.setName(rs.getString("ugName"));
/* 3480 */           group.setDescription(rs.getString("Description"));
/* 3481 */           group.setMaxDownloads(rs.getLong("DailyDownloadLimit"));
/* 3482 */           group.setIsDefaultGroup(rs.getBoolean("IsDefaultGroup"));
/* 3483 */           group.setMapping(rs.getString("ugMapping"));
/* 3484 */           group.setIpMapping(rs.getString("ugIPMapping"));
/* 3485 */           group.setUrlMapping(rs.getString("ugURLMapping"));
/* 3486 */           group.setDiscountPercentage(rs.getInt("DiscountPercentage"));
/* 3487 */           group.setUserCanOnlyEditOwnFiles(rs.getBoolean("CanOnlyEditOwn"));
/* 3488 */           group.setUserCanEmailAssets(rs.getBoolean("CanEmailAssets"));
/* 3489 */           group.setUserCanRepurposeAssets(rs.getBoolean("CanRepurposeAssets"));
/* 3490 */           group.setUsersCanSeeSourcePath(rs.getBoolean("CanSeeSourcePath"));
/* 3491 */           group.setMaxDownloadHeight(rs.getInt("MaxDownloadHeight"));
/* 3492 */           group.setMaxDownloadWidth(rs.getInt("MaxDownloadWidth"));
/* 3493 */           group.setUsersCanInviteUsers(rs.getBoolean("CanInviteUsers"));
/* 3494 */           group.setUsersCanAddEmptyAssets(rs.getBoolean("CanAddEmptyAssets"));
/* 3495 */           group.setUsersCanViewLargerSize(rs.getBoolean("CanViewLargerSize"));
/* 3496 */           group.setAutomaticBrandRegister(rs.getBoolean("AutomaticBrandRegister"));
/* 3497 */           group.setAdvancedViewing(rs.getBoolean("AdvancedViewing"));
/*      */ 
/* 3499 */           long lOrgUnitId = rs.getLong("ouId");
/* 3500 */           if (lOrgUnitId > 0L)
/*      */           {
/* 3502 */             OrgUnitMetadata org = buildOrgUnitMetadata(a_transaction, rs, false);
/* 3503 */             group.setOrgUnitReference(org);
/*      */           }
/*      */ 
/* 3506 */           if (rs.getLong("AdminGroupId") == group.getId())
/*      */           {
/* 3508 */             group.setOrgUnitAdminGroup(true);
/*      */           }
/*      */ 
/* 3511 */           long lBrandId = rs.getLong("brId");
/* 3512 */           if (lBrandId > 0L)
/*      */           {
/* 3514 */             group.getBrandReference().setId(lBrandId);
/* 3515 */             group.getBrandReference().setName(rs.getString("brName"));
/*      */           }
/*      */ 
/* 3519 */           if (AssetBankSettings.getUseGroupRoles())
/*      */           {
/* 3522 */             group.setRoles(this.m_roleManager.getRoles(transaction, group.getId()));
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 3528 */         if (rs.getLong("cId") <= 0L) {
/*      */           continue;
/*      */         }
/* 3531 */         category = new CategoryImpl();
/* 3532 */         category.setId(rs.getLong("cId"));
/* 3533 */         category.setCategoryTypeId(rs.getLong("CategoryTypeId"));
/* 3534 */         category.setName(rs.getString("cName"));
/* 3535 */         category.setSummary(rs.getString("Summary"));
/* 3536 */         category.setIsBrowsable(rs.getBoolean("IsBrowsable"));
/*      */ 
/* 3538 */         GroupCategoryPermission groupPermission = new GroupCategoryPermission();
/* 3539 */         groupPermission.setCategory(category);
/*      */ 
/* 3542 */         groupPermission.setDownloadPermissionLevel(1);
/*      */ 
/* 3544 */         if (rs.getBoolean("CanUpdateWithApproval"))
/*      */         {
/* 3546 */           groupPermission.setUploadPermissionLevel(6);
/*      */         }
/*      */ 
/* 3549 */         if (rs.getBoolean("CanUpdateAssets"))
/*      */         {
/* 3551 */           groupPermission.setUploadPermissionLevel(3);
/*      */         }
/*      */ 
/* 3554 */         if (rs.getBoolean("CanDeleteAssets"))
/*      */         {
/* 3556 */           groupPermission.setUploadPermissionLevel(4);
/*      */         }
/*      */ 
/* 3559 */         if (rs.getBoolean("CanApproveAssetUploads"))
/*      */         {
/* 3561 */           groupPermission.setUploadPermissionLevel(12);
/*      */         }
/*      */ 
/* 3564 */         if (rs.getBoolean("CanDownloadWithApproval"))
/*      */         {
/* 3566 */           groupPermission.setDownloadPermissionLevel(5);
/*      */         }
/*      */ 
/* 3569 */         if (rs.getBoolean("CanDownloadAssets"))
/*      */         {
/* 3571 */           groupPermission.setDownloadPermissionLevel(2);
/*      */         }
/*      */ 
/* 3574 */         if (rs.getBoolean("CanApproveAssets"))
/*      */         {
/* 3576 */           groupPermission.setDownloadPermissionLevel(7);
/*      */         }
/*      */ 
/* 3580 */         groupPermission.setCanViewRestrictedAssets(rs.getBoolean("CanViewRestrictedAssets"));
/* 3581 */         groupPermission.setCanDownloadOriginal(!rs.getBoolean("CantDownloadOriginal"));
/* 3582 */         groupPermission.setCanDownloadAdvanced(!rs.getBoolean("CantDownloadAdvanced"));
/* 3583 */         groupPermission.setCanReviewAssets(!rs.getBoolean("CantReviewAssets"));
/* 3584 */         groupPermission.setCanEditSubcategories(rs.getBoolean("CanEditSubcategories"));
/* 3585 */         groupPermission.setApprovalRequiredForHighRes(rs.getBoolean("ApprRequiredForHighRes"));
/*      */ 
/* 3588 */         if (vecPermissions == null)
/*      */         {
/* 3590 */           vecPermissions = new Vector();
/*      */         }
/*      */ 
/* 3593 */         vecPermissions.add(groupPermission);
/*      */       }
/*      */ 
/* 3597 */       psql.close();
/*      */ 
/* 3600 */       group.setCategoryPermissions(vecPermissions);
/*      */       
/* 3602 */       //category = group;
/*      */       return group;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 3606 */       throw new SQLStatementException(sSQL, e);
/*      */     }
/*      */     finally
/*      */     {
/* 3610 */       if ((a_transaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 3614 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 3618 */           this.m_logger.error("SQLException whilst trying to commit transaction", sqle);
/*      */           throw new Bn2Exception(sqle.getMessage());
                   }
/*      */       }
/* 3619 */     }//throw localObject;
/*      */   }
/*      */ 
/*      */   public boolean deleteGroup(long a_lGroupId)
/*      */     throws Bn2Exception
/*      */   {
/* 3644 */     Connection con = null;
/* 3645 */     boolean bSuccess = false;
/* 3646 */     this.m_logger.debug("UserManager.deleteGroup");
/*      */     try
/*      */     {
/* 3650 */       con = this.m_dataSource.getConnection();
/* 3651 */       PreparedStatement psql = null;
/*      */ 
/* 3654 */       String[] aSql = { "DELETE FROM UserInGroup WHERE UserGroupId=?", "DELETE FROM OrgUnitGroup WHERE UserGroupId=?", "DELETE FROM GroupAttributeExclusion WHERE UserGroupId=?", "DELETE FROM GroupUsageExclusion WHERE UserGroupId=?", "DELETE FROM CategoryVisibleToGroup WHERE UserGroupId=?", "DELETE FROM AttributeVisibleToGroup WHERE UserGroupId=?", "DELETE FROM GroupEmailRule WHERE UserGroupId=?", "DELETE FROM GroupIsRole WHERE UserGroupId=?", "DELETE FROM GroupFilterExclusion WHERE UserGroupId=?", "DELETE FROM UserGroup WHERE Id=?" };
/*      */ 
/* 3666 */       for (int i = 0; i < aSql.length; i++)
/*      */       {
/* 3668 */         psql = con.prepareStatement(aSql[i]);
/* 3669 */         psql.setLong(1, a_lGroupId);
/* 3670 */         psql.executeUpdate();
/* 3671 */         psql.close();
/*      */       }
/*      */ 
/* 3674 */       bSuccess = true;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       try
/*      */       {
/* 3680 */         this.m_logger.debug("UserManager.deleteGroup: About to rollback");
/* 3681 */         if (con != null)
/*      */         {
/* 3683 */           con.rollback();
/*      */         }
/*      */       }
/*      */       catch (SQLException rbe)
/*      */       {
/*      */       }
/*      */ 
/* 3690 */       this.m_logger.error("UserManager.deleteGroup: SQL Exception whilst deleting group: " + e);
/* 3691 */       throw new Bn2Exception("UserManager.deleteGroup: SQL Exception whilst deleting group: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 3696 */       if (con != null)
/*      */       {
/*      */         try
/*      */         {
/* 3700 */           con.commit();
/* 3701 */           con.close();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 3705 */           this.m_logger.error("UserManager.deleteGroup: SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/* 3709 */     return bSuccess;
/*      */   }
/*      */ 
/*      */   public boolean removeUsersFromGroup(long a_lGroupId)
/*      */     throws Bn2Exception
/*      */   {
/* 3725 */     Connection con = null;
/* 3726 */     boolean bSuccess = false;
/* 3727 */     this.m_logger.debug("UserManager.removeUsersFromGroup");
/*      */     try
/*      */     {
/* 3731 */       con = this.m_dataSource.getConnection();
/* 3732 */       PreparedStatement psql = null;
/*      */ 
/* 3735 */       String sSql = "DELETE FROM UserInGroup WHERE UserGroupId=?";
/* 3736 */       psql = con.prepareStatement(sSql);
/* 3737 */       psql.setLong(1, a_lGroupId);
/* 3738 */       psql.executeUpdate();
/* 3739 */       psql.close();
/*      */ 
/* 3741 */       bSuccess = true;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       try
/*      */       {
/* 3747 */         this.m_logger.debug("UserManager.removeUsersFromGroup: About to rollback");
/* 3748 */         con.rollback();
/*      */       }
/*      */       catch (SQLException rbe)
/*      */       {
/*      */       }
/*      */ 
/* 3754 */       this.m_logger.error("UserManager.removeUsersFromGroup: SQL Exception whilst removing users from group: " + e);
/* 3755 */       throw new Bn2Exception("UserManager.removeUsersFromGroup: SQL Exception whilst removing users from group: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 3760 */       if (con != null)
/*      */       {
/*      */         try
/*      */         {
/* 3764 */           con.commit();
/* 3765 */           con.close();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 3769 */           this.m_logger.error("UserManager.removeUsersFromGroup: SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/* 3773 */     return bSuccess;
/*      */   }
/*      */ 
/*      */   public void saveGroupPermissions(DBTransaction a_dbTransaction, Group a_group, Vector a_vecPermissions, long a_lCategoryTreeId)
/*      */     throws Bn2Exception
/*      */   {
/*      */     try
/*      */     {
/* 3791 */       Connection con = a_dbTransaction.getConnection();
/* 3792 */       saveGroupPermissions(con, a_group, a_vecPermissions, a_lCategoryTreeId);
/*      */ 
/* 3795 */       clearCaches();
/*      */ 
/* 3798 */       this.m_feedbackManager.clearRatingCache();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 3802 */       this.m_logger.error("UserManager.saveGroupPermissions:SQL Exception whilst trying to save permissions for group Id: " + a_group.getId() + e);
/* 3803 */       throw new Bn2Exception("UserManager.saveGroupPermissions:SQL Exception whilst trying save group permissions: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean saveGroup(DBTransaction a_dbTransaction, Group a_group)
/*      */     throws Bn2Exception
/*      */   {
/* 3816 */     return saveGroup(a_dbTransaction, a_group, null, 0L);
/*      */   }
/*      */ 
/*      */   public boolean saveGroup(DBTransaction a_dbTransaction, Group a_group, Vector a_vecPermissions, long a_lCategoryTreeId)
/*      */     throws Bn2Exception
/*      */   {
/* 3844 */     Connection con = null;
/* 3845 */     boolean bSuccess = false;
/*      */     try
/*      */     {
/* 3849 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 3852 */       String sSql = null;
/* 3853 */       PreparedStatement psql = null;
/* 3854 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/* 3855 */       long lNewId = 0L;
/*      */ 
/* 3857 */       if (a_group.getId() > 0L)
/*      */       {
/* 3860 */         sSql = "UPDATE UserGroup SET Name=?, Description=?, DailyDownloadLimit=?, IsDefaultGroup=?, Mapping=?, IPMapping=?, URLMapping=?, BrandId=?, DiscountPercentage=?, CanOnlyEditOwn=?, CanEmailAssets=?, CanRepurposeAssets=?, CanSeeSourcePath=?, MaxDownloadHeight=?, MaxDownloadWidth=?, CanInviteUsers=?, CanAddEmptyAssets=?, CanViewLargerSize=?, AutomaticBrandRegister=?, AdvancedViewing=? WHERE Id=?";
/*      */       }
/*      */       else
/*      */       {
/* 3886 */         sSql = "INSERT INTO UserGroup (";
/*      */ 
/* 3888 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 3890 */           lNewId = sqlGenerator.getUniqueId(con, "UserGroupSequence");
/* 3891 */           sSql = sSql + "Id, ";
/*      */         }
/*      */ 
/* 3894 */         sSql = sSql + "Name, Description, DailyDownloadLimit, IsDefaultGroup, Mapping, IPMapping, URLMapping, BrandId, DiscountPercentage, CanOnlyEditOwn, CanEmailAssets, CanRepurposeAssets, CanSeeSourcePath, MaxDownloadHeight, MaxDownloadWidth, CanInviteUsers, CanAddEmptyAssets, CanViewLargerSize, AutomaticBrandRegister, AdvancedViewing) VALUES (";
/*      */ 
/* 3896 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 3898 */           sSql = sSql + "?,";
/*      */         }
/*      */ 
/* 3901 */         sSql = sSql + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */       }
/*      */ 
/* 3904 */       psql = con.prepareStatement(sSql);
/* 3905 */       int iField = 1;
/*      */ 
/* 3907 */       if ((a_group.getId() <= 0L) && (!sqlGenerator.usesAutoincrementFields()))
/*      */       {
/* 3909 */         psql.setLong(iField++, lNewId);
/*      */       }
/*      */ 
/* 3912 */       psql.setString(iField++, a_group.getName());
/* 3913 */       psql.setString(iField++, a_group.getDescription());
/* 3914 */       psql.setLong(iField++, a_group.getMaxDownloads());
/* 3915 */       psql.setBoolean(iField++, a_group.getIsDefaultGroup());
/* 3916 */       psql.setString(iField++, a_group.getMapping());
/* 3917 */       psql.setString(iField++, a_group.getIpMapping());
/* 3918 */       psql.setString(iField++, a_group.getUrlMapping());
/* 3919 */       DBUtil.setFieldIdOrNull(psql, iField++, a_group.getBrandReference());
/* 3920 */       psql.setInt(iField++, a_group.getDiscountPercentage());
/* 3921 */       psql.setBoolean(iField++, a_group.getUserCanOnlyEditOwnFiles());
/* 3922 */       psql.setBoolean(iField++, a_group.getUserCanEmailAssets());
/* 3923 */       psql.setBoolean(iField++, a_group.getUserCanRepurposeAssets());
/* 3924 */       psql.setBoolean(iField++, a_group.getUsersCanSeeSourcePath());
/* 3925 */       psql.setInt(iField++, a_group.getMaxDownloadHeight());
/* 3926 */       psql.setInt(iField++, a_group.getMaxDownloadWidth());
/* 3927 */       psql.setBoolean(iField++, a_group.getUsersCanInviteUsers());
/* 3928 */       psql.setBoolean(iField++, a_group.getUsersCanAddEmptyAssets());
/* 3929 */       psql.setBoolean(iField++, a_group.getUsersCanViewLargerSize());
/* 3930 */       psql.setBoolean(iField++, a_group.getAutomaticBrandRegister());
/* 3931 */       psql.setBoolean(iField++, a_group.getAdvancedViewing());
/*      */ 
/* 3933 */       if (a_group.getId() > 0L)
/*      */       {
/* 3935 */         psql.setLong(iField++, a_group.getId());
/*      */       }
/*      */ 
/* 3938 */       psql.executeUpdate();
/* 3939 */       psql.close();
/*      */ 
/* 3942 */       if (a_group.getId() <= 0L)
/*      */       {
/* 3944 */         if (sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 3946 */           lNewId = sqlGenerator.getUniqueId(con, "UserGroup");
/*      */         }
/*      */ 
/* 3949 */         a_group.setId(lNewId);
/*      */ 
/* 3952 */         Vector <GroupAttributeExclusion> vDefaultAttExclusions = getAttributeExclusionsForGroup(a_dbTransaction, 1L);
/* 3953 */         for (GroupAttributeExclusion exclusion : vDefaultAttExclusions)
/*      */         {
/* 3955 */           addGroupAttributeExclusion(a_dbTransaction, lNewId, exclusion.getItemId(), exclusion.getValue());
/*      */         }
/*      */       }
/*      */ 
/* 3959 */       if (a_vecPermissions != null)
/*      */       {
/* 3962 */         saveGroupPermissions(con, a_group, a_vecPermissions, a_lCategoryTreeId);
/*      */ 
/* 3965 */         clearCaches();
/*      */       }
/* 3967 */       clearEmptyAssetSwitch();
/* 3968 */       bSuccess = true;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 3972 */       this.m_logger.error("UserManager.saveGroup:SQL Exception whilst trying to save group with Id: " + a_group.getId() + e);
/* 3973 */       throw new Bn2Exception("UserManager.saveGroup:SQL Exception whilst trying save group: " + e, e);
/*      */     }
/*      */ 
/* 3976 */     return bSuccess;
/*      */   }
/*      */ 
/*      */   public void setAttributeVisibilityForGroup(DBTransaction a_dbTransaction, Group a_group, int a_iAttributeVisibilityLevel)
/*      */     throws Bn2Exception
/*      */   {
/* 3992 */     Vector vAtts = this.m_attributeManager.getAttributePositionList(a_dbTransaction);
/*      */ 
/* 3995 */     removeGroupAttributeVisibility(a_dbTransaction, a_group.getId());
/*      */ 
/* 3997 */     if (a_iAttributeVisibilityLevel != 1)
/*      */     {
/* 3999 */       for (int i = 0; i < vAtts.size(); i++)
/*      */       {
/* 4001 */         Attribute att = (Attribute)vAtts.get(i);
/* 4002 */         GroupAttributeVisibility gav = new GroupAttributeVisibility();
/* 4003 */         gav.setAttributeId(att.getId());
/* 4004 */         gav.setGroupId(a_group.getId());
/* 4005 */         gav.setIsWriteable((!att.getReadOnly()) && (a_iAttributeVisibilityLevel == 3));
/* 4006 */         addGroupAttributeVisibility(a_dbTransaction, gav);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addUserToGroups(DBTransaction a_dbTransaction, long a_lUserId, Vector<Long> a_vecGroupIds)
/*      */     throws Bn2Exception
/*      */   {
/* 4029 */     String ksMethodName = "addUserToGroups";
/* 4030 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 4032 */     if (transaction == null)
/*      */     {
/* 4034 */       transaction = getDBTransactionManager().getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 4039 */       Connection con = transaction.getConnection();
/*      */ 
/* 4042 */       String sSql = "DELETE FROM UserInGroup WHERE UserId=?";
/* 4043 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 4044 */       psql.setLong(1, a_lUserId);
/*      */ 
/* 4046 */       psql.executeUpdate();
/* 4047 */       psql.close();
/*      */ 
/* 4050 */       sSql = "INSERT INTO UserInGroup (UserId, UserGroupId) VALUES (?,?)";
/* 4051 */       psql = con.prepareStatement(sSql);
/*      */ 
/* 4053 */       for (int i = 0; i < a_vecGroupIds.size(); i++)
/*      */       {
/* 4055 */         psql.setLong(1, a_lUserId);
/* 4056 */         psql.setLong(2, ((Long)a_vecGroupIds.elementAt(i)).longValue());
/* 4057 */         psql.executeUpdate();
/*      */       }
/*      */ 
/* 4060 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 4064 */       if (a_dbTransaction == null)
/*      */       {
/*      */         try
/*      */         {
/* 4068 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 4076 */       this.m_logger.error("ABUserManager.addUserToGroups" + e.getMessage());
/* 4077 */       throw new Bn2Exception("ABUserManager.addUserToGroups" + e.getMessage(), e);
/*      */     }
/*      */     finally
/*      */     {
/* 4082 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 4086 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 4090 */           this.m_logger.error("Sorry, SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addGroupsToUser(DBTransaction a_transaction, long a_lUserId, Vector a_vecGroupIds)
/*      */     throws Bn2Exception
/*      */   {
/* 4115 */     String ksMethodName = "addGroupsToUser";
/*      */     try
/*      */     {
/* 4119 */       Connection con = a_transaction.getConnection();
/*      */ 
/* 4122 */       String sSql = "SELECT UserGroupId FROM UserInGroup WHERE UserId=?";
/* 4123 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 4124 */       psql.setLong(1, a_lUserId);
/* 4125 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 4127 */       HashMap hmCurrentGroups = new HashMap();
/* 4128 */       while (rs.next())
/*      */       {
/* 4130 */         Long olGroupId = new Long(rs.getLong("UserGroupId"));
/* 4131 */         hmCurrentGroups.put(olGroupId, olGroupId);
/*      */       }
/* 4133 */       psql.close();
/*      */ 
/* 4136 */       Vector vecGroupsToAdd = new Vector();
/* 4137 */       Iterator itGroupsToCheck = a_vecGroupIds.iterator();
/* 4138 */       while (itGroupsToCheck.hasNext())
/*      */       {
/* 4140 */         Long olGroupToAdd = (Long)itGroupsToCheck.next();
/*      */ 
/* 4142 */         if (!hmCurrentGroups.containsKey(olGroupToAdd))
/*      */         {
/* 4145 */           vecGroupsToAdd.add(olGroupToAdd);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 4151 */       sSql = "INSERT INTO UserInGroup (UserId, UserGroupId) VALUES (?,?)";
/* 4152 */       psql = con.prepareStatement(sSql);
/*      */ 
/* 4154 */       for (int i = 0; i < vecGroupsToAdd.size(); i++)
/*      */       {
/* 4156 */         psql.setLong(1, a_lUserId);
/* 4157 */         psql.setLong(2, ((Long)vecGroupsToAdd.elementAt(i)).longValue());
/* 4158 */         psql.executeUpdate();
/*      */       }
/*      */ 
/* 4161 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 4165 */       this.m_logger.error("ABUserManager.addGroupsToUser - " + sqe);
/* 4166 */       throw new Bn2Exception("ABUserManager.addGroupsToUser", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeGroupsFromUser(DBTransaction a_transaction, long a_lUserId, Vector a_vecGroupIds)
/*      */     throws Bn2Exception
/*      */   {
/* 4189 */     String ksMethodName = "removeGroupsFromUser";
/*      */     try
/*      */     {
/* 4193 */       Connection con = a_transaction.getConnection();
/*      */ 
/* 4196 */       String sSql = "DELETE FROM UserInGroup WHERE UserId=? AND UserGroupId=? ";
/* 4197 */       PreparedStatement psql = con.prepareStatement(sSql);
/*      */ 
/* 4199 */       for (int i = 0; i < a_vecGroupIds.size(); i++)
/*      */       {
/* 4201 */         psql.setLong(1, a_lUserId);
/* 4202 */         psql.setLong(2, ((Long)a_vecGroupIds.elementAt(i)).longValue());
/* 4203 */         psql.executeUpdate();
/*      */       }
/*      */ 
/* 4206 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 4210 */       this.m_logger.error("ABUserManager.removeGroupsFromUser - " + sqe);
/* 4211 */       throw new Bn2Exception("ABUserManager.removeGroupsFromUser", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addUserToLoggedInUsersGroup(DBTransaction a_transaction, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 4232 */     String ksMethodName = "addUserToLoggedInUsersGroup";
/*      */     try
/*      */     {
/* 4236 */       Connection con = a_transaction.getConnection();
/*      */ 
/* 4239 */       String sSql = "DELETE FROM UserInGroup WHERE UserId=? AND UserGroupId=?";
/* 4240 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 4241 */       psql.setLong(1, a_lUserId);
/* 4242 */       psql.setLong(2, 1L);
/*      */ 
/* 4244 */       psql.executeUpdate();
/* 4245 */       psql.close();
/*      */ 
/* 4248 */       addUserToGroup(con, a_lUserId, 1L);
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 4252 */       this.m_logger.error("ABUserManager.addUserToLoggedInUsersGroup - " + sqe);
/* 4253 */       throw new Bn2Exception("ABUserManager.addUserToLoggedInUsersGroup", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addUserToGroup(Connection a_con, long a_lUserId, long a_lGroupId)
/*      */     throws SQLException
/*      */   {
/* 4263 */     String sSql = "SELECT 1 FROM UserGroup WHERE Id=?";
/* 4264 */     PreparedStatement psql = a_con.prepareStatement(sSql);
/* 4265 */     psql.setLong(1, a_lGroupId);
/* 4266 */     ResultSet rs = psql.executeQuery();
/*      */ 
/* 4268 */     boolean bFound = rs.next();
/*      */ 
/* 4270 */     psql.close();
/*      */ 
/* 4272 */     if (bFound)
/*      */     {
/* 4274 */       sSql = "INSERT INTO UserInGroup (UserId, UserGroupId) VALUES (?,?)";
/* 4275 */       psql = a_con.prepareStatement(sSql);
/* 4276 */       psql.setLong(1, a_lUserId);
/* 4277 */       psql.setLong(2, a_lGroupId);
/* 4278 */       psql.executeUpdate();
/* 4279 */       psql.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void saveGroupPermissions(Connection a_con, Group a_group, Vector<GroupCategoryPermission> a_vecPermissions, long a_lCategoryTreeId)
/*      */     throws SQLException
/*      */   {
/* 4311 */     if ((a_vecPermissions == null) || (a_vecPermissions.isEmpty()))
/*      */     {
/* 4313 */       return;
/*      */     }
/*      */ 
/* 4317 */     StringBuffer sbIdsToUpdate = new StringBuffer();
/* 4318 */     for (GroupCategoryPermission gcp : a_vecPermissions)
/*      */     {
/* 4320 */       sbIdsToUpdate.append(gcp.getCategory().getId());
/* 4321 */       sbIdsToUpdate.append(',');
/*      */     }
/* 4323 */     sbIdsToUpdate.setLength(sbIdsToUpdate.length() - 1);
/*      */ 
/* 4326 */     String sSql = "DELETE FROM CategoryVisibleToGroup WHERE \tUserGroupId=? AND   CategoryId IN (" + sbIdsToUpdate + ")";
/*      */ 
/* 4332 */     PreparedStatement psql = a_con.prepareStatement(sSql);
/* 4333 */     psql.setLong(1, a_group.getId());
/* 4334 */     psql.executeUpdate();
/* 4335 */     psql.close();
/*      */ 
/* 4338 */     sSql = "INSERT INTO CategoryVisibleToGroup (UserGroupId, CategoryId, CanDownloadWithApproval, CanDownloadAssets, CanApproveAssets, CanUpdateWithApproval, CanUpdateAssets, CanDeleteAssets, CanApproveAssetUploads, CantDownloadOriginal, CantDownloadAdvanced,CantReviewAssets,CanViewRestrictedAssets, CanEditSubcategories, ApprRequiredForHighRes) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */ 
/* 4356 */     psql = a_con.prepareStatement(sSql);
/* 4357 */     GroupCategoryPermission gcp = null;
/*      */ 
/* 4359 */     for (int i = 0; i < a_vecPermissions.size(); i++)
/*      */     {
/* 4361 */       gcp = (GroupCategoryPermission)(GroupCategoryPermission)a_vecPermissions.elementAt(i);
/*      */ 
/* 4363 */       if ((gcp.getDownloadPermissionLevel() == 0) && (gcp.getUploadPermissionLevel() == 0) && (!gcp.getCanEditSubcategories()))
/*      */       {
/*      */         continue;
/*      */       }
/* 4367 */       int iCol = 1;
/* 4368 */       psql.setLong(iCol++, a_group.getId());
/* 4369 */       psql.setLong(iCol++, gcp.getCategory().getId());
/* 4370 */       psql.setBoolean(iCol++, gcp.getDownloadPermissionLevel() == 5);
/* 4371 */       psql.setBoolean(iCol++, gcp.getDownloadPermissionLevel() == 2);
/* 4372 */       psql.setBoolean(iCol++, gcp.getCanApprove());
/* 4373 */       psql.setBoolean(iCol++, gcp.getUploadPermissionLevel() == 6);
/* 4374 */       psql.setBoolean(iCol++, gcp.getCanUpdate());
/* 4375 */       psql.setBoolean(iCol++, gcp.getCanDeleteAssets());
/* 4376 */       psql.setBoolean(iCol++, gcp.getCanApproveUploads());
/*      */ 
/* 4379 */       psql.setBoolean(iCol++, !gcp.getCanDownloadOriginal());
/* 4380 */       psql.setBoolean(iCol++, !gcp.getCanDownloadAdvanced());
/* 4381 */       psql.setBoolean(iCol++, !gcp.getCanReviewAssets());
/* 4382 */       psql.setBoolean(iCol++, gcp.getCanViewRestrictedAssets());
/* 4383 */       psql.setBoolean(iCol++, gcp.getCanEditSubcategories());
/* 4384 */       psql.setBoolean(iCol++, gcp.getApprovalRequiredForHighRes());
/*      */ 
/* 4386 */       psql.executeUpdate();
/*      */     }
/*      */ 
/* 4390 */     psql.close();
/*      */   }
/*      */ 
/*      */   public Vector getAttributeIdsForUser(DBTransaction a_dbTransaction, long a_lUserId, boolean a_bWriteableOnly)
/*      */     throws Bn2Exception
/*      */   {
/* 4406 */     return getAttributeIdsForUser(a_dbTransaction, a_lUserId, null, null, a_bWriteableOnly);
/*      */   }
/*      */ 
/*      */   public Vector getAttributeIdsForUser(DBTransaction a_dbTransaction, String a_sIP, String a_sURL)
/*      */     throws Bn2Exception
/*      */   {
/* 4422 */     return getAttributeIdsForUser(a_dbTransaction, 0L, a_sIP, a_sURL, false);
/*      */   }
/*      */ 
/*      */   private Vector getAttributeIdsForUser(DBTransaction a_dbTransaction, long a_lUserId, String a_sIP, String a_sURL, boolean a_bWriteableOnly)
/*      */     throws Bn2Exception
/*      */   {
/* 4444 */     Connection con = null;
/* 4445 */     PreparedStatement psql = null;
/* 4446 */     ResultSet rs = null;
/* 4447 */     DBTransaction transaction = a_dbTransaction;
/* 4448 */     String sSql = null;
/* 4449 */     Vector vIds = null;
/*      */ 
/* 4451 */     if (transaction == null)
/*      */     {
/* 4453 */       transaction = getDBTransactionManager().getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 4458 */       con = transaction.getConnection();
/*      */ 
/* 4460 */       sSql = "SELECT MAX(avg.IsWriteable) AS MaxIsWritable, avg.AttributeId FROM UserGroup ug INNER JOIN AttributeVisibleToGroup avg ON avg.UserGroupId=ug.Id LEFT JOIN UserInGroup uig ON uig.UserGroupId = ug.Id WHERE  uig.UserId=? OR " + getDefaultGroupSqlCriteria(a_sIP, a_sURL, con);
/*      */ 
/* 4468 */       sSql = sSql + "GROUP BY avg.AttributeId";
/*      */ 
/* 4470 */       psql = con.prepareStatement(sSql);
/* 4471 */       psql.setLong(1, a_lUserId);
/* 4472 */       rs = psql.executeQuery();
/*      */ 
/* 4474 */       vIds = new Vector();
/*      */ 
/* 4476 */       while (rs.next())
/*      */       {
/* 4478 */         if ((a_bWriteableOnly) && (!rs.getBoolean("MaxIsWritable")))
/*      */           continue;
/* 4480 */         vIds.add(new Long(rs.getLong("AttributeId")));
/*      */       }
/*      */ 
/* 4484 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 4488 */       this.m_logger.error("SQL Exception whilst getting attributes for asset : " + e);
/* 4489 */       throw new Bn2Exception("SQL Exception whilst getting attributes for asset : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 4494 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 4498 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 4502 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 4507 */     return vIds;
/*      */   }
/*      */ 
/*      */   public Vector getAttributeExclusionsForUser(DBTransaction a_dbTransaction, String a_sIP, String a_sURL)
/*      */     throws Bn2Exception
/*      */   {
/* 4523 */     Connection con = null;
/* 4524 */     PreparedStatement psql = null;
/* 4525 */     ResultSet rs = null;
/* 4526 */     DBTransaction transaction = a_dbTransaction;
/* 4527 */     String sSql = null;
/* 4528 */     Vector vAttExclusions = null;
/*      */ 
/* 4530 */     if (transaction == null)
/*      */     {
/* 4532 */       transaction = getDBTransactionManager().getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/* 4536 */     int iNumGroups = getNumberOfDefaultGroups(transaction, a_sIP, a_sURL);
/*      */     try
/*      */     {
/* 4540 */       con = transaction.getConnection();
/*      */ 
/* 4542 */       sSql = "SELECT a.Id aId, a.Label, a.AttributeTypeId, a.ValueColumnName, gae.Value, COUNT(ug.Id) AS NumGroups FROM Attribute a INNER JOIN GroupAttributeExclusion gae ON a.Id = gae.AttributeId INNER JOIN UserGroup ug ON gae.UserGroupId = ug.Id WHERE " + getDefaultGroupSqlCriteria(a_sIP, a_sURL, con);
/*      */ 
/* 4555 */       sSql = sSql + " GROUP BY a.Id, a.Label, a.AttributeTypeId, a.ValueColumnName, gae.Value";
/*      */ 
/* 4557 */       psql = con.prepareStatement(sSql);
/* 4558 */       rs = psql.executeQuery();
/*      */ 
/* 4560 */       Attribute att = null;
/* 4561 */       vAttExclusions = new Vector();
/*      */ 
/* 4564 */       while (rs.next())
/*      */       {
/* 4568 */         if ((rs.getLong("aId") <= 0L) || (rs.getInt("NumGroups") != iNumGroups))
/*      */           continue;
/* 4570 */         if ((att == null) || (att.getId() != rs.getLong("aId")))
/*      */         {
/* 4572 */           att = new Attribute();
/* 4573 */           att.setId(rs.getLong("aId"));
/* 4574 */           att.setTypeId(rs.getLong("AttributeTypeId"));
/* 4575 */           att.setLabel(rs.getString("Label"));
/* 4576 */           att.setValueColumnName(rs.getString("ValueColumnName"));
/*      */         }
/*      */ 
/* 4579 */         AttributeValue attVal = new AttributeValue();
/* 4580 */         attVal.setValue(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "Value"));
/* 4581 */         attVal.setAttribute(att);
/*      */ 
/* 4583 */         findAttributeValueTranslations(transaction, attVal);
/*      */ 
/* 4585 */         vAttExclusions.add(attVal);
/*      */       }
/*      */ 
/* 4589 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 4593 */       throw new SQLStatementException(sSql, e);
/*      */     }
/*      */     finally
/*      */     {
/* 4598 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 4602 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 4606 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 4611 */     return vAttExclusions;
/*      */   }
/*      */ 
/*      */   public Vector getAttributeExclusionsForUser(DBTransaction a_dbTransaction, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 4633 */     Connection con = null;
/* 4634 */     PreparedStatement psql = null;
/* 4635 */     ResultSet rs = null;
/* 4636 */     DBTransaction transaction = a_dbTransaction;
/* 4637 */     String sSQL = null;
/* 4638 */     Vector vAttExclusions = null;
/*      */ 
/* 4640 */     if (transaction == null)
/*      */     {
/* 4642 */       transaction = getDBTransactionManager().getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/* 4646 */     int iNumGroups = getNumberOfGroupsForUser(transaction, a_lUserId);
/*      */     try
/*      */     {
/* 4650 */       con = transaction.getConnection();
/*      */ 
/* 4652 */       sSQL = "SELECT a.Id aId, a.Label, a.AttributeTypeId, a.ValueColumnName, gae.Value, COUNT(ug.Id) NumGroups FROM Attribute a INNER JOIN GroupAttributeExclusion gae ON a.Id = gae.AttributeId INNER JOIN UserGroup ug ON gae.UserGroupId = ug.Id LEFT JOIN UserInGroup uig ON (uig.UserGroupId=ug.Id AND uig.UserId=?) WHERE ug.IsDefaultGroup>0 OR uig.UserId>0 GROUP BY a.Id, a.Label, a.AttributeTypeId, a.ValueColumnName, gae.Value";
/*      */ 
/* 4667 */       psql = con.prepareStatement(sSQL);
/* 4668 */       psql.setLong(1, a_lUserId);
/*      */ 
/* 4670 */       rs = psql.executeQuery();
/*      */ 
/* 4672 */       Attribute att = null;
/* 4673 */       vAttExclusions = new Vector();
/*      */ 
/* 4676 */       while (rs.next())
/*      */       {
/* 4680 */         if ((rs.getLong("aId") <= 0L) || (rs.getInt("NumGroups") != iNumGroups))
/*      */           continue;
/* 4682 */         if ((att == null) || (att.getId() != rs.getLong("aId")))
/*      */         {
/* 4684 */           att = new Attribute();
/* 4685 */           att.setId(rs.getLong("aId"));
/* 4686 */           att.setTypeId(rs.getLong("AttributeTypeId"));
/* 4687 */           att.setLabel(rs.getString("Label"));
/* 4688 */           att.setValueColumnName(rs.getString("ValueColumnName"));
/*      */         }
/*      */ 
/* 4691 */         AttributeValue attVal = new AttributeValue();
/* 4692 */         attVal.setValue(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "Value"));
/* 4693 */         attVal.setAttribute(att);
/*      */ 
/* 4695 */         findAttributeValueTranslations(transaction, attVal);
/*      */ 
/* 4697 */         vAttExclusions.add(attVal);
/*      */       }
/*      */ 
/* 4701 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 4705 */       this.m_logger.error("SQL Exception whilst getting attributes for asset : " + e);
/* 4706 */       throw new Bn2Exception("SQL Exception whilst getting attributes for asset : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 4711 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 4715 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 4719 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 4724 */     return vAttExclusions;
/*      */   }
/*      */ 
/*      */   private void findAttributeValueTranslations(DBTransaction a_dbTransaction, AttributeValue a_attributeValue)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 4735 */     AttributeType attributeType = this.m_attributeManager.getAttributeTypeById(a_attributeValue.getAttribute().getTypeId());
/*      */     String sSQL;
/* 4737 */    // switch (3.$SwitchMap$com$bright$assetbank$attribute$constant$AttributeStorageType[attributeType.getAttributeStorageType().ordinal()])
               switch (attributeType.getAttributeStorageType().ordinal())
/*      */     {
/*      */     case 1:
/* 4740 */       String sCol = a_attributeValue.getAttribute().getValueColumnName();
                System.out.println(sCol);
/* 4741 */       sSQL = "SELECT taav." + sCol + " Value, l.Id, l.Name, l.Code " + "FROM " + "AssetAttributeValues aav " + "INNER JOIN TranslatedAssetAttributeValues taav ON taav.AssetId=aav.AssetId " + "INNER JOIN Language l ON l.Id = taav.LanguageId " + "WHERE aav." + sCol + " LIKE ?";
/*      */ 
/* 4747 */       break;
/*      */     case 2:
/* 4750 */       sSQL = "SELECT tlav.Value Value, l.Id, l.Name, l.Code FROM ListAttributeValue lav INNER JOIN TranslatedListAttributeValue tlav ON tlav.ListAttributeValueId=lav.Id INNER JOIN Language l ON l.Id = tlav.LanguageId WHERE lav.AttributeId=? AND lav.Value LIKE ?";
/*      */ 
/* 4756 */       break;
/*      */     default:
/* 4759 */       throw new Bn2Exception("ABUserManager.findAttributeValueTranslations() : Can only find translations for LIST and VALUE_PER_ASSET attributes, cannot handle :" + attributeType.getAttributeStorageType().name());
/*      */     }
/*      */       System.out.println(sSQL);
/* 4763 */     PreparedStatement psql = a_dbTransaction.getConnection().prepareStatement(sSQL);
/*      */     try
/*      */     {
/* 4767 */       int iCol = 1;
/*      */ 
/* 4769 */       if (AttributeStorageType.LIST.equals(attributeType.getAttributeStorageType()))
/*      */       {
/* 4771 */         psql.setLong(iCol++, a_attributeValue.getAttribute().getId());
/*      */       }
/*      */ 
/* 4774 */       psql.setString(iCol++, a_attributeValue.getValue());
/*      */ 
/* 4776 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 4778 */       while (rs.next())
/*      */       {
/*      */         //AttributeValue tmp267_266 = a_attributeValue; tmp267_266.getClass(); AttributeValue.Translation translation = new AttributeValue.Translation(tmp267_266, new Language(rs.getLong("l.Id"), rs.getString("l.Name"), rs.getString("l.Code")));
/* 4781 */          AttributeValue.Translation translation = a_attributeValue.new Translation(new Language(rs.getLong("l.Id"), rs.getString("l.Name"), rs.getString("l.Code")));
                   translation.setValue(rs.getString("Value"));
/* 4782 */         a_attributeValue.getTranslations().add(translation);
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/* 4787 */       psql.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getAdminEmailAddressesInDivision(long a_lDivisionId, boolean a_bReceiveAlertsOnly)
/*      */     throws Bn2Exception
/*      */   {
/* 4808 */     UserSearchCriteria criteria = new UserSearchCriteria();
/* 4809 */     criteria.setIsAdmin(Boolean.TRUE);
/* 4810 */     criteria.setDivisionId(a_lDivisionId);
/*      */ 
/* 4812 */     Vector vecAdminUsers = findUsers(criteria, -1);
/*      */ 
/* 4814 */     String sEmailAddresses = null;
/*      */ 
/* 4816 */     for (int i = 0; i < vecAdminUsers.size(); i++)
/*      */     {
/* 4818 */       ABUser user = (ABUser)vecAdminUsers.get(i);
/*      */ 
/* 4820 */       if ((a_bReceiveAlertsOnly) && (!user.getReceiveAlerts()))
/*      */         continue;
/* 4822 */       if ((user.getEmailAddress() == null) || (user.getEmailAddress().length() == 0))
/*      */       {
/*      */         continue;
/*      */       }
/*      */ 
/* 4827 */       if (sEmailAddresses == null)
/*      */       {
/* 4829 */         sEmailAddresses = "";
/*      */       }
/*      */       else
/*      */       {
/* 4834 */         sEmailAddresses = sEmailAddresses + ";";
/*      */       }
/*      */ 
/* 4837 */       sEmailAddresses = sEmailAddresses + user.getEmailAddress();
/*      */     }
/*      */ 
/* 4841 */     return sEmailAddresses;
/*      */   }
/*      */ 
/*      */   public String getAdminEmailAddresses()
/*      */     throws Bn2Exception
/*      */   {
/* 4859 */     String sEmailAddresses = getAdminEmailAddressesInDivision(0L, true);
/*      */ 
/* 4861 */     return sEmailAddresses;
/*      */   }
/*      */ 
/*      */   public String getAdminEmailAddressesForAlerts()
/*      */     throws Bn2Exception
/*      */   {
/* 4874 */     String sEmailAddresses = getAdminEmailAddressesInDivision(0L, true);
/*      */ 
/* 4876 */     return sEmailAddresses;
/*      */   }
/*      */ 
/*      */   public Vector<GroupAttributeExclusion> getAttributeExclusionsForGroup(DBTransaction a_transaction, long a_lGroupId)
/*      */     throws Bn2Exception
/*      */   {
/* 4896 */     String ksMethodName = "getAttributeExclusionsForGroup";
/* 4897 */     Vector vecResults = new Vector();
/* 4898 */     DBTransaction transaction = a_transaction;
/*      */     try
/*      */     {
/* 4902 */       if (transaction == null)
/*      */       {
/* 4904 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/* 4907 */       Connection con = transaction.getConnection();
/*      */ 
/* 4910 */       String sSql = "SELECT AttributeId, Value FROM GroupAttributeExclusion WHERE UserGroupId=?";
/* 4911 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 4912 */       psql.setLong(1, a_lGroupId);
/*      */ 
/* 4914 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 4916 */       GroupAttributeExclusion exclusion = null;
/*      */ 
/* 4918 */       while (rs.next())
/*      */       {
/* 4920 */         exclusion = new GroupAttributeExclusion();
/* 4921 */         exclusion.setGroupId(a_lGroupId);
/* 4922 */         exclusion.setItemId(rs.getLong("AttributeId"));
/* 4923 */         exclusion.setValue(rs.getString("Value"));
/* 4924 */         vecResults.add(exclusion);
/*      */       }
/*      */ 
/* 4927 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 4932 */       if ((transaction != null) && (a_transaction == null))
/*      */         try {
/* 4934 */           transaction.rollback(); } catch (Exception e) {
/*      */         }
/* 4936 */       this.m_logger.error("ABUserManager.getAttributeExclusionsForGroup - " + sqe);
/* 4937 */       throw new Bn2Exception("ABUserManager.getAttributeExclusionsForGroup", sqe);
/*      */     }
/*      */     finally
/*      */     {
/* 4941 */       if ((transaction != null) && (a_transaction == null))
/*      */         try {
/* 4943 */           transaction.commit();
/*      */         } catch (Exception e) {
/*      */         }
/*      */     }
/* 4947 */     return vecResults;
/*      */   }
/*      */ 
/*      */   public void removeAttributeExclusionsForGroup(DBTransaction a_transaction, long a_lGroupId)
/*      */     throws Bn2Exception
/*      */   {
/* 4966 */     String ksMethodName = "removeAttributeExclusionForGroup";
/*      */     try
/*      */     {
/* 4970 */       Connection con = a_transaction.getConnection();
/*      */ 
/* 4973 */       String sSql = "DELETE FROM GroupAttributeExclusion WHERE UserGroupId=?";
/* 4974 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 4975 */       psql.setLong(1, a_lGroupId);
/*      */ 
/* 4977 */       psql.executeUpdate();
/* 4978 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 4982 */       this.m_logger.error("ABUserManager.removeAttributeExclusionForGroup - " + sqe);
/* 4983 */       throw new Bn2Exception("ABUserManager.removeAttributeExclusionForGroup", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addGroupAttributeExclusion(DBTransaction a_transaction, long a_lGroupId, long a_lAttributeId, String a_sValue)
/*      */     throws Bn2Exception
/*      */   {
/* 5007 */     String ksMethodName = "addGroupAttributeExclusion";
/*      */     try
/*      */     {
/* 5011 */       Connection con = a_transaction.getConnection();
/*      */ 
/* 5014 */       String sSql = "INSERT INTO GroupAttributeExclusion (UserGroupId,AttributeId,Value) VALUES (?,?,?)";
/* 5015 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 5016 */       psql.setLong(1, a_lGroupId);
/* 5017 */       psql.setLong(2, a_lAttributeId);
/* 5018 */       psql.setString(3, a_sValue);
/*      */ 
/* 5020 */       psql.executeUpdate();
/* 5021 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 5025 */       this.m_logger.error("ABUserManager.addGroupAttributeExclusion - " + sqe);
/* 5026 */       throw new Bn2Exception("ABUserManager.addGroupAttributeExclusion", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getGroupAttributeVisibilityList(DBTransaction a_transaction, long a_lGroupId)
/*      */     throws Bn2Exception
/*      */   {
/* 5046 */     String ksMethodName = "getGroupAttributeVisibilityList";
/* 5047 */     Vector vecResults = new Vector();
/*      */     try
/*      */     {
/* 5051 */       Connection con = a_transaction.getConnection();
/*      */ 
/* 5053 */       String sSql = "SELECT AttributeId, UserGroupId, IsWriteable FROM AttributeVisibleToGroup WHERE UserGroupId=?";
/* 5054 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 5055 */       psql.setLong(1, a_lGroupId);
/*      */ 
/* 5057 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 5059 */       GroupAttributeVisibility attVisibility = null;
/*      */ 
/* 5061 */       while (rs.next())
/*      */       {
/* 5063 */         attVisibility = new GroupAttributeVisibility();
/* 5064 */         attVisibility.setAttributeId(rs.getLong("AttributeId"));
/* 5065 */         attVisibility.setGroupId(rs.getLong("UserGroupId"));
/* 5066 */         attVisibility.setIsWriteable(rs.getBoolean("IsWriteable"));
/* 5067 */         vecResults.add(attVisibility);
/*      */       }
/*      */ 
/* 5070 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 5075 */       this.m_logger.error("ABUserManager.getGroupAttributeVisibilityList - " + sqe);
/* 5076 */       throw new Bn2Exception("ABUserManager.getGroupAttributeVisibilityList", sqe);
/*      */     }
/*      */ 
/* 5079 */     return vecResults;
/*      */   }
/*      */ 
/*      */   public void removeGroupAttributeVisibility(DBTransaction a_transaction, long a_lGroupId)
/*      */     throws Bn2Exception
/*      */   {
/* 5098 */     String ksMethodName = "removeGroupAttributeVisibility";
/*      */     try
/*      */     {
/* 5102 */       Connection con = a_transaction.getConnection();
/*      */ 
/* 5105 */       String sSql = "DELETE FROM AttributeVisibleToGroup WHERE UserGroupId=?";
/* 5106 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 5107 */       psql.setLong(1, a_lGroupId);
/*      */ 
/* 5109 */       psql.executeUpdate();
/* 5110 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 5114 */       this.m_logger.error("ABUserManager.removeGroupAttributeVisibility - " + sqe);
/* 5115 */       throw new Bn2Exception("ABUserManager.removeGroupAttributeVisibility", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addGroupAttributeVisibility(DBTransaction a_transaction, GroupAttributeVisibility a_attVisibility)
/*      */     throws Bn2Exception
/*      */   {
/* 5137 */     String ksMethodName = "addGroupAttributeVisibility";
/*      */     try
/*      */     {
/* 5141 */       Connection con = a_transaction.getConnection();
/*      */ 
/* 5144 */       String sSql = "INSERT INTO AttributeVisibleToGroup (UserGroupId,AttributeId,IsWriteable) VALUES (?,?,?)";
/* 5145 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 5146 */       psql.setLong(1, a_attVisibility.getGroupId());
/* 5147 */       psql.setLong(2, a_attVisibility.getAttributeId());
/* 5148 */       psql.setBoolean(3, a_attVisibility.getIsWriteable());
/*      */ 
/* 5150 */       psql.executeUpdate();
/* 5151 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 5155 */       this.m_logger.error("ABUserManager.addGroupAttributeVisibility - " + sqe);
/* 5156 */       throw new Bn2Exception("ABUserManager.addGroupAttributeVisibility", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getUsageExclusionsForGroup(DBTransaction a_transaction, long a_lGroupId)
/*      */     throws Bn2Exception
/*      */   {
/* 5173 */     String ksMethodName = "getUsageExclsuionsForGroup";
/* 5174 */     Vector vecResults = new Vector();
/*      */     try
/*      */     {
/* 5178 */       Connection con = a_transaction.getConnection();
/*      */ 
/* 5181 */       String sSql = "SELECT UsageTypeId FROM GroupUsageExclusion WHERE UserGroupId=?";
/* 5182 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 5183 */       psql.setLong(1, a_lGroupId);
/*      */ 
/* 5185 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 5187 */       GroupExclusion exclusion = null;
/*      */ 
/* 5189 */       while (rs.next())
/*      */       {
/* 5191 */         exclusion = new GroupExclusion();
/* 5192 */         exclusion.setGroupId(a_lGroupId);
/* 5193 */         exclusion.setItemId(rs.getLong("UsageTypeId"));
/* 5194 */         vecResults.add(exclusion);
/*      */       }
/*      */ 
/* 5197 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 5202 */       this.m_logger.error("ABUserManager.getUsageExclsuionsForGroup - " + sqe);
/* 5203 */       throw new Bn2Exception("ABUserManager.getUsageExclsuionsForGroup", sqe);
/*      */     }
/*      */ 
/* 5206 */     return vecResults;
/*      */   }
/*      */ 
/*      */   public Vector getFilterExclusionsForGroup(DBTransaction a_transaction, long a_lGroupId)
/*      */     throws Bn2Exception
/*      */   {
/* 5222 */     String ksMethodName = "getFilterExclusionsForGroup";
/* 5223 */     Vector vecResults = new Vector();
/*      */     try
/*      */     {
/* 5227 */       Connection con = a_transaction.getConnection();
/*      */ 
/* 5230 */       String sSql = "SELECT FilterId FROM GroupFilterExclusion WHERE UserGroupId=?";
/* 5231 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 5232 */       psql.setLong(1, a_lGroupId);
/*      */ 
/* 5234 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 5236 */       GroupExclusion exclusion = null;
/*      */ 
/* 5238 */       while (rs.next())
/*      */       {
/* 5240 */         exclusion = new GroupExclusion();
/* 5241 */         exclusion.setGroupId(a_lGroupId);
/* 5242 */         exclusion.setItemId(rs.getLong("FilterId"));
/* 5243 */         vecResults.add(exclusion);
/*      */       }
/*      */ 
/* 5246 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 5251 */       this.m_logger.error("ABUserManager.getFilterExclusionsForGroup - " + sqe);
/* 5252 */       throw new Bn2Exception("ABUserManager.getFilterExclusionsForGroup", sqe);
/*      */     }
/*      */ 
/* 5255 */     return vecResults;
/*      */   }
/*      */ 
/*      */   public void removeUsageExclusionsForGroup(DBTransaction a_transaction, long a_lGroupId)
/*      */     throws Bn2Exception
/*      */   {
/* 5270 */     String ksMethodName = "removeUsageExclusionsForGroup";
/*      */     try
/*      */     {
/* 5274 */       Connection con = a_transaction.getConnection();
/*      */ 
/* 5277 */       String sSql = "DELETE FROM GroupUsageExclusion WHERE UserGroupId=?";
/* 5278 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 5279 */       psql.setLong(1, a_lGroupId);
/*      */ 
/* 5281 */       psql.executeUpdate();
/* 5282 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 5286 */       this.m_logger.error("ABUserManager.removeUsageExclusionsForGroup - " + sqe);
/* 5287 */       throw new Bn2Exception("ABUserManager.removeUsageExclusionsForGroup", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addGroupUsageExclusion(DBTransaction a_transaction, long a_lGroupId, long a_lUsageTypeId)
/*      */     throws Bn2Exception
/*      */   {
/* 5305 */     String ksMethodName = "addGroupUsageExclusion";
/*      */     try
/*      */     {
/* 5309 */       Connection con = a_transaction.getConnection();
/*      */ 
/* 5312 */       String sSql = "INSERT INTO GroupUsageExclusion (UserGroupId, UsageTypeId) VALUES (?,?)";
/* 5313 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 5314 */       psql.setLong(1, a_lGroupId);
/* 5315 */       psql.setLong(2, a_lUsageTypeId);
/*      */ 
/* 5317 */       psql.executeUpdate();
/* 5318 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 5322 */       this.m_logger.error("ABUserManager.addGroupUsageExclusion - " + sqe);
/* 5323 */       throw new Bn2Exception("ABUserManager.addGroupUsageExclusion", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeFilterExclusionsForGroup(DBTransaction a_transaction, long a_lGroupId)
/*      */     throws Bn2Exception
/*      */   {
/* 5331 */     String ksMethodName = "removeFilterExclusionsForGroup";
/*      */     try
/*      */     {
/* 5335 */       Connection con = a_transaction.getConnection();
/* 5336 */       String sSql = "DELETE FROM GroupFilterExclusion WHERE UserGroupId=?";
/* 5337 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 5338 */       psql.setLong(1, a_lGroupId);
/* 5339 */       psql.executeUpdate();
/* 5340 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 5344 */       this.m_logger.error("ABUserManager.removeFilterExclusionsForGroup - " + sqe);
/* 5345 */       throw new Bn2Exception("ABUserManager.removeFilterExclusionsForGroup", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addGroupFilterExclusion(DBTransaction a_transaction, long a_lGroupId, long a_lFilterId)
/*      */     throws Bn2Exception
/*      */   {
/* 5353 */     String ksMethodName = "addGroupFilterExclusion";
/*      */     try
/*      */     {
/* 5356 */       Connection con = a_transaction.getConnection();
/* 5357 */       String sSql = "INSERT INTO GroupFilterExclusion (UserGroupId, FilterId) VALUES (?,?)";
/* 5358 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 5359 */       psql.setLong(1, a_lGroupId);
/* 5360 */       psql.setLong(2, a_lFilterId);
/* 5361 */       psql.executeUpdate();
/* 5362 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 5366 */       this.m_logger.error("ABUserManager.addGroupFilterExclusion - " + sqe);
/* 5367 */       throw new Bn2Exception("ABUserManager.addGroupFilterExclusion", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getUsageExclusionsForUser(DBTransaction a_dbTransaction, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 5383 */     Connection con = null;
/* 5384 */     PreparedStatement psql = null;
/* 5385 */     ResultSet rs = null;
/* 5386 */     String sSQL = null;
/* 5387 */     Vector vExclusions = new Vector();
/*      */ 
/* 5389 */     DBTransaction transaction = a_dbTransaction;
/* 5390 */     if (transaction == null)
/*      */     {
/* 5392 */       transaction = getDBTransactionManager().getNewTransaction();
/*      */     }
/*      */ 
/* 5396 */     boolean bIsAdmin = false;
/* 5397 */     if (a_lUserId > 0L)
/*      */     {
/* 5399 */       ABUser user = (ABUser)getUser(transaction, a_lUserId);
/* 5400 */       bIsAdmin = user.getIsAdmin();
/*      */     }
/*      */ 
/* 5403 */     if (!bIsAdmin)
/*      */     {
/* 5407 */       int iMinNumGroups = 0;
/*      */ 
/* 5409 */       if (AssetBankSettings.getStrictUsageExclusions())
/*      */       {
/* 5412 */         iMinNumGroups = 1;
/*      */       }
/*      */       else
/*      */       {
/* 5417 */         iMinNumGroups = getNumberOfGroupsForUser(transaction, a_lUserId);
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/* 5422 */         con = transaction.getConnection();
/*      */ 
/* 5426 */         sSQL = "SELECT ut.Id utId, ut.Description, COUNT(ug.Id) AS NumGroups  FROM UsageType ut INNER JOIN GroupUsageExclusion gue ON ut.Id = gue.UsageTypeId INNER JOIN UserGroup ug ON gue.UserGroupId = ug.Id LEFT JOIN UserInGroup uig ON (uig.UserGroupId=ug.Id AND uig.UserId=?) WHERE ug.IsDefaultGroup>0 OR uig.UserId>0 GROUP BY ut.Id, ut.Description";
/*      */ 
/* 5435 */         psql = con.prepareStatement(sSQL);
/* 5436 */         psql.setLong(1, a_lUserId);
/* 5437 */         rs = psql.executeQuery();
/*      */ 
/* 5439 */         UsageType usage = null;
/*      */ 
/* 5443 */         while (rs.next())
/*      */         {
/* 5447 */           if ((rs.getLong("utId") <= 0L) || (rs.getInt("NumGroups") < iMinNumGroups))
/*      */             continue;
/* 5449 */           if ((usage == null) || (usage.getId() != rs.getLong("utId")))
/*      */           {
/* 5451 */             usage = new UsageType();
/* 5452 */             usage.setId(rs.getLong("utId"));
/* 5453 */             usage.setDescription(rs.getString("Description"));
/*      */           }
/*      */ 
/* 5456 */           vExclusions.add(usage);
/*      */         }
/*      */ 
/* 5461 */         psql.close();
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/* 5465 */         this.m_logger.error("SQL Exception whilst getting usage exclusions : " + e);
/* 5466 */         throw new Bn2Exception("SQL Exception whilst getting usage exclusions : " + e, e);
/*      */       }
/*      */       finally
/*      */       {
/* 5471 */         if ((a_dbTransaction == null) && (transaction != null))
/*      */         {
/*      */           try
/*      */           {
/* 5475 */             transaction.commit();
/*      */           }
/*      */           catch (SQLException sqle)
/*      */           {
/* 5479 */             this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 5485 */     return vExclusions;
/*      */   }
/*      */ 
/*      */   public Vector getFilterExclusionsForUser(DBTransaction a_dbTransaction, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 5501 */     Vector vExclusions = new Vector();
/*      */ 
/* 5503 */     DBTransaction transaction = a_dbTransaction;
/* 5504 */     if (transaction == null)
/*      */     {
/* 5506 */       transaction = getDBTransactionManager().getNewTransaction();
/*      */     }
/*      */ 
/* 5510 */     boolean bIsAdmin = false;
/* 5511 */     if (a_lUserId > 0L)
/*      */     {
/* 5513 */       ABUser user = (ABUser)getUser(transaction, a_lUserId);
/* 5514 */       bIsAdmin = user.getIsAdmin();
/*      */     }
/*      */ 
/* 5517 */     if (!bIsAdmin)
/*      */     {
/* 5521 */       int iMinNumGroups = 1;
/*      */ 
/* 5523 */       if (!AssetBankSettings.getStrictFilterExclusions())
/*      */       {
/* 5526 */         iMinNumGroups = getNumberOfGroupsForUser(transaction, a_lUserId);
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/* 5531 */         Connection con = transaction.getConnection();
/*      */ 
/* 5535 */         String sSQL = "SELECT f.Id fId, f.Name, COUNT(ug.Id) AS NumGroups  FROM Filter f INNER JOIN GroupFilterExclusion gue ON f.Id = gue.FilterId INNER JOIN UserGroup ug ON gue.UserGroupId = ug.Id LEFT JOIN UserInGroup uig ON (uig.UserGroupId=ug.Id AND uig.UserId=?) WHERE ug.IsDefaultGroup>0 OR uig.UserId>0 GROUP BY f.Id, f.Name";
/*      */ 
/* 5544 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 5545 */         psql.setLong(1, a_lUserId);
/* 5546 */         ResultSet rs = psql.executeQuery();
/* 5547 */         Filter filter = null;
/*      */ 
/* 5549 */         while (rs.next())
/*      */         {
/* 5551 */           if ((rs.getLong("fId") <= 0L) || (rs.getInt("NumGroups") < iMinNumGroups))
/*      */             continue;
/* 5553 */           if ((filter == null) || (filter.getId() != rs.getLong("fId")))
/*      */           {
/* 5555 */             filter = new Filter();
/* 5556 */             filter.setId(rs.getLong("fId"));
/* 5557 */             filter.setName(rs.getString("Name"));
/*      */           }
/*      */ 
/* 5560 */           vExclusions.add(filter);
/*      */         }
/*      */ 
/* 5565 */         psql.close();
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/* 5569 */         this.m_logger.error("SQL Exception whilst getting filter exclusions : " + e);
/* 5570 */         throw new Bn2Exception("SQL Exception whilst getting filter exclusions : " + e, e);
/*      */       }
/*      */       finally
/*      */       {
/* 5575 */         if ((a_dbTransaction == null) && (transaction != null))
/*      */         {
/*      */           try
/*      */           {
/* 5579 */             transaction.commit();
/*      */           }
/*      */           catch (SQLException sqle)
/*      */           {
/* 5583 */             this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 5589 */     return vExclusions;
/*      */   }
/*      */ 
/*      */   public Vector getFilterExclusionsForUser(DBTransaction a_dbTransaction, String a_sIP, String a_sURL)
/*      */     throws Bn2Exception
/*      */   {
/* 5606 */     Vector vExclusions = new Vector();
/* 5607 */     DBTransaction transaction = a_dbTransaction;
/* 5608 */     if (transaction == null)
/*      */     {
/* 5610 */       transaction = getDBTransactionManager().getNewTransaction();
/*      */     }
/*      */ 
/* 5614 */     int iMinNumGroups = 1;
/*      */ 
/* 5616 */     if (!AssetBankSettings.getStrictUsageExclusions())
/*      */     {
/* 5619 */       iMinNumGroups = getNumberOfDefaultGroups(transaction, a_sIP, a_sURL);
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 5624 */       Connection con = transaction.getConnection();
/*      */ 
/* 5627 */       String sSQL = "SELECT f.Id fId, f.Name, COUNT(ug.Id) AS NumGroups  FROM Filter f INNER JOIN GroupFilterExclusion gue ON f.Id = gue.FilterId INNER JOIN UserGroup ug ON gue.UserGroupId = ug.Id WHERE " + getDefaultGroupSqlCriteria(a_sIP, a_sURL, con) + "GROUP BY f.Id, f.Name";
/*      */ 
/* 5635 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 5636 */       ResultSet rs = psql.executeQuery();
/* 5637 */       Filter filter = null;
/*      */ 
/* 5639 */       while (rs.next())
/*      */       {
/* 5641 */         if ((rs.getLong("fId") <= 0L) || (rs.getInt("NumGroups") < iMinNumGroups))
/*      */           continue;
/* 5643 */         if ((filter == null) || (filter.getId() != rs.getLong("fId")))
/*      */         {
/* 5645 */           filter = new Filter();
/* 5646 */           filter.setId(rs.getLong("fId"));
/* 5647 */           filter.setName(rs.getString("Name"));
/*      */         }
/* 5649 */         vExclusions.add(filter);
/*      */       }
/*      */ 
/* 5652 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 5656 */       this.m_logger.error("SQL Exception whilst getting default filter exclusions : " + e);
/* 5657 */       throw new Bn2Exception("SQL Exception whilst getting default filter exclusions : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 5662 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 5666 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 5670 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 5675 */     return vExclusions;
/*      */   }
/*      */ 
/*      */   public Vector getUsageExclusionsForUser(DBTransaction a_dbTransaction, String a_sIP, String a_sURL)
/*      */     throws Bn2Exception
/*      */   {
/* 5693 */     Connection con = null;
/* 5694 */     PreparedStatement psql = null;
/* 5695 */     ResultSet rs = null;
/* 5696 */     String sSQL = null;
/* 5697 */     Vector vExclusions = new Vector();
/*      */ 
/* 5699 */     DBTransaction transaction = a_dbTransaction;
/* 5700 */     if (transaction == null)
/*      */     {
/* 5702 */       transaction = getDBTransactionManager().getNewTransaction();
/*      */     }
/*      */ 
/* 5706 */     int iMinNumGroups = 0;
/*      */ 
/* 5708 */     if (AssetBankSettings.getStrictUsageExclusions())
/*      */     {
/* 5711 */       iMinNumGroups = 1;
/*      */     }
/*      */     else
/*      */     {
/* 5716 */       iMinNumGroups = getNumberOfDefaultGroups(transaction, a_sIP, a_sURL);
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 5721 */       con = transaction.getConnection();
/*      */ 
/* 5724 */       sSQL = "SELECT ut.Id utId, ut.Description, COUNT(ug.Id) AS NumGroups  FROM UsageType ut INNER JOIN GroupUsageExclusion gue ON ut.Id = gue.UsageTypeId INNER JOIN UserGroup ug ON gue.UserGroupId = ug.Id WHERE " + getDefaultGroupSqlCriteria(a_sIP, a_sURL, con);
/*      */ 
/* 5731 */       sSQL = sSQL + "GROUP BY ut.Id, ut.Description";
/*      */ 
/* 5733 */       psql = con.prepareStatement(sSQL);
/* 5734 */       rs = psql.executeQuery();
/*      */ 
/* 5736 */       UsageType usage = null;
/*      */ 
/* 5739 */       while (rs.next())
/*      */       {
/* 5743 */         if ((rs.getLong("utId") <= 0L) || (rs.getInt("NumGroups") < iMinNumGroups))
/*      */           continue;
/* 5745 */         if ((usage == null) || (usage.getId() != rs.getLong("utId")))
/*      */         {
/* 5747 */           usage = new UsageType();
/* 5748 */           usage.setId(rs.getLong("utId"));
/* 5749 */           usage.setDescription(rs.getString("Description"));
/*      */         }
/*      */ 
/* 5752 */         vExclusions.add(usage);
/*      */       }
/*      */ 
/* 5757 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 5761 */       this.m_logger.error("SQL Exception whilst getting usage exclusions : " + e);
/* 5762 */       throw new Bn2Exception("SQL Exception whilst getting usage exclusions : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 5767 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 5771 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 5775 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 5780 */     return vExclusions;
/*      */   }
/*      */ 
/*      */   private int getNumberOfGroupsForUser(DBTransaction a_dbTransaction, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 5795 */     Connection con = null;
/* 5796 */     PreparedStatement psql = null;
/* 5797 */     ResultSet rs = null;
/* 5798 */     String sSQL = null;
/*      */ 
/* 5800 */     int iNumGroups = 0;
/*      */     try
/*      */     {
/* 5804 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 5807 */       sSQL = "SELECT COUNT(*) numgroups FROM UserGroup ug LEFT JOIN UserInGroup uig  ON ug.Id = uig.UserGroupId WHERE uig.UserId=? OR ug.IsDefaultGroup=1";
/*      */ 
/* 5810 */       psql = con.prepareStatement(sSQL);
/* 5811 */       psql.setLong(1, a_lUserId);
/* 5812 */       rs = psql.executeQuery();
/* 5813 */       while (rs.next())
/*      */       {
/* 5815 */         iNumGroups = rs.getInt("numgroups");
/*      */       }
/*      */ 
/* 5818 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 5822 */       throw new Bn2Exception("getNumberOfGroupsForUser: SQL Exception : " + e, e);
/*      */     }
/*      */ 
/* 5825 */     return iNumGroups;
/*      */   }
/*      */ 
/*      */   private int getNumberOfDefaultGroups(DBTransaction a_dbTransaction, String a_sIP, String a_sURL)
/*      */     throws Bn2Exception
/*      */   {
/* 5841 */     Connection con = null;
/* 5842 */     PreparedStatement psql = null;
/* 5843 */     ResultSet rs = null;
/* 5844 */     String sSQL = null;
/*      */ 
/* 5846 */     int iNumGroups = 0;
/*      */     try
/*      */     {
/* 5850 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 5853 */       sSQL = "SELECT COUNT(*) numgroups FROM UserGroup ug WHERE " + getDefaultGroupSqlCriteria(a_sIP, a_sURL, con);
/*      */ 
/* 5856 */       psql = con.prepareStatement(sSQL);
/* 5857 */       rs = psql.executeQuery();
/* 5858 */       while (rs.next())
/*      */       {
/* 5860 */         iNumGroups = rs.getInt("numgroups");
/*      */       }
/*      */ 
/* 5863 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 5867 */       throw new Bn2Exception("getNumberOfGroupsForUser: SQL Exception : " + e, e);
/*      */     }
/*      */ 
/* 5870 */     return iNumGroups;
/*      */   }
/*      */ 
/*      */   private String getDefaultGroupSqlCriteria(String a_sIP, String a_sURL, Connection con)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 5883 */     String sSql = " ug.IsDefaultGroup=1 ";
/*      */ 
/* 5885 */     if (StringUtil.stringIsPopulated(a_sIP))
/*      */     {
/* 5888 */       Vector vecGroupIds = getGroupIdsForIPAddress(a_sIP, con);
/*      */ 
/* 5890 */       if ((vecGroupIds != null) && (vecGroupIds.size() > 0))
/*      */       {
/* 5893 */         String sGroupIds = StringUtil.convertNumbersToString(vecGroupIds, ",");
/*      */ 
/* 5895 */         sSql = sSql + " OR ug.Id IN ( " + sGroupIds + ")";
/*      */       }
/*      */     }
/* 5898 */     if (StringUtil.stringIsPopulated(a_sURL))
/*      */     {
/* 5900 */       sSql = sSql + " OR ug.URLMapping LIKE '%" + DBUtil.safeSQLStringValue(a_sURL) + "%' ";
/*      */     }
/*      */ 
/* 5903 */     return sSql;
/*      */   }
/*      */ 
/*      */   public boolean getApproverEmailsForAsset(long a_lAssetId, HashMap a_hmAdminEmails)
/*      */     throws Bn2Exception
/*      */   {
/* 5919 */     boolean bFound = false;
/* 5920 */     UserSearchCriteria search = new UserSearchCriteria();
/* 5921 */     search.setAssetId(a_lAssetId);
/* 5922 */     search.setPermissionInCategory(7);
/* 5923 */     Vector vecApprovers = findUsers(search, 1);
/*      */ 
/* 5926 */     Iterator it = vecApprovers.iterator();
/* 5927 */     while (it.hasNext())
/*      */     {
/* 5929 */       ABUser approver = (ABUser)it.next();
/* 5930 */       String sEmail = approver.getEmailAddress();
/*      */ 
/* 5932 */       if (StringUtil.stringIsPopulated(sEmail))
/*      */       {
/* 5934 */         a_hmAdminEmails.put(sEmail, sEmail);
/* 5935 */         bFound = true;
/*      */       }
/*      */     }
/*      */ 
/* 5939 */     return bFound;
/*      */   }
/*      */ 
/*      */   private boolean getUserEmailsForGroup(long a_lGroupId, Map<String, String> a_hmEmails, boolean a_bReceiveAlertsOnly)
/*      */     throws Bn2Exception
/*      */   {
/* 5956 */     boolean bFound = false;
/* 5957 */     UserSearchCriteria search = new UserSearchCriteria();
/* 5958 */     search.setGroupId(a_lGroupId);
/* 5959 */     Vector vecUsers = findUsers(search, 1);
/*      */ 
/* 5962 */     Iterator it = vecUsers.iterator();
/* 5963 */     while (it.hasNext())
/*      */     {
/* 5965 */       ABUser user = (ABUser)it.next();
/* 5966 */       String sEmail = user.getEmailAddress();
/*      */ 
/* 5968 */       if ((StringUtil.stringIsPopulated(sEmail)) && ((!a_bReceiveAlertsOnly) || (user.getReceiveAlerts())))
/*      */       {
/* 5970 */         a_hmEmails.put(sEmail, sEmail);
/* 5971 */         bFound = true;
/*      */       }
/*      */     }
/*      */ 
/* 5975 */     return bFound;
/*      */   }
/*      */ 
/*      */   public Map<String, String> getUserEmailsForGroups(List<StringDataBean> a_vecGroups)
/*      */     throws Bn2Exception
/*      */   {
/* 5990 */     Map hmEmails = new HashMap();
/*      */ 
/* 5992 */     Iterator it = a_vecGroups.iterator();
/* 5993 */     while (it.hasNext())
/*      */     {
/* 5995 */       StringDataBean group = (StringDataBean)it.next();
/*      */ 
/* 5997 */       getUserEmailsForGroup(group.getId(), hmEmails, false);
/*      */     }
/*      */ 
/* 6000 */     return hmEmails;
/*      */   }
/*      */ 
/*      */   public void populateUserEmailsForGroups(long[] a_alGroupIds, HashMap a_hmEmails, boolean a_bReceiveAlertsOnly)
/*      */     throws Bn2Exception
/*      */   {
/* 6015 */     for (int i = 0; i < a_alGroupIds.length; i++)
/*      */     {
/* 6017 */       long lId = a_alGroupIds[i];
/*      */ 
/* 6019 */       getUserEmailsForGroup(lId, a_hmEmails, a_bReceiveAlertsOnly);
/*      */     }
/*      */   }
/*      */ 
/*      */   public RefDataItem getDivision(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 6039 */     Connection con = null;
/* 6040 */     RefDataItem division = new RefDataItem();
/*      */     try
/*      */     {
/* 6044 */       if (a_lId >= 0L)
/*      */       {
/* 6046 */         con = a_dbTransaction.getConnection();
/*      */ 
/* 6048 */         String sSql = "SELECT Id, Name FROM Division WHERE Id=?";
/*      */ 
/* 6051 */         PreparedStatement psql = con.prepareStatement(sSql);
/* 6052 */         psql.setLong(1, a_lId);
/* 6053 */         ResultSet rs = psql.executeQuery();
/*      */ 
/* 6056 */         while (rs.next())
/*      */         {
/* 6058 */           division.setId(a_lId);
/* 6059 */           division.setDescription(rs.getString("Name"));
/*      */         }
/*      */ 
/* 6062 */         psql.close();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 6068 */       throw new Bn2Exception("Exception occurred in UserManager.getDivision : " + e, e);
/*      */     }
/*      */ 
/* 6071 */     return division;
/*      */   }
/*      */ 
/*      */   public void saveDivision(DBTransaction a_dbTransaction, RefDataItem a_division)
/*      */     throws Bn2Exception
/*      */   {
/* 6089 */     String ksMethodName = "saveDivision";
/* 6090 */     Connection con = null;
/*      */     try
/*      */     {
/* 6094 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 6097 */       String sSql = null;
/* 6098 */       PreparedStatement psql = null;
/* 6099 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/* 6100 */       long lNewId = 0L;
/*      */ 
/* 6102 */       if (a_division.getId() > 0L)
/*      */       {
/* 6105 */         sSql = "UPDATE Division SET Name=? WHERE Id=?";
/*      */       }
/*      */       else
/*      */       {
/* 6110 */         sSql = "INSERT INTO Division (";
/*      */ 
/* 6112 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 6114 */           lNewId = sqlGenerator.getUniqueId(con, "DivisionSequence");
/* 6115 */           sSql = sSql + "Id, ";
/*      */         }
/*      */ 
/* 6118 */         sSql = sSql + "Name) VALUES (";
/*      */ 
/* 6120 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 6122 */           sSql = sSql + "?,";
/*      */         }
/*      */ 
/* 6125 */         sSql = sSql + "?)";
/*      */       }
/*      */ 
/* 6128 */       psql = con.prepareStatement(sSql);
/* 6129 */       int iField = 1;
/*      */ 
/* 6131 */       if ((a_division.getId() <= 0L) && (!sqlGenerator.usesAutoincrementFields()))
/*      */       {
/* 6133 */         psql.setLong(iField++, lNewId);
/*      */       }
/*      */ 
/* 6136 */       psql.setString(iField++, a_division.getDescription());
/*      */ 
/* 6138 */       if (a_division.getId() > 0L)
/*      */       {
/* 6140 */         psql.setLong(iField++, a_division.getId());
/*      */       }
/*      */ 
/* 6143 */       psql.executeUpdate();
/* 6144 */       psql.close();
/*      */ 
/* 6147 */       if (a_division.getId() <= 0L)
/*      */       {
/* 6149 */         if (sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 6151 */           lNewId = sqlGenerator.getUniqueId(con, "Division");
/*      */         }
/*      */ 
/* 6154 */         a_division.setId(lNewId);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 6160 */       this.m_logger.error("ABUserManager.saveDivision:SQL Exception whilst trying to save division with Id: " + a_division.getId() + e);
/* 6161 */       throw new Bn2Exception("ABUserManager.saveDivision:SQL Exception whilst trying save division: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteDivision(DBTransaction a_dbTransaction, long a_lDivisionId)
/*      */     throws Bn2Exception
/*      */   {
/* 6181 */     String ksMethodName = "deleteDivision";
/* 6182 */     Connection con = null;
/*      */     try
/*      */     {
/* 6186 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 6188 */       String sSql = null;
/* 6189 */       PreparedStatement psql = null;
/*      */ 
/* 6192 */       sSql = "UPDATE AssetBankUser SET DivisionId = NULL WHERE DivisionId=?";
/* 6193 */       psql = con.prepareStatement(sSql);
/* 6194 */       psql.setLong(1, a_lDivisionId);
/* 6195 */       psql.executeUpdate();
/* 6196 */       psql.close();
/*      */ 
/* 6198 */       sSql = "DELETE FROM Division WHERE Id=?";
/* 6199 */       psql = con.prepareStatement(sSql);
/* 6200 */       psql.setLong(1, a_lDivisionId);
/* 6201 */       psql.executeUpdate();
/* 6202 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 6207 */       this.m_logger.error("ABUserManager.deleteDivision : SQL Exception whilst trying to delete division : " + e);
/* 6208 */       throw new Bn2Exception("ABUserManager.deleteDivision : SQL Exception whilst trying delete division : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getAllDivisions(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 6223 */     Connection con = null;
/* 6224 */     Vector vec = null;
/* 6225 */     String sSql = null;
/*      */     try
/*      */     {
/* 6229 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 6231 */       sSql = "SELECT * FROM Division ORDER BY Name";
/*      */ 
/* 6233 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 6234 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 6236 */       vec = new Vector();
/*      */ 
/* 6238 */       while (rs.next())
/*      */       {
/* 6240 */         RefDataItem division = new RefDataItem();
/*      */ 
/* 6242 */         division.setId(rs.getLong("Id"));
/* 6243 */         division.setDescription(rs.getString("Name"));
/*      */ 
/* 6245 */         vec.add(division);
/*      */       }
/*      */ 
/* 6248 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 6252 */       throw new Bn2Exception("Exception occurred in UserMngr:getAllDivisions : " + e, e);
/*      */     }
/*      */ 
/* 6255 */     return vec;
/*      */   }
/*      */ 
/*      */   public void clearCaches()
/*      */   {
/* 6270 */     this.m_categoryCountCacheManager.invalidateCache();
/*      */   }
/*      */ 
/*      */   public void updateUserAddress(DBTransaction a_dbTransaction, long a_lUserId, Address a_address)
/*      */     throws Bn2Exception
/*      */   {
/* 6286 */     ABUser user = (ABUser)getUser(a_dbTransaction, a_lUserId);
/*      */ 
/* 6288 */     Address existing = user.getHomeAddress();
/* 6289 */     existing.copy(a_address);
/*      */ 
/* 6291 */     this.m_addressManager.saveAddress(a_dbTransaction, existing);
/*      */   }
/*      */ 
/*      */   public void updateUserName(DBTransaction a_dbTransaction, long a_lUserId, String a_sFirst, String a_sLast)
/*      */     throws Bn2Exception
/*      */   {
/* 6307 */     String ksMethodName = "updateUserName";
/* 6308 */     Connection con = null;
/*      */     try
/*      */     {
/* 6312 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 6314 */       String sSql = null;
/* 6315 */       PreparedStatement psql = null;
/*      */ 
/* 6318 */       sSql = "UPDATE AssetBankUser SET Forename=?, Surname=? WHERE Id=?";
/* 6319 */       psql = con.prepareStatement(sSql);
/* 6320 */       psql.setString(1, a_sFirst);
/* 6321 */       psql.setString(2, a_sLast);
/* 6322 */       psql.setLong(3, a_lUserId);
/* 6323 */       psql.executeUpdate();
/* 6324 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 6329 */       this.m_logger.error("ABUserManager.updateUserName : SQL Exception whilst trying to delete division : " + e);
/* 6330 */       throw new Bn2Exception("ABUserManager.updateUserName : SQL Exception whilst trying delete division : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getAllBrands(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 6346 */     Connection con = null;
/* 6347 */     Vector vec = null;
/* 6348 */     String sSql = null;
/*      */     try
/*      */     {
/* 6352 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 6354 */       sSql = "SELECT * FROM Brand ORDER BY Name";
/*      */ 
/* 6356 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 6357 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 6359 */       vec = new Vector();
/*      */ 
/* 6361 */       while (rs.next())
/*      */       {
/* 6363 */         Brand brand = new Brand();
/* 6364 */         populateBrandFromResultSet(brand, rs);
/*      */ 
/* 6366 */         vec.add(brand);
/*      */       }
/*      */ 
/* 6369 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 6373 */       throw new Bn2Exception("Exception occurred in UserMngr:getAllBrands : " + e, e);
/*      */     }
/*      */ 
/* 6376 */     return vec;
/*      */   }
/*      */ 
/*      */   public Vector<Group> getBrandGroups(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 6391 */     Vector vec = null;
/*      */     try
/*      */     {
/* 6395 */       Connection con = a_dbTransaction.getConnection();
/*      */ 
/* 6397 */       SearchResults results = searchGroups(con, true, false, null, null, -1, -1, -1L, false);
/* 6398 */       vec = results.getSearchResults();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 6402 */       throw new Bn2Exception("Exception occurred in UserMngr:getBrandGroups : " + e, e);
/*      */     }
/*      */ 
/* 6405 */     return vec;
/*      */   }
/*      */ 
/*      */   public Vector<Group> getAutomaticRegistrationGroupsForBrand(DBTransaction a_dbTransaction, long a_lBrandId)
/*      */     throws Bn2Exception
/*      */   {
/* 6416 */     Vector vec = null;
/*      */     try
/*      */     {
/* 6420 */       Connection con = a_dbTransaction.getConnection();
/*      */ 
/* 6422 */       SearchResults results = searchGroups(con, true, false, null, null, -1, -1, a_lBrandId, true);
/* 6423 */       vec = results.getSearchResults();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 6427 */       throw new Bn2Exception("Exception occurred in getAutomaticRegistrationGroupsForBrand : " + e, e);
/*      */     }
/*      */ 
/* 6430 */     return vec;
/*      */   }
/*      */ 
/*      */   public Brand getBrandByCode(String a_sCode) throws Bn2Exception
/*      */   {
/* 6444 */     Connection con = null;
/*      */     Brand brand;
/*      */     try
/*      */     {
/* 6449 */       con = this.m_dataSource.getConnection();
/* 6450 */       brand = getBrandByCode(con, a_sCode);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       try
/*      */       {
/* 6456 */         con.rollback();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*      */       }
/*      */ 
/* 6462 */       throw new Bn2Exception("Exception occurred in UserMngr:getBrand : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 6467 */       if (con != null)
/*      */       {
/*      */         try
/*      */         {
/* 6471 */           con.commit();
/* 6472 */           con.close();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 6477 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 6482 */     return brand;
/*      */   }
/*      */ 
/*      */   public Brand getBrandById(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 6496 */     Connection con = null;
/* 6497 */     Brand brand = new Brand();
/*      */     try
/*      */     {
/* 6501 */       con = a_dbTransaction.getConnection();
/* 6502 */       String sSql = "SELECT * FROM Brand WHERE ID=?";
/*      */ 
/* 6504 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 6505 */       psql.setLong(1, a_lId);
/* 6506 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 6508 */       if (rs.next())
/*      */       {
/* 6510 */         populateBrandFromResultSet(brand, rs);
/*      */       }
/*      */ 
/* 6513 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 6517 */       throw new Bn2Exception("Exception occurred in UserMngr:getBrand : " + e, e);
/*      */     }
/*      */ 
/* 6521 */     return brand;
/*      */   }
/*      */ 
/*      */   private Brand getBrandByCode(Connection con, String a_sCode)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 6537 */     Brand brand = new Brand();
/*      */ 
/* 6540 */     AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/* 6542 */     String sSql = "SELECT * FROM Brand b WHERE " + sqlGenerator.getUpperCaseFunction("b.Code") + "=?";
/*      */ 
/* 6546 */     PreparedStatement psql = con.prepareStatement(sSql);
/* 6547 */     psql.setString(1, a_sCode.toUpperCase());
/*      */ 
/* 6549 */     ResultSet rs = psql.executeQuery();
/*      */ 
/* 6551 */     if (rs.next())
/*      */     {
/* 6553 */       populateBrandFromResultSet(brand, rs);
/*      */     }
/*      */ 
/* 6556 */     psql.close();
/*      */ 
/* 6558 */     return brand;
/*      */   }
/*      */ 
/*      */   private void populateBrandFromResultSet(Brand brand, ResultSet rs)
/*      */     throws SQLException
/*      */   {
/* 6573 */     brand.setId(rs.getLong("Id"));
/* 6574 */     brand.setName(rs.getString("Name"));
/* 6575 */     brand.setContentListIdentifier(rs.getString("ContentListIdentifier"));
/* 6576 */     brand.setCssFile(rs.getString("CssFile"));
/* 6577 */     brand.setLogoFile(rs.getString("LogoFile"));
/* 6578 */     brand.setLogoAlt(rs.getString("LogoAlt"));
/* 6579 */     brand.setLogoHeight(rs.getLong("LogoHeight"));
/* 6580 */     brand.setLogoWidth(rs.getLong("LogoWidth"));
/* 6581 */     brand.setCode(rs.getString("Code"));
/*      */   }
/*      */ 
/*      */   public ListItem getBrandedListItem(DBTransaction a_dbTransaction, String a_sIdentifier, String a_sContentListIdentifier, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/* 6600 */     String sItemId = a_sIdentifier;
/*      */ 
/* 6603 */     if (StringUtil.stringIsPopulated(a_sContentListIdentifier))
/*      */     {
/* 6605 */       sItemId = a_sIdentifier + "_" + a_sContentListIdentifier;
/*      */     }
/* 6607 */     ListItem item = this.m_listManager.getListItem(a_dbTransaction, sItemId, a_language);
/*      */ 
/* 6610 */     if ((item == null) || (!StringUtil.stringIsPopulated(item.getBody())))
/*      */     {
/* 6612 */       item = this.m_listManager.getListItem(a_dbTransaction, a_sIdentifier, a_language);
/*      */     }
/*      */ 
/* 6615 */     return item;
/*      */   }
/*      */ 
/*      */   public Vector getUserBulkUploads(DBTransaction a_dbTransaction, long a_lUserId, int a_iLimit)
/*      */     throws Bn2Exception
/*      */   {
/* 6637 */     Connection cCon = null;
/* 6638 */     Vector vecBulkUploads = null;
/*      */     try
/*      */     {
/* 6642 */       cCon = a_dbTransaction.getConnection();
/*      */ 
/* 6644 */       String sSql = "SELECT DISTINCT BulkUploadTimestamp FROM Asset WHERE AddedByUserId=? ORDER BY BulkUploadTimestamp DESC";
/*      */ 
/* 6646 */       PreparedStatement psql = cCon.prepareStatement(sSql);
/* 6647 */       psql.setLong(1, a_lUserId);
/* 6648 */       ResultSet rs = psql.executeQuery();
/* 6649 */       int iCount = 0;
/*      */ 
/* 6651 */       while (rs.next())
/*      */       {
/* 6654 */         if ((a_iLimit > 0) && (iCount >= a_iLimit))
/*      */         {
/*      */           break;
/*      */         }
/*      */ 
/* 6660 */         if (vecBulkUploads == null)
/*      */         {
/* 6662 */           vecBulkUploads = new Vector();
/*      */         }
/*      */ 
/* 6665 */         Timestamp timestamp = rs.getTimestamp("BulkUploadTimestamp");
/*      */ 
/* 6667 */         if (timestamp != null)
/*      */         {
/* 6670 */           vecBulkUploads.add(new java.util.Date(timestamp.getTime()));
/*      */ 
/* 6672 */           iCount++;
/*      */         }
/*      */       }
/*      */ 
/* 6676 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 6681 */       this.m_logger.debug("Exception occurred in UserMngr:getUser : " + e);
/* 6682 */       throw new Bn2Exception("Exception occurred in UserMngr:getUser : " + e, e);
/*      */     }
/*      */ 
/* 6685 */     return vecBulkUploads;
/*      */   }
/*      */ 
/*      */   private Vector getGroupIdsForIPAddress(String a_sIPAddress, Connection a_cCon)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 6709 */     Vector groupIds = new Vector();
/* 6710 */     SearchResults results = searchGroups(a_cCon, false, false, null, null, -1, -1, -1L, false);
/* 6711 */     Vector groups = results.getSearchResults();
/*      */ 
/* 6713 */     for (Iterator it = groups.iterator(); it.hasNext(); )
/*      */     {
/* 6715 */       Group group = (Group)it.next();
/*      */ 
/* 6717 */       if (group.isIpIncluded(a_sIPAddress))
/*      */       {
/* 6719 */         groupIds.add(new Long(group.getId()));
/*      */       }
/*      */     }
/* 6722 */     return groupIds;
/*      */   }
/*      */ 
/*      */   public Group getPublicGroup(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 6736 */     Vector vec = null;
/*      */     try
/*      */     {
/* 6740 */       Connection con = a_dbTransaction.getConnection();
/* 6741 */       SearchResults results = searchGroups(con, false, true, null, null, -1, -1, -1L, false);
/* 6742 */       vec = results.getSearchResults();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 6746 */       throw new Bn2Exception("Exception occurred in UserMngr:getPublicGroup : " + e, e);
/*      */     }
/*      */ 
/* 6749 */     Group ug = new Group();
/* 6750 */     if (vec.size() > 0)
/*      */     {
/* 6752 */       ug = (Group)vec.get(0);
/*      */     }
/*      */ 
/* 6755 */     return ug;
/*      */   }
/*      */ 
/*      */   public boolean getPublicGroupIPCheck(String a_sIP)
/*      */     throws Bn2Exception
/*      */   {
/* 6770 */     boolean bPermitted = true;
/*      */ 
/* 6772 */     DBTransaction transaction = getDBTransactionManager().getNewTransaction();
/*      */ 
/* 6774 */     Group publicGroup = getPublicGroup(transaction);
/*      */ 
/* 6776 */     if ((publicGroup.getHasIpMapping()) && (!publicGroup.isIpIncluded(a_sIP)))
/*      */     {
/* 6778 */       bPermitted = false;
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 6783 */       transaction.commit();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 6787 */       this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */     }
/*      */ 
/* 6790 */     return bPermitted;
/*      */   }
/*      */ 
/*      */   public boolean getCanPublicEmailAssets()
/*      */     throws Bn2Exception
/*      */   {
/* 6802 */     boolean bCanEmail = false;
/*      */ 
/* 6804 */     DBTransaction transaction = getDBTransactionManager().getNewTransaction();
/* 6805 */     Group publicGroup = getPublicGroup(transaction);
/*      */ 
/* 6807 */     if (publicGroup.getUserCanEmailAssets())
/*      */     {
/* 6809 */       bCanEmail = true;
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 6814 */       transaction.commit();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 6818 */       this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */     }
/*      */ 
/* 6821 */     return bCanEmail;
/*      */   }
/*      */ 
/*      */   public boolean getCanPublicViewLargerSize()
/*      */     throws Bn2Exception
/*      */   {
/* 6827 */     boolean bCanEmail = false;
/*      */ 
/* 6829 */     DBTransaction transaction = getDBTransactionManager().getNewTransaction();
/* 6830 */     Group publicGroup = getPublicGroup(transaction);
/*      */ 
/* 6832 */     if (publicGroup.getUsersCanViewLargerSize())
/*      */     {
/* 6834 */       bCanEmail = true;
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 6839 */       transaction.commit();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 6843 */       this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */     }
/*      */ 
/* 6846 */     return bCanEmail;
/*      */   }
/*      */ 
/*      */   public boolean deleteUserOrRemoveFromOU(DBTransaction a_dbTransaction, long a_lUserId, ABUserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/* 6860 */     boolean bPermission = true;
/*      */ 
/* 6862 */     ABUser adminUser = (ABUser)a_userProfile.getUser();
/*      */ 
/* 6865 */     Vector vecOUs = this.m_orgUnitManager.getOrgUnitsForUser(a_dbTransaction, a_lUserId);
/* 6866 */     if ((a_userProfile.getIsOrgUnitAdmin()) && (!adminUser.getIsAdminOfAtLeastOneOrgUnit(vecOUs)))
/*      */     {
/* 6868 */       this.m_logger.error("DeleteUserAction.execute : User not admin for user: id=" + a_lUserId);
/* 6869 */       bPermission = false;
/* 6870 */       return bPermission;
/*      */     }
/*      */ 
/* 6874 */     if (a_userProfile.getIsOrgUnitAdmin())
/*      */     {
/* 6877 */       this.m_orgUnitManager.deleteUserFromManagedOrgUnits(a_dbTransaction, a_lUserId, adminUser.getId());
/*      */     }
/*      */     else
/*      */     {
/* 6882 */       deleteUser(a_dbTransaction, a_lUserId);
/*      */     }
/* 6884 */     return bPermission;
/*      */   }
/*      */ 
/*      */   public void updateSearchResultsPerPage(DBTransaction a_dbTransaction, long a_lUserId, int a_iSearchResultsPerPage)
/*      */     throws Bn2Exception
/*      */   {
/* 6895 */     String ksMethodName = "updateSearchResultsPerPage";
/* 6896 */     Connection con = null;
/*      */     try
/*      */     {
/* 6900 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 6902 */       String sSql = null;
/* 6903 */       PreparedStatement psql = null;
/*      */ 
/* 6906 */       sSql = "UPDATE AssetBankUser SET SearchResultsPerPage=? WHERE Id=?";
/* 6907 */       psql = con.prepareStatement(sSql);
/* 6908 */       psql.setInt(1, a_iSearchResultsPerPage);
/* 6909 */       psql.setLong(2, a_lUserId);
/* 6910 */       psql.executeUpdate();
/* 6911 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 6916 */       this.m_logger.error("ABUserManager.updateSearchResultsPerPage : SQL Exception whilst trying to update AssetBankUser.SearchResultsPerPage : " + e);
/* 6917 */       throw new Bn2Exception("ABUserManager.updateSearchResultsPerPage : SQL Exception whilst trying to update AssetBankUser.SearchResultsPerPag : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateDateLastLoggedIn(DBTransaction a_dbTransaction, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 6928 */     String ksMethodName = "updateDateLastLoggedIn";
/* 6929 */     Connection con = null;
/*      */     try
/*      */     {
/* 6933 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 6935 */       String sSql = null;
/* 6936 */       PreparedStatement psql = null;
/*      */ 
/* 6938 */       sSql = "UPDATE AssetBankUser SET DateLastLoggedIn=?, ReactivationPending=? WHERE Id=?";
/* 6939 */       psql = con.prepareStatement(sSql);
/* 6940 */       DBUtil.setFieldTimestampOrNull(psql, 1, new java.util.Date());
/* 6941 */       psql.setBoolean(2, false);
/* 6942 */       psql.setLong(3, a_lUserId);
/* 6943 */       psql.executeUpdate();
/* 6944 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 6949 */       this.m_logger.error("ABUserManager.updateDateLastLoggedIn : SQL Exception whilst trying to update AssetBankUser.DateLastLoggedIn : " + e);
/* 6950 */       throw new Bn2Exception("ABUserManager.updateDateLastLoggedIn : SQL Exception whilst trying to update AssetBankUser.DateLastLoggedIn : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void expireAndDeleteUsers()
/*      */     throws Bn2Exception
/*      */   {
/* 6965 */     String ksMethodName = "checkIfReactivationRequired";
/*      */ 
/* 6967 */     Connection cCon = null;
/* 6968 */     Vector vecUsersThatNeedReactivating = new Vector();
/* 6969 */     Vector vecUsersThatNeedDeleting = new Vector();
/*      */ 
/* 6972 */     Calendar expireFrom = new GregorianCalendar();
/* 6973 */     expireFrom.add(5, -AssetBankSettings.getVerifyUsersInactiveFor());
/*      */ 
/* 6975 */     DBTransaction transaction = getDBTransactionManager().getNewTransaction();
/*      */     try
/*      */     {
/* 6980 */       cCon = transaction.getConnection();
/*      */ 
/* 6983 */       String sSql = "SELECT u.Id userId, u.Password, u.Surname, u.Forename, u.Username, u.EmailAddress, u.Title, u.Organisation, u.Address, u.RegistrationInfo, u.IsAdmin, u.IsSuspended, u.DisplayName, u.CN, u.DistinguishedName, u.Mailbox, u.NotActiveDirectory, u.Hidden, u.IsDeleted, u.NotApproved, u.RequiresUpdate, u.RequestedOrgUnitId, u.CanBeCategoryAdmin, u.ExpiryDate, u.RegisterDate, u.JobTitle, u.VATNumber, u.LDAPServerIndex, u.ReceiveAlerts, u.CanLoginFromCms, u.AdminNotes, u.LastModifiedById, u.InvitedByUserId, u.AdditionalApprovalName, u.SearchResultsPerPage, u.ReactivationPending, ug.Id ugId, ug.Name, ug.Description, ug.IsDefaultGroup, ug.Mapping, ug.DiscountPercentage, ug.CanOnlyEditOwn, ug.CanEmailAssets, ug.CanRepurposeAssets, ug.CanSeeSourcePath, ug.MaxDownloadHeight, ug.MaxDownloadWidth, ug.CanInviteUsers, ug.CanAddEmptyAssets, ug.CanViewLargerSize, ug.AutomaticBrandRegister, ug.AdvancedViewing, groupou.Id groupouId, groupou.DiskQuotaMb groupouDiskQuotaMb, cat.name groupouName, ou.Id ouId, ou.DiskQuotaMb ouDiskQuotaMb, oucat.name ouName, divis.Id divId, divis.Name divName, addr.Id addrId, br.Id brId, br.Name brName, l.Id lId, l.Code lCode, l.Name lName, l.IsDefault lIsDefault, l.IsRightToLeft lIsRightToLeft, l.UsesLatinAlphabet lUsesLatinAlphabet FROM AssetBankUser u  LEFT JOIN UserInGroup uig ON uig.UserId = u.Id LEFT JOIN UserGroup ug ON uig.UserGroupId = ug.Id LEFT JOIN OrgUnitGroup oug ON oug.UserGroupId = ug.Id LEFT JOIN OrgUnit groupou ON oug.OrgUnitId = groupou.Id LEFT JOIN CM_Category cat ON groupou.RootOrgUnitCategoryId = cat.Id LEFT JOIN Brand br ON ug.BrandId = br.Id LEFT JOIN OrgUnit ou ON ou.AdminGroupId = ug.Id LEFT JOIN CM_Category oucat ON ou.RootOrgUnitCategoryId = oucat.Id LEFT JOIN Division divis ON divis.Id = u.DivisionId LEFT JOIN Address addr ON addr.Id = u.AddressId LEFT JOIN Language l ON l.Id = u.LanguageId ";
/* 6984 */       sSql = sSql + " WHERE u.IsDeleted=? " + "AND u.ReactivationPending=? " + "AND u.NotActiveDirectory=? " + "AND u.Hidden=? " + "AND u.IsAdmin=? " + "AND u.DateLastLoggedIn<?";
/*      */ 
/* 6992 */       PreparedStatement psql = cCon.prepareStatement(sSql);
/*      */ 
/* 6994 */       int iField = 1;
/* 6995 */       psql.setBoolean(iField++, false);
/* 6996 */       psql.setBoolean(iField++, false);
/* 6997 */       psql.setBoolean(iField++, true);
/* 6998 */       psql.setBoolean(iField++, false);
/* 6999 */       psql.setBoolean(iField++, false);
/* 7000 */       DBUtil.setFieldDateOrNull(psql, iField++, expireFrom.getTime());
/*      */ 
/* 7002 */       ResultSet rs = psql.executeQuery();
/* 7003 */       vecUsersThatNeedReactivating = buildUsers(transaction, rs);
/*      */ 
/* 7005 */       psql.close();
/*      */ 
/* 7008 */       if (vecUsersThatNeedReactivating != null)
/*      */       {
/* 7010 */         Iterator itUsersToReactivate = vecUsersThatNeedReactivating.iterator();
/*      */ 
/* 7012 */         while (itUsersToReactivate.hasNext())
/*      */         {
/* 7014 */           ABUser user = (ABUser)itUsersToReactivate.next();
/*      */ 
/* 7017 */           HashMap params = new HashMap();
/* 7018 */           params.put("template", "user_reactivation");
/* 7019 */           params.put("email", user.getEmailAddress());
/* 7020 */           params.put("name", user.getFullName());
/*      */ 
/* 7022 */           params.put("notLoggedInFor", Integer.toString(AssetBankSettings.getVerifyUsersInactiveFor()));
/* 7023 */           params.put("reactivationPeriod", Integer.toString(AssetBankSettings.getVerifyUsersReactivationPeriod()));
/*      */ 
/* 7026 */           String sLink = AssetBankSettings.getApplicationUrl() + "/action/reactivateAccount?id=" + user.getId();
/*      */ 
/* 7028 */           params.put("url", sLink);
/*      */           try
/*      */           {
/* 7032 */             this.m_emailManager.sendTemplatedEmail(params, user.getLanguage());
/*      */           }
/*      */           catch (Bn2Exception e)
/*      */           {
/* 7037 */             this.m_logger.warn("UserManager.checkIfReactivationRequired: The reactivation email was not successfully sent to the user: " + user.getEmailAddress() + ": " + e.getMessage());
/*      */           }
/*      */ 
/* 7041 */           sSql = "UPDATE AssetBankUser SET ReactivationPending=? WHERE Id=?";
/* 7042 */           psql = cCon.prepareStatement(sSql);
/* 7043 */           psql.setBoolean(1, true);
/* 7044 */           psql.setLong(2, user.getId());
/* 7045 */           psql.executeUpdate();
/* 7046 */           psql.close();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 7052 */       Calendar deleteFrom = new GregorianCalendar();
/* 7053 */       deleteFrom.add(5, -(AssetBankSettings.getVerifyUsersInactiveFor() + AssetBankSettings.getVerifyUsersReactivationPeriod()));
/*      */ 
/* 7055 */       cCon = transaction.getConnection();
/*      */ 
/* 7058 */       sSql = "SELECT u.Id userId, u.Password, u.Surname, u.Forename, u.Username, u.EmailAddress, u.Title, u.Organisation, u.Address, u.RegistrationInfo, u.IsAdmin, u.IsSuspended, u.DisplayName, u.CN, u.DistinguishedName, u.Mailbox, u.NotActiveDirectory, u.Hidden, u.IsDeleted, u.NotApproved, u.RequiresUpdate, u.RequestedOrgUnitId, u.CanBeCategoryAdmin, u.ExpiryDate, u.RegisterDate, u.JobTitle, u.VATNumber, u.LDAPServerIndex, u.ReceiveAlerts, u.CanLoginFromCms, u.AdminNotes, u.LastModifiedById, u.InvitedByUserId, u.AdditionalApprovalName, u.SearchResultsPerPage, u.ReactivationPending, ug.Id ugId, ug.Name, ug.Description, ug.IsDefaultGroup, ug.Mapping, ug.DiscountPercentage, ug.CanOnlyEditOwn, ug.CanEmailAssets, ug.CanRepurposeAssets, ug.CanSeeSourcePath, ug.MaxDownloadHeight, ug.MaxDownloadWidth, ug.CanInviteUsers, ug.CanAddEmptyAssets, ug.CanViewLargerSize, ug.AutomaticBrandRegister, ug.AdvancedViewing, groupou.Id groupouId, groupou.DiskQuotaMb groupouDiskQuotaMb, cat.name groupouName, ou.Id ouId, ou.DiskQuotaMb ouDiskQuotaMb, oucat.name ouName, divis.Id divId, divis.Name divName, addr.Id addrId, br.Id brId, br.Name brName, l.Id lId, l.Code lCode, l.Name lName, l.IsDefault lIsDefault, l.IsRightToLeft lIsRightToLeft, l.UsesLatinAlphabet lUsesLatinAlphabet FROM AssetBankUser u  LEFT JOIN UserInGroup uig ON uig.UserId = u.Id LEFT JOIN UserGroup ug ON uig.UserGroupId = ug.Id LEFT JOIN OrgUnitGroup oug ON oug.UserGroupId = ug.Id LEFT JOIN OrgUnit groupou ON oug.OrgUnitId = groupou.Id LEFT JOIN CM_Category cat ON groupou.RootOrgUnitCategoryId = cat.Id LEFT JOIN Brand br ON ug.BrandId = br.Id LEFT JOIN OrgUnit ou ON ou.AdminGroupId = ug.Id LEFT JOIN CM_Category oucat ON ou.RootOrgUnitCategoryId = oucat.Id LEFT JOIN Division divis ON divis.Id = u.DivisionId LEFT JOIN Address addr ON addr.Id = u.AddressId LEFT JOIN Language l ON l.Id = u.LanguageId ";
/* 7059 */       sSql = sSql + " WHERE u.IsDeleted=? " + "AND u.ReactivationPending=? " + "AND u.NotActiveDirectory=? " + "AND u.Hidden=? " + "AND u.IsAdmin=? " + "AND u.DateLastLoggedIn<?";
/*      */ 
/* 7067 */       psql = cCon.prepareStatement(sSql);
/*      */ 
/* 7069 */       iField = 1;
/* 7070 */       psql.setBoolean(iField++, false);
/* 7071 */       psql.setBoolean(iField++, true);
/* 7072 */       psql.setBoolean(iField++, true);
/* 7073 */       psql.setBoolean(iField++, false);
/* 7074 */       psql.setBoolean(iField++, false);
/* 7075 */       DBUtil.setFieldDateOrNull(psql, iField++, deleteFrom.getTime());
/*      */ 
/* 7077 */       rs = psql.executeQuery();
/* 7078 */       vecUsersThatNeedDeleting = buildUsers(transaction, rs);
/*      */ 
/* 7080 */       psql.close();
/*      */ 
/* 7083 */       if (vecUsersThatNeedDeleting != null)
/*      */       {
/* 7085 */         Iterator itUsersToDelete = vecUsersThatNeedDeleting.iterator();
/*      */ 
/* 7087 */         while (itUsersToDelete.hasNext())
/*      */         {
/* 7089 */           ABUser user = (ABUser)itUsersToDelete.next();
/* 7090 */           deleteUser(transaction, user.getId());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 7098 */       this.m_logger.error("SQL Exception whilst checking for users to expire/delete : " + e);
/* 7099 */       throw new Bn2Exception("SQL Exception whilst checking for users to expire/delete : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 7104 */       if (transaction != null)
/*      */       {
/*      */         try
/*      */         {
/* 7108 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 7112 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public AssetBoxManager getAssetBoxManager()
/*      */   {
/* 7123 */     return this.m_assetBoxManager;
/*      */   }
/*      */ 
/*      */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*      */   {
/* 7129 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.service.ABUserManager
 * JD-Core Version:    0.6.0
 */