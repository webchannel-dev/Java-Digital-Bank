/*    */ package net.opengis.cat.csw._2_0;
/*    */ 
/*    */ import java.math.BigInteger;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlSchemaType;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="DistributedSearchType")
/*    */ public class DistributedSearchType
/*    */ {
/*    */ 
/*    */   @XmlAttribute(name="hopCount")
/*    */   @XmlSchemaType(name="positiveInteger")
/*    */   protected BigInteger hopCount;
/*    */ 
/*    */   public BigInteger getHopCount()
/*    */   {
/* 59 */     if (this.hopCount == null) {
/* 60 */       return new BigInteger("2");
/*    */     }
/* 62 */     return this.hopCount;
/*    */   }
/*    */ 
/*    */   public void setHopCount(BigInteger value)
/*    */   {
/* 75 */     this.hopCount = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.DistributedSearchType
 * JD-Core Version:    0.6.0
 */