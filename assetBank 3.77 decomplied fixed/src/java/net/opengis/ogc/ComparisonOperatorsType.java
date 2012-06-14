/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="ComparisonOperatorsType", propOrder={"comparisonOperator"})
/*    */ public class ComparisonOperatorsType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="ComparisonOperator", required=true)
/*    */   protected List<ComparisonOperatorType> comparisonOperator;
/*    */ 
/*    */   public List<ComparisonOperatorType> getComparisonOperator()
/*    */   {
/* 70 */     if (this.comparisonOperator == null) {
/* 71 */       this.comparisonOperator = new ArrayList();
/*    */     }
/* 73 */     return this.comparisonOperator;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.ComparisonOperatorsType
 * JD-Core Version:    0.6.0
 */