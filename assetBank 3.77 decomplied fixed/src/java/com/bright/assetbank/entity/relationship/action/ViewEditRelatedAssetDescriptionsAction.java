/*     */ package com.bright.assetbank.entity.relationship.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.relationship.bean.AssetEntityRelationship;
/*     */ import com.bright.assetbank.entity.relationship.form.RelationshipDescriptionForm;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.StringObjectBean;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewEditRelatedAssetDescriptionsAction extends BTransactionAction
/*     */ {
/*  57 */   private AssetManager m_assetManager = null;
/*     */ 
/*  63 */   private MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/*  60 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/*  66 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  76 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  77 */     ActionForward afForward = null;
/*  78 */     long lSourceAssetId = getLongParameter(a_request, "id");
/*     */ 
/*  82 */     if (!FrameworkSettings.getSupportMultiLanguage())
/*     */     {
/*  84 */       String sTargetChildAssetIds = a_request.getParameter("childAssetIds");
/*  85 */       String sTargetPeerAssetIds = a_request.getParameter("peerAssetIds");
/*     */       try
/*     */       {
/*  89 */         RelationshipDescriptionForm form = (RelationshipDescriptionForm)a_form;
/*  90 */         Asset sourceAsset = this.m_assetManager.getAsset(a_dbTransaction, lSourceAssetId, null, false, false);
/*  91 */         form.setSourceAsset(sourceAsset);
/*     */ 
/*  93 */         if ((!userProfile.getIsAdmin()) && (!this.m_assetManager.userCanUpdateAsset(userProfile, sourceAsset)))
/*     */         {
/*  96 */           return a_mapping.findForward("NoPermission");
/*     */         }
/*     */ 
/* 100 */         Vector vecChildAssets = getAssets(sTargetChildAssetIds);
/* 101 */         Vector vecPeerAssets = getAssets(sTargetPeerAssetIds);
/* 102 */         boolean bFound = false;
/*     */ 
/* 107 */         if ((vecChildAssets != null) && (vecChildAssets.size() > 0))
/*     */         {
/* 109 */           populateRelationshipDescriptionList(form.getChildAssetDescriptions(), sourceAsset.getEntity().getChildRelationships(), vecChildAssets);
/* 110 */           if ((form.getChildAssetDescriptions() != null) && (form.getChildAssetDescriptions().size() > 0))
/*     */           {
/* 112 */             bFound = true;
/*     */           }
/*     */         }
/*     */ 
/* 116 */         if ((vecPeerAssets != null) && (vecPeerAssets.size() > 0))
/*     */         {
/* 118 */           populateRelationshipDescriptionList(form.getPeerAssetDescriptions(), sourceAsset.getEntity().getPeerRelationships(), vecPeerAssets);
/* 119 */           if ((form.getPeerAssetDescriptions() != null) && (form.getPeerAssetDescriptions().size() > 0))
/*     */           {
/* 121 */             bFound = true;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 126 */         if (bFound)
/*     */         {
/* 128 */           afForward = a_mapping.findForward("Success");
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 134 */         this.m_logger.error(getClass().getSimpleName() + ": Error : ", e);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 139 */     if (afForward == null)
/*     */     {
/* 142 */       afForward = createRedirectingForward("id=" + lSourceAssetId, a_mapping, "AddedRelatedAsset");
/*     */     }
/*     */ 
/* 145 */     return afForward;
/*     */   }
/*     */ 
/*     */   private Vector<LightweightAsset> getAssets(String a_sAssetIds)
/*     */     throws Bn2Exception
/*     */   {
/* 164 */     if (StringUtil.stringIsPopulated(a_sAssetIds))
/*     */     {
/* 166 */       SearchCriteria criteria = new SearchCriteria();
/*     */ 
/* 168 */       criteria.setAssetIds(a_sAssetIds.replaceAll(",", " "));
/* 169 */       SearchResults results = this.m_searchManager.search(criteria);
/* 170 */       return results.getSearchResults();
/*     */     }
/* 172 */     return null;
/*     */   }
/*     */ 
/*     */   private void populateRelationshipDescriptionList(ArrayList<StringObjectBean> a_alResult, ArrayList<AssetEntityRelationship> a_alRelationshipDefinitions, Vector<LightweightAsset> a_vecAssets)
/*     */   {
/* 188 */     for (Iterator i$ = a_alRelationshipDefinitions.iterator(); i$.hasNext(); ) {AssetEntityRelationship aer = (AssetEntityRelationship)i$.next();
/*     */ 
/* 190 */       if (StringUtil.stringIsPopulated(aer.getRelationshipDescriptionLabel()))
/*     */       {
/* 193 */         Vector <LightweightAsset> vecMatchingAssets = getMatchingAssetsForEntity(a_vecAssets, aer.getRelatesToAssetEntityId());
/* 194 */         for (LightweightAsset la : vecMatchingAssets)
/*     */         {
/* 196 */           StringObjectBean bean = new StringObjectBean(aer.getRelationshipDescriptionLabel(), la);
/* 197 */           a_alResult.add(bean);
/*     */         }
/*     */       }
/*     */     }
/*     */    // AssetEntityRelationship aer;
/*     */   }
/*     */ 
/*     */   private Vector<LightweightAsset> getMatchingAssetsForEntity(Vector<LightweightAsset> a_vecAssets, long a_lEntityId)
/*     */   {
/* 214 */     Vector vecMatches = new Vector();
/* 215 */     for (LightweightAsset la : a_vecAssets)
/*     */     {
/* 217 */       if (la.getEntity().getId() == a_lEntityId)
/*     */       {
/* 219 */         vecMatches.add(la);
/*     */       }
/*     */     }
/* 222 */     return vecMatches;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.action.ViewEditRelatedAssetDescriptionsAction
 * JD-Core Version:    0.6.0
 */