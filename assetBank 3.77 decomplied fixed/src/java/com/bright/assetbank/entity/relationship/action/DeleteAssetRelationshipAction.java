/*     */ package com.bright.assetbank.entity.relationship.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.synchronise.util.SynchUtil;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class DeleteAssetRelationshipAction extends BTransactionAction
/*     */   implements FrameworkConstants, AssetBankConstants
/*     */ {
/*  55 */   private IAssetManager m_assetManager = null;
/*  56 */   private AssetRelationshipManager m_assetRelationshipManager = null;
/*  57 */   private MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  74 */     ActionForward afForward = null;
/*     */ 
/*  77 */     long lParentId = getLongParameter(a_request, "parentId");
/*  78 */     long lChildId = getLongParameter(a_request, "childId");
/*  79 */     long lRelationshipTypeId = getLongParameter(a_request, "relationshipTypeId");
/*     */ 
/*  82 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  83 */     Asset parentAsset = this.m_assetManager.getAsset(a_dbTransaction, lParentId, null, false, false);
/*     */ 
/*  86 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  89 */       if (!this.m_assetManager.userCanUpdateAsset(userProfile, parentAsset))
/*     */       {
/*  91 */         this.m_logger.debug("This user does not have permission to view the admin page");
/*  92 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */     }
/*     */ 
/*  96 */     Vector affectedIds = null;
/*     */ 
/*  99 */     if (lRelationshipTypeId == 3L)
/*     */     {
/* 101 */       affectedIds = this.m_assetRelationshipManager.deleteAssetRelationship(a_dbTransaction, lChildId, lParentId, 2L);
/*     */     }
/*     */     else
/*     */     {
/* 105 */       affectedIds = this.m_assetRelationshipManager.deleteAssetRelationship(a_dbTransaction, lParentId, lChildId, lRelationshipTypeId);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 111 */       a_dbTransaction.commit();
/* 112 */       a_dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 116 */       throw new Bn2Exception(sqle.getMessage(), sqle);
/*     */     }
/*     */ 
/* 119 */     Vector assetsToIndex = new Vector();
/*     */ 
/* 122 */     if (lRelationshipTypeId != 3L)
/*     */     {
/* 124 */       assetsToIndex.add(this.m_assetManager.getAsset(a_dbTransaction, lParentId, null, true, false));
/*     */     }
/*     */ 
/* 128 */     for (Iterator i$ = affectedIds.iterator(); i$.hasNext(); ) { long lId = ((Long)i$.next()).longValue();
/*     */ 
/* 131 */       assetsToIndex.add(this.m_assetManager.getAsset(a_dbTransaction, lId, null, true, true));
/*     */     }
/*     */ 
/* 135 */     SynchUtil.setLastModifiedDatesForSyncAssets(a_dbTransaction, assetsToIndex, this.m_assetManager);
/*     */     try
/*     */     {
/* 139 */       a_dbTransaction.commit();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 143 */       throw new Bn2Exception(sqle.getMessage());
/*     */     }
/*     */ 
/* 147 */     this.m_searchManager.indexDocuments(null, assetsToIndex, true);
/*     */ 
/* 150 */     Asset asset = new Asset();
/* 151 */     asset.setId(lParentId);
/* 152 */     AssetForm assetForm = (AssetForm)a_form;
/* 153 */     assetForm.setAsset(asset);
/*     */ 
/* 156 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 158 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 164 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetRelationshipManager(AssetRelationshipManager a_assetRelationshipManager)
/*     */   {
/* 169 */     this.m_assetRelationshipManager = a_assetRelationshipManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/* 174 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.action.DeleteAssetRelationshipAction
 * JD-Core Version:    0.6.0
 */