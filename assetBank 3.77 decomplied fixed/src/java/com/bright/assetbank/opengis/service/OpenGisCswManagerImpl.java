/*     */ package com.bright.assetbank.opengis.service;
/*     */ 
/*     */ import com.bn2web.common.constant.GlobalSettings;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.category.util.CategoryUtil;
import com.bright.assetbank.opengis.bean.OpenGisCswSearchQuery;
/*     */ import com.bright.assetbank.opengis.bean.OpenGisCswSearchQuery.AttributeLookup;
/*     */ import com.bright.assetbank.opengis.constant.OpenGisApiConstants;
/*     */ import com.bright.assetbank.opengis.constant.OpenGisApiSettings;
/*     */ import com.bright.assetbank.opengis.exception.OpenGisServiceException;
/*     */ import com.bright.assetbank.opengis.exception.OpenGisServiceException.ExceptionType;
/*     */ import com.bright.assetbank.opengis.util.OpenGisDomainTranslator;
/*     */ import com.bright.assetbank.opengis.util.OpenGisDomainTranslator.AssetLookup;
/*     */ import com.bright.assetbank.opengis.util.OpenGisUtils;
/*     */ import com.bright.assetbank.search.bean.BaseSearchQuery;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.Group;
/*     */ import com.bright.assetbank.user.bean.GroupCategoryPermission;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import java.io.File;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import net.opengis.cat.csw._2_0.CapabilitiesType;
/*     */ import net.opengis.cat.csw._2_0.DescribeRecordResponseType;
/*     */ import net.opengis.cat.csw._2_0.DescribeRecordType;
/*     */ import net.opengis.cat.csw._2_0.ElementSetNameType;
/*     */ import net.opengis.cat.csw._2_0.ElementSetType;
/*     */ import net.opengis.cat.csw._2_0.GetCapabilitiesType;
/*     */ import net.opengis.cat.csw._2_0.GetRecordByIdResponseType;
/*     */ import net.opengis.cat.csw._2_0.GetRecordByIdType;
/*     */ import net.opengis.cat.csw._2_0.GetRecordsType;
/*     */ import net.opengis.cat.csw._2_0.QueryType;
/*     */ import net.opengis.cat.csw._2_0.RecordType;
/*     */ import net.opengis.cat.csw._2_0.ResultType;
/*     */ import net.opengis.cat.csw._2_0.SchemaComponentType;
/*     */ import net.opengis.ogc.ComparisonOperatorType;
/*     */ import net.opengis.ogc.ComparisonOperatorsType;
/*     */ import net.opengis.ogc.Filter_Capabilities;
/*     */ import net.opengis.ogc.GeometryOperandsType;
/*     */ import net.opengis.ogc.Id_CapabilitiesType;
/*     */ import net.opengis.ogc.Scalar_CapabilitiesType;
/*     */ import net.opengis.ogc.SpatialOperatorNameType;
/*     */ import net.opengis.ogc.SpatialOperatorType;
/*     */ import net.opengis.ogc.SpatialOperatorsType;
/*     */ import net.opengis.ogc.Spatial_CapabilitiesType;
/*     */ import net.opengis.ows.AcceptVersionsType;
/*     */ import net.opengis.ows.AddressType;
/*     */ import net.opengis.ows.CodeType;
/*     */ import net.opengis.ows.ContactType;
/*     */ import net.opengis.ows.DCP;
/*     */ import net.opengis.ows.DomainType;
/*     */ import net.opengis.ows.HTTP;
/*     */ import net.opengis.ows.Operation;
/*     */ import net.opengis.ows.OperationsMetadata;
/*     */ import net.opengis.ows.RequestMethodType;
/*     */ import net.opengis.ows.ResponsiblePartySubsetType;
/*     */ import net.opengis.ows.ServiceIdentification;
/*     */ import net.opengis.ows.ServiceProvider;
/*     */ import net.opengis.ows.TelephoneType;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.axis.utils.XMLUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public class OpenGisCswManagerImpl extends Bn2Manager
/*     */   implements OpenGisCswManager
/*     */ {
/* 118 */   private Log m_logger = GlobalApplication.getInstance().getLogger();
/*     */   private AttributeManager m_attributeManager;
/*     */   private AssetManager m_assetManager;
/*     */   private MultiLanguageSearchManager m_searchManager;
/*     */   private ABUserManager m_userManager;
/*     */ 
/*     */   public Element getCapabilities(Element a_request, boolean a_bUseDefaultNamespace)
/*     */     throws OpenGisServiceException
/*     */   {
/* 131 */     if (this.m_logger.isTraceEnabled())
/*     */     {
/* 133 */       this.m_logger.trace(getClass().getName() + ".getCapabilities() called with following request:\n" + XMLUtils.ElementToString(a_request));
/*     */     }
/*     */ 
/* 136 */     JAXBContext jaxbContext = createJaxbContext();
/* 137 */     GetCapabilitiesType request = (GetCapabilitiesType)unmarshallRequest(a_request, jaxbContext, GetCapabilitiesType.class);
/*     */ 
/* 140 */     OpenGisUtils.validateServiceParameter(request.getService());
/*     */ 
/* 143 */     if (request.getAcceptVersions() != null)
/*     */     {
/* 145 */       List versions = request.getAcceptVersions().getVersion();
/*     */ 
/* 147 */       if ((versions != null) && (!versions.isEmpty()))
/*     */       {
/* 149 */         if (!versions.contains("2.0.2"))
/*     */         {
/* 151 */           throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.VERSION_NEGOTIATION_FAILED, "Must accept version 2.0.2 of the OpenGIS CSW spec as this is the only version implemented.");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 159 */     net.opengis.cat.csw._2_0.ObjectFactory cswFactory = new net.opengis.cat.csw._2_0.ObjectFactory();
/* 160 */     net.opengis.ows.ObjectFactory owsFactory = new net.opengis.ows.ObjectFactory();
/* 161 */     CapabilitiesType response = cswFactory.createCapabilitiesType();
/*     */ 
/* 163 */     response.setVersion("2.0.2");
/*     */ 
/* 166 */     ServiceIdentification si = owsFactory.createServiceIdentification();
/* 167 */     CodeType ct = owsFactory.createCodeType();
/* 168 */     ct.setValue("CSW");
/* 169 */     si.setServiceType(ct);
/* 170 */     si.getServiceTypeVersion().add("2.0.2");
/* 171 */     si.setTitle(OpenGisApiSettings.getOpenGisServerName());
/* 172 */     response.setServiceIdentification(si);
/*     */ 
/* 175 */     if ((StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceProviderName())) || (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactName())) || (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactPosition())) || (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactRoleCodeValue())) || (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactInstuctions())) || (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactHoursOfService())) || (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactTelephone())) || (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactFax())) || (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactEmail())))
/*     */     {
/* 185 */       ServiceProvider sp = owsFactory.createServiceProvider();
/* 186 */       if (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceProviderName()))
/*     */       {
/* 188 */         sp.setProviderName(OpenGisApiSettings.getOpenGisServiceProviderName());
/*     */       }
/* 190 */       if ((StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactName())) || (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactPosition())) || (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactRoleCodeValue())) || (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactInstuctions())) || (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactHoursOfService())) || (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactTelephone())) || (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactFax())) || (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactEmail())))
/*     */       {
/* 199 */         ResponsiblePartySubsetType rpst = owsFactory.createResponsiblePartySubsetType();
/* 200 */         if (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactName()))
/*     */         {
/* 202 */           rpst.setIndividualName(OpenGisApiSettings.getOpenGisServiceContactName());
/*     */         }
/* 204 */         if (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactPosition()))
/*     */         {
/* 206 */           rpst.setPositionName(OpenGisApiSettings.getOpenGisServiceContactPosition());
/*     */         }
/* 208 */         if (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactRoleCodeValue()))
/*     */         {
/* 210 */           CodeType role = owsFactory.createCodeType();
/* 211 */           role.setCodeSpace(OpenGisApiSettings.getOpenGisServiceContactRoleCodeSpace());
/* 212 */           role.setValue(OpenGisApiSettings.getOpenGisServiceContactRoleCodeValue());
/* 213 */           rpst.setRole(role);
/*     */         }
/* 215 */         if ((StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactInstuctions())) || (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactHoursOfService())) || (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactTelephone())) || (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactFax())) || (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactEmail())))
/*     */         {
/* 221 */           ContactType contact = owsFactory.createContactType();
/* 222 */           if (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactInstuctions()))
/*     */           {
/* 224 */             contact.setContactInstructions(OpenGisApiSettings.getOpenGisServiceContactInstuctions());
/*     */           }
/* 226 */           if (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactHoursOfService()))
/*     */           {
/* 228 */             contact.setHoursOfService(OpenGisApiSettings.getOpenGisServiceContactHoursOfService());
/*     */           }
/* 230 */           if ((StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactTelephone())) || (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactFax())))
/*     */           {
/* 233 */             TelephoneType tt = owsFactory.createTelephoneType();
/* 234 */             if (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactTelephone()))
/*     */             {
/* 236 */               tt.getVoice().add(OpenGisApiSettings.getOpenGisServiceContactTelephone());
/*     */             }
/* 238 */             if (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactFax()))
/*     */             {
/* 240 */               tt.getFacsimile().add(OpenGisApiSettings.getOpenGisServiceContactFax());
/*     */             }
/* 242 */             contact.setPhone(tt);
/*     */           }
/* 244 */           if (StringUtils.isNotEmpty(OpenGisApiSettings.getOpenGisServiceContactEmail()))
/*     */           {
/* 246 */             AddressType address = owsFactory.createAddressType();
/* 247 */             address.getElectronicMailAddress().add(OpenGisApiSettings.getOpenGisServiceContactEmail());
/* 248 */             contact.setAddress(address);
/*     */           }
/* 250 */           rpst.setContactInfo(contact);
/*     */         }
/* 252 */         sp.setServiceContact(rpst);
/*     */       }
/* 254 */       response.setServiceProvider(sp);
/*     */     }
/*     */ 
/* 259 */     OperationsMetadata om = owsFactory.createOperationsMetadata();
/*     */ 
/* 262 */     Operation op = addOperationMetadata(om, "GetCapabilities", true);
/*     */ 
/* 264 */     op = addOperationMetadata(om, "DescribeRecord", true);
/* 265 */     addOperationParameter(owsFactory, op, "outputFormat", new String[] { "application/xml" });
/* 266 */     addOperationParameter(owsFactory, op, "typeName", new String[] { XMLUtils.getPrefix("http://www.opengis.net/cat/csw/2.0.2", a_request) + ":" + "Record" });
/* 267 */     addOperationParameter(owsFactory, op, "schemaLanguage", new String[] { "XMLSCHEMA" });
/*     */ 
/* 269 */     op = addOperationMetadata(om, "GetRecords", false);
/* 270 */     addOperationParameter(owsFactory, op, "outputFormat", new String[] { "application/xml" });
/* 271 */     addOperationParameter(owsFactory, op, "typeName", new String[] { XMLUtils.getPrefix("http://www.opengis.net/cat/csw/2.0.2", a_request) + ":" + "Record" });
/* 272 */     addOperationParameter(owsFactory, op, "outputSchema", new String[] { "http://www.opengis.net/cat/csw/2.0.2" });
/* 273 */     addOperationParameter(owsFactory, op, "resultType", new String[] { "hits", "results" });
/* 274 */     addOperationParameter(owsFactory, op, "elementSetName", new String[] { "full" });
/* 275 */     addOperationParameter(owsFactory, op, "CONSTRAINTLANGUAGE", new String[] { "Filter" });
/*     */ 
/* 277 */     op = addOperationMetadata(om, "GetRecordById", false);
/* 278 */     addOperationParameter(owsFactory, op, "outputFormat", new String[] { "application/xml" });
/* 279 */     addOperationParameter(owsFactory, op, "typeName", new String[] { XMLUtils.getPrefix("http://www.opengis.net/cat/csw/2.0.2", a_request) + ":" + "Record" });
/* 280 */     addOperationParameter(owsFactory, op, "outputSchema", new String[] { "http://www.opengis.net/cat/csw/2.0.2" });
/* 281 */     addOperationParameter(owsFactory, op, "elementSetName", new String[] { "full" });
/*     */ 
/* 283 */     response.setOperationsMetadata(om);
/*     */ 
/* 285 */     net.opengis.ogc.ObjectFactory ogcFactory = new net.opengis.ogc.ObjectFactory();
/*     */ 
/* 289 */     Filter_Capabilities fc = ogcFactory.createFilter_Capabilities();
/* 290 */     response.setFilter_Capabilities(fc);
/*     */ 
/* 292 */     Id_CapabilitiesType idCaps = ogcFactory.createId_CapabilitiesType();
/* 293 */     fc.setId_Capabilities(idCaps);
/* 294 */     idCaps.getEIDOrFID().add(ogcFactory.createFID());
/*     */ 
/* 296 */     Scalar_CapabilitiesType scalarCaps = ogcFactory.createScalar_CapabilitiesType();
/* 297 */     fc.setScalar_Capabilities(scalarCaps);
/*     */ 
/* 299 */     scalarCaps.setLogicalOperators(ogcFactory.createLogicalOperators());
/*     */ 
/* 301 */     ComparisonOperatorsType compOps = ogcFactory.createComparisonOperatorsType();
/* 302 */     scalarCaps.setComparisonOperators(compOps);
/*     */ 
/* 304 */     compOps.getComparisonOperator().add(ComparisonOperatorType.EQUAL_TO);
/* 305 */     compOps.getComparisonOperator().add(ComparisonOperatorType.GREATER_THAN);
/* 306 */     compOps.getComparisonOperator().add(ComparisonOperatorType.GREATER_THAN_EQUAL_TO);
/* 307 */     compOps.getComparisonOperator().add(ComparisonOperatorType.LESS_THAN);
/* 308 */     compOps.getComparisonOperator().add(ComparisonOperatorType.LESS_THAN_EQUAL_TO);
/* 309 */     compOps.getComparisonOperator().add(ComparisonOperatorType.LIKE);
/* 310 */     compOps.getComparisonOperator().add(ComparisonOperatorType.BETWEEN);
/* 311 */     compOps.getComparisonOperator().add(ComparisonOperatorType.NULL_CHECK);
/*     */ 
/* 315 */     Spatial_CapabilitiesType spatialCaps = ogcFactory.createSpatial_CapabilitiesType();
/* 316 */     fc.setSpatial_Capabilities(spatialCaps);
/*     */ 
/* 318 */     GeometryOperandsType geoOps = ogcFactory.createGeometryOperandsType();
/* 319 */     spatialCaps.setGeometryOperands(geoOps);
/* 320 */     geoOps.getGeometryOperand().add(net.opengis.gml.ObjectFactory._Envelope_QNAME);
/*     */ 
/* 322 */     SpatialOperatorsType spatialOps = ogcFactory.createSpatialOperatorsType();
/* 323 */     spatialCaps.setSpatialOperators(spatialOps);
/*     */ 
/* 325 */     addSpatialOperator(ogcFactory, spatialOps, SpatialOperatorNameType.BBOX);
/* 326 */     addSpatialOperator(ogcFactory, spatialOps, SpatialOperatorNameType.CONTAINS);
/* 327 */     addSpatialOperator(ogcFactory, spatialOps, SpatialOperatorNameType.OVERLAPS);
/* 328 */     addSpatialOperator(ogcFactory, spatialOps, SpatialOperatorNameType.INTERSECTS);
/*     */ 
/* 330 */     return marshallResponse(response, jaxbContext, a_bUseDefaultNamespace);
/*     */   }
/*     */ 
/*     */   private void addSpatialOperator(net.opengis.ogc.ObjectFactory a_ogcFactory, SpatialOperatorsType a_spatialOps, SpatialOperatorNameType a_type)
/*     */   {
/* 336 */     SpatialOperatorType spatialOp = a_ogcFactory.createSpatialOperatorType();
/* 337 */     a_spatialOps.getSpatialOperator().add(spatialOp);
/* 338 */     spatialOp.setName(a_type);
/* 339 */     GeometryOperandsType geoOps = a_ogcFactory.createGeometryOperandsType();
/* 340 */     geoOps.getGeometryOperand().add(net.opengis.gml.ObjectFactory._Envelope_QNAME);
/* 341 */     spatialOp.setGeometryOperands(geoOps);
/*     */   }
/*     */ 
/*     */   private Operation addOperationMetadata(OperationsMetadata a_om, String a_sService, boolean a_bGetSupported)
/*     */   {
/* 353 */     net.opengis.ows.ObjectFactory owsFactory = new net.opengis.ows.ObjectFactory();
/*     */ 
/* 355 */     Operation op = owsFactory.createOperation();
/* 356 */     op.setName(a_sService);
/* 357 */     DCP dcp = owsFactory.createDCP();
/* 358 */     HTTP http = owsFactory.createHTTP();
/* 359 */     dcp.setHTTP(http);
/* 360 */     op.getDCP().add(dcp);
/* 361 */     a_om.getOperation().add(op);
/*     */ 
/* 363 */     addOperationParameter(owsFactory, op, "Format", new String[] { "text/xml" });
/* 364 */     addOperationParameter(owsFactory, op, "ExceptionFormat", new String[] { "text/xml" });
/*     */ 
/* 367 */     RequestMethodType method = owsFactory.createRequestMethodType();
/* 368 */     method.setHref(AssetBankSettings.getApplicationUrl() + "/" + "OpenGisCsw");
/* 369 */     http.getGetOrPost().add(new JAXBElement(OpenGisApiConstants.k_sQName_Ows_Post, RequestMethodType.class, method));
/* 370 */     DomainType dt = owsFactory.createDomainType();
/* 371 */     dt.setName("PostEncoding");
/* 372 */     dt.getValue().add("XML");
/* 373 */     method.getConstraint().add(dt);
/*     */ 
/* 376 */     method = owsFactory.createRequestMethodType();
/* 377 */     method.setHref(AssetBankSettings.getApplicationUrl() + "/" + "services/OpenGisCswService");
/* 378 */     http.getGetOrPost().add(new JAXBElement(OpenGisApiConstants.k_sQName_Ows_Post, RequestMethodType.class, method));
/* 379 */     dt = owsFactory.createDomainType();
/* 380 */     dt.setName("PostEncoding");
/* 381 */     dt.getValue().add("SOAP");
/* 382 */     method.getConstraint().add(dt);
/*     */ 
/* 385 */     if (a_bGetSupported)
/*     */     {
/* 387 */       method = owsFactory.createRequestMethodType();
/* 388 */       method.setHref(AssetBankSettings.getApplicationUrl() + "/" + "OpenGisCsw");
/* 389 */       http.getGetOrPost().add(new JAXBElement(OpenGisApiConstants.k_sQName_Ows_Get, RequestMethodType.class, method));
/*     */     }
/*     */ 
/* 392 */     return op;
/*     */   }
/*     */ 
/*     */   private Operation addOperationParameter(net.opengis.ows.ObjectFactory a_owsFactory, Operation a_operation, String a_sName, String[] a_asValues)
/*     */   {
/* 397 */     DomainType dt = a_owsFactory.createDomainType();
/* 398 */     dt.setName(a_sName);
/*     */ 
/* 400 */     for (int i = 0; i < a_asValues.length; i++)
/*     */     {
/* 402 */       dt.getValue().add(a_asValues[i]);
/*     */     }
/*     */ 
/* 405 */     a_operation.getParameter().add(dt);
/*     */ 
/* 407 */     return a_operation;
/*     */   }
/*     */ 
/*     */   public Element describeRecord(Element a_request, boolean a_bUseDefaultNamespace)
/*     */     throws OpenGisServiceException
/*     */   {
/* 416 */     if (this.m_logger.isTraceEnabled())
/*     */     {
/* 418 */       this.m_logger.trace(getClass().getName() + ".describeRecords() called with following request:\n" + XMLUtils.ElementToString(a_request));
/*     */     }
/*     */ 
/* 421 */     JAXBContext jaxbContext = createJaxbContext();
/* 422 */     DescribeRecordType request = (DescribeRecordType)unmarshallRequest(a_request, jaxbContext, DescribeRecordType.class);
/*     */ 
/* 425 */     OpenGisUtils.validateServiceParameter(request.getService());
/* 426 */     OpenGisUtils.validateVersionParameter(request.getVersion());
/*     */ 
/* 429 */     OpenGisUtils.validateTypeNames(request.getTypeName());
/*     */ 
/* 431 */     net.opengis.cat.csw._2_0.ObjectFactory cswFactory = new net.opengis.cat.csw._2_0.ObjectFactory();
/*     */ 
/* 433 */     DescribeRecordResponseType response = cswFactory.createDescribeRecordResponseType();
/*     */ 
/* 435 */     SchemaComponentType sct = cswFactory.createSchemaComponentType();
/* 436 */     response.getSchemaComponent().add(sct);
/*     */ 
/* 438 */     sct.setTargetNamespace("http://www.opengis.net/cat/csw/2.0.2");
/* 439 */     sct.setSchemaLanguage("XMLSCHEMA");
/*     */     try
/*     */     {
/* 444 */       DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
/* 445 */       dbfac.setNamespaceAware(true);
/* 446 */       DocumentBuilder builder = dbfac.newDocumentBuilder();
/* 447 */       Document doc = builder.parse(new File(GlobalSettings.getApplicationPath() + "/WEB-INF/manager-config/schema/opengis-csw-record.xsd"));
/* 448 */       sct.getContent().add(doc.getFirstChild());
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/* 452 */       throw new OpenGisServiceException("Could not load/parse record schema file at: " + GlobalSettings.getApplicationPath() + "/WEB-INF/manager-config/schema/opengis-csw-record.xsd", e);
/*     */     }
/*     */ 
/* 456 */     return marshallResponse(response, jaxbContext, a_bUseDefaultNamespace);
/*     */   }
/*     */ 
/*     */   public Element getRecordById(Element a_request, boolean a_bUseDefaultNamespace)
/*     */     throws OpenGisServiceException
/*     */   {
/* 465 */     if (this.m_logger.isTraceEnabled())
/*     */     {
/* 467 */       this.m_logger.trace(getClass().getName() + ".getRecordById() called with following request:\n" + XMLUtils.ElementToString(a_request));
/*     */     }
/*     */ 
/* 470 */     JAXBContext jaxbContext = createJaxbContext();
/* 471 */     GetRecordByIdType request = (GetRecordByIdType)unmarshallRequest(a_request, jaxbContext, GetRecordByIdType.class);
/*     */ 
/* 474 */     OpenGisUtils.validateServiceParameter(request.getService());
/* 475 */     OpenGisUtils.validateVersionParameter(request.getVersion());
/*     */ 
/* 478 */     OpenGisUtils.validateOutputFormat(request.getOutputFormat());
/* 479 */     OpenGisUtils.validateOutputSchema(request.getOutputSchema());
/*     */ 
/* 481 */     RecordType record = null;
/*     */ 
/* 483 */     if (request.getId().size() > 0)
/*     */     {
/* 485 */       String sId = (String)request.getId().get(0);
/*     */       try
/*     */       {
/* 489 */         long lId = Long.parseLong(sId);
/*     */ 
/* 491 */         Asset asset = this.m_assetManager.getAsset(null, lId, null, false, false);
/*     */ 
/* 493 */         if (asset != null)
/*     */         {
/* 496 */           if ((asset.getPermissionCategories() != null) && (OpenGisApiSettings.getPermissionGroupId() > 0L))
/*     */           {
/* 498 */             Group group = this.m_userManager.getGroup(null, OpenGisApiSettings.getPermissionGroupId());
/*     */ 
/* 500 */             if (group != null)
/*     */             {
/* 502 */               Vector<GroupCategoryPermission> permissions = group.getCategoryPermissions();
/*     */ 
/* 505 */               Set groupAccessLevelIds = new HashSet();
/* 506 */               for (GroupCategoryPermission gcp : permissions)
/*     */               {
/* 508 */                 if (gcp.getDownloadPermissionLevel() >= 1)
/*     */                 {
/* 510 */                   groupAccessLevelIds.add(Long.valueOf(gcp.getCategory().getId()));
/*     */                 }
/*     */ 
/*     */               }
/*     */ 
/* 515 */               Set assetAccessLevelIds = new HashSet();
/* 516 */               for (Category accessLevel : asset.getPermissionCategories())
/*     */               {
/* 518 */                 assetAccessLevelIds.add(Long.valueOf(accessLevel.getId()));
/*     */               }
/*     */ 
/* 522 */               if (!CategoryUtil.checkAccessLevelPermissions(groupAccessLevelIds, assetAccessLevelIds))
/*     */               {
/* 524 */                 throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.INVALID_PARAMETER_VALUE, "Record with Id '" + sId + "' is not accessible through this API.");
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/* 529 */               this.m_logger.warn("The group id configured to provide permissions for the OpenGIS CSW API does not exist.");
/*     */             }
/*     */           }
/*     */ 
/* 533 */           record = OpenGisDomainTranslator.translate(asset);
/*     */         }
/*     */         else
/*     */         {
/* 537 */           throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.INVALID_PARAMETER_VALUE, "Record with Id '" + sId + "' does not exist.");
/*     */         }
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/* 542 */         throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.INVALID_PARAMETER_VALUE, "The Id parameter value '" + sId + "' is not numeric.");
/*     */       }
/*     */       catch (Bn2Exception e)
/*     */       {
/* 546 */         throw new OpenGisServiceException("An unexpected error ocurred getting record with id " + sId, e);
/*     */       }
/*     */     }
/*     */ 
/* 550 */     net.opengis.cat.csw._2_0.ObjectFactory cswFactory = new net.opengis.cat.csw._2_0.ObjectFactory();
/* 551 */     GetRecordByIdResponseType response = cswFactory.createGetRecordByIdResponseType();
/*     */ 
/* 554 */     if (record != null)
/*     */     {
/* 556 */       response.getAbstractRecord().add(cswFactory.createRecord(record));
/*     */     }
/*     */ 
/* 559 */     return marshallResponse(response, jaxbContext, a_bUseDefaultNamespace);
/*     */   }
/*     */ 
/*     */   public Element getRecords(Element a_request, boolean a_bUseDefaultNamespace)
/*     */     throws OpenGisServiceException
/*     */   {
/* 568 */     if (this.m_logger.isTraceEnabled())
/*     */     {
/* 570 */       this.m_logger.trace(getClass().getName() + ".getRecords() called with following request:\n" + XMLUtils.ElementToString(a_request));
/*     */     }
/*     */ 
/* 573 */     JAXBContext jaxbContext = createJaxbContext();
/* 574 */     GetRecordsType request = (GetRecordsType)unmarshallRequest(a_request, jaxbContext, GetRecordsType.class);
/*     */ 
/* 576 */     QueryType queryType = (QueryType)request.getAbstractQuery().getValue();
/*     */ 
/* 579 */     OpenGisUtils.validateServiceParameter(request.getService());
/* 580 */     OpenGisUtils.validateVersionParameter(request.getVersion());
/*     */ 
/* 583 */     OpenGisUtils.validateTypeNames(queryType.getTypeNames());
/*     */ 
/* 586 */     OpenGisUtils.validateOutputFormat(request.getOutputFormat());
/* 587 */     OpenGisUtils.validateOutputSchema(request.getOutputSchema());
/*     */ 
/* 590 */     if ((queryType.getElementName() != null) && (queryType.getElementName().size() > 0))
/*     */     {
/* 592 */       throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.INVALID_PARAMETER_VALUE, "The ElementName parameter is not currently supported by this implementation.");
/*     */     }
/*     */ 
/* 596 */     boolean bHitsOnly = false;
/* 597 */     if (request.getResultType().equals(ResultType.HITS))
/*     */     {
/* 599 */       bHitsOnly = true;
/*     */     }
/*     */ 
/* 603 */     BaseSearchQuery query = OpenGisDomainTranslator.translate(request, bHitsOnly, new OpenGisCswSearchQuery.AttributeLookup()
/*     */     {
/*     */       public Attribute lookupAttribute(long a_lId) throws OpenGisServiceException
/*     */       {
/*     */         try {
/* 608 */           return OpenGisCswManagerImpl.this.m_attributeManager.getAttribute(null, a_lId);
/*     */         }
/*     */         catch (Bn2Exception e) {
/*     */         
/* 612 */         throw new OpenGisServiceException("Error whillst looking up native attribute with id " + a_lId, e);
/*     */       }}
/*     */     });
/* 618 */     if (OpenGisApiSettings.getPermissionGroupId() > 0L)
/*     */     {
/*     */       try
/*     */       {
/* 622 */         query.addCategoryRestrictions(OpenGisApiSettings.getPermissionGroupId());
/*     */       }
/*     */       catch (Bn2Exception e)
/*     */       {
/* 626 */         throw new OpenGisServiceException("Bn2Exception whilst adding category restrictions to search.");
/*     */       }
/*     */       catch (ComponentException e)
/*     */       {
/* 630 */         throw new OpenGisServiceException("ComponentException whilst adding category restrictions to search.");
/*     */       }
/*     */     }
/*     */ 
/*     */     SearchResults results;
/*     */     try
/*     */     {
/* 638 */       results = this.m_searchManager.searchByResultIndex(query, OpenGisDomainTranslator.getResultIndex(request), OpenGisDomainTranslator.getNumResults(request));
/*     */     }
/*     */     catch (Bn2Exception e1)
/*     */     {
/* 644 */       throw new OpenGisServiceException("Bn2Exception whilst searching.");
/*     */     }
/*     */ 
/* 651 */     OpenGisDomainTranslator.AssetLookup assetLookup = new OpenGisDomainTranslator.AssetLookup()
/*     */     {
/*     */       public Map<Long, Asset> getAssets(Vector<Long> a_aIds) throws OpenGisServiceException
/*     */       {
/*     */         try
/*     */         {
/* 657 */           Vector<Asset> vAssets = OpenGisCswManagerImpl.this.m_assetManager.getAssets(null, a_aIds, false, false);
/*     */ 
/* 659 */           Map hmAssets = new HashMap(vAssets.size() * 2);
/* 660 */           for (Asset asset : vAssets)
/*     */           {
/* 662 */             hmAssets.put(Long.valueOf(asset.getId()), asset);
/*     */           }
/*     */ 
/* 665 */           return hmAssets;
/*     */         }
/*     */         catch (Bn2Exception e)
/*     */         {
/* 669 */           OpenGisCswManagerImpl.this.m_logger.error("Unexpected Bn2Exception whist getting full records for search results.", e);
/* 670 */         throw new OpenGisServiceException("Unexpected Bn2Exception whist getting full records for search results.", e);
/*     */      } }
/*     */     };
/*     */     Object response;
/*     */     //Object response;
/* 676 */     if ((queryType.getElementSetName() != null) && (ElementSetType.BRIEF.equals(queryType.getElementSetName().getValue())))
/*     */     {
/* 680 */       response = OpenGisDomainTranslator.translate(results, bHitsOnly, assetLookup);
/*     */     }
/*     */     else
/*     */     {
/*     */       //sObject response;
/* 682 */       if ((queryType.getElementSetName() != null) && (ElementSetType.SUMMARY.equals(queryType.getElementSetName().getValue())))
/*     */       {
/* 686 */         response = OpenGisDomainTranslator.translate(results, bHitsOnly, assetLookup);
/*     */       }
/*     */       else
/*     */       {
/* 690 */         response = OpenGisDomainTranslator.translate(results, bHitsOnly, assetLookup);
/*     */       }
/*     */     }
/* 693 */     return marshallResponse(response, jaxbContext, a_bUseDefaultNamespace);
/*     */   }
/*     */ 
/*     */   private Element marshallResponse(Object a_response, JAXBContext a_jaxbContext, boolean a_bUseDefaultNamespace)
/*     */     throws OpenGisServiceException
/*     */   {
/*     */     try
/*     */     {
/* 711 */       DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
/* 712 */       DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
/* 713 */       Document doc = docBuilder.newDocument();
/*     */ 
/* 715 */       Marshaller marshaller = OpenGisUtils.createMarshaller(a_jaxbContext, a_bUseDefaultNamespace);
/*     */ 
/* 718 */       marshaller.marshal(a_response, doc);
/*     */ 
/* 721 */       return (Element)doc.getFirstChild();
/*     */     }
/*     */     catch (JAXBException e)
/*     */     {
/* 725 */       this.m_logger.error("JAXBException caught whilst marshalling from " + a_response.getClass().getSimpleName() + ":\n" + a_response);
/* 726 */       throw new OpenGisServiceException("JAXBException caught whilst marhalling from " + a_response.getClass().getSimpleName(), e);
/*     */     }
/*     */     catch (ParserConfigurationException e)
/*     */     {
/* 730 */       this.m_logger.error("ParserConfigurationException caught whilst marhalling from " + a_response.getClass().getSimpleName());
/* 731 */     throw new OpenGisServiceException("ParserConfigurationException caught whilst marhalling from " + a_response.getClass().getSimpleName(), e);
/*     */  } }
/*     */ 
/*     */   private <T> T unmarshallRequest(Element a_request, JAXBContext a_jaxbContext, Class<T> a_expectedClass)
/*     */     throws OpenGisServiceException
/*     */   {
/* 747 */     Object request = null;
/*     */     try
/*     */     {
/* 751 */       Unmarshaller unmarshaller = a_jaxbContext.createUnmarshaller();
/*     */ 
/* 754 */       JAXBElement requestElement = (JAXBElement)unmarshaller.unmarshal(a_request);
/*     */ 
/* 757 */       request = getUnmarshalledType(a_request, requestElement, a_expectedClass);
/*     */     }
/*     */     catch (JAXBException e)
/*     */     {
/* 761 */       this.m_logger.error("JAXBException caught whilst unmarhalling xml to " + a_expectedClass.getSimpleName() + ":\n" + XMLUtils.ElementToString(a_request));
/* 762 */       throw new OpenGisServiceException("JAXBException caught whilst unmarhalling xml to " + a_expectedClass.getSimpleName(), e);
/*     */     }
/* 764 */     return (T)request;
/*     */   }
/*     */ 
/*     */   private JAXBContext createJaxbContext() throws OpenGisServiceException
/*     */   {
/*     */     try
/*     */     {
/* 771 */       return OpenGisUtils.createContext();
/*     */     }
/*     */     catch (JAXBException e)
/*     */     {
/* 775 */       this.m_logger.error("JAXBException caught whilst instatiating JAXBContext context");
/* 776 */     throw new OpenGisServiceException("JAXBException caught whilst instatiating JAXBContext context", e);
/*     */ }  }
/*     */ 
/*     */   private <T> T getUnmarshalledType(Element a_requestXml, JAXBElement a_unmarshalledJaxElement, Class<T> a_expectedClass)
/*     */     throws OpenGisServiceException
/*     */   {
/* 793 */     if ((a_unmarshalledJaxElement == null) || (a_unmarshalledJaxElement.getValue() == null))
/*     */     {
/* 795 */       this.m_logger.error("Unmarshalled JAXBElement" + (a_unmarshalledJaxElement == null ? "" : "'s value ") + " was null.\n" + XMLUtils.ElementToString(a_requestXml));
/* 796 */       throw new OpenGisServiceException("Unmarshalled JAXBElement" + (a_unmarshalledJaxElement == null ? "" : "'s value ") + " was null.\n" + XMLUtils.ElementToString(a_requestXml));
/*     */     }
/*     */ 
/* 799 */     if (!a_expectedClass.isAssignableFrom(a_unmarshalledJaxElement.getValue().getClass()))
/*     */     {
/* 801 */       this.m_logger.error("Type of unmarshalled request was not as expected. Wanted " + a_expectedClass.getName() + ", got " + a_unmarshalledJaxElement.getValue().getClass().getName() + " :\n" + XMLUtils.ElementToString(a_requestXml));
/*     */ 
/* 804 */       throw new OpenGisServiceException("Type of unmarshalled request was not as expected. Wanted " + a_expectedClass.getName() + ", got " + a_unmarshalledJaxElement.getValue().getClass().getName());
/*     */     }
/*     */ 
/* 808 */     return (T)a_unmarshalledJaxElement.getValue();
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/* 816 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 824 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/* 832 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 841 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.opengis.service.OpenGisCswManagerImpl
 * JD-Core Version:    0.6.0
 */