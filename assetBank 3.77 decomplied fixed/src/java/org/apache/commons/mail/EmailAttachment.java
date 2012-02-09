/*     */ package org.apache.commons.mail;
/*     */ 
/*     */ import java.net.URL;
/*     */ 
/*     */ public class EmailAttachment
/*     */ {
/*     */   public static final String ATTACHMENT = "attachment";
/*     */   public static final String INLINE = "inline";
/*  32 */   private String name = "";
/*     */ 
/*  35 */   private String description = "";
/*     */ 
/*  38 */   private String path = "";
/*     */ 
/*  41 */   private URL url = null;
/*     */ 
/*  44 */   private String disposition = "attachment";
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  53 */     return this.description;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  63 */     return this.name;
/*     */   }
/*     */ 
/*     */   public String getPath()
/*     */   {
/*  73 */     return this.path;
/*     */   }
/*     */ 
/*     */   public URL getURL()
/*     */   {
/*  83 */     return this.url;
/*     */   }
/*     */ 
/*     */   public String getDisposition()
/*     */   {
/*  93 */     return this.disposition;
/*     */   }
/*     */ 
/*     */   public void setDescription(String desc)
/*     */   {
/* 103 */     this.description = desc;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 113 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public void setPath(String path)
/*     */   {
/* 127 */     this.path = path;
/*     */   }
/*     */ 
/*     */   public void setURL(URL url)
/*     */   {
/* 137 */     this.url = url;
/*     */   }
/*     */ 
/*     */   public void setDisposition(String disposition)
/*     */   {
/* 147 */     this.disposition = disposition;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.apache.commons.mail.EmailAttachment
 * JD-Core Version:    0.6.0
 */