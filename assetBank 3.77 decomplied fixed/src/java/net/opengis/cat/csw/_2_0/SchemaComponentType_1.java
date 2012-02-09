/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAnyElement;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlMixed;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="SchemaComponentType", propOrder={"content"})
/*     */ public class SchemaComponentType_1
/*     */ {
/*     */ 
/*     */   @XmlMixed
/*     */   @XmlAnyElement(lax=true)
/*     */   protected List<Object> content;
/*     */ 
/*     */   @XmlAttribute(name="targetNamespace", required=true)
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String targetNamespace;
/*     */ 
/*     */   @XmlAttribute(name="parentSchema")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String parentSchema;
/*     */ 
/*     */   @XmlAttribute(name="schemaLanguage", required=true)
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String schemaLanguage;
/*     */ 
/*     */   public List<Object> getContent()
/*     */   {
/*  97 */     if (this.content == null) {
/*  98 */       this.content = new ArrayList();
/*     */     }
/* 100 */     return this.content;
/*     */   }
/*     */ 
/*     */   public String getTargetNamespace()
/*     */   {
/* 112 */     return this.targetNamespace;
/*     */   }
/*     */ 
/*     */   public void setTargetNamespace(String value)
/*     */   {
/* 124 */     this.targetNamespace = value;
/*     */   }
/*     */ 
/*     */   public String getParentSchema()
/*     */   {
/* 136 */     return this.parentSchema;
/*     */   }
/*     */ 
/*     */   public void setParentSchema(String value)
/*     */   {
/* 148 */     this.parentSchema = value;
/*     */   }
/*     */ 
/*     */   public String getSchemaLanguage()
/*     */   {
/* 160 */     return this.schemaLanguage;
/*     */   }
/*     */ 
/*     */   public void setSchemaLanguage(String value)
/*     */   {
/* 172 */     this.schemaLanguage = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.SchemaComponentType
 * JD-Core Version:    0.6.0
 */