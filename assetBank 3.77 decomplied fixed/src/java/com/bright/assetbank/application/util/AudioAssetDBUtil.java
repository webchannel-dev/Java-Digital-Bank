/*    */ package com.bright.assetbank.application.util;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.bean.AudioAsset;
/*    */ import com.bright.assetbank.application.bean.FileFormat;
/*    */ import com.bright.assetbank.application.service.IAssetManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.util.FileUtil;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class AudioAssetDBUtil
/*    */ {
/*    */   public static final String c_ksAudioAssetFields = "audioa.PreviewClipLocation audPreviewClipLocation, audioa.EmbeddedPreviewClipLocation audEmbeddedPreviewClipLocation, audioa.Duration audDuration ";
/*    */ 
/*    */   public static void populateAudioAssetFromRS(AudioAsset a_audio, DBTransaction a_transaction, IAssetManager a_assetManager, ResultSet a_rs)
/*    */     throws SQLException, Bn2Exception
/*    */   {
/* 36 */     a_audio.setPreviewClipLocation(a_rs.getString("audPreviewClipLocation"));
/* 37 */     a_audio.setEmbeddedPreviewClipLocation(a_rs.getString("audEmbeddedPreviewClipLocation"));
/* 38 */     a_audio.setDuration(a_rs.getLong("audDuration"));
/*    */ 
/* 41 */     if ((a_audio.getPreviewClipLocation() != null) && (a_audio.getPreviewClipLocation().length() > 0))
/*    */     {
/* 43 */       String sExt = FileUtil.getSuffix(a_audio.getPreviewClipLocation());
/* 44 */       FileFormat format = a_assetManager.getFileFormatForExtension(a_transaction, sExt);
/* 45 */       a_audio.setPreviewClipFormat(format);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.util.AudioAssetDBUtil
 * JD-Core Version:    0.6.0
 */