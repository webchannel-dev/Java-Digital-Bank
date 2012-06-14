/*    */ package com.bright.assetbank.custom.indesign.entity.abplugin;
/*    */ 
/*    */ import com.bright.assetbank.custom.indesign.entity.bean.InDesignAssetEntity;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class InDesignAssetEntityDBUtil
/*    */ {
/*    */   public static final String c_ksInDesignAssetEntityFields = "idae.InDesignAssetEntityTypeId";
/*    */   public static final String c_ksInDesignAssetEntityOuterJoinFields = "idae.AssetEntityId";
/*    */ 
/*    */   public static InDesignAssetEntity createInDesignAssetEntityFromRSOuterJoin(ResultSet a_rs)
/*    */     throws SQLException
/*    */   {
/* 35 */     a_rs.getLong("AssetEntityId");
/* 36 */     if (a_rs.wasNull())
/*    */     {
/* 38 */       return null;
/*    */     }
/*    */ 
/* 42 */     return createInDesignAssetEntityFromRS(a_rs);
/*    */   }
/*    */ 
/*    */   public static InDesignAssetEntity createInDesignAssetEntityFromRS(ResultSet a_rs)
/*    */     throws SQLException
/*    */   {
/* 49 */     return populateInDesignAssetEntityFromRS(new InDesignAssetEntity(), a_rs);
/*    */   }
/*    */ 
/*    */   public static InDesignAssetEntity populateInDesignAssetEntityFromRS(InDesignAssetEntity a_idae, ResultSet a_rs)
/*    */     throws SQLException
/*    */   {
/* 55 */     a_idae.setInDesignAssetEntityTypeId(a_rs.getInt("InDesignAssetEntityTypeId"));
/* 56 */     return a_idae;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.entity.abplugin.InDesignAssetEntityDBUtil
 * JD-Core Version:    0.6.0
 */