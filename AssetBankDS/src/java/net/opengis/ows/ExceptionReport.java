/*     */ package net.opengis.ows;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="", propOrder={"exception"})
/*     */ @XmlRootElement(name="ExceptionReport")
/*     */ public class ExceptionReport
/*     */ {
/*     */ 
/*     */   @XmlElement(name="Exception", required=true)
/*     */   protected List<ExceptionType> exception;
/*     */ 
/*     */   @XmlAttribute(name="version", required=true)
/*     */   protected String version;
/*     */ 
/*     */   @XmlAttribute(name="language")
/*     */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*     */   @XmlSchemaType(name="language")
/*     */   protected String language;
/*     */ 
/*     */   public List<ExceptionType> getException()
/*     */   {
/*  84 */     if (this.exception == null) {
/*  85 */       this.exception = new ArrayList();
/*     */     }
/*  87 */     return this.exception;
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/*  99 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setVersion(String value)
/*     */   {
/* 111 */     this.version = value;
/*     */   }
/*     */ 
/*     */   public String getLanguage()
/*     */   {
/* 123 */     return this.language;
/*     */   }
/*     */ 
/*     */   public void setLanguage(String value)
/*     */   {
/* 135 */     this.language = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.ExceptionReport
 * JD-Core Version:    0.6.0
 */