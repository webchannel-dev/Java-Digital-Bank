/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAnyElement;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="GetRecordByIdResponseType", propOrder={"abstractRecord", "any"})
/*     */ @XmlRootElement(name="GetRecordByIdResponse")
/*     */ public class GetRecordByIdResponseType
/*     */ {
/*     */ 
/*     */   @XmlElementRef(name="AbstractRecord", namespace="http://www.opengis.net/cat/csw/2.0.2", type=JAXBElement.class)
/*     */   protected List<JAXBElement<? extends AbstractRecordType>> abstractRecord;
/*     */ 
/*     */   @XmlAnyElement(lax=true)
/*     */   protected List<Object> any;
/*     */ 
/*     */   public List<JAXBElement<? extends AbstractRecordType>> getAbstractRecord()
/*     */   {
/*  87 */     if (this.abstractRecord == null) {
/*  88 */       this.abstractRecord = new ArrayList();
/*     */     }
/*  90 */     return this.abstractRecord;
/*     */   }
/*     */ 
/*     */   public List<Object> getAny()
/*     */   {
/* 116 */     if (this.any == null) {
/* 117 */       this.any = new ArrayList();
/*     */     }
/* 119 */     return this.any;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.GetRecordByIdResponseType
 * JD-Core Version:    0.6.0
 */