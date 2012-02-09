package com.bright.assetbank.workflow.constant;

import com.bright.framework.workflow.constant.WorkflowConstants;

public abstract interface AssetWorkflowConstants extends WorkflowConstants
{
  public static final String k_sWorkflowApprovedGroup = "approvers";
  public static final String k_sWorkflowOwner = "owner";
  public static final String k_sWorkflowParamAssetId = "assetId";
  public static final String k_sWorkflowParamWorkflowInfoId = "workflowInfoId";
  public static final String k_sWorkflowParamTransitionNum = "transition";
  public static final String k_sWorkflowParamMessage = "message";
  public static final int k_iApprovalStatus_Any = -1;
  public static final int k_iApprovalStatus_None = 1;
  public static final int k_iApprovalStatus_Partial = 2;
  public static final int k_iApprovalStatus_Full = 3;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.constant.AssetWorkflowConstants
 * JD-Core Version:    0.6.0
 */