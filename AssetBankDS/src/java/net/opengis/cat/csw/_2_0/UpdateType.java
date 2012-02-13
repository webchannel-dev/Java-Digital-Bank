/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAnyElement;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlID;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="UpdateType", propOrder={"any", "recordProperty", "constraint"})
/*     */ public class UpdateType
/*     */ {
/*     */ 
/*     */   @XmlAnyElement(lax=true)
/*     */   protected Object any;
/*     */ 
/*     */   @XmlElement(name="RecordProperty")
/*     */   protected List<RecordPropertyType> recordProperty;
/*     */ 
/*     */   @XmlElement(name="Constraint")
/*     */   protected QueryConstraintType constraint;
/*     */ 
/*     */   @XmlAttribute(name="handle")
/*     */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*     */   @XmlID
/*     */   @XmlSchemaType(name="ID")
/*     */   protected String handle;
/*     */ 
/*     */   public Object getAny()
/*     */   {
/*  91 */     return this.any;
/*     */   }
/*     */ 
/*     */   public void setAny(Object value)
/*     */   {
/* 103 */     this.any = value;
/*     */   }
/*     */ 
/*     */   public List<RecordPropertyType> getRecordProperty()
/*     */   {
/* 129 */     if (this.recordProperty == null) {
/* 130 */       this.recordProperty = new ArrayList();
/*     */     }
/* 132 */     return this.recordProperty;
/*     */   }
/*     */ 
/*     */   public QueryConstraintType getConstraint()
/*     */   {
/* 144 */     return this.constraint;
/*     */   }
/*     */ 
/*     */   public void setConstraint(QueryConstraintType value)
/*     */   {
/* 156 */     this.constraint = value;
/*     */   }
/*     */ 
/*     */   public String getHandle()
/*     */   {
/* 168 */     return this.handle;
/*     */   }
/*     */ 
/*     */   public void setHandle(String value)
/*     */   {
/* 180 */     this.handle = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.UpdateType
 * JD-Core Version:    0.6.0
 */