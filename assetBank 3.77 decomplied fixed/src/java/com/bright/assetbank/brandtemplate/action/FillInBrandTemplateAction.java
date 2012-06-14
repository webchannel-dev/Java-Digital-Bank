/*     */ package com.bright.assetbank.brandtemplate.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.application.util.DownloadUtil;
/*     */ import com.bright.assetbank.brandtemplate.service.BrandTemplateManager;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class FillInBrandTemplateAction extends Bn2Action
/*     */   implements AssetBankConstants
/*     */ {
/*     */   private static final String c_ksTemplateParameterNamePrefix = "tf_";
/*  52 */   private IAssetManager m_assetManager = null;
/*  53 */   private BrandTemplateManager m_brandTemplateManager = null;
/*  54 */   private UsageManager m_usageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  63 */     long lAssetId = getIntParameter(a_request, "assetId");
/*     */ 
/*  65 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  66 */     long lUserId = userProfile.getUserId();
/*     */ 
/*  68 */     Asset asset = this.m_assetManager.getAsset(null, lAssetId, userProfile.getVisibleAttributeIds(), false, false);
/*     */ 
/*  71 */     if (!this.m_assetManager.userCanDownloadAsset(userProfile, asset))
/*     */     {
/*  73 */       this.m_logger.debug("This user does not have permission to view asset id=" + lAssetId);
/*  74 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  79 */     Map fieldValues = RequestUtil.getRequestParametersAsMap(a_request, "tf_", true);
/*     */ 
/*  83 */     String downloadFileLocation = this.m_brandTemplateManager.fillInTemplate(asset, fieldValues);
/*     */ 
/*  87 */     if (!asset.getIsUnsubmitted())
/*     */     {
/*  89 */       this.m_usageManager.logAssetUseAsynchronously(asset.getId(), lUserId, 0L, "Brand template", 7L, userProfile.getSessionId(), null);
/*     */     }
/*     */ 
/* 100 */     String sClientFilename = DownloadUtil.getDownloadFilename(asset, null);
/*     */ 
/* 103 */     String encyryptedFileLocation = FileUtil.encryptFilepath(downloadFileLocation);
/* 104 */     a_request.setAttribute("downloadFile", encyryptedFileLocation);
/* 105 */     a_request.setAttribute("downloadFilename", sClientFilename);
/* 106 */     a_request.setAttribute("deleteFileAfterUse", Boolean.TRUE);
/* 107 */     a_request.setAttribute("downloadFaliureUrl", a_mapping.findForward("DownloadFailure").getPath() + "?assetId=" + lAssetId);
/*     */ 
/* 110 */     return a_mapping.findForward("DownloadSuccess");
/*     */   }
/*     */ 
/*     */   public IAssetManager getAssetManager()
/*     */   {
/* 115 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_assetManager)
/*     */   {
/* 120 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public BrandTemplateManager getBrandTemplateManager()
/*     */   {
/* 125 */     return this.m_brandTemplateManager;
/*     */   }
/*     */ 
/*     */   public void setBrandTemplateManager(BrandTemplateManager a_brandTemplateManager)
/*     */   {
/* 130 */     this.m_brandTemplateManager = a_brandTemplateManager;
/*     */   }
/*     */ 
/*     */   public UsageManager getUsageManager()
/*     */   {
/* 135 */     return this.m_usageManager;
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager a_usageManager)
/*     */   {
/* 140 */     this.m_usageManager = a_usageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.brandtemplate.action.FillInBrandTemplateAction
 * JD-Core Version:    0.6.0
 */