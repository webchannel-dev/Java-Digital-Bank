/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlID;
/*    */ import javax.xml.bind.annotation.XmlSchemaType;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*    */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="FeatureIdType")
/*    */ public class FeatureIdType extends AbstractIdType
/*    */ {
/*    */ 
/*    */   @XmlAttribute(name="fid", required=true)
/*    */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*    */   @XmlID
/*    */   @XmlSchemaType(name="ID")
/*    */   protected String fid;
/*    */ 
/*    */   public String getFid()
/*    */   {
/* 59 */     return this.fid;
/*    */   }
/*    */ 
/*    */   public void setFid(String value)
/*    */   {
/* 71 */     this.fid = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.FeatureIdType
 * JD-Core Version:    0.6.0
 */