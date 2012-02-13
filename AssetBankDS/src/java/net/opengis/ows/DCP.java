/*    */ package net.opengis.ows;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="", propOrder={"http"})
/*    */ @XmlRootElement(name="DCP")
/*    */ public class DCP
/*    */ {
/*    */ 
/*    */   @XmlElement(name="HTTP")
/*    */   protected HTTP http;
/*    */ 
/*    */   public HTTP getHTTP()
/*    */   {
/* 56 */     return this.http;
/*    */   }
/*    */ 
/*    */   public void setHTTP(HTTP value)
/*    */   {
/* 68 */     this.http = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.DCP
 * JD-Core Version:    0.6.0
 */