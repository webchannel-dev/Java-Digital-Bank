<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Matt Stevenson		25-May-2005		Created
	d2		Matt Stevenson		12-Jan-2006		Changed back to reports link
	d3    Ben Browning   	21-Feb-2006    HTML/CSS tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />
<bright:applicationSetting id="showThumbnails" settingName="show-thumbnails-in-asset-reports" />

<%@include file="../inc/set_this_page_url.jsp"%>
<c:set scope="session" var="imageDetailReturnUrl" value="${thisUrl}"/>
<c:set scope="session" var="imageDetailReturnName" value="Report"/>



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
		
	<h2><bright:cmsWrite identifier="item" filter="false" case="mixed" /> View Report</h2>
	
	<logic:empty name="reportForm" property="details">
		<p>There are no records of any <bright:cmsWrite identifier="items" filter="false" /> being viewed in the date range you provided.</p>
	</logic:empty>
	
	<logic:notEmpty name="reportForm" property="details">		
		
		<h3>Report date range: 
			<fmt:formatDate value="${reportForm.startDate}" pattern="${dateFormat}" />
			to 
			<fmt:formatDate value="${reportForm.endDate}" pattern="${dateFormat}" />
		</h3>

		<c:if test="${reportForm.totalTimes gt 0}">
			<p>Total number of views in the selected period: <strong><c:out value="${reportForm.totalTimes}" /></strong>.</p>			
		</c:if>
		<c:if test="${reportForm.totalAssets gt 0}">
			<p>Total number of different <bright:cmsWrite identifier="items" filter="false" /> viewed in the selected period: <strong><c:out value="${reportForm.totalAssets}" /></strong>.</p>			
		</c:if>
		<p>Below is a list of <bright:cmsWrite identifier="items" filter="false" /> that have been viewed in the selected period, and the number of times they have been viewed.</p>
	
						
		<div class="hr"></div>
		
		<table cellspacing="0" border="0" class="report" summary="Image download report">		
			<tr>
				<c:if test="${showThumbnails}">
					<th>&nbsp;</th>
				</c:if>
				<th>
					<bright:cmsWrite identifier="item" filter="false" case="mixed" /> Id
				</th>
				<th>
					Original Filename
				</th>
				<th class="downloads">
					Number of Views
				</th>
			</tr>
	
			<logic:iterate name="reportForm" property="details" id="asset">
				<tr>
					<c:if test="${showThumbnails}">
						<td>
							<a href="viewAsset?id=<bean:write name='asset' property='idForAsset'/>">
								<img class="<c:if test="${asset.hasIcon}">icon</c:if><c:if test="${not asset.hasIcon}">image</c:if>" src="../servlet/display?file=<c:out value="${asset.rc4EncryptedThumbnailPath}"/>" border="0">
							</a>
						</td>
					</c:if>
					<td>
						<a href="viewAsset?id=<bean:write name='asset' property='idForAsset'/>"><bean:write name="asset" property="id"/></a>
					</td>
					<td>
						
						<span style="display:block;width:200px; overflow:hidden;"><bean:write name="asset" property="originalFilename"/></span>
					</td>
					<td class="downloads">
						<logic:equal name="reportForm" property="showGroups" value="false">
							<strong><bean:write name="asset" property="count"/></strong>
						</logic:equal>
					</td>
				</tr>
				<logic:equal name="reportForm" property="showGroups" value="true">
					<logic:iterate name="asset" property="groups" id="group">
						<tr class="downloadbreakdown">
							<td colspan="<c:if test="${showThumbnails}">3</c:if><c:if test="${not showThumbnails}">2</c:if>">
								-
								<logic:notEmpty name="group" property="groupName">
									<bean:write name="group" property="groupName"/>
								</logic:notEmpty>
								<logic:empty name="group" property="groupName">
									Unassigned users (admin/anonymous)
								</logic:empty>
							</td>
							<td class="downloads">
								<bean:write name="group" property="count"/>
							</td>
						</tr>
					</logic:iterate>
				</logic:equal>
				<logic:equal name="reportForm" property="showUsers" value="true">
					<logic:iterate name="asset" property="users" id="user">
						<tr class="downloadbreakdown">
							<td colspan="<c:if test="${showThumbnails}">3</c:if><c:if test="${not showThumbnails}">2</c:if>">
								-
								<c:choose>
									<c:when test="${user.userId > 0}">
										<bean:write name="user" property="forename" /> <bean:write name="user" property="surname" />
										(<bean:write name="user" property="username" />)										
									</c:when>
									<c:otherwise>
										(Anonymous)
									</c:otherwise>
								</c:choose>
							</td>
							<td class="downloads">
								<bean:write name="user" property="count"/>
							</td>
						</tr>
					</logic:iterate>
				</logic:equal>
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