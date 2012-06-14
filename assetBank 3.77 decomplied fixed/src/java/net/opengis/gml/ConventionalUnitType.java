/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="ConventionalUnitType", propOrder={"conversionToPreferredUnit", "roughConversionToPreferredUnit", "derivationUnitTerm"})
/*     */ public class ConventionalUnitType extends UnitDefinitionType
/*     */ {
/*     */   protected ConversionToPreferredUnitType conversionToPreferredUnit;
/*     */   protected ConversionToPreferredUnitType roughConversionToPreferredUnit;
/*     */   protected List<DerivationUnitTermType> derivationUnitTerm;
/*     */ 
/*     */   public ConversionToPreferredUnitType getConversionToPreferredUnit()
/*     */   {
/*  66 */     return this.conversionToPreferredUnit;
/*     */   }
/*     */ 
/*     */   public void setConversionToPreferredUnit(ConversionToPreferredUnitType value)
/*     */   {
/*  78 */     this.conversionToPreferredUnit = value;
/*     */   }
/*     */ 
/*     */   public ConversionToPreferredUnitType getRoughConversionToPreferredUnit()
/*     */   {
/*  90 */     return this.roughConversionToPreferredUnit;
/*     */   }
/*     */ 
/*     */   public void setRoughConversionToPreferredUnit(ConversionToPreferredUnitType value)
/*     */   {
/* 102 */     this.roughConversionToPreferredUnit = value;
/*     */   }
/*     */ 
/*     */   public List<DerivationUnitTermType> getDerivationUnitTerm()
/*     */   {
/* 128 */     if (this.derivationUnitTerm == null) {
/* 129 */       this.derivationUnitTerm = new ArrayList();
/*     */     }
/* 131 */     return this.derivationUnitTerm;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.ConventionalUnitType
 * JD-Core Version:    0.6.0
 */