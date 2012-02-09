/*    */ package com.bright.assetbank.attribute.bean;
/*    */ 
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class ListAttribute extends Attribute
/*    */ {
/* 36 */   private Vector m_vOptionValues = null;
/*    */ 
/*    */   public Vector getSelectedValues()
/*    */   {
/* 41 */     if (this.m_vOptionValues == null)
/*    */     {
/* 43 */       this.m_vOptionValues = new Vector();
/*    */     }
/*    */ 
/* 46 */     return this.m_vOptionValues;
/*    */   }
/*    */ 
/*    */   public void setSelectedValues(Vector a_vOptionValues)
/*    */   {
/* 52 */     this.m_vOptionValues = a_vOptionValues;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.bean.ListAttribute
 * JD-Core Version:    0.6.0
 */