<%@include file="../inc/doctype_html_admin.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	21-Feb-2006		Created
	 d2		Ben Browning	01-Mar-2006		HTML/CSS Tidy up
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="ecommerce" settingName="ecommerce" />



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Reports</title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	
	<bean:define id="section" value="reports"/>
	<bean:define id="pagetitle" value="Reports"/>
	<bean:define id="tabId" value="searchReports"/>
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
	

		<logic:present name="searchReportForm">
			<logic:equal name="searchReportForm" property="hasErrors" value="true"> 
				<div class="error">
					<logic:iterate name="searchReportForm" property="errors" id="errorText">
						<bright:writeError name="errorText" /><br />
					</logic:iterate>
				</div>
			</logic:equal>
		</logic:present>
		<bright:applicationSetting id="daysBeforeMonths" settingName="days-before-months" />
		<bright:applicationSetting id="dateFormatHelpString" settingName="date-format-help-string" />
		
		<p>Please select the report type below, then enter the date range you are interested in by providing a <c:out value="${dateFormatHelpString}" /> format start and end date and click 'Get Report'.</p>
		
		<html:form action="viewSearchReport" method="get">
		
		<h3>Choose Report:</h3>
		
		<html:radio name="searchReportForm" property="groupedReport" value="false" styleId="notgrouped" /> <label for="notgrouped">Report of all search terms with dates</label><br />
		<html:radio name="searchReportForm" property="groupedReport" value="true" styleId="grouped" /> <label for="grouped">Grouped report of search terms</label><br />
		<br />
		
		<bean:parameter id="successType" name="successType" value="0"/>
		<p><span class="inline">Show</span> <html:select name="searchReportForm" property="successType" size="1">
					<option value="0" <c:if test="${successType==0}">selected</c:if>>all searches</option>
					<option value="1" <c:if test="${successType==1}">selected</c:if>>only successful searches</option>
					<option value="2" <c:if test="${successType==2}">selected</c:if>>only failed searches</option>
				</html:select> <span class="inline">in this report</span></p>
		<br />
		<div id="reportSelect">
		<label for="startDate">From:</label> <html:text styleClass="date" name="searchReportForm" property="startDateString" size="20" styleId="startDate" /> 

		
		&nbsp;
		<label for="endDate">To:</label> <html:text styleClass="date" name="searchReportForm" property="endDateString" size="20" styleId="endDate" /> 

		
		&nbsp;
		
		<input type="submit" name="submit" class="button flush" value="Get Report">
		</div>
		</html:form>

		<%@include file="../inc/body_end.jsp"%>
			
</body>
</html>