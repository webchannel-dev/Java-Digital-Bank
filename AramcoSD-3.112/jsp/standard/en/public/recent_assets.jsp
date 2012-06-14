<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	30-Mar-2007		Created.
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

<c:set var="recentAssets"><bright:cmsWrite identifier="heading-recent" filter="false" /></c:set>

<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewRecentAssets?page=${browseItemsForm.searchResults.pageIndex}&pageSize=${browseItemsForm.searchResults.pageSize}"/>
<c:set scope="session" var="imageDetailReturnName" value="${recentAssets}"/>



<head>
	
	<title><bright:cmsWrite identifier="title-recent-images" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value=""/>
</head>

<body>

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-recent" filter="false" /></h1> 
	
	<!-- The intro text: this comes from the database (change in the 'Content' area of Admin) -->
	<bright:cmsWrite identifier="recent-intro" filter="false" />
	
	<c:set var="formBean" value="${browseItemsForm}"/>
	<c:set var="linkUrl" value="viewRecentAssets?t=1"/>
	<c:set var="styleClass" value="pager browsePager"/>
	
	<bean:define id="recent" value="true"/>
	<bean:define id="promoted" value="false"/>
	
	<%@include file="../inc/pager.jsp"%>
	
	<c:set var="numRecentItems" value="${browseItemsForm.searchResults.numResults}" />
	<h3 style="margin-bottom: 6px">
		<bright:cmsWrite identifier="subhead-recent-listed-below" filter="false" replaceVariables="true" />
	</h3>

	
	
	<div class="clearing">&nbsp;</div>
	
	<ul class="lightbox clearfix">

	<logic:iterate name='browseItemsForm' property='searchResults.searchResults' id='item' indexId='idx'>

		<bean:define id="sectionPage" value="recent"/>
		
		<%@include file="inc_promoted_asset.jsp"%>

	</logic:iterate>
  
  	</ul>

	<%@include file="../inc/pager.jsp"%>
	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>