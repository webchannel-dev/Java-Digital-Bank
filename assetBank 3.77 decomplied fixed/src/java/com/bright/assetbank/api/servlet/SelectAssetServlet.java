/*     */ package com.bright.assetbank.api.servlet;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.api.exception.ApiException;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.search.bean.BaseSearchQuery;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import java.io.Writer;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class SelectAssetServlet extends ApiServlet
/*     */ {
/*  51 */   private static String k_sClassName = "SelectAssetServlet";
/*     */ 
/*     */   protected void performAction(HttpServletRequest a_request, Writer a_writer)
/*     */     throws ApiException
/*     */   {
/*  63 */     String sAttributeValue = a_request.getParameter("attributeValue");
/*  64 */     String sAttributeId = a_request.getParameter("attributeId");
/*  65 */     long lAttributeId = 0L;
/*     */     try
/*     */     {
/*  70 */       if ((StringUtils.isEmpty(sAttributeId)) || (StringUtils.isEmpty(sAttributeValue)))
/*     */       {
/*  72 */         throw new ApiException(k_sClassName + ": Require attribute id and attribute value");
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/*  78 */         lAttributeId = Long.parseLong(sAttributeId);
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/*  82 */         throw new ApiException(k_sClassName + ": Attribute id '" + sAttributeId + "' could not be parsed to a long.");
/*     */       }
/*     */ 
/*  86 */       AttributeManager attManager = (AttributeManager)GlobalApplication.getInstance().getComponentManager().lookup("AttributeManager");
/*  87 */       Attribute attribute = attManager.getAttribute(null, lAttributeId);
/*     */ 
/*  89 */       if (attribute == null)
/*     */       {
/*  91 */         throw new ApiException(k_sClassName + ": No attribute found with id " + sAttributeId);
/*     */       }
/*     */ 
/*  95 */       BaseSearchQuery criteria = AttributeUtil.getAttributeValueSearch(sAttributeValue, attribute);
/*  96 */       criteria.setMaxNoOfResults(1);
/*     */ 
/*  99 */       MultiLanguageSearchManager searchManager = (MultiLanguageSearchManager)GlobalApplication.getInstance().getComponentManager().lookup("SearchManager");
/* 100 */       SearchResults results = searchManager.search(criteria);
/*     */ 
/* 102 */       if (results.getNumResults() == 0)
/*     */       {
/* 104 */         a_writer.write(String.valueOf(0));
/*     */       }
/*     */       else
/*     */       {
/* 108 */         a_writer.write(String.valueOf(((LightweightAsset)results.getSearchResults().get(0)).getId()));
/*     */       }
/*     */     }
/*     */     catch (Throwable t)
/*     */     {
/* 113 */       throw new ApiException(k_sClassName + ": " + t.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected String getContentType()
/*     */   {
/* 119 */     return "text/plain";
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.api.servlet.SelectAssetServlet
 * JD-Core Version:    0.6.0
 */