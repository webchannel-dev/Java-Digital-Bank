/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewPopularAssetAction extends Bn2Action
/*     */ {
/*  48 */   protected IAssetManager m_assetManager = null;
/*     */ 
/*     */   public final ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  66 */     this.m_logger.debug("In ViewPopularAssetAction.execute");
/*  67 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  69 */     int index = getIntParameter(a_request, "index");
/*  70 */     int iPopularityId = getIntParameter(a_request, "popularityId");
/*     */ 
/*  73 */     SearchResults results = null;
/*     */ 
/*  75 */     switch (iPopularityId)
/*     */     {
/*     */     case 1:
/*  79 */       results = this.m_assetManager.getAssetsByViews(AssetBankSettings.getNumberMostPopularAssets(), userProfile, index, 1, userProfile.getCurrentLanguage().getCode(), true);
/*     */ 
/*  81 */       break;
/*     */     case 2:
/*  85 */       results = this.m_assetManager.getAssetsByDownloads(AssetBankSettings.getNumberMostPopularAssets(), userProfile, index, 1, userProfile.getCurrentLanguage().getCode(), true);
/*     */ 
/*  87 */       break;
/*     */     case 3:
/*  91 */       results = this.m_assetManager.getAssetsByViews(AssetBankSettings.getNumberLeastPopularAssets(), userProfile, index, 1, userProfile.getCurrentLanguage().getCode(), false);
/*     */ 
/*  93 */       break;
/*     */     case 4:
/*  97 */       results = this.m_assetManager.getAssetsByDownloads(AssetBankSettings.getNumberLeastPopularAssets(), userProfile, index, 1, userProfile.getCurrentLanguage().getCode(), false);
/*     */     }
/*     */ 
/* 104 */     LightweightAsset result = (LightweightAsset)results.getSearchResults().elementAt(0);
/* 105 */     long lItemId = result.getId();
/*     */ 
/* 108 */     long lTotal = results.getNumResults();
/*     */ 
/* 110 */     String sQueryString = "id=" + lItemId + "&" + "index" + "=" + index + "&" + "total" + "=" + lTotal + "&" + "popularityId" + "=" + iPopularityId;
/*     */ 
/* 116 */     String sShowAdd = a_request.getParameter("showadd");
/* 117 */     if (StringUtil.stringIsPopulated(sShowAdd))
/*     */     {
/* 119 */       sQueryString = sQueryString + "&showadd=true";
/*     */     }
/*     */ 
/* 122 */     return createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 128 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewPopularAssetAction
 * JD-Core Version:    0.6.0
 */