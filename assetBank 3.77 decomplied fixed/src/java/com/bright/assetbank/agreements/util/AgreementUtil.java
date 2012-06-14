/*     */ package com.bright.assetbank.agreements.util;
/*     */ 
/*     */ import com.bright.assetbank.agreements.bean.Agreement;
/*     */ import com.bright.framework.common.bean.BrightDate;
/*     */ import com.bright.framework.util.DateUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringEscapeUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.jdom.CDATA;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.JDOMException;
/*     */ import org.jdom.input.SAXBuilder;
/*     */ import org.jdom.output.Format;
/*     */ import org.jdom.output.XMLOutputter;
/*     */ 
/*     */ public class AgreementUtil
/*     */ {
/*     */   private static final String k_sAttribute_SharedWithOU = "sharedWithOU";
/*     */   private static final String k_sAttribute_AvailableToAll = "availableToAll";
/*     */   private static final String k_sAttribute_Activated = "activated";
/*     */   private static final String k_sAttribute_Expiry = "expiry";
/*     */   private static final String k_sAttribute_Title = "title";
/*     */   private static final String k_sAttribute_Id = "id";
/*     */   private static final String k_sElement_Body = "body";
/*     */   private static final String k_sElement_Agreement = "agreement";
/*     */   private static final String k_sElement_Agreements = "agreements";
/*  59 */   private static final DateFormat k_dateFormat = DateUtil.getAgreementDateFormat();
/*     */ 
/*     */   public static String getAgreementsAsXml(List<Agreement> a_agreements)
/*     */   {
/*  71 */     if (a_agreements == null)
/*     */     {
/*  73 */       return null;
/*     */     }
/*     */ 
/*  76 */     Element agreements = new Element("agreements");
/*     */ 
/*  78 */     for (Agreement agreement : a_agreements)
/*     */     {
/*  80 */       agreements.addContent(toXml(agreement));
/*     */     }
/*     */ 
/*  83 */     Document doc = new Document(agreements);
/*  84 */     XMLOutputter xmlOut = new XMLOutputter(Format.getCompactFormat());
/*  85 */     return xmlOut.outputString(doc);
/*     */   }
/*     */ 
/*     */   private static Element toXml(Agreement a_agreement)
/*     */   {
/*  97 */     Element agreement = new Element("agreement");
/*  98 */     if (a_agreement.getId() > 0L)
/*     */     {
/* 100 */       agreement.setAttribute("id", String.valueOf(a_agreement.getId()));
/*     */     }
/* 102 */     if (a_agreement.getTitle() != null)
/*     */     {
/* 104 */       agreement.setAttribute("title", StringEscapeUtils.escapeXml(a_agreement.getTitle()));
/*     */     }
/* 106 */     if ((a_agreement.getExpiry() != null) && (a_agreement.getExpiry().getDate() != null))
/*     */     {
/* 108 */       agreement.setAttribute("expiry", k_dateFormat.format(a_agreement.getExpiry().getDate()));
/*     */     }
/* 110 */     if (a_agreement.getDateActivated() != null)
/*     */     {
/* 112 */       agreement.setAttribute("activated", k_dateFormat.format(a_agreement.getDateActivated()));
/*     */     }
/* 114 */     agreement.setAttribute("availableToAll", String.valueOf(a_agreement.getIsAvailableToAll()));
/* 115 */     agreement.setAttribute("sharedWithOU", String.valueOf(a_agreement.getIsSharedWithOU()));
/* 116 */     if (a_agreement.getBody() != null)
/*     */     {
/* 118 */       Element body = new Element("body");
/* 119 */       body.setContent(new CDATA(a_agreement.getBody()));
/* 120 */       agreement.addContent(body);
/*     */     }
/*     */ 
/* 123 */     return agreement;
/*     */   }
/*     */   public static List<Agreement> getAgreementsFromXml(String a_sXml) {
/* 136 */     List agreements = new ArrayList();
/*     */ 
/* 138 */     SAXBuilder builder = new SAXBuilder();
/*     */     Document doc;
/*     */     try {
/* 142 */       doc = builder.build(new StringReader(a_sXml));
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 146 */       throw new IllegalArgumentException("Input is not valid", e);
/*     */     }
/*     */     catch (JDOMException e)
/*     */     {
/* 150 */       throw new IllegalArgumentException("Input cannot be parsed using SAXBuilder.build", e);
/*     */     }
/*     */     List<Element> eb = doc.getRootElement().getChildren("agreement");
/* 153 */     for (Element el : eb)
/*     */     {
/* 155 */       Agreement agreement = new Agreement();
/*     */ 
/* 157 */       if (StringUtils.isNotEmpty(el.getAttributeValue("id")))
/*     */       {
/*     */         try
/*     */         {
/* 161 */           agreement.setId(Long.parseLong(el.getAttributeValue("id")));
/*     */         }
/*     */         catch (NumberFormatException e)
/*     */         {
/* 165 */           throw new IllegalArgumentException("Agreements xml is not valid: id '" + el.getAttributeValue("id") + "' cannot be parsed to a long.", e);
/*     */         }
/*     */       }
/* 168 */       if (StringUtils.isNotEmpty(el.getAttributeValue("title")))
/*     */       {
/* 170 */         agreement.setTitle(StringEscapeUtils.unescapeXml(el.getAttributeValue("title")));
/*     */       }
/* 172 */       if (StringUtils.isNotEmpty(el.getAttributeValue("expiry")))
/*     */       {
/* 174 */         String sExpiry = el.getAttributeValue("expiry");
/*     */         try
/*     */         {
/* 178 */           Date dtExpiry = k_dateFormat.parse(sExpiry);
/* 179 */           agreement.setExpiry(new BrightDate(dtExpiry));
/*     */         }
/*     */         catch (ParseException e)
/*     */         {
/* 183 */           throw new IllegalArgumentException("Agreements xml is not valid: expiry date '" + sExpiry + "' cannot be parsed using format " + "dd/MM/yyyy HH:mm:ss", e);
/*     */         }
/*     */       }
/* 186 */       if (StringUtils.isNotEmpty(el.getAttributeValue("activated")))
/*     */       {
/* 188 */         String sActivated = el.getAttributeValue("activated");
/*     */         try
/*     */         {
/* 192 */           agreement.setDateActivated(k_dateFormat.parse(sActivated));
/*     */         }
/*     */         catch (ParseException e)
/*     */         {
/* 196 */           throw new IllegalArgumentException("Agreements xml is not valid: activated date '" + sActivated + "' cannot be parsed using format " + "dd/MM/yyyy HH:mm:ss", e);
/*     */         }
/*     */       }
/* 199 */       if (StringUtils.isNotEmpty(el.getAttributeValue("availableToAll")))
/*     */       {
/* 201 */         agreement.setIsAvailableToAll(Boolean.parseBoolean(el.getAttributeValue("availableToAll")));
/*     */       }
/* 203 */       if (StringUtils.isNotEmpty(el.getAttributeValue("sharedWithOU")))
/*     */       {
/* 205 */         agreement.setIsAvailableToAll(Boolean.parseBoolean(el.getAttributeValue("sharedWithOU")));
/*     */       }
/*     */ 
/* 208 */       String sBody = el.getChildText("body");
/* 209 */       if (StringUtils.isNotEmpty(sBody))
/*     */       {
/* 211 */         agreement.setBody(sBody);
/*     */       }
/*     */ 
/* 214 */       agreements.add(agreement);
/*     */     }
/*     */ 
/* 217 */     return agreements;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.agreements.util.AgreementUtil
 * JD-Core Version:    0.6.0
 */