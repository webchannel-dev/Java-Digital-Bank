/*     */ package com.bright.framework.common.bean;
/*     */ 
/*     */ import com.bright.framework.common.constant.CommonSettings;
/*     */ import com.bright.framework.util.BrightDateFormat;
/*     */ import java.io.Serializable;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ 
/*     */ public class BrightDate
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3139641115960508025L;
/*     */   public static final long MILLISECS_PER_MINUTE = 60000L;
/*     */   public static final long MILLISECS_PER_HOUR = 3600000L;
/*     */   protected static final long MILLISECS_PER_DAY = 86400000L;
/*     */   public static final long EPOCH_UNIX_ERA_DAY = 2440588L;
/*     */   protected Date m_date;
/*     */   protected String m_sFormDate;
/*     */   protected String m_sFormDateFormat;
/*     */   protected String m_sDisplayDateFormat;
/*     */   protected String m_sSqlDateFormat;
/*     */ 
/*     */   public BrightDate()
/*     */   {
/*  90 */     this.m_sFormDateFormat = CommonSettings.getFormDateFormat();
/*  91 */     this.m_sDisplayDateFormat = CommonSettings.getDisplayDateFormat();
/*  92 */     this.m_sSqlDateFormat = CommonSettings.getSqlDateFormat();
/*     */ 
/*  94 */     setDate(null);
/*     */   }
/*     */ 
/*     */   public BrightDate(Date a_date)
/*     */   {
/* 102 */     this();
/*     */ 
/* 105 */     setDate(a_date);
/*     */   }
/*     */ 
/*     */   public BrightDate(BrightDate a_brightDate)
/*     */   {
/* 113 */     this();
/*     */ 
/* 116 */     if ((a_brightDate != null) && (a_brightDate.getDate() != null))
/*     */     {
/* 118 */       setDate(new Date(a_brightDate.getDate().getTime()));
/*     */     }
/*     */   }
/*     */ 
/*     */   public BrightDate(String a_sFormData)
/*     */   {
/* 127 */     setFormDate(a_sFormData);
/* 128 */     processFormData();
/*     */   }
/*     */ 
/*     */   public boolean processFormData()
/*     */   {
/* 146 */     boolean bIsValid = true;
/* 147 */     if ((this.m_sFormDate != null) && (this.m_sFormDate.length() > 0))
/*     */     {
/* 149 */       BrightDateFormat format = new BrightDateFormat(this.m_sFormDateFormat);
/* 150 */       format.setValidate4YearDate(true);
/*     */       try
/*     */       {
/* 153 */         this.m_date = format.parse(this.m_sFormDate);
/*     */       }
/*     */       catch (ParseException e)
/*     */       {
/* 157 */         bIsValid = false;
/*     */       }
/*     */     }
/* 160 */     return bIsValid;
/*     */   }
/*     */ 
/*     */   public boolean getIsFormDateEntered()
/*     */   {
/* 175 */     boolean bPopulated = (this.m_sFormDate != null) && (this.m_sFormDate.trim().length() > 0);
/* 176 */     return bPopulated;
/*     */   }
/*     */ 
/*     */   public boolean getIsInPast()
/*     */   {
/* 191 */     Calendar cal = Calendar.getInstance();
/*     */ 
/* 193 */     return (this.m_date != null) && (this.m_date.compareTo(cal.getTime()) < 0);
/*     */   }
/*     */ 
/*     */   public boolean getIsDayInPast()
/*     */   {
/* 208 */     Calendar calNow = Calendar.getInstance();
/* 209 */     Calendar calToday = new GregorianCalendar(calNow.get(1), calNow.get(2), calNow.get(5));
/*     */ 
/* 211 */     Date today = calToday.getTime();
/*     */ 
/* 213 */     return (this.m_date != null) && (this.m_date.compareTo(today) < 0);
/*     */   }
/*     */ 
/*     */   protected void generateFormData()
/*     */   {
/* 228 */     if (this.m_date != null)
/*     */     {
/* 230 */       this.m_sFormDate = new SimpleDateFormat(this.m_sFormDateFormat).format(this.m_date);
/*     */     }
/*     */     else
/*     */     {
/* 234 */       this.m_sFormDate = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getSqlDate()
/*     */   {
/* 251 */     String sSqlDate = null;
/* 252 */     if (this.m_date != null)
/*     */     {
/* 254 */       sSqlDate = new SimpleDateFormat(this.m_sSqlDateFormat).format(this.m_date);
/*     */     }
/* 256 */     return sSqlDate;
/*     */   }
/*     */ 
/*     */   public String getDisplayDate()
/*     */   {
/* 271 */     String sDate = null;
/* 272 */     if (this.m_date != null)
/*     */     {
/* 274 */       sDate = new SimpleDateFormat(this.m_sDisplayDateFormat).format(this.m_date);
/*     */     }
/* 276 */     return sDate;
/*     */   }
/*     */ 
/*     */   public BrightDateTime getBrightDateTime()
/*     */   {
/* 291 */     BrightDateTime date = new BrightDateTime();
/* 292 */     date.setDate(this.m_date);
/*     */ 
/* 294 */     return date;
/*     */   }
/*     */ 
/*     */   public static BrightDate today()
/*     */   {
/* 312 */     Calendar calNow = Calendar.getInstance();
/* 313 */     Calendar calToday = new GregorianCalendar(calNow.get(1), calNow.get(2), calNow.get(5));
/*     */ 
/* 315 */     BrightDate today = new BrightDate(calToday.getTime());
/*     */ 
/* 317 */     return today;
/*     */   }
/*     */ 
/*     */   public static BrightDate tomorrow()
/*     */   {
/* 333 */     return todayPlusOffsetDays(1);
/*     */   }
/*     */ 
/*     */   public static BrightDate yesterday()
/*     */   {
/* 344 */     return todayPlusOffsetDays(-1);
/*     */   }
/*     */ 
/*     */   public static BrightDate todayPlusOffsetDays(int a_iDays)
/*     */   {
/* 362 */     Calendar calNow = Calendar.getInstance();
/* 363 */     Calendar cal = new GregorianCalendar(calNow.get(1), calNow.get(2), calNow.get(5));
/* 364 */     cal.add(5, a_iDays);
/*     */ 
/* 366 */     BrightDate day = new BrightDate(cal.getTime());
/*     */ 
/* 368 */     return day;
/*     */   }
/*     */ 
/*     */   public static boolean validateFormat(String a_sValue)
/*     */   {
/* 380 */     BrightDate bd = new BrightDate();
/* 381 */     bd.setFormDate(a_sValue);
/* 382 */     boolean bResult = true;
/* 383 */     if (bd.getIsFormDateEntered())
/*     */     {
/* 385 */       bResult = bd.processFormData();
/*     */     }
/* 387 */     return bResult;
/*     */   }
/*     */ 
/*     */   public long getUnixDay()
/*     */   {
/* 398 */     Calendar cal = Calendar.getInstance();
/*     */ 
/* 400 */     long offset = cal.get(15) + cal.get(16);
/* 401 */    // long day = ()Math.floor((getDate().getTime() + offset) / 86400000.0D);
        long day = (long)Math.floor((getDate().getTime() + offset) / 86400000.0D);
/* 402 */     return day;
/*     */   }
/*     */ 
/*     */   public long getJulianDay()
/*     */   {
/* 411 */     return getUnixDay() + 2440588L;
/*     */   }
/*     */ 
/*     */   public Date getDate()
/*     */   {
/* 433 */     if (this.m_date == null)
/*     */     {
/* 435 */       processFormData();
/*     */     }
/*     */ 
/* 438 */     return this.m_date;
/*     */   }
/*     */ 
/*     */   public void setDate(Date a_sDate)
/*     */   {
/* 448 */     this.m_date = a_sDate;
/* 449 */     generateFormData();
/*     */   }
/*     */ 
/*     */   public String getFormDate()
/*     */   {
/* 467 */     if (this.m_sFormDate == null)
/*     */     {
/* 469 */       generateFormData();
/*     */     }
/*     */ 
/* 472 */     return this.m_sFormDate;
/*     */   }
/*     */ 
/*     */   public void setFormDate(String a_sViewDate)
/*     */   {
/* 481 */     this.m_sFormDate = a_sViewDate;
/*     */   }
/*     */ 
/*     */   public String getFormDateFormat()
/*     */   {
/* 499 */     return this.m_sFormDateFormat;
/*     */   }
/*     */ 
/*     */   public void setFormDateFormat(String a_sFormat)
/*     */   {
/* 508 */     this.m_sFormDateFormat = a_sFormat;
/*     */   }
/*     */ 
/*     */   public String getDisplayDateFormat()
/*     */   {
/* 525 */     return this.m_sDisplayDateFormat;
/*     */   }
/*     */ 
/*     */   public void setDisplayDateFormat(String a_sFormat)
/*     */   {
/* 534 */     this.m_sDisplayDateFormat = a_sFormat;
/*     */   }
/*     */ 
/*     */   public String getSqlDateFormat()
/*     */   {
/* 551 */     return this.m_sSqlDateFormat;
/*     */   }
/*     */ 
/*     */   public void setSqlDateFormat(String a_sFormat)
/*     */   {
/* 560 */     this.m_sSqlDateFormat = a_sFormat;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 568 */     int PRIME = 31;
/* 569 */     int result = 1;
/* 570 */     result = 31 * result + (this.m_date == null ? 0 : this.m_date.hashCode());
/* 571 */     return result;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 579 */     if (this == obj)
/* 580 */       return true;
/* 581 */     if (obj == null)
/* 582 */       return false;
/* 583 */     if (getClass() != obj.getClass())
/* 584 */       return false;
/* 585 */     BrightDate other = (BrightDate)obj;
/* 586 */     if (this.m_date == null)
/*     */     {
/* 588 */       if (other.m_date != null)
/* 589 */         return false;
/*     */     }
/* 591 */     else if (!this.m_date.equals(other.m_date))
/* 592 */       return false;
/* 593 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.bean.BrightDate
 * JD-Core Version:    0.6.0
 */