/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.RoleManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveGroupRolesAction extends BTransactionAction
/*     */ {
/*  46 */   private RoleManager m_roleManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  67 */     ActionForward afForward = null;
/*  68 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  71 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  73 */       this.m_logger.error("ViewGroupRolesAction.execute : User is not an admin.");
/*  74 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  78 */     long lGroupId = getLongParameter(a_request, "id");
/*     */ 
/*  81 */     Enumeration params = a_request.getParameterNames();
/*  82 */     Vector vecRoles = new Vector();
/*     */ 
/*  84 */     while (params.hasMoreElements())
/*     */     {
/*  86 */       String sName = (String)params.nextElement();
/*     */ 
/*  88 */       if (sName.startsWith("role"))
/*     */       {
/*  90 */         String sValue = a_request.getParameter(sName);
/*     */         try
/*     */         {
/*  95 */           Long longId = new Long(sValue);
/*  96 */           vecRoles.add(longId);
/*     */         }
/*     */         catch (NumberFormatException e)
/*     */         {
/* 100 */           this.m_logger.error("SaveGroupRolesAction.execute: Unable to parse role id : " + sValue);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 107 */     this.m_roleManager.saveRoles(a_dbTransaction, lGroupId, vecRoles);
/*     */ 
/* 109 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 111 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setRoleManager(RoleManager a_roleManager)
/*     */   {
/* 116 */     this.m_roleManager = a_roleManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.SaveGroupRolesAction
 * JD-Core Version:    0.6.0
 */