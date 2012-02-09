/*    */ package com.bright.assetbank.user.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.user.service.ABUserManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ 
/*    */ public class SaveGroupUsageExclusionAction extends SaveGroupExclusionAction
/*    */ {
/*    */   private static final String c_ksClassName = "SaveGroupUsageExclusionAction";
/*    */ 
/*    */   protected void removeExistingExclusions(DBTransaction a_transaction, long a_lGroupId)
/*    */     throws Bn2Exception
/*    */   {
/* 36 */     this.m_userManager.removeUsageExclusionsForGroup(a_transaction, a_lGroupId);
/*    */   }
/*    */ 
/*    */   protected void saveNewExclusion(DBTransaction a_transaction, long a_lGroupId, long a_lItemId, String a_sValue)
/*    */     throws Bn2Exception
/*    */   {
/* 42 */     this.m_userManager.addGroupUsageExclusion(a_transaction, a_lGroupId, a_lItemId);
/*    */   }
/*    */ 
/*    */   protected void runPostSaveActions()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.SaveGroupUsageExclusionAction
 * JD-Core Version:    0.6.0
 */