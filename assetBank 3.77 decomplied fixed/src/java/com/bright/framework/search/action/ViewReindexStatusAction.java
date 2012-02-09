/*     */ package com.bright.framework.search.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.search.form.ReindexStatusForm;
/*     */ import com.bright.framework.search.service.ManualReindexQueueManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewReindexStatusAction extends Bn2Action
/*     */ {
/*  44 */   protected ManualReindexQueueManager m_searchManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  67 */     ActionForward afForward = null;
/*  68 */     ReindexStatusForm statusForm = (ReindexStatusForm)a_form;
/*  69 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  72 */     if (this.m_searchManager.getMessages(userProfile.getUser().getId()) != null)
/*     */     {
/*  74 */       statusForm.setMessages((Vector)this.m_searchManager.getMessages(userProfile.getUser().getId()).clone());
/*     */     }
/*     */     else
/*     */     {
/*  78 */       statusForm.setMessages(null);
/*     */     }
/*     */ 
/*  82 */     if (this.m_searchManager.isBatchInProgress(userProfile.getUser().getId()))
/*     */     {
/*  84 */       statusForm.setReindexInProgress(true);
/*     */     }
/*     */     else
/*     */     {
/*  88 */       statusForm.setReindexInProgress(false);
/*     */     }
/*     */ 
/*  92 */     afForward = a_mapping.findForward("Success");
/*     */ 
/*  94 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(ManualReindexQueueManager a_searchManager)
/*     */   {
/* 111 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.action.ViewReindexStatusAction
 * JD-Core Version:    0.6.0
 */