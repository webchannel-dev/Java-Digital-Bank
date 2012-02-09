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
/*     */ public class MoveSortAttributeAction extends BTransactionAction
/*     */   implements AttributeConstants
/*     */ {
/*     */   private static final String c_ksClassName = "MoveSortAttributeAction";
/*  44 */   private AttributeManager m_attributeManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  71 */     ActionForward afForward = null;
/*  72 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  75 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  77 */       this.m_logger.error("MoveSortAttributeActionThis user does not have permission to view the admin pages");
/*  78 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  82 */     long lId = getLongParameter(a_request, "id");
/*  83 */     int iUp = getIntParameter(a_request, "up");
/*  84 */     boolean bUp = false;
/*     */ 
/*  86 */     if (iUp > 0)
/*     */     {
/*  88 */       bUp = true;
/*     */     }
/*     */ 
/*  91 */     int iSortArea = getIntParameter(a_request, "sortArea");
/*  92 */     String sQueryString = "browse=1";
/*     */ 
/*  94 */     if (iSortArea == 1L)
/*     */     {
/*  96 */       sQueryString = "";
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 102 */       this.m_attributeManager.moveSortAttribute(a_dbTransaction, lId, iSortArea, bUp);
/* 103 */       afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */     }
/*     */     catch (Bn2Exception bn2e)
/*     */     {
/* 107 */       this.m_logger.error("MoveSortAttributeAction" + bn2e.getMessage());
/* 108 */       throw bn2e;
/*     */     }
/*     */ 
/* 111 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 117 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.MoveSortAttributeAction
 * JD-Core Version:    0.6.0
 */