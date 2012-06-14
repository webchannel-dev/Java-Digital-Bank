/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="ArrayAssociationType", propOrder={"object"})
/*     */ public class ArrayAssociationType
/*     */ {
/*     */ 
/*     */   @XmlElementRef(name="_Object", namespace="http://www.opengis.net/gml", type=JAXBElement.class)
/*     */   protected List<JAXBElement<?>> object;
/*     */ 
/*     */   public List<JAXBElement<?>> get_Object()
/*     */   {
/* 116 */     if (this.object == null) {
/* 117 */       this.object = new ArrayList();
/*     */     }
/* 119 */     return this.object;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.ArrayAssociationType
 * JD-Core Version:    0.6.0
 */