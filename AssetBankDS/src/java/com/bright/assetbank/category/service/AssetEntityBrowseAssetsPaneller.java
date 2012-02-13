/*     */ package com.bright.assetbank.category.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.category.bean.Panel;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.plugin.iface.ABPlugin;
/*     */ import com.bright.assetbank.plugin.service.PluginSPI;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.CollectionUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class AssetEntityBrowseAssetsPaneller extends DefaultBrowseAssetsPaneller
/*     */   implements ABPlugin, AssetBankConstants
/*     */ {
/*     */   public void startup(PluginSPI a_spi)
/*     */     throws Bn2Exception
/*     */   {
/*  47 */     a_spi.setBrowseAssetsPaneller(this);
/*     */   }
/*     */ 
/*     */   public List<Panel> getPanels(UserProfile a_userProfile, List<LightweightAsset> a_colAssets, List<Panel> a_panels, Category a_category, boolean a_bListView)
/*     */   {
/*  62 */     boolean bError = false;
/*  63 */     DBTransaction transaction = null;
                String[] aPanelBreakdowns;
/*     */     try
/*     */     {
/*  68 */       String sPanelBreakdown = AssetBankSettings.getAssetEntityBrowsePanelBreakdown();
/*     */ 
/*  70 */       if (StringUtil.stringIsPopulated(sPanelBreakdown))
/*     */       {
/*  72 */         aPanelBreakdowns = sPanelBreakdown.split(":");
/*     */ 
/*  74 */         if ((aPanelBreakdowns != null) && (aPanelBreakdowns.length > 0))
/*     */         {
/*  77 */           ArrayList list = new ArrayList();
/*  78 */           boolean bSetPrePanelisedAssets = false;
/*  79 */           ArrayList alAllEntityIds = new ArrayList();
/*     */ 
/*  82 */           for (String sBreakdown : aPanelBreakdowns)
/*     */           {
/*  86 */             if (sBreakdown.equals("*"))
/*     */             {
/*  88 */               list.addAll(a_panels);
/*  89 */               bSetPrePanelisedAssets = true;
/*     */             }
/*     */             else
/*     */             {
/*  94 */               String[] aPair = sBreakdown.split("!");
/*  95 */               String[] aEntityIds = aPair[1].split(",");
/*  96 */               alAllEntityIds.addAll(StringUtil.convertToSetOfLongs(aEntityIds));
/*     */ 
/*  98 */               if ((aEntityIds == null) || (aEntityIds.length <= 0) || (a_colAssets == null))
/*     */                 continue;
/* 100 */               Panel panel = new Panel((aPanelBreakdowns.length > 1) || ((a_panels != null) && (a_panels.size() > 0)), aPair[0]);
/* 101 */               panel.setVisibilityStatus(2);
/* 102 */               panel.setListView(a_bListView);
/* 103 */               panel.setAddItemParameters("entityRestrictions=" + aPair[1]);
/*     */ 
/* 105 */               ArrayList alPanelAssets = new ArrayList();
/* 106 */               List remainingAssets = new ArrayList();
/*     */ 
/* 108 */               for (LightweightAsset asset : a_colAssets)
/*     */               {
/* 110 */                 boolean bFound = false;
/* 111 */                 for (String sEntityId : aEntityIds)
/*     */                 {
/* 113 */                   long lEntityId = Long.parseLong(sEntityId);
/* 114 */                   if (asset.getEntity().getId() != lEntityId) {
/*     */                     continue;
/*     */                   }
/* 117 */                   alPanelAssets.add(asset);
/* 118 */                   bFound = true;
/* 119 */                   break;
/*     */                 }
/*     */ 
/* 123 */                 if (!bFound)
/*     */                 {
/* 125 */                   remainingAssets.add(asset);
/*     */                 }
/*     */               }
/*     */ 
/* 129 */               a_colAssets = remainingAssets;
/* 130 */               panel.setAssets(alPanelAssets);
/* 131 */               list.add(panel);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 136 */           if (!bSetPrePanelisedAssets)
/*     */           {
/* 138 */             list.addAll(a_panels);
/*     */           }
/*     */ 
/* 143 */           DBTransactionManager transactionManager = (DBTransactionManager)(DBTransactionManager)GlobalApplication.getInstance().getComponentManager().lookup("DBTransactionManager");
/* 144 */           transaction = transactionManager.getCurrentOrNewTransaction();
/* 145 */           AssetEntityManager entityManager = (AssetEntityManager)(AssetEntityManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetEntityManager");
/* 146 */           ArrayList alEntityIds = entityManager.getAllEntityIds(transaction, true);

                    ListManager listManager ;
/* 147 */           if (!CollectionUtil.isSubSetOf(alAllEntityIds, alEntityIds))
/*     */           {
/* 149 */             listManager = (ListManager)GlobalApplication.getInstance().getComponentManager().lookup("ListManager");
/* 150 */             String sHeader = listManager.getListItem(transaction, "snippet-other", a_userProfile.getCurrentLanguage()).getBody();
/* 151 */             sHeader = sHeader + " " + listManager.getListItem(transaction, "items", a_userProfile.getCurrentLanguage()).getBody();
/* 152 */             Panel panel = new Panel(list.size() > 0, sHeader);
/* 153 */             panel.setVisibilityStatus(2);
/* 154 */             panel.setListView(a_bListView);
/* 155 */             panel.setAssets(a_colAssets);
/* 156 */             list.add(panel);
/*     */           }
/*     */ 
/* 159 */           //listManager = list;
                        //jsr 82;
                    return list;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */       //                aPanelBreakdowns;
/*     */      // ListManager listManager;
/* 167 */       bError = true;
/* 168 */       GlobalApplication.getInstance().getLogger().error(getClass().getSimpleName() + ": Error: ", e);
/* 169 */       return super.getPanels(a_userProfile, a_colAssets, a_panels, a_category, a_bListView);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 175 */         if ((transaction != null) && (bError))
/*     */         {
/* 177 */           transaction.rollback();
/*     */         }
/* 179 */         else if (transaction != null)
/*     */         {
/* 181 */           transaction.commit();
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 186 */         GlobalApplication.getInstance().getLogger().error(getClass().getSimpleName() + ": Error closing transaction: ", e);
/*     */       }
/*     */     }
/*     */ 
/* 190 */     return super.getPanels(a_userProfile, a_colAssets, a_panels, a_category, a_bListView);
/*     */   }
/*     */ 
/*     */   public boolean requiresPaging()
/*     */   {
/* 195 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.service.AssetEntityBrowseAssetsPaneller
 * JD-Core Version:    0.6.0
 */