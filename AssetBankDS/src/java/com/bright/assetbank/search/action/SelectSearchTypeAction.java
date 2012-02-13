/*    */ package com.bright.assetbank.search.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.search.bean.SearchBuilderQuery;
/*    */ import com.bright.assetbank.search.constant.AssetBankSearchConstants;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class SelectSearchTypeAction extends Bn2Action
/*    */   implements AssetBankSearchConstants
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 47 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 48 */     ActionForward forward = null;
/*    */ 
/* 50 */     if (a_request.getParameter("searchBuilder") != null)
/*    */     {
/* 52 */       if (Boolean.parseBoolean(a_request.getParameter("searchBuilder")))
/*    */       {
/* 54 */         forward = a_mapping.findForward("SearchBuilder");
/*    */       }
/*    */       else
/*    */       {
/* 58 */         forward = a_mapping.findForward("SearchForm");
/*    */       }
/*    */     }
/* 61 */     else if (a_request.getSession().getAttribute("searchBuilder") != null)
/*    */     {
/* 63 */       if (Boolean.parseBoolean((String)a_request.getSession().getAttribute("searchBuilder")))
/*    */       {
/* 65 */         forward = a_mapping.findForward("SearchBuilder");
/*    */       }
/*    */       else
/*    */       {
/* 69 */         forward = a_mapping.findForward("SearchForm");
/*    */       }
/*    */     }
/* 72 */     else if (((userProfile.getSearchCriteria() instanceof SearchBuilderQuery)) || ((userProfile.getSearchCriteria() == null) && (AssetBankSettings.getSearchBuilderIsDefault())))
/*    */     {
/* 75 */       forward = a_mapping.findForward("SearchBuilder");
/*    */     }
/*    */     else
/*    */     {
/* 79 */       forward = a_mapping.findForward("SearchForm");
/*    */     }
/*    */ 
/* 82 */     return forward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.SelectSearchTypeAction
 * JD-Core Version:    0.6.0
 */