/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.bean.GroupAttributeVisibility;
/*     */ import com.bright.assetbank.user.form.GroupAttributeVisibilityForm;
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
/*     */ public class SaveGroupAttributeVisibilityAction extends BTransactionAction
/*     */ {
/*     */   private static final String c_ksClassName = "SaveGroupAttributeVisibilityAction";
/*  50 */   private ABUserManager m_userManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  77 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  79 */     GroupAttributeVisibilityForm form = (GroupAttributeVisibilityForm)a_form;
/*     */ 
/*  82 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  84 */       this.m_logger.error("SaveGroupAttributeVisibilityAction : User does not have admin permission : " + userProfile);
/*  85 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  91 */       this.m_userManager.removeGroupAttributeVisibility(a_dbTransaction, form.getGroupId());
/*     */ 
/*  94 */       Enumeration enumParams = a_request.getParameterNames();
/*     */ 
/*  96 */       while (enumParams.hasMoreElements())
/*     */       {
/*  98 */         String sParamName = (String)enumParams.nextElement();
/*     */ 
/* 101 */         if (sParamName.startsWith("visibleAttribute_"))
/*     */         {
/* 104 */           String sValue = a_request.getParameter(sParamName);
/*     */ 
/* 107 */           if ((sValue != null) && (sValue.length() > 0))
/*     */           {
/* 111 */             String sAttId = sParamName.substring("visibleAttribute_".length(), sParamName.length());
/*     */ 
/* 115 */             GroupAttributeVisibility attVisibility = new GroupAttributeVisibility();
/* 116 */             attVisibility.setAttributeId(Long.parseLong(sAttId));
/* 117 */             attVisibility.setGroupId(form.getGroupId());
/*     */ 
/* 120 */             if ((sValue != null) && (sValue.equals("true")))
/*     */             {
/* 122 */               attVisibility.setIsWriteable(true);
/*     */             }
/*     */ 
/* 126 */             this.m_userManager.addGroupAttributeVisibility(a_dbTransaction, attVisibility);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Bn2Exception bn2)
/*     */     {
/* 137 */       this.m_logger.error("SaveGroupAttributeVisibilityAction exception: " + bn2.getMessage());
/* 138 */       throw bn2;
/*     */     }
/*     */ 
/* 141 */     return createRedirectingForward(RequestUtil.getAsQueryParameter(a_request, "name") + "&" + RequestUtil.getAsQueryParameter(a_request, "page") + "&" + RequestUtil.getAsQueryParameter(a_request, "pageSize"), a_mapping, "Success");
/*     */   }
/*     */ 
/*     */   public ABUserManager getUserManager()
/*     */   {
/* 151 */     return this.m_userManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 157 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.SaveGroupAttributeVisibilityAction
 * JD-Core Version:    0.6.0
 */