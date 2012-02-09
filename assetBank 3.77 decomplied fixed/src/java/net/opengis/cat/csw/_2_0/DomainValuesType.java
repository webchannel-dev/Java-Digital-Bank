/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="DomainValuesType", propOrder={"propertyName", "parameterName", "listOfValues", "conceptualScheme", "rangeOfValues"})
/*     */ public class DomainValuesType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="PropertyName")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String propertyName;
/*     */ 
/*     */   @XmlElement(name="ParameterName")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String parameterName;
/*     */ 
/*     */   @XmlElement(name="ListOfValues")
/*     */   protected ListOfValuesType listOfValues;
/*     */ 
/*     */   @XmlElement(name="ConceptualScheme")
/*     */   protected ConceptualSchemeType conceptualScheme;
/*     */ 
/*     */   @XmlElement(name="RangeOfValues")
/*     */   protected RangeOfValuesType rangeOfValues;
/*     */ 
/*     */   @XmlAttribute(name="type", required=true)
/*     */   protected QName type;
/*     */ 
/*     */   @XmlAttribute(name="uom")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String uom;
/*     */ 
/*     */   public String getPropertyName()
/*     */   {
/*  86 */     return this.propertyName;
/*     */   }
/*     */ 
/*     */   public void setPropertyName(String value)
/*     */   {
/*  98 */     this.propertyName = value;
/*     */   }
/*     */ 
/*     */   public String getParameterName()
/*     */   {
/* 110 */     return this.parameterName;
/*     */   }
/*     */ 
/*     */   public void setParameterName(String value)
/*     */   {
/* 122 */     this.parameterName = value;
/*     */   }
/*     */ 
/*     */   public ListOfValuesType getListOfValues()
/*     */   {
/* 134 */     return this.listOfValues;
/*     */   }
/*     */ 
/*     */   public void setListOfValues(ListOfValuesType value)
/*     */   {
/* 146 */     this.listOfValues = value;
/*     */   }
/*     */ 
/*     */   public ConceptualSchemeType getConceptualScheme()
/*     */   {
/* 158 */     return this.conceptualScheme;
/*     */   }
/*     */ 
/*     */   public void setConceptualScheme(ConceptualSchemeType value)
/*     */   {
/* 170 */     this.conceptualScheme = value;
/*     */   }
/*     */ 
/*     */   public RangeOfValuesType getRangeOfValues()
/*     */   {
/* 182 */     return this.rangeOfValues;
/*     */   }
/*     */ 
/*     */   public void setRangeOfValues(RangeOfValuesType value)
/*     */   {
/* 194 */     this.rangeOfValues = value;
/*     */   }
/*     */ 
/*     */   public QName getType()
/*     */   {
/* 206 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(QName value)
/*     */   {
/* 218 */     this.type = value;
/*     */   }
/*     */ 
/*     */   public String getUom()
/*     */   {
/* 230 */     return this.uom;
/*     */   }
/*     */ 
/*     */   public void setUom(String value)
/*     */   {
/* 242 */     this.uom = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.DomainValuesType
 * JD-Core Version:    0.6.0
 */