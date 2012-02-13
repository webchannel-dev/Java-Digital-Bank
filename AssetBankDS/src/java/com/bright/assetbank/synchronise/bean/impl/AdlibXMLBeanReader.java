/*     */ package com.bright.assetbank.synchronise.bean.impl;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.synchronise.bean.XMLBeanReader;
/*     */ import com.bright.assetbank.synchronise.bean.XMLImportAsset;
/*     */ import com.bright.assetbank.synchronise.constant.ExternalDataSynchronisationSettings;
/*     */ import com.bright.assetbank.synchronise.constant.impl.AdlibSynchronisationSettings;
/*     */ import com.bright.assetbank.synchronise.util.impl.AdlibSynchUtil;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.input.SAXBuilder;
/*     */ 
/*     */ public class AdlibXMLBeanReader extends XMLBeanReader
/*     */   implements FrameworkConstants, AssetBankConstants
/*     */ {
/*  59 */   private HashMap m_hmIdToCallbackXMLMapping = null;
/*     */ 
/*     */   public AdlibXMLBeanReader(String a_sXMLUrl, HashMap a_hmFileToIdMapping, ExternalDataSynchronisationSettings a_settings, boolean a_bAssetsApproved)
/*     */   {
/*  63 */     super(a_sXMLUrl, a_hmFileToIdMapping, a_settings, a_bAssetsApproved);
/*     */   }
/*     */ 
/*     */   protected String getFileName(Element a_recordElement)
/*     */   {
/*  69 */     return AdlibSynchUtil.getAdlibReproductionValue(a_recordElement, ((AdlibSynchronisationSettings)this.m_settings).getXMLElementNameReproductionUrl(), (AdlibSynchronisationSettings)this.m_settings);
/*     */   }
/*     */ 
/*     */   protected void addExtraAttributes(XMLImportAsset a_asset, Element a_recordElement)
/*     */   {
/*  77 */     String sFormat = AdlibSynchUtil.getAdlibReproductionValue(a_recordElement, ((AdlibSynchronisationSettings)this.m_settings).getXMLElementNameReproductionFormat(), (AdlibSynchronisationSettings)this.m_settings);
/*  78 */     String sCreator = AdlibSynchUtil.getAdlibReproductionValue(a_recordElement, ((AdlibSynchronisationSettings)this.m_settings).getXMLElementNameReproductionCreator(), (AdlibSynchronisationSettings)this.m_settings);
/*     */ 
/*  80 */     if (StringUtil.stringIsPopulated(sFormat))
/*     */     {
/*     */       try
/*     */       {
/*  84 */         Attribute attFormat = this.m_attManager.getAttribute(null, ((AdlibSynchronisationSettings)this.m_settings).getAttributeFormat());
/*     */ 
/*  86 */         if (attFormat != null)
/*     */         {
/*  88 */           AttributeValue attValFormat = new AttributeValue();
/*  89 */           attValFormat.setAttribute(attFormat);
/*  90 */           attValFormat.setValue(sFormat);
/*  91 */           a_asset.addAttributeValue(attValFormat);
/*     */         }
/*     */       }
/*     */       catch (Bn2Exception e)
/*     */       {
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 100 */     if (StringUtil.stringIsPopulated(sCreator))
/*     */     {
/*     */       try
/*     */       {
/* 104 */         Attribute attCreator = this.m_attManager.getAttribute(null, ((AdlibSynchronisationSettings)this.m_settings).getAttributeCreator());
/*     */ 
/* 106 */         if (attCreator != null)
/*     */         {
/* 108 */           AttributeValue attValCreator = new AttributeValue();
/* 109 */           attValCreator.setAttribute(attCreator);
/* 110 */           attValCreator.setValue(sCreator);
/* 111 */           a_asset.addAttributeValue(attValCreator);
/*     */         }
/*     */       }
/*     */       catch (Bn2Exception e)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void prepareForCallback(XMLImportAsset a_asset, Element a_recordElement)
/*     */   {
/* 124 */     String sCallbackXML = "<" + this.m_sRecordElementName + ">";
/*     */ 
/* 127 */     sCallbackXML = sCallbackXML + "<" + ((AdlibSynchronisationSettings)this.m_settings).getXMLElementNamePriref() + ">" + a_recordElement.getChildText(((AdlibSynchronisationSettings)this.m_settings).getXMLElementNamePriref()) + "</" + ((AdlibSynchronisationSettings)this.m_settings).getXMLElementNamePriref() + ">";
/*     */ 
/* 130 */     sCallbackXML = sCallbackXML + "<" + this.m_sAssetBankIdElementName + ">" + a_asset.getId() + "</" + this.m_sAssetBankIdElementName + ">";
/*     */ 
/* 133 */     String sUrl = AssetBankSettings.getApplicationUrl() + "/action/" + "viewAsset" + "?" + "id" + "=" + a_asset.getId();
/* 134 */     sCallbackXML = sCallbackXML + "<" + ((AdlibSynchronisationSettings)this.m_settings).getXMLElementNameAssetBankUrl() + ">" + sUrl + "</" + ((AdlibSynchronisationSettings)this.m_settings).getXMLElementNameAssetBankUrl() + ">";
/*     */ 
/* 136 */     if (this.m_hmIdToCallbackXMLMapping == null)
/*     */     {
/* 138 */       this.m_hmIdToCallbackXMLMapping = new HashMap();
/*     */     }
/* 140 */     this.m_hmIdToCallbackXMLMapping.put(new Long(a_asset.getId()), sCallbackXML);
/*     */   }
/*     */ 
/*     */   protected String getCallbackXMLForId(long a_lId, String a_sXMLExtraElements)
/*     */     throws Bn2Exception
/*     */   {
/* 158 */     if (this.m_hmIdToCallbackXMLMapping == null)
/*     */     {
/* 160 */       parseXML();
/*     */     }
/*     */ 
/* 163 */     if (this.m_hmIdToCallbackXMLMapping.get(new Long(a_lId)) != null)
/*     */     {
/* 165 */       String sCallback = (String)this.m_hmIdToCallbackXMLMapping.get(new Long(a_lId));
/*     */ 
/* 167 */       if (StringUtil.stringIsPopulated(a_sXMLExtraElements))
/*     */       {
/* 169 */         sCallback = sCallback + a_sXMLExtraElements;
/*     */       }
/*     */ 
/* 173 */       sCallback = sCallback + "</" + this.m_sRecordElementName + ">";
/*     */ 
/* 175 */       return sCallback;
/*     */     }
/*     */ 
/* 178 */     return null;
/*     */   }
/*     */ 
/*     */   protected HashMap getPrirefHash()
/*     */     throws Bn2Exception
/*     */   {
/* 192 */     HashMap hmPrirefHash = new HashMap();
/*     */     try
/*     */     {
/* 197 */       if (this.m_vecAssets == null)
/*     */       {
/* 199 */         parseXML();
/*     */       }
/*     */ 
/* 203 */       InputStream is = new URL(((AdlibSynchronisationSettings)this.m_settings).getAllRecordsXMLUrl()).openStream();
/* 204 */       Reader reader = new InputStreamReader(is, ((AdlibSynchronisationSettings)this.m_settings).getFeedEncoding());
/*     */ 
/* 206 */       SAXBuilder parser = new SAXBuilder();
/* 207 */       Document doc = parser.build(reader);
/* 208 */       Element rootElement = doc.getRootElement();
/* 209 */       Element recordsElement = rootElement.getChild(this.m_sRecordsElementName);
/*     */ 
/* 211 */       List recordList = recordsElement.getChildren(this.m_sRecordElementName);
/* 212 */       Iterator recordIterator = recordList.iterator();
/*     */ 
/* 214 */       while (recordIterator.hasNext())
/*     */       {
/* 216 */         Element tempElement = (Element)recordIterator.next();
/*     */ 
/* 218 */         if (tempElement != null)
/*     */         {
/* 221 */           String sAssetBankId = tempElement.getChildText(this.m_sAssetBankIdElementName);
/*     */ 
/* 223 */           if (StringUtil.stringIsPopulated(sAssetBankId))
/*     */           {
/* 225 */             long lAssetBankId = 0L;
/*     */             try
/*     */             {
/* 228 */               lAssetBankId = Long.parseLong(sAssetBankId);
/*     */             }
/*     */             catch (Exception e)
/*     */             {
/*     */             }
/*     */ 
/* 235 */             if (lAssetBankId > 0L)
/*     */             {
/* 238 */               String sPriref = tempElement.getChildText(((AdlibSynchronisationSettings)this.m_settings).getXMLElementNamePriref());
/* 239 */               GlobalApplication.getInstance().getLogger().debug("AdlibXMLBeanReader.getPrirefHash: About to add priref " + sPriref + " for asset bank asset " + lAssetBankId);
/* 240 */               if (StringUtil.stringIsPopulated(sPriref))
/*     */               {
/* 242 */                 hmPrirefHash.put(new Long(lAssetBankId), sPriref);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 251 */       throw new Bn2Exception("AdlibXMLBeanReader.getPrirefHash: Error get priref map : " + e.getMessage(), e);
/*     */     }
/* 253 */     return hmPrirefHash;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.impl.AdlibXMLBeanReader
 * JD-Core Version:    0.6.0
 */