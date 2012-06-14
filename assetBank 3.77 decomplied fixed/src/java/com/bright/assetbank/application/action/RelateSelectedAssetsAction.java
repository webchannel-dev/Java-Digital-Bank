/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*     */ import com.bright.assetbank.application.form.AssetForm;
import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.exception.InvalidRelationshipException;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.synchronise.util.SynchUtil;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.message.constant.MessageConstants;
import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
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
/*  63 */   private IAssetManager m_assetManager = null;
/*  64 */   private AssetEntityManager m_assetEntityManager = null;
/*  65 */   private ListManager m_listManager = null;
/*  66 */   private MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  75 */     ActionForward afForward = null;
/*  76 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  78 */     long lOriginalAssetId = getLongParameter(a_request, "sourceAssetId");
/*  79 */     long lRelationshipTypeId = getLongParameter(a_request, "relationshipTypeId");
/*  80 */     String[] asSelectedAssetIds = new String[0];
/*     */ 
/*  83 */     int iAll = getIntParameter(a_request, "all");
/*  84 */     if (iAll > 0)
/*     */     {
/*  86 */       SearchQuery lastSearch = userProfile.getSearchCriteria();
/*  87 */       SearchResults results = this.m_searchManager.search(lastSearch,userProfile.getCurrentLanguage().getCode());
/*  88 */       Vector vecResults = results.getSearchResults();
/*     */ 
/*  90 */       if (vecResults != null)
/*     */       {
/*  92 */         asSelectedAssetIds = new String[vecResults.size()];
/*     */ 
/*  94 */         for (int i = 0; i < vecResults.size(); i++)
/*     */         {
/*  96 */           LightweightAsset result = (LightweightAsset)vecResults.elementAt(i);
/*  97 */           asSelectedAssetIds[i] = String.valueOf(result.getId());
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 103 */       asSelectedAssetIds = a_request.getParameterValues("selectedAssetId");
/*     */     }
/*     */ 
/* 106 */     if (a_request.getParameter("b_cancel") != null)
/*     */     {
/* 108 */       return createRedirectingForward("id=" + lOriginalAssetId, a_mapping, "Success");
/*     */     }
/*     */ 
/* 111 */     Asset originalAsset = this.m_assetManager.getAsset(null, lOriginalAssetId, null, false, false);
/*     */    // try
/*     */    // {
/* 115 */    //   for (int i = 0; i < asSelectedAssetIds.length; i++)
/*     */    //   {
/* 117 */    //     this.m_assetEntityManager.assertRelationships(a_dbTransaction, originalAsset.getEntity().getId(), asSelectedAssetIds[i], lRelationshipTypeId, userProfile.getCurrentLanguage());
/*     */   //    }
/*     */ 
/*     */   //  }
/*     */   //  catch (InvalidRelationshipException e)
/*     */   //  {
/* 123 */   //    AssetForm form = (AssetForm)a_form;
/* 124 */   //    String sMessageId = e.isInvalidEfference() ? "failedValidationInvalidToRelationship" : "failedValidationInvalidFromRelationship";
/* 125 */   //    String[] asParams = { String.valueOf(e.getAfferentId()), e.getRelationshipName() };
/* 126 */   //    form.addError(this.m_listManager.getListItem(a_dbTransaction, sMessageId, userProfile.getCurrentLanguage(), asParams));
/* 127 */    //   return createForward("cachedCriteria=1", a_mapping, "Failure");
/*     */   //  }
/*     */ 
/* 130 */     Vector assetsToIndex = new Vector();
/* 131 */     Set assetsIdsToIndex = new HashSet();
/*     */ 
/* 134 */     for (int i = 0; i < asSelectedAssetIds.length; i++)
/*     */     {
/* 136 */       Vector vIdsToIndex =((AssetManager) this.m_assetManager).addAssetRelationships(a_dbTransaction, lOriginalAssetId, asSelectedAssetIds[i], lRelationshipTypeId);
/* 137 */       assetsIdsToIndex.addAll(vIdsToIndex);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 143 */       a_dbTransaction.commit();
/* 144 */       a_dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 148 */       throw new Bn2Exception(sqle.getMessage());
/*     */     }
/*     */ 
/* 152 */     if (assetsIdsToIndex.size() > 0)
/*     */     {
/* 154 */       Iterator itIds = assetsIdsToIndex.iterator();
/* 155 */       while (itIds.hasNext())
/*     */       {
/* 157 */         assetsToIndex.add(this.m_assetManager.getAsset(a_dbTransaction, ((Long)itIds.next()).longValue(), null, true, true));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 162 */     addAssetToIndexList(a_dbTransaction, lOriginalAssetId, lRelationshipTypeId, assetsToIndex);
/*     */ 
/* 164 */     SynchUtil.setLastModifiedDatesForSyncAssets(a_dbTransaction, assetsToIndex, this.m_assetManager);
/*     */     try
/*     */     {
/* 168 */       a_dbTransaction.commit();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 172 */       throw new Bn2Exception(sqle.getMessage());
/*     */     }
/*     */ 
/* 176 */     this.m_searchManager.indexDocuments(null, assetsToIndex, true);
/*     */ 
/* 179 */     afForward = createRedirectingForward("id=" + lOriginalAssetId, a_mapping, "Success");
/*     */ 
/* 181 */     return afForward;
/*     */   }
/*     */ 
/*     */   private void addAssetToIndexList(DBTransaction a_dbTransaction, long a_lOriginalAssetId, long a_lRelationshipTypeId, Vector a_assetsToIndex)
/*     */     throws AssetNotFoundException, Bn2Exception
/*     */   {
/* 197 */     if ((a_lRelationshipTypeId == 1L) || (
/* 198 */       ((!AssetBankSettings.getUseFirstChildAssetAsSurrogate()) && (!AssetBankSettings.getSortByPresenceOfChildAssets())) || ((a_lRelationshipTypeId == 2L) || (
/* 199 */       (AssetBankSettings.getIncludeParentMetadataForSearch()) && (a_lRelationshipTypeId == 3L)))))
/*     */     {
/* 201 */       Asset asset = this.m_assetManager.getAsset(a_dbTransaction, a_lOriginalAssetId, null, true, true);
/*     */ 
/* 204 */       if ((a_lRelationshipTypeId != 2L) || 
/* 205 */         (AssetBankSettings.getSortByPresenceOfChildAssets()) || 
/* 206 */         (!AssetBankSettings.getUseFirstChildAssetAsSurrogate()) || 
/* 207 */         (asset.getSurrogateAssetId() > 0L))
/*     */       {
/* 209 */         a_assetsToIndex.add(asset);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 217 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 222 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager searchManager)
/*     */   {
/* 228 */     this.m_searchManager = searchManager;
/*     */   }
/*     */ 
/*     */   public void setAssetEntityManager(AssetEntityManager assetEntityManager)
/*     */   {
/* 234 */     this.m_assetEntityManager = assetEntityManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.RelateSelectedAssetsAction
 * JD-Core Version:    0.6.0
 */