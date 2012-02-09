/*     */ package net.opengis.ows;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="ExceptionType", propOrder={"exceptionText"})
/*     */ public class ExceptionType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="ExceptionText")
/*     */   protected List<String> exceptionText;
/*     */ 
/*     */   @XmlAttribute(name="exceptionCode", required=true)
/*     */   protected String exceptionCode;
/*     */ 
/*     */   @XmlAttribute(name="locator")
/*     */   protected String locator;
/*     */ 
/*     */   public List<String> getExceptionText()
/*     */   {
/*  79 */     if (this.exceptionText == null) {
/*  80 */       this.exceptionText = new ArrayList();
/*     */     }
/*  82 */     return this.exceptionText;
/*     */   }
/*     */ 
/*     */   public String getExceptionCode()
/*     */   {
/*  94 */     return this.exceptionCode;
/*     */   }
/*     */ 
/*     */   public void setExceptionCode(String value)
/*     */   {
/* 106 */     this.exceptionCode = value;
/*     */   }
/*     */ 
/*     */   public String getLocator()
/*     */   {
/* 118 */     return this.locator;
/*     */   }
/*     */ 
/*     */   public void setLocator(String value)
/*     */   {
/* 130 */     this.locator = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.ExceptionType
 * JD-Core Version:    0.6.0
 */