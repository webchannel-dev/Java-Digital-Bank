/*     */ package com.bright.framework.search.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.constant.CommonConstants;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.search.constant.SearchConstants;
/*     */ import com.bright.framework.search.service.ManualReindexQueueManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ReindexAction extends Bn2Action
/*     */   implements CommonConstants, SearchConstants
/*     */ {
/*  45 */   protected ManualReindexQueueManager m_searchManager = null;
/*  46 */   protected CategoryManager m_categoryManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  70 */     ActionForward afForward = null;
/*     */ 
/*  72 */     this.m_logger.debug("In ReindexAction.execute method");
/*     */ 
/*  74 */     int iQuick = getIntParameter(a_request, "quick");
/*  75 */     boolean bQuick = false;
/*  76 */     if (iQuick > 0)
/*     */     {
/*  78 */       bQuick = true;
/*     */     }
/*     */ 
/*  82 */     UserProfile userprofile = UserProfile.getUserProfile(a_request.getSession());
/*  83 */     reindexSite(bQuick, userprofile.getUser().getId());
/*  84 */     afForward = a_mapping.findForward("Success");
/*     */ 
/*  86 */     return afForward;
/*     */   }
/*     */ 
/*     */   protected void reindexSite(boolean a_bQuick, long a_lUserId)
/*     */   {
/*  92 */     this.m_searchManager.queueRebuildIndex(a_bQuick, a_lUserId);
/*     */   }
/*     */ 
/*     */   public void setSearchManager(ManualReindexQueueManager a_searchManager)
/*     */   {
/* 109 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_sCategoryManager)
/*     */   {
/* 116 */     this.m_categoryManager = a_sCategoryManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.action.ReindexAction
 * JD-Core Version:    0.6.0
 */