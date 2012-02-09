/*     */ package com.bright.assetbank.entity.relationship.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.entity.action.AssetEntityAction;
/*     */ import com.bright.assetbank.entity.constant.AssetEntityConstants;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public abstract class AssetRelationshipsUpdateAction extends AssetEntityAction
/*     */   implements AssetEntityConstants
/*     */ {
/*  51 */   private AssetManager m_assetManager = null;
/*  52 */   private AssetRelationshipManager m_assetRelationshipManager = null;
/*  53 */   private MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  62 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  63 */     AssetForm form = (AssetForm)a_form;
/*     */ 
/*  65 */     long lAssetId = getLongParameter(a_request, "id");
/*  66 */     if (lAssetId <= 0L)
/*     */     {
/*  68 */       lAssetId = form.getAsset().getId();
/*     */     }
/*  70 */     Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, null, false, false);
/*     */ 
/*  72 */     if ((!userProfile.getIsAdmin()) && (!this.m_assetManager.userCanUpdateAsset(userProfile, asset)))
/*     */     {
/*  75 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  78 */     ActionForward forward = performAction(asset, a_mapping, form, a_request, a_response, a_dbTransaction);
/*     */ 
/*  80 */     if (forward != null)
/*     */     {
/*  82 */       return forward;
/*     */     }
/*  84 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   protected void reindexAssets(DBTransaction a_transaction, Vector<Long> a_vecAssetsToReindex)
/*     */     throws Bn2Exception
/*     */   {
/*  98 */     if ((a_vecAssetsToReindex != null) && (a_vecAssetsToReindex.size() > 0))
/*     */     {
/* 100 */       Vector assets = new Vector();
/* 101 */       for (Long longIdToIndex : a_vecAssetsToReindex)
/*     */       {
/* 103 */         assets.add(getAssetManager().getAsset(a_transaction, longIdToIndex.longValue(), null, true, true));
/*     */       }
/*     */ 
/* 107 */       if ((AssetBankSettings.getAllowPublishing()) && (AssetBankSettings.getIncludeParentMetadataForExport()))
/*     */       {
/* 109 */         getAssetManager().updateDateLastModifiedForAssets(a_transaction, new GregorianCalendar().getTime(), a_vecAssetsToReindex);
/*     */       }
/*     */ 
/* 112 */       this.m_searchManager.indexDocuments(a_transaction, assets, true, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected abstract ActionForward performAction(Asset paramAsset, ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, DBTransaction paramDBTransaction)
/*     */     throws Bn2Exception;
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/* 127 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/* 132 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   protected AssetManager getAssetManager()
/*     */   {
/* 137 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetRelationshipManager(AssetRelationshipManager a_assetRelationshipManager)
/*     */   {
/* 142 */     this.m_assetRelationshipManager = a_assetRelationshipManager;
/*     */   }
/*     */ 
/*     */   protected AssetRelationshipManager getAssetRelationshipManager()
/*     */   {
/* 147 */     return this.m_assetRelationshipManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.action.AssetRelationshipsUpdateAction
 * JD-Core Version:    0.6.0
 */