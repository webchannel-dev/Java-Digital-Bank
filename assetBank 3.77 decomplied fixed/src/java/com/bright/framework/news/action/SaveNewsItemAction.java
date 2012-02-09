/*     */ package com.bright.framework.news.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.news.bean.NewsItem;
/*     */ import com.bright.framework.news.form.NewsForm;
/*     */ import com.bright.framework.news.service.NewsManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveNewsItemAction extends BTransactionAction
/*     */ {
/*  47 */   private NewsManager m_newsManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  63 */     ActionForward forward = null;
/*  64 */     NewsForm form = (NewsForm)a_form;
/*  65 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  68 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  70 */       this.m_logger.error("SaveNewsItemAction.execute : User must be an administrator.");
/*  71 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  74 */     validateFieldLengths(form, a_request);
/*  75 */     validateMandatoryFields(form, a_request);
/*     */ 
/*  77 */     if (form.getHasErrors())
/*     */     {
/*  79 */       forward = a_mapping.findForward("Failure");
/*     */     }
/*     */     else
/*     */     {
/*  83 */       NewsItem item = form.getNewsItem();
/*     */       try
/*     */       {
/*  87 */         item.setCreatedDate(new SimpleDateFormat("dd/MM/yyyy kk:mm").parse(form.getCreatedDate()));
/*     */ 
/*  89 */         this.m_newsManager.saveNewsItem(a_dbTransaction, item);
/*     */ 
/*  91 */         forward = a_mapping.findForward("Success");
/*     */       }
/*     */       catch (ParseException e)
/*     */       {
/*  95 */         form.addError("Please ensure the date is in the right format: dd/mm/yyyy hh:mm");
/*  96 */         forward = a_mapping.findForward("Failure");
/*     */       }
/*     */     }
/*     */ 
/* 100 */     return forward;
/*     */   }
/*     */ 
/*     */   public void setNewsManager(NewsManager a_manager)
/*     */   {
/* 105 */     this.m_newsManager = a_manager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.news.action.SaveNewsItemAction
 * JD-Core Version:    0.6.0
 */