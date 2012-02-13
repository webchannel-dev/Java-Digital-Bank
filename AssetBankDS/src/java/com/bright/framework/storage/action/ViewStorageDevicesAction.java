/*    */ package com.bright.framework.storage.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.database.bean.DBTransaction;
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
/*    */ public class ViewStorageDevicesAction extends BaseStorageDeviceAction
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 47 */     StorageDeviceForm form = (StorageDeviceForm)a_form;
/* 48 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 51 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 53 */       this.m_logger.error(ViewStorageDevicesAction.class.getSimpleName() + ".execute : User must be an administrator.");
/* 54 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 58 */     form.setDevices(getStorageDeviceManager().getAllDevices(a_dbTransaction));
/*    */ 
/* 60 */     form.setFactories(getStorageDeviceManager().getFactories());
/*    */ 
/* 62 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.action.ViewStorageDevicesAction
 * JD-Core Version:    0.6.0
 */