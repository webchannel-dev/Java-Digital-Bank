/*     */ package com.bright.assetbank.opengis.util;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
import com.bright.assetbank.opengis.bean.OpenGisCswSearchQuery;
/*     */ import com.bright.assetbank.opengis.bean.OpenGisCswSearchQuery.AttributeLookup;
/*     */ import com.bright.assetbank.opengis.constant.OpenGisApiSettings;
/*     */ import com.bright.assetbank.opengis.exception.OpenGisServiceException;
/*     */ import com.bright.assetbank.opengis.exception.OpenGisServiceException.ExceptionType;
/*     */ import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.purl.dc.elements._1.SimpleLiteral;
/*     */ 
/*     */ public class OpenGisUtils
/*     */ {
/*     */   private static final String k_sPropertyName_NamespacePrefixMapper = "com.sun.xml.bind.namespacePrefixMapper";
/*  28 */   private static OpenGisCswNamespaceMapper k_mapperDefaultNamespace = new OpenGisCswNamespaceMapper(true);
/*  29 */   private static OpenGisCswNamespaceMapper k_mapperNoDefaultNamespace = new OpenGisCswNamespaceMapper(false);
/*     */ 
/*     */   public static String getNamespaceUriForPrefix(String a_sPrefix)
/*     */   {
/*  93 */     if (StringUtils.isEmpty(a_sPrefix))
/*     */     {
/*  95 */       return "http://www.opengis.net/cat/csw/2.0.2";
/*     */     }
/*  97 */     return k_mapperNoDefaultNamespace.getUri(a_sPrefix);
/*     */   }
/*     */ 
/*     */   public static JAXBContext createContext()
/*     */     throws JAXBException
/*     */   {
/* 103 */     return JAXBContext.newInstance("net.opengis.cat.csw._2_0:org.purl.dc.elements._1:net.opengis.ows:net.opengis.ogc:net.opengis.gml");
/*     */   }
/*     */ 
/*     */   public static SimpleLiteral createSimpleLiteral(String a_sValue)
/*     */   {
/* 108 */     SimpleLiteral value = new SimpleLiteral();
/* 109 */     value.getContent().add(a_sValue);
/* 110 */     return value;
/*     */   }
/*     */ 
/*     */   public static Marshaller createMarshaller(JAXBContext a_jaxbContext, boolean a_bUseDefaultNamespace)
/*     */     throws JAXBException
/*     */   {
/* 122 */     Marshaller marshaller = a_jaxbContext.createMarshaller();
/* 123 */     marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", a_bUseDefaultNamespace ? k_mapperDefaultNamespace : k_mapperNoDefaultNamespace);
/*     */ 
/* 125 */     return marshaller;
/*     */   }
/*     */ 
/*     */   public static void validateTypeNames(List<QName> a_typeNames)
/*     */     throws OpenGisServiceException
/*     */   {
/* 135 */     if ((a_typeNames != null) && (!a_typeNames.isEmpty()))
/*     */     {
/* 137 */       for (QName qName : a_typeNames)
/*     */       {
/* 139 */         if (qName == null)
/*     */         {
/* 141 */           throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.INVALID_PARAMETER_VALUE, "Only {http://www.opengis.net/cat/csw/2.0.2}Record is supported.");
/*     */         }
/*     */ 
/* 144 */         if ((!"http://www.opengis.net/cat/csw/2.0.2".equalsIgnoreCase(qName.getNamespaceURI())) || (!"Record".equalsIgnoreCase(qName.getLocalPart())))
/*     */         {
/* 147 */           throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.INVALID_PARAMETER_VALUE, "Only {http://www.opengis.net/cat/csw/2.0.2}Record is supported as a TypeName. Passed value was: " + qName.toString());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void validateServiceParameter(String a_sService)
/*     */     throws OpenGisServiceException
/*     */   {
/* 163 */     if (StringUtils.isEmpty(a_sService))
/*     */     {
/* 165 */       throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.MISSING_PARAMETER_VALUE, "Parameter/attribute service is missing.");
/*     */     }
/* 167 */     if (!"CSW".equalsIgnoreCase(a_sService))
/*     */     {
/* 169 */       throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.INVALID_PARAMETER_VALUE, "Only service CSW is implemented by this sever.");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void validateVersionParameter(String a_sVersion)
/*     */     throws OpenGisServiceException
/*     */   {
/* 183 */     if (StringUtils.isEmpty(a_sVersion))
/*     */     {
/* 185 */       throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.MISSING_PARAMETER_VALUE, "Parameter/attribute version is missing.");
/*     */     }
/* 187 */     if (!"2.0.2".equalsIgnoreCase(a_sVersion))
/*     */     {
/* 189 */       throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.VERSION_NEGOTIATION_FAILED, "Only version 2.0.2 is implemented by this sever.");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void validateOutputFormat(String a_sFormat)
/*     */     throws OpenGisServiceException
/*     */   {
/* 202 */     if (StringUtils.isNotEmpty(a_sFormat))
/*     */     {
/* 204 */       if (!"application/xml".equalsIgnoreCase(a_sFormat))
/*     */       {
/* 206 */         throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.INVALID_PARAMETER_VALUE, "Only application/xml is supported as an outputFormat");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void validateOutputSchema(String a_sSchema)
/*     */     throws OpenGisServiceException
/*     */   {
/* 220 */     if (StringUtils.isNotEmpty(a_sSchema))
/*     */     {
/* 222 */       if ((!"http://schemas.opengis.net/csw/2.0.2".equalsIgnoreCase(a_sSchema)) && (!"http://www.opengis.net/cat/csw/2.0.2".equalsIgnoreCase(a_sSchema)))
/*     */       {
/* 225 */         throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.INVALID_PARAMETER_VALUE, "Only http://schemas.opengis.net/csw/2.0.2 or http://www.opengis.net/cat/csw/2.0.2 are supported as an outputSchema");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Attribute getMappedAttribute(String a_FieldName, OpenGisCswSearchQuery.AttributeLookup a_attLookup) throws OpenGisServiceException
/*     */   {
/* 247 */     String sAttribute = OpenGisApiSettings.getAttributeForPropertyName(a_FieldName);
/*     */     long lMappedAttributeId;
/*     */     try {
/* 251 */       lMappedAttributeId = Long.parseLong(sAttribute);
/*     */     }
/*     */     catch (NumberFormatException e)
/*     */     {
/* 255 */       GlobalApplication.getInstance().getLogger().warn("Could not parse '" + sAttribute + "' as an attribute id.", e);
/* 256 */       throw new OpenGisServiceException("Cannot filter on property '" + a_FieldName + "' as this has not been mapped to a native attribute.");
/*     */     }
/*     */ 
/* 260 */     Attribute attribute = a_attLookup.lookupAttribute(lMappedAttributeId);
/*     */ 
/* 262 */     if (attribute == null)
/*     */     {
/* 264 */       throw new OpenGisServiceException("Mapped attribute id " + lMappedAttributeId + " for Filter property '" + a_FieldName + "' cannot be found.");
/*     */     }
/*     */ 
/* 268 */     return attribute;
/*     */   }
/*     */ 
/*     */   private static class OpenGisCswNamespaceMapper extends NamespacePrefixMapper
/*     */   {
/*     */     private final Map<String, String> m_namespaces;
/*     */     private final Map<String, String> m_prefixes;
/*     */ 
/*     */     private OpenGisCswNamespaceMapper(boolean a_bUseDefaultNamespace)
/*     */     {
/*  41 */       this.m_namespaces = new HashMap(15);
/*  42 */       this.m_prefixes = new HashMap(15);
/*     */ 
/*  45 */       addMapping("http://www.opengis.net/cat/csw/2.0.2/", a_bUseDefaultNamespace ? "" : "csw");
/*  46 */       addMapping("http://purl.org/dc/elements/1.1/", "dc");
/*  47 */       addMapping("http://purl.org/dc/terms/", "dct");
/*  48 */       addMapping("http://www.opengis.net/ows/", "ows");
/*  49 */       addMapping("http://www.opengis.net/ogc/", "ogc");
/*  50 */       addMapping("http://www.opengis.net/gml/", "gml");
/*  51 */       addMapping("http://www.w3.org/1999/xlink/", "xlink");
/*     */     }
/*     */ 
/*     */     private void addMapping(String a_sUri, String a_sPrefix)
/*     */     {
/*  56 */       this.m_namespaces.put(a_sUri, a_sPrefix);
/*  57 */       this.m_prefixes.put(a_sPrefix, a_sUri);
/*     */ 
/*  60 */       if (a_sUri.endsWith("/"))
/*     */       {
/*  62 */         this.m_namespaces.put(a_sUri.substring(0, a_sUri.length() - 1), a_sPrefix);
/*     */       }
/*     */     }
/*     */ 
/*     */     public String getUri(String a_sPrefix)
/*     */     {
/*  68 */       return (String)this.m_prefixes.get(a_sPrefix);
/*     */     }
/*     */ 
/*     */     public String getPreferredPrefix(String a_sNamespaceUri, String a_sSuggestion, boolean a_bRequirePrefix)
/*     */     {
/*  75 */       if (a_sNamespaceUri.endsWith("/"))
/*     */       {
/*  77 */         a_sNamespaceUri = a_sNamespaceUri.substring(0, a_sNamespaceUri.length() - 1);
/*     */       }
/*     */ 
/*  80 */       String sPrefix = (String)this.m_namespaces.get(a_sNamespaceUri);
/*     */ 
/*  82 */       if (sPrefix == null)
/*     */       {
/*  84 */         sPrefix = a_sSuggestion;
/*     */       }
/*     */ 
/*  87 */       return sPrefix;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.opengis.util.OpenGisUtils
 * JD-Core Version:    0.6.0
 */