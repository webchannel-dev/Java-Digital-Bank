/*     */ package com.bright.framework.util;
/*     */ 
/*     */ import com.bn2web.common.constant.GlobalSettings;
/*     */ import com.bright.assetbank.synchronise.constant.ExportConstants;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
/*     */ import org.apache.commons.lang.time.DateUtils;
/*     */ 
/*     */ public class DateUtil
/*     */ {
/*  43 */   public static final String k_standardDateFormat = FrameworkSettings.getInstance().getStringSetting("standard-date-format");
/*     */   public static final String k_RFC2822FormatPattern = "EEE, dd MMM yyyy hh:mm:ss 'GMT'";
/*     */   public static final String k_fullTimestampFormatPattern = "dd-MM-yyyy HH:mm:ss.S";
/*     */   public static final String k_agreementFormatPattern = "dd/MM/yyyy HH:mm:ss";
/*     */   public static final String k_yearOnlyFormatPattern = "yyyy";
/*  50 */   private static ThreadLocal<BrightDateFormat> c_standardDateFormat = new ThreadLocal()
/*     */   {
/*     */     protected BrightDateFormat initialValue()
/*     */     {
/*  55 */       BrightDateFormat format = new BrightDateFormat(DateUtil.k_standardDateFormat);
/*  56 */       format.setLenient(false);
/*  57 */       format.setValidate4YearDate(true);
/*  58 */       return format;
/*     */     }
/*  50 */   };
/*     */ 
/*  62 */   private static ThreadLocal<SimpleDateFormat> c_luceneDateFormat = new ThreadLocal()
/*     */   {
/*     */     protected SimpleDateFormat initialValue()
/*     */     {
/*  67 */       return new SimpleDateFormat("yyyyMMddHHmmss");
/*     */     }
/*  62 */   };
/*     */ 
/*  71 */   private static ThreadLocal<SimpleDateFormat> c_luceneWeekInYearDateFormat = new ThreadLocal()
/*     */   {
/*     */     protected SimpleDateFormat initialValue()
/*     */     {
/*  76 */       return new SimpleDateFormat("yyyyw");
/*     */     }
/*  71 */   };
/*     */ 
/*  80 */   private static ThreadLocal<SimpleDateFormat> c_importDateFormat = new ThreadLocal()
/*     */   {
/*     */     protected SimpleDateFormat initialValue()
/*     */     {
/*  85 */       return new SimpleDateFormat(ExportConstants.k_sImportFileDateFormat);
/*     */     }
/*  80 */   };
/*     */ 
/*  89 */   private static ThreadLocal<SimpleDateFormat> c_exportDateFormat = new ThreadLocal()
/*     */   {
/*     */     protected SimpleDateFormat initialValue()
/*     */     {
/*  94 */       return new SimpleDateFormat(ExportConstants.k_sExportFileDateFormat);
/*     */     }
/*  89 */   };
/*     */ 
/*  98 */   private static ThreadLocal<SimpleDateFormat> c_RFC2822DateFormat = new ThreadLocal()
/*     */   {
/*     */     protected SimpleDateFormat initialValue()
/*     */     {
/* 103 */       SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss 'GMT'");
/* 104 */       format.setTimeZone(TimeZone.getTimeZone("GMT"));
/* 105 */       return format;
/*     */     }
/*  98 */   };
/*     */ 
/* 109 */   private static ThreadLocal<SimpleDateFormat> c_fullTimestampDateFormat = new ThreadLocal()
/*     */   {
/*     */     protected SimpleDateFormat initialValue()
/*     */     {
/* 114 */       return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S");
/*     */     }
/* 109 */   };
/*     */ 
/* 118 */   private static ThreadLocal<SimpleDateFormat> c_agreementDateFormat = new ThreadLocal()
/*     */   {
/*     */     protected SimpleDateFormat initialValue()
/*     */     {
/* 123 */       return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/*     */     }
/* 118 */   };
/*     */ 
/* 127 */   private static ThreadLocal<SimpleDateFormat> c_yearOnlyDateFormat = new ThreadLocal()
/*     */   {
/*     */     protected SimpleDateFormat initialValue()
/*     */     {
/* 132 */       return new SimpleDateFormat("yyyy");
/*     */     }
/* 127 */   };
/*     */ 
/*     */   public static BrightDateFormat getStandardDateFormat()
/*     */   {
/* 141 */     return (BrightDateFormat)c_standardDateFormat.get();
/*     */   }
/*     */ 
/*     */   public static SimpleDateFormat getLuceneDateFormat()
/*     */   {
/* 147 */     return (SimpleDateFormat)c_luceneDateFormat.get();
/*     */   }
/*     */ 
/*     */   public static SimpleDateFormat getLuceneWeekInYearDateFormat()
/*     */   {
/* 153 */     return (SimpleDateFormat)c_luceneWeekInYearDateFormat.get();
/*     */   }
/*     */ 
/*     */   public static SimpleDateFormat getImportDateFormat()
/*     */   {
/* 159 */     return (SimpleDateFormat)c_importDateFormat.get();
/*     */   }
/*     */ 
/*     */   public static SimpleDateFormat getExportDateFormat()
/*     */   {
/* 165 */     return (SimpleDateFormat)c_exportDateFormat.get();
/*     */   }
/*     */ 
/*     */   public static SimpleDateFormat getRFC2822DateFormat()
/*     */   {
/* 171 */     return (SimpleDateFormat)c_RFC2822DateFormat.get();
/*     */   }
/*     */ 
/*     */   public static SimpleDateFormat getFullTimestampDateFormat()
/*     */   {
/* 177 */     return (SimpleDateFormat)c_fullTimestampDateFormat.get();
/*     */   }
/*     */ 
/*     */   public static SimpleDateFormat getAgreementDateFormat()
/*     */   {
/* 183 */     return (SimpleDateFormat)c_agreementDateFormat.get();
/*     */   }
/*     */ 
/*     */   public static SimpleDateFormat getYearOnlyDateFormat()
/*     */   {
/* 189 */     return (SimpleDateFormat)c_yearOnlyDateFormat.get();
/*     */   }
/*     */ 
/*     */   public static Date getBeginningOfDay(Date a_date)
/*     */   {
/* 201 */     if (a_date == null)
/*     */     {
/* 203 */       return null;
/*     */     }
/* 205 */     return DateUtils.setHours(DateUtils.setMinutes(DateUtils.setSeconds(DateUtils.setMilliseconds(a_date, 0), 0), 0), 0);
/*     */   }
/*     */ 
/*     */   public static Date getEndOfDay(Date a_date)
/*     */   {
/* 216 */     if (a_date == null)
/*     */     {
/* 218 */       return null;
/*     */     }
/* 220 */     return getEndOf(a_date, 5);
/*     */   }
/*     */ 
/*     */   public static Date getEndOfYear(Date a_date)
/*     */   {
/* 225 */     if (a_date == null)
/*     */     {
/* 227 */       return null;
/*     */     }
/* 229 */     return getEndOf(a_date, 1);
/*     */   }
/*     */ 
/*     */   public static Date getEndOf(Date a_date, int a_calendarField)
/*     */   {
/* 234 */     if (a_date == null)
/*     */     {
/* 236 */       return null;
/*     */     }
/*     */ 
/* 239 */     Calendar cal = Calendar.getInstance();
/* 240 */     cal.setTime(a_date);
/* 241 */     cal.add(a_calendarField, 1);
/* 242 */     cal = DateUtils.truncate(cal, a_calendarField);
/* 243 */     cal.add(14, -1);
/* 244 */     return cal.getTime();
/*     */   }
/*     */ 
/*     */   public static long daysBetweenDates(Date a_date1, Date a_date2)
/*     */   {
/* 249 */     long days = (a_date1.getTime() - a_date2.getTime()) / 86400000L;
/*     */ 
/* 251 */     return days;
/*     */   }
/*     */ 
/*     */   public static boolean datesEquals(Date dateA, Date dateB)
/*     */   {
/* 257 */     int[] fields = { 2, 5, 1 };
/* 258 */     Calendar calendar = Calendar.getInstance();
/*     */ 
/* 261 */     if ((dateA == null) && (dateB == null)) {
/* 262 */       return true;
/*     */     }
/*     */ 
/* 265 */     if ((dateA == null) || (dateB == null)) {
/* 266 */       return false;
/*     */     }
/*     */ 
/* 269 */     boolean b = true;
/* 270 */     int[] values = new int[fields.length];
/*     */ 
/* 272 */     calendar.setTime(dateA);
/* 273 */     for (int i = 0; i < fields.length; i++) {
/* 274 */       values[i] = calendar.get(fields[i]);
/*     */     }
/* 276 */     calendar.setTime(dateB);
/* 277 */     for (int i = 0; i < fields.length; i++) {
/* 278 */       int x = calendar.get(fields[i]);
/* 279 */       if (values[i] != x) {
/* 280 */         b = false;
/* 281 */         break;
/*     */       }
/*     */     }
/* 284 */     return b;
/*     */   }
/*     */ 
/*     */   public static boolean isCorrectFullNumericalDateFormat(DateFormat a_format, String a_sDate)
/*     */   {
/* 298 */     String sTestDate = a_format.format(new Date(0L));
/* 299 */     boolean bWasLenient = a_format.isLenient();
/*     */ 
/* 301 */     a_format.setLenient(false);
/*     */ 
/* 303 */     if ((a_sDate == null) || (sTestDate.length() > a_sDate.length() + 4))
/*     */     {
/* 305 */       return false;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 310 */       Date date = a_format.parse(a_sDate);
/*     */ 
/* 312 */       Calendar cal = Calendar.getInstance();
/* 313 */       cal.setTime(date);
/*     */ 
/* 315 */       String sYear = String.valueOf(cal.get(1));
/* 316 */       String sMonth = String.valueOf(cal.get(2) + 1);
/* 317 */       String sDay = String.valueOf(cal.get(5));
/*     */ 
/* 319 */       String[] asParts = a_sDate.split("[^0-9]+");
/*     */       int i;
/* 321 */       if (asParts.length != 3)
/*     */       {
/* 323 */         i = 0; 
                    //jsr 113;
/*     */       }
/*     */       int j;
/* 326 */       for (i = 0; i < asParts.length; i++)
/*     */       {
/* 328 */         asParts[i] = asParts[i].replaceAll("\\A0+", "");
/*     */ 
/* 330 */         if ((sYear.equals(asParts[i])) || (sMonth.equals(asParts[i])) || (sDay.equals(asParts[i])))
/*     */           continue;
/* 332 */         j = 0;
                    //jsr 37;
/*     */       }
/*     */ 
/* 336 */       i = 1;
/*     */     }
/*     */     catch (ParseException e)
/*     */     {
/*     */     }
/*     */     finally
/*     */     {
/*     */       int i;
/* 344 */       a_format.setLenient(bWasLenient);
/*     */     }
/* 346 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.DateUtil
 * JD-Core Version:    0.6.0
 */