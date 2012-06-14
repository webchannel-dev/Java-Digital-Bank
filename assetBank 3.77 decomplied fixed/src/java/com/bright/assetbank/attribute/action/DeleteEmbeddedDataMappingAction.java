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
/*     */ public class DeleteEmbeddedDataMappingAction extends BTransactionAction
/*     */   implements AttributeConstants
/*     */ {
/*     */   private static final String c_ksClassName = "DeleteEmbeddedDataMappingAction";
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
/*  75 */       this.m_logger.error("DeleteEmbeddedDataMappingActionThis user does not have permission to delete a sort attribute");
/*  76 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  82 */       long lAttributeId = getLongParameter(a_request, "attributeId");
/*  83 */       long lEmbeddedDataValueId = getLongParameter(a_request, "embeddedDataValueId");
/*  84 */       long lMappingDirectionId = getLongParameter(a_request, "mappingDirectionId");
/*  85 */       long lEmbeddedDataTypeId = getLongParameter(a_request, "embeddedDataTypeId");
/*     */ 
/*  87 */       if ((lAttributeId < 0L) || (lEmbeddedDataValueId < 0L) || (lMappingDirectionId < 0L))
/*     */       {
/*  89 */         throw new Bn2Exception("DeleteEmbeddedDataMappingAction - missing parameter called");
/*     */       }
/*     */ 
/*  93 */       this.m_attributeManager.deleteEmbeddedDataMapping(a_dbTransaction, lAttributeId, lEmbeddedDataValueId, lMappingDirectionId, lEmbeddedDataTypeId);
/*  94 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */     catch (Bn2Exception bn2e)
/*     */     {
/*  98 */       this.m_logger.error("DeleteEmbeddedDataMappingAction" + bn2e.getMessage());
/*  99 */       throw bn2e;
/*     */     }
/*     */ 
/* 102 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 108 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.DeleteEmbeddedDataMappingAction
 * JD-Core Version:    0.6.0
 */