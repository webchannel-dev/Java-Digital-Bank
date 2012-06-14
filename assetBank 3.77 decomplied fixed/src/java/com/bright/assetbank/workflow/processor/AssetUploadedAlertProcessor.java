/*     */ package com.bright.assetbank.workflow.processor;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import com.bright.framework.workflow.bean.AlertInfo;
/*     */ import com.bright.framework.workflow.bean.StateAlert;
/*     */ import com.bright.framework.workflow.bean.WorkflowInfo;
/*     */ import com.bright.framework.workflow.processor.SendAlertProcessorImpl;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class AssetUploadedAlertProcessor extends SendAlertProcessorImpl
/*     */ {
/*  48 */   private ABUserManager m_userManager = null;
/*     */ 
/*  54 */   private IAssetManager m_assetManager = null;
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/*  51 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/*  57 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void sendAlert(DBTransaction a_dbTransaction, AlertInfo a_alertInfo)
/*     */     throws Bn2Exception
/*     */   {
/*  75 */     HashMap hmApproverEmails = new HashMap();
/*  76 */     populateApproverEmailsForAlert(a_dbTransaction, a_alertInfo, hmApproverEmails);
/*  77 */     boolean bNeedSuperUsers = hmApproverEmails.size() == 0;
/*     */ 
/*  80 */     String sTemplate = a_alertInfo.getAlert().getTemplate();
/*     */ 
/*  83 */     long lAssetId = a_alertInfo.getWorkflowInfo().getEntityId();
/*     */ 
/*  86 */     Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, null, false, false);
/*  87 */     ABUser user = asset.getLastModifiedByUser();
/*     */ 
/*  89 */     sendApprovalEmail(user, hmApproverEmails, sTemplate, bNeedSuperUsers);
/*     */   }
/*     */ 
/*     */   protected void sendBatchAlert(DBTransaction a_dbTransaction, Vector a_vecAlerts)
/*     */     throws Bn2Exception
/*     */   {
/* 106 */     if (a_vecAlerts.size() > 0)
/*     */     {
/* 109 */       HashMap hmApproverEmails = new HashMap();
/* 110 */       this.m_logger.debug("In AssetUploadedAlertProcessor.sendBatchAlert:");
/*     */ 
/* 114 */       long lAssetId = 0L;
/* 115 */       String sTemplate = null;
/*     */ 
/* 117 */       Iterator it = a_vecAlerts.iterator();
/* 118 */       while (it.hasNext())
/*     */       {
/* 120 */         AlertInfo alertInfo = (AlertInfo)it.next();
/* 121 */         populateApproverEmailsForAlert(a_dbTransaction, alertInfo, hmApproverEmails);
/*     */ 
/* 123 */         if (lAssetId == 0L)
/*     */         {
/* 125 */           lAssetId = alertInfo.getWorkflowInfo().getEntityId();
/*     */         }
/*     */ 
/* 128 */         if (sTemplate == null)
/*     */         {
/* 130 */           sTemplate = alertInfo.getAlert().getTemplate();
/*     */         }
/*     */       }
/*     */ 
/* 134 */       boolean bNeedSuperUsers = hmApproverEmails.size() == 0;
/*     */ 
/* 137 */       ABUser user = null;
/*     */ 
/* 139 */       if (lAssetId > 0L)
/*     */       {
/* 141 */         Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, null, false, false);
/* 142 */         user = asset.getLastModifiedByUser();
/*     */       }
/*     */ 
/* 145 */       sendApprovalEmail(user, hmApproverEmails, sTemplate, bNeedSuperUsers);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void populateApproverEmailsForAlert(DBTransaction a_dbTransaction, AlertInfo a_alertInfo, HashMap a_hmApproverEmails)
/*     */     throws Bn2Exception
/*     */   {
/* 162 */     String sSendTo = a_alertInfo.getAlert().getSendTo();
/*     */ 
/* 166 */     WorkflowInfo wfInfo = a_alertInfo.getWorkflowInfo();
/* 167 */     long lAssetId = wfInfo.getEntityId();
/*     */ 
/* 171 */     sSendTo = this.m_assetWorkflowManager.substituteApproversWithAssetApprovers(a_dbTransaction, sSendTo, lAssetId);
/* 172 */     long[] alIds = StringUtil.getIdsArray(sSendTo);
/*     */ 
/* 175 */     this.m_userManager.populateUserEmailsForGroups(alIds, a_hmApproverEmails, true);
/*     */   }
/*     */ 
/*     */   private void sendApprovalEmail(ABUser user, HashMap hmApproverEmails, String sTemplate, boolean bNeedSuperUsers)
/*     */     throws Bn2Exception
/*     */   {
/* 191 */     String sName = "";
/*     */ 
/* 193 */     if (user != null)
/*     */     {
/* 195 */       sName = user.getFullName();
/*     */ 
/* 197 */       if (!StringUtil.stringIsPopulated(sName))
/*     */       {
/* 199 */         sName = user.getUsername();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 204 */     String sAdminEmailAddress = StringUtil.getEmailAddressesFromHashMap(hmApproverEmails);
/*     */ 
/* 206 */     if (bNeedSuperUsers)
/*     */     {
/* 208 */       if (StringUtil.stringIsPopulated(sAdminEmailAddress))
/*     */       {
/* 210 */         String sSuperEmailAddress = this.m_userManager.getAdminEmailAddresses();
/* 211 */         if (StringUtil.stringIsPopulated(sSuperEmailAddress))
/*     */         {
/* 213 */           sAdminEmailAddress = sAdminEmailAddress + ";" + sSuperEmailAddress;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 218 */         sAdminEmailAddress = this.m_userManager.getAdminEmailAddresses();
/*     */       }
/*     */     }
/*     */ 
/* 222 */     if (StringUtil.stringIsPopulated(sAdminEmailAddress))
/*     */     {
/* 225 */       HashMap params = new HashMap();
/* 226 */       params.put("template", sTemplate);
/* 227 */       params.put("name", sName);
/*     */ 
/* 229 */       params.put("adminEmailAddresses", sAdminEmailAddress);
/*     */       try
/*     */       {
/* 234 */         this.m_emailManager.sendPotentiallyDuplicateEmail(params, LanguageConstants.k_defaultLanguage);
/*     */       }
/*     */       catch (Bn2Exception e)
/*     */       {
/* 239 */         this.m_logger.debug("AssetUploadedAlertProcessor: The alert email was not successfully sent to the admin: " + e.getMessage());
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 245 */       this.m_logger.debug("AssetUploadedAlertProcessor: No admins to email alert to!");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.processor.AssetUploadedAlertProcessor
 * JD-Core Version:    0.6.0
 */