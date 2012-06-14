/*     */ package com.bright.assetbank.custom.indesign.asset.abplugin;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.custom.indesign.asset.bean.FileInfo;
/*     */ import com.bright.assetbank.custom.indesign.asset.bean.InDesignAsset;
/*     */ import com.bright.assetbank.custom.indesign.asset.bean.InDesignAssetWithNewFile;
/*     */ import com.bright.assetbank.custom.indesign.asset.bean.InDesignAssetWithRelated;
/*     */ import com.bright.assetbank.custom.indesign.asset.bean.InDesignDocument;
/*     */ import com.bright.assetbank.custom.indesign.entity.abplugin.InDesignAssetEntityDBUtil;
/*     */ import com.bright.assetbank.custom.indesign.entity.abplugin.InDesignAssetEntityService;
/*     */ import com.bright.assetbank.custom.indesign.entity.bean.InDesignAssetEntity;
/*     */ import com.bright.assetbank.custom.indesign.pdf.service.InDesignPDFJobService;
/*     */ import com.bright.assetbank.custom.indesign.search.bean.InDesignSearchCriteria;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.exception.RowNotFoundException;
/*     */ import com.bright.framework.database.exception.SQLStatementException;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.database.util.DBUtil;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.annotation.Resource;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class InDesignAssetService
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private InDesignAssetEntityService m_entityService;
/*     */ 
/*     */   @Resource
/*     */   private InDesignPDFJobService m_pdfJobService;
/*     */ 
/*     */   @Resource
/*     */   private InDesignDocumentDAO m_documentDAO;
/*     */ 
/*     */   @Resource
/*     */   private FileStoreManager m_fileStoreManager;
/*     */ 
/*     */   @Resource
/*     */   private MultiLanguageSearchManager m_searchManager;
/*     */ 
/*     */   public List<LightweightAsset> getTemplates(long a_lAssetEntityId, String a_sLanguageCode)
/*     */     throws Bn2Exception
/*     */   {
/*  77 */     SearchCriteria searchCriteria = new SearchCriteria();
/*     */ 
/*  79 */     searchCriteria.addAssetEntityIdToInclude(a_lAssetEntityId);
/*     */ 
/*  81 */     InDesignSearchCriteria inDesignSearchCriteria = new InDesignSearchCriteria();
/*  82 */     inDesignSearchCriteria.setIsTemplate(Boolean.TRUE);
/*  83 */     searchCriteria.setExternalFilterCriteria(inDesignSearchCriteria.toMap());
/*     */ 
/*  85 */     return getSearchManager().search(searchCriteria, a_sLanguageCode).getSearchResults();
/*     */   }
/*     */ 
/*     */   public InDesignAsset get(DBTransaction a_transaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/*  92 */     InDesignAsset out = new InDesignAsset();
/*     */ 
/*  94 */     if (maybePopulate(a_transaction, a_lId, out))
/*     */     {
/*  96 */       return out;
/*     */     }
/*     */ 
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */   public void populate(DBTransaction a_transaction, long a_lId, InDesignAsset a_out)
/*     */     throws Bn2Exception
/*     */   {
/* 110 */     boolean found = maybePopulate(a_transaction, a_lId, a_out);
/* 111 */     if (!found)
/*     */     {
/* 113 */       throw new RowNotFoundException("InDesignAsset", a_lId);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean maybePopulate(DBTransaction a_transaction, long a_lId, InDesignAsset a_out)
/*     */     throws Bn2Exception
/*     */   {
/* 124 */     String sSQL = "SELECT ida.IsTemplate, ida.TemplateAssetId, ida.InDesignDocumentId, ida.PDFStatusId FROM InDesignAsset ida WHERE ida.AssetId=?";
/*     */     boolean found;
/*     */     try
/*     */     {
/* 130 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 132 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 133 */       psql.setLong(1, a_lId);
/*     */ 
/* 135 */       ResultSet rs = psql.executeQuery();
/*     */       //boolean found;
/* 137 */       if (rs.next())
/*     */       {
/* 139 */         InDesignAssetDBUtil.populateInDesignAssetFromRS(a_out, rs);
/* 140 */         found = true;
/*     */       }
/*     */       else
/*     */       {
/* 144 */         found = false;
/*     */       }
/*     */ 
/* 147 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 151 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/* 153 */     return found;
/*     */   }
/*     */ 
/*     */   public InDesignAssetWithRelated getWithRelated(DBTransaction a_transaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/* 159 */     String sSQL = null;
/*     */     try
/*     */     {
/* 163 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 165 */       sSQL = "SELECT ida.IsTemplate, ida.TemplateAssetId, ida.InDesignDocumentId, ida.PDFStatusId, idae.InDesignAssetEntityTypeId, idae.AssetEntityId, idd.Id AS iddId, idd.InDesignPDFQualityId, idd.FileLocation, idd.OriginalFilename, idd.FileSizeInBytes, idd.NumSymbols, idps.Name AS PDFStatus FROM InDesignAsset ida JOIN Asset a on A.Id = ida.AssetId LEFT OUTER JOIN InDesignAssetEntity idae ON idae.AssetEntityId = a.AssetEntityId LEFT OUTER JOIN InDesignDocument idd ON idd.Id = ida.InDesignDocumentId LEFT OUTER JOIN InDesignPDFStatus idps ON idps.Id = ida.PDFStatusId WHERE ida.AssetId=?";
/*     */ 
/* 178 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 179 */       psql.setLong(1, a_lId);
/*     */ 
/* 181 */       ResultSet rs = psql.executeQuery();
/*     */       InDesignAssetWithRelated result;
/* 184 */       if (rs.next())
/*     */       {
/* 186 */         InDesignAssetEntity entity = InDesignAssetEntityDBUtil.createInDesignAssetEntityFromRSOuterJoin(rs);
/* 187 */         if (entity == null)
/*     */         {
/* 189 */           throw new Bn2Exception("All InDesign Assets must be related to an AssetEntity. Asset " + a_lId + " is not.");
/*     */         }
/*     */ 
/* 192 */         InDesignDocument document = InDesignDocumentDBUtil.createInDesignDocumentFromRSOuterJoin(rs);
/* 193 */         String pdfStatus = rs.getString("PDFStatus");
/*     */ 
/* 195 */         result = new InDesignAssetWithRelated(entity, document, pdfStatus);
/*     */ 
/* 197 */         InDesignAssetDBUtil.populateInDesignAssetFromRS(result, rs);
/*     */       }
/*     */       else
/*     */       {
/* 201 */         result = null;
/*     */       }
/*     */ 
/* 204 */       psql.close();
/*     */ 
/* 206 */       return result;
/*     */     }
/*     */     catch (SQLException e) {
/*     */     
/* 210 */     throw new SQLStatementException(sSQL, e);}
/*     */   }
/*     */ 
/*     */   public void putDocumentFile(DBTransaction a_transaction, long a_lAssetId, FileInfo a_newFile)
/*     */     throws Bn2Exception
/*     */   {
/* 230 */     InDesignAssetEntity inDAssetEntity = getEntityService().getInDesignEntityForAsset(a_transaction, a_lAssetId);
/* 231 */     if ((inDAssetEntity == null) || (!inDAssetEntity.isDocument()))
/*     */     {
/* 233 */       throw new Bn2Exception("Asset " + a_lAssetId + " is not an InDesign Document asset");
/*     */     }
/*     */ 
/* 236 */     InDesignAssetWithNewFile a_inDAsset = new InDesignAssetWithNewFile();
/* 237 */     populate(a_transaction, a_lAssetId, a_inDAsset);
/*     */ 
/* 239 */     a_inDAsset.setNewFile(a_newFile);
/*     */ 
/* 241 */     addOrUpdateWithNewFile(a_transaction, a_lAssetId, a_inDAsset);
/*     */   }
/*     */ 
/*     */   public void addOrUpdateWithNewFile(DBTransaction a_transaction, long a_lAssetId, InDesignAssetWithNewFile a_inDAsset)
/*     */     throws Bn2Exception
/*     */   {
/* 254 */     boolean pdfNeedsGeneration = false;
/* 255 */     String fileLocationToDelete = null;
/*     */ 
/* 257 */     FileInfo newFile = a_inDAsset.getNewFile();
/* 258 */     if (newFile != null)
/*     */     {
/* 261 */       long oldDocumentId = a_inDAsset.getInDesignDocumentId();
/*     */       InDesignDocument indd;
/* 262 */       if (oldDocumentId > 0L)
/*     */       {
/* 264 */         indd = getDocumentDAO().get(a_transaction, oldDocumentId);
/* 265 */         fileLocationToDelete = indd.getFileLocation();
/*     */       }
/*     */       else
/*     */       {
/* 269 */         indd = new InDesignDocument();
/*     */       }
/*     */       //int pdfQualityId;
/*     */       int pdfQualityId;
/* 273 */       if (a_inDAsset.getPdfQualityId() > 0)
/*     */       {
/* 276 */         pdfQualityId = a_inDAsset.getPdfQualityId();
/*     */       }
/*     */       else
/*     */       {
/*     */         //int pdfQualityId;
/* 284 */         if (oldDocumentId > 0L)
/*     */         {
/* 293 */           pdfQualityId = indd.getInDesignPDFQualityId();
/*     */         }
/*     */         else
/*     */         {
/*     */           //int pdfQualityId;
/* 295 */           if (a_inDAsset.getTemplateAssetId() > 0L)
/*     */           {
/* 298 */             InDesignAssetWithRelated templateInDAsset = getWithRelated(a_transaction, a_inDAsset.getTemplateAssetId());
/* 299 */             pdfQualityId = templateInDAsset.getDocument().getInDesignPDFQualityId();
/*     */           }
/*     */           else
/*     */           {
/* 305 */             pdfQualityId = 1;
/*     */           }
/*     */         }
/*     */       }
/* 308 */       indd.setInDesignPDFQualityId(pdfQualityId);
/*     */ 
/* 310 */       indd.populateFileInfoFrom(newFile);
/*     */ 
/* 312 */       if (oldDocumentId > 0L)
/*     */       {
/* 314 */         getDocumentDAO().update(a_transaction, indd);
/*     */       }
/*     */       else
/*     */       {
/* 318 */         getDocumentDAO().add(a_transaction, indd);
/*     */       }
/*     */ 
/* 321 */       a_inDAsset.setInDesignDocumentId(indd.getId());
/* 322 */       a_inDAsset.setPDFStatusId(2L);
/* 323 */       pdfNeedsGeneration = true;
/*     */     }
/* 329 */     else if ((a_inDAsset.getPdfQualityId() > 0) && (a_inDAsset.getInDesignDocumentId() > 0L))
/*     */     {
/* 332 */       InDesignDocument indd = getDocumentDAO().get(a_transaction, a_inDAsset.getInDesignDocumentId());
/*     */ 
/* 335 */       if (a_inDAsset.getPdfQualityId() != indd.getInDesignPDFQualityId())
/*     */       {
/* 337 */         indd.setInDesignPDFQualityId(a_inDAsset.getPdfQualityId());
/* 338 */         getDocumentDAO().update(a_transaction, indd);
/* 339 */         pdfNeedsGeneration = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 344 */     addOrUpdate(a_transaction, a_lAssetId, a_inDAsset);
/*     */ 
/* 346 */     if (pdfNeedsGeneration)
/*     */     {
/* 351 */       getPDFJobService().enqueue(a_transaction, a_lAssetId);
/*     */     }
/*     */ 
/* 354 */     if (fileLocationToDelete != null)
/*     */     {
/* 356 */       deleteDocumentFile(a_transaction, fileLocationToDelete);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addOrUpdate(DBTransaction a_transaction, long a_lId, InDesignAsset a_extensionData)
/*     */     throws Bn2Exception
/*     */   {
/* 370 */     String sSQL = null;
/*     */     try
/*     */     {
/* 374 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 376 */       if (!SQLGenerator.getInstance().getSupportsOnDuplicateKeyUpdate())
/*     */       {
/* 378 */         sSQL = "UPDATE InDesignAsset SET IsTemplate = ?, TemplateAssetId = ?, InDesignDocumentId = ?, PDFStatusId = ? WHERE AssetId = ?";
/*     */ 
/* 383 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 384 */         prepareInDesignAssetUpdate(psql, 1, a_lId, a_extensionData);
/*     */ 
/* 386 */         int rowCount = psql.executeUpdate();
/* 387 */         psql.close();
/*     */ 
/* 389 */         if (rowCount == 0)
/*     */         {
/* 392 */           sSQL = "INSERT INTO InDesignAsset (AssetId, IsTemplate, TemplateAssetId, InDesignDocumentId, PDFStatusId) VALUES (?, ?, ?, ?, ?)";
/*     */ 
/* 395 */           psql = con.prepareStatement(sSQL);
/* 396 */           prepareInDesignAssetInsert(psql, 1, a_lId, a_extensionData);
/* 397 */           psql.executeUpdate();
/* 398 */           psql.close();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 403 */         sSQL = "INSERT INTO InDesignAsset (AssetId, IsTemplate, TemplateAssetId, InDesignDocumentId, PDFStatusId) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE IsTemplate = VALUES(IsTemplate), TemplateAssetId = VALUES(TemplateAssetId), InDesignDocumentId = VALUES(InDesignDocumentId), PDFStatusId = VALUES(PDFStatusId)";
/*     */ 
/* 412 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 413 */         prepareInDesignAssetInsert(psql, 1, a_lId, a_extensionData);
/*     */ 
/* 415 */         psql.executeUpdate();
/* 416 */         psql.close();
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 421 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updatePDFStatus(DBTransaction a_transaction, long a_assetId, int a_newPDFStatusId)
/*     */     throws Bn2Exception
/*     */   {
/* 429 */     String sSQL = "UPDATE InDesignAsset SET PDFStatusId = ? WHERE AssetId = ?";
/*     */ 
/* 432 */     Connection con = a_transaction.getConnection();
/*     */     try
/*     */     {
/* 435 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 436 */       psql.setInt(1, a_newPDFStatusId);
/* 437 */       psql.setLong(2, a_assetId);
/* 438 */       int rowCount = psql.executeUpdate();
/* 439 */       psql.close();
/*     */ 
/* 441 */       if (rowCount != 1)
/*     */       {
/* 443 */         throw new Bn2Exception("Could not update the PDF status of InDesignAsset " + a_assetId + " to " + a_newPDFStatusId + ". Row count was " + rowCount + ", expected it to be 1");
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 449 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static int prepareInDesignAssetUpdate(PreparedStatement a_psql, int a_param, long a_lId, InDesignAsset a_inda)
/*     */     throws SQLException
/*     */   {
/* 456 */     a_param = prepareInDesignAssetNonPK(a_psql, a_param, a_inda);
/* 457 */     a_param = prepareInDesignAssetPK(a_psql, a_param, a_lId);
/* 458 */     return a_param;
/*     */   }
/*     */ 
/*     */   private static int prepareInDesignAssetInsert(PreparedStatement a_psql, int a_param, long a_lId, InDesignAsset a_inda)
/*     */     throws SQLException
/*     */   {
/* 464 */     a_param = prepareInDesignAssetPK(a_psql, a_param, a_lId);
/* 465 */     a_param = prepareInDesignAssetNonPK(a_psql, a_param, a_inda);
/* 466 */     return a_param;
/*     */   }
/*     */ 
/*     */   private static int prepareInDesignAssetNonPK(PreparedStatement a_psql, int a_param, InDesignAsset a_inda)
/*     */     throws SQLException
/*     */   {
/* 472 */     a_psql.setBoolean(a_param++, a_inda.getIsTemplate());
/* 473 */     DBUtil.setFieldIdOrNull(a_psql, a_param++, a_inda.getTemplateAssetId());
/* 474 */     DBUtil.setFieldIdOrNull(a_psql, a_param++, a_inda.getInDesignDocumentId());
/* 475 */     DBUtil.setFieldIdOrNull(a_psql, a_param++, a_inda.getPDFStatusId());
/* 476 */     return a_param;
/*     */   }
/*     */ 
/*     */   private static int prepareInDesignAssetPK(PreparedStatement a_psql, int a_param, long a_lId)
/*     */     throws SQLException
/*     */   {
/* 482 */     a_psql.setLong(a_param++, a_lId);
/* 483 */     return a_param;
/*     */   }
/*     */ 
/*     */   public void delete(DBTransaction a_transaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/* 492 */     InDesignAssetWithRelated inDAsset = getWithRelated(a_transaction, a_lId);
/* 493 */     String sSQL = "DELETE FROM InDesignAsset WHERE AssetId = ?";
/*     */     try
/*     */     {
/* 497 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 499 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 500 */       psql.setLong(1, a_lId);
/*     */ 
/* 502 */       psql.executeUpdate();
/* 503 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 507 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */ 
/* 510 */     if (inDAsset != null)
/*     */     {
/* 512 */       InDesignDocument document = inDAsset.getDocument();
/* 513 */       if (document != null)
/*     */       {
/* 515 */         deleteDocument(a_transaction, document);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void deleteDocument(DBTransaction a_transaction, InDesignDocument a_document)
/*     */     throws Bn2Exception
/*     */   {
/* 531 */     String sFileLocationToDelete = a_document.getFileLocation();
/* 532 */     getDocumentDAO().delete(a_transaction, a_document.getId());
/* 533 */     deleteDocumentFile(a_transaction, sFileLocationToDelete);
/*     */   }
/*     */ 
/*     */   private void deleteDocumentFile(DBTransaction a_transaction, String a_sFileLocationToDelete)
/*     */     throws Bn2Exception
/*     */   {
/* 548 */     Boolean result = getFileStoreManager().deleteFile(a_sFileLocationToDelete);
/* 549 */     if ((result == null) || (!result.booleanValue()))
/*     */     {
/* 551 */       a_transaction.rollback2();
/* 552 */       throw new Bn2Exception("Couldn't delete InDesign Document file " + a_sFileLocationToDelete);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getTemplateAssetName(long a_templateAssetId, String a_languageCode) throws Bn2Exception
/*     */   {
/* 558 */     SearchCriteria searchCriteria = new SearchCriteria();
/*     */ 
/* 560 */     searchCriteria.setAssetIds(Collections.singleton(Long.valueOf(a_templateAssetId)));
/*     */ 
/* 562 */     Vector lightweightAssets = getSearchManager().search(searchCriteria, a_languageCode).getSearchResults();
/* 563 */     return lightweightAssets.isEmpty() ? null : ((LightweightAsset)lightweightAssets.firstElement()).getSearchName();
/*     */   }
/*     */ 
/*     */   private FileStoreManager getFileStoreManager()
/*     */   {
/* 571 */     return this.m_fileStoreManager;
/*     */   }
/*     */ 
/*     */   private InDesignAssetEntityService getEntityService()
/*     */   {
/* 576 */     return this.m_entityService;
/*     */   }
/*     */ 
/*     */   private InDesignDocumentDAO getDocumentDAO()
/*     */   {
/* 581 */     return this.m_documentDAO;
/*     */   }
/*     */ 
/*     */   private InDesignPDFJobService getPDFJobService()
/*     */   {
/* 586 */     return this.m_pdfJobService;
/*     */   }
/*     */ 
/*     */   private MultiLanguageSearchManager getSearchManager()
/*     */   {
/* 591 */     return this.m_searchManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.asset.abplugin.InDesignAssetService
 * JD-Core Version:    0.6.0
 */