/*    */ package com.bright.assetbank.assetbox.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.assetbox.form.AssetBoxForm;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.image.constant.ImageConstants;
/*    */ import com.bright.framework.simplelist.bean.ListItem;
/*    */ import com.bright.framework.simplelist.service.ListManager;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewCopyAssetBoxAction extends BTransactionAction
/*    */   implements ImageConstants, AssetBankConstants
/*    */ {
/* 60 */   protected ListManager m_listManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 46 */     AssetBoxForm form = (AssetBoxForm)a_form;
/* 47 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 49 */     long lId = getLongParameter(a_request, "id");
/* 50 */     String sName = a_request.getParameter("name");
/*    */ 
/* 52 */     form.setCurrentAssetBoxId(lId);
/* 53 */     form.setName(this.m_listManager.getListItem(a_dbTransaction, "snippet-copy-of", userProfile.getCurrentLanguage()).getBody() + " " + sName);
/* 54 */     form.setPreviousName(sName);
/*    */ 
/* 56 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setListManager(ListManager listManager)
/*    */   {
/* 63 */     this.m_listManager = listManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.ViewCopyAssetBoxAction
 * JD-Core Version:    0.6.0
 */