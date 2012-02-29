<%@include file="../inc/doctype_html_admin.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Woollard    04-Apr-2008    Created
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="ecommerce" settingName="ecommerce" />
<bright:applicationSetting id="maxReportResults" settingName="max-report-results" />

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Audit Reports</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script src="../js/calendar.js" type="text/javascript"></script>
	
	<bean:define id="section" value="reports"/>
	<bean:define id="pagetitle" value="Audit Reports"/>
	<bean:define id="tabId" value="auditReports"/>
</head>

<body id="adminPage">
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../report/inc_report_tabs.jsp"%>
	

		<logic:present name="auditReportForm">
			<logic:equal name="auditReportForm" property="hasErrors" value="true"> 
				<div class="error">
					<logic:iterate name="auditReportForm" property="errors" id="errorText">
						<bean:write name="errorText" filter="false"/><br />
					</logic:iterate>
				</div>
			</logic:equal>
		</logic:present>
		<bright:applicationSetting id="daysBeforeMonths" settingName="days-before-months" />
		<bright:applicationSetting id="dateFormatHelpString" settingName="date-format-help-string" />
		
		<p>Please click 'Get Report' to display a list of the last <c:out value='${maxReportResults}' /> audit transactions.
			
		Optionally you can chose a date range by providing a <c:out value="${dateFormatHelpString}" /> format start and end date.
		
		The report can also be limited by username and/or IP address.<br/><br/>
		
		<html:form action="viewAuditReport" method="get">
			
		<div id="reportSelect">
			
		<table>
			<tr>
				<td>
					<label for="startDate">From:</label> <html:text styleClass="small text" name="auditReportForm" property="startDateString" size="20" styleId="startDate" /> 
					<script type="text/javascript">
						document.write('<a href="javascript:;" title="Open date selector in new window"><img class="icon" src="../images/standard/icon/calendar.gif" alt="Calendar" onclick="openDatePickerSupportUS(document.getElementsByName(\'startDateString\')[0], <bean:write name='daysBeforeMonths' />)" width="16" height="15" style="padding-right: 0;" /><\/a>');
					</script>
				</td>
				
				<td>
					<label for="endDate">To:</label> <html:text styleClass="small text" name="auditReportForm" property="endDateString" size="20" styleId="endDate" /> 
					<script type="text/javascript">
						document.write('<a href="javascript:;" title="Open date selector in new window"><img class="icon" src="../images/standard/icon/calendar.gif" alt="Calendar" onclick="openDatePickerSupportUS(document.getElementsByName(\'endDateString\')[0], <bean:write name='daysBeforeMonths' />)" width="16" height="15" style="padding-right: 0;" /><\/a>');
					</script>	
				</td>
			</tr>
			
			<tr>
				<td>
					<label for="username">Username:</label> <html:text styleClass="small text" name="auditReportForm" property="username" size="20" styleId="username" />
				</td>
				
				<td>
					<label for="ipAddress">IP address:</label> <html:text styleClass="small text" name="auditReportForm" property="ipAddress" size="20" styleId="ipAddress" />
				</td>
			</tr>
		</table>	
			
		<label for="includeViewsDownloads">Include views & downloads:</label> <html:checkbox name="auditReportForm" property="includeViewsDownloads" styleId="includeViewsDownloads" />

		<br/><br/>
		
		<input type="submit" name="submit" class="button flush" value="Get Report">
		</div>
		</html:form>

		<%@include file="../inc/body_end.jsp"%>
			
</body>
</html>