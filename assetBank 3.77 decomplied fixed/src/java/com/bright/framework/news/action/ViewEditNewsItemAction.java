/*     */ package com.bright.framework.news.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.news.bean.NewsItem;
/*     */ import com.bright.framework.news.form.NewsForm;
/*     */ import com.bright.framework.news.service.NewsManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewEditNewsItemAction extends BTransactionAction
/*     */ {
/*  47 */   private NewsManager m_newsManager = null;
/*  48 */   private LanguageManager m_languageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  64 */     NewsForm form = (NewsForm)a_form;
/*  65 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  68 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  70 */       this.m_logger.error("ViewEditNewsItemAction.execute : User must be an administrator.");
/*  71 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  74 */     if (!form.getHasErrors())
/*     */     {
/*  76 */       long lId = getLongParameter(a_request, "id");
/*     */ 
/*  78 */       if (lId > 0L)
/*     */       {
/*  80 */         form.setNewsItem(this.m_newsManager.getNewsItem(a_dbTransaction, lId));
/*  81 */         form.setCreatedDate(new SimpleDateFormat("dd/MM/yyyy kk:mm").format(form.getNewsItem().getCreatedDate()));
/*     */       }
/*     */       else
/*     */       {
/*  85 */         this.m_languageManager.createEmptyTranslations(a_dbTransaction, form.getNewsItem());
/*     */ 
/*  87 */         form.setCreatedDate(new SimpleDateFormat("dd/MM/yyyy kk:mm").format(new Date()));
/*     */       }
/*     */     }
/*     */ 
/*  91 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setNewsManager(NewsManager a_manager)
/*     */   {
/*  97 */     this.m_newsManager = a_manager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager a_manager)
/*     */   {
/* 103 */     this.m_languageManager = a_manager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.news.action.ViewEditNewsItemAction
 * JD-Core Version:    0.6.0
 */