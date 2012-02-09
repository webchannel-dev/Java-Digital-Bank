/*    */ package net.opengis.ows;
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
/* 60 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(String value)
/*    */   {
/* 72 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public String getCodeSpace()
/*    */   {
/* 84 */     return this.codeSpace;
/*    */   }
/*    */ 
/*    */   public void setCodeSpace(String value)
/*    */   {
/* 96 */     this.codeSpace = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.CodeType
 * JD-Core Version:    0.6.0
 */