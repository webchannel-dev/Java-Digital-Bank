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
	<c:set var="reportForm" value="${assetReportForm}" />
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	
	<div class="head">
		<%@include file="back_to_reports.jsp"%>
	</div>
	
	<h2>Asset Report by Date and/or Size</h2>

	<bean:size id="size" name="assetReportForm" property="details" />
	<logic:empty name="assetReportForm" property="details">
		<p>There are no <bright:cmsWrite identifier="items" filter="false" /> for the given date range.</p>
	</logic:empty>
	<logic:notEmpty name="assetReportForm" property="details">
		<p>
			<strong><c:out value="${size}" /></strong> <bright:cmsWrite identifier="items" filter="false" />
			<c:choose>
				<c:when test="${assetReportForm.dateType == 1}">
					were uploaded
				</c:when>
				<c:when test="${assetReportForm.dateType == 2}">
					were modifed
				</c:when>
			</c:choose>
			in the given period.
		</p>
		<h3>
		Report:
		<c:if test="${reportForm.startDate==null && reportForm.endDate==null && reportForm.fileSize le 0}">
			all assets
		</c:if> 
		<c:if test="${reportForm.startDate!=null || reportForm.endDate!=null}">
			<c:choose>
				<c:when test="${assetReportForm.dateType == 1}">
					Added
				</c:when>
				<c:when test="${assetReportForm.dateType == 2}">
					Modified
				</c:when>
			</c:choose> 
			Date
			<logic:notEmpty name="reportForm" property="startDate">
				from
				<fmt:formatDate value="${reportForm.startDate}" pattern="${dateFormat}" />
			</logic:notEmpty>
			<logic:notEmpty name="reportForm" property="endDate">
				to 
				<fmt:formatDate value="${reportForm.endDate}" pattern="${dateFormat}" />
			</logic:notEmpty>			
			<c:if test="${reportForm.fileSize>0}">
				,
			</c:if>
		</c:if>
		<c:if test="${reportForm.fileSize>0}">
			File Size
			<c:choose>
				<c:when test="${reportForm.fileSizeOperator==1}">
					less than
				</c:when>
				<c:otherwise>
					more than
				</c:otherwise>
			</c:choose>
			<bean:write name="reportForm" property="fileSize" format=",###,###.#####"/>
			<c:choose>
				<c:when test="${reportForm.fileSizeMultiplier==1}">
					bytes
				</c:when>
				<c:when test="${reportForm.fileSizeMultiplier==1024}">
					Kb
				</c:when>
				<c:when test="${reportForm.fileSizeMultiplier==(1024*1024)}">
					Mb
				</c:when>
				<c:when test="${reportForm.fileSizeMultiplier==(1024*1024*1024)}">
					Gb
				</c:when>
			</c:choose>
		</c:if>
		</h3>
		<div class="hr"></div>
		
		<table cellspacing="0" class="report permissions" summary="Usage report">		
			<tr>
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
					Date Uploaded
				</th>
				<th>
					Added By
				</th>
				<th>
					Date Modified
				</th>
				<th>
					Modified By
				</th>
				<th>
					Size (Mb)
				</th>
			</tr>
			<logic:iterate name="assetReportForm" property="details" id="asset">
				<tr>
					<c:if test="${showThumbnails}">
						<td>
							<a href="viewAsset?id=<bean:write name='asset' property='id'/>"><img src="../servlet/display?file=<bean:write name='asset' property='thumbnailImageFile.path'/>" class="<c:if test="${not asset.hasConvertedThumbnail}">icon</c:if><c:if test="${asset.hasConvertedThumbnail}">image</c:if>"/></a>
						</td>
					</c:if>
					<td>
						<a href="viewAsset?id=<bean:write name='asset' property='id'/>"><bean:write name="asset" property="id" /></a>
					</td>
					<td>
						<bean:write name="asset" property="originalFilename" filter="false" />
					</td>
					<td>
						<fmt:formatDate value="${asset.dateAdded}" pattern="${dateFormat}" />
					</td>
					<td>
						<bean:write name="asset" property="addedByUser.username" filter="false"/>
					</td>
					<td>
						<fmt:formatDate value="${asset.dateLastModified}" pattern="${dateFormat}" />
					</td>
					<td>
						<bean:write name="asset" property="lastModifiedByUser.username" filter="false" />
					</td>
					<td style="text-align: right;">
						<c:set var="fileSizeInMb" value="${asset.fileSizeInBytes / 1048576}"/>
						<bean:write name="fileSizeInMb" format="###,##0.00"/>
					</td>
				</tr>
			</logic:iterate>
		</table>
		
		<p>
			<a href="downloadAssetReport?reportType=<bean:write name="assetReportForm" property="reportType"/>&startDateString=<bean:write name="assetReportForm" property="startDateString"/>&endDateString=<bean:write name="assetReportForm" property="endDateString"/>&sortAscending=<bean:write name="assetReportForm" property="sortAscending"/>&dateType=<bean:write name="assetReportForm" property="dateType"/>&fileSize=<bean:write name="assetReportForm" property="fileSize"/>&fileSizeOperator=<bean:write name="assetReportForm" property="fileSizeOperator"/>&fileSizeMultiplier=<bean:write name="assetReportForm" property="fileSizeMultiplier"/>&attribute.id=<bean:write name="assetReportForm" property="attribute.id"/>&contentType=application/vnd.ms-excel">Download as excel file...</a>
		</p>
		
	</logic:notEmpty>
	
	<div class="hr"></div>
	
	<p><%@include file="back_to_reports.jsp"%></p>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>