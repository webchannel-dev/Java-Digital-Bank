/*     */ package net.opengis.gml;
/*     */ 
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="GeometryPropertyType", propOrder={"geometry"})
/*     */ public class GeometryPropertyType
/*     */ {
/*     */ 
/*     */   @XmlElementRef(name="_Geometry", namespace="http://www.opengis.net/gml", type=JAXBElement.class)
/*     */   protected JAXBElement<? extends AbstractGeometryType> geometry;
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
/*     */   public JAXBElement<? extends AbstractGeometryType> get_Geometry()
/*     */   {
/* 108 */     return this.geometry;
/*     */   }
/*     */ 
/*     */   public void set_Geometry(JAXBElement<? extends AbstractGeometryType> value)
/*     */   {
/* 146 */     this.geometry = value;
/*     */   }
/*     */ 
/*     */   public String getRemoteSchema()
/*     */   {
/* 158 */     return this.remoteSchema;
/*     */   }
/*     */ 
/*     */   public void setRemoteSchema(String value)
/*     */   {
/* 170 */     this.remoteSchema = value;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 182 */     if (this.type == null) {
/* 183 */       return "simple";
/*     */     }
/* 185 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String value)
/*     */   {
/* 198 */     this.type = value;
/*     */   }
/*     */ 
/*     */   public String getHref()
/*     */   {
/* 210 */     return this.href;
/*     */   }
/*     */ 
/*     */   public void setHref(String value)
/*     */   {
/* 222 */     this.href = value;
/*     */   }
/*     */ 
/*     */   public String getRole()
/*     */   {
/* 234 */     return this.role;
/*     */   }
/*     */ 
/*     */   public void setRole(String value)
/*     */   {
/* 246 */     this.role = value;
/*     */   }
/*     */ 
/*     */   public String getArcrole()
/*     */   {
/* 258 */     return this.arcrole;
/*     */   }
/*     */ 
/*     */   public void setArcrole(String value)
/*     */   {
/* 270 */     this.arcrole = value;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 282 */     return this.title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String value)
/*     */   {
/* 294 */     this.title = value;
/*     */   }
/*     */ 
/*     */   public String getShow()
/*     */   {
/* 306 */     return this.show;
/*     */   }
/*     */ 
/*     */   public void setShow(String value)
/*     */   {
/* 318 */     this.show = value;
/*     */   }
/*     */ 
/*     */   public String getActuate()
/*     */   {
/* 330 */     return this.actuate;
/*     */   }
/*     */ 
/*     */   public void setActuate(String value)
/*     */   {
/* 342 */     this.actuate = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.GeometryPropertyType
 * JD-Core Version:    0.6.0
 */