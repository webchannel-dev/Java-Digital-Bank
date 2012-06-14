/*     */ package com.bright.framework.database.util;
/*     */ 
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ import com.bright.framework.util.CollectionUtil;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.List;
/*     */ import org.apache.avalon.excalibur.datasource.ProxiedJdbcConnection;
/*     */ import org.apache.commons.collections.Predicate;
/*     */ 
/*     */ public class DBUtil
/*     */ {
/*     */   public static void setFieldIdOrNull(PreparedStatement a_psql, int a_iFieldNumber, DataBean a_dataBean)
/*     */     throws SQLException
/*     */   {
/*  70 */     if (a_dataBean != null)
/*     */     {
/*  72 */       setFieldIdOrNull(a_psql, a_iFieldNumber, a_dataBean.getId());
/*     */     }
/*     */     else
/*     */     {
/*  76 */       a_psql.setNull(a_iFieldNumber, -5);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setFieldIdOrNull(PreparedStatement a_psql, int a_iFieldNumber, Category a_category)
/*     */     throws SQLException
/*     */   {
/*  91 */     if (a_category != null)
/*     */     {
/*  93 */       setFieldIdOrNull(a_psql, a_iFieldNumber, a_category.getId());
/*     */     }
/*     */     else
/*     */     {
/*  97 */       a_psql.setNull(a_iFieldNumber, -5);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setFieldIdOrNull(PreparedStatement a_psql, int a_iFieldNumber, long a_lId)
/*     */     throws SQLException
/*     */   {
/* 117 */     if (a_lId > 0L)
/*     */     {
/* 119 */       a_psql.setLong(a_iFieldNumber, a_lId);
/*     */     }
/*     */     else
/*     */     {
/* 123 */       a_psql.setNull(a_iFieldNumber, -5);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setFieldLongOrNull(PreparedStatement a_psql, int a_iFieldNumber, long a_lValue)
/*     */     throws SQLException
/*     */   {
/* 143 */     if (a_lValue > 0L)
/*     */     {
/* 145 */       a_psql.setLong(a_iFieldNumber, a_lValue);
/*     */     }
/*     */     else
/*     */     {
/* 149 */       a_psql.setNull(a_iFieldNumber, -5);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setFieldIntOrNull(PreparedStatement a_psql, int a_iFieldNumber, int a_iValue)
/*     */     throws SQLException
/*     */   {
/* 169 */     if (a_iValue > 0)
/*     */     {
/* 171 */       a_psql.setInt(a_iFieldNumber, a_iValue);
/*     */     }
/*     */     else
/*     */     {
/* 175 */       a_psql.setNull(a_iFieldNumber, -5);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setFieldDoubleOrNull(PreparedStatement a_psql, int a_iFieldNumber, Double a_value)
/*     */     throws SQLException
/*     */   {
/* 196 */     if (a_value != null)
/*     */     {
/* 198 */       a_psql.setDouble(a_iFieldNumber, a_value.doubleValue());
/*     */     }
/*     */     else
/*     */     {
/* 202 */       a_psql.setNull(a_iFieldNumber, 8);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setFieldDoubleOrNull(PreparedStatement a_psql, int a_iFieldNumber, double a_value)
/*     */     throws SQLException
/*     */   {
/* 223 */     if (a_value != 0.0D)
/*     */     {
/* 225 */       a_psql.setDouble(a_iFieldNumber, a_value);
/*     */     }
/*     */     else
/*     */     {
/* 229 */       a_psql.setNull(a_iFieldNumber, 8);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setFieldBigDecimalOrNull(PreparedStatement a_psql, int a_iFieldNumber, BigDecimal a_value)
/*     */     throws SQLException
/*     */   {
/* 250 */     if (a_value != null)
/*     */     {
/* 252 */       a_psql.setBigDecimal(a_iFieldNumber, a_value);
/*     */     }
/*     */     else
/*     */     {
/* 256 */       a_psql.setNull(a_iFieldNumber, 3);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setFieldDateOrNull(PreparedStatement a_psql, int a_iFieldNumber, java.util.Date a_dtDate)
/*     */     throws SQLException
/*     */   {
/* 276 */     if (a_dtDate != null)
/*     */     {
/* 278 */       a_psql.setDate(a_iFieldNumber, new java.sql.Date(a_dtDate.getTime()));
/*     */     }
/*     */     else
/*     */     {
/* 282 */       a_psql.setNull(a_iFieldNumber, 91);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setFieldTimestampOrNull(PreparedStatement a_psql, int a_iFieldNumber, java.util.Date a_dtDate)
/*     */     throws SQLException
/*     */   {
/* 302 */     if (a_dtDate != null)
/*     */     {
/* 304 */       a_psql.setTimestamp(a_iFieldNumber, new Timestamp(a_dtDate.getTime()));
/*     */     }
/*     */     else
/*     */     {
/* 308 */       a_psql.setNull(a_iFieldNumber, 93);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static java.util.Date getDateOrNull(ResultSet a_rs, String a_sColumnName)
/*     */     throws SQLException
/*     */   {
/* 325 */     Timestamp ts = a_rs.getTimestamp(a_sColumnName);
/* 326 */     return ts == null ? null : new java.util.Date(ts.getTime());
/*     */   }
/*     */ 
/*     */   public static Integer getIntOrNull(ResultSet a_rs, String a_columnName)
/*     */     throws SQLException
/*     */   {
/* 339 */     int i = a_rs.getInt(a_columnName);
/* 340 */     return a_rs.wasNull() ? null : Integer.valueOf(i);
/*     */   }
/*     */ 
/*     */   public static Long getLongOrNull(ResultSet a_rs, String a_columnName)
/*     */     throws SQLException
/*     */   {
/* 353 */     long l = a_rs.getLong(a_columnName);
/* 354 */     return a_rs.wasNull() ? null : Long.valueOf(l);
/*     */   }
/*     */ 
/*     */   public static Float getFloatOrNull(ResultSet a_rs, String a_columnName)
/*     */     throws SQLException
/*     */   {
/* 368 */     float f = a_rs.getFloat(a_columnName);
/* 369 */     return a_rs.wasNull() ? null : Float.valueOf(f);
/*     */   }
/*     */ 
/*     */   public static Double getDoubleOrNull(ResultSet a_rs, String a_columnName)
/*     */     throws SQLException
/*     */   {
/* 382 */     double d = a_rs.getDouble(a_columnName);
/* 383 */     return a_rs.wasNull() ? null : Double.valueOf(d);
/*     */   }
/*     */ 
/*     */   public static String safeSQLStringValue(String a_source)
/*     */   {
/* 400 */     String result = null;
/*     */ 
/* 402 */     result = a_source.replaceAll("'", "''");
/*     */ 
/* 404 */     return result;
/*     */   }
/*     */ 
/*     */   public static DataBean getFirstDataBeanWithId(List a_list, long a_lId)
/*     */   {
/* 422 */     return (DataBean)CollectionUtil.getFirstMatch(a_list, new DataBeanHasId(a_lId));
/*     */   }
/*     */ 
/*     */   public static String getPlaceholders(int a_count)
/*     */   {
/* 472 */     if (a_count == 0)
/*     */     {
/* 474 */       return "";
/*     */     }
/*     */ 
/* 477 */     StringBuilder sbResult = new StringBuilder();
/*     */ 
/* 480 */     for (int i = 1; i < a_count; i++)
/*     */     {
/* 482 */       sbResult.append("?,");
/*     */     }
/*     */ 
/* 485 */     sbResult.append("?");
/*     */ 
/* 487 */     return sbResult.toString();
/*     */   }
/*     */ 
/*     */   public static Connection unwrapConnection(Connection a_con)
/*     */   {
/* 503 */     if (Proxy.isProxyClass(a_con.getClass()))
/*     */     {
/* 505 */       InvocationHandler ih = Proxy.getInvocationHandler(a_con);
/* 506 */       if ((ih instanceof ProxiedJdbcConnection))
/*     */       {
/* 508 */         a_con = ((ProxiedJdbcConnection)ih).getConnection();
/*     */       }
/*     */     }
/*     */ 
/* 512 */     return a_con;
/*     */   }
/*     */ 
/*     */   public static class DataBeanHasId
/*     */     implements Predicate
/*     */   {
/* 439 */     private long m_lId = 0L;
/*     */ 
/*     */     public DataBeanHasId(long a_lId)
/*     */     {
/* 443 */       this.m_lId = a_lId;
/*     */     }
/*     */ 
/*     */     public boolean evaluate(Object a_obj)
/*     */     {
/* 457 */       return ((DataBean)a_obj).getId() == this.m_lId;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.database.util.DBUtil
 * JD-Core Version:    0.6.0
 */