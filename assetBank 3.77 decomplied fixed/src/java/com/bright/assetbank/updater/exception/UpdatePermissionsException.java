/*    */ package com.bright.assetbank.updater.exception;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ 
/*    */ public class UpdatePermissionsException extends Bn2Exception
/*    */ {
/* 23 */   private UpdatePermissionDetails m_updatePermissionDetails = null;
/*    */ 
/*    */   public UpdatePermissionsException(UpdatePermissionDetails a_updatePermissionDetails)
/*    */   {
/* 27 */     this.m_updatePermissionDetails = a_updatePermissionDetails;
/*    */   }
/*    */ 
/*    */   public UpdatePermissionDetails getUpdatePermissionDetails()
/*    */   {
/* 35 */     return this.m_updatePermissionDetails;
/*    */   }
/*    */ 
/*    */   public void setUpdatePermissionDetails(UpdatePermissionDetails a_sUpdatePermissionDetails)
/*    */   {
/* 43 */     this.m_updatePermissionDetails = a_sUpdatePermissionDetails;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.updater.exception.UpdatePermissionsException
 * JD-Core Version:    0.6.0
 */