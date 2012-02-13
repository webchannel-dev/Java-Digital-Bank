/*    */ package com.bright.assetbank.attribute.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.attribute.service.AttributeValueManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ReorderAttributeValuesAction extends BTransactionAction
/*    */   implements AssetBankConstants
/*    */ {
/*    */   private AttributeValueManager m_attributeValueManager;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 47 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 50 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 52 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 53 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 56 */     long lAttributeId = getLongParameter(a_request, "attributeId");
/*    */ 
/* 58 */     this.m_attributeValueManager.reorderListAttributeValues(a_dbTransaction, lAttributeId);
/*    */ 
/* 60 */     ActionForward afForward = a_mapping.findForward("Success");
/*    */ 
/* 62 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setAttributeValueManager(AttributeValueManager a_attributeValueManager)
/*    */   {
/* 67 */     this.m_attributeValueManager = a_attributeValueManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.ReorderAttributeValuesAction
 * JD-Core Version:    0.6.0
 */