/*    */ package com.bright.datamigration;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.FileReader;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Iterator;
/*    */ import org.apache.commons.io.FileUtils;
/*    */ 
/*    */ public class SensitivityNotesExtractor
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 23 */     Iterator itFiles = FileUtils.iterateFiles(new File(args[0]), new String[] { "xml" }, true);
/*    */ 
/* 25 */     while (itFiles.hasNext())
/*    */     {
/*    */       try
/*    */       {
/* 29 */         boolean bSensitive = false;
/*    */ 
/* 31 */         File input = (File)itFiles.next();
/*    */ 
/* 33 */         FileReader fr = new FileReader(input);
/*    */ 
/* 35 */         BufferedReader br = new BufferedReader(fr);
/*    */ 
/* 38 */         String sNotes = null;
/* 39 */         String sId = null;
/* 40 */         String sItemId = null;
/* 41 */         boolean bInItems = false;
/* 42 */         String sIdToUse = null;
/*    */         String sLine;
/* 44 */         while ((sLine = br.readLine()) != null)
/*    */         {
/* 46 */           String sLineLower = sLine.toLowerCase();
/*    */ 
/* 48 */           if (sLineLower.toLowerCase().contains("<sensitive>true"))
/*    */           {
/* 50 */             bSensitive = true;
/*    */           }
/*    */ 
/* 53 */           if ((sId == null) && (sLineLower.contains("<id>")))
/*    */           {
/* 55 */             sId = sLine.substring(sLineLower.indexOf("<id>") + "<id>".length(), sLineLower.indexOf("</id>"));
/*    */           }
/*    */ 
/* 58 */           if ((sLineLower.contains("<retained-copies>")) || (sLineLower.contains("<items>")))
/*    */           {
/* 60 */             bInItems = true;
/*    */           }
/*    */ 
/* 63 */           if ((sLineLower.contains("</retained-copies>")) || (sLineLower.contains("</items>")))
/*    */           {
/* 65 */             bInItems = false;
/*    */           }
/*    */ 
/* 68 */           if ((bInItems) && (sLineLower.contains("<id>")))
/*    */           {
/* 70 */             sItemId = sLine.substring(sLineLower.indexOf("<id>") + "<id>".length(), sLineLower.indexOf("</id>"));
/*    */           }
/*    */ 
/* 73 */           if (sLineLower.contains("<sensitivity-notes>"))
/*    */           {
/* 75 */             sNotes = sLine.substring(sLineLower.indexOf("<sensitivity-notes>") + "<sensitivity-notes>".length(), sLineLower.indexOf("</sensitivity-notes>"));
/*    */ 
/* 77 */             if (bInItems)
/*    */             {
/* 79 */               throw new RuntimeException("Found sensitivity data in items - need to code for this case!");
/*    */             }
/*    */ 
/* 82 */             sIdToUse = bInItems ? sItemId : sId;
/*    */           }
/*    */         }
/*    */ 
/* 86 */         if ((bSensitive) && (sNotes != null))
/*    */         {
/* 88 */           if (sNotes.trim().length() > 0)
/*    */           {
/* 90 */             String sType = input.getAbsolutePath().replace('\\', '/');
/* 91 */             String sTypeId = sType.contains("/audio/") ? "7" : sType.contains("/video/") ? "3" : sType.contains("/image/") ? "1" : null;
/*    */ 
/* 93 */             if (sTypeId != null)
/*    */             {
/* 95 */               System.out.println("UPDATE AssetAttributeValues SET SensitivityNotes='" + sNotes.replace("'", "''") + "' WHERE AdamId='" + sIdToUse + "' AND AssetId IN (SELECT Id FROM Asset WHERE AssetEntityId=" + sTypeId + " AND IsSensitive=1);");
/*    */             }
/*    */           }
/*    */ 
/*    */         }
/*    */ 
/*    */       }
/*    */       catch (IOException e)
/*    */       {
/* 104 */         e.printStackTrace();
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.datamigration.SensitivityNotesExtractor
 * JD-Core Version:    0.6.0
 */