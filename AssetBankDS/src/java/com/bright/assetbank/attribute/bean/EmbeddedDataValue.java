/*    */ package com.bright.assetbank.attribute.bean;
/*    */ 
/*    */ import com.bright.framework.database.bean.DataBean;
/*    */ 
/*    */ public class EmbeddedDataValue extends DataBean
/*    */ {
/* 29 */   private String m_sName = null;
/* 30 */   private String m_sExpression = null;
/* 31 */   private EmbeddedDataType m_type = null;
/*    */ 
/*    */   public void setExpression(String a_sExpression)
/*    */   {
/* 35 */     this.m_sExpression = a_sExpression;
/*    */   }
/*    */ 
/*    */   public String getExpression()
/*    */   {
/* 40 */     return this.m_sExpression;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 44 */     return this.m_sName;
/*    */   }
/*    */ 
/*    */   public void setName(String a_sName) {
/* 48 */     this.m_sName = a_sName;
/*    */   }
/*    */ 
/*    */   public EmbeddedDataType getType() {
/* 52 */     return this.m_type;
/*    */   }
/*    */ 
/*    */   public void setType(EmbeddedDataType a_type) {
/* 56 */     this.m_type = a_type;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.bean.EmbeddedDataValue
 * JD-Core Version:    0.6.0
 */