/*     */ package org.purl.dc.elements._1;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlMixed;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="SimpleLiteral", propOrder={"content"})
/*     */ public class SimpleLiteral
/*     */ {
/*     */ 
/*     */   @XmlMixed
/*     */   protected List<String> content;
/*     */ 
/*     */   @XmlAttribute(name="scheme")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String scheme;
/*     */ 
/*     */   public List<String> getContent()
/*     */   {
/*  88 */     if (this.content == null) {
/*  89 */       this.content = new ArrayList();
/*     */     }
/*  91 */     return this.content;
/*     */   }
/*     */ 
/*     */   public String getScheme()
/*     */   {
/* 103 */     return this.scheme;
/*     */   }
/*     */ 
/*     */   public void setScheme(String value)
/*     */   {
/* 115 */     this.scheme = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.purl.dc.elements._1.SimpleLiteral
 * JD-Core Version:    0.6.0
 */