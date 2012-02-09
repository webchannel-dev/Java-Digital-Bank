/*     */ package com.bright.assetbank.api.servlet;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.api.constant.ApiConstants;
/*     */ import com.bright.assetbank.api.constant.ApiSettings;
/*     */ import com.bright.assetbank.api.exception.ApiException;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public abstract class ApiServlet extends HttpServlet
/*     */   implements ApiConstants
/*     */ {
/*     */   protected static final String k_sErrorResponse = "ERROR";
/*     */   protected static final String k_sNoPermissionResponse = "NO_PERMISSION";
/*     */ 
/*     */   protected abstract void performAction(HttpServletRequest paramHttpServletRequest, Writer paramWriter)
/*     */     throws ApiException;
/*     */ 
/*     */   private boolean checkPermissions(HttpServletRequest a_request)
/*     */   {
/*  52 */     if (!ApiSettings.getRestrictByIpAddress())
/*     */     {
/*  54 */       return true;
/*     */     }
/*     */ 
/*  58 */     String[] allowedIps = ApiSettings.getAllowedIpAddresses();
/*  59 */     String sRequestIp = a_request.getRemoteAddr();
/*     */ 
/*  61 */     for (int i = 0; i < allowedIps.length; i++)
/*     */     {
/*  63 */       if ((StringUtil.stringIsPopulated(allowedIps[i])) && (allowedIps[i].equals(sRequestIp)))
/*     */       {
/*  65 */         return true;
/*     */       }
/*     */     }
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */   protected void doGet(HttpServletRequest a_request, HttpServletResponse a_response) throws ServletException, IOException
/*     */   {
/*  73 */     a_response.setContentType(getContentType());
/*  74 */     Writer writer = a_response.getWriter();
/*     */     try
/*     */     {
/*  78 */       if (checkPermissions(a_request))
/*     */       {
/*  80 */         performAction(a_request, writer);
/*     */       }
/*     */       else
/*     */       {
/*  84 */         writer.write("NO_PERMISSION");
/*     */       }
/*     */     }
/*     */     catch (Throwable t)
/*     */     {
/*  89 */       GlobalApplication.getInstance().getLogger().error("API Error: " + t.getMessage());
/*  90 */       if (writer != null)
/*     */       {
/*  92 */         writer.write("ERROR");
/*     */       }
/*     */       else
/*     */       {
/*  96 */         a_response.sendError(500);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 101 */       if (writer != null)
/*     */       {
/* 103 */         writer.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest a_request, HttpServletResponse a_response) throws ServletException, IOException
/*     */   {
/* 110 */     doGet(a_request, a_response);
/*     */   }
/*     */ 
/*     */   protected String getContentType()
/*     */   {
/* 115 */     return "text/plain";
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.api.servlet.ApiServlet
 * JD-Core Version:    0.6.0
 */