/*     */ package com.bright.framework.news.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.database.AssetBankSql;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.bean.LanguageAwareComponent;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.news.bean.NewsItem;
/*     */ import com.bright.framework.news.bean.NewsItem.Translation;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class NewsManager extends Bn2Manager
/*     */   implements LanguageAwareComponent
/*     */ {
/*     */   private static final String c_ksClassName = "NewsManager";
/*     */ 
/*     */   public NewsItem getNewsItem(DBTransaction a_transaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/*  58 */     String ksMethodName = "getNewsItem";
/*  59 */     Connection con = null;
/*  60 */     String sSql = null;
/*  61 */     PreparedStatement psql = null;
/*  62 */     NewsItem item = null;
/*     */     try
/*     */     {
/*  66 */       con = a_transaction.getConnection();
/*     */ 
/*  68 */       sSql = "SELECT ni.Id niId, ni.Title niTitle, ni.Content niContent, ni.DateCreated niDateCreated, ni.IsPublished niIsPublished, tni.Title tniTitle, tni.Content tniContent, l.Id lId, l.Name lName, l.Code lCode FROM NewsItem ni LEFT JOIN TranslatedNewsItem tni ON tni.NewsItemId = ni.Id LEFT JOIN Language l ON l.Id = tni.LanguageId WHERE ni.Id=? ";
/*     */ 
/*  85 */       psql = con.prepareStatement(sSql);
/*  86 */       psql.setLong(1, a_lId);
/*  87 */       ResultSet rs = psql.executeQuery();
/*     */ 
/*  89 */       while (rs.next())
/*     */       {
/*  91 */         if (item == null)
/*     */         {
/*  93 */           item = new NewsItem();
/*  94 */           item.setId(a_lId);
/*  95 */           item.setName(rs.getString("niTitle"));
/*  96 */           item.setContent(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "niContent"));
/*  97 */           item.setCreatedDate(rs.getTimestamp("niDateCreated"));
/*  98 */           item.setPublished(rs.getBoolean("niIsPublished"));
/*     */         }
/*     */ 
/* 101 */         addNewsItemTranslation(item, rs, -1);
/*     */       }
/*     */ 
/* 104 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 108 */       this.m_logger.error("NewsManager.getNewsItem : SQL Exception : " + e);
/* 109 */       throw new Bn2Exception("NewsManager.getNewsItem : SQL Exception : " + e, e);
/*     */     }
/* 111 */     return item;
/*     */   }
/*     */ 
/*     */   public NewsItem getNewsItem(DBTransaction a_transaction, long a_lId, Language a_language, boolean a_bIsPublished)
/*     */     throws Bn2Exception
/*     */   {
/* 125 */     NewsItem item = getNewsItem(a_transaction, a_lId, a_language);
/* 126 */     if (!a_bIsPublished)
/*     */     {
/* 128 */       return item;
/*     */     }
/* 130 */     if (item.isPublished())
/*     */     {
/* 132 */       return item;
/*     */     }
/* 134 */     return null;
/*     */   }
/*     */ 
/*     */   public NewsItem getNewsItem(DBTransaction a_transaction, long a_lId, Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/* 147 */     NewsItem item = getNewsItem(a_transaction, a_lId);
/* 148 */     item.createTranslation(a_language);
/* 149 */     return item;
/*     */   }
/*     */ 
/*     */   private void addNewsItemTranslation(NewsItem a_item, ResultSet a_rs, int a_iTruncateAt) throws SQLException, Bn2Exception
/*     */   {
/* 154 */     if (a_rs.getLong("lId") > 0L)
/*     */     {
/*     */      // NewsItem tmp18_17 = a_item; tmp18_17.getClass(); NewsItem.Translation translation = new NewsItem.Translation(tmp18_17, new Language(a_rs.getLong("lId"), a_rs.getString("lName"), a_rs.getString("lCode")));
/* 157 */       NewsItem.Translation translation = a_item.new Translation(new Language(a_rs.getLong("lId"), a_rs.getString("lName"), a_rs.getString("lCode")));
                translation.setName(a_rs.getString("tniTitle"));
/* 158 */       String sTranslatedContent = SQLGenerator.getInstance().getStringFromLargeTextField(a_rs, "tniContent");
/* 159 */       if ((sTranslatedContent != null) && (a_iTruncateAt >= 0) && (StringUtil.stripHTML(sTranslatedContent).length() > a_iTruncateAt))
/*     */       {
/* 161 */         sTranslatedContent = StringUtil.stripHTML(sTranslatedContent);
/* 162 */         sTranslatedContent = sTranslatedContent.substring(0, a_iTruncateAt).trim();
/*     */       }
/* 164 */       translation.setContent(sTranslatedContent);
/*     */ 
/* 166 */       a_item.getTranslations().add(translation);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Vector getNewsItems(DBTransaction a_transaction, Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/* 180 */     Vector vNews = getNewsItems(a_transaction, true, AssetBankSettings.getNewsMaxCharacters(), AssetBankSettings.getNewsMaxItems());
/*     */ 
/* 182 */     LanguageUtils.setLanguageOnAll(vNews, a_language);
/*     */ 
/* 184 */     return vNews;
/*     */   }
/*     */ 
/*     */   public Vector getNewsItems(DBTransaction a_transaction, boolean a_bPublishedOnly, int a_iTruncateAt, int a_iMaxNewsItems)
/*     */     throws Bn2Exception
/*     */   {
/* 197 */     String ksMethodName = "getNewsItems";
/* 198 */     Connection con = null;
/* 199 */     String sSql = null;
/* 200 */     PreparedStatement psql = null;
/* 201 */     Vector items = null;
/*     */     try
/*     */     {
/* 205 */       con = a_transaction.getConnection();
/*     */ 
/* 207 */       sSql = "SELECT ni.Id niId, ni.Title niTitle, ni.Content niContent, ni.DateCreated niDateCreated, ni.IsPublished niIsPublished, tni.Title tniTitle, tni.Content tniContent, l.Id lId, l.Name lName, l.Code lCode FROM NewsItem ni LEFT JOIN TranslatedNewsItem tni ON tni.NewsItemId = ni.Id LEFT JOIN Language l ON l.Id = tni.LanguageId " + (a_bPublishedOnly ? "WHERE ni.IsPublished=? " : "") + "ORDER BY ni.DateCreated DESC ";
/*     */ 
/* 225 */       if (a_iMaxNewsItems > 0)
/*     */       {
/* 227 */         sSql = SQLGenerator.getInstance().setRowLimit(sSql, a_iMaxNewsItems);
/*     */       }
/*     */ 
/* 230 */       psql = con.prepareStatement(sSql);
/*     */ 
/* 232 */       if (a_bPublishedOnly)
/*     */       {
/* 234 */         psql.setBoolean(1, true);
/*     */       }
/*     */ 
/* 239 */       ResultSet rs = psql.executeQuery();
/* 240 */       long lLastId = 0L;
/* 241 */       NewsItem item = null;
/* 242 */       items = new Vector();
/*     */ 
/* 244 */       while (rs.next())
/*     */       {
/* 246 */         if (rs.getLong("niId") != lLastId)
/*     */         {
/* 248 */           item = new NewsItem();
/* 249 */           item.setId(rs.getLong("niId"));
/* 250 */           item.setName(rs.getString("niTitle"));
/*     */ 
/* 253 */           String sContent = SQLGenerator.getInstance().getStringFromLargeTextField(rs, "niContent");
/* 254 */           if ((a_iTruncateAt >= 0) && (StringUtil.stripHTML(sContent).length() > a_iTruncateAt))
/*     */           {
/* 256 */             sContent = StringUtil.stripHTML(sContent);
/* 257 */             sContent = sContent.substring(0, a_iTruncateAt).trim();
/* 258 */             item.setIsTruncated(true);
/*     */           }
/*     */ 
/* 261 */           item.setContent(sContent);
/*     */ 
/* 263 */           item.setCreatedDate(rs.getTimestamp("niDateCreated"));
/* 264 */           item.setPublished(rs.getBoolean("niIsPublished"));
/* 265 */           items.add(item);
/*     */ 
/* 267 */           lLastId = item.getId();
/*     */         }
/*     */ 
/* 270 */         addNewsItemTranslation(item, rs, a_iTruncateAt);
/*     */       }
/*     */ 
/* 273 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 277 */       this.m_logger.error("NewsManager.getNewsItems : SQL Exception : " + e);
/* 278 */       throw new Bn2Exception("NewsManager.getNewsItems : SQL Exception : " + e, e);
/*     */     }
/* 280 */     return items;
/*     */   }
/*     */ 
/*     */   public void saveNewsItem(DBTransaction a_transaction, NewsItem a_item)
/*     */     throws Bn2Exception
/*     */   {
/* 292 */     String ksMethodName = "saveNewsItem";
/* 293 */     Connection con = null;
/*     */     try
/*     */     {
/* 297 */       con = a_transaction.getConnection();
/*     */ 
/* 300 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*     */ 
/* 302 */       if (a_item.getId() <= 0L)
/*     */       {
/* 304 */         long lNewId = 0L;
/*     */ 
/* 306 */         String sSql = "INSERT INTO NewsItem (";
/*     */ 
/* 308 */         if (!sqlGenerator.usesAutoincrementFields())
/*     */         {
/* 310 */           lNewId = sqlGenerator.getUniqueId(con, "NewsItemSequence");
/* 311 */           sSql = sSql + "Id,";
/*     */         }
/*     */ 
/* 314 */         sSql = sSql + "Title,Content,DateCreated,IsPublished) VALUES (?,?,?,?";
/*     */ 
/* 316 */         if (!sqlGenerator.usesAutoincrementFields())
/*     */         {
/* 318 */           sSql = sSql + ",?";
/*     */         }
/*     */ 
/* 321 */         sSql = sSql + ")";
/*     */ 
/* 323 */         int iCol = 1;
/* 324 */         PreparedStatement psql = con.prepareStatement(sSql);
/*     */ 
/* 326 */         if (!sqlGenerator.usesAutoincrementFields())
/*     */         {
/* 328 */           psql.setLong(iCol++, lNewId);
/*     */         }
/*     */ 
/* 331 */         psql.setString(iCol++, a_item.getName());
/* 332 */         psql.setString(iCol++, a_item.getContent());
/* 333 */         psql.setTimestamp(iCol++, new Timestamp(a_item.getCreatedDate().getTime()));
/* 334 */         psql.setBoolean(iCol++, a_item.isPublished());
/*     */ 
/* 336 */         psql.executeUpdate();
/*     */ 
/* 339 */         if (sqlGenerator.usesAutoincrementFields())
/*     */         {
/* 341 */           lNewId = sqlGenerator.getUniqueId(con, "NewsItem");
/*     */         }
/*     */ 
/* 344 */         psql.close();
/*     */ 
/* 346 */         a_item.setId(lNewId);
/*     */       }
/*     */       else
/*     */       {
/* 350 */         String sSql = "UPDATE NewsItem SET Title=?, Content=?, DateCreated=?, IsPublished=? WHERE Id=?";
/*     */ 
/* 352 */         PreparedStatement psql = con.prepareStatement(sSql);
/* 353 */         psql.setString(1, a_item.getName());
/* 354 */         psql.setString(2, a_item.getContent());
/* 355 */         psql.setTimestamp(3, new Timestamp(a_item.getCreatedDate().getTime()));
/* 356 */         psql.setBoolean(4, a_item.isPublished());
/* 357 */         psql.setLong(5, a_item.getId());
/*     */ 
/* 359 */         psql.executeUpdate();
/*     */ 
/* 361 */         psql.close();
/*     */       }
/*     */ 
/* 365 */       Iterator itTranslations = a_item.getTranslations().iterator();
/* 366 */       while (itTranslations.hasNext())
/*     */       {
/* 368 */         NewsItem.Translation translation = (NewsItem.Translation)itTranslations.next();
/* 369 */         if (translation.getLanguage().getId() > 0L)
/*     */         {
/* 371 */           saveNewsItemTranslation(a_transaction, translation);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 377 */       this.m_logger.error("NewsManager.saveNewsItem : SQL Exception : " + e);
/* 378 */       throw new Bn2Exception("NewsManager.saveNewsItem : SQL Exception : " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void saveNewsItemTranslation(DBTransaction a_dbTransaction, NewsItem.Translation a_translation)
/*     */     throws Bn2Exception
/*     */   {
/* 385 */     String ksMethodName = "saveNewsItemTranslation";
/*     */ 
/* 387 */     Connection con = null;
/* 388 */     PreparedStatement psql = null;
/* 389 */     String sSQL = null;
/*     */     try
/*     */     {
/* 393 */       con = a_dbTransaction.getConnection();
/* 394 */       int iCol = 1;
/*     */ 
/* 396 */       sSQL = "DELETE FROM TranslatedNewsItem WHERE NewsItemId=? AND LanguageId=?";
/* 397 */       psql = con.prepareStatement(sSQL);
/* 398 */       psql.setLong(iCol++, a_translation.getStringDataBeanId());
/* 399 */       psql.setLong(iCol++, a_translation.getLanguage().getId());
/* 400 */       psql.executeUpdate();
/* 401 */       psql.close();
/*     */ 
/* 403 */       iCol = 1;
/* 404 */       sSQL = "INSERT INTO TranslatedNewsItem (Title,Content,NewsItemId,LanguageId) VALUES (?,?,?,?)";
/* 405 */       psql = con.prepareStatement(sSQL);
/* 406 */       psql.setString(iCol++, a_translation.getName());
/* 407 */       psql.setString(iCol++, a_translation.getContent());
/* 408 */       psql.setLong(iCol++, a_translation.getStringDataBeanId());
/* 409 */       psql.setLong(iCol++, a_translation.getLanguage().getId());
/* 410 */       psql.executeUpdate();
/* 411 */       psql.close();
/*     */     }
/*     */     catch (SQLException sqe)
/*     */     {
/* 415 */       this.m_logger.error("NewsManager.saveNewsItemTranslation - " + sqe);
/* 416 */       throw new Bn2Exception("NewsManager.saveNewsItemTranslation", sqe);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteNewsItem(DBTransaction a_transaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/* 429 */     String ksMethodName = "deleteNewsItem";
/* 430 */     Connection con = null;
/* 431 */     String sSql = null;
/* 432 */     PreparedStatement psql = null;
/*     */     try
/*     */     {
/* 435 */       con = a_transaction.getConnection();
/*     */ 
/* 437 */       sSql = "DELETE FROM TranslatedNewsItem WHERE NewsItemId=?";
/* 438 */       psql = con.prepareStatement(sSql);
/* 439 */       psql.setLong(1, a_lId);
/* 440 */       psql.executeUpdate();
/*     */ 
/* 442 */       sSql = "DELETE FROM NewsItem WHERE Id=?";
/* 443 */       psql = con.prepareStatement(sSql);
/* 444 */       psql.setLong(1, a_lId);
/* 445 */       psql.executeUpdate();
/*     */ 
/* 447 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 451 */       this.m_logger.error("NewsManager.deleteNewsItem : SQL Exception : " + e);
/* 452 */       throw new Bn2Exception("NewsManager.deleteNewsItem : SQL Exception : " + e, e);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.news.service.NewsManager
 * JD-Core Version:    0.6.0
 */