/*     */ package com.bright.assetbank.usage.bean;
/*     */ 
/*     */ import com.bright.framework.image.util.ImageUtil;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.bean.TranslatableWithLanguage;
/*     */ import com.bright.framework.language.bean.Translation;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class UsageTypeFormat extends BaseUsageTypeFormat
/*     */   implements TranslatableWithLanguage
/*     */ {
/*     */   public static final String k_sPreserveFormatListDelimiter = ";";
/*     */   private long m_lFormatId;
/*     */   private int m_iWidth;
/*     */   private int m_iHeight;
/*     */   private long m_lUsageTypeId;
/*     */   private boolean m_bScaleUp;
/*     */   private int m_iDensity;
/*     */   private int m_iJpegQuality;
/*     */   private String m_sPreserveFormatList;
/*     */   private boolean m_bApplyStrip;
/*     */   private boolean m_bOmitIfLowerRes;
/*     */   private long m_lColorSpace;
/*     */   private boolean m_bWatermark;
/*     */   private Vector m_vTranslations;
/*     */   private boolean m_bCropToFit;
/*     */   private boolean m_allowMasking;
/*     */   private long m_presetMaskId;
/*     */   private long m_presetMaskColourId;
/*     */ 
/*     */   public UsageTypeFormat()
/*     */   {
/*  50 */     this.m_lFormatId = 0L;
/*  51 */     this.m_iWidth = 0;
/*  52 */     this.m_iHeight = 0;
/*  53 */     this.m_lUsageTypeId = 0L;
/*  54 */     this.m_bScaleUp = false;
/*  55 */     this.m_iDensity = 0;
/*  56 */     this.m_iJpegQuality = 0;
/*  57 */     this.m_sPreserveFormatList = null;
/*  58 */     this.m_bApplyStrip = false;
/*  59 */     this.m_bOmitIfLowerRes = false;
/*  60 */     this.m_lColorSpace = 0L;
/*  61 */     this.m_bWatermark = false;
/*     */ 
/*  64 */     this.m_vTranslations = null;
/*     */ 
/*  66 */     this.m_bCropToFit = false;
/*     */ 
/*  68 */     this.m_allowMasking = false;
/*     */   }
/*     */ 
/*     */   public static UsageTypeFormat createDefault()
/*     */   {
/*  89 */     UsageTypeFormat utf = new UsageTypeFormat();
/*  90 */     utf.setAllowMasking(true);
/*  91 */     return utf;
/*     */   }
/*     */ 
/*     */   public static boolean extensionInPreserveList(String a_sFormatExt, String a_sPreserveFormatList)
/*     */   {
/* 100 */     String[] aStringArray = StringUtil.convertToArray(a_sPreserveFormatList, ";");
/* 101 */     return StringUtil.arrayContains(aStringArray, a_sFormatExt);
/*     */   }
/*     */ 
/*     */   public boolean preserveExtension(String a_sFormatExt)
/*     */   {
/* 109 */     return extensionInPreserveList(a_sFormatExt, this.m_sPreserveFormatList);
/*     */   }
/*     */ 
/*     */   public boolean getNonMaskingOptionsAllowMasking()
/*     */   {
/* 117 */     return !getCropToFit();
/*     */   }
/*     */ 
/*     */   public void setFormatId(long a_lFormatId)
/*     */   {
/* 122 */     this.m_lFormatId = a_lFormatId;
/*     */   }
/*     */ 
/*     */   public long getFormatId()
/*     */   {
/* 127 */     return this.m_lFormatId;
/*     */   }
/*     */ 
/*     */   public void setWidth(int a_iWidth)
/*     */   {
/* 132 */     this.m_iWidth = a_iWidth;
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/* 137 */     return this.m_iWidth;
/*     */   }
/*     */ 
/*     */   public void setUsageTypeId(long a_lUsageTypeId)
/*     */   {
/* 142 */     this.m_lUsageTypeId = a_lUsageTypeId;
/*     */   }
/*     */ 
/*     */   public long getUsageTypeId()
/*     */   {
/* 147 */     return this.m_lUsageTypeId;
/*     */   }
/*     */ 
/*     */   public int getHeight() {
/* 151 */     return this.m_iHeight;
/*     */   }
/*     */ 
/*     */   public void setHeight(int a_iHeight) {
/* 155 */     this.m_iHeight = a_iHeight;
/*     */   }
/*     */ 
/*     */   public boolean getScaleUp() {
/* 159 */     return this.m_bScaleUp;
/*     */   }
/*     */ 
/*     */   public void setScaleUp(boolean a_bScaleUp) {
/* 163 */     this.m_bScaleUp = a_bScaleUp;
/*     */   }
/*     */ 
/*     */   public int getDensity()
/*     */   {
/* 168 */     return this.m_iDensity;
/*     */   }
/*     */ 
/*     */   public void setDensity(int a_iDensity) {
/* 172 */     this.m_iDensity = a_iDensity;
/*     */   }
/*     */ 
/*     */   public int getJpegQuality() {
/* 176 */     return this.m_iJpegQuality;
/*     */   }
/*     */ 
/*     */   public void setJpegQuality(int a_iJpegQuality) {
/* 180 */     this.m_iJpegQuality = a_iJpegQuality;
/*     */   }
/*     */ 
/*     */   public String getPreserveFormatList()
/*     */   {
/* 185 */     return this.m_sPreserveFormatList;
/*     */   }
/*     */ 
/*     */   public void setPreserveFormatList(String a_sPreserveFormatList)
/*     */   {
/* 190 */     this.m_sPreserveFormatList = a_sPreserveFormatList;
/*     */   }
/*     */ 
/*     */   public boolean getApplyStrip()
/*     */   {
/* 196 */     return this.m_bApplyStrip;
/*     */   }
/*     */ 
/*     */   public void setApplyStrip(boolean a_bApplyStrip) {
/* 200 */     this.m_bApplyStrip = a_bApplyStrip;
/*     */   }
/*     */ 
/*     */   public boolean getOmitIfLowerRes()
/*     */   {
/* 205 */     return this.m_bOmitIfLowerRes;
/*     */   }
/*     */ 
/*     */   public void setOmitIfLowerRes(boolean a_bOmitIfLowerRes) {
/* 209 */     this.m_bOmitIfLowerRes = a_bOmitIfLowerRes;
/*     */   }
/*     */ 
/*     */   public void setWatermark(boolean a_bWatermark)
/*     */   {
/* 214 */     this.m_bWatermark = a_bWatermark;
/*     */   }
/*     */ 
/*     */   public boolean getWatermark()
/*     */   {
/* 219 */     return this.m_bWatermark;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 224 */     if ((this.m_language != null) && (!this.m_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */     {
/* 226 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/* 227 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getDescription())))
/*     */       {
/* 229 */         return translation.getDescription();
/*     */       }
/*     */     }
/* 232 */     return super.getDescription();
/*     */   }
/*     */ 
/*     */   public Vector getTranslations()
/*     */   {
/* 237 */     if (this.m_vTranslations == null)
/*     */     {
/* 239 */       this.m_vTranslations = new Vector();
/*     */     }
/* 241 */     return this.m_vTranslations;
/*     */   }
/*     */ 
/*     */   public void setTranslations(Vector a_vTranslations)
/*     */   {
/* 246 */     this.m_vTranslations = a_vTranslations;
/*     */   }
/*     */ 
/*     */   public Translation createTranslation(Language a_language)
/*     */   {
/* 251 */     return new Translation(a_language);
/*     */   }
/*     */ 
/*     */   public void setCropToFit(boolean a_bCropToFit)
/*     */   {
/* 268 */     this.m_bCropToFit = a_bCropToFit;
/*     */   }
/*     */ 
/*     */   public boolean getCropToFit()
/*     */   {
/* 273 */     return this.m_bCropToFit;
/*     */   }
/*     */ 
/*     */   public boolean getSupportsMultiLayers()
/*     */   {
/* 278 */     return ImageUtil.supportsMultiLayers(this.m_lFormatId);
/*     */   }
/*     */ 
/*     */   public void setColorSpace(long a_lColorSpace)
/*     */   {
/* 283 */     this.m_lColorSpace = a_lColorSpace;
/*     */   }
/*     */ 
/*     */   public long getColorSpace()
/*     */   {
/* 288 */     return this.m_lColorSpace;
/*     */   }
/*     */ 
/*     */   public boolean getAllowMasking()
/*     */   {
/* 293 */     return this.m_allowMasking;
/*     */   }
/*     */ 
/*     */   public void setAllowMasking(boolean a_allowMasking)
/*     */   {
/* 298 */     this.m_allowMasking = a_allowMasking;
/*     */   }
/*     */ 
/*     */   public long getPresetMaskId()
/*     */   {
/* 303 */     return this.m_presetMaskId;
/*     */   }
/*     */ 
/*     */   public void setPresetMaskId(long a_presetMaskId)
/*     */   {
/* 308 */     this.m_presetMaskId = a_presetMaskId;
/*     */   }
/*     */ 
/*     */   public long getPresetMaskColourId()
/*     */   {
/* 313 */     return this.m_presetMaskColourId;
/*     */   }
/*     */ 
/*     */   public void setPresetMaskColourId(long a_presetMaskColourId)
/*     */   {
/* 318 */     this.m_presetMaskColourId = a_presetMaskColourId;
/*     */   }
/*     */ 
/*     */   public class Translation extends BaseUsageTypeFormat
/*     */     implements com.bright.framework.language.bean.Translation
/*     */   {
/*     */     public Translation(Language a_language)
/*     */     {
/* 262 */       this.m_language = a_language;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.bean.UsageTypeFormat
 * JD-Core Version:    0.6.0
 */