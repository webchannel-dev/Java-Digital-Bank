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
/*     */ @XmlType(name="AssociationType", propOrder={"object"})
/*     */ public class AssociationType
/*     */ {
/*     */ 
/*     */   @XmlElementRef(name="_Object", namespace="http://www.opengis.net/gml", type=JAXBElement.class)
/*     */   protected JAXBElement<?> object;
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
/*     */   public JAXBElement<?> get_Object()
/*     */   {
/* 130 */     return this.object;
/*     */   }
/*     */ 
/*     */   public void set_Object(JAXBElement<?> value)
/*     */   {
/* 182 */     this.object = value;
/*     */   }
/*     */ 
/*     */   public String getRemoteSchema()
/*     */   {
/* 194 */     return this.remoteSchema;
/*     */   }
/*     */ 
/*     */   public void setRemoteSchema(String value)
/*     */   {
/* 206 */     this.remoteSchema = value;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 218 */     if (this.type == null) {
/* 219 */       return "simple";
/*     */     }
/* 221 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String value)
/*     */   {
/* 234 */     this.type = value;
/*     */   }
/*     */ 
/*     */   public String getHref()
/*     */   {
/* 246 */     return this.href;
/*     */   }
/*     */ 
/*     */   public void setHref(String value)
/*     */   {
/* 258 */     this.href = value;
/*     */   }
/*     */ 
/*     */   public String getRole()
/*     */   {
/* 270 */     return this.role;
/*     */   }
/*     */ 
/*     */   public void setRole(String value)
/*     */   {
/* 282 */     this.role = value;
/*     */   }
/*     */ 
/*     */   public String getArcrole()
/*     */   {
/* 294 */     return this.arcrole;
/*     */   }
/*     */ 
/*     */   public void setArcrole(String value)
/*     */   {
/* 306 */     this.arcrole = value;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 318 */     return this.title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String value)
/*     */   {
/* 330 */     this.title = value;
/*     */   }
/*     */ 
/*     */   public String getShow()
/*     */   {
/* 342 */     return this.show;
/*     */   }
/*     */ 
/*     */   public void setShow(String value)
/*     */   {
/* 354 */     this.show = value;
/*     */   }
/*     */ 
/*     */   public String getActuate()
/*     */   {
/* 366 */     return this.actuate;
/*     */   }
/*     */ 
/*     */   public void setActuate(String value)
/*     */   {
/* 378 */     this.actuate = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.AssociationType
 * JD-Core Version:    0.6.0
 */