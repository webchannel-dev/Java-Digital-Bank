/*    */ package com.bright.assetbank.usage.util;
/*    */ 
/*    */ import com.bright.assetbank.usage.bean.NamedColour;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class NamedColourDBUtil
/*    */ {
/*    */   public static final String c_ksNamedColourFields = "nc.id AS ncId, nc.name AS ncName, nc.red, nc.green, nc.blue";
/*    */ 
/*    */   public static NamedColour createNamedColourFromRS(ResultSet a_rs)
/*    */     throws SQLException
/*    */   {
/* 36 */     return populateNamedColourFromRS(new NamedColour(), a_rs);
/*    */   }
/*    */ 
/*    */   public static NamedColour populateNamedColourFromRS(NamedColour a_mask, ResultSet a_rs)
/*    */     throws SQLException
/*    */   {
/* 42 */     a_mask.setId(a_rs.getLong("ncId"));
/* 43 */     a_mask.setName(a_rs.getString("ncName"));
/* 44 */     a_mask.setRed(a_rs.getInt("red"));
/* 45 */     a_mask.setGreen(a_rs.getInt("green"));
/* 46 */     a_mask.setBlue(a_rs.getInt("blue"));
/* 47 */     return a_mask;
/*    */   }
/*    */ 
/*    */   public static int prepareNamedColourStatement(NamedColour a_mask, PreparedStatement a_psql, int a_iParam)
/*    */     throws SQLException
/*    */   {
/* 58 */     a_psql.setString(a_iParam++, a_mask.getName());
/* 59 */     a_psql.setInt(a_iParam++, a_mask.getRed());
/* 60 */     a_psql.setInt(a_iParam++, a_mask.getGreen());
/* 61 */     a_psql.setInt(a_iParam++, a_mask.getBlue());
/* 62 */     return a_iParam;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.util.NamedColourDBUtil
 * JD-Core Version:    0.6.0
 */