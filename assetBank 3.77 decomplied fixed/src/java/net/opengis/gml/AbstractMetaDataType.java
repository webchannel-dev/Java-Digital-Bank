/*     */ package net.opengis.gml;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlID;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlSeeAlso;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.XmlValue;
/*     */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="AbstractMetaDataType", propOrder={"content"})
/*     */ @XmlSeeAlso({GenericMetaDataType.class})
/*     */ public abstract class AbstractMetaDataType
/*     */ {
/*     */ 
/*     */   @XmlValue
/*     */   protected String content;
/*     */ 
/*     */   @XmlAttribute(name="id", namespace="http://www.opengis.net/gml")
/*     */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*     */   @XmlID
/*     */   @XmlSchemaType(name="ID")
/*     */   protected String id;
/*     */ 
/*     */   public String getContent()
/*     */   {
/*  68 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String value)
/*     */   {
/*  80 */     this.content = value;
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/*  92 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String value)
/*     */   {
/* 104 */     this.id = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.AbstractMetaDataType
 * JD-Core Version:    0.6.0
 */