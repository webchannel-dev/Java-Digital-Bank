/*     */ package com.bright.framework.search.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.lucene.analysis.Analyzer;
/*     */ import org.apache.lucene.index.IndexWriter;
/*     */ import org.apache.lucene.index.IndexWriter.MaxFieldLength;
/*     */ import org.apache.lucene.store.FSDirectory;
/*     */ 
/*     */ public class IndexModifierManager
/*     */ {
/*     */   private static final int k_iCreateModifierRetryAttempts = 5;
/*     */   private static final int k_iCreateModifierRetryPeriodMs = 200;
/*     */   private static IndexModifierManager s_instance;
/*  49 */   private Map m_hmIndexModifiers = null;
/*  50 */   private Map m_hmIndexModifierCount = null;
/*     */ 
/*  52 */   private Log m_logger = GlobalApplication.getInstance().getLogger();
/*     */ 
/*     */   private IndexModifierManager()
/*     */   {
/*  57 */     this.m_hmIndexModifiers = new HashMap();
/*  58 */     this.m_hmIndexModifierCount = new HashMap();
/*     */   }
/*     */ 
/*     */   public static IndexModifierManager getInstance()
/*     */   {
/*  68 */     if (s_instance == null)
/*     */     {
/*  70 */       s_instance = new IndexModifierManager();
/*     */     }
/*  72 */     return s_instance;
/*     */   }
/*     */ 
/*     */   public synchronized IndexWriter getIndexWriter(String a_sIndexFilePath, boolean a_bCreateIndexIfNecessary, Analyzer a_analyzer)
/*     */     throws Bn2Exception
/*     */   {
/*  92 */     int iErrorCount = 0;
/*  93 */     long lRetryPause = 200L;
/*     */ 
/*  95 */     if (!this.m_hmIndexModifiers.containsKey(a_sIndexFilePath))
/*     */     {
/*     */       do
/*     */       {
/*     */         try
/*     */         {
/* 101 */           IndexWriter modifier = new IndexWriter(FSDirectory.open(new File(a_sIndexFilePath)), a_analyzer, a_bCreateIndexIfNecessary, IndexWriter.MaxFieldLength.UNLIMITED);
/* 102 */           modifier.setUseCompoundFile(true);
/*     */ 
/* 106 */           modifier.setMaxFieldLength(2147483647);
/*     */ 
/* 108 */           this.m_hmIndexModifiers.put(a_sIndexFilePath, modifier);
/* 109 */           this.m_hmIndexModifierCount.put(modifier, new Integer(0));
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 113 */           if (iErrorCount++ < 4)
/*     */           {
/* 115 */             this.m_logger.error("IndexModifierManager.getIndexWriter() : Exception whilst instantiating index modifier : trying again after attempt " + iErrorCount);
/*     */             try
/*     */             {
/* 118 */               Thread.sleep(lRetryPause *= 2L);
/*     */             }
/*     */             catch (InterruptedException ie) {
/*     */             }
/*     */           }
/*     */           else {
/* 124 */             this.m_logger.error("IndexModifierManager.getIndexWriter() : Exception whilst instantiating index modifier : giving up after 3 failed attempts " + iErrorCount);
/* 125 */             throw new Bn2Exception("Exception whilst creating index modifier, giving up after 3 failed attempts", e);
/*     */           }
/*     */         }
/*     */       }
/* 128 */       while ((iErrorCount > 0) && (iErrorCount < 5));
/*     */     }
/* 130 */     IndexWriter modifier = (IndexWriter)this.m_hmIndexModifiers.get(a_sIndexFilePath);
/* 131 */     this.m_hmIndexModifierCount.put(modifier, Integer.valueOf(((Integer)this.m_hmIndexModifierCount.get(modifier)).intValue() + 1));
/* 132 */     return modifier;
/*     */   }
/*     */ 
/*     */   public synchronized void releaseIndexModifier(String a_sIndexFilePath, IndexWriter a_indexWriter)
/*     */     throws Bn2Exception
/*     */   {
/* 146 */     Integer numThreads = (Integer)this.m_hmIndexModifierCount.get(a_indexWriter);
/*     */ 
/* 148 */     if ((numThreads != null) && (numThreads.intValue() <= 1))
/*     */     {
/*     */       try
/*     */       {
/* 152 */         a_indexWriter.close();
/* 153 */         this.m_hmIndexModifierCount.remove(a_indexWriter);
/* 154 */         this.m_hmIndexModifiers.remove(a_sIndexFilePath);
/*     */       }
/*     */       catch (IOException ioe)
/*     */       {
/* 158 */         this.m_logger.error("IndexModifierManager.releaseIndexModifier() : IOException whilst closing index writer");
/* 159 */         throw new Bn2Exception("IOException whilst closing index modifier", ioe);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 164 */       this.m_hmIndexModifierCount.put(a_indexWriter, Integer.valueOf(((Integer)this.m_hmIndexModifierCount.get(a_indexWriter)).intValue() - 1));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.service.IndexModifierManager
 * JD-Core Version:    0.6.0
 */