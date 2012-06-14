<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home	25-Mar-2007		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="getRelatedAssets" settingName="get-related-assets"/>
<bright:applicationSetting id="showRequestOnCd" settingName="show-request-on-cd"/>
<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="showImageTitle" settingName="show-image-title-on-browse"/>
<bright:applicationSetting id="titleMaxLength" settingName="browse-title-max-length"/>
<bright:applicationSetting id="numLeastPopularAssets" settingName="num-least-popular-assets"/>
<bright:applicationSetting id="highlightIncompleteAssets" settingName="highlight-incomplete-assets"/>
<bright:applicationSetting id="highlightRestrictedAssets" settingName="highlight-restricted-assets"/>

<c:set var="heading"><bright:cmsWrite identifier="heading-most-downloaded" filter="false" case="mixed"/></c:set>

<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewMostDownloadedAssets?page=${browseItemsForm.searchResults.pageIndex}&pageSize=${browseItemsForm.searchResults.pageSize}"/>
<c:set scope="session" var="imageDetailReturnName" value="${heading}"/>



<head>
	
	<title><bright:cmsWrite identifier="title-most-downloaded" filter="false" case="mixed"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value=""/>
</head>

<body>

	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="tab-browse-popularity" filter="false"/></h1>
	
	<bean:define id="tabId" value="browseByPopularity"/>
	<%@include file="../public/inc_browse_tabs.jsp"%>

	<div class="head <c:if test="${tabsPresent}">tabsAbove</c:if>">
   	<bright:cmsWrite identifier="you-are-here" filter="false" />&nbsp;
   	<a href="../action/browseAssetsByPopularity"><bright:cmsWrite identifier="tab-browse-popularity" filter="false" /></a>&nbsp;&raquo;
   	<bright:cmsWrite identifier="heading-most-downloaded" filter="false" case="mixed"/>
   </div>

	<c:set var="formBean" value="${browseItemsForm}"/>
	<c:set var="linkUrl" value="viewMostDownloadedAssets?t=1"/>
	<c:set var="styleClass" value="pager browsePager"/>
	<%@include file="../inc/pager.jsp"%>
		
	<h2><bright:cmsWrite identifier="heading-most-downloaded" filter="false" case="mixed"/></h2> 
	
	<!-- The intro text: this comes from the database (change in the 'Content' area of Admin) -->
	<bright:cmsWrite identifier="intro-most-downloaded" filter="false" />
	
	<div class="clearing">&nbsp;</div>
	
	<ul class="lightbox clearfix">
	
		<c:set var="showDownloads" value="true"/>
		<bean:define id="sectionPage" value=""/>
		
		<logic:present parameter="page">
			<bean:parameter id="p_page" name="page"/>
		</logic:present>
		<logic:present parameter="pageSize">
			<bean:parameter id="p_pageSize" name="pageSize"/>
		</logic:present>
		
		<logic:notPresent name="forwardParams">
			<c:set var="forwardParams">forward=/action/viewMostDownloadedAssets<c:if test="${not empty p_page}">&page=<c:out value="${p_page}"/></c:if><c:if test="${not empty p_page}">&pageSize=<c:out value="${p_pageSize}"/></c:if></c:set>
		</logic:notPresent>
		
		<c:set var="popularityId" value="2"/>
		
		<logic:iterate name='browseItemsForm' property='searchResults.searchResults' id='item' indexId='idx'>
			
			<%@include file="../inc/inc_browse_asset.jsp"%>

		</logic:iterate>
  
  	</ul>
  	
  	<%@include file="../inc/pager.jsp"%>
	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>