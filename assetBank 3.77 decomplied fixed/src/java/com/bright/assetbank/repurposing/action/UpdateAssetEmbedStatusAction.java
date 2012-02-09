/*    */ package com.bright.assetbank.repurposing.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.repurposing.service.AssetRepurposingManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class UpdateAssetEmbedStatusAction extends BTransactionAction
/*    */   implements AssetBankConstants, FrameworkConstants
/*    */ {
/*    */   private AssetRepurposingManager m_assetRepurposingManager;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 50 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 53 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 55 */       this.m_logger.error("AddUserAction.execute : User does not have admin permission : " + userProfile);
/* 56 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 59 */     long lId = getLongParameter(a_request, "id");
/* 60 */     boolean bCanEmbed = Boolean.parseBoolean(a_request.getParameter("embed"));
/*    */ 
/* 62 */     this.m_assetRepurposingManager.updateAssetEmbedStatus(a_dbTransaction, lId, bCanEmbed);
/*    */ 
/* 64 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setAssetRepurposingManager(AssetRepurposingManager assetRepurposingManager)
/*    */   {
/* 69 */     this.m_assetRepurposingManager = assetRepurposingManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.action.UpdateAssetEmbedStatusAction
 * JD-Core Version:    0.6.0
 */