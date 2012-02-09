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
/*     */ public class MoveEmbeddedDataMappingAction extends BTransactionAction
/*     */   implements AttributeConstants
/*     */ {
/*     */   private static final String c_ksClassName = "MoveEmbeddedDataMappingAction";
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
/*  77 */       this.m_logger.error("MoveEmbeddedDataMappingActionThis user does not have permission to view the admin pages");
/*  78 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  82 */     long lAttributeId = getLongParameter(a_request, "attributeId");
/*  83 */     long lEmbeddedDataValueId = getLongParameter(a_request, "embeddedDataValueId");
/*  84 */     long lMappingDirectionId = getLongParameter(a_request, "mappingDirectionId");
/*  85 */     int iUp = getIntParameter(a_request, "up");
/*  86 */     boolean bUp = false;
/*     */ 
/*  88 */     if (iUp > 0)
/*     */     {
/*  90 */       bUp = true;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  96 */       this.m_attributeManager.moveEmbeddedDataMapping(a_dbTransaction, lAttributeId, lEmbeddedDataValueId, lMappingDirectionId, bUp);
/*  97 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */     catch (Bn2Exception bn2e)
/*     */     {
/* 101 */       this.m_logger.error("MoveEmbeddedDataMappingAction" + bn2e.getMessage());
/* 102 */       throw bn2e;
/*     */     }
/*     */ 
/* 105 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 111 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.MoveEmbeddedDataMappingAction
 * JD-Core Version:    0.6.0
 */