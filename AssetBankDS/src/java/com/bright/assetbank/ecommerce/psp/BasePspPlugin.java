/*     */ package com.bright.assetbank.ecommerce.psp;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.ecommerce.bean.PspPaymentReturn;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class BasePspPlugin
/*     */ {
/*     */   public String getKeyFromReturnRequest(HttpServletRequest a_request)
/*     */   {
/*  56 */     String sTransidKey = "trans_id";
/*  57 */     String sTransactionId = a_request.getParameter(sTransidKey);
/*     */ 
/*  59 */     return sTransactionId;
/*     */   }
/*     */ 
/*     */   protected static int getIntParameter(HttpServletRequest a_dhsrRequest, String a_sParam)
/*     */   {
/*  72 */     String sId = a_dhsrRequest.getParameter(a_sParam);
/*  73 */     int iId = -1;
/*     */ 
/*  76 */     if ((sId == null) || (sId.length() == 0))
/*     */     {
/*  78 */       return -1;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  83 */       iId = Integer.parseInt(sId);
/*     */     }
/*     */     catch (NumberFormatException nfe)
/*     */     {
/*  87 */       GlobalApplication.getInstance().getLogger().error("ID format exception for parameter: " + a_sParam + " : " + nfe.toString());
/*     */     }
/*     */ 
/*  90 */     return iId;
/*     */   }
/*     */ 
/*     */   protected static long getLongParameter(HttpServletRequest a_dhsrRequest, String a_sParam)
/*     */   {
/* 109 */     String sId = a_dhsrRequest.getParameter(a_sParam);
/* 110 */     long lId = -1L;
/*     */ 
/* 113 */     if ((sId == null) || (sId.length() == 0))
/*     */     {
/* 115 */       return -1L;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 120 */       lId = Long.parseLong(sId);
/*     */     }
/*     */     catch (NumberFormatException nfe)
/*     */     {
/* 124 */       GlobalApplication.getInstance().getLogger().error("Long format exception for parameter: " + a_sParam + " : " + nfe.toString());
/*     */     }
/*     */ 
/* 127 */     return lId;
/*     */   }
/*     */ 
/*     */   protected static ActionForward createRedirectingForward(String a_sQueryString, ActionMapping a_mapping, String a_sKey)
/*     */   {
/* 138 */     ActionForward originalForward = a_mapping.findForward(a_sKey);
/* 139 */     String sPath = originalForward.getPath();
/* 140 */     if (a_sQueryString.length() > 0)
/*     */     {
/* 142 */       sPath = sPath + "?" + a_sQueryString;
/*     */     }
/*     */ 
/* 146 */     ActionForward forward = new ActionForward(sPath);
/* 147 */     forward.setRedirect(true);
/* 148 */     return forward;
/*     */   }
/*     */ 
/*     */   protected static ActionForward createRedirectingForward(String a_sPath)
/*     */   {
/* 164 */     ActionForward forward = new ActionForward(a_sPath);
/* 165 */     forward.setRedirect(true);
/* 166 */     return forward;
/*     */   }
/*     */ 
/*     */   protected static ActionForward createForward(String a_sQueryString, ActionMapping a_mapping, String a_sKey)
/*     */   {
/* 185 */     ActionForward originalForward = a_mapping.findForward(a_sKey);
/* 186 */     String sPath = originalForward.getPath();
/*     */ 
/* 188 */     if (a_sQueryString.length() > 0)
/*     */     {
/* 190 */       sPath = sPath + "?" + a_sQueryString;
/*     */     }
/*     */ 
/* 194 */     ActionForward forward = new ActionForward(sPath);
/* 195 */     return forward;
/*     */   }
/*     */ 
/*     */   protected static ActionForward createForward(String a_sPath)
/*     */   {
/* 211 */     ActionForward forward = new ActionForward(a_sPath);
/* 212 */     forward.setRedirect(false);
/* 213 */     return forward;
/*     */   }
/*     */ 
/*     */   public boolean requiresRedirectFromCallback()
/*     */   {
/* 231 */     return false;
/*     */   }
/*     */ 
/*     */   public String generateSuccessRedirect(PspPaymentReturn a_info)
/*     */   {
/* 249 */     return "";
/*     */   }
/*     */ 
/*     */   public String generateFailureRedirect(PspPaymentReturn a_info)
/*     */   {
/* 267 */     return "";
/*     */   }
/*     */ 
/*     */   public boolean registerSuccessOnCallback()
/*     */   {
/* 286 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean registerSuccessOnReturn()
/*     */   {
/* 305 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean processReturn(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */   {
/* 332 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.psp.BasePspPlugin
 * JD-Core Version:    0.6.0
 */