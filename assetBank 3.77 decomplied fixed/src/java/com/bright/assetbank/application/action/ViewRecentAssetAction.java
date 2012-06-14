/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewRecentAssetAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  46 */   protected IAssetManager m_assetManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  56 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  59 */     ActionForward afForward = null;
/*     */ 
/*  62 */     int iIndex = getIntParameter(a_request, "index");
/*     */ 
/*  65 */     SearchResults srRecentAssets = this.m_assetManager.getRecentAssets(a_dbTransaction, AssetBankSettings.getNumberMoreRecentAssets(), userProfile, 0, AssetBankSettings.getNumberMoreRecentAssets(), false);
/*     */ 
/*  75 */     long lTotal = srRecentAssets.getNumResults();
/*  76 */     long lItemId = 0L;
/*  77 */     long lExpectedId = getLongParameter(a_request, "expectedAssetId");
/*     */ 
/*  79 */     if (srRecentAssets.getSearchResults().size() > iIndex)
/*     */     {
/*  82 */       LightweightAsset result = (LightweightAsset)srRecentAssets.getSearchResults().elementAt(iIndex);
/*  83 */       lItemId = result.getId();
/*     */     }
/*     */     String sQueryString;
/*  91 */     if ((lExpectedId > 0L) && (lExpectedId != lItemId))
/*     */     {
/*  93 */       sQueryString = "id=" + lExpectedId;
/*     */     }
/*     */     else
/*     */     {
/*  97 */       sQueryString = "id=" + lItemId + "&" + "index" + "=" + iIndex + "&" + "total" + "=" + lTotal + "&" + "recent" + "=true";
/*     */     }
/*     */ 
/* 103 */     sQueryString = sQueryString + "&recent=true";
/*     */ 
/* 105 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/* 106 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 111 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewRecentAssetAction
 * JD-Core Version:    0.6.0
 */