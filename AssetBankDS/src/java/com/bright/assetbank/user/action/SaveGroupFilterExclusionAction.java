/*    */ package com.bright.assetbank.user.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.user.service.ABUserManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ 
/*    */ public class SaveGroupFilterExclusionAction extends SaveGroupExclusionAction
/*    */ {
/*    */   private static final String c_ksClassName = "SaveGroupFilterExclusionAction";
/*    */ 
/*    */   protected void removeExistingExclusions(DBTransaction a_transaction, long a_lGroupId)
/*    */     throws Bn2Exception
/*    */   {
/* 35 */     this.m_userManager.removeFilterExclusionsForGroup(a_transaction, a_lGroupId);
/*    */   }
/*    */ 
/*    */   protected void saveNewExclusion(DBTransaction a_transaction, long a_lGroupId, long a_lItemId, String a_sValue)
/*    */     throws Bn2Exception
/*    */   {
/* 41 */     this.m_userManager.addGroupFilterExclusion(a_transaction, a_lGroupId, a_lItemId);
/*    */   }
/*    */ 
/*    */   protected void runPostSaveActions()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.SaveGroupFilterExclusionAction
 * JD-Core Version:    0.6.0
 */