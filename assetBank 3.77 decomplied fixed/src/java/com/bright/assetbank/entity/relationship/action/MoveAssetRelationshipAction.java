/*     */ package com.bright.assetbank.entity.relationship.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class MoveAssetRelationshipAction extends Bn2Action
/*     */   implements AssetBankConstants
/*     */ {
/*     */   private DBTransactionManager m_transactionManager;
/*  47 */   private IAssetManager m_assetManager = null;
/*  48 */   private AssetRelationshipManager m_assetRelationshipManager = null;
/*  49 */   private MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response) throws Bn2Exception {
/*  59 */     long lParentId = getLongParameter(a_request, "id");
/*  60 */     long lChildId = getLongParameter(a_request, "childId");
/*     */ 
/*  63 */     DBTransaction transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*     */     boolean bReindex;
/*     */     try { bReindex = this.m_assetRelationshipManager.demoteChildInRelationshipSequence(transaction, lParentId, lChildId);
/*     */     }
/*     */     finally
/*     */     {
/*  70 */       transaction.commit2();
/*     */     }
/*     */ 
/*  77 */     if (bReindex)
/*     */     {
/*  79 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*     */       try
/*     */       {
/*  82 */         this.m_searchManager.indexDocument(this.m_assetManager.getAsset(transaction, lParentId, null, true, true), true);
/*     */       }
/*     */       finally
/*     */       {
/*  86 */         transaction.commit2();
/*     */       }
/*     */     }
/*     */ 
/*  90 */     return createRedirectingForward("id=" + lParentId + "#related", a_mapping, "Success");
/*     */   }
/*     */ 
/*     */   public void setDBTransactionManager(DBTransactionManager a_transactionManager)
/*     */   {
/*  95 */     this.m_transactionManager = a_transactionManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 101 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetRelationshipManager(AssetRelationshipManager a_assetRelationshipManager)
/*     */   {
/* 106 */     this.m_assetRelationshipManager = a_assetRelationshipManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager searchManager)
/*     */   {
/* 111 */     this.m_searchManager = searchManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.action.MoveAssetRelationshipAction
 * JD-Core Version:    0.6.0
 */