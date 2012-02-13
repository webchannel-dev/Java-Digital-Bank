/*     */ package com.bright.framework.storage.remote.amazon;
/*     */ 
/*     */ import com.amazonaws.AmazonServiceException;
/*     */ import com.amazonaws.auth.BasicAWSCredentials;
/*     */ import com.amazonaws.services.s3.AmazonS3;
/*     */ import com.amazonaws.services.s3.AmazonS3Client;
/*     */ import com.amazonaws.services.s3.model.CannedAccessControlList;
/*     */ import com.amazonaws.services.s3.model.GetObjectRequest;
/*     */ import com.amazonaws.services.s3.model.ObjectMetadata;
/*     */ import com.amazonaws.services.s3.model.PutObjectRequest;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.storage.bean.BaseRemoteStorageDevice;
/*     */ import com.bright.framework.storage.bean.CachedStorageDevice;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.storage.exception.UnsupportedStoredFileTypeException;
/*     */ import com.bright.framework.storage.util.StorageDeviceUtil;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ 
/*     */ public class AmazonS3StorageDevice extends BaseRemoteStorageDevice
/*     */   implements CachedStorageDevice
/*     */ {
/*     */   private String m_sBucketName;
/*     */ 
/*     */   public AmazonS3StorageDevice(String a_sFactoryClassName, String a_sFactoryDisplayName)
/*     */   {
/*  52 */     super(a_sFactoryClassName, a_sFactoryDisplayName);
/*     */   }
/*     */ 
/*     */   private synchronized String getBucketName()
/*     */   {
/*  62 */     if ((this.m_sBucketName == null) && (getHttpBaseUrl() != null))
/*     */     {
/*  64 */       this.m_sBucketName = getHttpBaseUrl().trim().toLowerCase();
/*  65 */       this.m_sBucketName = this.m_sBucketName.replaceAll("https?://", "");
/*  66 */       this.m_sBucketName = this.m_sBucketName.replace(".s3.amazonaws.com", "");
/*     */     }
/*  68 */     return this.m_sBucketName;
/*     */   }
/*     */ 
/*     */   public void setHttpBaseUrl(String a_sHttpBaseUrl)
/*     */   {
/*  74 */     super.setHttpBaseUrl(a_sHttpBaseUrl);
/*     */ 
/*  77 */     this.m_sBucketName = null;
/*     */   }
/*     */ 
/*     */   public void doStoreExistingFile(String a_sRelativePath)
/*     */     throws FileNotFoundException, IOException, Bn2Exception
/*     */   {
/*  84 */     doStoreNewFile(a_sRelativePath, null);
/*     */   }
/*     */ 
/*     */   public void doStoreNewFile(String a_sRelativePath, StoredFileType a_type)
/*     */     throws UnsupportedStoredFileTypeException, Bn2Exception
/*     */   {
/*  91 */     BasicAWSCredentials creds = new BasicAWSCredentials(getAuthenticationId(), getAuthenticationKey());
/*  92 */     AmazonS3 s3 = new AmazonS3Client(creds);
/*     */ 
/*  94 */     PutObjectRequest req = new PutObjectRequest(getBucketName(), StorageDeviceUtil.removeDevicePrefix(a_sRelativePath), new File(getFullLocalPath(a_sRelativePath)));
/*     */ 
/*  99 */     req.setCannedAcl(CannedAccessControlList.AuthenticatedRead);
/*     */ 
/* 102 */     ObjectMetadata metadata = new ObjectMetadata();
/* 103 */     String filename = new File(a_sRelativePath).getName();
/* 104 */     metadata.setContentDisposition("attachment; filename=" + filename);
/* 105 */     req.setMetadata(metadata);
/*     */ 
/* 107 */     s3.putObject(req);
/*     */   }
/*     */ 
/*     */   protected String generateUniqueFilename(String a_sDirectory, String a_sFilename, StoredFileType a_type)
/*     */     throws Bn2Exception
/*     */   {
/* 115 */     return a_sFilename;
/*     */   }
/*     */ 
/*     */   public String getHttpUrl(String a_sRelativePath, boolean testFileExists) throws FileNotFoundException, IOException
/*     */   {
/* 120 */     BasicAWSCredentials creds = new BasicAWSCredentials(getAuthenticationId(), getAuthenticationKey());
/* 121 */     AmazonS3 s3 = new AmazonS3Client(creds);
/* 122 */     String key = StorageDeviceUtil.removeDevicePrefix(a_sRelativePath);
/* 123 */     Calendar cal = Calendar.getInstance();
/* 124 */     cal.add(12, 1);
/* 125 */     Date expiryTime = new Date(cal.getTimeInMillis());
/*     */ 
/* 127 */     return s3.generatePresignedUrl(getBucketName(), key, expiryTime).toString();
/*     */   }
/*     */ 
/*     */   protected String getPathForHttpUrl(String a_sRelativePath)
/*     */   {
/* 142 */     return a_sRelativePath;
/*     */   }
/*     */ 
/*     */   public void doRunUsageScan()
/*     */   {
/* 148 */     setUsedSpaceInBytes(-1L);
/*     */   }
/*     */ 
/*     */   protected void prepareFileForLocalAccess(String a_sRelativePath)
/*     */     throws Bn2Exception
/*     */   {
/* 163 */     String sPath = buildFullLocalPath(a_sRelativePath);
/* 164 */     String key = StorageDeviceUtil.removeDevicePrefix(a_sRelativePath);
/*     */ 
/* 167 */     if (!new File(sPath).exists())
/*     */     {
/* 169 */       BasicAWSCredentials creds = new BasicAWSCredentials(getAuthenticationId(), getAuthenticationKey());
/* 170 */       AmazonS3 s3 = new AmazonS3Client(creds);
/*     */       try
/*     */       {
/* 174 */         FileUtil.ensureDirectoryExists(new File(FilenameUtils.getPathNoEndSeparator(sPath)));
/* 175 */         s3.getObject(new GetObjectRequest(getBucketName(), key), new File(sPath));
/*     */       }
/*     */       catch (AmazonServiceException e)
/*     */       {
/* 180 */         if (e.getStatusCode() != 404)
/*     */         {
/* 182 */           throw new Bn2Exception(getClass().getSimpleName() + ".prepareFileForLocalAccess() : Unexpected exception retrieving S3 content: " + e.getMessage(), e);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.remote.amazon.AmazonS3StorageDevice
 * JD-Core Version:    0.6.0
 */