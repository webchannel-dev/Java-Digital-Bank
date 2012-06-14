/*     */ package com.bright.assetbank.brandtemplate.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.brandtemplate.bean.TemplateField;
/*     */ import com.bright.assetbank.brandtemplate.bean.TemplateVariables;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.storage.service.StorageDeviceManager;
/*     */ import com.lowagie.text.DocumentException;
/*     */ import com.lowagie.text.pdf.AcroFields;
/*     */ import com.lowagie.text.pdf.PRAcroForm;
/*     */ import com.lowagie.text.pdf.PRAcroForm.FieldInformation;
/*     */ import com.lowagie.text.pdf.PdfDictionary;
/*     */ import com.lowagie.text.pdf.PdfName;
/*     */ import com.lowagie.text.pdf.PdfNumber;
/*     */ import com.lowagie.text.pdf.PdfReader;
/*     */ import com.lowagie.text.pdf.PdfStamper;
/*     */ import com.lowagie.text.pdf.PdfString;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.beanutils.BeanUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class BrandTemplateManager extends Bn2Manager
/*     */   implements AssetBankConstants
/*     */ {
/*     */   private static final String c_ksClassName = "BrandTemplateManager";
/*  65 */   private static final PdfName c_ksKeyTextUserInterface = PdfName.TU;
/*  66 */   private static final PdfName c_ksKeyFieldType = PdfName.FT;
/*  67 */   private static final PdfName c_ksKeyFieldFlags = new PdfName("Ff");
/*  68 */   private static final PdfName c_ksKeyFieldValue = PdfName.V;
/*  69 */   private static final PdfName c_ksFieldTypeText = new PdfName("Tx");
/*     */   private IAssetManager m_assetManager;
/*     */   private StorageDeviceManager m_storageDeviceManager;
/*     */   private FileStoreManager m_fileStoreManager;
/*  78 */   private final Pattern m_placeholderPattern = Pattern.compile("#\\{([^}]*)\\}");
/*     */ 
/*     */   public boolean isValidBrandTemplate(String a_fileLocation)
/*     */     throws Bn2Exception
/*     */   {
/*  89 */     return (AssetBankSettings.fileNameHasBrandTemplateExtension(a_fileLocation)) && (!findFieldsInAsset(a_fileLocation).isEmpty());
/*     */   }
/*     */ 
/*     */   public boolean isValidBrandTemplate(String a_fileName, InputStream a_is)
/*     */     throws Bn2Exception
/*     */   {
/* 105 */     return (AssetBankSettings.fileNameHasBrandTemplateExtension(a_fileName)) && (!findFieldsInAsset(a_is).isEmpty());
/*     */   }
/*     */ 
/*     */   public List<TemplateField> findFieldsInAssetAndReplacePlaceholders(Asset a_asset, TemplateVariables a_templateVariables)
/*     */     throws Bn2Exception
/*     */   {
/* 122 */     List<TemplateField> fields = findFieldsInAsset(a_asset.getFileLocation());
/* 123 */     for (TemplateField field : fields)
/*     */     {
/* 125 */       String value = field.getValue();
/*     */ 
/* 127 */       if (StringUtils.isNotEmpty(value))
/*     */       {
/* 131 */         Matcher matcher = this.m_placeholderPattern.matcher(value);
/* 132 */         while (matcher.find())
/*     */         {
/* 134 */           String propertyName = matcher.group(1);
/* 135 */           String replacement = safeGetProperty(a_templateVariables, propertyName);
/* 136 */           if (replacement == null)
/*     */           {
/* 141 */             replacement = "[couldn't get " + propertyName + "]";
/*     */           }
/*     */ 
/* 144 */           value = value.substring(0, matcher.start()) + replacement + value.substring(matcher.end());
/* 145 */           matcher.reset(value);
/*     */         }
/*     */ 
/* 148 */         field.setValue(value);
/*     */       }
/*     */     }
/* 151 */     return fields;
/*     */   }
/*     */ 
/*     */   private String safeGetProperty(Object a_bean, String a_propertyName)
/*     */   {
/*     */     try
/*     */     {
/* 165 */       String propertyValue = BeanUtils.getProperty(a_bean, a_propertyName);
/* 166 */       if (propertyValue == null)
/*     */       {
/* 168 */         propertyValue = "";
/*     */       }
/* 170 */       return propertyValue;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 179 */       this.m_logger.error("Couldn't get property " + a_propertyName + " of objecct " + a_bean, e);
/* 180 */     }return null;
/*     */   }
/*     */ 
/*     */   public List<TemplateField> findFieldsInAsset(String a_fileLocation)
/*     */     throws Bn2Exception
/*     */   {
/* 192 */     final String fullPath = this.m_fileStoreManager.getAbsolutePath(a_fileLocation);
/*     */ 
/* 194 */     PdfReaderSource pdfReaderSource = new PdfReaderSource()
/*     */     {
/*     */       public PdfReader createPdfReader() throws IOException
/*     */       {
/* 198 */         return new PdfReader(fullPath);
/*     */       }
/*     */     };
/* 202 */     return findFieldsInAsset(pdfReaderSource);
/*     */   }
/*     */ 
/*     */   private List<TemplateField> findFieldsInAsset(final InputStream a_is)
/*     */     throws Bn2Exception
/*     */   {
/* 213 */     PdfReaderSource pdfReaderSource = new PdfReaderSource()
/*     */     {
/*     */       public PdfReader createPdfReader() throws IOException
/*     */       {
/* 217 */         return new PdfReader(a_is);
/*     */       }
/*     */     };
/* 221 */     return findFieldsInAsset(pdfReaderSource);
/*     */   }
/*     */ 
/*     */   private List<TemplateField> findFieldsInAsset(PdfReaderSource a_pdfReaderSource) throws Bn2Exception {
/* 234 */     PdfReader pdfReader = null;
/*     */     List results;
/*     */     try {
/* 237 */       pdfReader = a_pdfReaderSource.createPdfReader();
/*     */ 
/* 239 */       results = new ArrayList();
/*     */ 
/* 241 */       PRAcroForm acroForm = pdfReader.getAcroForm();
/*     */ 
/* 243 */       if (acroForm != null)
/*     */       {
/* 245 */         List fields = acroForm.getFields();
/* 246 */         for (Iterator i$ = fields.iterator(); i$.hasNext(); ) { Object oField = i$.next();
/*     */ 
/* 248 */           PRAcroForm.FieldInformation field = (PRAcroForm.FieldInformation)oField;
/*     */ 
/* 250 */           PdfDictionary fieldDict = field.getInfo();
/* 251 */           PdfName fieldType = fieldDict.getAsName(c_ksKeyFieldType);
/* 252 */           if (fieldType == null)
/*     */           {
/* 255 */             this.m_logger.error("PDF field " + field.getName() + " has no type.  Ignoring it");
/*     */           }
/* 257 */           else if (!fieldType.equals(c_ksFieldTypeText))
/*     */           {
/* 259 */             this.m_logger.warn("PDF field " + field.getName() + " is not a text field.  Ignoring it");
/*     */           }
/*     */           else
/*     */           {
/* 267 */             PdfString textUI = fieldDict.getAsString(c_ksKeyTextUserInterface);
/*     */             String humanName;
/*     */             //String humanName;
/* 269 */             if ((textUI != null) && (textUI.length() > 0))
/*     */             {
/* 271 */               humanName = textUI.toString();
/*     */             }
/*     */             else
/*     */             {
/* 275 */               humanName = field.getName();
/*     */             }
/*     */ 
/* 278 */             PdfNumber pdfnFieldFlags = fieldDict.getAsNumber(c_ksKeyFieldFlags);
/* 279 */             int fieldFlags = pdfnFieldFlags != null ? pdfnFieldFlags.intValue() : 0;
/* 280 */             boolean multiline = (fieldFlags & 0x1000) == 4096;
/*     */ 
/* 282 */             PdfString pdfsValue = fieldDict.getAsString(c_ksKeyFieldValue);
/* 283 */             String value = pdfsValue != null ? pdfsValue.toString() : null;
/* 284 */             results.add(new TemplateField(field.getName(), humanName, multiline, value));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */       Iterator i$;
/* 291 */       throw new Bn2Exception("BrandTemplateManager: IOException reading form field names", e);
/*     */     }
/*     */     finally
/*     */     {
/* 295 */       if (pdfReader != null)
/*     */       {
/* 297 */         pdfReader.close();
/*     */       }
/*     */     }
/*     */ 
/* 301 */     return results;
/*     */   }
/*     */ 
/*     */   public String fillInTemplate(Asset a_asset, Map<String, String> a_fieldValues)
/*     */     throws Bn2Exception
/*     */   {
/* 314 */     String ksMethodName = "fillInTemplate";
/*     */ 
/* 316 */     String sRelativeOutputPath = this.m_fileStoreManager.getUniqueFilepath(a_asset.getFileName(), StoredFileType.TEMP);
/* 317 */     String sCompleteOutputPath = this.m_storageDeviceManager.getFullPathForRelativePath(sRelativeOutputPath);
/*     */ 
/* 319 */     PdfReader pdfReader = null;
/* 320 */     PdfStamper pdfStamper = null;
/* 321 */     OutputStream os = null;
/*     */     try
/*     */     {
/* 324 */       pdfReader = createPdfReaderForAsset(a_asset);
/* 325 */       os = new FileOutputStream(sCompleteOutputPath);
/* 326 */       pdfStamper = new PdfStamper(pdfReader, os);
/*     */ 
/* 328 */       pdfStamper.setFormFlattening(true);
            /*     */
            /* 330 */
            AcroFields stamperFields = pdfStamper.getAcroFields();
/*     */ 
/* 333 */       for (Map.Entry fieldEntry : a_fieldValues.entrySet())
/*     */       {
/* 335 */         String fieldName = (String)fieldEntry.getKey();
/* 336 */         String fieldValue = (String)fieldEntry.getValue();
/*     */ 
/* 340 */         fieldValue = fieldValue.replace("\r\n", "\n");
/*     */ 
/* 344 */         boolean foundAndChanged = stamperFields.setField(fieldName, fieldValue);
/* 345 */         if (!foundAndChanged)
/*     */         {
/* 347 */           this.m_logger.error("BrandTemplateManager.fillInTemplate: setField : No form field with name " + fieldName + " found in asset " + a_asset.getId());
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */       AcroFields stamperFields;
/* 365 */       throw new Bn2Exception("BrandTemplateManager: IOException whilst filling in form", e);
/*     */     }
/*     */     catch (DocumentException e)
/*     */     {
/* 369 */       throw new Bn2Exception("BrandTemplateManager: DocumentException whilst filling in form", e);
/*     */     }
/*     */     finally
/*     */     {
/* 373 */       if (pdfStamper != null)
/*     */       {
/*     */         try
/*     */         {
/* 377 */           pdfStamper.close();
/*     */         }
/*     */         catch (DocumentException e)
/*     */         {
/* 381 */           this.m_logger.error("BrandTemplateManager: DocumentException closing PdfStamper", e);
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/* 385 */           this.m_logger.error("BrandTemplateManager: IOException closing PdfStamper", e);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 391 */       if ((pdfStamper == null) && (os != null))
/*     */       {
/*     */         try
/*     */         {
/* 395 */           os.close();
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/* 399 */           this.m_logger.error("BrandTemplateManager: IOException closing OutputStream", e);
/*     */         }
/*     */       }
/*     */ 
/* 403 */       if (pdfReader != null)
/*     */       {
/* 405 */         pdfReader.close();
/*     */       }
/*     */     }
/*     */ 
/* 409 */     return sRelativeOutputPath;
/*     */   }
/*     */ 
/*     */   private PdfReader createPdfReaderForAsset(Asset asset)
/*     */     throws Bn2Exception
/*     */   {
/* 421 */     String fullPath = this.m_fileStoreManager.getAbsolutePath(asset.getFileLocation());
/*     */     try
/*     */     {
/* 424 */       return new PdfReader(fullPath);
/*     */     }
/*     */     catch (IOException e) {
/*     */     
/* 428 */     throw new Bn2Exception("BrandTemplateManager: IOException loading asset into PDDocument: ", e);
/*     */   }}
/*     */ 
/*     */   public IAssetManager getAssetManager()
/*     */   {
/* 435 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_assetManager)
/*     */   {
/* 440 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public StorageDeviceManager getStorageDeviceManager()
/*     */   {
/* 445 */     return this.m_storageDeviceManager;
/*     */   }
/*     */ 
/*     */   public void setStorageDeviceManager(StorageDeviceManager a_storageDeviceManager)
/*     */   {
/* 450 */     this.m_storageDeviceManager = a_storageDeviceManager;
/*     */   }
/*     */ 
/*     */   public FileStoreManager getFileStoreManager()
/*     */   {
/* 455 */     return this.m_fileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*     */   {
/* 460 */     this.m_fileStoreManager = a_fileStoreManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.brandtemplate.service.BrandTemplateManager
 * JD-Core Version:    0.6.0
 */