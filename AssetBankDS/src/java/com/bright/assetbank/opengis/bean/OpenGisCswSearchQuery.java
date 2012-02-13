/*     */ package com.bright.assetbank.opengis.bean;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.opengis.constant.OpenGisApiSettings;
/*     */ import com.bright.assetbank.opengis.exception.OpenGisServiceException;
/*     */ import com.bright.assetbank.opengis.exception.OpenGisServiceException.ExceptionType;
/*     */ import com.bright.assetbank.opengis.util.OpenGisUtils;
/*     */ import com.bright.assetbank.search.bean.BaseSearchQuery;
/*     */ import com.bright.assetbank.search.constant.AssetBankSearchConstants;
/*     */ import com.bright.assetbank.search.constant.AssetIndexConstants;
/*     */ import com.bright.assetbank.search.lucene.CategoryApprovalFilter;
/*     */ import com.bright.framework.search.constant.SearchConstants;
/*     */ import com.bright.framework.search.service.IndexManager;
/*     */ import java.text.ParseException;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.namespace.QName;
/*     */ import net.opengis.gml.DirectPositionType;
/*     */ import net.opengis.gml.EnvelopeType;
/*     */ import net.opengis.ogc.BBOXType;
/*     */ import net.opengis.ogc.BinaryComparisonOpType;
/*     */ import net.opengis.ogc.BinaryLogicOpType;
/*     */ import net.opengis.ogc.BinarySpatialOpType;
/*     */ import net.opengis.ogc.FilterType;
/*     */ import net.opengis.ogc.LiteralType;
/*     */ import net.opengis.ogc.LowerBoundaryType;
/*     */ import net.opengis.ogc.PropertyIsBetweenType;
/*     */ import net.opengis.ogc.PropertyIsLikeType;
/*     */ import net.opengis.ogc.PropertyIsNullType;
/*     */ import net.opengis.ogc.PropertyNameType;
/*     */ import net.opengis.ogc.UnaryLogicOpType;
/*     */ import net.opengis.ogc.UpperBoundaryType;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.time.DateFormatUtils;
/*     */ import org.apache.commons.lang.time.DateUtils;
/*     */ import org.apache.commons.lang.time.FastDateFormat;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class OpenGisCswSearchQuery extends BaseSearchQuery
/*     */   implements AssetBankSearchConstants, AssetIndexConstants, SearchConstants
/*     */ {
/*     */   private static final String k_sLuceneOp_OR = " OR ";
/*     */   private static final String k_sLuceneOp_AND = " AND ";
/*     */   private String m_sQuery;
/*     */ 
/*     */   public void setOpenGisFilter(FilterType a_filter, AttributeLookup a_attLookup)
/*     */     throws OpenGisServiceException
/*     */   {
/*  73 */     StringBuilder sbQuery = new StringBuilder();
/*     */ 
/*  76 */     if (a_filter.getLogicOps() != null)
/*     */     {
/*  78 */       if (UnaryLogicOpType.class.equals(a_filter.getLogicOps().getDeclaredType()))
/*     */       {
/*  80 */         throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.INVALID_PARAMETER_VALUE, "Unary operators (e.g. NOT) are not implemented by the Filter.");
/*     */       }
/*     */ 
/*  84 */       if (BinaryLogicOpType.class.equals(a_filter.getLogicOps().getDeclaredType()))
/*     */       {
/*  86 */         String sConjunctionOp = getLuceneConjunctionOperator(a_filter.getLogicOps());
/*  87 */         String sQuery = buildBinaryOps((BinaryLogicOpType)a_filter.getLogicOps().getValue(), sConjunctionOp, a_attLookup);
/*  88 */         sbQuery.append(sQuery);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  93 */     if (a_filter.getComparisonOps() != null)
/*     */     {
/*  95 */       if (sbQuery.length() > 0)
/*     */       {
/*  97 */         sbQuery.append(" AND ");
/*     */       }
/*  99 */       addComparisonOrSpatialOperation(sbQuery, a_attLookup, a_filter.getComparisonOps());
/*     */     }
/*     */ 
/* 103 */     if (a_filter.getSpatialOps() != null)
/*     */     {
/* 105 */       if (sbQuery.length() > 0)
/*     */       {
/* 107 */         sbQuery.append(" AND ");
/*     */       }
/* 109 */       addComparisonOrSpatialOperation(sbQuery, a_attLookup, a_filter.getSpatialOps());
/*     */     }
/*     */ 
/* 114 */     if (!OpenGisApiSettings.getIncludeUnsubmittedAssets())
/*     */     {
/* 116 */       addTerm(sbQuery, "f_unsubmitted:0");
/*     */     }
/*     */ 
/* 119 */     if (!OpenGisApiSettings.getIncludeIncompleteAssets())
/*     */     {
/* 121 */       addTerm(sbQuery, "f_complete:1");
/*     */     }
/*     */ 
/* 125 */     if (sbQuery.length() == 0)
/*     */     {
/* 127 */       sbQuery.append(IndexManager.allDocsTerm());
/*     */     }
/*     */ 
/* 130 */     this.m_sQuery = sbQuery.toString();
/*     */   }
/*     */ 
/*     */   private String buildBinaryOps(BinaryLogicOpType a_binaryLogicOp, String a_sConjunctionOp, AttributeLookup a_attLookup)
/*     */     throws OpenGisServiceException
/*     */   {
/* 144 */     StringBuilder sbResult = new StringBuilder();
/*     */ 
/* 147 */     for (JAXBElement jaxbElement : a_binaryLogicOp.getComparisonOpsOrSpatialOpsOrLogicOps())
/*     */     {
/* 150 */       if (sbResult.length() > 0)
/*     */       {
/* 152 */         sbResult.append(a_sConjunctionOp);
/*     */       }
/*     */ 
/* 156 */       if (UnaryLogicOpType.class.equals(jaxbElement.getDeclaredType()))
/*     */       {
/* 158 */         throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.INVALID_PARAMETER_VALUE, "Unary operators (e.g. NOT) are not supported by this Filter implementation.");
/*     */       }
/*     */ 
/* 163 */       if (BinaryLogicOpType.class.isAssignableFrom(jaxbElement.getDeclaredType()))
/*     */       {
/* 165 */         String sOperator = getLuceneConjunctionOperator(jaxbElement);
/* 166 */         String sQuery = buildBinaryOps((BinaryLogicOpType)jaxbElement.getValue(), sOperator, a_attLookup);
/*     */ 
/* 168 */         sbResult.append("(");
/* 169 */         sbResult.append(sQuery);
/* 170 */         sbResult.append(")");
/*     */       }
/*     */       else
/*     */       {
/* 175 */         addComparisonOrSpatialOperation(sbResult, a_attLookup, jaxbElement);
/*     */       }
/*     */     }
/*     */ 
/* 179 */     return sbResult.toString();
/*     */   }
/*     */ 
/*     */   private void addComparisonOrSpatialOperation(StringBuilder a_sbResult, AttributeLookup a_attLookup, JAXBElement<?> a_operation)
/*     */     throws OpenGisServiceException
/*     */   {
/* 194 */     if (BinaryComparisonOpType.class.isAssignableFrom(a_operation.getDeclaredType()))
/*     */     {
/* 196 */       BinaryComparisonOpType compOp = (BinaryComparisonOpType)a_operation.getValue();
/* 197 */       List expression = compOp.getExpression();
/*     */ 
/* 200 */       if (expression.size() != 2)
/*     */       {
/* 202 */         throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.INVALID_PARAMETER_VALUE, "A binary comparison in the Filter did not have 2 operands.");
/*     */       }
/*     */ 
/* 206 */       String sName = null;
/* 207 */       String sValue = null;
/*     */ 
/* 210 */       if (PropertyNameType.class.isAssignableFrom(((JAXBElement)expression.get(0)).getDeclaredType()))
/*     */       {
/* 212 */         sName = getPropertyName((PropertyNameType)((JAXBElement)expression.get(0)).getValue());
/*     */       }
/*     */ 
/* 216 */       if (LiteralType.class.isAssignableFrom(((JAXBElement)expression.get(1)).getDeclaredType()))
/*     */       {
/* 218 */         sValue = getLiteralValue((LiteralType)((JAXBElement)expression.get(1)).getValue());
/*     */       }
/*     */ 
/* 222 */       if (net.opengis.ogc.ObjectFactory._PropertyIsEqualTo_QNAME.equals(a_operation.getName()))
/*     */       {
/* 226 */         addTermQuery(a_sbResult, sName, "\"" + sValue + "\"", a_attLookup);
/*     */       }
/* 228 */       else if (net.opengis.ogc.ObjectFactory._PropertyIsGreaterThan_QNAME.equals(a_operation.getName()))
/*     */       {
/* 230 */         addRangeQuery(a_sbResult, sName, sValue, null, false, a_attLookup);
/*     */       }
/* 232 */       else if (net.opengis.ogc.ObjectFactory._PropertyIsGreaterThanOrEqualTo_QNAME.equals(a_operation.getName()))
/*     */       {
/* 234 */         addRangeQuery(a_sbResult, sName, sValue, null, true, a_attLookup);
/*     */       }
/* 236 */       else if (net.opengis.ogc.ObjectFactory._PropertyIsLessThan_QNAME.equals(a_operation.getName()))
/*     */       {
/* 238 */         addRangeQuery(a_sbResult, sName, null, sValue, false, a_attLookup);
/*     */       }
/* 240 */       else if (net.opengis.ogc.ObjectFactory._PropertyIsLessThanOrEqualTo_QNAME.equals(a_operation.getName()))
/*     */       {
/* 242 */         addRangeQuery(a_sbResult, sName, null, sValue, true, a_attLookup);
/*     */       }
/*     */ 
/*     */     }
/* 246 */     else if (PropertyIsBetweenType.class.isAssignableFrom(a_operation.getDeclaredType()))
/*     */     {
/* 248 */       PropertyIsBetweenType betweenOp = (PropertyIsBetweenType)a_operation.getValue();
/*     */ 
/* 250 */       if (PropertyNameType.class.isAssignableFrom(betweenOp.getExpression().getDeclaredType()))
/*     */       {
/* 252 */         String sName = getPropertyName((PropertyNameType)betweenOp.getExpression().getValue());
/*     */ 
/* 254 */         if ((LiteralType.class.isAssignableFrom(betweenOp.getLowerBoundary().getExpression().getDeclaredType())) && (LiteralType.class.isAssignableFrom(betweenOp.getUpperBoundary().getExpression().getDeclaredType())))
/*     */         {
/* 257 */           String sLower = getLiteralValue((LiteralType)betweenOp.getLowerBoundary().getExpression().getValue());
/* 258 */           String sUpper = getLiteralValue((LiteralType)betweenOp.getUpperBoundary().getExpression().getValue());
/*     */ 
/* 260 */           addRangeQuery(a_sbResult, sName, sLower, sUpper, true, a_attLookup);
/*     */         }
/*     */         else
/*     */         {
/* 264 */           throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.INVALID_PARAMETER_VALUE, "The boundaries of a PropertyIsBetween element were not literal values.");
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 270 */         throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.INVALID_PARAMETER_VALUE, "A PropertyIsBetween element didn't have a property name as its expression.");
/*     */       }
/*     */ 
/*     */     }
/* 277 */     else if (PropertyIsLikeType.class.isAssignableFrom(a_operation.getDeclaredType()))
/*     */     {
/* 279 */       PropertyIsLikeType likeOp = (PropertyIsLikeType)a_operation.getValue();
/*     */ 
/* 281 */       String sName = getPropertyName(likeOp.getPropertyName());
/* 282 */       String sWildcard = likeOp.getWildCard();
/* 283 */       String sSingleChar = likeOp.getSingleChar();
/* 284 */       String sEscape = likeOp.getEscapeChar();
/* 285 */       String sValue = getLiteralValue(likeOp.getLiteral());
/*     */ 
/* 288 */       sValue = replaceWildcard(sWildcard, sValue, sEscape, "*");
/* 289 */       sValue = replaceWildcard(sSingleChar, sValue, sEscape, "?");
/*     */ 
/* 291 */       addTermQuery(a_sbResult, sName, sValue, a_attLookup);
/*     */     }
/* 294 */     else if (PropertyIsNullType.class.isAssignableFrom(a_operation.getDeclaredType()))
/*     */     {
/* 296 */       PropertyIsNullType nullOp = (PropertyIsNullType)a_operation.getValue();
/* 297 */       String sName = getPropertyName(nullOp.getPropertyName());
/* 298 */       addTermQuery(a_sbResult, sName, "isempty", a_attLookup);
/*     */     }
/* 301 */     else if (BinarySpatialOpType.class.isAssignableFrom(a_operation.getDeclaredType()))
/*     */     {
/* 303 */       BinarySpatialOpType binSpOp = (BinarySpatialOpType)a_operation.getValue();
/* 304 */       String sName = getPropertyName(binSpOp.getPropertyName());
/* 305 */       EnvelopeType envelope = binSpOp.getEnvelope();
/*     */ 
/* 307 */       if (a_operation.getName().equals(net.opengis.ogc.ObjectFactory._Contains_QNAME))
/*     */       {
/* 309 */         addContainsQuery(a_sbResult, sName, envelope, a_attLookup);
/*     */       }
/* 311 */       else if (a_operation.getName().equals(net.opengis.ogc.ObjectFactory._Intersects_QNAME))
/*     */       {
/* 313 */         addIntersectsQuery(a_sbResult, sName, envelope, a_attLookup, true);
/*     */       }
/* 315 */       else if (a_operation.getName().equals(net.opengis.ogc.ObjectFactory._Overlaps_QNAME))
/*     */       {
/* 317 */         addIntersectsQuery(a_sbResult, sName, envelope, a_attLookup, false);
/*     */       }
/*     */       else
/*     */       {
/* 321 */         throw new OpenGisServiceException("Binary spatial operator " + a_operation.getName().getLocalPart() + " is not supported by this implementation.");
/*     */       }
/*     */ 
/*     */     }
/* 326 */     else if (BBOXType.class.isAssignableFrom(a_operation.getDeclaredType()))
/*     */     {
/* 328 */       BBOXType box = (BBOXType)a_operation.getValue();
/* 329 */       String sName = getPropertyName(box.getPropertyName());
/* 330 */       EnvelopeType envelope = box.getEnvelope();
/*     */ 
/* 332 */       addIntersectsQuery(a_sbResult, sName, envelope, a_attLookup, true);
/*     */     }
/*     */     else
/*     */     {
/* 336 */       throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.INVALID_PARAMETER_VALUE, "Encountered unsupported Filter operator: " + a_operation.getName().getLocalPart());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addIntersectsQuery(StringBuilder a_sbResult, String a_sPropertyName, EnvelopeType a_envelope, AttributeLookup a_attLookup, boolean a_bInclusive)
/*     */     throws OpenGisServiceException
/*     */   {
/* 369 */     List coordsUpper = a_envelope.getUpperCorner().getValue();
/* 370 */     List coordsLower = a_envelope.getLowerCorner().getValue();
/*     */ 
/* 373 */     String sBoxTop = String.valueOf(coordsUpper.get(1));
/* 374 */     String sBoxBottom = String.valueOf(coordsLower.get(1));
/* 375 */     String sBoxLeft = String.valueOf(coordsLower.get(0));
/* 376 */     String sBoxRight = String.valueOf(coordsUpper.get(0));
/*     */ 
/* 380 */     a_sbResult.append("(");
/*     */ 
/* 383 */     addRangeQuery(a_sbResult, a_sPropertyName + "_LEFT", null, sBoxRight, a_bInclusive, a_attLookup);
/* 384 */     a_sbResult.append(" AND ");
/* 385 */     addRangeQuery(a_sbResult, a_sPropertyName + "_RIGHT", sBoxLeft, null, a_bInclusive, a_attLookup);
/* 386 */     a_sbResult.append(" AND ");
/* 387 */     addRangeQuery(a_sbResult, a_sPropertyName + "_TOP", sBoxBottom, null, a_bInclusive, a_attLookup);
/* 388 */     a_sbResult.append(" AND ");
/* 389 */     addRangeQuery(a_sbResult, a_sPropertyName + "_BOTTOM", null, sBoxTop, a_bInclusive, a_attLookup);
/*     */ 
/* 391 */     a_sbResult.append(")");
/*     */   }
/*     */ 
/*     */   private void addContainsQuery(StringBuilder a_sbResult, String a_sPropertyName, EnvelopeType a_envelope, AttributeLookup a_attLookup)
/*     */     throws OpenGisServiceException
/*     */   {
/* 417 */     List coordsUpper = a_envelope.getUpperCorner().getValue();
/* 418 */     List coordsLower = a_envelope.getLowerCorner().getValue();
/*     */ 
/* 421 */     String sBoxTop = String.valueOf(coordsUpper.get(1));
/* 422 */     String sBoxBottom = String.valueOf(coordsLower.get(1));
/* 423 */     String sBoxLeft = String.valueOf(coordsLower.get(0));
/* 424 */     String sBoxRight = String.valueOf(coordsUpper.get(0));
/*     */ 
/* 428 */     a_sbResult.append("(");
/*     */ 
/* 430 */     addRangeQuery(a_sbResult, a_sPropertyName + "_LEFT", sBoxLeft, sBoxRight, true, a_attLookup);
/*     */ 
/* 434 */     if (getAttributeIdForPropertyName(a_sPropertyName + "_RIGHT") != getAttributeIdForPropertyName(a_sPropertyName + "_LEFT"))
/*     */     {
/* 437 */       a_sbResult.append(" AND ");
/* 438 */       addRangeQuery(a_sbResult, a_sPropertyName + "_RIGHT", sBoxLeft, sBoxRight, true, a_attLookup);
/*     */     }
/*     */ 
/* 441 */     a_sbResult.append(" AND ");
/* 442 */     addRangeQuery(a_sbResult, a_sPropertyName + "_TOP", sBoxBottom, sBoxTop, true, a_attLookup);
/*     */ 
/* 446 */     if (getAttributeIdForPropertyName(a_sPropertyName + "_TOP") != getAttributeIdForPropertyName(a_sPropertyName + "_BOTTOM"))
/*     */     {
/* 449 */       a_sbResult.append(" AND ");
/* 450 */       addRangeQuery(a_sbResult, a_sPropertyName + "_BOTTOM", sBoxBottom, sBoxTop, true, a_attLookup);
/*     */     }
/*     */ 
/* 453 */     a_sbResult.append(")");
/*     */   }
/*     */ 
/*     */   private String replaceWildcard(String a_sWildcard, String a_sValue, String a_sEscapeChar, String a_sLuceneEquivalent)
/*     */   {
/* 467 */     if (StringUtils.isNotEmpty(a_sWildcard))
/*     */     {
/* 469 */       if (!a_sLuceneEquivalent.equals(a_sWildcard))
/*     */       {
/* 472 */         a_sValue = a_sValue.replace(a_sEscapeChar + a_sWildcard, "<>");
/*     */ 
/* 475 */         a_sValue = a_sValue.replace(a_sWildcard, a_sLuceneEquivalent);
/*     */ 
/* 478 */         a_sValue = a_sValue.replace("<>", a_sWildcard);
/*     */       }
/*     */ 
/* 482 */       while ((a_sValue.length() > 0) && (a_sValue.indexOf(a_sLuceneEquivalent) == 0))
/*     */       {
/* 484 */         a_sValue = a_sValue.substring(a_sLuceneEquivalent.length());
/*     */       }
/*     */     }
/* 487 */     return a_sValue;
/*     */   }
/*     */ 
/*     */   private void addTermQuery(StringBuilder a_sbQueryString, String a_sPropertyName, String a_PropertyValue, AttributeLookup a_attLookup)
/*     */     throws OpenGisServiceException
/*     */   {
/* 502 */     String sValue = a_PropertyValue;
/*     */ 
/* 504 */     if ((StringUtils.isEmpty(a_sPropertyName)) || (StringUtils.isEmpty(sValue)))
/*     */     {
/* 506 */       throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.INVALID_PARAMETER_VALUE, "A property name or value was missing in a Filter expression.");
/*     */     }
/*     */ 
/* 509 */     String sName = OpenGisApiSettings.getAttributeForPropertyName(a_sPropertyName);
/*     */ 
/* 511 */     if (StringUtils.isEmpty(sName))
/*     */     {
/* 513 */       throw new OpenGisServiceException("Cannot build Filter query for property name " + a_sPropertyName + " as this has not been mapped to a native attribute.");
/*     */     }
/*     */ 
/* 517 */     if ("keywords".equalsIgnoreCase(sName))
/*     */     {
/* 519 */       sName = "f_keywords";
/*     */     }
/* 522 */     else if ((sName == null) && (org.purl.dc.elements._1.ObjectFactory._Type_QNAME.getLocalPart().endsWith(a_sPropertyName)))
/*     */     {
/* 524 */       sName = "f_typeid";
/*     */ 
/* 526 */       if ("http://purl.org/dc/dcmitype/MovingImage".equalsIgnoreCase(sValue))
/*     */       {
/* 528 */         sValue = String.valueOf(3L);
/*     */       }
/* 530 */       else if ("http://purl.org/dc/dcmitype/Sound".equalsIgnoreCase(sValue))
/*     */       {
/* 532 */         sValue = String.valueOf(4L);
/*     */       }
/* 534 */       else if ("http://purl.org/dc/dcmitype/StillImage".equalsIgnoreCase(sValue))
/*     */       {
/* 536 */         sValue = String.valueOf(2L);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 542 */       Attribute attribute = OpenGisUtils.getMappedAttribute(a_sPropertyName, a_attLookup);
/*     */ 
/* 545 */       sName = AttributeUtil.getIndexFieldName(attribute);
/*     */ 
/* 547 */       if ((attribute.getIsDatepicker()) || (attribute.getIsDateTime()))
/*     */       {
/* 549 */         String sDate = sValue.replace("\"", "");
/*     */ 
/* 551 */         sValue = getIsoDateIfPossible(sDate);
/*     */       }
/*     */     }
/*     */ 
/* 555 */     if ((StringUtils.isNotEmpty(sName)) && (StringUtils.isNotEmpty(sValue)))
/*     */     {
/* 557 */       a_sbQueryString.append(sName);
/* 558 */       a_sbQueryString.append(":(");
/* 559 */       a_sbQueryString.append(sValue);
/* 560 */       a_sbQueryString.append(")");
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String getIsoDateIfPossible(String a_sDate)
/*     */   {
/* 572 */     if (StringUtils.isEmpty(a_sDate))
/*     */     {
/* 574 */       return a_sDate;
/*     */     }
/*     */ 
/* 577 */     String sIsoDate = a_sDate;
/*     */ 
/* 579 */     if (a_sDate.length() > 11)
/*     */     {
/*     */       try
/*     */       {
/* 583 */         Date date = DateUtils.parseDate(a_sDate, new String[] { DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.getPattern() });
/* 584 */         sIsoDate = String.valueOf(date.getTime());
/*     */       }
/*     */       catch (ParseException e)
/*     */       {
/*     */         try
/*     */         {
/* 591 */           Date date = DateUtils.parseDate(a_sDate, new String[] { DateFormatUtils.ISO_DATETIME_FORMAT.getPattern() });
/* 592 */           sIsoDate = String.valueOf(date.getTime());
/*     */         }
/*     */         catch (ParseException e1)
/*     */         {
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*     */       try
/*     */       {
/* 604 */         Date date = DateUtils.parseDate(a_sDate, new String[] { DateFormatUtils.ISO_DATE_TIME_ZONE_FORMAT.getPattern() });
/* 605 */         sIsoDate = String.valueOf(date.getTime());
/*     */       }
/*     */       catch (ParseException e)
/*     */       {
/*     */         try
/*     */         {
/* 612 */           Date date = DateUtils.parseDate(a_sDate, new String[] { DateFormatUtils.ISO_DATE_FORMAT.getPattern() });
/* 613 */           sIsoDate = String.valueOf(date.getTime());
/*     */         }
/*     */         catch (ParseException e1)
/*     */         {
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 621 */     return sIsoDate;
/*     */   }
/*     */ 
/*     */   private void addRangeQuery(StringBuilder a_sbQueryString, String a_FieldName, String a_LowerValue, String a_UpperValue, boolean a_bInclusive, AttributeLookup a_attLookup)
/*     */     throws OpenGisServiceException
/*     */   {
/* 644 */     if ((StringUtils.isEmpty(a_FieldName)) || ((StringUtils.isEmpty(a_LowerValue)) && (StringUtils.isEmpty(a_UpperValue))))
/*     */     {
/* 646 */       throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.INVALID_PARAMETER_VALUE, "A property name or value was missing in a Filter range expression.");
/*     */     }
/*     */ 
/* 653 */     Attribute attribute = OpenGisUtils.getMappedAttribute(a_FieldName, a_attLookup);
/*     */ 
/* 656 */     if ((!attribute.getIsNumeric()) && (!attribute.getIsNumeric()) && (!attribute.getIsDateTime()) && (!attribute.getIsDateTime()))
/*     */     {
/* 661 */       throw new OpenGisServiceException("Cannot use a range Filter query on non-numeric native attribute '" + attribute.getLabel() + "'");
/*     */     }
/*     */ 
/* 665 */     if ((attribute.getIsDateTime()) || (attribute.getIsDatepicker()))
/*     */     {
/* 667 */       a_LowerValue = getIsoDateIfPossible(a_LowerValue);
/* 668 */       a_UpperValue = getIsoDateIfPossible(a_UpperValue);
/*     */     }
/*     */ 
/* 672 */     String sName = AttributeUtil.getIndexFieldName(attribute);
/*     */ 
/* 674 */     char cLeftBracket = a_bInclusive ? '[' : '{';
/* 675 */     char cRightBracket = a_bInclusive ? ']' : '}';
/*     */ 
/* 678 */     if (a_LowerValue == null)
/*     */     {
/* 680 */       a_LowerValue = String.valueOf(-9223372036854775808L);
/*     */     }
/* 682 */     if (a_UpperValue == null)
/*     */     {
/* 684 */       a_UpperValue = String.valueOf(9223372036854775807L);
/*     */     }
/*     */ 
/* 687 */     a_sbQueryString.append(sName);
/* 688 */     a_sbQueryString.append(":");
/* 689 */     a_sbQueryString.append(cLeftBracket);
/* 690 */     a_sbQueryString.append(a_LowerValue);
/* 691 */     a_sbQueryString.append(" TO ");
/* 692 */     a_sbQueryString.append(a_UpperValue);
/* 693 */     a_sbQueryString.append(cRightBracket);
/*     */   }
/*     */ 
/*     */   private long getAttributeIdForPropertyName(String a_sPropertyName)
/*     */     throws OpenGisServiceException
/*     */   {
/* 699 */     String sValue = OpenGisApiSettings.getAttributeForPropertyName(a_sPropertyName);
/*     */     try
/*     */     {
/* 702 */       return Long.parseLong(OpenGisApiSettings.getAttributeForPropertyName(a_sPropertyName));
/*     */     }
/*     */     catch (NumberFormatException e)
/*     */     {
/* 706 */       c_logger.warn(getClass().getSimpleName() + " : Could not parse '" + sValue + "' as an attribute id.", e);
/* 707 */     }throw new OpenGisServiceException("Cannot filter on property '" + a_sPropertyName + "' as this has not been correctly mapped to a native attribute.");
/*     */   }
/*     */ 
/*     */   private String getPropertyName(PropertyNameType a_nameType)
/*     */     throws OpenGisServiceException
/*     */   {
/* 721 */     if ((a_nameType.getContent() != null) || (a_nameType.getContent().size() > 0))
/*     */     {
/* 723 */       String sPropertyName = a_nameType.getContent().get(0).toString();
/*     */ 
/* 726 */       sPropertyName = sPropertyName.substring(sPropertyName.lastIndexOf('/') + 1);
/*     */ 
/* 728 */       return sPropertyName;
/*     */     }
/* 730 */     throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.INVALID_PARAMETER_VALUE, "A PropertyName value could not be correctly extracted from the encoded Filter.");
/*     */   }
/*     */ 
/*     */   private String getLiteralValue(LiteralType a_literalType)
/*     */     throws OpenGisServiceException
/*     */   {
/* 743 */     if ((a_literalType.getContent() != null) || (a_literalType.getContent().size() > 0))
/*     */     {
/* 745 */       return a_literalType.getContent().get(0).toString();
/*     */     }
/* 747 */     throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.INVALID_PARAMETER_VALUE, "A Literal value could not be correctly extracted from the encoded Filter.");
/*     */   }
/*     */ 
/*     */   private String getLuceneConjunctionOperator(JAXBElement<?> a_opsType)
/*     */     throws OpenGisServiceException
/*     */   {
/* 760 */     if (net.opengis.ogc.ObjectFactory._Or_QNAME.equals(a_opsType.getName()))
/*     */     {
/* 762 */       return " OR ";
/*     */     }
/* 764 */     if (net.opengis.ogc.ObjectFactory._And_QNAME.equals(a_opsType.getName()))
/*     */     {
/* 766 */       return " AND ";
/*     */     }
/* 768 */     throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.INVALID_PARAMETER_VALUE, "Only logic operators OR and AND are support by the Filter.");
/*     */   }
/*     */ 
/*     */   private void addTerm(StringBuilder a_sbQuery, String a_sTerm)
/*     */   {
/* 779 */     if (a_sbQuery.length() > 0)
/*     */     {
/* 781 */       a_sbQuery.insert(0, "(").append(")").append(" AND ");
/*     */     }
/* 783 */     a_sbQuery.append(a_sTerm);
/*     */   }
/*     */ 
/*     */   protected CategoryApprovalFilter getCategoryApprovalFilter()
/*     */     throws Bn2Exception
/*     */   {
/* 790 */     return null;
/*     */   }
/*     */ 
/*     */   public String getLuceneQuery()
/*     */   {
/* 795 */     return this.m_sQuery;
/*     */   }
/*     */ 
/*     */   public static abstract interface AttributeLookup
/*     */   {
/*     */     public abstract Attribute lookupAttribute(long paramLong)
/*     */       throws OpenGisServiceException;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.opengis.bean.OpenGisCswSearchQuery
 * JD-Core Version:    0.6.0
 */