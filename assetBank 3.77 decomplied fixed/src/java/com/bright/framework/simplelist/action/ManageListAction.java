/*     */ package com.bright.framework.simplelist.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.form.ListForm;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ManageListAction extends BTransactionAction
/*     */ {
/*  45 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  67 */     ActionForward afForward = null;
/*  68 */     ListForm form = (ListForm)a_form;
/*  69 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  72 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  74 */       this.m_logger.error("SaveListItemAction.execute : User must be an admin.");
/*  75 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  79 */     long lListId = getLongParameter(a_request, "id");
/*     */ 
/*  82 */     int lListOrder = getIntParameter(a_request, "listOrder");
/*     */ 
/*  84 */     if (lListId > 0L)
/*     */     {
/*  87 */       form.setList(this.m_listManager.getList(a_dbTransaction, lListId, null, true, lListOrder));
/*  88 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */     else
/*     */     {
/*  92 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  95 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_listManager)
/*     */   {
/* 101 */     this.m_listManager = a_listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.simplelist.action.ManageListAction
 * JD-Core Version:    0.6.0
 */