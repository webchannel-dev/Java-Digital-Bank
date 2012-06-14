/*    */ package com.bright.assetbank.custom.bluebooks.constant;
/*    */ 
/*    */ import com.bright.framework.common.constant.Settings;
/*    */ 
/*    */ public class BluebooksSettings extends Settings
/*    */ {
/* 16 */   private static BluebooksSettings c_instance = null;
/*    */ 
/*    */   protected BluebooksSettings()
/*    */   {
/* 20 */     super("BluebooksSettings");
/*    */   }
/*    */ 
/*    */   public static void setTestingInstance(BluebooksSettings a_testingInstance)
/*    */   {
/* 26 */     c_instance = a_testingInstance;
/*    */   }
/*    */ 
/*    */   public static long getModelEntityId()
/*    */   {
/* 34 */     return getInstance().getLongSetting("model-entity-id");
/*    */   }
/*    */ 
/*    */   public static long getSKUEntityId()
/*    */   {
/* 42 */     return getInstance().getLongSetting("sku-entity-id");
/*    */   }
/*    */ 
/*    */   private static synchronized BluebooksSettings getInstance()
/*    */   {
/* 47 */     if (c_instance == null)
/*    */     {
/* 49 */       c_instance = new BluebooksSettings();
/*    */     }
/* 51 */     return c_instance;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.bluebooks.constant.BluebooksSettings
 * JD-Core Version:    0.6.0
 */