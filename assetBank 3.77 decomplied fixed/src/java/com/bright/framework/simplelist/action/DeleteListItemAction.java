/*     */ package com.bright.framework.simplelist.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
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
/*     */ public class DeleteListItemAction extends BTransactionAction
/*     */ {
/*  47 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  69 */     ActionForward afForward = null;
/*  70 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  73 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  75 */       this.m_logger.error("DeleteListItemAction.execute : User must be an admin.");
/*  76 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  80 */     String sIdentifier = a_request.getParameter("id");
/*     */ 
/*  83 */     long lLanguageId = getLongParameter(a_request, "languageId");
/*     */ 
/*  86 */     long lListId = getLongParameter(a_request, "listid");
/*     */ 
/*  88 */     if ((!StringUtil.stringIsPopulated(sIdentifier)) || (lListId <= 0L))
/*     */     {
/*  90 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  94 */     this.m_listManager.deleteListItem(a_dbTransaction, sIdentifier, lLanguageId);
/*     */ 
/*  96 */     String sQueryString = "id=" + lListId;
/*     */ 
/*  98 */     int iListOrder = getIntParameter(a_request, "listOrder");
/*  99 */     if (iListOrder > 0)
/*     */     {
/* 101 */       sQueryString = sQueryString + "&listOrder=" + iListOrder;
/*     */     }
/*     */ 
/* 104 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/* 105 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_listManager)
/*     */   {
/* 111 */     this.m_listManager = a_listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.simplelist.action.DeleteListItemAction
 * JD-Core Version:    0.6.0
 */