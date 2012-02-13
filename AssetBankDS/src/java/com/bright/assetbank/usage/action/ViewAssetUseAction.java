/*     */ package com.bright.assetbank.usage.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.usage.form.AssetUseForm;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewAssetUseAction extends Bn2Action
/*     */   implements AssetBankConstants, FrameworkConstants
/*     */ {
/*  46 */   private IAssetManager m_assetManager = null;
/*  47 */   private UsageManager m_usageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Exception
/*     */   {
/*  70 */     ActionForward afForward = null;
/*  71 */     AssetUseForm form = (AssetUseForm)a_form;
/*  72 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  75 */     long lAssetId = getLongParameter(a_request, "id");
/*     */ 
/*  77 */     Asset asset = this.m_assetManager.getAsset(null, lAssetId, null, false, false);
/*     */ 
/*  80 */     if (!this.m_assetManager.userCanViewAsset(userProfile, asset))
/*     */     {
/*  82 */       this.m_logger.debug("This user does not have permission to view asset id=" + lAssetId);
/*  83 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  86 */     form.setAssetTitle(asset.getName());
/*     */ 
/*  88 */     form.setAssetUsageLog(this.m_usageManager.getAssetUsage(null, lAssetId));
/*     */ 
/*  90 */     afForward = a_mapping.findForward("Success");
/*     */ 
/*  92 */     return afForward;
/*     */   }
/*     */ 
/*     */   public UsageManager getUsageManager()
/*     */   {
/*  98 */     return this.m_usageManager;
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager a_sUsageManager)
/*     */   {
/* 104 */     this.m_usageManager = a_sUsageManager;
/*     */   }
/*     */ 
/*     */   public IAssetManager getAssetManager()
/*     */   {
/* 110 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 116 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.ViewAssetUseAction
 * JD-Core Version:    0.6.0
 */