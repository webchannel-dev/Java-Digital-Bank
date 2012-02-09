/*     */ package com.bright.assetbank.repurposing.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.VideoAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.application.util.ServletUtil;
          import com.bright.assetbank.repurposing.bean.RepurposedAsset;
/*     */ import com.bright.assetbank.repurposing.bean.RepurposedVersion;
/*     */ import com.bright.assetbank.repurposing.form.RepurposingForm;
/*     */ import com.bright.assetbank.repurposing.service.AssetRepurposingManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewRepurposedVideosAction extends BTransactionAction
/*     */   implements AssetBankConstants, FrameworkConstants
/*     */ {
/*     */   private static final String k_sParamName_Embed = "embed";
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
/*  68 */     //String sUrlBase = AssetBankSettings.getRepurposedFileBaseUrl();
                String sUrlBase = AssetBankSettings.getRepurposedFileBaseUrl(a_request);
/*     */ 
/*  70 */     if (StringUtils.isEmpty(sUrlBase))
/*     */     {
/*  72 */       sUrlBase = ServletUtil.getApplicationUrl(a_request);
/*     */     }
/*     */ 
/*  75 */     //Vector versions = this.m_assetRepurposingManager.getRepurposedImages(a_dbTransaction, lId, sUrlBase);
/*     */       ArrayList<RepurposedAsset> versions = this.m_assetRepurposingManager.getRepurposedImages(a_dbTransaction, lId);
/*  78 */     if (((versions == null) || (versions.size() == 0)) && (Boolean.parseBoolean(a_request.getParameter("embed"))))
/*     */     {
/*  80 */       Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lId, null, false, false);
/*     */ 
/*  82 */       if (!(asset instanceof VideoAsset))
/*     */       {
/*  84 */         throw new Bn2Exception("ViewRepurposedVideosAction can only be used with video assets");
/*     */       }
/*     */ 
/*  87 */       String sPath = ((VideoAsset)asset).getEmbeddedPreviewClipLocation();
/*     */ 
/*  89 */       StringUtils.isEmpty(sPath);
/*     */ 
/*  94 */       RepurposedVersion version = this.m_assetRepurposingManager.addRepurposedVersion(a_dbTransaction, lId, profile.getUser().getId(), sPath, sUrlBase);
/*     */ 
/*  96 */       version.setCreatedByUser((ABUser)profile.getUser());
/*     */       //versions.
/*  98 */       versions.add(version);
/*     */     }
/*     */ 
/* 101 */     form.setRepurposedVersions(versions);
/*     */ 
/* 103 */     form.setBaseUrl(sUrlBase);
/* 104 */     form.setAssetId(lId);
/*     */ 
/* 106 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setAssetRepurposingManager(AssetRepurposingManager a_assetRepurposingManager)
/*     */   {
/* 111 */     this.m_assetRepurposingManager = a_assetRepurposingManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_manager)
/*     */   {
/* 116 */     this.m_assetManager = a_manager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.action.ViewRepurposedVideosAction
 * JD-Core Version:    0.6.0
 */