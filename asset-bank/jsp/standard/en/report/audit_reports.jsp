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
	
	
	<bean:define id="section" value="reports"/>
	<bean:define id="pagetitle" value="Audit Reports"/>
	<bean:define id="tabId" value="auditReports"/>
	<script type="text/JavaScript">
		$j(function() {
			initDatePicker();
		});
	</script>	
</head>

<body id="adminPage">
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" /></h1> 

	<%@include file="../report/inc_report_tabs.jsp"%>
	

		<logic:present name="auditReportForm">
			<logic:equal name="auditReportForm" property="hasErrors" value="true"> 
				<div class="error">
					<logic:iterate name="auditReportForm" property="errors" id="errorText">
						<bright:writeError name="errorText" /><br />
					</logic:iterate>
				</div>
			</logic:equal>
		</logic:present>
		<bright:applicationSetting id="daysBeforeMonths" settingName="days-before-months" />
		<bright:applicationSetting id="dateFormatHelpString" settingName="date-format-help-string" />
		
		<h2>Audit Reports</h2>
		
		Complete the form for the report you are interested in from the list below and click the relevant 'Get Report' button. Enter a <c:out value="${dateFormatHelpString}" /> format start and end date in the boxes blow to view your report for a particular period: 
		
		<html:form action="viewAuditReport" method="get">
		<input type="hidden" name="reportType" id="reportType" value="1" />
		<br />
		<table>
			<tr>
				<td>
					<label for="startDate">From:</label> <html:text styleClass="date" name="auditReportForm" property="startDateString" size="20" styleId="startDate" /> 
			
					<label for="endDate">To:</label> <html:text styleClass="date" name="auditReportForm" property="endDateString" size="20" styleId="endDate" /> 
				</td>
			</tr>
		</table>
		
		<div class="hr"></div>
		
		<h3>Audit Log Report</h3>
		
		<p>Click the 'Get Report' to display a list of audit transactions constrained to the date range provided in the boxes above. If you don't provide a date range then the last <c:out value='${maxReportResults}' /> results will be displayed. The report can also be limited by username and/or IP address.</p>
		
		<br/>
		
		<div id="reportSelect">
		<bean:parameter name="username" id="username" value="" />	
		<bean:parameter name="ipAddress" id="ipAddress" value="" />	
		<bean:parameter name="includeViewsDownloads" id="includeViewsDownloads" value="false" />	
		<table>
			<tr>
				<td>
					<label for="username">Username:</label> <input type="text" class="small text" name="username" size="20" id="username" value="<c:out value='${username}' />" />
				</td>
			</tr>
			
			<tr>	
				<td>
					<label for="ipAddress">IP address:</label> <input type="text" class="small text" name="ipAddress" size="20" id="ipAddress"  value="<c:out value='${ipAddress}' />"  />
				</td>
			</tr>
		</table>	
			
		<label for="includeViewsDownloads">Include views & downloads:</label> <input type="checkbox" name="includeViewsDownloads" id="includeViewsDownloads" value="true" <c:if test="${includeViewsDownloads}">checked</c:if>/>

		<br/><br/>
		
		<input type="submit" name="submit" class="button flush" value="Get Report" onclick="document.getElementById('reportType').value = 1;" />
		</div>
		
		<div class="hr"></div>
		
		<h3>User Login Report</h3>
		
		<p>Just click the 'Get Report' button to show a list of user logins for the period selected in the date range boxes above (if no dates are provided the last <c:out value='${maxReportResults}' /> user logins will be displayed):<br/>
		
		<div id="reportSelect2">
			
		<input type="submit" name="submit" class="button flush" value="Get Report" onclick="document.getElementById('reportType').value = 2;">
		
		</div>
		
		</html:form>
		
		<%@include file="../inc/body_end.jsp"%>
			
</body>
</html>