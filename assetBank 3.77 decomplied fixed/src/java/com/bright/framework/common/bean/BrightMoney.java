/*     */ package com.bright.framework.common.bean;
/*     */ 
/*     */ import com.bright.framework.common.constant.CommonSettings;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigDecimal;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.Currency;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class BrightMoney
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4978885228295233967L;
/*  57 */   private Locale m_locale = null;
/*  58 */   private NumberFormat m_nfDisplay = null;
/*  59 */   private NumberFormat m_nfForm = null;
/*  60 */   private Currency m_curr = null;
/*     */   private long m_lAmount;
/*     */   private String m_sFormAmount;
/*     */   private String m_sLanguage;
/*     */   private String m_sCountry;
/*     */ 
/*     */   public BrightMoney()
/*     */   {
/*  68 */     this.m_sCountry = CommonSettings.getCountry();
/*  69 */     this.m_sLanguage = CommonSettings.getLanguage();
/*  70 */     initializeCurrency();
/*     */   }
/*     */ 
/*     */   public BrightMoney(long a_lAmount)
/*     */   {
/*  79 */     this.m_sCountry = CommonSettings.getCountry();
/*  80 */     this.m_sLanguage = CommonSettings.getLanguage();
/*  81 */     initializeCurrency();
/*     */ 
/*  84 */     setAmount(a_lAmount);
/*     */   }
/*     */ 
/*     */   private void initializeCurrency()
/*     */   {
/*  96 */     this.m_locale = new Locale(this.m_sLanguage, this.m_sCountry);
/*  97 */     this.m_nfDisplay = NumberFormat.getCurrencyInstance(this.m_locale);
/*  98 */     this.m_nfForm = NumberFormat.getNumberInstance(this.m_locale);
/*  99 */     this.m_curr = this.m_nfDisplay.getCurrency();
/*     */   }
/*     */ 
/*     */   public boolean processFormData()
/*     */   {
/* 112 */     boolean bIsValid = true;
/* 113 */     this.m_lAmount = 0L;
/*     */ 
/* 116 */     if (getIsFormAmountEntered())
/*     */     {
/*     */       try
/*     */       {
/* 120 */         Number num = this.m_nfForm.parse(this.m_sFormAmount);
/* 121 */         this.m_lAmount = convertMajorToMinorUnits(num.doubleValue());
/*     */       }
/*     */       catch (ParseException e)
/*     */       {
/* 125 */         bIsValid = false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 130 */     return bIsValid;
/*     */   }
/*     */ 
/*     */   public boolean getIsFormAmountEntered()
/*     */   {
/* 141 */     return (this.m_sFormAmount != null) && (this.m_sFormAmount.trim().length() > 0);
/*     */   }
/*     */ 
/*     */   protected void generateFormData()
/*     */   {
/* 153 */     double dAmountMajorUnits = convertMinorToMajorUnits(this.m_lAmount);
/*     */ 
/* 156 */     this.m_sFormAmount = this.m_nfForm.format(dAmountMajorUnits);
/*     */   }
/*     */ 
/*     */   public String getDisplayAmount()
/*     */   {
/* 168 */     String sHTMLValue = StringUtil.encodeString(getDisplayAmountTextFormat());
/* 169 */     return sHTMLValue;
/*     */   }
/*     */ 
/*     */   public String getDisplayAmountTextFormat()
/*     */   {
/* 180 */     double dAmountMajorUnits = convertMinorToMajorUnits(this.m_lAmount);
/*     */ 
/* 183 */     String sValue = this.m_nfDisplay.format(dAmountMajorUnits);
/* 184 */     return sValue;
/*     */   }
/*     */ 
/*     */   public String getDisplayAmountWithCodePrefix()
/*     */   {
/* 196 */     String sValue = getCurrencyCode() + " " + getFormAmount();
/* 197 */     return sValue;
/*     */   }
/*     */ 
/*     */   public String getFormAmountWithoutSeperator()
/*     */   {
/* 208 */     if (getFormAmount() != null)
/*     */     {
/* 210 */       return getFormAmount().replaceAll(",", "");
/*     */     }
/* 212 */     return null;
/*     */   }
/*     */ 
/*     */   public String getCurrencySymbol()
/*     */   {
/* 218 */     return StringUtil.encodeString(getCurrencySymbolTextFormat());
/*     */   }
/*     */ 
/*     */   public String getCurrencySymbolTextFormat()
/*     */   {
/* 224 */     return this.m_curr.getSymbol();
/*     */   }
/*     */ 
/*     */   public String getCurrencyCode()
/*     */   {
/* 234 */     return this.m_curr.getCurrencyCode();
/*     */   }
/*     */ 
/*     */   private double convertMinorToMajorUnits(long a_lAmount)
/*     */   {
/* 243 */     BigDecimal bdMultiplier = new BigDecimal(Math.pow(10.0D, this.m_curr.getDefaultFractionDigits()));
/* 244 */     BigDecimal bdAmountMinorUnits = new BigDecimal(a_lAmount);
/* 245 */     BigDecimal bdAmountMajorUnits = bdAmountMinorUnits.divide(bdMultiplier, this.m_curr.getDefaultFractionDigits(), 7);
/* 246 */     return bdAmountMajorUnits.doubleValue();
/*     */   }
/*     */ 
/*     */   private long convertMajorToMinorUnits(double a_dAmount)
/*     */   {
/* 252 */     BigDecimal bdMultiplier = new BigDecimal(Math.pow(10.0D, this.m_curr.getDefaultFractionDigits()));
/* 253 */     BigDecimal bdAmountMajorUnits = new BigDecimal(a_dAmount);
/* 254 */     BigDecimal bdAmountMinorUnits = bdAmountMajorUnits.multiply(bdMultiplier);
/* 255 */     bdAmountMinorUnits = bdAmountMinorUnits.setScale(0, 5);
/* 256 */     long lAmount = bdAmountMinorUnits.longValue();
/* 257 */     return lAmount;
/*     */   }
/*     */ 
/*     */   public long getAmount()
/*     */   {
/* 277 */     return this.m_lAmount;
/*     */   }
/*     */ 
/*     */   public void setAmount(long a_sAmount)
/*     */   {
/* 287 */     this.m_lAmount = a_sAmount;
/* 288 */     generateFormData();
/*     */   }
/*     */ 
/*     */   public String getFormAmount()
/*     */   {
/* 305 */     return this.m_sFormAmount;
/*     */   }
/*     */ 
/*     */   public void setFormAmount(String a_sFormAmount)
/*     */   {
/* 314 */     this.m_sFormAmount = a_sFormAmount;
/*     */   }
/*     */ 
/*     */   public String getLanguage()
/*     */   {
/* 333 */     return this.m_sLanguage;
/*     */   }
/*     */ 
/*     */   public void setLanguage(String a_sLanguage)
/*     */   {
/* 342 */     this.m_sLanguage = a_sLanguage;
/*     */   }
/*     */ 
/*     */   public String getCountry()
/*     */   {
/* 359 */     return this.m_sCountry;
/*     */   }
/*     */ 
/*     */   public void setCountry(String a_sCountry)
/*     */   {
/* 368 */     this.m_sCountry = a_sCountry;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.bean.BrightMoney
 * JD-Core Version:    0.6.0
 */