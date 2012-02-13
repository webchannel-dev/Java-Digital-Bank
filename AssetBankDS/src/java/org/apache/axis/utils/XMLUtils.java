/*     */ package org.apache.axis.utils;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.StringWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.io.Writer;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.ProtocolException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Stack;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import javax.xml.transform.stream.StreamSource;
import oracle.net.aso.e;
/*     */ import org.apache.axis.AxisEngine;
/*     */ import org.apache.axis.AxisProperties;
/*     */ import org.apache.axis.InternalException;
/*     */ import org.apache.axis.Message;
/*     */ import org.apache.axis.MessageContext;
/*     */ import org.apache.axis.components.encoding.XMLEncoder;
/*     */ import org.apache.axis.components.encoding.XMLEncoderFactory;
/*     */ import org.apache.axis.components.logger.LogFactory;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.CharacterData;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.Text;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import org.xml.sax.XMLReader;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ public class XMLUtils
/*     */ {
/*  77 */   protected static Log log = LogFactory.getLog(XMLUtils.class.getName());
/*     */   public static final String httpAuthCharEncoding = "ISO-8859-1";
/*     */   private static final String saxParserFactoryProperty = "javax.xml.parsers.SAXParserFactory";
/*  84 */   private static DocumentBuilderFactory dbf = getDOMFactory();
/*     */   private static SAXParserFactory saxFactory;
/*  86 */   private static Stack saxParsers = new Stack();
/*  87 */   private static DefaultHandler doNothingContentHandler = new DefaultHandler();
/*     */ 
/*  89 */   private static String EMPTY = "";
/*  90 */   private static ByteArrayInputStream bais = new ByteArrayInputStream(EMPTY.getBytes());
/*     */ 
/*  92 */   private static boolean tryReset = true;
/*     */ 
/*  94 */   protected static boolean enableParserReuse = false;
/*     */ 
/* 107 */   private static ThreadLocalDocumentBuilder documentBuilder = new ThreadLocalDocumentBuilder();
/*     */ static {
       // Initialize SAX Parser factory defaults
      initSAXFactory(null, true, false);

        String value = AxisProperties.getProperty(AxisEngine.PROP_XML_REUSE_SAX_PARSERS,
                "" + false);
         if (value.equalsIgnoreCase("true") ||
                 value.equals("1") ||
                 value.equalsIgnoreCase("yes")) {
             enableParserReuse = true;
        } else {
             enableParserReuse = false;
         }
     }
/*     */  // public static void flushThreadLocals()
/*     */  // {
/* 133 */   //  documentBuilder = new ThreadLocalDocumentBuilder(null);
/*     *///   }
/*     */ 
/*     */   public static String xmlEncodeString(String orig)
/*     */   {
/* 143 */     XMLEncoder encoder = getXMLEncoder(MessageContext.getCurrentContext());
/* 144 */     return encoder.encode(orig);
/*     */   }
/*     */ 
/*     */   public static XMLEncoder getXMLEncoder(MessageContext msgContext)
/*     */   {
/* 152 */     return getXMLEncoder(getEncoding(null, msgContext));
/*     */   }
/*     */ 
/*     */   public static XMLEncoder getXMLEncoder(String encoding)
/*     */   {
/* 160 */     XMLEncoder encoder = null;
/*     */     try {
/* 162 */       encoder = XMLEncoderFactory.getEncoder(encoding);
/*     */     } catch (Exception e) {
/* 164 */       log.error(Messages.getMessage("exception00"), e);
/* 165 */       encoder = XMLEncoderFactory.getDefaultEncoder();
/*     */     }
/* 167 */     return encoder;
/*     */   }
/*     */ 
/*     */   public static String getEncoding(MessageContext msgContext)
/*     */   {
/* 175 */     XMLEncoder encoder = getXMLEncoder(msgContext);
/* 176 */     return encoder.getEncoding();
/*     */   }
/*     */ 
/*     */   public static String getEncoding()
/*     */   {
/* 184 */     XMLEncoder encoder = getXMLEncoder(MessageContext.getCurrentContext());
/* 185 */     return encoder.getEncoding();
/*     */   }
/*     */ 
/*     */   public static void initSAXFactory(String factoryClassName, boolean namespaceAware, boolean validating)
/*     */   {
/* 205 */     if (factoryClassName != null)
/*     */       try {
/* 207 */         saxFactory = (SAXParserFactory)Class.forName(factoryClassName).newInstance();
/*     */ 
/* 213 */         if (System.getProperty("javax.xml.parsers.SAXParserFactory") == null)
/* 214 */           System.setProperty("javax.xml.parsers.SAXParserFactory", factoryClassName);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 218 */         log.error(Messages.getMessage("exception00"), e);
/* 219 */         saxFactory = null;
/*     */       }
/*     */     else {
/* 222 */       saxFactory = SAXParserFactory.newInstance();
/*     */     }
/* 224 */     saxFactory.setNamespaceAware(namespaceAware);
/* 225 */     saxFactory.setValidating(validating);
/*     */ 
/* 228 */     saxParsers.clear();
/*     */   }
/*     */   private static DocumentBuilderFactory getDOMFactory() {
/*     */     DocumentBuilderFactory dbf;
/*     */     try {
/* 234 */       dbf = DocumentBuilderFactory.newInstance();
/* 235 */       dbf.setNamespaceAware(true);
/*     */     }
/*     */     catch (Exception e) {
/* 238 */       log.error(Messages.getMessage("exception00"), e);
/* 239 */       dbf = null;
/*     */     }
/* 241 */     return dbf;
/*     */   }
/*     */ 
/*     */   public static DocumentBuilder getDocumentBuilder()
/*     */     throws ParserConfigurationException
/*     */   {
/* 250 */     return (DocumentBuilder)documentBuilder.get();
/*     */   }
/*     */ 
/*     */   public static void releaseDocumentBuilder(DocumentBuilder db)
/*     */   {
/*     */     try
/*     */     {
/* 259 */       db.setErrorHandler(null);
/*     */     } catch (Throwable t) {
/* 261 */       log.debug("Failed to set ErrorHandler to null on DocumentBuilder", t);
/*     */     }
/*     */     try
/*     */     {
/* 265 */       db.setEntityResolver(null);
/*     */     } catch (Throwable t) {
/* 267 */       log.debug("Failed to set EntityResolver to null on DocumentBuilder", t);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static synchronized SAXParser getSAXParser()
/*     */   {
/* 277 */     if ((enableParserReuse) && (!saxParsers.empty())) {
/* 278 */       return (SAXParser)saxParsers.pop();
/*     */     }
/*     */     try
/*     */     {
/* 282 */       SAXParser parser = saxFactory.newSAXParser();
/* 283 */       XMLReader reader = parser.getXMLReader();
/*     */       try
/*     */       {
/* 290 */         reader.setEntityResolver(new DefaultEntityResolver());
/*     */       } catch (Throwable t) {
/* 292 */         log.debug("Failed to set EntityResolver on DocumentBuilder", t);
/*     */       }
/* 294 */       reader.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
/* 295 */       return parser;
/*     */     } catch (ParserConfigurationException e) {
/* 297 */       log.error(Messages.getMessage("parserConfigurationException00"), e);
/* 298 */       return null;
/*     */     } catch (SAXException se) {
/* 300 */       log.error(Messages.getMessage("SAXException00"), se);
/* 301 */     }return null;
/*     */   }
/*     */ 
/*     */   public static void releaseSAXParser(SAXParser parser)
/*     */   {
/* 310 */     if ((!tryReset) || (!enableParserReuse)) return;
/*     */ 
/*     */     try
/*     */     {
/* 314 */       XMLReader xmlReader = parser.getXMLReader();
/* 315 */       if (null != xmlReader) {
/* 316 */         xmlReader.setContentHandler(doNothingContentHandler);
/* 317 */         xmlReader.setDTDHandler(doNothingContentHandler);
/*     */         try {
/* 319 */           xmlReader.setEntityResolver(doNothingContentHandler);
/*     */         } catch (Throwable t) {
/* 321 */           log.debug("Failed to set EntityResolver on DocumentBuilder", t);
/*     */         }
/*     */         try {
/* 324 */           xmlReader.setErrorHandler(doNothingContentHandler);
/*     */         } catch (Throwable t) {
/* 326 */           log.debug("Failed to set ErrorHandler on DocumentBuilder", t);
/*     */         }
/*     */ 
/* 329 */         synchronized (XMLUtils.class) {
/* 330 */           saxParsers.push(parser);
/*     */         }
/*     */       }
/*     */       else {
/* 334 */         tryReset = false;
/*     */       }
/*     */     } catch (SAXException e) {
/* 337 */       tryReset = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Document newDocument()
/*     */     throws ParserConfigurationException
/*     */   {
/* 348 */     DocumentBuilder db = null;
/*     */     try {
/* 350 */       db = getDocumentBuilder();
/* 351 */       Document doc = db.newDocument();
/* 352 */       return doc;
/*     */     }
/*     */     finally
/*     */     {
/*     */      
/* 354 */       if (db != null)
{
/* 355 */         releaseDocumentBuilder(db);
/*     */     }
/*     */   }
}
/*     */ 
/*     */   public static Document newDocument(InputSource inp)
/*     */     throws ParserConfigurationException, SAXException, IOException
/*     */   {
/* 369 */     DocumentBuilder db = null;
/*     */     try {
/* 371 */       db = getDocumentBuilder();
/*     */       try {
/* 373 */         db.setEntityResolver(new DefaultEntityResolver());
/*     */       } catch (Throwable t) {
/* 375 */         log.debug("Failed to set EntityResolver on DocumentBuilder", t);
/*     */       }
/*     */       try {
/* 378 */         db.setErrorHandler(new ParserErrorHandler());
/*     */       } catch (Throwable t) {
/* 380 */         log.debug("Failed to set ErrorHandler on DocumentBuilder", t);
/*     */       }
/* 382 */       Document doc = db.parse(inp);
/* 383 */       return doc;
/*     */     }
/*     */     finally
/*     */     {
/*     */       Document localDocument1;
/* 385 */       if (db != null)
/* 386 */         releaseDocumentBuilder(db);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Document newDocument(InputStream inp)
/*     */     throws ParserConfigurationException, SAXException, IOException
/*     */   {
/* 401 */     return newDocument(new InputSource(inp));
/*     */   }
/*     */ 
/*     */   public static Document newDocument(String uri)
/*     */     throws ParserConfigurationException, SAXException, IOException
/*     */   {
/* 416 */     return newDocument(uri, null, null);
/*     */   }
/*     */ 
/*     */   public static Document newDocument(String uri, String username, String password)
/*     */     throws ParserConfigurationException, SAXException, IOException
/*     */   {
/* 432 */     InputSource ins = getInputSourceFromURI(uri, username, password);
/* 433 */     Document doc = newDocument(ins);
/*     */ 
/* 435 */     if (ins.getByteStream() != null)
/* 436 */       ins.getByteStream().close();
/* 437 */     else if (ins.getCharacterStream() != null) {
/* 438 */       ins.getCharacterStream().close();
/*     */     }
/* 440 */     return doc;
/*     */   }
/*     */ 
/*     */   private static String privateElementToString(Element element, boolean omitXMLDecl)
/*     */   {
/* 446 */     return DOM2Writer.nodeToString(element, omitXMLDecl);
/*     */   }
/*     */ 
/*     */   public static String ElementToString(Element element)
/*     */   {
/* 455 */     return privateElementToString(element, true);
/*     */   }
/*     */ 
/*     */   public static String DocumentToString(Document doc)
/*     */   {
/* 464 */     return privateElementToString(doc.getDocumentElement(), false);
/*     */   }
/*     */ 
/*     */   public static String PrettyDocumentToString(Document doc) {
/* 468 */     StringWriter sw = new StringWriter();
/* 469 */     PrettyElementToWriter(doc.getDocumentElement(), sw);
/* 470 */     return sw.toString();
/*     */   }
/*     */ 
/*     */   public static void privateElementToWriter(Element element, Writer writer, boolean omitXMLDecl, boolean pretty)
/*     */   {
/* 476 */     DOM2Writer.serializeAsXML(element, writer, omitXMLDecl, pretty);
/*     */   }
/*     */ 
/*     */   public static void ElementToStream(Element element, OutputStream out) {
/* 480 */     Writer writer = getWriter(out);
/* 481 */     privateElementToWriter(element, writer, true, false);
/*     */   }
/*     */ 
/*     */   public static void PrettyElementToStream(Element element, OutputStream out) {
/* 485 */     Writer writer = getWriter(out);
/* 486 */     privateElementToWriter(element, writer, true, true);
/*     */   }
/*     */ 
/*     */   public static void ElementToWriter(Element element, Writer writer) {
/* 490 */     privateElementToWriter(element, writer, true, false);
/*     */   }
/*     */ 
/*     */   public static void PrettyElementToWriter(Element element, Writer writer) {
/* 494 */     privateElementToWriter(element, writer, true, true);
/*     */   }
/*     */ 
/*     */   public static void DocumentToStream(Document doc, OutputStream out) {
/* 498 */     Writer writer = getWriter(out);
/* 499 */     privateElementToWriter(doc.getDocumentElement(), writer, false, false);
/*     */   }
/*     */ 
/*     */   public static void PrettyDocumentToStream(Document doc, OutputStream out) {
/* 503 */     Writer writer = getWriter(out);
/* 504 */     privateElementToWriter(doc.getDocumentElement(), writer, false, true);
/*     */   }
/*     */ 
/*     */   private static Writer getWriter(OutputStream os) {
/* 508 */     Writer writer = null;
/*     */     try {
/* 510 */       writer = new OutputStreamWriter(os, "UTF-8");
/*     */     } catch (UnsupportedEncodingException uee) {
/* 512 */       log.error(Messages.getMessage("exception00"), uee);
/* 513 */       writer = new OutputStreamWriter(os);
/*     */     }
/* 515 */     return writer;
/*     */   }
/*     */ 
/*     */   public static void DocumentToWriter(Document doc, Writer writer) {
/* 519 */     privateElementToWriter(doc.getDocumentElement(), writer, false, false);
/*     */   }
/*     */ 
/*     */   public static void PrettyDocumentToWriter(Document doc, Writer writer) {
/* 523 */     privateElementToWriter(doc.getDocumentElement(), writer, false, true);
/*     */   }
/*     */ 
/*     */   public static Element StringToElement(String namespace, String name, String string)
/*     */   {
/*     */     try
/*     */     {
/* 535 */       Document doc = newDocument();
/* 536 */       Element element = doc.createElementNS(namespace, name);
/* 537 */       Text text = doc.createTextNode(string);
/* 538 */       element.appendChild(text);
/* 539 */       return element;
/*     */     }/*     */     catch (ParserConfigurationException e) {
/*     */     
/* 543 */     throw new InternalException(e);
/*     */   }
}
//</editor-fold>
/*     */ 
/*     */   public static String getInnerXMLString(Element element)
/*     */   {
/* 555 */     String elementString = ElementToString(element);
/*     */ 
/* 557 */     int start = elementString.indexOf(">") + 1;
/* 558 */     int end = elementString.lastIndexOf("</");
/* 559 */     if (end > 0) {
/* 560 */       return elementString.substring(start, end);
/*     */     }
/* 562 */     return null;
/*     */   }
/*     */ 
/*     */   public static String getPrefix(String uri, Node e) {
/* 566 */     while ((e != null) && (e.getNodeType() == 1)) {
/* 567 */       NamedNodeMap attrs = e.getAttributes();
/* 568 */       for (int n = 0; n < attrs.getLength(); n++) {
/* 569 */         Attr a = (Attr)attrs.item(n);
/*     */         String name;
/* 571 */         if (((name = a.getName()).startsWith("xmlns:")) && (a.getNodeValue().equals(uri)))
/*     */         {
/* 573 */           return name.substring(6);
/*     */         }
/*     */       }
/* 576 */       e = e.getParentNode();
/*     */     }
/* 578 */     return null;
/*     */   }
/*     */ 
/*     */   public static String getNamespace(String prefix, Node e, Node stopNode)
/*     */   {
/* 594 */     while ((e != null) && (e.getNodeType() == 1)) {
/* 595 */       Attr attr = null;
/* 596 */       if (prefix == null)
/* 597 */         attr = ((Element)e).getAttributeNode("xmlns");
/*     */       else {
/* 599 */         attr = ((Element)e).getAttributeNodeNS("http://www.w3.org/2000/xmlns/", prefix);
/*     */       }
/*     */ 
/* 602 */       if (attr != null) return attr.getValue();
/* 603 */       if (e == stopNode)
/* 604 */         return null;
/* 605 */       e = e.getParentNode();
/*     */     }
/* 607 */     return null;
/*     */   }
/*     */ 
/*     */   public static String getNamespace(String prefix, Node e) {
/* 611 */     return getNamespace(prefix, e, null);
/*     */   }
/*     */ 
/*     */   public static QName getQNameFromString(String str, Node e)
/*     */   {
/* 621 */     return getQNameFromString(str, e, false);
/*     */   }
/*     */ 
/*     */   public static QName getFullQNameFromString(String str, Node e)
/*     */   {
/* 631 */     return getQNameFromString(str, e, true);
/*     */   }
/*     */   private static QName getQNameFromString(String str, Node e, boolean defaultNS) {
/* 634 */     if ((str == null) || (e == null)) {
/* 635 */       return null;
/*     */     }
/* 637 */     int idx = str.indexOf(':');
/* 638 */     if (idx > -1) {
/* 639 */       String prefix = str.substring(0, idx);
/* 640 */       String ns = getNamespace(prefix, e);
/* 641 */       if (ns == null)
/* 642 */         return null;
/* 643 */       return new QName(ns, str.substring(idx + 1));
/*     */     }
/* 645 */     if (defaultNS) {
/* 646 */       String ns = getNamespace(null, e);
/* 647 */       if (ns != null)
/* 648 */         return new QName(ns, str);
/*     */     }
/* 650 */     return new QName("", str);
/*     */   }
/*     */ 
/*     */   public static String getStringForQName(QName qname, Element e)
/*     */   {
/* 660 */     String uri = qname.getNamespaceURI();
/* 661 */     String prefix = getPrefix(uri, e);
/* 662 */     if (prefix == null) {
/* 663 */       int i = 1;
/* 664 */       prefix = "ns" + i;
/* 665 */       while (getNamespace(prefix, e) != null) {
/* 666 */         i++;
/* 667 */         prefix = "ns" + i;
/*     */       }
/* 669 */       e.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix, uri);
/*     */     }
/*     */ 
/* 672 */     return prefix + ":" + qname.getLocalPart();
/*     */   }
/*     */ 
/*     */   public static String getChildCharacterData(Element parentEl)
/*     */   {
/* 685 */     if (parentEl == null) {
/* 686 */       return null;
/*     */     }
/* 688 */     Node tempNode = parentEl.getFirstChild();
/* 689 */     StringBuffer strBuf = new StringBuffer();
/*     */ 
/* 692 */     while (tempNode != null) {
/* 693 */       switch (tempNode.getNodeType()) { case 3:
/*     */       case 4:
/* 695 */         CharacterData charData = (CharacterData)tempNode;
/* 696 */         strBuf.append(charData.getData());
/*     */       }
/*     */ 
/* 699 */       tempNode = tempNode.getNextSibling();
/*     */     }
/* 701 */     return strBuf.toString();
/*     */   }
/*     */ 
/*     */   public static InputSource getInputSourceFromURI(String uri)
/*     */   {
/* 750 */     return new InputSource(uri);
/*     */   }
/*     */ 
/*     */   public static InputSource sourceToInputSource(Source source)
/*     */   {
/* 759 */     if ((source instanceof SAXSource))
/* 760 */       return ((SAXSource)source).getInputSource();
/* 761 */     if ((source instanceof DOMSource)) {
/* 762 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 763 */       Node node = ((DOMSource)source).getNode();
/* 764 */       if ((node instanceof Document)) {
/* 765 */         node = ((Document)node).getDocumentElement();
/*     */       }
/* 767 */       Element domElement = (Element)node;
/* 768 */       ElementToStream(domElement, baos);
/* 769 */       InputSource isource = new InputSource(source.getSystemId());
/* 770 */       isource.setByteStream(new ByteArrayInputStream(baos.toByteArray()));
/* 771 */       return isource;
/* 772 */     }if ((source instanceof StreamSource)) {
/* 773 */       StreamSource ss = (StreamSource)source;
/* 774 */       InputSource isource = new InputSource(ss.getSystemId());
/* 775 */       isource.setByteStream(ss.getInputStream());
/* 776 */       isource.setCharacterStream(ss.getReader());
/* 777 */       isource.setPublicId(ss.getPublicId());
/* 778 */       return isource;
/*     */     }
/* 780 */     return getInputSourceFromURI(source.getSystemId());
/*     */   }
/*     */ 
/*     */   private static InputSource getInputSourceFromURI(String uri, String username, String password)
/*     */     throws IOException, ProtocolException, UnsupportedEncodingException
/*     */   {
/* 803 */     URL wsdlurl = null;
/*     */     try {
/* 805 */       wsdlurl = new URL(uri);
/*     */     }
/*     */     catch (MalformedURLException e)
/*     */     {
/* 809 */       return new InputSource(uri);
/*     */     }
/*     */ 
/* 813 */     if ((username == null) && (wsdlurl.getUserInfo() == null)) {
/* 814 */       return new InputSource(uri);
/*     */     }
/*     */ 
/* 818 */     if (!wsdlurl.getProtocol().startsWith("http")) {
/* 819 */       return new InputSource(uri);
/*     */     }
/*     */ 
/* 822 */     URLConnection connection = wsdlurl.openConnection();
/*     */ 
/* 824 */     if (!(connection instanceof HttpURLConnection))
/*     */     {
/* 826 */       return new InputSource(uri);
/*     */     }
/* 828 */     HttpURLConnection uconn = (HttpURLConnection)connection;
/* 829 */     String userinfo = wsdlurl.getUserInfo();
/* 830 */     uconn.setRequestMethod("GET");
/* 831 */     uconn.setAllowUserInteraction(false);
/* 832 */     uconn.setDefaultUseCaches(false);
/* 833 */     uconn.setDoInput(true);
/* 834 */     uconn.setDoOutput(false);
/* 835 */     uconn.setInstanceFollowRedirects(true);
/* 836 */     uconn.setUseCaches(false);
/*     */ 
/* 839 */     String auth = null;
/* 840 */     if (userinfo != null)
/* 841 */       auth = userinfo;
/* 842 */     else if (username != null) {
/* 843 */       auth = username + ":" + password;
/*     */     }
/*     */ 
/* 846 */     if (auth != null) {
/* 847 */       uconn.setRequestProperty("Authorization", "Basic " + base64encode(auth.getBytes("ISO-8859-1")));
/*     */     }
/*     */ 
/* 852 */     uconn.connect();
/*     */ 
/* 854 */     return new InputSource(uconn.getInputStream());
/*     */   }
/*     */ 
/*     */   public static final String base64encode(byte[] bytes) {
/* 858 */     return new String(Base64.encode(bytes));
/*     */   }
/*     */ 
/*     */   public static InputSource getEmptyInputSource() {
/* 862 */     return new InputSource(bais);
/*     */   }
/*     */ 
/*     */   public static Node findNode(Node node, QName name)
/*     */   {
/* 873 */     if ((name.getNamespaceURI().equals(node.getNamespaceURI())) && (name.getLocalPart().equals(node.getLocalName())))
/*     */     {
/* 875 */       return node;
/* 876 */     }NodeList children = node.getChildNodes();
/* 877 */     for (int i = 0; i < children.getLength(); i++) {
/* 878 */       Node ret = findNode(children.item(i), name);
/* 879 */       if (ret != null)
/* 880 */         return ret;
/*     */     }
/* 882 */     return null;
/*     */   }
/*     */ 
/*     */   public static void normalize(Node node)
/*     */   {
/* 891 */     if (node.getNodeType() == 3) {
/* 892 */       String data = ((Text)node).getData();
/* 893 */       if (data.length() > 0) {
/* 894 */         char ch = data.charAt(data.length() - 1);
/* 895 */         if ((ch == '\n') || (ch == '\r') || (ch == ' ')) {
/* 896 */           String data2 = trim(data);
/* 897 */           ((Text)node).setData(data2);
/*     */         }
/*     */       }
/*     */     }
/* 901 */     for (Node currentChild = node.getFirstChild(); currentChild != null; currentChild = currentChild.getNextSibling())
/* 902 */       normalize(currentChild);
/*     */   }
/*     */ 
/*     */   public static String trim(String str)
/*     */   {
/* 907 */     if (str.length() == 0) {
/* 908 */       return str;
/*     */     }
/*     */ 
/* 911 */     if (str.length() == 1) {
/* 912 */       if (("\r".equals(str)) || ("\n".equals(str))) {
/* 913 */         return "";
/*     */       }
/* 915 */       return str;
/*     */     }
/*     */ 
/* 919 */     int lastIdx = str.length() - 1;
/* 920 */     char last = str.charAt(lastIdx);
/* 921 */     while ((lastIdx > 0) && (
/* 922 */       (last == '\n') || (last == '\r') || (last == ' ')))
/*     */     {
/* 924 */       lastIdx--;
/* 925 */       last = str.charAt(lastIdx);
/*     */     }
/* 927 */     if (lastIdx == 0)
/* 928 */       return "";
/* 929 */     return str.substring(0, lastIdx);
/*     */   }
/*     */ 
/*     */   public static Element[] asElementArray(List list)
/*     */   {
/* 940 */     Element[] elements = new Element[list.size()];
/*     */ 
/* 942 */     int i = 0;
/* 943 */     Iterator detailIter = list.iterator();
/* 944 */     while (detailIter.hasNext()) {
/* 945 */       elements[(i++)] = ((Element)detailIter.next());
/*     */     }
/*     */ 
/* 948 */     return elements;
/*     */   }
/*     */ 
/*     */   public static String getEncoding(Message message, MessageContext msgContext)
/*     */   {
/* 953 */     return getEncoding(message, msgContext, XMLEncoderFactory.getDefaultEncoder());
/*     */   }
/*     */ 
/*     */   public static String getEncoding(Message message, MessageContext msgContext, XMLEncoder defaultEncoder)
/*     */   {
/* 960 */     String encoding = null;
/*     */     try {
/* 962 */       if (message != null)
/* 963 */         encoding = (String)message.getProperty("javax.xml.soap.character-set-encoding");
/*     */     }
/*     */     catch (SOAPException e) {
/*     */     }
/* 967 */     if (msgContext == null) {
/* 968 */       msgContext = MessageContext.getCurrentContext();
/*     */     }
/* 970 */     if ((msgContext != null) && (encoding == null)) {
/* 971 */       encoding = (String)msgContext.getProperty("javax.xml.soap.character-set-encoding");
/*     */     }
/* 973 */     if ((msgContext != null) && (encoding == null) && (msgContext.getAxisEngine() != null)) {
/* 974 */       encoding = (String)msgContext.getAxisEngine().getOption("axis.xmlEncoding");
/*     */     }
/* 976 */     if ((encoding == null) && (defaultEncoder != null)) {
/* 977 */       encoding = defaultEncoder.getEncoding();
/*     */     }
/* 979 */     return encoding;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 111 */     initSAXFactory(null, true, false);
/*     */ 
/* 113 */     String value = AxisProperties.getProperty("axis.xml.reuseParsers", "true");
/*     */ 
/* 115 */     if ((value.equalsIgnoreCase("true")) || (value.equals("1")) || (value.equalsIgnoreCase("yes")))
/*     */     {
/* 118 */       enableParserReuse = true;
/*     */     }
/* 120 */     else enableParserReuse = false;
/*     */   }
/*     */ 
/*     */   public static class ParserErrorHandler
/*     */     implements ErrorHandler
/*     */   {
/* 705 */     protected static Log log = LogFactory.getLog(ParserErrorHandler.class.getName());
/*     */ 
/*     */     private String getParseExceptionInfo(SAXParseException spe)
/*     */     {
/* 711 */       String systemId = spe.getSystemId();
/* 712 */       if (systemId == null) {
/* 713 */         systemId = "null";
/*     */       }
/* 715 */       String info = "URI=" + systemId + " Line=" + spe.getLineNumber() + ": " + spe.getMessage();
/*     */ 
/* 718 */       return info;
/*     */     }
/*     */ 
/*     */     public void warning(SAXParseException spe)
/*     */       throws SAXException
/*     */     {
/* 725 */       if (log.isDebugEnabled())
/* 726 */         log.debug(Messages.getMessage("warning00", getParseExceptionInfo(spe)));
/*     */     }
/*     */ 
/*     */     public void error(SAXParseException spe) throws SAXException {
/* 730 */       String message = "Error: " + getParseExceptionInfo(spe);
/* 731 */       throw new SAXException(message);
/*     */     }
/*     */ 
/*     */     public void fatalError(SAXParseException spe) throws SAXException {
/* 735 */       String message = "Fatal Error: " + getParseExceptionInfo(spe);
/* 736 */       throw new SAXException(message);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class ThreadLocalDocumentBuilder extends ThreadLocal
/*     */   {
/*     */     protected Object initialValue()
/*     */     {
/*     */       try
/*     */       {
/*  99 */         //return XMLUtils.access$000().newDocumentBuilder();
    return getDOMFactory().newDocumentBuilder();
/*     */       } catch (ParserConfigurationException e) {
/* 101 */         XMLUtils.log.error(Messages.getMessage("parserConfigurationException00"), e);
/*     */       }
/*     */ 
/* 104 */       return null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.apache.axis.utils.XMLUtils
 * JD-Core Version:    0.6.0
 */