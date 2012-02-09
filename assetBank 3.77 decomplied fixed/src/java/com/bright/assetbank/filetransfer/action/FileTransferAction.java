/*     */ package com.bright.assetbank.filetransfer.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.filetransfer.form.FileTransferForm;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.RC4CipherUtil;
/*     */ import com.bright.framework.util.ServletUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class FileTransferAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*     */   private static final String c_ksClassName = "FileTransferAction";
/*     */   private EmailManager m_emailManager;
/*     */   private FileStoreManager m_fileStoreManager;
/*     */   private ListManager m_listManager;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  69 */     FileTransferForm form = (FileTransferForm)a_form;
/*  70 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  73 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  75 */       this.m_logger.debug("This user does not have permission to view the file transfer page");
/*  76 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  80 */     String[] recipients = form.getRecipientArray();
/*     */ 
/*  82 */     boolean isValidRecipient = recipients.length > 0;
/*  83 */     int i = 0;
/*  84 */     while ((isValidRecipient) && (i < recipients.length))
/*     */     {
/*  86 */       isValidRecipient = (isValidRecipient) && (StringUtil.isValidEmailAddress(recipients[(i++)]));
/*     */     }
/*     */ 
/*  89 */     if (!isValidRecipient)
/*     */     {
/*  91 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationRecipients", userProfile.getCurrentLanguage()).getBody());
/*  92 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  97 */     User user = userProfile.getUser();
/*     */ 
/* 100 */     HashMap params = new HashMap();
/* 101 */     params.put("recipients", form.getRecipients());
/* 102 */     params.put("filename", form.getFilename());
/* 103 */     params.put("message", form.getMessage());
/* 104 */     params.put("username", user.getUsername());
/* 105 */     params.put("from", user.getEmailAddress());
/*     */ 
/* 108 */     String sTempPath = form.getFileLocation();
/*     */ 
/* 111 */     String sSharePath = this.m_fileStoreManager.getUniqueFilepath(FileUtil.getFilename(sTempPath), StoredFileType.SHARED);
/*     */ 
/* 114 */     String sTempPathAbs = this.m_fileStoreManager.getAbsolutePath(sTempPath);
/* 115 */     String sSharePathAbs = this.m_fileStoreManager.getAbsolutePath(sSharePath);
/* 116 */     File tempFile = new File(sTempPathAbs);
/* 117 */     File shareFile = new File(sSharePathAbs);
/*     */ 
/* 120 */     if (!tempFile.renameTo(shareFile))
/*     */     {
/* 123 */       FileUtil.copyFile(sTempPathAbs, sSharePathAbs);
/* 124 */       if (!tempFile.delete())
/*     */       {
/* 126 */         this.m_logger.error("FileTransferAction: could not delete temp file " + tempFile);
/*     */       }
/*     */       else
/*     */       {
/* 130 */         FileUtil.logFileDeletion(tempFile);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 135 */     StringBuilder sbUrl = new StringBuilder(ServletUtil.getApplicationUrl(a_request) + "/action/viewDownloadSharedAsset?downloadFile=" + FileUtil.encryptFilepath(sSharePath));
/*     */ 
/* 138 */     if (form.getNotify())
/*     */     {
/* 140 */       sbUrl.append("&notify=" + RC4CipherUtil.encryptToHex(new StringBuilder().append(user.getId()).append(":").append(form.getRecipients()).toString()));
/*     */     }
/*     */ 
/* 143 */     String sUrl = sbUrl.toString();
/*     */ 
/* 145 */     params.put("template", "file_transfer");
/* 146 */     params.put("url", sUrl);
/*     */ 
/* 149 */     this.m_emailManager.sendTemplatedEmail(params, false, userProfile.getCurrentLanguage());
/*     */ 
/* 151 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public EmailManager getEmailManager()
/*     */   {
/* 157 */     return this.m_emailManager;
/*     */   }
/*     */ 
/*     */   public void setEmailManager(EmailManager a_emailManager)
/*     */   {
/* 162 */     this.m_emailManager = a_emailManager;
/*     */   }
/*     */ 
/*     */   public FileStoreManager getFileStoreManager()
/*     */   {
/* 167 */     return this.m_fileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*     */   {
/* 172 */     this.m_fileStoreManager = a_fileStoreManager;
/*     */   }
/*     */ 
/*     */   public ListManager getListManager()
/*     */   {
/* 177 */     return this.m_listManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_listManager)
/*     */   {
/* 182 */     this.m_listManager = a_listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.filetransfer.action.FileTransferAction
 * JD-Core Version:    0.6.0
 */