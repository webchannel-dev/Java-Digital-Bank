/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.AssetFileSource;
/*     */ import com.bright.assetbank.application.exception.AssetFileReadException;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.CategoryImpl;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class AdhocAdminAction extends BTransactionAction
/*     */   implements FrameworkConstants
/*     */ {
/*  67 */   private IAssetManager m_assetManager = null;
/*  68 */   protected AssetCategoryManager m_categoryManager = null;
/*  69 */   protected AssetEntityManager m_assetEntityManager = null;
/*  70 */   private AssetEntity m_entity = null;
/*  71 */   protected AssetWorkflowManager m_approvalManager = null;
/*     */ 
/*  73 */   private String[] m_aTypeIdentifiers = { "SPOT", "CMYK_HR", "CMYK_LR", "CMYK", "RGB_HR", "RGB_LR", "RGB" };
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 102 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 105 */     if (!userProfile.getIsAdmin())
/*     */     {
/* 107 */       this.m_logger.error("This user does not have permission to view the admin pages");
/* 108 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 111 */     long lTopLevelCatId = getLongParameter(a_request, "id");
/*     */ 
/* 113 */     this.m_logger.debug("Performing adhocadminaction on id:" + lTopLevelCatId);
/*     */ 
/* 115 */     long[] catIds = this.m_categoryManager.getDescendantIds(a_dbTransaction, 2L, lTopLevelCatId);
/*     */ 
/* 117 */     this.m_logger.debug("Found descendant ids:" + catIds);
/*     */ 
/* 120 */     for (int i = 0; i < catIds.length; i++)
/*     */     {
/* 122 */       Category cat = this.m_categoryManager.getCategory(a_dbTransaction, 2L, catIds[i]);
/*     */ 
/* 124 */       this.m_logger.debug("Processing category:" + cat.getName());
/*     */ 
/* 127 */       if (!cat.getName().equalsIgnoreCase("different formats")) {
/*     */         continue;
/*     */       }
/* 130 */       long lParentId = cat.getParentCategory().getId();
/*     */ 
/* 132 */       this.m_logger.debug("Found different formats category");
/*     */       try
/*     */       {
/* 136 */         Connection con = null;
/* 137 */         con = a_dbTransaction.getConnection();
/*     */ 
/* 142 */         String sSql = "UPDATE CM_ItemInCategory SET CategoryId=? WHERE CategoryId=?";
/* 143 */         PreparedStatement psql = con.prepareStatement(sSql);
/* 144 */         psql.setLong(1, lParentId);
/* 145 */         psql.setLong(2, cat.getId());
/* 146 */         psql.executeUpdate();
/*     */ 
/* 149 */         sSql = "SELECT ItemId FROM CM_ItemInCategory WHERE CategoryId=?";
/* 150 */         psql = con.prepareStatement(sSql);
/* 151 */         psql = con.prepareStatement(sSql);
/* 152 */         psql.setLong(1, lParentId);
/*     */ 
/* 154 */         ResultSet rs = psql.executeQuery();
/*     */ 
/* 156 */         boolean first = true;
/* 157 */         String sAssetIds = "";
/*     */ 
/* 159 */         while (rs.next())
/*     */         {
/* 161 */           if (!first)
/*     */           {
/* 163 */             sAssetIds = sAssetIds + ",";
/*     */           }
/*     */ 
/* 166 */           sAssetIds = sAssetIds + rs.getLong("ItemId");
/*     */ 
/* 168 */           first = false;
/*     */         }
/*     */ 
/* 172 */         sSql = "DELETE FROM CM_ItemInCategory WHERE ItemId IN (" + sAssetIds + ") AND CategoryId !=?";
/* 173 */         psql = con.prepareStatement(sSql);
/* 174 */         psql.setLong(1, lParentId);
/* 175 */         psql.executeUpdate();
/*     */ 
/* 178 */         sSql = "DELETE FROM CategoryVisibleToGroup WHERE CategoryId=?";
/* 179 */         psql = con.prepareStatement(sSql);
/* 180 */         psql.setLong(1, cat.getId());
/* 181 */         psql.executeUpdate();
/*     */ 
/* 183 */         sSql = "DELETE FROM TranslatedCategory WHERE CategoryId=?";
/* 184 */         psql = con.prepareStatement(sSql);
/* 185 */         psql.setLong(1, cat.getId());
/* 186 */         psql.executeUpdate();
/*     */ 
/* 188 */         sSql = "DELETE FROM CM_Category WHERE Id=?";
/* 189 */         psql = con.prepareStatement(sSql);
/* 190 */         psql.setLong(1, cat.getId());
/* 191 */         psql.executeUpdate();
/*     */ 
/* 195 */         psql.close();
/*     */       }
/*     */       catch (SQLException e)
/*     */       {
/* 199 */         this.m_logger.error("AdHocAdminAction: " + e);
/* 200 */         throw new Bn2Exception("AdHocAdminAction SQL Exception: ", e);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 207 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setupCategories(DBTransaction a_dbTransaction, long a_lTreeId, long a_lCategoryId)
/*     */     throws Bn2Exception
/*     */   {
/* 216 */     long[] catIds = this.m_categoryManager.getDescendantIds(a_dbTransaction, a_lTreeId, 1L);
/*     */ 
/* 219 */     for (int i = 0; i < catIds.length; i++)
/*     */     {
/* 221 */       long descendantId = catIds[i];
/*     */ 
/* 223 */       long[] descendantIds = this.m_categoryManager.getDescendantIds(a_dbTransaction, a_lTreeId, descendantId);
/*     */ 
/* 226 */       if (descendantIds.length != 0)
/*     */         continue;
/* 228 */       this.m_logger.debug("found low level category: " + catIds[i]);
/*     */ 
/* 231 */       Vector categoryNames = new Vector();
/* 232 */       categoryNames.add("No Out of the Ordinary");
/*     */ 
/* 235 */       Category cat = this.m_categoryManager.getCategory(a_dbTransaction, a_lTreeId, catIds[i]);
/*     */ 
/* 238 */       if (categoryNames.contains(cat.getName())) {
/*     */         continue;
/*     */       }
/* 241 */       Iterator itNames = categoryNames.iterator();
/* 242 */       while (itNames.hasNext())
/*     */       {
/* 244 */         CategoryImpl newCategory = new CategoryImpl();
/* 245 */         newCategory.setWorkflowName(cat.getWorkflowName());
/* 246 */         newCategory.setName((String)itNames.next());
/* 247 */         newCategory.setCanAssignIfNotLeaf(true);
/* 248 */         AssetCategoryManager assetCategoryManager = this.m_categoryManager;
/* 249 */         assetCategoryManager.newPermissionCategory(a_dbTransaction, a_lTreeId, newCategory, catIds[i]);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean migrationRoutine(DBTransaction a_dbTransaction, File a_directory, long a_lParentCategoryId)
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/* 265 */       File[] files = a_directory.listFiles();
/* 266 */       Category differentFormatCategory = null;
/* 267 */       int i = 0;
/*     */ 
/* 269 */       for (File file : files)
/*     */       {
/* 271 */         if ((i != 0) && (i % 200 == 0))
/*     */         {
/* 273 */           this.m_logger.warn("AdhocAdminAction: Checked 200 files/directories");
/*     */ 
/* 276 */           a_dbTransaction.commit();
/* 277 */           a_dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */         }
/* 279 */         i++;
/*     */ 
/* 281 */         String sName = file.getName();
/* 282 */         this.m_logger.warn("AdhocAdminAction: EXAMINING FILE: " + sName);
/* 283 */         if (file.isDirectory())
/*     */         {
/* 286 */           long lCatId = createCategory(a_dbTransaction, sName, a_lParentCategoryId);
/* 287 */           migrationRoutine(a_dbTransaction, file, lCatId);
/*     */         }
/*     */         else
/*     */         {
/* 293 */           String sNameNoSuffix = FileUtil.getFilenameWithoutSuffix(sName);
/* 294 */           if ((!sNameNoSuffix.endsWith("RGB_LR")) && (!sNameNoSuffix.endsWith("RGB_LR_M"))) {
/*     */             continue;
/*     */           }
/* 297 */           Category permissionCategory = this.m_categoryManager.getCategory(a_dbTransaction, 2L, a_lParentCategoryId);
/*     */ 
/* 300 */           ArrayList<File> alDifferentFormatFiles = getDifferentFormatFiles(file);
/*     */ 
/* 303 */           Asset asset = addFileAsAsset(a_dbTransaction, file, permissionCategory, false);
/*     */ 
/* 306 */           if (differentFormatCategory == null)
/*     */           {
/* 308 */             long lDifferentFormatCatId = createCategory(a_dbTransaction, "Different formats", a_lParentCategoryId);
/* 309 */             differentFormatCategory = this.m_categoryManager.getCategory(a_dbTransaction, 2L, lDifferentFormatCatId);
/*     */           }
/*     */ 
/* 313 */           String sRelatedIds = "";
/* 314 */           for (File diffFile : alDifferentFormatFiles)
/*     */           {
/* 316 */             if (!diffFile.getName().equals(file.getName()))
/*     */             {
/* 318 */               Asset diffAsset = addFileAsAsset(a_dbTransaction, diffFile, differentFormatCategory, false);
/* 319 */               sRelatedIds = sRelatedIds + diffAsset.getId() + ",";
/*     */             }
/*     */           }
/*     */ 
/* 323 */           if (!StringUtil.stringIsPopulated(sRelatedIds))
/*     */           {
/*     */             continue;
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 334 */       this.m_logger.error("ADHOCADMINACTION: Error: ", e);
/*     */     }
/* 336 */     return true;
/*     */   }
/*     */ 
/*     */   private long createCategory(DBTransaction a_dbTransaction, String a_sName, long a_lParentCategoryId)
/*     */     throws Bn2Exception
/*     */   {
/* 344 */     Category newCategory = null;
/*     */ 
/* 347 */     Vector<Category> vecCategoriesByName = this.m_categoryManager.getCategoriesByName(a_dbTransaction, 2L, a_sName, false);
/*     */ 
/* 349 */     if (vecCategoriesByName != null)
/*     */     {
/* 351 */       for (Category cat : vecCategoriesByName)
/*     */       {
/* 354 */         if (cat.getParentId() == a_lParentCategoryId)
/*     */         {
/* 356 */           newCategory = cat;
/* 357 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 362 */     if (newCategory == null)
/*     */     {
/* 364 */       newCategory = new CategoryImpl();
/* 365 */       if (a_lParentCategoryId > 0L)
/*     */       {
/* 367 */         Category parent = this.m_categoryManager.getCategory(a_dbTransaction, 2L, a_lParentCategoryId);
/* 368 */         if ((parent != null) && (StringUtil.stringIsPopulated(parent.getWorkflowName())))
/*     */         {
/* 370 */           newCategory.setWorkflowName(parent.getWorkflowName());
/*     */         }
/*     */       }
/* 373 */       newCategory.setName(a_sName);
/* 374 */       newCategory.setCanAssignIfNotLeaf(true);
/* 375 */       AssetCategoryManager assetCategoryManager = this.m_categoryManager;
/* 376 */       assetCategoryManager.newPermissionCategory(a_dbTransaction, 2L, newCategory, a_lParentCategoryId);
/*     */     }
/*     */ 
/* 379 */     return newCategory.getId();
/*     */   }
/*     */ 
/*     */   private String getIdentifierFromMainFile(File a_file)
/*     */   {
/* 385 */     String sNameNoSuffix = FileUtil.getFilenameWithoutSuffix(a_file.getName());
/*     */ 
/* 388 */     String sName = sNameNoSuffix;
/* 389 */     if (sNameNoSuffix.endsWith("_M"))
/*     */     {
/* 391 */       sName = sName.substring(0, sNameNoSuffix.length() - 2);
/*     */     }
/*     */ 
/* 395 */     for (String sIdentifier : this.m_aTypeIdentifiers)
/*     */     {
/* 397 */       sIdentifier = "_" + sIdentifier;
/* 398 */       if (sName.endsWith(sIdentifier))
/*     */       {
/* 401 */         return sName.substring(0, sName.length() - sIdentifier.length());
/*     */       }
/*     */     }
/* 404 */     return null;
/*     */   }
/*     */ 
/*     */   private ArrayList<File> getDifferentFormatFiles(File a_file)
/*     */   {
/* 410 */     String sIdentifier = getIdentifierFromMainFile(a_file);
/* 411 */     ArrayList alFiles = new ArrayList();
/*     */ 
/* 414 */     File dir = a_file.getParentFile();
/* 415 */     File[] files = dir.listFiles();
/* 416 */     for (File file : files)
/*     */     {
/* 418 */       if ((!file.getName().startsWith(sIdentifier)) || (file.getName() == a_file.getName()))
/*     */         continue;
/* 420 */       alFiles.add(file);
/*     */     }
/*     */ 
/* 423 */     return alFiles;
/*     */   }
/*     */ 
/*     */   private Asset addFileAsAsset(DBTransaction a_dbTransaction, File a_file, Category a_permissionCat, boolean a_bAddToAllParents)
/*     */     throws AssetFileReadException, FileNotFoundException, Bn2Exception
/*     */   {
/* 430 */     AssetFileSource source = new AssetFileSource();
/* 431 */     FileInputStream input = new FileInputStream(a_file);
/* 432 */     source.setInputStream(input);
/* 433 */     source.setFilename(a_file.getName());
/* 434 */     source.setOriginalFilename(a_file.getName());
/* 435 */     Asset tempAsset = new Asset();
/*     */ 
/* 437 */     Vector vecPermCat = new Vector();
/* 438 */     vecPermCat.add(a_permissionCat);
/* 439 */     if (a_bAddToAllParents)
/*     */     {
/* 441 */       for (Category cat : a_permissionCat.getAncestors())
/*     */       {
/* 443 */         vecPermCat.add(cat);
/*     */       }
/*     */     }
/*     */ 
/* 447 */     tempAsset.setPermissionCategories(vecPermCat);
/* 448 */     tempAsset.setEntity(this.m_entity);
/* 449 */     tempAsset.setDateAdded(new Date());
/* 450 */     tempAsset = this.m_assetManager.saveAsset(a_dbTransaction, tempAsset, source, 1L, null, null, false, 0);
/*     */ 
/* 452 */     this.m_approvalManager.approveAssetEndAllWorkflows(a_dbTransaction, tempAsset, false);
/*     */ 
/* 454 */     return tempAsset;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 460 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetWorkflowManager(AssetWorkflowManager a_approvalManager)
/*     */   {
/* 465 */     this.m_approvalManager = a_approvalManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_categoryManager)
/*     */   {
/* 470 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public void setAssetEntityManager(AssetEntityManager a_assetEntityManager)
/*     */   {
/* 475 */     this.m_assetEntityManager = a_assetEntityManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.AdhocAdminAction
 * JD-Core Version:    0.6.0
 */