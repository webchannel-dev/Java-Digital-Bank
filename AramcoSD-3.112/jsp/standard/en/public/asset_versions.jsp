<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1	29-Feb-2008		James Home	Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	<title><bright:cmsWrite identifier="title-asset-versions" case="mixed" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value=""/>
</head>

<body>
	<c:set scope="session" var="imageDetailReturnName" value="Item Versions"/>

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-asset-versions" case="mixed"  /></h1>
	<ul class="versions">
	<bean:define id="disablePreview" value="true"/>

	<bright:refDataList componentName="BatchReleaseManager" transactionManagerName="DBTransactionManager"  methodName="getMetadataMap" argumentValue="${assetListForm.batchReleaseIds}" id="brMap"/>
	<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewAssetVersions?id=${latestVersionId}"/>

	<logic:iterate name='assetListForm' property='assets' id='item' indexId='idx'>
		<c:if test="${item.typeId!=2}">
			<c:set var="resultImgClass" value="icon"/>
		</c:if>
		<c:if test="${item.typeId==2}">
			<c:set var="resultImgClass" value="image"/>
		</c:if>
			
		<li class="clearfix">
			<a href="../action/viewAsset?id=<bean:write name='item' property='id'/>" >
				<logic:empty name="item" property="displayPreviewImageFile.path">
					<bean:define id="asset" name="item" />
					<%@include file="../inc/view_thumbnail.jsp"%>		
				</logic:empty>
			</a>
			<div class="details">		
				<strong>Version: <bean:write name="asset" property="versionNumber" /></strong><br />
				
				<c:choose>
					<c:when test="${asset.versionNumber==1}">
						Added: <bean:write name="asset" property="dateAdded" format="dd/MM/yyyy HH:mm:ss"/>
						by <bean:write name="asset" property="addedByUser.username" /> <br />
					</c:when>
					<c:otherwise>
						Version last modified: <bean:write name="asset" property="dateLastModified" format="dd/MM/yyyy HH:mm:ss"/>
						by <bean:write name="asset" property="lastModifiedByUser.username" /> <br />
					</c:otherwise>
				</c:choose>

				<c:if test="${asset.currentBatchReleaseId > 0 && not empty brMap}">
					<br />
					<bean:define id="release" name="brMap" property="metadata(${asset.currentBatchReleaseId})" />
					<bean:define id="brAsset" name="asset" />
					<%@include file="../batch-release/inc_info_panel.jsp"%>
				</c:if>
				<a class="view" href="../action/viewAsset?id=<bean:write name='item' property='id'/>">
					<bright:cmsWrite identifier="link-view-details" filter="false" />...
				</a>
			</div>

		</li>

	</logic:iterate>
  
  	</ul>
	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>