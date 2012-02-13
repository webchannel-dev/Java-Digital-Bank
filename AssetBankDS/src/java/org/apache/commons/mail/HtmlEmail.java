/*     */ package org.apache.commons.mail;
/*     */ 
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.URLDataSource;
/*     */ import javax.mail.BodyPart;
/*     */ import javax.mail.MessagingException;
/*     */ import javax.mail.internet.MimeBodyPart;
/*     */ import javax.mail.internet.MimeMultipart;
/*     */ import org.apache.commons.lang.RandomStringUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HtmlEmail extends MultiPartEmail
/*     */ {
/*     */   private String text;
/*     */   private String html;
/*  91 */   private List inlineImages = new ArrayList();
/*     */ 
/*  93 */   private boolean m_bAlreadyPrepared = false;
/*     */ 
/*     */   public HtmlEmail setTextMsg(String text)
/*     */   {
/* 103 */     this.text = text;
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   public HtmlEmail setHtmlMsg(String html)
/*     */   {
/* 115 */     this.html = html;
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */   public Email setMsg(String msg)
/*     */   {
/* 133 */     setTextMsg(msg);
/*     */ 
/* 135 */     setHtmlMsg("<html><body><pre>" + msg + "</pre></body></html>");
/*     */ 
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   public String embed(String a_sFilepath, String a_sName)
/*     */     throws MessagingException
/*     */   {
/* 168 */     URL fileUrl = null;
/*     */     try
/*     */     {
/* 172 */       fileUrl = new URL("file:" + a_sFilepath);
/*     */     }
/*     */     catch (MalformedURLException me)
/*     */     {
/* 176 */       throw new MessagingException("Could not attach file: " + a_sFilepath + ". Error is: " + me.toString());
/*     */     }
/*     */ 
/* 179 */     MimeBodyPart mbp = new MimeBodyPart();
/*     */ 
/* 181 */     mbp.setDataHandler(new DataHandler(new URLDataSource(fileUrl)));
/* 182 */     mbp.setFileName(a_sName);
/* 183 */     mbp.setDisposition("inline");
/*     */ 
/* 186 */     String cid = RandomStringUtils.random(10, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
/* 187 */     mbp.addHeader("Content-ID", "<" + cid + ">");
/*     */ 
/* 189 */     this.inlineImages.add(mbp);
/* 190 */     return cid;
/*     */   }
/*     */ 
/*     */   public void send()
/*     */     throws MessagingException
/*     */   {
/* 201 */     if (!this.m_bAlreadyPrepared)
/*     */     {
/* 204 */       MimeMultipart container = getContainer();
/*     */ 
/* 206 */       BodyPart msgText = null;
/* 207 */       BodyPart msgHtml = null;
/*     */       Iterator iter;
/* 219 */       if (StringUtils.isNotEmpty(this.html))
/*     */       {
/* 221 */         msgHtml = getPrimaryBodyPart();
/*     */ 
/* 223 */         if (this.charset != null)
/*     */         {
/* 225 */           msgHtml.setContent(this.html, "text/html;charset=" + this.charset);
/*     */         }
/*     */         else
/*     */         {
/* 229 */           msgHtml.setContent(this.html, "text/html");
/*     */         }
/*     */ 
/* 233 */         for (iter = this.inlineImages.iterator(); iter.hasNext(); )
/*     */         {
/* 235 */           BodyPart image = (BodyPart)iter.next();
/*     */ 
/* 237 */           container.addBodyPart(image);
/*     */         }
/*     */       }
/*     */ 
/* 241 */       if (StringUtils.isNotEmpty(this.text))
/*     */       {
/* 243 */         String sContentType = "text/plain";
/*     */ 
/* 245 */         if (this.charset != null)
/*     */         {
/* 247 */           sContentType = sContentType + ";charset=" + this.charset;
/*     */         }
/*     */ 
/* 252 */         if (msgHtml == null)
/*     */         {
/* 254 */           msgText = getPrimaryBodyPart();
/* 255 */           msgText.setContent(this.text, sContentType);
/*     */         }
/*     */         else
/*     */         {
/* 259 */           msgText = new MimeBodyPart();
/* 260 */           msgText.setContent(this.text, sContentType);
/* 261 */           container.addBodyPart(msgText);
/*     */         }
/*     */       }
/*     */ 
/* 265 */       this.m_bAlreadyPrepared = true;
/*     */     }
/*     */ 
/* 268 */     super.send();
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final boolean isValid(String foo)
/*     */   {
/* 281 */     return StringUtils.isNotEmpty(foo);
/*     */   }
/*     */ 
/*     */   public String getHtmlMsg()
/*     */   {
/* 286 */     return this.html;
/*     */   }
/*     */ 
/*     */   public String getTextMsg()
/*     */   {
/* 291 */     return this.text;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.apache.commons.mail.HtmlEmail
 * JD-Core Version:    0.6.0
 */