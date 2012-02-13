package com.bright.assetbank.usage.constant;

public abstract interface UsageConstants
{
  public static final int k_iReportType_UsageTypes = 1;
  public static final int k_iReportType_ViewsGroup = 2;
  public static final int k_iReportType_ViewsUser = 3;
  public static final int k_iReportType_ViewsImage = 14;
  public static final int k_iReportType_UploadsGroup = 4;
  public static final int k_iReportType_UploadsUser = 5;
  public static final int k_iReportType_UploadsUserGroup = 6;
  public static final int k_iReportType_Downloads = 7;
  public static final int k_iReportType_DownloadsUser = 8;
  public static final int k_iReportType_DownloadsUserGroup = 9;
  public static final int k_iReportType_UserDownloads = 10;
  public static final int k_iReportType_GroupDownloads = 11;
  public static final int k_iReportType_ReasonForDownload = 12;
  public static final int k_iReportType_DownloadsByUploader = 13;
  public static final int k_iReportType_ViewsAccessLevel = 15;
  public static final int k_iReportType_DownloadsAccessLevel = 16;
  public static final int k_iReportType_MostViewed = 1;
  public static final int k_iReportType_LeastViewed = 2;
  public static final int k_iReportType_MostDownloaded = 3;
  public static final int k_iReportType_LeastDownloaded = 4;
  public static final int k_iNumPopularAssetsToShow = 100;
  public static final String k_sForward_UsageReport = "UsageReport";
  public static final String k_sForward_ViewsReport = "ViewsReport";
  public static final String k_sForward_UploadsReport = "UploadsReport";
  public static final String k_sForward_DownloadsByUploaderReport = "DownloadsByUploaderReport";
  public static final String k_sForward_DownloadsReport = "DownloadsReport";
  public static final String k_sForward_UserGroupDownloadsReport = "UserGroupDownloadsReport";
  public static final String k_sForward_ReasonForDownloadReport = "ReasonForDownloadReport";
  public static final String k_sForward_ScheduleReport = "ScheduleReport";
  public static final String k_sForward_DownloadReport = "DownloadReport";
  public static final String k_sForward_AccessLevelReport = "AccessLevelReport";
  public static final String k_sForward_CancelReport = "Cancel";
  public static final String k_sUnassignedUsersGroup = "Unassigned Users";
  public static final String k_sStartDateParam = "startDateString";
  public static final String k_sEndDateParam = "endDateString";
  public static final String k_sReportType = "reportType";
  public static final String k_sParam_ParentId = "parentId";
  public static final String k_sParam_ReportFrequency = "reportFrequency";
  public static final String k_sParam_ReportType = "reportType";
  public static final String k_sParam_ReportId = "reportId";
  public static final String k_sParam_ReportName = "reportName";
  public static final String k_sParam_CancelScheduled = "Cancel";
  public static final String k_sParam_ReportOption = "reportOption";
  public static final String k_sparam_Force = "force";
  public static final String k_sReportFrequency_Daily = "Daily";
  public static final String k_sReportFrequency_Weekly = "Weekly";
  public static final String k_sReportFrequency_Monthly = "Monthly";
  public static final String k_sReport_FileName = "usage_report.xls";
  public static final String k_sButton_ViewReport = "View report";
  public static final String k_sButton_ScheduleReport = "Schedule report";
  public static final int k_iColorSpace_RGB = 1;
  public static final int k_iColorSpace_CMYK = 2;
  public static final int k_iColorSpace_Greyscale = 3;
  public static final String k_sParam_SecondaryUsagePrefix = "secondary_";
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.constant.UsageConstants
 * JD-Core Version:    0.6.0
 */