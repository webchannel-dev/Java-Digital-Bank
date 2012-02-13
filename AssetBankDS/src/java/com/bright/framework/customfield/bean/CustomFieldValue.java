/*    */ package com.bright.framework.customfield.bean;
/*    */ 
/*    */ import com.bright.framework.database.bean.DataBean;
/*    */ 
/*    */ public class CustomFieldValue extends DataBean
/*    */ {
/* 28 */   private long m_lCustomFieldId = -1L;
/* 29 */   private String m_sValue = null;
/*    */ 
/*    */   public void setCustomFieldId(long a_lCustomFieldId)
/*    */   {
/* 33 */     this.m_lCustomFieldId = a_lCustomFieldId;
/*    */   }
/*    */ 
/*    */   public long getCustomFieldId()
/*    */   {
/* 38 */     return this.m_lCustomFieldId;
/*    */   }
/*    */ 
/*    */   public void setValue(String a_sValue)
/*    */   {
/* 43 */     this.m_sValue = a_sValue;
/*    */   }
/*    */ 
/*    */   public String getValue()
/*    */   {
/* 48 */     return this.m_sValue;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.customfield.bean.CustomFieldValue
 * JD-Core Version:    0.6.0
 */