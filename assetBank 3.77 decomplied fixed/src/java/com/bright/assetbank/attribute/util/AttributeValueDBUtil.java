/*     */ package com.bright.assetbank.attribute.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue.Translation;
/*     */ import com.bright.framework.common.bean.BrightDate;
/*     */ import com.bright.framework.common.bean.BrightDateTime;
/*     */ import com.bright.framework.database.bean.JDBCValue;
/*     */ import com.bright.framework.database.exception.SQLStatementException;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.database.util.DBUtil;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class AttributeValueDBUtil
/*     */ {
/*     */   public static void insertAssetAttributeValues(Connection a_con, long a_lAssetId, Map<Long, Attribute> a_attributesById, Map<Long, JDBCValue> a_valuesByAttributeId)
/*     */     throws SQLStatementException
/*     */   {
/*  62 */     insertAssetAttributeValues(a_con, a_lAssetId, -1L, a_attributesById, a_valuesByAttributeId);
/*     */   }
/*     */ 
/*     */   public static void insertAssetAttributeValues(Connection a_con, long a_lAssetId, long a_lLanguageId, Map<Long, Attribute> a_attributesById, Map<Long, JDBCValue> a_valuesByAttributeId)
/*     */     throws SQLStatementException
/*     */   {
/*  86 */     if (!haveNonEmptyValue(a_valuesByAttributeId.values()))
/*     */     {
/*  88 */       return;
/*     */     }
/*     */ 
/*  91 */     boolean bTranslated = a_lLanguageId > 0L;
/*     */ 
/*  93 */     StringBuilder sbColumns = new StringBuilder();
/*  94 */     List<JDBCValue> values = new ArrayList();
/*  95 */     for (Map.Entry entry : a_valuesByAttributeId.entrySet())
/*     */     {
/*  97 */       Long attributeId = (Long)entry.getKey();
/*  98 */       JDBCValue value = (JDBCValue)entry.getValue();
/*  99 */       Attribute attribute = (Attribute)a_attributesById.get(attributeId);
/*     */ 
/* 103 */       if ((!bTranslated) || (attribute.getIsTranslatable()))
/*     */       {
/* 106 */         sbColumns.append(",");
/* 107 */         sbColumns.append(attribute.getValueColumnName());
/*     */ 
/* 109 */         values.add(value);
/*     */       }
/*     */     }
/*     */ 
/* 113 */     if (values.isEmpty())
/*     */     {
/* 115 */       return;
/*     */     }
/*     */ 
/* 118 */     String sValuesPlaceholders = "," + DBUtil.getPlaceholders(values.size());
/*     */ 
/* 120 */     String sSQL = "INSERT INTO AssetAttributeValues (AssetId" + sbColumns + ") " + "VALUES (?" + sValuesPlaceholders + ")";
/*     */     try
/*     */     {
/* 128 */       PreparedStatement psql = a_con.prepareStatement(sSQL);
/* 129 */       int iParam = 1;
/* 130 */       psql.setLong(iParam++, a_lAssetId);
/* 131 */       if (bTranslated)
/*     */       {
/* 133 */         psql.setLong(iParam++, a_lLanguageId);
/*     */       }
/* 135 */       for (JDBCValue value : values)
/*     */       {
/* 137 */         if (value.getValue() != null)
/*     */         {
/* 139 */           psql.setObject(iParam++, value.getValue(), value.getJDBCType());
/*     */         }
/*     */         else
/*     */         {
/* 143 */           psql.setNull(iParam++, value.getJDBCType());
/*     */         }
/*     */       }
/*     */ 
/* 147 */       psql.executeUpdate();
/*     */ 
/* 149 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 153 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void updateAssetAttributeValues(Connection a_con, long a_lAssetId, Map<Long, Attribute> a_attributesById, Map<Long, JDBCValue> a_valuesByAttributeId)
/*     */     throws SQLStatementException
/*     */   {
/* 170 */     updateAssetAttributeValues(a_con, a_lAssetId, -1L, a_attributesById, a_valuesByAttributeId);
/*     */   }
/*     */ 
/*     */   public static void updateAssetAttributeValues(Connection a_con, long a_lAssetId, long a_lLanguageId, Map<Long, Attribute> a_attributesById, Map<Long, JDBCValue> a_valuesByAttributeId)
/*     */     throws SQLStatementException
/*     */   {
/* 191 */     if (!haveNonEmptyValue(a_valuesByAttributeId.values()))
/*     */     {
/* 193 */       deleteAssetAttributeValues(a_con, a_lAssetId, a_lLanguageId);
/* 194 */       return;
/*     */     }
/*     */ 
/* 205 */     boolean bTranslated = a_lLanguageId > 0L;
/*     */ 
/* 207 */     StringBuilder sbSQL = new StringBuilder();
/* 208 */     sbSQL.append("UPDATE ");
/* 209 */     sbSQL.append(bTranslated ? "TranslatedAssetAttributeValues" : "AssetAttributeValues");
/*     */ 
/* 212 */     sbSQL.append(" SET ");
/*     */ 
/* 214 */     List<JDBCValue> values = new ArrayList();
/* 215 */     for (Map.Entry entry : a_valuesByAttributeId.entrySet())
/*     */     {
/* 217 */       Long attributeId = (Long)entry.getKey();
/* 218 */       JDBCValue value = (JDBCValue)entry.getValue();
/* 219 */       Attribute attribute = (Attribute)a_attributesById.get(attributeId);
/*     */ 
/* 223 */       if ((!bTranslated) || (attribute.getIsTranslatable()))
/*     */       {
/* 226 */         sbSQL.append(attribute.getValueColumnName());
/* 227 */         sbSQL.append("=?");
/*     */ 
/* 229 */         sbSQL.append(",");
/*     */ 
/* 231 */         values.add(value);
/*     */       }
/*     */     }
/*     */ 
/* 235 */     sbSQL.setLength(sbSQL.length() - 1);
/*     */ 
/* 239 */     if (values.isEmpty())
/*     */     {
/* 241 */       return;
/*     */     }
/*     */ 
/* 244 */     sbSQL.append(" WHERE AssetId=?");
/* 245 */     if (bTranslated)
/*     */     {
/* 247 */       sbSQL.append(" AND LanguageId=?");
/*     */     }
/*     */ 
/* 250 */     String sSQL = sbSQL.toString();
/*     */     try
/*     */     {
/* 254 */       PreparedStatement psql = a_con.prepareStatement(sSQL);
/* 255 */       int iParam = 1;
/*     */ 
/* 257 */       for (JDBCValue value : values)
/*     */       {
/* 259 */         if (value.getValue() != null)
/*     */         {
/* 261 */           psql.setObject(iParam++, value.getValue(), value.getJDBCType());
/*     */         }
/*     */         else
/*     */         {
/* 265 */           psql.setNull(iParam++, value.getJDBCType());
/*     */         }
/*     */       }
/*     */ 
/* 269 */       psql.setLong(iParam++, a_lAssetId);
/* 270 */       if (bTranslated)
/*     */       {
/* 272 */         psql.setLong(iParam++, a_lLanguageId);
/*     */       }
/*     */ 
/* 275 */       int rowsUpdated = psql.executeUpdate();
/*     */ 
/* 277 */       psql.close();
/*     */ 
/* 279 */       if (rowsUpdated == 0)
/*     */       {
/* 284 */         insertAssetAttributeValues(a_con, a_lAssetId, a_lLanguageId, a_attributesById, a_valuesByAttributeId);
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 289 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void deleteAssetAttributeValues(Connection a_con, long a_lAssetId, long a_lLanguageId)
/*     */     throws SQLStatementException
/*     */   {
/* 297 */     boolean bTranslated = a_lLanguageId > 0L;
/*     */ 
/* 299 */     String sSQL = bTranslated ? "DELETE FROM TranslatedAssetAttributeValues WHERE AssetId=? AND LanguageId=?" : "DELETE FROM AssetAttributeValues WHERE AssetId=?";
/*     */     try
/*     */     {
/* 305 */       PreparedStatement psql = a_con.prepareStatement(sSQL);
/* 306 */       psql.setLong(1, a_lAssetId);
/* 307 */       if (bTranslated)
/*     */       {
/* 309 */         psql.setLong(2, a_lLanguageId);
/*     */       }
/*     */ 
/* 312 */       psql.executeUpdate();
/*     */ 
/* 314 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 318 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static boolean haveNonEmptyValue(Collection<JDBCValue> a_jdbcValues)
/*     */   {
/* 328 */     for (JDBCValue jdbcValue : a_jdbcValues)
/*     */     {
/* 330 */       Object value = jdbcValue.getValue();
/* 331 */       if ((value != null) && (!"".equals(value)))
/*     */       {
/* 333 */         return true;
/*     */       }
/*     */     }
/* 336 */     return false;
/*     */   }
/*     */ 
/*     */   public static List<AttributeValue> buildPerAssetAttributeValues(ResultSet a_rs, List<Attribute> a_dynamicAttributes)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/* 355 */     List attributeValues = new ArrayList();
/*     */ 
/* 357 */     for (Attribute attribute : a_dynamicAttributes)
/*     */     {
/* 359 */       AttributeValue av = new AttributeValue();
/* 360 */       av.setAttribute(attribute);
/* 361 */       if (a_rs != null)
/*     */       {
/* 363 */         populateAttributeValueFromValueColumn(av, a_rs, attribute);
/*     */       }
/* 365 */       attributeValues.add(av);
/*     */     }
/*     */ 
/* 368 */     return attributeValues;
/*     */   }
/*     */ 
/*     */   public static void populateAttributeValueFromValueColumn(AttributeValue a_av, ResultSet a_rs, Attribute a_attribute)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/* 378 */     String sColumnName = a_attribute.getValueColumnName();
/*     */    // String sValue;
/*     */     String sValue;
/* 381 */     if (a_attribute.getIsDatepicker())
/*     */     {
/* 383 */       java.util.Date dateValue = a_rs.getDate(sColumnName);
/*     */       //String sValue;
/* 385 */       if (dateValue != null)
/*     */       {
/* 388 */         BrightDate bd = new BrightDate(dateValue);
/* 389 */         sValue = bd.getFormDate();
/*     */       }
/*     */       else
/*     */       {
/* 393 */         sValue = null;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*     */       //String sValue;
/* 397 */       if (a_attribute.getIsDateTime())
/*     */       {
/* 399 */         java.util.Date dateValue = DBUtil.getDateOrNull(a_rs, sColumnName);
/*     */         //String sValue;
/* 401 */         if (dateValue != null)
/*     */         {
/* 404 */           BrightDateTime bd = new BrightDateTime(dateValue);
/* 405 */           sValue = bd.getFormDateTime();
/*     */         }
/*     */         else
/*     */         {
/* 409 */           sValue = null;
/*     */         }
/*     */       }
/* 412 */       else if (a_attribute.getIsExternalDictionary())
/*     */       {
/* 414 */         String sPacked = SQLGenerator.getInstance().getStringFromLargeTextField(a_rs, sColumnName);
/* 415 */         String[] valueAndAdditionalValue = AttributeUtil.unpackExternalDictionaryIDsAndValues(sPacked);
/* 416 */         sValue = valueAndAdditionalValue[0];
/* 417 */         a_av.setAdditionalValue(valueAndAdditionalValue[1]);
/*     */       }
/*     */       else
/*     */       {
/*     */         //String sValue;
/* 419 */         if (a_attribute.getIsNumeric())
/*     */         {
/* 421 */           BigDecimal bdValue = a_rs.getBigDecimal(a_attribute.getValueColumnName());
/*     */           //String sValue;
/* 422 */           if (bdValue != null)
/*     */           {
/* 424 */             NumberFormat format = new DecimalFormat("0.#");
/* 425 */             format.setMaximumFractionDigits(a_attribute.getMaxDecimalPlaces());
/* 426 */             format.setMinimumFractionDigits(a_attribute.getMinDecimalPlaces());
/* 427 */             sValue = format.format(bdValue);
/*     */           }
/*     */           else
/*     */           {
/* 431 */             sValue = "";
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 436 */           sValue = getTextValueFromValueColumn(a_rs, a_attribute);
/*     */         }
/*     */       }
/*     */     }
/* 438 */     a_av.setValue(sValue);
/*     */   }
/*     */ 
/*     */   public static String getTextValueFromValueColumn(ResultSet a_rs, Attribute a_attribute)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/* 454 */     String sColumnName = a_attribute.getValueColumnName();
/*     */     String sValue;
/*     */     //String sValue;
/* 455 */     if ((a_attribute.getIsTextareaLong()) || (a_attribute.getIsTextfieldLong()))
/*     */     {
/* 457 */       sValue = SQLGenerator.getInstance().getStringFromLargeTextField(a_rs, sColumnName);
/*     */     }
/*     */     else
/*     */     {
/* 461 */       sValue = a_rs.getString(sColumnName);
/*     */     }
/* 463 */     return sValue;
/*     */   }
/*     */ 
/*     */   public static String getIsNullOrEmptySQL(Attribute a_attribute)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/* 469 */     ApplicationSql sqlGenerator = SQLGenerator.getInstance();
/* 470 */     if ((a_attribute.getIsTextareaLong()) || (a_attribute.getIsTextfieldLong()))
/*     */     {
/* 473 */       return "(" + sqlGenerator.getNullCheckStatement(a_attribute.getValueColumnName()) + " OR " + sqlGenerator.getLengthUpperBoundFunctionForLargeTextField(a_attribute.getValueColumnName()) + "=0)";
/*     */     }
/*     */ 
/* 476 */     if ((a_attribute.getIsDatepicker()) || (a_attribute.getIsDateTime()) || (a_attribute.getIsExternalDictionary()))
/*     */     {
/* 481 */       return sqlGenerator.getNullCheckStatement(a_attribute.getValueColumnName());
/*     */     }
/*     */ 
/* 486 */     return "(" + sqlGenerator.getNullCheckStatement(a_attribute.getValueColumnName()) + " OR " + a_attribute.getValueColumnName() + "='')";
/*     */   }
/*     */ 
/*     */   public static JDBCValue jdbcValueFromAttributeValue(AttributeValue a_av)
/*     */   {
/* 503 */     Attribute attribute = a_av.getAttribute();
/* 504 */     if (attribute.getIsDatepicker())
/*     */     {
/* 506 */       java.util.Date date = a_av.getDateValue().getDate();
/* 507 */       java.sql.Date sqlDate = date == null ? null : new java.sql.Date(date.getTime());
/* 508 */       return new JDBCValue(91, sqlDate);
/*     */     }
/* 510 */     if (attribute.getIsDateTime())
/*     */     {
/* 512 */       java.util.Date date = a_av.getDateTimeValue().getDate();
/* 513 */       Timestamp timestamp = date == null ? null : new Timestamp(date.getTime());
/* 514 */       return new JDBCValue(93, timestamp);
/*     */     }
/* 516 */     if (attribute.getIsExternalDictionary())
/*     */     {
/* 518 */       String sPacked = AttributeUtil.packExternalDictionaryIDsAndValues(a_av.getValue(), a_av.getAdditionalValue());
/* 519 */       return new JDBCValue(12, sPacked);
/*     */     }
/* 521 */     if (attribute.getIsNumeric())
/*     */     {
/* 523 */       return new JDBCValue(3, getBigDecimalValueOrNull(a_av.getValue()));
/*     */     }
/*     */ 
/* 527 */     return new JDBCValue(12, a_av.getValue());
/*     */   }
/*     */ 
/*     */   private static BigDecimal getBigDecimalValueOrNull(String a_sValue)
/*     */   {
/* 533 */     if (StringUtils.isEmpty(a_sValue))
/*     */     {
/* 535 */       return null;
/*     */     }
/* 537 */     return new BigDecimal(a_sValue);
/*     */   }
/*     */ 
/*     */   public static JDBCValue jdbcValueFromAttributeValueTranslation(AttributeValue.Translation a_translation)
/*     */   {
/* 548 */     return new JDBCValue(12, a_translation.getValue());
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.util.AttributeValueDBUtil
 * JD-Core Version:    0.6.0
 */