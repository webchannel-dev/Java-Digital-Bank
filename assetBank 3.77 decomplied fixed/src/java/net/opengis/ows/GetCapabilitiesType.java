/*     */ package net.opengis.ows;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="GetCapabilitiesType", propOrder={"acceptVersions", "sections", "acceptFormats"})
/*     */ public class GetCapabilitiesType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="AcceptVersions")
/*     */   protected AcceptVersionsType acceptVersions;
/*     */ 
/*     */   @XmlElement(name="Sections")
/*     */   protected SectionsType sections;
/*     */ 
/*     */   @XmlElement(name="AcceptFormats")
/*     */   protected AcceptFormatsType acceptFormats;
/*     */ 
/*     */   @XmlAttribute(name="updateSequence")
/*     */   protected String updateSequence;
/*     */ 
/*     */   public AcceptVersionsType getAcceptVersions()
/*     */   {
/*  68 */     return this.acceptVersions;
/*     */   }
/*     */ 
/*     */   public void setAcceptVersions(AcceptVersionsType value)
/*     */   {
/*  80 */     this.acceptVersions = value;
/*     */   }
/*     */ 
/*     */   public SectionsType getSections()
/*     */   {
/*  92 */     return this.sections;
/*     */   }
/*     */ 
/*     */   public void setSections(SectionsType value)
/*     */   {
/* 104 */     this.sections = value;
/*     */   }
/*     */ 
/*     */   public AcceptFormatsType getAcceptFormats()
/*     */   {
/* 116 */     return this.acceptFormats;
/*     */   }
/*     */ 
/*     */   public void setAcceptFormats(AcceptFormatsType value)
/*     */   {
/* 128 */     this.acceptFormats = value;
/*     */   }
/*     */ 
/*     */   public String getUpdateSequence()
/*     */   {
/* 140 */     return this.updateSequence;
/*     */   }
/*     */ 
/*     */   public void setUpdateSequence(String value)
/*     */   {
/* 152 */     this.updateSequence = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.GetCapabilitiesType
 * JD-Core Version:    0.6.0
 */