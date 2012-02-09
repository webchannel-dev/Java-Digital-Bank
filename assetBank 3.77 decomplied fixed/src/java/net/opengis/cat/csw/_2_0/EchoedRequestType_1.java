/*    */ package net.opengis.cat.csw._2_0;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAnyElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="EchoedRequestType", propOrder={"any"})
/*    */ public class EchoedRequestType_1
/*    */ {
/*    */ 
/*    */   @XmlAnyElement(lax=true)
/*    */   protected Object any;
/*    */ 
/*    */   public Object getAny()
/*    */   {
/* 58 */     return this.any;
/*    */   }
/*    */ 
/*    */   public void setAny(Object value)
/*    */   {
/* 71 */     this.any = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.EchoedRequestType
 * JD-Core Version:    0.6.0
 */