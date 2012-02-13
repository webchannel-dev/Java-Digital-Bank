/*     */ package com.bright.assetbank.workflow.processor;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.workflow.bean.AssetWorkflowAuditEntry;
/*     */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import com.bright.framework.workflow.bean.AlertInfo;
/*     */ import com.bright.framework.workflow.bean.StateAlert;
/*     */ import com.bright.framework.workflow.bean.WorkflowInfo;
/*     */ import com.bright.framework.workflow.processor.SendAlertProcessorImpl;
/*     */ import java.util.HashMap;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class SendToOwnerAlertProcessor extends SendAlertProcessorImpl
/*     */ {
/*  46 */   private IAssetManager m_assetManager = null;
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager) {
/*  49 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void sendAlert(DBTransaction a_dbTransaction, AlertInfo a_alertInfo)
/*     */     throws Bn2Exception
/*     */   {
/*  58 */     ABUser user = null;
/*     */ 
/*  61 */     String sTemplate = a_alertInfo.getAlert().getTemplate();
/*     */ 
/*  64 */     String sAssetDescription = "";
/*     */ 
/*  67 */     String sMessage = "";
/*     */ 
/*  70 */     String sSendTo = a_alertInfo.getAlert().getSendTo();
/*     */ 
/*  73 */     if (sSendTo.equalsIgnoreCase("owner"))
/*     */     {
/*  76 */       WorkflowInfo wfInfo = a_alertInfo.getWorkflowInfo();
/*  77 */       long lAssetId = wfInfo.getEntityId();
/*     */ 
/*  79 */       if (lAssetId > 0L)
/*     */       {
/*  81 */         Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, null, false, false);
/*     */ 
/*  84 */         user = asset.getAddedByUser();
/*     */ 
/*  87 */         sAssetDescription = AttributeUtil.getDisplayDescriptionAsString(asset.getDisplayAttributes(1L), user.getLanguage().getCode(), ", ");
/*     */ 
/*  90 */         Vector vecMessages = this.m_assetWorkflowManager.getWorkflowAudit(a_dbTransaction, lAssetId);
/*     */ 
/*  92 */         if ((vecMessages != null) && (vecMessages.size() > 0))
/*     */         {
/*  94 */           AssetWorkflowAuditEntry audit = (AssetWorkflowAuditEntry)vecMessages.get(0);
/*  95 */           sMessage = audit.getMessage();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 100 */     if (user != null)
/*     */     {
/* 102 */       sendEmail(user, sTemplate, sAssetDescription, sMessage);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void sendBatchAlert(DBTransaction a_dbTransaction, Vector a_vecAlerts)
/*     */     throws Bn2Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   private void sendEmail(ABUser toUser, String sTemplate, String sAssetDescription, String sMessage)
/*     */   {
/* 125 */     String sEmailAddress = toUser.getEmailAddress();
/*     */ 
/* 127 */     String sName = toUser.getFullName();
/* 128 */     if (!StringUtil.stringIsPopulated(sName))
/*     */     {
/* 130 */       sName = toUser.getUsername();
/*     */     }
/*     */ 
/* 133 */     if (StringUtil.stringIsPopulated(sEmailAddress))
/*     */     {
/* 136 */       HashMap params = new HashMap();
/* 137 */       params.put("template", sTemplate);
/*     */ 
/* 139 */       params.put("name", sName);
/* 140 */       params.put("emailAddresses", sEmailAddress);
/* 141 */       params.put("assetDescription", sAssetDescription);
/* 142 */       params.put("message", sMessage);
/*     */       try
/*     */       {
/* 146 */         this.m_emailManager.sendPotentiallyDuplicateEmail(params, LanguageConstants.k_defaultLanguage);
/*     */       }
/*     */       catch (Bn2Exception e)
/*     */       {
/* 151 */         this.m_logger.debug("SendToOwnerAlertProcessor: The alert email was not successfully sent: " + e.getMessage());
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 157 */       this.m_logger.debug("SendToOwnerAlertProcessor: No one to email alert to!");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.processor.SendToOwnerAlertProcessor
 * JD-Core Version:    0.6.0
 */