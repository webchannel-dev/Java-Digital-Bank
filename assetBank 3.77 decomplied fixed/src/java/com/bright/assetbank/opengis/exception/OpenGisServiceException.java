/*     */ package com.bright.assetbank.opengis.exception;
/*     */ 
/*     */ import com.bright.assetbank.opengis.util.OpenGisDomainTranslator;
/*     */ import com.bright.assetbank.opengis.util.OpenGisUtils;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ public class OpenGisServiceException extends Exception
/*     */ {
/*     */   private ExceptionType m_type;
/*     */   private String m_sLocator;
/*     */ 
/*     */   private OpenGisServiceException(ExceptionType a_type, String a_sMessage, String a_sLocator, Throwable a_cause)
/*     */   {
/*  67 */     super(a_sMessage, a_cause);
/*     */ 
/*  69 */     if (a_type == null)
/*     */     {
/*  71 */       throw new IllegalArgumentException("Argument a_type cannot be null");
/*     */     }
/*     */ 
/*  74 */     if (StringUtils.isEmpty(a_sMessage))
/*     */     {
/*  76 */       throw new IllegalArgumentException("Argument a_sMessage cannot be empty");
/*     */     }
/*     */ 
/*  79 */     this.m_type = a_type;
/*  80 */     this.m_sLocator = a_sLocator;
/*     */   }
/*     */ 
/*     */   public OpenGisServiceException(String a_sMessage)
/*     */   {
/*  89 */     this(a_sMessage, null);
/*     */   }
/*     */ 
/*     */   public OpenGisServiceException(String a_sMessage, Throwable a_cause)
/*     */   {
/*  98 */     this(ExceptionType.NONE_OF_THE_ABOVE, a_sMessage, null, a_cause);
/*     */   }
/*     */ 
/*     */   public OpenGisServiceException(ExceptionType a_type, String a_sMessage, String a_sLocator)
/*     */   {
/* 103 */     this(a_type, a_sMessage, a_sLocator, null);
/*     */   }
/*     */ 
/*     */   public OpenGisServiceException(ExceptionType a_type, String a_sMessage)
/*     */   {
/* 108 */     this(a_type, a_sMessage, null, null);
/*     */   }
/*     */ 
/*     */   public int getHttpStatusCode()
/*     */   {
/* 113 */     return this.m_type.m_iHttpStatusCode;
/*     */   }
/*     */ 
/*     */   public String getCode()
/*     */   {
/* 118 */     return this.m_type.m_sReasonCode;
/*     */   }
/*     */ 
/*     */   public String getLocator()
/*     */   {
/* 123 */     return this.m_sLocator;
/*     */   }
/*     */ 
/*     */   public boolean hasMeaningfulType()
/*     */   {
/* 128 */     return !ExceptionType.NONE_OF_THE_ABOVE.equals(this.m_type);
/*     */   }
/*     */ 
/*     */   public Element addExceptionReportElement(Node a_parent, boolean a_bUseDefaultNamespace)
/*     */     throws JAXBException, ParserConfigurationException
/*     */   {
/* 139 */     return getExceptionReportElement(OpenGisUtils.createContext(), a_parent, a_bUseDefaultNamespace);
/*     */   }
/*     */ 
/*     */   public Element getExceptionReportElement(JAXBContext a_context, Node a_parent, boolean a_bUseDefaultNamespace)
/*     */     throws JAXBException, ParserConfigurationException
/*     */   {
/* 151 */     if (a_parent == null)
/*     */     {
/* 153 */       DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
/* 154 */       DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
/* 155 */       Document doc = docBuilder.newDocument();
/* 156 */       a_parent = doc;
/*     */     }
/*     */ 
/* 159 */     Marshaller marshaller = OpenGisUtils.createMarshaller(a_context, a_bUseDefaultNamespace);
/*     */ 
/* 162 */     marshaller.marshal(OpenGisDomainTranslator.translateToExceptionReport(this), a_parent);
/*     */ 
/* 165 */     return (Element)a_parent.getFirstChild();
/*     */   }
/*     */ 
/*     */   public static enum ExceptionType
/*     */   {
/*  45 */     OPERATION_NOT_SUPPORTED("OperationNotSupported", 501), 
/*  46 */     MISSING_PARAMETER_VALUE("MissingParameterValue", 400), 
/*  47 */     INVALID_PARAMETER_VALUE("InvalidParameterValue", 400), 
/*  48 */     VERSION_NEGOTIATION_FAILED("VersionNegotiationFailed", 400), 
/*  49 */     INVALID_UPDATE_SEQUENCE("InvalidUpdateSequence", 400), 
/*  50 */     NONE_OF_THE_ABOVE("NoApplicableCode", 500);
/*     */ 
/*     */     private String m_sReasonCode;
/*     */     private int m_iHttpStatusCode;
/*     */ 
/*  57 */     private ExceptionType(String a_sReasonCode, int a_iHttpStatusCode) { this.m_sReasonCode = a_sReasonCode;
/*  58 */       this.m_iHttpStatusCode = a_iHttpStatusCode;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.opengis.exception.OpenGisServiceException
 * JD-Core Version:    0.6.0
 */