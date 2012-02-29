<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Matt Woollard		24-Oct-2008         Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />


<%@include file="../inc/set_this_page_url.jsp"%>
<c:set scope="session" var="imageDetailReturnUrl" value="${thisUrl}"/>
<c:set scope="session" var="imageDetailReturnName" value="Report"/>



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Reports</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<link rel="stylesheet" type="text/css" href="../css/standard/print.css" media="print" />
	<bean:define id="section" value="reports"/>
	<bean:define id="pagetitle" value="Reports"/>
	
	<c:if test="${reportForm.showViews}">
		<bean:define id="reportType" value="Views"/>
	</c:if>
	<c:if test="${reportForm.showDownloads}">
		<bean:define id="reportType" value="Downloads"/>
	</c:if>
	
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	
	<div class="head">
	<%@include file="back_to_reports.jsp"%>
	</div>
		
	<h2><bean:write name="reportType" filter="false"/> by Access Level</h2>
	
	<logic:empty name="reportForm" property="details">
		<p>There are no records of any <bright:cmsWrite identifier="items" filter="false" /> being viewed in the date range you provided.</p>
	</logic:empty>
	
	<logic:notEmpty name="reportForm" property="details">		
		<h3>Report date range: 
		<fmt:formatDate value="${reportForm.startDate}" pattern="${dateFormat}" />
		to 
		<fmt:formatDate value="${reportForm.endDate}" pattern="${dateFormat}" />
		</h3>

		<p>Total number of <bean:write name="reportType" filter="false"/> in the selected period: <strong><c:out value="${reportForm.totalTimes}" /></strong>.</p>			
		
		<table class="report" cellspacing="0">
			<tr>
				<th>Access Level</th>
				<th class="downloads">Number of <bean:write name="reportType" filter="false"/></th>
			</tr>
			<logic:iterate name="reportForm" property="details" id="item" indexId="i">
				<logic:notEqual name='item' property='depth' value='0'>
				<tr>
					<c:choose>
						<c:when test="${item.depth>1}">
							<td style="padding-left: <c:out value="${-1 + item.depth}"/>em;">
						</c:when>
						<c:otherwise>
							<td>
						</c:otherwise>
					</c:choose>	
					
						<bean:write name="item" property="name" filter="false"/>
					</td>
					<td class="downloads">
						<strong>
							<c:if test="${reportForm.showViews}">
								<bean:write name="item" property="numViews" filter="false"/>
							</c:if>
							<c:if test="${reportForm.showDownloads}">
								<bean:write name="item" property="numDownloads" filter="false"/>
							</c:if>
						</strong>
					</td>
					
				</tr>
				</logic:notEqual>
			</logic:iterate>
		</table>
	
	</logic:notEmpty>
	
	<div class="hr"></div>
	
	<p><%@include file="back_to_reports.jsp"%></p>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>