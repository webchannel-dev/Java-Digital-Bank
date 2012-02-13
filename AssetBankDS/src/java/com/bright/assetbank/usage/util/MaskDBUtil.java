/*    */ package com.bright.assetbank.usage.util;
/*    */ 
/*    */ import com.bright.assetbank.usage.bean.Mask;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class MaskDBUtil
/*    */ {
/*    */   public static final String c_ksMaskFields = "m.id AS maskId, m.name, m.filename, m.width, m.height";
/*    */ 
/*    */   public static Mask createMaskFromRS(ResultSet a_rs)
/*    */     throws SQLException
/*    */   {
/* 36 */     return populateMaskFromRS(new Mask(), a_rs);
/*    */   }
/*    */ 
/*    */   public static Mask populateMaskFromRS(Mask a_mask, ResultSet a_rs)
/*    */     throws SQLException
/*    */   {
/* 42 */     a_mask.setId(a_rs.getLong("maskId"));
/* 43 */     a_mask.setName(a_rs.getString("name"));
/* 44 */     a_mask.setFilename(a_rs.getString("filename"));
/* 45 */     a_mask.setWidth(a_rs.getInt("width"));
/* 46 */     a_mask.setHeight(a_rs.getInt("height"));
/* 47 */     return a_mask;
/*    */   }
/*    */ 
/*    */   public static int prepareMaskStatement(Mask a_mask, PreparedStatement a_psql, int a_iParam)
/*    */     throws SQLException
/*    */   {
/* 58 */     a_psql.setString(a_iParam++, a_mask.getName());
/* 59 */     a_psql.setString(a_iParam++, a_mask.getFilename());
/* 60 */     a_psql.setInt(a_iParam++, a_mask.getWidth());
/* 61 */     a_psql.setInt(a_iParam++, a_mask.getHeight());
/* 62 */     return a_iParam;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.util.MaskDBUtil
 * JD-Core Version:    0.6.0
 */