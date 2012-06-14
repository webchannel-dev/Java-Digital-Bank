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
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewAssetBox"/>
<c:set scope="session" var="imageDetailReturnName"><bright:cmsWrite identifier="a-lightbox" filter="false"/></c:set>

<head>
	<c:set var="lightBoxName" value="${userprofile.assetBox.name}"/>
	<title><c:out value="${pageTitle}" /></title>

	<jsp:include flush="true" page="../inc/head-elements.jsp"/>
	
	<bean:define id="section" value="public-lightbox"/>
	<c:set var="tabId" value="publicAssetBox" scope="request"/>
	<script type="text/javascript" src="../js/lib/scriptaculous.js"></script>
	<script type="text/javascript">
		//Global variable which keeps count of the number of assets selected
		var checkedCount = 0;
		//Store text for confirming removal from assetbox
		var confirmRemoveMessage = "<bright:cmsWrite identifier='js-confirm-remove-selected-lightbox' filter='false'/>"
	</script>	
	<script type="text/javascript" src="../js/assetbox.js"></script>
</head>

<body id="lightboxPage" class="assetSelectPage">
	<c:set var="displayAttributeGroup" value="3" scope="request" />
	<%@include file="../inc/body_start.jsp"%>
	
	<jsp:include page="inc_lightbox_tabs.jsp"/> 
	<jsp:include page="inc_assetbox_messages.jsp"/>	
	
	<html:form action="viewAssetBox" method="get">
		<p>
			<bright:cmsWrite identifier="lightbox-intro" filter="false"/>
			<c:if test="${assetBoxForm.publicPage==1}">
				<a href="../action/viewPublicAssetBoxes"><bright:cmsWrite identifier="link-back" filter="false"/></a>
			</c:if>
		
		</p>
	</html:form>
	
	<ul class="lightbox clearfix" id="lightboxUl">
		<logic:iterate name="assetBoxForm" property="listCanDownload" id="assetinlist" indexId="index" scope="request">
			<c:set var="index" value="${index}" scope="request"/>
			<c:set var="assetinlist" value="${assetinlist}" scope="request"/>
			<c:set var="asset" value="${assetinlist.asset}" scope="request"/>	
			<c:set var="assetState" value="1,2" scope="request"/>
			<c:set var="listTotal" value="${assetBoxForm.noCanDownload + assetBoxForm.noApprovalApproved}" scope="request"/>
			<c:set var="collection" value="${imageDetailReturnName}: ${subheadReadyDownload}" scope="request"/>
			<jsp:include page="../public/inc_public_assetbox_asset.jsp"/>	 		
		</logic:iterate>
	</ul>
	
	
		 				
			
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>

