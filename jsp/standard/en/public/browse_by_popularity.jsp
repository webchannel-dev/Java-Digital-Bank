<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		01-Apr-2005		Created.
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>	

<bright:applicationSetting id="numMostPopularAssets" settingName="num-most-popular-assets"/>
<bright:applicationSetting id="numLeastPopularAssets" settingName="num-least-popular-assets"/>

<head>
	
	<title><bright:cmsWrite identifier="title-browse-by-popularity"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="browse"/>
	
</head>
<body id="browsePage">
	
	<%@include file="../inc/body_start.jsp"%>

	<h1><bright:cmsWrite identifier="heading-browse" filter="false"/></h1> 
		
	<bean:define id="tabId" value="browseByPopularity"/>
	<%@include file="../public/inc_browse_tabs.jsp"%>
   
   <div class="head <c:if test="${tabsPresent}">tabsAbove</c:if>">
   	<bright:cmsWrite identifier="you-are-here" filter="false" />&nbsp;
   	<bright:cmsWrite identifier="tab-browse-popularity" filter="false" />
   </div>
   
   <div class="categoryList">
		<ul>
			<c:if test="${numMostPopularAssets>0}">
				<li><a href="viewMostViewedAssets"><bright:cmsWrite identifier="heading-most-viewed" filter="false" case="mixed"/></a></li>
				<li><a href="viewMostDownloadedAssets"><bright:cmsWrite identifier="heading-most-downloaded" filter="false" case="mixed"/></a></li>
			</c:if>
			<c:if test="${numLeastPopularAssets>0}">
				<li><a href="viewLeastViewedAssets"><bright:cmsWrite identifier="heading-least-viewed" filter="false" case="mixed"/></a></li>
				<li><a href="viewLeastDownloadedAssets"><bright:cmsWrite identifier="heading-least-downloaded" filter="false" case="mixed"/></a></li>
			</c:if>
		</ul>
	</div>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>
