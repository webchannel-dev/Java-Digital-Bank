/*     */ package com.bright.assetbank.attribute.filter.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.attribute.filter.service.FilterManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class MoveFilterAction extends BTransactionAction
/*     */ {
/*     */   private static final String c_ksClassName = "MoveFilterAction";
/*  47 */   private FilterManager m_filterManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  71 */     ActionForward afForward = null;
/*  72 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  75 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  77 */       this.m_logger.error("MoveFilterActionThis user does not have permission to view the admin pages");
/*  78 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  84 */       long lId = getLongParameter(a_request, "id");
/*  85 */       long lCatId = getLongParameter(a_request, "categoryId");
/*     */ 
/*  88 */       if (lId <= 0L)
/*     */       {
/*  90 */         this.m_filterManager.fixFilterOrdering(a_dbTransaction, lCatId, true);
/*     */       }
/*     */       else
/*     */       {
/*  95 */         boolean bMoveUp = new Boolean(a_request.getParameter("up")).booleanValue();
/*     */ 
/*  97 */         this.m_filterManager.moveFilter(a_dbTransaction, lId, lCatId, bMoveUp);
/*     */       }
/*     */ 
/* 100 */       if (lCatId > 0L)
/*     */       {
/* 102 */         afForward = a_mapping.findForward("CategoryFilters");
/*     */       }
/*     */       else
/*     */       {
/* 106 */         afForward = a_mapping.findForward("Success");
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Bn2Exception bn2e)
/*     */     {
/* 112 */       this.m_logger.error("MoveFilterAction" + bn2e.getMessage());
/* 113 */       throw bn2e;
/*     */     }
/*     */ 
/* 116 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setFilterManager(FilterManager a_filterManager)
/*     */   {
/* 121 */     this.m_filterManager = a_filterManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.filter.action.MoveFilterAction
 * JD-Core Version:    0.6.0
 */