/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAnyElement;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlID;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="InsertType", propOrder={"any"})
/*     */ public class InsertType
/*     */ {
/*     */ 
/*     */   @XmlAnyElement(lax=true)
/*     */   protected List<Object> any;
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
/*     */   public List<Object> getAny()
/*     */   {
/*  92 */     if (this.any == null) {
/*  93 */       this.any = new ArrayList();
/*     */     }
/*  95 */     return this.any;
/*     */   }
/*     */ 
/*     */   public String getTypeName()
/*     */   {
/* 107 */     return this.typeName;
/*     */   }
/*     */ 
/*     */   public void setTypeName(String value)
/*     */   {
/* 119 */     this.typeName = value;
/*     */   }
/*     */ 
/*     */   public String getHandle()
/*     */   {
/* 131 */     return this.handle;
/*     */   }
/*     */ 
/*     */   public void setHandle(String value)
/*     */   {
/* 143 */     this.handle = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.InsertType
 * JD-Core Version:    0.6.0
 */