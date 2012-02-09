/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="MultiSolidType", propOrder={"solidMember", "solidMembers"})
/*     */ public class MultiSolidType extends AbstractGeometricAggregateType
/*     */ {
/*     */   protected List<SolidPropertyType> solidMember;
/*     */   protected SolidArrayPropertyType solidMembers;
/*     */ 
/*     */   public List<SolidPropertyType> getSolidMember()
/*     */   {
/*  75 */     if (this.solidMember == null) {
/*  76 */       this.solidMember = new ArrayList();
/*     */     }
/*  78 */     return this.solidMember;
/*     */   }
/*     */ 
/*     */   public SolidArrayPropertyType getSolidMembers()
/*     */   {
/*  90 */     return this.solidMembers;
/*     */   }
/*     */ 
/*     */   public void setSolidMembers(SolidArrayPropertyType value)
/*     */   {
/* 102 */     this.solidMembers = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.MultiSolidType
 * JD-Core Version:    0.6.0
 */