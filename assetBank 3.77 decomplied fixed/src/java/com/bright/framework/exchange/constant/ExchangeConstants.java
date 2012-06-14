package com.bright.framework.exchange.constant;

public abstract interface ExchangeConstants
{
  public static final int k_iSentReceivedType_Sent = 1;
  public static final int k_iSentReceivedType_Received = 1;
  public static final int k_iSentReceivedType_SentAndReceieved = 1;
  public static final String k_sInboxName = "Inbox";
  public static final String k_sSentItemsName = "Sent Items";
  public static final String k_sCalendarName = "Calendar";
  public static final String k_sExchangeName = "Exchange";
  public static final String k_sCalendarBusyStatus_Busy = "Busy";
  public static final String k_sCalendarBusyStatus_Free = "Free";
  public static final String k_sCalendarBusyStatus_Tentative = "Tentative";
  public static final String k_sCalendarBusyStatus_OutOfOffice = "OOF";
  public static final int k_iEmailFieldCharLimit = 10000;
  public static final String k_sRecipientListDelimiter = ",";
  public static final long c_klLDAPSearchResultsNoLimit = 0L;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.exchange.constant.ExchangeConstants
 * JD-Core Version:    0.6.0
 */