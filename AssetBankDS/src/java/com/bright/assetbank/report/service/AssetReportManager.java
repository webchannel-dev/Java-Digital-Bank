/*     */ package com.bright.assetbank.report.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.application.util.AssetUtil;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeType;
/*     */ import com.bright.assetbank.attribute.constant.AttributeStorageType;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.category.bean.ExtendedCategoryInfo;
/*     */ import com.bright.assetbank.report.bean.AssetByCategoryRecord;
/*     */ import com.bright.assetbank.report.bean.AssetRecord;
/*     */ import com.bright.assetbank.report.bean.KeywordRecord;
/*     */ import com.bright.assetbank.report.constant.ReportConstants;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.FlatCategoryList;
/*     */ import com.bright.framework.category.util.CategoryUtil;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.database.util.DBUtil;
/*     */ import com.bright.framework.image.bean.ImageFile;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.util.BrightDateFormat;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.Format;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class AssetReportManager extends Bn2Manager
/*     */   implements ReportConstants, AssetBankConstants
/*     */ {
/*     */   private static final String c_ksClassName = "AssetReportManager";
/*  84 */   private AssetCategoryManager m_categoryManager = null;
/*     */ 
/*  90 */   private FileStoreManager m_fileStoreManager = null;
/*     */ 
/*  96 */   private ListManager m_listManager = null;
/*     */ 
/* 102 */   private AttributeManager m_attributeManager = null;
/*     */ 
/* 108 */   private AssetManager m_assetManager = null;
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_categoryManager)
/*     */   {
/*  87 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_sFileStoreManager)
/*     */   {
/*  93 */     this.m_fileStoreManager = a_sFileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_listManager)
/*     */   {
/*  99 */     this.m_listManager = a_listManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 105 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager manager)
/*     */   {
/* 111 */     this.m_assetManager = manager;
/*     */   }
/*     */ 
/*     */   public Vector<AssetByCategoryRecord> getAssetsByCategoryAndAttributeReport(DBTransaction a_dbTransaction, long a_lTreeId, Attribute a_attribute, boolean a_bShowTotalsOnly)
/*     */     throws Bn2Exception
/*     */   {
/* 135 */     String ksMethodName = "getAssetsByCategoryAndAttributeReport";
/* 136 */     Connection con = null;
/* 137 */     String sSQL = null;
/* 138 */     PreparedStatement psql = null;
/* 139 */     Vector vec = new Vector();
/*     */ 
/* 142 */     FlatCategoryList catList = this.m_categoryManager.getFlatCategoryList(a_dbTransaction, a_lTreeId);
/*     */ 
/* 144 */     AttributeType attributeType = null;
/* 145 */     if (a_attribute != null)
/*     */     {
/* 147 */       attributeType = this.m_attributeManager.getAttributeTypeById(a_attribute.getTypeId());
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 152 */       con = a_dbTransaction.getConnection();
/*     */ 
/* 154 */       Iterator itCats = catList.getCategories().iterator();
/* 155 */       while (itCats.hasNext())
/*     */       {
/* 157 */         Category cat = (Category)itCats.next();
/* 158 */         AssetByCategoryRecord rec = new AssetByCategoryRecord();
/* 159 */         rec.setCategory(cat);
/*     */ 
/* 162 */         String sDescendantList = StringUtil.convertNumbersToString(cat.getAllDescendantsIds(), ", ");
/* 163 */         if (StringUtil.stringIsPopulated(sDescendantList))
/*     */         {
/* 165 */           sDescendantList = sDescendantList + ", ";
/*     */         }
/* 167 */         sDescendantList = sDescendantList + cat.getId();
/*     */ 
/* 170 */         sSQL = "SELECT Count(DISTINCT ItemId) NumItems FROM CM_ItemInCategory aic WHERE aic.CategoryId IN (" + sDescendantList + ")";
/*     */ 
/* 174 */         psql = con.prepareStatement(sSQL);
/* 175 */         ResultSet rs = psql.executeQuery();
/*     */ 
/* 177 */         long lCount = 0L;
/* 178 */         if (rs.next())
/*     */         {
/* 180 */           lCount = rs.getLong("NumItems");
/*     */         }
/* 182 */         psql.close();
/* 183 */         rec.setNumUnderCategory(lCount);
/*     */ 
/* 187 */         sSQL = "SELECT asset.Id AssetId, asset.ThumbnailFileLocation, asset.OriginalFilename, iic.CategoryId, iic.IsApproved ";
/*     */ 
/* 189 */         if (attributeType != null)
/*     */         {
/* 191 */           if (attributeType.getAttributeStorageType() == AttributeStorageType.LIST)
/*     */           {
/* 193 */             sSQL = sSQL + ", lav.Value AS AttributeValue ";
/*     */           }
/* 195 */           else if (attributeType.getAttributeStorageType() == AttributeStorageType.VALUE_PER_ASSET)
/*     */           {
/* 197 */             sSQL = sSQL + ", aav" + a_attribute.getValueColumnName() + " AS AttributeValue ";
/*     */           }
/*     */         }
/*     */ 
/* 201 */         sSQL = sSQL + "FROM Asset asset INNER JOIN CM_ItemInCategory aic ON asset.Id = aic.ItemId LEFT JOIN CM_ItemInCategory iic ON asset.Id = iic.ItemId LEFT JOIN CM_Category c ON iic.CategoryId = c.Id ";
/*     */ 
/* 207 */         if (attributeType != null)
/*     */         {
/* 209 */           if (attributeType.getAttributeStorageType() == AttributeStorageType.LIST)
/*     */           {
/* 211 */             sSQL = sSQL + "LEFT JOIN AssetListAttributeValue alav ON alav.AssetId = asset.Id ";
/* 212 */             sSQL = sSQL + "LEFT JOIN ListAttributeValue lav ON alav.ListAttributeValueId = lav.Id AND lav.AttributeId = " + a_attribute.getId() + " ";
/*     */           }
/* 214 */           else if (attributeType.getAttributeStorageType() == AttributeStorageType.VALUE_PER_ASSET)
/*     */           {
/* 216 */             sSQL = sSQL + "LEFT JOIN AssetAttributeValues aav ON aav.AssetId = asset.Id ";
/*     */           }
/*     */         }
/*     */ 
/* 220 */         sSQL = sSQL + "WHERE aic.CategoryId = " + cat.getId() + " AND c.CategoryTypeId=" + 2L + " ";
/*     */ 
/* 222 */         if (attributeType != null)
/*     */         {
/* 224 */           if (attributeType.getAttributeStorageType() == AttributeStorageType.LIST)
/*     */           {
/* 226 */             sSQL = sSQL + "ORDER BY lav.SequenceNumber";
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 231 */           sSQL = sSQL + "ORDER BY asset.Id";
/*     */         }
/* 233 */         psql = con.prepareStatement(sSQL);
/* 234 */         rs = psql.executeQuery();
/* 235 */         Vector vecAssets = new Vector();
/* 236 */         long lLastAssetId = -1L;
/* 237 */         boolean bApproved = true;
/* 238 */         AssetRecord asset = null;
/* 239 */         boolean bFirst = true;
/*     */ 
/* 241 */         while (rs.next())
/*     */         {
/* 243 */           long lAssetId = rs.getLong("AssetId");
/*     */ 
/* 245 */           if (lAssetId != lLastAssetId)
/*     */           {
/* 247 */             if (!bFirst)
/*     */             {
/* 249 */               asset.setApproved(bApproved);
/* 250 */               bApproved = true;
/* 251 */               vecAssets.add(asset);
/*     */             }
/*     */ 
/* 255 */             asset = new AssetRecord();
/* 256 */             asset.setId(rs.getLong("AssetId"));
/*     */ 
/* 258 */             String sThumbnailPath = AssetUtil.getThumbnailFileLocation(a_dbTransaction, this.m_assetManager, rs.getString("ThumbnailFileLocation"), rs.getString("OriginalFilename"));
/*     */ 
/* 261 */             asset.setThumbnailPath(sThumbnailPath);
/*     */ 
/* 263 */             if ((attributeType != null) && ((attributeType.getAttributeStorageType() == AttributeStorageType.LIST) || (attributeType.getAttributeStorageType() == AttributeStorageType.VALUE_PER_ASSET)))
/*     */             {
/* 267 */               asset.setAttributeValue(rs.getString("AttributeValue"));
/*     */             }
/*     */ 
/* 270 */             lLastAssetId = lAssetId;
/* 271 */             bFirst = false;
/*     */           }
/*     */ 
/* 274 */           bApproved = (bApproved) && (rs.getBoolean("IsApproved"));
/*     */         }
/*     */ 
/* 278 */         if (asset != null)
/*     */         {
/* 280 */           asset.setApproved(bApproved);
/* 281 */           vecAssets.add(asset);
/*     */         }
/*     */ 
/* 284 */         psql.close();
/* 285 */         rec.setAssets(vecAssets);
/* 286 */         rec.setNumDirectInCategory(vecAssets.size());
/* 287 */         vec.add(rec);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 294 */       throw new Bn2Exception("AssetReportManager.getAssetsByCategoryAndAttributeReport: Exception occurred: " + e, e);
/*     */     }
/*     */ 
/* 297 */     return vec;
/*     */   }
/*     */ 
/*     */   public Vector<Asset> getAssetsByDateReport(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate, int a_iDateType, int a_iFileSizeOperator, long a_iFileSize)
/*     */     throws Bn2Exception
/*     */   {
/* 319 */     String ksMethodName = "getAssetsByDateReport";
/* 320 */     Connection con = null;
/* 321 */     PreparedStatement psql = null;
/* 322 */     String sSQL = null;
/* 323 */     ResultSet rs = null;
/* 324 */     Vector vecResults = new Vector();
/*     */     try
/*     */     {
/* 328 */       con = a_dbTransaction.getConnection();
/*     */ 
/* 330 */       sSQL = "SELECT asset.Id AS AssetId, asset.OriginalFilename, asset.ThumbnailFileLocation, asset.DateAdded, asset.DateLastModified, asset.FileSizeInBytes, addUser.Id AS AddUserId, cat.ExtensionAssetId, addUser.Username AS AddUserUsername, modUser.Id AS ModUserId, modUser.Username AS ModUserUsername FROM Asset asset LEFT JOIN AssetBankUser addUser ON addUser.Id = asset.AddedByUserId LEFT JOIN CM_Category cat ON cat.ExtensionAssetId=asset.Id LEFT JOIN AssetBankUser modUser ON modUser.Id = asset.LastModifiedByUserId ";
/*     */ 
/* 347 */       String sWhereCondition = "";
/* 348 */       String sDateColumn = null;
/*     */ 
/* 350 */       if (a_iDateType == 1)
/*     */       {
/* 352 */         sDateColumn = "DateAdded";
/*     */       }
/* 354 */       if (a_iDateType == 2)
/*     */       {
/* 356 */         sDateColumn = "DateLastModified";
/*     */       }
/*     */ 
/* 359 */       if (a_dtStartDate != null)
/*     */       {
/* 361 */         sWhereCondition = sDateColumn + " >= ? ";
/*     */       }
/* 363 */       if (a_dtEndDate != null)
/*     */       {
/* 365 */         if (StringUtils.isNotEmpty(sWhereCondition))
/*     */         {
/* 367 */           sWhereCondition = sWhereCondition + " AND ";
/*     */         }
/* 369 */         sWhereCondition = sWhereCondition + sDateColumn + " <= ? ";
/*     */       }
/*     */ 
/* 372 */       if (a_iFileSizeOperator > 0)
/*     */       {
/* 374 */         String sOperator = ">";
/*     */ 
/* 376 */         if (a_iFileSizeOperator == 1)
/*     */         {
/* 378 */           sOperator = "<";
/*     */         }
/*     */ 
/* 381 */         if (StringUtils.isNotEmpty(sWhereCondition))
/*     */         {
/* 383 */           sWhereCondition = sWhereCondition + " AND ";
/*     */         }
/*     */ 
/* 386 */         sWhereCondition = sWhereCondition + "FileSizeInBytes " + sOperator + " " + a_iFileSize;
/*     */       }
/*     */ 
/* 389 */       if (StringUtils.isNotEmpty(sWhereCondition))
/*     */       {
/* 391 */         sSQL = sSQL + " WHERE " + sWhereCondition;
/*     */       }
/*     */ 
/* 394 */       psql = con.prepareStatement(sSQL);
/*     */ 
/* 396 */       int iCol = 1;
/* 397 */       if (a_dtStartDate != null)
/*     */       {
/* 399 */         DBUtil.setFieldTimestampOrNull(psql, iCol++, a_dtStartDate);
/*     */       }
/* 401 */       if (a_dtEndDate != null)
/*     */       {
/* 403 */         DBUtil.setFieldTimestampOrNull(psql, iCol, a_dtEndDate);
/*     */       }
/*     */ 
/* 406 */       rs = psql.executeQuery();
/* 407 */       while (rs.next())
/*     */       {
/* 409 */         Asset asset = buildAsset(a_dbTransaction, rs);
/* 410 */         vecResults.add(asset);
/*     */       }
/*     */ 
/* 413 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 418 */       throw new Bn2Exception("AssetReportManager.getAssetsByDateReport: Exception occurred: " + e, e);
/*     */     }
/*     */ 
/* 421 */     return vecResults;
/*     */   }
/*     */ 
/*     */   public Vector<KeywordRecord> getAssetsByKeyword(DBTransaction a_dbTransaction, long a_lTreeId)
/*     */     throws Bn2Exception
/*     */   {
/* 442 */     String ksMethodName = "getAssetsByKeyword";
/* 443 */     Connection con = null;
/* 444 */     PreparedStatement psql = null;
/* 445 */     String sSQL = null;
/* 446 */     ResultSet rs = null;
/* 447 */     Vector vecResults = new Vector();
/*     */ 
/* 450 */     Vector vecKeywords = this.m_categoryManager.getCategories(a_dbTransaction, a_lTreeId, -1L);
/* 451 */     String sKeywordIds = CategoryUtil.convertCategoryVectorToString(vecKeywords, ", ");
/*     */ 
/* 453 */     if (StringUtil.stringIsPopulated(sKeywordIds))
/*     */     {
/*     */       try
/*     */       {
/* 457 */         con = a_dbTransaction.getConnection();
/*     */ 
/* 459 */         sSQL = "SELECT k.Id AS KeywordId, k.Name AS KeywordValue, asset.Id AS AssetId, asset.OriginalFilename, asset.ThumbnailFileLocation, asset.DateAdded, asset.DateLastModified, asset.FileSizeInBytes, cat.ExtensionAssetId, addUser.Id AS AddUserId, addUser.Username AS AddUserUsername, modUser.Id AS ModUserId, modUser.Username AS ModUserUsername FROM Asset asset LEFT JOIN CM_ItemInCategory iic ON (asset.id = iic.ItemId AND iic.CategoryId IN (" + sKeywordIds + ")) " + "LEFT JOIN CM_Category k ON (k.Id = iic.CategoryId AND k.CategoryTypeId = ?) " + "LEFT JOIN CM_Category cat ON cat.ExtensionAssetId=asset.Id " + "LEFT JOIN AssetBankUser addUser ON addUser.Id = asset.AddedByUserId " + "LEFT JOIN AssetBankUser modUser ON modUser.Id = asset.LastModifiedByUserId " + "ORDER BY k.Name, asset.ID ";
/*     */ 
/* 482 */         psql = con.prepareStatement(sSQL);
/* 483 */         psql.setLong(1, a_lTreeId);
/* 484 */         rs = psql.executeQuery();
/*     */ 
/* 486 */         long lLastKeywordId = -1L;
/* 487 */         KeywordRecord keyword = new KeywordRecord();
/*     */ 
/* 489 */         while (rs.next())
/*     */         {
/* 492 */           long lNextKeywordId = rs.getLong("KeywordId");
/* 493 */           if (lNextKeywordId != lLastKeywordId)
/*     */           {
/* 495 */             keyword = new KeywordRecord();
/* 496 */             keyword.setId(rs.getLong("KeywordId"));
/*     */ 
/* 498 */             if (keyword.getId() > 0L)
/*     */             {
/* 500 */               keyword.setName(rs.getString("KeywordValue"));
/*     */             }
/*     */ 
/* 503 */             vecResults.add(keyword);
/* 504 */             lLastKeywordId = lNextKeywordId;
/*     */           }
/*     */ 
/* 508 */           Asset asset = buildAsset(a_dbTransaction, rs);
/* 509 */           keyword.getAssets().add(asset);
/*     */         }
/*     */ 
/* 512 */         psql.close();
/*     */       }
/*     */       catch (SQLException e)
/*     */       {
/* 517 */         throw new Bn2Exception("AssetReportManager.getAssetsByKeyword: Exception occurred: " + e, e);
/*     */       }
/*     */     }
/*     */ 
/* 521 */     return vecResults;
/*     */   }
/*     */ 
/*     */   public Vector<Vector<Asset>> getPotentiallyDuplicateFiles(DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 542 */     String ksMethodName = "getPotentiallyDuplicateFiles";
/* 543 */     Connection con = null;
/* 544 */     PreparedStatement psql = null;
/* 545 */     String sSQL = null;
/* 546 */     ResultSet rs = null;
/* 547 */     Vector vecResults = new Vector();
/*     */     try
/*     */     {
/* 551 */       con = a_dbTransaction.getConnection();
/*     */ 
/* 553 */       sSQL = "SELECT asset.Id AS AssetId, asset.OriginalFilename, asset.FileLocation, asset.ThumbnailFileLocation, asset.DateAdded, asset.DateLastModified, asset.FileSizeInBytes, asset.IsNotDuplicate, cat.ExtensionAssetId, addUser.Id AS AddUserId, addUser.Username AS AddUserUsername, modUser.Id AS ModUserId, modUser.Username AS ModUserUsername FROM Asset asset LEFT JOIN AssetBankUser addUser ON addUser.Id = asset.AddedByUserId LEFT JOIN AssetBankUser modUser ON modUser.Id = asset.LastModifiedByUserId LEFT JOIN CM_Category cat ON cat.ExtensionAssetId=asset.Id WHERE " + SQLGenerator.getInstance().getNullCheckStatement("CurrentVersionId") + " AND " + "FileSizeInBytes > 0 " + "ORDER BY FileSizeInBytes, OriginalFilename";
/*     */ 
/* 576 */       psql = con.prepareStatement(sSQL);
/* 577 */       rs = psql.executeQuery();
/* 578 */       long lLastFileSize = -1L;
/* 579 */       Vector vecCurrentGroup = new Vector();
/* 580 */       vecResults = new Vector();
/* 581 */       String sLastFileLocation = null;
/* 582 */       int iRunningTotal = 0;
/* 583 */       boolean bAllNonDuplicates = true;
/*     */ 
/* 585 */       while (rs.next())
/*     */       {
/* 587 */         Asset asset = buildAsset(a_dbTransaction, rs);
/* 588 */         asset.setFileLocation(rs.getString("FileLocation"));
/* 589 */         asset.setNotDuplicate(rs.getBoolean("IsNotDuplicate"));
/*     */ 
/* 592 */         boolean bNewGroup = false;
/* 593 */         if (asset.getFileSizeInBytes() <= AssetBankSettings.getDuplicateAssetSizeThreshold())
/*     */         {
/* 595 */           if ((sLastFileLocation != null) && (asset.getOriginalFilename() != null))
/*     */           {
/* 597 */             String sThisLocation = asset.getOriginalFilename();
/* 598 */             if (!sThisLocation.equals(sLastFileLocation))
/*     */             {
/* 600 */               bNewGroup = true;
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 606 */         if ((asset.getFileSizeInBytes() != lLastFileSize) || (bNewGroup))
/*     */         {
/* 608 */           if ((vecCurrentGroup != null) && (vecCurrentGroup.size() > 1) && (!bAllNonDuplicates))
/*     */           {
/* 610 */             iRunningTotal += vecCurrentGroup.size();
/* 611 */             if (iRunningTotal > AssetBankSettings.getDuplicateAssetReportLimit())
/*     */             {
/*     */               break;
/*     */             }
/*     */ 
/* 616 */             vecResults.add(vecCurrentGroup);
/*     */           }
/* 618 */           vecCurrentGroup = new Vector();
/* 619 */           bAllNonDuplicates = true;
/*     */         }
/*     */ 
/* 622 */         lLastFileSize = asset.getFileSizeInBytes();
/* 623 */         sLastFileLocation = asset.getOriginalFilename();
/* 624 */         bAllNonDuplicates &= asset.isNotDuplicate();
/* 625 */         vecCurrentGroup.add(asset);
/*     */       }
/*     */ 
/* 628 */       if ((vecCurrentGroup != null) && (vecCurrentGroup.size() > 1))
/*     */       {
/* 630 */         vecResults.add(vecCurrentGroup);
/*     */       }
/*     */ 
/* 633 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 638 */       throw new Bn2Exception("AssetReportManager.getPotentiallyDuplicateFiles: Exception occurred: " + e, e);
/*     */     }
/*     */ 
/* 641 */     return vecResults;
/*     */   }
/*     */ 
/*     */   private Asset buildAsset(DBTransaction a_dbTransaction, ResultSet rs)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/* 648 */     Asset asset = new Asset();
/* 649 */     asset.setId(rs.getLong("AssetId"));
/* 650 */     asset.setDateAdded(rs.getTimestamp("DateAdded"));
/* 651 */     asset.setDateLastModified(rs.getTimestamp("DateLastModified"));
/* 652 */     asset.setFileSizeInBytes(rs.getLong("FileSizeInBytes"));
/* 653 */     asset.setOriginalFilename(rs.getString("OriginalFilename"));
/* 654 */     asset.getExtendsCategory().setId(rs.getLong("ExtensionAssetId"));
/*     */ 
/* 656 */     String sThumbnailPath = AssetUtil.getThumbnailFileLocation(a_dbTransaction, this.m_assetManager, rs.getString("ThumbnailFileLocation"), rs.getString("OriginalFilename"));
/*     */ 
/* 659 */     asset.setThumbnailImageFile(new ImageFile(sThumbnailPath));
/*     */ 
/* 661 */     ABUser addUser = new ABUser();
/* 662 */     addUser.setId(rs.getLong("AddUserId"));
/* 663 */     addUser.setUsername(rs.getString("AddUserUsername"));
/* 664 */     asset.setAddedByUser(addUser);
/*     */ 
/* 666 */     ABUser modUser = new ABUser();
/* 667 */     modUser.setId(rs.getLong("ModUserId"));
/* 668 */     modUser.setUsername(rs.getString("ModUserUsername"));
/* 669 */     asset.setLastModifiedByUser(modUser);
/*     */ 
/* 671 */     return asset;
/*     */   }
/*     */ 
/*     */   public String createAssetsByDateReportFile(Vector<Asset> a_vData, Date a_dtStartDate, Date a_dtEndDate, int a_iDateType, int a_iFileSizeOperator, int a_lFileSize, int a_lFileSizeMultiplier)
/*     */     throws Bn2Exception
/*     */   {
/* 690 */     String sFilename = this.m_fileStoreManager.getUniqueFilepath("asset_report.xls", StoredFileType.TEMP);
/*     */ 
/* 693 */     Writer writer = null;
/* 694 */     BrightDateFormat format = AssetBankSettings.getStandardDateFormat();
/*     */     try
/*     */     {
/* 698 */       writer = new BufferedWriter(new FileWriter(this.m_fileStoreManager.getAbsolutePath(sFilename)));
/*     */ 
/* 701 */       writer.append("Asset Report by Date and/or Size\n");
/*     */ 
/* 704 */       writer.append(a_vData.size() + " " + this.m_listManager.getListItem("item", LanguageConstants.k_defaultLanguage).getBody() + (a_vData.size() > 1 ? "s" : "") + " were ");
/* 705 */       if (a_iDateType == 1)
/*     */       {
/* 707 */         writer.append("uploaded");
/*     */       }
/* 709 */       else if (a_iDateType == 2)
/*     */       {
/* 711 */         writer.append("modified");
/*     */       }
/* 713 */       writer.append(" in the given period\n");
/*     */ 
/* 716 */       writer.append("Report: ");
/* 717 */       if ((a_dtStartDate == null) && (a_dtEndDate == null) && (a_lFileSize <= 0))
/*     */       {
/* 719 */         writer.append("all assets");
/*     */       }
/* 721 */       if ((a_dtStartDate != null) || (a_dtEndDate != null))
/*     */       {
/* 723 */         if (a_iDateType == 1)
/*     */         {
/* 725 */           writer.append("Added");
/*     */         }
/* 727 */         else if (a_iDateType == 2)
/*     */         {
/* 729 */           writer.append("Modified");
/*     */         }
/* 731 */         writer.append(" Date ");
/* 732 */         if (a_dtStartDate != null)
/*     */         {
/* 734 */           writer.append("from " + format.format(a_dtStartDate));
/*     */         }
/* 736 */         if (a_dtEndDate != null)
/*     */         {
/* 738 */           writer.append("to " + format.format(a_dtEndDate));
/*     */         }
/* 740 */         if (a_lFileSize > 0)
/*     */         {
/* 742 */           writer.append(", ");
/*     */         }
/*     */       }
/* 745 */       if (a_lFileSize > 0)
/*     */       {
/* 747 */         writer.append("File Size ");
/* 748 */         if (a_iFileSizeOperator == 1)
/*     */         {
/* 750 */           writer.append("less than ");
/*     */         }
/*     */         else
/*     */         {
/* 754 */           writer.append("more than ");
/*     */         }
/* 756 */         writer.append(String.valueOf(a_lFileSize));
/* 757 */         if (a_lFileSizeMultiplier == 1)
/*     */         {
/* 759 */           writer.append(" bytes");
/*     */         }
/* 761 */         if (a_lFileSizeMultiplier == 1024)
/*     */         {
/* 763 */           writer.append(" Kb");
/*     */         }
/* 765 */         if (a_lFileSizeMultiplier == 1048576)
/*     */         {
/* 767 */           writer.append(" Mb");
/*     */         }
/* 769 */         if (a_lFileSizeMultiplier == 1073741824)
/*     */         {
/* 771 */           writer.append(" Gb");
/*     */         }
/* 773 */         writer.append("\n");
/*     */       }
/*     */ 
/* 777 */       String sAssetName = StringUtils.capitalize(this.m_listManager.getListItem("item", LanguageConstants.k_defaultLanguage).getBody());
/* 778 */       writer.append(sAssetName + " ID\t");
/* 779 */       writer.append("Original Filename\t");
/*     */ 
/* 784 */       writer.append("Date Uploaded\t");
/* 785 */       writer.append("Added By\t");
/* 786 */       writer.append("Date Modified\t");
/* 787 */       writer.append("Modified By\t");
/* 788 */       writer.append("Size (Mb)\n");
/*     */ 
/* 790 */       NumberFormat sizeFormat = new DecimalFormat("###,##0.00");
/* 791 */       for (int i = 0; i < a_vData.size(); i++)
/*     */       {
/* 793 */         Asset asset = (Asset)a_vData.get(i);
/* 794 */         writer.append(asset.getId() + "\t");
/* 795 */         writer.append(asset.getOriginalFilename() + "\t");
/* 796 */         appendDate(writer, format, asset.getDateAdded());
/* 797 */         writer.append(asset.getAddedByUser().getUsername() + "\t");
/* 798 */         appendDate(writer, format, asset.getDateLastModified());
/* 799 */         writer.append(asset.getLastModifiedByUser().getUsername() + "\t");
/* 800 */         writer.append(sizeFormat.format(asset.getFileSizeInBytes() / 1048576.0D) + "\n");
/*     */       }
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 805 */       this.m_logger.error("AssetReportManager.createAssetsByDateReportFile() : IOException whilst creating report file : " + ioe);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 811 */         writer.flush();
/* 812 */         writer.close();
/*     */       }
/*     */       catch (IOException ioe2)
/*     */       {
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 820 */     return sFilename;
/*     */   }
/*     */ 
/*     */   private void appendDate(Writer a_writer, Format a_format, Date d_date)
/*     */     throws IOException
/*     */   {
/* 834 */     if (d_date != null)
/*     */     {
/* 836 */       a_writer.append(a_format.format(d_date) + "\t");
/*     */     }
/*     */     else
/*     */     {
/* 840 */       a_writer.append("\t");
/*     */     }
/*     */   }
/*     */ 
/*     */   public String createAssetsByCategoryAndAttributeReportFile(Vector<AssetByCategoryRecord> a_vData, Attribute a_attribute)
/*     */     throws Bn2Exception
/*     */   {
/* 854 */     String sFilename = this.m_fileStoreManager.getUniqueFilepath("asset_report.xls", StoredFileType.TEMP);
/*     */ 
/* 857 */     Writer writer = null;
/*     */     try
/*     */     {
/* 861 */       writer = new BufferedWriter(new FileWriter(this.m_fileStoreManager.getAbsolutePath(sFilename)));
/*     */ 
/* 863 */       writer.append("Assets By Category Report\n");
/*     */ 
/* 865 */       writer.append("This report shows the tree of descriptive categories, and the assets in each.\n");
/* 866 */       writer.append("Totals are given for the sum of assets in each category including subcategories, and also the number of assets explicitly within each category.\n");
/*     */ 
/* 868 */       if (a_attribute.getId() > 0L)
/*     */       {
/* 870 */         writer.append("Assets are shown sorted by the attribute " + a_attribute.getLabel() + ".\n");
/*     */       }
/*     */ 
/* 873 */       String sAssetName = StringUtils.capitalize(this.m_listManager.getListItem("item", LanguageConstants.k_defaultLanguage).getBody());
/*     */ 
/* 875 */       writer.append("Category name\t");
/* 876 */       writer.append(sAssetName + "s within category\t");
/* 877 */       writer.append(sAssetName + "s directly in category\t");
/* 878 */       if (a_attribute.getId() > 0L)
/*     */       {
/* 880 */         writer.append(a_attribute.getLabel() + "\t");
/*     */       }
/* 882 */       writer.append(sAssetName + " ID\t");
/* 883 */       writer.append(sAssetName + " Title\t");
/* 884 */       writer.append("Approved\n");
/*     */ 
/* 886 */       for (int i = 0; i < a_vData.size(); i++)
/*     */       {
/* 888 */         AssetByCategoryRecord record = (AssetByCategoryRecord)a_vData.get(i);
/*     */ 
/* 890 */         for (int iCat = 0; iCat < record.getCategory().getDepth(); iCat++)
/*     */         {
/* 892 */           writer.append(" ");
/*     */         }
/* 894 */         writer.append(record.getCategory().getName() + "\t");
/* 895 */         writer.append(record.getNumUnderCategory() + "\t");
/* 896 */         writer.append(record.getNumDirectInCategory() + "\t\n");
/*     */ 
/* 898 */         for (int j = 0; j < record.getAssets().size(); j++)
/*     */         {
/* 900 */           AssetRecord asset = (AssetRecord)record.getAssets().get(j);
/* 901 */           writer.append("\t\t\t");
/* 902 */           if (a_attribute.getId() > 0L)
/*     */           {
/* 904 */             writer.append(asset.getAttributeValue() + "\t");
/*     */           }
/* 906 */           writer.append(asset.getId() + "\t");
/* 907 */           writer.append(asset.getName() + "\t");
/* 908 */           writer.append((asset.getApproved() ? "yes" : "no") + "\n");
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 914 */       this.m_logger.error("AssetReportManager.createAssetsByCategoryAndAttributeReportFile() : IOException whilst creating report file : " + ioe);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 921 */         writer.flush();
/* 922 */         writer.close();
/*     */       }
/*     */       catch (IOException ioe2)
/*     */       {
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 930 */     return sFilename;
/*     */   }
/*     */ 
/*     */   public String createAssetsByKeywordReportFile(Vector<KeywordRecord> a_vData)
/*     */     throws Bn2Exception
/*     */   {
/* 942 */     String sFilename = this.m_fileStoreManager.getUniqueFilepath("asset_report.xls", StoredFileType.TEMP);
/*     */ 
/* 945 */     Writer writer = null;
/*     */     try
/*     */     {
/* 949 */       writer = new BufferedWriter(new FileWriter(this.m_fileStoreManager.getAbsolutePath(sFilename)));
/*     */ 
/* 951 */       writer.append("Asset Report\n");
/*     */ 
/* 955 */       for (int i = 0; i < a_vData.size(); i++)
/*     */       {
/* 957 */         writer.append("\n");
/*     */       }
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 962 */       this.m_logger.error("AssetReportManager.createAssetsByKeywordReportFile() : IOException whilst creating report file : " + ioe);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 968 */         writer.flush();
/* 969 */         writer.close();
/*     */       }
/*     */       catch (IOException ioe2)
/*     */       {
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 977 */     return sFilename;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.report.service.AssetReportManager
 * JD-Core Version:    0.6.0
 */