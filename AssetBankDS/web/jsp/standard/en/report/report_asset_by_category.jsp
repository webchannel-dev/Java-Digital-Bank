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
	
	<bean:define id="section" value="reports"/>
	<bean:define id="pagetitle" value="Asset Reports"/>
	<link rel="stylesheet" type="text/css" href="../css/standard/print.css" media="print" />
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	
	<div class="head">
		<%@include file="back_to_reports.jsp"%>
	</div>
	
	<h2><bright:cmsWrite identifier="items" filter="false" case="mixed" /> By Category Report</h2>

	<logic:empty name="assetReportForm" property="details">
		<p>There are no assets.</p>
	</logic:empty>
	<logic:notEmpty name="assetReportForm" property="details">
		<p>
			This report shows the tree of descriptive categories, and the <bright:cmsWrite identifier="items" filter="false" /> in each.<br />
			Totals are given for the sum of <bright:cmsWrite identifier="items" filter="false" /> in each category including subcategories, and also
			the number of <bright:cmsWrite identifier="items" filter="false" /> explicitly within each category.<br />
			<c:if test="${assetReportForm.attribute.id > 0}">
				<bright:cmsWrite identifier="items" filter="false" case="mixed" /> are shown sorted by the attribute <bean:write name="assetReportForm" property="attribute.label" />.
			</c:if>
		</p>
		<div class="hr"></div>
		
		<table cellspacing="0" class="report permissions" summary="Usage report">		
			<tr>
				<th>
					Category name
				</th>
				<th>
					<bright:cmsWrite identifier="items" filter="false" case="mixed" /> within Category
				</th>
				<th>
					<bright:cmsWrite identifier="items" filter="false" case="mixed" /> directly in Category
				</th>
			<c:if test="${assetReportForm.attribute.id > 0}">
				<th>
					<bean:write name="assetReportForm" property="attribute.label" />
				</th>
			</c:if>
			<c:if test="${!assetReportForm.showCategoryTotalsOnly}">
				<c:if test="${showThumbnails}">
					<th>&nbsp;</th>
				</c:if>
				<th>
					<bright:cmsWrite identifier="item" filter="false" case="mixed" /> ID
				</th>
				<th>
					Approved
				</th>
			</c:if>
			</tr>
			<logic:iterate name="assetReportForm" property="details" id="record">
				<tr class="downloadbreakdown categoryRow">
					<td>
						<span class="catPermission<bean:write name='record' property='category.depth'/>"><bean:write name="record" property="category.name" filter="false"/></span>
					</td>
					<td>
						<bean:write name="record" property="numUnderCategory"/>
					</td>
					<td>
						<bean:write name="record" property="numDirectInCategory" />
					</td>
					<c:if test="${assetReportForm.attribute.id > 0}">
						<td>&nbsp;</td>
					</c:if>
					<c:if test="${!assetReportForm.showCategoryTotalsOnly}">
						<c:if test="${showThumbnails}">
							<td>&nbsp;</td>
						</c:if>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</c:if>
				</tr>
				
				<c:if test="${!assetReportForm.showCategoryTotalsOnly}">
					
					<logic:iterate name="record" property="assets" id="asset">
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<c:if test="${assetReportForm.attribute.id > 0}">
								<td>
									<bean:write name="asset" property="attributeValue" />
								</td>
							</c:if>
							<c:if test="${showThumbnails}">
								<td>
									<a href="viewAsset?id=<bean:write name='asset' property='id'/>"><img src="../servlet/display?file=<bean:write name='asset' property='rc4EncryptedThumbnailPath'/>" class="<c:if test="${asset.hasIcon}">icon</c:if><c:if test="${not asset.hasIcon}">image</c:if>"/></a>
								</td>
							</c:if>
							<td>
								<a href="viewAsset?id=<bean:write name='asset' property='id'/>"><bean:write name="asset" property="id" /></a>
							</td>
							<td>
								<c:choose>
									<c:when test="${asset.approved}">
										Yes
									</c:when>
									<c:otherwise>
										No
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</logic:iterate>
				</c:if>	
					
			</logic:iterate>
		</table>
		
		<c:if test="${!assetReportForm.showCategoryTotalsOnly}">
			<p>
				<a href="downloadAssetReport?reportType=<bean:write name="assetReportForm" property="reportType"/>&startDateString=<bean:write name="assetReportForm" property="startDateString"/>&endDateString=<bean:write name="assetReportForm" property="endDateString"/>&sortAscending=<bean:write name="assetReportForm" property="sortAscending"/>&attribute.id=<bean:write name="assetReportForm" property="attribute.id"/>&contentType=application/vnd.ms-excel">Download as excel file...</a>
			</p>
		</c:if>
		
	</logic:notEmpty>

	<div class="hr"></div>
	
	<p><%@include file="back_to_reports.jsp"%></p>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>