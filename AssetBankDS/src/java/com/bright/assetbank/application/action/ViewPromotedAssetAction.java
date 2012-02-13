/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
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
/*     */ public class ViewPromotedAssetAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  45 */   protected IAssetManager m_assetManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  55 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  58 */     ActionForward afForward = null;
/*     */ 
/*  61 */     int iIndex = getIntParameter(a_request, "index");
/*     */ 
/*  64 */     SearchResults srPromotedAssets = this.m_assetManager.getPromotedAssets(a_dbTransaction, 0, iIndex + 1, userProfile.getSelectedFilters(), userProfile);
/*     */ 
/*  72 */     long lTotal = srPromotedAssets.getNumResults();
/*  73 */     long lItemId = 0L;
/*  74 */     long lExpectedId = getLongParameter(a_request, "expectedAssetId");
/*     */ 
/*  76 */     if (srPromotedAssets.getSearchResults().size() > iIndex)
/*     */     {
/*  79 */       LightweightAsset result = (LightweightAsset)srPromotedAssets.getSearchResults().elementAt(iIndex);
/*  80 */       lItemId = result.getId();
/*     */     }
/*     */     String sQueryString;
/*  88 */     if ((lExpectedId > 0L) && (lExpectedId != lItemId))
/*     */     {
/*  90 */       sQueryString = "id=" + lExpectedId;
/*     */     }
/*     */     else
/*     */     {
/*  94 */       sQueryString = "id=" + lItemId + "&" + "index" + "=" + iIndex + "&" + "total" + "=" + lTotal + "&" + "promoted" + "=true";
/*     */     }
/*     */ 
/* 100 */     sQueryString = sQueryString + "&recent=true";
/*     */ 
/* 102 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/* 103 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 108 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewPromotedAssetAction
 * JD-Core Version:    0.6.0
 */