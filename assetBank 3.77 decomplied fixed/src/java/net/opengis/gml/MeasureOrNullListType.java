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
/*     */ @XmlType(name="MeasureOrNullListType", propOrder={"value"})
/*     */ public class MeasureOrNullListType
/*     */ {
/*     */ 
/*     */   @XmlValue
/*     */   protected List<String> value;
/*     */ 
/*     */   @XmlAttribute(name="uom", required=true)
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String uom;
/*     */ 
/*     */   public List<String> getValue()
/*     */   {
/*  78 */     if (this.value == null) {
/*  79 */       this.value = new ArrayList();
/*     */     }
/*  81 */     return this.value;
/*     */   }
/*     */ 
/*     */   public String getUom()
/*     */   {
/*  93 */     return this.uom;
/*     */   }
/*     */ 
/*     */   public void setUom(String value)
/*     */   {
/* 105 */     this.uom = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.MeasureOrNullListType
 * JD-Core Version:    0.6.0
 */