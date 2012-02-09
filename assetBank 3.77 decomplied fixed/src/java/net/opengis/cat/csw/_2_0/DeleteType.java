/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlID;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="DeleteType", propOrder={"constraint"})
/*     */ public class DeleteType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="Constraint", required=true)
/*     */   protected QueryConstraintType constraint;
/*     */ 
/*     */   @XmlAttribute(name="typeName")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String typeName;
/*     */ 
/*     */   @XmlAttribute(name="handle")
/*     */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*     */   @XmlID
/*     */   @XmlSchemaType(name="ID")
/*     */   protected String handle;
/*     */ 
/*     */   public QueryConstraintType getConstraint()
/*     */   {
/*  74 */     return this.constraint;
/*     */   }
/*     */ 
/*     */   public void setConstraint(QueryConstraintType value)
/*     */   {
/*  86 */     this.constraint = value;
/*     */   }
/*     */ 
/*     */   public String getTypeName()
/*     */   {
/*  98 */     return this.typeName;
/*     */   }
/*     */ 
/*     */   public void setTypeName(String value)
/*     */   {
/* 110 */     this.typeName = value;
/*     */   }
/*     */ 
/*     */   public String getHandle()
/*     */   {
/* 122 */     return this.handle;
/*     */   }
/*     */ 
/*     */   public void setHandle(String value)
/*     */   {
/* 134 */     this.handle = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.DeleteType
 * JD-Core Version:    0.6.0
 */