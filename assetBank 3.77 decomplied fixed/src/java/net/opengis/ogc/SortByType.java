/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="SortByType", propOrder={"sortProperty"})
/*    */ public class SortByType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="SortProperty", required=true)
/*    */   protected List<SortPropertyType> sortProperty;
/*    */ 
/*    */   public List<SortPropertyType> getSortProperty()
/*    */   {
/* 70 */     if (this.sortProperty == null) {
/* 71 */       this.sortProperty = new ArrayList();
/*    */     }
/* 73 */     return this.sortProperty;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.SortByType
 * JD-Core Version:    0.6.0
 */