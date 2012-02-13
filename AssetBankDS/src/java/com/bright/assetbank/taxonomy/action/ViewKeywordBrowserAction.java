/*    */ package com.bright.assetbank.taxonomy.action;
/*    */ 
/*    */ import com.bright.framework.util.StringUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class ViewKeywordBrowserAction extends ViewKeywordChooserAction
/*    */ {
/*    */   protected long getTreeId(HttpServletRequest a_request)
/*    */   {
/* 33 */     return getLongParameter(a_request, "categoryTypeId");
/*    */   }
/*    */ 
/*    */   protected String getFilter(HttpServletRequest a_request)
/*    */   {
/* 38 */     if (StringUtil.stringIsPopulated(a_request.getParameter("filter")))
/*    */     {
/* 40 */       return a_request.getParameter("filter");
/*    */     }
/* 42 */     return "all";
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.taxonomy.action.ViewKeywordBrowserAction
 * JD-Core Version:    0.6.0
 */