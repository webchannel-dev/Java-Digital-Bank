/*     */ package com.bright.assetbank.opengis.util;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.FileFormat;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.opengis.bean.OpenGisCswSearchQuery;
/*     */ import com.bright.assetbank.opengis.bean.OpenGisCswSearchQuery.AttributeLookup;
/*     */ import com.bright.assetbank.opengis.constant.OpenGisApiSettings;
/*     */ import com.bright.assetbank.opengis.exception.OpenGisServiceException;
/*     */ import com.bright.assetbank.opengis.exception.OpenGisServiceException.ExceptionType;
/*     */ import com.bright.framework.category.util.CategoryUtil;
/*     */ import com.bright.framework.common.bean.BrightDate;
/*     */ import com.bright.framework.common.bean.BrightDateTime;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.util.XMLUtil;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.datatype.XMLGregorianCalendar;
/*     */ import javax.xml.namespace.QName;
/*     */ import net.opengis.cat.csw._2_0.ElementSetType;
/*     */ import net.opengis.cat.csw._2_0.GetRecordsResponseType;
/*     */ import net.opengis.cat.csw._2_0.GetRecordsType;
/*     */ import net.opengis.cat.csw._2_0.QueryConstraintType;
/*     */ import net.opengis.cat.csw._2_0.QueryType;
/*     */ import net.opengis.cat.csw._2_0.RecordType;
/*     */ import net.opengis.cat.csw._2_0.RequestStatusType;
/*     */ import net.opengis.cat.csw._2_0.SearchResultsType;
/*     */ import net.opengis.ogc.FilterType;
/*     */ import net.opengis.ogc.PropertyNameType;
/*     */ import net.opengis.ogc.SortByType;
/*     */ import net.opengis.ogc.SortOrderType;
/*     */ import net.opengis.ogc.SortPropertyType;
/*     */ import net.opengis.ows.BoundingBoxType;
/*     */ import net.opengis.ows.ExceptionReport;
/*     */ //import net.opengis.ows.ExceptionType;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.lucene.search.SortField;
/*     */ import org.purl.dc.elements._1.SimpleLiteral;
/*     */ 
/*     */ public class OpenGisDomainTranslator
/*     */ {
/*  80 */   private static Log k_logger = GlobalApplication.getInstance().getLogger();
/*     */ 
/*  83 */   private static net.opengis.cat.csw._2_0.ObjectFactory k_cswFactory = new net.opengis.cat.csw._2_0.ObjectFactory();
/*  84 */   private static org.purl.dc.elements._1.ObjectFactory k_dcFactory = new org.purl.dc.elements._1.ObjectFactory();
/*  85 */   private static net.opengis.ows.ObjectFactory k_owsFactory = new net.opengis.ows.ObjectFactory();
/*     */ 
/*     */   public static OpenGisCswSearchQuery translate(GetRecordsType a_source, boolean a_bHitsOnly, OpenGisCswSearchQuery.AttributeLookup a_attLookup)
/*     */     throws OpenGisServiceException
/*     */   {
/* 104 */     OpenGisCswSearchQuery query = new OpenGisCswSearchQuery();
/*     */ 
/* 106 */     if (a_bHitsOnly)
/*     */     {
/* 108 */       query.setMaxNoOfResults(0);
/*     */     }
/*     */     else
/*     */     {
/* 112 */       int iIndex = getResultIndex(a_source);
/* 113 */       int iNumResults = getNumResults(a_source);
/*     */ 
/* 115 */       if ((iNumResults > 0) && (iIndex >= 0))
/*     */       {
/* 117 */         query.setMaxNoOfResults(iIndex + iNumResults + 1);
/*     */       }
/*     */       else
/*     */       {
/* 121 */         query.setMaxNoOfResults(1000);
/*     */       }
/*     */     }
/*     */ 
/* 125 */     QueryType queryType = (QueryType)a_source.getAbstractQuery().getValue();
/* 126 */     QueryConstraintType constraint = queryType.getConstraint();
/*     */ 
/* 128 */     FilterType filter = constraint.getFilter();
/*     */ 
/* 130 */     if (filter == null)
/*     */     {
/* 132 */       throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.MISSING_PARAMETER_VALUE, "The Filter element is missing.");
/*     */     }
/*     */ 
/* 135 */     query.setOpenGisFilter(filter, a_attLookup);
/*     */ 
/* 139 */     SortByType sortType = queryType.getSortBy();
/*     */ 
/* 141 */     if (sortType != null)
/*     */     {
/* 143 */       ArrayList sortFields = new ArrayList();
/*     */ 
/* 146 */       for (SortPropertyType sortProperty : sortType.getSortProperty())
/*     */       {
/* 148 */         if (sortProperty.getPropertyName().getContent().size() > 0)
/*     */         {
/* 151 */           String sPropertyName = sortProperty.getPropertyName().getContent().get(0).toString();
/* 152 */           Attribute attribute = OpenGisUtils.getMappedAttribute(sPropertyName, a_attLookup);
/* 153 */           SortField sortField = AttributeUtil.getSortFieldForAttributeSort(attribute, !SortOrderType.ASC.equals(sortProperty.getSortOrder()));
/* 154 */           sortFields.add(sortField);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 159 */       if (sortFields.size() > 0)
/*     */       {
/* 161 */         sortFields.add(SortField.FIELD_SCORE);
/* 162 */         SortField[] aSortFields = (SortField[])sortFields.toArray(new SortField[sortFields.size()]);
/* 163 */         query.setSortFields(aSortFields);
/*     */       }
/*     */     }
/*     */ 
/* 167 */     return query;
/*     */   }
/*     */ 
/*     */   public static int getResultIndex(GetRecordsType a_source)
/*     */   {
/* 177 */     if (a_source.getStartPosition() != null)
/*     */     {
/* 179 */       return a_source.getStartPosition().intValue() - 1;
/*     */     }
/* 181 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int getNumResults(GetRecordsType a_source)
/*     */   {
/* 191 */     if (a_source.getMaxRecords() != null)
/*     */     {
/* 193 */       return a_source.getMaxRecords().intValue();
/*     */     }
/* 195 */     return -1;
/*     */   }
/*     */ 
/*     */   public static GetRecordsResponseType translate(SearchResults a_source, boolean a_bHitsOnly, AssetLookup a_assetLookup)
/*     */     throws OpenGisServiceException
/*     */   {
/* 209 */     GetRecordsResponseType response = k_cswFactory.createGetRecordsResponseType();
/*     */ 
/* 213 */     RequestStatusType status = k_cswFactory.createRequestStatusType();
/* 214 */     response.setSearchStatus(status);
/* 215 */     status.setTimestamp(XMLUtil.getXMLGregorianCalendar(new Date()));
/*     */ 
/* 219 */     SearchResultsType results = k_cswFactory.createSearchResultsType();
/* 220 */     response.setSearchResults(results);
/*     */ 
/* 222 */     results.setNumberOfRecordsMatched(BigInteger.valueOf(a_source.getTotalHits()));
/*     */     Map hmAssets;
/* 224 */     if (a_bHitsOnly)
/*     */     {
/* 226 */       results.setNumberOfRecordsReturned(BigInteger.valueOf(0L));
/*     */     }
/*     */     else
/*     */     {
/* 230 */       results.setNumberOfRecordsReturned(BigInteger.valueOf(a_source.getNumResultsPopulated()));
/*     */ 
/* 232 */       results.setElementSet(ElementSetType.FULL);
/*     */ 
/* 234 */       results.setRecordSchema("http://schemas.opengis.net/csw/2.0.2/record.xsd");
/*     */ 
/* 237 */       Vector<Long> vIds = new Vector(a_source.getNumResultsPopulated());
                Vector<LightweightAsset> lassets = a_source.getSearchResults();
/* 238 */       for (LightweightAsset asset :lassets )
/*     */       {
/* 240 */         vIds.add(Long.valueOf(asset.getId()));
/*     */       }
/*     */ 
/* 244 */       hmAssets = a_assetLookup.getAssets(vIds);
/*     */ 
/* 247 */       for (Long lId : vIds)
/*     */       {
/* 249 */         RecordType record = translate((Asset)hmAssets.get(lId));
/*     */ 
/* 251 */         results.getAbstractRecord().add(k_cswFactory.createRecord(record));
/*     */       }
/*     */     }
/*     */ 
/* 255 */     return response;
/*     */   }
/*     */ 
/*     */   public static RecordType translate(Asset asset) throws OpenGisServiceException
/*     */   {
/* 260 */     RecordType record = k_cswFactory.createRecordType();
/*     */ 
/* 263 */     SimpleLiteral type = OpenGisUtils.createSimpleLiteral(getDublinCoreType(asset));
/* 264 */     record.getDCElement().add(k_dcFactory.createType(type));
/*     */ 
/* 267 */     if (asset.getHasFile())
/*     */     {
/*     */       SimpleLiteral format;
/*     */       //SimpleLiteral format;
/* 271 */       if (StringUtils.isNotEmpty(asset.getFormat().getContentType()))
/*     */       {
/* 273 */         format = OpenGisUtils.createSimpleLiteral(asset.getFormat().getContentType());
/*     */       }
/*     */       else
/*     */       {
/* 277 */         format = OpenGisUtils.createSimpleLiteral(asset.getFormat().getName());
/*     */       }
/* 279 */       record.getDCElement().add(k_dcFactory.createFormat(format));
/*     */     }
/*     */ 
/* 282 */     BoundingBoxType bbox = k_owsFactory.createBoundingBoxType();
/*     */ 
/* 285 */     for (AttributeValue attVal : asset.getAttributeValues())
/*     */     {
/* 288 */       if ((StringUtils.isEmpty(attVal.getValue())) && (attVal.getKeywordCategories() == null))
/*     */       {
/*     */         continue;
/*     */       }
/*     */ 
/* 294 */       String sName = OpenGisApiSettings.getPropertyNameForAttribtueId(String.valueOf(attVal.getAttribute().getId()));
/*     */ 
/* 297 */       if (StringUtils.isEmpty(sName))
/*     */       {
/*     */         continue;
/*     */       }
/*     */ 
/* 303 */       if (isBoundingBoxProperty(sName))
/*     */       {
/*     */         try
/*     */         {
/* 307 */           if (sName.endsWith("_LEFT"))
/*     */           {
/* 309 */             bbox.getLowerCorner().add(0, Double.valueOf(Double.parseDouble(attVal.getValue())));
/*     */           }
/* 311 */           else if (sName.endsWith("_RIGHT"))
/*     */           {
/* 313 */             bbox.getUpperCorner().add(0, Double.valueOf(Double.parseDouble(attVal.getValue())));
/*     */           }
/* 315 */           else if (sName.endsWith("_BOTTOM"))
/*     */           {
/* 317 */             bbox.getLowerCorner().add(Double.valueOf(Double.parseDouble(attVal.getValue())));
/*     */           }
/* 319 */           else if (sName.endsWith("_TOP"))
/*     */           {
/* 321 */             bbox.getUpperCorner().add(Double.valueOf(Double.parseDouble(attVal.getValue())));
/*     */           }
/*     */         }
/*     */         catch (NumberFormatException e)
/*     */         {
/* 326 */           k_logger.error("Native attribute '" + attVal.getAttribute().getLabel() + "' is mapped to a bounding box point, but is not numeric.", e);
/* 327 */           throw new OpenGisServiceException("Native attribute '" + attVal.getAttribute().getLabel() + "' is mapped to a bounding box point, but is not numeric.", e);
/*     */         }
/*     */ 
/* 331 */         if ((bbox.getLowerCorner().size() == 2) && (bbox.getUpperCorner().size() == 2))
/*     */         {
/* 333 */           record.getBoundingBox().add(k_owsFactory.createBoundingBox(bbox));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*     */         String sValue;
/*     */         //String sValue;
/* 341 */         if ((attVal.getDateValue() != null) && (attVal.getDateValue().getDate() != null))
/*     */         {
/* 343 */           sValue = XMLUtil.getXMLGregorianCalendar(attVal.getDateValue().getDate()).toXMLFormat();
/*     */         }
/*     */         else
/*     */         {
/*     */           //String sValue;
/* 345 */           if ((attVal.getDateTimeValue() != null) && (attVal.getDateTimeValue().getDate() != null))
/*     */           {
/* 347 */             sValue = XMLUtil.getXMLGregorianCalendar(attVal.getDateTimeValue().getDate()).toXMLFormat();
/*     */           }
/*     */           else
/*     */           {
/*     */             //String sValue;
/* 349 */             if (attVal.getAttribute().getIsKeywordPicker())
/*     */             {
/* 351 */               sValue = CategoryUtil.getCategoryDescriptionString(attVal.getKeywordCategories(), OpenGisApiSettings.getKeywordAttributeValueSeparator(), OpenGisApiSettings.getKeywordAttributeHierarchySeparator());
/*     */             }
/*     */             else
/*     */             {
/* 357 */               sValue = attVal.getValue();
/*     */             }
/*     */           }
/*     */         }
/* 360 */         String sPrefix = XMLUtil.getPrefix(sName);
/* 361 */         String sLocalPart = XMLUtil.getLocalPart(sName);
/* 362 */         String sNamespace = OpenGisUtils.getNamespaceUriForPrefix(sPrefix);
/*     */         //QName elementName;
/*     */         QName elementName;
/* 367 */         if (!StringUtils.isEmpty(sNamespace))
/*     */         {
/* 369 */           elementName = new QName(sNamespace, sLocalPart, sPrefix);
/*     */         }
/*     */         else
/*     */         {
/* 373 */           elementName = new QName("http://www.opengis.net/cat/csw/2.0.2", sName);
/*     */         }
/*     */ 
/* 376 */         SimpleLiteral value = OpenGisUtils.createSimpleLiteral(sValue);
/* 377 */         record.getDCElement().add(new JAXBElement(elementName, SimpleLiteral.class, value));
/*     */       }
/*     */     }
/* 380 */     return record;
/*     */   }
/*     */ 
/*     */   private static String getDublinCoreType(Asset a_asset)
/*     */   {
/* 395 */     String sType = "http://purl.org/dc/dcmitype/Dataset";
/*     */ 
/* 397 */     if (a_asset.getTypeId() == 2L)
/*     */     {
/* 399 */       sType = "http://purl.org/dc/dcmitype/StillImage";
/*     */     }
/* 401 */     else if (a_asset.getTypeId() == 3L)
/*     */     {
/* 403 */       sType = "http://purl.org/dc/dcmitype/MovingImage";
/*     */     }
/* 405 */     else if (a_asset.getTypeId() == 4L)
/*     */     {
/* 407 */       sType = "http://purl.org/dc/dcmitype/Sound";
/*     */     }
/* 409 */     else if (a_asset.getFormat() != null)
/*     */     {
/* 411 */       String sExt = a_asset.getFormat().getFileExtension();
/*     */ 
/* 413 */       if (("txt".equals(sExt)) || ("doc".equals(sExt)) || ("docx".equals(sExt)) || ("ppt".equals(sExt)) || ("pptx".equals(sExt)) || ("xls".equals(sExt)) || ("xlsx".equals(sExt)) || ("html".equals(sExt)) || ("htm".equals(sExt)) || ("rtf".equals(sExt)) || ("pdf".equals(sExt)))
/*     */       {
/* 425 */         sType = "http://purl.org/dc/dcmitype/Text";
/*     */       }
/*     */     }
/* 428 */     return sType;
/*     */   }
/*     */ 
/*     */   private static boolean isBoundingBoxProperty(String a_sPropertyName)
/*     */   {
/* 438 */     return (a_sPropertyName.endsWith("_BOTTOM")) || (a_sPropertyName.endsWith("_TOP")) || (a_sPropertyName.endsWith("_LEFT")) || (a_sPropertyName.endsWith("_RIGHT"));
/*     */   }
/*     */ 
/*     */   public static net.opengis.ows.ExceptionType translate(OpenGisServiceException a_exception)
/*     */   {
/* 451 */      net.opengis.ows.ExceptionType et = k_owsFactory.createExceptionType();
/*     */ 
/* 453 */     et.setExceptionCode(a_exception.getCode());
/* 454 */     et.setLocator(a_exception.getLocator());
/*     */ 
/* 456 */     StringBuilder sbText = new StringBuilder();
/*     */ 
/* 458 */     if (!a_exception.hasMeaningfulType())
/*     */     {
/* 460 */       sbText.append("No applicable code for error. printing exception details:\n");
/*     */     }
/*     */ 
/* 463 */     sbText.append(a_exception.getLocalizedMessage() + "\n");
/*     */ 
/* 465 */     StringWriter sw = new StringWriter();
/* 466 */     PrintWriter pw = new PrintWriter(sw);
/* 467 */     a_exception.printStackTrace(pw);
/* 468 */     sbText.append(sw.toString());
/* 469 */     sbText.append("\n");
/*     */ 
/* 471 */     et.getExceptionText().add(sbText.toString());
/*     */ 
/* 473 */     return et;
/*     */   }
/*     */ 
/*     */   public static ExceptionReport translateToExceptionReport(OpenGisServiceException a_exception)
/*     */   {
/* 483 */     net.opengis.ows.ExceptionType exception = translate(a_exception);
/* 484 */     ExceptionReport report = k_owsFactory.createExceptionReport();
/* 485 */     report.getException().add(exception);
/* 486 */     report.setLanguage(Locale.ENGLISH.getLanguage());
/* 487 */     report.setVersion("2.0.0");
/* 488 */     return report;
/*     */   }
/*     */ 
/*     */   public static abstract interface AssetLookup
/*     */   {
/*     */     public abstract Map<Long, Asset> getAssets(Vector<Long> paramVector)
/*     */       throws OpenGisServiceException;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.opengis.util.OpenGisDomainTranslator
 * JD-Core Version:    0.6.0
 */