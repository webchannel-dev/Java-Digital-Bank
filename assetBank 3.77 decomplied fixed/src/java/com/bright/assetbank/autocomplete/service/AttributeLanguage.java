/*    */ package com.bright.assetbank.autocomplete.service;
/*    */ 
/*    */ class AttributeLanguage
/*    */ {
/*    */   private long m_attributeId;
/*    */   private String m_languageCode;
/*    */ 
/*    */   AttributeLanguage(long a_attributeId, String a_languageCode)
/*    */   {
/* 28 */     this.m_attributeId = a_attributeId;
/* 29 */     this.m_languageCode = a_languageCode;
/*    */   }
/*    */ 
/*    */   public long getAttributeId()
/*    */   {
/* 34 */     return this.m_attributeId;
/*    */   }
/*    */ 
/*    */   public String getLanguageCode()
/*    */   {
/* 39 */     return this.m_languageCode;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 46 */     if (this == o) return true;
/* 47 */     if ((o == null) || (getClass() != o.getClass())) return false;
/*    */ 
/* 49 */     AttributeLanguage that = (AttributeLanguage)o;
/*    */ 
/* 51 */     if (this.m_attributeId != that.m_attributeId) return false;
/* 52 */     return this.m_languageCode.equals(that.m_languageCode);
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 61 */     int result = (int)(this.m_attributeId ^ this.m_attributeId >>> 32);
/* 62 */     result = 31 * result + this.m_languageCode.hashCode();
/* 63 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.autocomplete.service.AttributeLanguage
 * JD-Core Version:    0.6.0
 */