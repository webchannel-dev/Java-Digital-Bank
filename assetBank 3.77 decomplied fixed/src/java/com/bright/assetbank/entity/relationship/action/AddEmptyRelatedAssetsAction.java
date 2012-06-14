/*     */ package com.bright.assetbank.entity.relationship.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.relationship.bean.AssetEntityRelationship;
/*     */ import com.bright.assetbank.entity.relationship.bean.EmptyRelatedAssetBatch;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*     */ import com.bright.assetbank.entity.relationship.util.RelationshipUtil;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.bean.NameValueBean;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class AddEmptyRelatedAssetsAction extends AssetRelationshipsUpdateAction
/*     */ {
/*     */   protected ActionForward performAction(Asset a_asset, ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  55 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  58 */     if ((a_asset != null) && (a_asset.getEntity() != null))
/*     */     {
/*  60 */       long lBatchChildId = RelationshipUtil.getEmptyRelatedAssetsBatchId(userProfile, a_asset, 2);
/*  61 */       long lBatchPeerId = RelationshipUtil.getEmptyRelatedAssetsBatchId(userProfile, a_asset, 1);
/*  62 */       String sQueryString = "id=" + a_asset.getId();
/*     */ 
/*  64 */       HashMap hmEmptyChildren = new HashMap();
/*  65 */       HashMap hmEmptyPeers = new HashMap();
/*  66 */       populateRelationshipQuantityHash(a_asset.getEntity().getChildRelationships(), hmEmptyChildren, a_request, "childquantity", "childname");
/*  67 */       populateRelationshipQuantityHash(a_asset.getEntity().getPeerRelationships(), hmEmptyPeers, a_request, "peerquantity", "peername");
/*     */ 
/*  71 */       EmptyRelatedAssetBatch childBatch = new EmptyRelatedAssetBatch();
/*  72 */       childBatch.setAsset(a_asset);
/*  73 */       childBatch.setRelatedAssets(hmEmptyChildren);
/*  74 */       childBatch.setRelType(2);
/*  75 */       childBatch.setUserProfile(userProfile);
/*  76 */       childBatch.setJobId(lBatchChildId);
/*     */ 
/*  78 */       EmptyRelatedAssetBatch peerBatch = new EmptyRelatedAssetBatch();
/*  79 */       peerBatch.setAsset(a_asset);
/*  80 */       peerBatch.setRelatedAssets(hmEmptyPeers);
/*  81 */       peerBatch.setRelType(1);
/*  82 */       peerBatch.setUserProfile(userProfile);
/*  83 */       peerBatch.setJobId(lBatchPeerId);
/*     */ 
/*  86 */       getAssetRelationshipManager().clearMessages(childBatch.getJobId());
/*  87 */       getAssetRelationshipManager().clearMessages(peerBatch.getJobId());
/*     */ 
/*  89 */       getAssetRelationshipManager().queueItem(childBatch);
/*  90 */       getAssetRelationshipManager().queueItem(peerBatch);
/*     */ 
/*  93 */       return createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */     }
/*     */ 
/*  96 */     this.m_logger.error(getClass().getSimpleName() + ".execute: Error adding empty relationships to asset either the asset is missing or un-typed");
/*  97 */     return a_mapping.findForward("Failure");
/*     */   }
/*     */ 
/*     */   private void populateRelationshipQuantityHash(ArrayList<AssetEntityRelationship> a_alRels, HashMap<Long, NameValueBean> a_hmMap, HttpServletRequest a_request, String a_sQuantityPrefix, String a_sNamePrefix)
/*     */   {
/* 110 */     for (AssetEntityRelationship aer : a_alRels)
/*     */     {
/* 112 */       int iQuantity = getIntParameter(a_request, a_sQuantityPrefix + aer.getRelatesToAssetEntityId());
/* 113 */       if (iQuantity > 0)
/*     */       {
/* 115 */         NameValueBean bean = new NameValueBean();
/* 116 */         bean.setValue(String.valueOf(iQuantity));
/* 117 */         bean.setName(a_request.getParameter(a_sNamePrefix + aer.getRelatesToAssetEntityId()));
/* 118 */         a_hmMap.put(new Long(aer.getRelatesToAssetEntityId()), bean);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.action.AddEmptyRelatedAssetsAction
 * JD-Core Version:    0.6.0
 */