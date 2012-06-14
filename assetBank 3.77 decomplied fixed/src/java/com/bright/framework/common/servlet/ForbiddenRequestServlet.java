/*    */ package com.bright.framework.common.servlet;
/*    */ 
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class ForbiddenRequestServlet extends HttpServlet
/*    */   implements AssetBankConstants
/*    */ {
/*    */   private static final long serialVersionUID = -8828715237534279182L;
/*    */ 
/*    */   public void doPost(HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws IOException, ServletException
/*    */   {
/* 44 */     doGet(a_request, a_response);
/*    */   }
/*    */ 
/*    */   public void doGet(HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws IOException, ServletException
/*    */   {
/* 54 */     a_response.sendError(403);
/* 55 */     a_response.flushBuffer();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.servlet.ForbiddenRequestServlet
 * JD-Core Version:    0.6.0
 */