/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElements;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="Id_CapabilitiesType", propOrder={"eidOrFID"})
/*    */ public class Id_CapabilitiesType
/*    */ {
/*    */ 
/*    */   @XmlElements({@javax.xml.bind.annotation.XmlElement(name="EID", type=EID.class), @javax.xml.bind.annotation.XmlElement(name="FID", type=FID.class)})
/*    */   protected List<Object> eidOrFID;
/*    */ 
/*    */   public List<Object> getEIDOrFID()
/*    */   {
/* 76 */     if (this.eidOrFID == null) {
/* 77 */       this.eidOrFID = new ArrayList();
/*    */     }
/* 79 */     return this.eidOrFID;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.Id_CapabilitiesType
 * JD-Core Version:    0.6.0
 */