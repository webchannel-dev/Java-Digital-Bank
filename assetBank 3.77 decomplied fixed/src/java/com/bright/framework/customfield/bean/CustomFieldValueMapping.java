/*    */ package com.bright.framework.customfield.bean;
/*    */ 
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class CustomFieldValueMapping
/*    */ {
/* 29 */   private long m_lItemId = -1L;
/* 30 */   private Vector<CustomFieldValue> m_listValues = null;
/* 31 */   private CustomField m_customField = null;
/* 32 */   private String m_sTextValue = null;
/*    */ 
/*    */   public CustomField getCustomField()
/*    */   {
/* 36 */     return this.m_customField;
/*    */   }
/*    */ 
/*    */   public void setCustomField(CustomField a_customField)
/*    */   {
/* 41 */     this.m_customField = a_customField;
/*    */   }
/*    */ 
/*    */   public Vector<CustomFieldValue> getListValues()
/*    */   {
/* 46 */     return this.m_listValues;
/*    */   }
/*    */ 
/*    */   public void setListValues(Vector<CustomFieldValue> a_listValues)
/*    */   {
/* 51 */     this.m_listValues = a_listValues;
/*    */   }
/*    */ 
/*    */   public long getItemId()
/*    */   {
/* 56 */     return this.m_lItemId;
/*    */   }
/*    */ 
/*    */   public void setItemId(long a_lItemId)
/*    */   {
/* 61 */     this.m_lItemId = a_lItemId;
/*    */   }
/*    */ 
/*    */   public String getTextValue()
/*    */   {
/* 66 */     return this.m_sTextValue;
/*    */   }
/*    */ 
/*    */   public void setTextValue(String a_sTextValue)
/*    */   {
/* 71 */     this.m_sTextValue = a_sTextValue;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.customfield.bean.CustomFieldValueMapping
 * JD-Core Version:    0.6.0
 */