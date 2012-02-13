/*    */ package net.opengis.gml;
/*    */ 
/*    */ import javax.xml.bind.JAXBElement;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElementRef;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="AbstractRingPropertyType", propOrder={"ring"})
/*    */ public class AbstractRingPropertyType
/*    */ {
/*    */ 
/*    */   @XmlElementRef(name="_Ring", namespace="http://www.opengis.net/gml", type=JAXBElement.class)
/*    */   protected JAXBElement<? extends AbstractRingType> ring;
/*    */ 
/*    */   public JAXBElement<? extends AbstractRingType> get_Ring()
/*    */   {
/* 59 */     return this.ring;
/*    */   }
/*    */ 
/*    */   public void set_Ring(JAXBElement<? extends AbstractRingType> value)
/*    */   {
/* 73 */     this.ring = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.AbstractRingPropertyType
 * JD-Core Version:    0.6.0
 */