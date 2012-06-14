/*     */ package net.opengis.gml;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="MultiCurvePropertyType", propOrder={"multiCurve"})
/*     */ public class MultiCurvePropertyType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="MultiCurve")
/*     */   protected MultiCurveType multiCurve;
/*     */ 
/*     */   @XmlAttribute(name="remoteSchema", namespace="http://www.opengis.net/gml")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String remoteSchema;
/*     */ 
/*     */   @XmlAttribute(name="type", namespace="http://www.w3.org/1999/xlink")
/*     */   protected String type;
/*     */ 
/*     */   @XmlAttribute(name="href", namespace="http://www.w3.org/1999/xlink")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String href;
/*     */ 
/*     */   @XmlAttribute(name="role", namespace="http://www.w3.org/1999/xlink")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String role;
/*     */ 
/*     */   @XmlAttribute(name="arcrole", namespace="http://www.w3.org/1999/xlink")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String arcrole;
/*     */ 
/*     */   @XmlAttribute(name="title", namespace="http://www.w3.org/1999/xlink")
/*     */   protected String title;
/*     */ 
/*     */   @XmlAttribute(name="show", namespace="http://www.w3.org/1999/xlink")
/*     */   protected String show;
/*     */ 
/*     */   @XmlAttribute(name="actuate", namespace="http://www.w3.org/1999/xlink")
/*     */   protected String actuate;
/*     */ 
/*     */   public MultiCurveType getMultiCurve()
/*     */   {
/*  79 */     return this.multiCurve;
/*     */   }
/*     */ 
/*     */   public void setMultiCurve(MultiCurveType value)
/*     */   {
/*  91 */     this.multiCurve = value;
/*     */   }
/*     */ 
/*     */   public String getRemoteSchema()
/*     */   {
/* 103 */     return this.remoteSchema;
/*     */   }
/*     */ 
/*     */   public void setRemoteSchema(String value)
/*     */   {
/* 115 */     this.remoteSchema = value;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 127 */     if (this.type == null) {
/* 128 */       return "simple";
/*     */     }
/* 130 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String value)
/*     */   {
/* 143 */     this.type = value;
/*     */   }
/*     */ 
/*     */   public String getHref()
/*     */   {
/* 155 */     return this.href;
/*     */   }
/*     */ 
/*     */   public void setHref(String value)
/*     */   {
/* 167 */     this.href = value;
/*     */   }
/*     */ 
/*     */   public String getRole()
/*     */   {
/* 179 */     return this.role;
/*     */   }
/*     */ 
/*     */   public void setRole(String value)
/*     */   {
/* 191 */     this.role = value;
/*     */   }
/*     */ 
/*     */   public String getArcrole()
/*     */   {
/* 203 */     return this.arcrole;
/*     */   }
/*     */ 
/*     */   public void setArcrole(String value)
/*     */   {
/* 215 */     this.arcrole = value;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 227 */     return this.title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String value)
/*     */   {
/* 239 */     this.title = value;
/*     */   }
/*     */ 
/*     */   public String getShow()
/*     */   {
/* 251 */     return this.show;
/*     */   }
/*     */ 
/*     */   public void setShow(String value)
/*     */   {
/* 263 */     this.show = value;
/*     */   }
/*     */ 
/*     */   public String getActuate()
/*     */   {
/* 275 */     return this.actuate;
/*     */   }
/*     */ 
/*     */   public void setActuate(String value)
/*     */   {
/* 287 */     this.actuate = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.MultiCurvePropertyType
 * JD-Core Version:    0.6.0
 */