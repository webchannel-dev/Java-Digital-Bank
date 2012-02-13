/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.form.GroupExclusionForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import java.util.Enumeration;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public abstract class SaveGroupExclusionAction extends BTransactionAction
/*     */ {
/*     */   private static final String c_ksClassName = "SaveGroupExclusionAction";
/*  48 */   protected ABUserManager m_userManager = null;
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager) {
/*  51 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  74 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  76 */     GroupExclusionForm form = (GroupExclusionForm)a_form;
/*     */ 
/*  79 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  81 */       this.m_logger.error("SaveGroupExclusionAction : User does not have admin permission : " + userProfile);
/*  82 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  86 */     removeExistingExclusions(a_dbTransaction, form.getGroupId());
/*     */ 
/*  89 */     Enumeration enumParams = a_request.getParameterNames();
/*     */ 
/*  91 */     while (enumParams.hasMoreElements())
/*     */     {
/*  93 */       String sParamName = (String)enumParams.nextElement();
/*     */ 
/*  96 */       if (sParamName.startsWith("excluded_"))
/*     */       {
/*  99 */         int iEndIndex = sParamName.length();
/* 100 */         if (complexId())
/*     */         {
/* 102 */           iEndIndex = sParamName.lastIndexOf("_");
/*     */         }
/* 104 */         String sId = sParamName.substring("excluded_".length(), iEndIndex);
/* 105 */         long lId = Long.parseLong(sId);
/* 106 */         String sValue = a_request.getParameter(sParamName);
/*     */ 
/* 109 */         saveNewExclusion(a_dbTransaction, form.getGroupId(), lId, sValue);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 114 */     runPostSaveActions();
/*     */ 
/* 116 */     return createRedirectingForward(RequestUtil.getAsQueryParameter(a_request, "name") + "&" + RequestUtil.getAsQueryParameter(a_request, "page") + "&" + RequestUtil.getAsQueryParameter(a_request, "pageSize"), a_mapping, "Success");
/*     */   }
/*     */   protected abstract void removeExistingExclusions(DBTransaction paramDBTransaction, long paramLong) throws Bn2Exception;
/*     */ 
/*     */   protected abstract void saveNewExclusion(DBTransaction paramDBTransaction, long paramLong1, long paramLong2, String paramString) throws Bn2Exception;
/*     */ 
/*     */   protected abstract void runPostSaveActions();
/*     */ 
/*     */   protected boolean complexId() {
/* 128 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.SaveGroupExclusionAction
 * JD-Core Version:    0.6.0
 */