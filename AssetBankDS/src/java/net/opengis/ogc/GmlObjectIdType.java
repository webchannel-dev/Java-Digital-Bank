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
/*    */ @XmlType(name="GmlObjectIdType")
/*    */ public class GmlObjectIdType extends AbstractIdType
/*    */ {
/*    */ 
/*    */   @XmlAttribute(name="id", namespace="http://www.opengis.net/gml", required=true)
/*    */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*    */   @XmlID
/*    */   @XmlSchemaType(name="ID")
/*    */   protected String id;
/*    */ 
/*    */   public String getId()
/*    */   {
/* 59 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(String value)
/*    */   {
/* 71 */     this.id = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.GmlObjectIdType
 * JD-Core Version:    0.6.0
 */