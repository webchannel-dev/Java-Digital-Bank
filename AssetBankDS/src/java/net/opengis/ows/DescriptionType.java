/*     */ package net.opengis.ows;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlSeeAlso;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="DescriptionType", propOrder={"title", "_abstract", "keywords"})
/*     */ @XmlSeeAlso({IdentificationType.class, ServiceIdentification.class})
/*     */ public class DescriptionType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="Title")
/*     */   protected String title;
/*     */ 
/*     */   @XmlElement(name="Abstract")
/*     */   protected String _abstract;
/*     */ 
/*     */   @XmlElement(name="Keywords")
/*     */   protected List<KeywordsType> keywords;
/*     */ 
/*     */   public String getTitle()
/*     */   {
/*  72 */     return this.title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String value)
/*     */   {
/*  84 */     this.title = value;
/*     */   }
/*     */ 
/*     */   public String getAbstract()
/*     */   {
/*  96 */     return this._abstract;
/*     */   }
/*     */ 
/*     */   public void setAbstract(String value)
/*     */   {
/* 108 */     this._abstract = value;
/*     */   }
/*     */ 
/*     */   public List<KeywordsType> getKeywords()
/*     */   {
/* 134 */     if (this.keywords == null) {
/* 135 */       this.keywords = new ArrayList();
/*     */     }
/* 137 */     return this.keywords;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.DescriptionType
 * JD-Core Version:    0.6.0
 */