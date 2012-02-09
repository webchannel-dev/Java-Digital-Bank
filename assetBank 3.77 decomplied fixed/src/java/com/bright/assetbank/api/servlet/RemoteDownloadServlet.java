/*     */ package com.bright.assetbank.api.servlet;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.api.constant.ApiConstants;
/*     */ import com.bright.assetbank.api.exception.ApiException;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.ImageConversionInfo;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
          import com.bright.assetbank.application.service.AssetManager;
/*     *
/*     */ import com.bright.assetbank.application.util.DownloadUtil;
/*     */ import com.bright.assetbank.cmsintegration.util.FileTransferHelper;
/*     */ import com.bright.assetbank.cmsintegration.util.FileTransferHelperFactory;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.io.Writer;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class RemoteDownloadServlet extends ApiServlet
/*     */   implements ApiConstants
/*     */ {
/*  73 */   private FileStoreManager m_fileStoreManager = null;
/*  74 */   private UsageManager m_usageManager = null;
/*     */ 
/*     */   public void init() throws ServletException
/*     */   {
/*  78 */     super.init();
/*     */     try
/*     */     {
/*  82 */       this.m_fileStoreManager = ((FileStoreManager)GlobalApplication.getInstance().getComponentManager().lookup("FileStoreManager"));
/*  83 */       this.m_usageManager = ((UsageManager)GlobalApplication.getInstance().getComponentManager().lookup("UsageManager"));
/*     */     }
/*     */     catch (ComponentException e)
/*     */     {
/*  87 */       throw new ServletException("ComponentException whilst getting FileStoreManager in RemoteDownloadServlet : " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void performAction(HttpServletRequest a_request, Writer a_writer)
/*     */     throws ApiException
/*     */   {
/* 101 */     String sAssetId = a_request.getParameter("assetId");
/* 102 */     long lAssetId = 0L;
/* 103 */     FileTransferHelper ftHelper = null;
/* 104 */     String sFilename = null;
/* 105 */     String sSource = null;
/*     */     try
/*     */     {
/* 110 */       if (StringUtils.isEmpty(sAssetId))
/*     */       {
/* 112 */         throw new ApiException("The assetId parameter is required");
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 118 */         lAssetId = Long.parseLong(sAssetId);
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/* 122 */         throw new ApiException("Asset id '" + sAssetId + "' could not be parsed to a long.");
/*     */       }
/*     */ 
/* 126 */       String sUploadLocation = a_request.getParameter("uploadLocation");
/*     */ 
/* 128 */       AssetManager assetManager = (AssetManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetManager");
/* 129 */       Asset asset = assetManager.getAsset(null, lAssetId, null, false, false);
/*     */ 
/* 131 */       if (asset == null)
/*     */       {
/* 133 */         throw new ApiException("No asset found with id '" + lAssetId + "'");
/*     */       }
/*     */ 
/* 137 */       if (!asset.getIsImage())
/*     */       {
/* 139 */         sFilename = DownloadUtil.getDownloadFilename(asset, null);
/* 140 */         sSource = asset.getOriginalFileLocation();
/*     */ 
/* 142 */         if (StringUtils.isEmpty(sSource))
/*     */         {
/* 144 */           sSource = asset.getFileLocation();
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 150 */         int iHeight = 0;
/* 151 */         int iWidth = 0;
/*     */         try
/*     */         {
/* 154 */           iHeight = Integer.parseInt(a_request.getParameter("height"));
/* 155 */           iWidth = Integer.parseInt(a_request.getParameter("width"));
/*     */         }
/*     */         catch (NumberFormatException e)
/*     */         {
/* 159 */           throw new ApiException("Both height and width parameters must be present and must be numeric");
/*     */         }
/*     */ 
/* 163 */         if ((iHeight <= 0) || (iWidth <= 0))
/*     */         {
/* 165 */           throw new ApiException("Both height and width parameters must be > 0");
/*     */         }
/*     */ 
/* 168 */         if ((iHeight > AssetBankSettings.getMaxImageDownloadDimension()) || (iWidth > AssetBankSettings.getMaxImageDownloadDimension()))
/*     */         {
/* 170 */           throw new ApiException("Both width and height parameters must be below the configured maximum (" + AssetBankSettings.getMaxImageDownloadDimension() + " pixels)");
/*     */         }
/*     */ 
/* 173 */         float fQuality = AssetBankSettings.getJpgConversionQuality();
/* 174 */         if (StringUtils.isNotEmpty(a_request.getParameter("jpegQuality")))
/*     */         {
/*     */           try
/*     */           {
/* 178 */             fQuality = Float.parseFloat(a_request.getParameter("jpegQuality"));
/*     */           }
/*     */           catch (NumberFormatException e)
/*     */           {
/* 182 */             throw new ApiException("If present, the jpegQuality parameter must be a valid floating point number");
/*     */           }
/*     */         }
/*     */ 
/* 186 */         String sFormatExt = a_request.getParameter("format");
/*     */ 
/* 188 */         if (StringUtils.isNotEmpty(sFormatExt))
/*     */         {
/* 190 */           sFilename = DownloadUtil.getDownloadFilename(asset, sFormatExt);
/*     */         }
/*     */         else
/*     */         {
/* 194 */           sFilename = DownloadUtil.getDownloadFilename(asset, null);
/* 195 */           sFormatExt = FileUtil.getSuffix(sFilename);
/*     */         }
/*     */ 
/* 198 */         ImageConversionInfo conversionInfo = new ImageConversionInfo();
/* 199 */         conversionInfo.setJpegQuality(fQuality);
/* 200 */         conversionInfo.setMaxHeight(iHeight);
/* 201 */         conversionInfo.setMaxWidth(iWidth);
/* 202 */         conversionInfo.setMaintainAspectRatio(Boolean.parseBoolean(a_request.getParameter("maintainAspectRatio")));
/* 203 */         conversionInfo.setDeferAllowed(false);
/*     */ 
/* 205 */         if (Boolean.parseBoolean(a_request.getParameter("convertToRGB")))
/*     */         {
/* 207 */           conversionInfo.setConvertToColorSpace(this.m_usageManager.getColorSpace(null, 1));
/* 208 */           conversionInfo.setCurrentColorSpace(this.m_usageManager.getColorSpace(null, 2));
/*     */         }
/*     */ 
/* 211 */         conversionInfo.setScaleUp(Boolean.parseBoolean(a_request.getParameter("upscale")));
/*     */ 
/* 213 */         sSource = assetManager.getDownloadableAssetPath(asset, sFormatExt, conversionInfo);
/*     */       }
/*     */ 
/* 217 */       String sFileTransferMethod = a_request.getParameter("transferMethod");
/* 218 */       sSource = this.m_fileStoreManager.getAbsolutePath(sSource);
/*     */ 
/* 220 */       String sHost = a_request.getParameter("host");
/* 221 */       String sPort = a_request.getParameter("port");
/* 222 */       int iPort = -1;
/* 223 */       if (sPort != null)
/*     */       {
/* 225 */         iPort = Integer.parseInt(sPort);
/*     */       }
/* 227 */       String sUsername = a_request.getParameter("username");
/* 228 */       String sPassword = a_request.getParameter("password");
/*     */ 
/* 231 */       ftHelper = FileTransferHelperFactory.createHelper(sFileTransferMethod);
/*     */ 
/* 234 */       ftHelper.connect(sHost, iPort, sUsername, sPassword);
/*     */ 
/* 236 */       boolean bEnsureFilenameUnique = AssetBankSettings.getApiRemoteDownloadDestinationFilenamesUnique();
/*     */ 
/* 238 */       if (StringUtils.isNotEmpty(a_request.getParameter("filenameUnique")))
/*     */       {
/* 240 */         bEnsureFilenameUnique = Boolean.parseBoolean(a_request.getParameter("filenameUnique"));
/*     */       }
/*     */ 
/* 243 */       String sRemoteFilename = ftHelper.copyFile(sSource, sUploadLocation, sFilename, bEnsureFilenameUnique);
/* 244 */       a_writer.write(sRemoteFilename);
/*     */     }
/*     */     catch (Throwable t)
/*     */     {
/* 248 */       throw new ApiException(t.getMessage());
/*     */     }
/*     */     finally
/*     */     {
/* 252 */       if (ftHelper != null)
/*     */       {
/* 254 */         ftHelper.disconnect();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected String getContentType()
/*     */   {
/* 261 */     return "text/plain";
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.api.servlet.RemoteDownloadServlet
 * JD-Core Version:    0.6.0
 */