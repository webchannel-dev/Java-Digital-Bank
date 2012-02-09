/*     */ package com.bright.assetbank.batch.update.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.batch.service.BatchQueueManager;
/*     */ import com.bright.assetbank.batch.update.bean.MetadataImportInfo;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.taxonomy.service.TaxonomyManager;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.common.bean.BrightDate;
/*     */ import com.bright.framework.common.bean.BrightDateTime;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.queue.bean.QueuedItem;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class MetadataImportManager extends BatchQueueManager
/*     */   implements AssetBankConstants
/*     */ {
/*  67 */   private FileStoreManager m_fileStoreManager = null;
/*  68 */   private AttributeManager m_attributeManager = null;
/*  69 */   private MultiLanguageSearchManager m_searchManager = null;
/*  70 */   private IAssetManager m_assetManager = null;
/*  71 */   protected CategoryManager m_categoryManager = null;
/*  72 */   protected TaxonomyManager m_taxonomyManager = null;
/*  73 */   private DBTransactionManager m_transactionManager = null;
/*     */ 
/*     */   public void processQueueItem(QueuedItem a_queuedItem)
/*     */     throws Bn2Exception
/*     */   {
/*  91 */     MetadataImportInfo importInfo = null;
/*     */     try
/*     */     {
/*  96 */       importInfo = (MetadataImportInfo)a_queuedItem;
/*     */ 
/*  99 */       processImport(importInfo, 
/* 100 */         false);
/*     */ 
/* 103 */       this.m_fileStoreManager.deleteFile(importInfo.getFileUrl());
/*     */ 
/* 105 */       addMessage(importInfo.getUser().getId(), "Finished metadata import");
/*     */     }
/*     */     catch (Bn2Exception bn2e)
/*     */     {
/* 109 */       this.m_logger.error("MetadataImportManager.processQueueItem - exception:", bn2e);
/* 110 */       throw bn2e;
/*     */     }
/*     */     finally
/*     */     {
/* 114 */       if (importInfo != null)
/*     */       {
/* 116 */         endBatchProcess(importInfo.getUser().getId());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean processImport(MetadataImportInfo a_importInfo, boolean a_bCheckOnly)
/*     */     throws Bn2Exception
/*     */   {
/* 138 */     boolean bFatalError = false;
/* 139 */     DateFormat dfDateFormat = AssetBankSettings.getStandardDateFormat();
                Attribute att;
/*     */     try
/*     */     {
/* 144 */       String sFullFilePath = this.m_fileStoreManager.getAbsolutePath(a_importInfo.getFileUrl());
/*     */ 
/* 147 */       BufferedReader inFile = new BufferedReader(new FileReader(sFullFilePath));
/* 148 */       String sLine = null;
/* 149 */       String[] asFields = (String[])null;
/* 150 */       String[] asFieldNames = (String[])null;
/*     */ 
/* 152 */       int iFilenameColumn = -1;
/* 153 */       int iCategoriesColumn = -1;
/* 154 */       int iKeywordsColumn = -1;
/* 155 */       int iLineNum = 0;
/* 156 */       HashMap hmProcessFilenames = new HashMap();
/*     */ 
/* 158 */       while (((sLine = inFile.readLine()) != null) && (!
/* 159 */         bFatalError))
/*     */       {
/* 161 */         iLineNum++;
/*     */ 
/* 164 */         asFields = sLine.split("\t");
/*     */ 
/* 166 */         if (iLineNum == 1)
/*     */         {
/* 169 */           asFieldNames = asFields;
/*     */ 
/* 172 */           for (int i = 0; i < asFieldNames.length; i++)
/*     */           {
/* 175 */             if (asFieldNames[i].equals("Filename"))
/*     */             {
/* 178 */               iFilenameColumn = i;
/*     */             }
/* 181 */             else if (asFieldNames[i].equals("Categories"))
/*     */             {
/* 183 */               iCategoriesColumn = i;
/*     */             }
/* 185 */             else if ((AssetBankSettings.getEnableKeywordTaxonomy()) && 
/* 186 */               (asFieldNames[i].equals("Keywords")))
/*     */             {
/* 188 */               iKeywordsColumn = i;
/*     */             }
/*     */             else
/*     */             {
/* 194 */               Attribute attPos = getAttribute(asFieldNames[i]);
/*     */ 
/* 197 */               if (attPos != null) {
/*     */                 continue;
/*     */               }
/* 200 */               addMessage(a_importInfo.getUser().getId(), 
/* 201 */                 "Header for column " + (i + 1) + ": there is no attribute with label " + asFieldNames[i]);
/* 202 */               bFatalError = true;
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 208 */           if (iFilenameColumn >= 0)
/*     */             continue;
/* 210 */           addMessage(a_importInfo.getUser().getId(), 
/* 211 */             "Missing a column for filename. Header for column should be: Filename");
/* 212 */           bFatalError = true;
/*     */         }
/* 219 */         else if (asFields.length != asFieldNames.length)
/*     */         {
/* 222 */           addMessage(a_importInfo.getUser().getId(), 
/* 223 */             "Line " + iLineNum + ": wrong number of columns (skipping this line)");
/*     */         }
/*     */         else
/*     */         {
/* 229 */           String sFilename = asFields[iFilenameColumn];
/*     */ 
/* 232 */           sFilename = FileUtil.getSafeFilename(sFilename, true);
/*     */ 
/* 234 */           boolean bFileError = false;
/* 235 */           Asset asset = null;
/*     */ 
/* 238 */           if (hmProcessFilenames.containsKey(sFilename))
/*     */           {
/* 240 */             Integer intLastLine = (Integer)hmProcessFilenames.get(sFilename);
/*     */ 
/* 242 */             addMessage(a_importInfo.getUser().getId(), 
/* 243 */               "Line " + iLineNum + ": the filename specified is the same as the filename specified on line " + intLastLine.toString() + " (skipping this line)");
/*     */ 
/* 245 */             bFileError = true;
/*     */           }
/*     */           else
/*     */           {
/* 250 */             hmProcessFilenames.put(sFilename, new Integer(iLineNum));
/*     */ 
/* 253 */             SearchCriteria searchCriteria = new SearchCriteria();
/* 254 */             searchCriteria.setFilename(sFilename);
/* 255 */            // SearchResults searchResults = this.m_searchManager.search(searchCriteria, 
/* 256 */            //   -1, 
/* 257 */            //   -1, 
/* 258 */            //   "en");
/*     */             SearchResults searchResults = this.m_searchManager.search(searchCriteria,"en");
/* 261 */             int iNumMatches = 0;
/* 262 */             int iResIndex = 0;
/* 263 */             while (iResIndex < searchResults.getSearchResults().size())
/*     */             {
/* 267 */               LightweightAsset tempAsset = (LightweightAsset)searchResults.getSearchResults().get(iResIndex);
/*     */ 
/* 270 */               Asset candidateAsset = this.m_assetManager.getAsset(null, 
/* 271 */                 tempAsset.getId(), 
/* 272 */                 null, true, false);
/*     */ 
/* 275 */               if (candidateAsset.getFileName().equalsIgnoreCase(sFilename))
/*     */               {
/* 277 */                 asset = candidateAsset;
/* 278 */                 iNumMatches++;
/*     */               }
/* 264 */               iResIndex++;
/*     */             }
/*     */ 
/* 282 */             if (iNumMatches == 0)
/*     */             {
/* 284 */               addMessage(a_importInfo.getUser().getId(), 
/* 285 */                 "Line " + iLineNum + ": there is no asset visible to you with filename " + sFilename + " (skipping this line)");
/* 286 */               bFileError = true;
/*     */             }
/*     */ 
/* 289 */             if (iNumMatches > 1)
/*     */             {
/* 291 */               addMessage(a_importInfo.getUser().getId(), 
/* 292 */                 "Line " + iLineNum + ": there are multiple assets with filename " + sFilename + " (skipping this line)");
/* 293 */               bFileError = true;
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 298 */           if (bFileError) {
/*     */             continue;
/*     */           }
/* 301 */           boolean bError = false;
/*     */ 
/* 304 */           String sCategoryIds = null;
/* 305 */           if (iCategoriesColumn >= 0)
/*     */           {
/* 307 */             sCategoryIds = getCategoryIds(a_importInfo, 
/* 308 */               asFields[iCategoriesColumn]);
/*     */ 
/* 310 */             if (sCategoryIds == null)
/*     */             {
/* 312 */               bError = true;
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 317 */           String sFieldName = "";
/*     */           try
/*     */           {
/* 320 */             for (int i = 0; i < asFields.length; i++)
/*     */             {
/* 322 */               if ((i == iFilenameColumn) || 
/* 323 */                 (i == iCategoriesColumn) || 
/* 324 */                 (i == iKeywordsColumn)) {
/*     */                 continue;
/*     */               }
/* 327 */               sFieldName = asFieldNames[i];
/* 328 */               Attribute attPos = getAttribute(asFieldNames[i]);
/*     */ 
/* 331 */               if ((attPos.getFieldName() != null) && 
/* 332 */                 (attPos.getFieldName().equals("dateAdded")) && 
/* 333 */                 (asFields[i] != null) && (asFields[i].length() > 0))
/*     */               {
/* 335 */                 Date dtDateAdded = dfDateFormat.parse(asFields[i]);
/*     */ 
/* 337 */                 if (dtDateAdded == null)
/*     */                   continue;
/* 339 */                 asset.setDateAdded(dtDateAdded);
/*     */               }
/* 342 */               else if ((attPos.getFieldName() != null) && 
/* 343 */                 (attPos.getFieldName().equals("dateLastModified")) && 
/* 344 */                 (asFields[i] != null) && (asFields[i].length() > 0))
/*     */               {
/* 346 */                 Date dtDateLastModified = dfDateFormat.parse(asFields[i]);
/* 347 */                 asset.setDateLastModified(dtDateLastModified);
/*     */               }
/*     */               else
/*     */               {
/* 354 */                 AttributeValue atVal = asset.getAttributeValue(attPos.getId());
/* 355 */                 att = atVal.getAttribute();
/*     */ 
/* 357 */                 if (atVal == null) {
/*     */                   continue;
/*     */                 }
/* 360 */                 if (((att.getIsDatepicker()) && (!BrightDate.validateFormat(asFields[i]))) || ((att.getIsDateTime()) && (!BrightDateTime.validateFormat(asFields[i]))))
/*     */                 {
/* 362 */                   addMessage(a_importInfo.getUser().getId(), 
/* 363 */                     "The column " + sFieldName + " contains a date that is not in the required date format (" + AssetBankSettings.getStandardDateFormat() + ")");
/* 364 */                   bError = true;
/*     */                 }
/*     */ 
/* 368 */                 if ((att.getIsDropdownList()) || (att.getIsCheckList()) || (att.getIsOptionList()))
/*     */                 {
/* 370 */                   if (asFields[i].trim().length() > 0)
/*     */                   {
/* 373 */                     AttributeValue av = this.m_attributeManager.getAttributeValueId(null, 
/* 374 */                       att.getId(), 
/* 375 */                       asFields[i], 
/* 376 */                       !a_bCheckOnly);
/*     */ 
/* 378 */                     if (av == null)
/*     */                       continue;
/* 380 */                     atVal.setId(av.getId());
/*     */                   }
/*     */                   else
/*     */                   {
/* 385 */                     atVal.setId(0L);
/*     */                   }
/*     */                 }
/* 388 */                 else if (att.getIsKeywordPicker())
/*     */                 {
/* 390 */                   atVal.setKeywordCategories(this.m_taxonomyManager.getKeywordsForAttributeValue(null, asFields[i], att, LanguageConstants.k_defaultLanguage));
/*     */                 }
/*     */                 else
/*     */                 {
/* 395 */                   atVal.setValue(asFields[i]);
/*     */                 }
/*     */ 
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/*     */           catch (ParseException pe)
/*     */           {
/* 405 */             addMessage(a_importInfo.getUser().getId(), 
/* 406 */               "The column " + sFieldName + " contains a date that is not in the required date format (" + AssetBankSettings.getStandardDateFormat() + ")");
/* 407 */             bError = true;
/*     */           }
/*     */ 
/* 411 */           if (bError)
/*     */           {
/* 413 */             addMessage(a_importInfo.getUser().getId(), 
/* 414 */               "Line " + iLineNum + ": Skipping this line because of the above errors.");
/*     */           }
/*     */           else {
/* 417 */             if (a_bCheckOnly) {
/*     */               continue;
/*     */             }
/* 420 */             DBTransaction dbTransaction = null;
/*     */             try
/*     */             {
/* 425 */               dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */ 
/* 428 */               this.m_assetManager.saveAsset(dbTransaction, 
/* 429 */                 asset, 
/* 430 */                 null, 
/* 431 */                 a_importInfo.getUser().getId(), 
/* 432 */                 null, 
/* 433 */                 null, 
/* 434 */                 false, 
/* 435 */                 0);
/*     */ 
/* 438 */               if (sCategoryIds != null)
/*     */               {
/* 440 */                 
                        //this.m_categoryManager.updateItemCategories(dbTransaction,asset,1L,sCategoryIds);
/*     */               }
/*     */ 
/* 447 */               asset = this.m_assetManager.getAsset(dbTransaction, 
/* 448 */                 asset.getId(), 
/* 449 */                 null, true, true);
/*     */             }
/*     */             catch (Bn2Exception be)
/*     */             {
/* 453 */               this.m_logger.error("Exception in DataImportManager:" + be.getMessage());
/*     */ 
/* 455 */               if (dbTransaction != null)
/*     */               {
/*     */                 try
/*     */                 {
/* 459 */                   dbTransaction.rollback();
/*     */                 }
/*     */                 catch (Exception e2)
/*     */                 {
/* 463 */                   this.m_logger.error("Exception rolling back transaction in DataImportManager:" + e2.getMessage());
/*     */                 }
/*     */ 
/*     */               }
/*     */ 
/* 468 */               throw be;
/*     */             }
/*     */             finally
/*     */             {
/* 472 */               if (dbTransaction != null)
/*     */               {
/*     */                 try
/*     */                 {
/* 476 */                   dbTransaction.commit();
/*     */                 }
/*     */                 catch (Exception e2)
/*     */                 {
/* 480 */                   this.m_logger.error("Exception committing transaction in DataImportManager:" + e2.getMessage());
/*     */                 }
/*     */ 
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/* 487 */             this.m_searchManager.indexDocument(asset, true);
/*     */ 
/* 489 */             addMessage(a_importInfo.getUser().getId(), 
/* 490 */               "Line " + iLineNum + ": Successfully update asset with id " + asset.getId());
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 504 */       inFile.close();
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 508 */       this.m_logger.error("IOException in DataImportManager.importFromFile: " + ioe.getMessage());
/* 509 */       throw new Bn2Exception("IOException in DataImportManager.importFromFile: " + ioe.getMessage());
/*     */     }
/*     */ 
/* 513 */     return bFatalError;
/*     */   }
/*     */ 
/*     */   private Attribute getAttribute(String a_sLabel)
/*     */     throws Bn2Exception
/*     */   {
/* 531 */     Attribute attPos = null;
/* 532 */     Attribute attPosRetVal = null;
/* 533 */     Vector vecAttPosList = this.m_attributeManager.getAttributePositionList();
/*     */ 
/* 536 */     for (int i = 0; i < vecAttPosList.size(); i++)
/*     */     {
/* 538 */       attPos = (Attribute)vecAttPosList.get(i);
/*     */ 
/* 540 */       if (!attPos.getLabel().equals(a_sLabel))
/*     */         continue;
/* 542 */       attPosRetVal = attPos;
/* 543 */       break;
/*     */     }
/*     */ 
/* 547 */     return attPosRetVal;
/*     */   }
/*     */ 
/*     */   private String getCategoryIds(MetadataImportInfo a_importInfo, String a_sCateogryFieldValue)
/*     */     throws Bn2Exception
/*     */   {
/* 566 */     boolean bError = false;
/*     */ 
/* 568 */     if ((a_sCateogryFieldValue == null) || (a_sCateogryFieldValue.length() == 0))
/*     */     {
/* 570 */       addMessage(a_importInfo.getUser().getId(), 
/* 571 */         "The category field is empty");
/* 572 */       return null;
/*     */     }
/*     */ 
/* 575 */     String sCatIds = null;
/*     */ 
/* 578 */     String[] asFullCatNames = a_sCateogryFieldValue.split(",");
/*     */ 
/* 581 */     for (int i = 0; (asFullCatNames != null) && (i < asFullCatNames.length); i++)
/*     */     {
/* 584 */       Category cat = null;
/* 585 */       if (asFullCatNames[i] != null)
/*     */       {
/* 587 */         cat = this.m_categoryManager.getCategory(null, 
/* 588 */           1L, 
/* 589 */           asFullCatNames[i].trim(), 
/* 590 */           true);
/*     */       }
/*     */ 
/* 594 */       if (cat == null)
/*     */       {
/* 596 */         addMessage(a_importInfo.getUser().getId(), 
/* 597 */           "The name " + asFullCatNames[i] + " does not map to a valid category. Check the help page for how to name and delimit categories.");
/* 598 */         bError = true;
/*     */       }
/*     */       else
/*     */       {
/* 602 */         if (sCatIds == null)
/*     */         {
/* 604 */           sCatIds = "";
/*     */         }
/*     */         else
/*     */         {
/* 608 */           sCatIds = sCatIds + ",";
/*     */         }
/*     */ 
/* 611 */         sCatIds = sCatIds + cat.getId();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 616 */     if (bError)
/*     */     {
/* 618 */       sCatIds = null;
/*     */     }
/*     */ 
/* 621 */     return sCatIds;
/*     */   }
/*     */ 
/*     */   public int queueImport(MetadataImportInfo a_importInfo)
/*     */   {
/* 639 */     startBatchProcess(a_importInfo.getUser().getId());
/*     */ 
/* 642 */     return queueItem(a_importInfo);
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_sFileStoreManager)
/*     */   {
/* 647 */     this.m_fileStoreManager = a_sFileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_sAttributeManager) {
/* 651 */     this.m_attributeManager = a_sAttributeManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager) {
/* 655 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 660 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_categoryManager)
/*     */   {
/* 665 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public void setTaxonomyManager(TaxonomyManager a_taxonomyManager)
/*     */   {
/* 670 */     this.m_taxonomyManager = a_taxonomyManager;
/*     */   }
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_transactionManager)
/*     */   {
/* 675 */     this.m_transactionManager = a_transactionManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.service.MetadataImportManager
 * JD-Core Version:    0.6.0
 */