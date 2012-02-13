/*     */ package com.bright.assetbank.assetbox.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.assetbox.form.ShareAssetBoxForm;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.ServletUtil;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.mail.HtmlEmail;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class AddAssetBoxUsersPreviewEmailAction extends Bn2Action
/*     */   implements AssetBoxConstants, AssetBankConstants, FrameworkConstants
/*     */ {
/*  54 */   private AssetBoxManager m_assetBoxManager = null;
/*  55 */   private LanguageManager m_languageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  63 */     ActionForward afForward = null;
/*  64 */     ShareAssetBoxForm form = (ShareAssetBoxForm)a_form;
/*  65 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  66 */     long lId = getLongParameter(a_request, "assetBoxId");
/*  67 */     String[] asUserIds = a_request.getParameterValues("selectedUsers");
/*     */ 
/*  70 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  72 */       this.m_logger.debug("The user must be logged in to access " + getClass().getSimpleName());
/*  73 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  76 */     if ((asUserIds != null) && (asUserIds.length > 0))
/*     */     {
/*  78 */       long[] alUserIds = new long[asUserIds.length];
/*     */ 
/*  80 */       for (int i = 0; i < asUserIds.length; i++)
/*     */       {
/*  82 */         alUserIds[i] = Long.parseLong(asUserIds[i]);
/*     */       }
/*     */ 
/*  85 */       String sUrl = ServletUtil.getApplicationUrl(a_request) + "/action/" + "viewAssetBox" + "?" + "assetBoxId" + "=" + lId;
/*     */ 
/*  87 */       long lTemplateTypeId = StringUtils.isEmpty(form.getEmailTemplateId()) ? 1L : 2L;
/*     */ 
/*  89 */       String sUsername = "jsmith";
/*  90 */       String sFullName = "John Smith";
/*  91 */       String sEmailAddress = "sample@assetbank.co.uk";
/*     */ 
/*  93 */       List languages = this.m_languageManager.getLanguages(null, true, true);
/*  94 */       Vector vEmails = new Vector(languages.size());
/*     */ 
/*  96 */       for (Iterator itLanguages = languages.iterator(); itLanguages.hasNext(); )
/*     */       {
/*  98 */         Language language = (Language)itLanguages.next();
/*  99 */         HtmlEmail email = this.m_assetBoxManager.getShareAssetboxAddUserEmail(sUrl, form.getEmailTemplateId(), lTemplateTypeId, form.getEmailContent(), sUsername, sFullName, sEmailAddress, language);
/*     */ 
/* 107 */         if (email != null)
/*     */         {
/* 109 */           vEmails.add(email);
/*     */         }
/*     */       }
/*     */ 
/* 113 */       form.setLanguages(languages);
/* 114 */       form.setPreviewEmails(vEmails);
/*     */     }
/*     */ 
/* 117 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 119 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/* 125 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager languageManager)
/*     */   {
/* 130 */     this.m_languageManager = languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.AddAssetBoxUsersPreviewEmailAction
 * JD-Core Version:    0.6.0
 */