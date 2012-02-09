/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.FileFormat;
/*     */ import com.bright.assetbank.application.bean.ImageAsset;
/*     */ import com.bright.assetbank.application.bean.ImageConversionInfo;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*     */ import com.bright.assetbank.application.form.PrintImageForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.application.util.ABImageMagick;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.usage.bean.ColorSpace;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.bean.ImageFile;
/*     */ import com.bright.framework.image.util.ImageUtil;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.storage.bean.FileInStorageDevice;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class PrintImageAction extends BTransactionAction
/*     */   implements AssetBankConstants, FrameworkConstants
/*     */ {
/*  62 */   private IAssetManager m_assetManager = null;
/*  63 */   private FileStoreManager m_fileStoreManager = null;
/*  64 */   private UsageManager m_usageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  89 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  91 */     PrintImageForm form = (PrintImageForm)a_form;
/*  92 */     Vector vecAttributeIds = AttributeUtil.getControlPanelSelectedAttributes(a_request);
/*     */     try
/*     */     {
/*  97 */       Asset asset = this.m_assetManager.getAsset(a_dbTransaction, form.getAsset().getId(), vecAttributeIds, false, userProfile.getCurrentLanguage(), false);
/*     */ 
/*  99 */       form.setAsset(asset);
/*     */ 
/* 102 */       if ((AssetBankSettings.getDownloadFromFilesystem()) && (userProfile.getUserCanSeeSourcePath()))
/*     */       {
/* 105 */         String sFileLocation = asset.getOriginalFileLocation();
/*     */ 
/* 107 */         if ((sFileLocation == null) || (sFileLocation.length() == 0))
/*     */         {
/* 109 */           sFileLocation = asset.getFileLocation();
/*     */         }
/*     */ 
/* 112 */         if (sFileLocation != null)
/*     */         {
/* 114 */           FileInStorageDevice fileInSD = new FileInStorageDevice(this.m_fileStoreManager.getAbsolutePath(sFileLocation), userProfile.getUserOsIsWindows());
/* 115 */           form.setFileInStorageDevice(fileInSD);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 120 */       form.setEncryptedFilePath(FileUtil.encryptFilepath(asset.getFileLocation()));
/* 121 */       form.setEncryptedOriginalFilePath(FileUtil.encryptFilepath(asset.getOriginalFileLocation()));
/*     */     }
/*     */     catch (AssetNotFoundException anfe)
/*     */     {
/* 125 */       return a_mapping.findForward("AssetNotFound");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 133 */       long lImageId = form.getAsset().getId();
/*     */ 
/* 136 */       if (form.getAsset().getSurrogateAssetId() > 0L)
/*     */       {
/* 138 */         lImageId = form.getAsset().getSurrogateAssetId();
/*     */       }
/*     */ 
/* 142 */       int iSize = AssetBankSettings.getPrintImageSize();
/*     */ 
/* 145 */       if (iSize > AssetBankSettings.getMaxImageDownloadDimension())
/*     */       {
/* 147 */         iSize = AssetBankSettings.getMaxImageDownloadDimension();
/*     */       }
/*     */ 
/* 150 */       ImageAsset image = null;
/*     */ 
/* 152 */       image = (ImageAsset)this.m_assetManager.getAsset(null, lImageId, null, false, false);
/*     */ 
/* 155 */       if ((!userProfile.getIsAdmin()) && (!this.m_assetManager.userCanViewAsset(userProfile, image)))
/*     */       {
/* 157 */         this.m_logger.debug("This user does not have permission to view image id=" + lImageId);
/* 158 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */ 
/* 162 */       String sDownloadPath = null;
/*     */ 
/* 165 */       if ((AssetBankSettings.getWatermarkFullSize()) || (!ImageUtil.isWebImageFile(image.getFileLocation())) || (iSize > 0))
/*     */       {
/* 170 */         if ((image.getLargeImageFile() != null) && (iSize == AssetBankSettings.getLargeImageSize()) && (!AssetBankSettings.getWatermarkFullSize()))
/*     */         {
/* 174 */           sDownloadPath = image.getLargeImageFile().getPath();
/*     */         }
/*     */         else
/*     */         {
/* 180 */           String sFormat = "jpg";
/*     */ 
/* 182 */           ImageConversionInfo conversionInfo = new ImageConversionInfo();
/*     */ 
/* 186 */           if (ABImageMagick.getIsCMYK(image.getColorSpace()))
/*     */           {
/* 189 */             ColorSpace destinationColorSpace = this.m_usageManager.getColorSpace(a_dbTransaction, 1);
/* 190 */             ColorSpace currentColorSpace = this.m_usageManager.getCurrentColorSpace(image, a_dbTransaction);
/*     */ 
/* 192 */             conversionInfo.setConvertToColorSpace(destinationColorSpace);
/* 193 */             conversionInfo.setCurrentColorSpace(currentColorSpace);
/*     */           }
/*     */ 
/* 197 */           if (iSize > -1)
/*     */           {
/* 199 */             conversionInfo.setMaxWidth(iSize);
/* 200 */             conversionInfo.setMaxHeight(iSize);
/* 201 */             conversionInfo.setMaintainAspectRatio(true);
/*     */ 
/* 204 */             int iLayerNo = getIntParameter(a_request, "layer");
/*     */ 
/* 206 */             if (iLayerNo > 0)
/*     */             {
/* 208 */               conversionInfo.setLayerToConvert(iLayerNo);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 213 */           sDownloadPath = this.m_assetManager.getDownloadableAssetPath(image, sFormat, conversionInfo);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 220 */         sDownloadPath = image.getFileLocation();
/*     */       }
/*     */ 
/* 226 */       int iOrigWidth = 0;
/* 227 */       int iOrigHeight = 0;
/*     */ 
/* 229 */       if (image.getWidth() > image.getHeight())
/*     */       {
/* 231 */         iOrigWidth = iSize;
/* 232 */         iOrigHeight = iSize * image.getHeight() / image.getWidth();
/*     */       }
/*     */       else
/*     */       {
/* 236 */         iOrigHeight = iSize;
/* 237 */         iOrigWidth = iSize * image.getWidth() / image.getHeight();
/*     */       }
/*     */ 
/* 240 */       a_request.setAttribute("file", FileUtil.encryptFilepath(sDownloadPath));
/* 241 */       a_request.setAttribute("originalWidth", new Integer(iOrigWidth));
/* 242 */       a_request.setAttribute("originalHeight", new Integer(iOrigHeight));
/*     */ 
/* 244 */       if (image.getFormat().getCanConvertIndividualLayers())
/*     */       {
/* 246 */         a_request.setAttribute("numLayers", Integer.valueOf(image.getNumPages()));
/*     */       }
/*     */       else
/*     */       {
/* 250 */         a_request.setAttribute("numLayers", Integer.valueOf(1));
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Bn2Exception bn2)
/*     */     {
/* 256 */       this.m_logger.error("ViewFullSizedImageAction: " + bn2.getMessage());
/* 257 */       throw bn2;
/*     */     }
/*     */ 
/* 260 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public IAssetManager getAssetManager()
/*     */   {
/* 267 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 273 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*     */   {
/* 278 */     this.m_fileStoreManager = a_fileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager a_sUsageManager)
/*     */   {
/* 283 */     this.m_usageManager = a_sUsageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.PrintImageAction
 * JD-Core Version:    0.6.0
 */