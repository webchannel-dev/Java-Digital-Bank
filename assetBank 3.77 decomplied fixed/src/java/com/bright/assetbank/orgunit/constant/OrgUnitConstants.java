package com.bright.assetbank.orgunit.constant;

import com.bright.assetbank.application.constant.AssetBankConstants;

public abstract interface OrgUnitConstants extends AssetBankConstants
{
  public static final String k_sParamOrgUnitId = "ouid";
  public static final String k_sParamUpdateChildWorkflows = "updateChildWorkflows";
  public static final int k_iPermissionNone = 0;
  public static final int k_iDownloadPermissionForUsersOnCategory = 2;
  public static final int k_iUploadPermissionForUsersOnCategory = 0;
  public static final int k_iDownloadPermissionForAdminsOnCategory = 7;
  public static final int k_iUploadPermissionForAdminsOnCategory = 12;
  public static final String k_sOrgUnitContentPageIdentifierPrefix = "orgunit-content-page-";
  public static final String k_sOrgUnitContentPageTitlePrefix = "Org unit content page: ";
  public static final String k_sOrgUnitContentMenuIdentifierPrefix = "orgunit-content-menu-";
  public static final String k_sOrgUnitContentMenuTitlePrefix = "Org unit content menu item: ";
  public static final String k_sOrgUnitContentMenuBodySuffix = " Content";
  public static final long k_lContentPurposeId_ExtraContentPage = 1L;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.constant.OrgUnitConstants
 * JD-Core Version:    0.6.0
 */