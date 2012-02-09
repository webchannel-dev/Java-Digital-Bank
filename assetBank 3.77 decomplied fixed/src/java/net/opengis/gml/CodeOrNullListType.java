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
/*     */ @XmlType(name="CodeOrNullListType", propOrder={"value"})
/*     */ public class CodeOrNullListType
/*     */ {
/*     */ 
/*     */   @XmlValue
/*     */   protected List<String> value;
/*     */ 
/*     */   @XmlAttribute(name="codeSpace")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String codeSpace;
/*     */ 
/*     */   public List<String> getValue()
/*     */   {
/*  79 */     if (this.value == null) {
/*  80 */       this.value = new ArrayList();
/*     */     }
/*  82 */     return this.value;
/*     */   }
/*     */ 
/*     */   public String getCodeSpace()
/*     */   {
/*  94 */     return this.codeSpace;
/*     */   }
/*     */ 
/*     */   public void setCodeSpace(String value)
/*     */   {
/* 106 */     this.codeSpace = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.CodeOrNullListType
 * JD-Core Version:    0.6.0
 */