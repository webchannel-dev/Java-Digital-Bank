/*    */ package net.opengis.cat.csw._2_0;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="GetDomainResponseType", propOrder={"domainValues"})
/*    */ public class GetDomainResponseType_1
/*    */ {
/*    */ 
/*    */   @XmlElement(name="DomainValues", required=true)
/*    */   protected List<DomainValuesType> domainValues;
/*    */ 
/*    */   public List<DomainValuesType> getDomainValues()
/*    */   {
/* 74 */     if (this.domainValues == null) {
/* 75 */       this.domainValues = new ArrayList();
/*    */     }
/* 77 */     return this.domainValues;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.GetDomainResponseType
 * JD-Core Version:    0.6.0
 */