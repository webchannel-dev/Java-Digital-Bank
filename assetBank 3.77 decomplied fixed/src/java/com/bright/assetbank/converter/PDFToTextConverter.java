/*     */ package com.bright.assetbank.converter;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.pdfbox.pdfparser.PDFParser;
/*     */ import org.pdfbox.pdmodel.PDDocument;
/*     */ import org.pdfbox.util.PDFTextStripper;
/*     */ 
/*     */ public class PDFToTextConverter
/*     */   implements AssetToTextConverter
/*     */ {
/*     */   public String getText(String a_sFullPath)
/*     */     throws Bn2Exception
/*     */   {
/*  56 */     String sText = null;
/*  57 */     PDDocument pDocument = null;
/*  58 */     InputStream in = null;
/*     */     try
/*     */     {
/*  64 */       in = new FileInputStream(a_sFullPath);
/*     */ 
/*  67 */       PDFParser parser = new PDFParser(in);
/*  68 */       parser.parse();
/*     */ 
/*  71 */       pDocument = parser.getPDDocument();
/*     */ 
/*  74 */       PDFTextStripper stripper = new PDFTextStripper();
/*  75 */       sText = stripper.getText(pDocument);
/*     */     }
/*     */     catch (FileNotFoundException fnf)
/*     */     {
/*  80 */       throw new Bn2Exception("PDFToTextConverter: file not found exception " + fnf.getMessage(), fnf);
/*     */     }
/*     */     catch (IOException io)
/*     */     {
/*  84 */       throw new Bn2Exception("PDFToTextConverter: file not found exception " + io.getMessage(), io);
/*     */     }
/*     */     catch (Throwable t)
/*     */     {
/*  88 */       GlobalApplication.getInstance().getLogger().error("Exception whilst reading PDF: " + t.getMessage(), t);
/*     */     }
/*     */     finally
/*     */     {
/*  93 */       if (pDocument != null)
/*     */       {
/*     */         try
/*     */         {
/*  97 */           pDocument.close();
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/* 101 */           e.printStackTrace();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 106 */       if (in != null)
/*     */       {
/*     */         try
/*     */         {
/* 110 */           in.close();
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/* 114 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 119 */     return sText;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.converter.PDFToTextConverter
 * JD-Core Version:    0.6.0
 */