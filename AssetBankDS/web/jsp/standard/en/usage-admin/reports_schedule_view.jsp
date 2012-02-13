<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matthew Woollard	28-Sep-2007		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="daysBeforeMonths" settingName="days-before-months" />
<bright:applicationSetting id="dateFormatHelpString" settingName="date-format-help-string" />
<bright:applicationSetting id="recordSearches" settingName="record-searches" />
<bright:applicationSetting id="ecommerce" settingName="ecommerce" />




<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Scheduled reports</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script src="../js/calendar.js" type="text/javascript"></script>
	
	<bean:define id="section" value="reports"/>
	<bean:define id="helpsection" value="scheduled_reports"/>
	<bean:define id="pagetitle" value="Reports"/>
	<bean:define id="tabId" value="scheduledReports"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../report/inc_report_tabs.jsp"%>
	
	<logic:empty name="reportForm" property="scheduledReports">
		<p>No reports have been scheduled</p>
	</logic:empty>
	
	<logic:notEmpty name="reportForm" property="scheduledReports">
		<p>Below is a summary of the scheduled reports that are currently active. Click delete next to any report to prevent it from being run automatically.</p>
		
		<table class="admin">
		<tr>
			<th>Report Name</th>
			<th>Frequency</th>
			<th>Action</th>
		</tr>
	
	</logic:notEmpty>

	<logic:iterate name="reportForm" property="scheduledReports" id="scheduledReport">
		<tr>
			<td><bean:write name="scheduledReport" property="reportName" /></td>
			<td><bean:write name="scheduledReport" property="reportPeriod" /></td>
			<td>[<a href="deleteScheduledReport?reportId=<bean:write name='scheduledReport' property='id'/>">Delete</a>]</td>
		</tr>
	</logic:iterate>

	
	</table>

	

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>