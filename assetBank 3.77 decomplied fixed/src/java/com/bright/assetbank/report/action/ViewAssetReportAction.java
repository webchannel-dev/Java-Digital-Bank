/*    */ package com.bright.assetbank.report.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.attribute.service.AttributeManager;
/*    */ import com.bright.assetbank.report.form.AssetReportForm;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewAssetReportAction extends BTransactionAction
/*    */   implements AssetBankConstants
/*    */ {
/* 47 */   private AttributeManager m_attributeManager = null;
/*    */ 
/*    */   public void setAttributeManager(AttributeManager a_sAttributeManager) {
/* 50 */     this.m_attributeManager = a_sAttributeManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 74 */     ActionForward afForward = null;
/* 75 */     AssetReportForm form = (AssetReportForm)a_form;
/* 76 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 79 */     if ((!userProfile.getIsAdmin()) && (!userProfile.checkForRolePermission()))
/*    */     {
/* 81 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 85 */     Vector vecKeywordPickers = this.m_attributeManager.getAttributes(a_dbTransaction, 7L);
/* 86 */     form.setKeywordPickers(vecKeywordPickers);
/* 87 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 89 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.report.action.ViewAssetReportAction
 * JD-Core Version:    0.6.0
 */