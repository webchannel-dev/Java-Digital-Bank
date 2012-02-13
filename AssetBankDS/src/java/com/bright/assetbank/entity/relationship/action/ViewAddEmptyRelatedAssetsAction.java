/*     */ package com.bright.assetbank.entity.relationship.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.relationship.bean.AssetEntityRelationship;
/*     */ import com.bright.assetbank.entity.relationship.form.AddEmptyRelatedAssetsForm;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*     */ import com.bright.assetbank.entity.relationship.util.RelationshipUtil;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.bean.IdValueBean;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewAddEmptyRelatedAssetsAction extends AssetRelationshipsUpdateAction
/*     */ {
/*     */   protected ActionForward performAction(Asset a_asset, ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  58 */     AddEmptyRelatedAssetsForm form = (AddEmptyRelatedAssetsForm)a_form;
/*  59 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  61 */     long lBatchChildId = RelationshipUtil.getEmptyRelatedAssetsBatchId(userProfile, a_asset, 2);
/*  62 */     long lBatchPeerId = RelationshipUtil.getEmptyRelatedAssetsBatchId(userProfile, a_asset, 1);
/*     */ 
/*  64 */     if ((!getAssetRelationshipManager().isBatchInProgress(lBatchChildId)) && (!getAssetRelationshipManager().isBatchInProgress(lBatchPeerId)))
/*     */     {
/*  68 */       Vector vecEntities = getAssetEntityManager().getAllEntities(a_dbTransaction);
/*  69 */       form.setPotentialChildren(getIdValuePairsFromEntities(a_asset.getEntity().getChildRelationships(), vecEntities, userProfile.getCurrentLanguage()));
/*  70 */       form.setPotentialPeers(getIdValuePairsFromEntities(a_asset.getEntity().getPeerRelationships(), vecEntities, userProfile.getCurrentLanguage()));
/*     */ 
/*  73 */       form.setAsset(a_asset);
/*     */ 
/*  75 */       return a_mapping.findForward("Success");
/*     */     }
/*     */ 
/*  79 */     String sQueryString = "id=" + a_asset.getId();
/*  80 */     return createRedirectingForward(sQueryString, a_mapping, "Status");
/*     */   }
/*     */ 
/*     */   private ArrayList<IdValueBean> getIdValuePairsFromEntities(ArrayList<AssetEntityRelationship> a_aers, Vector<AssetEntity> a_vecEntities, Language a_language)
/*     */   {
/*  96 */     ArrayList alPairs = new ArrayList();
/*  97 */     if (a_aers != null)
/*     */     {
/*  99 */       for (AssetEntityRelationship aer : a_aers)
/*     */       {
/* 101 */         IdValueBean pair = new IdValueBean();
/* 102 */         pair.setId(aer.getRelatesToAssetEntityId());
/*     */ 
/* 104 */         for (AssetEntity entity : a_vecEntities)
/*     */         {
/* 106 */           if (entity.getId() == pair.getId())
/*     */           {
/* 108 */             entity.setLanguage(a_language);
/* 109 */             pair.setValue(entity.getName());
/* 110 */             break;
/*     */           }
/*     */         }
/* 113 */         alPairs.add(pair);
/*     */       }
/*     */     }
/* 116 */     return alPairs;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.action.ViewAddEmptyRelatedAssetsAction
 * JD-Core Version:    0.6.0
 */