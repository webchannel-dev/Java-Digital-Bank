/*     */ package com.bright.assetbank.synchronise.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.queue.bean.MessageBatchMonitor;
/*     */ import com.bright.framework.util.Counter;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class CategoryNameOrIdHandler
/*     */   implements CategoryHandler
/*     */ {
/*     */   public static final String c_ksClassName = "CategoryNameOrIdHandler";
/*  48 */   private Log m_logger = GlobalApplication.getInstance().getLogger();
/*     */   private DBTransactionManager m_transactionManager;
/*     */   private DBTransaction m_dbTransaction;
/*     */   private CategoryManager m_categoryManager;
/*     */   private MessageBatchMonitor m_batchMonitor;
/*     */   private Counter m_lineCounter;
/*     */   private long m_lUserId;
/*     */   private boolean m_bCheckOnly;
/*     */   private boolean m_bCreateMissingCategories;
/*     */ 
/*     */   private CategoryNameOrIdHandler(DBTransactionManager a_transactionManager, DBTransaction a_dbTransaction, CategoryManager a_categoryManager, MessageBatchMonitor a_batchMonitor, Counter a_lineCounter, long a_lUserId, boolean a_bCheckOnly, boolean a_bCreateMissingCategories)
/*     */   {
/*  61 */     this.m_transactionManager = a_transactionManager;
/*  62 */     this.m_dbTransaction = a_dbTransaction;
/*  63 */     this.m_categoryManager = a_categoryManager;
/*  64 */     this.m_batchMonitor = a_batchMonitor;
/*  65 */     this.m_lineCounter = a_lineCounter;
/*  66 */     this.m_lUserId = a_lUserId;
/*  67 */     this.m_bCheckOnly = a_bCheckOnly;
/*  68 */     this.m_bCreateMissingCategories = a_bCreateMissingCategories;
/*     */ 
/*  71 */     if ((this.m_transactionManager == null) && (this.m_dbTransaction == null))
/*  72 */       throw new NullPointerException();
/*  73 */     if (this.m_categoryManager == null)
/*  74 */       throw new NullPointerException();
/*  75 */     if (this.m_batchMonitor == null)
/*  76 */       throw new NullPointerException();
/*     */   }
/*     */ 
/*     */   public CategoryNameOrIdHandler(DBTransaction a_dbTransaction, CategoryManager a_categoryManager, MessageBatchMonitor a_batchMonitor, Counter a_lineCounter, long a_lUserId, boolean a_bCheckOnly, boolean a_bCreateMissingCategories)
/*     */   {
/*  89 */     this(null, a_dbTransaction, a_categoryManager, a_batchMonitor, a_lineCounter, a_lUserId, a_bCheckOnly, a_bCreateMissingCategories);
/*     */   }
/*     */ 
/*     */   public CategoryNameOrIdHandler(DBTransactionManager a_dbTransactionManager, CategoryManager a_categoryManager, MessageBatchMonitor a_batchMonitor, Counter a_lineCounter, long a_lUserId, boolean a_bCheckOnly, boolean a_bCreateMissingCategories)
/*     */   {
/* 101 */     this(a_dbTransactionManager, null, a_categoryManager, a_batchMonitor, a_lineCounter, a_lUserId, a_bCheckOnly, a_bCreateMissingCategories);
/*     */   }
/*     */ 
/*     */   public Set<Long> getCategoryIds(List<? extends Object> a_categoryNamesOrIds, long a_lCatTreeId)
/*     */     throws Bn2Exception
/*     */   {
/* 113 */     Set categoryIds = new HashSet();
/*     */ 
/* 115 */     DBTransaction dbTransaction = this.m_dbTransaction == null ? this.m_transactionManager.getNewTransaction() : this.m_dbTransaction;
        
/*     */     try
/*     */     {
/* 124 */       for (Iterator i$ = a_categoryNamesOrIds.iterator(); i$.hasNext(); ) { Object obj = i$.next();
/*     */ 
/* 127 */         Category cat = null;
/* 128 */         boolean bValidIdentifier = false;
/*     */ 
/* 130 */         if (((obj instanceof String)) && (StringUtils.isNotEmpty((String)obj)))
/*     */         {
/* 132 */           cat = this.m_categoryManager.getCategory(this.m_dbTransaction, a_lCatTreeId, ((String)obj).trim(), true);
/*     */ 
/* 136 */           bValidIdentifier = true;
/*     */ 
/* 138 */           if ((cat == null) && (!this.m_bCheckOnly) && (this.m_bCreateMissingCategories))
/*     */           {
/* 140 */             cat = this.m_categoryManager.ensureCategoryExists(this.m_dbTransaction, a_lCatTreeId, ((String)obj).trim(), a_lCatTreeId == 2L);
/*     */           }
/*     */         }
/* 143 */         else if (((obj instanceof Long)) && (((Long)obj).longValue() > 0L))
/*     */         {
/* 145 */           cat = this.m_categoryManager.getCategory(null, a_lCatTreeId, ((Long)obj).longValue());
/*     */ 
/* 148 */           bValidIdentifier = true;
/*     */         }
/*     */ 
/* 152 */         if (cat == null)
/*     */         {
/* 154 */           if (bValidIdentifier)
/*     */           {
/* 156 */             if (!this.m_bCreateMissingCategories)
/*     */             {
/* 158 */               this.m_batchMonitor.addMessage(this.m_lUserId, "Line " + getLineNumber() + ": The category '" + obj + "' does not map to a valid category. Check the help page for how to name and delimit categories.");
/*     */             }
/*     */             else
/*     */             {
/* 163 */               this.m_batchMonitor.addMessage(this.m_lUserId, "Line " + getLineNumber() + ": The category '" + obj + "' does not map to an existing category. This category will be created upon running an import.");
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 170 */           categoryIds.add(Long.valueOf(cat.getId()));
/*     */         }
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       Iterator i$;
/* 176 */       if ((this.m_dbTransaction == null) && (dbTransaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 180 */           dbTransaction.commit();
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 184 */           this.m_logger.error("CategoryNameOrIdHandler: Exception committing transaction", e);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 189 */     return categoryIds;
/*     */   }
/*     */ 
/*     */   private String getLineNumber()
/*     */   {
/* 194 */     return this.m_lineCounter == null ? "unknown" : String.valueOf(this.m_lineCounter.getValue());
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.service.CategoryNameOrIdHandler
 * JD-Core Version:    0.6.0
 */