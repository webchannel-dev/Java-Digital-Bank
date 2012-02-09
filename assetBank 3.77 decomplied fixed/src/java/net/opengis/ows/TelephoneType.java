/*     */ package net.opengis.ows;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="TelephoneType", propOrder={"voice", "facsimile"})
/*     */ public class TelephoneType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="Voice")
/*     */   protected List<String> voice;
/*     */ 
/*     */   @XmlElement(name="Facsimile")
/*     */   protected List<String> facsimile;
/*     */ 
/*     */   public List<String> getVoice()
/*     */   {
/*  76 */     if (this.voice == null) {
/*  77 */       this.voice = new ArrayList();
/*     */     }
/*  79 */     return this.voice;
/*     */   }
/*     */ 
/*     */   public List<String> getFacsimile()
/*     */   {
/* 105 */     if (this.facsimile == null) {
/* 106 */       this.facsimile = new ArrayList();
/*     */     }
/* 108 */     return this.facsimile;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.TelephoneType
 * JD-Core Version:    0.6.0
 */