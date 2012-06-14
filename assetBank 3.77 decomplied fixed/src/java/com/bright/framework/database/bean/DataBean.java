/*    */ package com.bright.framework.database.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class DataBean
/*    */   implements Cloneable, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -1285688125848043361L;
/* 30 */   protected long m_lId = 0L;
/*    */ 
/*    */   public DataBean()
/*    */   {
/*    */   }
/*    */ 
/*    */   public DataBean(DataBean a_bean)
/*    */   {
/* 39 */     this.m_lId = a_bean.m_lId;
/*    */   }
/*    */ 
/*    */   public void setId(long a_lId)
/*    */   {
/* 44 */     this.m_lId = a_lId;
/*    */   }
/*    */ 
/*    */   public long getId()
/*    */   {
/* 49 */     return this.m_lId;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object obj)
/*    */   {
/* 54 */     if (obj == null) return false;
/* 55 */     if (!getClass().equals(obj.getClass())) return false;
/*    */ 
/* 57 */     DataBean bean = (DataBean)obj;
/* 58 */     return getId() == bean.getId();
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 63 */     return new Long(this.m_lId).hashCode();
/*    */   }
/*    */ 
/*    */   public Object clone()
/*    */   {
/*    */     try
/*    */     {
/* 70 */       return super.clone();
/*    */     }
/*    */     catch (CloneNotSupportedException e)
/*    */     {
                    throw new RuntimeException(e);
/*    */     }
/* 75 */     
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.database.bean.DataBean
 * JD-Core Version:    0.6.0
 */