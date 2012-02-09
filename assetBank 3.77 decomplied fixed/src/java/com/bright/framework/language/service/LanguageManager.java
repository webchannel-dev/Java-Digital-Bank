/*     */ package com.bright.framework.language.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.database.AssetBankSql;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.Category.Translation;
/*     */ import com.bright.framework.category.bean.FlatCategoryList;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.exception.SQLStatementException;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.bean.Translatable;
/*     */// import com.bright.framework.language.bean.Translation;
/*     */ import com.bright.framework.language.exception.LanguageCodeNotUniqueException;
/*     */ import com.bright.framework.language.exception.LanguageNameNotUniqueException;
/*     */ import com.bright.framework.language.util.LanguageDBUtil;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class LanguageManager extends Bn2Manager
/*     */ {
/*  61 */   private static final String c_ksClassName = LanguageManager.class.getName();
/*     */ 
/*  63 */   private ListManager m_listManager = null;
/*  64 */   private CategoryManager m_categoryManager = null;
/*  65 */   private DBTransactionManager m_transactionManager = null;
/*  66 */   private List<Language> m_lLanguageCacheAll = null;
/*  67 */   private Map<String, Language> m_languageCacheAllByCode = null;
/*  68 */   private List<Language> m_lLanguageCacheNotSuspendedWithDefault = null;
/*  69 */   private List<Language> m_lLanguageCacheNotSuspendedNoDefault = null;
/*  70 */   private List<Language> m_lLanguageCacheIncludingSuspendedNoDefault = null;
/*  71 */   private Language m_defaultLanguage = null;
/*     */ 
/*     */   protected synchronized void clearCaches()
/*     */   {
/*  78 */     this.m_defaultLanguage = null;
/*  79 */     this.m_lLanguageCacheAll = null;
/*  80 */     this.m_languageCacheAllByCode = null;
/*  81 */     this.m_lLanguageCacheNotSuspendedWithDefault = null;
/*  82 */     this.m_lLanguageCacheNotSuspendedNoDefault = null;
/*  83 */     this.m_lLanguageCacheIncludingSuspendedNoDefault = null;
/*     */   }
/*     */ 
/*     */   public synchronized List<Language> getLanguages(DBTransaction a_transaction, boolean a_bIncludeDefault)
/*     */     throws Bn2Exception
/*     */   {
/*  92 */     return getLanguages(a_transaction, a_bIncludeDefault, true);
/*     */   }
/*     */ 
/*     */   public List<Language> getLanguages(DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/* 101 */     return getLanguages(a_transaction, true, true);
/*     */   }
/*     */ 
/*     */   public List<Language> getLanguages(DBTransaction a_transaction, boolean a_bIncludeDefault, boolean a_bIncludeSuspended)
/*     */     throws Bn2Exception
/*     */   {
/* 111 */     if ((a_bIncludeDefault) && (a_bIncludeSuspended) && (this.m_lLanguageCacheAll != null))
/*     */     {
/* 113 */       return this.m_lLanguageCacheAll;
/*     */     }
/* 115 */     if ((a_bIncludeDefault) && (!a_bIncludeSuspended) && (this.m_lLanguageCacheNotSuspendedWithDefault != null))
/*     */     {
/* 117 */       return this.m_lLanguageCacheNotSuspendedWithDefault;
/*     */     }
/* 119 */     if ((!a_bIncludeDefault) && (a_bIncludeSuspended) && (this.m_lLanguageCacheIncludingSuspendedNoDefault != null))
/*     */     {
/* 121 */       return this.m_lLanguageCacheIncludingSuspendedNoDefault;
/*     */     }
/* 123 */     if ((!a_bIncludeDefault) && (!a_bIncludeSuspended) && (this.m_lLanguageCacheNotSuspendedNoDefault != null))
/*     */     {
/* 125 */       return this.m_lLanguageCacheNotSuspendedNoDefault;
/*     */     }
/*     */ 
/* 128 */     DBTransaction transaction = a_transaction;
/*     */ 
/* 130 */     String sSql = null;
/*     */ 
/* 133 */     if (transaction == null)
/*     */     {
/* 135 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*     */     }
/*     */     List<Language>languages;
/*     */     try {
/* 140 */       Connection con = transaction.getConnection();
/*     */ 
/* 142 */       sSql = "SELECT lang.Id AS langId,lang.Name AS langName,lang.NativeName AS langNativeName,lang.Code AS langCode,lang.IsSuspended AS langIsSuspended,lang.IsDefault AS langIsDefault,lang.IsRightToLeft AS langIsRightToLeft,lang.IconFilename AS langIconFilename,lang.UsesLatinAlphabet AS langUsesLatinAlphabet  FROM Language lang";
/*     */ 
/* 144 */       if (!a_bIncludeDefault)
/*     */       {
/* 146 */         sSql = sSql + " WHERE IsDefault=0 ";
/*     */       }
/*     */ 
/* 149 */       if (!a_bIncludeSuspended)
/*     */       {
/* 151 */         sSql = sSql + " WHERE IsSuspended=0 ";
/*     */       }
/*     */ 
/* 154 */       sSql = sSql + " ORDER BY Id";
/*     */ 
/* 156 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 157 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 159 */       languages = new ArrayList();
/*     */ 
/* 161 */       while (rs.next())
/*     */       {
/* 163 */         Language language = LanguageDBUtil.createLanguageFromRS(rs);
/* 164 */         languages.add(language);
/*     */       }
/*     */ 
/* 167 */       psql.close();
/*     */ 
/* 170 */       if ((a_bIncludeDefault) && (a_bIncludeSuspended))
/*     */       {
/* 172 */         this.m_lLanguageCacheAll = languages;
/* 173 */         this.m_languageCacheAllByCode = new HashMap();
/* 174 */         for (Language language : languages)
/*     */         {
/* 176 */           this.m_languageCacheAllByCode.put(language.getCode(), language);
/*     */         }
/*     */       }
/* 179 */       else if ((a_bIncludeDefault) && (!a_bIncludeSuspended))
/*     */       {
/* 181 */         this.m_lLanguageCacheNotSuspendedWithDefault = languages;
/*     */       }
/* 183 */       else if ((!a_bIncludeDefault) && (a_bIncludeSuspended))
/*     */       {
/* 185 */         this.m_lLanguageCacheIncludingSuspendedNoDefault = languages;
/*     */       }
/* 187 */       else if ((!a_bIncludeDefault) && (!a_bIncludeSuspended))
/*     */       {
/* 189 */         this.m_lLanguageCacheNotSuspendedNoDefault = languages;
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 194 */       if ((transaction != null) && (a_transaction == null))
/*     */       {
/*     */         try
/*     */         {
/* 198 */           transaction.rollback();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 202 */           this.m_logger.error("SQL Exception whilst rolling back", sqle);
/*     */         }
/*     */       }
/*     */ 
/* 206 */       throw new SQLStatementException(sSql, e);
/*     */     }
/*     */     finally
/*     */     {
/* 211 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 215 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 219 */           this.m_logger.error("SQL Exception whilst trying to close connection", sqle);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 224 */     return languages;
/*     */   }
/*     */ 
/*     */   public Language getLanguageByCode(DBTransaction a_dbTransaction, String a_sLanguageCode)
/*     */     throws Bn2Exception
/*     */   {
/* 230 */     if (this.m_languageCacheAllByCode == null)
/*     */     {
/* 232 */       getLanguages(a_dbTransaction, true, true);
/*     */     }
/* 234 */     return (Language)this.m_languageCacheAllByCode.get(a_sLanguageCode);
/*     */   }
/*     */ 
/*     */   public void createEmptyTranslations(DBTransaction a_transaction, Translatable a_translatable)
/*     */     throws Bn2Exception
/*     */   {
/* 243 */     List languages = getLanguages(a_transaction, false);
/* 244 */     Iterator itLanguages = languages.iterator();
/* 245 */     Vector vTranslations = new Vector();
/* 246 */     while (itLanguages.hasNext())
/*     */     {
/* 248 */       com.bright.framework.language.bean.Translation translation = a_translatable.createTranslation((Language)itLanguages.next());
/* 249 */       vTranslations.add(translation);
/*     */     }
/* 251 */     a_translatable.setTranslations(vTranslations);
/*     */   }
/*     */ 
/*     */   public void ensureTranslations(DBTransaction a_transaction, Translatable a_translatable)
/*     */     throws Bn2Exception
/*     */   {
/* 260 */     List languages = getLanguages(a_transaction, false);
/* 261 */     Iterator itLanguages = languages.iterator();
/* 262 */     Vector vTranslations = a_translatable.getTranslations();
/* 263 */     int iLanguageIndex = 0;
/* 264 */     while (itLanguages.hasNext())
/*     */     {
/* 266 */       boolean bFoundTranslation = false;
/* 267 */       Language language = (Language)itLanguages.next();
/* 268 */       for (Object vTranslation : vTranslations)
/*     */       {
/* 270 */         Translation translation = (Translation)vTranslation;
/* 271 */         if (translation.getLanguage().equals(language))
/*     */         {
/* 273 */           bFoundTranslation = true;
/* 274 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 278 */       if (!bFoundTranslation)
/*     */       {
/* 280 */         com.bright.framework.language.bean.Translation translation = a_translatable.createTranslation(language);
/* 281 */         vTranslations.add(iLanguageIndex, translation);
/*     */       }
/* 283 */       iLanguageIndex++;
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized Language getDefaultLanguage(DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/* 293 */     if (this.m_defaultLanguage == null)
/*     */     {
/* 295 */       this.m_defaultLanguage = getLanguage(a_transaction, -1L);
/*     */     }
/* 297 */     return this.m_defaultLanguage;
/*     */   }
/*     */ 
/*     */   public Language getLanguage(DBTransaction a_transaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/* 306 */     DBTransaction transaction = a_transaction;
/*     */ 
/* 309 */     if (transaction == null)
/*     */     {
/* 311 */       transaction = this.m_transactionManager.getNewTransaction();
/* 315 */     }
/*     */ String ksMethodName = "getLanguage";
/* 316 */     String sSQL = "SELECT lang.Id AS langId,lang.Name AS langName,lang.NativeName AS langNativeName,lang.Code AS langCode,lang.IsSuspended AS langIsSuspended,lang.IsDefault AS langIsDefault,lang.IsRightToLeft AS langIsRightToLeft,lang.IconFilename AS langIconFilename,lang.UsesLatinAlphabet AS langUsesLatinAlphabet  FROM Language lang ";
/*     */     Language language;
/*     */     try {
/* 320 */       Connection con = transaction.getConnection();
/*     */ 
/* 322 */       if (a_lId <= 0L)
/*     */       {
/* 324 */         sSQL = sSQL + "WHERE IsDefault=?";
/*     */       }
/*     */       else
/*     */       {
/* 328 */         sSQL = sSQL + "WHERE Id=?";
/*     */       }
/*     */ 
/* 331 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*     */ 
/* 333 */       if (a_lId <= 0L)
/*     */       {
/* 335 */         psql.setBoolean(1, true);
/*     */       }
/*     */       else
/*     */       {
/* 339 */         psql.setLong(1, a_lId);
/*     */       }
/*     */ 
/* 342 */       ResultSet rs = psql.executeQuery();
/*     */       //language;
/* 344 */       if (rs.next())
/*     */       {
/* 346 */         language = LanguageDBUtil.createLanguageFromRS(rs);
/*     */       }
/*     */       else
/*     */       {
/* 350 */         throw new Bn2Exception(c_ksClassName + "." + "getLanguage" + "No language found for id: " + a_lId);
/*     */       }
/*     */ 
/* 353 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 357 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */     finally
/*     */     {
/* 362 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 366 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 370 */           this.m_logger.error("SQL Exception whilst trying to close connection ", sqle);
/*     */         }
/*     */       }
/*     */     }
/* 374 */     return language;
/*     */   }
/*     */ 
/*     */   public Language addLanguage(DBTransaction a_transaction, Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/* 385 */     String ksMethodName = "addLanguage";
/*     */     try
/*     */     {
/* 390 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 392 */       verifyUniqueFields(a_language, con);
/*     */ 
/* 394 */       String sSql = "INSERT INTO Language (";
/*     */ 
/* 396 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/* 397 */       long lNewId = 0L;
/* 398 */       int iCol = 1;
/*     */ 
/* 400 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 402 */         lNewId = sqlGenerator.getUniqueId(con, "LanguageSequence");
/* 403 */         sSql = sSql + "Id,";
/*     */       }
/*     */ 
/* 406 */       sSql = sSql + "Name,NativeName,Code,IsSuspended,IsRightToLeft,IconFilename,UsesLatinAlphabet,IsDefault) VALUES (?,?,?,?,?,?,?,?";
/*     */ 
/* 408 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 410 */         sSql = sSql + ",?";
/*     */       }
/*     */ 
/* 413 */       sSql = sSql + ")";
/*     */ 
/* 416 */       PreparedStatement psql = con.prepareStatement(sSql);
/*     */ 
/* 418 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 420 */         psql.setLong(iCol++, lNewId);
/*     */       }
/*     */ 
/* 423 */       psql.setString(iCol++, a_language.getName());
/* 424 */       psql.setString(iCol++, a_language.getNativeName());
/* 425 */       psql.setString(iCol++, a_language.getCode());
/* 426 */       psql.setBoolean(iCol++, a_language.isSuspended());
/* 427 */       psql.setBoolean(iCol++, a_language.isRightToLeft());
/* 428 */       psql.setString(iCol++, a_language.getIconFilename());
/* 429 */       psql.setBoolean(iCol++, a_language.getUsesLatinAlphabet());
/* 430 */       psql.setBoolean(iCol++, false);
/*     */ 
/* 432 */       psql.executeUpdate();
/*     */ 
/* 434 */       if (sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 436 */         lNewId = sqlGenerator.getUniqueId(con, "Language");
/*     */       }
/*     */ 
/* 439 */       a_language.setId(lNewId);
/*     */ 
/* 441 */       psql.close();
/*     */ 
/* 444 */       clearCaches();
/*     */ 
/* 446 */       return a_language;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 450 */       this.m_logger.error(c_ksClassName + "." + "addLanguage" + " : SQL Exception : " + e);
/* 451 */     throw new Bn2Exception(c_ksClassName + "." + "addLanguage" + " : SQL Exception : " + e, e);
/*     */  } }
/*     */ 
/*     */   protected void createTranslationsForCategoryTree(DBTransaction a_transaction, long a_lCatTreeId, Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/* 464 */     FlatCategoryList cats = this.m_categoryManager.getFlatCategoryList(a_transaction, a_lCatTreeId);
/*     */ 
/* 466 */     Iterator itCats = cats.getCategories().iterator();
/* 467 */     while (itCats.hasNext())
/*     */     {
/* 469 */       Category cat = ((Category)itCats.next()).clone();
/*     */ 
/* 472 */       Category.Translation translation = cat.createTranslation(a_language);
/* 473 */       cat.getTranslations().add(translation);
/*     */ 
/* 476 */       this.m_categoryManager.updateCategory(a_transaction, a_lCatTreeId, cat, false);
/*     */     }
/*     */ 
/* 479 */     this.m_categoryManager.rebuildCategoryCache(a_transaction, a_lCatTreeId);
/*     */   }
/*     */ 
/*     */   private void verifyUniqueFields(Language a_language, Connection con)
/*     */     throws SQLException, LanguageNameNotUniqueException, LanguageCodeNotUniqueException
/*     */   {
/* 493 */     String sSql = "SELECT Code, Name FROM Language WHERE (Code=? OR Name=?) AND Id<>?";
/* 494 */     PreparedStatement psql = con.prepareStatement(sSql);
/* 495 */     psql.setString(1, a_language.getCode());
/* 496 */     psql.setString(2, a_language.getName());
/* 497 */     psql.setLong(3, a_language.getId());
/* 498 */     ResultSet rs = psql.executeQuery();
/*     */     try
/*     */     {
/* 502 */       if (rs.next())
/*     */       {
/* 504 */         if (a_language.getName().equalsIgnoreCase(rs.getString("Name")))
/*     */         {
/* 506 */           throw new LanguageNameNotUniqueException();
/*     */         }
/* 508 */         throw new LanguageCodeNotUniqueException();
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 513 */       psql.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateLanguage(DBTransaction a_transaction, Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/* 526 */     String ksMethodName = "updateLanguage";
/*     */     try
/*     */     {
/* 532 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 534 */       verifyUniqueFields(a_language, con);
/*     */ 
/* 536 */       String sSql = "UPDATE Language SET Name=?, NativeName=?, Code=?, IsSuspended=?, IsDefault=?, IsRightToLeft=?, IconFilename=?, UsesLatinAlphabet=? WHERE Id=?";
/*     */ 
/* 538 */       int iCol = 1;
/*     */ 
/* 540 */       PreparedStatement psql = con.prepareStatement(sSql);
/*     */ 
/* 542 */       psql.setString(iCol++, a_language.getName());
/* 543 */       psql.setString(iCol++, a_language.getNativeName());
/* 544 */       psql.setString(iCol++, a_language.getCode());
/* 545 */       psql.setBoolean(iCol++, a_language.isSuspended());
/* 546 */       psql.setBoolean(iCol++, a_language.isDefault());
/* 547 */       psql.setBoolean(iCol++, a_language.isRightToLeft());
/* 548 */       psql.setString(iCol++, a_language.getIconFilename());
/* 549 */       psql.setBoolean(iCol++, a_language.getUsesLatinAlphabet());
/* 550 */       psql.setLong(iCol++, a_language.getId());
/*     */ 
/* 552 */       psql.executeUpdate();
/* 553 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 557 */       this.m_logger.error(c_ksClassName + "." + "updateLanguage" + " : SQL Exception : " + e);
/* 558 */       throw new Bn2Exception(c_ksClassName + "." + "updateLanguage" + " : SQL Exception : " + e, e);
/*     */     }
/*     */ 
/* 562 */     clearCaches();
/*     */   }
/*     */ 
/*     */   public void deleteLanguage(DBTransaction a_transaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/* 572 */     String ksMethodName = "deleteLanguage";
/*     */     try
/*     */     {
/* 577 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 579 */       String sSql = "DELETE FROM ListItem WHERE LanguageId=?";
/* 580 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 581 */       psql.setLong(1, a_lId);
/* 582 */       psql.executeUpdate();
/*     */ 
/* 584 */       sSql = "DELETE FROM EmailTemplate WHERE LanguageId=?";
/* 585 */       psql = con.prepareStatement(sSql);
/* 586 */       psql.setLong(1, a_lId);
/* 587 */       psql.executeUpdate();
/*     */ 
/* 589 */       sSql = "DELETE FROM TranslatedCategory WHERE LanguageId=?";
/* 590 */       psql = con.prepareStatement(sSql);
/* 591 */       psql.setLong(1, a_lId);
/* 592 */       psql.executeUpdate();
/*     */ 
/* 594 */       sSql = "DELETE FROM Language WHERE Id=?";
/* 595 */       psql = con.prepareStatement(sSql);
/* 596 */       psql.setLong(1, a_lId);
/* 597 */       psql.executeUpdate();
/*     */ 
/* 599 */       this.m_listManager.clearItemCache();
/*     */ 
/* 601 */       this.m_categoryManager.rebuildAllCategoryCaches(a_transaction);
/*     */ 
/* 603 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 607 */       this.m_logger.error(c_ksClassName + "." + "deleteLanguage" + " : SQL Exception : " + e);
/* 608 */       throw new Bn2Exception(c_ksClassName + "." + "deleteLanguage" + " : SQL Exception : " + e, e);
/*     */     }
/*     */ 
/* 612 */     clearCaches();
/*     */   }
/*     */ 
/*     */   public void suspendLanguage(DBTransaction a_transaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/* 623 */     suspendOrResumeLanguage(a_transaction, a_lId, true);
/*     */   }
/*     */ 
/*     */   private void suspendOrResumeLanguage(DBTransaction a_transaction, long a_lId, boolean bSuspend)
/*     */     throws Bn2Exception
/*     */   {
/* 635 */     String ksMethodName = "suspendOrResumeLanguage";
/*     */     try
/*     */     {
/* 640 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 642 */       String sSql = "UPDATE Language SET IsSuspended=? WHERE Id=?";
/*     */ 
/* 644 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 645 */       psql.setBoolean(1, bSuspend);
/* 646 */       psql.setLong(2, a_lId);
/*     */ 
/* 648 */       psql.executeUpdate();
/*     */ 
/* 650 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 654 */       this.m_logger.error(c_ksClassName + "." + "suspendOrResumeLanguage" + " : SQL Exception : " + e);
/* 655 */       throw new Bn2Exception(c_ksClassName + "." + "suspendOrResumeLanguage" + " : SQL Exception : " + e, e);
/*     */     }
/*     */ 
/* 659 */     clearCaches();
/*     */   }
/*     */ 
/*     */   public void resumeLanguage(DBTransaction a_transaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/* 670 */     suspendOrResumeLanguage(a_transaction, a_lId, false);
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager categoryManager)
/*     */   {
/* 675 */     this.m_categoryManager = categoryManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 680 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_transactionManager)
/*     */   {
/* 685 */     this.m_transactionManager = a_transactionManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.language.service.LanguageManager
 * JD-Core Version:    0.6.0
 */