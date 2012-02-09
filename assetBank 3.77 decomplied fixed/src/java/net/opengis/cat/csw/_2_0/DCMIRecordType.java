/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlSeeAlso;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import org.purl.dc.elements._1.SimpleLiteral;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="DCMIRecordType", propOrder={"dcElement"})
/*     */ @XmlSeeAlso({RecordType.class})
/*     */ public class DCMIRecordType extends AbstractRecordType
/*     */ {
/*     */ 
/*     */   @XmlElementRef(name="DC-element", namespace="http://purl.org/dc/elements/1.1/", type=JAXBElement.class)
/*     */   protected List<JAXBElement<SimpleLiteral>> dcElement;
/*     */ 
/*     */   public List<JAXBElement<SimpleLiteral>> getDCElement()
/*     */   {
/* 135 */     if (this.dcElement == null) {
/* 136 */       this.dcElement = new ArrayList();
/*     */     }
/* 138 */     return this.dcElement;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.DCMIRecordType
 * JD-Core Version:    0.6.0
 */