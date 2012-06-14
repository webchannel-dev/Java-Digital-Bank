/*    */ package com.bright.framework.image.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class ColourImpl
/*    */   implements MutableColour, Serializable
/*    */ {
/*    */   private int m_red;
/*    */   private int m_green;
/*    */   private int m_blue;
/*    */ 
/*    */   public ColourImpl()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ColourImpl(int a_red, int a_green, int a_blue)
/*    */   {
/* 37 */     this.m_red = a_red;
/* 38 */     this.m_green = a_green;
/* 39 */     this.m_blue = a_blue;
/*    */   }
/*    */ 
/*    */   public int getRed()
/*    */   {
/* 44 */     return this.m_red;
/*    */   }
/*    */ 
/*    */   public void setRed(int a_red)
/*    */   {
/* 49 */     this.m_red = a_red;
/*    */   }
/*    */ 
/*    */   public int getGreen()
/*    */   {
/* 54 */     return this.m_green;
/*    */   }
/*    */ 
/*    */   public void setGreen(int a_green)
/*    */   {
/* 59 */     this.m_green = a_green;
/*    */   }
/*    */ 
/*    */   public int getBlue()
/*    */   {
/* 64 */     return this.m_blue;
/*    */   }
/*    */ 
/*    */   public void setBlue(int a_blue)
/*    */   {
/* 69 */     this.m_blue = a_blue;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.image.bean.ColourImpl
 * JD-Core Version:    0.6.0
 */