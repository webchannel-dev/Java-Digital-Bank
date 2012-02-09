/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.form.GroupAttributeVisibilityForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
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
/*     */ public class ViewGroupAttributeVisibilityAction extends BTransactionAction
/*     */ {
/*     */   private static final String c_ksClassName = "ViewGroupVisibilityAction";
/*  45 */   private IAssetManager m_assetManager = null;
/*  46 */   private ABUserManager m_userManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  71 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  73 */     GroupAttributeVisibilityForm form = (GroupAttributeVisibilityForm)a_form;
/*     */ 
/*  76 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  78 */       this.m_logger.error("ViewGroupVisibilityAction : User does not have admin permission : " + userProfile);
/*  79 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  84 */       long lGroupId = getLongParameter(a_request, "id");
/*     */ 
/*  86 */       if (lGroupId <= 0L)
/*     */       {
/*  88 */         throw new Bn2Exception("ViewGroupVisibilityAction : no group id passed");
/*     */       }
/*     */ 
/*  92 */       form.setAttributeList(this.m_assetManager.getAssetAttributes(a_dbTransaction, null));
/*     */ 
/*  95 */       form.setVisibilityList(this.m_userManager.getGroupAttributeVisibilityList(a_dbTransaction, lGroupId));
/*     */ 
/*  97 */       form.setGroupId(lGroupId);
/*     */     }
/*     */     catch (Bn2Exception bn2)
/*     */     {
/* 101 */       this.m_logger.error("ViewGroupVisibilityAction exception: " + bn2.getMessage());
/* 102 */       throw bn2;
/*     */     }
/*     */ 
/* 105 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public ABUserManager getUserManager()
/*     */   {
/* 111 */     return this.m_userManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 117 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public IAssetManager getAssetManager()
/*     */   {
/* 123 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 129 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ViewGroupAttributeVisibilityAction
 * JD-Core Version:    0.6.0
 */