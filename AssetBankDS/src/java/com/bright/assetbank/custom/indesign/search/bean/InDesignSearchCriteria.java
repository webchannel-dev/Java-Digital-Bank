/*    */ package com.bright.assetbank.custom.indesign.search.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class InDesignSearchCriteria
/*    */   implements Serializable
/*    */ {
/*    */   private Boolean m_isTemplate;
/*    */ 
/*    */   public boolean isEmpty()
/*    */   {
/* 31 */     return this.m_isTemplate == null;
/*    */   }
/*    */ 
/*    */   public Map<String, String> toMap()
/*    */   {
/* 40 */     Map externalFilterCriteria = new HashMap();
/*    */ 
/* 42 */     if (getIsTemplate() != null)
/*    */     {
/* 44 */       externalFilterCriteria.put("isTemplate", getIsTemplate().toString());
/*    */     }
/*    */ 
/* 49 */     return externalFilterCriteria;
/*    */   }
/*    */ 
/*    */   public Boolean getIsTemplate()
/*    */   {
/* 54 */     return this.m_isTemplate;
/*    */   }
/*    */ 
/*    */   public void setIsTemplate(Boolean a_isTemplate)
/*    */   {
/* 59 */     this.m_isTemplate = a_isTemplate;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.search.bean.InDesignSearchCriteria
 * JD-Core Version:    0.6.0
 */