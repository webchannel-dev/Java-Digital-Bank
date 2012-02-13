/*    */ package com.bright.assetbank.assetbox.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.assetbox.form.RequestAssetBoxForm;
/*    */ import com.bright.assetbank.user.service.ABUserManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class RequestAssetBoxOnCDAction extends BTransactionAction
/*    */   implements AssetBankConstants
/*    */ {
/* 48 */   private ABUserManager m_userManager = null;
/*    */ 
/*    */   public void setUserManager(ABUserManager a_userManager) {
/* 51 */     this.m_userManager = a_userManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 62 */     ActionForward afForward = null;
/* 63 */     RequestAssetBoxForm form = (RequestAssetBoxForm)a_form;
/*    */ 
/* 65 */     form.setAssetId(getLongParameter(a_request, "id"));
/*    */ 
/* 67 */     form.setAssetEntityId(getLongParameter(a_request, "typeId"));
/*    */ 
/* 70 */     Vector vec = this.m_userManager.getAllDivisions(a_dbTransaction);
/* 71 */     form.setDivisionList(vec);
/*    */ 
/* 73 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 75 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.RequestAssetBoxOnCDAction
 * JD-Core Version:    0.6.0
 */