/*    */ package com.bright.datamigration;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.PrintStream;
/*    */ import java.util.HashSet;
/*    */ 
/*    */ public class AssetRelationshipsBuilder
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 39 */     String sFilename = args[0];
/* 40 */     String sOutputFile = args[1];
/* 41 */     FileWriter writer = null;
/*    */     try
/*    */     {
/* 45 */       InputStreamReader reader = new InputStreamReader(new FileInputStream(sFilename), "UTF-8");
/* 46 */       BufferedReader bReader = new BufferedReader(reader);
/* 47 */       writer = new FileWriter(sOutputFile);
/*    */ 
/* 49 */       String sLine = bReader.readLine();
/* 50 */       String accessCopyId = null;
/* 51 */       String itemId = null;
/* 52 */       boolean bAccessCopy = false;
/* 53 */       boolean bItems = false;
/* 54 */       int iSeq = 1;
/* 55 */       long entityId = 0L;
/* 56 */       long parentEntityId = 0L;
/* 57 */       String sAssetId = null;
/* 58 */       HashSet items = new HashSet();
/*    */ 
/* 60 */       writer.write("UPDATE RelatedAsset SET SequenceNumber=1 WHERE SequenceNumber IS NULL;\n");
/*    */ 
/* 62 */       while (sLine != null)
/*    */       {
/* 64 */         if (sLine.matches("[0-9]+\\t<.*"))
/*    */         {
/* 66 */           String line = sLine.substring(sLine.indexOf("<")) + "\n";
/* 67 */           if (line.indexOf("video") > 0)
/*    */           {
/* 69 */             entityId = 5L;
/* 70 */             parentEntityId = 3L;
/*    */           }
/* 72 */           else if (line.indexOf("audio") > 0)
/*    */           {
/* 74 */             entityId = 8L;
/* 75 */             parentEntityId = 7L;
/*    */           }
/*    */           else
/*    */           {
/* 79 */             entityId = 2L;
/* 80 */             parentEntityId = 1L;
/*    */           }
/*    */ 
/* 83 */           sLine = bReader.readLine();
/*    */ 
/* 85 */           sAssetId = sLine.substring(sLine.indexOf("<id>") + 4, sLine.indexOf("</id>"));
/* 86 */           accessCopyId = null;
/* 87 */           iSeq = 1;
/*    */         } else {
/* 89 */           if (sLine.matches(".*<adam-collections?>.*"))
/*    */           {
/* 91 */             while (!sLine.matches(".*</adam-collections?>.*"))
/*    */             {
/* 93 */               sLine = bReader.readLine();
/*    */             }
/*    */           }
/* 96 */           if ((sLine.matches(".*<archive-master>.*")) || (sLine.matches(".*<access-copy>.*")))
/*    */           {
/* 98 */             bAccessCopy = true;
/* 99 */             bItems = false;
/*    */           }
/* 101 */           else if ((sLine.matches(".*</archive-master>.*")) || (sLine.matches(".*</access-copy>.*")))
/*    */           {
/* 103 */             bAccessCopy = false;
/*    */           }
/* 105 */           else if ((sLine.matches(".*<retained-copies>.*")) || (sLine.matches(".*<items>.*")))
/*    */           {
/* 107 */             bItems = true;
/* 108 */             bAccessCopy = false;
/*    */           }
/* 110 */           else if ((sLine.matches(".*</retained-copies>.*")) || (sLine.matches(".*</items>.*")))
/*    */           {
/* 112 */             bItems = false;
/* 113 */             bAccessCopy = false;
/*    */           }
/* 115 */           else if (bAccessCopy)
/*    */           {
/* 117 */             if (sLine.matches(".*<id>[0-9]+</id>.*"))
/*    */             {
/* 119 */               accessCopyId = sLine.replaceAll("(.*<id>)|(</id>.*)", "");
/*    */ 
/* 122 */               if (items.contains(accessCopyId + "-" + sAssetId + "-" + entityId))
/*    */               {
/* 124 */                 writer.write("UPDATE RelatedAsset SET SequenceNumber=1 WHERE ChildId IN (SELECT Id FROM Asset WHERE Author='" + accessCopyId + "' AND AssetEntityId=" + entityId + ") AND ParentId IN (SELECT Id FROM Asset WHERE Author='" + sAssetId + "' AND AssetEntityId=" + parentEntityId + ");\n");
/* 125 */                 items.remove(accessCopyId + "-" + sAssetId + "-" + entityId);
/* 126 */                 System.out.println("Found reordered items in version of asset " + sAssetId + "!");
/*    */               }
/*    */             }
/*    */           }
/* 130 */           else if (bItems)
/*    */           {
/* 132 */             if (sLine.matches(".*<id>[0-9]+</id>.*"))
/*    */             {
/* 134 */               itemId = sLine.replaceAll("(.*<id>)|(</id>.*)", "");
/*    */ 
/* 136 */               if (!itemId.equals(accessCopyId))
/*    */               {
/* 138 */                 iSeq++;
/* 139 */                 writer.write("UPDATE RelatedAsset SET SequenceNumber=" + iSeq + " WHERE ChildId IN (SELECT Id FROM Asset WHERE Author='" + itemId + "' AND AssetEntityId=" + entityId + ") AND ParentId IN (SELECT Id FROM Asset WHERE Author='" + sAssetId + "' AND AssetEntityId=" + parentEntityId + ");\n");
/*    */ 
/* 141 */                 items.add(itemId + "-" + sAssetId + "-" + entityId);
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/* 146 */         sLine = bReader.readLine();
/*    */       }
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 151 */       e.printStackTrace();
/* 152 */       System.exit(1);
/*    */     }
/*    */     finally
/*    */     {
/* 156 */       if (writer != null)
/*    */         try {
/* 158 */           writer.close();
/*    */         }
/*    */         catch (IOException e)
/*    */         {
/*    */         }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.datamigration.AssetRelationshipsBuilder
 * JD-Core Version:    0.6.0
 */