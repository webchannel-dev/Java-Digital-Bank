/*    */ package com.bright.assetbank.category.service;
/*    */ 
/*    */ import com.bright.assetbank.application.bean.LightweightAsset;
/*    */ import com.bright.assetbank.category.bean.Panel;
/*    */ import com.bright.framework.category.bean.Category;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DefaultBrowseAssetsPaneller
/*    */   implements BrowseAssetsPaneller
/*    */ {
/*    */   public List<Panel> getPanels(UserProfile a_userProfile, List<LightweightAsset> a_colAssets, List<Panel> a_panels, Category a_category, boolean a_bListView)
/*    */   {
/* 40 */     Panel panel = new Panel(false, null);
/* 41 */     panel.setVisibilityStatus(3);
/* 42 */     panel.setAssets(a_colAssets);
/* 43 */     panel.setListView(a_bListView);
/* 44 */     ArrayList list = new ArrayList();
/* 45 */     list.add(panel);
/*    */ 
/* 48 */     if (a_panels != null)
/*    */     {
/* 50 */       for (Panel tempPanel : a_panels)
/*    */       {
/* 52 */         list.add(tempPanel);
/*    */       }
/*    */     }
/*    */ 
/* 56 */     return list;
/*    */   }
/*    */ 
/*    */   public boolean requiresPaging()
/*    */   {
/* 61 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.service.DefaultBrowseAssetsPaneller
 * JD-Core Version:    0.6.0
 */