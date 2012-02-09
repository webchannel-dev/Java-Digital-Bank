/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.XmlValue;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="MeasureListType", propOrder={"value"})
/*     */ public class MeasureListType
/*     */ {
/*     */ 
/*     */   @XmlValue
/*     */   protected List<Double> value;
/*     */ 
/*     */   @XmlAttribute(name="uom", required=true)
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String uom;
/*     */ 
/*     */   public List<Double> getValue()
/*     */   {
/*  77 */     if (this.value == null) {
/*  78 */       this.value = new ArrayList();
/*     */     }
/*  80 */     return this.value;
/*     */   }
/*     */ 
/*     */   public String getUom()
/*     */   {
/*  92 */     return this.uom;
/*     */   }
/*     */ 
/*     */   public void setUom(String value)
/*     */   {
/* 104 */     this.uom = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.MeasureListType
 * JD-Core Version:    0.6.0
 */