/*    */ package com.bright.assetbank.assetbox.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*    */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ChangeAssetBoxSharePermissionAction extends BTransactionAction
/*    */   implements AssetBoxConstants, FrameworkConstants
/*    */ {
/* 41 */   private AssetBoxManager m_assetBoxManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 50 */     ActionForward afForward = null;
/*    */ 
/* 53 */     long lAssetBoxId = getLongParameter(a_request, "assetBoxId");
/* 54 */     long lUserId = getLongParameter(a_request, "userId");
/* 55 */     boolean bCanEdit = Boolean.parseBoolean(a_request.getParameter("canEdit"));
/* 56 */     this.m_assetBoxManager.changeAssetBoxSharePermission(a_dbTransaction, lAssetBoxId, lUserId, bCanEdit);
/*    */ 
/* 58 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 60 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*    */   {
/* 66 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.ChangeAssetBoxSharePermissionAction
 * JD-Core Version:    0.6.0
 */