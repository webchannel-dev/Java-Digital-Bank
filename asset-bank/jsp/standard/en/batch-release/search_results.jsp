<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		02-Feb-2011
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	
	<title><bright:cmsWrite identifier="title-batch-release-search" filter="false"/></title>
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="batchReleases"/>
	
	<c:set var="fromPage"><bright:cmsWrite identifier="tab-batch-release-search" filter="false" case="mixed" /></c:set>
	<c:set var="fromUrl" value="viewBatchReleaseSearch"/>
	<c:set var="tabId" value="batchReleaseSearch" scope="request" />

</head>

<body id="searchPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<%@include file="../public/inc_search_tabs.jsp"%>
	<c:if test="${not empty fromUrl}">
		<p><bright:cmsWrite identifier="you-are-here" filter="false" />&nbsp;<a href="<bean:write name="fromUrl" filter="false"/>"><bean:write name="fromPage" filter="false"/></a> &raquo; <bright:cmsWrite identifier="heading-search-results" filter="false"/></p>
	</c:if>
	
	<bean:size name="results" property="results" id="numberHits" />
	
	<p><c:choose><c:when test="${numberHits == 0 || numberHits > 1}"><c:choose><c:when test="${numberHits < results.total}"><bean:define id="totalHits" name="results" property="total" /><bright:cmsWrite identifier='search-results-restricted' filter="false" replaceVariables="true" /></c:when><c:otherwise><bright:cmsWrite identifier='search-results' filter="false" replaceVariables="true" /></c:otherwise></c:choose></c:when><c:otherwise><bright:cmsWrite identifier='search-results-one' filter="false" replaceVariables="true" /></c:otherwise></c:choose>  |  <a href="viewBatchReleaseSearch?refineSearch=1"><bright:cmsWrite identifier="refine-search-results" filter="false"/></a>&nbsp;|&nbsp;<a href="viewBatchReleaseSearch"><bright:cmsWrite identifier="link-search-again" filter="false"/></a></p>
	
	
	<bean:define id="releases" name="results" property="results" />
	<bean:define id="extraParams" value="search=true" />
	<%@include file="inc_list.jsp"%>

	
	<c:if test="${userprofile.canManageBatchReleases}">
		<p><a href="viewEditBatchRelease"><bright:cmsWrite identifier="link-add-new-batch-release" filter="false"/></a></p>
	</c:if>


	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>