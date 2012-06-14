<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home	12-Sep-2008		Created
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
	
	<title><bright:cmsWrite identifier="title-repurposed-audio-clips" filter="false" /></title> 
	<bean:define id="section" value=""/>
	<%@include file="../inc/head-elements.jsp"%>
</head>

<body id="detailsPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-repurposed-audio-clips" filter="false" /></h1>

	<c:set var="url">viewDownloadAudio?id=<bean:write name="repurposingForm" property="assetId"/>&repurposeAsset=true<c:if test="${not empty parentId}">&parentId=<bean:write name="parentId"/></c:if></c:set>
	<c:if test="${not empty repurposingForm.repurposedVersions}">
		<p><bright:cmsWrite identifier="snippet-repurposed-audio-clips-intro" replaceVariables="true" filter="false"/></p>
	</c:if>
	<c:if test="${empty repurposingForm.repurposedVersions}">
		<p><bright:cmsWrite identifier="snippet-no-repurposed-audio-clips" filter="false" replaceVariables="true"/></p>
	</c:if>

	<div class="hr"></div>
		
	<logic:iterate name="repurposingForm" property="repurposedVersions" id="version" indexId="index">
		<table class="form"  style="width:auto; margin-bottom: 0px" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<c:set var="height" value="74"/>
					<c:set var="width" value="300"/>
					<bean:define id="file" name="version" property="url"/>
					<bean:define id="autoplay" value="false"/>
					
					<bean:write name="version" property="embeddableHtml" filter="false"/>

					<br/>
					<a href="viewRepurposedAudioClip?id=<bean:write name="version" property="id"/>&assetId=<bean:write name="repurposingForm" property="assetId"/>"><bright:cmsWrite identifier="link-view-details" filter="false"/> &raquo;</a>
				</td>
			<tr>
				<td>
					<c:set var="duration" value="${version.duration / 1000}"/> 
					<bean:write name="duration" format="0"/> <bright:cmsWrite identifier="snippet-seconds" filter="false" />
					- <bright:cmsWrite identifier="snippet-version-created-on" filter="false"/> <bean:write name="version" property="createdDate" format="dd/MM/yyyy"/>
					<c:if test="${userprofile.isAdmin && not empty version.createdByUser}"> 
						by <bean:write name="version" property="createdByUser.fullName" /> <c:if test="${not empty version.createdByUser.emailAddress}">(<bean:write name="version" property="createdByUser.emailAddress" />)</c:if>
					</c:if>
					 <c:if test="${userprofile.isAdmin && empty version.createdByUser}">
					 	by a user who was not logged in or has been deleted
					 </c:if>
				</td>
			</tr>
			<tr>
			<td style="padding-right:0px;">
				<div style="width:690px; overflow: auto;">
					<form action=""> 
					 	<textarea style="width: auto;" cols="129" rows="3" onclick="this.focus();this.select();" readonly><bean:write name="version" property="embeddableHtml" /></textarea>
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
		<a href="viewDownloadAudio?id=<bean:write name="repurposingForm" property="assetId"/>&repurposeAsset=true<c:if test="${not empty parentId}">&parentId=<bean:write name="parentId"/></c:if>"><bright:cmsWrite identifier="link-create-new-version" filter="false"/></a>
	</c:if>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>