/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="AffinePlacementType", propOrder={"location", "refDirection", "inDimension", "outDimension"})
/*     */ public class AffinePlacementType
/*     */ {
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   protected DirectPositionType location;
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   protected List<VectorType> refDirection;
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   @XmlSchemaType(name="positiveInteger")
/*     */   protected BigInteger inDimension;
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   @XmlSchemaType(name="positiveInteger")
/*     */   protected BigInteger outDimension;
/*     */ 
/*     */   public DirectPositionType getLocation()
/*     */   {
/*  99 */     return this.location;
/*     */   }
/*     */ 
/*     */   public void setLocation(DirectPositionType value)
/*     */   {
/* 111 */     this.location = value;
/*     */   }
/*     */ 
/*     */   public List<VectorType> getRefDirection()
/*     */   {
/* 137 */     if (this.refDirection == null) {
/* 138 */       this.refDirection = new ArrayList();
/*     */     }
/* 140 */     return this.refDirection;
/*     */   }
/*     */ 
/*     */   public BigInteger getInDimension()
/*     */   {
/* 152 */     return this.inDimension;
/*     */   }
/*     */ 
/*     */   public void setInDimension(BigInteger value)
/*     */   {
/* 164 */     this.inDimension = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getOutDimension()
/*     */   {
/* 176 */     return this.outDimension;
/*     */   }
/*     */ 
/*     */   public void setOutDimension(BigInteger value)
/*     */   {
/* 188 */     this.outDimension = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.AffinePlacementType
 * JD-Core Version:    0.6.0
 */