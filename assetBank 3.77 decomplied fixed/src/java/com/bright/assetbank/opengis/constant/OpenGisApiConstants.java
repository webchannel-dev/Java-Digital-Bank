/*    */ package com.bright.assetbank.opengis.constant;
/*    */ 
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ public class OpenGisApiConstants
/*    */ {
/*    */   public static final String k_sRelativeRecordSchemaFileLocation = "/WEB-INF/manager-config/schema/opengis-csw-record.xsd";
/*    */   public static final String k_sSchemaLanguage_XmlSchema = "XMLSCHEMA";
/*    */   public static final String k_sSchemaLocation_CswRoot = "http://schemas.opengis.net/csw/2.0.2";
/*    */   public static final String k_sSchemaLocation2_CswRoot = "http://www.opengis.net/cat/csw/2.0.2";
/*    */   public static final String k_sSchemaLocation_CswRecord = "http://schemas.opengis.net/csw/2.0.2/record.xsd";
/*    */   public static final String k_sTypeName_Record = "Record";
/*    */   public static final String k_sNamespaceUri_CSW = "http://www.opengis.net/cat/csw/2.0.2";
/*    */   public static final String k_sSpecificationImplementationVersion = "2.0.2";
/*    */   public static final String k_sExceptionReportSpecVersion = "2.0.0";
/*    */   public static final String k_sSupportedOutputFormat = "application/xml";
/*    */   public static final String k_sServiceImplementationCode_CSW = "CSW";
/*    */   public static final String k_sServiceName_DescribeRecord = "DescribeRecord";
/*    */   public static final String k_sServiceName_GetCapabilities = "GetCapabilities";
/*    */   public static final String k_sServiceName_GetRecords = "GetRecords";
/*    */   public static final String k_sServiceName_GetRecordById = "GetRecordById";
/*    */   public static final String k_sDublinCoreTypePrefix = "http://purl.org/dc/dcmitype/";
/*    */   public static final String k_sDublinCoreType_Text = "http://purl.org/dc/dcmitype/Text";
/*    */   public static final String k_sDublinCoreType_Sound = "http://purl.org/dc/dcmitype/Sound";
/*    */   public static final String k_sDublinCoreType_MovingImage = "http://purl.org/dc/dcmitype/MovingImage";
/*    */   public static final String k_sDublinCoreType_StillImage = "http://purl.org/dc/dcmitype/StillImage";
/*    */   public static final String k_sDublinCoreType_Dataset = "http://purl.org/dc/dcmitype/Dataset";
/*    */   public static final String k_sOpenGisApiServletPath = "OpenGisCsw";
/*    */   public static final String k_sOpenGisApiSoapPath = "services/OpenGisCswService";
/*    */   public static final String k_sBBoxPropertySuffix_Bottom = "_BOTTOM";
/*    */   public static final String k_sBBoxPropertySuffix_Top = "_TOP";
/*    */   public static final String k_sBBoxPropertySuffix_Right = "_RIGHT";
/*    */   public static final String k_sBBoxPropertySuffix_Left = "_LEFT";
/* 55 */   public static final QName k_sQName_Ows_Get = new QName("http://www.opengis.net/ows", "Get");
/* 56 */   public static final QName k_sQName_Ows_Post = new QName("http://www.opengis.net/ows", "Post");
/*    */   public static final int k_iMaxUnboundedSearchResults = 1000;
/*    */   public static final String k_sSoapFaultReason = "A server exception was encountered.";
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.opengis.constant.OpenGisApiConstants
 * JD-Core Version:    0.6.0
 */