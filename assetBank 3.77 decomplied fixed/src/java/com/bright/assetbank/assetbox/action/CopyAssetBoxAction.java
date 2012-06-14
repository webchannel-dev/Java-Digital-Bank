/*    */ package com.bright.assetbank.assetbox.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.assetbox.form.AssetBoxForm;
/*    */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.image.constant.ImageConstants;
/*    */ import com.bright.framework.simplelist.bean.ListItem;
/*    */ import com.bright.framework.simplelist.service.ListManager;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class CopyAssetBoxAction extends BTransactionAction
/*    */   implements ImageConstants, AssetBankConstants
/*    */ {
/* 39 */   private AssetBoxManager m_assetBoxManager = null;
/*    */ 
/* 86 */   protected ListManager m_listManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 48 */     AssetBoxForm form = (AssetBoxForm)a_form;
/* 49 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 52 */     if (StringUtils.isEmpty(form.getName()))
/*    */     {
/* 54 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "assetboxNameRequired", userProfile.getCurrentLanguage()).getBody());
/* 55 */       return a_mapping.findForward("Failure");
/*    */     }
/*    */ 
/* 59 */     if (!this.m_assetBoxManager.isAssetBoxNameUnique(form.getName(), userProfile))
/*    */     {
/* 61 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "assetboxNameNotUnique", userProfile.getCurrentLanguage()).getBody());
/* 62 */       return a_mapping.findForward("Failure");
/*    */     }
/*    */ 
/* 66 */     long lNewId = this.m_assetBoxManager.copyAssetBox(a_dbTransaction, form.getCurrentAssetBoxId(), userProfile.getUserId(), form.getName());
/*    */ 
/* 68 */     form.setNewAssetBoxId(lNewId);
/*    */ 
/* 71 */     this.m_assetBoxManager.refreshAssetBoxesInProfile(a_dbTransaction, userProfile);
/*    */ 
/* 74 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*    */   {
/* 83 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*    */   }
/*    */ 
/*    */   public void setListManager(ListManager listManager)
/*    */   {
/* 89 */     this.m_listManager = listManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.CopyAssetBoxAction
 * JD-Core Version:    0.6.0
 */