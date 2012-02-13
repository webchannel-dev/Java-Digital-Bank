/*    */ package com.bright.assetbank.application.service;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bn2web.common.service.Bn2Manager;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.simplelist.bean.ListItem;
/*    */ import com.bright.framework.simplelist.service.ListManager;
/*    */ import org.apache.struts.upload.FormFile;
/*    */ 
/*    */ public class FileUploadManager extends Bn2Manager
/*    */ {
/*    */   ListManager m_listManager;
/*    */ 
/*    */   public String validateMaxFileUploadSize(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, FormFile a_file)
/*    */     throws Bn2Exception
/*    */   {
/* 40 */     String maxFileUploadSizeError = null;
/* 41 */     if (!a_userProfile.getIsAdmin())
/*    */     {
/* 43 */       if ((AssetBankSettings.getMaxFileUploadSize() > 0) && (a_file.getFileSize() / 1024 > AssetBankSettings.getMaxFileUploadSize()))
/*    */       {
/* 46 */         maxFileUploadSizeError = getListManager().getListItem(a_dbTransaction, "maxUploadSizeExceeded", a_userProfile.getCurrentLanguage()).getBody();
/*    */       }
/*    */     }
/* 49 */     return maxFileUploadSizeError;
/*    */   }
/*    */ 
/*    */   public ListManager getListManager()
/*    */   {
/* 54 */     return this.m_listManager;
/*    */   }
/*    */ 
/*    */   public void setListManager(ListManager a_listManager)
/*    */   {
/* 59 */     this.m_listManager = a_listManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.FileUploadManager
 * JD-Core Version:    0.6.0
 */