/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="InsertResultType", propOrder={"briefRecord"})
/*     */ public class InsertResultType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="BriefRecord", required=true)
/*     */   protected List<BriefRecordType> briefRecord;
/*     */ 
/*     */   @XmlAttribute(name="handleRef")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String handleRef;
/*     */ 
/*     */   public List<BriefRecordType> getBriefRecord()
/*     */   {
/*  82 */     if (this.briefRecord == null) {
/*  83 */       this.briefRecord = new ArrayList();
/*     */     }
/*  85 */     return this.briefRecord;
/*     */   }
/*     */ 
/*     */   public String getHandleRef()
/*     */   {
/*  97 */     return this.handleRef;
/*     */   }
/*     */ 
/*     */   public void setHandleRef(String value)
/*     */   {
/* 109 */     this.handleRef = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.InsertResultType
 * JD-Core Version:    0.6.0
 */