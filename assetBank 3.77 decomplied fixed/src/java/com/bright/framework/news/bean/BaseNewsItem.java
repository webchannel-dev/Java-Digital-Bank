/*    */ package com.bright.framework.news.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.BaseTranslatableStringDataBean;
/*    */ 
/*    */ public class BaseNewsItem extends BaseTranslatableStringDataBean
/*    */ {
/* 28 */   private String m_sContent = null;
/*    */ 
/*    */   public String getContent()
/*    */   {
/* 32 */     return this.m_sContent;
/*    */   }
/*    */ 
/*    */   public void setContent(String content)
/*    */   {
/* 37 */     this.m_sContent = content;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.news.bean.BaseNewsItem
 * JD-Core Version:    0.6.0
 */