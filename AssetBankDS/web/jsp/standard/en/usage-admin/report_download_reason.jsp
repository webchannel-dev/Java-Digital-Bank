<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Matt Stevenson		25-Sep-2005		Created
	d2		Tamora James		03-Oct-2005		Added to demo
	d3		Matt Stevenson		12-Jan-2006		Changed back to reports link
	d4   	Ben Browning   		21-Feb-2006		HTML/CSS tidy up
	d5		Matt Stevenson		19-Feb-2008		Added organisation and country name
	d6		Matt Stevenson		30-Jul-2008		Added naming attribute text
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />
<bright:applicationSetting id="structuredAddress" settingName="users-have-structured-address"/>
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
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<div class="head">
		<%@include file="back_to_reports.jsp"%>
	</div>
		
	<h2 class="report">Reason for Download Report</h2>

	<logic:empty name="reportForm" property="details">
		<p>There have been no downloads between the dates specified.<br /><br /></p>
		<p>If you are expecting to see downloads shown here then it may be that your naming attribute is not properly set. Please visit the <a href="viewManageDisplayAttributes">Display Attributes page</a> select a name attribute and reindex the images before returning to this report.</p>
	</logic:empty>
	<logic:notEmpty name="reportForm" property="details">
		<h3>Report date range: <bean:write name="reportForm" property="startDate" format="MM/dd/yyyy"/> to <bean:write name="reportForm" property="endDate" format="MM/dd/yyyy"/></h3>
		
		<div class="hr"></div>
		
		<table cellspacing="0" class="report" summary="Usage report">		
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
				<th>
					Date / Time of Download
				</th>
				<th>
					Reason for Download
				</th>
				<th>
					User
				</th>
				<c:if test="${structuredAddress == true}">
				<th>
					Country
				</th>
				</c:if>
				<th>
					Organisation
				</th>
			</tr>
			<logic:iterate name="reportForm" property="details" id="detail">
				<tr>
					<c:if test="${showThumbnails}">
						<td>
							<a href="viewAsset?id=<bean:write name='detail' property='assetId'/>">
								<img class="<c:if test="${detail.hasIcon}">icon</c:if><c:if test="${not detail.hasIcon}">image</c:if>" src="../servlet/display?file=<c:out value="${detail.rc4EncryptedThumbnailPath}"/>" border="0">
							</a>
						</td>
					</c:if>
					<td>
						<a href="viewAsset?id=<bean:write name="detail" property="assetId"/>"><bean:write name="detail" property="assetId"/></a>
					</td>
					<td>
						<bean:write name="detail" property="originalFilename"/>
					</td>
					<td>
						<fmt:formatDate value="${detail.downloadTime}" pattern="${dateFormat} hh:mm:ss" />
					</td>
					<td>
						<bean:write name="detail" property="reasonForDownload" filter="false"/>
						<logic:iterate name="detail" property="secondaryUsageTypes" id="secondaryUsageType">
							<br/><bean:write name="secondaryUsageType"/>
						</logic:iterate>
					</td>
					<td>
						<c:if test="${detail.userId > 0}">
							<bean:write name="detail" property="userFullname" filter="false"/>
						</c:if>
						<c:if test="${detail.userId <= 0}">
							Public User
						</c:if>
					</td>
					<c:if test="${structuredAddress == true}">
					<td>
						<logic:notEmpty name="detail" property="countryName">
							<bean:write name="detail" property="countryName" filter="false"/>
						</logic:notEmpty>
						<logic:empty name="detail" property="countryName">
							-
						</logic:empty>
					</td>
					</c:if>
					<td>
						<logic:notEmpty name="detail" property="organisation">
							<bean:write name="detail" property="organisation" filter="false"/>
						</logic:notEmpty>
						<logic:empty name="detail" property="organisation">
							-
						</logic:empty>
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