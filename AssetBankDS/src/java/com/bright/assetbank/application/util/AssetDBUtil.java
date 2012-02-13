/*    */ package com.bright.assetbank.application.util;
/*    */ 
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.application.bean.FileFormat;
/*    */ import com.bright.framework.image.bean.ImageFile;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class AssetDBUtil
/*    */ {
/*    */   public static void addImageFiles(ResultSet a_rs, Asset a_asset)
/*    */     throws SQLException
/*    */   {
/* 34 */     if ((a_rs.getString("assThumbnailFileLocation") == null) || (a_rs.getString("assThumbnailFileLocation").trim().length() == 0))
/*    */     {
/* 37 */       a_asset.setThumbnailImageFile(new ImageFile(a_asset.getFormat().getThumbnailImageLocation()));
/*    */     }
/*    */     else
/*    */     {
/* 41 */       a_asset.setThumbnailImageFile(new ImageFile(a_rs.getString("assThumbnailFileLocation")));
/*    */     }
/*    */ 
/* 45 */     if ((a_rs.getString("assSmallFileLocation") != null) && (a_rs.getString("assSmallFileLocation").trim().length() > 0))
/*    */     {
/* 48 */       a_asset.setHomogenizedImageFile(new ImageFile(a_rs.getString("assSmallFileLocation")));
/*    */     }
/*    */ 
/* 52 */     if ((a_rs.getString("assMediumFileLocation") != null) && (a_rs.getString("assMediumFileLocation").trim().length() > 0))
/*    */     {
/* 55 */       a_asset.setPreviewImageFile(new ImageFile(a_rs.getString("assMediumFileLocation")));
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.util.AssetDBUtil
 * JD-Core Version:    0.6.0
 */