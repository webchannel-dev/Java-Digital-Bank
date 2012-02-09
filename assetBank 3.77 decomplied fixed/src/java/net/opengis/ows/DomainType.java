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
/*     */ @XmlType(name="DomainType", propOrder={"value", "metadata"})
/*     */ public class DomainType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="Value", required=true)
/*     */   protected List<String> value;
/*     */ 
/*     */   @XmlElement(name="Metadata")
/*     */   protected List<MetadataType> metadata;
/*     */ 
/*     */   @XmlAttribute(name="name", required=true)
/*     */   protected String name;
/*     */ 
/*     */   public List<String> getValue()
/*     */   {
/*  80 */     if (this.value == null) {
/*  81 */       this.value = new ArrayList();
/*     */     }
/*  83 */     return this.value;
/*     */   }
/*     */ 
/*     */   public List<MetadataType> getMetadata()
/*     */   {
/* 109 */     if (this.metadata == null) {
/* 110 */       this.metadata = new ArrayList();
/*     */     }
/* 112 */     return this.metadata;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 124 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String value)
/*     */   {
/* 136 */     this.name = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.DomainType
 * JD-Core Version:    0.6.0
 */