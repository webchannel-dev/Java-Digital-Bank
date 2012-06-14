/*    */ package com.bright.framework.news.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.framework.constant.FrameworkSettings;
/*    */ import com.bright.framework.language.util.LanguageUtils;
/*    */ import com.bright.framework.news.bean.NewsItem;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class NewsForm extends Bn2Form
/*    */ {
/* 34 */   private Vector m_vNewsItems = null;
/* 35 */   private NewsItem m_newsItem = null;
/* 36 */   private String m_sCreatedDate = null;
/*    */ 
/*    */   public NewsItem getNewsItem()
/*    */   {
/* 40 */     if (this.m_newsItem == null)
/*    */     {
/* 42 */       this.m_newsItem = new NewsItem();
/*    */ 
/* 45 */       if (FrameworkSettings.getSupportMultiLanguage())
/*    */       {
/* 47 */         LanguageUtils.createEmptyTranslations(this.m_newsItem, 20);
/*    */       }
/*    */     }
/* 50 */     return this.m_newsItem;
/*    */   }
/*    */ 
/*    */   public void setNewsItem(NewsItem a_item)
/*    */   {
/* 55 */     this.m_newsItem = a_item;
/*    */   }
/*    */ 
/*    */   public Vector getNewsItems() {
/* 59 */     return this.m_vNewsItems;
/*    */   }
/*    */ 
/*    */   public void setNewsItems(Vector a_newsItems) {
/* 63 */     this.m_vNewsItems = a_newsItems;
/*    */   }
/*    */ 
/*    */   public String getCreatedDate()
/*    */   {
/* 68 */     return this.m_sCreatedDate;
/*    */   }
/*    */ 
/*    */   public void setCreatedDate(String createdDate)
/*    */   {
/* 73 */     this.m_sCreatedDate = createdDate;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.news.form.NewsForm
 * JD-Core Version:    0.6.0
 */