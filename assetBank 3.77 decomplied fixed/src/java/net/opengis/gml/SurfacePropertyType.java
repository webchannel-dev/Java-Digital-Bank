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
/*     */ @XmlType(name="SurfacePropertyType", propOrder={"surface"})
/*     */ public class SurfacePropertyType
/*     */ {
/*     */ 
/*     */   @XmlElementRef(name="_Surface", namespace="http://www.opengis.net/gml", type=JAXBElement.class)
/*     */   protected JAXBElement<? extends AbstractSurfaceType> surface;
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
/*     */   public JAXBElement<? extends AbstractSurfaceType> get_Surface()
/*     */   {
/*  86 */     return this.surface;
/*     */   }
/*     */ 
/*     */   public void set_Surface(JAXBElement<? extends AbstractSurfaceType> value)
/*     */   {
/* 104 */     this.surface = value;
/*     */   }
/*     */ 
/*     */   public String getRemoteSchema()
/*     */   {
/* 116 */     return this.remoteSchema;
/*     */   }
/*     */ 
/*     */   public void setRemoteSchema(String value)
/*     */   {
/* 128 */     this.remoteSchema = value;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 140 */     if (this.type == null) {
/* 141 */       return "simple";
/*     */     }
/* 143 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String value)
/*     */   {
/* 156 */     this.type = value;
/*     */   }
/*     */ 
/*     */   public String getHref()
/*     */   {
/* 168 */     return this.href;
/*     */   }
/*     */ 
/*     */   public void setHref(String value)
/*     */   {
/* 180 */     this.href = value;
/*     */   }
/*     */ 
/*     */   public String getRole()
/*     */   {
/* 192 */     return this.role;
/*     */   }
/*     */ 
/*     */   public void setRole(String value)
/*     */   {
/* 204 */     this.role = value;
/*     */   }
/*     */ 
/*     */   public String getArcrole()
/*     */   {
/* 216 */     return this.arcrole;
/*     */   }
/*     */ 
/*     */   public void setArcrole(String value)
/*     */   {
/* 228 */     this.arcrole = value;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 240 */     return this.title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String value)
/*     */   {
/* 252 */     this.title = value;
/*     */   }
/*     */ 
/*     */   public String getShow()
/*     */   {
/* 264 */     return this.show;
/*     */   }
/*     */ 
/*     */   public void setShow(String value)
/*     */   {
/* 276 */     this.show = value;
/*     */   }
/*     */ 
/*     */   public String getActuate()
/*     */   {
/* 288 */     return this.actuate;
/*     */   }
/*     */ 
/*     */   public void setActuate(String value)
/*     */   {
/* 300 */     this.actuate = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.SurfacePropertyType
 * JD-Core Version:    0.6.0
 */