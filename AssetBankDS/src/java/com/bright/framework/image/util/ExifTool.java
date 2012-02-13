/*     */ package com.bright.framework.image.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.framework.common.bean.KeyValueBean;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import com.bright.framework.util.commandline.CommandLineExec;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class ExifTool extends CommandLineExec
/*     */ {
/*  49 */   private static boolean m_bWarningGiven = false;
/*     */ 
/*  52 */   private static String[] c_sDateFormats = { "yyyy:MM:dd HH:mm:ss", "yyyy:MM:dd", "dd MMM yyyy", "dd MMMM yyyy", "ddMMMyyyy", "MMMyyyy" };
/*     */ 
/*  56 */   public static String[] c_sAutoRotateFields = { "MakerNotes:AutoRotate", "MakerNotes:Rotation", "XMP:Orientation", "EXIF:Orientation" };
/*  57 */   public static String[] c_sAutoRotate90 = { "Rotate 90 CW", "Rotated 270 CW", "6" };
/*  58 */   public static String[] c_sAutoRotate180 = { "Rotate 180", "Rotated 180", "3" };
/*  59 */   public static String[] c_sAutoRotate270 = { "Rotate 270 CW", "Rotated 90 CW", "8" };
/*  60 */   public static String c_sXMPXResolution = "Photoshop:XResolution";
/*  61 */   public static String c_sXMPYResolution = "Photoshop:YResolution";
/*     */ 
/*  64 */   public static String[] c_sGPSLatitudeTags = { "EXIF:GPSLatitude", "EXIF:GPSLatitudeRef" };
/*  65 */   public static String[] c_sGPSLongitudeTags = { "EXIF:GPSLongitude", "EXIF:GPSLongitudeRef" };
/*  66 */   public static String[] c_sGPSMapDatumTags = { "EXIF:GPSLatitude", "EXIF:GPSMapDatum" };
/*     */ 
/*  69 */   public static String[] c_sGPSSouthNames = { "s", "south" };
/*  70 */   public static String[] c_sGPSWestNames = { "w", "west" };
/*     */ 
/*  73 */   public static String c_sAlphaChannelsNames = "AlphaChannelsNames";
/*     */ 
/*  76 */   public static String c_sMapDatumDefault = "4326 (WGS 84)";
/*     */ 
/*  78 */   private static Log m_logger = GlobalApplication.getInstance().getLogger();
/*     */ 
/*     */   public static void addMetadata(KeyValueBean[] a_asDataValues, String a_sImagePath)
/*     */     throws Bn2Exception
/*     */   {
/*  95 */     int iOptionIndex = 0;
/*  96 */     String[] asCommand = new String[5 + a_asDataValues.length];
/*  97 */     asCommand[(iOptionIndex++)] = getCommand();
/*     */ 
/* 100 */     if ((asCommand[0] == null) || (asCommand[0].length() == 0))
/*     */     {
/* 102 */       logWarning();
/* 103 */       return;
/*     */     }
/*     */ 
/* 106 */     asCommand[(iOptionIndex++)] = "-m";
/* 107 */     asCommand[(iOptionIndex++)] = "-overwrite_original";
/*     */ 
/* 111 */     asCommand[(iOptionIndex++)] = "-codedcharacterset=utf8";
/*     */ 
/* 113 */     for (int i = 0; i < a_asDataValues.length; i++)
/*     */     {
/* 116 */       asCommand[(iOptionIndex++)] = ("-" + a_asDataValues[i].getKey() + "=" + format(a_asDataValues[i].getValue()));
/*     */     }
/*     */ 
/* 122 */     asCommand[(iOptionIndex++)] = a_sImagePath;
/*     */ 
/* 125 */     StringBuffer sbErrors = new StringBuffer();
/* 126 */     StringBuffer sbOutput = new StringBuffer();
/*     */ 
/* 128 */     int iRetValue = execute(asCommand, sbOutput, sbErrors, -1L, "UTF-8");
/*     */ 
/* 130 */     if (iRetValue < 0)
/*     */     {
/* 132 */       throw new Bn2Exception("EXIFTool: couldn't run command " + getCommand() + " - return value is " + iRetValue + ". Error output is: " + sbErrors.toString() + ". Output is: " + sbOutput.toString());
/*     */     }
/*     */ 
/* 139 */     if (iRetValue != 0)
/*     */     {
/* 145 */       m_logger.error("EXIFTool: couldn't run command " + getCommand() + " - return value is " + iRetValue + ". Error output is: " + sbErrors.toString() + ". Output is: " + sbOutput.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String format(String a_sValue)
/*     */   {
/* 168 */     String sFormatted = a_sValue.replaceAll("\"", "\\\\\"");
/*     */ 
/* 175 */     return sFormatted;
/*     */   }
/*     */ 
/*     */   public static HashMap getData(String a_sImagePath, Vector a_vTags, boolean a_bPopulateGroups)
/*     */     throws Bn2Exception
/*     */   {
/* 192 */     HashMap hmResults = new HashMap();
/* 193 */     getData(hmResults, a_sImagePath, a_vTags, a_bPopulateGroups);
/* 194 */     return hmResults;
/*     */   }
/*     */ 
/*     */   public static void getData(HashMap a_hmResults, String a_sImagePath, Vector a_vTags, boolean a_bPopulateGroups)
/*     */     throws Bn2Exception
/*     */   {
/* 220 */     String sCommand = getCommand();
/*     */ 
/* 222 */     if ((sCommand == null) || (sCommand.length() == 0))
/*     */     {
/* 224 */       logWarning();
/* 225 */       return;
/*     */     }
/*     */ 
/* 228 */     StringBuffer sbErrors = new StringBuffer();
/* 229 */     StringBuffer sbOutput = new StringBuffer();
/* 230 */     HashMap hmTagsWithoutGroups = null;
/* 231 */     String sTag = null;
/*     */ 
/* 233 */     int iNumTags = 0;
/* 234 */     int iArraySize = 0;
/*     */ 
/* 236 */     if (a_vTags != null)
/*     */     {
/* 238 */       iNumTags = a_vTags.size();
/*     */     }
/*     */ 
/* 241 */     iArraySize += 8 + iNumTags;
/*     */ 
/* 243 */     if (a_bPopulateGroups)
/*     */     {
/* 245 */       iArraySize++;
/*     */     }
/*     */ 
/* 248 */     String[] asCommand = new String[iArraySize];
/*     */ 
/* 251 */     int iArgIndex = 0;
/* 252 */     asCommand[(iArgIndex++)] = sCommand;
/* 253 */     asCommand[(iArgIndex++)] = "-s";
/*     */ 
/* 255 */     if (a_bPopulateGroups)
/*     */     {
/* 257 */       asCommand[(iArgIndex++)] = "-G";
/* 258 */       hmTagsWithoutGroups = new HashMap();
/*     */     }
/*     */ 
/* 261 */     asCommand[(iArgIndex++)] = "-m";
/* 262 */     asCommand[(iArgIndex++)] = "-q";
/*     */ 
/* 266 */     asCommand[(iArgIndex++)] = "-a";
/*     */ 
/* 269 */     asCommand[(iArgIndex++)] = "-c";
/* 270 */     asCommand[(iArgIndex++)] = AssetBankSettings.getEmbeddedGPSCoordinateFormat();
/*     */ 
/* 273 */     int i = 0;
/* 274 */     if (a_vTags != null)
/*     */     {
/* 276 */       Iterator itTags = a_vTags.iterator();
/* 277 */       while (itTags.hasNext())
/*     */       {
/* 279 */         sTag = (String)itTags.next();
/* 280 */         asCommand[(iArgIndex + i)] = ("-" + sTag);
/*     */ 
/* 283 */         if ((a_bPopulateGroups) && (!sTag.contains(":")))
/*     */         {
/* 285 */           hmTagsWithoutGroups.put(sTag, null);
/*     */         }
/*     */ 
/* 288 */         i++;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 293 */     asCommand[(iArgIndex + iNumTags)] = a_sImagePath;
/*     */ 
/* 296 */     int iRetValue = execute(asCommand, sbOutput, sbErrors, -1L, "UTF-8");
/*     */ 
/* 298 */     if (iRetValue < 0)
/*     */     {
/* 300 */       throw new Bn2Exception("EXIFTool: couldn't run command " + getCommand() + " - return value is " + iRetValue + ". Error output is: " + sbErrors.toString() + ". Output is: " + sbOutput.toString());
/*     */     }
/*     */ 
/* 307 */     if (iRetValue != 0)
/*     */     {
/* 313 */       m_logger.error("EXIFTool: couldn't run command " + getCommand() + " - return value is " + iRetValue + ". Error output is: " + sbErrors.toString() + ". Output is: " + sbOutput.toString());
/*     */     }
/*     */ 
/* 322 */     String[] saTags = StringUtil.splitOnNewlines(sbOutput.toString());
/*     */ 
/* 324 */     String sGroup = null;
/* 325 */     String sValue = null;
/* 326 */     String[] saPair = null;
/*     */     try
/*     */     {
/* 330 */       for (i = 0; (saTags != null) && (i < saTags.length); i++)
/*     */       {
/* 332 */         if ((saTags[i] == null) || (saTags[i].length() <= 0)) {
/*     */           continue;
/*     */         }
/* 335 */         saPair = saTags[i].split(":", 2);
/*     */ 
/* 337 */         sValue = saPair[1].trim();
/*     */ 
/* 340 */         if (a_bPopulateGroups)
/*     */         {
/* 342 */           saPair = saPair[0].split("]");
/* 343 */           sTag = saPair[1].trim();
/* 344 */           sGroup = saPair[0].trim().substring(1);
/*     */ 
/* 346 */           a_hmResults.put(sGroup + ":" + sTag, sValue);
/*     */ 
/* 349 */           if ((hmTagsWithoutGroups == null) || (!hmTagsWithoutGroups.containsKey(sTag)))
/*     */             continue;
/* 351 */           a_hmResults.put(sTag, sValue);
/*     */         }
/*     */         else
/*     */         {
/* 356 */           a_hmResults.put(saPair[0].trim(), sValue);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Throwable t)
/*     */     {
/* 363 */       throw new Bn2Exception(t + "EXIFTool: couldn't run command " + getCommand() + t.getMessage() + " - return value is " + iRetValue + ". Error output is: " + sbErrors.toString() + ". Output is: " + sbOutput.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void getBinaryData(HashMap a_hmResults, String a_sImagePath, Vector a_vTags)
/*     */     throws Bn2Exception
/*     */   {
/* 382 */     int iRetValue = 0;
/*     */ 
/* 384 */     StringBuffer sbErrors = null;
/* 385 */     StringBuffer sbOutput = null;
/*     */ 
/* 387 */     String sCommand = getCommand();
/*     */ 
/* 389 */     if ((sCommand == null) || (sCommand.length() == 0))
/*     */     {
/* 391 */       logWarning();
/*     */     }
/*     */ 
/* 394 */     String[] asCommand = new String[9];
/*     */ 
/* 397 */     int iArgIndex = 0;
/* 398 */     asCommand[(iArgIndex++)] = sCommand;
/* 399 */     asCommand[(iArgIndex++)] = "-s";
/* 400 */     asCommand[(iArgIndex++)] = "-G";
/* 401 */     asCommand[(iArgIndex++)] = "-m";
/* 402 */     asCommand[(iArgIndex++)] = "-q";
/* 403 */     asCommand[(iArgIndex++)] = "-q";
/* 404 */     asCommand[(iArgIndex++)] = "-b";
/*     */     try
/*     */     {
/* 410 */       Iterator itTags = a_vTags.iterator();
/*     */ 
/* 412 */       while (itTags.hasNext())
/*     */       {
/* 414 */         sbErrors = new StringBuffer();
/* 415 */         sbOutput = new StringBuffer();
/* 416 */         int i = 0;
/*     */ 
/* 418 */         String sNextTag = (String)itTags.next();
/*     */ 
/* 420 */         asCommand[(iArgIndex + i++)] = ("-" + sNextTag);
/*     */ 
/* 422 */         asCommand[(iArgIndex + i++)] = a_sImagePath;
/*     */ 
/* 425 */         iRetValue = execute(asCommand, sbOutput, sbErrors, -1L, "UTF-8");
/*     */ 
/* 427 */         if (iRetValue < 0)
/*     */         {
/* 429 */           throw new Bn2Exception("EXIFTool: couldn't run command " + getCommand() + " - return value is " + iRetValue + ". Error output is: " + sbErrors.toString() + ". Output is: " + sbOutput.toString());
/*     */         }
/*     */ 
/* 436 */         if (iRetValue != 0)
/*     */         {
/* 443 */           m_logger.error("EXIFTool: couldn't run command " + getCommand() + " - return value is " + iRetValue + ". Error output is: " + sbErrors.toString() + ". Output is: " + sbOutput.toString());
/*     */         }
/*     */ 
/* 451 */         if (!a_hmResults.containsKey(sNextTag))
/*     */         {
/* 453 */           a_hmResults.put(sNextTag, sbOutput.toString());
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Throwable t)
/*     */     {
/* 459 */       throw new Bn2Exception("EXIFTool: couldn't run command " + getCommand() + " - return value is " + iRetValue + ". Error output is: " + sbErrors.toString() + ". Output is: " + sbOutput.toString(), t);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static synchronized void logWarning()
/*     */   {
/* 481 */     String sMessage = "EXIFTool: the path to ExifTool is empty in the settings file so IPTC/EXIF/XMP/etc data will not be extracted from any images.";
/* 482 */     if (!m_bWarningGiven)
/*     */     {
/* 484 */       GlobalApplication.getInstance().getLogger().warn(sMessage);
/* 485 */       m_bWarningGiven = true;
/*     */     }
/*     */     else
/*     */     {
/* 489 */       GlobalApplication.getInstance().getLogger().debug(sMessage);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String getCommand()
/*     */   {
/* 495 */     return AssetBankSettings.getExifToolPath();
/*     */   }
/*     */ 
/*     */   public static Date getValueAsDate(String a_sExifValue)
/*     */   {
/* 512 */     Date dtValue = null;
/*     */ 
/* 514 */     DateFormat dfFormat = null;
/*     */ 
/* 517 */     for (int i = 0; (i < c_sDateFormats.length) && (dtValue == null); i++)
/*     */     {
/*     */       try
/*     */       {
/* 521 */         dtValue = new SimpleDateFormat(c_sDateFormats[i]).parse(a_sExifValue);
/*     */       }
/*     */       catch (ParseException pe)
/*     */       {
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 530 */     return dtValue;
/*     */   }
/*     */ 
/*     */   public static int getAutoRotate(HashMap a_mapValues)
/*     */   {
/* 546 */     int iRotate = 0;
/*     */ 
/* 549 */     for (int i = 0; (i < c_sAutoRotateFields.length) && (iRotate == 0); i++)
/*     */     {
/* 551 */       String sValue = (String)a_mapValues.get(c_sAutoRotateFields[i]);
/*     */ 
/* 553 */       if ((sValue == null) || (sValue.length() <= 0)) {
/*     */         continue;
/*     */       }
/* 556 */       if (StringUtil.arrayContains(c_sAutoRotate90, sValue))
/*     */       {
/* 558 */         iRotate = 90;
/* 559 */         break;
/*     */       }
/* 561 */       if (StringUtil.arrayContains(c_sAutoRotate180, sValue))
/*     */       {
/* 563 */         iRotate = 180;
/* 564 */         break;
/*     */       }
/* 566 */       if (!StringUtil.arrayContains(c_sAutoRotate270, sValue))
/*     */         continue;
/* 568 */       iRotate = 270;
/* 569 */       break;
/*     */     }
/*     */ 
/* 574 */     return iRotate;
/*     */   }
/*     */ 
/*     */   public static Vector getGPSLatitudeTags()
/*     */   {
/* 585 */     Vector vec = new Vector();
/* 586 */     for (int i = 0; i < c_sGPSLatitudeTags.length; i++)
/*     */     {
/* 588 */       vec.add(c_sGPSLatitudeTags[i]);
/*     */     }
/* 590 */     return vec;
/*     */   }
/*     */ 
/*     */   public static Vector getGPSLongitudeTags()
/*     */   {
/* 600 */     Vector vec = new Vector();
/* 601 */     for (int i = 0; i < c_sGPSLongitudeTags.length; i++)
/*     */     {
/* 603 */       vec.add(c_sGPSLongitudeTags[i]);
/*     */     }
/* 605 */     return vec;
/*     */   }
/*     */ 
/*     */   public static Vector getGPSMapDatumTags()
/*     */   {
/* 615 */     Vector vec = new Vector();
/* 616 */     for (int i = 0; i < c_sGPSMapDatumTags.length; i++)
/*     */     {
/* 618 */       vec.add(c_sGPSMapDatumTags[i]);
/*     */     }
/* 620 */     return vec;
/*     */   }
/*     */ 
/*     */   public static String getSignedLatitude(HashMap a_hmEmbeddedValues)
/*     */   {
/* 632 */     String sLatitude = (String)a_hmEmbeddedValues.get(c_sGPSLatitudeTags[0]);
/*     */ 
/* 635 */     String sLatitudeRef = (String)a_hmEmbeddedValues.get(c_sGPSLatitudeTags[1]);
/*     */ 
/* 638 */     boolean bSouth = false;
/* 639 */     for (String sMatch : c_sGPSSouthNames)
/*     */     {
/* 641 */       if (!sMatch.equalsIgnoreCase(sLatitudeRef))
/*     */         continue;
/* 643 */       bSouth = true;
/* 644 */       break;
/*     */     }
/*     */ 
/* 647 */     if ((bSouth) && (sLatitude != null))
/*     */     {
/* 649 */       sLatitude = "-" + sLatitude;
/*     */     }
/*     */ 
/* 652 */     return sLatitude;
/*     */   }
/*     */ 
/*     */   public static String getSignedLongitude(HashMap a_hmEmbeddedValues)
/*     */   {
/* 664 */     String sLongitude = (String)a_hmEmbeddedValues.get(c_sGPSLongitudeTags[0]);
/*     */ 
/* 667 */     String sLongitudeRef = (String)a_hmEmbeddedValues.get(c_sGPSLongitudeTags[1]);
/*     */ 
/* 670 */     boolean bWest = false;
/* 671 */     for (String sMatch : c_sGPSWestNames)
/*     */     {
/* 673 */       if (!sMatch.equalsIgnoreCase(sLongitudeRef))
/*     */         continue;
/* 675 */       bWest = true;
/* 676 */       break;
/*     */     }
/*     */ 
/* 679 */     if ((bWest) && (sLongitude != null))
/*     */     {
/* 681 */       sLongitude = "-" + sLongitude;
/*     */     }
/*     */ 
/* 684 */     return sLongitude;
/*     */   }
/*     */ 
/*     */   public static String getMapDatumMapped(HashMap a_hmEmbeddedValues)
/*     */   {
/* 697 */     String sLatitude = (String)a_hmEmbeddedValues.get(c_sGPSMapDatumTags[0]);
/* 698 */     String sMapDatum = (String)a_hmEmbeddedValues.get(c_sGPSMapDatumTags[1]);
/*     */ 
/* 701 */     String sValue = "";
/* 702 */     if (StringUtil.stringIsPopulated(sLatitude))
/*     */     {
/* 704 */       sValue = c_sMapDatumDefault;
/*     */     }
/*     */ 
/* 707 */     return sValue;
/*     */   }
/*     */ 
/*     */   public static String getAlphaChannelsNames(String a_sInputPath)
/*     */   {
/* 717 */     Vector vTags = new Vector();
/* 718 */     vTags.add(c_sAlphaChannelsNames);
/*     */     try
/*     */     {
/* 722 */       HashMap hmValues = getData(a_sInputPath, vTags, false);
/*     */ 
/* 724 */       if (hmValues.containsKey(c_sAlphaChannelsNames))
/*     */       {
/* 726 */         return (String)hmValues.get(c_sAlphaChannelsNames);
/*     */       }
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/* 731 */       m_logger.error(ExifTool.class.getSimpleName() + ".getAlphaChannelsNames() : Bn2Exception whilst getting AlphaChannelsNames tag using ExifTool", e);
/*     */     }
/* 733 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.image.util.ExifTool
 * JD-Core Version:    0.6.0
 */