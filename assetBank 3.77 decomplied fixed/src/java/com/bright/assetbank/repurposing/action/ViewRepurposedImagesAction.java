/*     */ package com.bright.assetbank.repurposing.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.ImageAsset;
/*     */ import com.bright.assetbank.application.bean.ImageConversionInfo;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.application.util.ABImageMagick;
/*     */ import com.bright.assetbank.repurposing.bean.RepurposedAsset;
/*     */ import com.bright.assetbank.repurposing.form.RepurposingForm;
/*     */ import com.bright.assetbank.repurposing.service.AssetRepurposingManager;
/*     */ import com.bright.assetbank.usage.bean.ColorSpace;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.ArrayList;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewRepurposedImagesAction extends BTransactionAction
/*     */   implements AssetBankConstants, FrameworkConstants
/*     */ {
/*     */   private AssetRepurposingManager m_assetRepurposingManager;
/*     */   private AssetManager m_assetManager;
/*  58 */   private UsageManager m_usageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  67 */     RepurposingForm form = (RepurposingForm)a_form;
/*  68 */     ABUserProfile profile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  70 */     long lId = getLongParameter(a_request, "id");
/*     */ 
/*  72 */     String sUrlBase = AssetBankSettings.getRepurposedFileBaseUrl(a_request);
/*  73 */     ArrayList versions = this.m_assetRepurposingManager.getRepurposedAssets(a_dbTransaction, lId, sUrlBase);
/*     */ 
/*  76 */     if (((versions == null) || (versions.size() == 0)) && (Boolean.parseBoolean(a_request.getParameter("embed"))))
/*     */     {
/*  78 */       ImageConversionInfo conversionInfo = new ImageConversionInfo();
/*     */ 
/*  80 */       conversionInfo.setMaxWidth(AssetBankSettings.getDefaultRepurposedImageMaxWidth());
/*  81 */       conversionInfo.setMaxHeight(AssetBankSettings.getDefaultRepurposedImageMaxHeight());
/*  82 */       conversionInfo.setAddWatermark(false);
/*  83 */       conversionInfo.setJpegQuality(AssetBankSettings.getJpgConversionQuality());
/*  84 */       conversionInfo.setMaintainAspectRatio(true);
/*     */ 
/*  86 */       Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lId, null, false, false);
/*     */ 
/*  88 */       if (((asset instanceof ImageAsset)) && (ABImageMagick.getIsCMYK(((ImageAsset)asset).getColorSpace())))
/*     */       {
/*  91 */         ColorSpace destinationColorSpace = this.m_usageManager.getColorSpace(a_dbTransaction, 1);
/*  92 */         ColorSpace currentColorSpace = this.m_usageManager.getCurrentColorSpace((ImageAsset)asset, a_dbTransaction);
/*     */ 
/*  94 */         conversionInfo.setConvertToColorSpace(destinationColorSpace);
/*  95 */         conversionInfo.setCurrentColorSpace(currentColorSpace);
/*     */       }
/*     */ 
/*  98 */       String sPath = this.m_assetManager.getDownloadableAssetPath(asset, AssetBankSettings.getDefaultRepurposedImageExtension(), conversionInfo);
/*     */ 
/* 100 */       RepurposedAsset version = this.m_assetRepurposingManager.addRepurposedAsset(a_dbTransaction, asset, profile.getUser().getId(), sPath, sUrlBase);
/*     */ 
/* 102 */       version.setCreatedByUser((ABUser)profile.getUser());
/*     */ 
/* 104 */       versions.add(version);
/*     */     }
/*     */ 
/* 107 */     form.setRepurposedVersions(versions);
/*     */ 
/* 109 */     form.setBaseUrl(sUrlBase);
/* 110 */     form.setAssetId(lId);
/*     */ 
/* 112 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setAssetRepurposingManager(AssetRepurposingManager a_assetRepurposingManager)
/*     */   {
/* 117 */     this.m_assetRepurposingManager = a_assetRepurposingManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_manager)
/*     */   {
/* 122 */     this.m_assetManager = a_manager;
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager a_sUsageManager)
/*     */   {
/* 127 */     this.m_usageManager = a_sUsageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.action.ViewRepurposedImagesAction
 * JD-Core Version:    0.6.0
 */