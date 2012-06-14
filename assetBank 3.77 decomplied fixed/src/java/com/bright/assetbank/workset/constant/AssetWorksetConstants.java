package com.bright.assetbank.workset.constant;

public abstract interface AssetWorksetConstants
{
  public static final int k_iSubmitOptionDoNotSubmit = 2;
  public static final int k_iSubmitOptionPutLive = 0;
  public static final int k_iSubmitOptionLeaveLive = 4;
  public static final int k_iSubmitOptionSendForApproval = 1;
  public static final int k_iSubmitOptionLeaveUnnapproved = 3;
  public static final int k_iSubmitOptionLeaveInCurrentState = 5;
  public static final int k_iSubmitOptionLeaveInCurrentStateButMoveLiveToApproval = 6;
  public static final int k_iUnsubmittedItemOptionPutLive = 0;
  public static final int k_iUnsubmittedItemOptionSendForApproval = 1;
  public static final int k_iUnsubmittedItemOptionDelete = 2;
  public static final String k_sParamUnsubmitted = "unsubmitted";
  public static final String k_sParamWFRemoved = "wfRemoved";
  public static final String k_sParamOption = "option";
  public static final String k_sParamSelectedSubmitOption = "selectedSubmitOption";
  public static final String k_sWorkflowSpecificSubmitOptionSeparator = "_";
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workset.constant.AssetWorksetConstants
 * JD-Core Version:    0.6.0
 */