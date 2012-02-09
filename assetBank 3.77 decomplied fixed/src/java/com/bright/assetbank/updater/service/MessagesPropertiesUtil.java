/*     */ package com.bright.assetbank.updater.service;
/*     */ 
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.updater.constant.ApplicationUpdateConstants;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class MessagesPropertiesUtil extends PropertiesFileUtil
/*     */ {
/*     */   public MessagesPropertiesUtil()
/*     */     throws IOException
/*     */   {
/*  45 */     super(AssetBankSettings.getApplicationPath() + File.separator + ApplicationUpdateConstants.k_sMessagesPropertiesPath);
/*     */   }
/*     */ 
/*     */   public void addSetting(String a_sSettingName, String a_sSettingValue)
/*     */     throws IOException
/*     */   {
/*  69 */     Matcher matcher = getMatcher(getRegexSettingStartVal(a_sSettingName));
/*     */ 
/*  71 */     if (matcher.find())
/*     */     {
/*  73 */       updateSetting(a_sSettingName, a_sSettingValue);
/*     */     }
/*     */     else
/*     */     {
/*  77 */       StringBuffer newSetting = new StringBuffer();
/*  78 */       newSetting.append(AssetBankConstants.k_sNewLine);
/*  79 */       newSetting.append(a_sSettingName);
/*  80 */       newSetting.append("=");
/*  81 */       newSetting.append(a_sSettingValue);
/*     */ 
/*  84 */       this.m_buffer.insert(this.m_buffer.length(), newSetting);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateSetting(String a_sSettingName, String a_sSettingValue)
/*     */     throws IOException
/*     */   {
/* 105 */     Matcher matcher = getMatcher(getRegexSettingStartVal(a_sSettingName));
/* 106 */     if (matcher.find())
/*     */     {
/* 108 */       int iReplaceStartIndex = matcher.end();
/* 109 */       Pattern pattern2 = Pattern.compile(".*");
/* 110 */       Matcher matcher2 = pattern2.matcher(this.m_buffer);
/* 111 */       matcher2.find(iReplaceStartIndex);
/* 112 */       int iReplaceEndIndex = matcher2.end();
/* 113 */       this.m_buffer.replace(iReplaceStartIndex, iReplaceEndIndex, a_sSettingValue);
/*     */     }
/*     */     else
/*     */     {
/* 117 */       addSetting(a_sSettingName, a_sSettingValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteSetting(String a_sSettingName)
/*     */     throws IOException
/*     */   {
/* 136 */     Matcher matcher = getMatcher(getRegexSettingEndVal(a_sSettingName) + "[(^\\s*$)|\\n]*");
/*     */ 
/* 139 */     if (matcher.find())
/*     */     {
/* 141 */       this.m_buffer.delete(matcher.start(), matcher.end());
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.updater.service.MessagesPropertiesUtil
 * JD-Core Version:    0.6.0
 */