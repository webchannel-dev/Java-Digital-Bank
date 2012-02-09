/*    */ package com.bright.assetbank.synchronise.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.BooleanDataBean;
/*    */ import com.bright.framework.queue.bean.QueuedItem;
/*    */ import com.bright.framework.user.bean.User;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class SynchronisationBatch extends QueuedItem
/*    */ {
/* 33 */   private Vector<BooleanDataBean> m_vecAssetIdsToPublish = null;
/* 34 */   private User m_user = null;
/* 35 */   private String m_sExportName = null;
/*    */ 
/*    */   public Vector<BooleanDataBean> getAssetIdsToPublish()
/*    */   {
/* 39 */     return this.m_vecAssetIdsToPublish;
/*    */   }
/*    */ 
/*    */   public void setAssetIdsToPublish(Vector<BooleanDataBean> a_vecAssetIdsToPublish)
/*    */   {
/* 44 */     this.m_vecAssetIdsToPublish = a_vecAssetIdsToPublish;
/*    */   }
/*    */ 
/*    */   public User getUser()
/*    */   {
/* 49 */     return this.m_user;
/*    */   }
/*    */ 
/*    */   public void setUser(User a_user)
/*    */   {
/* 54 */     this.m_user = a_user;
/*    */   }
/*    */ 
/*    */   public void setExportName(String a_sExportName)
/*    */   {
/* 59 */     this.m_sExportName = a_sExportName;
/*    */   }
/*    */ 
/*    */   public String getExportName()
/*    */   {
/* 64 */     return this.m_sExportName;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.SynchronisationBatch
 * JD-Core Version:    0.6.0
 */