/*     */ package com.bright.assetbank.synchronise.constant.impl;
/*     */ 
/*     */ import com.bright.assetbank.synchronise.constant.ExternalDataSynchronisationSettings;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ 
/*     */ public class AdlibSynchronisationSettings extends ExternalDataSynchronisationSettings
/*     */ {
/*     */   public AdlibSynchronisationSettings(String a_sSettingsFile)
/*     */   {
/*  34 */     super(a_sSettingsFile);
/*     */   }
/*     */ 
/*     */   public String getAdlibXMLUrl()
/*     */   {
/*  39 */     return getStringSetting("adlib-xml-url");
/*     */   }
/*     */ 
/*     */   public boolean getCallbackToAdlib()
/*     */   {
/*  44 */     return getBooleanSetting("callback-to-adlib");
/*     */   }
/*     */ 
/*     */   public String getAdlibCallback()
/*     */   {
/*  49 */     return getStringSetting("adlib-callback-url");
/*     */   }
/*     */ 
/*     */   public String getAdlibDatabaseName()
/*     */   {
/*  54 */     return getStringSetting("adlib-db-name");
/*     */   }
/*     */ 
/*     */   public String getAttributeCreator()
/*     */   {
/*  59 */     return getStringSetting("attributeCreator");
/*     */   }
/*     */ 
/*     */   public String getAttributeFormat()
/*     */   {
/*  64 */     return getStringSetting("attributeFormat");
/*     */   }
/*     */ 
/*     */   public String[] getAdlibReproductionFormats()
/*     */   {
/*  69 */     String sFormats = getStringSetting("reproFormats");
/*  70 */     if (StringUtil.stringIsPopulated(sFormats))
/*     */     {
/*  72 */       return sFormats.split(",");
/*     */     }
/*  74 */     return null;
/*     */   }
/*     */ 
/*     */   public String[] getAdlibReproductionResolutions()
/*     */   {
/*  79 */     String sResolutions = getStringSetting("reproResolutions");
/*  80 */     if (StringUtil.stringIsPopulated(sResolutions))
/*     */     {
/*  82 */       return sResolutions.split(",");
/*     */     }
/*  84 */     return null;
/*     */   }
/*     */ 
/*     */   public String getXMLElementNameReproductionUrl()
/*     */   {
/*  90 */     return getStringSetting("xmlElementReproductionUrl");
/*     */   }
/*     */ 
/*     */   public String getXMLElementNameReproductionType() {
/*  94 */     return getStringSetting("xmlElementReproductionType");
/*     */   }
/*     */ 
/*     */   public String getXMLElementNameReproductionFormat() {
/*  98 */     return getStringSetting("xmlElementReproductionFormat");
/*     */   }
/*     */ 
/*     */   public String getXMLElementNameReproductionCreator() {
/* 102 */     return getStringSetting("xmlElementReproductionCreator");
/*     */   }
/*     */ 
/*     */   public String getXMLElementNameReproduction() {
/* 106 */     return getStringSetting("xmlElementReproduction");
/*     */   }
/*     */ 
/*     */   public String getXMLElementNamePriref() {
/* 110 */     return getStringSetting("xmlElementPriref");
/*     */   }
/*     */ 
/*     */   public String getXMLElementNameAssetBankId() {
/* 114 */     return getStringSetting("xmlElementAssetBankId");
/*     */   }
/*     */ 
/*     */   public String getXMLElementNameAssetBankUrl() {
/* 118 */     return getStringSetting("xmlElementAssetBankUrl");
/*     */   }
/*     */ 
/*     */   public String getXMLElementNameAssetBankRelationships() {
/* 122 */     return getStringSetting("xmlElementAssetBankRelationships");
/*     */   }
/*     */ 
/*     */   public String getAllRecordsXMLUrl()
/*     */   {
/* 127 */     return getStringSetting("adlib-xml-url-all");
/*     */   }
/*     */ 
/*     */   public boolean getSynchroniseRelatedAssets()
/*     */   {
/* 132 */     return getBooleanSetting("synch-related-assets");
/*     */   }
/*     */ 
/*     */   public int getSynchroniseRelatedAssetsLimit()
/*     */   {
/* 137 */     return getIntSetting("synch-related-assets-limit");
/*     */   }
/*     */ 
/*     */   public String getXMLRecordAttributeAction()
/*     */   {
/* 142 */     return getStringSetting("xml-record-attribute-action");
/*     */   }
/*     */ 
/*     */   public String getCallbackActionUpdate()
/*     */   {
/* 147 */     return getStringSetting("callback-action-update");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.constant.impl.AdlibSynchronisationSettings
 * JD-Core Version:    0.6.0
 */