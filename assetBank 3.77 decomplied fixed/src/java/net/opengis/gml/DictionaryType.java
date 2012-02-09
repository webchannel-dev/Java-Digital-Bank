/*    */ package net.opengis.gml;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.JAXBElement;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElementRefs;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="DictionaryType", propOrder={"dictionaryEntryOrIndirectEntry"})
/*    */ public class DictionaryType extends DefinitionType
/*    */ {
/*    */ 
/*    */   @XmlElementRefs({@javax.xml.bind.annotation.XmlElementRef(name="dictionaryEntry", namespace="http://www.opengis.net/gml", type=JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="indirectEntry", namespace="http://www.opengis.net/gml", type=JAXBElement.class)})
/*    */   protected List<JAXBElement<?>> dictionaryEntryOrIndirectEntry;
/*    */ 
/*    */   public List<JAXBElement<?>> getDictionaryEntryOrIndirectEntry()
/*    */   {
/* 84 */     if (this.dictionaryEntryOrIndirectEntry == null) {
/* 85 */       this.dictionaryEntryOrIndirectEntry = new ArrayList();
/*    */     }
/* 87 */     return this.dictionaryEntryOrIndirectEntry;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.DictionaryType
 * JD-Core Version:    0.6.0
 */