/*     */ package com.bright.assetbank.entity.relationship.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.entity.relationship.bean.RelationshipDescriptionEntry;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveAssetRelationshipDescriptionsAction extends AssetRelationshipsUpdateAction
/*     */ {
/*     */   public ActionForward performAction(Asset a_asset, ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  52 */     ArrayList alEntries = getRelationshipDescriptions(a_asset, a_request);
/*     */ 
/*  55 */     getAssetRelationshipManager().saveAssetRelationshipDescriptions(a_dbTransaction, alEntries);
/*     */ 
/*  58 */     Vector vecIdsToReindex = getTargetAssetIdsFromRelationshipDescriptions(alEntries);
/*  59 */     reindexAssets(a_dbTransaction, vecIdsToReindex);
/*     */ 
/*  62 */     String sQueryString = "id=" + a_asset.getId();
/*  63 */     ActionForward afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*  64 */     return afForward;
/*     */   }
/*     */ 
/*     */   private ArrayList<RelationshipDescriptionEntry> getRelationshipDescriptions(Asset a_asset, HttpServletRequest a_request)
/*     */   {
/*  84 */     ArrayList alEntries = new ArrayList();
/*     */ 
/*  87 */     populateEntriesForRelationshipType(alEntries, a_asset, a_request, a_asset.getChildAssetIdsAsString(), 2L);
/*  88 */     populateEntriesForRelationshipType(alEntries, a_asset, a_request, a_asset.getPeerAssetIdsAsString(), 1L);
/*     */ 
/*  90 */     return alEntries;
/*     */   }
/*     */ 
/*     */   private Vector<Long> getTargetAssetIdsFromRelationshipDescriptions(ArrayList<RelationshipDescriptionEntry> a_alEntries)
/*     */   {
/* 103 */     Vector vecIds = new Vector(a_alEntries.size());
/* 104 */     for (RelationshipDescriptionEntry rde : a_alEntries)
/*     */     {
/* 106 */       vecIds.add(Long.valueOf(rde.getTargetAssetId()));
/*     */     }
/* 108 */     return vecIds;
/*     */   }
/*     */ 
/*     */   private void populateEntriesForRelationshipType(ArrayList<RelationshipDescriptionEntry> a_alEntries, Asset a_asset, HttpServletRequest a_request, String a_sRelatedAssetIds, long a_lRelationshipTypeId)
/*     */   {
/* 128 */     if (StringUtil.stringIsPopulated(a_sRelatedAssetIds))
/*     */     {
/* 130 */       String[] aIds = a_sRelatedAssetIds.split(",");
/* 131 */       for (String sId : aIds)
/*     */       {
/* 133 */         String sIdentifier = sId + "relationshipDescription" + a_lRelationshipTypeId;
/* 134 */         String sDescription = a_request.getParameter(sIdentifier);
/* 135 */         if (!StringUtil.stringIsPopulated(sDescription))
/*     */           continue;
/* 137 */         RelationshipDescriptionEntry entry = new RelationshipDescriptionEntry();
/* 138 */         entry.setSourceAssetId(a_asset.getId());
/* 139 */         entry.setTargetAssetId(Long.parseLong(sId));
/* 140 */         entry.setRelationshipTypeId(a_lRelationshipTypeId);
/* 141 */         entry.setDescription(sDescription);
/* 142 */         a_alEntries.add(entry);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.action.SaveAssetRelationshipDescriptionsAction
 * JD-Core Version:    0.6.0
 */