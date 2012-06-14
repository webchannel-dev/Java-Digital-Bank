/*     */ package com.bright.assetbank.updater.service;
/*     */ 
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public abstract class PropertiesFileUtil
/*     */ {
/*  37 */   protected StringBuffer m_buffer = null;
/*     */ 
/*  39 */   private String m_sFilename = null;
/*     */ 
/*     */   public PropertiesFileUtil(String a_sFilename)
/*     */     throws IOException
/*     */   {
/*  49 */     this.m_sFilename = a_sFilename;
/*  50 */     loadSettings();
/*     */   }
/*     */ 
/*     */   private void loadSettings()
/*     */     throws IOException
/*     */   {
/*  68 */     this.m_buffer = FileUtil.readIntoStringBuffer(this.m_sFilename);
/*     */   }
/*     */ 
/*     */   public void saveSettings()
/*     */     throws IOException
/*     */   {
/*  86 */     BufferedWriter out = new BufferedWriter(new FileWriter(this.m_sFilename));
/*  87 */     out.write(this.m_buffer.toString());
/*  88 */     out.flush();
/*  89 */     out.close();
/*     */   }
/*     */ 
/*     */   protected Matcher getMatcher(String a_sRegex)
/*     */     throws IOException
/*     */   {
/* 109 */     if (this.m_buffer == null)
/*     */     {
/* 111 */       loadSettings();
/*     */     }
/*     */ 
/* 114 */     Pattern pattern = Pattern.compile(a_sRegex, 10);
/* 115 */     return pattern.matcher(this.m_buffer);
/*     */   }
/*     */ 
/*     */   protected static String getRegexSettingStartVal(String a_sSettingName)
/*     */   {
/* 134 */     StringBuffer buf = new StringBuffer();
/* 135 */     buf.append("^");
/* 136 */     buf.append("\\Q");
/* 137 */     buf.append(a_sSettingName);
/* 138 */     buf.append("\\E=");
/*     */ 
/* 140 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   protected static String getRegexSettingEndVal(String a_sSettingName)
/*     */   {
/* 159 */     return getRegexSettingStartVal(a_sSettingName).concat(".*$");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.updater.service.PropertiesFileUtil
 * JD-Core Version:    0.6.0
 */