/*    */ package com.bright.assetbank.attribute.filter.bean;
/*    */ 
/*    */ import com.bright.framework.database.bean.DataBean;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class FilterGroup extends DataBean
/*    */ {
/* 32 */   private int m_iFilterTypeId = 0;
/* 33 */   private String m_sName = null;
/* 34 */   private Vector m_vecFilters = null;
/*    */ 
/*    */   public void setFilterTypeId(int a_iFilterTypeId)
/*    */   {
/* 38 */     this.m_iFilterTypeId = a_iFilterTypeId;
/*    */   }
/*    */ 
/*    */   public int getFilterTypeId()
/*    */   {
/* 43 */     return this.m_iFilterTypeId;
/*    */   }
/*    */ 
/*    */   public void setName(String a_sName)
/*    */   {
/* 48 */     this.m_sName = a_sName;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 53 */     return this.m_sName;
/*    */   }
/*    */ 
/*    */   public void setFilters(Vector a_vecFilters)
/*    */   {
/* 58 */     this.m_vecFilters = a_vecFilters;
/*    */   }
/*    */ 
/*    */   public Vector getFilters()
/*    */   {
/* 63 */     return this.m_vecFilters;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.filter.bean.FilterGroup
 * JD-Core Version:    0.6.0
 */