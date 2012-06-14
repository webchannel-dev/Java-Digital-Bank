/*     */ package com.bright.framework.mail.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.service.LanguageManager;
/*     */ import com.bright.framework.mail.bean.EmailTemplate;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class EmailTemplateManager extends Bn2Manager
/*     */ {
/*     */   private static final String c_ksClassName = "EmailTemplateManager";
/*  52 */   private LanguageManager m_languageManager = null;
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/*  61 */     super.startup();
/*     */   }
/*     */ 
/*     */   public Vector getEmailItems(DBTransaction a_dbTransaction, long a_lTypeId, int a_iHiddenStatus)
/*     */     throws Bn2Exception
/*     */   {
/*  74 */     return getEmailItems(a_dbTransaction, null, a_lTypeId, null, a_iHiddenStatus);
/*     */   }
/*     */ 
/*     */   public Vector getEmailItems(DBTransaction a_dbTransaction, long a_lTypeId, Language a_language, int a_iHiddenStatus)
/*     */     throws Bn2Exception
/*     */   {
/*  87 */     return getEmailItems(a_dbTransaction, null, a_lTypeId, a_language, a_iHiddenStatus);
/*     */   }
/*     */ 
/*     */   public Vector getEmailItems(DBTransaction a_dbTransaction, String a_sTextId, long a_lTypeId, int a_iHiddenStatus)
/*     */     throws Bn2Exception
/*     */   {
/* 100 */     return getEmailItems(a_dbTransaction, a_sTextId, a_lTypeId, null, a_iHiddenStatus);
/*     */   }
/*     */ 
/*     */   public Vector getEmailItems(DBTransaction a_dbTransaction, String a_sTextId, long a_lTypeId, Language a_language, int a_iHiddenStatus)
/*     */     throws Bn2Exception
/*     */   {
/* 120 */     String ksMethodName = "getEmailItems";
/* 121 */     Vector vecItems = new Vector();
/*     */     try
/*     */     {
/* 125 */       Connection con = a_dbTransaction.getConnection();
/*     */ 
/* 127 */       String sSql = "SELECT et.Code etCode, et.AddrFrom etAddrFrom, et.AddrTo etAddrTo, et.AddrCc etAddrCc, et.AddrBcc etAddrBcc, et.Subject etSubject, et.Body etBody, et.TextID etTextId,et.Hidden etHidden, et.IsEnabled etIsEnabled, l.Id lId, l.Name lName,l.Code lCode FROM EmailTemplate et INNER JOIN Language l ON l.Id = et.LanguageId WHERE et.TypeId=? " + (a_sTextId != null ? " AND et.TextId=? " : "") + (a_language != null ? " AND l.Id=? " : "");
/*     */ 
/* 149 */       if (a_iHiddenStatus == 0)
/*     */       {
/* 151 */         sSql = sSql + "AND et.Hidden=1 ";
/*     */       }
/* 153 */       else if (a_iHiddenStatus == 1)
/*     */       {
/* 155 */         sSql = sSql + "AND et.Hidden=0 ";
/*     */       }
/*     */ 
/* 158 */       sSql = sSql + "ORDER BY et.Code, l.Id";
/*     */ 
/* 160 */       int iCol = 1;
/* 161 */       PreparedStatement psql = con.prepareStatement(sSql, 1004, 1007);
/* 162 */       psql.setLong(iCol++, a_lTypeId);
/*     */ 
/* 164 */       if (a_sTextId != null)
/*     */       {
/* 166 */         psql.setString(iCol++, a_sTextId);
/*     */       }
/*     */ 
/* 169 */       if (a_language != null)
/*     */       {
/* 171 */         psql.setLong(iCol++, a_language.getId());
/*     */       }
/*     */ 
/* 174 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 176 */       List languages = this.m_languageManager.getLanguages(a_dbTransaction, false);
/*     */ 
/* 178 */       while (rs.next())
/*     */       {
/* 180 */         EmailTemplate emailTemplate = createTemplate(rs);
/* 181 */         emailTemplate.setLanguage(new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
/* 182 */         vecItems.add(emailTemplate);
/*     */ 
/* 184 */         if (a_language == null)
/*     */         {
/* 186 */           Iterator itLangs = languages.iterator();
/*     */ 
/* 189 */           while (itLangs.hasNext())
/*     */           {
/* 191 */             Language language = (Language)itLangs.next();
/*     */ 
/* 195 */             if ((!rs.isAfterLast()) && (rs.next()))
/*     */             {
/* 199 */               if (rs.getLong("lId") == language.getId())
/*     */               {
/* 201 */                 EmailTemplate templateTranslation = createTemplate(rs);
/* 202 */                 templateTranslation.setLanguage(language);
/* 203 */                 vecItems.add(templateTranslation);
/* 204 */                 continue;
/*     */               }
/*     */ 
/* 207 */               rs.previous();
/*     */             }
/*     */ 
/* 211 */             EmailTemplate copy = (EmailTemplate)emailTemplate.clone();
/* 212 */             copy.setLanguage(language);
/* 213 */             vecItems.add(copy);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 218 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 223 */       throw new Bn2Exception("EmailTemplateManager.getEmailItems: Exception occurred: " + e, e);
/*     */     }
/*     */ 
/* 226 */     return vecItems;
/*     */   }
/*     */ 
/*     */   private EmailTemplate createTemplate(ResultSet rs) throws SQLException, Bn2Exception
/*     */   {
/* 231 */     EmailTemplate emailTemplate = new EmailTemplate();
/* 232 */     emailTemplate.setCode(rs.getString("etCode"));
/* 233 */     emailTemplate.setAddrFrom(rs.getString("etAddrFrom"));
/* 234 */     emailTemplate.setAddrTo(rs.getString("etAddrTo"));
/* 235 */     emailTemplate.setAddrCc(rs.getString("etAddrCc"));
/* 236 */     emailTemplate.setAddrBcc(rs.getString("etAddrBcc"));
/* 237 */     emailTemplate.setAddrSubject(rs.getString("etSubject"));
/* 238 */     emailTemplate.setAddrBody(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "etBody"));
/* 239 */     emailTemplate.setTextId(rs.getString("etTextId"));
/* 240 */     emailTemplate.setHidden(rs.getBoolean("etHidden"));
/* 241 */     emailTemplate.setEnabled(rs.getBoolean("etIsEnabled"));
/* 242 */     return emailTemplate;
/*     */   }
/*     */ 
/*     */   public EmailTemplate getEmailItem(DBTransaction a_dbTransaction, String a_textId, long a_lTypeId, Language a_langauge)
/*     */     throws Bn2Exception
/*     */   {
/* 265 */     EmailTemplate emailTemplate = null;
/* 266 */     String ksMethodName = "getEmailItem";
/*     */ 
/* 268 */     String sSql = "SELECT et.Code etCode, et.AddrFrom etAddrFrom, et.AddrTo etAddrTo, et.AddrCc etAddrCc, et.AddrBcc etAddrBcc, et.Subject etSubject, et.Body etBody, et.TextID etTextID,et.Hidden etHidden, et.IsEnabled etIsEnabled, l.Id lId, l.Name lName,l.Code lCode FROM EmailTemplate et INNER JOIN Language l ON l.Id = et.LanguageId WHERE et.TextId=? AND et.TypeId=? AND ((l.Id=? OR l.Code=?) OR l.IsDefault = ?) ORDER BY et.TextId, l.Id DESC";
/*     */     try
/*     */     {
/* 293 */       Connection con = a_dbTransaction.getConnection();
/*     */ 
/* 295 */       PreparedStatement psql = con.prepareStatement(sSql);
/*     */ 
/* 297 */       psql.setString(1, a_textId);
/* 298 */       psql.setLong(2, a_lTypeId);
/* 299 */       psql.setLong(3, a_langauge.getId());
/* 300 */       psql.setString(4, a_langauge.getCode() != null ? a_langauge.getCode() : "");
/* 301 */       psql.setBoolean(5, true);
/*     */ 
/* 303 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 305 */       if (rs.next())
/*     */       {
/* 307 */         emailTemplate = new EmailTemplate();
/* 308 */         emailTemplate.setCode(rs.getString("etCode"));
/* 309 */         emailTemplate.setAddrFrom(rs.getString("etAddrFrom"));
/* 310 */         emailTemplate.setAddrTo(rs.getString("etAddrTo"));
/* 311 */         emailTemplate.setAddrCc(rs.getString("etAddrCc"));
/* 312 */         emailTemplate.setAddrBcc(rs.getString("etAddrBcc"));
/* 313 */         emailTemplate.setAddrSubject(rs.getString("etSubject"));
/* 314 */         emailTemplate.setAddrBody(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "etBody"));
/* 315 */         emailTemplate.setTextId(rs.getString("etTextID"));
/* 316 */         emailTemplate.setHidden(rs.getBoolean("etHidden"));
/* 317 */         emailTemplate.setEnabled(rs.getBoolean("etIsEnabled"));
/*     */ 
/* 319 */         if (rs.getLong("lId") == a_langauge.getId())
/*     */         {
/* 321 */           emailTemplate.setLanguage(new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
/*     */         }
/*     */         else
/*     */         {
/* 325 */           emailTemplate.setLanguage(this.m_languageManager.getLanguage(a_dbTransaction, a_langauge.getId()));
/*     */         }
/*     */       }
/*     */ 
/* 329 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 334 */       throw new Bn2Exception("EmailTemplateManager.getEmailItem: Exception occurred: " + e, e);
/*     */     }
/* 336 */     return emailTemplate;
/*     */   }
/*     */ 
/*     */   public void saveEmailItem(DBTransaction a_dbTransaction, EmailTemplate a_emailItem, long a_lTypeId)
/*     */     throws Bn2Exception
/*     */   {
/* 354 */     String ksMethodName = "saveEmailItem";
/*     */     try
/*     */     {
/* 358 */       String sSQL = null;
/* 359 */       PreparedStatement psql = null;
/* 360 */       Connection con = a_dbTransaction.getConnection();
/*     */ 
/* 362 */       sSQL = "UPDATE EmailTemplate SET Code=?, AddrFrom=?, AddrTo=?, AddrCc=?, AddrBcc=?, Subject=?, Body=?, Hidden=?, IsEnabled=? WHERE TextId=? AND TypeId=? AND LanguageId=? ";
/*     */ 
/* 364 */       psql = con.prepareStatement(sSQL);
/* 365 */       int iCol = 1;
/* 366 */       psql.setString(iCol++, a_emailItem.getCode());
/* 367 */       psql.setString(iCol++, a_emailItem.getAddrFrom());
/* 368 */       psql.setString(iCol++, a_emailItem.getAddrTo());
/* 369 */       psql.setString(iCol++, a_emailItem.getAddrCc());
/* 370 */       psql.setString(iCol++, a_emailItem.getAddrBcc());
/* 371 */       psql.setString(iCol++, a_emailItem.getAddrSubject());
/* 372 */       psql.setString(iCol++, a_emailItem.getAddrBody());
/* 373 */       psql.setBoolean(iCol++, a_emailItem.getHidden());
/* 374 */       psql.setBoolean(iCol++, a_emailItem.getEnabled());
/* 375 */       psql.setString(iCol++, a_emailItem.getTextId());
/* 376 */       psql.setLong(iCol++, a_lTypeId);
/* 377 */       psql.setLong(iCol++, a_emailItem.getLanguage().getId());
/* 378 */       int iRows = psql.executeUpdate();
/* 379 */       psql.close();
/*     */ 
/* 382 */       if (iRows <= 0)
/*     */       {
/* 384 */         sSQL = "INSERT INTO EmailTemplate (TextId,LanguageId,TypeId,Code,AddrFrom,AddrTo,AddrCc,AddrBcc,Subject,Body,Hidden,IsEnabled) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";
/*     */ 
/* 386 */         psql = con.prepareStatement(sSQL);
/* 387 */         iCol = 1;
/* 388 */         psql.setString(iCol++, a_emailItem.getTextId());
/* 389 */         psql.setLong(iCol++, a_emailItem.getLanguage().getId());
/* 390 */         psql.setLong(iCol++, a_lTypeId);
/* 391 */         psql.setString(iCol++, a_emailItem.getCode());
/* 392 */         psql.setString(iCol++, a_emailItem.getAddrFrom());
/* 393 */         psql.setString(iCol++, a_emailItem.getAddrTo());
/* 394 */         psql.setString(iCol++, a_emailItem.getAddrCc());
/* 395 */         psql.setString(iCol++, a_emailItem.getAddrBcc());
/* 396 */         psql.setString(iCol++, a_emailItem.getAddrSubject());
/* 397 */         psql.setString(iCol++, a_emailItem.getAddrBody());
/* 398 */         psql.setBoolean(iCol++, a_emailItem.getHidden());
/* 399 */         psql.setBoolean(iCol++, a_emailItem.getEnabled());
/*     */ 
/* 401 */         psql.executeUpdate();
/* 402 */         psql.close();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 408 */       throw new Bn2Exception("EmailTemplateManager.saveEmailItem: Exception occurred: " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void saveFromEmailAddress(DBTransaction a_dbTransaction, EmailTemplate a_emailItem)
/*     */     throws Bn2Exception
/*     */   {
/* 427 */     String ksMethodName = "saveFromEmailAddress";
/*     */     try
/*     */     {
/* 431 */       String sSQL = null;
/* 432 */       PreparedStatement psql = null;
/* 433 */       Connection con = a_dbTransaction.getConnection();
/*     */ 
/* 435 */       sSQL = "UPDATE EmailTemplate SET AddrFrom=? WHERE AddrFrom NOT LIKE '%#%'";
/*     */ 
/* 437 */       psql = con.prepareStatement(sSQL);
/* 438 */       int iCol = 1;
/* 439 */       psql.setString(iCol++, a_emailItem.getAddrFrom());
/*     */ 
/* 441 */       psql.executeUpdate();
/* 442 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 448 */       throw new Bn2Exception("EmailTemplateManager.saveFromEmailAddress: Exception occurred: " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 454 */       if (a_dbTransaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 458 */           a_dbTransaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 462 */           this.m_logger.error("EmailTemplateManager: SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addEmailItem(DBTransaction a_dbTransaction, EmailTemplate a_emailItem, long a_lTypeId)
/*     */     throws Bn2Exception
/*     */   {
/* 480 */     String ksMethodName = "addEmailItem";
/*     */     try
/*     */     {
/* 484 */       String sSQL = null;
/* 485 */       PreparedStatement psql = null;
/* 486 */       Connection con = a_dbTransaction.getConnection();
/*     */ 
/* 488 */       ApplicationSql sqlGenerator = SQLGenerator.getInstance();
/* 489 */       long lNewId = 0L;
/*     */ 
/* 491 */       sSQL = "INSERT INTO EmailTemplate (Code,AddrFrom,AddrTo,AddrCc,AddrBcc,Subject,Body,TextId,LanguageId,TypeId,Hidden) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
/*     */ 
/* 494 */       psql = con.prepareStatement(sSQL);
/* 495 */       int iCol = 1;
/*     */ 
/* 497 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 499 */         psql.setLong(iCol++, lNewId);
/*     */       }
/*     */ 
/* 502 */       psql.setString(iCol++, a_emailItem.getCode());
/* 503 */       psql.setString(iCol++, a_emailItem.getAddrFrom());
/* 504 */       psql.setString(iCol++, a_emailItem.getAddrTo());
/* 505 */       psql.setString(iCol++, a_emailItem.getAddrCc());
/* 506 */       psql.setString(iCol++, a_emailItem.getAddrBcc());
/* 507 */       psql.setString(iCol++, a_emailItem.getAddrSubject());
/* 508 */       psql.setString(iCol++, a_emailItem.getAddrBody());
/* 509 */       psql.setString(iCol++, a_emailItem.getTextId());
/* 510 */       psql.setLong(iCol++, a_emailItem.getLanguage().getId());
/* 511 */       psql.setLong(iCol++, a_lTypeId);
/* 512 */       psql.setBoolean(iCol++, a_emailItem.getHidden());
/*     */ 
/* 514 */       psql.executeUpdate();
/* 515 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 520 */       throw new Bn2Exception("EmailTemplateManager.addEmailItem: Exception occurred: " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteEmailTemplate(DBTransaction a_dbTransaction, long lTypeId, String sTextId)
/*     */     throws Bn2Exception
/*     */   {
/* 534 */     String ksMethodName = "deleteEmailTemplate";
/*     */     try
/*     */     {
/* 538 */       String sSQL = null;
/* 539 */       PreparedStatement psql = null;
/* 540 */       Connection con = a_dbTransaction.getConnection();
/*     */ 
/* 542 */       sSQL = "DELETE FROM EmailTemplate WHERE TextId=? AND TypeId=?";
/*     */ 
/* 544 */       psql = con.prepareStatement(sSQL);
/* 545 */       psql.setString(1, sTextId);
/* 546 */       psql.setLong(2, lTypeId);
/*     */ 
/* 548 */       psql.executeUpdate();
/* 549 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 554 */       throw new Bn2Exception("EmailTemplateManager.deleteEmailTemplate: Exception occurred: " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void hideEmailTemplate(DBTransaction a_dbTransaction, long a_lTypeId, String a_sTextId)
/*     */     throws Bn2Exception
/*     */   {
/* 574 */     String ksMethodName = "hideEmailTemplate";
/*     */     try
/*     */     {
/* 578 */       String sSQL = null;
/* 579 */       PreparedStatement psql = null;
/* 580 */       Connection con = a_dbTransaction.getConnection();
/*     */ 
/* 582 */       sSQL = "UPDATE EmailTemplate SET Hidden=1 WHERE TextId=? AND TypeId=?";
/*     */ 
/* 584 */       psql = con.prepareStatement(sSQL);
/* 585 */       psql.setString(1, a_sTextId);
/* 586 */       psql.setLong(2, a_lTypeId);
/*     */ 
/* 588 */       psql.executeUpdate();
/* 589 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 594 */       throw new Bn2Exception("EmailTemplateManager.hideEmailTemplate: Exception occurred: " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void showEmailTemplate(DBTransaction a_dbTransaction, long a_lTypeId, String a_sTextId)
/*     */     throws Bn2Exception
/*     */   {
/* 612 */     String ksMethodName = "showEmailTemplate";
/*     */     try
/*     */     {
/* 616 */       String sSQL = null;
/* 617 */       PreparedStatement psql = null;
/* 618 */       Connection con = a_dbTransaction.getConnection();
/*     */ 
/* 620 */       sSQL = "UPDATE EmailTemplate SET Hidden=0 WHERE TextId=? AND TypeId=?";
/*     */ 
/* 622 */       psql = con.prepareStatement(sSQL);
/* 623 */       psql.setString(1, a_sTextId);
/* 624 */       psql.setLong(2, a_lTypeId);
/*     */ 
/* 626 */       psql.executeUpdate();
/* 627 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 632 */       throw new Bn2Exception("EmailTemplateManager.showEmailTemplate: Exception occurred: " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager languageManager)
/*     */   {
/* 638 */     this.m_languageManager = languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.service.EmailTemplateManager
 * JD-Core Version:    0.6.0
 */