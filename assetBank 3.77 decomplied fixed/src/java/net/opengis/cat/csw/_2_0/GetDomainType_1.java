/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="GetDomainType", propOrder={"propertyName", "parameterName"})
/*     */ public class GetDomainType_1 extends RequestBaseType
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
/*     */   public String getPropertyName()
/*     */   {
/*  68 */     return this.propertyName;
/*     */   }
/*     */ 
/*     */   public void setPropertyName(String value)
/*     */   {
/*  80 */     this.propertyName = value;
/*     */   }
/*     */ 
/*     */   public String getParameterName()
/*     */   {
/*  92 */     return this.parameterName;
/*     */   }
/*     */ 
/*     */   public void setParameterName(String value)
/*     */   {
/* 104 */     this.parameterName = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.GetDomainType
 * JD-Core Version:    0.6.0
 */