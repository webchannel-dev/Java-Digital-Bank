/*     */ package net.opengis.ogc;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="FunctionType", propOrder={"expression"})
/*     */ public class FunctionType extends ExpressionType
/*     */ {
/*     */ 
/*     */   @XmlElementRef(name="expression", namespace="http://www.opengis.net/ogc", type=JAXBElement.class)
/*     */   protected List<JAXBElement<?>> expression;
/*     */ 
/*     */   @XmlAttribute(name="name", required=true)
/*     */   protected String name;
/*     */ 
/*     */   public List<JAXBElement<?>> getExpression()
/*     */   {
/*  84 */     if (this.expression == null) {
/*  85 */       this.expression = new ArrayList();
/*     */     }
/*  87 */     return this.expression;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  99 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String value)
/*     */   {
/* 111 */     this.name = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.FunctionType
 * JD-Core Version:    0.6.0
 */