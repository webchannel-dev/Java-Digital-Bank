<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home	02-Jun-2008		Created
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>		

<logic:present parameter="parentId">
	<bean:parameter id="backToAssetId" name="parentId"/>
	<bean:parameter id="parentId" name="parentId"/>
</logic:present>
<logic:notPresent parameter="parentId">
	<bean:define id="backToAssetId" name="repurposingForm" property="assetId"/>
</logic:notPresent>
			
<head>
	
	<title><bright:cmsWrite identifier="title-repurposed-images" filter="false" /></title> 
	<bean:define id="section" value=""/>
	<%@include file="../inc/head-elements.jsp"%>
</head>

<body id="detailsPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-repurposed-images" filter="false" /></h1>

	<c:set var="url">viewDownloadImage?id=<bean:write name="repurposingForm" property="assetId"/>&repurposeAsset=true<c:if test="${not empty parentId}">&parentId=<bean:write name="parentId"/></c:if></c:set>
	<c:if test="${not empty repurposingForm.repurposedVersions}">
		<p><bright:cmsWrite identifier="snippet-repurposed-images-intro" replaceVariables="true" filter="false"/></p>
	</c:if>
	<c:if test="${empty repurposingForm.repurposedVersions}">
		<p><bright:cmsWrite identifier="snippet-no-repurposed-images" filter="false" replaceVariables="true"/></p>
	</c:if>

	<div class="hr"></div>
		
	<logic:iterate name="repurposingForm" property="repurposedVersions" id="image">
		<table class="form"  style="width:auto; margin-bottom: 0px" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<div style="<c:if test="${image.width>768}">width:768px; overflow:auto;</c:if><c:if test="${image.height>500}">height: 500px; overflow:auto;</c:if>">
						<a href="viewRepurposedImage?id=<bean:write name="image" property="id"/>&assetId=<bean:write name="repurposingForm" property="assetId"/>">
							<img style="border: 1px #aaa solid; margin-bottom:1em;" src="<bean:write name="repurposingForm" property="baseUrl"/>/<bean:write name="image" property="url"/>" height="<bean:write name="image" property="height"/>" width="<bean:write name="image" property="width"/>">
						</a>
					</div>
					<p><a href="viewRepurposedImage?id=<bean:write name="image" property="id"/>&assetId=<bean:write name="repurposingForm" property="assetId"/>"><bright:cmsWrite identifier="link-view-details" filter="false"/> &raquo;</a></p>
				</td>
			<tr>
				<td>
					<p><bean:write name="image" property="width"/> <span class="disabled">(w)</span> x <bean:write name="image" property="height"/> <span class="disabled">(h)</span>
					- <bright:cmsWrite identifier="snippet-version-created-on" filter="false"/> <bean:write name="image" property="createdDate" format="dd/MM/yyyy"/>
					<c:if test="${userprofile.isAdmin && not empty image.createdByUser}"> 
						by <bean:write name="image" property="createdByUser.fullName" filter="false"/> <c:if test="${not empty image.createdByUser.emailAddress}">(<bean:write name="image" property="createdByUser.emailAddress" filter="false"/>)</c:if>
					</c:if>
					 <c:if test="${userprofile.isAdmin && empty image.createdByUser}">
					 	by a user who was not logged in or has been deleted
					 </c:if></p>
				</td>
			</tr>
			</tr>
			<td style="padding-right:0px;">
				<div style="width:690px; overflow: auto;">
					<bright:cmsWrite identifier="label-image-url" filter="false"/>
					<form action=""> 
					 	<input value="<bean:write name="repurposingForm" property="baseUrl"/>/<bean:write name="image" property="url"/>" style="width: auto;" size="130" onclick="this.focus();this.select();" readonly/>
					</form>
				</div>
			</td>
			</tr>
			<td style="padding-right:0px;">
				<div style="width:690px; overflow: auto;">
					<bright:cmsWrite identifier="label-html-code" filter="false"/>
					<form action=""> 
					 	<input value="<bean:write name="image" property="embeddableHtml" />" style="width: auto;" size="130" onclick="this.focus();this.select();" readonly/>
					</form>
				</div>
			</td>
			</tr>
		</table>
		<div class="hr"></div>
	</logic:iterate>
	
	<a href="viewAsset?id=<bean:write name="backToAssetId"/>"><bright:cmsWrite identifier="link-back-item" filter="false"/></a>
	<c:if test="${not empty repurposingForm.repurposedVersions}">
		|
		<a href="viewDownloadImage?id=<bean:write name="repurposingForm" property="assetId"/>&repurposeAsset=true<c:if test="${not empty parentId}">&parentId=<bean:write name="parentId"/></c:if>"><bright:cmsWrite identifier="link-create-new-version" filter="false"/></a>
	</c:if>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>