/*     */ package org.apache.commons.mail;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.activation.FileDataSource;
/*     */ import javax.activation.URLDataSource;
/*     */ import javax.mail.MessagingException;
/*     */ import javax.mail.internet.MimeBodyPart;
/*     */ import javax.mail.internet.MimeMultipart;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class MultiPartEmail extends Email
/*     */ {
/*  52 */   private MimeMultipart container = null;
/*     */ 
/*  55 */   private MimeBodyPart primaryBodyPart = null;
/*     */ 
/*  58 */   private boolean initialized = false;
/*     */ 
/*     */   private void init()
/*     */     throws MessagingException
/*     */   {
/*  67 */     if (this.initialized)
/*     */     {
/*  69 */       throw new IllegalStateException("Already initialized");
/*     */     }
/*     */ 
/*  72 */     this.container = new MimeMultipart();
/*  73 */     super.setContent(this.container);
/*     */ 
/*  76 */     this.primaryBodyPart = new MimeBodyPart();
/*  77 */     this.container.addBodyPart(this.primaryBodyPart);
/*     */ 
/*  79 */     this.initialized = true;
/*     */   }
/*     */ 
/*     */   public Email setMsg(String msg)
/*     */     throws MessagingException
/*     */   {
/*  91 */     if (this.charset != null)
/*     */     {
/*  93 */       getPrimaryBodyPart().setText(msg, this.charset);
/*     */     }
/*     */     else
/*     */     {
/*  97 */       getPrimaryBodyPart().setText(msg);
/*     */     }
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */   public void send()
/*     */     throws MessagingException
/*     */   {
/* 113 */     MimeBodyPart body = getPrimaryBodyPart();
/* 114 */     Object content = null;
/*     */     try
/*     */     {
/* 117 */       content = body.getContent();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */     }
/*     */ 
/* 124 */     if (content == null)
/*     */     {
/* 126 */       body.setContent("", "text/plain");
/*     */     }
/*     */ 
/* 129 */     super.send();
/*     */   }
/*     */ 
/*     */   public MultiPartEmail attach(EmailAttachment attachment)
/*     */     throws MessagingException
/*     */   {
/* 142 */     MultiPartEmail result = null;
/*     */ 
/* 144 */     URL url = attachment.getURL();
/* 145 */     if (url == null)
/*     */     {
/* 147 */       String fileName = null;
/*     */       try
/*     */       {
/* 150 */         fileName = attachment.getPath();
/* 151 */         File file = new File(fileName);
/* 152 */         if (!file.exists())
/*     */         {
/* 154 */           throw new IOException("\"" + fileName + "\" does not exist");
/*     */         }
/* 156 */         result = attach(new FileDataSource(file), attachment.getName(), attachment.getDescription(), attachment.getDisposition());
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 162 */         throw new MessagingException("Cannot attach file \"" + fileName + "\"", e);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 168 */       result = attach(url, attachment.getName(), attachment.getDescription(), attachment.getDisposition());
/*     */     }
/*     */ 
/* 172 */     return result;
/*     */   }
/*     */ 
/*     */   public MultiPartEmail attach(URL url, String name, String description)
/*     */     throws MessagingException
/*     */   {
/* 188 */     return attach(url, name, description, "attachment");
/*     */   }
/*     */ 
/*     */   public MultiPartEmail attach(URL url, String name, String description, String disposition)
/*     */     throws MessagingException
/*     */   {
/* 205 */     return attach(new URLDataSource(url), name, description, disposition);
/*     */   }
/*     */ 
/*     */   public MultiPartEmail attach(DataSource ds, String name, String description)
/*     */     throws MessagingException
/*     */   {
/* 221 */     return attach(ds, name, description, "attachment");
/*     */   }
/*     */ 
/*     */   public MultiPartEmail attach(DataSource ds, String name, String description, String disposition)
/*     */     throws MessagingException
/*     */   {
/* 238 */     MimeBodyPart mbp = new MimeBodyPart();
/* 239 */     getContainer().addBodyPart(mbp);
/*     */ 
/* 241 */     mbp.setDisposition(disposition);
/* 242 */     if (StringUtils.isEmpty(name))
/*     */     {
/* 244 */       name = ds.getName();
/*     */     }
/* 246 */     mbp.setFileName(name);
/* 247 */     mbp.setDescription(description);
/* 248 */     mbp.setDataHandler(new DataHandler(ds));
/*     */ 
/* 250 */     return this;
/*     */   }
/*     */ 
/*     */   protected MimeBodyPart getPrimaryBodyPart()
/*     */     throws MessagingException
/*     */   {
/* 261 */     if (!this.initialized) {
/* 262 */       init();
/*     */     }
/* 264 */     return this.primaryBodyPart;
/*     */   }
/*     */ 
/*     */   protected MimeMultipart getContainer()
/*     */     throws MessagingException
/*     */   {
/* 275 */     if (!this.initialized) {
/* 276 */       init();
/*     */     }
/* 278 */     return this.container;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.apache.commons.mail.MultiPartEmail
 * JD-Core Version:    0.6.0
 */