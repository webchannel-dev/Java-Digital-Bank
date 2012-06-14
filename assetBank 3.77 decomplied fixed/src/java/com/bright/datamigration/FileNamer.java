/*    */ package com.bright.datamigration;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.PrintStream;
/*    */ import org.apache.commons.io.FileUtils;
/*    */ 
/*    */ public class FileNamer
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 18 */     String sInputDir = args[0];
/* 19 */     String sOutputDir = args[1];
/*    */     try
/*    */     {
/* 23 */       File directory = new File(sInputDir);
/* 24 */       File[] files = directory.listFiles();
/*    */ 
/* 26 */       for (int i = 0; i < files.length; i++)
/*    */       {
/* 28 */         File file = files[i];
/* 29 */         InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
/* 30 */         BufferedReader bReader = new BufferedReader(reader);
/*    */ 
/* 32 */         String sLine = bReader.readLine();
/* 33 */         String sContents = "";
/* 34 */         boolean bDone = false;
/* 35 */         String sName = "";
/* 36 */         while (sLine != null)
/*    */         {
/* 38 */           if ((sLine.matches(".*<id>[0-9]+</id>.*")) && (!bDone))
/*    */           {
/* 40 */             char[] chars = sLine.toCharArray();
/* 41 */             boolean bStoreChars = false;
/* 42 */             for (int x = 0; x < chars.length; x++)
/*    */             {
/* 44 */               if ((chars[x] == '<') && (bStoreChars))
/*    */               {
/*    */                 break;
/*    */               }
/*    */ 
/* 49 */               if (bStoreChars)
/*    */               {
/* 51 */                 sName = sName + chars[x];
/*    */               }
/*    */ 
/* 54 */               if (chars[x] != '>')
/*    */                 continue;
/* 56 */               bStoreChars = true;
/*    */             }
/*    */ 
/* 59 */             sName = sName + ".xml";
/* 60 */             bDone = true;
/*    */           }
/* 62 */           sContents = sContents + sLine;
/* 63 */           sLine = bReader.readLine();
/*    */         }
/*    */ 
/* 66 */         File actualFile = new File(sOutputDir + "/" + sName);
/* 67 */         FileUtils.writeStringToFile(actualFile, sContents, "UTF-8");
/* 68 */         System.out.println("OUTPUT FILE : " + file.getName() + " TO : " + actualFile.getName());
/*    */       }
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 73 */       e.printStackTrace();
/* 74 */       System.exit(1);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.datamigration.FileNamer
 * JD-Core Version:    0.6.0
 */