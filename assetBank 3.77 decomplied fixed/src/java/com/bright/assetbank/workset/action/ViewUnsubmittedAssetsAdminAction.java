/*    */ package com.bright.assetbank.workset.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.assetbank.workset.bean.UnsubmittedAssets;
/*    */ import com.bright.assetbank.workset.bean.UnsubmittedItemOptions;
/*    */ import com.bright.assetbank.workset.form.UnsubmittedAssetsForm;
/*    */ import com.bright.assetbank.workset.service.AssetWorksetManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.language.bean.Language;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewUnsubmittedAssetsAdminAction extends BTransactionAction
/*    */ {
/* 48 */   private AssetWorksetManager m_assetWorksetManager = null;
/*    */ 
/*    */   public void setAssetWorksetManager(AssetWorksetManager a_wm) {
/* 51 */     this.m_assetWorksetManager = a_wm;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 62 */     ActionForward afForward = null;
/* 63 */     UnsubmittedAssetsForm form = (UnsubmittedAssetsForm)a_form;
/* 64 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 65 */     String sLangCode = userProfile.getCurrentLanguage().getCode();
/*    */ 
/* 68 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 70 */       this.m_logger.debug("ViewUnsubmittedAssetsAdminAction: not admin");
/* 71 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 74 */     long lUserId = form.getSelectedUserId();
/*    */ 
/* 76 */     if (lUserId > 0L)
/*    */     {
/* 78 */       UnsubmittedAssets list = this.m_assetWorksetManager.getUnsubmittedAssetsForUser(lUserId, sLangCode);
/*    */ 
/* 81 */       UnsubmittedItemOptions options = ViewUnsubmittedAssetsAction.createOptions(true, true, true);
/* 82 */       list.setSingleSubmitOptions(options);
/*    */ 
/* 85 */       form.setListAssets(list);
/*    */     }
/*    */ 
/* 89 */     Vector listUsers = this.m_assetWorksetManager.getUsersWithUnsubmittedAssets(a_dbTransaction);
/* 90 */     form.setListUsers(listUsers);
/*    */ 
/* 92 */     afForward = a_mapping.findForward("Success");
/* 93 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workset.action.ViewUnsubmittedAssetsAdminAction
 * JD-Core Version:    0.6.0
 */