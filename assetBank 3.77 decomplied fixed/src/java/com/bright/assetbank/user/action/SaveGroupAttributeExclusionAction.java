/*    */ package com.bright.assetbank.user.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.user.service.ABUserManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ 
/*    */ public class SaveGroupAttributeExclusionAction extends SaveGroupExclusionAction
/*    */ {
/*    */   private static final String c_ksClassName = "SaveGroupAttributeExclusionAction";
/*    */ 
/*    */   protected void removeExistingExclusions(DBTransaction a_transaction, long a_lGroupId)
/*    */     throws Bn2Exception
/*    */   {
/* 36 */     this.m_userManager.removeAttributeExclusionsForGroup(a_transaction, a_lGroupId);
/*    */   }
/*    */ 
/*    */   protected void saveNewExclusion(DBTransaction a_transaction, long a_lGroupId, long a_lItemId, String a_sValue)
/*    */     throws Bn2Exception
/*    */   {
/* 42 */     this.m_userManager.addGroupAttributeExclusion(a_transaction, a_lGroupId, a_lItemId, a_sValue);
/*    */   }
/*    */ 
/*    */   protected void runPostSaveActions()
/*    */   {
/* 47 */     this.m_userManager.clearCaches();
/*    */   }
/*    */ 
/*    */   protected boolean complexId()
/*    */   {
/* 52 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.SaveGroupAttributeExclusionAction
 * JD-Core Version:    0.6.0
 */