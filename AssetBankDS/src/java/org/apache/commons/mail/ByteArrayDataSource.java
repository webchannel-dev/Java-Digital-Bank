/*     */ package org.apache.commons.mail;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import javax.activation.DataSource;
/*     */ 
/*     */ /** @deprecated */
/*     */ public class ByteArrayDataSource
/*     */   implements DataSource
/*     */ {
/*  46 */   private ByteArrayOutputStream baos = null;
/*     */ 
/*  49 */   private String type = "application/octet-stream";
/*     */ 
/*     */   public ByteArrayDataSource(byte[] data, String type)
/*     */     throws IOException
/*     */   {
/*  61 */     ByteArrayInputStream Bis = null;
/*     */     try
/*     */     {
/*  65 */       Bis = new ByteArrayInputStream(data);
/*  66 */       byteArrayDataSource(Bis, type);
/*     */     }
/*     */     catch (IOException ioex)
/*     */     {
/*  70 */       throw ioex;
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/*  76 */         if (Bis != null)
/*     */         {
/*  78 */           Bis.close();
/*     */         }
/*     */       }
/*     */       catch (IOException ignored)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public ByteArrayDataSource(InputStream aIs, String type)
/*     */     throws IOException
/*     */   {
/*  97 */     byteArrayDataSource(aIs, type);
/*     */   }
/*     */ 
/*     */   private void byteArrayDataSource(InputStream aIs, String type)
/*     */     throws IOException
/*     */   {
/* 110 */     this.type = type;
/*     */ 
/* 112 */     BufferedInputStream Bis = null;
/* 113 */     BufferedOutputStream osWriter = null;
/*     */     try
/*     */     {
/* 117 */       int length = 0;
/* 118 */       byte[] buffer = new byte[512];
/*     */ 
/* 120 */       Bis = new BufferedInputStream(aIs);
/* 121 */       this.baos = new ByteArrayOutputStream();
/* 122 */       osWriter = new BufferedOutputStream(this.baos);
/*     */ 
/* 125 */       while ((length = Bis.read(buffer)) != -1)
/*     */       {
/* 127 */         osWriter.write(buffer, 0, length);
/*     */       }
/* 129 */       osWriter.flush();
/* 130 */       osWriter.close();
/*     */     }
/*     */     catch (IOException ioex)
/*     */     {
/* 135 */       throw ioex;
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 141 */         if (Bis != null)
/*     */         {
/* 143 */           Bis.close();
/*     */         }
/* 145 */         if (this.baos != null)
/*     */         {
/* 147 */           this.baos.close();
/*     */         }
/* 149 */         if (osWriter != null)
/*     */         {
/* 151 */           osWriter.close();
/*     */         }
/*     */       }
/*     */       catch (IOException ignored)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public ByteArrayDataSource(String data, String type)
/*     */   {
/* 169 */     this.type = type;
/*     */     try
/*     */     {
/* 173 */       this.baos = new ByteArrayOutputStream();
/*     */ 
/* 178 */       this.baos.write(data.getBytes("iso-8859-1"));
/* 179 */       this.baos.flush();
/* 180 */       this.baos.close();
/*     */     }
/*     */     catch (UnsupportedEncodingException uex)
/*     */     {
/*     */     }
/*     */     catch (IOException ignored)
/*     */     {
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 194 */         if (this.baos != null)
/*     */         {
/* 196 */           this.baos.close();
/*     */         }
/*     */       }
/*     */       catch (IOException ignored)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getContentType()
/*     */   {
/* 212 */     return this.type == null ? "application/octet-stream" : this.type;
/*     */   }
/*     */ 
/*     */   public InputStream getInputStream()
/*     */     throws IOException
/*     */   {
/* 224 */     if (this.baos == null)
/*     */     {
/* 226 */       throw new IOException("no data");
/*     */     }
/* 228 */     return new ByteArrayInputStream(this.baos.toByteArray());
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 238 */     return "ByteArrayDataSource";
/*     */   }
/*     */ 
/*     */   public OutputStream getOutputStream()
/*     */     throws IOException
/*     */   {
/* 250 */     this.baos = new ByteArrayOutputStream();
/* 251 */     return this.baos;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.apache.commons.mail.ByteArrayDataSource
 * JD-Core Version:    0.6.0
 */