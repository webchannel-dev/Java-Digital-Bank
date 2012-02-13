/*     */ package com.bright.assetbank.updater.service;
/*     */ 
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.updater.constant.ApplicationUpdateConstants;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class ApplicationSettingsUtil extends PropertiesFileUtil
/*     */ {
/*     */   public ApplicationSettingsUtil()
/*     */     throws IOException
/*     */   {
/*  51 */     super(AssetBankSettings.getApplicationPath() + File.separator + ApplicationUpdateConstants.k_sAppSettingsPath);
/*     */   }
/*     */ 
/*     */   public ApplicationSettingsUtil(String a_sAppFilePath)
/*     */     throws IOException
/*     */   {
/*  65 */     super(a_sAppFilePath);
/*     */   }
/*     */ 
/*     */   public void addSection(String a_sSectionName)
/*     */     throws IOException
/*     */   {
/*  88 */     Matcher matcher = getMatcher(getRegexStartSection(a_sSectionName));
/*     */ 
/*  91 */     if (!matcher.find())
/*     */     {
/*  93 */       appendCommentSymbols(this.m_buffer);
/*  94 */       this.m_buffer.append(" ");
/*  95 */       this.m_buffer.append(a_sSectionName);
/*  96 */       this.m_buffer.append(" ");
/*  97 */       appendCommentSymbols(this.m_buffer);
/*  98 */       this.m_buffer.append(AssetBankConstants.k_sNewLine);
/*  99 */       this.m_buffer.append(AssetBankConstants.k_sNewLine);
/* 100 */       appendCommentSymbols(this.m_buffer);
/* 101 */       this.m_buffer.append(" End ");
/* 102 */       this.m_buffer.append(a_sSectionName);
/* 103 */       this.m_buffer.append(" ");
/* 104 */       appendCommentSymbols(this.m_buffer);
/* 105 */       this.m_buffer.append(AssetBankConstants.k_sNewLine);
/* 106 */       this.m_buffer.append(AssetBankConstants.k_sNewLine);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteSection(String a_sSectionName)
/*     */     throws IOException
/*     */   {
/* 127 */     Matcher matcher = getMatcher(getRegexStartSection(a_sSectionName) + "[(^.*$)|(.*\\n)]*" + getRegexEndSection(a_sSectionName) + "[(^\\s*$)|\\n]*");
/*     */ 
/* 132 */     if (matcher.find())
/*     */     {
/* 134 */       this.m_buffer.delete(matcher.start(), matcher.end());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addSetting(String a_sSettingName, String a_sSettingValue, String a_sSectionName, Vector a_vecComments)
/*     */     throws IOException
/*     */   {
/* 165 */     StringBuffer newSetting = new StringBuffer();
/* 166 */     for (Iterator it = a_vecComments.iterator(); it.hasNext(); )
/*     */     {
/* 168 */       newSetting.append("#");
/* 169 */       newSetting.append(" ");
/* 170 */       newSetting.append(it.next());
/* 171 */       newSetting.append(AssetBankConstants.k_sNewLine);
/*     */     }
/* 173 */     newSetting.append(a_sSettingName);
/* 174 */     newSetting.append("=");
/* 175 */     newSetting.append(a_sSettingValue);
/* 176 */     newSetting.append(AssetBankConstants.k_sNewLine);
/* 177 */     newSetting.append(AssetBankConstants.k_sNewLine);
/*     */ 
/* 179 */     Matcher matcher = getMatcher(getRegexSettingStartVal(a_sSettingName));
/* 180 */     if (matcher.find())
/*     */     {
/* 182 */       updateSetting(a_sSettingName, a_sSettingValue, a_sSectionName, a_vecComments);
/*     */     }
/*     */     else
/*     */     {
/*     */       int iInsertionIndex;
/*     */      // int iInsertionIndex;
/* 189 */       if (a_sSectionName != null)
/*     */       {
/* 192 */         addSection(a_sSectionName);
/*     */ 
/* 195 */         matcher = getMatcher(getRegexEndSection(a_sSectionName));
/*     */         //int iInsertionIndex;
/* 197 */         if (matcher.find())
/*     */         {
/* 199 */           iInsertionIndex = matcher.start();
/*     */         }
/*     */         else
/*     */         {
/* 203 */           iInsertionIndex = this.m_buffer.length();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 208 */         iInsertionIndex = this.m_buffer.length();
/*     */       }
/*     */ 
/* 212 */       this.m_buffer.insert(iInsertionIndex, newSetting);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteSetting(String a_sSettingName)
/*     */     throws IOException
/*     */   {
/* 232 */     Matcher matcher = getMatcher("(^#+.*\\n)*" + getRegexSettingEndVal(a_sSettingName) + "[(^\\s*$)|\\n]*");
/*     */ 
/* 235 */     if (matcher.find())
/*     */     {
/* 237 */       this.m_buffer.delete(matcher.start(), matcher.end());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateSetting(String a_sSettingName, String a_sSettingValue, String a_sSectionName, Vector a_vecComments)
/*     */     throws IOException
/*     */   {
/* 262 */     Matcher matcher = getMatcher(getRegexSettingStartVal(a_sSettingName));
/* 263 */     if (matcher.find())
/*     */     {
/* 268 */       StringBuffer newComments = createCommentBlock(a_vecComments);
/* 269 */       if (newComments.length() > 0)
/*     */       {
/* 272 */         matcher = getMatcher("(^#+.*\\n)*" + getRegexSettingStartVal(a_sSettingName));
/*     */ 
/* 274 */         if (matcher.find())
/*     */         {
/* 276 */           this.m_buffer.replace(matcher.start(), matcher.end(), newComments + a_sSettingName + "=");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 281 */       if (a_sSettingValue != null)
/*     */       {
/* 283 */         matcher = getMatcher(getRegexSettingStartVal(a_sSettingName));
/* 284 */         if (matcher.find())
/*     */         {
/* 286 */           int iReplaceStartIndex = matcher.end();
/* 287 */           Pattern pattern2 = Pattern.compile(".*");
/* 288 */           Matcher matcher2 = pattern2.matcher(this.m_buffer);
/* 289 */           matcher2.find(iReplaceStartIndex);
/* 290 */           int iReplaceEndIndex = matcher2.end();
/* 291 */           this.m_buffer.replace(iReplaceStartIndex, iReplaceEndIndex, a_sSettingValue);
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 297 */       addSetting(a_sSettingName, a_sSettingValue, a_sSectionName, a_vecComments);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void appendSetting(String a_sSettingName, String a_sSettingValue, String a_sSectionName, Vector a_vecComments)
/*     */     throws IOException
/*     */   {
/* 322 */     Matcher matcher = getMatcher(getRegexSettingEndVal(a_sSettingName));
/* 323 */     if (matcher.find())
/*     */     {
/* 325 */       if (a_sSettingValue != null)
/*     */       {
/* 327 */         this.m_buffer.insert(matcher.end(), a_sSettingValue);
/*     */       }
/*     */ 
/* 330 */       int iCommentInsertionIndex = matcher.start();
/* 331 */       StringBuffer newComments = createCommentBlock(a_vecComments);
/* 332 */       this.m_buffer.insert(iCommentInsertionIndex, newComments);
/*     */     }
/*     */     else
/*     */     {
/* 336 */       addSetting(a_sSettingName, a_sSettingValue, a_sSectionName, a_vecComments);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String getRegexStartSection(String a_sSectionName)
/*     */   {
/* 356 */     StringBuffer buf = new StringBuffer();
/* 357 */     buf.append("^");
/* 358 */     buf.append("#");
/* 359 */     buf.append("*\\s*\\Q");
/* 360 */     buf.append(a_sSectionName);
/* 361 */     buf.append("\\E.*$");
/*     */ 
/* 363 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   private static String getRegexEndSection(String a_sSectionName)
/*     */   {
/* 382 */     StringBuffer buf = new StringBuffer();
/* 383 */     buf.append("^");
/* 384 */     buf.append("#");
/* 385 */     buf.append("*\\s*End\\s*\\Q");
/* 386 */     buf.append(a_sSectionName);
/* 387 */     buf.append("\\E.*$");
/*     */ 
/* 389 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   private void appendCommentSymbols(StringBuffer a_buffer)
/*     */   {
/* 404 */     appendCommentSymbols(a_buffer, 10);
/*     */   }
/*     */ 
/*     */   private void appendCommentSymbols(StringBuffer a_buffer, int a_numSymbols)
/*     */   {
/* 419 */     for (int i = 0; i < a_numSymbols; i++)
/*     */     {
/* 421 */       a_buffer.append("#");
/*     */     }
/*     */   }
/*     */ 
/*     */   private StringBuffer createCommentBlock(Vector a_vecComments)
/*     */   {
/* 440 */     StringBuffer newComments = new StringBuffer();
/*     */     Iterator it;
/* 441 */     if (a_vecComments != null)
/*     */     {
/* 443 */       for (it = a_vecComments.iterator(); it.hasNext(); )
/*     */       {
/* 445 */         newComments.append("#");
/* 446 */         newComments.append(" ");
/* 447 */         newComments.append(it.next());
/* 448 */         newComments.append(AssetBankConstants.k_sNewLine);
/*     */       }
/*     */     }
/*     */ 
/* 452 */     return newComments;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.updater.service.ApplicationSettingsUtil
 * JD-Core Version:    0.6.0
 */