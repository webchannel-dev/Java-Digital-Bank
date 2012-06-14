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
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Download reports</title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	
	<bean:define id="section" value="reports"/>
	<bean:define id="pagetitle" value="Download Reports"/>
	<bean:define id="tabId" value="usageReports"/>
	<script type="text/JavaScript">
		$j(function() {
			initDatePicker();
		});
	</script>	
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
	
	<p>Please enter the date range you are interested in by providing a <c:out value="${dateFormatHelpString}" /> format start and end date and click 'Get Report'.</p>

	<html:form action="viewReport" method="get">
	<div id="reportSelect">
		<label for="startDate">From:</span> <html:text styleClass="small text date" name="reportForm" property="startDateString" size="20" styleId="startDate" /> 
		
		
		<span class="required">*</span> 
		&nbsp;
		<label for="endDate">To:</span> <html:text styleClass="small text date" name="reportForm" property="endDateString" size="20" styleId="endDate" /> 

		
		<span class="required">*</span>  
		&nbsp;
		
		<html:checkbox styleId="sortAscending" name="reportForm" property="sortAscending" style="margin-bottom:0" /><label for="sortAscending">Lowest first</label>
		&nbsp;
		<input type="hidden" name="reportType" value="<bean:write name="reportForm" property="reportType"/>">
			
		<input type="submit" name="reportOption" class="button inline" value="Submit">
		</html:form>
	
	</div>
	
	<br />


	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>