/*     */ package com.bright.assetbank.repurposing.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.AudioAsset;
/*     */ import com.bright.assetbank.application.bean.VideoAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.repurposing.bean.RepurposedAsset;
/*     */ import com.bright.assetbank.repurposing.form.RepurposingForm;
/*     */ import com.bright.assetbank.repurposing.service.AssetRepurposingManager;
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
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewRepurposedMediaAction extends BTransactionAction
/*     */   implements AssetBankConstants, FrameworkConstants
/*     */ {
/*     */   private AssetRepurposingManager m_assetRepurposingManager;
/*     */   private AssetManager m_assetManager;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  63 */     RepurposingForm form = (RepurposingForm)a_form;
/*  64 */     ABUserProfile profile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  66 */     long lId = getLongParameter(a_request, "id");
/*     */ 
/*  68 */     String sUrlBase = AssetBankSettings.getRepurposedFileBaseUrl(a_request);
/*  69 */     ArrayList versions = this.m_assetRepurposingManager.getRepurposedAssets(a_dbTransaction, lId, sUrlBase);
/*     */ 
/*  72 */     if (((versions == null) || (versions.size() == 0)) && (Boolean.parseBoolean(a_request.getParameter("embed"))))
/*     */     {
/*  74 */       Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lId, null, false, false);
/*     */ 
/*  76 */       if ((!(asset instanceof VideoAsset)) && (!(asset instanceof AudioAsset)))
/*     */       {
/*  78 */         throw new Bn2Exception("ViewRepurposedMediaAction can only be used with video assets");
/*     */       }
/*     */ 
/*  81 */       String sPath = asset.getEmbeddedPreviewClipLocation();
/*     */ 
/*  83 */       if (!StringUtils.isEmpty(sPath))
/*     */       {
/*  85 */         long lUserId = profile.getUser() != null ? profile.getUser().getId() : 0L;
/*     */ 
/*  87 */         RepurposedAsset version = this.m_assetRepurposingManager.addRepurposedAsset(a_dbTransaction, asset, lUserId, sPath, sUrlBase);
/*     */ 
/*  89 */         version.setCreatedByUser((ABUser)profile.getUser());
/*     */ 
/*  91 */         versions.add(version);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  99 */     form.setRepurposedVersions(versions);
/* 100 */     form.setBaseUrl(sUrlBase);
/* 101 */     form.setAssetId(lId);
/*     */ 
/* 103 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setAssetRepurposingManager(AssetRepurposingManager a_assetRepurposingManager)
/*     */   {
/* 108 */     this.m_assetRepurposingManager = a_assetRepurposingManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_manager)
/*     */   {
/* 113 */     this.m_assetManager = a_manager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.action.ViewRepurposedMediaAction
 * JD-Core Version:    0.6.0
 */