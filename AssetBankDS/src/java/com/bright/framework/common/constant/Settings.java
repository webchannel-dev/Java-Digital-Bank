/*     */ package com.bright.framework.common.constant;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bn2web.common.util.ReloadablePropertyResourceBundle;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class Settings
/*     */ {
/*  35 */   protected ResourceBundle m_rbSettingsBundle = null;
/*     */ 
/*     */   public Settings(String a_sSettingsFilename, String a_sFallbackSettingsFilename)
/*     */   {
/*     */     try
/*     */     {
/*  42 */       this.m_rbSettingsBundle = ReloadablePropertyResourceBundle.getResourceBundle(a_sSettingsFilename);
/*     */     }
/*     */     catch (MissingResourceException e)
/*     */     {
/*  46 */       this.m_rbSettingsBundle = ReloadablePropertyResourceBundle.getResourceBundle(a_sFallbackSettingsFilename);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Settings(String a_sSettingsFilename)
/*     */   {
/*  53 */     this.m_rbSettingsBundle = ReloadablePropertyResourceBundle.getResourceBundle(a_sSettingsFilename);
/*     */   }
/*     */ 
/*     */   public int getIntSetting(String a_sName)
/*     */   {
/*  59 */     String sValue = getStringSetting(a_sName);
/*  60 */     int iValue = -1;
/*     */ 
/*  62 */     if (StringUtil.stringIsPopulated(sValue))
/*     */     {
/*     */       try
/*     */       {
/*  66 */         iValue = Integer.parseInt(sValue);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */     }
/*     */ 
/*  73 */     return iValue;
/*     */   }
/*     */ 
/*     */   public long getLongSetting(String a_sName)
/*     */   {
/*  79 */     String sValue = getStringSetting(a_sName);
/*  80 */     long lValue = -1L;
/*     */ 
/*  82 */     if (StringUtil.stringIsPopulated(sValue))
/*     */     {
/*     */       try
/*     */       {
/*  86 */         lValue = Long.parseLong(sValue);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */     }
/*     */ 
/*  93 */     return lValue;
/*     */   }
/*     */ 
/*     */   public boolean getBooleanSetting(String a_sName)
/*     */   {
/*  99 */     String sValue = getStringSetting(a_sName);
/* 100 */     boolean bValue = false;
/*     */ 
/* 102 */     if (StringUtil.stringIsPopulated(sValue))
/*     */     {
/*     */       try
/*     */       {
/* 106 */         bValue = Boolean.parseBoolean(sValue);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */     }
/*     */ 
/* 113 */     return bValue;
/*     */   }
/*     */ 
/*     */   public String getStringSetting(String a_sName)
/*     */   {
/* 128 */     String sValue = "";
/*     */ 
/* 130 */     if (this.m_rbSettingsBundle != null)
/*     */     {
/*     */       try
/*     */       {
/* 134 */         sValue = this.m_rbSettingsBundle.getString(a_sName);
/*     */       }
/*     */       catch (MissingResourceException mre)
/*     */       {
/* 138 */         if (GlobalApplication.getInstance().getLogger() != null)
/*     */         {
/* 140 */           GlobalApplication.getInstance().getLogger().error("Settings: there is no property called " + a_sName + " in the application settings file");
/*     */         }
/*     */         else
/*     */         {
/* 144 */           System.out.println("Settings: there is no property called " + a_sName + " in the application settings file");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/* 150 */     else if (GlobalApplication.getInstance().getLogger() != null)
/*     */     {
/* 152 */       GlobalApplication.getInstance().getLogger().error("The Settings class has not been initialised: the ResourceBundle is null.");
/*     */     }
/*     */     else
/*     */     {
/* 156 */       System.out.println("The Settings class has not been initialised: the ResourceBundle is null.");
/*     */     }
/*     */ 
/* 159 */     return sValue;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.constant.Settings
 * JD-Core Version:    0.6.0
 */