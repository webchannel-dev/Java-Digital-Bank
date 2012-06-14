/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import net.opengis.ogc.FilterType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="QueryConstraintType", propOrder={"filter", "cqlText"})
/*     */ public class QueryConstraintType_1
/*     */ {
/*     */ 
/*     */   @XmlElement(name="Filter", namespace="http://www.opengis.net/ogc")
/*     */   protected FilterType filter;
/*     */ 
/*     */   @XmlElement(name="CqlText")
/*     */   protected String cqlText;
/*     */ 
/*     */   @XmlAttribute(name="version", required=true)
/*     */   protected String version;
/*     */ 
/*     */   public FilterType getFilter()
/*     */   {
/*  67 */     return this.filter;
/*     */   }
/*     */ 
/*     */   public void setFilter(FilterType value)
/*     */   {
/*  79 */     this.filter = value;
/*     */   }
/*     */ 
/*     */   public String getCqlText()
/*     */   {
/*  91 */     return this.cqlText;
/*     */   }
/*     */ 
/*     */   public void setCqlText(String value)
/*     */   {
/* 103 */     this.cqlText = value;
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 115 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setVersion(String value)
/*     */   {
/* 127 */     this.version = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.QueryConstraintType
 * JD-Core Version:    0.6.0
 */