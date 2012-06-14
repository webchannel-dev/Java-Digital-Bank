/*    */ package com.bright.framework.storage.remote.amazon;
/*    */ 
/*    */ import com.bright.framework.storage.service.BaseRemoteStorageDeviceFactory;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class AmazonS3StorageDeviceFactory extends BaseRemoteStorageDeviceFactory<AmazonS3StorageDevice>
/*    */ {
/*    */   public static final String k_sName = "Amazon S3";
/*    */ 
/*    */   public AmazonS3StorageDevice createNewStorageDevice()
/*    */   {
/* 33 */     return new AmazonS3StorageDevice(getClass().getName(), getDisplayName());
/*    */   }
/*    */ 
/*    */   public String getDisplayName()
/*    */   {
/* 38 */     return "Amazon S3";
/*    */   }
/*    */ 
/*    */   public List<String> validateStorageDevice(AmazonS3StorageDevice a_device)
/*    */   {
/* 52 */     List messages = super.validateStorageDevice(a_device);
/*    */ 
/* 54 */     if (StringUtils.isNotEmpty(a_device.getHttpBaseUrl()))
/*    */     {
/* 56 */       String sBaseUrl = a_device.getHttpBaseUrl();
/*    */ 
/* 58 */       if (!sBaseUrl.toLowerCase().matches("https?://[a-z\\-\\.]+\\.s3\\.amazonaws\\.com"))
/*    */       {
/* 60 */         messages.add("The http base url for an Amazon S3 storage device should be: http(s)://[BUCKET-NAME].s3.amazonaws.com");
/*    */       }
/*    */     }
/*    */ 
/* 64 */     return messages;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.remote.amazon.AmazonS3StorageDeviceFactory
 * JD-Core Version:    0.6.0
 */