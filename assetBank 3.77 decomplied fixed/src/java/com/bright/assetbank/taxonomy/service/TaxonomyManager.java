/*     */ package com.bright.assetbank.taxonomy.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.taxonomy.bean.Keyword;
/*     */ import com.bright.assetbank.taxonomy.constant.KeywordConstants;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.Category.Translation;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.lang.WordUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class TaxonomyManager extends Bn2Manager
/*     */   implements AssetBankConstants, KeywordConstants
/*     */ {
/*     */   private static final String c_ksClassName = "TaxonomyManager";
/*  78 */   private String m_sDelimiter = " ";
/*     */ 
/*  81 */   private AssetCategoryManager m_categoryManager = null;
/*     */ 
/*  87 */   private DBTransactionManager m_transactionManager = null;
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_sCategoryManager)
/*     */   {
/*  84 */     this.m_categoryManager = a_sCategoryManager;
/*     */   }
/*     */ 
/*     */   public void setDBTransactionManager(DBTransactionManager a_transactionManager)
/*     */   {
/*  90 */     this.m_transactionManager = a_transactionManager;
/*     */   }
/*     */ 
/*     */   public void startup() throws Bn2Exception
/*     */   {
/*  95 */     super.startup();
/*     */ 
/*  98 */     this.m_sDelimiter = AssetBankSettings.getKeywordDelimiter();
/*     */   }
/*     */ 
/*     */   public Vector<Keyword> getKeywordsForFilter(DBTransaction a_dbTransaction, String a_sStartsWith, long a_lTreeId, boolean a_bBrowsableOnly, boolean a_bIncludeExpired, final Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/* 124 */     String ksMethodName = "getKeywordsForFilter";
/*     */ 
/* 126 */     Connection con = null;
/* 127 */     PreparedStatement psql = null;
/* 128 */     String sSQL = null;
/* 129 */     Vector vecResults = new Vector();
/*     */     try
/*     */     {
/* 133 */       con = a_dbTransaction.getConnection();
/* 134 */       ApplicationSql sqlGenerator = SQLGenerator.getInstance();
/*     */ 
/* 136 */       sSQL = "SELECT c.Id cId, c.Name cName, c.Description cDescription,c.IsExpired cIsExpired,tc.Name tcName, tc.Description tcDescription, l.Id lId, l.Code lCode, l.Name lName " + (!AssetBankSettings.getKeywordAnscestorsSelectable() ? ", COUNT(childCat.Id) numChildren " : "") + "FROM " + "CM_Category c " + "LEFT JOIN TranslatedCategory tc ON tc.CategoryId=c.Id " + "LEFT JOIN Language l ON l.Id=tc.LanguageId AND l.Id=? " + (!AssetBankSettings.getKeywordAnscestorsSelectable() ? "LEFT JOIN CM_Category childCat ON childCat.ParentId=c.Id " : "") + "WHERE " + "c.CategoryTypeId=? ";
/*     */ 
/* 155 */       if (StringUtil.stringIsPopulated(a_sStartsWith))
/*     */       {
/* 157 */         if ((a_language == null) || (a_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */         {
/* 160 */           sSQL = sSQL + "AND UPPER(" + sqlGenerator.getTrimFunction("c.Name") + ") LIKE UPPER(" + sqlGenerator.getTrimFunction("?") + ") ";
/*     */         }
/*     */         else
/*     */         {
/* 164 */           sSQL = sSQL + "AND ((" + sqlGenerator.getNullCheckStatement("tc.Name") + " AND UPPER(" + sqlGenerator.getTrimFunction("c.Name") + ") LIKE UPPER(" + sqlGenerator.getTrimFunction("?") + ")) ";
/* 165 */           sSQL = sSQL + "OR (UPPER(" + sqlGenerator.getTrimFunction("tc.Name") + ") LIKE UPPER(" + sqlGenerator.getTrimFunction("?") + "))) ";
/*     */         }
/*     */       }
/*     */ 
/* 169 */       if (a_bBrowsableOnly)
/*     */       {
/* 171 */         sSQL = sSQL + "AND c.IsBrowsable = 1 ";
/*     */       }
/*     */ 
/* 174 */       if (!a_bIncludeExpired)
/*     */       {
/* 176 */         sSQL = sSQL + "AND c.IsExpired = ? ";
/*     */       }
/*     */ 
/* 179 */       if (!AssetBankSettings.getKeywordAnscestorsSelectable())
/*     */       {
/* 181 */         sSQL = sSQL + "GROUP BY c.Id, c.Name, c.Description, c.IsExpired, tc.Name, tc.Description, l.Id, l.Code, l.Name ";
/*     */       }
/*     */ 
/* 184 */       sSQL = sSQL + "ORDER BY c.Name";
/*     */ 
/* 187 */       psql = con.prepareStatement(sSQL);
/*     */ 
/* 189 */       int iCol = 1;
/*     */ 
/* 191 */       psql.setLong(iCol++, a_language.getId());
/* 192 */       psql.setLong(iCol++, a_lTreeId);
/*     */ 
/* 194 */       if (StringUtil.stringIsPopulated(a_sStartsWith))
/*     */       {
/* 196 */         String sSearchTerm = a_sStartsWith + "%";
/* 197 */         psql.setString(iCol++, sSearchTerm);
/*     */ 
/* 199 */         if ((a_language != null) && (!a_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */         {
/* 201 */           psql.setString(iCol++, sSearchTerm);
/*     */         }
/*     */       }
/*     */ 
/* 205 */       if (!a_bIncludeExpired)
/*     */       {
/* 207 */         psql.setBoolean(iCol++, false);
/*     */       }
/*     */ 
/* 210 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 212 */       Keyword keyword = null;
/* 213 */       long lLastKeywordId = 0L;
/* 214 */       boolean bTranslatedValuesExist = false;
/*     */ 
/* 216 */       while (rs.next())
/*     */       {
/* 218 */         if ((!AssetBankSettings.getKeywordAnscestorsSelectable()) && (rs.getInt("numChildren") != 0))
/*     */           continue;
/* 220 */         if (rs.getLong("cId") != lLastKeywordId)
/*     */         {
/* 222 */           keyword = new Keyword();
/* 223 */           keyword.setId(rs.getLong("cId"));
/* 224 */           keyword.setName(rs.getString("cName"));
/* 225 */           keyword.setExpired(rs.getBoolean("cIsExpired"));
/* 226 */           String sSynonyms = topAndTailDelimiters(rs.getString("cDescription"));
/* 227 */           keyword.setDescription(sSynonyms);
/*     */ 
/* 229 */           vecResults.add(keyword);
/*     */ 
/* 231 */           lLastKeywordId = keyword.getId();
/*     */         }
/*     */ 
/* 234 */         if ((rs.getLong("lId") <= 0L) || (rs.getLong("lId") == 1L))
/*     */           continue;
/* 236 */         Category.Translation translation = keyword.createTranslation(new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
/* 237 */         translation.setName(rs.getString("tcName"));
/* 238 */         translation.setDescription(topAndTailDelimiters(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "tcDescription")));
/* 239 */         keyword.getTranslations().add(translation);
/* 240 */         bTranslatedValuesExist = true;
/*     */       }
/*     */ 
/* 245 */       psql.close();
/*     */ 
/* 249 */       if ((!a_language.isDefault()) && (bTranslatedValuesExist))
/*     */       {
/* 251 */         Collections.sort(vecResults, new Comparator()
/*     */         {
/*     */           public int compare(Object a_keyword1, Object a_keyword2)
/*     */           {
/* 255 */             String sName1 =( (Keyword)a_keyword1).getName(a_language);
/* 256 */             String sName2 = ((Keyword)a_keyword2).getName(a_language);
/*     */ 
/* 258 */             return sName1.compareToIgnoreCase(sName2);
/*     */           }
/*     */         });
/*     */       }
/*     */     }
/*     */     catch (SQLException sqe) {
/* 265 */       throw new Bn2Exception("TaxonomyManager.getKeywordsForFilter", sqe);
/*     */     }
/*     */ 
/* 268 */     return vecResults;
/*     */   }
/*     */ 
/*     */   public Keyword getKeyword(DBTransaction a_dbTransaction, String a_sValue, long a_lTreeId, Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/* 290 */     String ksMethodName = "getKeyword";
/*     */ 
/* 292 */     Keyword keyword = null;
/* 293 */     Connection con = null;
/* 294 */     PreparedStatement psql = null;
/* 295 */     String sSQL = null;
/*     */     try
/*     */     {
/* 299 */       con = a_dbTransaction.getConnection();
/* 300 */       ApplicationSql sqlGenerator = SQLGenerator.getInstance();
/*     */ 
/* 302 */       sSQL = "SELECT c.Id cId, c.Name cName, c.Description cDescription, tc.Name tcName, tc.Description tcDescription, l.Id lId, l.Code lCode, l.Name lName FROM CM_Category c LEFT JOIN TranslatedCategory tc ON tc.CategoryId=c.Id LEFT JOIN Language l ON l.Id=tc.LanguageId WHERE CategoryTypeId=? AND ((UPPER(" + sqlGenerator.getTrimFunction("?") + ") = ";
/*     */ 
/* 319 */       if ((a_language == null) || (LanguageConstants.k_defaultLanguage.equals(a_language)))
/*     */       {
/* 321 */         sSQL = sSQL + "UPPER(" + sqlGenerator.getTrimFunction("c.Name") + "))) ";
/*     */       }
/*     */       else
/*     */       {
/* 325 */         sSQL = sSQL + "UPPER(" + sqlGenerator.getTrimFunction("tc.Name") + ") AND l.Id=? ) OR UPPER(" + sqlGenerator.getTrimFunction("?") + ") = UPPER(" + sqlGenerator.getTrimFunction("c.Name") + ")) ";
/*     */       }
/*     */ 
/* 328 */       psql = con.prepareStatement(sSQL);
/*     */ 
/* 330 */       psql.setLong(1, a_lTreeId);
/* 331 */       psql.setString(2, a_sValue);
/*     */ 
/* 333 */       if ((a_language != null) && (!LanguageConstants.k_defaultLanguage.equals(a_language)))
/*     */       {
/* 335 */         psql.setLong(3, a_language.getId());
/* 336 */         psql.setString(4, a_sValue);
/*     */       }
/*     */ 
/* 339 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 341 */       if (rs.next())
/*     */       {
/* 343 */         keyword = new Keyword();
/* 344 */         keyword.setId(rs.getLong("cId"));
/* 345 */         keyword.setName(rs.getString("cName"));
/* 346 */         keyword.setDescription(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "cDescription"));
/* 347 */         keyword.setInMasterList(true);
/*     */ 
/* 349 */         if ((rs.getLong("lId") > 0L) && (rs.getLong("lId") != 1L))
/*     */         {
/* 351 */           Category.Translation translation = keyword.createTranslation(new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
/* 352 */           translation.setName(rs.getString("tcName"));
/* 353 */           translation.setDescription(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "tcDescription"));
/* 354 */           keyword.getTranslations().add(translation);
/*     */         }
/*     */       }
/*     */ 
/* 358 */       psql.close();
/*     */     }
/*     */     catch (SQLException sqe)
/*     */     {
/* 363 */       this.m_logger.error("TaxonomyManager.getKeyword - " + sqe);
/* 364 */       throw new Bn2Exception("TaxonomyManager.getKeyword", sqe);
/*     */     }
/*     */ 
/* 367 */     return keyword;
/*     */   }
/*     */ 
/*     */   public Vector getKeywordStatusList(DBTransaction a_dbTransaction, String a_sKeywords, long a_lTreeId, Language a_language, boolean a_bIncludeAncestors)
/*     */     throws Bn2Exception
/*     */   {
/* 394 */     Vector vecKeywords = new Vector();
/*     */ 
/* 399 */     Vector vWords = tokenizeKeywords(a_sKeywords);
/* 400 */     Iterator it = vWords.iterator();
/* 401 */     while (it.hasNext())
/*     */     {
/* 403 */       String sKeyword = (String)it.next();
/*     */ 
/* 405 */       String[] aAncestors = sKeyword.split(AssetBankSettings.getKeywordAnscestorDelimiter());
/*     */ 
/* 407 */       Vector vAncestors = new Vector();
/*     */ 
/* 409 */       for (int i = a_bIncludeAncestors ? 0 : aAncestors.length - 1; i < aAncestors.length; i++)
/*     */       {
/* 411 */         sKeyword = aAncestors[i];
/*     */ 
/* 413 */         Keyword keyword = getKeyword(a_dbTransaction, sKeyword, a_lTreeId, a_language);
/*     */ 
/* 415 */         if (keyword == null)
/*     */         {
/* 417 */           keyword = new Keyword();
/* 418 */           keyword.setName(sKeyword);
/* 419 */           keyword.setInMasterList(false);
/* 420 */           keyword.setIsBrowsable(true);
/*     */ 
/* 423 */           Vector vecSynonymOfKeywords = getKeywordsForSynonym(a_dbTransaction, sKeyword, a_lTreeId);
/* 424 */           keyword.setKeywordsForThisSynonym(vecSynonymOfKeywords);
/*     */         }
/*     */ 
/* 427 */         if (i < aAncestors.length - 1)
/*     */         {
/* 429 */           vAncestors.add(keyword);
/*     */         }
/*     */         else
/*     */         {
/* 433 */           keyword.setAncestors(vAncestors);
/* 434 */           vecKeywords.add(keyword);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 439 */     return vecKeywords;
/*     */   }
/*     */ 
/*     */   public void processSynonymsField(Category a_category)
/*     */   {
/* 455 */     String sExistingSynonyms = a_category.getDescription();
/*     */ 
/* 457 */     if (StringUtil.stringIsPopulated(sExistingSynonyms))
/*     */     {
/* 459 */       a_category.setDescription(getProcessedSynonyms(sExistingSynonyms));
/*     */ 
/* 461 */       Iterator itTranslations = a_category.getTranslations().iterator();
/* 462 */       while (itTranslations.hasNext())
/*     */       {
/* 464 */         Category.Translation translation = (Category.Translation)itTranslations.next();
/* 465 */         translation.setDescription(getProcessedSynonyms(translation.getDescription()));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private String getProcessedSynonyms(String a_sSynonyms)
/*     */   {
/* 478 */     String sDelim = AssetBankSettings.getKeywordDelimiter();
/*     */ 
/* 480 */     if (StringUtil.stringIsPopulated(a_sSynonyms))
/*     */     {
/* 482 */       Vector vWords = tokenizeKeywords(a_sSynonyms);
/* 483 */       Iterator it = vWords.iterator();
/* 484 */       String sSynonyms = "";
/*     */ 
/* 486 */       if (vWords.size() > 0)
/*     */       {
/* 488 */         sSynonyms = sSynonyms + sDelim;
/*     */       }
/*     */ 
/* 491 */       while (it.hasNext())
/*     */       {
/* 493 */         String sWord = (String)it.next();
/* 494 */         sSynonyms = sSynonyms + sWord + sDelim;
/*     */       }
/*     */ 
/* 497 */       return sSynonyms;
/*     */     }
/* 499 */     return a_sSynonyms;
/*     */   }
/*     */ 
/*     */   private Vector tokenizeKeywords(String a_sKeywords)
/*     */   {
/* 517 */     String[] asKeywords = null;
/*     */ 
/* 519 */     asKeywords = a_sKeywords.split(Pattern.quote(this.m_sDelimiter));
/*     */ 
/* 523 */     Vector vKeywords = new Vector();
/* 524 */     HashSet keywords = new HashSet();
/*     */ 
/* 526 */     if (asKeywords != null)
/*     */     {
/* 528 */       for (int i = 0; i < asKeywords.length; i++)
/*     */       {
/* 530 */         String sWord = asKeywords[i].trim();
/*     */ 
/* 533 */         if (!StringUtil.stringIsPopulated(sWord))
/*     */           continue;
/* 535 */         if (keywords.contains(sWord))
/*     */           continue;
/* 537 */         vKeywords.add(sWord);
/* 538 */         keywords.add(sWord);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 543 */     return vKeywords;
/*     */   }
/*     */ 
/*     */   private Vector getKeywordsForSynonym(DBTransaction a_dbTransaction, String a_sWord, long a_lTreeId)
/*     */     throws Bn2Exception
/*     */   {
/* 563 */     String ksMethodName = "getKeywordsForSynonym";
/*     */ 
/* 565 */     Connection con = null;
/* 566 */     PreparedStatement psql = null;
/* 567 */     String sSQL = null;
/* 568 */     Vector vecResults = new Vector();
/*     */     try
/*     */     {
/* 572 */       con = a_dbTransaction.getConnection();
/*     */ 
/* 575 */       sSQL = "SELECT Id, Name, Description FROM CM_Category WHERE CategoryTypeId=? ";
/* 576 */       sSQL = sSQL + "AND (Description LIKE ? OR Description LIKE ? OR Description LIKE ?)";
/* 577 */       sSQL = sSQL + "ORDER BY Name";
/*     */ 
/* 581 */       String sSearchTermLower = "%" + this.m_sDelimiter + a_sWord.toLowerCase() + this.m_sDelimiter + "%";
/* 582 */       String sSearchTermUpper = "%" + this.m_sDelimiter + a_sWord.toUpperCase() + this.m_sDelimiter + "%";
/* 583 */       String sSearchTermCamel = "%" + this.m_sDelimiter + WordUtils.capitalize(a_sWord) + this.m_sDelimiter + "%";
/*     */ 
/* 585 */       psql = con.prepareStatement(sSQL);
/* 586 */       psql.setLong(1, a_lTreeId);
/* 587 */       psql.setString(2, sSearchTermLower);
/* 588 */       psql.setString(3, sSearchTermUpper);
/* 589 */       psql.setString(4, sSearchTermCamel);
/* 590 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 592 */       Keyword keyword = null;
/*     */ 
/* 594 */       while (rs.next())
/*     */       {
/* 596 */         keyword = new Keyword();
/* 597 */         keyword.setId(rs.getLong("Id"));
/* 598 */         keyword.setName(rs.getString("Name"));
/* 599 */         keyword.setDescription(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "Description"));
/* 600 */         keyword.setInMasterList(true);
/* 601 */         vecResults.add(keyword);
/*     */       }
/*     */ 
/* 604 */       psql.close();
/*     */     }
/*     */     catch (SQLException sqe)
/*     */     {
/* 609 */       throw new Bn2Exception("TaxonomyManager.getKeywordsForSynonym", sqe);
/*     */     }
/*     */ 
/* 612 */     return vecResults;
/*     */   }
/*     */ 
/*     */   public String getAttributeNameForKeywordTree(DBTransaction a_dbTransaction, String a_sTreeId)
/*     */     throws Bn2Exception
/*     */   {
/* 628 */     String ksMethodName = "getAttributeNameForKeywordTree";
/*     */ 
/* 630 */     Connection con = null;
/* 631 */     PreparedStatement psql = null;
/* 632 */     String sSQL = null;
/* 633 */     String sName = null;
/*     */     try
/*     */     {
/* 637 */       long lTreeId = Long.parseLong(a_sTreeId);
/* 638 */       con = a_dbTransaction.getConnection();
/*     */ 
/* 641 */       sSQL = "SELECT Label FROM Attribute WHERE TreeId=?";
/*     */ 
/* 643 */       psql = con.prepareStatement(sSQL);
/* 644 */       psql.setLong(1, lTreeId);
/* 645 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 647 */       if (rs.next())
/*     */       {
/* 649 */         sName = rs.getString("Label");
/*     */       }
/*     */ 
/* 652 */       psql.close();
/*     */     }
/*     */     catch (Exception sqe)
/*     */     {
/* 657 */       throw new Bn2Exception("TaxonomyManager.getAttributeNameForKeywordTree", sqe);
/*     */     }
/*     */ 
/* 660 */     return sName;
/*     */   }
/*     */ 
/*     */   public void addKeywordsToAsset(DBTransaction a_dbTransaction, long a_lAssetId, Vector a_vecKeywords)
/*     */     throws Bn2Exception
/*     */   {
/* 681 */     if (a_vecKeywords != null)
/*     */     {
/* 684 */       for (int i = 0; i < a_vecKeywords.size(); i++)
/*     */       {
/* 686 */         Keyword catKeyword = (Keyword)a_vecKeywords.get(i);
/*     */ 
/* 689 */         if ((!catKeyword.getInMasterList()) || (catKeyword.getId() <= 0L))
/*     */           continue;
/* 691 */         this.m_categoryManager.addItemToCategory(a_dbTransaction, a_lAssetId, catKeyword.getId(), catKeyword.getCategoryTypeId());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateKeywordsForAsset(DBTransaction a_dbTransaction, long a_lAssetId, Vector a_vecAttributeValues)
/*     */     throws Bn2Exception
/*     */   {
/* 703 */     for (int i = 0; i < a_vecAttributeValues.size(); i++)
/*     */     {
/* 705 */       AttributeValue attVal = (AttributeValue)a_vecAttributeValues.elementAt(i);
/*     */ 
/* 707 */       if (!attVal.getAttribute().getIsKeywordPicker()) {
/*     */         continue;
/*     */       }
/* 710 */       this.m_categoryManager.deleteItemFromCategories(a_dbTransaction, attVal.getAttribute().getTreeId(), a_lAssetId);
/*     */ 
/* 712 */       checkKeywordAutoAdd(a_dbTransaction, attVal.getKeywordCategories(), attVal.getAttribute().getTreeId(), AssetBankSettings.getKeywordAutoAdd());
/*     */ 
/* 715 */       addKeywordsToAsset(a_dbTransaction, a_lAssetId, attVal.getKeywordCategories());
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean newKeyword(DBTransaction a_dbTransaction, Category a_newCategory, long a_lParentCatId, long a_lTreeId)
/*     */     throws Bn2Exception
/*     */   {
/* 738 */     processSynonymsField(a_newCategory);
/*     */ 
/* 741 */     boolean bSuccess = this.m_categoryManager.newCategory(a_dbTransaction, a_lTreeId, a_newCategory, a_lParentCatId);
/* 742 */     return bSuccess;
/*     */   }
/*     */ 
/*     */   public boolean updateKeyword(DBTransaction a_dbTransaction, Category a_category, long a_lTreeId)
/*     */     throws Bn2Exception
/*     */   {
/* 761 */     processSynonymsField(a_category);
/*     */ 
/* 766 */     boolean bSuccess = this.m_categoryManager.updateCategoryDetails(a_dbTransaction, a_lTreeId, a_category);
/* 767 */     return bSuccess;
/*     */   }
/*     */ 
/*     */   public void deleteKeyword(DBTransaction a_dbTransaction, long a_lCategoryId, long a_lTreeId, long a_lUserId)
/*     */     throws Bn2Exception
/*     */   {
/* 785 */     this.m_categoryManager.deleteCategory(a_dbTransaction, a_lTreeId, a_lCategoryId, a_lUserId);
/*     */   }
/*     */ 
/*     */   public static String topAndTailDelimiters(String a_sWords)
/*     */   {
/* 803 */     String sDelim = AssetBankSettings.getKeywordDelimiter();
/*     */ 
/* 805 */     if (StringUtil.stringIsPopulated(a_sWords))
/*     */     {
/* 807 */       if (a_sWords.startsWith(sDelim))
/*     */       {
/* 809 */         a_sWords = a_sWords.substring(1, a_sWords.length() - 1);
/*     */       }
/* 811 */       if (a_sWords.endsWith(sDelim))
/*     */       {
/* 813 */         a_sWords = a_sWords.substring(0, a_sWords.length() - 2);
/*     */       }
/*     */     }
/*     */ 
/* 817 */     return a_sWords;
/*     */   }
/*     */ 
/*     */   public List<Keyword> checkKeywordAutoAdd(DBTransaction a_dbTransaction, Vector a_vecKeywords, long a_lTreeId, boolean a_bAddMissingKeywords)
/*     */     throws Bn2Exception
/*     */   {
/* 840 */     List ret = new ArrayList();
/*     */ 
/* 843 */     if (a_vecKeywords != null)
/*     */     {
/* 846 */       Iterator it = a_vecKeywords.iterator();
/* 847 */       while (it.hasNext())
/*     */       {
/* 849 */         Keyword keyword = (Keyword)it.next();
/* 850 */         long lParentId = -1L;
/*     */ 
/* 853 */         if (keyword.getAncestors() != null)
/*     */         {
/* 855 */           Vector vAncestors = keyword.getAncestors();
/*     */ 
/* 857 */           for (int i = 0; i < vAncestors.size(); i++)
/*     */           {
/* 859 */             Keyword ancestor = (Keyword)vAncestors.get(i);
/*     */ 
/* 861 */             if (!ancestor.getInMasterList())
/*     */             {
/* 863 */               ret.add(ancestor);
/* 864 */               if (a_bAddMissingKeywords) {
/* 865 */                 newKeyword(a_dbTransaction, ancestor, lParentId, a_lTreeId);
/*     */               }
/*     */             }
/*     */ 
/* 869 */             lParentId = ancestor.getId();
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 874 */         if (!keyword.getInMasterList())
/*     */         {
/* 876 */           ret.add(keyword);
/* 877 */           if (a_bAddMissingKeywords)
/*     */           {
/* 879 */             newKeyword(a_dbTransaction, keyword, lParentId, a_lTreeId);
/*     */ 
/* 882 */             keyword.setInMasterList(true);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 888 */     return ret;
/*     */   }
/*     */ 
/*     */   public Vector getKeywordsForAttributeValue(DBTransaction a_dbTransaction, String a_sValue, Attribute a_attribute, Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/* 908 */     String ksMethodName = "getKeywordsForAttributeValue";
/*     */ 
/* 911 */     DBTransaction dbTransaction = a_dbTransaction;
/* 912 */     Vector vecKeywords = null;
/*     */     try
/*     */     {
/* 916 */       if (dbTransaction == null)
/*     */       {
/* 918 */         dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */       }
/*     */ 
/* 921 */       if (StringUtil.stringIsPopulated(a_sValue))
/*     */       {
/* 923 */         vecKeywords = getKeywordStatusList(dbTransaction, a_sValue, a_attribute.getTreeId(), a_language, true);
/*     */ 
/* 926 */         checkKeywordAutoAdd(dbTransaction, vecKeywords, a_attribute.getTreeId(), AssetBankSettings.getKeywordAutoAdd());
/*     */       }
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/*     */       throw new Bn2Exception("TaxonomyManager.getKeywordsForAttributeValue : Exception getting keywords for " + a_sValue, e);
/*     */     }
/*     */     finally
/*     */     {
/* 946 */       if ((dbTransaction != null) && (a_dbTransaction == null))
/*     */       {
/*     */         try
/*     */         {
/* 950 */           dbTransaction.commit();
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 954 */           this.m_logger.error("TaxonomyManager.getKeywordsForAttributeValue : Exception whilst trying to close connection " + e.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 959 */     return vecKeywords;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.taxonomy.service.TaxonomyManager
 * JD-Core Version:    0.6.0
 */