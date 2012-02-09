/*     */ package com.bright.assetbank.converter;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Vector;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipException;
/*     */ import java.util.zip.ZipFile;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class DOCXToTextConverter
/*     */   implements AssetToTextConverter
/*     */ {
/*     */   public String getText(String a_sFullPath)
/*     */     throws Bn2Exception
/*     */   {
/*  68 */     Vector<String> paragraphs = new Vector();
/*     */     try
/*     */     {
/*  72 */       ZipFile docxFile = new ZipFile(new File(a_sFullPath));
/*  73 */       ZipEntry documentXML = docxFile.getEntry("word/document.xml");
/*  74 */       InputStream documentXMLIS = docxFile.getInputStream(documentXML);
/*  75 */       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/*  76 */       Document doc = dbf.newDocumentBuilder().parse(documentXMLIS);
/*  77 */       Element tElement = doc.getDocumentElement();
/*  78 */       NodeList n = tElement.getElementsByTagName("w:t");
/*     */ 
/*  80 */       for (int j = 0; j < n.getLength(); j++)
/*     */       {
/*  82 */         Node child = n.item(j);
/*  83 */         String currentLine = child.getTextContent();
/*  84 */         currentLine = currentLine.replaceAll("\\cM?r?rnt", "");
/*  85 */         currentLine = currentLine.trim();
/*     */ 
/*  89 */         if ((currentLine == null) || (StringUtils.isEmpty(currentLine)) || (currentLine == " "))
/*     */           continue;
/*  91 */         paragraphs.add(currentLine);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (ZipException e)
/*     */     {
/*  99 */       throw new Bn2Exception("ZipException in DOCXToTextConcerter", e);
/*     */     }
/*     */     catch (DOMException e)
/*     */     {
/* 103 */       throw new Bn2Exception("ZipException in DOCXToTextConcerter", e);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 107 */       throw new Bn2Exception("ZipException in DOCXToTextConcerter", e);
/*     */     }
/*     */     catch (SAXException e)
/*     */     {
/* 111 */       throw new Bn2Exception("ZipException in DOCXToTextConcerter", e);
/*     */     }
/*     */     catch (ParserConfigurationException e)
/*     */     {
/* 115 */       throw new Bn2Exception("ZipException in DOCXToTextConcerter", e);
/*     */     }
/*     */ 
/* 120 */     StringBuilder sReturn = new StringBuilder();
/* 121 */     for (String sLine : paragraphs)
/*     */     {
/* 123 */       sReturn.append(sLine);
/* 124 */       sReturn.append("\n");
/*     */     }
/*     */ 
/* 127 */     GlobalApplication.getInstance().getLogger().debug("DOCXToTextConverter: Text from DOCX file: " + sReturn.toString());
/*     */ 
/* 129 */     return sReturn.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.converter.DOCXToTextConverter
 * JD-Core Version:    0.6.0
 */