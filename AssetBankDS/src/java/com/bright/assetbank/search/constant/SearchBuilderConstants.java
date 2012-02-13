package com.bright.assetbank.search.constant;

public abstract interface SearchBuilderConstants
{
  public static final int k_iMaxSearchBuilderClauses = 10;
  public static final long k_lPseudoAttributeId_Keywords = 0L;
  public static final long k_lPseudoAttributeId_AgreementText = -1L;
  public static final long k_lPseudoAttributeId_ApprovalStatus = -2L;
  public static final long k_lPseudoAttributeId_Completeness = -3L;
  public static final long k_lPseudoAttributeId_AddedByUserId = -4L;
  public static final long k_lPseudoAttributeId_AssetTypeId = -5L;
  public static final int k_iOperatorId_Contains = 1;
  public static final int k_iOperatorId_DoesNotContain = 2;
  public static final int k_iOperatorId_EqualTo = 3;
  public static final int k_iOperatorId_NotEqualTo = 4;
  public static final int k_iOperatorId_Before = 5;
  public static final int k_iOperatorId_After = 6;
  public static final int k_iOperatorId_LessThan = 7;
  public static final int k_iOperatorId_MoreThan = 8;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.constant.SearchBuilderConstants
 * JD-Core Version:    0.6.0
 */