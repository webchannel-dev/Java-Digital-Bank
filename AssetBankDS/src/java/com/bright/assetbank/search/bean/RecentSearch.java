/*    */ package com.bright.assetbank.search.bean;
/*    */ 
/*    */ public class RecentSearch extends SavedSearch
/*    */ {
/* 28 */   private String m_sQueryString = null;
/*    */ 
/*    */   public String getQueryString()
/*    */   {
/* 32 */     return this.m_sQueryString;
/*    */   }
/*    */ 
/*    */   public void setQueryString(String queryString) {
/* 36 */     this.m_sQueryString = queryString;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object obj)
/*    */   {
/* 41 */     if (this == obj)
/* 42 */       return true;
/* 43 */     if (!super.equals(obj))
/* 44 */       return false;
/* 45 */     if (getClass() != obj.getClass())
/* 46 */       return false;
/* 47 */     RecentSearch other = (RecentSearch)obj;
/* 48 */     if (this.m_sQueryString == null) {
/* 49 */       if (other.m_sQueryString != null)
/* 50 */         return false;
/* 51 */     } else if (!this.m_sQueryString.equals(other.m_sQueryString))
/* 52 */       return false;
/* 53 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.bean.RecentSearch
 * JD-Core Version:    0.6.0
 */