/*    */ package com.bright.datamigration;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.FileReader;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class TextFileIdCollator
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/*    */     try
/*    */     {
/* 41 */       BufferedReader reader = new BufferedReader(new FileReader(args[0]));
/* 42 */       BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
/*    */ 
/* 44 */       String line = null;
/* 45 */       String outputLine = null;
/* 46 */       long lastId = 0L;
/*    */ 
/* 48 */       while ((line = reader.readLine()) != null)
/*    */       {
/* 50 */         String[] ids = line.split("\t");
/*    */         try
/*    */         {
/* 54 */           if (ids.length > 1)
/*    */           {
/* 56 */             long id = Long.parseLong(ids[0]);
/*    */ 
/* 58 */             if (id == lastId)
/*    */             {
/* 60 */               outputLine = outputLine + "," + ids[1];
/*    */             }
/*    */             else
/*    */             {
/* 64 */               if (outputLine != null)
/*    */               {
/* 66 */                 writer.write(outputLine + "\n");
/*    */               }
/* 68 */               outputLine = line;
/* 69 */               lastId = id;
/*    */             }
/*    */           }
/*    */         }
/*    */         catch (NumberFormatException e)
/*    */         {
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/* 79 */       if (outputLine != null)
/*    */       {
/* 81 */         writer.write(outputLine + "\n");
/*    */       }
/* 83 */       writer.flush();
/* 84 */       writer.close();
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 88 */       e.printStackTrace();
/* 89 */       System.exit(1);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 93 */       e.printStackTrace();
/* 94 */       System.exit(1);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.datamigration.TextFileIdCollator
 * JD-Core Version:    0.6.0
 */