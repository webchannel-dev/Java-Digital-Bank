/*     */ package com.bright.assetbank.custom.indesign.pdf.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.AssetFileSource;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.custom.indesign.asset.abplugin.InDesignAssetExtension;
/*     */ import com.bright.assetbank.custom.indesign.asset.bean.InDesignAssetWithRelated;
/*     */ import com.bright.assetbank.custom.indesign.asset.bean.InDesignDocument;
/*     */ import com.bright.assetbank.custom.indesign.constant.InDesignSettings;
/*     */ import com.bright.assetbank.custom.indesign.custom.InDesignAttributeProvider;
/*     */ import com.bright.assetbank.custom.indesign.entity.abplugin.InDesignAssetEntityService;
/*     */ import com.bright.assetbank.custom.indesign.entity.bean.InDesignAssetEntity;
/*     */ import com.bright.assetbank.custom.indesign.pdf.bean.InDesignPDFQuality;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.exception.SQLStatementException;
/*     */ import com.bright.framework.database.service.DBTransactionCallback;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.indesign.export.DataMergeDocOp;
/*     */ import com.bright.framework.indesign.export.ExportAsPDFDocOp;
/*     */ import com.bright.framework.indesign.scaffolding.InDesignScaffold;
/*     */ import com.bright.framework.storage.bean.StorageDevice;
/*     */ import com.bright.framework.storage.service.StorageDeviceManager;
/*     */ import com.bright.framework.util.ClassUtil;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.LogUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Vector;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Resource;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.springframework.stereotype.Component;
/*     */ import org.supercsv.io.CsvMapWriter;
/*     */ import org.supercsv.prefs.CsvPreference;
/*     */ 
/*     */ @Component
/*     */ public class InDesignPDFGenerator
/*     */ {
/*  75 */   private static final String c_ksClassName = ClassUtil.currentClassName();
/*  76 */   private Log m_logger = LogUtil.getLog(c_ksClassName);
/*     */ 
/*     */   @Resource
/*     */   private InDesignAssetEntityService m_inDesignAssetEntityService;
/*     */ 
/*     */   @Resource
/*     */   private InDesignPDFQualityService m_pdfQualityService;
/*     */ 
/*     */   @Resource
/*     */   private InDesignAttributeProvider m_attributeProvider;
/*     */ 
/*     */   @Resource
/*     */   private DBTransactionManager m_transactionManager;
/*     */ 
/*     */   @Resource
/*     */   private IAssetManager m_assetManager;
/*     */ 
/*     */   @Resource
/*     */   private AttributeManager m_attributeManager;
/*     */ 
/*     */   @Resource
/*     */   private StorageDeviceManager m_storageDeviceManager;
/*     */ 
/*     */   @Resource
/*     */   private ABUserManager m_userManager;
/*     */   private InDesignScaffold m_scaffold;
/*     */ 
/* 103 */   public InDesignPDFGenerator() { this.m_scaffold = new InDesignScaffold(InDesignSettings.getIORFilename());
/*     */ 
/* 106 */     File workDir = new File(InDesignSettings.getLocalWorkDirectory());
/* 107 */     if (!workDir.isDirectory())
/*     */     {
/* 109 */       if (!workDir.mkdirs())
/*     */       {
/* 111 */         this.m_logger.error("Couldn't create work directory " + workDir + ". PDF generation will probably fail");
/*     */       }
/*     */     } }
/*     */ 
/*     */   public void generatePDFForAsset(long a_assetId)
/*     */     throws Bn2Exception
/*     */   {
/* 118 */     new PDFForAssetGenerator(a_assetId).invoke();
/*     */   }
/*     */ 
/*     */   private Map<Long, String> fillInTheBlanks(List<Long> a_attributeIdsInInDD, Map<Long, String> a_attributeValues)
/*     */   {
/* 287 */     HashMap fillMe = new HashMap(a_attributeValues);
/* 288 */     for (Long id : a_attributeIdsInInDD)
/*     */     {
/* 290 */       if (!fillMe.containsKey(id))
/*     */       {
/* 292 */         fillMe.put(id, "");
/*     */       }
/*     */     }
/*     */ 
/* 296 */     return fillMe;
/*     */   }
/*     */ 
/*     */   private File writeDataMergeCSVFile(File a_workDir, List<Long> a_attributeIdsInInDD, Map<Long, String> a_attributeValues, List<String> a_symbolFileNames, int a_numSymbolsInDocument)
/*     */     throws IOException, Bn2Exception
/*     */   {
/* 317 */     if ((a_attributeValues.isEmpty()) && (a_numSymbolsInDocument == 0))
/*     */     {
/* 319 */       return null;
/*     */     }
/*     */ 
/* 322 */     File dataCSVInWorkDir = new File(a_workDir, "data.csv");
/*     */ 
/* 326 */     Map fieldValues = new HashMap();
/*     */ 
/* 331 */     String[] headings = new String[a_attributeIdsInInDD.size() + a_numSymbolsInDocument];
/* 332 */     String[] nameMapping = new String[a_attributeIdsInInDD.size() + a_numSymbolsInDocument];
/* 333 */     Pattern startingAt = Pattern.compile("^@");
/* 334 */     int iHeadings = 0;
/* 335 */     for (Iterator i$ = a_attributeIdsInInDD.iterator(); i$.hasNext(); ) { long attributeId = ((Long)i$.next()).longValue();
/*     */ 
/* 337 */       Attribute attribute = getAttributeManager().getAttribute(null, attributeId);
/* 338 */       String attributeLabel = attribute.getLabel();
/*     */ 
/* 342 */       String heading = startingAt.matcher(attributeLabel).replaceFirst("(at)");
/* 343 */       headings[iHeadings] = heading;
/* 344 */       nameMapping[iHeadings] = String.valueOf(attributeId);
/* 345 */       iHeadings++;
/*     */     }
/*     */ 
/* 354 */     Iterator it = a_symbolFileNames.iterator();
/* 355 */     for (int symbolNum = 1; symbolNum <= a_numSymbolsInDocument; symbolNum++)
/*     */     {
/* 357 */       headings[iHeadings] = ("@Symbol" + symbolNum);
/* 358 */       nameMapping[iHeadings] = headings[iHeadings];
/*     */       String symbolFileName;
/*     */       //String symbolFileName;
/* 362 */       if (it.hasNext())
/*     */       {
/* 364 */         symbolFileName = (String)it.next();
/*     */       }
/*     */       else
/*     */       {
/* 368 */         symbolFileName = "";
/*     */       }
/*     */ 
/* 371 */       fieldValues.put(headings[iHeadings], symbolFileName);
/* 372 */       iHeadings++;
/*     */     }
/*     */ 
/* 378 */     for (Map.Entry entry : a_attributeValues.entrySet())
/*     */     {
/* 380 */       String sAttributeId = ((Long)entry.getKey()).toString();
/*     */ 
/* 384 */       String valueNoNewlines = ((String)entry.getValue()).replace("\r\n", " ").replace('\r', ' ').replace('\n', ' ');
/*     */ 
/* 388 */       fieldValues.put(sAttributeId, valueNoNewlines);
/*     */     }
/*     */ 
/* 392 */     Writer csvFileWriter = new OutputStreamWriter(new FileOutputStream(dataCSVInWorkDir), "UTF-16");
/*     */     try
/*     */     {
/* 396 */       CsvMapWriter csvWriter = new CsvMapWriter(csvFileWriter, CsvPreference.STANDARD_PREFERENCE);
/* 397 */       csvWriter.writeHeader(headings);
/*     */ 
/* 399 */       csvWriter.write(fieldValues, nameMapping);
/*     */ 
/* 402 */       csvWriter.close();
/*     */     }
/*     */     finally
/*     */     {
/* 406 */       csvFileWriter.close();
/*     */     }
/* 408 */     return dataCSVInWorkDir;
/*     */   }
/*     */ 
/*     */   private String translateToInDesignServerPath(String a_localPath)
/*     */   {
/* 420 */     String localWorkDirectory = InDesignSettings.getLocalWorkDirectory();
/* 421 */     if (!a_localPath.startsWith(localWorkDirectory))
/*     */     {
/* 423 */       throw new IllegalArgumentException("Local path \"" + a_localPath + "\" is not in work directory \"" + localWorkDirectory + "\"");
/*     */     }
/*     */ 
/* 428 */     return a_localPath.replace(localWorkDirectory, InDesignSettings.getRemoteWorkDirectory());
/*     */   }
/*     */ 
/*     */   private List<Long> getAttributeIdsInInDD(DBTransaction a_transaction, long a_inddId)
/*     */     throws Bn2Exception
/*     */   {
/* 436 */     String sSQL = "SELECT AttributeId FROM AttributeInInDesignDocument WHERE InDesignDocumentId = ?";
/*     */     try
/*     */     {
/* 441 */       Connection con = a_transaction.getConnection();
/* 442 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 443 */       psql.setLong(1, a_inddId);
/*     */ 
/* 445 */       ResultSet rs = psql.executeQuery();
/* 446 */       List attIds = new ArrayList();
/* 447 */       while (rs.next())
/*     */       {
/* 449 */         attIds.add(Long.valueOf(rs.getLong("AttributeId")));
/*     */       }
/*     */ 
/* 452 */       psql.close();
/*     */ 
/* 454 */       return attIds;
/*     */     }
/*     */     catch (SQLException e) {
/*     */     
/* 458 */     throw new SQLStatementException(sSQL, e);}
/*     */   }
/*     */ 
/*     */   private DBTransactionManager getTransactionManager()
/*     */   {
/* 465 */     return this.m_transactionManager;
/*     */   }
/*     */ 
/*     */   private IAssetManager getAssetManager()
/*     */   {
/* 470 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   private AttributeManager getAttributeManager()
/*     */   {
/* 475 */     return this.m_attributeManager;
/*     */   }
/*     */ 
/*     */   private StorageDeviceManager getStorageDeviceManager()
/*     */   {
/* 480 */     return this.m_storageDeviceManager;
/*     */   }
/*     */ 
/*     */   private ABUserManager getUserManager()
/*     */   {
/* 485 */     return this.m_userManager;
/*     */   }
/*     */ 
/*     */   private InDesignAssetEntityService getInDesignAssetEntityService()
/*     */   {
/* 490 */     return this.m_inDesignAssetEntityService;
/*     */   }
/*     */ 
/*     */   private InDesignPDFQualityService getPdfQualityService()
/*     */   {
/* 495 */     return this.m_pdfQualityService;
/*     */   }
/*     */ 
/*     */   private class PDFForAssetGenerator
/*     */   {
/*     */     private final long m_assetId;
/*     */     private Asset m_asset;
/*     */     private InDesignAssetWithRelated m_inDAsset;
/*     */     private List<Long> m_attributeIdsInInDD;
/*     */     private InDesignPDFQuality m_pdfQuality;
/*     */     private List<Asset> m_childAssets;
/*     */ 
/*     */     public PDFForAssetGenerator(long a_assetId)
/*     */     {
/* 133 */       this.m_assetId = a_assetId;
/*     */     }
/*     */ 
/*     */     public void invoke()
/*     */       throws Bn2Exception
/*     */     {
/* 139 */       InDesignPDFGenerator.this.m_logger.info(InDesignPDFGenerator.c_ksClassName + ": about to generate PDF for asset " + this.m_assetId);
/*     */ 
/* 141 */       String fullInDDPath = (String)InDesignPDFGenerator.this.getTransactionManager().execute(new DBTransactionCallback()
/*     */       {
/*     */         public String doInTransaction(DBTransaction a_transaction) throws SQLException, Bn2Exception
/*     */         {
/* 145 */           InDesignPDFGenerator.PDFForAssetGenerator.access$202(InDesignPDFGenerator.PDFForAssetGenerator.this, InDesignPDFGenerator.this.getAssetManager().getAsset(a_transaction, InDesignPDFGenerator.PDFForAssetGenerator.this.m_assetId));
/* 146 */           InDesignPDFGenerator.PDFForAssetGenerator.access$502(InDesignPDFGenerator.PDFForAssetGenerator.this, InDesignAssetExtension.getInDesignAsset(InDesignPDFGenerator.PDFForAssetGenerator.this.m_asset));
/* 147 */           InDesignPDFGenerator.PDFForAssetGenerator.access$602(InDesignPDFGenerator.PDFForAssetGenerator.this, InDesignPDFGenerator.this.getPdfQualityService().get(a_transaction, InDesignPDFGenerator.PDFForAssetGenerator.this.m_inDAsset.getDocument().getInDesignPDFQualityId()));
/*     */ 
/* 150 */           InDesignPDFGenerator.PDFForAssetGenerator.access$802(InDesignPDFGenerator.PDFForAssetGenerator.this, InDesignPDFGenerator.this.getAttributeIdsInInDD(a_transaction, InDesignPDFGenerator.PDFForAssetGenerator.this.m_inDAsset.getInDesignDocumentId()));
/*     */ 
/* 152 */           Vector childAssetIds = new Vector();
/* 153 */           childAssetIds.addAll(InDesignPDFGenerator.PDFForAssetGenerator.this.m_asset.getChildAssetIds());
/* 154 */           InDesignPDFGenerator.PDFForAssetGenerator.access$1002(InDesignPDFGenerator.PDFForAssetGenerator.this, InDesignPDFGenerator.this.getAssetManager().getAssets(a_transaction, childAssetIds, true, true));
/*     */ 
/* 156 */           String fileLocation = InDesignPDFGenerator.PDFForAssetGenerator.this.m_inDAsset.getDocument().getFileLocation();
/* 157 */           StorageDevice storageDevice = InDesignPDFGenerator.this.getStorageDeviceManager().getDeviceForRelativePath(fileLocation);
/* 158 */           return storageDevice.getFullLocalPath(fileLocation);
/*     */         }
/*     */       });
/* 162 */       File workDir = null;
/*     */       try
/*     */       {
/* 165 */         File sourceInDD = new File(fullInDDPath);
/*     */ 
/* 168 */         File workDirParent = new File(InDesignSettings.getLocalWorkDirectory());
/* 169 */         workDir = FileUtil.createTempDir("", "ind", workDirParent);
/* 170 */         InDesignPDFGenerator.this.m_logger.debug("Created work directory " + workDir);
/*     */ 
/* 173 */         File inDDInWorkDir = new File(workDir, sourceInDD.getName());
/* 174 */         InDesignPDFGenerator.this.m_logger.debug("About to copy InDD to work directory: " + sourceInDD);
/* 175 */         FileUtils.copyFile(sourceInDD, inDDInWorkDir);
/*     */ 
/* 179 */         List symbolFileNames = new ArrayList();
/* 180 */         for (final Asset childAsset : this.m_childAssets)
/*     */         {
/* 182 */           //InDesignAssetEntity childInDEntity = (InDesignAssetEntity)InDesignPDFGenerator.this.getTransactionManager().execute(new DBTransactionCallback(childAsset)
                    InDesignAssetEntity childInDEntity = (InDesignAssetEntity)InDesignPDFGenerator.this.getTransactionManager().execute(new DBTransactionCallback()
/*     */           {
/*     */             public InDesignAssetEntity doInTransaction(DBTransaction a_transaction) throws SQLException, Bn2Exception
/*     */             {
/* 186 */               return InDesignPDFGenerator.this.getInDesignAssetEntityService().get(a_transaction, childAsset.getEntity().getId());
/*     */             }
/*     */           });
/* 190 */           if ((childInDEntity.isImage()) || (childInDEntity.isSymbol()))
/*     */           {
/* 192 */             String fileLocation = childAsset.getFileLocation();
/* 193 */             StorageDevice storageDevice = InDesignPDFGenerator.this.getStorageDeviceManager().getDeviceForRelativePath(fileLocation);
/* 194 */             File childImageFile = new File(storageDevice.getFullLocalPath(fileLocation));
/* 195 */             InDesignPDFGenerator.this.m_logger.debug("About to copy image to work directory: " + childImageFile);
/* 196 */             File childImageFileInWorkDir = new File(workDir, childImageFile.getName());
/* 197 */             FileUtils.copyFile(childImageFile, childImageFileInWorkDir);
/*     */ 
/* 199 */             if (childInDEntity.isSymbol())
/*     */             {
/* 201 */               symbolFileNames.add(childImageFile.getName());
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 212 */         String pdfExportPresetName = this.m_pdfQuality.getName();
/* 213 */         InDesignPDFGenerator.this.m_logger.debug("Using InDesign server to generate PDF. Quality preset: " + pdfExportPresetName);
/* 214 */         String sInDDInWorkDirTranslatedPath = InDesignPDFGenerator.this.translateToInDesignServerPath(inDDInWorkDir.getPath());
/* 215 */         File pdfInWorkDir = new File(workDir, "out.pdf");
/* 216 */         String sPdfInWorkDirTranslatedPath = InDesignPDFGenerator.this.translateToInDesignServerPath(pdfInWorkDir.getPath());
/*     */         Map attributeValues;
/*     */         //Map attributeValues;
/* 220 */         if (this.m_attributeIdsInInDD.isEmpty())
/*     */         {
/* 222 */           attributeValues = new HashMap();
/*     */         }
/*     */         else
/*     */         {
/* 226 */           attributeValues = InDesignPDFGenerator.this.m_attributeProvider.getAttributeValues(this.m_assetId, this.m_attributeIdsInInDD);
/* 227 */           attributeValues = InDesignPDFGenerator.this.fillInTheBlanks(this.m_attributeIdsInInDD, attributeValues);
/*     */         }
/*     */ 
/* 231 */         File dataCSVInWorkDir = InDesignPDFGenerator.this.writeDataMergeCSVFile(workDir, this.m_attributeIdsInInDD, attributeValues, symbolFileNames, this.m_inDAsset.getDocument().getNumSymbols());
/*     */ 
/* 234 */         if (dataCSVInWorkDir == null)
/*     */         {
/* 237 */           InDesignPDFGenerator.this.m_scaffold.execute(sInDDInWorkDirTranslatedPath, new ExportAsPDFDocOp(sPdfInWorkDirTranslatedPath, pdfExportPresetName));
/*     */         }
/*     */         else
/*     */         {
/* 243 */           String sDataCSVInWorkDirTranslatedPath = InDesignPDFGenerator.this.translateToInDesignServerPath(dataCSVInWorkDir.getPath());
/*     */ 
/* 246 */           InDesignPDFGenerator.this.m_scaffold.execute(sInDDInWorkDirTranslatedPath, new DataMergeDocOp(sDataCSVInWorkDirTranslatedPath, sPdfInWorkDirTranslatedPath, pdfExportPresetName));
/*     */         }
/*     */ 
/* 253 */         InDesignPDFGenerator.this.m_logger.debug("Saving generated PDF to asset");
/* 254 */         ABUser appUser = InDesignPDFGenerator.this.getUserManager().getApplicationUser();
/* 255 */         AssetFileSource pdfFileSource = new AssetFileSource(pdfInWorkDir);
/* 256 */         InDesignPDFGenerator.this.getAssetManager().saveAsset(null, this.m_asset, pdfFileSource, appUser.getId(), null, null, false, 0, null);
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 260 */         throw new Bn2Exception(e);
/*     */       }
/*     */       finally
/*     */       {
/* 264 */         if (workDir != null)
/*     */         {
/*     */           try
/*     */           {
/* 268 */             InDesignPDFGenerator.this.m_logger.debug("Deleting work directory " + workDir);
/* 269 */             FileUtils.deleteDirectory(workDir);
/*     */           }
/*     */           catch (IOException e)
/*     */           {
/* 273 */             InDesignPDFGenerator.this.m_logger.error("Error deleting temporary directory", e);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.pdf.service.InDesignPDFGenerator
 * JD-Core Version:    0.6.0
 */