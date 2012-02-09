/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.datatype.Duration;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="HarvestType", propOrder={"source", "resourceType", "resourceFormat", "harvestInterval", "responseHandler"})
/*     */ public class HarvestType_1 extends RequestBaseType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="Source", required=true)
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String source;
/*     */ 
/*     */   @XmlElement(name="ResourceType", required=true)
/*     */   protected String resourceType;
/*     */ 
/*     */   @XmlElement(name="ResourceFormat", defaultValue="application/xml")
/*     */   protected String resourceFormat;
/*     */ 
/*     */   @XmlElement(name="HarvestInterval")
/*     */   protected Duration harvestInterval;
/*     */ 
/*     */   @XmlElement(name="ResponseHandler")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected List<String> responseHandler;
/*     */ 
/*     */   public String getSource()
/*     */   {
/*  97 */     return this.source;
/*     */   }
/*     */ 
/*     */   public void setSource(String value)
/*     */   {
/* 109 */     this.source = value;
/*     */   }
/*     */ 
/*     */   public String getResourceType()
/*     */   {
/* 121 */     return this.resourceType;
/*     */   }
/*     */ 
/*     */   public void setResourceType(String value)
/*     */   {
/* 133 */     this.resourceType = value;
/*     */   }
/*     */ 
/*     */   public String getResourceFormat()
/*     */   {
/* 145 */     return this.resourceFormat;
/*     */   }
/*     */ 
/*     */   public void setResourceFormat(String value)
/*     */   {
/* 157 */     this.resourceFormat = value;
/*     */   }
/*     */ 
/*     */   public Duration getHarvestInterval()
/*     */   {
/* 169 */     return this.harvestInterval;
/*     */   }
/*     */ 
/*     */   public void setHarvestInterval(Duration value)
/*     */   {
/* 181 */     this.harvestInterval = value;
/*     */   }
/*     */ 
/*     */   public List<String> getResponseHandler()
/*     */   {
/* 207 */     if (this.responseHandler == null) {
/* 208 */       this.responseHandler = new ArrayList();
/*     */     }
/* 210 */     return this.responseHandler;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.HarvestType
 * JD-Core Version:    0.6.0
 */