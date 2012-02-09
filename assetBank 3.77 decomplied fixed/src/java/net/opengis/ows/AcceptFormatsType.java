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
/*    */ @XmlType(name="AcceptFormatsType", propOrder={"outputFormat"})
/*    */ public class AcceptFormatsType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="OutputFormat")
/*    */   protected List<String> outputFormat;
/*    */ 
/*    */   public List<String> getOutputFormat()
/*    */   {
/* 72 */     if (this.outputFormat == null) {
/* 73 */       this.outputFormat = new ArrayList();
/*    */     }
/* 75 */     return this.outputFormat;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.AcceptFormatsType
 * JD-Core Version:    0.6.0
 */