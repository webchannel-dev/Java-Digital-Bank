/*    */ package com.bright.assetbank.brandtemplate.bean;
/*    */ 
/*    */ public class TemplateField
/*    */ {
/*    */   private String m_fullyQualifiedName;
/*    */   private String m_humanName;
/*    */   private boolean multiline;
/*    */   private String value;
/*    */ 
/*    */   public TemplateField()
/*    */   {
/*    */   }
/*    */ 
/*    */   public TemplateField(String a_fullyQualifiedName, String a_humanName, boolean a_multiline, String a_value)
/*    */   {
/* 41 */     this.m_fullyQualifiedName = a_fullyQualifiedName;
/* 42 */     this.m_humanName = a_humanName;
/* 43 */     this.multiline = a_multiline;
/* 44 */     this.value = a_value;
/*    */   }
/*    */ 
/*    */   public String getFullyQualifiedName()
/*    */   {
/* 49 */     return this.m_fullyQualifiedName;
/*    */   }
/*    */ 
/*    */   public void setFullyQualifiedName(String a_fullyQualifiedName)
/*    */   {
/* 54 */     this.m_fullyQualifiedName = a_fullyQualifiedName;
/*    */   }
/*    */ 
/*    */   public String getHumanName()
/*    */   {
/* 59 */     return this.m_humanName;
/*    */   }
/*    */ 
/*    */   public void setHumanName(String a_humanName)
/*    */   {
/* 64 */     this.m_humanName = a_humanName;
/*    */   }
/*    */ 
/*    */   public boolean isMultiline()
/*    */   {
/* 69 */     return this.multiline;
/*    */   }
/*    */ 
/*    */   public void setMultiline(boolean a_multiline)
/*    */   {
/* 74 */     this.multiline = a_multiline;
/*    */   }
/*    */ 
/*    */   public String getValue()
/*    */   {
/* 79 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(String a_value)
/*    */   {
/* 84 */     this.value = a_value;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.brandtemplate.bean.TemplateField
 * JD-Core Version:    0.6.0
 */