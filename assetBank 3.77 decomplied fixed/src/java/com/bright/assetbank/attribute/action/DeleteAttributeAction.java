/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
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
/*     */ public class DeleteAttributeAction extends BTransactionAction
/*     */ {
/*     */   private static final String c_ksClassName = "DeleteAttributeAction";
/*  47 */   private AttributeManager m_attributeManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  73 */     ActionForward afForward = null;
/*  74 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  77 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  79 */       this.m_logger.error("DeleteAttributeActionThis user does not have permission to view the admin pages");
/*  80 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  86 */       long lId = getLongParameter(a_request, "attributeId");
/*     */ 
/*  88 */       if (lId < 0L)
/*     */       {
/*  90 */         throw new Bn2Exception("DeleteAttributeAction - missing parameter called attributeId");
/*     */       }
/*     */ 
/*  94 */       this.m_attributeManager.deleteAttribute(a_dbTransaction, lId);
/*     */ 
/*  96 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */     catch (Bn2Exception bn2e)
/*     */     {
/* 100 */       this.m_logger.error("DeleteAttributeAction" + bn2e.getMessage());
/* 101 */       throw bn2e;
/*     */     }
/*     */ 
/* 104 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 110 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.DeleteAttributeAction
 * JD-Core Version:    0.6.0
 */