/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import net.opengis.ows.BoundingBoxType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="RecordType", propOrder={"anyText", "boundingBox"})
/*     */ public class RecordType extends DCMIRecordType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="AnyText")
/*     */   protected List<EmptyType> anyText;
/*     */ 
/*     */   @XmlElementRef(name="BoundingBox", namespace="http://www.opengis.net/ows", type=JAXBElement.class)
/*     */   protected List<JAXBElement<? extends BoundingBoxType>> boundingBox;
/*     */ 
/*     */   public List<EmptyType> getAnyText()
/*     */   {
/*  87 */     if (this.anyText == null) {
/*  88 */       this.anyText = new ArrayList();
/*     */     }
/*  90 */     return this.anyText;
/*     */   }
/*     */ 
/*     */   public List<JAXBElement<? extends BoundingBoxType>> getBoundingBox()
/*     */   {
/* 117 */     if (this.boundingBox == null) {
/* 118 */       this.boundingBox = new ArrayList();
/*     */     }
/* 120 */     return this.boundingBox;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.RecordType
 * JD-Core Version:    0.6.0
 */