<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Ben Browning	06-Ju-2006		Created.
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


<c:set var="promotedAssets"><bright:cmsWrite identifier="heading-promoted" filter="false" /></c:set>
<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewPromotedAssets?page=${browseItemsForm.searchResults.pageIndex}&pageSize=${browseItemsForm.searchResults.pageSize}"/>
<c:set scope="session" var="imageDetailReturnName" value="${promotedAssets}"/>



<head>
	
	<title><bright:cmsWrite identifier="title-promoted-images" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value=""/>
</head>

<body>

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-promoted" filter="false" /></h1> 
	
	<!-- The intro text: this comes from the database (change in the 'Content' area of Admin) -->
	<bright:cmsWrite identifier="promoted-intro" filter="false" />
	
	<c:set var="numPromotedItems" value="${browseItemsForm.searchResults.numResults}" />
	<h3 style="margin-bottom: 6px">
		<bright:cmsWrite identifier="subhead-promoted-listed-below" filter="false" replaceVariables="true" />
	</h3>

	<c:if test="${userprofile.isAdmin && browseItemsForm.searchResults.numResults gt 0}">
		<p>
			<a href="../action/removeAllFromPromoted"  onclick="return confirm('<bright:cmsWrite identifier="js-confirm-remove-all-promoted" filter="false"/>');"><bright:cmsWrite identifier="link-remove-all-promoted" filter="false" /></a>					
		</p>
	</c:if>

	
	<c:set var="formBean" value="${browseItemsForm}"/>
	<c:set var="linkUrl" value="viewPromotedAssets?t=1"/>
	<c:set var="styleClass" value="pager browsePager"/>

	<bean:define id="recent" value="false"/>
	<bean:define id="promoted" value="true"/>
	
	<%@include file="../inc/pager.jsp"%>
	<div class="clearing">&nbsp;</div>
	<ul class="lightbox clearfix">

	<logic:iterate name='browseItemsForm' property='searchResults.searchResults' id='item' indexId='idx'>
		
		<%@include file="inc_promoted_asset.jsp"%>
		
	</logic:iterate>
  
  	</ul>
	
	<%@include file="../inc/pager.jsp"%>
	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>