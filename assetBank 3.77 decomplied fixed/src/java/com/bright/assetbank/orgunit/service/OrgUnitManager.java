/*      */ package com.bright.assetbank.orgunit.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.language.service.LanguageManager;
/*      */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*      */ import com.bright.assetbank.orgunit.bean.OrgUnitContent;
/*      */ import com.bright.assetbank.orgunit.bean.OrgUnitMetadata;
/*      */ import com.bright.assetbank.orgunit.bean.OrgUnitSearchCriteria;
/*      */ import com.bright.assetbank.orgunit.constant.OrgUnitConstants;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.assetbank.user.bean.Group;
/*      */ import com.bright.assetbank.user.bean.GroupCategoryPermission;
/*      */ import com.bright.assetbank.user.bean.UserSearchCriteria;
/*      */ import com.bright.assetbank.user.service.ABUserManager;
/*      */ import com.bright.framework.category.bean.Category;
/*      */ import com.bright.framework.category.bean.FlatCategoryList;
/*      */ import com.bright.framework.category.constant.CategoryConstants;
/*      */ import com.bright.framework.category.util.CategoryUtil;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.ApplicationSql;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.constant.LanguageConstants;
/*      */ import com.bright.framework.search.bean.SearchResults;
/*      */ import com.bright.framework.simplelist.bean.ListItem;
/*      */ import com.bright.framework.simplelist.service.ListManager;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class OrgUnitManager extends Bn2Manager
/*      */   implements OrgUnitConstants, CategoryConstants
/*      */ {
/*      */   private static final long k_lOneMbInBytes = 1048576L;
/*      */   private static final String c_ksClassName = "OrgUnitManager";
/*   91 */   private AssetCategoryManager m_categoryManager = null;
/*      */ 
/*   97 */   private ABUserManager m_userManager = null;
/*      */ 
/*  103 */   private ListManager m_listManager = null;
/*      */ 
/*  109 */   private LanguageManager m_languageManager = null;
/*      */ 
/*  115 */   private DBTransactionManager m_transactionManager = null;
/*      */ 
/*      */   public void setCategoryManager(AssetCategoryManager a_categoryManager)
/*      */   {
/*   94 */     this.m_categoryManager = a_categoryManager;
/*      */   }
/*      */ 
/*      */   public void setUserManager(ABUserManager a_userManager)
/*      */   {
/*  100 */     this.m_userManager = a_userManager;
/*      */   }
/*      */ 
/*      */   public void setListManager(ListManager a_listManager)
/*      */   {
/*  106 */     this.m_listManager = a_listManager;
/*      */   }
/*      */ 
/*      */   public void setLanguageManager(LanguageManager a_languageManager)
/*      */   {
/*  112 */     this.m_languageManager = a_languageManager;
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_transactionManager)
/*      */   {
/*  118 */     this.m_transactionManager = a_transactionManager;
/*      */   }
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*  131 */     super.startup();
/*      */ 
/*  134 */     DBTransaction transaction = null;
/*      */     try
/*      */     {
/*  137 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*  138 */       Vector<OrgUnit> orgs = getOrgUnitListWithContent(transaction);
/*  139 */       if (orgs != null)
/*      */       {
/*  141 */         for (OrgUnit org : orgs)
/*      */         {
/*  143 */           initialiseExtraContentItems(transaction, org);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  149 */       if (transaction != null)
/*      */         try {
/*  151 */           transaction.rollback(); } catch (SQLException ex) {
/*      */         }
/*  153 */       this.m_logger.error(getClass().getSimpleName() + ".startup: Error ", e);
/*  154 */       throw new Bn2Exception(getClass().getSimpleName() + ".startup: Error ", e);
/*      */     }
/*      */     finally
/*      */     {
/*  158 */       if (transaction != null)
/*      */         try {
/*  160 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException ex)
/*      */         {
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector<OrgUnit> getOrgUnitList(DBTransaction a_dbTransaction, OrgUnitSearchCriteria a_search, boolean a_bGetContent)
/*      */     throws Bn2Exception
/*      */   {
/*  187 */     String ksMethodName = "getOrgUnitList";
/*  188 */     Connection con = null;
/*  189 */     String sSQL = null;
/*  190 */     PreparedStatement psql = null;
/*  191 */     Vector vecList = new Vector();
/*      */     try
/*      */     {
/*  195 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  197 */       sSQL = "SELECT ou.Id ouId, ou.DiskQuotaMb ouDiskQuotaMb, ug.Id ugId, ug.Name ugName, ug.Description ugDescription, IsDefaultGroup, cat.Id catId, cat.Name catName FROM OrgUnit ou INNER JOIN CM_Category cat ON ou.RootOrgUnitCategoryId = cat.Id INNER JOIN UserGroup ug ON ou.AdminGroupId = ug.Id ";
/*      */ 
/*  204 */       if (a_search.getAdminUserId() > 0L)
/*      */       {
/*  206 */         sSQL = sSQL + "LEFT JOIN UserInGroup uig ON uig.UserGroupId=ug.Id ";
/*      */       }
/*      */ 
/*  209 */       sSQL = sSQL + "WHERE 1=1 ";
/*      */ 
/*  211 */       if (a_search.getAdminUserId() > 0L)
/*      */       {
/*  214 */         sSQL = sSQL + "AND uig.UserId=" + a_search.getAdminUserId() + " ";
/*      */       }
/*      */ 
/*  217 */       sSQL = sSQL + "ORDER BY cat.Name";
/*      */ 
/*  219 */       psql = con.prepareStatement(sSQL);
/*  220 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  222 */       while (rs.next())
/*      */       {
/*  224 */         OrgUnit ou = new OrgUnit();
/*  225 */         ou.setId(rs.getLong("ouId"));
/*  226 */         ou.setDiskQuotaMb(rs.getInt("ouDiskQuotaMb"));
/*  227 */         ou.getAdminGroup().setId(rs.getLong("ugId"));
/*  228 */         ou.getAdminGroup().setName(rs.getString("ugName"));
/*  229 */         ou.getAdminGroup().setDescription(rs.getString("ugDescription"));
/*  230 */         ou.getAdminGroup().setIsDefaultGroup(rs.getBoolean("IsDefaultGroup"));
/*  231 */         ou.getCategory().setId(rs.getLong("catId"));
/*  232 */         ou.getCategory().setName(rs.getString("catName"));
/*  233 */         ou.setName(rs.getString("catName"));
/*  234 */         ou.setDiskUsageBytes(this.m_categoryManager.getDiskUsageForCategoryAssets(a_dbTransaction, ou.getCategory().getId(), 2L));
/*      */ 
/*  236 */         if (a_bGetContent)
/*      */         {
/*  238 */           populateContent(a_dbTransaction, ou);
/*      */         }
/*      */ 
/*  241 */         vecList.add(ou);
/*      */       }
/*      */ 
/*  245 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  251 */       throw new Bn2Exception("OrgUnitManager.getOrgUnitList: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/*  254 */     return vecList;
/*      */   }
/*      */ 
/*      */   public Vector<OrgUnit> getOrgUnitListForAdminUser(DBTransaction a_dbTransaction, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/*  273 */     OrgUnitSearchCriteria search = new OrgUnitSearchCriteria();
/*  274 */     search.setAdminUserId(a_lUserId);
/*      */ 
/*  276 */     return getOrgUnitList(a_dbTransaction, search, false);
/*      */   }
/*      */ 
/*      */   public Vector<OrgUnit> getOrgUnitList(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  295 */     OrgUnitSearchCriteria search = new OrgUnitSearchCriteria();
/*  296 */     return getOrgUnitList(a_dbTransaction, search, false);
/*      */   }
/*      */ 
/*      */   public Vector<OrgUnit> getOrgUnitListWithContent(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  311 */     OrgUnitSearchCriteria search = new OrgUnitSearchCriteria();
/*  312 */     return getOrgUnitList(a_dbTransaction, search, true);
/*      */   }
/*      */ 
/*      */   public OrgUnit getOrgUnit(DBTransaction a_dbTransaction, long a_lOrgUnitId)
/*      */     throws Bn2Exception
/*      */   {
/*  333 */     return getOrgUnit(a_dbTransaction, a_lOrgUnitId, 0L);
/*      */   }
/*      */ 
/*      */   private OrgUnit getOrgUnit(DBTransaction a_dbTransaction, long a_lOrgUnitId, long a_lRootCategoryId)
/*      */     throws Bn2Exception
/*      */   {
/*  352 */     String ksMethodName = "getOrgUnit";
/*  353 */     Connection con = null;
/*  354 */     String sSQL = null;
/*  355 */     PreparedStatement psql = null;
/*  356 */     OrgUnit ou = new OrgUnit();
/*      */     try
/*      */     {
/*  360 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  362 */       long lId = 0L;
/*  363 */       sSQL = "SELECT ou.Id ouId, ou.AdminGroupId adminugId, ou.StandardGroupId standardugId, ou.RootOrgUnitCategoryId catId, ou.RootDescriptiveCategoryId descCatId, ou.DiskQuotaMb ouDiskQuotaMb FROM OrgUnit ou ";
/*      */ 
/*  373 */       if (a_lOrgUnitId > 0L)
/*      */       {
/*  375 */         sSQL = sSQL + "WHERE ou.Id = ? ";
/*  376 */         lId = a_lOrgUnitId;
/*      */       }
/*      */       else
/*      */       {
/*  380 */         sSQL = sSQL + "WHERE RootOrgUnitCategoryId = ? ";
/*  381 */         lId = a_lRootCategoryId;
/*      */       }
/*      */ 
/*  384 */       psql = con.prepareStatement(sSQL);
/*  385 */       psql.setLong(1, lId);
/*  386 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  388 */       while (rs.next())
/*      */       {
/*  390 */         ou.setId(rs.getLong("ouId"));
/*  391 */         ou.setDiskQuotaMb(rs.getInt("ouDiskQuotaMb"));
/*  392 */         long lCatId = rs.getLong("catId");
/*  393 */         Category cat = this.m_categoryManager.getCategory(a_dbTransaction, 2L, lCatId);
/*  394 */         ou.setCategory(cat);
/*      */ 
/*  396 */         lCatId = rs.getLong("descCatId");
/*  397 */         if (lCatId > 0L)
/*      */         {
/*  399 */           cat = this.m_categoryManager.getCategory(a_dbTransaction, 1L, lCatId);
/*  400 */           ou.setRootDescriptiveCategory(cat);
/*      */         }
/*      */ 
/*  403 */         long lGroupId = rs.getLong("adminugId");
/*  404 */         Group group = this.m_userManager.getGroup(lGroupId);
/*  405 */         ou.setAdminGroup(group);
/*      */ 
/*  407 */         long lStandardGroupId = rs.getLong("standardugId");
/*  408 */         Group standard = this.m_userManager.getGroup(lStandardGroupId);
/*  409 */         ou.setUserGroup(standard);
/*      */ 
/*  411 */         populateContent(a_dbTransaction, ou);
/*      */       }
/*      */ 
/*  414 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  420 */       throw new Bn2Exception("OrgUnitManager.getOrgUnit: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/*  423 */     return ou;
/*      */   }
/*      */ 
/*      */   public void populateContent(DBTransaction a_dbTransaction, OrgUnitMetadata a_orgUnit)
/*      */     throws Bn2Exception
/*      */   {
/*  437 */     DBTransaction transaction = a_dbTransaction;
/*      */     try
/*      */     {
/*  441 */       if (transaction == null)
/*      */       {
/*  443 */         transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */       }
/*  445 */       Connection con = transaction.getConnection();
/*      */ 
/*  448 */       String sSQL = "SELECT * FROM OrgUnitContent WHERE OrgUnitId=?";
/*  449 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  450 */       psql.setLong(1, a_orgUnit.getId());
/*  451 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  453 */       while (rs.next())
/*      */       {
/*  455 */         OrgUnitContent content = buildOrgUnitContent(rs);
/*  456 */         a_orgUnit.addOrgUnitContent(content);
/*      */       }
/*  458 */       psql.close();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  462 */       if ((transaction != null) && (a_dbTransaction == null))
/*      */         try {
/*  464 */           transaction.rollback(); } catch (SQLException ex) {
/*      */         }
/*  466 */       String sMessage = getClass().getSimpleName() + ".populateContent: Error : ";
/*  467 */       this.m_logger.error(sMessage, e);
/*  468 */       throw new Bn2Exception(sMessage, e);
/*      */     }
/*      */     finally
/*      */     {
/*  472 */       if ((transaction != null) && (a_dbTransaction == null))
/*      */         try {
/*  474 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException ex)
/*      */         {
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean newOrgUnit(DBTransaction a_dbTransaction, OrgUnit a_ou)
/*      */     throws Bn2Exception
/*      */   {
/*  501 */     Category cat = a_ou.getCategory();
/*  502 */     cat.setCannotBeDeleted(true);
/*  503 */     cat.setIsRestrictive(true);
/*  504 */     long lParentId = -1L;
/*  505 */     if (AssetBankSettings.getOrgUnitParentALId() > 0L)
/*      */     {
/*  507 */       lParentId = AssetBankSettings.getOrgUnitParentALId();
/*      */     }
/*      */ 
/*  511 */     if (AssetBankSettings.getSupportMultiLanguage())
/*      */     {
/*  513 */       this.m_languageManager.createEmptyTranslations(a_dbTransaction, cat);
/*      */     }
/*      */ 
/*  516 */     boolean bSuccess = this.m_categoryManager.newPermissionCategory(a_dbTransaction, 2L, cat, lParentId);
/*      */ 
/*  518 */     if (bSuccess)
/*      */     {
/*  523 */       Vector vecUserPermissions = new Vector();
/*  524 */       Vector vecAdminPermissions = new Vector();
/*      */ 
/*  527 */       GroupCategoryPermission gcpUser = new GroupCategoryPermission();
/*  528 */       gcpUser.getCategory().setId(cat.getId());
/*  529 */       gcpUser.setParentCategoryId(cat.getParentId());
/*  530 */       gcpUser.setDownloadPermissionLevel(2);
/*  531 */       gcpUser.setUploadPermissionLevel(0);
/*  532 */       vecUserPermissions.add(gcpUser);
/*      */ 
/*  534 */       GroupCategoryPermission gcpAdmin = new GroupCategoryPermission();
/*  535 */       gcpAdmin.getCategory().setId(cat.getId());
/*  536 */       gcpAdmin.setParentCategoryId(cat.getParentId());
/*  537 */       gcpAdmin.setDownloadPermissionLevel(7);
/*  538 */       gcpAdmin.setUploadPermissionLevel(12);
/*  539 */       vecAdminPermissions.add(gcpAdmin);
/*      */ 
/*  543 */       Group adminGroup = a_ou.getAdminGroup();
/*  544 */       adminGroup.setName(AssetBankSettings.getOrgUnitAdminGroupName());
/*  545 */       adminGroup.setCategoryPermissions(vecAdminPermissions);
/*  546 */       this.m_userManager.saveGroup(a_dbTransaction, adminGroup, vecAdminPermissions, 2L);
/*      */ 
/*  549 */       Group userGroup = a_ou.getUserGroup();
/*  550 */       userGroup.setName(AssetBankSettings.getOrgUnitStandardGroupName());
/*  551 */       userGroup.setCategoryPermissions(vecUserPermissions);
/*  552 */       this.m_userManager.saveGroup(a_dbTransaction, userGroup, vecUserPermissions, 2L);
/*      */ 
/*  555 */       addOrgUnit(a_dbTransaction, a_ou);
/*  556 */       initialiseExtraContentItems(a_dbTransaction, a_ou);
/*      */     }
/*      */ 
/*  559 */     return bSuccess;
/*      */   }
/*      */ 
/*      */   public void setDefaultPermissions(DBTransaction a_dbTransaction, long a_lCategoryId, long a_lParentCatId)
/*      */     throws Bn2Exception
/*      */   {
/*  585 */     OrgUnit ou = getOrgUnitForCategory(a_dbTransaction, a_lCategoryId);
/*      */ 
/*  588 */     Vector vecGroupList = this.m_userManager.getAllGroups();
/*      */ 
/*  590 */     Iterator itGroups = vecGroupList.iterator();
/*  591 */     while (itGroups.hasNext())
/*      */     {
/*  594 */       Group bareGroup = (Group)itGroups.next();
/*  595 */       long lGroupId = bareGroup.getId();
/*  596 */       Group fullGroup = this.m_userManager.getGroup(lGroupId);
/*  597 */       Vector vecPermissions = new Vector();
/*      */ 
/*  600 */       if (fullGroup.getCategoryPermissions() != null)
/*      */       {
/*  602 */         for (int i = 0; i < fullGroup.getCategoryPermissions().size(); i++)
/*      */         {
/*  604 */           GroupCategoryPermission temp = (GroupCategoryPermission)fullGroup.getCategoryPermissions().elementAt(i);
/*  605 */           if (temp.getCategory().getCategoryTypeId() != 2L)
/*      */             continue;
/*  607 */           vecPermissions.add(temp);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  613 */       int iNewDownloadPermissionLevel = 0;
/*  614 */       int iNewUploadPermissionLevel = 0;
/*      */ 
/*  617 */       if (a_lParentCatId == -1L)
/*      */       {
/*  619 */         if ((ou.getId() > 0L) && (fullGroup.getId() == ou.getAdminGroup().getId()))
/*      */         {
/*  622 */           iNewDownloadPermissionLevel = 7;
/*  623 */           iNewUploadPermissionLevel = 12;
/*      */         }
/*  625 */         else if ((ou.getId() > 0L) && (fullGroup.getOrgUnitReference().getId() == ou.getId()))
/*      */         {
/*  628 */           iNewDownloadPermissionLevel = 2;
/*  629 */           iNewUploadPermissionLevel = 0;
/*      */         }
/*      */         else
/*      */         {
/*  634 */           iNewDownloadPermissionLevel = 0;
/*  635 */           iNewUploadPermissionLevel = 0;
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  641 */         Iterator itPermissions = vecPermissions.iterator();
/*  642 */         while (itPermissions.hasNext())
/*      */         {
/*  644 */           GroupCategoryPermission perm = (GroupCategoryPermission)itPermissions.next();
/*  645 */           if (perm.getCategory().getId() == a_lParentCatId)
/*      */           {
/*  647 */             iNewDownloadPermissionLevel = perm.getDownloadPermissionLevel();
/*  648 */             iNewUploadPermissionLevel = perm.getUploadPermissionLevel();
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  654 */       GroupCategoryPermission gcp = new GroupCategoryPermission();
/*  655 */       gcp.getCategory().setId(a_lCategoryId);
/*  656 */       gcp.setParentCategoryId(a_lParentCatId);
/*  657 */       gcp.setDownloadPermissionLevel(iNewDownloadPermissionLevel);
/*  658 */       gcp.setUploadPermissionLevel(iNewUploadPermissionLevel);
/*  659 */       vecPermissions.add(gcp);
/*      */ 
/*  662 */       this.m_userManager.saveGroup(a_dbTransaction, fullGroup, vecPermissions, 2L);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean updateOrgUnit(DBTransaction a_dbTransaction, OrgUnit a_ou, boolean a_bUpdateDiskQuota)
/*      */     throws Bn2Exception
/*      */   {
/*  678 */     String ksMethodName = "updateOrgUnit";
/*      */ 
/*  681 */     long lCategoryId = a_ou.getCategory().getId();
/*  682 */     String sName = a_ou.getCategory().getName();
/*  683 */     Category cat = this.m_categoryManager.getCategory(a_dbTransaction, 2L, lCategoryId);
/*  684 */     cat = cat.clone();
/*  685 */     cat.setName(sName);
/*  686 */     boolean bSuccess = this.m_categoryManager.updateCategoryDetails(a_dbTransaction, 2L, cat);
/*      */ 
/*  689 */     if (bSuccess)
/*      */     {
/*  691 */       Connection con = null;
/*  692 */       String sSQL = null;
/*  693 */       PreparedStatement psql = null;
/*      */       try
/*      */       {
/*  697 */         con = a_dbTransaction.getConnection();
/*      */ 
/*  699 */         sSQL = "UPDATE OrgUnit Set RootDescriptiveCategoryId=? ";
/*      */ 
/*  701 */         if (a_bUpdateDiskQuota)
/*      */         {
/*  703 */           sSQL = sSQL + ", DiskQuotaMb=? ";
/*      */         }
/*      */ 
/*  706 */         sSQL = sSQL + "WHERE Id=?";
/*      */ 
/*  708 */         psql = con.prepareStatement(sSQL);
/*  709 */         int iCol = 1;
/*      */ 
/*  711 */         DBUtil.setFieldIdOrNull(psql, iCol++, a_ou.getRootDescriptiveCategory());
/*      */ 
/*  713 */         if (a_bUpdateDiskQuota)
/*      */         {
/*  715 */           psql.setInt(iCol++, a_ou.getDiskQuotaMb());
/*      */         }
/*      */ 
/*  718 */         psql.setLong(iCol++, a_ou.getId());
/*  719 */         psql.executeUpdate();
/*  720 */         psql.close();
/*      */ 
/*  722 */         initialiseExtraContentItems(a_dbTransaction, getOrgUnit(a_dbTransaction, a_ou.getId()));
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/*  727 */         throw new Bn2Exception("OrgUnitManager.updateOrgUnit: Exception occurred: " + e, e);
/*      */       }
/*      */     }
/*      */ 
/*  731 */     return bSuccess;
/*      */   }
/*      */ 
/*      */   public void deleteOrgUnit(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  754 */     String ksMethodName = "deleteOrgUnit";
/*  755 */     Connection con = null;
/*  756 */     String sSQL = null;
/*  757 */     PreparedStatement psql = null;
        OrgUnitContent content;
/*      */     try
/*      */     {
/*  762 */       OrgUnit ou = getOrgUnit(a_dbTransaction, a_lId);
/*      */ 
/*  764 */       con = a_dbTransaction.getConnection();
/*      */       Iterator i$;
/*  767 */       if ((ou.getOrgUnitContent() != null) && (ou.getOrgUnitContent().size() > 0))
/*      */       {
/*  769 */         for (i$ = ou.getOrgUnitContent().iterator(); i$.hasNext(); ) { content = (OrgUnitContent)i$.next();
/*      */ 
/*  772 */           List<Language> langs = this.m_languageManager.getLanguages(a_dbTransaction);
/*  773 */           for (Language lang : langs)
/*      */           {
/*  775 */             if (StringUtil.stringIsPopulated(content.getContentListItemIdentifier()))
/*      */             {
/*  777 */               this.m_listManager.deleteListItem(a_dbTransaction, content.getContentListItemIdentifier(), lang.getId());
/*      */             }
/*      */ 
/*  780 */             if (StringUtil.stringIsPopulated(content.getMenuListItemIdentifier()))
/*      */             {
/*  782 */               this.m_listManager.deleteListItem(a_dbTransaction, content.getMenuListItemIdentifier(), lang.getId());
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       //OrgUnitContent content;
/*  789 */       sSQL = "UPDATE AssetBankUser SET RequestedOrgUnitId=? WHERE RequestedOrgUnitId=?";
/*  790 */       psql = con.prepareStatement(sSQL);
/*  791 */       psql.setNull(1, -5);
/*  792 */       psql.setLong(2, a_lId);
/*  793 */       psql.executeUpdate();
/*  794 */       psql.close();
/*      */ 
/*  797 */       String[] aSQL = { "DELETE FROM OrgUnitContent WHERE OrgUnitId=?", "DELETE FROM OrgUnitGroup WHERE OrgUnitId=?", "DELETE FROM OrgUnitForAgreement WHERE OrgUnitId=?", "DELETE FROM OrgUnit WHERE Id=?" };
/*      */ 
/*  802 */       for (String sTempSQL : aSQL)
/*      */       {
/*  804 */         psql = con.prepareStatement(sTempSQL);
/*  805 */         psql.setLong(1, a_lId);
/*  806 */         psql.executeUpdate();
/*  807 */         psql.close();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  813 */       throw new Bn2Exception("OrgUnitManager.deleteOrgUnit: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getOrgUnitGroups(DBTransaction a_dbTransaction, long a_lOrgUnitId)
/*      */     throws Bn2Exception
/*      */   {
/*  831 */     SearchResults results = searchOrgUnitGroups(a_dbTransaction, a_lOrgUnitId, null, -1, -1);
/*  832 */     return results.getSearchResults();
/*      */   }
/*      */   public SearchResults searchOrgUnitGroups(DBTransaction a_dbTransaction, long a_lOrgUnitId, String a_sNameFilter, int a_iStartIndex, int a_iMaxResults) throws Bn2Exception {
/*  857 */     String ksMethodName = "getOrgUnitGroups";
/*  858 */     Connection con = null;
/*  859 */     String sSQL = null;
/*  860 */     PreparedStatement psql = null;
/*      */     SearchResults results;
/*      */     try {
/*  865 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  867 */       sSQL = "SELECT ug.Id, ug.Name, ug.IsDefaultGroup, oug.OrgUnitId FROM UserGroup ug, OrgUnitGroup oug WHERE oug.UserGroupId = ug.Id ";
/*      */ 
/*  878 */       if (a_lOrgUnitId > 0L)
/*      */       {
/*  880 */         sSQL = sSQL + "AND oug.OrgUnitId = ? ";
/*      */       }
/*      */ 
/*  883 */       if (StringUtils.isNotEmpty(a_sNameFilter))
/*      */       {
/*  885 */         a_sNameFilter = a_sNameFilter.replaceAll("\\*", "%");
/*  886 */         a_sNameFilter = a_sNameFilter.replaceAll("'", "''");
/*  887 */         sSQL = sSQL + " AND " + SQLGenerator.getInstance().getLowerCaseFunction("ug.Name") + " LIKE '%" + a_sNameFilter.toLowerCase() + "%' ";
/*      */       }
/*      */ 
/*  890 */       sSQL = sSQL + "ORDER BY ug.Id, ug.Name, ug.IsDefaultGroup";
/*      */ 
/*  892 */       psql = con.prepareStatement(sSQL);
/*      */ 
/*  894 */       if (a_lOrgUnitId > 0L)
/*      */       {
/*  896 */         psql.setLong(1, a_lOrgUnitId);
/*      */       }
/*      */ 
/*  899 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  901 */       results = new SearchResults();
/*      */ 
/*  903 */       int iNumResults = 0;
/*  904 */       int iTotalHits = 0;
/*  905 */       int iIndex = 0;
/*      */ 
/*  907 */       while (rs.next())
/*      */       {
/*  909 */         iTotalHits++;
/*      */ 
/*  912 */         if (((a_iStartIndex >= 0) && (iIndex++ < a_iStartIndex)) || ((a_iMaxResults > 0) && (iNumResults >= a_iMaxResults)))
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/*  917 */         Group group = new Group();
/*      */ 
/*  919 */         group.setId(rs.getLong("Id"));
/*  920 */         group.setName(rs.getString("Name"));
/*  921 */         group.setIsDefaultGroup(rs.getBoolean("IsDefaultGroup"));
/*      */ 
/*  923 */         group.getOrgUnitReference().setId(rs.getLong("OrgUnitId"));
/*      */ 
/*  925 */         results.getSearchResults().add(group);
/*      */       }
/*      */ 
/*  928 */       psql.close();
/*      */ 
/*  930 */       results.setNumResults(iTotalHits);
/*  931 */       results.setPageSize(a_iMaxResults);
/*  932 */       results.setPageIndex((int)Math.floor(a_iStartIndex / a_iMaxResults));
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  937 */       throw new Bn2Exception("OrgUnitManager.getOrgUnitGroups: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/*  940 */     return results;
/*      */   }
/*      */ 
/*      */   public Vector getOrgUnitAdminUserManagedGroupsAsIds(DBTransaction a_dbTransaction, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/*  960 */     Vector vecIds = new Vector();
/*      */ 
/*  962 */     Vector vecGroups = getOrgUnitAdminUserManagedGroups(a_dbTransaction, a_lUserId);
/*  963 */     Iterator it = vecGroups.iterator();
/*  964 */     while (it.hasNext())
/*      */     {
/*  966 */       Group group = (Group)it.next();
/*  967 */       Long olId = new Long(group.getId());
/*  968 */       vecIds.add(olId);
/*      */     }
/*      */ 
/*  971 */     return vecIds;
/*      */   }
/*      */ 
/*      */   public Vector getOrgUnitAdminUserManagedGroups(DBTransaction a_dbTransaction, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/*  985 */     SearchResults results = searchOrgUnitAdminUserManagedGroups(a_dbTransaction, a_lUserId, null, -1, -1);
/*  986 */     return results.getSearchResults();
/*      */   }
/*      */ 
/*      */   public SearchResults searchOrgUnitAdminUserManagedGroups(DBTransaction a_dbTransaction, long a_lUserId, String a_sNameFilter, int a_iStartIndex, int a_iMaxResults)
/*      */     throws Bn2Exception
/*      */   {
/* 1010 */     String ksMethodName = "getOrgUnitUserAdminGroups";
/* 1011 */     Connection con = null;
/* 1012 */     String sSQL = null;
/* 1013 */     PreparedStatement psql = null;
/* 1014 */     SearchResults results = new SearchResults();
/*      */     try
/*      */     {
/* 1018 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1022 */       sSQL = "SELECT ug.Id, ug.Name, ug.IsDefaultGroup, ou.Id AS ouId, ou.AdminGroupId, ou.StandardGroupId, ou.DiskQuotaMb ouDiskQuotaMb, cat.Name AS ouName FROM UserInGroup uiadming, OrgUnit ou, OrgUnitGroup oug, UserGroup ug, CM_Category cat WHERE oug.UserGroupId = ug.Id AND ou.id = oug.OrgUnitId AND ou.RootOrgUnitCategoryId = cat.Id AND uiadming.UserGroupId = ou.AdminGroupId AND uiadming.UserId = ? ";
/*      */ 
/* 1044 */       if (StringUtils.isNotEmpty(a_sNameFilter))
/*      */       {
/* 1046 */         a_sNameFilter = a_sNameFilter.replaceAll("\\*", "%");
/* 1047 */         a_sNameFilter = a_sNameFilter.replaceAll("'", "''");
/* 1048 */         sSQL = sSQL + " AND (" + SQLGenerator.getInstance().getLowerCaseFunction("ug.Name") + " LIKE '%" + a_sNameFilter.toLowerCase() + "%' ";
/* 1049 */         sSQL = sSQL + " OR " + SQLGenerator.getInstance().getLowerCaseFunction("cat.Name") + " LIKE '%" + a_sNameFilter.toLowerCase() + "%') ";
/*      */       }
/*      */ 
/* 1053 */       sSQL = sSQL + "ORDER BY cat.Name, ug.Id, ug.Name, ug.IsDefaultGroup";
/*      */ 
/* 1055 */       psql = con.prepareStatement(sSQL);
/* 1056 */       psql.setLong(1, a_lUserId);
/* 1057 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1059 */       int iNumResults = 0;
/* 1060 */       int iTotalHits = 0;
/* 1061 */       int iIndex = 0;
/*      */ 
/* 1063 */       while (rs.next())
/*      */       {
/* 1065 */         iTotalHits++;
/*      */ 
/* 1068 */         if (((a_iStartIndex >= 0) && (iIndex++ < a_iStartIndex)) || ((a_iMaxResults > 0) && (iNumResults >= a_iMaxResults)))
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/* 1073 */         Group group = new Group();
/*      */ 
/* 1075 */         group.setId(rs.getLong("Id"));
/* 1076 */         group.setName(rs.getString("Name"));
/* 1077 */         group.setIsDefaultGroup(rs.getBoolean("IsDefaultGroup"));
/*      */ 
/* 1079 */         OrgUnitMetadata org = this.m_userManager.buildOrgUnitMetadata(a_dbTransaction, rs, false);
/* 1080 */         group.setOrgUnitReference(org);
/*      */ 
/* 1082 */         if ((rs.getLong("AdminGroupId") == group.getId()) || (rs.getLong("StandardGroupId") == group.getId()))
/*      */         {
/* 1085 */           group.setCanDelete(false);
/*      */         }
/*      */ 
/* 1088 */         if (rs.getLong("AdminGroupId") == group.getId())
/*      */         {
/* 1090 */           group.setOrgUnitAdminGroup(true);
/*      */         }
/*      */ 
/* 1093 */         results.getSearchResults().add(group);
/*      */       }
/*      */ 
/* 1096 */       psql.close();
/*      */ 
/* 1098 */       results.setNumResults(iTotalHits);
/* 1099 */       results.setPageSize(a_iMaxResults);
/* 1100 */       results.setPageIndex((int)Math.floor(a_iStartIndex / a_iMaxResults));
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1106 */       throw new Bn2Exception("OrgUnitManager.getOrgUnitUserAdminGroups: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/* 1109 */     return results;
/*      */   }
/*      */ 
/*      */   public OrgUnit getOrgUnitForGroup(DBTransaction a_dbTransaction, long a_lGroupId)
/*      */     throws Bn2Exception
/*      */   {
/* 1131 */     String ksMethodName = "getOrgUnitForGroup";
/* 1132 */     Connection con = null;
/* 1133 */     String sSQL = null;
/* 1134 */     PreparedStatement psql = null;
/* 1135 */     OrgUnit ou = new OrgUnit();
/*      */     try
/*      */     {
/* 1139 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1142 */       sSQL = "SELECT oug.OrgUnitId FROM OrgUnitGroup oug WHERE oug.UserGroupId = ? ";
/*      */ 
/* 1146 */       psql = con.prepareStatement(sSQL);
/* 1147 */       psql.setLong(1, a_lGroupId);
/* 1148 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1150 */       if (rs.next())
/*      */       {
/* 1152 */         long lOrgUnitId = rs.getLong("OrgUnitId");
/* 1153 */         ou = getOrgUnit(a_dbTransaction, lOrgUnitId);
/*      */       }
/*      */ 
/* 1156 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1162 */       throw new Bn2Exception("OrgUnitManager.getOrgUnitForGroup: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/* 1165 */     return ou;
/*      */   }
/*      */ 
/*      */   public FlatCategoryList getOrgUnitCategories(DBTransaction a_dbTransaction, long a_lOrgUnitId)
/*      */     throws Bn2Exception
/*      */   {
/* 1186 */     OrgUnit ou = getOrgUnit(a_dbTransaction, a_lOrgUnitId);
/*      */ 
/* 1189 */     Vector vecCatIds = new Vector();
/* 1190 */     addCategoriesToVector(a_dbTransaction, ou.getCategory(), vecCatIds);
/* 1191 */     Set categoryIds = new HashSet(vecCatIds);
/*      */ 
/* 1195 */     FlatCategoryList allCategories = this.m_categoryManager.getFlatCategoryList(a_dbTransaction, 2L);
/* 1196 */     FlatCategoryList ouCategories = CategoryUtil.filterFlatCategoryListByCategoryIds(categoryIds, allCategories);
/*      */ 
/* 1198 */     return ouCategories;
/*      */   }
/*      */ 
/*      */   public FlatCategoryList getOrgUnitAdminUserCategories(DBTransaction a_dbTransaction, long a_lUserId, int a_iMaxSizeElseTopLevelOnly)
/*      */     throws Bn2Exception
/*      */   {
/* 1219 */     Vector vecOrgUnits = getOrgUnitListForAdminUser(a_dbTransaction, a_lUserId);
/*      */ 
/* 1222 */     Vector vecCatIds = new Vector();
/*      */ 
/* 1224 */     Iterator it = vecOrgUnits.iterator();
/* 1225 */     while (it.hasNext())
/*      */     {
/* 1227 */       OrgUnit ou = (OrgUnit)it.next();
/* 1228 */       addCategoriesToVector(a_dbTransaction, ou.getCategory(), vecCatIds);
/*      */     }
/*      */     FlatCategoryList userCategories;
/*      */     //FlatCategoryList userCategories;
/* 1234 */     if (vecCatIds.size() <= a_iMaxSizeElseTopLevelOnly)
/*      */     {
/* 1237 */       Set categoryIds = new HashSet(vecCatIds);
/*      */ 
/* 1241 */       FlatCategoryList allCategories = this.m_categoryManager.getFlatCategoryList(a_dbTransaction, 2L);
/* 1242 */       userCategories = CategoryUtil.filterFlatCategoryListByCategoryIds(categoryIds, allCategories);
/*      */     }
/*      */     else
/*      */     {
/* 1247 */       vecCatIds.clear();
/* 1248 */       it = vecOrgUnits.iterator();
/* 1249 */       while (it.hasNext())
/*      */       {
/* 1251 */         OrgUnit ou = (OrgUnit)it.next();
/* 1252 */         vecCatIds.add(Long.valueOf(ou.getCategory().getId()));
/*      */       }
/* 1254 */       userCategories = new FlatCategoryList();
/* 1255 */       userCategories.setCategories(this.m_categoryManager.getCategories(a_dbTransaction, 2L, vecCatIds));
/*      */     }
/*      */ 
/* 1258 */     return userCategories;
/*      */   }
/*      */ 
/*      */   public void addGroupToOrgUnit(DBTransaction a_dbTransaction, long a_lOrgUnitId, long a_lGroupId)
/*      */     throws Bn2Exception
/*      */   {
/* 1281 */     String ksMethodName = "addGroupToOrgUnit";
/* 1282 */     Connection con = null;
/* 1283 */     String sSQL = null;
/* 1284 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/* 1288 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1291 */       sSQL = "DELETE FROM OrgUnitGroup WHERE UserGroupId = ? ";
/* 1292 */       psql = con.prepareStatement(sSQL);
/* 1293 */       psql.setLong(1, a_lGroupId);
/* 1294 */       psql.executeUpdate();
/* 1295 */       psql.close();
/*      */ 
/* 1298 */       sSQL = "INSERT INTO OrgUnitGroup (OrgUnitId, UserGroupId) VALUES (?, ?)";
/*      */ 
/* 1300 */       psql = con.prepareStatement(sSQL);
/* 1301 */       psql.setLong(1, a_lOrgUnitId);
/* 1302 */       psql.setLong(2, a_lGroupId);
/* 1303 */       psql.executeUpdate();
/* 1304 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1310 */       throw new Bn2Exception("OrgUnitManager.addGroupToOrgUnit: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector<OrgUnit> getOrgUnitsForUser(DBTransaction a_dbTransaction, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 1332 */     String ksMethodName = "getOrgUnitForUser";
/* 1333 */     Connection con = null;
/* 1334 */     String sSQL = null;
/* 1335 */     PreparedStatement psql = null;
/* 1336 */     Vector vec = new Vector();
/*      */     try
/*      */     {
/* 1340 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1343 */       sSQL = "SELECT oug.OrgUnitId FROM OrgUnitGroup oug, UserInGroup uig WHERE uig.UserGroupId = oug.UserGroupId AND uig.UserId = ? ";
/*      */ 
/* 1348 */       psql = con.prepareStatement(sSQL);
/* 1349 */       psql.setLong(1, a_lUserId);
/* 1350 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1352 */       while (rs.next())
/*      */       {
/* 1354 */         long lOrgUnitId = rs.getLong("OrgUnitId");
/* 1355 */         OrgUnit ou = getOrgUnit(a_dbTransaction, lOrgUnitId);
/* 1356 */         vec.add(ou);
/*      */       }
/*      */ 
/* 1359 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1364 */       throw new Bn2Exception("OrgUnitManager.getOrgUnitForUser: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/* 1367 */     return vec;
/*      */   }
/*      */ 
/*      */   public void addUserToOrgUnit(DBTransaction a_dbTransaction, long a_lOrgUnitId, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 1387 */     String ksMethodName = "addUserToOrgUnit";
/*      */ 
/* 1390 */     OrgUnit ou = getOrgUnit(a_dbTransaction, a_lOrgUnitId);
/*      */ 
/* 1392 */     Group standard = ou.getUserGroup();
/*      */ 
/* 1394 */     Connection con = null;
/* 1395 */     String sSQL = null;
/* 1396 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/* 1400 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1403 */       sSQL = "INSERT INTO UserInGroup (UserId, UserGroupId) VALUES (?,?)";
/*      */ 
/* 1405 */       psql = con.prepareStatement(sSQL);
/* 1406 */       psql.setLong(1, a_lUserId);
/* 1407 */       psql.setLong(2, standard.getId());
/* 1408 */       psql.executeUpdate();
/* 1409 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1415 */       throw new Bn2Exception("OrgUnitManager.addUserToOrgUnit: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getAdminEmailAddresses(DBTransaction a_dbTransaction, long a_lOrgUnitId)
/*      */     throws Bn2Exception
/*      */   {
/* 1434 */     HashMap hmAdminEmails = new HashMap();
/*      */ 
/* 1436 */     OrgUnit ou = getOrgUnit(a_dbTransaction, a_lOrgUnitId);
/* 1437 */     Group adminGroup = ou.getAdminGroup();
/*      */ 
/* 1439 */     UserSearchCriteria search = new UserSearchCriteria();
/* 1440 */     search.setGroupId(adminGroup.getId());
/* 1441 */     Vector<ABUser> vecAdminUsers = this.m_userManager.findUsers(search, 0);
/*      */ 
/* 1443 */     for (ABUser user : vecAdminUsers)
/*      */     {
/* 1445 */       String sEmail = user.getEmailAddress();
/* 1446 */       if ((StringUtil.stringIsPopulated(sEmail)) && (user.getReceiveAlerts()))
/*      */       {
/* 1448 */         hmAdminEmails.put(sEmail, sEmail);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1453 */     String sEmailAddresses = StringUtil.getEmailAddressesFromHashMap(hmAdminEmails);
/*      */ 
/* 1455 */     return sEmailAddresses;
/*      */   }
/*      */ 
/*      */   public void deleteUserFromManagedOrgUnits(DBTransaction a_dbTransaction, long a_lUserId, long a_lOrgUnitAdminUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 1474 */     String ksMethodName = "deleteUserFromManagedOrgUnits";
/* 1475 */     Connection con = null;
/* 1476 */     boolean bCanDelete = true;
/*      */     try
/*      */     {
/* 1480 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1482 */       String sSql = null;
/* 1483 */       PreparedStatement psql = null;
/*      */ 
/* 1486 */       Vector vecManagedGroupIds = getOrgUnitAdminUserManagedGroupsAsIds(a_dbTransaction, a_lOrgUnitAdminUserId);
/* 1487 */       String sManagedGroupIds = StringUtil.convertNumbersToString(vecManagedGroupIds, ",");
/*      */ 
/* 1490 */       sSql = "DELETE FROM UserInGroup WHERE UserGroupId IN ( " + sManagedGroupIds + " ) " + "AND UserId=? ";
/*      */ 
/* 1493 */       psql = con.prepareStatement(sSql);
/* 1494 */       psql.setLong(1, a_lUserId);
/* 1495 */       psql.executeUpdate();
/* 1496 */       psql.close();
/*      */ 
/* 1499 */       sSql = "SELECT 1 FROM UserInGroup WHERE UserGroupId <> 1 AND UserId=? ";
/*      */ 
/* 1501 */       psql = con.prepareStatement(sSql);
/* 1502 */       psql.setLong(1, a_lUserId);
/* 1503 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1505 */       while (rs.next())
/*      */       {
/* 1507 */         bCanDelete = false;
/*      */       }
/*      */ 
/* 1510 */       psql.close();
/*      */ 
/* 1512 */       if (bCanDelete)
/*      */       {
/* 1515 */         this.m_userManager.deleteUser(a_dbTransaction, a_lUserId);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1521 */       throw new Bn2Exception("OrgUnitManager.deleteUserFromManagedOrgUnits: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void initialiseExtraContentItems(DBTransaction a_dbTransaction, OrgUnitMetadata a_ou)
/*      */     throws Bn2Exception
/*      */   {
/* 1540 */     ArrayList alContent = a_ou.getOrgUnitContentForPurpose(1L, false);
/* 1541 */     if ((alContent == null) || (alContent.size() <= 0))
/*      */     {
/* 1543 */       String ksMethodName = "initialiseExtraContentItems";
/* 1544 */       Connection con = null;
/* 1545 */       String sSQL = null;
/* 1546 */       PreparedStatement psql = null;
/*      */ 
/* 1548 */       OrgUnitContent content = new OrgUnitContent(1L);
/*      */ 
/* 1551 */       ListItem item = new ListItem();
/* 1552 */       item.setListId(1L);
/* 1553 */       String sIdentifier = "orgunit-content-page-" + a_ou.getId();
/* 1554 */       item.setIdentifier(sIdentifier);
/* 1555 */       content.setContentListItemIdentifier(sIdentifier);
/* 1556 */       item.setTitle("Org unit content page: " + a_ou.getId());
/* 1557 */       item.setListItemTextTypeId(1L);
/* 1558 */       item.setLanguage(LanguageConstants.k_defaultLanguage);
/* 1559 */       this.m_listManager.saveListItem(a_dbTransaction, item);
/*      */ 
/* 1561 */       ListItem menuitem = new ListItem();
/* 1562 */       menuitem.setListId(7L);
/* 1563 */       sIdentifier = "orgunit-content-menu-" + a_ou.getId();
/* 1564 */       menuitem.setIdentifier(sIdentifier);
/* 1565 */       content.setMenuListItemIdentifier(sIdentifier);
/* 1566 */       menuitem.setTitle("Org unit content menu item: " + a_ou.getId());
/* 1567 */       menuitem.setBody(a_ou.getName() + " Content");
/* 1568 */       menuitem.setListItemTextTypeId(2L);
/* 1569 */       menuitem.setLanguage(LanguageConstants.k_defaultLanguage);
/* 1570 */       this.m_listManager.saveListItem(a_dbTransaction, menuitem);
/*      */ 
/* 1572 */       a_ou.addOrgUnitContent(content);
/*      */       try
/*      */       {
/* 1577 */         con = a_dbTransaction.getConnection();
/*      */ 
/* 1579 */         sSQL = "INSERT INTO OrgUnitContent (OrgUnitId, ContentPurposeId, ContentListItemIdentifier, MenuListItemIdentifier) VALUES (?,?,?,?)";
/* 1580 */         psql = con.prepareStatement(sSQL);
/* 1581 */         psql.setLong(1, a_ou.getId());
/* 1582 */         psql.setLong(2, content.getContentPurposeId());
/* 1583 */         psql.setString(3, content.getContentListItemIdentifier());
/* 1584 */         psql.setString(4, content.getMenuListItemIdentifier());
/* 1585 */         psql.executeUpdate();
/* 1586 */         psql.close();
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/* 1591 */         throw new Bn2Exception("OrgUnitManager.initialiseExtraContentItems: Exception occurred: " + e, e);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private OrgUnit getOrgUnitForCategory(DBTransaction a_dbTransaction, long a_lCategoryId)
/*      */     throws Bn2Exception
/*      */   {
/* 1617 */     OrgUnit ou = new OrgUnit();
/*      */ 
/* 1620 */     long lRootCategory = 0L;
/* 1621 */     Category cat = this.m_categoryManager.getCategory(a_dbTransaction, 2L, a_lCategoryId);
/*      */ 
/* 1623 */     if (cat.getParentCategory().getId() == -1L)
/*      */     {
/* 1626 */       lRootCategory = cat.getId();
/*      */     }
/*      */     else
/*      */     {
/* 1630 */       Vector vecAncestors = cat.getAncestors();
/* 1631 */       Iterator it = vecAncestors.iterator();
/*      */ 
/* 1633 */       while (it.hasNext())
/*      */       {
/* 1635 */         Category ancestor = (Category)it.next();
/*      */ 
/* 1637 */         if (ancestor.getParentCategory().getId() == -1L)
/*      */         {
/* 1639 */           lRootCategory = ancestor.getId();
/* 1640 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1645 */     if (lRootCategory > 0L)
/*      */     {
/* 1648 */       ou = getOrgUnit(a_dbTransaction, 0L, lRootCategory);
/*      */     }
/*      */ 
/* 1651 */     return ou;
/*      */   }
/*      */ 
/*      */   private void addOrgUnit(DBTransaction a_dbTransaction, OrgUnit a_ou)
/*      */     throws Bn2Exception
/*      */   {
/* 1665 */     String ksMethodName = "addOrgUnit";
/* 1666 */     Connection con = null;
/* 1667 */     String sSQL = null;
/* 1668 */     PreparedStatement psql = null;
/* 1669 */     long lNewId = 0L;
/*      */     try
/*      */     {
/* 1673 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1679 */       sSQL = "INSERT INTO OrgUnit (";
/*      */ 
/* 1681 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/* 1682 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1684 */         lNewId = sqlGenerator.getUniqueId(con, "OrgUnitSequence");
/* 1685 */         sSQL = sSQL + "Id, ";
/*      */       }
/* 1687 */       sSQL = sSQL + "RootOrgUnitCategoryId, RootDescriptiveCategoryId, AdminGroupId, StandardGroupId, DiskQuotaMb) VALUES (";
/*      */ 
/* 1689 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1691 */         sSQL = sSQL + "?, ";
/*      */       }
/* 1693 */       sSQL = sSQL + "?,?,?,?,?)";
/*      */ 
/* 1695 */       psql = con.prepareStatement(sSQL);
/* 1696 */       int iCol = 1;
/* 1697 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1699 */         psql.setLong(iCol++, lNewId);
/*      */       }
/* 1701 */       psql.setLong(iCol++, a_ou.getCategory().getId());
/* 1702 */       DBUtil.setFieldIdOrNull(psql, iCol++, a_ou.getRootDescriptiveCategory());
/* 1703 */       psql.setLong(iCol++, a_ou.getAdminGroup().getId());
/* 1704 */       psql.setLong(iCol++, a_ou.getUserGroup().getId());
/* 1705 */       psql.setInt(iCol++, a_ou.getDiskQuotaMb());
/* 1706 */       psql.executeUpdate();
/* 1707 */       psql.close();
/*      */ 
/* 1709 */       if (sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1711 */         lNewId = sqlGenerator.getUniqueId(con, "OrgUnit");
/*      */       }
/* 1713 */       a_ou.setId(lNewId);
/*      */ 
/* 1716 */       sSQL = "INSERT INTO OrgUnitGroup (OrgUnitId, UserGroupId) VALUES (?, ?)";
/*      */ 
/* 1718 */       psql = con.prepareStatement(sSQL);
/* 1719 */       psql.setLong(1, a_ou.getId());
/* 1720 */       psql.setLong(2, a_ou.getAdminGroup().getId());
/* 1721 */       psql.executeUpdate();
/* 1722 */       psql.close();
/*      */ 
/* 1724 */       psql = con.prepareStatement(sSQL);
/* 1725 */       psql.setLong(1, a_ou.getId());
/* 1726 */       psql.setLong(2, a_ou.getUserGroup().getId());
/* 1727 */       psql.executeUpdate();
/* 1728 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1735 */       throw new Bn2Exception("OrgUnitManager.addOrgUnit: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addCategoriesToVector(DBTransaction a_dbTransaction, Category a_root, Vector a_vecCatIds)
/*      */     throws Bn2Exception
/*      */   {
/* 1756 */     a_vecCatIds.add(new Long(a_root.getId()));
/*      */ 
/* 1759 */     long[] alDescendantIds = this.m_categoryManager.getDescendantIds(a_dbTransaction, 2L, a_root.getId());
/* 1760 */     for (int j = 0; j < alDescendantIds.length; j++)
/*      */     {
/* 1762 */       a_vecCatIds.add(new Long(alDescendantIds[j]));
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isDiskQuotaExceeded(DBTransaction a_dbTransaction, long a_lUserId, long a_lNewSpaceRequiredInBytes)
/*      */     throws Bn2Exception
/*      */   {
/* 1775 */     Vector vOrgUnits = getOrgUnitsForUser(a_dbTransaction, a_lUserId);
/*      */ 
/* 1777 */     if ((vOrgUnits == null) || (vOrgUnits.size() == 0))
/*      */     {
/* 1779 */       return false;
/*      */     }
/*      */ 
/* 1782 */     return isDiskQuotaExceededForAll(a_dbTransaction, vOrgUnits, a_lNewSpaceRequiredInBytes);
/*      */   }
/*      */ 
/*      */   public boolean isDiskQuotaExceededForAll(DBTransaction a_dbTransaction, Vector a_vOrgUnits, long a_lNewSpaceRequiredInBytes)
/*      */     throws Bn2Exception
/*      */   {
/* 1793 */     for (int i = 0; i < a_vOrgUnits.size(); i++)
/*      */     {
/* 1795 */       OrgUnit orgUnit = (OrgUnit)a_vOrgUnits.get(i);
/*      */ 
/* 1797 */       long lUsage = this.m_categoryManager.getDiskUsageForCategoryAssets(a_dbTransaction, orgUnit.getCategory().getId(), orgUnit.getCategory().getCategoryTypeId());
/*      */ 
/* 1799 */       if ((orgUnit.getDiskQuotaMb() <= 0) || (lUsage + a_lNewSpaceRequiredInBytes < orgUnit.getDiskQuotaMb() * 1048576L))
/*      */       {
/* 1801 */         return false;
/*      */       }
/*      */     }
/* 1804 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean isDiskQuotaExceededForAny(DBTransaction a_dbTransaction, Vector a_vOrgUnits, long a_lNewSpaceRequiredInBytes)
/*      */     throws Bn2Exception
/*      */   {
/* 1815 */     for (int i = 0; i < a_vOrgUnits.size(); i++)
/*      */     {
/* 1817 */       OrgUnit orgUnit = (OrgUnit)a_vOrgUnits.get(i);
/*      */ 
/* 1819 */       long lUsage = this.m_categoryManager.getDiskUsageForCategoryAssets(a_dbTransaction, orgUnit.getCategory().getId(), orgUnit.getCategory().getCategoryTypeId());
/*      */ 
/* 1821 */       if ((orgUnit.getDiskQuotaMb() > 0) && (lUsage + a_lNewSpaceRequiredInBytes >= orgUnit.getDiskQuotaMb() * 1048576L))
/*      */       {
/* 1823 */         return true;
/*      */       }
/*      */     }
/* 1826 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean isDiskQuotaExceeded(DBTransaction a_dbTransaction, OrgUnit a_orgUnit, long a_lNewSpaceRequiredInBytes)
/*      */     throws Bn2Exception
/*      */   {
/* 1837 */     long lUsage = this.m_categoryManager.getDiskUsageForCategoryAssets(a_dbTransaction, a_orgUnit.getCategory().getId(), a_orgUnit.getCategory().getCategoryTypeId());
/*      */ 
/* 1839 */     return a_orgUnit.getDiskQuotaMb() * 1048576L < lUsage + a_lNewSpaceRequiredInBytes;
/*      */   }
/*      */ 
/*      */   private OrgUnitContent buildOrgUnitContent(ResultSet a_rs)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 1854 */     OrgUnitContent content = new OrgUnitContent(a_rs.getLong("ContentPurposeId"));
/* 1855 */     content.setOrgUnitId(a_rs.getLong("OrgUnitId"));
/* 1856 */     content.setContentListItemIdentifier(a_rs.getString("ContentListItemIdentifier"));
/* 1857 */     content.setMenuListItemIdentifier(a_rs.getString("MenuListItemIdentifier"));
/*      */ 
/* 1861 */     ListItem item = this.m_listManager.getListItem(content.getContentListItemIdentifier(), LanguageConstants.k_defaultLanguage);
/* 1862 */     if ((item != null) && (StringUtil.stringIsPopulated(item.getBody())))
/*      */     {
/* 1864 */       content.setEnabled(true);
/*      */     }
/*      */ 
/* 1867 */     return content;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.service.OrgUnitManager
 * JD-Core Version:    0.6.0
 */