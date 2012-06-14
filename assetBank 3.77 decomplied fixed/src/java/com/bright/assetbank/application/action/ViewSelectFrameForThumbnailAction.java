/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.VideoAsset;
/*     */ import com.bright.assetbank.application.bean.VideoInfo;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.VideoFrameSelectionForm;
/*     */ import com.bright.assetbank.application.service.VideoAssetManagerImpl;
/*     */ import com.bright.assetbank.application.util.VideoUtil;
/*     */ import com.bright.assetbank.user.exception.NoPermissionException;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.storage.bean.StorageDevice;
/*     */ import com.bright.framework.storage.service.StorageDeviceManager;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.io.File;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewSelectFrameForThumbnailAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  58 */   private VideoAssetManagerImpl m_assetManager = null;
/*  59 */   private FileStoreManager m_fileStoreManager = null;
/*  60 */   private StorageDeviceManager m_storageDeviceManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  77 */     VideoFrameSelectionForm form = (VideoFrameSelectionForm)a_form;
/*     */ 
/*  80 */     int iId = getIntParameter(a_request, "id");
/*  81 */     String sFileLocation = null;
/*  82 */     VideoAsset asset = null;
/*     */ 
/*  84 */     if (iId <= 0)
/*     */     {
/*  87 */       sFileLocation = a_request.getParameter("file");
/*     */     }
/*     */     else
/*     */     {
/*  91 */       asset = (VideoAsset)this.m_assetManager.getAsset(a_dbTransaction, iId, null, false);
/*  92 */       sFileLocation = asset.getOriginalFileLocation();
/*     */     }
/*     */ 
/*  96 */     int iPageNo = getIntParameter(a_request, "pageNo");
/*     */ 
/*  98 */     int iPerPage = AssetBankSettings.getVideoFrameSelectionPageSize();
/*     */ 
/* 100 */     int iStart = iPageNo * iPerPage;
/* 101 */     int iEnd = iStart + iPerPage;
/*     */ 
/* 103 */     String sDirName = a_request.getParameter("dirName");
/*     */ 
/* 105 */     if (sDirName.indexOf("..") >= 0)
/*     */     {
/* 107 */       throw new NoPermissionException("Unable to access temp directory");
/*     */     }
/*     */ 
/* 110 */     String sFileIndex = a_request.getParameter("index");
/* 111 */     String sDirectory = this.m_fileStoreManager.getAbsolutePath(sDirName);
/* 112 */     long lDeviceId = this.m_storageDeviceManager.getDeviceForRelativePath(sDirName).getId();
/*     */ 
/* 114 */     String sFile = null;
/*     */ 
/* 117 */     File f = new File(sDirectory);
/* 118 */     FileUtil.ensureDirectoryExists(f);
/*     */ 
/* 121 */     int iWidth = AssetBankSettings.getVideoFrameThumbnailWidth();
/*     */ 
/* 123 */     String sSourceFullPath = this.m_fileStoreManager.getAbsolutePath(sFileLocation);
/*     */ 
/* 126 */     VideoInfo info = asset;
/* 127 */     if (info == null)
/*     */     {
/* 129 */       info = VideoUtil.getVideoInfo(sSourceFullPath);
/*     */     }
/* 131 */     int iHeight = info.getDisplayHeight();
/* 132 */     float fModifier =1.0f* iWidth / info.getWidth();
              System.out.println("ihight :"+iHeight+" iWidth:"+iWidth+" fModifier:"+fModifier);
/* 133 */     iHeight = (int)(iHeight * fModifier);
/*     */     System.out.println("iHeight calculated :"+iHeight);
/* 136 */     if (iHeight % 2 != 0)
/*     */     {
/* 138 */       iHeight++;
/*     */     }
/*     */ 
/* 141 */     Vector vecFrames = new Vector();
/*     */ 
/* 143 */     for (int i = iStart; i < iEnd; i++)
/*     */     {
/* 146 */       String sFilename = "/thumb" + i + sFileIndex + "." + "png";
/* 147 */       String sLocation = this.m_fileStoreManager.getRealRelativePath(sDirName) + sFilename;
/* 148 */       if (lDeviceId > 0L)
/*     */       {
/* 150 */         sLocation = lDeviceId + ":" + sLocation;
/*     */       }
/* 152 */       sFile = sDirectory + sFilename;
/* 153 */       f = new File(sFile);
/*     */ 
/* 156 */       if (!f.exists())
/*     */       {
                    //
/* 158 */         VideoUtil.createThumbnailFromVideo(sSourceFullPath, i, iHeight, iWidth, sFile);
/*     */ 
/* 161 */         f = new File(sFile);
/*     */ 
/* 163 */         if (f.length() <= 0L)
/*     */         {
/* 166 */           form.setLast(true);
/* 167 */           break;
/*     */         }
/* 169 */         vecFrames.add(sLocation);
/*     */       }
/* 171 */       else if (f.length() > 0L)
/*     */       {
/* 173 */         vecFrames.add(sLocation);
/*     */       }
/*     */       else
/*     */       {
/* 177 */         form.setLast(true);
/* 178 */         break;
/*     */       }
/*     */     }
/* 181 */     form.setFrames(vecFrames);
/*     */ 
/* 183 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setVideoAssetManager(VideoAssetManagerImpl a_assetManager)
/*     */   {
/* 189 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*     */   {
/* 194 */     this.m_fileStoreManager = a_fileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setStorageDeviceManager(StorageDeviceManager a_storageDeviceManager)
/*     */   {
/* 199 */     this.m_storageDeviceManager = a_storageDeviceManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewSelectFrameForThumbnailAction
 * JD-Core Version:    0.6.0
 */