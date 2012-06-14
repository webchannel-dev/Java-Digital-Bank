/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.RemoteStorageRequest;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.form.EmailForm;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.mail.service.EmailManager;
          import com.bright.framework.queue.bean.MessageBatchMonitor;
/*     */ import com.bright.framework.queue.bean.MessageBatchMonitor.Status;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.service.RemoteStoreManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.ServletUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.mail.EmailAttachment;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class EmailAssetAction extends BTransactionAction
/*     */   implements AssetBankConstants, FrameworkConstants
/*     */ {
/*     */   private EmailManager m_emailManager;
/*     */   private FileStoreManager m_fileStoreManager;
/*     */   private RemoteStoreManager m_remoteStoreManager;
/* 242 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  81 */     EmailForm form = (EmailForm)a_form;
/*  82 */     ABUserProfile profile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  83 */     User user = profile.getUser();
/*     */ 
/*  85 */     if (a_request.getMethod() == "GET")
/*     */     {
/*  88 */       long assetId = getLongParameter(a_request, "assetId");
/*  89 */       long queuedTaskId = getLongParameter(a_request, "queuedTaskId");
/*  90 */       MessageBatchMonitor.Status status = this.m_remoteStoreManager.getStatus(queuedTaskId);
/*     */ 
/*  92 */       a_request.setAttribute("messages", this.m_remoteStoreManager.getMessages(queuedTaskId));
/*  93 */       a_request.setAttribute("assetId", Long.valueOf(assetId));
/*  94 */       a_request.setAttribute("queuedTaskId", Long.valueOf(queuedTaskId));
/*     */ 
/*  96 */       if (status == MessageBatchMonitor.Status.SUCCESS)
/*     */       {
/*  98 */         return a_mapping.findForward("Success");
/*     */       }
/* 100 */       if (status == MessageBatchMonitor.Status.FAILURE)
/*     */       {
/* 102 */         return a_mapping.findForward("Failure");
/*     */       }
/* 104 */       if (status == MessageBatchMonitor.Status.RUNNING)
/*     */       {
/* 106 */         return a_mapping.findForward("InProgress");
/*     */       }
/* 108 */       if (status == null)
/*     */       {
/* 110 */         throw new Bn2Exception("Upload task not found");
/*     */       }
/*     */     }
/*     */ 
/* 114 */     a_request.setAttribute("assetId", Long.valueOf(form.getAsset().getId()));
/* 115 */     String sAbsFilePath = this.m_fileStoreManager.getAbsolutePath(form.getFileId());
/*     */ 
/* 118 */     String[] recipients = form.getRecipientArray();
/*     */ 
/* 120 */     boolean isValidRecipient = recipients.length > 0;
/* 121 */     int i = 0;
/* 122 */     while ((isValidRecipient) && (i < recipients.length))
/*     */     {
/* 124 */       isValidRecipient = (isValidRecipient) && (StringUtil.isValidEmailAddress(recipients[(i++)]));
/*     */     }
/*     */ 
/* 127 */     if (!isValidRecipient)
/*     */     {
/* 129 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationRecipients", profile.getCurrentLanguage()).getBody());
/* 130 */       return a_mapping.findForward("ValidationFailure");
/*     */     }
/*     */ 
/* 136 */     Map params = new HashMap();
/* 137 */     params.put("recipients", form.getRecipients());
/* 138 */     params.put("filename", form.getFilename());
/* 139 */     params.put("message", form.getMessage());
/*     */ 
/* 141 */     String sUsername = "";
/* 142 */     String sEmail = "noreply@noreply.com";
/* 143 */     if (user != null)
/*     */     {
/* 145 */       sUsername = user.getUsername();
/* 146 */       sEmail = user.getEmailAddress();
/*     */     }
/* 148 */     params.put("username", sUsername);
/* 149 */     params.put("from", sEmail);
/*     */ 
/* 151 */     EmailAttachment[] attachments = null;
/*     */ 
/* 153 */     if ((!form.getAssetIsAttachable()) || (form.getAssetLinkedNotAttached()))
/*     */     {
/* 155 */       params.put("template", "asset_linked");
/*     */ 
/* 157 */       if (this.m_remoteStoreManager.usesRemoteStorage())
/*     */       {
/* 161 */         String filepath = this.m_fileStoreManager.getAbsolutePath(form.getFileId());
/* 162 */         RemoteStorageRequest remoteStorageRequest = new RemoteStorageRequest(filepath, profile, params);
/* 163 */         this.m_remoteStoreManager.queueStorageRequest(remoteStorageRequest);
/*     */ 
/* 165 */         a_request.setAttribute("queuedTaskId", remoteStorageRequest.getRequestId());
/* 166 */         return a_mapping.findForward("InProgress");
/*     */       }
/*     */ 
/* 173 */       String sSharePath = this.m_fileStoreManager.getUniqueFilepath(FileUtil.getFilename(form.getFileId()), StoredFileType.SHARED);
/*     */ 
/* 176 */       String sUrl = ServletUtil.getApplicationUrl(a_request) + "/action/viewDownloadSharedAsset?downloadFile=" + FileUtil.encryptFilepath(sSharePath);
/*     */ 
/* 179 */       String sSharePathAbs = this.m_fileStoreManager.getAbsolutePath(sSharePath);
/* 180 */       File tempFile = new File(sAbsFilePath);
/* 181 */       File shareFile = new File(sSharePathAbs);
/*     */ 
/* 184 */       if (form.getFileId().startsWith(FrameworkSettings.getTemporaryDirectory()))
/*     */       {
/* 187 */         if (!tempFile.renameTo(shareFile))
/*     */         {
/* 190 */           FileUtil.copyFile(sAbsFilePath, sSharePathAbs);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 196 */         FileUtil.copyFile(sAbsFilePath, sSharePathAbs);
/*     */       }
/*     */ 
/* 199 */       params.put("url", sUrl);
/*     */ 
/* 202 */       this.m_emailManager.sendTemplatedEmail(params, attachments, false, profile.getCurrentLanguage());
/* 203 */       return a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 210 */     params.put("template", "asset_attached");
/*     */ 
/* 213 */     EmailAttachment attachment = new EmailAttachment();
/*     */ 
/* 215 */     attachment.setDescription("file");
/* 216 */     attachment.setPath(sAbsFilePath);
/*     */ 
/* 218 */     attachments = new EmailAttachment[] { attachment };
/*     */ 
/* 221 */     this.m_emailManager.sendTemplatedEmail(params, attachments, false, profile.getCurrentLanguage());
/* 222 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setEmailManager(EmailManager a_emailManager)
/*     */   {
/* 229 */     this.m_emailManager = a_emailManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*     */   {
/* 234 */     this.m_fileStoreManager = a_fileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setRemoteStoreManager(RemoteStoreManager a_remoteStoreManager)
/*     */   {
/* 239 */     this.m_remoteStoreManager = a_remoteStoreManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 245 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.EmailAssetAction
 * JD-Core Version:    0.6.0
 */