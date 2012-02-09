/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class LogMissingFilesAction extends BTransactionAction
/*     */ {
/*  51 */   private IAssetManager m_assetManager = null;
/*  52 */   private FileStoreManager m_fileManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  79 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  82 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  84 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  85 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  88 */     this.m_logger.debug("LogMissingFilesAction: About to search for missing files");
/*     */     try
/*     */     {
/*  92 */       String sLowerId = a_request.getParameter("lowerId");
/*  93 */       String sUpperId = a_request.getParameter("upperId");
/*  94 */       this.m_logger.debug("LogMissingFilesAction: Got lower id: " + sLowerId + " and upper id :" + sUpperId);
/*     */ 
/*  96 */       Vector vecIds = null;
/*     */ 
/*  99 */       if ((StringUtil.stringIsPopulated(sLowerId)) && (StringUtil.stringIsPopulated(sUpperId)))
/*     */       {
/* 102 */         long lLower = Long.parseLong(sLowerId);
/* 103 */         long lUpper = Long.parseLong(sUpperId);
/* 104 */         long lAssetId = -1L;
/* 105 */         vecIds = new Vector();
/*     */ 
/* 108 */         for (lAssetId = lLower; lAssetId <= lUpper; lAssetId += 1L)
/*     */         {
/* 110 */           vecIds.add(new Long(lAssetId));
/*     */         }
/*     */       }
/*     */ 
/* 114 */       Asset asset = null;
/*     */ 
/* 117 */       if ((vecIds != null) && (vecIds.size() > 0))
/*     */       {
/* 119 */         this.m_logger.warn("LogMissingFilesAction: About to check files for " + vecIds.size() + " assets");
/* 120 */         long lAssetId = -1L;
/*     */ 
/* 122 */         for (int i = 0; i < vecIds.size(); i++)
/*     */         {
/*     */           try
/*     */           {
/* 126 */             lAssetId = ((Long)vecIds.elementAt(i)).longValue();
/* 127 */             asset = this.m_assetManager.getAsset(a_transaction, lAssetId, null, false, false);
/*     */ 
/* 129 */             if (asset != null)
/*     */             {
/* 132 */               if (StringUtil.stringIsPopulated(asset.getFileLocation()))
/*     */               {
/* 134 */                 String sFullPath = this.m_fileManager.getAbsolutePath(asset.getFileLocation());
/* 135 */                 File tempFile = new File(sFullPath);
/* 136 */                 if (!tempFile.exists())
/*     */                 {
/* 138 */                   this.m_logger.warn("LogMissingFilesAction: Unable to find file for asset: " + asset.getId() + " : Expected file location: " + sFullPath);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 145 */             this.m_logger.error("LogMissingFilesAction: Unable to check file status for asset " + lAssetId + " : " + e.getMessage());
/*     */           }
/*     */ 
/* 148 */           if ((i == 0) || (i % 1000 != 0))
/*     */             continue;
/* 150 */           this.m_logger.warn("LogMissingFilesAction: Checked batch of 1000 asset ids");
/*     */ 
/* 153 */           a_transaction.commit();
/* 154 */           a_transaction = this.m_transactionManager.getNewTransaction();
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 161 */       this.m_logger.error("LogMissingFilesAction: Fatal Error: " + e.getMessage());
/*     */     }
/* 163 */     this.m_logger.warn("LogMissingFilesAction: Finished!");
/*     */ 
/* 166 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/* 171 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_fileManager)
/*     */   {
/* 176 */     this.m_fileManager = a_fileManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.LogMissingFilesAction
 * JD-Core Version:    0.6.0
 */