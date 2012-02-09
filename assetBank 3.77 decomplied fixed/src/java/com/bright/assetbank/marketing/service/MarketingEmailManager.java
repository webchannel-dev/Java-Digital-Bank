/*     */ package com.bright.assetbank.marketing.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.database.AssetBankSql;
/*     */ import com.bright.assetbank.marketing.bean.MarketingEmail;
/*     */ import com.bright.assetbank.marketing.bean.MarketingEmail.Translation;
/*     */ import com.bright.assetbank.marketing.constant.MarketingConstants;
/*     */ import com.bright.assetbank.marketing.util.MarketingUtils;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.mail.bean.EmailContent;
/*     */ import com.bright.framework.mail.constant.MailConstants;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.mail.util.MailUtils;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.mail.MessagingException;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.mail.HtmlEmail;
/*     */ 
/*     */ public class MarketingEmailManager extends Bn2Manager
/*     */   implements MailConstants, MarketingConstants
/*     */ {
/*     */   private static final String c_ksClassName = "MarketingEmailManager";
/*  61 */   private EmailManager m_emailManager = null;
/*  62 */   private AssetManager m_assetManager = null;
/*     */ 
/*     */   public int sendMarketingEmail(DBTransaction a_transaction, EmailContent a_email, String a_sFromAddress, long[] a_alUserIds, MarketingEmail a_emailPageContent, long a_lAssetBoxId)
/*     */     throws Bn2Exception
/*     */   {
/*  80 */     String ksMethodName = "sendMarketingEmail";
/*     */ 
/*  82 */     int iSuccessCount = 0;
/*  83 */     PreparedStatement psql = null;
/*     */ 
/*  85 */     if (a_lAssetBoxId > 0L)
/*     */     {
/*  87 */       addMarketingEmailToDB(a_transaction, a_email, a_emailPageContent, a_lAssetBoxId);
/*     */     }
/*     */ 
/*  90 */     Connection con = a_transaction.getConnection();
/*     */     try
/*     */     {
/*  94 */       String sSql = "SELECT Username, Title, Forename, Surname, EmailAddress, LanguageId FROM AssetBankUser WHERE Id IN (0," + StringUtil.convertNumbersToString(a_alUserIds, ",") + ") " + "ORDER BY LanguageId";
/*     */ 
/*  99 */       psql = con.prepareStatement(sSql);
/* 100 */       ResultSet rs = psql.executeQuery();
/* 101 */       Language language = null;
/*     */ 
/* 103 */       while (rs.next())
/*     */       {
/* 106 */         if ((language == null) || (language.getId() != rs.getLong("LanguageId")))
/*     */         {
/* 108 */           language = new Language(rs.getLong("LanguageId"));
/*     */         }
/*     */ 
/* 111 */         if (!StringUtils.isNotEmpty(rs.getString("EmailAddress")))
/*     */           continue;
/*     */         try
/*     */         {
/* 115 */           String sBody = a_email.getBody(language);
/*     */ 
/* 117 */           sBody = MarketingUtils.replaceEmailVariables(sBody, rs.getString("Username"), rs.getString("Title"), rs.getString("Forename"), rs.getString("Surname"));
/*     */ 
/* 119 */           HtmlEmail htmlEmail = MailUtils.populateHtmlMessage(a_email.getSubject(language), sBody, a_sFromAddress, rs.getString("EmailAddress"), null, null, null);
/*     */ 
/* 127 */           iSuccessCount += this.m_emailManager.sendEmail(htmlEmail);
/*     */         }
/*     */         catch (MessagingException e)
/*     */         {
/* 131 */           this.m_logger.error("MarketingEmailManager.sendMarketingEmail : Messaging exception whilst building html email : " + e);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 138 */       this.m_logger.error("MarketingEmailManager.sendMarketingEmail : SQL Exception : " + e);
/* 139 */       throw new Bn2Exception("MarketingEmailManager.sendMarketingEmail : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 143 */       if (psql != null)
/*     */       {
/*     */         try
/*     */         {
/* 147 */           psql.close();
/*     */         }
/*     */         catch (SQLException e) {
/*     */         }
/*     */       }
/*     */     }
/* 153 */     return iSuccessCount;
/*     */   }
/*     */ 
/*     */   private void addMarketingEmailToDB(DBTransaction a_transaction, EmailContent a_email, MarketingEmail a_emailPageContent, long a_lAssetBoxId)
/*     */     throws Bn2Exception
/*     */   {
/* 168 */     Connection con = null;
/* 169 */     String ksMethodName = "createAssetGroupForEmail";
/*     */     try
/*     */     {
/* 173 */       con = a_transaction.getConnection();
/*     */ 
/* 177 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/* 178 */       long lNewId = 0L;
/*     */ 
/* 180 */       String sSql = "INSERT INTO MarketingEmail (";
/*     */ 
/* 182 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 184 */         lNewId = sqlGenerator.getUniqueId(con, "MarketingEmailSequence");
/* 185 */         sSql = sSql + "Id,";
/*     */       }
/*     */ 
/* 188 */       sSql = sSql + "Name, Introduction, TimeSent) VALUES (?,?,?";
/*     */ 
/* 190 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 192 */         sSql = sSql + ",?";
/*     */       }
/*     */ 
/* 195 */       sSql = sSql + ")";
/*     */ 
/* 197 */       int iCol = 1;
/* 198 */       PreparedStatement psql = con.prepareStatement(sSql);
/*     */ 
/* 200 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 202 */         psql.setLong(iCol++, lNewId);
/*     */       }
/*     */ 
/* 205 */       psql.setString(iCol++, StringUtils.isEmpty(a_emailPageContent.getName()) ? null : a_emailPageContent.getName());
/* 206 */       psql.setString(iCol++, StringUtils.isEmpty(a_emailPageContent.getIntroduction()) ? null : a_emailPageContent.getIntroduction());
/* 207 */       psql.setTimestamp(iCol++, new Timestamp(System.currentTimeMillis()));
/*     */ 
/* 209 */       psql.executeUpdate();
/*     */ 
/* 212 */       if (sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 214 */         lNewId = sqlGenerator.getUniqueId(con, "MarketingGroup");
/*     */       }
/*     */ 
/* 217 */       psql.close();
/*     */ 
/* 221 */       Iterator itTranslations = a_emailPageContent.getTranslations().iterator();
/* 222 */       while (itTranslations.hasNext())
/*     */       {
/* 224 */         MarketingEmail.Translation translation = (MarketingEmail.Translation)itTranslations.next();
/* 225 */         if (translation.getLanguage().getId() > 0L)
/*     */         {
/* 227 */           iCol = 1;
/* 228 */           sSql = "INSERT INTO TranslatedMarketingEmail (Name,Introduction,MarketingEmailId,LanguageId) VALUES (?,?,?,?)";
/* 229 */           psql = con.prepareStatement(sSql);
/* 230 */           psql.setString(iCol++, translation.getName());
/* 231 */           psql.setString(iCol++, translation.getIntroduction());
/* 232 */           psql.setLong(iCol++, lNewId);
/* 233 */           psql.setLong(iCol++, translation.getLanguage().getId());
/* 234 */           psql.executeUpdate();
/* 235 */           psql.close();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 241 */       sSql = "SELECT AssetId FROM AssetBoxAsset WHERE AssetBoxId=?";
/* 242 */       psql = con.prepareStatement(sSql);
/* 243 */       psql.setLong(1, a_lAssetBoxId);
/*     */ 
/* 245 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 247 */       String sSqlInsert = "INSERT INTO AssetInMarketingEmail (MarketingEmailId,AssetId) VALUES (?,?)";
/* 248 */       while (rs.next())
/*     */       {
/* 250 */         PreparedStatement psqlInsert = con.prepareStatement(sSqlInsert);
/* 251 */         psqlInsert.setLong(1, lNewId);
/* 252 */         psqlInsert.setLong(2, rs.getLong("AssetId"));
/* 253 */         psqlInsert.executeUpdate();
/* 254 */         psqlInsert.close();
/*     */       }
/*     */ 
/* 259 */       a_email.setBody(a_email.getBody().replaceAll("#emailId#", String.valueOf(lNewId)));
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 263 */       this.m_logger.error("MarketingEmailManager.createAssetGroupForEmail : SQL Exception : " + e);
/* 264 */       throw new Bn2Exception("MarketingEmailManager.createAssetGroupForEmail : SQL Exception : " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public MarketingEmail getMarketingEmailPageData(DBTransaction a_dbTransaction, long a_lAssetBoxId, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 280 */     String ksMethodName = "getMarketingEmailPageData";
/* 281 */     Connection con = null;
/* 282 */     ResultSet rs = null;
/* 283 */     DBTransaction transaction = a_dbTransaction;
/* 284 */     String sSQL = null;
/* 285 */     PreparedStatement psql = null;
/* 286 */     Language language = a_userProfile.getCurrentLanguage();
/* 287 */     boolean bDefaultLanguage = language.equals(LanguageConstants.k_defaultLanguage);
/* 288 */     MarketingEmail email = null;
/*     */     try
/*     */     {
/* 292 */       con = transaction.getConnection();
/*     */ 
/* 294 */       sSQL = "SELECT me.Id meId, me.TimeSent, me.Name, me.Introduction, " + (bDefaultLanguage ? "" : "tme.Name, tme.Introduction, l.Id lId, l.Name lName, l.Code lCode, ") + "ass.Id assId, " + "ass.AssetTypeId assAssetTypeId " + "FROM " + "MarketingEmail me " + "LEFT JOIN AssetInMarketingEmail aime ON aime.MarketingEmailId = me.Id " + "LEFT JOIN Asset ass ON aime.AssetId = ass.Id " + (bDefaultLanguage ? "" : "LEFT JOIN TranslatedMarketingEmail tme ON tme.MarketingEmailId = me.Id LEFT JOIN Language l ON l.Id = tme.LanguageId ") + "WHERE " + "me.Id = ? " + (bDefaultLanguage ? "" : " AND l.Id = ?");
/*     */ 
/* 312 */       psql = con.prepareStatement(sSQL);
/* 313 */       psql.setLong(1, a_lAssetBoxId);
/*     */ 
/* 315 */       if (!bDefaultLanguage)
/*     */       {
/* 317 */         psql.setLong(2, language.getId());
/*     */       }
/*     */ 
/* 320 */       rs = psql.executeQuery();
/*     */ 
/* 322 */       while (rs.next())
/*     */       {
/* 324 */         if (email == null)
/*     */         {
/* 326 */           email = new MarketingEmail();
/* 327 */           email.setName(rs.getString("me.Name"));
/* 328 */           email.setIntroduction(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "me.Introduction"));
/*     */ 
/* 330 */           if (!bDefaultLanguage)
/*     */           {
/* 332 */             if (StringUtils.isNotEmpty(rs.getString("tme.Name")))
/*     */             {
/* 334 */               email.setName(rs.getString("tme.Name"));
/*     */             }
/* 336 */             if (StringUtils.isNotEmpty(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "tme.Introduction")))
/*     */             {
/* 338 */               email.setIntroduction(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "tme.Introduction"));
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 343 */         if (rs.getLong("assId") <= 0L)
/*     */           continue;
/* 345 */         AssetInList ail = new AssetInList(language);
/*     */ 
/* 348 */         Asset asset = this.m_assetManager.getAsset(transaction, rs.getLong("assId"), null, false, language, false);
/*     */ 
/* 351 */         asset.setInAssetBox(a_userProfile.getAssetBox().containsAsset(asset.getId()));
/*     */ 
/* 353 */         if (this.m_assetManager.userCanViewAsset(a_userProfile, asset))
/*     */         {
/* 355 */           ail.setAsset(asset);
/*     */ 
/* 358 */           ail.setIsDownloadable(this.m_assetManager.userCanDownloadAsset(a_userProfile, asset));
/* 359 */           ail.setIsDownloadable(this.m_assetManager.userCanDownloadAsset(a_userProfile, asset));
/* 360 */           ail.setIsDownloadableWithApproval(this.m_assetManager.userCanDownloadWithApprovalAsset(a_userProfile, asset));
/* 361 */           ail.setUserCanDownloadOriginal(this.m_assetManager.userCanDownloadOriginal(a_userProfile, asset));
/* 362 */           ail.setUserCanDownloadAdvanced(this.m_assetManager.userCanDownloadAdvanced(a_userProfile, asset));
/*     */ 
/* 364 */           email.getAssets().add(ail);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 369 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 373 */       this.m_logger.error("MarketingEmailManager.getMarketingEmailPageData : SQL Exception : " + e);
/* 374 */       throw new Bn2Exception("MarketingEmailManager.getMarketingEmailPageData : SQL Exception : " + e, e);
/*     */     }
/*     */ 
/* 377 */     return email;
/*     */   }
/*     */ 
/*     */   public void setEmailManager(EmailManager emailManager)
/*     */   {
/* 382 */     this.m_emailManager = emailManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager assetManager)
/*     */   {
/* 387 */     this.m_assetManager = assetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.service.MarketingEmailManager
 * JD-Core Version:    0.6.0
 */