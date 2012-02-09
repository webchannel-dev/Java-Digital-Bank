/*    */ package com.bright.assetbank.attribute.util;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.attribute.bean.Attribute;
/*    */ import com.bright.assetbank.attribute.bean.Attribute.Translation;
/*    */ import com.bright.framework.database.sql.ApplicationSql;
/*    */ import com.bright.framework.database.sql.SQLGenerator;
/*    */ import com.bright.framework.language.bean.Language;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class AttributeDBUtil
/*    */ {
/*    */   public static void addAttributeTranslation(ResultSet a_rs, Attribute a_attribute)
/*    */     throws SQLException
/*    */   {
/*    */    // Attribute tmp5_4 = a_attribute; tmp5_4.getClass(); Attribute.Translation translation = new Attribute.Translation(tmp5_4, new Language(a_rs.getLong("taLanguageId"), a_rs.getString("lName"), a_rs.getString("lCode")));
             Attribute tmp5_4 = a_attribute;
             tmp5_4.getClass(); 
             Attribute.Translation translation = a_attribute.new Translation(new Language(a_rs.getLong("taLanguageId"), a_rs.getString("lName"), a_rs.getString("lCode")));
/* 17 */     translation.setLabel(a_rs.getString("taLabel"));
/* 18 */     translation.setDefaultValue(a_rs.getString("taDefaultValue"));
/* 19 */     translation.setValueIfNotVisible(a_rs.getString("taValueIfNotVisible"));
/* 20 */     translation.setHelpText(a_rs.getString("taHelpText"));
/* 21 */     translation.setAltText(a_rs.getString("taAltText"));
/* 22 */     translation.setDisplayName(a_rs.getString("taDisplayName"));
/* 23 */     translation.setInputMask(a_rs.getString("taInputMask"));
/* 24 */     a_attribute.getTranslations().add(translation);
/*    */   }
/*    */ 
/*    */   public static Attribute buildAttribute(ResultSet a_rs)
/*    */     throws SQLException, Bn2Exception
/*    */   {
/* 30 */     Attribute attribute = new Attribute();
/*    */ 
/* 32 */     attribute.setId(a_rs.getLong("aId"));
/* 33 */     attribute.setTypeId(a_rs.getLong("AttributeTypeId"));
/* 34 */     attribute.setLabel(a_rs.getString("Label"));
/* 35 */     attribute.setMandatory(a_rs.getBoolean("IsMandatory"));
/* 36 */     attribute.setMandatoryBulkUpload(a_rs.getBoolean("IsMandatoryBulkUpload"));
/* 37 */     attribute.setDefaultValue(a_rs.getString("DefaultValue"));
/* 38 */     attribute.setSequence(a_rs.getInt("SequenceNumber"));
/* 39 */     attribute.setStatic(a_rs.getBoolean("IsStatic"));
/* 40 */     attribute.setReadOnly(a_rs.getBoolean("IsReadOnly"));
/* 41 */     attribute.setFieldName(a_rs.getString("StaticFieldName"));
/* 42 */     attribute.setKeywordSearchable(a_rs.getBoolean("IsKeywordSearchable"));
/* 43 */     attribute.setSearchField(a_rs.getBoolean("IsSearchField"));
/* 44 */     attribute.setSearchBuilderField(a_rs.getBoolean("IsSearchBuilderField"));
/* 45 */     attribute.setValueIfNotVisible(a_rs.getString("ValueIfNotVisible"));
/* 46 */     attribute.setMaxDisplayLength(a_rs.getInt("MaxDisplayLength"));
/* 47 */     attribute.setTreeId(a_rs.getLong("TreeId"));
/* 48 */     attribute.setHighlight(a_rs.getBoolean("Highlight"));
/* 49 */     attribute.setHelpText(a_rs.getString("HelpText"));
/* 50 */     attribute.setIsNameAttribute(a_rs.getBoolean("NameAttribute"));
/* 51 */     attribute.setIsDescriptionAttribute(a_rs.getBoolean("IsDescriptionAttribute"));
/* 52 */     attribute.setDefaultKeywordFilter(a_rs.getString("DefaultKeywordFilter"));
/* 53 */     attribute.setOnChangeScript(SQLGenerator.getInstance().getStringFromLargeTextField(a_rs, "OnChangeScript"));
/* 54 */     attribute.setIsHtml(a_rs.getBoolean("IsHtml"));
/* 55 */     attribute.setRequiredForCompleteness(a_rs.getBoolean("IsRequiredForCompleteness"));
/* 56 */     attribute.setRequiresTranslation(a_rs.getBoolean("RequiresTranslation"));
/* 57 */     attribute.setDisplayName(a_rs.getString("DisplayName"));
/* 58 */     attribute.setBaseUrl(a_rs.getString("BaseUrl"));
/* 59 */     attribute.setAltText(a_rs.getString("AltText"));
/* 60 */     attribute.setHasSearchTokens(a_rs.getBoolean("HasSearchTokens"));
/* 61 */     attribute.setTokenDelimiterRegex(a_rs.getString("TokenDelimiterRegex"));
/* 62 */     attribute.setHeight(a_rs.getInt("Height"));
/* 63 */     attribute.setWidth(a_rs.getInt("Width"));
/* 64 */     attribute.setMaxSize(a_rs.getInt("MaxSize"));
/* 65 */     attribute.setFiltered(a_rs.getBoolean("IsFiltered"));
/* 66 */     attribute.setSeed(a_rs.getLong("Seed"));
/* 67 */     attribute.setIncrement(a_rs.getInt("IncrementAmount"));
/* 68 */     attribute.setPrefix(a_rs.getString("Prefix"));
/* 69 */     attribute.setIsExtendableList(a_rs.getBoolean("IsExtendableList"));
/* 70 */     attribute.setDataFromChildren(a_rs.getBoolean("GetDataFromChildren"));
/* 71 */     attribute.setInputMask(a_rs.getString("InputMask"));
/* 72 */     attribute.setShowOnChild(a_rs.getBoolean("ShowOnChild"));
/* 73 */     attribute.setIncludeInSearchForChild(a_rs.getBoolean("IncludeInSearchForChild"));
/* 74 */     attribute.setHideForSort(a_rs.getBoolean("HideForSort"));
/* 75 */     attribute.setAutoComplete(a_rs.getBoolean("IsAutoComplete"));
/* 76 */     attribute.setPluginClass(a_rs.getString("PluginClass"));
/* 77 */     attribute.setAttributeIdsForPluginParams(a_rs.getString("PluginParamsAttributeIds"));
/* 78 */     attribute.setValueColumnName(a_rs.getString("ValueColumnName"));
/* 79 */     attribute.setShowOnDownload(a_rs.getBoolean("ShowOnDownload"));
/* 80 */     attribute.setMaxDecimalPlaces(a_rs.getInt("MaxDecimalPlaces"));
/* 81 */     attribute.setMinDecimalPlaces(a_rs.getInt("MinDecimalPlaces"));
/* 82 */     attribute.setHidden(a_rs.getBoolean("Hidden"));
/*    */ 
/* 86 */     return attribute;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.util.AttributeDBUtil
 * JD-Core Version:    0.6.0
 */