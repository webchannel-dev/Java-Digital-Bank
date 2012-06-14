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

<bright:applicationSetting id="maxPreviewHeight" settingName="repurposed-video-preview-max-height"/>

<logic:present parameter="parentId">
	<bean:parameter id="backToAssetId" name="parentId"/>
	<bean:parameter id="parentId" name="parentId"/>
</logic:present>
<logic:notPresent parameter="parentId">
	<bean:define id="backToAssetId" name="repurposingForm" property="assetId"/>
</logic:notPresent>
			
<head>
	
	<title><bright:cmsWrite identifier="title-repurposed-videos" filter="false" /></title> 
	<bean:define id="section" value=""/>
	<%@include file="../inc/head-elements.jsp"%>
</head>

<body id="detailsPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-repurposed-videos" filter="false" /></h1>

	<c:set var="url">viewDownloadVideo?id=<bean:write name="repurposingForm" property="assetId"/>&repurposeAsset=true<c:if test="${not empty parentId}">&parentId=<bean:write name="parentId"/></c:if></c:set>
	<c:if test="${not empty repurposingForm.repurposedVersions}">
		<p><bright:cmsWrite identifier="snippet-repurposed-videos-intro" replaceVariables="true" filter="false"/></p>
	</c:if>
	<c:if test="${empty repurposingForm.repurposedVersions}">
		<p><bright:cmsWrite identifier="snippet-no-repurposed-videos" filter="false" replaceVariables="true"/></p>
	</c:if>

	<div class="hr"></div>
		
	<logic:iterate name="repurposingForm" property="repurposedVersions" id="video" indexId="index">
		<table class="form"  style="width:auto; margin-bottom: 0px" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<c:set var="height" value="${video.height + 28}"/>
					<c:set var="width" value="${video.width}"/>
					<bean:define id="file" name="video" property="url"/>
					<bean:define id="autoplay" value="false"/>
					<c:set var="actualSize" value="true"/>

					<c:if test="${maxPreviewHeight>0 && height-28>maxPreviewHeight}">
						<c:set var="width" value="${width*(maxPreviewHeight/height)}"/>
						<c:set var="height" value="${maxPreviewHeight + 28}"/>
						<c:set var="actualSize" value="false"/>
					</c:if>
					
					<bean:write name="video" property="embeddableHtml" filter="false"/>
					<br />

					<c:if test="${!actualSize}">
					<span class="comment">[<bright:cmsWrite identifier="snippet-smaller-then-actual-size" filter="false"/>]</span>
					<br/>
					</c:if>

					<br/>
					<p><a href="viewRepurposedVideo?id=<bean:write name="video" property="id"/>&assetId=<bean:write name="repurposingForm" property="assetId"/>"><bright:cmsWrite identifier="link-view-details-resize" filter="false"/> &raquo;</a></p>
				</td>
			<tr>
				<td>
					<c:set var="duration" value="${video.duration / 1000}"/>
					<p><bean:write name="video" property="width"/> <span class="disabled">(w)</span> x <bean:write name="video" property="height"/> <span class="disabled">(h)</span>
					, <bean:write name="duration" format="0"/> <bright:cmsWrite identifier="snippet-seconds" filter="false" />
					- <bright:cmsWrite identifier="snippet-version-created-on" filter="false"/> <bean:write name="video" property="createdDate" format="dd/MM/yyyy"/>
					<c:if test="${userprofile.isAdmin && not empty video.createdByUser}"> 
						by <bean:write name="video" property="createdByUser.fullName" /> <c:if test="${not empty video.createdByUser.emailAddress}">(<bean:write name="video" property="createdByUser.emailAddress" />)</c:if>
					</c:if>
					 <c:if test="${userprofile.isAdmin && empty video.createdByUser}">
					 	by a user who was not logged in or has been deleted
					 </c:if></p>
				</td>
			</tr>
			<tr>
			<td style="padding-right:0px;">
				<div style="width:690px; overflow: auto;">
					<form action=""> 
					 	<textarea style="width: auto;" cols="129" rows="3" onclick="this.focus();this.select();" readonly><bean:write name="video" property="embeddableHtml" /></textarea>
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
		<a href="viewDownloadVideo?id=<bean:write name="repurposingForm" property="assetId"/>&repurposeAsset=true<c:if test="${not empty parentId}">&parentId=<bean:write name="parentId"/></c:if>"><bright:cmsWrite identifier="link-create-new-version" filter="false"/></a>
	</c:if>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>