/*    */ package net.opengis.gml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="DefinitionProxyType", propOrder={"definitionRef"})
/*    */ public class DefinitionProxyType extends DefinitionType
/*    */ {
/*    */ 
/*    */   @XmlElement(required=true)
/*    */   protected ReferenceType definitionRef;
/*    */ 
/*    */   public ReferenceType getDefinitionRef()
/*    */   {
/* 58 */     return this.definitionRef;
/*    */   }
/*    */ 
/*    */   public void setDefinitionRef(ReferenceType value)
/*    */   {
/* 70 */     this.definitionRef = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.DefinitionProxyType
 * JD-Core Version:    0.6.0
 */