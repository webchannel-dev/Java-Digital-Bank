/*    */ package com.bright.assetbank.entity.action;
/*    */ 
/*    */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ 
/*    */ public abstract class AssetEntityAction extends BTransactionAction
/*    */ {
/* 29 */   private AssetEntityManager m_assetEntityManager = null;
/*    */ 
/*    */   public AssetEntityManager getAssetEntityManager()
/*    */   {
/* 33 */     return this.m_assetEntityManager;
/*    */   }
/*    */ 
/*    */   public void setAssetEntityManager(AssetEntityManager a_assetEntityManager)
/*    */   {
/* 38 */     this.m_assetEntityManager = a_assetEntityManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.action.AssetEntityAction
 * JD-Core Version:    0.6.0
 */