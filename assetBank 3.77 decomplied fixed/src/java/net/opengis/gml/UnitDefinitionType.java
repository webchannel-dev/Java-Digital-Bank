/*     */ package net.opengis.gml;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlSeeAlso;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="UnitDefinitionType", propOrder={"quantityType", "catalogSymbol"})
/*     */ @XmlSeeAlso({DerivedUnitType.class, BaseUnitType.class, ConventionalUnitType.class})
/*     */ public class UnitDefinitionType extends DefinitionType
/*     */ {
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   protected StringOrRefType quantityType;
/*     */   protected CodeType catalogSymbol;
/*     */ 
/*     */   public StringOrRefType getQuantityType()
/*     */   {
/*  67 */     return this.quantityType;
/*     */   }
/*     */ 
/*     */   public void setQuantityType(StringOrRefType value)
/*     */   {
/*  79 */     this.quantityType = value;
/*     */   }
/*     */ 
/*     */   public CodeType getCatalogSymbol()
/*     */   {
/*  91 */     return this.catalogSymbol;
/*     */   }
/*     */ 
/*     */   public void setCatalogSymbol(CodeType value)
/*     */   {
/* 103 */     this.catalogSymbol = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.UnitDefinitionType
 * JD-Core Version:    0.6.0
 */