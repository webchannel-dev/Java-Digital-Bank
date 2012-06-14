/*     */ package com.bright.assetbank.search.bean;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.search.constant.AssetBankSearchConstants;
/*     */ import com.bright.assetbank.search.lucene.CategoryApprovalFilter;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.search.constant.SearchConstants;
/*     */ import com.bright.framework.search.service.IndexManager;
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class SearchBuilderQuery extends BaseSearchQuery
/*     */   implements Serializable, AssetBankSearchConstants, SearchConstants
/*     */ {
/*     */   private static final long serialVersionUID = 2355847158541207917L;
/*  49 */   private Vector m_vClauses = null;
/*     */ 
/*     */   public Vector getClauses()
/*     */   {
/*  53 */     return this.m_vClauses;
/*     */   }
/*     */ 
/*     */   public void setClauses(Vector a_vClauses)
/*     */   {
/*  58 */     this.m_vClauses = a_vClauses;
/*     */   }
/*     */ 
/*     */   public String getLuceneQuery()
/*     */   {
/*  66 */     StringBuffer sbQuery = new StringBuffer();
/*  67 */     DateFormat appFormat = AssetBankSettings.getStandardDateFormat();
/*     */ 
/*  69 */     if (this.m_vClauses != null)
/*     */     {
/*  71 */       for (int i = 0; i < this.m_vClauses.size(); i++)
/*     */       {
/*  73 */         SearchBuilderClause clause = (SearchBuilderClause)this.m_vClauses.get(i);
/*     */ 
/*  75 */         String sValue = clause.getValue();
/*  76 */         if (!StringUtils.isNotEmpty(sValue))
/*     */           continue;
/*  78 */         String sStaticAttributeIndexFieldname = getStaticAttributeIndexFieldname(clause.getAttributeId());
/*     */ 
/*  80 */         if ((StringUtils.isNotEmpty(sStaticAttributeIndexFieldname)) && (!clause.isNumeric()) && (!clause.isDate()))
/*     */         {
/*  82 */           addClause(sbQuery, clause, sStaticAttributeIndexFieldname);
/*     */         }
/*     */         else
/*     */         {
/*  87 */           addConjunction(sbQuery, clause);
/*     */ 
/*  90 */           if ((clause.isNumeric()) || (clause.isDate()))
/*     */           {
/*  92 */             String sPrefix = null; String sLower = sValue; String sUpper = sValue;
/*     */ 
/*  95 */             if ((clause.getNumericIsWholeNumber()) || (clause.isDate()))
/*     */             {
/*  97 */               if (clause.isDate())
/*     */               {
/*     */                 try
/*     */                 {
/* 101 */                   sValue = String.valueOf(appFormat.parse(sValue).getTime());
/*     */                 }
/*     */                 catch (ParseException e)
/*     */                 {
/*     */                 }
/*     */ 
/*     */               }
/* 109 */               else if ((clause.getAttributeId() == 14L) || (clause.getAttributeId() == 12L))
/*     */               {
/*     */                 try
/*     */                 {
/* 113 */                   Double dValue = Double.valueOf(Double.parseDouble(sValue));
/*     */ 
/* 116 */                   if (clause.getAttributeId() == 14L)
/*     */                   {
/* 118 */                     sValue = String.valueOf((double)(dValue.doubleValue() * 100.0D));
/*     */                   }
/*     */                   else
/*     */                   {
/* 123 */                     sValue = String.valueOf((double)(dValue.doubleValue() * 1024.0D));
/*     */                   }
/*     */                 }
/*     */                 catch (NumberFormatException e)
/*     */                 {
/*     */                 }
/*     */ 
/*     */               }
/*     */ 
/* 132 */               sPrefix = "f_long_att_";
/*     */ 
/* 134 */               if ((clause.getOperatorId() == 8L) || (clause.getOperatorId() == 6L))
/*     */               {
/* 137 */                 sLower = sValue;
/* 138 */                 sUpper = String.valueOf(9223372036854775807L);
/*     */               }
/* 140 */               else if ((clause.getOperatorId() == 7L) || (clause.getOperatorId() == 5L))
/*     */               {
/* 143 */                 sLower = String.valueOf(-9223372036854775808L);
/* 144 */                 sUpper = sValue;
/*     */               }
/*     */ 
/*     */             }
/*     */             else
/*     */             {
/* 150 */               sPrefix = "f_dbl_att_";
/*     */ 
/* 152 */               if (clause.getOperatorId() == 8L)
/*     */               {
/* 154 */                 sLower = sValue;
/* 155 */                 sUpper = String.valueOf(1.7976931348623157E+308D);
/*     */               }
/* 157 */               else if (clause.getOperatorId() == 7L)
/*     */               {
/* 159 */                 sLower = String.valueOf(-1.797693134862316E+307D);
/* 160 */                 sUpper = sValue;
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/* 165 */             if (StringUtils.isNotEmpty(sStaticAttributeIndexFieldname))
/*     */             {
/* 167 */               sbQuery.append(sStaticAttributeIndexFieldname);
/*     */             }
/*     */             else
/*     */             {
/* 171 */               sbQuery.append(sPrefix).append(clause.getAttributeId());
/*     */             }
/*     */ 
/* 175 */             if (clause.getOperatorId() == 3L)
/*     */             {
/* 177 */               sbQuery.append(":(").append(sValue).append(')');
/*     */             }
/*     */             else
/*     */             {
/* 181 */               sbQuery.append(":[").append(sLower).append(" TO ").append(sUpper).append(']');
/*     */             }
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 187 */             sbQuery.append("f_att_").append(clause.getAttributeId());
/*     */ 
/* 189 */             sValue = mungeAttributeValueForLuceneQuery(sValue, clause.isListAttribute(), clause.getDelimiterIsSpace(), clause.getTokenDelimiterRegex());
/*     */ 
/* 194 */             sbQuery.append(":(").append(sValue).append(')');
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 201 */     Vector vCatIds = new Vector();
/* 202 */     Vector vAttValues = null;
/*     */ 
/* 205 */     vAttValues = addFilterCriteria(vAttValues, vCatIds);
/*     */ 
/* 207 */     String sQuery = sbQuery.toString();
/*     */ 
/* 209 */     if ((vAttValues != null) && (vAttValues.size() > 0))
/*     */     {
/* 211 */       sQuery = addAttributeSearches(sQuery, vAttValues, false);
/*     */     }
/*     */ 
/* 215 */     sQuery = addCategorySearch(sQuery, vCatIds);
/*     */ 
/* 218 */     sQuery = addPreviousVersionSearch(sQuery);
/*     */ 
/* 221 */     sQuery = addAttributeExclusions(sQuery);
/*     */ 
/* 224 */     sQuery = addEntitySearch(sQuery);
/*     */ 
/* 226 */     if (sQuery.length() > 0)
/*     */     {
/* 228 */       sQuery = "(" + sQuery + ") AND ";
/*     */     }
/*     */ 
/* 232 */     sQuery = sQuery + "f_unsubmitted:(0) ";
/*     */ 
/* 234 */     return sQuery;
/*     */   }
/*     */ 
/*     */   private String getStaticAttributeIndexFieldname(long a_lAttributeId)
/*     */   {
/* 239 */     if (a_lAttributeId == 0L)
/*     */     {
/* 241 */       return "f_keywords";
/*     */     }
/* 243 */     if (a_lAttributeId == 10L)
/*     */     {
/* 245 */       return "f_addedBy";
/*     */     }
/* 247 */     if (a_lAttributeId == -5L)
/*     */     {
/* 249 */       return "f_typeid";
/*     */     }
/* 251 */     if (a_lAttributeId == -4L)
/*     */     {
/* 253 */       return "f_addedByUserId";
/*     */     }
/* 255 */     if (a_lAttributeId == 11L)
/*     */     {
/* 257 */       return "f_modifiedBy";
/*     */     }
/* 259 */     if (a_lAttributeId == 400L)
/*     */     {
/* 261 */       return "f_agreementTypeId";
/*     */     }
/* 263 */     if (a_lAttributeId == -1L)
/*     */     {
/* 265 */       return "f_agreement";
/*     */     }
/* 267 */     if (a_lAttributeId == -2L)
/*     */     {
/* 269 */       return "f_approvalStatus";
/*     */     }
/* 271 */     if (a_lAttributeId == -3L)
/*     */     {
/* 273 */       return "f_complete";
/*     */     }
/* 275 */     if (a_lAttributeId == 1L)
/*     */     {
/* 277 */       return "f_filename";
/*     */     }
/* 279 */     if (a_lAttributeId == 13L)
/*     */     {
/* 281 */       return "f_orientation";
/*     */     }
/* 283 */     if (a_lAttributeId == 300L)
/*     */     {
/* 285 */       return "f_sensitive";
/*     */     }
/* 287 */     if (a_lAttributeId == 6L)
/*     */     {
/* 289 */       return "f_filename";
/*     */     }
/* 291 */     if (a_lAttributeId == 8L)
/*     */     {
/* 293 */       return "f_long_added";
/*     */     }
/* 295 */     if (a_lAttributeId == 12L)
/*     */     {
/* 297 */       return "f_long_fileSize";
/*     */     }
/* 299 */     if (a_lAttributeId == 9L)
/*     */     {
/* 301 */       return "f_long_modified";
/*     */     }
/* 303 */     if (a_lAttributeId == 14L)
/*     */     {
/* 305 */       return "f_long_price";
/*     */     }
/* 307 */     if (a_lAttributeId == 600L)
/*     */     {
/* 309 */       return "f_dbl_averageRating";
/*     */     }
/* 311 */     if (a_lAttributeId == 2L)
/*     */     {
/* 313 */       return "f_long_id";
/*     */     }
/*     */ 
/* 316 */     return null;
/*     */   }
/*     */ 
/*     */   private void addClause(StringBuffer a_sbQuery, SearchBuilderClause a_clause, String a_sIndexField)
/*     */   {
/* 321 */     addConjunction(a_sbQuery, a_clause);
/* 322 */     a_sbQuery.append(a_sIndexField + ":(" + a_clause.getValue() + ")");
/*     */   }
/*     */ 
/*     */   private void addConjunction(StringBuffer a_sbQuery, SearchBuilderClause a_clause)
/*     */   {
/* 328 */     if (((a_sbQuery.length() == 0) && (a_clause.getOperatorId() == 2L)) || (a_clause.getOperatorId() == 4L))
/*     */     {
/* 332 */       a_sbQuery.append(IndexManager.allDocsTerm() + " ");
/*     */     }
/*     */ 
/* 335 */     if (a_sbQuery.length() > 0)
/*     */     {
/* 337 */       a_sbQuery.insert(0, "(");
/* 338 */       a_sbQuery.append(") ");
/* 339 */       a_sbQuery.append(a_clause.isOrClause() ? " OR " : " AND ");
/*     */ 
/* 341 */       if ((a_clause.getOperatorId() == 2L) || (a_clause.getOperatorId() == 4L))
/*     */       {
/* 344 */         a_sbQuery.append(" NOT ");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected CategoryApprovalFilter getCategoryApprovalFilter()
/*     */     throws Bn2Exception
/*     */   {
/* 357 */     if (getClauses() != null)
/*     */     {
/* 359 */       Enumeration e = getClauses().elements();
/* 360 */       while (e.hasMoreElements())
/*     */       {
/* 362 */         SearchBuilderClause clause = (SearchBuilderClause)e.nextElement();
/* 363 */         if ((clause != null) && (clause.getValue() != null) && (clause.getAttributeId() == -2L) && (isPartialApprovalSearch(clause)))
/*     */         {
/* 369 */           return buildCategoryApprovalFilter();
/*     */         }
/*     */       }
/*     */     }
/* 373 */     return null;
/*     */   }
/*     */ 
/*     */   private boolean isPartialApprovalSearch(SearchBuilderClause a_clause)
/*     */   {
/* 383 */     return (a_clause.getValue().equals(String.valueOf(2))) || (a_clause.getValue().startsWith(String.valueOf("2 "))) || (a_clause.getValue().endsWith(String.valueOf(" 2"))) || (a_clause.getValue().contains(String.valueOf(" 2 ")));
/*     */   }
/*     */ 
/*     */   public String getQueryDescription()
/*     */   {
/* 393 */     String sDescription = null;
/*     */ 
/* 395 */     if ((this.m_vClauses != null) && (this.m_vClauses.size() > 0))
/*     */     {
/* 397 */       for (int i = 0; i < this.m_vClauses.size(); i++)
/*     */       {
/* 399 */         SearchBuilderClause clause = (SearchBuilderClause)this.m_vClauses.get(i);
/* 400 */         if (clause.getAttributeId() == 0L)
/*     */         {
/* 402 */           sDescription = clause.getValue();
/* 403 */           break;
/*     */         }
/* 405 */         if (sDescription != null)
/*     */           continue;
/* 407 */         sDescription = clause.getValue();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 412 */     return sDescription;
/*     */   }
/*     */ 
/*     */   public void setupPermissions(ABUserProfile a_userProfile)
/*     */   {
/* 418 */     super.setupPermissions(a_userProfile);
/*     */ 
/* 420 */     if (!a_userProfile.getIsAdmin())
/*     */     {
/* 422 */       SearchBuilderClause clause = new SearchBuilderClause();
/* 423 */       clause.setAttributeId(-2L);
/* 424 */       clause.setOperatorId(3L);
/* 425 */       clause.setValue(String.valueOf(2) + " OR " + String.valueOf(3));
/*     */ 
/* 427 */       if (getAdvancedViewing())
/*     */       {
/* 430 */         clause.setValue(clause.getValue() + " OR " + String.valueOf(1));
/*     */       }
/*     */ 
/* 433 */       getClauses().add(clause);
/*     */     }
/*     */ 
/* 436 */     if ((AssetBankSettings.getHideIncompleteAssets()) && (!a_userProfile.getCanUpdateAssets()))
/*     */     {
/* 438 */       SearchBuilderClause clause = new SearchBuilderClause();
/* 439 */       clause.setAttributeId(-3L);
/* 440 */       clause.setOperatorId(3L);
/* 441 */       clause.setValue("1");
/* 442 */       getClauses().add(clause);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 453 */     return StringUtils.isEmpty(getLuceneQuery());
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.bean.SearchBuilderQuery
 * JD-Core Version:    0.6.0
 */