package com.bright.framework.mail.constant;

public abstract interface MailConstants
{
  public static final int k_sMaxEmailParamLength = 4096;
  public static final String k_sParamName_Password = "password";
  public static final String k_sForward_ValidationFailure = "ValidationFailure";
  public static final String k_sTOEMAILParam = "TO_EMAIL";
  public static final String k_sREDIRECTSUCCESSParam = "REDIRECT_SUCCESS";
  public static final String k_sREDIRECTFAILUREParam = "REDIRECT_FAILURE";
  public static final String k_sEmailTemplateFileSuffix = ".xml";
  public static final String k_sTemplateParam = "template";
  public static final String k_sEmailTemplate_EmailThisPage = "email_this_page";
  public static final String k_sParamName_ReplacementSubject = "replacement_subject";
  public static final String k_sParamName_ReplacementBody = "replacement_body";
  public static final String k_sTemplateVariableStart = "#";
  public static final String k_sTemplateVariableEnd = "#";
  public static final String k_sEmailAddressSeparator = ";";
  public static final String k_sTemplateTerm = "template";
  public static final String k_sTemplateMessage = "message";
  public static final String k_sTemplateUrl = "url";
  public static final String k_sCharEncodingUTF8 = "UTF-8";
  public static final long k_lEmailTemplateTypeId_General = 1L;
  public static final long k_lEmailTemplateTypeId_Marketing = 2L;
  public static final int k_iHiddenStatus_Either = -1;
  public static final int k_iHiddenStatus_Hidden = 0;
  public static final int k_iHiddenStatus_Visible = 1;
  public static final String k_sEmailThisPage_LinkDefaultItem = "link-visit-the-page";
  public static final String k_sEmailThisPage_YourNameItem = "snippet-your-name";
  public static final String k_sDefaultFromEmail = "noreply@noreply.com";
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.constant.MailConstants
 * JD-Core Version:    0.6.0
 */