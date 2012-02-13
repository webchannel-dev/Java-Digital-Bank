/*     */ package com.bright.framework.image.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.cache.FileCache;
/*     */ import com.bright.framework.cache.SizeLimitedObjectCache;
/*     */ import com.bright.framework.cache.service.FileCacheManager;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.image.exception.ImageException;
/*     */ import com.bright.framework.util.CollectionUtil;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.commandline.CommandLineExec;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class ImageMagick
/*     */ {
/*     */   private static final String k_sErrorResponsePrefix = "ERROR : ";
/*     */   public static final String k_sClassName = "ImageMagick";
/*     */   public static final int c_kiUndefinedColorspace = 0;
/*     */   public static final int c_kiRGBColorspace = 1;
/*     */   public static final int c_kiGRAYColorspace = 2;
/*     */   public static final int c_kiTransparentColorspace = 3;
/*     */   public static final int c_kiOHTAColorspace = 4;
/*     */   public static final int c_kiLABColorspace = 5;
/*     */   public static final int c_kiXYZColorspace = 6;
/*     */   public static final int c_kiYCbCrColorspace = 7;
/*     */   public static final int c_kiYCCColorspace = 8;
/*     */   public static final int c_kiYIQColorspace = 9;
/*     */   public static final int c_kiYPbPrColorspace = 10;
/*     */   public static final int c_kiYUVColorspace = 11;
/*     */   public static final int c_kiCMYKColorspace = 12;
/*     */   public static final int c_kisRGBColorspace = 13;
/*     */   public static final int c_kiHSBColorspace = 14;
/*     */   public static final int c_kiHSLColorspace = 15;
/*     */   public static final int c_kiHWBColorspace = 16;
/*     */   public static final int c_kiRec601LumaColorspace = 17;
/*     */   public static final int c_kiRec601YCbCrColorspace = 18;
/*     */   public static final int c_kiRec709LumaColorspace = 19;
/*     */   public static final int c_kiRec709YCbCrColorspace = 20;
/*     */   public static final int c_kiLogColorspace = 21;
/*     */   public static final String c_ksPixelsPerInch = "PixelsPerInch";
/*     */   public static final String c_ksPixelsPerCentimeter = "PixelsPerCentimeter";
/*  86 */   private static String c_ksPath = "";
/*     */   private static final String c_ksDELIMITER = "-@-";
/*  92 */   private static Log m_logger = GlobalApplication.getInstance().getLogger();
/*     */ 
/*  99 */   private static final SizeLimitedObjectCache s_returnValueCache = new SizeLimitedObjectCache(FrameworkSettings.getImageMagickOutputCacheSize());
/*     */ 
/*     */   private static FileCache getFileCache()
/*     */   {
/*     */     try
/*     */     {
/* 106 */       FileCacheManager manager = (FileCacheManager)GlobalApplication.getInstance().getComponentManager().lookup("FileCacheManager");
/* 107 */       return manager.getFileCache(ImageMagick.class.getSimpleName(), FrameworkSettings.getImageMagickFileCacheSize());
/*     */     }
/*     */     catch (ComponentException e)
/*     */     {
/* 111 */       GlobalApplication.getInstance().getLogger().error("ImageMagick.getFileCache() : Coule not get file cache due to ComponentException : " + e.getMessage(), e);
/* 112 */       throw new RuntimeException("ImageMagick.getFileCache() : Coule not get file cache due to ComponentException : " + e.getMessage(), e);
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/* 116 */       GlobalApplication.getInstance().getLogger().error("ImageMagick.getFileCache() : Coule not get file cache due to Bn2Exception : " + e.getMessage(), e);
/* 117 */     throw new RuntimeException("ImageMagick.getFileCache() : Coule not get file cache due to ComponentException : " + e.getMessage(), e);
                }
/*     */   }
/*     */ 
/*     */   public static void setPath(String a_sPath)
/*     */   {
/* 128 */     c_ksPath = a_sPath;
/*     */   }
/*     */ 
/*     */   public static void clearCaches()
/*     */   {
/* 133 */     s_returnValueCache.clear();
/* 134 */     getFileCache().clear();
/*     */   }
/*     */ 
/*     */   public static void convert(ImageMagickOptionList a_options)
/*     */     throws ImageException
/*     */   {
/* 145 */     convert(a_options, false);
/*     */   }
/*     */ 
/*     */   public static void convert(ImageMagickOptionList a_options, boolean a_bSuppressErrors)
/*     */     throws ImageException
/*     */   {
/* 158 */     execute("convert", a_options, a_bSuppressErrors);
/*     */   }
/*     */ 
/*     */   public static void composite(ImageMagickOptionList a_options)
/*     */     throws ImageException
/*     */   {
/* 169 */     execute("composite", a_options);
/*     */   }
/*     */ 
/*     */   public static void mogrify(ImageMagickOptionList a_options)
/*     */     throws ImageException
/*     */   {
/* 180 */     execute("mogrify", a_options);
/*     */   }
/*     */ 
/*     */   public static String identify(ImageMagickOptionList a_options)
/*     */     throws ImageException
/*     */   {
/* 191 */     return execute("identify", a_options);
/*     */   }
/*     */ 
/*     */   public static HashMap getEXIFInfo(String a_sFilename, Collection a_fields, Collection a_fieldNames)
/*     */     throws ImageException
/*     */   {
/* 211 */     if (a_fieldNames == null)
/*     */     {
/* 213 */       a_fieldNames = a_fields;
/*     */     }
/*     */ 
/* 217 */     String[] data = getMetaInfo("EXIF", a_fields, a_sFilename);
/*     */ 
/* 220 */     return buildInfoMap(a_fieldNames, Arrays.asList(data));
/*     */   }
/*     */ 
/*     */   public static HashMap getIPTCInfo(String a_sFilename, Collection a_fieldCodes, Collection a_fieldNames)
/*     */     throws ImageException
/*     */   {
/* 240 */     if (a_fieldNames == null)
/*     */     {
/* 242 */       a_fieldNames = a_fieldCodes;
/*     */     }
/*     */ 
/* 246 */     String[] data = getMetaInfo("IPTC", a_fieldCodes, a_sFilename);
/*     */ 
/* 249 */     return buildInfoMap(a_fieldNames, Arrays.asList(data));
/*     */   }
/*     */ 
/*     */   public static String[] getInfo(Collection a_tags, String a_sFilename)
/*     */     throws ImageException
/*     */   {
/* 268 */     ImageMagickOptionList options = new ImageMagickOptionList();
/*     */ 
/* 270 */     options.addFormat("%" + CollectionUtil.join(a_tags, "-@-%") + "-@-");
/*     */ 
/* 273 */     if ((FileUtil.hasSuffix(a_sFilename, "pdf")) || (FileUtil.hasSuffix(a_sFilename, "ai")))
/*     */     {
/* 275 */       options.addUseCropbox();
/*     */     }
/*     */ 
/* 278 */     options.addInputFilename(a_sFilename);
/*     */ 
/* 281 */     return identify(options).split("-@-");
/*     */   }
/*     */ 
/*     */   public static int parseColorspace(String a_sColorspace)
/*     */   {
/* 287 */     if ((a_sColorspace.startsWith("DirectClass")) || (a_sColorspace.startsWith("PseudoClass")))
/*     */     {
/* 289 */       a_sColorspace = a_sColorspace.substring(11);
/*     */     }
/* 291 */     else if (a_sColorspace.startsWith("SudoClass"))
/*     */     {
/* 293 */       a_sColorspace = a_sColorspace.substring(9);
/*     */     }
/*     */ 
/* 296 */     int iColorspace = -1;
/*     */ 
/* 298 */     if (a_sColorspace.equals("Undefined"))
/*     */     {
/* 300 */       iColorspace = 0;
/*     */     }
/* 302 */     else if (a_sColorspace.equals("RGB"))
/*     */     {
/* 304 */       iColorspace = 1;
/*     */     }
/* 306 */     else if (a_sColorspace.equals("GRAY"))
/*     */     {
/* 308 */       iColorspace = 2;
/*     */     }
/* 310 */     else if (a_sColorspace.equals("Transparent"))
/*     */     {
/* 312 */       iColorspace = 3;
/*     */     }
/* 314 */     else if (a_sColorspace.equals("OHTA"))
/*     */     {
/* 316 */       iColorspace = 4;
/*     */     }
/* 318 */     else if (a_sColorspace.equals("LAB"))
/*     */     {
/* 320 */       iColorspace = 5;
/*     */     }
/* 322 */     else if (a_sColorspace.equals("XYZ"))
/*     */     {
/* 324 */       iColorspace = 6;
/*     */     }
/* 326 */     else if (a_sColorspace.equals("YCbCr"))
/*     */     {
/* 328 */       iColorspace = 7;
/*     */     }
/* 330 */     else if (a_sColorspace.equals("YUV"))
/*     */     {
/* 332 */       iColorspace = 11;
/*     */     }
/* 334 */     else if (a_sColorspace.equals("CMYK"))
/*     */     {
/* 336 */       iColorspace = 12;
/*     */     }
/* 338 */     else if (a_sColorspace.equals("CMYKMatte"))
/*     */     {
/* 340 */       iColorspace = 12;
/*     */     }
/* 342 */     else if (a_sColorspace.equals("sRGB"))
/*     */     {
/* 344 */       iColorspace = 13;
/*     */     }
/* 346 */     else if (a_sColorspace.equals("CYMK"))
/*     */     {
/* 348 */       iColorspace = 12;
/*     */     }
/* 350 */     else if (a_sColorspace.equals("HSBC"))
/*     */     {
/* 352 */       iColorspace = 14;
/*     */     }
/* 354 */     else if (a_sColorspace.equals("HSL"))
/*     */     {
/* 356 */       iColorspace = 15;
/*     */     }
/* 358 */     else if (a_sColorspace.equals("HWB"))
/*     */     {
/* 360 */       iColorspace = 16;
/*     */     }
/* 362 */     else if (a_sColorspace.equals("Rec601Luma"))
/*     */     {
/* 364 */       iColorspace = 17;
/*     */     }
/* 366 */     else if (a_sColorspace.equals("Rec601YCbCr"))
/*     */     {
/* 368 */       iColorspace = 18;
/*     */     }
/* 370 */     else if (a_sColorspace.equals("Rec709Luma"))
/*     */     {
/* 372 */       iColorspace = 19;
/*     */     }
/* 374 */     else if (a_sColorspace.equals("ec709YCbCr"))
/*     */     {
/* 376 */       iColorspace = 20;
/*     */     }
/* 378 */     else if (a_sColorspace.equals("Log"))
/*     */     {
/* 380 */       iColorspace = 21;
/*     */     }
/*     */ 
/* 383 */     return iColorspace;
/*     */   }
/*     */ 
/*     */   private static String execute(String a_sName, ImageMagickOptionList a_options)
/*     */     throws ImageException
/*     */   {
/* 406 */     return execute(a_sName, a_options, false);
/*     */   }
/*     */ 
/*     */   private static String execute(String a_sName, ImageMagickOptionList a_options, boolean a_bSuppressErrors)
/*     */     throws ImageException
/*     */   {
/* 433 */     StringBuffer sbOut = new StringBuffer();
/*     */ 
/* 436 */     if (c_ksPath != null)
/*     */     {
/* 438 */       a_sName = c_ksPath + "/" + a_sName;
/*     */     }
/*     */ 
/* 441 */     a_options.addExecCommand(a_sName);
/*     */ 
/* 443 */     synchronized (s_returnValueCache)
/*     */     {
/* 446 */       if ((a_options.getOutputFile() != null) && (getFileCache().containsFile(a_options)))
/*     */       {
/*     */         try
/*     */         {
/* 450 */           FileUtils.copyFile(new File(getFileCache().getFile(a_options)), new File(a_options.getOutputFile()));
/* 451 */           m_logger.debug("ImageMagick.execute() : Used file from file cache to create output: " + a_options.getOutputFile() + " for command: " + a_options);
/* 452 */           return String.valueOf(0);
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/* 456 */           GlobalApplication.getInstance().getLogger().error("ImageMagick.execute() : IOException whilst copying cached file : " + e.getMessage(), e);
/*     */         }
/*     */       }
/* 459 */       else if (s_returnValueCache.containsKey(a_options))
/*     */       {
/* 461 */         String sReturnValue = (String)s_returnValueCache.get(a_options);
/* 462 */         m_logger.debug("ImageMagick.execute() : Used return data from return value cache: " + sReturnValue);
/*     */ 
/* 465 */         if ((sReturnValue.startsWith("ERROR : ")) && (!a_bSuppressErrors))
/*     */         {
/* 467 */           throw new ImageException("ImageMagick.execute, error returned from cache for command : " + a_options + " : " + sReturnValue);
/*     */         }
/*     */ 
/* 470 */         return sReturnValue;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 475 */     int iCode = -1;
/*     */     try
/*     */     {
/* 479 */       StringBuffer sbErrors = new StringBuffer();
/* 480 */       iCode = CommandLineExec.execute(a_options.toStringArray(), sbOut, sbErrors);
/*     */ 
/* 482 */       if (iCode != 0)
/*     */       {
/* 485 */         s_returnValueCache.put(a_options, "ERROR : " + iCode + " : " + sbErrors.toString());
/*     */ 
/* 487 */         if (!a_bSuppressErrors)
/*     */         {
/* 489 */           throw new Exception("Returned code: " + iCode + ", error output: " + sbErrors.toString());
/*     */         }
/*     */ 
/*     */       }
/* 495 */       else if ((a_options.getOutputFile() != null) && (!a_options.getDoNotCache()))
/*     */       {
/* 497 */         getFileCache().cacheFile(a_options, a_options.getOutputFile());
/*     */       }
/* 499 */       else if (sbOut.length() > 0)
/*     */       {
/* 501 */         s_returnValueCache.put(a_options, sbOut.toString());
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 507 */       throw new ImageException("ImageMagick.execute: " + a_options + " : " + e.getMessage(), e);
/*     */     }
/*     */ 
/* 510 */     return sbOut.toString();
/*     */   }
/*     */ 
/*     */   private static String[] getMetaInfo(String a_sType, Collection a_tags, String a_sFilename)
/*     */     throws ImageException
/*     */   {
/* 527 */     Collection tags = new Vector();
/* 528 */     Iterator it = a_tags.iterator();
/*     */ 
/* 531 */     while (it.hasNext())
/*     */     {
/* 533 */       tags.add("[" + a_sType + ":" + it.next() + "]");
/*     */     }
/*     */ 
/* 536 */     return getInfo(tags, a_sFilename);
/*     */   }
/*     */ 
/*     */   private static HashMap buildInfoMap(Collection a_fields, Collection a_values)
/*     */   {
/* 548 */     HashMap map = new HashMap();
/*     */ 
/* 550 */     Iterator itF = a_fields.iterator();
/* 551 */     Iterator itV = a_values.iterator();
/*     */ 
/* 556 */     while ((itF.hasNext()) && (itV.hasNext()))
/*     */     {
/* 558 */       String sField = (String)itF.next();
/* 559 */       String sValue = (String)itV.next();
/*     */ 
/* 561 */       if ((sValue == null) || (sValue.equals("")) || (sValue.equals("unknown"))) {
/*     */         continue;
/*     */       }
/* 564 */       if (sField.startsWith("WinXP-"))
/*     */       {
/* 566 */         sValue = cleanupWinXPField(sValue);
/*     */       }
/*     */ 
/* 569 */       map.put(sField, sValue);
/*     */     }
/*     */ 
/* 573 */     return map;
/*     */   }
/*     */ 
/*     */   public static String cleanupWinXPField(String a_sValue)
/*     */   {
/* 591 */     if ((a_sValue == null) || (a_sValue.length() == 0))
/*     */     {
/* 593 */       return a_sValue;
/*     */     }
/*     */ 
/* 596 */     StringBuffer sbRetVal = new StringBuffer();
/*     */ 
/* 599 */     for (int i = 0; i < a_sValue.length() - 2; i++)
/*     */     {
/* 602 */       if (i % 2 != 0)
/*     */         continue;
/* 604 */       sbRetVal.append(a_sValue.charAt(i));
/*     */     }
/*     */ 
/* 608 */     return sbRetVal.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.image.util.ImageMagick
 * JD-Core Version:    0.6.0
 */