/*    */ package com.bright.datamigration;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.PrintStream;
/*    */ import org.apache.commons.io.FileUtils;
/*    */ 
/*    */ public class FileSpitter
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 39 */     String sFilename = args[0];
/* 40 */     String sOutputDir = args[1];
/*    */     try
/*    */     {
/* 44 */       InputStreamReader reader = new InputStreamReader(new FileInputStream(sFilename), "UTF-8");
/* 45 */       BufferedReader bReader = new BufferedReader(reader);
/*    */ 
/* 47 */       String sLine = bReader.readLine();
/* 48 */       String sXmlAsset = null;
/* 49 */       String id = null;
/* 50 */       boolean bIsVideo = false;
/* 51 */       boolean bIsAudio = false;
/* 52 */       boolean bIsSeries = false;
/*    */ 
/* 54 */       while (sLine != null)
/*    */       {
/* 56 */         if (sLine.matches("[0-9]+\\t<.*"))
/*    */         {
/* 58 */           if (sXmlAsset != null)
/*    */           {
/* 60 */             if (id == null)
/*    */             {
/* 62 */               FileUtils.writeStringToFile(new File(sOutputDir + "/no_id_" + System.currentTimeMillis() + ".xml"), sXmlAsset, "UTF-8");
/*    */             }
/*    */             else
/*    */             {
/* 66 */               long dir = Long.parseLong(id);
/*    */ 
/* 68 */               dir /= 100L;
/*    */ 
/* 70 */               FileUtils.forceMkdir(new File(sOutputDir + "/" + (bIsAudio ? "audio" : bIsSeries ? "series" : bIsVideo ? "video" : "image") + "/" + dir));
/* 71 */               System.out.println("Writing file for ID: " + id);
/* 72 */               FileUtils.writeStringToFile(new File(sOutputDir + "/" + (bIsAudio ? "audio" : bIsSeries ? "series" : bIsVideo ? "video" : "image") + "/" + dir + "/" + id + ".xml"), sXmlAsset, "UTF-8");
/*    */             }
/*    */           }
/*    */ 
/* 76 */           sXmlAsset = sLine.substring(sLine.indexOf("<")) + "\n";
/*    */ 
/* 79 */           bIsVideo = sXmlAsset.indexOf("video") > 0;
/* 80 */           bIsAudio = sXmlAsset.indexOf("audio-asset") > 0;
/* 81 */           bIsSeries = sXmlAsset.indexOf("asset-series") > 0;
/*    */ 
/* 83 */           id = null;
/*    */         }
/*    */         else
/*    */         {
/* 87 */           if ((id == null) && (sLine.matches(".*<id>[0-9]+</id>.*")))
/*    */           {
/* 89 */             id = sLine.substring(sLine.indexOf("<id>") + 4, sLine.indexOf("</id>"));
/*    */           }
/* 91 */           sXmlAsset = sXmlAsset + sLine + "\n";
/*    */         }
/*    */ 
/* 94 */         sLine = bReader.readLine();
/*    */       }
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 99 */       e.printStackTrace();
/* 100 */       System.exit(1);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.datamigration.FileSpitter
 * JD-Core Version:    0.6.0
 */