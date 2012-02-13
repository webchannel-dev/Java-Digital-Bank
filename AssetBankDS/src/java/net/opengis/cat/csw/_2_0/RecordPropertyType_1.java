/*    */ package net.opengis.cat.csw._2_0;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="RecordPropertyType", propOrder={"name", "value"})
/*    */ public class RecordPropertyType_1
/*    */ {
/*    */ 
/*    */   @XmlElement(name="Name", required=true)
/*    */   protected String name;
/*    */ 
/*    */   @XmlElement(name="Value")
/*    */   protected Object value;
/*    */ 
/*    */   public String getName()
/*    */   {
/* 58 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String value)
/*    */   {
/* 70 */     this.name = value;
/*    */   }
/*    */ 
/*    */   public Object getValue()
/*    */   {
/* 82 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(Object value)
/*    */   {
/* 94 */     this.value = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.RecordPropertyType
 * JD-Core Version:    0.6.0
 */