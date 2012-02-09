package com.bright.assetbank.search.constant;

public abstract interface AssetBankSearchConstants
{
  public static final String k_sIndexFieldValue_None = "0";
  public static final String k_sIndexFieldValue_Empty = "isempty";
  public static final String k_sIndexFieldValue_Untranslated = " untranslated";
  public static final String k_sIndexFieldValue_FALSE = "0";
  public static final String k_sIndexFieldValue_TRUE = "1";
  public static final long k_lCompleteOrIncomplete = 1L;
  public static final long k_lCompleteOnly = 2L;
  public static final long k_lIncompleteOnly = 3L;
  public static final int k_iAllTypesId = -999;
  public static final String k_sParam_IgnoreAutoFilter = "ignoreAutomaticFiltering";
  public static final String k_sParam_RefineSearch = "refineSearch";
  public static final String k_sParamName_SearchBuilder = "searchBuilder";
  public static final String k_sAttributeName_SearchBuilder = "searchBuilder";
  public static final String k_sNoCriteriaForward = "NoCriteria";
  public static final String k_sForward_Current = "SuccessCurrent";
  public static final String k_sForward_Recent = "SuccessRecent";
  public static final String k_sForward_SearchForm = "SearchForm";
  public static final String k_sForward_SearchBuilder = "SearchBuilder";
  public static final String k_sForward_ReindexStatus = "ReindexStatus";
  public static final String k_sParam_SearchThisCat = "searchThisCat";
  public static final String k_sParam_SearchThisCatType = "searchThisCatType";
  public static final String k_sParam_SavedSearchName = "savedSearchName";
  public static final String k_sParam_SearchCriteria = "searchCriteria";
  public static final String k_sParam_QuickSearch = "quickSearch";
  public static final int k_iMaxSortFieldLength = 50;
  public static final long k_lPseudoSortAttributeId_Views = -1L;
  public static final long k_lPseudoSortAttributeId_Downloads = -2L;
  public static final long k_lPseudoSortAttributeId_Votes = -3L;
  public static final String k_sSettingName_LastSavedSearchAlert = "LastSavedSearchAlert";
  public static final String k_sCandidateSerializedCriteriaFilename = "search.obj";
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.constant.AssetBankSearchConstants
 * JD-Core Version:    0.6.0
 */