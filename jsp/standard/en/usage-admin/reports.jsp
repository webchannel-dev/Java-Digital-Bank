<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	24-May-2005		Created
	 d2      Ben Browning   21-Feb-2006    HTML/CSS tidy up
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
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Reports</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script src="../js/calendar.js" type="text/javascript"></script>
	
	<bean:define id="section" value="reports"/>
	<bean:define id="pagetitle" value="Reports"/>
	<bean:define id="tabId" value="usageReports"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../report/inc_report_tabs.jsp"%>
		

	<logic:present name="reportForm">
		<logic:equal name="reportForm" property="hasErrors" value="true"> 
			<div class="error">
				<logic:iterate name="reportForm" property="errors" id="errorText">
					<bean:write name="errorText" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<p>Please select the report type below. If you wish to view the report now, enter the date range you are interested in by providing a <c:out value="${dateFormatHelpString}" /> format start and end date and click 'View report'.</p>
	<p>You can schedule a report to run in the future (the report will be emailed to you) by selecting a report type and clicking 'Schedule report'.</p>
	<html:form action="viewReport" method="get">
		
	<h3>Choose Report:</h3>
	
	<input type="radio" name="reportType" value="14" id="report14" onclick="document.getElementById('reportName').value = 'Number of views per <bright:cmsWrite identifier='item' filter='false' /> (and total views over the period)';" <c:if test="${reportForm.reportType == 14}">checked</c:if>/> <label for="report14">Number of views per <bright:cmsWrite identifier='item' filter='false' /> (and total views over the period)</label><br />
	<input type="radio" name="reportType" value="2" id="report2" onclick="document.getElementById('reportName').value = 'Number of views per <bright:cmsWrite identifier='item' filter='false' /> by group';" <c:if test="${reportForm.reportType == 2}">checked</c:if>/> <label for="report2">Number of views per <bright:cmsWrite identifier='item' filter='false' /> by group</label><br />
	<input type="radio" name="reportType" value="3" id="report3" onclick="document.getElementById('reportName').value = 'Number of views per <bright:cmsWrite identifier='item' filter='false' /> by user';" <c:if test="${reportForm.reportType == 3}">checked</c:if>/> <label for="report3">Number of views per <bright:cmsWrite identifier='item' filter='false' /> by user</label><br />
	<br />
	<input type="radio" name="reportType" value="4" id="report4" onclick="document.getElementById('reportName').value = 'Number of uploads by group';" <c:if test="${reportForm.reportType == 4}">checked</c:if>/> <label for="report4">Number of uploads by group</label><br />
	<input type="radio" name="reportType" value="5" id="report5" onclick="document.getElementById('reportName').value = 'Number of uploads by user';" <c:if test="${reportForm.reportType == 5}">checked</c:if>/> <label for="report5">Number of uploads by user</label><br />
	<input type="radio" name="reportType" value="6" id="report6" onclick="document.getElementById('reportName').value = 'Number of uploads by user and group';" <c:if test="${reportForm.reportType == 6}">checked</c:if>/> <label for="report6">Number of uploads by user and group</label><br />
	<br />
	<input type="radio" name="reportType" value="13" id="report13" onclick="document.getElementById('reportName').value = 'Number of downloads by uploader';" <c:if test="${reportForm.reportType == 13}">checked</c:if>/> <label for="report13">Number of downloads by uploader</label><br />
	<br />
	<input type="radio" name="reportType" value="1" id="report1" onclick="document.getElementById('reportName').value = 'Number of downloads by usage type';" <c:if test="${reportForm.reportType == 1}">checked</c:if>/> <label for="report1">Number of downloads by usage type</label><br />
	<input type="radio" name="reportType" value="10" id="report10" onclick="document.getElementById('reportName').value = 'Number of downloads per user';" <c:if test="${reportForm.reportType == 10}">checked</c:if>/> <label for="report10">Number of downloads per user</label><br />
	<input type="radio" name="reportType" value="11" id="report11" onclick="document.getElementById('reportName').value = 'Number of downloads per group';" <c:if test="${reportForm.reportType == 11}">checked</c:if>/> <label for="report11">Number of downloads per group</label><br />
	<input type="radio" name="reportType" value="7" id="report7" onclick="document.getElementById('reportName').value = 'Number of downloads per <bright:cmsWrite identifier='item' filter='false' /> (and total downloads over the period)';" <c:if test="${reportForm.reportType == 7}">checked</c:if>/> <label for="report7">Number of downloads per <bright:cmsWrite identifier='item' filter='false' /> (and total downloads over the period)</label><br />
	<input type="radio" name="reportType" value="8" id="report8" onclick="document.getElementById('reportName').value = 'Number of downloads per <bright:cmsWrite identifier='item' filter='false' /> by user';" <c:if test="${reportForm.reportType == 8}">checked</c:if>/> <label for="report8">Number of downloads per <bright:cmsWrite identifier='item' filter='false' /> by user</label><br />
	<input type="radio" name="reportType" value="9" id="report9" onclick="document.getElementById('reportName').value = 'Number of downloads per <bright:cmsWrite identifier='item' filter='false' /> by user and group';" <c:if test="${reportForm.reportType == 9}">checked</c:if>/> <label for="report9">Number of downloads per <bright:cmsWrite identifier='item' filter='false' /> by user and group</label><br />
	<br />
	<input type="radio" name="reportType" value="12" id="report12" onclick="document.getElementById('reportName').value = 'Reason for download by asset and user';" <c:if test="${reportForm.reportType == 12}">checked</c:if>/> <label for="report12">Reason for download by asset and user</label><br />
	<br />
	<input type="radio" name="reportType" value="15" id="report15" onclick="document.getElementById('reportName').value = 'Number of views per <bright:cmsWrite identifier='item' filter='false' /> by access level';" <c:if test="${reportForm.reportType == 15}">checked</c:if>/> <label for="report15">Number of views per <bright:cmsWrite identifier='item' filter='false' /> by access level</label><br />
	<input type="radio" name="reportType" value="16" id="report16" onclick="document.getElementById('reportName').value = 'Number of downloads per <bright:cmsWrite identifier='item' filter='false' /> by access level';" <c:if test="${reportForm.reportType == 16}">checked</c:if>/> <label for="report16">Number of downloads per <bright:cmsWrite identifier='item' filter='false' /> by access level</label><br />
	<br />
	
	<div id="reportSelect">
	
		<h3>View a report</h3>
		<label for="startDate">From:</span> <html:text styleClass="small text" name="reportForm" property="startDateString" size="20" styleId="startDate" /> 
		
		<script type="text/javascript">
			document.write('<a href="javascript:;" title="Date chooser"><img class="icon" src="../images/standard/icon/calendar.gif" alt="Date chooser" onclick="openDatePickerSupportUS(document.getElementsByName(\'startDateString\')[0], <bean:write name='daysBeforeMonths' />)" width="16" height="15" style="padding-right: 0;" /><\/a>');
		</script>
		
		<span class="required">*</span> 
		&nbsp;
		<label for="endDate">To:</span> <html:text styleClass="small text" name="reportForm" property="endDateString" size="20" styleId="endDate" /> 
		
		<script type="text/javascript">
			document.write('<a href="javascript:;" title="Date chooser"><img class="icon" src="../images/standard/icon/calendar.gif" alt="Date chooser" onclick="openDatePickerSupportUS(document.getElementsByName(\'endDateString\')[0], <bean:write name='daysBeforeMonths' />)" width="16" height="15" style="padding-right: 0;" /><\/a>');
		</script>
		
		<span class="required">*</span>  
		&nbsp;
		
		<html:checkbox styleId="sortAscending" name="reportForm" property="sortAscending" style="margin-bottom:0" /><label for="sortAscending">Lowest first</label>
		&nbsp;
		
		<input type="submit" name="reportOption" class="button inline" property="reportOption" value="View report">

		<br/><br/><br/>
		<h3>Schedule a report</h3>
		<input type="hidden" name="reportName" id="reportName" value="Number of views per item (and total views over the period"/>
		<input type="submit" name="reportOption" class="button flush" property="reportOption" value="Schedule report">
		</html:form>
	
	</div>
	
	<br />


	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>