/*     */ package com.bright.assetbank.entity.relationship.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.constant.AssetEntityConstants;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class RelationshipUtil
/*     */   implements AssetEntityConstants
/*     */ {
/*     */   public static long getEmptyRelatedAssetsBatchId(ABUserProfile a_userProfile, Asset a_asset, int a_iRelationshipType)
/*     */     throws Bn2Exception
/*     */   {
/*  66 */     return a_userProfile.getSessionId() + a_asset.getId() + a_iRelationshipType;
/*     */   }
/*     */ 
/*     */   public static void populateRelatedAssets(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, Asset a_asset, AssetForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/*  88 */       AssetRelationshipManager arManager = (AssetRelationshipManager)(AssetRelationshipManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetRelationshipManager");
/*     */ 
/*  90 */       if (AssetBankSettings.getGroupRelatedAssets())
/*     */       {
/*  92 */         if (StringUtils.isNotEmpty(a_asset.getPeerAssetIdsAsString()))
/*     */         {
/*  94 */           a_form.setGroupedPeerAssets(arManager.getRelatedAssetsByCategoryNames(a_dbTransaction, new long[] { a_asset.getId() }, a_userProfile, 1L));
/*     */         }
/*  96 */         if (StringUtils.isNotEmpty(a_asset.getChildAssetIdsAsString()))
/*     */         {
/*  98 */           a_form.setChildAssets(arManager.getRelatedAssets(a_dbTransaction, new long[] { a_asset.getId() }, a_userProfile, 2L));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 103 */         if (StringUtils.isNotEmpty(a_asset.getPeerAssetIdsAsString()))
/*     */         {
/* 105 */           Vector vPeers = arManager.getRelatedAssets(a_dbTransaction, new long[] { a_asset.getId() }, a_userProfile, 1L);
/* 106 */           a_userProfile.setInAssetBoxFlagOnAll(vPeers);
/* 107 */           a_form.setPeerAssets(vPeers);
/*     */         }
/* 109 */         if (StringUtils.isNotEmpty(a_asset.getChildAssetIdsAsString()))
/*     */         {
/* 111 */           Vector vChildren = arManager.getRelatedAssets(a_dbTransaction, new long[] { a_asset.getId() }, a_userProfile, 2L);
/* 112 */           a_userProfile.setInAssetBoxFlagOnAll(vChildren);
/* 113 */           a_form.setChildAssets(vChildren);
/*     */ 
/* 115 */           for (int i = 0; i < vChildren.size(); i++)
/*     */           {
/* 117 */             if (!((LightweightAsset)vChildren.get(i)).getEntity().getMustHaveParent())
/*     */               continue;
/* 119 */             a_form.setChildrenMustHaveParents(true);
/* 120 */             break;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 125 */         if (StringUtils.isNotEmpty(a_asset.getParentAssetIdsAsString()))
/*     */         {
/* 128 */           long[] alIds = StringUtil.getIdsArray(a_asset.getParentAssetIdsAsString(), ",");
/* 129 */           Vector vSiblings = arManager.getRelatedAssets(a_dbTransaction, alIds, a_userProfile, 2L);
/* 130 */           Iterator itSiblings = vSiblings.iterator();
/*     */ 
/* 133 */           while (itSiblings.hasNext())
/*     */           {
/* 135 */             LightweightAsset asset = (LightweightAsset)itSiblings.next();
/* 136 */             if (asset.getId() == a_asset.getId())
/*     */             {
/* 138 */               itSiblings.remove();
/*     */             }
/*     */           }
/*     */ 
/* 142 */           a_form.setSiblingAssets(vSiblings);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 147 */       if ((a_asset.getEntity() != null) && (a_asset.getEntity().getId() > 0L) && (StringUtils.isNotEmpty(a_asset.getParentAssetIdsAsString())))
/*     */       {
/* 149 */         Vector vIds = StringUtil.convertToVectorOfLongs(a_asset.getParentAssetIdsAsString(), ",");
/* 150 */         AssetManager aManager = (AssetManager)(AssetManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetManager");
/* 151 */         a_form.setParentAssets(aManager.getAssets(null, vIds, false, false));
/*     */ 
/* 155 */         if ((a_form.getParentAssets() != null) && (AssetBankSettings.getIncludeParentMetadataForSearch()))
/*     */         {
/* 157 */           HashMap hmParentAttributes = new HashMap();
/*     */ 
/* 159 */           Iterator it = a_form.getParentAssets().iterator();
/* 160 */           while (it.hasNext())
/*     */           {
/* 162 */             Vector vecAttributes = new Vector();
/*     */ 
/* 164 */             Asset parent = (Asset)it.next();
/* 165 */             Vector vecAttributeValues = parent.getAttributeValues();
/*     */ 
/* 167 */             if (vecAttributeValues != null)
/*     */             {
/* 169 */               Iterator itAttrs = vecAttributeValues.iterator();
/* 170 */               while (itAttrs.hasNext())
/*     */               {
/* 172 */                 AttributeValue av = (AttributeValue)itAttrs.next();
/* 173 */                 if (av.getAttribute().getShowOnChild())
/*     */                 {
/* 175 */                   vecAttributes.add(av);
/*     */                 }
/*     */               }
/*     */             }
/*     */ 
/* 180 */             hmParentAttributes.put(new Long(parent.getId()), vecAttributes);
/*     */           }
/*     */ 
/* 183 */           a_form.setParentAttributes(hmParentAttributes);
/*     */         }
/*     */ 
/* 187 */         if ((a_form.getParentAssets().size() == 1) && (StringUtil.stringIsPopulated(a_asset.getEntity().getChildRelationshipFromName())))
/*     */         {
/* 190 */           a_form.setParentRelationshipName(a_asset.getEntity().getChildRelationshipFromName());
/*     */         }
/* 192 */         else if ((a_form.getParentAssets().size() > 1) && (StringUtil.stringIsPopulated(a_asset.getEntity().getChildRelationshipFromNamePlural())))
/*     */         {
/* 195 */           a_form.setParentRelationshipName(a_asset.getEntity().getChildRelationshipFromNamePlural());
/*     */         }
/* 197 */         a_form.setSiblingRelationshipName(a_asset.getEntity().getTermForSiblings());
/*     */       }
/*     */ 
/* 201 */       if (StringUtils.isEmpty(a_form.getSiblingRelationshipName()))
/*     */       {
/* 203 */         a_form.setSiblingRelationshipName(a_asset.getEntity().getTermForSiblings());
/*     */       }
/*     */     }
/*     */     catch (ComponentException e)
/*     */     {
/* 208 */       throw new Bn2Exception("RelationshipUtil.getRelatedAssets: Error getting components: ", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static LinkedHashMap<Long, Integer> getCopyChildRelationshipActions(Asset a_asset, HttpServletRequest a_request)
/*     */   {
/* 220 */     return getCopyChildRelationshipActions(a_asset, a_request, -1);
/*     */   }
/*     */ 
/*     */   public static LinkedHashMap<Long, Integer> getCopyChildRelationshipActions(Asset a_asset, int iRetainRelationshipType)
/*     */   {
/* 233 */     return getCopyChildRelationshipActions(a_asset, null, iRetainRelationshipType);
/*     */   }
/*     */ 
/*     */   private static LinkedHashMap<Long, Integer> getCopyChildRelationshipActions(Asset a_asset, HttpServletRequest a_request, int iDefaultRetainRelationshipType)
/*     */   {
/* 245 */     LinkedHashMap lhmChildRelationshipsActions = new LinkedHashMap();
/*     */ 
/* 247 */     if (StringUtil.stringIsPopulated(a_asset.getChildAssetIdsAsString()))
/*     */     {
/* 249 */       String[] aIds = a_asset.getChildAssetIdsAsString().split(",");
/*     */ 
/* 251 */       for (String sId : aIds)
/*     */       {
/* 253 */         if (a_request != null)
/*     */         {
/* 256 */           String sKeepRelationship = a_request.getParameter("shouldrelate" + sId);
/* 257 */           if ((StringUtil.stringIsPopulated(sKeepRelationship)) && (Integer.parseInt(sKeepRelationship) > 0))
/*     */           {
/* 261 */             String sRetainRelationshipType = a_request.getParameter("relate" + sId);
/* 262 */             lhmChildRelationshipsActions.put(Long.valueOf(Long.parseLong(sId)), Integer.valueOf(Integer.parseInt(sRetainRelationshipType)));
/*     */           }
/*     */           else
/*     */           {
/* 266 */             lhmChildRelationshipsActions.put(Long.valueOf(Long.parseLong(sId)), Integer.valueOf(3));
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 272 */           lhmChildRelationshipsActions.put(Long.valueOf(Long.parseLong(sId)), Integer.valueOf(iDefaultRetainRelationshipType));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 277 */     return lhmChildRelationshipsActions;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.util.RelationshipUtil
 * JD-Core Version:    0.6.0
 */