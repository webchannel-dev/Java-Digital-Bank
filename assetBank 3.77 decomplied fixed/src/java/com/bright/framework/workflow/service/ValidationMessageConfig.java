/*     */ package com.bright.framework.workflow.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.framework.workflow.constant.WorkflowSettings;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class ValidationMessageConfig extends Bn2Manager
/*     */ {
/*     */   private static final String c_ksClassName = "ValidationMessageConfig";
/*  44 */   private static ValidationMessageConfig c_instance = null;
/*     */ 
/*  46 */   protected HashMap c_hmMessages = new HashMap(20);
/*     */ 
/*     */   public static ValidationMessageConfig getInstance()
/*     */     throws Bn2Exception
/*     */   {
/*  69 */     if (c_instance == null)
/*     */     {
/*  71 */       synchronized (ValidationMessageConfig.class)
/*     */       {
/*  73 */         if (c_instance == null)
/*     */         {
/*  75 */           c_instance = new ValidationMessageConfig();
/*  76 */           c_instance.startup();
/*     */         }
/*     */       }
/*     */     }
/*  80 */     return c_instance;
/*     */   }
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/*  94 */     super.startup();
/*     */ 
/*  97 */     String sXmlDocPath = WorkflowSettings.getApplicationPath() + 
/*  98 */       "/" + WorkflowSettings.getValidationMessagesXmlFilePath();
/*     */ 
/* 101 */     loadMessages(sXmlDocPath, this.c_hmMessages);
/*     */   }
/*     */ 
/*     */   public String getValidationMessage(String a_sName)
/*     */     throws Bn2Exception
/*     */   {
/* 118 */     String ksMethodName = "getMessage";
/*     */ 
/* 120 */     String sMessage = (String)this.c_hmMessages.get(a_sName);
/*     */ 
/* 122 */     if (sMessage == null)
/*     */     {
/* 124 */       throw new Bn2Exception("ValidationMessageConfig.getMessage: there is no message with name: " + a_sName);
/*     */     }
/*     */ 
/* 127 */     return sMessage;
/*     */   }
/*     */ 
/*     */   private static void loadMessages(String sXmlDocPath, HashMap a_hmMessages)
/*     */     throws Bn2Exception
/*     */   {
/* 147 */     String ksMethodName = "loadMessages";
/*     */     try
/*     */     {
/* 151 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/* 152 */       DocumentBuilder builder = factory.newDocumentBuilder();
/*     */ 
/* 155 */       File fXmlFile = new File(sXmlDocPath);
/* 156 */       Document xmlMessageDocument = builder.parse(fXmlFile);
/*     */ 
/* 160 */       a_hmMessages.clear();
/*     */ 
/* 162 */       Element elMessageElement = null;
/*     */ 
/* 165 */       NodeList list = xmlMessageDocument.getElementsByTagName("validation-message");
/*     */ 
/* 168 */       for (int i = 0; i < list.getLength(); i++)
/*     */       {
/* 171 */         elMessageElement = (Element)list.item(i);
/*     */ 
/* 174 */         if (elMessageElement.getAttribute("name") != null)
/*     */         {
/* 177 */           if (a_hmMessages.get(elMessageElement.getAttribute("name")) != null)
/*     */           {
/* 179 */             throw new Bn2Exception("ValidationMessageConfig.loadMessages: there are two messages with the same name in the xml file");
/*     */           }
/*     */ 
/* 183 */           String sBody = null;
/* 184 */           Node nBodyText = elMessageElement.getFirstChild();
/*     */ 
/* 186 */           if (nBodyText != null)
/*     */           {
/* 188 */             sBody = nBodyText.getNodeValue();
/*     */           }
/*     */ 
/* 192 */           a_hmMessages.put(elMessageElement.getAttribute("name"), 
/* 193 */             sBody);
/*     */         }
/*     */         else
/*     */         {
/* 197 */           throw new Bn2Exception("ValidationMessageConfig.loadMessages: encountered a validation message with no name in the xml file");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 205 */       throw new Bn2Exception("IOException in ValidationMessageConfig.loadMessages:" + ioe.getMessage());
/*     */     }
/*     */     catch (ParserConfigurationException pce)
/*     */     {
/* 209 */       throw new Bn2Exception("ParserConfigurationException in ValidationMessageConfig.loadMessages:" + pce.getMessage());
/*     */     }
/*     */     catch (SAXException saxe)
/*     */     {
/* 213 */       throw new Bn2Exception("SAXException in ValidationMessageConfig.loadMessages:" + saxe.getMessage());
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.workflow.service.ValidationMessageConfig
 * JD-Core Version:    0.6.0
 */