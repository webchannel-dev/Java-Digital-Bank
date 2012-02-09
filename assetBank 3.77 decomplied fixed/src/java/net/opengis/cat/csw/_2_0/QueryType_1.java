/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.namespace.QName;
/*     */ import net.opengis.ogc.SortByType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="QueryType", propOrder={"elementSetName", "elementName", "constraint", "sortBy"})
/*     */ public class QueryType_1 extends AbstractQueryType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="ElementSetName", defaultValue="summary")
/*     */   protected ElementSetNameType elementSetName;
/*     */ 
/*     */   @XmlElement(name="ElementName")
/*     */   protected List<QName> elementName;
/*     */ 
/*     */   @XmlElement(name="Constraint")
/*     */   protected QueryConstraintType constraint;
/*     */ 
/*     */   @XmlElement(name="SortBy", namespace="http://www.opengis.net/ogc")
/*     */   protected SortByType sortBy;
/*     */ 
/*     */   @XmlAttribute(name="typeNames", required=true)
/*     */   protected List<QName> typeNames;
/*     */ 
/*     */   public ElementSetNameType getElementSetName()
/*     */   {
/*  88 */     return this.elementSetName;
/*     */   }
/*     */ 
/*     */   public void setElementSetName(ElementSetNameType value)
/*     */   {
/* 100 */     this.elementSetName = value;
/*     */   }
/*     */ 
/*     */   public List<QName> getElementName()
/*     */   {
/* 126 */     if (this.elementName == null) {
/* 127 */       this.elementName = new ArrayList();
/*     */     }
/* 129 */     return this.elementName;
/*     */   }
/*     */ 
/*     */   public QueryConstraintType getConstraint()
/*     */   {
/* 141 */     return this.constraint;
/*     */   }
/*     */ 
/*     */   public void setConstraint(QueryConstraintType value)
/*     */   {
/* 153 */     this.constraint = value;
/*     */   }
/*     */ 
/*     */   public SortByType getSortBy()
/*     */   {
/* 165 */     return this.sortBy;
/*     */   }
/*     */ 
/*     */   public void setSortBy(SortByType value)
/*     */   {
/* 177 */     this.sortBy = value;
/*     */   }
/*     */ 
/*     */   public List<QName> getTypeNames()
/*     */   {
/* 203 */     if (this.typeNames == null) {
/* 204 */       this.typeNames = new ArrayList();
/*     */     }
/* 206 */     return this.typeNames;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.QueryType
 * JD-Core Version:    0.6.0
 */