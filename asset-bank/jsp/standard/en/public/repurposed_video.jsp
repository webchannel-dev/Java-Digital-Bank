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
</logic:present>
<logic:notPresent parameter="parentId">
	<bean:define id="backToAssetId" name="repurposingForm" property="assetId"/>
</logic:notPresent>
		
<bean:define id="video" name="repurposingForm" property="repurposedVersion"/>

<c:set var="height" value="${video.height}"/>
<c:set var="width" value="${video.width}"/>
<c:if test="${height.class.name=='java.lang.Long'}">
	<c:set var="height" value="${height + 28}"/>
</c:if>

<head>
	
	<title><bright:cmsWrite identifier="title-repurposed-video" filter="false" /></title> 
	<bean:define id="section" value=""/>
	<%@include file="../inc/head-elements.jsp"%>
	<script src="../js/download-form.js" type="text/javascript"></script>
	<script type="text/javascript">
	//<!--
	function init()
	{
		new DownloadForm(
				<bean:write name='video' property='id'/>,
				<bean:write name='video' property='width'/>,
				<bean:write name='video' property='height'/>);
	}
	//--></script>
</head>

<body id="detailsPage" onload="init();">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-repurposed-video" filter="false" /></h1>
	
	<logic:present parameter="new">
		<p><bright:cmsWrite identifier="snippet-repurposed-video-intro" filter="false"/></p>
	</logic:present>
		
	
	<bean:define id="file" name="video" property="url"/>
	<bean:define id="autoplay" value="false"/>
	
	<bean:write name="video" property="embeddableHtml" filter="false"/>	

	<br/>

	<form action="viewRepurposedVideo" method="get" id="downloadForm" name="downloadForm">
		<input type="hidden" name="id" value="<bean:write name="repurposingForm" property="repurposedVersion.id"/>"/>
		<input type="hidden" name="assetId" value="<bean:write name="repurposingForm" property="assetId"/>"/>
		<table cellpadding="0" cellspacing="0" border="0" class="admin">
			<c:if test="${not empty video.createdByUser}">
				<tr>
					<th><bright:cmsWrite identifier="label-created-by" filter="false"/></th>
					<td><bean:write name="video" property="createdByUser.fullName" /> <c:if test="${not empty video.createdByUser.emailAddress}"><bean:write name="video" property="createdByUser.emailAddress" /></c:if>
					</td>
				</tr>
			</c:if>
			<tr>
				<th nowrap="nowrap"><bright:cmsWrite identifier="label-created-date" filter="false"/></th>
				<td><bean:write name="video" property="createdDate" format="dd/MM/yyyy hh:mm a"/></td>
			</tr>
			<tr>
				<th><bright:cmsWrite identifier="label-duration" filter="false"/></th>
				<td>
					<c:set var="duration" value="${video.duration / 1000}"/> 
					<bean:write name="duration" format="0"/> <bright:cmsWrite identifier="snippet-seconds" filter="false"/>
				</td>
			</tr>
			<tr>
				<th><bright:cmsWrite identifier="label-file-type" filter="false"/></th>
				<td><bean:write name="video" property="suffix"/></td>
			</tr>
			<tr>
				<th><bright:cmsWrite identifier="label-video-url" filter="false"/></th>
				<td>
					<span><bean:write name="repurposingForm" property="baseUrl"/>/<bean:write name="video" property="url"/></span>
				</td>
			</tr>
			<tr>
				<th><bright:cmsWrite identifier="label-width" filter="false"/></th>
				<td>
					<input type="text" class="text" id="width" name="width" value="<bean:write name="video" property="width"/>" size="4" style="width:auto;"/> <bright:cmsWrite identifier="pixels" filter="false"/>						
				</td>
			</tr>
			<tr>
				<th><bright:cmsWrite identifier="label-height" filter="false"/></th>
				<td>
					<input type="text" class="text" id="height" name="height" value="<bean:write name="video" property="height"/>" size="4" style="width:auto;"/> <bright:cmsWrite identifier="pixels" filter="false"/>
					<input type="submit" class="button" value="<bright:cmsWrite identifier="button-update" filter="false"/>"/>
					<input type="button" class="button" value="<bright:cmsWrite identifier="button-reset" filter="false"/>" onclick="window.location='viewRepurposedVideo?id=<bean:write name="repurposingForm" property="repurposedVersion.id"/>&assetId=<bean:write name="repurposingForm" property="assetId"/>';"/>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input type="checkbox" name="lockAspectRatio" checked="checked" class="checkbox" id="lockAspectRatio"/> <label for="lockAspectRatio"><bright:cmsWrite identifier="label-lock-aspect-nc" filter="false"/></label></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><span class="disabled"><bright:cmsWrite identifier="snippet-video-changes-page-only" filter="false"/></span></td>
			</tr>
			<tr>
				<th style="vertical-align: top;  width:auto;"><bright:cmsWrite identifier="label-html-code" filter="false"/></th>
				<td style="padding-right:0px;">
					<div style="width:690px; overflow: auto;">
						<form action=""> 
						 	<textarea style="width: auto;" cols="129" rows = "3" onclick="javascript:this.focus();this.select();" readonly><bean:write name="video" property="embeddableHtml"/></textarea>
						</form>
					</div>
				</td>
			</tr>
		</table>
	</form>
	<br/>
	<logic:present parameter="new">
		<bean:parameter id="isNew" name="new"/>
	</logic:present>
	<c:if test="${userprofile.isAdmin || (not empty isNew && not empty repurposingForm.repurposedVersion.createdByUser && userprofile.user.id==repurposingForm.repurposedVersion.createdByUser.id)}">
		<bean:parameter id="id" name="id"/>
		<c:choose>
			<c:when test="${not empty isNew && userprofile.user.id==repurposingForm.repurposedVersion.createdByUser.id}">
				<p><bright:cmsWrite identifier="snippet-can-delete-version" filter="false"/> <c:if test="${!userprofile.isAdmin}"> <bright:cmsWrite identifier="snippet-cannot-after-leaving-page" filter="false"/></c:if></p> 
			</c:when>
			<c:otherwise>
				<div class="warning">
					You may delete this version by clicking on the button below, but be aware that this file may be being used by external websites via http - if so, deleting this version would result in a http 404 error being returned to those websites.
				</div>
			</c:otherwise>
		</c:choose>
		<c:if test="${isNew}">
			<form action="removeNewRepurposedVersion">
		</c:if>
		<c:if test="${!isNew}">
			<form action="removeRepurposedVideo">
		</c:if>
			<input type="hidden" name="id" value="<bean:write name="id"/>"/>
			<input type="hidden" name="assetId" value="<bean:write name="backToAssetId"/>"/>
			<input class="button flush" type="submit" value="<bright:cmsWrite identifier="button-delete"/>" onclick="return confirm('<bright:cmsWrite identifier="js-confirm-delete-image-version"/>');"/>
		</form>
		<br/>
	</c:if>
	
	<logic:present parameter="new">
		<a href="viewAsset?id=<bean:write name="backToAssetId"/>"><bright:cmsWrite identifier="link-back-item" filter="false"/></a>
	</logic:present>
	<logic:notPresent parameter="new">
		<a href="viewRepurposedVideos?id=<bean:write name="repurposingForm" property="assetId"/>">&laquo; <bright:cmsWrite identifier="link-back-to-repurposed-videos" filter="false"/></a>
	</logic:notPresent>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>