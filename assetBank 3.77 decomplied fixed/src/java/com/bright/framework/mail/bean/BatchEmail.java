/*     */ package com.bright.framework.mail.bean;
/*     */ 
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class BatchEmail
/*     */ {
/*  28 */   private Vector m_vecRecipients = new Vector();
/*  29 */   private String m_sSubject = null;
/*  30 */   private String m_sBody = null;
/*  31 */   private String m_sDirectoryContainingHtmlFile = null;
/*  32 */   private String m_sHtmlFilename = null;
/*  33 */   private String m_sFromAddress = null;
/*  34 */   private String m_sHtmlCharEncoding = null;
/*     */ 
/*     */   public void addRecipient(BatchRecipient a_recipient)
/*     */   {
/*  49 */     this.m_vecRecipients.add(a_recipient);
/*     */   }
/*     */ 
/*     */   public Vector getRecipients()
/*     */   {
/*  54 */     return this.m_vecRecipients;
/*     */   }
/*     */ 
/*     */   public void setSubject(String a_sSubject)
/*     */   {
/*  59 */     this.m_sSubject = a_sSubject;
/*     */   }
/*     */ 
/*     */   public String getSubject()
/*     */   {
/*  64 */     return this.m_sSubject;
/*     */   }
/*     */ 
/*     */   public void setBody(String a_sBody)
/*     */   {
/*  69 */     this.m_sBody = a_sBody;
/*     */   }
/*     */ 
/*     */   public String getBody()
/*     */   {
/*  74 */     return this.m_sBody;
/*     */   }
/*     */ 
/*     */   public void setHtmlFilename(String a_sHtmlFilename)
/*     */   {
/*  79 */     this.m_sHtmlFilename = a_sHtmlFilename;
/*     */   }
/*     */ 
/*     */   public String getHtmlFilename()
/*     */   {
/*  84 */     return this.m_sHtmlFilename;
/*     */   }
/*     */ 
/*     */   public void setDirectoryContainingHtmlFile(String a_sDirectoryContainingHtmlFile)
/*     */   {
/*  89 */     this.m_sDirectoryContainingHtmlFile = a_sDirectoryContainingHtmlFile;
/*     */   }
/*     */ 
/*     */   public String getDirectoryContainingHtmlFile()
/*     */   {
/*  94 */     return this.m_sDirectoryContainingHtmlFile;
/*     */   }
/*     */ 
/*     */   public void setFromAddress(String a_sFromAddress)
/*     */   {
/*  99 */     this.m_sFromAddress = a_sFromAddress;
/*     */   }
/*     */ 
/*     */   public String getFromAddress()
/*     */   {
/* 104 */     return this.m_sFromAddress;
/*     */   }
/*     */ 
/*     */   public String getHtmlCharEncoding()
/*     */   {
/* 109 */     return this.m_sHtmlCharEncoding;
/*     */   }
/*     */ 
/*     */   public void setHtmlCharEncoding(String a_sHtmlCharEncoding)
/*     */   {
/* 114 */     this.m_sHtmlCharEncoding = a_sHtmlCharEncoding;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.bean.BatchEmail
 * JD-Core Version:    0.6.0
 */