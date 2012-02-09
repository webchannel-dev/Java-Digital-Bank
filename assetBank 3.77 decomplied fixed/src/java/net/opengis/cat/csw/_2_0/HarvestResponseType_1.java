/*    */ package net.opengis.cat.csw._2_0;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="HarvestResponseType", propOrder={"acknowledgement", "transactionResponse"})
/*    */ public class HarvestResponseType_1
/*    */ {
/*    */ 
/*    */   @XmlElement(name="Acknowledgement")
/*    */   protected AcknowledgementType acknowledgement;
/*    */ 
/*    */   @XmlElement(name="TransactionResponse")
/*    */   protected TransactionResponseType transactionResponse;
/*    */ 
/*    */   public AcknowledgementType getAcknowledgement()
/*    */   {
/* 58 */     return this.acknowledgement;
/*    */   }
/*    */ 
/*    */   public void setAcknowledgement(AcknowledgementType value)
/*    */   {
/* 70 */     this.acknowledgement = value;
/*    */   }
/*    */ 
/*    */   public TransactionResponseType getTransactionResponse()
/*    */   {
/* 82 */     return this.transactionResponse;
/*    */   }
/*    */ 
/*    */   public void setTransactionResponse(TransactionResponseType value)
/*    */   {
/* 94 */     this.transactionResponse = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.HarvestResponseType
 * JD-Core Version:    0.6.0
 */