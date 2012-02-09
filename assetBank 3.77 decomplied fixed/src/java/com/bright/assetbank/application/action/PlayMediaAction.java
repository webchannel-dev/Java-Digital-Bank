/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class PlayMediaAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  46 */   private IAssetManager m_assetManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  71 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  74 */     String sFile = a_request.getParameter("file");
/*     */ 
/*  76 */     if (StringUtil.stringIsPopulated(sFile))
/*     */     {
/*  78 */       a_request.setAttribute("file", sFile);
/*     */ 
/*  80 */       return a_mapping.findForward("Success");
/*     */     }
/*     */ 
/*  85 */     int lAssetId = getIntParameter(a_request, "id");
/*     */     try
/*     */     {
/*  88 */       Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, null, false, userProfile.getCurrentLanguage(), false);
/*     */ 
/*  91 */       String sEncryptedPath = asset.getEncryptedEmbeddedPreviewClipLocation();
/*     */ 
/*  93 */       a_request.setAttribute("file", sEncryptedPath);
/*     */     }
/*     */     catch (AssetNotFoundException anfe)
/*     */     {
/*     */     }
/*     */ 
/* 108 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 114 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.PlayMediaAction
 * JD-Core Version:    0.6.0
 */