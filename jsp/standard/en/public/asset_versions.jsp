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
	<title><bright:cmsWrite identifier="title-asset-versions" case="mixed" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value=""/>
</head>

<body>
	<c:set scope="session" var="imageDetailReturnName" value="Item Versions"/>

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-asset-versions" case="mixed" filter="false" /></h1> 
	
	<ul class="versions">

	<bean:define id="disablePreview" value="true"/>
	
	<logic:iterate name='assetListForm' property='assets' id='item' indexId='idx'>

		<c:if test="${idx==0}">
			<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewAssetVersions?id=${item.id}"/>
		</c:if>
	
		<c:if test="${item.typeId!=2}">
			<c:set var="resultImgClass" value="icon"/>
		</c:if>
		<c:if test="${item.typeId==2}">
			<c:set var="resultImgClass" value="image"/>
		</c:if>
			
		<li class="clearfix">
			<a href="../action/viewAsset?id=<bean:write name='item' property='id'/>" >
				<logic:empty name="item" property="previewImageFile.path">
					<bean:define id="asset" name="item" />
					<%@include file="../inc/view_thumbnail.jsp"%>		
				</logic:empty>
			</a>
			<div class="details">		
				<strong>Version: <bean:write name="asset" property="versionNumber" filter="false"/></strong><br />
				<c:if test="${asset.versionNumber==1}">Added:</c:if>
				<c:if test="${asset.versionNumber>1}">Version created:</c:if> 
				<bean:write name="asset" property="dateAdded" filter="false" format="dd/MM/yyyy HH:mm:ss"/> |
				By: <bean:write name="asset" property="addedByUser.username" filter="false"/> <br />
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