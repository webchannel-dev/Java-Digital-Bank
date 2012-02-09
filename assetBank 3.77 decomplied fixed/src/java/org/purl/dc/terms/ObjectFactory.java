/*     */ package org.purl.dc.terms;
/*     */ 
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlElementDecl;
/*     */ import javax.xml.bind.annotation.XmlRegistry;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.purl.dc.elements._1.SimpleLiteral;
/*     */ 
/*     */ @XmlRegistry
/*     */ public class ObjectFactory
/*     */ {
/*  35 */   private static final QName _BibliographicCitation_QNAME = new QName("http://purl.org/dc/terms/", "bibliographicCitation");
/*  36 */   private static final QName _License_QNAME = new QName("http://purl.org/dc/terms/", "license");
/*  37 */   private static final QName _ConformsTo_QNAME = new QName("http://purl.org/dc/terms/", "conformsTo");
/*  38 */   private static final QName _Extent_QNAME = new QName("http://purl.org/dc/terms/", "extent");
/*  39 */   private static final QName _Valid_QNAME = new QName("http://purl.org/dc/terms/", "valid");
/*  40 */   private static final QName _IsFormatOf_QNAME = new QName("http://purl.org/dc/terms/", "isFormatOf");
/*  41 */   private static final QName _TableOfContents_QNAME = new QName("http://purl.org/dc/terms/", "tableOfContents");
/*  42 */   private static final QName _DateCopyrighted_QNAME = new QName("http://purl.org/dc/terms/", "dateCopyrighted");
/*  43 */   private static final QName _IsReferencedBy_QNAME = new QName("http://purl.org/dc/terms/", "isReferencedBy");
/*  44 */   private static final QName _IsVersionOf_QNAME = new QName("http://purl.org/dc/terms/", "isVersionOf");
/*  45 */   private static final QName _AccessRights_QNAME = new QName("http://purl.org/dc/terms/", "accessRights");
/*  46 */   private static final QName _Temporal_QNAME = new QName("http://purl.org/dc/terms/", "temporal");
/*  47 */   private static final QName _HasPart_QNAME = new QName("http://purl.org/dc/terms/", "hasPart");
/*  48 */   private static final QName _Medium_QNAME = new QName("http://purl.org/dc/terms/", "medium");
/*  49 */   private static final QName _Abstract_QNAME = new QName("http://purl.org/dc/terms/", "abstract");
/*  50 */   private static final QName _Audience_QNAME = new QName("http://purl.org/dc/terms/", "audience");
/*  51 */   private static final QName _Spatial_QNAME = new QName("http://purl.org/dc/terms/", "spatial");
/*  52 */   private static final QName _DateSubmitted_QNAME = new QName("http://purl.org/dc/terms/", "dateSubmitted");
/*  53 */   private static final QName _HasFormat_QNAME = new QName("http://purl.org/dc/terms/", "hasFormat");
/*  54 */   private static final QName _References_QNAME = new QName("http://purl.org/dc/terms/", "references");
/*  55 */   private static final QName _Created_QNAME = new QName("http://purl.org/dc/terms/", "created");
/*  56 */   private static final QName _EducationLevel_QNAME = new QName("http://purl.org/dc/terms/", "educationLevel");
/*  57 */   private static final QName _RightsHolder_QNAME = new QName("http://purl.org/dc/terms/", "rightsHolder");
/*  58 */   private static final QName _IsReplacedBy_QNAME = new QName("http://purl.org/dc/terms/", "isReplacedBy");
/*  59 */   private static final QName _HasVersion_QNAME = new QName("http://purl.org/dc/terms/", "hasVersion");
/*  60 */   private static final QName _Mediator_QNAME = new QName("http://purl.org/dc/terms/", "mediator");
/*  61 */   private static final QName _IsPartOf_QNAME = new QName("http://purl.org/dc/terms/", "isPartOf");
/*  62 */   private static final QName _Provenance_QNAME = new QName("http://purl.org/dc/terms/", "provenance");
/*  63 */   private static final QName _DateAccepted_QNAME = new QName("http://purl.org/dc/terms/", "dateAccepted");
/*  64 */   private static final QName _Alternative_QNAME = new QName("http://purl.org/dc/terms/", "alternative");
/*  65 */   private static final QName _Available_QNAME = new QName("http://purl.org/dc/terms/", "available");
/*  66 */   private static final QName _Requires_QNAME = new QName("http://purl.org/dc/terms/", "requires");
/*  67 */   private static final QName _IsRequiredBy_QNAME = new QName("http://purl.org/dc/terms/", "isRequiredBy");
/*  68 */   private static final QName _Modified_QNAME = new QName("http://purl.org/dc/terms/", "modified");
/*  69 */   private static final QName _Replaces_QNAME = new QName("http://purl.org/dc/terms/", "replaces");
/*  70 */   private static final QName _Issued_QNAME = new QName("http://purl.org/dc/terms/", "issued");
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="bibliographicCitation", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="identifier")
/*     */   public JAXBElement<SimpleLiteral> createBibliographicCitation(SimpleLiteral value)
/*     */   {
/*  85 */     return new JAXBElement(_BibliographicCitation_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="license", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="rights")
/*     */   public JAXBElement<SimpleLiteral> createLicense(SimpleLiteral value)
/*     */   {
/*  94 */     return new JAXBElement(_License_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="conformsTo", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="relation")
/*     */   public JAXBElement<SimpleLiteral> createConformsTo(SimpleLiteral value)
/*     */   {
/* 103 */     return new JAXBElement(_ConformsTo_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="extent", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="format")
/*     */   public JAXBElement<SimpleLiteral> createExtent(SimpleLiteral value)
/*     */   {
/* 112 */     return new JAXBElement(_Extent_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="valid", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="date")
/*     */   public JAXBElement<SimpleLiteral> createValid(SimpleLiteral value)
/*     */   {
/* 121 */     return new JAXBElement(_Valid_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="isFormatOf", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="relation")
/*     */   public JAXBElement<SimpleLiteral> createIsFormatOf(SimpleLiteral value)
/*     */   {
/* 130 */     return new JAXBElement(_IsFormatOf_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="tableOfContents", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="description")
/*     */   public JAXBElement<SimpleLiteral> createTableOfContents(SimpleLiteral value)
/*     */   {
/* 139 */     return new JAXBElement(_TableOfContents_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="dateCopyrighted", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="date")
/*     */   public JAXBElement<SimpleLiteral> createDateCopyrighted(SimpleLiteral value)
/*     */   {
/* 148 */     return new JAXBElement(_DateCopyrighted_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="isReferencedBy", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="relation")
/*     */   public JAXBElement<SimpleLiteral> createIsReferencedBy(SimpleLiteral value)
/*     */   {
/* 157 */     return new JAXBElement(_IsReferencedBy_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="isVersionOf", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="relation")
/*     */   public JAXBElement<SimpleLiteral> createIsVersionOf(SimpleLiteral value)
/*     */   {
/* 166 */     return new JAXBElement(_IsVersionOf_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="accessRights", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="rights")
/*     */   public JAXBElement<SimpleLiteral> createAccessRights(SimpleLiteral value)
/*     */   {
/* 175 */     return new JAXBElement(_AccessRights_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="temporal", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="coverage")
/*     */   public JAXBElement<SimpleLiteral> createTemporal(SimpleLiteral value)
/*     */   {
/* 184 */     return new JAXBElement(_Temporal_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="hasPart", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="relation")
/*     */   public JAXBElement<SimpleLiteral> createHasPart(SimpleLiteral value)
/*     */   {
/* 193 */     return new JAXBElement(_HasPart_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="medium", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="format")
/*     */   public JAXBElement<SimpleLiteral> createMedium(SimpleLiteral value)
/*     */   {
/* 202 */     return new JAXBElement(_Medium_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="abstract", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="description")
/*     */   public JAXBElement<SimpleLiteral> createAbstract(SimpleLiteral value)
/*     */   {
/* 211 */     return new JAXBElement(_Abstract_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="audience", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="DC-element")
/*     */   public JAXBElement<SimpleLiteral> createAudience(SimpleLiteral value)
/*     */   {
/* 220 */     return new JAXBElement(_Audience_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="spatial", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="coverage")
/*     */   public JAXBElement<SimpleLiteral> createSpatial(SimpleLiteral value)
/*     */   {
/* 229 */     return new JAXBElement(_Spatial_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="dateSubmitted", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="date")
/*     */   public JAXBElement<SimpleLiteral> createDateSubmitted(SimpleLiteral value)
/*     */   {
/* 238 */     return new JAXBElement(_DateSubmitted_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="hasFormat", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="relation")
/*     */   public JAXBElement<SimpleLiteral> createHasFormat(SimpleLiteral value)
/*     */   {
/* 247 */     return new JAXBElement(_HasFormat_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="references", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="relation")
/*     */   public JAXBElement<SimpleLiteral> createReferences(SimpleLiteral value)
/*     */   {
/* 256 */     return new JAXBElement(_References_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="created", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="date")
/*     */   public JAXBElement<SimpleLiteral> createCreated(SimpleLiteral value)
/*     */   {
/* 265 */     return new JAXBElement(_Created_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="educationLevel", substitutionHeadNamespace="http://purl.org/dc/terms/", substitutionHeadName="audience")
/*     */   public JAXBElement<SimpleLiteral> createEducationLevel(SimpleLiteral value)
/*     */   {
/* 274 */     return new JAXBElement(_EducationLevel_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="rightsHolder", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="DC-element")
/*     */   public JAXBElement<SimpleLiteral> createRightsHolder(SimpleLiteral value)
/*     */   {
/* 283 */     return new JAXBElement(_RightsHolder_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="isReplacedBy", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="relation")
/*     */   public JAXBElement<SimpleLiteral> createIsReplacedBy(SimpleLiteral value)
/*     */   {
/* 292 */     return new JAXBElement(_IsReplacedBy_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="hasVersion", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="relation")
/*     */   public JAXBElement<SimpleLiteral> createHasVersion(SimpleLiteral value)
/*     */   {
/* 301 */     return new JAXBElement(_HasVersion_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="mediator", substitutionHeadNamespace="http://purl.org/dc/terms/", substitutionHeadName="audience")
/*     */   public JAXBElement<SimpleLiteral> createMediator(SimpleLiteral value)
/*     */   {
/* 310 */     return new JAXBElement(_Mediator_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="isPartOf", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="relation")
/*     */   public JAXBElement<SimpleLiteral> createIsPartOf(SimpleLiteral value)
/*     */   {
/* 319 */     return new JAXBElement(_IsPartOf_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="provenance", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="DC-element")
/*     */   public JAXBElement<SimpleLiteral> createProvenance(SimpleLiteral value)
/*     */   {
/* 328 */     return new JAXBElement(_Provenance_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="dateAccepted", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="date")
/*     */   public JAXBElement<SimpleLiteral> createDateAccepted(SimpleLiteral value)
/*     */   {
/* 337 */     return new JAXBElement(_DateAccepted_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="alternative", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="title")
/*     */   public JAXBElement<SimpleLiteral> createAlternative(SimpleLiteral value)
/*     */   {
/* 346 */     return new JAXBElement(_Alternative_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="available", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="date")
/*     */   public JAXBElement<SimpleLiteral> createAvailable(SimpleLiteral value)
/*     */   {
/* 355 */     return new JAXBElement(_Available_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="requires", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="relation")
/*     */   public JAXBElement<SimpleLiteral> createRequires(SimpleLiteral value)
/*     */   {
/* 364 */     return new JAXBElement(_Requires_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="isRequiredBy", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="relation")
/*     */   public JAXBElement<SimpleLiteral> createIsRequiredBy(SimpleLiteral value)
/*     */   {
/* 373 */     return new JAXBElement(_IsRequiredBy_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="modified", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="date")
/*     */   public JAXBElement<SimpleLiteral> createModified(SimpleLiteral value)
/*     */   {
/* 382 */     return new JAXBElement(_Modified_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="replaces", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="relation")
/*     */   public JAXBElement<SimpleLiteral> createReplaces(SimpleLiteral value)
/*     */   {
/* 391 */     return new JAXBElement(_Replaces_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/terms/", name="issued", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="date")
/*     */   public JAXBElement<SimpleLiteral> createIssued(SimpleLiteral value)
/*     */   {
/* 400 */     return new JAXBElement(_Issued_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.purl.dc.terms.ObjectFactory
 * JD-Core Version:    0.6.0
 */