/*    */ package net.opengis.gml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="IndirectEntryType", propOrder={"definitionProxy"})
/*    */ public class IndirectEntryType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="DefinitionProxy", required=true)
/*    */   protected DefinitionProxyType definitionProxy;
/*    */ 
/*    */   public DefinitionProxyType getDefinitionProxy()
/*    */   {
/* 56 */     return this.definitionProxy;
/*    */   }
/*    */ 
/*    */   public void setDefinitionProxy(DefinitionProxyType value)
/*    */   {
/* 68 */     this.definitionProxy = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.IndirectEntryType
 * JD-Core Version:    0.6.0
 */