/*    */ package com.bright.assetbank.synchronise.bean;
/*    */ 
/*    */ import com.bright.assetbank.synchronise.constant.ExternalDataSynchronisationSettings;
/*    */ import com.bright.assetbank.synchronise.constant.SynchronisationConstants;
/*    */ import java.util.TimerTask;
/*    */ 
/*    */ public abstract class ExternalDataSynchronisationTask extends TimerTask
/*    */   implements SynchronisationConstants
/*    */ {
/* 34 */   protected ExternalDataSynchronisationSettings m_settings = null;
/*    */ 
/*    */   public void setSettings(ExternalDataSynchronisationSettings a_settings)
/*    */   {
/* 38 */     this.m_settings = a_settings;
/*    */   }
/*    */ 
/*    */   public ExternalDataSynchronisationSettings getSettings()
/*    */   {
/* 43 */     return this.m_settings;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.ExternalDataSynchronisationTask
 * JD-Core Version:    0.6.0
 */