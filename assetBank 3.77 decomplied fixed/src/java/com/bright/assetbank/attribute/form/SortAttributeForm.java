/*    */ package com.bright.assetbank.attribute.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.assetbank.attribute.bean.SortAttribute;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class SortAttributeForm extends Bn2Form
/*    */ {
/* 32 */   private SortAttribute m_sortAttribute = new SortAttribute();
/* 33 */   private Vector m_vecAttributes = null;
/* 34 */   private Vector m_vecSortAttributes = null;
/* 35 */   private long m_lSortArea = 0L;
/*    */ 
/*    */   public void setAttributes(Vector a_vecAttributes)
/*    */   {
/* 39 */     this.m_vecAttributes = a_vecAttributes;
/*    */   }
/*    */ 
/*    */   public Vector getAttributes()
/*    */   {
/* 44 */     return this.m_vecAttributes;
/*    */   }
/*    */ 
/*    */   public void setSortArea(long a_lSortArea)
/*    */   {
/* 49 */     this.m_lSortArea = a_lSortArea;
/*    */   }
/*    */ 
/*    */   public long getSortArea()
/*    */   {
/* 54 */     return this.m_lSortArea;
/*    */   }
/*    */ 
/*    */   public void setSortAttributes(Vector a_vecSortAttributes)
/*    */   {
/* 59 */     this.m_vecSortAttributes = a_vecSortAttributes;
/*    */   }
/*    */ 
/*    */   public Vector getSortAttributes()
/*    */   {
/* 64 */     return this.m_vecSortAttributes;
/*    */   }
/*    */ 
/*    */   public int getNoOfSortAttributes()
/*    */   {
/* 69 */     if (getSortAttributes() != null)
/*    */     {
/* 71 */       return getSortAttributes().size();
/*    */     }
/* 73 */     return 0;
/*    */   }
/*    */ 
/*    */   public void setSortAttribute(SortAttribute a_sortAttribute)
/*    */   {
/* 78 */     this.m_sortAttribute = a_sortAttribute;
/*    */   }
/*    */ 
/*    */   public SortAttribute getSortAttribute()
/*    */   {
/* 83 */     return this.m_sortAttribute;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.form.SortAttributeForm
 * JD-Core Version:    0.6.0
 */