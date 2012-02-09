/*    */ package com.bright.assetbank.usage.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.SelectOption;
/*    */ 
/*    */ public class UsageTypeSelectOption extends SelectOption
/*    */ {
/* 29 */   private UsageType m_usageType = null;
/*    */ 
/*    */   public UsageTypeSelectOption()
/*    */   {
/* 33 */     this.m_usageType = new UsageType();
/*    */   }
/*    */ 
/*    */   public UsageType getUsageType()
/*    */   {
/* 38 */     return this.m_usageType;
/*    */   }
/*    */ 
/*    */   public void setUsageType(UsageType a_sUsageType)
/*    */   {
/* 43 */     this.m_usageType = a_sUsageType;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.bean.UsageTypeSelectOption
 * JD-Core Version:    0.6.0
 */