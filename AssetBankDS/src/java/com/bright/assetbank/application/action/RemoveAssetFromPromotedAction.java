/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class RemoveAssetFromPromotedAction extends BTransactionAction
/*     */ {
/*  48 */   protected IAssetManager m_assetManager = null;
/*  49 */   protected MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  70 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  73 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  75 */       this.m_logger.debug("This user does not have permission to remove an asset from the promoted set");
/*  76 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  82 */       long lAssetId = getLongParameter(a_request, "id");
/*  83 */       this.m_assetManager.removeAssetFromPromoted(a_dbTransaction, lAssetId);
/*     */ 
/*  86 */       Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, null, false, true);
/*  87 */       this.m_searchManager.indexDocument(asset, true);
/*     */     }
/*     */     catch (Bn2Exception be)
/*     */     {
/*  91 */       this.m_logger.error("Exception in RemoveAssetFromPromotedAction: " + be.getMessage());
/*  92 */       throw be;
/*     */     }
/*     */ 
/*  96 */     int iPageIndex = getIntParameter(a_request, "page");
/*     */ 
/*  98 */     String sQuery = "page=" + iPageIndex;
/*     */ 
/* 100 */     int iPageSize = getIntParameter(a_request, "pageSize");
/*     */ 
/* 102 */     if (iPageSize > 0)
/*     */     {
/* 104 */       sQuery = sQuery + "&pageSize=" + iPageSize;
/*     */     }
/*     */ 
/* 108 */     return createRedirectingForward(sQuery, a_mapping, "Success");
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 113 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/* 118 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.RemoveAssetFromPromotedAction
 * JD-Core Version:    0.6.0
 */