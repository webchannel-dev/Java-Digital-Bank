/*     */ package com.bright.assetbank.application.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.UploadFileForm;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.batch.upload.bean.BatchBulkUploadInfo;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.workflow.bean.WorkflowUpdate;
/*     */ import com.bright.framework.common.bean.BrightDateTime;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class UploadUtil
/*     */ {
/*  63 */   private static Log k_sLogger = GlobalApplication.getInstance().getLogger();
/*     */ 
/*     */   public static void setUploadToolOption(HttpServletRequest a_request, UploadFileForm a_form)
/*     */   {
/*  68 */     String sUploadToolOption = a_request.getParameter("uploadToolOption");
/*     */ 
/*  71 */     if (sUploadToolOption == null)
/*     */     {
/*  73 */       if (AssetBankSettings.getSingleUploadUseAdvancedByDefault())
/*     */       {
/*  75 */         sUploadToolOption = "advanced";
/*     */       }
/*  77 */       else sUploadToolOption = "basic";
/*     */ 
/*     */     }
/*     */ 
/*  83 */     if (sUploadToolOption.equals("advanced"))
/*     */     {
/*  85 */       if ((AssetBankSettings.getBulkUploadUseFlash()) && (!AssetBankSettings.getBulkUploadUseApplet()))
/*     */       {
/*  87 */         sUploadToolOption = "flash";
/*     */       }
/*     */ 
/*  90 */       if ((AssetBankSettings.getBulkUploadUseApplet()) && (!AssetBankSettings.getBulkUploadUseFlash()))
/*     */       {
/*  92 */         sUploadToolOption = "applet";
/*     */       }
/*     */     }
/*     */ 
/*  96 */     a_form.setUploadToolOption(sUploadToolOption);
/*     */   }
/*     */ 
/*     */   public static String getUploadDirectory(HttpSession a_session, boolean a_bSingleUpload, boolean a_bImportFilesToExistingAssets)
/*     */   {
/* 107 */     String sFullFilePath = null;
/* 108 */     String sCurrentUser = null;
/*     */ 
/* 111 */     if (FrameworkSettings.getUseRelativeDirectories())
/*     */     {
/* 113 */       sFullFilePath = AssetBankSettings.getApplicationPath() + "/";
/*     */     }
/*     */     else
/*     */     {
/* 117 */       sFullFilePath = "";
/*     */     }
/*     */ 
/* 120 */     if (a_bSingleUpload)
/*     */     {
/* 122 */       sFullFilePath = sFullFilePath + AssetBankSettings.getSingleUploadDirectory();
/*     */     }
/*     */     else
/*     */     {
/* 126 */       sFullFilePath = sFullFilePath + AssetBankSettings.getBulkUploadDirectory();
/*     */     }
/*     */ 
/* 130 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_session);
/*     */ 
/* 133 */     sCurrentUser = userProfile.getUser().getUsername();
/*     */ 
/* 135 */     sFullFilePath = sFullFilePath + "/" + FileUtil.getSafeFilename(sCurrentUser, true) + "/";
/*     */ 
/* 137 */     if (a_bSingleUpload)
/*     */     {
/* 139 */       sFullFilePath = sFullFilePath + a_session.getId() + "/";
/*     */       try
/*     */       {
/* 143 */         FileUtils.forceMkdir(new File(sFullFilePath));
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 147 */         GlobalApplication.getInstance().getLogger().error("BulkUploadServlet.getUploadDirectory() : Could not create single upload directory : " + e.getMessage(), e);
/*     */       }
/*     */     }
/*     */ 
/* 151 */     if (a_bImportFilesToExistingAssets)
/*     */     {
/* 153 */       sFullFilePath = sFullFilePath + "files_for_existing_assets/";
/*     */       try
/*     */       {
/* 157 */         FileUtils.forceMkdir(new File(sFullFilePath));
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 161 */         GlobalApplication.getInstance().getLogger().error("BulkUploadServlet.getUploadDirectory() : Could not create directory for importing files to existing assets : " + e.getMessage(), e);
/*     */       }
/*     */     }
/*     */ 
/* 165 */     return sFullFilePath;
/*     */   }
/*     */ 
/*     */   public static void storeUploadedFile(String a_filename, InputStream a_inStream, ABUserProfile a_userProfile, HttpSession a_session)
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/* 179 */       File dir = new File(a_userProfile.getSingleUploadDir() + "/" + a_session.getId());
/*     */ 
/* 181 */       if (!dir.exists())
/*     */       {
/* 183 */         dir.mkdirs();
/*     */       }
/*     */ 
/* 186 */       OutputStream os = new BufferedOutputStream(new FileOutputStream(a_userProfile.getSingleUploadDir() + "/" + a_session.getId() + "/" + a_filename));
/* 187 */       InputStream is = new BufferedInputStream(a_inStream);
/*     */ 
/* 189 */       IOUtils.copy(is, os);
/*     */ 
/* 191 */       is.close();
/* 192 */       os.close();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 196 */       k_sLogger.error("Could not store uploaded file in user's single upload directory: " + a_userProfile.getSingleUploadDir());
/* 197 */       throw new Bn2Exception("Could not store uploaded file in user's single upload directory: " + a_userProfile.getSingleUploadDir(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static File getUploadedFile(ABUserProfile a_userProfile, HttpSession a_session)
/*     */   {
/* 210 */     File file = null;
/*     */ 
/* 212 */     if (a_userProfile != null)
/*     */     {
/* 214 */       File dir = new File(a_userProfile.getSingleUploadDir() + "/" + a_session.getId());
/*     */ 
/* 216 */       if (dir.exists())
/*     */       {
/* 218 */         File[] files = dir.listFiles();
/*     */ 
/* 220 */         if (files.length > 1)
/*     */         {
/* 222 */           Arrays.sort(files, new Comparator()
/*     */           {
/*     */             public int compare(Object file1, Object file2)
/*     */             {
/* 226 */               if (((file1 instanceof File)) && ((file2 instanceof File)))
/*     */               {
/* 228 */                 return ((File)file1).lastModified() > ((File)file2).lastModified() ? -1 : 1;
/*     */               }
/* 230 */               return 0;
/*     */             }
/*     */           });
/*     */         }
/* 235 */         if (files.length > 0)
/*     */         {
/* 237 */           file = files[0];
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 242 */     return file;
/*     */   }
/*     */ 
/*     */   public static File clearSingleUploadDir(ABUserProfile a_userProfile, HttpSession a_session)
/*     */     throws Bn2Exception
/*     */   {
/* 254 */     File file = null;
/*     */ 
/* 256 */     if (a_userProfile != null)
/*     */     {
/*     */       try
/*     */       {
/* 260 */         File dir = new File(a_userProfile.getSingleUploadDir() + "/" + a_session.getId());
/*     */ 
/* 262 */         if (dir.exists())
/*     */         {
/* 264 */           FileUtils.cleanDirectory(dir);
/*     */         }
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 269 */         k_sLogger.error("Could not clear user's single upload directory: " + a_userProfile.getSingleUploadDir());
/* 270 */         throw new Bn2Exception("Could not clear user's single upload directory: " + a_userProfile.getSingleUploadDir(), e);
/*     */       }
/*     */     }
/*     */ 
/* 274 */     return file;
/*     */   }
/*     */ 
/*     */   public static BatchBulkUploadInfo prepPlaceholderBatchInfo(Asset a_metaData, int a_iQuantity, WorkflowUpdate a_update, ABUserProfile a_userProfile, boolean a_bLinkAssets, Vector<Attribute> a_vecAllAttributes, long a_lSourcePeerId)
/*     */   {
/* 299 */     GregorianCalendar now = new GregorianCalendar();
/* 300 */     BrightDateTime bdtDate = new BrightDateTime();
/* 301 */     bdtDate.setDate(now.getTime());
/* 302 */     bdtDate.zeroMillis();
/* 303 */     a_metaData.setBulkUploadTimestamp(bdtDate.getDate());
/*     */ 
/* 306 */     BatchBulkUploadInfo batchInfo = new BatchBulkUploadInfo();
/* 307 */     batchInfo.setUser((ABUser)a_userProfile.getUser());
/* 308 */     batchInfo.setUserProfile(a_userProfile);
/* 309 */     batchInfo.setAssetMetadata(a_metaData);
/* 310 */     batchInfo.setAssetAttributes(a_vecAllAttributes);
/* 311 */     batchInfo.setNumAssetsToAdd(a_iQuantity);
/* 312 */     batchInfo.setLinkAssets(a_bLinkAssets);
/* 313 */     batchInfo.setAddPlaceholders(true);
/* 314 */     batchInfo.setWorkflowUpdate(a_update);
/* 315 */     batchInfo.setSourcePeerId(a_lSourcePeerId);
/*     */ 
/* 317 */     return batchInfo;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.util.UploadUtil
 * JD-Core Version:    0.6.0
 */