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
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Schedule reports</title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<bean:define id="section" value="reports"/>
	<bean:define id="helpsection" value="scheduled_reports"/>
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
	
	<p>Please enter the details to setup a new scheduled report.</p>
	<p>The report will be automatically generated for the time period selected (e.g. Monthly on 1st of month).</p>
	<p>The admin users and other groups selected will receive a copy of the report as an email attachment.</p>
	<bean:parameter id="reportName" name="reportName" value=""/>
	<p><em><span class="required">*</span> denotes a required field.</em></p>
	
	<html:form action="viewScheduledReport" method="get" styleClass="floated" styleId="scheduleForm">

		
		<label for="reportName">Report Name:</label>	
		<input type="text" name="reportName" id="reportName" value="<bean:write name='reportName'/>" style="width: 360px"/>
		<br />
		
		<label for="reportFrequency">Frequency: <span class="required">*</span></label>
		<html:select name="reportForm" property="reportFrequency" styleId="reportFrequency">
			<html:option value="Daily">Daily</html:option>
			<html:option value="Weekly">Weekly</html:option>
			<html:option value="Monthly">Monthly</html:option>
		</html:select>
		<br />	
			
	
		<p>Reports will be emailed to admin users by default. You can select additional groups  to receive the reports from the list below:</p> 
				
		<label>Additional groups:</label>
		<div class="floatLeft">
	 	<logic:iterate name="reportForm" property="allGroups" id="group" indexId="i">
	 		<bean:define id="groupId" name="group" property="id" />
			<html:multibox name="reportForm" property="groupSelectedList" styleClass="checkbox" styleId="<%=\"add_groups_\"+groupId%>">
				<bean:write name="group" property="id" />
			</html:multibox>
			<label class="after" for="add_groups_<bean:write name="group" property="id" />"><bean:write name="group" property="nameWithOrgUnit"/></label><br />
		</logic:iterate>
		</div>
		<br />
		
		<input type="hidden" name="reportType" value="<bean:write name="reportForm" property="reportType"/>">
		
		<div class="hr"></div>
		
		
		<input type="submit" name="reportOption" class="button flush" value="Submit"> 
		
		<a href="viewReportHome?reportType=<c:out value='${reportForm.reportType}'/>" class="cancelLink js-enabled-show hidden">Cancel</a>
		
		<input type="submit" name="Cancel" value="Cancel" class="button js-enabled-hide" />
	</html:form>
	<br />


	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>