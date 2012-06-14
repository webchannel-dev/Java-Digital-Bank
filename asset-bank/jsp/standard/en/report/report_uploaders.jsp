<%@include file="../inc/doctype_html_admin.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Matt Stevenson		18-Jun-2007		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Reports</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="reports"/>
	<bean:define id="pagetitle" value="Reports"/>
</head>


<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>

	<h1><bean:write name="pagetitle" /></h1> 
	
	<div class="head">
		<a href="../action/viewEcommerceReports?startDateString=<bean:write name='ecommerceReportForm' property='startDateString'/>&endDateString=<bean:write name='ecommerceReportForm' property='endDateString'/>&reportType=2">&laquo; Back to Reports</a>
	</div>
	
	<h2 class="report">Uploaders Report</h2>

	<logic:empty name="ecommerceReportForm" property="details">
		<p>There are no sales recorded for any of the uploaders between the specified dates.</p>
	</logic:empty>
	<logic:notEmpty name="ecommerceReportForm" property="details">
		<c:choose>
			<c:when test="${ecommerceReportForm.startDate != null && ecommerceReportForm.endDate != null}">
				<h3 class="report">Report date range: <bean:write name="ecommerceReportForm" property="startDate" format="dd/MM/yyyy"/> to <bean:write name="ecommerceReportForm" property="endDate" format="dd/MM/yyyy"/></h3>
			</c:when>
			<c:when test="${ecommerceReportForm.startDate != null}">
				<h3 class="report">Report date range: On or after <bean:write name="ecommerceReportForm" property="startDate" format="dd/MM/yyyy"/></h3>
			</c:when>
			<c:when test="${ecommerceReportForm.endDate != null}">
				<h3 class="report">Report date range: On or before <bean:write name="ecommerceReportForm" property="endDate" format="dd/MM/yyyy"/></h3>
			</c:when>
		</c:choose>
		
		<div class="hr"></div>
		
			<table cellspacing="0" class="form" summary="Usage report">
					<tr>
						<th>User Id:</th>
						<th style="width: 35%">Name:</th>
						<th style="width: 25%">Email Address:</th>
						<th style="width: 15%">Number of <bright:cmsWrite identifier="items" filter="false" /> sold:</th>
						<th style="width: 15%">Total earned:</th>
					</tr>
					
					<logic:iterate name="ecommerceReportForm" property="details" id="order">
					<tr>
						<td><bean:write name="order" property="userId"/></td>
						<td><bean:write name="order" property="forename" />&nbsp;<bean:write name="order" property="surname" />&nbsp;(<bean:write name="order" property="username" />)</td>
						<td><bean:write name="order" property="emailAddress" /></td>
						<td><bean:write name="order" property="assetsSold"/></td>
						<td><bright:writeMoney name="order" property="totalIncome.displayAmount" /></td>
					</tr>
					</logic:iterate>
					<tr><td colspan="5">&nbsp;</td></tr>
				
					<tr><td colspan="5" class="hr">&nbsp;</td></tr>

					<tr>
						<td colspan="3"></td>
						<td><em>Total:</em>&nbsp;&nbsp;</td>
						<td><bright:writeMoney name="ecommerceReportForm" property="total.displayAmount" /></td>
					</tr>
			</table>
		<div class="hr"></div>
	</logic:notEmpty>
	<p><a href="../action/viewEcommerceReports?startDateString=<bean:write name='ecommerceReportForm' property='startDateString'/>&endDateString=<bean:write name='ecommerceReportForm' property='endDateString'/>&reportType=2">&laquo; Back to Reports</a></p>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>