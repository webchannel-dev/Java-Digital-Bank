/*    */ package com.bright.assetbank.attribute.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.StringDataBean;
/*    */ 
/*    */ public class AttributeInList extends StringDataBean
/*    */ {
/* 30 */   private long m_lTypeId = 0L;
/* 31 */   private String m_sStaticFieldName = null;
/*    */ 
/*    */   public AttributeInList()
/*    */   {
/*    */   }
/*    */ 
/*    */   public AttributeInList(long a_lId, String a_sName, long a_lTypeId, String a_sStaticFieldName)
/*    */   {
/* 40 */     super(a_lId, a_sName);
/* 41 */     this.m_lTypeId = a_lTypeId;
/* 42 */     this.m_sStaticFieldName = a_sStaticFieldName;
/*    */   }
/*    */ 
/*    */   public long getTypeId()
/*    */   {
/* 47 */     return this.m_lTypeId;
/*    */   }
/*    */ 
/*    */   public void setTypeId(long a_lTypeId)
/*    */   {
/* 52 */     this.m_lTypeId = a_lTypeId;
/*    */   }
/*    */ 
/*    */   public String getStaticFieldName()
/*    */   {
/* 57 */     return this.m_sStaticFieldName;
/*    */   }
/*    */ 
/*    */   public void setStaticFieldName(String a_sStaticFieldName)
/*    */   {
/* 62 */     this.m_sStaticFieldName = a_sStaticFieldName;
/*    */   }
/*    */ 
/*    */   public boolean getIsTranslatable()
/*    */   {
/* 67 */     return (this.m_lTypeId == 1L) || (this.m_lTypeId == 2L) || (this.m_lTypeId == 14L) || (this.m_lTypeId == 15L);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.bean.AttributeInList
 * JD-Core Version:    0.6.0
 */