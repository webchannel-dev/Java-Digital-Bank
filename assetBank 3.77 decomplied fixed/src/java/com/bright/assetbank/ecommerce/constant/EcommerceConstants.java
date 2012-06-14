package com.bright.assetbank.ecommerce.constant;

import com.bright.framework.constant.FrameworkConstants;

public abstract interface EcommerceConstants extends FrameworkConstants
{
  public static final String k_sEnumPurchaseOffline = "offline";
  public static final String k_sEnumPurchaseOnline = "online";
  public static final String k_sForward_Ready = "Ready";
  public static final String k_sForward_Login = "Login";
  public static final String k_sForward_Tscs = "Tscs";
  public static final String k_sForward_CommercialTscs = "CommercialTscs";
  public static final String k_sForward_Register = "Register";
  public static final String k_sForward_Shipping = "Shipping";
  public static final String k_sForward_NoPaymentRequired = "NoPaymentRequired";
  public static final String k_sForward_MixedCommercial = "MixedCommercial";
  public static final String k_sForward_NotAuthorised = "NotAuthorisedFailure";
  public static final String k_sForward_ZeroPrice = "ZeroPrice";
  public static final String k_sForward_OfflineRegister = "OfflineRegister";
  public static final String k_sForward_DoOffline = "DoOffline";
  public static final String k_sForward_PSPInvalidResponse = "PSPInvalidResponse";
  public static final String k_sForward_PSPAmountCheckFailure = "PSPAmountCheckFailure";
  public static final String k_sForward_PSPCancelResponse = "PSPCancelResponse";
  public static final String k_sForward_PaymentReturnBasket = "Basket";
  public static final String k_sForward_PaymentReturnSubscription = "Subscription";
  public static final String k_sForward_NoUserNotes = "NoUserNotes";
  public static final String k_sParam_EmailAddress = "email";
  public static final String k_sParam_TransId = "trans_id";
  public static final String k_sParam_PSPTransId = "transId";
  public static final String k_sParam_Checkout = "checkout";
  public static final String k_sParamValue_CheckoutRestart = "checkout";
  public static final String k_sParamValue_CheckoutRegister = "register";
  public static final String k_sParam_AcceptTCs = "accepttcs";
  public static final String k_sParam_AcceptCommercialTCs = "acceptcommercialtcs";
  public static final String k_sParamOrderStatusId = "orderStatus";
  public static final String k_sParam_OrderId = "orderId";
  public static final String k_sParam_AssetId = "assetId";
  public static final String k_sParam_PriceBandId = "priceBandId";
  public static final String k_sParam_CommercialOptionPurchaseEditType = "commercialOptionPurchaseEditType";
  public static final String k_sParamValue_Reset = "reset";
  public static final String k_sParamValue_SaveOptions = "saveOptions";
  public static final String k_sParamValue_SaveNotes = "saveNotes";
  public static final String k_sParam_OrderSearchCache = "orderSearchCache";
  public static final String k_sParam_ForwardOnPaymentStatus = "forwardOnStatus";
  public static final int k_iReportType_OrderDetail = 1;
  public static final int k_iReportType_Uploaders = 2;
  public static final int k_iReportType_Subscription = 3;
  public static final int k_iReportType_UserSubscription = 4;
  public static final String k_sForward_OrderDetailReport = "OrderDetailReport";
  public static final String k_sForward_UploadersReport = "UploadersReport";
  public static final String k_sForward_SubscriptionReport = "SubscriptionReport";
  public static final String k_sForward_UserSubscriptionReport = "UserSubscriptionReport";
  public static final String k_sSettingName_PurchaseId = "PurchaseId";
  public static final int k_iOrderStatus_Fulfilled = 1;
  public static final int k_iOrderStatus_AwaitingFulfillment = 2;
  public static final int k_iOrderStatus_RequiresApproval = 3;
  public static final int k_iOrderStatus_RequiresOnlinePayment = 4;
  public static final int k_iOrderStatus_RequiresOfflinePayment = 5;
  public static final int k_iOrderStatus_PaidForOnline = 6;
  public static final int k_iOrderStatus_PaidForOffline = 7;
  public static final int k_iOrderStatus_Declined = 8;
  public static final int k_iOrderStatusWorkflow_Personal = 1;
  public static final int k_iOrderStatusWorkflow_Commercial = 2;
  public static final int k_iShippingCalculation_PerPrint = 1;
  public static final int k_iShippingCalculation_PerOrder = 2;
  public static final String k_sMessageSetting_PasswordHidden = "snippet-password-hidden";
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.constant.EcommerceConstants
 * JD-Core Version:    0.6.0
 */