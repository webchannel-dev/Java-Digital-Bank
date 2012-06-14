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
/*     */ @XmlType(name="KeywordsType", propOrder={"keyword", "type"})
/*     */ public class KeywordsType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="Keyword", required=true)
/*     */   protected List<String> keyword;
/*     */ 
/*     */   @XmlElement(name="Type")
/*     */   protected CodeType type;
/*     */ 
/*     */   public List<String> getKeyword()
/*     */   {
/*  76 */     if (this.keyword == null) {
/*  77 */       this.keyword = new ArrayList();
/*     */     }
/*  79 */     return this.keyword;
/*     */   }
/*     */ 
/*     */   public CodeType getType()
/*     */   {
/*  91 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(CodeType value)
/*     */   {
/* 103 */     this.type = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.KeywordsType
 * JD-Core Version:    0.6.0
 */