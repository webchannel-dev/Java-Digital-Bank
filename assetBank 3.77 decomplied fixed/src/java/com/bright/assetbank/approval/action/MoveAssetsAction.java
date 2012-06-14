/*     */ package com.bright.assetbank.approval.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.approval.constant.AssetApprovalConstants;
/*     */ import com.bright.assetbank.search.bean.BaseSearchQuery;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class MoveAssetsAction extends BTransactionAction
/*     */   implements AssetApprovalConstants
/*     */ {
/*  53 */   private MultiLanguageSearchManager m_searchManager = null;
/*  54 */   private AssetCategoryManager m_categoryManager = null;
/*  55 */   private AssetManager m_assetManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  82 */     ActionForward afForward = null;
/*     */ 
/*  85 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  87 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsInitialOrgUnitAdmin()))
/*     */     {
/*  89 */       this.m_logger.error("MoveAssetsAction.execute : User does not have admin permission : " + userProfile);
/*  90 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  94 */     long lFromCatId = getLongParameter(a_request, "fromCatId");
/*  95 */     long lToCatId = getLongParameter(a_request, "toCatId");
/*  96 */     long lCatTypeId = getLongParameter(a_request, "categoryTypeId");
/*     */ 
/*  98 */     BaseSearchQuery searchCriteria = new SearchCriteria();
/*  99 */     searchCriteria.setCategoryIds(String.valueOf(lFromCatId));
/*     */ 
/* 101 */     SearchResults sr = this.m_searchManager.search(searchCriteria);
/* 102 */     Vector vecItems = sr.getSearchResults();
/* 103 */     Asset asset = null;
/* 104 */     int iAction = 0;
/*     */ 
/* 106 */     for (int i = 0; i < vecItems.size(); i++)
/*     */     {
/* 108 */       asset = (Asset)vecItems.elementAt(i);
/*     */ 
/* 111 */       iAction = getIntParameter(a_request, "action_" + asset.getId());
/*     */ 
/* 114 */       if ((iAction != 0) && (iAction != 2))
/*     */         continue;
/* 116 */       if (iAction == 0)
/*     */       {
/* 118 */         this.m_categoryManager.deleteItemFromCategory(a_dbTransaction, asset.getId(), lFromCatId, lCatTypeId);
/*     */       }
/*     */       else
/*     */       {
/* 122 */         this.m_categoryManager.deleteItemFromCategory(a_dbTransaction, asset.getId(), lFromCatId, lCatTypeId);
/* 123 */         this.m_categoryManager.addItemToCategory(a_dbTransaction, asset.getId(), lToCatId, lCatTypeId);
/*     */       }
/*     */ 
/* 127 */       asset = this.m_assetManager.getAsset(a_dbTransaction, asset.getId(), null, true, true);
/* 128 */       this.m_searchManager.indexDocument(asset, true);
/*     */     }
/*     */ 
/* 135 */     String sQueryString = "fromCatId=" + lFromCatId + "&" + "toCatId" + "=" + lToCatId;
/* 136 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */ 
/* 138 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/* 143 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_categoryManager)
/*     */   {
/* 148 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/* 153 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.approval.action.MoveAssetsAction
 * JD-Core Version:    0.6.0
 */