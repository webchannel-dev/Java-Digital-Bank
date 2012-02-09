/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.VideoConversionResult;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.exception.AssetConversionFailedException;
/*     */ import com.bright.assetbank.application.form.DownloadForm;
/*     */ import com.bright.assetbank.application.service.VideoAssetManagerImpl;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewVideoConversionStatusAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  48 */   protected VideoAssetManagerImpl m_assetManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  57 */     ActionForward afForward = null;
/*  58 */     DownloadForm form = (DownloadForm)a_form;
/*     */ 
/*  60 */     boolean bRepurpose = StringUtils.isNotEmpty(a_request.getParameter("repurpose"));
/*     */ 
/*  62 */     form.setRepurpose(bRepurpose);
/*     */ 
/*  64 */     if (form.getAsset().getId() <= 0L)
/*     */     {
/*  66 */       form.getAsset().setId(getLongParameter(a_request, "assetId"));
/*     */     }
/*     */ 
/*  70 */     if (StringUtil.stringIsPopulated(a_request.getParameter("filePath")))
/*     */     {
/*  72 */       setResultInRequest(a_mapping, a_request, a_request.getParameter("filePath"));
/*     */ 
/*  74 */       if (bRepurpose)
/*     */       {
/*  76 */         a_request.setAttribute("deleteFileAfterUse", Boolean.TRUE);
/*  77 */         return a_mapping.findForward("DownloadRepurposeSuccess");
/*     */       }
/*     */ 
/*  80 */       return a_mapping.findForward("Success");
/*     */     }
/*     */ 
/*  85 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */     try
/*     */     {
/*  90 */       int iVideoConversionStatus = this.m_assetManager.getVideoConversionStatus(userProfile.getDownloadId());
/*     */ 
/*  93 */       if (iVideoConversionStatus == 1)
/*     */       {
/*  96 */         long lFilesize = this.m_assetManager.getCurrentlyConvertingFileSize(userProfile.getDownloadId());
/*     */ 
/*  99 */         double dFilesizeMb = lFilesize / 1048576.0D;
/*     */ 
/* 102 */         dFilesizeMb = Math.round(dFilesizeMb * Math.pow(10.0D, 2.0D)) / Math.pow(10.0D, 2.0D);
/*     */ 
/* 104 */         form.setFilesizeInMegabytes(dFilesizeMb);
/* 105 */         afForward = a_mapping.findForward("InProgress");
/*     */       }
/* 107 */       else if (iVideoConversionStatus == 0)
/*     */       {
/* 110 */         afForward = a_mapping.findForward("Failure");
/*     */       }
/*     */       else
/*     */       {
/* 115 */         VideoConversionResult result = this.m_assetManager.getCompletedVideoConversionDetails(userProfile.getDownloadId());
/*     */ 
/* 117 */         if (result != null)
/*     */         {
/* 119 */           form.setConversionInProgress(false);
/* 120 */           form.setVideoConversionResult(result);
/* 121 */           form.setRepurpose(bRepurpose);
/*     */ 
/* 123 */           if (bRepurpose)
/*     */           {
/* 125 */             setResultInRequest(a_mapping, a_request, form.getVideoConversionResult().getConvertedFilename());
/* 126 */             a_request.setAttribute("deleteFileAfterUse", Boolean.TRUE);
/* 127 */             afForward = a_mapping.findForward("DownloadRepurposeSuccess");
/*     */           }
/*     */           else
/*     */           {
/* 131 */             afForward = a_mapping.findForward("InProgress");
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 137 */           afForward = a_mapping.findForward("Failure");
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (AssetConversionFailedException e)
/*     */     {
/* 143 */       form.addError(e.getCause().getMessage());
/* 144 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 147 */     return afForward;
/*     */   }
/*     */ 
/*     */   private void setResultInRequest(ActionMapping a_mapping, HttpServletRequest a_request, String sPath)
/*     */   {
/* 152 */     String sCompress = a_request.getParameter("compressAsset");
/* 153 */     boolean bCompress = false;
/* 154 */     if (StringUtil.stringIsPopulated(sCompress))
/*     */     {
/* 156 */       bCompress = Boolean.parseBoolean(sCompress);
/*     */     }
/*     */ 
/* 160 */     a_request.setAttribute("downloadFilename", sPath);
/* 161 */     a_request.setAttribute("compressFile", new Boolean(bCompress));
/* 162 */     a_request.setAttribute("downloadFile", sPath);
/*     */ 
/* 164 */     a_request.setAttribute("downloadFaliureUrl", a_mapping.findForward("Failure").getPath());
/*     */   }
/*     */ 
/*     */   public void setAssetManager(VideoAssetManagerImpl a_assetManager)
/*     */   {
/* 169 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewVideoConversionStatusAction
 * JD-Core Version:    0.6.0
 */