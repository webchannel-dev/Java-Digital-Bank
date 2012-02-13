/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="ConceptualSchemeType", propOrder={"name", "document", "authority"})
/*     */ public class ConceptualSchemeType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="Name", required=true)
/*     */   protected String name;
/*     */ 
/*     */   @XmlElement(name="Document", required=true)
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String document;
/*     */ 
/*     */   @XmlElement(name="Authority", required=true)
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String authority;
/*     */ 
/*     */   public String getName()
/*     */   {
/*  65 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String value)
/*     */   {
/*  77 */     this.name = value;
/*     */   }
/*     */ 
/*     */   public String getDocument()
/*     */   {
/*  89 */     return this.document;
/*     */   }
/*     */ 
/*     */   public void setDocument(String value)
/*     */   {
/* 101 */     this.document = value;
/*     */   }
/*     */ 
/*     */   public String getAuthority()
/*     */   {
/* 113 */     return this.authority;
/*     */   }
/*     */ 
/*     */   public void setAuthority(String value)
/*     */   {
/* 125 */     this.authority = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.ConceptualSchemeType
 * JD-Core Version:    0.6.0
 */