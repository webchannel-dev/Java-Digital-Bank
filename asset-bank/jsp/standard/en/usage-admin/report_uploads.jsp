<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Matt Stevenson		25-May-2005		Created
	d2		Matt Stevenson		12-Jan-2006		Changed back to reports link
	d3   	Ben Browning   	21-Feb-2006    HTML/CSS tidy up
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
		
	<h2><bright:cmsWrite identifier="item" filter="false" case="mixed" /> Upload Report</h2>
	
	<logic:empty name="reportForm" property="details">
		<p>There are no records of any <bright:cmsWrite identifier="items" filter="false" /> being uploaded in the date range you provided.</p>
	</logic:empty>
	<logic:notEmpty name="reportForm" property="details">
		<p>Below is a list of uploads in the selected period.</p>
	
			<h3>Report date range: 
				<fmt:formatDate value="${reportForm.startDate}" pattern="${dateFormat}" />
				to 
				<fmt:formatDate value="${reportForm.endDate}" pattern="${dateFormat}" />
			</h3>
		
		<div class="hr"></div>
		
		<table cellspacing="0" class="report" summary="Image upload report">		
			<tr>
				<th>
					<logic:equal name="reportForm" property="showGroups" value="false">
						User
					</logic:equal>
					<logic:notEqual name="reportForm" property="showGroups" value="false">
						Group
					</logic:notEqual>
				</th>
				<th class="downloads">
					Number of Uploads
				</th>
			</tr>
	
			<logic:equal name="reportForm" property="showGroups" value="false">
				<logic:iterate name="reportForm" property="details" id="user">
					<tr>
						<td>
							<bean:write name="user" property="forename" /> <bean:write name="user" property="surname" />
							(<bean:write name="user" property="username" />)
						</td>
						<td class="downloads">
							<bean:write name="user" property="count"/>
						</td>
					</tr>
				</logic:iterate>
			</logic:equal>
	
			<logic:equal name="reportForm" property="showGroups" value="true">
				<logic:iterate name="reportForm" property="details" id="group">
					<tr>
						<td>
							<logic:notEmpty name="group" property="groupName">
								<bean:write name="group" property="groupName"/>
							</logic:notEmpty>
							<logic:empty name="group" property="groupName">
								Unassigned users (admin)
							</logic:empty>
						</td>
						<td class="downloads">
							<bean:write name="group" property="count"/>
						</td>
					</tr>
	
					<logic:equal name="reportForm" property="showUsers" value="true">
						<logic:iterate name="group" property="users" id="user">
							<tr class="downloadbreakdown">							
								<td>
									-
									<bean:write name="user" property="forename" /> <bean:write name="user" property="surname" />
									(<bean:write name="user" property="username" />)
								</td>
								<td class="downloads">
									<bean:write name="user" property="count"/>
								</td>
							</tr>
						</logic:iterate>
					</logic:equal>
	
				</logic:iterate>
			</logic:equal>
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