/*     */ package com.bright.assetbank.opengis.servlet;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.opengis.exception.OpenGisServiceException;
/*     */ import com.bright.assetbank.opengis.exception.OpenGisServiceException.ExceptionType;
/*     */ import com.bright.assetbank.opengis.service.OpenGisCswManager;
/*     */ import com.bright.framework.util.XMLUtil;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringReader;
/*     */ import java.io.StringWriter;
/*     */ import java.text.ParseException;
/*     */ import java.util.Arrays;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import net.opengis.cat.csw._2_0.DescribeRecordType;
/*     */ import net.opengis.cat.csw._2_0.GetRecordByIdType;
/*     */ import net.opengis.cat.csw._2_0.GetRecordsType;
/*     */ import net.opengis.ows.GetCapabilitiesType;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.axis.utils.XMLUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.jdom.Namespace;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class OpenGisCswRestServlet extends HttpServlet
/*     */ {
/*     */   private static final String k_sNamespacePrefix_CSW = "csw";
/*     */   private static final String k_sParamName_AcceptVersions = "AcceptVersions";
/*     */   private static final String k_sParamName_Service = "SERVICE";
/*     */   private static final String k_sParamName_Request = "REQUEST";
/*  75 */   private Log m_logger = GlobalApplication.getInstance().getLogger();
/*     */ 
/*  77 */   private OpenGisCswManager m_cswManager = null;
/*     */ 
/*     */   protected void doGet(HttpServletRequest a_req, HttpServletResponse a_resp)
/*     */     throws ServletException, IOException
/*     */   {
/*  86 */     this.m_logger.trace(getClass().getSimpleName() + " get called");
/*     */ 
/*  88 */     String sRequestName = a_req.getParameter("REQUEST");
/*  89 */     String sServiceName = a_req.getParameter("SERVICE");
/*  90 */     String sVersions = a_req.getParameter("AcceptVersions");
/*     */ 
/*  92 */     PrintWriter out = null;
/*     */     try
/*     */     {
/*  98 */       if (StringUtils.isEmpty(sRequestName))
/*     */       {
/* 100 */         throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.MISSING_PARAMETER_VALUE, "Parameter REQUEST is missing or empty.");
/*     */       }
/* 102 */       if (StringUtils.isEmpty(sServiceName))
/*     */       {
/* 104 */         throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.MISSING_PARAMETER_VALUE, "Parameter SERVICE is missing or empty.");
/*     */       }
/* 106 */       if (!"CSW".equalsIgnoreCase(sServiceName))
/*     */       {
/* 108 */         throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.MISSING_PARAMETER_VALUE, "Parameter SERVICE must be CSW as this is the only service implementation available.");
/*     */       }
/*     */ 
/* 113 */       if (StringUtils.isNotEmpty(sVersions))
/*     */       {
/* 115 */         String[] asVersions = sVersions.split(" *, *");
/* 116 */         Arrays.sort(asVersions);
/* 117 */         if (Arrays.binarySearch(asVersions, "2.0.2") < 0)
/*     */         {
/* 119 */           throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.VERSION_NEGOTIATION_FAILED, "Must accept version 2.0.2 of the OpenGIS CSW spec as this is the only version implemented.");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 125 */       String sResponse = dispatchServiceRequest(sRequestName, a_req);
/*     */ 
/* 127 */       a_resp.setContentType("text/xml");
/* 128 */       a_resp.setCharacterEncoding("UTF-8");
/* 129 */       a_resp.setStatus(200);
/*     */ 
/* 131 */       out = a_resp.getWriter();
/* 132 */       out.print(sResponse);
/*     */     }
/*     */     catch (OpenGisServiceException e)
/*     */     {
/* 136 */       this.m_logger.error(getClass().getSimpleName() + "Handled exception whilst processing GET (KVP) request: " + e.getMessage(), e);
/* 137 */       handleServiceException(a_resp, e);
/*     */     }
/*     */     catch (Throwable t)
/*     */     {
/* 141 */       this.m_logger.error(getClass().getSimpleName() + "Unhandled throwable whilst processing GET (KVP) request: " + t.getMessage(), t);
/* 142 */       handleServiceException(a_resp, t);
/*     */     }
/*     */     finally
/*     */     {
/* 146 */       IOUtils.closeQuietly(out);
/*     */     }
/*     */   }
/*     */ 
/*     */   private String dispatchServiceRequest(String a_sServiceName, HttpServletRequest a_req)
/*     */     throws OpenGisServiceException
/*     */   {
/* 160 */     String sResponse = null;
/* 161 */     Document doc = createDocument();
/* 162 */     Element response = null;
/*     */ 
/* 164 */     if ("GetCapabilities".equalsIgnoreCase(a_sServiceName))
/*     */     {
/* 167 */       Element element = doc.createElementNS("http://www.opengis.net/cat/csw/2.0.2", "GetCapabilities");
/* 168 */       element.setPrefix("csw");
/* 169 */       element.setAttributeNS("http://www.opengis.net/ows", "service", "CSW");
/* 170 */       doc.appendChild(element);
/*     */ 
/* 173 */       element = recreateElementForApparentlyNoReason(element);
/*     */ 
/* 175 */       response = getOpenGisCswManager().getCapabilities(element, true);
/*     */     }
/* 177 */     else if ("DescribeRecord".equalsIgnoreCase(a_sServiceName))
/*     */     {
/* 179 */       Element element = doc.createElementNS("http://www.opengis.net/cat/csw/2.0.2", "DescribeRecord");
/* 180 */       element.setPrefix("csw");
/* 181 */       element.setAttributeNS("http://www.opengis.net/ows", "service", "CSW");
/* 182 */       doc.appendChild(element);
/*     */ 
/* 184 */       Namespace[] namespaces = null;
/*     */ 
/* 187 */       if (StringUtils.isNotEmpty(a_req.getParameter("NAMESPACE")))
/*     */       {
/* 189 */         String[] asNamespaces = a_req.getParameter("NAMESPACE").split(" *, *");
/* 190 */         namespaces = new Namespace[asNamespaces.length];
/* 191 */         for (int i = 0; i < asNamespaces.length; i++)
/*     */         {
/* 193 */           if (!StringUtils.isNotEmpty(asNamespaces[i]))
/*     */             continue;
/*     */           try
/*     */           {
/* 197 */             namespaces[i] = XMLUtil.parseNamespace(asNamespaces[i]);
/*     */           }
/*     */           catch (ParseException e)
/*     */           {
/* 201 */             throw new OpenGisServiceException("Could not parse value of NAMESPACE parameter", e);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 208 */       if (StringUtils.isNotEmpty(a_req.getParameter("TypeName")))
/*     */       {
/* 210 */         String[] asTypeNames = a_req.getParameter("TypeName").split(" *, *");
/*     */ 
/* 212 */         for (int i = 0; i < asTypeNames.length; i++)
/*     */         {
/* 214 */           String sTypeName = asTypeNames[i];
/*     */ 
/* 216 */           Element typeNameElement = doc.createElementNS("http://www.opengis.net/cat/csw/2.0.2", "TypeName");
/* 217 */           typeNameElement.setPrefix("csw");
/*     */ 
/* 220 */           if ((namespaces != null) && (namespaces.length > i))
/*     */           {
/* 223 */             if (!"http://www.opengis.net/cat/csw/2.0.2".equals(namespaces[i].getURI()))
/*     */             {
/* 225 */               typeNameElement.setAttribute("xmlns:" + namespaces[i].getPrefix(), namespaces[i].getURI());
/* 226 */               sTypeName = namespaces[i].getPrefix() + ":" + sTypeName;
/*     */             }
/*     */             else
/*     */             {
/* 230 */               sTypeName = "csw:" + sTypeName;
/*     */             }
/*     */           }
/*     */ 
/* 234 */           typeNameElement.setTextContent(sTypeName);
/* 235 */           element.appendChild(typeNameElement);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 240 */       element = recreateElementForApparentlyNoReason(element);
/*     */ 
/* 242 */       response = getOpenGisCswManager().describeRecord(element, true);
/*     */     }
/*     */     else
/*     */     {
/* 247 */       throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.OPERATION_NOT_SUPPORTED, "Service: " + a_sServiceName + " is not supported via the GET method by this implementation.");
/*     */     }
/*     */ 
/* 250 */     if (response != null)
/*     */     {
/* 252 */       sResponse = XMLUtils.DocumentToString(response.getOwnerDocument());
/*     */ 
/* 255 */       if (this.m_logger.isTraceEnabled())
/*     */       {
/* 257 */         sResponse = XMLUtil.prettyFormatXmlString(sResponse, 3);
/*     */       }
/*     */     }
/*     */ 
/* 261 */     return sResponse;
/*     */   }
/*     */ 
/*     */   private Element recreateElementForApparentlyNoReason(Element element)
/*     */     throws OpenGisServiceException
/*     */   {
/*     */     try
/*     */     {
/* 279 */       DocumentBuilder builder = createDocumentBuilder();
/* 280 */       Document doc = builder.parse(new InputSource(new StringReader(XMLUtils.ElementToString(element))));
/* 281 */       return (Element)doc.getFirstChild();
/*     */     }
/*     */     catch (Throwable t) {
/*     */     
/* 285 */     throw new OpenGisServiceException("Exception whilst preparing GetCapabilities request.", t);
/*     */  } }
/*     */ 
/*     */   private Document createDocument()
/*     */     throws OpenGisServiceException
/*     */   {
/* 291 */     DocumentBuilder docBuilder = createDocumentBuilder();
/* 292 */     Document doc = docBuilder.newDocument();
/* 293 */     return doc;
/*     */   }
/*     */ 
/*     */   private DocumentBuilder createDocumentBuilder() throws OpenGisServiceException
/*     */   {
/*     */     try
/*     */     {
/* 300 */       DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
/* 301 */       dbfac.setNamespaceAware(true);
/*     */ 
/* 303 */       DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
/* 304 */       return docBuilder;
/*     */     }
/*     */     catch (ParserConfigurationException e) {
/*     */     
/* 308 */     throw new OpenGisServiceException("Exception whilst creating service request.", e);
/*     */   }}
/*     */ 
/*     */   protected void doPost(HttpServletRequest a_req, HttpServletResponse a_resp)
/*     */     throws ServletException, IOException
/*     */   {
/* 319 */     InputStream in = new BufferedInputStream(a_req.getInputStream());
/* 320 */     OutputStream out = null;
/*     */     try
/*     */     {
/* 326 */       String sPostData = IOUtils.toString(in);
/*     */ 
/* 328 */       this.m_logger.trace(getClass().getSimpleName() + " received post data:\n" + sPostData);
/*     */ 
/* 330 */       if (StringUtils.isEmpty(sPostData))
/*     */       {
/* 332 */         throw new OpenGisServiceException("The request was empty!");
/*     */       }
/*     */ 
/*     */       Node node;
/*     */       try
/*     */       {
/* 340 */         DocumentBuilder docBuilder = createDocumentBuilder();
/* 341 */         InputSource is = new InputSource(new StringReader(sPostData));
/* 342 */         Document doc = docBuilder.parse(is);
/* 343 */         node = doc.getFirstChild();
/*     */       }
/*     */       catch (SAXException e)
/*     */       {
/* 347 */         throw new OpenGisServiceException("Error while parsing POST data as xml.", e);
/*     */       }
/*     */ 
/* 350 */       if (!(node instanceof Element))
/*     */       {
/* 352 */         throw new OpenGisServiceException("Error while parsing POST data as xml: top level node is not an element!");
/*     */       }
/*     */ 
/* 355 */       Element response = dispatchServiceRequest((Element)node);
/*     */ 
/* 357 */       a_resp.setContentType("text/xml");
/* 358 */       a_resp.setCharacterEncoding("UTF-8");
/* 359 */       a_resp.setStatus(200);
/*     */ 
/* 361 */       out = new BufferedOutputStream(a_resp.getOutputStream());
/* 362 */       Document doc = response.getOwnerDocument();
/*     */ 
/* 365 */       if (this.m_logger.isTraceEnabled())
/*     */       {
/* 367 */         String sResponse = XMLUtils.DocumentToString(doc);
/* 368 */         sResponse = XMLUtil.prettyFormatXmlString(sResponse, 3);
/* 369 */         OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
/* 370 */         osw.write(sResponse);
/* 371 */         osw.close();
/*     */       }
/*     */       else
/*     */       {
/* 375 */         XMLUtils.DocumentToStream(doc, out);
/*     */       }
/*     */     }
/*     */     catch (OpenGisServiceException e)
/*     */     {
/* 380 */       this.m_logger.error(getClass().getSimpleName() + "Handled exception whilst processing POST (XML) request: " + e.getMessage(), e);
/* 381 */       handleServiceException(a_resp, e);
/*     */     }
/*     */     catch (Throwable t)
/*     */     {
/* 385 */       this.m_logger.error(getClass().getSimpleName() + "Unhandled throwable whilst processing POST (XML) request: " + t.getMessage(), t);
/* 386 */       handleServiceException(a_resp, t);
/*     */     }
/*     */     finally
/*     */     {
/* 390 */       IOUtils.closeQuietly(in);
/* 391 */       IOUtils.closeQuietly(out);
/*     */     }
/*     */   }
/*     */ 
/*     */   private Element dispatchServiceRequest(Element a_request)
/*     */     throws OpenGisServiceException
/*     */   {
/* 404 */     Element response = null;
/* 405 */     net.opengis.cat.csw._2_0.ObjectFactory cswFactory = new net.opengis.cat.csw._2_0.ObjectFactory();
/* 406 */     net.opengis.ows.ObjectFactory ogcFactory = new net.opengis.ows.ObjectFactory();
/* 407 */     String sServiceName = a_request.getLocalName();
/*     */ 
/* 410 */     if (ogcFactory.createGetCapabilities(new GetCapabilitiesType()).getName().getLocalPart().equalsIgnoreCase(sServiceName))
/*     */     {
/* 412 */       response = getOpenGisCswManager().getCapabilities(a_request, true);
/*     */     }
/* 415 */     else if (cswFactory.createDescribeRecord(new DescribeRecordType()).getName().getLocalPart().equalsIgnoreCase(sServiceName))
/*     */     {
/* 417 */       response = getOpenGisCswManager().describeRecord(a_request, true);
/*     */     }
/* 420 */     else if (cswFactory.createGetRecords(new GetRecordsType()).getName().getLocalPart().equalsIgnoreCase(sServiceName))
/*     */     {
/* 422 */       response = getOpenGisCswManager().getRecords(a_request, true);
/*     */     }
/* 425 */     else if (cswFactory.createGetRecordById(new GetRecordByIdType()).getName().getLocalPart().equalsIgnoreCase(sServiceName))
/*     */     {
/* 427 */       response = getOpenGisCswManager().getRecordById(a_request, true);
/*     */     }
/*     */     else
/*     */     {
/* 431 */       throw new OpenGisServiceException(OpenGisServiceException.ExceptionType.OPERATION_NOT_SUPPORTED, sServiceName + " was not recognised as a valid service request element.");
/*     */     }
/*     */ 
/* 434 */     return response;
/*     */   }
/*     */ 
/*     */   private void handleServiceException(HttpServletResponse a_resp, OpenGisServiceException a_exception)
/*     */   {
/* 444 */     String sResponse = null;
/* 445 */     String sContentType = "plain/text";
/* 446 */     int iResponseCode = 500;
/*     */     try
/*     */     {
/* 450 */       DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
/* 451 */       DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
/* 452 */       Document doc = docBuilder.newDocument();
/*     */ 
/* 455 */       a_exception.addExceptionReportElement(doc, true);
/*     */ 
/* 458 */       sResponse = XMLUtils.DocumentToString(doc);
/*     */ 
/* 461 */       if (this.m_logger.isTraceEnabled())
/*     */       {
/* 463 */         sResponse = XMLUtil.prettyFormatXmlString(sResponse, 3);
/*     */       }
/*     */ 
/* 467 */       iResponseCode = a_exception.getHttpStatusCode();
/*     */     }
/*     */     catch (Throwable t)
/*     */     {
/* 471 */       String sMsg = "An exception was encountered whilst creating the ExceptionReport! " + t.getClass().getSimpleName() + " : " + t.getLocalizedMessage();
/*     */ 
/* 473 */       this.m_logger.error(sMsg, t);
/*     */ 
/* 475 */       StringWriter sw = new StringWriter();
/* 476 */       PrintWriter pw = new PrintWriter(sw);
/* 477 */       t.printStackTrace(pw);
/* 478 */       sResponse = sMsg + "\n" + sw.toString();
/*     */     }
/*     */ 
/* 481 */     a_resp.setContentType(sContentType);
/* 482 */     a_resp.setStatus(iResponseCode);
/*     */ 
/* 484 */     this.m_logger.trace(getClass().getSimpleName() + " set response status code: " + iResponseCode);
/*     */     try
/*     */     {
/* 488 */       OutputStream out = new BufferedOutputStream(a_resp.getOutputStream());
/* 489 */       IOUtils.write(sResponse, out, "UTF-8");
/* 490 */       out.close();
/*     */ 
/* 492 */       this.m_logger.trace(getClass().getSimpleName() + " sent response data:\n" + sResponse);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   private void handleServiceException(HttpServletResponse a_resp, Throwable a_throwable)
/*     */   {
/* 509 */     OpenGisServiceException wrapper = new OpenGisServiceException(a_throwable.getClass().getName() + " : " + a_throwable.getMessage(), a_throwable);
/* 510 */     wrapper.setStackTrace(a_throwable.getStackTrace());
/* 511 */     handleServiceException(a_resp, wrapper);
/*     */   }
/*     */ 
/*     */   private OpenGisCswManager getOpenGisCswManager()
/*     */     throws OpenGisServiceException
/*     */   {
/* 517 */     if (this.m_cswManager == null)
/*     */     {
/*     */       try
/*     */       {
/* 521 */         this.m_cswManager = ((OpenGisCswManager)GlobalApplication.getInstance().getComponentManager().lookup("OpenGisCswManager"));
/*     */       }
/*     */       catch (ComponentException e)
/*     */       {
/* 525 */         this.m_logger.error("ComponentException caught whilst getting OpenGisCswManager : " + e.getMessage());
/* 526 */         throw new OpenGisServiceException("ComponentException caught whilst getting OpenGisCswManager", e);
/*     */       }
/*     */     }
/* 529 */     return this.m_cswManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.opengis.servlet.OpenGisCswRestServlet
 * JD-Core Version:    0.6.0
 */