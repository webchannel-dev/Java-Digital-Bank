<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Steve Bryan		07-Mar-2006		Created
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
	<bean:define id="pagetitle" value="Asset Reports"/>
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	
	<div class="head">
		<%@include file="back_to_reports.jsp"%>
	</div>
	
	<h2><bright:cmsWrite identifier="item" filter="false" case="mixed" /> By Keyword Report</h2>

	<logic:empty name="assetReportForm" property="details">
		<p>There are no <bright:cmsWrite identifier="items" filter="false" />.</p>
	</logic:empty>
	<logic:notEmpty name="assetReportForm" property="details">
		<p>
			This report shows all <bright:cmsWrite identifier="items" filter="false" /> by keyword.
		</p>
		<div class="hr"></div>
		
		<table cellspacing="0" class="report permissions" summary="Usage report">		
			<tr>
				<th>Keyword</th>
				<th>Number of <bright:cmsWrite identifier="item" filter="false" case="mixed" /></th>
				<c:if test="${showThumbnails}">
					<th>&nbsp;</th>
				</c:if>
				<th>
					<bright:cmsWrite identifier="item" filter="false" case="mixed" /> ID
				</th>
				<th>
					Original Filename
				</th>
				<th>
					Date Last Modified
				</th>
				<th>
					Modified By
				</th>
			</tr>
			<logic:iterate name="assetReportForm" property="details" id="record">
				<tr class="downloadbreakdown">
					<td>
						<c:choose>
							<c:when test="${record.id == 0}">
								(None)
							</c:when>
							<c:otherwise>
								<bean:write name="record" property="name" filter="false"/>
							</c:otherwise>
						</c:choose>						
					</td>
					<td>
						<bean:write name="record" property="numberOfAssets"/>
					</td>
					<c:if test="${showThumbnails}">
						<td></td>
					</c:if>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				
				<logic:iterate name="record" property="assets" id="asset">
					<tr>
						<td></td>
						<td></td>
						<c:if test="${showThumbnails}">
							<td>
								<a href="viewAsset?id=<bean:write name='asset' property='id'/>"><img src="../servlet/display?file=<bean:write name='asset' property='displayThumbnailImageFile.path'/>" class="<c:if test="${not asset.hasConvertedThumbnail}">icon</c:if><c:if test="${asset.hasConvertedThumbnail}">image</c:if>"/></a>
							</td>
						</c:if>
						<td>
							<a href="viewAsset?id=<bean:write name='asset' property='id'/>"><bean:write name="asset" property="id" /></a>
						</td>
						<td>
							<bean:write name="asset" property="originalFilename" filter="false"/>
						</td>
						<td>
							<fmt:formatDate value="${asset.dateLastModified}" pattern="${dateFormat}" />
						</td>
						<td>
							<bean:write name="asset" property="lastModifiedByUser.username" filter="false"/>
						</td>
				</tr>
				</logic:iterate>
			</logic:iterate>
		</table>
	</logic:notEmpty>

	<div class="hr"></div>
	
	<p><%@include file="back_to_reports.jsp"%></p>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>