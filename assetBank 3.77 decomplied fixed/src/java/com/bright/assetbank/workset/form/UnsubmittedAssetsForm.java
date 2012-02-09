/*    */ package com.bright.assetbank.workset.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.assetbank.workset.bean.UnsubmittedAssets;
/*    */ import com.bright.framework.common.bean.StringDataBean;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class UnsubmittedAssetsForm extends Bn2Form
/*    */ {
/* 27 */   private UnsubmittedAssets m_listAssets = null;
/*    */ 
/* 29 */   private long m_lSelectedUserId = 0L;
/*    */ 
/* 31 */   private Vector<StringDataBean> m_listUsers = null;
/*    */ 
/*    */   public UnsubmittedAssets getListAssets()
/*    */   {
/* 36 */     return this.m_listAssets;
/*    */   }
/*    */ 
/*    */   public void setListAssets(UnsubmittedAssets a_sListAssets)
/*    */   {
/* 41 */     this.m_listAssets = a_sListAssets;
/*    */   }
/*    */ 
/*    */   public long getSelectedUserId()
/*    */   {
/* 46 */     return this.m_lSelectedUserId;
/*    */   }
/*    */ 
/*    */   public void setSelectedUserId(long a_sSelectedUserId)
/*    */   {
/* 51 */     this.m_lSelectedUserId = a_sSelectedUserId;
/*    */   }
/*    */ 
/*    */   public Vector<StringDataBean> getListUsers()
/*    */   {
/* 56 */     return this.m_listUsers;
/*    */   }
/*    */ 
/*    */   public void setListUsers(Vector<StringDataBean> a_sListUsers)
/*    */   {
/* 61 */     this.m_listUsers = a_sListUsers;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workset.form.UnsubmittedAssetsForm
 * JD-Core Version:    0.6.0
 */