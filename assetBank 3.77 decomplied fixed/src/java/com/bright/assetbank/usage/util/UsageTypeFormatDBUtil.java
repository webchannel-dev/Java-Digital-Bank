/*    */ package com.bright.assetbank.usage.util;
/*    */ 
/*    */ import com.bright.assetbank.usage.bean.UsageTypeFormat;
/*    */ import com.bright.framework.database.util.DBUtil;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class UsageTypeFormatDBUtil
/*    */ {
/*    */   public static int prepareUsageTypeFormatStatement(UsageTypeFormat a_format, PreparedStatement a_psql, int a_iCol)
/*    */     throws SQLException
/*    */   {
/* 39 */     a_psql.setString(a_iCol++, a_format.getDescription());
/* 40 */     a_psql.setInt(a_iCol++, a_format.getWidth());
/* 41 */     a_psql.setInt(a_iCol++, a_format.getHeight());
/* 42 */     a_psql.setLong(a_iCol++, a_format.getFormatId());
/* 43 */     a_psql.setBoolean(a_iCol++, a_format.getScaleUp());
/* 44 */     a_psql.setInt(a_iCol++, a_format.getDensity());
/* 45 */     a_psql.setInt(a_iCol++, a_format.getJpegQuality());
/* 46 */     a_psql.setString(a_iCol++, a_format.getPreserveFormatList());
/* 47 */     a_psql.setBoolean(a_iCol++, a_format.getApplyStrip());
/* 48 */     a_psql.setBoolean(a_iCol++, a_format.getCropToFit());
/* 49 */     a_psql.setBoolean(a_iCol++, a_format.getOmitIfLowerRes());
/* 50 */     DBUtil.setFieldIdOrNull(a_psql, a_iCol++, a_format.getColorSpace());
/* 51 */     a_psql.setBoolean(a_iCol++, a_format.getWatermark());
/* 52 */     a_psql.setBoolean(a_iCol++, a_format.getAllowMasking());
/* 53 */     DBUtil.setFieldIdOrNull(a_psql, a_iCol++, a_format.getPresetMaskId());
/* 54 */     DBUtil.setFieldIdOrNull(a_psql, a_iCol++, a_format.getPresetMaskColourId());
/* 55 */     return a_iCol;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.util.UsageTypeFormatDBUtil
 * JD-Core Version:    0.6.0
 */