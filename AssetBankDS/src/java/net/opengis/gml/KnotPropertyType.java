/*    */ package net.opengis.gml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="KnotPropertyType", propOrder={"knot"})
/*    */ public class KnotPropertyType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="Knot", required=true)
/*    */   protected KnotType knot;
/*    */ 
/*    */   public KnotType getKnot()
/*    */   {
/* 56 */     return this.knot;
/*    */   }
/*    */ 
/*    */   public void setKnot(KnotType value)
/*    */   {
/* 68 */     this.knot = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.KnotPropertyType
 * JD-Core Version:    0.6.0
 */