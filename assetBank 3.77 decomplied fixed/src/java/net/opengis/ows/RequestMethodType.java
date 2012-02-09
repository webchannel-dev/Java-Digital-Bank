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
/*    */ @XmlType(name="RequestMethodType", propOrder={"constraint"})
/*    */ public class RequestMethodType extends OnlineResourceType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="Constraint")
/*    */   protected List<DomainType> constraint;
/*    */ 
/*    */   public List<DomainType> getConstraint()
/*    */   {
/* 74 */     if (this.constraint == null) {
/* 75 */       this.constraint = new ArrayList();
/*    */     }
/* 77 */     return this.constraint;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.RequestMethodType
 * JD-Core Version:    0.6.0
 */