<%@include file="../inc/doctype_html_admin.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Audit Report</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<link rel="stylesheet" type="text/css" href="../css/standard/print.css" media="print" />
	<bean:define id="section" value="reports"/>
	<bean:define id="pagetitle" value="Reports"/>
</head>
<body id="adminPage">
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" /></h1> 

	<div class="head">
		<a href="../action/viewAuditReportPage?startDateString=<bean:write name='auditReportForm' property='startDateString'/>&endDateString=<bean:write name='auditReportForm' property='endDateString'/>" class="bold">&laquo; Back to Reports</a>
	</div>

	<h2>User Login Report</h2>
	
	<logic:empty name="auditReportForm" property="userLoginReportLines">
		<p>There are no user login entries for the date range you provided.</p>
	</logic:empty>
	
	<logic:notEmpty name="auditReportForm" property="userLoginReportLines">
		
		<c:choose>
			<c:when test="${auditReportForm.startDate != null || auditReportForm.endDate != null}"> 
				Report date range: 
				<c:choose>
					<c:when test="${auditReportForm.startDate != null && auditReportForm.endDate == null}"> 
						logins after <fmt:formatDate value="${auditReportForm.startDate}" pattern="${dateFormat}" />
					</c:when>
					<c:when test="${auditReportForm.startDate == null && auditReportForm.endDate != null}">
						logins before <fmt:formatDate value="${auditReportForm.endDate}" pattern="${dateFormat}" />
					</c:when>
					<c:otherwise>
						<fmt:formatDate value="${auditReportForm.startDate}" pattern="${dateFormat}" /> to <fmt:formatDate value="${auditReportForm.endDate}" pattern="${dateFormat}" />
					</c:otherwise>
				</c:choose>
			</c:when>
		</c:choose>
			
		<div class="hr"></div>
		
		<table cellspacing="0" class="report" summary="Search report" style="width: 80%;">		
			<tr>
				<th>
					Username
				</th>
				<th>
					Login date
				</th>
				<th>
					Login time
				</th>
				
				<th>
					IP Address
				</th>
			</tr>
			<logic:iterate name="auditReportForm" property="userLoginReportLines" id="reportLine" indexId="index">
				<tr>
					<td>
						<bean:write name="reportLine" property="username" />
					</td>			
					<td>
						<fmt:formatDate value="${reportLine.loginDateAndTime}" pattern="${dateFormat}" />
					</td>	
					<td>
						<bean:write name="reportLine" property="loginDateAndTime" format="hh:mm:ss a" />
					</td>	
					<td>
						<bean:write name="reportLine" property="IPAddress" />
					</td>	
				</tr>
			</logic:iterate>
		</table>
	</logic:notEmpty>
	
	<div class="hr"></div>
	<p>
	<a href="../action/viewAuditReportPage?startDateString=<bean:write name='auditReportForm' property='startDateString'/>&endDateString=<bean:write name='auditReportForm' property='endDateString'/>" class="bold">&laquo; Back to Reports</a><logic:notEmpty name="auditReportForm" property="userLoginReportLines">&nbsp;|&nbsp;
	<a href="../action/viewAuditReport?reportType=2&downloadFile=1&startDateString=<bean:write name='auditReportForm' property='startDateString'/>&endDateString=<bean:write name='auditReportForm' property='endDateString'/>&contentType=application/vnd.ms-excel">Download as excel file...</a></logic:notEmpty>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>