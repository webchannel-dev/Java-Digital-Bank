/*     */ package com.bright.framework.search.lucene;
/*     */ 
/*     */ import org.apache.lucene.analysis.Analyzer;
/*     */ import org.apache.lucene.index.Term;
/*     */ import org.apache.lucene.queryParser.QueryParser;
/*     */ import org.apache.lucene.search.NumericRangeQuery;
/*     */ import org.apache.lucene.search.Query;
/*     */ import org.apache.lucene.search.TermQuery;
/*     */ import org.apache.lucene.util.NumericUtils;
/*     */ import org.apache.lucene.util.Version;
/*     */ 
/*     */ public class NumericRangeQueryParser extends QueryParser
/*     */ {
/*     */   public NumericRangeQueryParser(Version matchVersion, String f, Analyzer a)
/*     */   {
/*  31 */     super(matchVersion, f, a);
/*     */   }
/*     */ 
/*     */   protected Query newRangeQuery(String a_sField, String a_sLower, String a_sUpper, boolean a_bInclusive)
/*     */   {
/*  44 */     if ((a_sField.startsWith("f_long_")) || (a_sField.startsWith("f_dbl_")))
/*     */     {
/*     */       try
/*     */       {
/*  50 */         double dLower = Double.parseDouble(a_sLower);
/*  51 */         double dUpper = Double.parseDouble(a_sUpper);
/*     */ 
/*  53 */         if (a_sField.startsWith("f_long_"))
/*     */         {
/*  55 */           dLower = Math.ceil(dLower);
/*  56 */           dUpper = Math.floor(dUpper);
/*     */ 
/*  58 */           return NumericRangeQuery.newLongRange(a_sField, Long.valueOf((long)dLower), Long.valueOf((long)dUpper), a_bInclusive, a_bInclusive);
/*     */         }
/*     */ 
/*  62 */         return NumericRangeQuery.newDoubleRange(a_sField, Double.valueOf(dLower), Double.valueOf(dUpper), a_bInclusive, a_bInclusive);
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/*  67 */         return null;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  72 */     return super.newRangeQuery(a_sField, a_sLower, a_sUpper, a_bInclusive);
/*     */   }
/*     */ 
/*     */   protected Query newTermQuery(Term a_term)
/*     */   {
/*  86 */     if (a_term.field().startsWith("f_long_"))
/*     */     {
/*     */       try
/*     */       {
/*  90 */         double dVal = Double.parseDouble(a_term.text());
/*     */ 
/*  93 */         if (dVal % 1.0D > 0.0D)
/*     */         {
/*  95 */           throw new NumberFormatException();
/*     */         }
/*     */ 
/*  98 */         return new TermQuery(new Term(a_term.field(), NumericUtils.longToPrefixCoded((long)dVal)));
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/* 102 */         return new TermQuery(new Term(a_term.field(), ""));
/*     */       }
/*     */     }
/* 105 */     if (a_term.field().startsWith("f_dbl_"))
/*     */     {
/*     */       try
/*     */       {
/* 109 */         return new TermQuery(new Term(a_term.field(), NumericUtils.doubleToPrefixCoded(Double.parseDouble(a_term.text()))));
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/* 113 */         return new TermQuery(new Term(a_term.field(), ""));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 118 */     return super.newTermQuery(a_term);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.lucene.NumericRangeQueryParser
 * JD-Core Version:    0.6.0
 */