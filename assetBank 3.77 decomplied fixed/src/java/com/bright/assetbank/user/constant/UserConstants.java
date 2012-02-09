package com.bright.assetbank.user.constant;

public abstract interface UserConstants
{
  public static final String SECURITY_VIOLATION_KEY = "SecurityViolation";
  public static final String NO_PROFILE_ADDRESS_KEY = "NoProfileAddress";
  public static final long k_lUserNotLoggedInId = 0L;
  public static final long k_lLoggedInUsersGroupId = 1L;
  public static final long k_lPublicUsersGroupId = 2L;
  public static final long k_lCustomFieldId_Telephone = 1001L;
  public static final String k_sSubmitParamName = "submit";
  public static final String k_sCancelParam = "Cancel";
  public static final String CANCEL_KEY = "Cancel";
  public static final String k_sAdminViewingCompanyIdParam = "adminViewingCompanyId";
  public static final String k_sParamName_OrderId = "orderId";
  public static final String k_sParamName_UserId = "userId";
  public static final int k_iUserSearchOrderId_Name_Asc = 1;
  public static final int k_iUserSearchOrderId_Name_Desc = 2;
  public static final int k_iUserSearchOrderId_Username_Asc = 3;
  public static final int k_iUserSearchOrderId_Username_Desc = 4;
  public static final int k_iUserSearchOrderId_Email_Asc = 5;
  public static final int k_iUserSearchOrderId_Email_Desc = 6;
  public static final int k_iUserSearchOrderId_Group_Asc = 7;
  public static final int k_iUserSearchOrderId_Group_Desc = 8;
  public static final int k_iUserSearchOrderId_Organisation_Asc = 9;
  public static final int k_iUserSearchOrderId_Organisation_Desc = 10;
  public static final int k_iUserSearchOrderId_ExpiryDate_Asc = 11;
  public static final int k_iUserSearchOrderId_ExpiryDate_Desc = 12;
  public static final int k_iUserSearchOrderId_RegDate_Asc = 13;
  public static final int k_iUserSearchOrderId_RegDate_Desc = 14;
  public static final String k_sParam_Group = "group";
  public static final String k_sParam_CategoryPermission = "catPermission";
  public static final String k_sParam_CategoryPermissionUpload = "catUploadPermission";
  public static final String k_sParam_CategoryPermissionDownloadOriginal = "catDownloadOriginal";
  public static final String k_sParam_CategoryPermissionDownloadAdvanced = "catDownloadAdvanced";
  public static final String k_sParam_CategoryPermissionReviewAssets = "catReviewAssets";
  public static final String k_sParam_CategoryPermissionViewRestricted = "catViewRestrictedAssets";
  public static final String k_sParam_CategoryPermissionEditSubcategories = "catEditSubcategories";
  public static final String k_sParam_CategoryPermissionApprovalForHighRes = "catHighResApproval";
  public static final String k_sRolesSecurityFileName = "role_security.xml";
  public static final String k_sGroupSecurityXMLElement_AllowedRoles = "allowed_roles";
  public static final String k_sGroupSecurityXMLElement_Secured = "secured";
  public static final String k_sGroupSecurityXMLAttribute_Type = "type";
  public static final String k_sGroupSecurityType_Action = "action";
  public static final int k_iUserRoleStatus_NoneDefined = -1;
  public static final int k_iUserRoleStatus_DefinedNotPresent = 0;
  public static final int k_iUserRoleStatus_DefinedPresent = 1;
  public static final String k_sParam_Role = "role";
  public static final int k_iPermissionLevel_None = 0;
  public static final int k_iPermissionLevel_Visible = 1;
  public static final int k_iPermissionLevel_Downloadable = 2;
  public static final int k_iPermissionLevel_Editable = 3;
  public static final int k_iPermissionLevel_Deletable = 4;
  public static final int k_iPermissionLevel_DownloadableWithApproval = 5;
  public static final int k_iPermissionLevel_UpdateWithApproval = 6;
  public static final int k_iPermissionLevel_Approvable = 7;
  public static final int k_iPermissionLevel_DownloadOriginal = 8;
  public static final int k_iPermissionLevel_DownloadAdvanced = 9;
  public static final int k_iPermissionLevel_EditSubcategories = 10;
  public static final int k_iPermissionLevel_ReviewAssets = 11;
  public static final int k_iPermissionLevel_ApprovableUploads = 12;
  public static final int k_iPermissionLevel_ViewRestrictedAssets = 13;
  public static final int k_iPermissionLevel_ApprovalRequiredForHighRes = 14;
  public static final int k_iPermissionLevel_MAX = 14;
  public static final int k_iAttributeVisibilityLevel_Hidden = 1;
  public static final int k_iAttributeVisibilityLevel_Visible = 2;
  public static final int k_iAttributeVisibilityLevel_Writable = 3;
  public static final int k_iRandomPasswordLength = 8;
  public static final String k_sParamNameStart_RegInfo = "regInfo_";
  public static final String k_sParamNamePrefix_VisibleAttribute = "visibleAttribute_";
  public static final String k_sParamNamePrefix_Excluded = "excluded_";
  public static final String k_sApplicationUserUsername = "application";
  public static final String k_sParam_ReloadPage = "reloadPage";
  public static final String k_sForward_Reload = "Reload";
  public static final String k_sParam_InvitedBy = "invitedBy";
  public static final String k_sParam_UserId = "userId";
  public static final String k_sParam_Approve = "approve";
  public static final String k_sParam_OrgUnitId = "orgUnitId";
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.constant.UserConstants
 * JD-Core Version:    0.6.0
 */