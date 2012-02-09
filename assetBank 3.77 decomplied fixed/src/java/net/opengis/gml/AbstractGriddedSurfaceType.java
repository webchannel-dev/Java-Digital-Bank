/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlSeeAlso;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="AbstractGriddedSurfaceType", propOrder={"row", "rows", "columns"})
/*     */ @XmlSeeAlso({ConeType.class, CylinderType.class, SphereType.class})
/*     */ public class AbstractGriddedSurfaceType extends AbstractParametricCurveSurfaceType
/*     */ {
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   protected List<Row> row;
/*     */   protected BigInteger rows;
/*     */   protected BigInteger columns;
/*     */ 
/*     */   public List<Row> getRow()
/*     */   {
/* 109 */     if (this.row == null) {
/* 110 */       this.row = new ArrayList();
/*     */     }
/* 112 */     return this.row;
/*     */   }
/*     */ 
/*     */   public BigInteger getRows()
/*     */   {
/* 124 */     return this.rows;
/*     */   }
/*     */ 
/*     */   public void setRows(BigInteger value)
/*     */   {
/* 136 */     this.rows = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getColumns()
/*     */   {
/* 148 */     return this.columns;
/*     */   }
/*     */ 
/*     */   public void setColumns(BigInteger value)
/*     */   {
/* 160 */     this.columns = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.AbstractGriddedSurfaceType
 * JD-Core Version:    0.6.0
 */