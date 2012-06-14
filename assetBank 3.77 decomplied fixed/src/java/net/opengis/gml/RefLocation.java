/*     */ package net.opengis.gml;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)

/*     */ @XmlType(name="", propOrder={"affinePlacement"})

/*     */ public class RefLocation
/*     */ {
/*     */ 
/*     */   @XmlElement(name="AffinePlacement", required=true)
/*     */   protected AffinePlacementType affinePlacement;
/*     */ 
/*     */   public AffinePlacementType getAffinePlacement()
/*     */   {
/* 206 */     return this.affinePlacement;
/*     */   }
/*     */ 
/*     */   public void setAffinePlacement(AffinePlacementType value)
/*     */   {
/* 218 */     this.affinePlacement = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.ClothoidType.RefLocation
 * JD-Core Version:    0.6.0
 */