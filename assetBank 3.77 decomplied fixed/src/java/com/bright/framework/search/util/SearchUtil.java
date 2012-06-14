/*    */ package com.bright.framework.search.util;
/*    */ 
/*    */ import com.bright.framework.search.constant.SearchConstants;
/*    */ import java.text.SimpleDateFormat;
/*    */ 
/*    */ public class SearchUtil
/*    */   implements SearchConstants
/*    */ {
/* 33 */   private static SimpleDateFormat s_luceneDateFormat = new SimpleDateFormat("yyyyMMdd");
/*    */ 
/*    */   public static SimpleDateFormat getLuceneDateFormat()
/*    */   {
/* 38 */     return s_luceneDateFormat;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.util.SearchUtil
 * JD-Core Version:    0.6.0
 */