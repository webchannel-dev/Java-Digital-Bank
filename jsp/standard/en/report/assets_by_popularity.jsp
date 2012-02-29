<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1	29-Feb-2008		James Home	Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="displayDatetimeFormat" settingName="display-datetime-format"/>

<logic:present parameter="numAssets">
	<bean:parameter id="numAssets" name="numAssets"/>
</logic:present>
<logic:present parameter="reportType">
	<bean:parameter id="reportType" name="reportType"/>
</logic:present>

<c:set var="heading">
	<c:if test="${reportType==2 || reportType==4}">Least</c:if><c:if test="${reportType==1 || reportType==3}">Most</c:if>
	<c:if test="${reportType==1 || reportType==2}">Viewed</c:if><c:if test="${reportType==3 || reportType==4}">Downloaded</c:if>
	<bright:cmsWrite identifier="items" filter="false" case="mixed"/>
</c:set>

<head>
	<title><bean:write name="heading" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value=""/>
</head>

<body>

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="heading" filter="false"/></h1> 

	<c:if test="${empty searchForm.searchResults.searchResults}">
		There are no <bright:cmsWrite identifier="items" filter="false"/> to display<br/><br/>
		<a href="viewAssetReportPage?reportType=<bean:write name="reportType"/><c:if test="${not empty numAssets}">&numAssets=<bean:write name="numAssets"/></c:if>">&laquo; Back</a>	
	</c:if>
	
	<c:if test="${not empty searchForm.searchResults.searchResults}">
		<c:set var="formBean" value="${searchForm}"/>
		<c:set var="queryString">?reportType=<bean:write name="reportType"/><c:if test="${not empty numAssets}">&numAssets=<bean:write name="numAssets"/></c:if><c:if test="${not empty pageSize}">&pageSize=<bean:write name="pageSize"/></c:if></c:set>
		<c:set var="linkUrl">viewAssetsByPopularity<bean:write name="queryString" filter="false"/></c:set>
		<c:set var="styleClass" value="pager searchPager"/>
		<%@include file="../inc/pager.jsp"%>
		<a href="viewAssetReportPage?reportType=<bean:write name="reportType"/><c:if test="${not empty numAssets}">&numAssets=<bean:write name="numAssets"/></c:if>">&laquo; Back</a>	
		| <a href="../action/exportAssetsByPopularity?reportType=<bean:write name="reportType"/><logic:notEmpty name="numAssets">&numAssets=<bean:write name="numAssets"/></logic:notEmpty>">Export results &raquo;</a>
		<br/>
		<div class="hr"></div>
	</c:if>
	
	<ul class="versions">

	<logic:iterate name='searchForm' property='searchResults.searchResults' id='item' indexId='idx'>
		<c:if test="${not empty item}">
			<bean:define id="asset" name="item" />
			<li class="clearfix">
				<a href="../action/viewAsset?id=<bean:write name='asset' property='id'/>" >
					<logic:empty name="asset" property="previewImageFile.path">
						<c:if test="${asset.hasConvertedThumbnail}"><c:set var="resultImgClass" value="image"/></c:if>
						<c:if test="${not asset.hasConvertedThumbnail}"><c:set var="resultImgClass" value="icon"/></c:if>
						<%@include file="../inc/view_thumbnail.jsp"%>		
					</logic:empty>
				</a>
				<div class="details">
					<c:if test="${not empty asset.name && asset.name!=asset.id}"><bean:write name="asset" property="name" filter="false"/> |</c:if>		
					Id: <bean:write name="asset" property="id" filter="false"/><br />
					File: <bean:write name="asset" property="fileLocation" filter="false"/> <br />
					Added: <bean:write name="asset" property="dateAdded" filter="false" format="<%= (String)pageContext.getAttribute(\"displayDatetimeFormat\") %>"/>
					by <bean:write name="asset" property="addedByUser.username" filter="false"/> <br />
					Views: <strong><bean:write name="item" property="numViews" filter="false"/></strong>, Downloads: <strong><bean:write name="item" property="numDownloads" filter="false"/></strong><br />
					<a class="view" href="../action/viewAsset?id=<bean:write name='asset' property='id'/>">
						<bright:cmsWrite identifier="link-view-details" filter="false" />...
					</a>
				</div>
			</li>
		</c:if>
	</logic:iterate>
	
	
	<c:if test="${not empty searchForm.searchResults.searchResults}">
		<%@include file="../inc/pager.jsp"%>	
		<a href="viewAssetReportPage?reportType=<bean:write name="reportType"/><c:if test="${not empty numAssets}">&numAssets=<bean:write name="numAssets"/></c:if>">&laquo; Back</a>	
		 | <a href="../action/exportAssetsByPopularity?reportType=<bean:write name="reportType"/><logic:notEmpty name="numAssets">&numAssets=<bean:write name="numAssets"/></logic:notEmpty>">Export results &raquo;</a>
	</c:if>
  
  	</ul>
	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>