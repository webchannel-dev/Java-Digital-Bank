/*    */ package com.bright.assetbank.application.service;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.bean.ImageConversionRequest;
/*    */ import com.bright.assetbank.application.util.DownloadUtil;
/*    */ import com.bright.assetbank.attribute.service.AttributeManager;
/*    */ import com.bright.framework.queue.bean.QueuedItem;
/*    */ import com.bright.framework.queue.service.QueueManager;
/*    */ import com.bright.framework.service.FileStoreManager;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public class DownloadImageQueueManager extends QueueManager
/*    */ {
/*    */   private ImageAssetManagerImpl m_imageManager;
/* 42 */   private AttributeManager m_attributeManager = null;
/* 43 */   private FileStoreManager m_fileStoreManager = null;
/*    */ 
/*    */   public void processQueueItem(QueuedItem a_item)
/*    */     throws Bn2Exception
/*    */   {
/* 55 */     ImageConversionRequest conversion = (ImageConversionRequest)a_item;
/*    */ 
/* 57 */     this.m_imageManager.doConversion(conversion);
/*    */ 
/* 59 */     DownloadUtil.embedMetadata(conversion.getRelativeTempDownloadFilePath(), conversion.getImage(), false, false, conversion.getConversionInfo(), this.m_attributeManager, this.m_fileStoreManager);
/*    */ 
/* 61 */     String sAbsoluteTempDownloadFilePath = this.m_fileStoreManager.getAbsolutePath(conversion.getRelativeTempDownloadFilePath());
/*    */ 
/* 63 */     File file = new File(sAbsoluteTempDownloadFilePath + ".ready");
/*    */     try
/*    */     {
/* 66 */       file.createNewFile();
/*    */     }
/*    */     catch (IOException ioe)
/*    */     {
/* 70 */       this.m_logger.error("DownloadImageQueueManager.processQueueItem() : IOException whilst creating file to denote conversion completion");
/*    */     }
/*    */   }
/*    */ 
/*    */   public void setImageManager(ImageAssetManagerImpl a_sImageManager)
/*    */   {
/* 76 */     this.m_imageManager = a_sImageManager;
/*    */   }
/*    */ 
/*    */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*    */   {
/* 81 */     this.m_fileStoreManager = a_fileStoreManager;
/*    */   }
/*    */ 
/*    */   public void setAttributeManager(AttributeManager a_attributeManager)
/*    */   {
/* 86 */     this.m_attributeManager = a_attributeManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.DownloadImageQueueManager
 * JD-Core Version:    0.6.0
 */