/*    */ package com.bright.assetbank.custom.indesign.asset.abplugin;
/*    */ 
/*    */ import com.bright.assetbank.custom.indesign.asset.bean.InDesignAsset;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class InDesignAssetDBUtil
/*    */ {
/*    */   public static final String c_ksInDesignAssetFields = "ida.IsTemplate, ida.TemplateAssetId, ida.InDesignDocumentId, ida.PDFStatusId";
/*    */ 
/*    */   public static InDesignAsset createInDesignAssetFromRS(ResultSet a_rs)
/*    */     throws SQLException
/*    */   {
/* 30 */     return populateInDesignAssetFromRS(new InDesignAsset(), a_rs);
/*    */   }
/*    */ 
/*    */   public static InDesignAsset populateInDesignAssetFromRS(InDesignAsset a_ida, ResultSet a_rs)
/*    */     throws SQLException
/*    */   {
/* 36 */     a_ida.setIsTemplate(a_rs.getBoolean("IsTemplate"));
/* 37 */     a_ida.setTemplateAssetId(a_rs.getLong("TemplateAssetId"));
/* 38 */     a_ida.setInDesignDocumentId(a_rs.getLong("InDesignDocumentId"));
/* 39 */     a_ida.setPDFStatusId(a_rs.getLong("PDFStatusId"));
/* 40 */     return a_ida;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.asset.abplugin.InDesignAssetDBUtil
 * JD-Core Version:    0.6.0
 */