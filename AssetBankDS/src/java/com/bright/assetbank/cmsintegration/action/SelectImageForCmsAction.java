/*     */ package com.bright.assetbank.cmsintegration.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.cmsintegration.bean.CmsInfo;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SelectImageForCmsAction extends BTransactionAction
/*     */ {
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  59 */     ABUserProfile userProfile = (ABUserProfile)ABUserProfile.getUserProfile(a_request);
/*     */ 
/*  62 */     CmsInfo cmsInfo = new CmsInfo();
/*     */ 
/*  65 */     cmsInfo.setCurrentLocation(a_request.getParameter("currentlocation"));
/*     */ 
/*  68 */     cmsInfo.setPlaceholder(a_request.getParameter("placeholder"));
/*     */ 
/*  71 */     String sCallbackUrl = a_request.getParameter("callbackurl");
/*     */     try
/*     */     {
/*  75 */       sCallbackUrl = URLDecoder.decode(sCallbackUrl, "UTF-8");
/*     */     }
/*     */     catch (UnsupportedEncodingException uee)
/*     */     {
/*  80 */       this.m_logger.error("The callbackurl could not be decoded in SelectImageForCmsAction.java");
/*     */     }
/*     */ 
/*  84 */     cmsInfo.setCallbackUrl(sCallbackUrl);
/*     */ 
/*  87 */     int iRepositoryNumber = getIntParameter(a_request, "repositoryNumber");
/*     */ 
/*  90 */     cmsInfo.setRepositoryNumber(iRepositoryNumber);
/*     */ 
/*  93 */     String sSubRepositoryName = a_request.getParameter("subrepositoryName");
/*     */ 
/*  95 */     if (sSubRepositoryName != null)
/*     */     {
/*  97 */       if ((sSubRepositoryName.length() > 0) && (!sSubRepositoryName.startsWith(".")))
/*     */       {
/* 100 */         cmsInfo.setSubRepository(sSubRepositoryName);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 105 */     String sCancelUrl = AssetBankSettings.getCmsCancelUrl(iRepositoryNumber);
/*     */ 
/* 107 */     if ((sCancelUrl != null) && (sCancelUrl.length() > 0))
/*     */     {
/* 109 */       cmsInfo.setCancelUrl(sCancelUrl);
/*     */     }
/*     */ 
/* 113 */     String sJsElement = a_request.getParameter("jsCallbackElementId");
/*     */ 
/* 115 */     if (StringUtils.isNotEmpty(sJsElement))
/*     */     {
/* 117 */       cmsInfo.setJavascriptCallbackElement(sJsElement);
/*     */     }
/*     */ 
/* 121 */     userProfile.setCmsInfo(cmsInfo);
/*     */ 
/* 123 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.cmsintegration.action.SelectImageForCmsAction
 * JD-Core Version:    0.6.0
 */