/*    */ package com.bright.assetbank.search.util;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.attribute.bean.Attribute;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.bean.StringDataBean;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.language.bean.Language;
/*    */ import com.bright.framework.simplelist.bean.ListItem;
/*    */ import com.bright.framework.simplelist.service.ListManager;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class SearchBuilderUtil
/*    */ {
/*    */   public static Attribute getPseudoSearchAttribute(DBTransaction a_dbTransaction, ListManager a_listManager, ABUserProfile a_userProfile, long a_lId)
/*    */     throws Bn2Exception
/*    */   {
/* 42 */     Attribute att = null;
/*    */ 
/* 44 */     if (a_lId == -1L)
/*    */     {
/* 46 */       att = createNewAttribute(-1L, a_listManager.getListItem(a_dbTransaction, "label-agreement-text-nc", a_userProfile.getCurrentLanguage(), null), true, 2L);
/*    */     }
/* 52 */     else if (a_lId == -3L)
/*    */     {
/* 54 */       att = createNewAttribute(-3L, a_listManager.getListItem(a_dbTransaction, "label-completeness-nc", a_userProfile.getCurrentLanguage(), null), true, 4L);
/*    */     }
/* 60 */     else if (a_lId == -2L)
/*    */     {
/* 62 */       att = createNewAttribute(-2L, a_listManager.getListItem(a_dbTransaction, "label-approval-status-nc", a_userProfile.getCurrentLanguage(), null), true, 4L);
/*    */     }
/*    */ 
/* 69 */     return att;
/*    */   }
/*    */ 
/*    */   private static Attribute createNewAttribute(long a_lId, String a_sLabel, boolean a_bIsStatic, long a_lTypeId)
/*    */   {
/* 74 */     Attribute att = new Attribute();
/* 75 */     att.setId(a_lId);
/* 76 */     att.setLabel(a_sLabel);
/* 77 */     att.setStatic(a_bIsStatic);
/* 78 */     att.setTypeId(a_lTypeId);
/* 79 */     att.setIsVisible(true);
/* 80 */     att.setIsRelevant(true);
/* 81 */     return att;
/*    */   }
/*    */ 
/*    */   public static Vector getSearchBuilderOperators(ListManager a_listManager, Language a_language)
/*    */     throws Bn2Exception
/*    */   {
/* 88 */     Vector vOperators = new Vector(8);
/*    */ 
/* 90 */     vOperators.add(new StringDataBean(1L, a_listManager.getListItem("snippet-op-contains", a_language).getBody()));
/* 91 */     vOperators.add(new StringDataBean(2L, a_listManager.getListItem("snippet-op-not-contains", a_language).getBody()));
/* 92 */     vOperators.add(new StringDataBean(3L, a_listManager.getListItem("snippet-op-equal-to", a_language).getBody()));
/* 93 */     vOperators.add(new StringDataBean(4L, a_listManager.getListItem("snippet-op-not-equal-to", a_language).getBody()));
/* 94 */     vOperators.add(new StringDataBean(5L, a_listManager.getListItem("snippet-op-before", a_language).getBody()));
/* 95 */     vOperators.add(new StringDataBean(6L, a_listManager.getListItem("snippet-op-after", a_language).getBody()));
/* 96 */     vOperators.add(new StringDataBean(7L, a_listManager.getListItem("snippet-op-less-than", a_language).getBody()));
/* 97 */     vOperators.add(new StringDataBean(8L, a_listManager.getListItem("snippet-op-more-than", a_language).getBody()));
/*    */ 
/* 99 */     return vOperators;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.util.SearchBuilderUtil
 * JD-Core Version:    0.6.0
 */