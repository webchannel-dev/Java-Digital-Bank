/*    */ package com.bright.assetbank.test;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.FileReader;
/*    */ 
/*    */ public class RandomWordGenerator
/*    */ {
/* 32 */   private String m_sInputFile = null;
/* 33 */   private String[] m_asInput = null;
/*    */ 
/*    */   public RandomWordGenerator(String a_sInputFile)
/*    */     throws Bn2Exception
/*    */   {
/* 38 */     this.m_sInputFile = a_sInputFile;
/* 39 */     init();
/*    */   }
/*    */ 
/*    */   private void init()
/*    */     throws Bn2Exception
/*    */   {
/*    */     try
/*    */     {
/* 48 */       FileReader fReader = new FileReader(this.m_sInputFile);
/* 49 */       BufferedReader buff = new BufferedReader(fReader);
/*    */ 
/* 51 */       String sLine = null;
/* 52 */       StringBuffer sbInput = new StringBuffer();
/* 53 */       while ((sLine = buff.readLine()) != null)
/*    */       {
/* 55 */         sbInput.append(sLine);
/*    */       }
/*    */ 
/* 58 */       buff.close();
/* 59 */       fReader.close();
/*    */ 
/* 62 */       this.m_asInput = sbInput.toString().split(" ");
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 66 */       throw new Bn2Exception("Exception in RandomWordGenerator.init: ", e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getSentence(int a_iWordCount)
/*    */   {
/* 72 */     String sSentence = null;
/*    */ 
/* 74 */     for (int i = 0; i < a_iWordCount; i++)
/*    */     {
/* 77 */       int iIndex = (int)Math.round(Math.random() * (this.m_asInput.length - 1));
/*    */ 
/* 79 */       if (sSentence == null)
/*    */       {
/* 81 */         sSentence = "";
/*    */       }
/*    */       else
/*    */       {
/* 85 */         sSentence = sSentence + " ";
/*    */       }
/*    */ 
/* 89 */       sSentence = sSentence + this.m_asInput[iIndex];
/*    */     }
/*    */ 
/* 92 */     return sSentence;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.test.RandomWordGenerator
 * JD-Core Version:    0.6.0
 */