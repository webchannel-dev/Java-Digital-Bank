/*    */ package com.bright.framework.storage.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.storage.bean.StorageDevice;
/*    */ import com.bright.framework.storage.form.StorageDeviceForm;
/*    */ import com.bright.framework.storage.service.StorageDeviceManager;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewEditStorageDeviceAction extends BaseStorageDeviceAction
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 50 */     StorageDeviceForm form = (StorageDeviceForm)a_form;
/* 51 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 54 */     if ((!userProfile.getIsAdmin()) || (AssetBankSettings.getStorageDevicesLocked()))
/*    */     {
/* 56 */       this.m_logger.error(SaveStorageDeviceAction.class.getSimpleName() + ".execute : User must be an administrator.");
/* 57 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 60 */     long lId = getLongParameter(a_request, "id");
/*    */ 
/* 62 */     StorageDevice device = getStorageDeviceManager().getDeviceFromDatabase(a_dbTransaction, lId);
/*    */ 
/* 64 */     form.setDevice(device);
/*    */ 
/* 66 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.action.ViewEditStorageDeviceAction
 * JD-Core Version:    0.6.0
 */