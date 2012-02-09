/*    */ package com.bright.assetbank.custom.indesign.pdf.service;
/*    */ 
/*    */ import com.bright.assetbank.custom.indesign.pdf.bean.InDesignPDFQuality;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class InDesignPDFQualityDBUtil
/*    */ {
/*    */   public static final String c_ksInDesignPDFQualityFields = "idpq.Id AS idpqId, idpq.Name";
/*    */ 
/*    */   public static InDesignPDFQuality createInDesignPDFQualityFromRS(ResultSet a_rs)
/*    */     throws SQLException
/*    */   {
/* 28 */     return populateInDesignPDFQualityFromRS(new InDesignPDFQuality(), a_rs);
/*    */   }
/*    */ 
/*    */   public static InDesignPDFQuality populateInDesignPDFQualityFromRS(InDesignPDFQuality a_pdfQuality, ResultSet a_rs)
/*    */     throws SQLException
/*    */   {
/* 34 */     a_pdfQuality.setId(a_rs.getInt("idpqId"));
/* 35 */     a_pdfQuality.setName(a_rs.getString("Name"));
/* 36 */     return a_pdfQuality;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.pdf.service.InDesignPDFQualityDBUtil
 * JD-Core Version:    0.6.0
 */