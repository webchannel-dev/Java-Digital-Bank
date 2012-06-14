/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="SortPropertyType", propOrder={"propertyName", "sortOrder"})
/*    */ public class SortPropertyType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="PropertyName", required=true)
/*    */   protected PropertyNameType propertyName;
/*    */ 
/*    */   @XmlElement(name="SortOrder")
/*    */   protected SortOrderType sortOrder;
/*    */ 
/*    */   public PropertyNameType getPropertyName()
/*    */   {
/* 58 */     return this.propertyName;
/*    */   }
/*    */ 
/*    */   public void setPropertyName(PropertyNameType value)
/*    */   {
/* 70 */     this.propertyName = value;
/*    */   }
/*    */ 
/*    */   public SortOrderType getSortOrder()
/*    */   {
/* 82 */     return this.sortOrder;
/*    */   }
/*    */ 
/*    */   public void setSortOrder(SortOrderType value)
/*    */   {
/* 94 */     this.sortOrder = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.SortPropertyType
 * JD-Core Version:    0.6.0
 */