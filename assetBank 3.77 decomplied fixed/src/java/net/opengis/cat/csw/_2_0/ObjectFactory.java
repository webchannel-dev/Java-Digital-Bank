/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlElementDecl;
/*     */ import javax.xml.bind.annotation.XmlRegistry;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ @XmlRegistry
/*     */ public class ObjectFactory
/*     */ {
/*  33 */   private static final QName _Record_QNAME = new QName("http://www.opengis.net/cat/csw/2.0.2", "Record");
/*  34 */   private static final QName _BriefRecord_QNAME = new QName("http://www.opengis.net/cat/csw/2.0.2", "BriefRecord");
/*  35 */   private static final QName _SummaryRecord_QNAME = new QName("http://www.opengis.net/cat/csw/2.0.2", "SummaryRecord");
/*  36 */   private static final QName _DCMIRecord_QNAME = new QName("http://www.opengis.net/cat/csw/2.0.2", "DCMIRecord");
/*  37 */   private static final QName _AbstractRecord_QNAME = new QName("http://www.opengis.net/cat/csw/2.0.2", "AbstractRecord");
/*  38 */   private static final QName _AbstractQuery_QNAME = new QName("http://www.opengis.net/cat/csw/2.0.2", "AbstractQuery");
/*  39 */   private static final QName _GetRecords_QNAME = new QName("http://www.opengis.net/cat/csw/2.0.2", "GetRecords");
/*  40 */   private static final QName _DescribeRecord_QNAME = new QName("http://www.opengis.net/cat/csw/2.0.2", "DescribeRecord");
/*  41 */   private static final QName _GetRecordById_QNAME = new QName("http://www.opengis.net/cat/csw/2.0.2", "GetRecordById");
/*  42 */   private static final QName _GetCapabilities_QNAME = new QName("http://www.opengis.net/cat/csw/2.0.2", "GetCapabilities");
/*  43 */   private static final QName _Capabilities_QNAME = new QName("http://www.opengis.net/cat/csw/2.0.2", "Capabilities");
/*     */ 
/*  45 */   private static final QName _Query_QNAME = new QName("http://www.opengis.net/cat/csw/2.0.2", "Query");
/*     */ 
/*     */   public DCMIRecordType createDCMIRecordType()
/*     */   {
/*  59 */     return new DCMIRecordType();
/*     */   }
/*     */ 
/*     */   public BriefRecordType createBriefRecordType()
/*     */   {
/*  67 */     return new BriefRecordType();
/*     */   }
/*     */ 
/*     */   public EmptyType createEmptyType()
/*     */   {
/*  75 */     return new EmptyType();
/*     */   }
/*     */ 
/*     */   public SummaryRecordType createSummaryRecordType()
/*     */   {
/*  83 */     return new SummaryRecordType();
/*     */   }
/*     */ 
/*     */   public RecordType createRecordType()
/*     */   {
/*  91 */     return new RecordType();
/*     */   }
/*     */ 
/*     */   public RequestStatusType createRequestStatusType()
/*     */   {
/*  99 */     return new RequestStatusType();
/*     */   }
/*     */ 
/*     */   public SearchResultsType createSearchResultsType()
/*     */   {
/* 107 */     return new SearchResultsType();
/*     */   }
/*     */ 
/*     */   public GetRecordsResponseType createGetRecordsResponseType()
/*     */   {
/* 116 */     return new GetRecordsResponseType();
/*     */   }
/*     */ 
/*     */   public GetRecordsType createGetRecordsType()
/*     */   {
/* 125 */     return new GetRecordsType();
/*     */   }
/*     */ 
/*     */   public CapabilitiesType createCapabilitiesType()
/*     */   {
/* 133 */     return new CapabilitiesType();
/*     */   }
/*     */ 
/*     */   public DescribeRecordResponseType createDescribeRecordResponseType()
/*     */   {
/* 141 */     return new DescribeRecordResponseType();
/*     */   }
/*     */ 
/*     */   public GetRecordByIdResponseType createGetRecordByIdResponseType()
/*     */   {
/* 149 */     return new GetRecordByIdResponseType();
/*     */   }
/*     */ 
/*     */   public GetCapabilitiesType createGetCapabilitiesType()
/*     */   {
/* 157 */     return new GetCapabilitiesType();
/*     */   }
/*     */ 
/*     */   public SchemaComponentType createSchemaComponentType()
/*     */   {
/* 165 */     return new SchemaComponentType();
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/cat/csw/2.0.2", name="Record", substitutionHeadNamespace="http://www.opengis.net/cat/csw/2.0.2", substitutionHeadName="AbstractRecord")
/*     */   public JAXBElement<RecordType> createRecord(RecordType value)
/*     */   {
/* 175 */     return new JAXBElement(_Record_QNAME, RecordType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/cat/csw/2.0.2", name="BriefRecord", substitutionHeadNamespace="http://www.opengis.net/cat/csw/2.0.2", substitutionHeadName="AbstractRecord")
/*     */   public JAXBElement<BriefRecordType> createBriefRecord(BriefRecordType value)
/*     */   {
/* 184 */     return new JAXBElement(_BriefRecord_QNAME, BriefRecordType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/cat/csw/2.0.2", name="SummaryRecord", substitutionHeadNamespace="http://www.opengis.net/cat/csw/2.0.2", substitutionHeadName="AbstractRecord")
/*     */   public JAXBElement<SummaryRecordType> createSummaryRecord(SummaryRecordType value)
/*     */   {
/* 193 */     return new JAXBElement(_SummaryRecord_QNAME, SummaryRecordType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/cat/csw/2.0.2", name="DCMIRecord", substitutionHeadNamespace="http://www.opengis.net/cat/csw/2.0.2", substitutionHeadName="AbstractRecord")
/*     */   public JAXBElement<DCMIRecordType> createDCMIRecord(DCMIRecordType value)
/*     */   {
/* 202 */     return new JAXBElement(_DCMIRecord_QNAME, DCMIRecordType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/cat/csw/2.0.2", name="AbstractRecord")
/*     */   public JAXBElement<AbstractRecordType> createAbstractRecord(AbstractRecordType value)
/*     */   {
/* 211 */     return new JAXBElement(_AbstractRecord_QNAME, AbstractRecordType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/cat/csw/2.0.2", name="AbstractQuery")
/*     */   public JAXBElement<AbstractQueryType> createAbstractQuery(AbstractQueryType value)
/*     */   {
/* 220 */     return new JAXBElement(_AbstractQuery_QNAME, AbstractQueryType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/cat/csw/2.0.2", name="Query", substitutionHeadNamespace="http://www.opengis.net/cat/csw/2.0.2", substitutionHeadName="AbstractQuery")
/*     */   public JAXBElement<QueryType> createQuery(QueryType value)
/*     */   {
/* 229 */     return new JAXBElement(_Query_QNAME, QueryType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/cat/csw/2.0.2", name="GetRecords")
/*     */   public JAXBElement<GetRecordsType> createGetRecords(GetRecordsType value)
/*     */   {
/* 238 */     return new JAXBElement(_GetRecords_QNAME, GetRecordsType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/cat/csw/2.0.2", name="DescribeRecord")
/*     */   public JAXBElement<DescribeRecordType> createDescribeRecord(DescribeRecordType value)
/*     */   {
/* 247 */     return new JAXBElement(_DescribeRecord_QNAME, DescribeRecordType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/cat/csw/2.0.2", name="GetRecordById")
/*     */   public JAXBElement<GetRecordByIdType> createGetRecordById(GetRecordByIdType value)
/*     */   {
/* 256 */     return new JAXBElement(_GetRecordById_QNAME, GetRecordByIdType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/cat/csw/2.0.2", name="GetCapabilities")
/*     */   public JAXBElement<GetCapabilitiesType> createGetCapabilities(GetCapabilitiesType value)
/*     */   {
/* 265 */     return new JAXBElement(_GetCapabilities_QNAME, GetCapabilitiesType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/cat/csw/2.0.2", name="Capabilities")
/*     */   public JAXBElement<CapabilitiesType> createGetCapabilities(CapabilitiesType value)
/*     */   {
/* 274 */     return new JAXBElement(_Capabilities_QNAME, CapabilitiesType.class, null, value);
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.ObjectFactory
 * JD-Core Version:    0.6.0
 */