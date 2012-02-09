/*    */ package com.bright.assetbank.actiononasset.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.framework.common.bean.DescriptionDataBean;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ 
/*    */ public abstract class ActionOnAsset extends DescriptionDataBean
/*    */ {
/* 32 */   private String m_sActionClassName = null;
/*    */ 
/*    */   public static final ActionOnAsset createAction(String a_sActionClassName)
/*    */     throws Bn2Exception
/*    */   {
/*    */     try
/*    */     {
/* 44 */       Class toInstantiate = Class.forName(a_sActionClassName);
/*    */ 
/* 46 */       if (!ActionOnAsset.class.isAssignableFrom(toInstantiate))
/*    */       {
/* 48 */         throw new IllegalArgumentException("The argument must be a valid, fully-qualified class name for a subclass of " + ActionOnAsset.class.getName());
/*    */       }
/*    */ 
/*    */       try
/*    */       {
/* 53 */         ActionOnAsset action = (ActionOnAsset)toInstantiate.newInstance();
/*    */ 
/* 55 */         return action;
/*    */       }
/*    */       catch (InstantiationException e)
/*    */       {
/* 59 */         throw new Bn2Exception("ActionOnAsset.createAction() : Caught InstantiationException when creating instance : " + e.getLocalizedMessage(), e);
/*    */       }
/*    */       catch (IllegalAccessException e)
/*    */       {
/* 63 */         throw new Bn2Exception("ActionOnAsset.createAction() : Caught IllegalAccessException when creating instance : " + e.getLocalizedMessage(), e);
/*    */       }
/*    */     }
/*    */     catch (ClassNotFoundException e) {
/*    */     
/* 68 */     throw new IllegalArgumentException("The argument must be a valid, fully-qualified class name", e);
            }
/*    */   }
/*    */ 
/*    */   public String getActionClassName()
/*    */   {
/* 74 */     return this.m_sActionClassName;
/*    */   }
/*    */ 
/*    */   public void setActionClassName(String actionClassName)
/*    */   {
/* 79 */     this.m_sActionClassName = actionClassName;
/*    */   }
/*    */ 
/*    */   public abstract void performOnAssetBeforeSave(DBTransaction paramDBTransaction, Asset paramAsset)
/*    */     throws Bn2Exception;
/*    */ 
/*    */   public abstract void performOnAssetAfterSave(DBTransaction paramDBTransaction, long paramLong1, long paramLong2)
/*    */     throws Bn2Exception;
/*    */ 
/*    */   public abstract boolean actionRequiresReindex();
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.actiononasset.action.ActionOnAsset
 * JD-Core Version:    0.6.0
 */