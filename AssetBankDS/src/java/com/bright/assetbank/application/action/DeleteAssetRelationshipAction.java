/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
//import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
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
/*  56 */   private MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  73 */     ActionForward afForward = null;
/*     */ 
/*  76 */     long lParentId = getLongParameter(a_request, "parentId");
/*  77 */     long lChildId = getLongParameter(a_request, "childId");
/*  78 */     long lRelationshipTypeId = getLongParameter(a_request, "relationshipTypeId");
/*     */ 
/*  81 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  82 */     Asset parentAsset = this.m_assetManager.getAsset(a_dbTransaction, lParentId, null, false, false);
/*     */ 
/*  85 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  88 */       if (!this.m_assetManager.userCanUpdateAsset(userProfile, parentAsset))
/*     */       {
/*  90 */         this.m_logger.debug("This user does not have permission to view the admin page");
/*  91 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */     }
/*     */ 
/*  95 */     Vector affectedIds = null;
/*     */ 
/*  98 */     if (lRelationshipTypeId == 3L)
/*     */     {
/* 100 */       affectedIds = this.m_assetManager.deleteAssetRelationship(a_dbTransaction, lChildId, lParentId, 2L); 
                                //DBTransaction a_dbTransaction, long a_lParentAssetId, long a_lChildAssetId, long a_lRelationshipTypeId, boolean a_bIgnoreCategoryCheck)
/*     */     }
/*     */     else
/*     */     {
/* 104 */       affectedIds = this.m_assetManager.deleteAssetRelationship(a_dbTransaction, lParentId, lChildId, lRelationshipTypeId);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 110 */       a_dbTransaction.commit();
/* 111 */       a_dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 115 */       throw new Bn2Exception(sqle.getMessage());
/*     */     }
/*     */ 
/* 118 */     Vector assetsToIndex = new Vector();
/*     */ 
/* 121 */     if (lRelationshipTypeId != 3L)
/*     */     {
/* 123 */       assetsToIndex.add(this.m_assetManager.getAsset(a_dbTransaction, lParentId, null, true, false));
/*     */     }
/*     */ 
/* 127 */     if (affectedIds.size() > 0)
/*     */     {
/* 129 */       Iterator itIds = affectedIds.iterator();
/* 130 */       while (itIds.hasNext())
/*     */       {
/* 132 */         assetsToIndex.add(this.m_assetManager.getAsset(a_dbTransaction, ((Long)itIds.next()).longValue(), null, true, true));
/*     */       }
/*     */     }
/*     */ 
/* 136 */     SynchUtil.setLastModifiedDatesForSyncAssets(a_dbTransaction, assetsToIndex, this.m_assetManager);
/*     */     try
/*     */     {
/* 140 */       a_dbTransaction.commit();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 144 */       throw new Bn2Exception(sqle.getMessage());
/*     */     }
/*     */ 
/* 148 */     this.m_searchManager.indexDocuments(null, assetsToIndex, true);
/*     */ 
/* 151 */     Asset asset = new Asset();
/* 152 */     asset.setId(lParentId);
/* 153 */     AssetForm assetForm = (AssetForm)a_form;
/* 154 */     assetForm.setAsset(asset);
/*     */ 
/* 157 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 159 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 165 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/* 170 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.DeleteAssetRelationshipAction
 * JD-Core Version:    0.6.0
 */