/*     */ package com.bright.assetbank.search.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.CreateLuceneDocumentFromAssetParameters;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.util.AssetUtil;
/*     */ import com.bright.assetbank.attribute.service.AttributeValueManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.search.bean.IndexableDocument;
/*     */ import com.bright.framework.search.service.AnalyzerFactory;
/*     */ import com.bright.framework.search.service.IndexManager;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collection;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class AssetIndexManager extends IndexManager
/*     */ {
/*  49 */   private DBTransactionManager m_transactionManager = null;
/*  50 */   private AttributeValueManager m_attributeValueManager = null;
/*     */ 
/*     */   public AssetIndexManager(String a_sIndexFilePath, AnalyzerFactory a_analyzerFactory)
/*     */   {
/*  58 */     super(a_sIndexFilePath, "f_description", a_analyzerFactory);
/*     */   }
/*     */ 
/*     */   public void setDBTransactionManager(DBTransactionManager a_transactionManager)
/*     */   {
/*  63 */     this.m_transactionManager = a_transactionManager;
/*     */   }
/*     */ 
/*     */   public DBTransactionManager getDBTransactionManager() throws Bn2Exception
/*     */   {
/*  68 */     if (this.m_transactionManager == null)
/*     */     {
/*     */       try
/*     */       {
/*  72 */         this.m_transactionManager = ((DBTransactionManager)GlobalApplication.getInstance().getComponentManager().lookup("DBTransactionManager"));
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  76 */         throw new Bn2Exception("AssetIndexManager.getTransactionManager: " + e.getMessage(), e);
/*     */       }
/*     */     }
/*     */ 
/*  80 */     return this.m_transactionManager;
/*     */   }
/*     */ 
/*     */   private AttributeValueManager getAttributeValueManager() throws Bn2Exception
/*     */   {
/*  85 */     if (this.m_attributeValueManager == null)
/*     */     {
/*     */       try
/*     */       {
/*  89 */         this.m_attributeValueManager = ((AttributeValueManager)GlobalApplication.getInstance().getComponentManager().lookup("AttributeValueManager"));
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  94 */         throw new Bn2Exception("AssetIndexManager.getAssetManager: " + e.getMessage(), e);
/*     */       }
/*     */     }
/*     */ 
/*  98 */     return this.m_attributeValueManager;
/*     */   }
/*     */ 
/*     */   public synchronized void indexDocuments(Collection<? extends IndexableDocument> a_docs, Object a_createLuceneDocumentParams)
/*     */     throws Bn2Exception
/*     */   {
/* 111 */     DBTransaction transaction = null;
/* 112 */     CreateLuceneDocumentFromAssetParameters createLuceneDocumentParams = (CreateLuceneDocumentFromAssetParameters)a_createLuceneDocumentParams;
/* 113 */     Language a_language = createLuceneDocumentParams.getLanguage();
/*     */ 
/* 115 */     if (AssetBankSettings.getIncludeParentMetadataForSearch())
/*     */     {
/*     */       try
/*     */       {
/* 123 */         transaction = getDBTransactionManager().getCurrentOrNewTransaction();
/*     */ 
/* 125 */         for (IndexableDocument a_vDoc : a_docs)
/*     */         {
/* 127 */           AssetUtil.mergeParentAssetData(transaction, (Asset)a_vDoc, getAttributeValueManager(), " ", false, false, true, a_language);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 132 */         this.m_logger.error("AssetIndexManager.indexDocuments: Error merging parent data: " + e.getMessage());
/*     */ 
/* 135 */         if (transaction != null)
/*     */         {
/*     */           try
/*     */           {
/* 139 */             transaction.rollback();
/*     */           }
/*     */           catch (SQLException e2)
/*     */           {
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */       finally
/*     */       {
/* 149 */         if (transaction != null)
/*     */         {
/*     */           try
/*     */           {
/* 153 */             transaction.commit();
/*     */           }
/*     */           catch (SQLException e2)
/*     */           {
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 163 */     super.indexDocuments(a_docs, a_createLuceneDocumentParams);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.service.AssetIndexManager
 * JD-Core Version:    0.6.0
 */