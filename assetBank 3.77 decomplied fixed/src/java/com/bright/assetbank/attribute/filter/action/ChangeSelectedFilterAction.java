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
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ChangeSelectedFilterAction extends BTransactionAction
/*     */   implements AssetBankConstants, AttributeConstants
/*     */ {
/*  52 */   private FilterManager m_filterManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  81 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  83 */     Vector vecFilterIds = new Vector();
/*  84 */     Enumeration e = a_request.getParameterNames();
/*     */ 
/*  86 */     while (e.hasMoreElements())
/*     */     {
/*  88 */       String sName = (String)e.nextElement();
/*     */ 
/*  90 */       if (sName.startsWith("filterId"))
/*     */       {
/*  92 */         long lFilterId = getLongParameter(a_request, sName);
/*     */ 
/*  94 */         if (lFilterId > 0L)
/*     */         {
/*  96 */           vecFilterIds.add(new Long(lFilterId));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 101 */     userProfile.clearSelectedFilters();
/* 102 */     if ((vecFilterIds != null) && (vecFilterIds.size() > 0))
/*     */     {
/* 104 */       for (int i = 0; i < vecFilterIds.size(); i++)
/*     */       {
/* 106 */         long lId = ((Long)vecFilterIds.elementAt(i)).longValue();
/* 107 */         Filter filter = this.m_filterManager.getFilter(a_transaction, lId);
/* 108 */         filter.setLanguage(userProfile.getCurrentLanguage());
/* 109 */         userProfile.addSelectedFilter(filter);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 114 */     int iAdvancedSearchCheck = getIntParameter(a_request, "advancedSearch");
/*     */     ActionForward forward;
/*     */    // ActionForward forward;
/* 117 */     if (iAdvancedSearchCheck > 0)
/*     */     {
/* 120 */       forward = createRedirectingForward("/action/viewSearch");
/*     */     }
/*     */     else
/*     */     {
/* 124 */       forward = new ActionForward((String)a_request.getSession().getAttribute("lastGetRequestUri"));
/*     */     }
/*     */ 
/* 127 */     return forward;
/*     */   }
/*     */ 
/*     */   public void setFilterManager(FilterManager a_filterManager)
/*     */   {
/* 132 */     this.m_filterManager = a_filterManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.filter.action.ChangeSelectedFilterAction
 * JD-Core Version:    0.6.0
 */