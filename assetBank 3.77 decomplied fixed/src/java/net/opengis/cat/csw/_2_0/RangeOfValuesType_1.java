/*    */ package net.opengis.cat.csw._2_0;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="RangeOfValuesType", propOrder={"minValue", "maxValue"})
/*    */ public class RangeOfValuesType_1
/*    */ {
/*    */ 
/*    */   @XmlElement(name="MinValue", required=true)
/*    */   protected Object minValue;
/*    */ 
/*    */   @XmlElement(name="MaxValue", required=true)
/*    */   protected Object maxValue;
/*    */ 
/*    */   public Object getMinValue()
/*    */   {
/* 58 */     return this.minValue;
/*    */   }
/*    */ 
/*    */   public void setMinValue(Object value)
/*    */   {
/* 70 */     this.minValue = value;
/*    */   }
/*    */ 
/*    */   public Object getMaxValue()
/*    */   {
/* 82 */     return this.maxValue;
/*    */   }
/*    */ 
/*    */   public void setMaxValue(Object value)
/*    */   {
/* 94 */     this.maxValue = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.RangeOfValuesType
 * JD-Core Version:    0.6.0
 */