/*     */ package com.bright.framework.common.bean;
/*     */ 
/*     */ import com.bright.framework.common.constant.CommonSettings;
/*     */ import com.bright.framework.util.BrightDateFormat;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.Serializable;
/*     */ import java.sql.Time;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class BrightDateTime extends BrightDate
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4835825629670182103L;
/*     */   private String m_sDisplayDateTimeFormat;
/*     */   private String m_sSqlDateTimeFormat;
/*     */   private String m_sSqlTimeFormat;
/*     */   private String m_sFormTime;
/*     */   private String m_sFormTimeFormat;
/*     */ 
/*     */   public BrightDateTime()
/*     */   {
/*  54 */     this.m_sFormTimeFormat = CommonSettings.getFormTimeFormat();
/*  55 */     this.m_sDisplayDateTimeFormat = CommonSettings.getDisplayDateTimeFormat();
/*  56 */     this.m_sSqlTimeFormat = CommonSettings.getSqlTimeFormat();
/*  57 */     this.m_sSqlDateTimeFormat = (getSqlDateFormat() + " " + getSqlTimeFormat());
/*     */ 
/*  59 */     setDate(null);
/*     */   }
/*     */ 
/*     */   public BrightDateTime(Date a_date)
/*     */   {
/*  68 */     this();
/*     */ 
/*  71 */     setDate(a_date);
/*     */   }
/*     */ 
/*     */   public BrightDateTime(BrightDateTime a_brightDateTime)
/*     */   {
/*  76 */     this();
/*  77 */     if ((a_brightDateTime != null) && (a_brightDateTime.getDate() != null))
/*     */     {
/*  80 */       setDate(new Date(a_brightDateTime.getDate().getTime()));
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getSqlDateTime()
/*     */   {
/*  98 */     String sDateTime = null;
/*  99 */     if (this.m_date != null)
/*     */     {
/* 101 */       sDateTime = new SimpleDateFormat(this.m_sSqlDateTimeFormat).format(this.m_date);
/*     */     }
/* 103 */     return sDateTime;
/*     */   }
/*     */ 
/*     */   public String getSqlTime()
/*     */   {
/* 119 */     String sTime = null;
/* 120 */     if (this.m_date != null)
/*     */     {
/* 122 */       sTime = new SimpleDateFormat(this.m_sSqlTimeFormat).format(this.m_date);
/*     */     }
/* 124 */     return sTime;
/*     */   }
/*     */ 
/*     */   public String getDisplayDateTime()
/*     */   {
/* 140 */     String sDateTime = null;
/*     */ 
/* 142 */     if (getTimeIsZero())
/*     */     {
/* 144 */       sDateTime = super.getDisplayDate();
/*     */     }
/*     */     else
/*     */     {
/* 148 */       sDateTime = new SimpleDateFormat(this.m_sDisplayDateTimeFormat).format(this.m_date);
/*     */     }
/* 150 */     return sDateTime;
/*     */   }
/*     */ 
/*     */   public boolean processFormData()
/*     */   {
/* 168 */     boolean bIsDateValid = super.processFormData();
/*     */ 
/* 170 */     boolean bIsDateTimeValid = true;
/* 171 */     if ((this.m_sFormTime != null) && (this.m_sFormTime.length() > 0))
/*     */     {
/* 174 */       String sFormDateTimeFormat = this.m_sFormDateFormat + " " + this.m_sFormTimeFormat;
/* 175 */       String sFormDateTime = this.m_sFormDate + " " + this.m_sFormTime;
/*     */ 
/* 178 */       BrightDateFormat format = new BrightDateFormat(sFormDateTimeFormat);
/* 179 */       format.setValidate4YearDate(true);
/*     */       try
/*     */       {
/* 182 */         this.m_date = format.parse(sFormDateTime);
/*     */       }
/*     */       catch (ParseException e)
/*     */       {
/* 186 */         bIsDateTimeValid = false;
/*     */       }
/*     */     }
/*     */ 
/* 190 */     return (bIsDateValid) && (bIsDateTimeValid);
/*     */   }
/*     */ 
/*     */   public boolean getIsFormTimeEntered()
/*     */   {
/* 205 */     boolean bPopulated = (this.m_sFormTime != null) && (this.m_sFormTime.trim().length() > 0);
/* 206 */     return bPopulated;
/*     */   }
/*     */ 
/*     */   public boolean getIsFormDateTimeEntered()
/*     */   {
/* 216 */     return (getIsFormDateEntered()) || (getIsFormTimeEntered());
/*     */   }
/*     */ 
/*     */   protected void generateFormData()
/*     */   {
/* 233 */     super.generateFormData();
/*     */ 
/* 235 */     if (!getTimeIsZero())
/*     */     {
/* 237 */       this.m_sFormTime = new SimpleDateFormat(this.m_sFormTimeFormat).format(this.m_date);
/*     */     }
/*     */     else
/*     */     {
/* 241 */       this.m_sFormTime = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setTime(Time a_time)
/*     */   {
/* 259 */     if (a_time != null)
/*     */     {
/* 262 */       Calendar calTime = Calendar.getInstance();
/* 263 */       calTime.setTime(a_time);
/*     */ 
/* 266 */       setTime(calTime.get(11), calTime.get(12), calTime.get(13));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setTime(int a_iHour, int a_iMinute, int a_iSecond)
/*     */   {
/* 291 */     Calendar calDate = Calendar.getInstance();
/*     */ 
/* 293 */     if (this.m_date != null)
/*     */     {
/* 295 */       calDate.setTime(this.m_date);
/*     */ 
/* 297 */       calDate.set(11, a_iHour);
/* 298 */       calDate.set(12, a_iMinute);
/* 299 */       calDate.set(13, a_iSecond);
/*     */ 
/* 302 */       calDate.set(14, 0);
/*     */ 
/* 304 */       this.m_date = calDate.getTime();
/*     */     }
/*     */ 
/* 307 */     generateFormData();
/*     */   }
/*     */ 
/*     */   public void zeroMillis()
/*     */   {
/* 317 */     Calendar calDate = Calendar.getInstance();
/*     */ 
/* 319 */     if (this.m_date != null)
/*     */     {
/* 321 */       calDate.setTime(this.m_date);
/* 322 */       calDate.set(14, 0);
/* 323 */       this.m_date = calDate.getTime();
/*     */     }
/*     */ 
/* 326 */     generateFormData();
/*     */   }
/*     */ 
/*     */   public Date getDatePart()
/*     */   {
/*     */     Date datePart;
/*     */     //Date datePart;
/* 338 */     if (this.m_date == null)
/*     */     {
/* 340 */       datePart = null;
/*     */     }
/*     */     else
/*     */     {
/* 344 */       Calendar date = Calendar.getInstance();
/* 345 */       date.setTime(this.m_date);
/* 346 */       date.set(11, 0);
/* 347 */       date.set(12, 0);
/* 348 */       date.set(13, 0);
/* 349 */       date.set(14, 0);
/*     */ 
/* 351 */       datePart = date.getTime();
/*     */     }
/*     */ 
/* 354 */     return datePart;
/*     */   }
/*     */ 
/*     */   public Date getTimePart()
/*     */   {
/*     */     Date timePart;
/*     */    // Date timePart;
/* 366 */     if (this.m_date == null)
/*     */     {
/* 368 */       timePart = null;
/*     */     }
/*     */     else
/*     */     {
/* 372 */       Calendar date = Calendar.getInstance();
/* 373 */       date.setTime(this.m_date);
/* 374 */       date.set(1, 0);
/* 375 */       date.set(2, 0);
/* 376 */       date.set(5, 0);
/*     */ 
/* 378 */       timePart = date.getTime();
/*     */     }
/*     */ 
/* 381 */     return timePart;
/*     */   }
/*     */ 
/*     */   public boolean getTimeIsZero()
/*     */   {
/* 391 */     if (this.m_date == null)
/*     */     {
/* 393 */       return true;
/*     */     }
/*     */ 
/* 396 */     Calendar date = Calendar.getInstance();
/* 397 */     date.setTime(this.m_date);
/*     */ 
/* 399 */     return (date.get(11) == 0) && (date.get(12) == 0) && (date.get(13) == 0);
/*     */   }
/*     */ 
/*     */   public String getZeroSQLTime()
/*     */   {
/* 410 */     String sTime = null;
/*     */ 
/* 413 */     Calendar calDate = Calendar.getInstance();
/*     */ 
/* 415 */     calDate.setTime(new Date());
/* 416 */     calDate.set(11, 0);
/* 417 */     calDate.set(12, 0);
/* 418 */     calDate.set(13, 0);
/* 419 */     calDate.set(14, 0);
/*     */ 
/* 421 */     Date zeroTime = calDate.getTime();
/* 422 */     sTime = new SimpleDateFormat(this.m_sSqlTimeFormat).format(zeroTime);
/*     */ 
/* 424 */     return sTime;
/*     */   }
/*     */ 
/*     */   public boolean setFormDateTime(String a_sValue)
/*     */   {
/* 437 */     if (a_sValue == null)
/*     */     {
/* 439 */       setDate(null);
/* 440 */       return true;
/*     */     }
/*     */ 
/* 443 */     String sDatePart = "";
/* 444 */     String sTimePart = "";
/*     */ 
/* 447 */     int iLenTimePart = this.m_sFormTimeFormat.length();
/* 448 */     int iLenDatePart = this.m_sFormDateFormat.length();
/*     */ 
/* 451 */     if ((a_sValue.length() != iLenTimePart + iLenDatePart + 1) && (a_sValue.length() != iLenDatePart))
/*     */     {
/* 454 */       return false;
/*     */     }
/*     */ 
/* 457 */     sDatePart = a_sValue.substring(0, iLenDatePart);
/*     */ 
/* 460 */     if (a_sValue.length() == iLenTimePart + iLenDatePart + 1)
/*     */     {
/* 462 */       sTimePart = a_sValue.substring(iLenDatePart + 1);
/*     */     }
/*     */ 
/* 465 */     setFormDate(sDatePart);
/* 466 */     setFormTime(sTimePart);
/*     */ 
/* 468 */     return true;
/*     */   }
/*     */ 
/*     */   public String getFormDateTime()
/*     */   {
/* 474 */     String sFormDateTime = this.m_sFormDate;
/*     */ 
/* 476 */     if (this.m_sFormTime != null)
/*     */     {
/* 478 */       sFormDateTime = sFormDateTime + " " + this.m_sFormTime;
/*     */     }
/* 480 */     return sFormDateTime;
/*     */   }
/*     */ 
/*     */   public static BrightDateTime now()
/*     */   {
/* 493 */     return nowPlusOffsetDays(0);
/*     */   }
/*     */ 
/*     */   public static BrightDateTime thisTimeTomorrow()
/*     */   {
/* 504 */     return nowPlusOffsetDays(1);
/*     */   }
/*     */ 
/*     */   public static BrightDateTime nowPlusOffsetDays(int a_iDays)
/*     */   {
/* 517 */     Calendar cal = Calendar.getInstance();
/* 518 */     cal.add(5, a_iDays);
/*     */ 
/* 520 */     BrightDateTime day = new BrightDateTime(cal.getTime());
/*     */ 
/* 522 */     return day;
/*     */   }
/*     */ 
/*     */   public static BrightDateTime addOffsetDays(BrightDateTime a_dt, int a_iDays)
/*     */   {
/* 535 */     Calendar cal = Calendar.getInstance();
/* 536 */     cal.setTime(a_dt.getDate());
/* 537 */     cal.add(5, a_iDays);
/*     */ 
/* 539 */     BrightDateTime day = new BrightDateTime(cal.getTime());
/*     */ 
/* 541 */     return day;
/*     */   }
/*     */ 
/*     */   public static boolean validateFormat(String a_sValue)
/*     */   {
/* 553 */     BrightDateTime bd = new BrightDateTime();
/* 554 */     boolean bResult = true;
/*     */ 
/* 556 */     if (StringUtil.stringIsPopulated(a_sValue))
/*     */     {
/* 558 */       bResult = bd.setFormDateTime(a_sValue);
/*     */ 
/* 560 */       if (bResult)
/*     */       {
/* 562 */         bResult = bd.processFormData();
/*     */       }
/*     */     }
/* 565 */     return bResult;
/*     */   }
/*     */ 
/*     */   public static String formFormat(Date a_date)
/*     */   {
/* 576 */     if (a_date == null)
/*     */     {
/* 578 */       return null;
/*     */     }
/*     */ 
/* 582 */     return new BrightDateTime(a_date).getDisplayDateTime();
/*     */   }
/*     */ 
/*     */   public String getDisplayDateTimeFormat()
/*     */   {
/* 603 */     return this.m_sDisplayDateTimeFormat;
/*     */   }
/*     */ 
/*     */   public void setDisplayDateTimeFormat(String a_sDisplayDateTimeFormat)
/*     */   {
/* 612 */     this.m_sDisplayDateTimeFormat = a_sDisplayDateTimeFormat;
/*     */   }
/*     */ 
/*     */   public String getSqlDateTimeFormat()
/*     */   {
/* 629 */     return this.m_sSqlDateTimeFormat;
/*     */   }
/*     */ 
/*     */   public void setSqlDateTimeFormat(String a_sSqlDateTimeFormat)
/*     */   {
/* 638 */     this.m_sSqlDateTimeFormat = a_sSqlDateTimeFormat;
/*     */   }
/*     */ 
/*     */   public String getSqlTimeFormat()
/*     */   {
/* 655 */     return this.m_sSqlTimeFormat;
/*     */   }
/*     */ 
/*     */   public void setSqlTimeFormat(String a_sSqlTimeFormat)
/*     */   {
/* 664 */     this.m_sSqlTimeFormat = a_sSqlTimeFormat;
/*     */   }
/*     */ 
/*     */   public String getFormTime()
/*     */   {
/* 681 */     return this.m_sFormTime;
/*     */   }
/*     */ 
/*     */   public void setFormTime(String a_sFormTime)
/*     */   {
/* 690 */     this.m_sFormTime = a_sFormTime;
/*     */   }
/*     */ 
/*     */   public String getFormTimeFormat()
/*     */   {
/* 707 */     return this.m_sFormTimeFormat;
/*     */   }
/*     */ 
/*     */   public void setFormTimeFormat(String a_sFormTimeFormat)
/*     */   {
/* 716 */     this.m_sFormTimeFormat = a_sFormTimeFormat;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.bean.BrightDateTime
 * JD-Core Version:    0.6.0
 */