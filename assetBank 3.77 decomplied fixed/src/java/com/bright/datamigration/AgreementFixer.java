/*    */ package com.bright.datamigration;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileWriter;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class AgreementFixer
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 18 */     String sBrokeListFilename = "output.sql";
/* 19 */     String sSQLFilename = "agreements.sql";
/* 20 */     String sOutputFile = "fixagreements.sql";
/* 21 */     FileWriter writer = null;
/* 22 */     String sStart = "INSERT INTO AssetAgreement (AgreementId,AssetId,DateActivated,IsCurrent) VALUES ((SELECT LAST_INSERT_ID()),(SELECT a.Id FROM Asset a WHERE a.Author='";
/* 23 */     String sMiddle = "' AND a.AssetEntityId=";
/*    */     try
/*    */     {
/* 26 */       writer = new FileWriter("C:\\Temp\\" + sOutputFile);
/*    */ 
/* 28 */       InputStreamReader reader = new InputStreamReader(new FileInputStream("C:\\Temp\\" + sBrokeListFilename), "UTF-8");
/* 29 */       BufferedReader bReader = new BufferedReader(reader);
/* 30 */       String sLine = bReader.readLine();
/* 31 */       String sCurrentInsert = "";
/* 32 */       int iCounter = 0;
/*    */ 
/* 34 */       while (sLine != null)
/*    */       {
/* 36 */         String[] asPair = sLine.split("\t");
/* 37 */         String sAuthor = asPair[0];
/*    */ 
/* 39 */         if (!sAuthor.equals("NULL"))
/*    */         {
/* 41 */           String sEntityId = asPair[1];
/*    */ 
/* 43 */           System.out.println("CHECKING AUTHOR = " + sAuthor + " ENTITY = " + sEntityId);
/*    */ 
/* 45 */           String sTempInsert = null;
/* 46 */           String sTempMap = null;
/*    */ 
/* 49 */           InputStreamReader sqlReader = new InputStreamReader(new FileInputStream("C:\\Temp\\" + sSQLFilename));
/* 50 */           BufferedReader bSQLReader = new BufferedReader(sqlReader);
/* 51 */           String sSQLLine = bSQLReader.readLine();
/*    */ 
/* 53 */           while (sSQLLine != null)
/*    */           {
/* 55 */             if (sSQLLine.startsWith("INSERT INTO Agreement "))
/*    */             {
/* 57 */               sCurrentInsert = "";
/* 58 */               while (!sSQLLine.endsWith("');"))
/*    */               {
/* 60 */                 sCurrentInsert = sCurrentInsert + sSQLLine;
/* 61 */                 sCurrentInsert = sCurrentInsert + System.getProperty("line.separator");
/* 62 */                 sSQLLine = bSQLReader.readLine();
/*    */               }
/* 64 */               sCurrentInsert = sCurrentInsert + sSQLLine;
/*    */             }
/*    */ 
/* 67 */             if (sSQLLine.startsWith(sStart + sAuthor + sMiddle + sEntityId))
/*    */             {
/* 70 */               sTempMap = sSQLLine;
/* 71 */               sTempInsert = sCurrentInsert;
/* 72 */               sCurrentInsert = "";
/*    */             }
/* 74 */             sSQLLine = bSQLReader.readLine();
/*    */           }
/*    */ 
/* 77 */           bSQLReader.close();
/* 78 */           sqlReader.close();
/*    */ 
/* 81 */           if ((sTempMap != null) && (sTempInsert != null))
/*    */           {
/* 83 */             writer.write(sTempInsert);
/* 84 */             writer.write(System.getProperty("line.separator"));
/* 85 */             writer.write(sTempMap);
/* 86 */             writer.write(System.getProperty("line.separator"));
/* 87 */             iCounter++;
/* 88 */             System.out.println("WROTE ENTRY " + iCounter);
/*    */           }
/*    */         }
/*    */ 
/* 92 */         sLine = bReader.readLine();
/*    */       }
/* 94 */       System.out.println("WROTE " + iCounter + " ENTRIES TO FIX FILE");
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 98 */       System.out.println("ERROR: " + e.getMessage());
/*    */     }
/*    */     finally
/*    */     {
/*    */       try
/*    */       {
/* 104 */         writer.close();
/*    */       }
/*    */       catch (Exception e)
/*    */       {
/* 108 */         e.printStackTrace();
/* 109 */         System.exit(1);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.datamigration.AgreementFixer
 * JD-Core Version:    0.6.0
 */