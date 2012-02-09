/*    */ package com.bright.framework.language.util;
/*    */ 
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.framework.language.bean.Language;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class LanguageDBUtil
/*    */ {
/*    */   public static final String c_ksLanguageFields = "lang.Id AS langId,lang.Name AS langName,lang.NativeName AS langNativeName,lang.Code AS langCode,lang.IsSuspended AS langIsSuspended,lang.IsDefault AS langIsDefault,lang.IsRightToLeft AS langIsRightToLeft,lang.IconFilename AS langIconFilename,lang.UsesLatinAlphabet AS langUsesLatinAlphabet ";
/*    */ 
/*    */   public static Language createLanguageFromRS(ResultSet a_rs)
/*    */     throws SQLException
/*    */   {
/* 52 */     return populateLanguageFromRS(new Language(), a_rs);
/*    */   }
/*    */ 
/*    */   public static Language populateLanguageFromRS(Language a_language, ResultSet a_rs)
/*    */     throws SQLException
/*    */   {
/* 58 */     a_language.setId(a_rs.getLong("langId"));
/* 59 */     a_language.setName(a_rs.getString("langName"));
/* 60 */     a_language.setNativeName(a_rs.getString("langNativeName"));
/* 61 */     a_language.setCode(a_rs.getString("langCode"));
/* 62 */     a_language.setSuspended(a_rs.getBoolean("langIsSuspended"));
/* 63 */     a_language.setDefault(a_rs.getBoolean("langIsDefault"));
/* 64 */     a_language.setRightToLeft(a_rs.getBoolean("langIsRightToLeft"));
/* 65 */     String sIconFilename = a_rs.getString("langIconFilename");
/* 66 */     a_language.setIconFilename(sIconFilename);
/* 67 */     a_language.setUsesLatinAlphabet(a_rs.getBoolean("langUsesLatinAlphabet"));
/* 68 */     if (StringUtils.isNotEmpty(sIconFilename))
/*    */     {
/* 70 */       a_language.setIconFilePath("/" + AssetBankSettings.getLanguageIconDirectory() + "/" + sIconFilename);
/*    */     }
/* 72 */     if (StringUtils.isEmpty(a_language.getNativeName()))
/*    */     {
/* 74 */       a_language.setNativeName(a_language.getName());
/*    */     }
/* 76 */     return a_language;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.language.util.LanguageDBUtil
 * JD-Core Version:    0.6.0
 */