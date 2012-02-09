/*     */ package com.bright.assetbank.custom.indesign.asset.abplugin;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.AssetFileSource;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.application.service.FileUploadManager;
/*     */ import com.bright.assetbank.custom.indesign.asset.bean.FileInfo;
/*     */ import com.bright.assetbank.custom.indesign.asset.bean.InDesignAssetWithNewFile;
/*     */ import com.bright.assetbank.custom.indesign.asset.bean.InDesignAssetWithRelated;
/*     */ import com.bright.assetbank.custom.indesign.asset.bean.InDesignDocument;
/*     */ import com.bright.assetbank.custom.indesign.entity.abplugin.InDesignAssetEntityService;
/*     */ import com.bright.assetbank.custom.indesign.entity.bean.InDesignAssetEntity;
/*     */ import com.bright.assetbank.custom.indesign.pdf.service.InDesignPDFQualityService;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.plugin.bean.ABExtensibleBean;
/*     */ import com.bright.assetbank.plugin.iface.ABEditMod;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.struts.Bn2ExtensibleForm;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.ClassUtil;
/*     */ import com.bright.framework.util.LogUtil;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.upload.FormFile;
/*     */ 
/*     */ public abstract class InDesignAssetEditMod
/*     */   implements ABEditMod
/*     */ {
/*     */   private static final String c_ksFormKey_TemplateAssetId = "indesign_templateAssetId";
/*     */   private static final String c_ksFormKey_PDFQualityId = "indesign_pdfQuality";
/*  67 */   private Log m_logger = LogUtil.getLog(ClassUtil.currentClassName());
/*     */ 
/*     */   @Resource
/*     */   private InDesignAssetService m_service;
/*     */ 
/*     */   @Resource
/*     */   private InDesignAssetEntityService m_entityService;
/*     */ 
/*     */   @Resource
/*     */   private InDesignPDFQualityService m_pdfQualityService;
/*     */ 
/*     */   @Resource
/*     */   private FileStoreManager m_fileStoreManager;
/*     */ 
/*     */   @Resource
/*     */   private FileUploadManager m_fileUploadManager;
/*     */ 
/*  85 */   public void populateForm(DBTransaction a_transaction, Object a_oCoreObject, Serializable a_extensionData, HttpServletRequest a_request, Bn2ExtensibleForm a_form) throws Bn2Exception { InDesignAssetWithRelated inDAsset = (InDesignAssetWithRelated)a_extensionData;
/*     */ 
/*  90 */     if (a_extensionData != null)
/*     */     {
/*  92 */       a_form.setExt("indesign_templateAssetId", Long.valueOf(inDAsset.getTemplateAssetId()));
/*  93 */       InDesignDocument indd = inDAsset.getDocument();
/*  94 */       if (indd != null)
/*     */       {
/*  96 */         a_form.setExt("indesign_documentFilename", indd.getOriginalFilename());
/*  97 */         a_form.setExt("indesign_pdfQuality", Integer.valueOf(indd.getInDesignPDFQualityId()));
/*     */       }
/*     */     }
/*     */ 
/* 101 */     AssetForm form = (AssetForm)a_form;
/*     */ 
/* 105 */     long assetEntityId = form.getAsset().getEntity().getId();
/* 106 */     InDesignAssetEntity inDAssetEntity = getEntityService().get(a_transaction, assetEntityId);
/* 107 */     a_request.setAttribute("indesign_inDAssetEntity", inDAssetEntity);
/*     */   }
/*     */ 
/*     */   public void populateViewEditRequest(DBTransaction a_transaction, HttpServletRequest a_request, Bn2ExtensibleForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 116 */     String sLanguageCode = UserProfile.getUserProfile(a_request.getSession()).getCurrentLanguage().getCode();
/*     */ 
/* 120 */     AssetForm form = (AssetForm)a_form;
/*     */ 
/* 122 */     AssetEntity entity = form.getAsset().getEntity();
/*     */ 
/* 124 */     this.m_logger.debug("InDesignAssetEditMod: entity ID = " + entity.getId() + ", entity name = " + entity.getName());
/*     */ 
/* 129 */     List templateAssets = getService().getTemplates(entity.getId(), sLanguageCode);
/*     */ 
/* 131 */     a_request.setAttribute("indesign_templateAssets", templateAssets);
/*     */ 
/* 133 */     List pdfQualities = getPdfQualityService().getAll(a_transaction);
/* 134 */     a_request.setAttribute("indesign_pdfQualities", pdfQualities);
/*     */   }
/*     */ 
/*     */   public void validate(DBTransaction a_transaction, ABUserProfile a_userProfile, Object a_oCoreObject, Bn2ExtensibleForm a_form) throws Bn2Exception
/*     */   {
/* 139 */     FormFile file = (FormFile)a_form.getExt("indesign_documentFile");
/* 140 */     if (file != null)
/*     */     {
/* 143 */       String maxFileUploadSizeError = this.m_fileUploadManager.validateMaxFileUploadSize(a_transaction, a_userProfile, file);
/*     */ 
/* 146 */       if (maxFileUploadSizeError != null)
/*     */       {
/* 148 */         a_form.addError(maxFileUploadSizeError);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Serializable extractDataFromForm(DBTransaction a_transaction, ABExtensibleBean a_coreObject, Bn2ExtensibleForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 160 */     Asset asset = (Asset)a_coreObject;
/*     */ 
/* 162 */     InDesignAssetEntity inDAssetEntity = getEntityService().get(a_transaction, asset.getEntity().getId());
/* 163 */     int inDAssetEntityTypeId = getEntityService().typeIdFromInDesignAssetEntity(inDAssetEntity);
/* 164 */     if (inDAssetEntityTypeId <= 0)
/*     */     {
/* 167 */       return null;
/*     */     }
/*     */ 
/* 170 */     if (inDAssetEntityTypeId != 1)
/*     */     {
/* 174 */       return null;
/*     */     }
/*     */ 
/* 177 */     FileInfo newFile = null;
/* 178 */     FormFile file = (FormFile)a_form.getExt("indesign_documentFile");
/* 179 */     if ((file != null) && (file.getFileSize() > 0))
/*     */     {
/*     */       try
/*     */       {
/* 183 */         AssetFileSource source = new AssetFileSource(file);
/*     */         try
/*     */         {
/* 187 */           String sNewFileLocation = this.m_fileStoreManager.addFile(source.getInputStream(), source.getFilename(), StoredFileType.ASSET);
/*     */ 
/* 191 */           newFile = new FileInfo();
/* 192 */           newFile.setFileLocation(sNewFileLocation);
/* 193 */           newFile.setOriginalFilename(source.getFilename());
/* 194 */           newFile.setFileSizeInBytes(source.getFileSize());
/*     */         }
/*     */         finally
/*     */         {
/* 199 */           source.close();
/*     */         }
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 204 */         throw new Bn2Exception("Error whilst receiving uploaded InDesign Document", e);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 209 */     long templateAssetId = a_form.getExtLong("indesign_templateAssetId");
/*     */ 
/* 212 */     boolean isTemplate = templateAssetId <= 0L;
/*     */ 
/* 215 */     boolean adding = asset.getId() <= 0L;
/*     */     InDesignAssetWithNewFile inDAsset;
/* 216 */     if (adding)
/*     */     {
/* 218 */       inDAsset = new InDesignAssetWithNewFile();
/*     */ 
/* 221 */       inDAsset.setPDFStatusId(1L);
/*     */     }
/*     */     else
/*     */     {
/* 229 */       inDAsset = new InDesignAssetWithNewFile();
/* 230 */       getService().populate(a_transaction, asset.getId(), inDAsset);
/*     */     }
/*     */ 
/* 234 */     String sPDFQualityId = a_form.getExt("indesign_pdfQuality").toString();
/* 235 */     int pdfQualityId = RequestUtil.intFromStringParameter(sPDFQualityId);
/*     */ 
/* 237 */     inDAsset.setIsTemplate(isTemplate);
/* 238 */     inDAsset.setTemplateAssetId(templateAssetId);
/* 239 */     inDAsset.setNewFile(newFile);
/* 240 */     inDAsset.setPdfQualityId(pdfQualityId);
/*     */ 
/* 242 */     return inDAsset;
/*     */   }
/*     */ 
/*     */   protected InDesignAssetService getService()
/*     */   {
/* 247 */     return this.m_service;
/*     */   }
/*     */ 
/*     */   protected InDesignAssetEntityService getEntityService()
/*     */   {
/* 252 */     return this.m_entityService;
/*     */   }
/*     */ 
/*     */   protected InDesignPDFQualityService getPdfQualityService()
/*     */   {
/* 257 */     return this.m_pdfQualityService;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.asset.abplugin.InDesignAssetEditMod
 * JD-Core Version:    0.6.0
 */