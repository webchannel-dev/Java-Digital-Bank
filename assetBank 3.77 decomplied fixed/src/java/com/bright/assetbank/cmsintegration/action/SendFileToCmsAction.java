/*     */ package com.bright.assetbank.cmsintegration.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.DownloadForm;
/*     */ import com.bright.assetbank.application.util.ABImageMagick;
/*     */ import com.bright.assetbank.cmsintegration.bean.CmsInfo;
/*     */ import com.bright.assetbank.cmsintegration.bean.CmsUploadResult;
/*     */ import com.bright.assetbank.cmsintegration.service.CmsIntegrationManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SendFileToCmsAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  55 */   private CmsIntegrationManager m_cmsIntegrationManager = null;
/*  56 */   protected ListManager m_listManager = null;
/*  57 */   private FileStoreManager m_fileStoreManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  83 */     ActionForward afForward = null;
/*  84 */     String sSource = null;
/*  85 */     String sFilename = null;
/*  86 */     boolean bDeleteDownloadedFile = false;
/*     */ 
/*  88 */     DownloadForm form = (DownloadForm)a_form;
/*     */     try
/*     */     {
/*  94 */       ABUserProfile userProfile = (ABUserProfile)ABUserProfile.getUserProfile(a_request);
/*     */ 
/*  97 */       if (!userProfile.getIsFromCms())
/*     */       {
/*  99 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "cmsSessionError", userProfile.getCurrentLanguage()).getBody());
/* 100 */         return a_mapping.findForward("Failure");
/*     */       }
/*     */ 
/* 104 */       sSource = (String)a_request.getAttribute("downloadFile");
/* 105 */       sSource = FileUtil.decryptFilepath(sSource);
/* 106 */       sSource = this.m_fileStoreManager.getAbsolutePath(sSource);
/*     */ 
/* 109 */       int[] iWidthHeight = ABImageMagick.getDimensions(sSource);
/*     */ 
/* 112 */       String[] asAllowedFormats = AssetBankSettings.getCmsAllowedFormats();
/* 113 */       boolean bAllowed = false;
/* 114 */       String sSuffix = FileUtil.getSuffix(sSource);
/*     */ 
/* 117 */       int i = 0;
/*     */ 
/* 119 */       for (; (asAllowedFormats != null) && (sSuffix != null) && (i < asAllowedFormats.length); i++)
/*     */       {
/* 121 */         if (!sSuffix.equalsIgnoreCase(asAllowedFormats[i]))
/*     */           continue;
/* 123 */         bAllowed = true;
/* 124 */         break;
/*     */       }
/*     */ 
/* 130 */       if (!bAllowed)
/*     */       {
/* 132 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationInvalidFormatForCms", userProfile.getCurrentLanguage()).getBody());
/* 133 */         return a_mapping.findForward("InvalidFormat");
/*     */       }
/*     */ 
/* 137 */       sFilename = (String)a_request.getAttribute("downloadFilename");
/*     */ 
/* 140 */       if (a_request.getAttribute("deleteFileAfterUse") != null)
/*     */       {
/* 142 */         bDeleteDownloadedFile = ((Boolean)a_request.getAttribute("deleteFileAfterUse")).booleanValue();
/*     */       }
/*     */ 
/* 145 */       CmsUploadResult result = this.m_cmsIntegrationManager.sendFileToCms(userProfile.getCmsInfo(), sSource, sFilename, bDeleteDownloadedFile);
/*     */ 
/* 150 */       String sReturnUrl = this.m_cmsIntegrationManager.getReturnUrl(userProfile.getCmsInfo().getRepositoryNumber(), userProfile.getCmsInfo().getSubRepository(), result.getRemoteFilename());
/*     */ 
/* 152 */       String sRemoteFileLocation = (StringUtils.isNotEmpty(result.getRemoteDirectory()) ? result.getRemoteDirectory() + "/" : "") + result.getRemoteFilename();
/*     */ 
/* 155 */       if (StringUtil.stringIsPopulated(AssetBankSettings.getCmsRepositoryUrl()))
/*     */       {
/* 157 */         sRemoteFileLocation = AssetBankSettings.getCmsRepositoryUrl();
/*     */ 
/* 159 */         if (StringUtil.stringIsPopulated(result.getSubfolder()))
/*     */         {
/* 161 */           sRemoteFileLocation = sRemoteFileLocation + "/" + result.getSubfolder();
/*     */         }
/*     */ 
/* 164 */         sRemoteFileLocation = sRemoteFileLocation + "/" + result.getRemoteFilename();
/*     */       }
/*     */ 
/* 169 */       form.setAttributeXml(this.m_cmsIntegrationManager.generateAttributeXml(form.getAsset().getAttributeValues(), sRemoteFileLocation, iWidthHeight[0], iWidthHeight[1]));
/*     */ 
/* 172 */       if (StringUtils.isNotEmpty(sReturnUrl))
/*     */       {
/* 174 */         afForward = createRedirectingForward(sReturnUrl);
/*     */       }
/*     */       else
/*     */       {
/* 180 */         form.setCmsReturnUrl(sReturnUrl);
/* 181 */         form.setCmsCallbackElementId(userProfile.getCmsInfo().getJavascriptCallbackElement());
/* 182 */         form.setCmsCallbackElementValue(sRemoteFileLocation);
/* 183 */         afForward = a_mapping.findForward("Success");
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 188 */       this.m_logger.error("Error in SendImageToCmsAction: ", e);
/* 189 */       afForward = a_mapping.findForward("SystemFailure");
/*     */     }
/*     */ 
/* 192 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setCmsIntegrationManager(CmsIntegrationManager a_sCmsIntegrationManager)
/*     */   {
/* 197 */     this.m_cmsIntegrationManager = a_sCmsIntegrationManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 202 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager fileStoreManager)
/*     */   {
/* 207 */     this.m_fileStoreManager = fileStoreManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.cmsintegration.action.SendFileToCmsAction
 * JD-Core Version:    0.6.0
 */