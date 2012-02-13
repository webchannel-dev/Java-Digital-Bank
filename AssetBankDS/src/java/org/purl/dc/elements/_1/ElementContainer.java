/*     */ package org.purl.dc.elements._1;
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
/*     */ @XmlType(name="elementContainer", propOrder={"dcElement"})
/*     */ public class ElementContainer
/*     */ {
/*     */ 
/*     */   @XmlElementRef(name="DC-element", namespace="http://purl.org/dc/elements/1.1/", type=JAXBElement.class)
/*     */   protected List<JAXBElement<SimpleLiteral>> dcElement;
/*     */ 
/*     */   public List<JAXBElement<SimpleLiteral>> getDCElement()
/*     */   {
/* 125 */     if (this.dcElement == null) {
/* 126 */       this.dcElement = new ArrayList();
/*     */     }
/* 128 */     return this.dcElement;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.purl.dc.elements._1.ElementContainer
 * JD-Core Version:    0.6.0
 */