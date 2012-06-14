/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="BagType", propOrder={"member", "members"})
/*     */ public class BagType extends AbstractGMLType
/*     */ {
/*     */   protected List<AssociationType> member;
/*     */   protected ArrayAssociationType members;
/*     */ 
/*     */   public List<AssociationType> getMember()
/*     */   {
/*  87 */     if (this.member == null) {
/*  88 */       this.member = new ArrayList();
/*     */     }
/*  90 */     return this.member;
/*     */   }
/*     */ 
/*     */   public ArrayAssociationType getMembers()
/*     */   {
/* 102 */     return this.members;
/*     */   }
/*     */ 
/*     */   public void setMembers(ArrayAssociationType value)
/*     */   {
/* 114 */     this.members = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.BagType
 * JD-Core Version:    0.6.0
 */