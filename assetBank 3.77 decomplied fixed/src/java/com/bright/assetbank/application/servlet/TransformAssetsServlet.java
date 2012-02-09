/*     */ package com.bright.assetbank.application.servlet;
/*     */ 
/*     */ import com.bn2web.common.constant.GlobalSettings;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.util.AssetUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import com.bright.framework.util.XMLUtil;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public abstract class TransformAssetsServlet extends HttpServlet
/*     */   implements AssetBankConstants
/*     */ {
/*     */   private static final String c_ksClassName = "TransformAssetsServlet";
/*     */ 
/*     */   public void doGet(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws IOException, ServletException
/*     */   {
/*  65 */     transformAssets(a_request, a_response);
/*     */   }
/*     */ 
/*     */   public void doPost(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws IOException, ServletException
/*     */   {
/*  82 */     transformAssets(a_request, a_response);
/*     */   }
/*     */ 
/*     */   private void transformAssets(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws ServletException
/*     */   {
/*  95 */     String ksMethodName = "transformSearchResults";
/*     */ 
/*  98 */     a_response.setHeader("Pragma", "No-cache");
/*  99 */     a_response.setHeader("Cache-Control", "no-cache");
/* 100 */     a_response.setDateHeader("Expires", 1L);
/*     */ 
/* 103 */     String sXSLTFilename = a_request.getParameter("transformWith");
/* 104 */     String sReturnContentType = a_request.getParameter("contentType");
/*     */ 
/* 108 */     ArrayList alCaptionAttributeIds = AssetUtil.checkForCaptionAttributeIds(a_request);
/* 109 */     String sIncLabels = a_request.getParameter("labels");
/* 110 */     boolean bShowCaptionLabels = false;
/*     */ 
/* 112 */     if (StringUtil.stringIsPopulated(sIncLabels))
/*     */     {
/* 114 */       bShowCaptionLabels = Boolean.parseBoolean(sIncLabels);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 120 */       Vector vecAssets = getAssets(a_request);
/*     */ 
/* 122 */       if ((vecAssets != null) && (vecAssets.size() > 0) && (StringUtil.stringIsPopulated(sXSLTFilename)) && (StringUtil.stringIsPopulated(sReturnContentType)))
/*     */       {
/* 126 */         String sResultsAsXML = AssetUtil.getAssetsAsXML(vecAssets, true, alCaptionAttributeIds, bShowCaptionLabels, "..", false, true, null, null, null, a_request, -1L, -1L);
/*     */ 
/* 129 */         sXSLTFilename = AssetBankSettings.getSearchResultsTransformationDirectory() + "/" + sXSLTFilename;
/* 130 */         sXSLTFilename = GlobalSettings.getApplicationPath() + "/" + sXSLTFilename;
/* 131 */         File xsltFile = new File(sXSLTFilename);
/*     */ 
/* 134 */         String sReturn = XMLUtil.transformXML(sResultsAsXML, xsltFile);
/*     */ 
/* 136 */         a_response.setContentType(sReturnContentType);
/* 137 */         a_response.getWriter().write(sReturn);
/*     */       }
/*     */       else
/*     */       {
/* 141 */         String sMessage = "TransformAssetsServlet.transformSearchResults: Unable to transform xml - assets, xslt file or return content type hasn't been provided";
/* 142 */         GlobalApplication.getInstance().getLogger().error(sMessage);
/* 143 */         throw new ServletException(sMessage);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 148 */       String sMessage = "TransformAssetsServlet.transformSearchResults: Unable to transform xml: " + e.getMessage();
/* 149 */       GlobalApplication.getInstance().getLogger().error(sMessage);
/* 150 */       throw new ServletException(sMessage);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected abstract Vector<Asset> getAssets(HttpServletRequest paramHttpServletRequest)
/*     */     throws Bn2Exception;
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.servlet.TransformAssetsServlet
 * JD-Core Version:    0.6.0
 */