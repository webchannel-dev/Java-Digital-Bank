/*    */ package com.bright.assetbank.user.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class GroupAttributeVisibilityForm extends Bn2Form
/*    */ {
/* 31 */   private long m_lGroupId = 0L;
/* 32 */   private Vector m_vecAttributeList = null;
/* 33 */   private Vector m_vecVisibilityList = null;
/*    */ 
/*    */   public Vector getAttributeList()
/*    */   {
/* 38 */     return this.m_vecAttributeList;
/*    */   }
/*    */ 
/*    */   public void setAttributeList(Vector a_vecAttributeList)
/*    */   {
/* 43 */     this.m_vecAttributeList = a_vecAttributeList;
/*    */   }
/*    */ 
/*    */   public long getGroupId()
/*    */   {
/* 48 */     return this.m_lGroupId;
/*    */   }
/*    */ 
/*    */   public void setGroupId(long a_lGroupId)
/*    */   {
/* 53 */     this.m_lGroupId = a_lGroupId;
/*    */   }
/*    */ 
/*    */   public Vector getVisibilityList()
/*    */   {
/* 58 */     return this.m_vecVisibilityList;
/*    */   }
/*    */ 
/*    */   public void setVisibilityList(Vector a_vecVisibilityList)
/*    */   {
/* 63 */     this.m_vecVisibilityList = a_vecVisibilityList;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.form.GroupAttributeVisibilityForm
 * JD-Core Version:    0.6.0
 */