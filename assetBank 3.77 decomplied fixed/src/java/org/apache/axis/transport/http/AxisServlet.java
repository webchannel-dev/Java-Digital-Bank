/*      */ package org.apache.axis.transport.http;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintWriter;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.ServletContext;
/*      */ import javax.servlet.ServletException;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpUtils;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.soap.MimeHeader;
/*      */ import javax.xml.soap.MimeHeaders;
/*      */ import javax.xml.soap.SOAPException;
/*      */ import org.apache.axis.AxisEngine;
/*      */ import org.apache.axis.AxisFault;
/*      */ import org.apache.axis.ConfigurationException;
/*      */ import org.apache.axis.Constants;
/*      */ import org.apache.axis.EngineConfiguration;
/*      */ import org.apache.axis.Handler;
/*      */ import org.apache.axis.Message;
/*      */ import org.apache.axis.MessageContext;
/*      */ import org.apache.axis.SOAPPart;
/*      */ import org.apache.axis.SimpleTargetedChain;
/*      */ import org.apache.axis.components.logger.LogFactory;
/*      */ import org.apache.axis.description.OperationDesc;
/*      */ import org.apache.axis.description.ServiceDesc;
/*      */ import org.apache.axis.handlers.soap.SOAPService;
/*      */ import org.apache.axis.management.ServiceAdmin;
/*      */ import org.apache.axis.security.servlet.ServletSecurityProvider;
/*      */ import org.apache.axis.server.AxisServer;
/*      */ import org.apache.axis.utils.JavaUtils;
/*      */ import org.apache.axis.utils.Messages;
/*      */ import org.apache.axis.utils.XMLUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.w3c.dom.Element;
/*      */ 
/*      */ public class AxisServlet extends AxisServletBase
/*      */ {
/*   80 */   protected static Log log = LogFactory.getLog(AxisServlet.class.getName());
/*      */ 
/*   86 */   private static Log tlog = LogFactory.getLog("org.apache.axis.TIME");
/*      */ 
/*   93 */   private static Log exceptionLog = LogFactory.getLog("org.apache.axis.EXCEPTIONS");
/*      */   public static final String INIT_PROPERTY_TRANSPORT_NAME = "transport.name";
/*      */   public static final String INIT_PROPERTY_USE_SECURITY = "use-servlet-security";
/*      */   public static final String INIT_PROPERTY_ENABLE_LIST = "axis.enableListQuery";
/*      */   public static final String INIT_PROPERTY_JWS_CLASS_DIR = "axis.jws.servletClassDir";
/*      */   public static final String INIT_PROPERTY_DISABLE_SERVICES_LIST = "axis.disableServiceList";
/*      */   public static final String INIT_PROPERTY_SERVICES_PATH = "axis.servicesPath";
/*      */   private String transportName;
/*      */   private Handler transport;
/*  120 */   private ServletSecurityProvider securityProvider = null;
/*      */   private String servicesPath;
/*  128 */   private static boolean isDebug = false;
/*      */ 
/*  135 */   private boolean enableList = false;
/*      */ 
/*  141 */   private boolean disableServicesList = false;
/*      */ 
/*  146 */   private String jwsClassDir = null;
/*      */ 
/*  147 */   protected String getJWSClassDir() { return this.jwsClassDir;
/*      */   }
/*      */ 
/*      */   public void init()
/*      */     throws ServletException
/*      */   {
/*  161 */     super.init();
/*  162 */     ServletContext context = getServletConfig().getServletContext();
/*      */ 
/*  164 */     isDebug = log.isDebugEnabled();
/*  165 */     if (isDebug) {
/*  166 */       log.debug("In servlet init");
/*      */     }
/*  168 */     this.transportName = getOption(context, "transport.name", "http");
/*      */ 
/*  172 */     if (JavaUtils.isTrueExplicitly(getOption(context, "use-servlet-security", null)))
/*      */     {
/*  174 */       this.securityProvider = new ServletSecurityProvider();
/*      */     }
/*      */ 
/*  177 */     this.enableList = JavaUtils.isTrueExplicitly(getOption(context, "axis.enableListQuery", null));
/*      */ 
/*  181 */     this.jwsClassDir = getOption(context, "axis.jws.servletClassDir", null);
/*      */ 
/*  184 */     this.disableServicesList = JavaUtils.isTrue(getOption(context, "axis.disableServiceList", "false"));
/*      */ 
/*  187 */     this.servicesPath = getOption(context, "axis.servicesPath", "/services/");
/*      */ 
/*  197 */     if (this.jwsClassDir != null) {
/*  198 */       if (getHomeDir() != null)
/*  199 */         this.jwsClassDir = (getHomeDir() + this.jwsClassDir);
/*      */     }
/*      */     else {
/*  202 */       this.jwsClassDir = getDefaultJWSClassDir();
/*      */     }
/*      */ 
/*  205 */     initQueryStringHandlers();
/*      */     try
/*      */     {
/*  209 */       ServiceAdmin.setEngine(getEngine(), context.getServerInfo());
/*      */     } catch (AxisFault af) {
/*  211 */       exceptionLog.info("Exception setting AxisEngine on ServiceAdmin " + af);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*      */     throws ServletException, IOException
/*      */   {
/*  227 */     if (isDebug) {
/*  228 */       log.debug("Enter: doGet()");
/*      */     }
/*      */ 
/*  231 */     PrintWriter writer = new FilterPrintWriter(response);
/*      */     try
/*      */     {
/*  234 */       AxisEngine engine = getEngine();
/*  235 */       ServletContext servletContext = getServletConfig().getServletContext();
/*      */ 
/*  238 */       String pathInfo = request.getPathInfo();
/*  239 */       String realpath = servletContext.getRealPath(request.getServletPath());
/*  240 */       if (realpath == null) {
/*  241 */         realpath = request.getServletPath();
/*      */       }
/*      */ 
/*  247 */       boolean isJWSPage = request.getRequestURI().endsWith(".jws");
/*  248 */       if (isJWSPage) {
/*  249 */         pathInfo = request.getServletPath();
/*      */       }
/*      */ 
/*  254 */       if (processQuery(request, response, writer) == true) {
/*  255 */         return ;
/*      */       }
/*      */ 
/*  258 */       boolean hasNoPath = (pathInfo == null) || (pathInfo.equals(""));
/*  259 */       if (!this.disableServicesList) {
/*  260 */         if (hasNoPath)
/*      */         {
/*  265 */           reportAvailableServices(response, writer, request);
/*  266 */         } else if (realpath != null)
/*      */         {
/*  270 */           MessageContext msgContext = createMessageContext(engine, request, response);
/*      */ 
/*  285 */           String url = HttpUtils.getRequestURL(request).toString();
/*      */ 
/*  287 */           msgContext.setProperty("transport.url", url);
/*      */           String serviceName;

/*  294 */           if (pathInfo.startsWith("/"))
/*  295 */             serviceName = pathInfo.substring(1);
/*      */           else {
/*  297 */             serviceName = pathInfo;
/*      */           }
/*      */ 
/*  300 */           SOAPService s = engine.getService(serviceName);
/*  301 */           if (s == null)
/*      */           {
/*  303 */             if (isJWSPage)
/*  304 */               reportCantGetJWSService(request, response, writer);
/*      */             else {
/*  306 */               reportCantGetAxisService(request, response, writer);
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  311 */             reportServiceInfo(response, writer, s, serviceName);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  319 */         response.setContentType("text/html; charset=utf-8");
/*  320 */         writer.println("<html><h1>Axis HTTP Servlet</h1>");
/*  321 */         writer.println(Messages.getMessage("reachedServlet00"));
/*      */ 
/*  323 */         writer.println("<p>" + Messages.getMessage("transportName00", new StringBuilder().append("<b>").append(this.transportName).append("</b>").toString()));
/*      */ 
/*  326 */         writer.println("</html>");
/*      */       }
/*      */     } catch (AxisFault fault) {
/*  329 */       reportTroubleInGet(fault, response, writer);
/*      */     } catch (Exception e) {
/*  331 */       reportTroubleInGet(e, response, writer);
/*      */     } finally {
/*  333 */       writer.close();
/*  334 */       if (isDebug)
/*  335 */         log.debug("Exit: doGet()");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void reportTroubleInGet(Throwable exception, HttpServletResponse response, PrintWriter writer)
/*      */   {
/*  352 */     response.setContentType("text/html; charset=utf-8");
/*  353 */     response.setStatus(500);
/*  354 */     writer.println("<h2>" + Messages.getMessage("error00") + "</h2>");
/*      */ 
/*  357 */     writer.println("<p>" + Messages.getMessage("somethingWrong00") + "</p>");
/*      */ 
/*  360 */     if ((exception instanceof AxisFault)) {
/*  361 */       AxisFault fault = (AxisFault)exception;
/*  362 */       processAxisFault(fault);
/*  363 */       writeFault(writer, fault);
/*      */     } else {
/*  365 */       logException(exception);
/*  366 */       writer.println("<pre>Exception - " + exception + "<br>");
/*      */ 
/*  368 */       if (isDevelopment()) {
/*  369 */         writer.println(JavaUtils.stackToString(exception));
/*      */       }
/*  371 */       writer.println("</pre>");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void processAxisFault(AxisFault fault)
/*      */   {
/*  383 */     Element runtimeException = fault.lookupFaultDetail(Constants.QNAME_FAULTDETAIL_RUNTIMEEXCEPTION);
/*      */ 
/*  385 */     if (runtimeException != null) {
/*  386 */       exceptionLog.info(Messages.getMessage("axisFault00"), fault);
/*      */ 
/*  388 */       fault.removeFaultDetail(Constants.QNAME_FAULTDETAIL_RUNTIMEEXCEPTION);
/*      */     }
/*  390 */     else if (exceptionLog.isDebugEnabled()) {
/*  391 */       exceptionLog.debug(Messages.getMessage("axisFault00"), fault);
/*      */     }
/*      */ 
/*  394 */     if (!isDevelopment())
/*      */     {
/*  396 */       fault.removeFaultDetail(Constants.QNAME_FAULTDETAIL_STACKTRACE);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void logException(Throwable e)
/*      */   {
/*  405 */     exceptionLog.info(Messages.getMessage("exception00"), e);
/*      */   }
/*      */ 
/*      */   private void writeFault(PrintWriter writer, AxisFault axisFault)
/*      */   {
/*  415 */     String localizedMessage = XMLUtils.xmlEncodeString(axisFault.getLocalizedMessage());
/*      */ 
/*  417 */     writer.println("<pre>Fault - " + localizedMessage + "<br>");
/*  418 */     writer.println(axisFault.dumpToString());
/*  419 */     writer.println("</pre>");
/*      */   }
/*      */ 
/*      */   protected void reportServiceInfo(HttpServletResponse response, PrintWriter writer, SOAPService service, String serviceName)
/*      */   {
/*  432 */     response.setContentType("text/html; charset=utf-8");
/*      */ 
/*  434 */     writer.println("<h1>" + service.getName() + "</h1>");
/*      */ 
/*  437 */     writer.println("<p>" + Messages.getMessage("axisService00") + "</p>");
/*      */ 
/*  441 */     writer.println("<i>" + Messages.getMessage("perhaps00") + "</i>");
/*      */   }
/*      */ 
/*      */   protected void reportNoWSDL(HttpServletResponse res, PrintWriter writer, String moreDetailCode, AxisFault axisFault) {
/*      */   }
/*      */ 
/*      */   protected void reportAvailableServices(HttpServletResponse response, PrintWriter writer, HttpServletRequest request) throws ConfigurationException, AxisFault {
/*  480 */     AxisEngine engine = getEngine();
/*      */ 
/*  482 */     response.setContentType("text/html; charset=utf-8");
/*  483 */     writer.println("<h2>And now... Some Services</h2>");
/*      */     Iterator i;
/*      */     try {
/*  487 */       i = engine.getConfig().getDeployedServices();
/*      */     }
/*      */     catch (ConfigurationException configException)
/*      */     {
/*  491 */       if ((configException.getContainedException() instanceof AxisFault)) {
/*  492 */         throw ((AxisFault)configException.getContainedException());
/*      */       }
/*  494 */       throw configException;
/*      */     }
/*      */ 
/*  499 */     String defaultBaseURL = getWebappBase(request) + this.servicesPath;
/*  500 */     writer.println("<ul>");
/*  501 */     while (i.hasNext()) {
/*  502 */       ServiceDesc sd = (ServiceDesc)i.next();
/*  503 */       StringBuffer sb = new StringBuffer();
/*  504 */       sb.append("<li>");
/*  505 */       String name = sd.getName();
/*  506 */       sb.append(name);
/*  507 */       sb.append(" <a href=\"");
/*  508 */       String endpointURL = sd.getEndpointURL();
/*  509 */       String baseURL = endpointURL == null ? defaultBaseURL : endpointURL;
/*      */ 
/*  511 */       sb.append(baseURL);
/*  512 */       sb.append(name);
/*  513 */       sb.append("?wsdl\"><i>(wsdl)</i></a></li>");
/*  514 */       writer.println(sb.toString());
/*  515 */       ArrayList operations = sd.getOperations();
/*  516 */       if (!operations.isEmpty()) {
/*  517 */         writer.println("<ul>");
/*  518 */         for (Iterator it = operations.iterator(); it.hasNext(); ) {
/*  519 */           OperationDesc desc = (OperationDesc)it.next();
/*  520 */           writer.println("<li>" + desc.getName());
/*      */         }
/*  522 */         writer.println("</ul>");
/*      */       }
/*      */     }
/*  525 */     writer.println("</ul>");
/*      */   }
/*      */ 
/*      */   protected void reportCantGetAxisService(HttpServletRequest request, HttpServletResponse response, PrintWriter writer)
/*      */   {
/*  538 */     response.setStatus(404);
/*  539 */     response.setContentType("text/html; charset=utf-8");
/*  540 */     writer.println("<h2>" + Messages.getMessage("error00") + "</h2>");
/*      */ 
/*  542 */     writer.println("<p>" + Messages.getMessage("noService06") + "</p>");
/*      */   }
/*      */ 
/*      */   protected void reportCantGetJWSService(HttpServletRequest request, HttpServletResponse response, PrintWriter writer)
/*      */   {
/*  559 */     String requestPath = request.getServletPath() + (request.getPathInfo() != null ? request.getPathInfo() : "");
/*      */ 
/*  561 */     String realpath = getServletConfig().getServletContext().getRealPath(requestPath);
/*      */ 
/*  563 */     log.debug("JWS real path: " + realpath);
/*  564 */     boolean foundJWSFile = (new File(realpath).exists()) && (realpath.endsWith(".jws"));
/*      */ 
/*  567 */     response.setContentType("text/html; charset=utf-8");
/*  568 */     if (foundJWSFile) {
/*  569 */       response.setStatus(200);
/*  570 */       writer.println(Messages.getMessage("foundJWS00") + "<p>");
/*  571 */       String url = request.getRequestURI();
/*  572 */       String urltext = Messages.getMessage("foundJWS01");
/*  573 */       writer.println("<a href='" + url + "?wsdl'>" + urltext + "</a>");
/*      */     } else {
/*  575 */       response.setStatus(404);
/*  576 */       writer.println(Messages.getMessage("noService06"));
/*      */     }
/*      */   }
/*      */ 
/*      */   public void doPost(HttpServletRequest req, HttpServletResponse res)
/*      */     throws ServletException, IOException
/*      */   {
/*  591 */     long t0 = 0L; long t1 = 0L; long t2 = 0L; long t3 = 0L; long t4 = 0L;
/*  592 */     String soapAction = null;
/*  593 */     MessageContext msgContext = null;
/*  594 */     if (isDebug) {
/*  595 */       log.debug("Enter: doPost()");
/*      */     }
/*  597 */     if (tlog.isDebugEnabled()) {
/*  598 */       t0 = System.currentTimeMillis();
/*      */     }
/*      */ 
/*  601 */     Message responseMsg = null;
/*  602 */     String contentType = null;
/*      */     try
/*      */     {
/*  605 */       AxisEngine engine = getEngine();
/*      */ 
/*  607 */       if (engine == null)
/*      */       {
/*  609 */         ServletException se = new ServletException(Messages.getMessage("noEngine00"));
/*      */ 
/*  611 */         log.debug("No Engine!", se);
/*  612 */         throw se;
/*      */       }
/*      */ 
/*  615 */       res.setBufferSize(8192);
/*      */ 
/*  619 */       msgContext = createMessageContext(engine, req, res);
/*      */ 
/*  623 */       if (this.securityProvider != null) {
/*  624 */         if (isDebug) {
/*  625 */           log.debug("securityProvider:" + this.securityProvider);
/*      */         }
/*  627 */         msgContext.setProperty("securityProvider", this.securityProvider);
/*      */       }
/*      */ 
/*  633 */       Message requestMsg = new Message(req.getInputStream(), false, req.getHeader("Content-Type"), req.getHeader("Content-Location"));
/*      */ 
/*  640 */       MimeHeaders requestMimeHeaders = requestMsg.getMimeHeaders();
/*  641 */       for (Enumeration e = req.getHeaderNames(); e.hasMoreElements(); ) {
/*  642 */         String headerName = (String)e.nextElement();
/*  643 */         Enumeration f = req.getHeaders(headerName);
/*  644 */         while (f.hasMoreElements()) {
/*  645 */           String headerValue = (String)f.nextElement();
/*  646 */           requestMimeHeaders.addHeader(headerName, headerValue);
/*      */         }
/*      */       }
/*      */ 
/*  650 */       if (isDebug) {
/*  651 */         log.debug("Request Message:" + requestMsg);
/*      */       }
/*      */ 
/*  656 */       msgContext.setRequestMessage(requestMsg);
/*  657 */       String url = HttpUtils.getRequestURL(req).toString();
/*  658 */       msgContext.setProperty("transport.url", url);
/*      */       try
/*      */       {
/*  663 */         String requestEncoding = (String)requestMsg.getProperty("javax.xml.soap.character-set-encoding");
/*      */ 
/*  665 */         if (requestEncoding != null) {
/*  666 */           msgContext.setProperty("javax.xml.soap.character-set-encoding", requestEncoding);
/*      */         }
/*      */ 
/*      */       }
/*      */       catch (SOAPException e1)
/*      */       {
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/*  683 */         soapAction = getSoapAction(req);
/*      */ 
/*  685 */         if (soapAction != null) {
/*  686 */           msgContext.setUseSOAPAction(true);
/*  687 */           msgContext.setSOAPActionURI(soapAction);
/*      */         }
/*      */ 
/*  693 */         msgContext.setSession(new AxisHttpSession(req));
/*      */ 
/*  695 */         if (tlog.isDebugEnabled()) {
/*  696 */           t1 = System.currentTimeMillis();
/*      */         }
/*      */ 
/*  700 */         if (isDebug) {
/*  701 */           log.debug("Invoking Axis Engine.");
/*      */         }
/*      */ 
/*  704 */         engine.invoke(msgContext);
/*  705 */         if (isDebug) {
/*  706 */           log.debug("Return from Axis Engine.");
/*      */         }
/*  708 */         if (tlog.isDebugEnabled()) {
/*  709 */           t2 = System.currentTimeMillis();
/*      */         }
/*  711 */         responseMsg = msgContext.getResponseMessage();
/*      */       }
/*      */       catch (AxisFault fault)
/*      */       {
/*  720 */         processAxisFault(fault);
/*  721 */         configureResponseFromAxisFault(res, fault);
/*  722 */         responseMsg = msgContext.getResponseMessage();
/*  723 */         if (responseMsg == null) {
/*  724 */           responseMsg = new Message(fault);
/*  725 */           ((SOAPPart)responseMsg.getSOAPPart()).getMessage().setMessageContext(msgContext);
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  730 */         responseMsg = msgContext.getResponseMessage();
/*  731 */         res.setStatus(500);
/*  732 */         responseMsg = convertExceptionToAxisFault(e, responseMsg);
/*  733 */         ((SOAPPart)responseMsg.getSOAPPart()).getMessage().setMessageContext(msgContext);
/*      */       }
/*      */       catch (Throwable t) {
/*  736 */         logException(t);
/*      */ 
/*  738 */         responseMsg = msgContext.getResponseMessage();
/*  739 */         res.setStatus(500);
/*  740 */         responseMsg = new Message(new AxisFault(t.toString(), t));
/*  741 */         ((SOAPPart)responseMsg.getSOAPPart()).getMessage().setMessageContext(msgContext);
/*      */       }
/*      */     }
/*      */     catch (AxisFault fault) {
/*  745 */       processAxisFault(fault);
/*  746 */       configureResponseFromAxisFault(res, fault);
/*  747 */       responseMsg = msgContext.getResponseMessage();
/*  748 */       if (responseMsg == null) {
/*  749 */         responseMsg = new Message(fault);
/*  750 */         ((SOAPPart)responseMsg.getSOAPPart()).getMessage().setMessageContext(msgContext);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  755 */     if (tlog.isDebugEnabled()) {
/*  756 */       t3 = System.currentTimeMillis();
/*      */     }
/*      */ 
/*  761 */     if (responseMsg != null)
/*      */     {
/*  763 */       MimeHeaders responseMimeHeaders = responseMsg.getMimeHeaders();
/*  764 */       for (Iterator i = responseMimeHeaders.getAllHeaders(); i.hasNext(); ) {
/*  765 */         MimeHeader responseMimeHeader = (MimeHeader)i.next();
/*  766 */         res.addHeader(responseMimeHeader.getName(), responseMimeHeader.getValue());
/*      */       }
/*      */ 
/*  770 */       String responseEncoding = (String)msgContext.getProperty("javax.xml.soap.character-set-encoding");
/*      */ 
/*  772 */       if (responseEncoding != null) {
/*      */         try {
/*  774 */           responseMsg.setProperty("javax.xml.soap.character-set-encoding", responseEncoding);
/*      */         }
/*      */         catch (SOAPException e)
/*      */         {
/*      */         }
/*      */       }
/*  780 */       contentType = responseMsg.getContentType(msgContext.getSOAPConstants());
/*      */ 
/*  782 */       sendResponse(contentType, res, responseMsg);
/*      */     }
/*      */     else {
/*  785 */       res.setStatus(202);
/*      */     }
/*      */ 
/*  788 */     if (isDebug) {
/*  789 */       log.debug("Response sent.");
/*  790 */       log.debug("Exit: doPost()");
/*      */     }
/*  792 */     if (tlog.isDebugEnabled()) {
/*  793 */       t4 = System.currentTimeMillis();
/*  794 */       tlog.debug("axisServlet.doPost: " + soapAction + " pre=" + (t1 - t0) + " invoke=" + (t2 - t1) + " post=" + (t3 - t2) + " send=" + (t4 - t3) + " " + msgContext.getTargetService() + "." + (msgContext.getOperation() == null ? "" : msgContext.getOperation().getName()));
/*      */     }
/*      */   }
/*      */ 
/*      */   private void configureResponseFromAxisFault(HttpServletResponse response, AxisFault fault)
/*      */   {
/*  817 */     int status = getHttpServletResponseStatus(fault);
/*  818 */     if (status == 401)
/*      */     {
/*  821 */       response.setHeader("WWW-Authenticate", "Basic realm=\"AXIS\"");
/*      */     }
/*  823 */     response.setStatus(status);
/*      */   }
/*      */ 
/*      */   private Message convertExceptionToAxisFault(Exception exception, Message responseMsg)
/*      */   {
/*  838 */     logException(exception);
/*  839 */     if (responseMsg == null) {
/*  840 */       AxisFault fault = AxisFault.makeFault(exception);
/*  841 */       processAxisFault(fault);
/*  842 */       responseMsg = new Message(fault);
/*      */     }
/*  844 */     return responseMsg;
/*      */   }
/*      */ 
/*      */   protected int getHttpServletResponseStatus(AxisFault af)
/*      */   {
/*  856 */     return af.getFaultCode().getLocalPart().startsWith("Server.Unauth") ? 401 : 500;
/*      */   }
/*      */ 
/*      */   private void sendResponse(String contentType, HttpServletResponse res, Message responseMsg)
/*      */     throws AxisFault, IOException
/*      */   {
/*  875 */     if (responseMsg == null) {
/*  876 */       res.setStatus(204);
/*  877 */       if (isDebug) {
/*  878 */         log.debug("NO AXIS MESSAGE TO RETURN!");
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  884 */       if (isDebug) {
/*  885 */         log.debug("Returned Content-Type:" + contentType);
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/*  892 */         res.setContentType(contentType);
/*      */ 
/*  907 */         responseMsg.writeTo(res.getOutputStream());
/*      */       } catch (SOAPException e) {
/*  909 */         logException(e);
/*      */       }
/*      */     }
/*      */ 
/*  913 */     if (!res.isCommitted())
/*  914 */       res.flushBuffer();
/*      */   }
/*      */ 
/*      */   private MessageContext createMessageContext(AxisEngine engine, HttpServletRequest req, HttpServletResponse res)
/*      */   {
/*  929 */     MessageContext msgContext = new MessageContext(engine);
/*      */ 
/*  931 */     String requestPath = getRequestPath(req);
/*      */ 
/*  933 */     if (isDebug) {
/*  934 */       log.debug("MessageContext:" + msgContext);
/*  935 */       log.debug("HEADER_CONTENT_TYPE:" + req.getHeader("Content-Type"));
/*      */ 
/*  937 */       log.debug("HEADER_CONTENT_LOCATION:" + req.getHeader("Content-Location"));
/*      */ 
/*  939 */       log.debug("Constants.MC_HOME_DIR:" + String.valueOf(getHomeDir()));
/*  940 */       log.debug("Constants.MC_RELATIVE_PATH:" + requestPath);
/*  941 */       log.debug("HTTPConstants.MC_HTTP_SERVLETLOCATION:" + String.valueOf(getWebInfPath()));
/*      */ 
/*  943 */       log.debug("HTTPConstants.MC_HTTP_SERVLETPATHINFO:" + req.getPathInfo());
/*      */ 
/*  945 */       log.debug("HTTPConstants.HEADER_AUTHORIZATION:" + req.getHeader("Authorization"));
/*      */ 
/*  947 */       log.debug("Constants.MC_REMOTE_ADDR:" + req.getRemoteAddr());
/*  948 */       log.debug("configPath:" + String.valueOf(getWebInfPath()));
/*      */     }
/*      */ 
/*  953 */     msgContext.setTransportName(this.transportName);
/*      */ 
/*  957 */     msgContext.setProperty("jws.classDir", this.jwsClassDir);
/*  958 */     msgContext.setProperty("home.dir", getHomeDir());
/*  959 */     msgContext.setProperty("path", requestPath);
/*  960 */     msgContext.setProperty(HTTPConstants.MC_HTTP_SERVLET, this);
/*  961 */     msgContext.setProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST, req);
/*  962 */     msgContext.setProperty(HTTPConstants.MC_HTTP_SERVLETRESPONSE, res);
/*  963 */     msgContext.setProperty(HTTPConstants.MC_HTTP_SERVLETLOCATION, getWebInfPath());
/*      */ 
/*  965 */     msgContext.setProperty(HTTPConstants.MC_HTTP_SERVLETPATHINFO, req.getPathInfo());
/*      */ 
/*  967 */     msgContext.setProperty("Authorization", req.getHeader("Authorization"));
/*      */ 
/*  969 */     msgContext.setProperty("remoteaddr", req.getRemoteAddr());
/*      */ 
/*  972 */     ServletEndpointContextImpl sec = new ServletEndpointContextImpl();
/*      */ 
/*  974 */     msgContext.setProperty("servletEndpointContext", sec);
/*      */ 
/*  977 */     String realpath = getServletConfig().getServletContext().getRealPath(requestPath);
/*      */ 
/*  980 */     if (realpath != null) {
/*  981 */       msgContext.setProperty("realpath", realpath);
/*      */     }
/*      */ 
/*  984 */     msgContext.setProperty("configPath", getWebInfPath());
/*      */ 
/*  986 */     return msgContext;
/*      */   }
/*      */ 
/*      */   private String getSoapAction(HttpServletRequest req)
/*      */     throws AxisFault
/*      */   {
/*  998 */     String soapAction = req.getHeader("SOAPAction");
/*  999 */     if (soapAction == null) {
/* 1000 */       String contentType = req.getHeader("Content-Type");
/* 1001 */       if (contentType != null) {
/* 1002 */         int index = contentType.indexOf("action");
/* 1003 */         if (index != -1) {
/* 1004 */           soapAction = contentType.substring(index + 7);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1009 */     if (isDebug) {
/* 1010 */       log.debug("HEADER_SOAP_ACTION:" + soapAction);
/*      */     }
/*      */ 
/* 1018 */     if (soapAction == null)
/*      */     {
/* 1034 */       return null;
/*      */     }
/*      */ 
/* 1043 */     if ((soapAction.startsWith("\"")) && (soapAction.endsWith("\"")) && (soapAction.length() >= 2))
/*      */     {
/* 1045 */       int end = soapAction.length() - 1;
/* 1046 */       soapAction = soapAction.substring(1, end);
/*      */     }
/*      */ 
/* 1049 */     if (soapAction.length() == 0) {
/* 1050 */       soapAction = req.getContextPath();
/*      */     }
/*      */ 
/* 1053 */     return soapAction;
/*      */   }
/*      */ 
/*      */   protected String getDefaultJWSClassDir()
/*      */   {
/* 1062 */     return getWebInfPath() + File.separator + "jwsClasses";
/*      */   }
/*      */ 
/*      */   public void initQueryStringHandlers()
/*      */   {
/*      */     try
/*      */     {
/* 1074 */       this.transport = getEngine().getTransport(this.transportName);
/*      */ 
/* 1076 */       if (this.transport == null)
/*      */       {
/* 1080 */         this.transport = new SimpleTargetedChain();
/*      */ 
/* 1082 */         this.transport.setOption("qs.list", "org.apache.axis.transport.http.QSListHandler");
/*      */ 
/* 1084 */         this.transport.setOption("qs.method", "org.apache.axis.transport.http.QSMethodHandler");
/*      */ 
/* 1086 */         this.transport.setOption("qs.wsdl", "org.apache.axis.transport.http.QSWSDLHandler");
/*      */ 
/* 1089 */         return;
/*      */       }
/*      */ 
/* 1096 */       boolean defaultQueryStrings = true;
/* 1097 */       String useDefaults = (String)this.transport.getOption("useDefaultQueryStrings");
/*      */ 
/* 1100 */       if ((useDefaults != null) && (useDefaults.toLowerCase().equals("false")))
/*      */       {
/* 1102 */         defaultQueryStrings = false;
/*      */       }
/*      */ 
/* 1105 */       if (defaultQueryStrings == true)
/*      */       {
/* 1108 */         this.transport.setOption("qs.list", "org.apache.axis.transport.http.QSListHandler");
/*      */ 
/* 1110 */         this.transport.setOption("qs.method", "org.apache.axis.transport.http.QSMethodHandler");
/*      */ 
/* 1112 */         this.transport.setOption("qs.wsdl", "org.apache.axis.transport.http.QSWSDLHandler");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (AxisFault e)
/*      */     {
/* 1121 */       this.transport = new SimpleTargetedChain();
/*      */ 
/* 1123 */       this.transport.setOption("qs.list", "org.apache.axis.transport.http.QSListHandler");
/*      */ 
/* 1125 */       this.transport.setOption("qs.method", "org.apache.axis.transport.http.QSMethodHandler");
/*      */ 
/* 1127 */       this.transport.setOption("qs.wsdl", "org.apache.axis.transport.http.QSWSDLHandler");
/*      */ 
/* 1130 */       return;
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean processQuery(HttpServletRequest request, HttpServletResponse response, PrintWriter writer)
/*      */     throws AxisFault
/*      */   {
/* 1148 */     String path = request.getServletPath();
/* 1149 */     String queryString = request.getQueryString();
/*      */ 
/* 1151 */     AxisEngine engine = getEngine();
/* 1152 */     Iterator i = this.transport.getOptions().keySet().iterator();
/*      */ 
/* 1154 */     if (queryString == null) {
/* 1155 */       return false;
/*      */     }
/*      */ 
/* 1158 */     String servletURI = request.getContextPath() + path;
/* 1159 */     String reqURI = request.getRequestURI();
/*      */     String serviceName;

/* 1161 */     if (servletURI.length() + 1 < reqURI.length())
/* 1162 */       serviceName = reqURI.substring(servletURI.length() + 1);
/*      */     else
/* 1164 */       serviceName = "";
/* 1165 */     while (i.hasNext() == true) {
/* 1166 */       String queryHandler = (String)i.next();
/*      */ 
/* 1168 */       if (queryHandler.startsWith("qs.") == true)
/*      */       {
/* 1172 */         String handlerName = queryHandler.substring(queryHandler.indexOf(".") + 1).toLowerCase();
/*      */ 
/* 1180 */         int length = 0;
/* 1181 */         boolean firstParamFound = false;
/*      */ 
/* 1183 */         while ((!firstParamFound) && (length < queryString.length())) {
/* 1184 */           char ch = queryString.charAt(length++);
/*      */ 
/* 1186 */           if ((ch == '&') || (ch == '=')) {
/* 1187 */             firstParamFound = true;
/*      */ 
/* 1189 */             length--;
/*      */           }
/*      */         }
/*      */ 
/* 1193 */         if (length < queryString.length()) {
/* 1194 */           queryString = queryString.substring(0, length);
/*      */         }
/*      */ 
/* 1197 */         if (queryString.toLowerCase().equals(handlerName) == true)
/*      */         {
/* 1203 */           if (this.transport.getOption(queryHandler).equals("")) {
/* 1204 */             return false;
/*      */           }
/*      */ 
/*      */           try
/*      */           {
/* 1211 */             MessageContext msgContext = createMessageContext(engine, request, response);
/*      */ 
/* 1213 */             Class plugin = Class.forName((String)this.transport.getOption(queryHandler));
/*      */ 
/* 1215 */             Method pluginMethod = plugin.getDeclaredMethod("invoke", new Class[] { msgContext.getClass() });
/*      */ 
/* 1217 */             String url = HttpUtils.getRequestURL(request).toString();
/*      */ 
/* 1222 */             msgContext.setProperty("transport.url", url);
/* 1223 */             msgContext.setProperty("transport.http.plugin.serviceName", serviceName);
/*      */ 
/* 1225 */             msgContext.setProperty("transport.http.plugin.pluginName", handlerName);
/*      */ 
/* 1227 */             msgContext.setProperty("transport.http.plugin.isDevelopment", new Boolean(isDevelopment()));
/*      */ 
/* 1230 */             msgContext.setProperty("transport.http.plugin.enableList", new Boolean(this.enableList));
/*      */ 
/* 1232 */             msgContext.setProperty("transport.http.plugin.engine", engine);
/*      */ 
/* 1234 */             msgContext.setProperty("transport.http.plugin.writer", writer);
/*      */ 
/* 1236 */             msgContext.setProperty("transport.http.plugin.log", log);
/* 1237 */             msgContext.setProperty("transport.http.plugin.exceptionLog", exceptionLog);
/*      */ 
/* 1243 */             pluginMethod.invoke(plugin.newInstance(), new Object[] { msgContext });
/*      */ 
/* 1246 */             writer.close();
/*      */ 
/* 1248 */             return true;
/*      */           } catch (InvocationTargetException ie) {
/* 1250 */             reportTroubleInGet(ie.getTargetException(), response, writer);
/*      */ 
/* 1253 */             return true;
/*      */           } catch (Exception e) {
/* 1255 */             reportTroubleInGet(e, response, writer);
/*      */ 
/* 1257 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1263 */     return false;
/*      */   }
/*      */ 
/*      */   private static String getRequestPath(HttpServletRequest request)
/*      */   {
/* 1276 */     return request.getServletPath() + (request.getPathInfo() != null ? request.getPathInfo() : "");
/*      */   }
/*      */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.apache.axis.transport.http.AxisServlet
 * JD-Core Version:    0.6.0
 */