/*    */ package com.bright.assetbank.custom.indesign.asset.abplugin;
/*    */ 
/*    */ import com.bright.assetbank.custom.indesign.asset.bean.InDesignDocument;
/*    */ import com.bright.framework.database.util.DBUtil;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class InDesignDocumentDBUtil
/*    */ {
/*    */   public static final String c_ksInDesignDocumentNonPKFields = "idd.InDesignPDFQualityId, idd.FileLocation, idd.OriginalFilename, idd.FileSizeInBytes, idd.NumSymbols";
/*    */   public static final String c_ksInDesignDocumentNonPKFieldsWithoutAlias = "InDesignPDFQualityId, FileLocation, OriginalFilename, FileSizeInBytes, NumSymbols";
/*    */   public static final String c_ksInDesignDocumentFields = "idd.Id AS iddId, idd.InDesignPDFQualityId, idd.FileLocation, idd.OriginalFilename, idd.FileSizeInBytes, idd.NumSymbols";
/*    */   public static final String c_ksInDesignDocumentNonPKPlaceholders = "?, ?, ?, ?, ?";
/*    */ 
/*    */   public static InDesignDocument createInDesignDocumentFromRSOuterJoin(ResultSet a_rs)
/*    */     throws SQLException
/*    */   {
/* 39 */     a_rs.getLong("iddId");
/* 40 */     if (a_rs.wasNull())
/*    */     {
/* 42 */       return null;
/*    */     }
/*    */ 
/* 46 */     return createInDesignDocumentFromRS(a_rs);
/*    */   }
/*    */ 
/*    */   public static InDesignDocument createInDesignDocumentFromRS(ResultSet a_rs)
/*    */     throws SQLException
/*    */   {
/* 53 */     return populateInDesignDocumentFromRS(new InDesignDocument(), a_rs);
/*    */   }
/*    */ 
/*    */   public static InDesignDocument populateInDesignDocumentFromRS(InDesignDocument a_indd, ResultSet a_rs)
/*    */     throws SQLException
/*    */   {
/* 59 */     a_indd.setId(a_rs.getLong("iddId"));
/* 60 */     a_indd.setInDesignPDFQualityId(a_rs.getInt("InDesignPDFQualityId"));
/* 61 */     a_indd.setFileLocation(a_rs.getString("FileLocation"));
/* 62 */     a_indd.setOriginalFilename(a_rs.getString("OriginalFilename"));
/* 63 */     a_indd.setFileSizeInBytes(a_rs.getLong("FileSizeInBytes"));
/* 64 */     a_indd.setNumSymbols(a_rs.getInt("NumSymbols"));
/* 65 */     return a_indd;
/*    */   }
/*    */ 
/*    */   public static int prepareInDesignDocumentNonPK(PreparedStatement a_psql, int a_param, InDesignDocument a_indd)
/*    */     throws SQLException
/*    */   {
/* 71 */     a_psql.setInt(a_param++, a_indd.getInDesignPDFQualityId());
/* 72 */     a_psql.setString(a_param++, a_indd.getFileLocation());
/* 73 */     a_psql.setString(a_param++, a_indd.getOriginalFilename());
/* 74 */     DBUtil.setFieldLongOrNull(a_psql, a_param++, a_indd.getFileSizeInBytes());
/* 75 */     a_psql.setInt(a_param++, a_indd.getNumSymbols());
/*    */ 
/* 77 */     return a_param;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.asset.abplugin.InDesignDocumentDBUtil
 * JD-Core Version:    0.6.0
 */