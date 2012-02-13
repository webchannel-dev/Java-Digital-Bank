/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.XmlValue;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="ElementSetNameType", propOrder={"value"})
/*     */ public class ElementSetNameType_1
/*     */ {
/*     */ 
/*     */   @XmlValue
/*     */   protected ElementSetType value;
/*     */ 
/*     */   @XmlAttribute(name="typeNames")
/*     */   protected List<QName> typeNames;
/*     */ 
/*     */   public ElementSetType getValue()
/*     */   {
/*  60 */     return this.value;
/*     */   }
/*     */ 
/*     */   public void setValue(ElementSetType value)
/*     */   {
/*  72 */     this.value = value;
/*     */   }
/*     */ 
/*     */   public List<QName> getTypeNames()
/*     */   {
/*  98 */     if (this.typeNames == null) {
/*  99 */       this.typeNames = new ArrayList();
/*     */     }
/* 101 */     return this.typeNames;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.ElementSetNameType
 * JD-Core Version:    0.6.0
 */