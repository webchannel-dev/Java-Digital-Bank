/*    */ package com.bright.assetbank.entity.relationship.form;
/*    */ 
/*    */ import com.bright.assetbank.application.form.AssetForm;
/*    */ import com.bright.framework.common.bean.IdValueBean;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class AddEmptyRelatedAssetsForm extends AssetForm
/*    */ {
/* 32 */   private ArrayList<IdValueBean> m_alPotentialChildren = null;
/* 33 */   private ArrayList<IdValueBean> m_alPotentialPeers = null;
/*    */ 
/*    */   public ArrayList<IdValueBean> getPotentialChildren()
/*    */   {
/* 37 */     return this.m_alPotentialChildren;
/*    */   }
/*    */ 
/*    */   public void setPotentialChildren(ArrayList<IdValueBean> a_alPotentialChildren)
/*    */   {
/* 42 */     this.m_alPotentialChildren = a_alPotentialChildren;
/*    */   }
/*    */ 
/*    */   public ArrayList<IdValueBean> getPotentialPeers()
/*    */   {
/* 47 */     return this.m_alPotentialPeers;
/*    */   }
/*    */ 
/*    */   public void setPotentialPeers(ArrayList<IdValueBean> a_alPotentialPeers)
/*    */   {
/* 52 */     this.m_alPotentialPeers = a_alPotentialPeers;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.form.AddEmptyRelatedAssetsForm
 * JD-Core Version:    0.6.0
 */