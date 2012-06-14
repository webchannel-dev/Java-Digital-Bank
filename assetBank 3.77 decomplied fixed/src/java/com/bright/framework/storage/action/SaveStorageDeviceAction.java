/*     */ package com.bright.framework.storage.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.storage.bean.StorageDevice;
/*     */ import com.bright.framework.storage.form.StorageDeviceForm;
/*     */ import com.bright.framework.storage.service.StorageDeviceManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveStorageDeviceAction extends BaseStorageDeviceAction
/*     */ {
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  55 */     StorageDeviceForm form = (StorageDeviceForm)a_form;
/*  56 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  59 */     if ((!userProfile.getIsAdmin()) || (AssetBankSettings.getStorageDevicesLocked()))
/*     */     {
/*  61 */       this.m_logger.error(DeleteStorageDeviceAction.class.getSimpleName() + ".execute : User must be an administrator.");
/*  62 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  66 */     form.getErrors().clear();
/*     */ 
/*  68 */     validateMandatoryFields(form, a_request);
/*     */ 
/*  70 */     StorageDevice device = form.getDevice();
/*     */ 
/*  73 */     if (StringUtils.isNotEmpty(device.getSubPath()))
/*     */     {
/*  75 */       if ((device.getSubPath().endsWith("\\")) || (device.getSubPath().endsWith("/")))
/*     */       {
/*  77 */         device.setSubPath(device.getSubPath().substring(0, device.getSubPath().length() - 1));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  82 */     List messages = getStorageDeviceManager().validateDevice(device);
/*  83 */     if (!messages.isEmpty())
/*     */     {
/*  85 */       form.getErrors().addAll(messages);
/*     */     }
/*     */     else
/*     */     {
/*  90 */       messages = getStorageDeviceManager().validateOverallStorage(a_dbTransaction, device);
/*  91 */       form.getErrors().addAll(messages);
/*     */     }
/*     */ 
/*  94 */     if (!form.getHasErrors())
/*     */     {
/*  97 */       String sPath = device.getLocalBasePath();
/*     */ 
/*  99 */       if (device.isLocalBasePathRelative())
/*     */       {
/* 101 */         sPath = FrameworkSettings.getApplicationPath() + "/" + sPath;
/*     */       }
/*     */ 
/* 104 */       File testFile = new File(sPath);
/* 105 */       if (!testFile.isDirectory())
/*     */       {
/* 107 */         form.addError("The system cannot access the base storage directory: " + sPath + ". Please check that this path exists and is a directory.");
/*     */       }
/* 109 */       else if (!testFile.canWrite())
/*     */       {
/* 111 */         form.addError("The system cannot write to the base storage directory: " + sPath + ". Please check that the server (tomcat) process has permission to write to this directory.");
/*     */       }
/*     */     }
/*     */ 
/* 115 */     if (form.getHasErrors())
/*     */     {
/* 117 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 121 */     getStorageDeviceManager().saveDevice(a_dbTransaction, form.getDevice());
/*     */ 
/* 123 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.action.SaveStorageDeviceAction
 * JD-Core Version:    0.6.0
 */