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
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="GetRecordByIdType", propOrder={"id", "elementSetName"})
/*     */ public class GetRecordByIdType extends RequestBaseType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="Id", required=true)
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected List<String> id;
/*     */ 
/*     */   @XmlElement(name="ElementSetName", defaultValue="summary")
/*     */   protected ElementSetNameType elementSetName;
/*     */ 
/*     */   @XmlAttribute(name="outputFormat")
/*     */   protected String outputFormat;
/*     */ 
/*     */   @XmlAttribute(name="outputSchema")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String outputSchema;
/*     */ 
/*     */   public List<String> getId()
/*     */   {
/*  95 */     if (this.id == null) {
/*  96 */       this.id = new ArrayList();
/*     */     }
/*  98 */     return this.id;
/*     */   }
/*     */ 
/*     */   public ElementSetNameType getElementSetName()
/*     */   {
/* 110 */     return this.elementSetName;
/*     */   }
/*     */ 
/*     */   public void setElementSetName(ElementSetNameType value)
/*     */   {
/* 122 */     this.elementSetName = value;
/*     */   }
/*     */ 
/*     */   public String getOutputFormat()
/*     */   {
/* 134 */     if (this.outputFormat == null) {
/* 135 */       return "application/xml";
/*     */     }
/* 137 */     return this.outputFormat;
/*     */   }
/*     */ 
/*     */   public void setOutputFormat(String value)
/*     */   {
/* 150 */     this.outputFormat = value;
/*     */   }
/*     */ 
/*     */   public String getOutputSchema()
/*     */   {
/* 162 */     return this.outputSchema;
/*     */   }
/*     */ 
/*     */   public void setOutputSchema(String value)
/*     */   {
/* 174 */     this.outputSchema = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.GetRecordByIdType
 * JD-Core Version:    0.6.0
 */