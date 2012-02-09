/*    */ package net.opengis.gml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlSchemaType;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ import javax.xml.bind.annotation.XmlValue;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="CodeType", propOrder={"value"})
/*    */ public class CodeType
/*    */ {
/*    */ 
/*    */   @XmlValue
/*    */   protected String value;
/*    */ 
/*    */   @XmlAttribute(name="codeSpace")
/*    */   @XmlSchemaType(name="anyURI")
/*    */   protected String codeSpace;
/*    */ 
/*    */   public String getValue()
/*    */   {
/* 63 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(String value)
/*    */   {
/* 75 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public String getCodeSpace()
/*    */   {
/* 87 */     return this.codeSpace;
/*    */   }
/*    */ 
/*    */   public void setCodeSpace(String value)
/*    */   {
/* 99 */     this.codeSpace = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.CodeType
 * JD-Core Version:    0.6.0
 */