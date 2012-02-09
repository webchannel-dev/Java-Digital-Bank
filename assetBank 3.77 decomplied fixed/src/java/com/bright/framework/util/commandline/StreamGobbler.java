/*     */ package com.bright.framework.util.commandline;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ 
/*     */ class StreamGobbler extends Thread
/*     */ {
/*  32 */   private InputStream m_inputStream = null;
/*  33 */   private StringBuffer m_sbOutput = null;
/*  34 */   private boolean m_bFinished = false;
/*     */   private String m_sCharSet;
/*     */ 
/*     */   public StreamGobbler(InputStream a_is, StringBuffer a_sbOutput, String a_sCharSet)
/*     */   {
/*  51 */     this.m_inputStream = a_is;
/*  52 */     this.m_sbOutput = a_sbOutput;
/*  53 */     this.m_sCharSet = a_sCharSet;
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*     */     try
/*     */     {
/*  71 */       InputStreamReader isr = new InputStreamReader(this.m_inputStream, this.m_sCharSet);
/*  72 */       BufferedReader br = new BufferedReader(isr);
/*  73 */       int iChar = -1;
/*     */ 
/*  75 */       while ((iChar = br.read()) != -1)
/*     */       {
/*  77 */         if (this.m_sbOutput == null)
/*     */           continue;
/*  79 */         this.m_sbOutput.append((char)iChar);
/*     */       }
/*     */ 
/*  84 */       if (this.m_sbOutput != null)
/*     */       {
/*  87 */         if ((this.m_sbOutput.length() > 0) && (this.m_sbOutput.charAt(this.m_sbOutput.length() - 1) == '\n'))
/*     */         {
/*  89 */           this.m_sbOutput.deleteCharAt(this.m_sbOutput.length() - 1);
/*     */         }
/*     */ 
/*  93 */         if ((this.m_sbOutput.length() > 0) && (this.m_sbOutput.charAt(this.m_sbOutput.length() - 1) == '\r'))
/*     */         {
/*  95 */           this.m_sbOutput.deleteCharAt(this.m_sbOutput.length() - 1);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 102 */       ioe.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 106 */       setFinished(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   private synchronized void setFinished(boolean a_bFinished)
/*     */   {
/* 112 */     this.m_bFinished = a_bFinished;
/*     */   }
/*     */ 
/*     */   public synchronized boolean hasFinished()
/*     */   {
/* 117 */     return this.m_bFinished;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.commandline.StreamGobbler
 * JD-Core Version:    0.6.0
 */