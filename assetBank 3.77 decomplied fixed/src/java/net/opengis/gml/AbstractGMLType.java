/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlID;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlSeeAlso;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="AbstractGMLType", propOrder={"metaDataProperty", "description", "name"})
/*     */ @XmlSeeAlso({ArrayType.class, DefinitionType.class, BagType.class, AbstractGeometryType.class})
/*     */ public abstract class AbstractGMLType
/*     */ {
/*     */   protected List<MetaDataPropertyType> metaDataProperty;
/*     */   protected StringOrRefType description;
/*     */   protected List<CodeType> name;
/*     */ 
/*     */   @XmlAttribute(name="id", namespace="http://www.opengis.net/gml")
/*     */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*     */   @XmlID
/*     */   @XmlSchemaType(name="ID")
/*     */   protected String id;
/*     */ 
/*     */   public List<MetaDataPropertyType> getMetaDataProperty()
/*     */   {
/*  94 */     if (this.metaDataProperty == null) {
/*  95 */       this.metaDataProperty = new ArrayList();
/*     */     }
/*  97 */     return this.metaDataProperty;
/*     */   }
/*     */ 
/*     */   public StringOrRefType getDescription()
/*     */   {
/* 109 */     return this.description;
/*     */   }
/*     */ 
/*     */   public void setDescription(StringOrRefType value)
/*     */   {
/* 121 */     this.description = value;
/*     */   }
/*     */ 
/*     */   public List<CodeType> getName()
/*     */   {
/* 147 */     if (this.name == null) {
/* 148 */       this.name = new ArrayList();
/*     */     }
/* 150 */     return this.name;
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/* 162 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String value)
/*     */   {
/* 174 */     this.id = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.AbstractGMLType
 * JD-Core Version:    0.6.0
 */