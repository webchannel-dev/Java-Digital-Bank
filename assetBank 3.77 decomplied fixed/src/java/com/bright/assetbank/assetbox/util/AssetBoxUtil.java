/*    */ package com.bright.assetbank.assetbox.util;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.approval.bean.AssetInList;
/*    */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import java.util.Collection;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class AssetBoxUtil
/*    */   implements AssetBankConstants
/*    */ {
/*    */   public static Vector<Asset> getAssetsFromAssetBox(ABUserProfile a_userProfile, boolean a_bGetAll, long a_lTypeId)
/*    */     throws Bn2Exception
/*    */   {
/* 44 */     Vector vecAssets = new Vector();
/*    */ 
/* 47 */     AssetBox assetBox = a_userProfile.getAssetBox();
/*    */ 
/* 49 */     if (assetBox != null)
/*    */     {
/* 51 */       Collection<AssetInList> assetBoxAssets = assetBox.getAllAssetsSorted();
/* 52 */       if (assetBoxAssets != null)
/*    */       {
/* 54 */         for (AssetInList abAsset : assetBoxAssets)
/*    */         {
/* 57 */           if ((a_bGetAll) || (abAsset.getIsSelected()))
/*    */           {
/* 59 */             if ((a_lTypeId <= 0L) || (a_lTypeId == abAsset.getAsset().getTypeId()))
/*    */             {
/* 61 */               vecAssets.add(abAsset.getAsset());
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 69 */     return vecAssets;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.util.AssetBoxUtil
 * JD-Core Version:    0.6.0
 */