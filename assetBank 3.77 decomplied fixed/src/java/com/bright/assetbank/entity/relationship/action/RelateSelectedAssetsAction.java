/*     */ package com.bright.assetbank.entity.relationship.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.exception.InvalidRelationshipException;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetEntityRelationshipManager;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.synchronise.util.SynchUtil;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.CollectionUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class RelateSelectedAssetsAction extends BTransactionAction
/*     */   implements AssetBoxConstants, MessageConstants, AssetBankConstants, ImageConstants
/*     */ {
/*  64 */   private IAssetManager m_assetManager = null;
/*  65 */   private AssetRelationshipManager m_assetRelationshipManager = null;
/*     */ 
/*  67 */   private AssetEntityRelationshipManager m_assetEntityRelationshipManager = null;
/*  68 */   private ListManager m_listManager = null;
/*  69 */   private MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  78 */     ActionForward afForward = null;
/*  79 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  81 */     long lOriginalAssetId = getLongParameter(a_request, "sourceAssetId");
/*  82 */     long lRelationshipTypeId = getLongParameter(a_request, "relationshipTypeId");
/*  83 */     String[] asSelectedAssetIds = new String[0];
/*     */ 
/*  86 */     int iAll = getIntParameter(a_request, "all");
/*  87 */     if (iAll > 0)
/*     */     {
/*  89 */       SearchQuery lastSearch = userProfile.getSearchCriteria();
/*  90 */       SearchResults results = this.m_searchManager.search(lastSearch, userProfile.getCurrentLanguage().getCode());
/*  91 */       Vector vecResults = results.getSearchResults();
/*     */ 
/*  93 */       if (vecResults != null)
/*     */       {
/*  95 */         asSelectedAssetIds = new String[vecResults.size()];
/*     */ 
/*  97 */         for (int i = 0; i < vecResults.size(); i++)
/*     */         {
/*  99 */           LightweightAsset result = (LightweightAsset)vecResults.elementAt(i);
/* 100 */           asSelectedAssetIds[i] = String.valueOf(result.getId());
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 106 */       asSelectedAssetIds = a_request.getParameterValues("selectedAssetId");
/*     */     }
/*     */ 
/* 109 */     if (a_request.getParameter("b_cancel") != null)
/*     */     {
/* 111 */       return createRedirectingForward("id=" + lOriginalAssetId, a_mapping, "Success");
/*     */     }
/*     */ 
/* 114 */     Asset originalAsset = this.m_assetManager.getAsset(null, lOriginalAssetId, null, false, false);
/*     */ 
/* 117 */     AssetForm form = (AssetForm)a_form;
/* 118 */     asSelectedAssetIds = CollectionUtil.removeFromStringArray(asSelectedAssetIds, String.valueOf(originalAsset.getId()));
/*     */     try
/*     */     {
/* 122 */       for (int i = 0; i < asSelectedAssetIds.length; i++)
/*     */       {
/* 124 */         this.m_assetEntityRelationshipManager.validateAssetRelationships(a_dbTransaction, originalAsset.getEntity().getId(), asSelectedAssetIds[i], lRelationshipTypeId, userProfile.getCurrentLanguage());
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (InvalidRelationshipException e)
/*     */     {
/* 130 */       String sMessageId = e.isInvalidEfference() ? "failedValidationInvalidToRelationship" : "failedValidationInvalidFromRelationship";
/* 131 */       String[] asParams = { String.valueOf(e.getAfferentId()), e.getRelationshipName() };
/* 132 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, sMessageId, userProfile.getCurrentLanguage(), asParams));
/* 133 */       return createForward("cachedCriteria=1", a_mapping, "Failure");
/*     */     }
/*     */ 
/* 136 */     String sIds = originalAsset.getRelatedAssetIdsAsString(lRelationshipTypeId);
/* 137 */     if (sIds == null)
/*     */     {
/* 139 */       sIds = "";
/*     */     }
/* 141 */     sIds = sIds + "," + StringUtil.convertStringArrayToString(asSelectedAssetIds, ",");
/* 142 */     this.m_assetRelationshipManager.saveRelatedAssetIds(a_dbTransaction, originalAsset, sIds, lRelationshipTypeId, false);
/*     */ 
/* 144 */     Vector assetsToIndex = new Vector();
/* 145 */     Vector assetsIdsToIndex = StringUtil.convertToVectorOfLongs(sIds, ",");
/*     */     try
/*     */     {
/* 150 */       a_dbTransaction.commit();
/* 151 */       a_dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 155 */       throw new Bn2Exception(sqle.getMessage(), sqle);
/*     */     }
/*     */ 
/* 159 */     for (Iterator i$ = assetsIdsToIndex.iterator(); i$.hasNext(); ) { long lId = ((Long)i$.next()).longValue();
/*     */ 
/* 161 */       assetsToIndex.add(this.m_assetManager.getAsset(a_dbTransaction, lId, null, true, true));
/*     */     }
/*     */ 
/* 165 */     addAssetToIndexList(a_dbTransaction, lOriginalAssetId, lRelationshipTypeId, assetsToIndex);
/*     */ 
/* 167 */     SynchUtil.setLastModifiedDatesForSyncAssets(a_dbTransaction, assetsToIndex, this.m_assetManager);
/*     */     try
/*     */     {
/* 171 */       a_dbTransaction.commit();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 175 */       throw new Bn2Exception(sqle.getMessage());
/*     */     }
/*     */ 
/* 179 */     this.m_searchManager.indexDocuments(null, assetsToIndex, true);
/*     */ 
/* 182 */     String sParam = null;
/* 183 */     if (lRelationshipTypeId == 1L)
/*     */     {
/* 185 */       sParam = "peerAssetIds";
/*     */     }
/* 187 */     else if (lRelationshipTypeId == 2L)
/*     */     {
/* 189 */       sParam = "childAssetIds";
/*     */     }
/*     */ 
/* 192 */     afForward = createRedirectingForward("id=" + lOriginalAssetId + "&" + sParam + "=" + StringUtil.convertStringArrayToString(asSelectedAssetIds, ","), a_mapping, "Success");
/*     */ 
/* 194 */     return afForward;
/*     */   }
/*     */ 
/*     */   private void addAssetToIndexList(DBTransaction a_dbTransaction, long a_lOriginalAssetId, long a_lRelationshipTypeId, Vector<Asset> a_assetsToIndex)
/*     */     throws AssetNotFoundException, Bn2Exception
/*     */   {
/* 210 */     if ((a_lRelationshipTypeId == 1L) || (((!AssetBankSettings.getUseFirstChildAssetAsSurrogate()) && (!AssetBankSettings.getSortByPresenceOfChildAssets())) || ((a_lRelationshipTypeId == 2L) || ((AssetBankSettings.getIncludeParentMetadataForSearch()) && (a_lRelationshipTypeId == 3L)))))
/*     */     {
/* 214 */       Asset asset = this.m_assetManager.getAsset(a_dbTransaction, a_lOriginalAssetId, null, true, true);
/*     */ 
/* 217 */       if ((a_lRelationshipTypeId != 2L) || (AssetBankSettings.getSortByPresenceOfChildAssets()) || (!AssetBankSettings.getUseFirstChildAssetAsSurrogate()) || (asset.getSurrogateAssetId() > 0L))
/*     */       {
/* 222 */         a_assetsToIndex.add(asset);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 230 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetRelationshipManager(AssetRelationshipManager a_assetRelationshipManager)
/*     */   {
/* 235 */     this.m_assetRelationshipManager = a_assetRelationshipManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 240 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager searchManager)
/*     */   {
/* 246 */     this.m_searchManager = searchManager;
/*     */   }
/*     */ 
/*     */   public void setAssetEntityRelationshipManager(AssetEntityRelationshipManager assetEntityRelationshipManager)
/*     */   {
/* 252 */     this.m_assetEntityRelationshipManager = assetEntityRelationshipManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.action.RelateSelectedAssetsAction
 * JD-Core Version:    0.6.0
 */