/*     */ package com.bright.framework.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import java.io.StringWriter;
/*     */ import java.text.ParseException;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Vector;
/*     */ import javax.xml.datatype.XMLGregorianCalendar;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.jdom.Namespace;
/*     */ import org.jdom.output.XMLOutputter;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class XMLUtil
/*     */   implements FrameworkConstants
/*     */ {
/*     */   private static final String c_ksClassName = "XMLUtil";
/*     */ 
/*     */   public static String getXMLElementContent(org.jdom.Element a_element)
/*     */   {
/*  79 */     String sFormattedContent = null;
/*     */ 
/*  81 */     if (a_element == null)
/*     */     {
/*  83 */       return null;
/*     */     }
/*     */ 
/*  87 */     StringWriter swWriter = new StringWriter();
/*     */ 
/*  89 */     XMLOutputter outputter = new XMLOutputter();
/*     */     try
/*     */     {
/*  92 */       outputter.outputElementContent(a_element, swWriter);
/*  93 */       sFormattedContent = swWriter.toString();
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/*  97 */       sFormattedContent = null;
/*     */     }
/*     */ 
/* 101 */     return sFormattedContent;
/*     */   }
/*     */ 
/*     */   public static String getXMLElementContentForHTML(org.jdom.Element a_element)
/*     */   {
/* 122 */     String sFormattedContent = null;
/*     */ 
/* 124 */     sFormattedContent = getXMLElementContent(a_element);
/*     */ 
/* 126 */     if (a_element != null)
/*     */     {
/* 128 */       sFormattedContent = sFormattedContent.replaceAll("\\n", "<br>");
/* 129 */       sFormattedContent = sFormattedContent.replaceAll("&lt;", "<");
/* 130 */       sFormattedContent = sFormattedContent.replaceAll("&gt;", ">");
/* 131 */       sFormattedContent = sFormattedContent.replaceAll("&quot;", "\"");
/* 132 */       sFormattedContent = sFormattedContent.replaceAll("&apos;", "'");
/* 133 */       sFormattedContent = sFormattedContent.replaceAll("&amp;", "&");
/*     */     }
/*     */ 
/* 136 */     return sFormattedContent;
/*     */   }
/*     */ 
/*     */   public static String getXMLSafeValue(String a_sValue)
/*     */   {
/* 141 */     a_sValue = a_sValue.replaceAll("<", "&lt;");
/* 142 */     a_sValue = a_sValue.replaceAll(">", "&gt;");
/* 143 */     a_sValue = a_sValue.replaceAll("\"", "&quot;");
/* 144 */     a_sValue = a_sValue.replaceAll("'", "&apos;");
/* 145 */     a_sValue = a_sValue.replaceAll("&", "&amp;");
/* 146 */     return a_sValue;
/*     */   }
/*     */ 
/*     */   public static String replaceString(String a_sString, String a_sSubStringToReplace, String a_sReplacement)
/*     */   {
/* 164 */     StringBuffer sbNewText = new StringBuffer("");
/* 165 */     int iStartPos = 0;
/* 166 */     int iStartSearchPos = 0;
/*     */ 
/* 169 */     if (a_sString == null)
/*     */     {
/* 171 */       return a_sString;
/*     */     }
/*     */ 
/* 174 */     while (iStartPos >= 0)
/*     */     {
/* 177 */       iStartPos = a_sString.indexOf(a_sSubStringToReplace, iStartSearchPos);
/*     */ 
/* 179 */       if (iStartPos < 0)
/*     */       {
/* 182 */         sbNewText.append(a_sString.substring(iStartSearchPos, a_sString.length())); continue;
/*     */       }
/*     */ 
/* 187 */       sbNewText.append(a_sString.substring(iStartSearchPos, iStartPos));
/*     */ 
/* 190 */       sbNewText.append(a_sReplacement);
/*     */ 
/* 193 */       iStartSearchPos = iStartPos + a_sSubStringToReplace.length();
/*     */     }
/*     */ 
/* 198 */     return sbNewText.toString();
/*     */   }
/*     */ 
/*     */   public static Vector getTextFromChildren(org.jdom.Element a_xmlElement, String a_sChlidElementName)
/*     */   {
/* 213 */     Vector vChildText = null;
/* 214 */     String sText = null;
/* 215 */     org.jdom.Element xmlChild = null;
/*     */ 
/* 218 */     if (a_xmlElement == null)
/*     */     {
/* 221 */       return new Vector();
/*     */     }
/*     */ 
/* 225 */     List lstChildren = a_xmlElement.getChildren(a_sChlidElementName);
/* 226 */     ListIterator lstiChildren = (ListIterator)(ListIterator)lstChildren.iterator();
/*     */ 
/* 228 */     vChildText = new Vector(lstChildren.size());
/*     */ 
/* 230 */     while (lstiChildren.hasNext())
/*     */     {
/* 232 */       xmlChild = (org.jdom.Element)lstiChildren.next();
/*     */ 
/* 234 */       if (xmlChild == null)
/*     */         continue;
/* 236 */       sText = xmlChild.getTextTrim();
/*     */ 
/* 238 */       if ((sText == null) || (sText.length() <= 0))
/*     */         continue;
/* 240 */       vChildText.add(sText);
/*     */     }
/*     */ 
/* 245 */     return vChildText;
/*     */   }
/*     */ 
/*     */   public static String getTextFromChild(org.w3c.dom.Element a_elDocElement, String a_sTagName)
/*     */   {
/* 257 */     String sBody = "";
/*     */ 
/* 260 */     NodeList nlBodies = a_elDocElement.getElementsByTagName(a_sTagName);
/*     */ 
/* 262 */     if (nlBodies.getLength() > 0)
/*     */     {
/* 265 */       org.w3c.dom.Element elBody = (org.w3c.dom.Element)nlBodies.item(0);
/*     */ 
/* 267 */       Node nBodyText = elBody.getFirstChild();
/*     */ 
/* 269 */       if (nBodyText != null)
/*     */       {
/* 271 */         sBody = nBodyText.getNodeValue();
/*     */       }
/*     */     }
/*     */ 
/* 275 */     return sBody.trim();
/*     */   }
/*     */ 
/*     */   public static Vector getTextFromChildren(org.w3c.dom.Element a_xmlElement, String a_sChildElementName)
/*     */   {
/* 291 */     Vector vChildText = null;
/* 292 */     String sText = null;
/*     */ 
/* 295 */     if (a_xmlElement == null)
/*     */     {
/* 298 */       return new Vector();
/*     */     }
/*     */ 
/* 302 */     NodeList nlBodies = a_xmlElement.getChildNodes();
/*     */ 
/* 304 */     vChildText = new Vector(nlBodies.getLength());
/*     */ 
/* 306 */     int i = 0;
/* 307 */     while (i < nlBodies.getLength())
/*     */     {
/* 309 */       Node elBody = nlBodies.item(i);
/*     */ 
/* 312 */       if (elBody.getNodeName().equals(a_sChildElementName))
/*     */       {
/* 314 */         Node nBodyText = elBody.getFirstChild();
/*     */ 
/* 316 */         if (nBodyText != null)
/*     */         {
/* 318 */           sText = nBodyText.getNodeValue();
/*     */ 
/* 320 */           if ((sText != null) && (sText.length() > 0))
/*     */           {
/* 322 */             vChildText.add(sText);
/*     */           }
/*     */         }
/*     */       }
/* 326 */       i++;
/*     */     }
/*     */ 
/* 329 */     return vChildText;
/*     */   }
/*     */ 
/*     */   public static String transformXML(File a_xmlFile, File a_xsltFile)
/*     */     throws Bn2Exception
/*     */   {
/* 336 */     return transformXML(a_xmlFile, null, a_xsltFile);
/*     */   }
/*     */ 
/*     */   public static String transformXML(String a_sXMLString, File a_xsltFile)
/*     */     throws Bn2Exception
/*     */   {
/* 342 */     return transformXML(null, a_sXMLString, a_xsltFile);
/*     */   }
/*     */ 
/*     */   private static String transformXML(File a_xmlFile, String a_sXMLString, File a_xsltFile)
/*     */     throws Bn2Exception
/*     */   {
/* 361 */     String ksMethodName = "transformXML";
/* 362 */     String sResult = null;
/*     */     try
/*     */     {
/* 367 */       Source xmlSource = null;
/*     */ 
/* 369 */       if (a_xmlFile != null)
/*     */       {
/* 371 */         xmlSource = new StreamSource(a_xmlFile);
/*     */       }
/*     */       else
/*     */       {
/* 376 */         StringReader reader = new StringReader(a_sXMLString);
/* 377 */         xmlSource = new StreamSource(reader);
/*     */       }
/* 379 */       Source xsltSource = new StreamSource(a_xsltFile);
/*     */ 
/* 382 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 383 */       StreamResult output = new StreamResult(baos);
/*     */ 
/* 386 */       TransformerFactory factory = TransformerFactory.newInstance();
/* 387 */       Transformer transformer = factory.newTransformer(xsltSource);
/* 388 */       transformer.transform(xmlSource, output);
/*     */ 
/* 391 */       sResult = baos.toString("UTF-8");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 395 */       String sMessage = "XMLUtil.transformXML: Error transforming XML: " + e.getMessage();
/* 396 */       GlobalApplication.getInstance().getLogger().error(sMessage);
/* 397 */       throw new Bn2Exception(sMessage, e);
/*     */     }
/* 399 */     return sResult;
/*     */   }
/*     */ 
/*     */   public static String prettyFormatXmlString(String a_sXml, int a_iIndent)
/*     */   {
/*     */     try
/*     */     {
/* 415 */       Source xmlInput = new StreamSource(new StringReader(a_sXml));
/* 416 */       StringWriter stringWriter = new StringWriter();
/* 417 */       StreamResult xmlOutput = new StreamResult(stringWriter);
/* 418 */       Transformer transformer = TransformerFactory.newInstance().newTransformer();
/* 419 */       transformer.setOutputProperty("indent", "yes");
/* 420 */       transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(a_iIndent));
/* 421 */       transformer.transform(xmlInput, xmlOutput);
/* 422 */       return xmlOutput.getWriter().toString();
/*     */     }
/*     */     catch (Exception e) {
/*     */     }
/* 426 */     return a_sXml;
/*     */   }
/*     */ 
/*     */   public static Namespace parseNamespace(String a_sNamespace)
/*     */     throws ParseException
/*     */   {
/* 439 */     if (a_sNamespace == null)
/*     */     {
/* 441 */       return null;
/*     */     }
/*     */ 
/* 444 */     if (!a_sNamespace.matches("xmlns\\([a-zA-Z]+=.*\\)"))
/*     */     {
/* 446 */       throw new ParseException("Namespace declarations should be in the format \"xmlns(prefix=uri)\"", 0);
/*     */     }
/*     */ 
/* 449 */     String sPrefix = a_sNamespace.substring(6, a_sNamespace.indexOf('=', 6));
/* 450 */     String sUri = a_sNamespace.substring(a_sNamespace.indexOf('=', 6) + 1, a_sNamespace.length() - 1);
/*     */ 
/* 452 */     return Namespace.getNamespace(sPrefix, sUri);
/*     */   }
/*     */ 
/*     */   public static String getPrefix(String a_sElementName)
/*     */   {
/* 457 */     int i = a_sElementName.indexOf(':');
/*     */ 
/* 459 */     if (i > 0)
/*     */     {
/* 461 */       return a_sElementName.substring(0, i);
/*     */     }
/* 463 */     return "";
/*     */   }
/*     */ 
/*     */   public static String getLocalPart(String a_sElementName)
/*     */   {
/* 468 */     int i = a_sElementName.indexOf(':');
/*     */ 
/* 470 */     if (i > 0)
/*     */     {
/* 472 */       return a_sElementName.substring(i + 1);
/*     */     }
/* 474 */     return a_sElementName;
/*     */   }
/*     */ 
/*     */   public static XMLGregorianCalendar getXMLGregorianCalendar(Date a_dtDate)
/*     */   {
/* 479 */     Calendar cal = Calendar.getInstance();
/* 480 */     cal.setTime(a_dtDate);
/*     */ 
/* 482 */     return XMLGregorianCalendarImpl.createDateTime(cal.get(1), cal.get(2) + 1, cal.get(5), cal.get(11), cal.get(12), cal.get(13));
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.XMLUtil
 * JD-Core Version:    0.6.0
 */