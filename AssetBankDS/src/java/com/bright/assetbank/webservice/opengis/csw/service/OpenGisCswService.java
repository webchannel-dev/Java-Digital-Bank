/*     */ package com.bright.assetbank.webservice.opengis.csw.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.opengis.exception.OpenGisServiceException;
/*     */ import com.bright.assetbank.opengis.service.OpenGisCswManager;
/*     */ import com.bright.framework.util.CollectionUtil;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import javax.xml.soap.Detail;
/*     */ import javax.xml.soap.SOAPConstants;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public class OpenGisCswService
/*     */ {
/*  51 */   private Log m_logger = GlobalApplication.getInstance().getLogger();
/*     */ 
/*  53 */   private OpenGisCswManager m_cswManager = null;
/*     */ 
/*     */   public Element[] getCapabilities(Element[] a_bodyElements)
/*     */     throws SOAPException
/*     */   {
/*  63 */     Element response = null;
/*     */ 
/*  66 */     if ((a_bodyElements != null) && (a_bodyElements.length >= 1))
/*     */     {
/*     */       try
/*     */       {
/*  70 */         response = getOpenGisCswManager().getCapabilities(a_bodyElements[0], false);
/*     */       }
/*     */       catch (Throwable t)
/*     */       {
/*  74 */         this.m_logger.error(getClass().getSimpleName() + ".getRecords() : Throwable caught whilst calling manager: ", t);
/*  75 */         response = getSoapFault(t);
/*     */       }
/*     */     }
/*     */ 
/*  79 */     return (Element[])CollectionUtil.asArrayRemoveNulls(Element.class, new Element[] { response });
/*     */   }
/*     */ 
/*     */   public Element[] describeRecord(Element[] a_bodyElements)
/*     */     throws SOAPException
/*     */   {
/*  90 */     Element response = null;
/*     */ 
/*  93 */     if ((a_bodyElements != null) && (a_bodyElements.length >= 1))
/*     */     {
/*     */       try
/*     */       {
/*  97 */         response = getOpenGisCswManager().describeRecord(a_bodyElements[0], false);
/*     */       }
/*     */       catch (Throwable t)
/*     */       {
/* 101 */         this.m_logger.error(getClass().getSimpleName() + ".getRecords() : Throwable caught whilst calling manager: ", t);
/* 102 */         response = getSoapFault(t);
/*     */       }
/*     */     }
/*     */ 
/* 106 */     return (Element[])CollectionUtil.asArrayRemoveNulls(Element.class, new Element[] { response });
/*     */   }
/*     */ 
/*     */   public Element[] getRecordById(Element[] a_bodyElements)
/*     */     throws SOAPException
/*     */   {
/* 117 */     Element response = null;
/*     */ 
/* 120 */     if ((a_bodyElements != null) && (a_bodyElements.length >= 1))
/*     */     {
/*     */       try
/*     */       {
/* 124 */         response = getOpenGisCswManager().getRecordById(a_bodyElements[0], false);
/*     */       }
/*     */       catch (Throwable t)
/*     */       {
/* 128 */         this.m_logger.error(getClass().getSimpleName() + ".getRecords() : Throwable caught whilst calling manager: ", t);
/* 129 */         response = getSoapFault(t);
/*     */       }
/*     */     }
/*     */ 
/* 133 */     return (Element[])CollectionUtil.asArrayRemoveNulls(Element.class, new Element[] { response });
/*     */   }
/*     */ 
/*     */   public Element[] getRecords(Element[] a_bodyElements)
/*     */     throws SOAPException
/*     */   {
/* 144 */     Element response = null;
/*     */ 
/* 147 */     if ((a_bodyElements != null) && (a_bodyElements.length >= 1))
/*     */     {
/*     */       try
/*     */       {
/* 151 */         response = getOpenGisCswManager().getRecords(a_bodyElements[0], false);
/*     */       }
/*     */       catch (Throwable t)
/*     */       {
/* 155 */         this.m_logger.error(getClass().getSimpleName() + ".getRecords() : Throwable caught whilst calling manager: ", t);
/* 156 */         response = getSoapFault(t);
/*     */       }
/*     */     }
/*     */ 
/* 160 */     return (Element[])CollectionUtil.asArrayRemoveNulls(Element.class, new Element[] { response });
/*     */   }
/*     */ 
/*     */   private SOAPFault getSoapFault(Throwable a_throwable)
/*     */     throws SOAPException
/*     */   {
/* 170 */     OpenGisServiceException exception = null;
/*     */ 
/* 172 */     if (!(a_throwable instanceof OpenGisServiceException))
/*     */     {
/* 176 */       exception = new OpenGisServiceException(a_throwable.getClass().getName() + " : " + a_throwable.getMessage(), a_throwable);
/* 177 */       exception.setStackTrace(a_throwable.getStackTrace());
/*     */     }
/*     */     else
/*     */     {
/* 181 */       exception = (OpenGisServiceException)a_throwable;
/*     */     }
/*     */ 
/* 186 */     SOAPFactory factory = SOAPFactory.newInstance("SOAP 1.2 Protocol");
/* 187 */     SOAPFault fault = factory.createFault("A server exception was encountered.", SOAPConstants.SOAP_RECEIVER_FAULT);
/*     */     try
/*     */     {
/* 192 */       Detail detail = fault.getDetail();
/* 193 */       if (detail == null)
/*     */       {
/* 195 */         detail = fault.addDetail();
/*     */       }
/* 197 */       exception.addExceptionReportElement(detail, false);
/*     */     }
/*     */     catch (Throwable t)
/*     */     {
/*     */       try
/*     */       {
/* 204 */         String sMsg = "An exception was encountered whilst creating the ExceptionReport! " + t.getClass().getSimpleName() + " : " + t.getLocalizedMessage();
/* 205 */         this.m_logger.error(sMsg, t);
/*     */ 
/* 207 */         fault.getDetail().addTextNode(sMsg + " ");
/*     */ 
/* 209 */         if (t.getCause() != null)
/*     */         {
/* 211 */           StringWriter sw = new StringWriter();
/* 212 */           PrintWriter pw = new PrintWriter(sw);
/* 213 */           t.printStackTrace(pw);
/* 214 */           fault.getDetail().addTextNode(sw.toString());
/*     */         }
/*     */ 
/* 217 */         fault.getDetail().addTextNode("\nThe original exception message was: " + a_throwable.getClass().getSimpleName() + " : " + a_throwable.getLocalizedMessage());
/*     */ 
/* 219 */         if (a_throwable.getCause() != null)
/*     */         {
/* 221 */           StringWriter sw = new StringWriter();
/* 222 */           PrintWriter pw = new PrintWriter(sw);
/* 223 */           a_throwable.printStackTrace(pw);
/* 224 */           fault.getDetail().addTextNode(sw.toString());
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (Throwable t2)
/*     */       {
/* 230 */         this.m_logger.error("An exception was encountered whilst creating the ExceptionReport!! " + t.getLocalizedMessage(), t);
/* 231 */         throw new SOAPException("Exception whilst creating fall-back SOAP fault!! " + t2.getLocalizedMessage(), t2);
/*     */       }
/*     */     }
/*     */ 
/* 235 */     return fault;
/*     */   }
/*     */ 
/*     */   private OpenGisCswManager getOpenGisCswManager()
/*     */     throws Bn2Exception
/*     */   {
/* 241 */     if (this.m_cswManager == null)
/*     */     {
/*     */       try
/*     */       {
/* 245 */         this.m_cswManager = ((OpenGisCswManager)GlobalApplication.getInstance().getComponentManager().lookup("OpenGisCswManager"));
/*     */       }
/*     */       catch (ComponentException e)
/*     */       {
/* 249 */         this.m_logger.error("ComponentException caught whilst getting OpenGisCswManager : " + e.getMessage());
/* 250 */         throw new Bn2Exception("ComponentException caught whilst getting OpenGisCswManager", e);
/*     */       }
/*     */     }
/* 253 */     return this.m_cswManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.webservice.opengis.csw.service.OpenGisCswService
 * JD-Core Version:    0.6.0
 */