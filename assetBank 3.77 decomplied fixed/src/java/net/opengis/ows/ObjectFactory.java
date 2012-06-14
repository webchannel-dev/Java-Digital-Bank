/*     */ package net.opengis.ows;
/*     */ 
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlElementDecl;
/*     */ import javax.xml.bind.annotation.XmlRegistry;
/*     */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ @XmlRegistry
/*     */ public class ObjectFactory
/*     */ {
/*  36 */   private static final QName _HTTPGet_QNAME = new QName("http://www.opengis.net/ows", "Get");
/*  37 */   private static final QName _HTTPPost_QNAME = new QName("http://www.opengis.net/ows", "Post");
/*  38 */   private static final QName _Metadata_QNAME = new QName("http://www.opengis.net/ows", "Metadata");
/*  39 */   private static final QName _ContactInfo_QNAME = new QName("http://www.opengis.net/ows", "ContactInfo");
/*  40 */   private static final QName _BoundingBox_QNAME = new QName("http://www.opengis.net/ows", "BoundingBox");
/*  41 */   private static final QName _SupportedCRS_QNAME = new QName("http://www.opengis.net/ows", "SupportedCRS");
/*  42 */   private static final QName _AccessConstraints_QNAME = new QName("http://www.opengis.net/ows", "AccessConstraints");
/*  43 */   private static final QName _PositionName_QNAME = new QName("http://www.opengis.net/ows", "PositionName");
/*  44 */   private static final QName _PointOfContact_QNAME = new QName("http://www.opengis.net/ows", "PointOfContact");
/*  45 */   private static final QName _Keywords_QNAME = new QName("http://www.opengis.net/ows", "Keywords");
/*  46 */   private static final QName _Identifier_QNAME = new QName("http://www.opengis.net/ows", "Identifier");
/*  47 */   private static final QName _OutputFormat_QNAME = new QName("http://www.opengis.net/ows", "OutputFormat");
/*  48 */   private static final QName _OrganisationName_QNAME = new QName("http://www.opengis.net/ows", "OrganisationName");
/*  49 */   private static final QName _AbstractMetaData_QNAME = new QName("http://www.opengis.net/ows", "AbstractMetaData");
/*  50 */   private static final QName _Abstract_QNAME = new QName("http://www.opengis.net/ows", "Abstract");
/*  51 */   private static final QName _AvailableCRS_QNAME = new QName("http://www.opengis.net/ows", "AvailableCRS");
/*  52 */   private static final QName _WGS84BoundingBox_QNAME = new QName("http://www.opengis.net/ows", "WGS84BoundingBox");
/*  53 */   private static final QName _Language_QNAME = new QName("http://www.opengis.net/ows", "Language");
/*  54 */   private static final QName _Role_QNAME = new QName("http://www.opengis.net/ows", "Role");
/*  55 */   private static final QName _IndividualName_QNAME = new QName("http://www.opengis.net/ows", "IndividualName");
/*  56 */   private static final QName _ExtendedCapabilities_QNAME = new QName("http://www.opengis.net/ows", "ExtendedCapabilities");
/*  57 */   private static final QName _Title_QNAME = new QName("http://www.opengis.net/ows", "Title");
/*  58 */   private static final QName _GetCapabilities_QNAME = new QName("http://www.opengis.net/ows", "GetCapabilities");
/*  59 */   private static final QName _Fees_QNAME = new QName("http://www.opengis.net/ows", "Fees");
/*  60 */   private static final QName _Exception_QNAME = new QName("http://www.opengis.net/ows", "Exception");
/*     */ 
/*     */   public DomainType createDomainType()
/*     */   {
/*  74 */     return new DomainType();
/*     */   }
/*     */ 
/*     */   public IdentificationType createIdentificationType()
/*     */   {
/*  82 */     return new IdentificationType();
/*     */   }
/*     */ 
/*     */   public DCP createDCP()
/*     */   {
/*  90 */     return new DCP();
/*     */   }
/*     */ 
/*     */   public BoundingBoxType createBoundingBoxType()
/*     */   {
/*  98 */     return new BoundingBoxType();
/*     */   }
/*     */ 
/*     */   public OnlineResourceType createOnlineResourceType()
/*     */   {
/* 106 */     return new OnlineResourceType();
/*     */   }
/*     */ 
/*     */   public GetCapabilitiesType createGetCapabilitiesType()
/*     */   {
/* 114 */     return new GetCapabilitiesType();
/*     */   }
/*     */ 
/*     */   public MetadataType createMetadataType()
/*     */   {
/* 122 */     return new MetadataType();
/*     */   }
/*     */ 
/*     */   public ExceptionReport createExceptionReport()
/*     */   {
/* 130 */     return new ExceptionReport();
/*     */   }
/*     */ 
/*     */   public SectionsType createSectionsType()
/*     */   {
/* 138 */     return new SectionsType();
/*     */   }
/*     */ 
/*     */   public ContactType createContactType()
/*     */   {
/* 146 */     return new ContactType();
/*     */   }
/*     */ 
/*     */   public RequestMethodType createRequestMethodType()
/*     */   {
/* 154 */     return new RequestMethodType();
/*     */   }
/*     */ 
/*     */   public WGS84BoundingBoxType createWGS84BoundingBoxType()
/*     */   {
/* 162 */     return new WGS84BoundingBoxType();
/*     */   }
/*     */ 
/*     */   public KeywordsType createKeywordsType()
/*     */   {
/* 170 */     return new KeywordsType();
/*     */   }
/*     */ 
/*     */   public CodeType createCodeType()
/*     */   {
/* 178 */     return new CodeType();
/*     */   }
/*     */ 
/*     */   public TelephoneType createTelephoneType()
/*     */   {
/* 186 */     return new TelephoneType();
/*     */   }
/*     */ 
/*     */   public ServiceIdentification createServiceIdentification()
/*     */   {
/* 194 */     return new ServiceIdentification();
/*     */   }
/*     */ 
/*     */   public AcceptVersionsType createAcceptVersionsType()
/*     */   {
/* 202 */     return new AcceptVersionsType();
/*     */   }
/*     */ 
/*     */   public AcceptFormatsType createAcceptFormatsType()
/*     */   {
/* 210 */     return new AcceptFormatsType();
/*     */   }
/*     */ 
/*     */   public AddressType createAddressType()
/*     */   {
/* 218 */     return new AddressType();
/*     */   }
/*     */ 
/*     */   public ResponsiblePartyType createResponsiblePartyType()
/*     */   {
/* 226 */     return new ResponsiblePartyType();
/*     */   }
/*     */ 
/*     */   public Operation createOperation()
/*     */   {
/* 234 */     return new Operation();
/*     */   }
/*     */ 
/*     */   public ResponsiblePartySubsetType createResponsiblePartySubsetType()
/*     */   {
/* 242 */     return new ResponsiblePartySubsetType();
/*     */   }
/*     */ 
/*     */   public ExceptionType createExceptionType()
/*     */   {
/* 250 */     return new ExceptionType();
/*     */   }
/*     */ 
/*     */   public ServiceProvider createServiceProvider()
/*     */   {
/* 258 */     return new ServiceProvider();
/*     */   }
/*     */ 
/*     */   public HTTP createHTTP()
/*     */   {
/* 266 */     return new HTTP();
/*     */   }
/*     */ 
/*     */   public DescriptionType createDescriptionType()
/*     */   {
/* 274 */     return new DescriptionType();
/*     */   }
/*     */ 
/*     */   public CapabilitiesBaseType createCapabilitiesBaseType()
/*     */   {
/* 282 */     return new CapabilitiesBaseType();
/*     */   }
/*     */ 
/*     */   public OperationsMetadata createOperationsMetadata()
/*     */   {
/* 290 */     return new OperationsMetadata();
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="Get", scope=HTTP.class)
/*     */   public JAXBElement<RequestMethodType> createHTTPGet(RequestMethodType value)
/*     */   {
/* 299 */     return new JAXBElement(_HTTPGet_QNAME, RequestMethodType.class, HTTP.class, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="Post", scope=HTTP.class)
/*     */   public JAXBElement<RequestMethodType> createHTTPPost(RequestMethodType value)
/*     */   {
/* 308 */     return new JAXBElement(_HTTPPost_QNAME, RequestMethodType.class, HTTP.class, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="Metadata")
/*     */   public JAXBElement<MetadataType> createMetadata(MetadataType value)
/*     */   {
/* 317 */     return new JAXBElement(_Metadata_QNAME, MetadataType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="ContactInfo")
/*     */   public JAXBElement<ContactType> createContactInfo(ContactType value)
/*     */   {
/* 326 */     return new JAXBElement(_ContactInfo_QNAME, ContactType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="BoundingBox")
/*     */   public JAXBElement<BoundingBoxType> createBoundingBox(BoundingBoxType value)
/*     */   {
/* 335 */     return new JAXBElement(_BoundingBox_QNAME, BoundingBoxType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="SupportedCRS", substitutionHeadNamespace="http://www.opengis.net/ows", substitutionHeadName="AvailableCRS")
/*     */   public JAXBElement<String> createSupportedCRS(String value)
/*     */   {
/* 344 */     return new JAXBElement(_SupportedCRS_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="AccessConstraints")
/*     */   public JAXBElement<String> createAccessConstraints(String value)
/*     */   {
/* 353 */     return new JAXBElement(_AccessConstraints_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="PositionName")
/*     */   public JAXBElement<String> createPositionName(String value)
/*     */   {
/* 362 */     return new JAXBElement(_PositionName_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="PointOfContact")
/*     */   public JAXBElement<ResponsiblePartyType> createPointOfContact(ResponsiblePartyType value)
/*     */   {
/* 371 */     return new JAXBElement(_PointOfContact_QNAME, ResponsiblePartyType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="Keywords")
/*     */   public JAXBElement<KeywordsType> createKeywords(KeywordsType value)
/*     */   {
/* 380 */     return new JAXBElement(_Keywords_QNAME, KeywordsType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="Identifier")
/*     */   public JAXBElement<CodeType> createIdentifier(CodeType value)
/*     */   {
/* 389 */     return new JAXBElement(_Identifier_QNAME, CodeType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="OutputFormat")
/*     */   public JAXBElement<String> createOutputFormat(String value)
/*     */   {
/* 398 */     return new JAXBElement(_OutputFormat_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="OrganisationName")
/*     */   public JAXBElement<String> createOrganisationName(String value)
/*     */   {
/* 407 */     return new JAXBElement(_OrganisationName_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="AbstractMetaData")
/*     */   public JAXBElement<Object> createAbstractMetaData(Object value)
/*     */   {
/* 416 */     return new JAXBElement(_AbstractMetaData_QNAME, Object.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="Abstract")
/*     */   public JAXBElement<String> createAbstract(String value)
/*     */   {
/* 425 */     return new JAXBElement(_Abstract_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="AvailableCRS")
/*     */   public JAXBElement<String> createAvailableCRS(String value)
/*     */   {
/* 434 */     return new JAXBElement(_AvailableCRS_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="WGS84BoundingBox", substitutionHeadNamespace="http://www.opengis.net/ows", substitutionHeadName="BoundingBox")
/*     */   public JAXBElement<WGS84BoundingBoxType> createWGS84BoundingBox(WGS84BoundingBoxType value)
/*     */   {
/* 443 */     return new JAXBElement(_WGS84BoundingBox_QNAME, WGS84BoundingBoxType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="Language")
/*     */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*     */   public JAXBElement<String> createLanguage(String value)
/*     */   {
/* 453 */     return new JAXBElement(_Language_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="Role")
/*     */   public JAXBElement<CodeType> createRole(CodeType value)
/*     */   {
/* 462 */     return new JAXBElement(_Role_QNAME, CodeType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="IndividualName")
/*     */   public JAXBElement<String> createIndividualName(String value)
/*     */   {
/* 471 */     return new JAXBElement(_IndividualName_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="ExtendedCapabilities")
/*     */   public JAXBElement<Object> createExtendedCapabilities(Object value)
/*     */   {
/* 480 */     return new JAXBElement(_ExtendedCapabilities_QNAME, Object.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="Title")
/*     */   public JAXBElement<String> createTitle(String value)
/*     */   {
/* 489 */     return new JAXBElement(_Title_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="GetCapabilities")
/*     */   public JAXBElement<GetCapabilitiesType> createGetCapabilities(GetCapabilitiesType value)
/*     */   {
/* 498 */     return new JAXBElement(_GetCapabilities_QNAME, GetCapabilitiesType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="Fees")
/*     */   public JAXBElement<String> createFees(String value)
/*     */   {
/* 507 */     return new JAXBElement(_Fees_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ows", name="Exception")
/*     */   public JAXBElement<ExceptionType> createException(ExceptionType value)
/*     */   {
/* 516 */     return new JAXBElement(_Exception_QNAME, ExceptionType.class, null, value);
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.ObjectFactory
 * JD-Core Version:    0.6.0
 */