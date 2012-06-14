/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ import javax.xml.bind.annotation.XmlValue;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="FunctionNameType", propOrder={"value"})
/*    */ public class FunctionNameType
/*    */ {
/*    */ 
/*    */   @XmlValue
/*    */   protected String value;
/*    */ 
/*    */   @XmlAttribute(name="nArgs", required=true)
/*    */   protected String nArgs;
/*    */ 
/*    */   public String getValue()
/*    */   {
/* 55 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(String value)
/*    */   {
/* 67 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public String getNArgs()
/*    */   {
/* 79 */     return this.nArgs;
/*    */   }
/*    */ 
/*    */   public void setNArgs(String value)
/*    */   {
/* 91 */     this.nArgs = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.FunctionNameType
 * JD-Core Version:    0.6.0
 */