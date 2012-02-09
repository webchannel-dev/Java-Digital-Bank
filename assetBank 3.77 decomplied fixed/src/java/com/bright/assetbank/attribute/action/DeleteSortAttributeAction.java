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
/*     */ public class DeleteSortAttributeAction extends BTransactionAction
/*     */   implements AttributeConstants
/*     */ {
/*     */   private static final String c_ksClassName = "DeleteSortAttributeAction";
/*  45 */   private AttributeManager m_attributeManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  69 */     ActionForward afForward = null;
/*  70 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  73 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  75 */       this.m_logger.error("DeleteSortAttributeActionThis user does not have permission to delete a sort attribute");
/*  76 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  82 */       long lId = getLongParameter(a_request, "id");
/*     */ 
/*  84 */       if (lId < 0L)
/*     */       {
/*  86 */         throw new Bn2Exception("DeleteSortAttributeAction - missing parameter called id");
/*     */       }
/*     */ 
/*  89 */       String sQueryString = "";
/*  90 */       long lSortArea = getLongParameter(a_request, "sortArea");
/*  91 */       if (lSortArea == 2L)
/*     */       {
/*  93 */         sQueryString = "browse=1";
/*     */       }
/*     */ 
/*  97 */       this.m_attributeManager.deleteSortAttribute(a_dbTransaction, lId, lSortArea);
/*  98 */       afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */     }
/*     */     catch (Bn2Exception bn2e)
/*     */     {
/* 102 */       this.m_logger.error("DeleteSortAttributeAction" + bn2e.getMessage());
/* 103 */       throw bn2e;
/*     */     }
/*     */ 
/* 106 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 112 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.DeleteSortAttributeAction
 * JD-Core Version:    0.6.0
 */