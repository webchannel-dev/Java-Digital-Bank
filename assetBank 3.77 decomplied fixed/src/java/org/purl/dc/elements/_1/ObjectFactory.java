/*     */ package org.purl.dc.elements._1;
/*     */ 
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlElementDecl;
/*     */ import javax.xml.bind.annotation.XmlRegistry;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ @XmlRegistry
/*     */ public class ObjectFactory
/*     */ {
/*  34 */   private static final QName _DCElement_QNAME = new QName("http://purl.org/dc/elements/1.1/", "DC-element");
/*  35 */   private static final QName _Source_QNAME = new QName("http://purl.org/dc/elements/1.1/", "source");
/*  36 */   private static final QName _Coverage_QNAME = new QName("http://purl.org/dc/elements/1.1/", "coverage");
/*  37 */   private static final QName _Date_QNAME = new QName("http://purl.org/dc/elements/1.1/", "date");
/*  38 */   private static final QName _Creator_QNAME = new QName("http://purl.org/dc/elements/1.1/", "creator");
/*  39 */   private static final QName _Publisher_QNAME = new QName("http://purl.org/dc/elements/1.1/", "publisher");
/*  40 */   private static final QName _Subject_QNAME = new QName("http://purl.org/dc/elements/1.1/", "subject");
/*  41 */   private static final QName _Title_QNAME = new QName("http://purl.org/dc/elements/1.1/", "title");
/*  42 */   private static final QName _Relation_QNAME = new QName("http://purl.org/dc/elements/1.1/", "relation");
/*  43 */   private static final QName _Format_QNAME = new QName("http://purl.org/dc/elements/1.1/", "format");
/*  44 */   private static final QName _Language_QNAME = new QName("http://purl.org/dc/elements/1.1/", "language");
/*  45 */   private static final QName _Identifier_QNAME = new QName("http://purl.org/dc/elements/1.1/", "identifier");
/*  46 */   private static final QName _Rights_QNAME = new QName("http://purl.org/dc/elements/1.1/", "rights");
/*  47 */   public static final QName _Type_QNAME = new QName("http://purl.org/dc/elements/1.1/", "type");
/*  48 */   private static final QName _Contributor_QNAME = new QName("http://purl.org/dc/elements/1.1/", "contributor");
/*  49 */   private static final QName _Description_QNAME = new QName("http://purl.org/dc/elements/1.1/", "description");
/*     */ 
/*     */   public SimpleLiteral createSimpleLiteral()
/*     */   {
/*  63 */     return new SimpleLiteral();
/*     */   }
/*     */ 
/*     */   public ElementContainer createElementContainer()
/*     */   {
/*  71 */     return new ElementContainer();
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/elements/1.1/", name="DC-element")
/*     */   public JAXBElement<SimpleLiteral> createDCElement(SimpleLiteral value)
/*     */   {
/*  80 */     return new JAXBElement(_DCElement_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/elements/1.1/", name="source", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="DC-element")
/*     */   public JAXBElement<SimpleLiteral> createSource(SimpleLiteral value)
/*     */   {
/*  89 */     return new JAXBElement(_Source_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/elements/1.1/", name="coverage", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="DC-element")
/*     */   public JAXBElement<SimpleLiteral> createCoverage(SimpleLiteral value)
/*     */   {
/*  98 */     return new JAXBElement(_Coverage_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/elements/1.1/", name="date", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="DC-element")
/*     */   public JAXBElement<SimpleLiteral> createDate(SimpleLiteral value)
/*     */   {
/* 107 */     return new JAXBElement(_Date_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/elements/1.1/", name="creator", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="DC-element")
/*     */   public JAXBElement<SimpleLiteral> createCreator(SimpleLiteral value)
/*     */   {
/* 116 */     return new JAXBElement(_Creator_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/elements/1.1/", name="publisher", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="DC-element")
/*     */   public JAXBElement<SimpleLiteral> createPublisher(SimpleLiteral value)
/*     */   {
/* 125 */     return new JAXBElement(_Publisher_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/elements/1.1/", name="subject", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="DC-element")
/*     */   public JAXBElement<SimpleLiteral> createSubject(SimpleLiteral value)
/*     */   {
/* 134 */     return new JAXBElement(_Subject_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/elements/1.1/", name="title", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="DC-element")
/*     */   public JAXBElement<SimpleLiteral> createTitle(SimpleLiteral value)
/*     */   {
/* 143 */     return new JAXBElement(_Title_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/elements/1.1/", name="relation", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="DC-element")
/*     */   public JAXBElement<SimpleLiteral> createRelation(SimpleLiteral value)
/*     */   {
/* 152 */     return new JAXBElement(_Relation_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/elements/1.1/", name="format", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="DC-element")
/*     */   public JAXBElement<SimpleLiteral> createFormat(SimpleLiteral value)
/*     */   {
/* 161 */     return new JAXBElement(_Format_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/elements/1.1/", name="language", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="DC-element")
/*     */   public JAXBElement<SimpleLiteral> createLanguage(SimpleLiteral value)
/*     */   {
/* 170 */     return new JAXBElement(_Language_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/elements/1.1/", name="identifier", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="DC-element")
/*     */   public JAXBElement<SimpleLiteral> createIdentifier(SimpleLiteral value)
/*     */   {
/* 179 */     return new JAXBElement(_Identifier_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/elements/1.1/", name="rights", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="DC-element")
/*     */   public JAXBElement<SimpleLiteral> createRights(SimpleLiteral value)
/*     */   {
/* 188 */     return new JAXBElement(_Rights_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/elements/1.1/", name="type", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="DC-element")
/*     */   public JAXBElement<SimpleLiteral> createType(SimpleLiteral value)
/*     */   {
/* 197 */     return new JAXBElement(_Type_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/elements/1.1/", name="contributor", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="DC-element")
/*     */   public JAXBElement<SimpleLiteral> createContributor(SimpleLiteral value)
/*     */   {
/* 206 */     return new JAXBElement(_Contributor_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://purl.org/dc/elements/1.1/", name="description", substitutionHeadNamespace="http://purl.org/dc/elements/1.1/", substitutionHeadName="DC-element")
/*     */   public JAXBElement<SimpleLiteral> createDescription(SimpleLiteral value)
/*     */   {
/* 215 */     return new JAXBElement(_Description_QNAME, SimpleLiteral.class, null, value);
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.purl.dc.elements._1.ObjectFactory
 * JD-Core Version:    0.6.0
 */