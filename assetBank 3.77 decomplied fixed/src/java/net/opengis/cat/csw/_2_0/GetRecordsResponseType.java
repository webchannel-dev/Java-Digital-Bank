/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="GetRecordsResponseType", propOrder={"requestId", "searchStatus", "searchResults"})
/*     */ @XmlRootElement(name="GetRecordsResponse")
/*     */ public class GetRecordsResponseType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="RequestId")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String requestId;
/*     */ 
/*     */   @XmlElement(name="SearchStatus", required=true)
/*     */   protected RequestStatusType searchStatus;
/*     */ 
/*     */   @XmlElement(name="SearchResults", required=true)
/*     */   protected SearchResultsType searchResults;
/*     */ 
/*     */   @XmlAttribute(name="version")
/*     */   protected String version;
/*     */ 
/*     */   public String getRequestId()
/*     */   {
/*  76 */     return this.requestId;
/*     */   }
/*     */ 
/*     */   public void setRequestId(String value)
/*     */   {
/*  88 */     this.requestId = value;
/*     */   }
/*     */ 
/*     */   public RequestStatusType getSearchStatus()
/*     */   {
/* 100 */     return this.searchStatus;
/*     */   }
/*     */ 
/*     */   public void setSearchStatus(RequestStatusType value)
/*     */   {
/* 112 */     this.searchStatus = value;
/*     */   }
/*     */ 
/*     */   public SearchResultsType getSearchResults()
/*     */   {
/* 124 */     return this.searchResults;
/*     */   }
/*     */ 
/*     */   public void setSearchResults(SearchResultsType value)
/*     */   {
/* 136 */     this.searchResults = value;
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 148 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setVersion(String value)
/*     */   {
/* 160 */     this.version = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.GetRecordsResponseType
 * JD-Core Version:    0.6.0
 */