/*    */ package com.bright.assetbank.publishing.bean;
/*    */ 
/*    */ import com.bright.framework.database.bean.DataBean;
/*    */ 
/*    */ public class PublishingAction extends DataBean
/*    */ {
/*  7 */   private String m_name = null;
/*  8 */   private String m_path = null;
/*  9 */   private long m_accessLevelId = 0L;
/* 10 */   private boolean m_runDaily = false;
/*    */ 
/*    */   public PublishingAction(Long a_id, String a_name, String a_path, long a_accessLevelId, boolean a_runDaily)
/*    */   {
/* 14 */     this.m_lId = a_id.longValue();
/* 15 */     this.m_name = a_name;
/* 16 */     this.m_path = a_path;
/* 17 */     this.m_accessLevelId = a_accessLevelId;
/* 18 */     this.m_runDaily = a_runDaily;
/*    */   }
/*    */ 
/*    */   public void setName(String a_name)
/*    */   {
/* 23 */     this.m_name = a_name;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 28 */     return this.m_name;
/*    */   }
/*    */ 
/*    */   public void setPath(String a_path)
/*    */   {
/* 33 */     this.m_path = a_path;
/*    */   }
/*    */ 
/*    */   public String getPath()
/*    */   {
/* 38 */     return this.m_path;
/*    */   }
/*    */ 
/*    */   public void setAccessLevelId(long a_accessLevelId)
/*    */   {
/* 43 */     this.m_accessLevelId = a_accessLevelId;
/*    */   }
/*    */ 
/*    */   public long getAccessLevelId()
/*    */   {
/* 48 */     return this.m_accessLevelId;
/*    */   }
/*    */ 
/*    */   public void setRunDaily(boolean a_runDaily)
/*    */   {
/* 53 */     this.m_runDaily = a_runDaily;
/*    */   }
/*    */ 
/*    */   public boolean getRunDaily()
/*    */   {
/* 58 */     return this.m_runDaily;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.publishing.bean.PublishingAction
 * JD-Core Version:    0.6.0
 */