/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ import net.opengis.gml.EnvelopeType;
         
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="BBOXType", propOrder={"propertyName", "envelope"})
/*    */ public class BBOXType extends SpatialOpsType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="PropertyName", required=true)
/*    */   protected PropertyNameType propertyName;
/*    */ 
/*    */   @XmlElement(name="Envelope", namespace="http://www.opengis.net/gml", required=true)
/*    */   protected EnvelopeType envelope;
/*    */ 
/*    */   public PropertyNameType getPropertyName()
/*    */   {
/* 61 */     return this.propertyName;
/*    */   }
/*    */ 
/*    */   public void setPropertyName(PropertyNameType value)
/*    */   {
/* 73 */     this.propertyName = value;
/*    */   }
/*    */ 
/*    */   public EnvelopeType getEnvelope()
/*    */   {
/* 85 */     return this.envelope;
/*    */   }
/*    */ 
/*    */   public void setEnvelope(EnvelopeType value)
/*    */   {
/* 97 */     this.envelope = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.BBOXType
 * JD-Core Version:    0.6.0
 */