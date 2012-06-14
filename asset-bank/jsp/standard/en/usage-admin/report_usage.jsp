<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Matt Stevenson		24-May-2005		Created
	d2		Matt Stevenson		25-May-2005		Minor text and layout changes
	d3		Matt Stevenson		12-Jan-2006		Changed back to reports link
	d4    Ben Browning   	21-Feb-2006    HTML/CSS tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Reports</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<link rel="stylesheet" type="text/css" href="../css/standard/print.css" media="print" />
	<bean:define id="section" value="reports"/>
	<bean:define id="pagetitle" value="Reports"/>
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" /></h1> 
	
	<div class="head">
		<%@include file="back_to_reports.jsp"%>
	</div>
	
	<h2>Usage Report</h2>

	<logic:empty name="reportForm" property="details">
		<p>None of the users in the system downloaded any of the images in the specified date range.</p>
	</logic:empty>
	<logic:notEmpty name="reportForm" property="details">
		<p>Below is a list of downloads in the selected period, organised by usage type.</p>

		<h3>Report date range: 
			<fmt:formatDate value="${reportForm.startDate}" pattern="${dateFormat}" />
			to 
			<fmt:formatDate value="${reportForm.endDate}" pattern="${dateFormat}" />
		</h3>
		<div class="hr"></div>
		
		<table cellspacing="0" class="report" summary="Usage report">		
			<tr>
				<th>
					Usage Type
				</th>
				<th class="downloads">
					Number of Downloads
				</th>
			</tr>
			<logic:iterate name="reportForm" property="details" id="detail">
				<tr>
					<td>
						<bean:write name="detail" property="usageDescription"/>
					</td>
					<td class="uses">
						<bean:write name="detail" property="count"/>
					</td>
				</tr>
			</logic:iterate>
		</table>
		
		<p>
			<a href="downloadUsageReport?reportType=<bean:write name="reportForm" property="reportType"/>&startDateString=<bean:write name="reportForm" property="startDateString"/>&endDateString=<bean:write name="reportForm" property="endDateString"/>&sortAscending=<bean:write name="reportForm" property="sortAscending"/>&contentType=application/vnd.ms-excel">Download as excel file...</a>
		</p>
		
	</logic:notEmpty>

	<div class="hr"></div>
	
	<p><%@include file="back_to_reports.jsp"%></p>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>