/*    */ package com.bright.framework.category.constant;
/*    */ 
/*    */ import com.bn2web.common.constant.GlobalSettings;
/*    */ 
/*    */ public class CategorySettings extends GlobalSettings
/*    */ {
/*    */   public static int getCategoryImageHeight()
/*    */   {
/* 30 */     return getInstance().getIntSetting("category-image-height");
/*    */   }
/*    */ 
/*    */   public static int getCategoryImageWidth()
/*    */   {
/* 35 */     return getInstance().getIntSetting("category-image-width");
/*    */   }
/*    */ 
/*    */   public static String getCategoryImagesDir()
/*    */   {
/* 40 */     return getInstance().getStringSetting("category-images-dir");
/*    */   }
/*    */ 
/*    */   public static String getCategoryImageFormat()
/*    */   {
/* 45 */     return getInstance().getStringSetting("category-image-format");
/*    */   }
/*    */ 
/*    */   public static String getBrowsingRootCategoryId(long a_lTypeId)
/*    */   {
/* 50 */     return getInstance().getStringSetting("browsing-root-category-id" + a_lTypeId);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.constant.CategorySettings
 * JD-Core Version:    0.6.0
 */