<jsp:include page="/jsp/standard/en/inc/doctype_html.jsp" />

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<c:set scope="request" var="imageDetailReturnUrl" value="/action/viewAsset?id=${inDesignLinkedAssets.inDesignAsset.id}"/>
<c:set scope="request" var="imageDetailReturnName" ><bright:cmsWrite identifier="heading-asset-details" /></c:set>

<head>
	<title><bright:cmsWrite identifier="title-indesign-linked-assets"/></title>
	<jsp:include page="/jsp/standard/en/inc/head-elements.jsp" />
	<bean:define toScope="request" id="section" value=""/> 
	<bean:define toScope="request" id="helpsection" value="view_asset"/>
</head>

<body>
	<%@include file="/jsp/standard/en/inc/body_start.jsp"%>

	<c:set var="linkedAssetName" value="${inDesignLinkedAssets.inDesignAsset.fileName}" />
	<h1><bright:cmsWrite identifier="heading-indesign-linked-assets" replaceVariables="true" /></h1>

	
	<jsp:include page="/jsp/standard/en/public/inc_file_breadcrumb.jsp" />

	<c:set var="missingFilesList"  value="${inDesignLinkedAssets.missingAssetsFilenames}" />
	<logic:notEmpty name="missingFilesList">
		<div class="warning">
			<bright:cmsWrite identifier="missingInDesignLinkedResourceOnView" />
			<ul>
				<logic:iterate name="missingFilesList" id="filename">
					<li><c:out value="${filename}" /></li>
				</logic:iterate>
			</ul>
		</div>
	</logic:notEmpty>
	
	<%-- Display the linked assets --%>
	<c:set var="searchResults" value="${inDesignLinkedAssets.allAssetsLinkedInDB}" scope="request" />
	<jsp:include page="./inc/inc_view_thumbnail_collection.jsp" />

	<%-- Display the assets that have duplicate filename conflicts which are not sorted out yet --%>
	<c:set var="duplicateResources"  value="${inDesignLinkedAssets.ambiguousResources}" />
	<logic:notEmpty name="duplicateResources">
		<div class="warning">
			<bright:cmsWrite identifier="duplicateAssetsInfoOnView" filter="false"/>
		</div>
	
		<logic:iterate name="duplicateResources" id="linkedResource">
			<h3>${linkedResource.leafname}</h3>
			<c:set var="searchResults" value="${linkedResource.allMatching}" scope="request" />
	    	<jsp:include page="./inc/inc_view_thumbnail_collection.jsp" />
		</logic:iterate>
	</logic:notEmpty>
	
	<%@include file="/jsp/standard/en/inc/body_end.jsp"%>
</body>
</html>