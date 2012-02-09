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
/*    */ @XmlType(name="AcceptVersionsType", propOrder={"version"})
/*    */ public class AcceptVersionsType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="Version", required=true)
/*    */   protected List<String> version;
/*    */ 
/*    */   public List<String> getVersion()
/*    */   {
/* 72 */     if (this.version == null) {
/* 73 */       this.version = new ArrayList();
/*    */     }
/* 75 */     return this.version;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.AcceptVersionsType
 * JD-Core Version:    0.6.0
 */