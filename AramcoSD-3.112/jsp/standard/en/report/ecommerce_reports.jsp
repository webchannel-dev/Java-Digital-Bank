<%@include file="../inc/doctype_html_admin.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	24-May-2005		Created
	 d2 		Ben Browning	05-Jul-2006		Tidied up HTML
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
	
<bright:applicationSetting id="daysBeforeMonths" settingName="days-before-months" />
<bright:applicationSetting id="dateFormatHelpString" settingName="date-format-help-string" />
<bright:applicationSetting id="recordSearches" settingName="record-searches" />
<bright:applicationSetting id="subscriptionsEnabled" settingName="subscription" />

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Reports</title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	
	<bean:define id="section" value="reports"/>
	<bean:define id="pagetitle" value="Reports"/>
	<bean:define id="tabId" value="eccomerceReports"/>
	<script type="text/JavaScript">
		$j(function () {
			initDatePicker();
		});
	</script>
</head>

<body id="adminPage">
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../report/inc_report_tabs.jsp"%>

	<logic:present name="ecommerceReportForm">
		<logic:equal name="ecommerceReportForm" property="hasErrors" value="true"> 
			<div class="error">
				<logic:iterate name="ecommerceReportForm" property="errors" id="errorText">
					<bean:write name="errorText" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<p>Please select the report type below, then enter the date range you are interested in by providing a dd/mm/yyyy format start and end date and click 'Get Report'.</p>
		
	<h3>Order reports</h3>
	
	<html:form action="viewEcommerceReport" method="get">
		
		
		
		<html:radio property="reportType" value="1"/>&nbsp;Order detail report<br/>
		<html:radio property="reportType" value="2"/>&nbsp;Uploaders report<br/>
		
		<br/>

		<div id="reportSelect">
			<label for="startDate">From:</label> <html:text styleClass="small text date" styleId="startDate" name="ecommerceReportForm" property="startDateString" size="20"/> 
			
		
			
			<span class="required">*</span> 
			&nbsp;
			<label for="endDate">To:</label> <html:text styleClass="small text date" name="ecommerceReportForm" styleId="endDate" property="endDateString" size="20"/> 
			
		
			
			<span class="required">*</span>  
			&nbsp;
			
			
				
			<input type="submit" name="submit" class="button" value="Get Report">
		</div>
	
	</html:form>
	
	<br/>
	
	<c:if test="${subscriptionsEnabled}">	
		<div class="hr"></div>
		<h3>Subscription report by subscription model</h3>
		<html:form action="viewEcommerceReport" method="get">
			<html:hidden name="assetReportForm" property="reportType" value="3" />
			
			<html:checkbox name="ecommerceReportForm" property="showActiveSubscriptions">Active subscriptions only</html:checkbox><br/>
			<html:checkbox name="ecommerceReportForm" property="showActiveSubscriptionModels">Active subscription models only</html:checkbox><br/>
			
			<br/>
			
			<div id="reportSelect">
				
				<label for="startDate">From:</label> <html:text styleClass="small text date" styleId="startDate2" name="ecommerceReportForm" property="startDateString" size="20"/> 
	
				
				<span class="required">*</span> 
				&nbsp;
				<label for="endDate">To:</label> <html:text styleClass="small text date" name="ecommerceReportForm" styleId="endDate2" property="endDateString" size="20"/> 

				
				<span class="required">*</span>  
				&nbsp;
					
				<input type="submit" name="submit" class="button" value="Get Report">
			</div>
		
		</html:form>
		
		<br/>
		
		<div class="hr"></div>
		
		<h3>Subscription report by user</h3>
		<html:form action="viewEcommerceReport" method="get">
			<html:hidden name="assetReportForm" property="reportType" value="4" />
			
			Username: <html:text name="ecommerceReportForm" styleId="userId" property="username" size="20"/> <input type="submit" name="submit" class="button" value="Get Report">
		</html:form>

	</c:if>
	<br/>	
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>