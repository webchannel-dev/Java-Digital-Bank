/*     */ package com.bright.assetbank.attribute.filter.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*     */ import com.bright.assetbank.attribute.filter.bean.Filter;
/*     */ import com.bright.assetbank.attribute.filter.service.FilterManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ChangeSelectedFilterAndRedirectAction extends BTransactionAction
/*     */   implements AssetBankConstants, AttributeConstants
/*     */ {
/*  48 */   private FilterManager m_filterManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  75 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  77 */     Vector vecFilterIds = new Vector();
/*  78 */     Enumeration e = a_request.getParameterNames();
/*     */ 
/*  80 */     String sForwardParameters = "";
/*     */ 
/*  82 */     boolean bFirst = true;
/*     */ 
/*  84 */     while (e.hasMoreElements())
/*     */     {
/*  86 */       String sName = (String)e.nextElement();
/*     */ 
/*  88 */       if (sName.startsWith("filterId"))
/*     */       {
/*  90 */         long lFilterId = getLongParameter(a_request, sName);
/*     */ 
/*  92 */         if (lFilterId > 0L)
/*     */         {
/*  94 */           vecFilterIds.add(new Long(lFilterId));
/*     */         }
/*     */       }
/*  97 */       else if (!sName.contains("action"))
/*     */       {
/* 100 */         if (bFirst)
/*     */         {
/* 102 */           sForwardParameters = sForwardParameters + "?";
/*     */         }
/*     */         else
/*     */         {
/* 106 */           sForwardParameters = sForwardParameters + "&";
/*     */         }
/*     */ 
/* 109 */         sForwardParameters = sForwardParameters + sName + "=" + a_request.getParameter(sName);
/*     */ 
/* 111 */         bFirst = false;
/*     */       }
/*     */     }
/*     */ 
/* 115 */     userProfile.clearSelectedFilters();
/* 116 */     if ((vecFilterIds != null) && (vecFilterIds.size() > 0))
/*     */     {
/* 118 */       for (int i = 0; i < vecFilterIds.size(); i++)
/*     */       {
/* 120 */         long lId = ((Long)vecFilterIds.elementAt(i)).longValue();
/* 121 */         Filter filter = this.m_filterManager.getFilter(a_transaction, lId);
/* 122 */         filter.setLanguage(userProfile.getCurrentLanguage());
/* 123 */         userProfile.addSelectedFilter(filter);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 129 */     String sForwardAction = "/action/" + a_request.getParameter("action") + sForwardParameters;
/*     */ 
/* 132 */     ActionForward forward = new ActionForward(sForwardAction);
/*     */ 
/* 134 */     return forward;
/*     */   }
/*     */ 
/*     */   public void setFilterManager(FilterManager a_filterManager)
/*     */   {
/* 139 */     this.m_filterManager = a_filterManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.filter.action.ChangeSelectedFilterAndRedirectAction
 * JD-Core Version:    0.6.0
 */