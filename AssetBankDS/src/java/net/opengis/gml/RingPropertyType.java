/*    */ package net.opengis.gml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="RingPropertyType", propOrder={"ring"})
/*    */ public class RingPropertyType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="Ring", required=true)
/*    */   protected RingType ring;
/*    */ 
/*    */   public RingType getRing()
/*    */   {
/* 56 */     return this.ring;
/*    */   }
/*    */ 
/*    */   public void setRing(RingType value)
/*    */   {
/* 68 */     this.ring = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.RingPropertyType
 * JD-Core Version:    0.6.0
 */