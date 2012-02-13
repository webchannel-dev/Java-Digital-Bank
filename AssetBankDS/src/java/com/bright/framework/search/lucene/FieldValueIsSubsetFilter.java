/*     */ package com.bright.framework.search.lucene;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.lucene.document.Document;
/*     */ import org.apache.lucene.document.FieldSelector;
/*     */ import org.apache.lucene.document.SetBasedFieldSelector;
/*     */ import org.apache.lucene.index.IndexReader;
/*     */ import org.apache.lucene.index.Term;
/*     */ import org.apache.lucene.index.TermDocs;
/*     */ import org.apache.lucene.search.DocIdSet;
/*     */ import org.apache.lucene.search.Filter;
/*     */ import org.apache.lucene.util.OpenBitSet;
/*     */ 
/*     */ public class FieldValueIsSubsetFilter extends Filter
/*     */ {
/*  49 */   private Log m_logger = GlobalApplication.getInstance().getLogger();
/*  50 */   private long[] m_alValuesToCheck = null;
/*  51 */   private Term[] m_aTermsToCheck = null;
/*  52 */   private String m_sFieldName = null;
/*     */   private char m_cFieldValueDelimiter;
/*     */ 
/*     */   public FieldValueIsSubsetFilter(String a_sFieldName, char a_sFieldValueDelimiter, Set<Long> a_toCheck)
/*     */   {
/*  66 */     this.m_sFieldName = a_sFieldName;
/*  67 */     this.m_cFieldValueDelimiter = a_sFieldValueDelimiter;
/*     */ 
/*  69 */     if (a_toCheck != null)
/*     */     {
/*  72 */       this.m_aTermsToCheck = new Term[a_toCheck.size()];
/*  73 */       this.m_alValuesToCheck = new long[a_toCheck.size()];
/*  74 */       int i = 0;
/*  75 */       for (Long lVal : a_toCheck)
/*     */       {
/*  77 */         this.m_alValuesToCheck[i] = lVal.longValue();
/*  78 */         this.m_aTermsToCheck[(i++)] = new Term(this.m_sFieldName, String.valueOf(lVal));
/*     */       }
/*     */ 
/*  82 */       Arrays.sort(this.m_alValuesToCheck);
/*     */     }
/*     */   }
/*     */ 
/*     */   public DocIdSet getDocIdSet(IndexReader reader)
/*     */     throws IOException
/*     */   {
/*  89 */     long lTime = System.currentTimeMillis();
/*     */ 
/*  92 */     OpenBitSet result = new OpenBitSet(reader.maxDoc());
/*     */ 
/*  95 */     if ((this.m_aTermsToCheck == null) || (this.m_aTermsToCheck.length == 0))
/*     */     {
/*  97 */       return result;
/*     */     }
/*     */ 
/* 101 */     OpenBitSet checked = new OpenBitSet(reader.maxDoc());
/*     */ 
/* 104 */     Set fieldToLoad = new HashSet(1);
/* 105 */     fieldToLoad.add(this.m_sFieldName);
/* 106 */     FieldSelector selector = new SetBasedFieldSelector(fieldToLoad, Collections.EMPTY_SET);
/*     */ 
/* 108 */     TermDocs td = reader.termDocs();
/*     */     try
/*     */     {
/* 113 */       for (int i = 0; i < this.m_aTermsToCheck.length; i++)
/*     */       {
/* 116 */         td.seek(this.m_aTermsToCheck[i]);
/*     */ 
/* 120 */         while (td.next())
/*     */         {
/* 123 */           if (checked.get(td.doc()))
/*     */           {
/*     */             continue;
/*     */           }
/*     */ 
/* 129 */           Document doc = reader.document(td.doc(), selector);
/* 130 */           String sIds = doc.get(this.m_sFieldName);
/*     */ 
/* 132 */           if (StringUtils.isNotEmpty(sIds))
/*     */           {
/* 134 */             long[] alIdsInDoc = StringUtil.getAsArrayOfLongs(sIds, this.m_cFieldValueDelimiter);
/*     */ 
/* 136 */             int iIdInDocIndex = 0;
/* 137 */             int iIdToCheckIndex = 0;
/*     */ 
/* 139 */             if (alIdsInDoc.length <= this.m_alValuesToCheck.length)
/*     */             {
/* 142 */               while ((iIdInDocIndex < alIdsInDoc.length) && (iIdToCheckIndex < this.m_alValuesToCheck.length))
/*     */               {
/* 146 */                 if (alIdsInDoc[iIdInDocIndex] < this.m_alValuesToCheck[iIdToCheckIndex])
/*     */                 {
/*     */                   break;
/*     */                 }
/*     */ 
/* 152 */                 if (alIdsInDoc[iIdInDocIndex] > this.m_alValuesToCheck[iIdToCheckIndex])
/*     */                 {
/* 154 */                   iIdToCheckIndex++; continue;
/*     */                 }
/*     */ 
/* 157 */                 if (alIdsInDoc[iIdInDocIndex] != this.m_alValuesToCheck[iIdToCheckIndex])
/*     */                   continue;
/* 159 */                 iIdInDocIndex++;
/* 160 */                 iIdToCheckIndex++;
/*     */               }
/*     */ 
/* 165 */               if (iIdInDocIndex >= alIdsInDoc.length)
/*     */               {
/* 167 */                 result.set(td.doc());
/*     */               }
/*     */             }
/*     */           }
/* 171 */           checked.set(td.doc());
/*     */         }
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 177 */       td.close();
/* 178 */       this.m_logger.trace(getClass().getSimpleName() + " : Time in filter for segment = " + (System.currentTimeMillis() - lTime) + "ms");
/*     */     }
/* 180 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.lucene.FieldValueIsSubsetFilter
 * JD-Core Version:    0.6.0
 */