/*    */ package net.opengis.gml;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="DerivedUnitType", propOrder={"derivationUnitTerm"})
/*    */ public class DerivedUnitType extends UnitDefinitionType
/*    */ {
/*    */ 
/*    */   @XmlElement(required=true)
/*    */   protected List<DerivationUnitTermType> derivationUnitTerm;
/*    */ 
/*    */   public List<DerivationUnitTermType> getDerivationUnitTerm()
/*    */   {
/* 74 */     if (this.derivationUnitTerm == null) {
/* 75 */       this.derivationUnitTerm = new ArrayList();
/*    */     }
/* 77 */     return this.derivationUnitTerm;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.DerivedUnitType
 * JD-Core Version:    0.6.0
 */