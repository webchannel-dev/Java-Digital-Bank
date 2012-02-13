/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.form.GroupAttributeExclusionForm;
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
/*     */ public class ViewGroupAttributeExclusionAction extends BTransactionAction
/*     */ {
/*     */   private static final String c_ksClassName = "ViewGroupAttributeExclusionAction";
/*  46 */   private IAssetManager m_assetManager = null;
/*  47 */   private ABUserManager m_userManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  72 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  74 */     GroupAttributeExclusionForm form = (GroupAttributeExclusionForm)a_form;
/*     */ 
/*  77 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  79 */       this.m_logger.error("ViewGroupAttributeExclusionAction : User does not have admin permission : " + userProfile);
/*  80 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  85 */       long lGroupId = getLongParameter(a_request, "id");
/*     */ 
/*  87 */       if (lGroupId <= 0L)
/*     */       {
/*  89 */         throw new Bn2Exception("ViewGroupAttributeExclusionAction : no group id passed");
/*     */       }
/*     */ 
/*  93 */       form.setAttributeList(this.m_assetManager.getAssetAttributes(a_dbTransaction, null));
/*     */ 
/*  96 */       form.setExcludedList(this.m_userManager.getAttributeExclusionsForGroup(a_dbTransaction, lGroupId));
/*     */ 
/*  98 */       form.setGroupId(lGroupId);
/*     */     }
/*     */     catch (Bn2Exception bn2)
/*     */     {
/* 102 */       this.m_logger.error("ViewGroupAttributeExclusionAction exception: " + bn2.getMessage());
/* 103 */       throw bn2;
/*     */     }
/*     */ 
/* 106 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public ABUserManager getUserManager()
/*     */   {
/* 112 */     return this.m_userManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 118 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public IAssetManager getAssetManager()
/*     */   {
/* 124 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 130 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ViewGroupAttributeExclusionAction
 * JD-Core Version:    0.6.0
 */