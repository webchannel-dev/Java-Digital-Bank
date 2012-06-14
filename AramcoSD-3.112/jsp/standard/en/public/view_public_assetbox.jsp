<%@include file="../inc/doctype_html.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<c:if test="${not userprofile.isLoggedIn}"><c:set var="multipleLightboxes" value="${false}"/></c:if>

<c:set var="pageTitle"><c:if test="${multipleLightboxes}"><bright:cmsWrite identifier="a-lightbox" filter="false"/>: <bean:write name="userprofile" property="assetBox.name"/></c:if><c:if test="${!multipleLightboxes}"><bright:cmsWrite identifier="my-lightbox" filter="false"/></c:if></c:set>

<%@include file="../inc/set_this_page_url.jsp"%>

<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewPublicAssetBox?assetBoxId=${assetBoxId}&publicPage=1"/>
<c:set scope="session" var="imageDetailReturnName"><bright:cmsWrite identifier="a-lightbox" filter="false"/>: <c:out value="${publicAssetBoxName}"/></c:set>

<head>
	<c:set var="lightBoxName" value="${userprofile.assetBox.name}"/>
	<title><c:out value="${pageTitle}" /></title>

	<jsp:include flush="true" page="../inc/head-elements.jsp"/>
	
	<bean:define id="section" value="public-lightbox"/>
	<c:set var="tabId" value="publicAssetBox" scope="request"/>
</head>

<body id="lightboxPage" class="assetSelectPage">
	<c:set var="displayAttributeGroup" value="3" scope="request" />
	<%@include file="../inc/body_start.jsp"%>
	
	<jsp:include page="inc_lightbox_tabs.jsp"/> 
	<jsp:include page="inc_assetbox_messages.jsp"/>	
	
	<html:form action="viewAssetBox" method="get">
		<p>
			<bright:cmsWrite identifier="lightbox-intro" filter="false"/><a href="../action/viewPublicAssetBoxes"><bright:cmsWrite identifier="link-back" filter="false"/></a>
		</p>
	</html:form>
	
	<logic:present name="publicAssetBoxAssets">
		<ul class="lightbox clearfix" id="lightboxUl">
			<logic:iterate name="publicAssetBoxAssets" id="assetinlist" indexId="index" scope="request">
				<c:set var="index" value="${index}" scope="request"/>
				<c:set var="assetinlist" value="${assetinlist}" scope="request"/>
				<c:set var="asset" value="${assetinlist.asset}" scope="request"/>
				<c:set var="collection" value="${imageDetailReturnName}" scope="request"/>
				<jsp:include page="../public/inc_public_assetbox_asset.jsp"/>	 		
			</logic:iterate>
		</ul>
	</logic:present>

	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>

