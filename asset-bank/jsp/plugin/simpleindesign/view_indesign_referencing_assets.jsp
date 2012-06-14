<jsp:include page="/jsp/standard/en/inc/doctype_html.jsp" />

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<c:set scope="request" var="imageDetailReturnUrl" value="/action/viewAsset?id=${refAsset.id}"/>
<c:set scope="request" var="imageDetailReturnName" ><bright:cmsWrite identifier="heading-asset-details" /></c:set>

<head>
	<title><bright:cmsWrite identifier="title-indesign-linking-assets"/></title>
	<jsp:include page="/jsp/standard/en/inc/head-elements.jsp" />
	<bean:define toScope="request" id="section" value=""/> 
	<bean:define toScope="request" id="helpsection" value="view_asset"/>	
</head>

<body>
	<%@include file="/jsp/standard/en/inc/body_start.jsp"%>

	<c:set var="linkedAssetName" value="${refAsset.fileName}" />
	<h1><bright:cmsWrite identifier="heading-indesign-linking-assets" replaceVariables="true" /></h1>

	<jsp:include page="/jsp/standard/en/public/inc_file_breadcrumb.jsp" />

	<%-- Display the assets that reference the given asset --%>
	<c:set var="searchResults"  value="${referencingAssets}" scope="request" />
	<jsp:include page="./inc/inc_view_thumbnail_collection.jsp" />
	
	<%@include file="/jsp/standard/en/inc/body_end.jsp"%>
</body>
</html>