/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="DescribeRecordType", propOrder={"typeName"})
/*     */ public class DescribeRecordType extends RequestBaseType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="TypeName")
/*     */   protected List<QName> typeName;
/*     */ 
/*     */   @XmlAttribute(name="outputFormat")
/*     */   protected String outputFormat;
/*     */ 
/*     */   @XmlAttribute(name="schemaLanguage")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String schemaLanguage;
/*     */ 
/*     */   public List<QName> getTypeName()
/*     */   {
/*  91 */     if (this.typeName == null) {
/*  92 */       this.typeName = new ArrayList();
/*     */     }
/*  94 */     return this.typeName;
/*     */   }
/*     */ 
/*     */   public String getOutputFormat()
/*     */   {
/* 106 */     if (this.outputFormat == null) {
/* 107 */       return "application/xml";
/*     */     }
/* 109 */     return this.outputFormat;
/*     */   }
/*     */ 
/*     */   public void setOutputFormat(String value)
/*     */   {
/* 122 */     this.outputFormat = value;
/*     */   }
/*     */ 
/*     */   public String getSchemaLanguage()
/*     */   {
/* 134 */     if (this.schemaLanguage == null) {
/* 135 */       return "http://www.w3.org/XML/Schema";
/*     */     }
/* 137 */     return this.schemaLanguage;
/*     */   }
/*     */ 
/*     */   public void setSchemaLanguage(String value)
/*     */   {
/* 150 */     this.schemaLanguage = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.DescribeRecordType
 * JD-Core Version:    0.6.0
 */