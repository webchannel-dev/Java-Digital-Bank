<%@page import="com.bright.framework.util.*"%>

<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Ben Browning	17-Feb-2006		HTML/CSS Tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<%-- Clear the return url and clear breadcrumb trail, since this page has a view link --%>
<c:remove scope="session" var="breadcrumbTrail" />	
<c:remove scope="session" var="imageDetailReturnUrl" />
<c:remove scope="session" var="imageDetailReturnName" />



<head>
	
	<title><bright:cmsWrite identifier="title-upload-success" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="upload"/>
</head>

<bean:parameter id="assetId" name="id"/>

<body id="importPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-upload-success" filter="false" /></h1> 

	<bean:parameter id="addingFromBrowseCatId" name="addingFromBrowseCatId" value="-1" />
	<bean:parameter id="addingFromBrowseTreeId" name="addingFromBrowseTreeId" value="-1" />
	
	<c:set var="viewAssetUrl" value="viewAsset?id=${assetId}" />
	<c:set var="addAnotherFileUrl" value="../action/viewUploadAssetFile" />
	<c:if test="${addingFromBrowseCatId > 0 && addingFromBrowseTreeId > 0}"> 
		<c:set var="addAnotherFileUrl" value="${addAnotherFileUrl}?addingFromBrowseCatId=${addingFromBrowseCatId}&addingFromBrowseTreeId=${addingFromBrowseTreeId}" />
	</c:if>

	<bright:cmsWrite identifier="intro-upload-success" filter="false" replaceVariables="true" />
	
	<logic:present parameter="isParent">
		<bean:parameter id="childName" value="child" name="childName"/>
		<bean:parameter id="childrenName" value="children" name="childrenName"/>
		<bean:parameter id="typeName" value="item" name="typeName"/>
		
		<%--  Steve 07-Jun-2011: updated the cms text to remove hyperlinks, which are now shown separately below as on the view page --%>
		<%--  No need to check for upload permission since have it to get this screen --%>
		<p><bright:cmsWrite identifier="upload-success-parent" filter="false" replaceVariables="true" /> </p>
		
		<p>
		
			<%-- For these two links we can just specify the parentId parameter --%>
			<a href="../action/viewUploadAssetFile?parentId=<bean:write name='assetId' />"><bright:cmsWrite identifier="link-add-single" /> <bright:write name="childName" encodeForUrl="true" /></a> |		
			<a href="../action/viewDataImport?parentId=<bean:write name='assetId'/>"><bright:cmsWrite identifier="link-add-multiple" /> <bright:write name="childrenName" encodeForUrl="true"/></a> |
			
			<%--  For the search link we should pass selectedEntities parameters. 
			These are specified in the entityId param as a comma separated list.
			We'll do the conversion in a scriptlet since this page has no Action class. --%>
			<%
				String sEntityIds = request.getParameter("entityId");
				String sSelectedEntitiesParams = "";
				if (StringUtil.stringIsPopulated(sEntityIds))
				{
					long[] alIds = StringUtil.convertToArrayOfLongs(sEntityIds, ",");
					
					for (long lId : alIds)
					{
						sSelectedEntitiesParams += "&amp;selectedEntities=" + lId;
					}
				}	
				
				pageContext.setAttribute("sSelectedEntitiesParams", sSelectedEntitiesParams);
			%>		
		 	<a href="../action/selectAssetBySearch?selectUrl=relateSelectedAssets&amp;relationName=<bright:write name="childrenName" encodeForUrl="true"/>&amp;newSearch=true&amp;relationshipTypeId=2&amp;sourceAssetId=<bean:write name="assetId"/><bean:write name="sSelectedEntitiesParams" filter="false" />"><bright:cmsWrite identifier="link-find-multiple" filter="false"/> <bright:write name="childrenName" encodeForUrl="true"/></a>
		
		</p>
	</logic:present>
	
	<c:if test="${!empty param.unsubmitted}">
		<p>
			<bright:cmsWrite identifier="snippet-upload-success-unsubmitted" filter="false" />
			<a href="viewUnsubmittedAssets"><bright:cmsWrite identifier="link-view-unsubmitted" filter="false" /></a>
		</p>
	</c:if>

	<c:if test="${addingFromBrowseCatId > 0 && addingFromBrowseTreeId > 0}"> 
		<div class="hr"></div>
		<c:set var="actionUrl" value="browseItems" />
		<c:choose>
			<c:when test="${addingFromBrowseTreeId == 2}">
				<c:set var="type"><bright:cmsWrite identifier="access-level-node" /></c:set>
				<c:if test="${categoryExplorerType == 'accesslevels'}">
					<c:set var="actionUrl" value="viewHome" />
				</c:if>
			</c:when>
			<c:otherwise>
				<c:set var="type"><bright:cmsWrite identifier="category-node" /></c:set>
				<c:if test="${categoryExplorerType == 'categories'}">
					<c:set var="actionUrl" value="viewHome" />
				</c:if>
			</c:otherwise>
		</c:choose>
		
		
		<p><a href="<c:out value='${actionUrl}' />?categoryId=<bean:write name='addingFromBrowseCatId'/>&categoryTypeId=<bean:write name='addingFromBrowseTreeId'/>"><bright:cmsWrite identifier="link-return-to-browse" filter="false" replaceVariables="true" /></a></p>
	</c:if>
	
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>