/*     */ package com.bright.framework.simplelist.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.form.ListForm;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveListItemAction extends BTransactionAction
/*     */   implements FrameworkConstants
/*     */ {
/*  51 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  73 */     ActionForward afForward = null;
/*  74 */     ListForm form = (ListForm)a_form;
/*  75 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  78 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  80 */       this.m_logger.error("SaveListItemAction.execute : User must be an admin.");
/*  81 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  84 */     ListItem listItem = form.getListItem();
/*     */ 
/*  86 */     if (validateMandatoryFields(form, a_request))
/*     */     {
/*  88 */       if (!StringUtil.stringIsPopulated(listItem.getIdentifier()))
/*     */       {
/*  91 */         listItem.setIdentifier(this.m_listManager.getNextCustomIdentifier());
/*  92 */         listItem.setListItemTextTypeId(2L);
/*  93 */         listItem.setLanguage(LanguageConstants.k_defaultLanguage);
/*  94 */         listItem.setTitle(listItem.getBody());
/*  95 */         listItem.setSummary(listItem.getBody());
/*     */       }
/*     */ 
/*  99 */       this.m_listManager.saveListItem(a_dbTransaction, listItem);
/*     */ 
/* 101 */       String sQueryString = "id=" + listItem.getListId();
/*     */ 
/* 103 */       int iListOrder = getIntParameter(a_request, "listOrder");
/* 104 */       if (iListOrder > 0)
/*     */       {
/* 106 */         sQueryString = sQueryString + "&listOrder=" + iListOrder;
/*     */       }
/*     */ 
/* 109 */       afForward = getForward(a_mapping, a_request, sQueryString);
/*     */     }
/*     */     else
/*     */     {
/* 113 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 116 */     return afForward;
/*     */   }
/*     */ 
/*     */   protected ActionForward getForward(ActionMapping a_mapping, HttpServletRequest a_request, String a_sQueryString)
/*     */   {
/* 124 */     String sUrl = a_request.getParameter("url");
/*     */ 
/* 126 */     if (StringUtil.stringIsPopulated(sUrl))
/*     */     {
/* 128 */       ActionForward forward = new ActionForward(sUrl);
/* 129 */       forward.setRedirect(true);
/* 130 */       return forward;
/*     */     }
/*     */ 
/* 133 */     return createRedirectingForward(a_sQueryString, a_mapping, "Success");
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_listManager)
/*     */   {
/* 139 */     this.m_listManager = a_listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.simplelist.action.SaveListItemAction
 * JD-Core Version:    0.6.0
 */