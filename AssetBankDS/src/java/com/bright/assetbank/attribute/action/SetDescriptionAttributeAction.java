/*    */ package com.bright.assetbank.attribute.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.application.service.FileAssetManagerImpl;
/*    */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*    */ import com.bright.assetbank.attribute.form.DisplayAttributeForm;
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
/*    */ public class SetDescriptionAttributeAction extends BTransactionAction
/*    */   implements AttributeConstants, AssetBankConstants
/*    */ {
/*    */   private static final String c_ksClassName = "SetDescriptionAttributeAction";
/* 46 */   private FileAssetManagerImpl m_fileAssetManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 73 */     ActionForward afForward = null;
/* 74 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 77 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 79 */       this.m_logger.error("SetDescriptionAttributeActionThis user does not have permission to view the admin pages");
/* 80 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 84 */     DisplayAttributeForm form = (DisplayAttributeForm)a_form;
/* 85 */     this.m_fileAssetManager.setAssetDescriptionAttribute(a_dbTransaction, form.getDescriptionAttribute());
/* 86 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 88 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setFileAssetManager(FileAssetManagerImpl a_fileAssetManager)
/*    */   {
/* 93 */     this.m_fileAssetManager = a_fileAssetManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.SetDescriptionAttributeAction
 * JD-Core Version:    0.6.0
 */