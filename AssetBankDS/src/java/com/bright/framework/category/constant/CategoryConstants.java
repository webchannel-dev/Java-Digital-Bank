package com.bright.framework.category.constant;

public abstract interface CategoryConstants
{
  public static final long k_lTopCategoryLevelId = -1L;
  public static final long k_lDescriptiveCategoryType = 1L;
  public static final long k_lAccessLevelCategoryType = 2L;
  public static final int k_iDBLength_Name = 255;
  public static final int k_iDBLength_Summary = 255;
  public static final String k_sTableName_CategoryTree = "CM_Category";
  public static final String k_sTableName_ItemInCategory = "CM_ItemInCategory";
  public static final String k_sCategoryTypeIdParam = "categoryTypeId";
  public static final String k_sCategoryIdParam = "categoryId";
  public static final String k_sCategoryParentIdParam = "parentId";
  public static final String k_sCategoryIdToMoveParam = "catIdToMove";
  public static final String k_sCancelParam = "cancel";
  public static final String k_sCatParam = "cat";
  public static final String k_sNameParam = "name";
  public static final String k_sSubmitParam = "submit";
  public static final String k_sRootParam = "root";
  public static final String k_sParam_AllowAdvancedOptions = "allowAdvancedOptions";
  public static final String k_sMoveDirectionParam = "direction";
  public static final String k_sParamValue_DirectionUp = "up";
  public static final String k_sParamValue_DirectionDown = "down";
  public static final String k_sParamName_TreeId = "treeId";
  public static final String k_sAction_Browse = "browseItems";
  public static final String k_sAction_Admin = "viewCategoryAdmin";
  public static final String k_sCategoryFullNameDivider = "/";
  public static final String k_sForwardAccessLevel = "AccessLevel";
  public static final String k_sPermissionCatIdString_Delimiter = ",";
  public static final String k_sRootCategoryName = "Root";
  public static final String k_sImportSearchableNo = "no";
  public static final String k_sImportSearchableFalse = "false";
  public static final String k_sCategoryLogo = "logo";
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.constant.CategoryConstants
 * JD-Core Version:    0.6.0
 */