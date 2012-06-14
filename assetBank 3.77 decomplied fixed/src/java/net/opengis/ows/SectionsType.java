/*    */ package net.opengis.ows;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="SectionsType", propOrder={"section"})
/*    */ public class SectionsType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="Section")
/*    */   protected List<String> section;
/*    */ 
/*    */   public List<String> getSection()
/*    */   {
/* 72 */     if (this.section == null) {
/* 73 */       this.section = new ArrayList();
/*    */     }
/* 75 */     return this.section;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.SectionsType
 * JD-Core Version:    0.6.0
 */