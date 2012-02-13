/*    */ package com.bright.assetbank.entity.relationship.action;
/*    */ 
/*    */ import com.bright.assetbank.entity.action.AssetEntityAction;
/*    */ import com.bright.assetbank.entity.relationship.service.AssetEntityRelationshipManager;
/*    */ 
/*    */ public abstract class AssetEntityRelationshipAction extends AssetEntityAction
/*    */ {
/* 29 */   private AssetEntityRelationshipManager m_assetEntityRelationshipManager = null;
/*    */ 
/*    */   public AssetEntityRelationshipManager getAssetEntityRelationshipManager()
/*    */   {
/* 33 */     return this.m_assetEntityRelationshipManager;
/*    */   }
/*    */ 
/*    */   public void setAssetEntityRelationshipManager(AssetEntityRelationshipManager a_assetEntityRelationshipManager)
/*    */   {
/* 38 */     this.m_assetEntityRelationshipManager = a_assetEntityRelationshipManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.action.AssetEntityRelationshipAction
 * JD-Core Version:    0.6.0
 */