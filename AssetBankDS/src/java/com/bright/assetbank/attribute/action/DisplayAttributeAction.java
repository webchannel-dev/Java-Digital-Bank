/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
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
/*     */ public abstract class DisplayAttributeAction extends BTransactionAction
/*     */   implements AttributeConstants
/*     */ {
/*  43 */   private AttributeManager m_attributeManager = null;
/*     */ 
/*     */   protected AttributeManager getAttributeManager() {
/*  46 */     return this.m_attributeManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/*  51 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  74 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  77 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  79 */       this.m_logger.error("This user does not have permission to view the admin pages");
/*  80 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  83 */     return performAction(a_mapping, a_form, a_request, a_response, a_dbTransaction);
/*     */   }
/*     */ 
/*     */   protected abstract ActionForward performAction(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, DBTransaction paramDBTransaction)
/*     */     throws Bn2Exception;
/*     */ 
/*     */   protected long getAttributeId(HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/* 114 */     long lId = getLongParameter(a_request, "id");
/* 115 */     if (lId <= 0L)
/*     */     {
/* 117 */       throw new Bn2Exception(getClass().getSimpleName() + " - missing parameter called " + "id");
/*     */     }
/* 119 */     return lId;
/*     */   }
/*     */ 
/*     */   protected long getGroupId(HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/* 131 */     long lGroupId = getLongParameter(a_request, "daGroupId");
/* 132 */     if (lGroupId <= 0L)
/*     */     {
/* 134 */       throw new Bn2Exception(getClass().getSimpleName() + " - missing parameter called " + "daGroupId");
/*     */     }
/* 136 */     return lGroupId;
/*     */   }
/*     */ 
/*     */   protected ActionForward getForwardWithGroupId(ActionMapping a_mapping, long lGroupId, String sKey)
/*     */   {
/* 149 */     String sQueryString = "daGroupId=" + lGroupId;
/* 150 */     ActionForward afForward = createRedirectingForward(sQueryString, a_mapping, sKey);
/* 151 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.DisplayAttributeAction
 * JD-Core Version:    0.6.0
 */