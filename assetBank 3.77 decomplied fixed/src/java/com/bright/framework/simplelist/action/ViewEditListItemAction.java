/*     */ package com.bright.framework.simplelist.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
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
/*     */ public class ViewEditListItemAction extends BTransactionAction
/*     */ {
/*  50 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  72 */     ListForm form = (ListForm)a_form;
/*  73 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  76 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  78 */       this.m_logger.error("SaveListItemAction.execute : User must be an admin.");
/*  79 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  83 */     String sIdentifier = a_request.getParameter("id");
/*  84 */     long lLanguageId = getLongParameter(a_request, "languageId");
/*     */ 
/*  86 */     if (StringUtil.stringIsPopulated(sIdentifier))
/*     */     {
/*  89 */       ListItem listItem = this.m_listManager.getListItem(a_dbTransaction, sIdentifier, lLanguageId, true);
/*     */ 
/*  96 */       if (listItem != null)
/*     */       {
/*  98 */         form.setListItem(listItem);
/*     */       }
/*     */       else
/*     */       {
/* 103 */         ListItem newListItem = new ListItem();
/* 104 */         newListItem.setIdentifier(sIdentifier);
/* 105 */         newListItem.setTitle("Custom help - " + sIdentifier);
/* 106 */         newListItem.setListId(2L);
/* 107 */         newListItem.setListItemTextTypeId(1L);
/* 108 */         newListItem.setCannotBeDeleted(false);
/* 109 */         newListItem.setLanguage(userProfile.getCurrentLanguage());
/*     */ 
/* 111 */         form.setListItem(newListItem);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 117 */       long lListId = getLongParameter(a_request, "listid");
/*     */ 
/* 120 */       if (lListId <= 0L)
/*     */       {
/* 122 */         return a_mapping.findForward("Failure");
/*     */       }
/*     */ 
/* 125 */       form.getListItem().setListId(lListId);
/*     */     }
/*     */ 
/* 128 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_listManager)
/*     */   {
/* 134 */     this.m_listManager = a_listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.simplelist.action.ViewEditListItemAction
 * JD-Core Version:    0.6.0
 */