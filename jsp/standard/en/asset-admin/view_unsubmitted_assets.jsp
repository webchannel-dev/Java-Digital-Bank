<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
    d1      Steve Bryan    31-Oct-2008    Created 	    
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewUnsubmittedAssets"/>
<c:set scope="session" var="imageDetailReturnName"><bright:cmsWrite identifier="heading-unsubmitted-items" filter="false"/></c:set>

<head>
	
	<title><bright:cmsWrite identifier="heading-unsubmitted-items" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="my-uploads"/>
	<bean:define id="helpsection" value="unsubmitted_assets"/>
	<bean:define id="tabId" value="user"/>

</head>

<body id="importPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1>My Uploads</h1> 

	<bean:define id="tabId" value="unsubmitted"/>
	<%@include file="../asset-admin/inc_my_uploads_tabs.jsp"%>
	

	<c:if test="${userprofile.isAdmin}">
		<p class="tabHolderPopup clearfix">
				<a class="active" href="viewUnsubmittedAssets">My Items</a>
				<a href="../action/viewUnsubmittedAssetsAdmin">Other Users</a>
		</p>
		<div id="tabContent">
	</c:if>
	
		<c:set var="returnSize" value="${assetWorksetForm.listAssets.returnedNumberResults}" />
		<c:set var="maxExceeded" value="${assetWorksetForm.listAssets.maxResultsExceeded}" />
		<c:set var="lNumUnsubmittedAssets" value="${returnSize}" />
	
		<p>
			<c:choose>
				<c:when test="${lNumUnsubmittedAssets == 1}">
					<bright:cmsWrite identifier="snippet-unsubmitted-asset" filter="false" replaceVariables="true" />
				</c:when>
				<c:otherwise>
					<bright:cmsWrite identifier="snippet-unsubmitted-assets" filter="false" replaceVariables="true" />
				</c:otherwise>
			</c:choose>
		</p>
	
		<c:set var="userIdParam" value="0" />
		<c:set var="submitUrl" value="submitUnsubmittedAsset" />
		<%@include file="inc_unsubmitted_assets.jsp"%>
	
	<c:if test="${userprofile.isAdmin}">
		</div> <!-- End of tabContent -->
	</c:if>	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>