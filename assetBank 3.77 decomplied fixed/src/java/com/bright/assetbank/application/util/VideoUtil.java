/*     */ package com.bright.assetbank.application.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.VideoInfo;
/*     */ import com.bright.assetbank.application.bean.VideoInfoImpl;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.converter.constant.ConverterSettings;
/*     */ import com.bright.assetbank.converter.exception.AssetConversionException;
/*     */ import com.bright.framework.image.util.ExifTool;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.commandline.CommandLineExec;
/*     */ import java.io.File;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class VideoUtil
/*     */   implements AssetBankConstants
/*     */ {
/*     */   private static final String k_sExifField_ImageSize = "ImageSize";
/*     */   private static final String k_sExifField_PlayDuration = "PlayDuration";
/*     */   private static final String k_sExifField_Duration = "Duration";
/*     */   private static final String k_sFfmpegOutput_Video = "Video:";
/*     */   private static final String k_sFfmpegOutput_PAR = "PAR";
/*     */   private static final String k_sFfmpegOutput_DurationFormat = "kk:mm:ss.SSS";
/*     */   private static final String k_sFfmpegOutput_Duration = "Duration:";
/*     */ 
/*     */   public static VideoInfo getVideoInfo(String a_sLocation)
/*     */     throws Bn2Exception
/*     */   {
/*  61 */     VideoInfo info = null;
/*     */ 
/*  64 */     String[] saCommand = { ConverterSettings.getFfmpegPath(), "-i", a_sLocation, "-t", "0", "-f", "null", "-" };
/*     */ 
/*  69 */     StringBuffer sbOutput = new StringBuffer();
/*  70 */     StringBuffer sbErrors = new StringBuffer();
/*     */     try
/*     */     {
/*  74 */       int iResult = CommandLineExec.execute(saCommand, sbOutput, sbErrors);
/*     */ 
/*  76 */       if (iResult < 2)
/*     */       {
/*  78 */         info = new VideoInfoImpl();
/*  79 */         String sOutput = null;
/*     */ 
/*  81 */         if (sbOutput.length() > 0)
/*     */         {
/*  83 */           sOutput = sbOutput.toString();
/*     */         }
/*     */         else
/*     */         {
/*  87 */           sOutput = sbErrors.toString();
/*     */         }
/*     */ 
/*  92 */         if (sOutput.indexOf("Duration:") > 0)
/*     */         {
/*  94 */           int iStart = sOutput.lastIndexOf("Duration:") + "Duration:".length();
/*  95 */           String sDuration = sOutput.substring(iStart, sOutput.indexOf(",", iStart));
/*     */ 
/*  97 */           sDuration = sDuration.trim();
/*     */ 
/*  99 */           if ((sDuration.length() >= 7) && (sDuration.charAt(2) == ':') && (sDuration.charAt(5) == ':'))
/*     */           {
/* 103 */             String sHours = sDuration.substring(0, 2);
/* 104 */             String sMinutes = sDuration.substring(3, 5);
/* 105 */             String sSeconds = sDuration.substring(6);
/*     */             try
/*     */             {
/* 109 */               int iHours = Integer.parseInt(sHours);
/* 110 */               int iMinutes = Integer.parseInt(sMinutes);
/* 111 */               double dSeconds = Double.parseDouble(sSeconds);
/* 112 */               long lSecondsAsMillis = Math.round(1000.0D * dSeconds);
/*     */ 
/* 114 */               long lMillis = (iHours * 60L + iMinutes) * 60000L + lSecondsAsMillis;
/* 115 */               info.setDuration(lMillis);
/*     */             }
/*     */             catch (NumberFormatException e)
/*     */             {
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 124 */         if (sOutput.indexOf("Video:") > 0)
/*     */         {
/* 126 */           int iStart = sOutput.lastIndexOf("Video:") + "Video:".length();
/* 127 */           String sVidStats = sOutput.substring(iStart, sOutput.indexOf("\n", iStart));
/* 128 */           String[] asStats = sVidStats.trim().split(",");
/*     */ 
/* 130 */           for (int i = 0; i < asStats.length; i++)
/*     */           {
                        System.out.println("video details :"+asStats[i]);
/* 132 */             if (!asStats[i].trim().matches("[0-9]+x[0-9]+"))
/*     */               continue;
/* 134 */             String[] asDim = asStats[i].trim().split("x");
/* 135 */             info.setWidth(Integer.parseInt(asDim[0]));
/* 136 */             info.setHeight(Integer.parseInt(asDim[1]));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 142 */         int iIndex = sOutput.indexOf("PAR");
/* 143 */         if (iIndex > 0)
/*     */         {
/* 146 */           String sValue = sOutput.substring(iIndex, sOutput.length());
/* 147 */           char[] aChars = sValue.toCharArray();
/* 148 */           boolean bPassedStart = false;
/* 149 */           String sDim = "";
/* 150 */           for (char cChar : aChars)
/*     */           {
/* 152 */             if ((bPassedStart) && (cChar == ' '))
/*     */             {
/*     */               break;
/*     */             }
/*     */ 
/* 157 */             if (("PAR".contains(String.valueOf(cChar))) || (cChar == ' '))
/*     */               continue;
/* 159 */             bPassedStart = true;
/* 160 */             sDim = sDim + cChar;
/*     */           }
/*     */ 
/* 163 */           String[] aVals = sDim.split(":");
/* 164 */           int iX = Integer.parseInt(aVals[0]);
/* 165 */           int iY = Integer.parseInt(aVals[1]);
/* 166 */           float fPAR = iX / iY;
/* 167 */           info.setPAR(fPAR);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 174 */       GlobalApplication.getInstance().getLogger().error("VideoUtil.getVideoInfo: Error getting info from ffmpeg", e);
/*     */     }
/*     */ 
/* 178 */     if ((info == null) || (info.getDuration() <= 0L) || (info.getWidth() <= 0) || (info.getHeight() <= 0))
/*     */     {
/* 180 */       if (info == null)
/*     */       {
/* 182 */         info = new VideoInfoImpl();
/*     */       }
/*     */ 
/* 185 */       HashMap hmFields = ExifTool.getData(a_sLocation, null, false);
/*     */ 
/* 187 */       if (info.getDuration() <= 0L)
/*     */       {
/* 189 */         String sDuration = null;
/*     */ 
/* 191 */         if (hmFields.containsKey("Duration"))
/*     */         {
/* 193 */           sDuration = (String)hmFields.get("Duration");
/*     */         }
/* 195 */         else if (hmFields.containsKey("PlayDuration"))
/*     */         {
/* 197 */           sDuration = (String)hmFields.get("PlayDuration");
/*     */         }
/*     */ 
/* 200 */         if (sDuration != null)
/*     */         {
/* 202 */           sDuration = sDuration.trim();
/*     */ 
/* 204 */           if (sDuration.matches("[0-9]+(\\.[0-9]+)? ?s(ec)?"))
/*     */           {
/* 206 */             sDuration = sDuration.replaceAll("[a-zA-Z ]+", "");
/* 207 */             double dDuration = Double.parseDouble(sDuration);
/* 208 */    //         info.setDuration(()(dDuration * 1000.0D));
                         info.setDuration((long)(dDuration * 1000.0D));
/*     */           }
/* 210 */           else if (sDuration.matches("[0-9]+ ?msec"))
/*     */           {
/* 212 */             sDuration = sDuration.replaceAll("[a-zA-Z ]+", "");
/* 213 */             info.setDuration(Long.parseLong(sDuration));
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 218 */       if ((info.getHeight() <= 0) || (info.getWidth() <= 0))
/*     */       {
/* 220 */         if (hmFields.containsKey("ImageSize"))
/*     */         {
/* 222 */           String sSize = (String)hmFields.get("ImageSize");
/*     */ 
/* 224 */           if (sSize.trim().matches("[0-9]+x[0-9]+"))
/*     */           {
/* 226 */             String[] asDim = sSize.trim().split("x");
/* 227 */             info.setWidth(Integer.parseInt(asDim[0]));
                       info.setHeight(Integer.parseInt(asDim[1]));
                       info.setWidth(140);
/* 228 */              info.setHeight(110);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 234 */     return info;
/*     */   }
/*     */ 
/*     */   public static void createThumbnailFromVideo(String a_sSourceFullPath, int a_iOffset, int a_iHeight, int a_iWidth, String a_sOutputFilename)
/*     */     throws AssetConversionException
/*     */   {
                System.out.println("CTFV height: "+String.valueOf(a_iHeight)); 
               
/* 257 */     String[] saThumbCommand = { ConverterSettings.getFfmpegPath(), "-i", a_sSourceFullPath, "-vcodec", "png", "-b", AssetBankSettings.getDefaultVideoPreviewBitrate(), "-ss", String.valueOf(a_iOffset), "-s", String.valueOf(a_iWidth) + "x" +String.valueOf(a_iHeight) , "-vframes", AssetBankSettings.getVideoFramesSelectionInterval(), "-an", "-f", "rawvideo", "-y", a_sOutputFilename };
/*     */ 
/* 270 */     runCommand(saThumbCommand);
/*     */   }
/*     */ 
/*     */   public static void createPreviewImage(String a_sSourcePath, String a_sDestinationPath, int a_iFrameOffset)
/*     */     throws AssetConversionException, Bn2Exception
/*     */   {
/* 292 */     VideoInfo info = getVideoInfo(a_sSourcePath);
/*     */ 
/* 294 */     String[] saCommand = { ConverterSettings.getFfmpegPath(), "-i", a_sSourcePath, "-vcodec", "png", "-b", AssetBankSettings.getDefaultVideoPreviewBitrate(), "-ss", String.valueOf(a_iFrameOffset), "-vframes", AssetBankSettings.getVideoFramesSelectionInterval(), "-s", info.getWidth() + "x" +info.getDisplayHeight(), "-an", "-f", "rawvideo", "-y", a_sDestinationPath };
/*     */ 
/* 307 */     runCommand(saCommand);
/*     */   }
/*     */ 
/*     */   public static void clearThumbnailDirectory(String a_sPath)
/*     */   {
/* 326 */     if ((a_sPath != null) && (a_sPath.contains(AssetBankSettings.getVideoFramesTempDir() + "/")))
/*     */     {
/* 328 */       File f = new File(a_sPath);
/* 329 */       String[] aFileNames = f.list();
/*     */ 
/* 331 */       if (aFileNames != null)
/*     */       {
/* 333 */         for (int i = 0; i < aFileNames.length; i++)
/*     */         {
/* 335 */           File tempFile = new File(a_sPath + "/" + aFileNames[i]);
/* 336 */           if (!tempFile.isFile())
/*     */             continue;
/* 338 */           tempFile.delete();
/* 339 */           FileUtil.logFileDeletion(tempFile);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 344 */       f.delete();
/* 345 */       FileUtil.logFileDeletion(f);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static int getDisplayHeight(int a_iPixelHeight, float a_fPAR)
/*     */   {
/* 352 */     int iDisplayHeight = a_iPixelHeight;
/* 353 */     if ((a_fPAR != 0.0F) && (a_fPAR != 1.0F))
/*     */     {
/* 355 */       iDisplayHeight = Math.round(a_iPixelHeight / a_fPAR);
/* 356 */       if (iDisplayHeight % 2 != 0)
/*     */       {
/* 358 */         iDisplayHeight++;
/*     */       }
/* 360 */       return iDisplayHeight;
/*     */     }
/*     */ 
/* 363 */     return iDisplayHeight;
/*     */   }
/*     */ 
/*     */   public static void runCommand(String[] a_aCommand)
/*     */     throws AssetConversionException
/*     */   {
/*     */     try
/*     */     {
                //System.out.println(a_aCommand);
/* 383 */       CommandLineExec.execute(a_aCommand);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 387 */       throw new AssetConversionException("VideoToPngConverter.convert: exception: ", e);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.util.VideoUtil
 * JD-Core Version:    0.6.0
 */