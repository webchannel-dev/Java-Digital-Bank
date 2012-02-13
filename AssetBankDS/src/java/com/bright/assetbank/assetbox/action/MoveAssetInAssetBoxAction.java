/*    */ package com.bright.assetbank.assetbox.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*    */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class MoveAssetInAssetBoxAction extends BTransactionAction
/*    */ {
/* 47 */   private AssetBoxManager m_assetBoxManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 66 */     ActionForward afForward = null;
/* 67 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 70 */     int iId = getIntParameter(a_request, "id");
/*    */ 
/* 72 */     int iMoveToId = getIntParameter(a_request, "moveTo");
/*    */ 
/* 75 */     if (iMoveToId > 0)
/*    */     {
/* 77 */       this.m_assetBoxManager.moveAssetInAssetBox(a_dbTransaction, iId, userProfile.getAssetBox().getId(), false, iMoveToId);
/* 78 */       afForward = a_mapping.findForward("Success");
/* 79 */       afForward = a_mapping.findForward("Ajax");
/*    */     }
/*    */     else
/*    */     {
/* 85 */       boolean bMoveUp = new Boolean(a_request.getParameter("up")).booleanValue();
/* 86 */       this.m_assetBoxManager.moveAssetInAssetBox(a_dbTransaction, iId, userProfile.getAssetBox().getId(), bMoveUp, 0L);
/* 87 */       afForward = a_mapping.findForward("Success");
/*    */     }
/*    */ 
/* 93 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*    */   {
/* 98 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.MoveAssetInAssetBoxAction
 * JD-Core Version:    0.6.0
 */