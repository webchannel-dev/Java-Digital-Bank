package com.bright.assetbank.approval.constant;

public abstract interface AssetApprovalConstants
{
  public static final long k_lAssetApprovalPendingApprovalId = 1L;
  public static final long k_lAssetApprovalApprovedId = 2L;
  public static final long k_lAssetApprovalRejectedId = 3L;
  public static final long k_lAssetApprovalHighResId = 4L;
  public static final String k_sFieldNamePrefixApprovalStatus = "approvalstatus_";
  public static final String k_sFieldNamePrefixApprovalAdminNotes = "adminnotes_";
  public static final String k_sFieldNamePrefixApprovalUserNotes = "usernotes_";
  public static final String k_sFieldNamePrefixApprovalDateExpiry = "approvalexpiry_";
  public static final String k_sFieldNamePrefixInclude = "include_";
  public static final String k_sFieldNamePrefixUsageTypeId = "usageTypeId_";
  public static final int k_iFieldLength_AdminNotes = 4000;
  public static final String k_sParamName_FromCatId = "fromCatId";
  public static final String k_sParamName_ToCatId = "toCatId";
  public static final String k_sParamName_Action = "action_";
  public static final int k_iMoveAction_Reject = 0;
  public static final int k_iMoveAction_Approve = 2;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.approval.constant.AssetApprovalConstants
 * JD-Core Version:    0.6.0
 */