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
		
<bean:define id="image" name="repurposingForm" property="repurposedVersion"/>

<head>
	
	<title><bright:cmsWrite identifier="title-repurposed-image" filter="false" /></title> 
	<bean:define id="section" value=""/>
	<%@include file="../inc/head-elements.jsp"%>
	
	<script type="text/javascript">
	
function toggleImageSize()
{
	var imgTag = document.getElementById('image');
	var imgDiv = document.getElementById('imgDiv');
	var link = document.getElementById('toggleLink');
	
	if(imgTag.style.width=='auto')
	{
		imgTag.style.width='99%';
		imgTag.style.border='1px #aaa solid';
		imgDiv.style.height='auto';
		imgDiv.style.border='0px #fff solid';
		imgDiv.style.width='100%';
		link.innerHTML='<bright:cmsWrite identifier="link-view-full-size" filter="false"/>';
	}
	else
	{
		imgDiv.style.width='99%';
		<c:if test="${image.height>600}">
			imgDiv.style.height='600px';
		</c:if>
		<c:if test="${image.height<=600}">
			imgDiv.style.height='<bean:write name="image" property="height"/>px';
		</c:if>
		imgDiv.style.border='1px #aaa solid';
		imgTag.style.width='auto';
		imgTag.style.border='0px #fff solid';
		link.innerHTML='<bright:cmsWrite identifier="link-fit-to-page" filter="false"/>';
	}
}	
	
	</script>
</head>

<body id="detailsPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-repurposed-image" filter="false" /></h1>
	
	<logic:present parameter="new">
		<p><bright:cmsWrite identifier="snippet-repurposed-image-intro" filter="false"/></p>
	</logic:present>
	
	<div id="imgDiv" style="<c:if test="${image.width>782}">width:782px; overflow: auto; </c:if> height:auto; overflow: auto;">
		<img id="image" style="border: 1px #aaa solid; <c:if test="${image.width>782}">width: 99%;</c:if> height: auto;" src="<bean:write name="repurposingForm" property="baseUrl"/>/<bean:write name="image" property="url"/>" height="<bean:write name="image" property="height"/>" width="<bean:write name="image" property="width"/>">
	</div>
	
	<c:if test="${image.width>782}">
		<script type="text/javascript">
			document.write('<p>[<a href="javaScript:toggleImageSize();" id="toggleLink"><bright:cmsWrite identifier="link-view-full-size" filter="false"/></a>]</p>');
		</script>
	</c:if>
	<c:if test="${image.width<=782}">
		<p><span class="disabled">[<bright:cmsWrite identifier="snippet-image-shown-actual-size" filter="false"/>]</span></p>
	</c:if>
	
	<table cellpadding="0" cellspacing="0" border="0" class="admin">
		<c:if test="${not empty image.createdByUser}">
			<tr>
				<th><bright:cmsWrite identifier="label-created-by" filter="false"/></th>
				<td><bean:write name="image" property="createdByUser.fullName" /> <c:if test="${not empty image.createdByUser.emailAddress}"><bean:write name="image" property="createdByUser.emailAddress" /></c:if>
				</td>
			</tr>
		</c:if>
		<tr>
			<th nowrap="nowrap"><bright:cmsWrite identifier="label-created-date" filter="false"/></th>
			<td><bean:write name="image" property="createdDate" format="dd/MM/yyyy hh:mm a"/></td>
		</tr>
		<tr>
			<th><bright:cmsWrite identifier="label-width" filter="false"/></th>
			<td><bean:write name="image" property="width"/> <bright:cmsWrite identifier="pixels" filter="false"/></td>
		</tr>
		<tr>
			<th><bright:cmsWrite identifier="label-height" filter="false"/></th>
			<td><bean:write name="image" property="height"/> <bright:cmsWrite identifier="pixels" filter="false"/></td>
		</tr>
		<tr>
			<th><bright:cmsWrite identifier="label-file-type" filter="false"/></th>
			<td><bean:write name="image" property="suffix"/></td>
		</tr>
		<tr>
			<th><bright:cmsWrite identifier="label-image-url" filter="false"/></th>
			<td>
				<span><bean:write name="repurposingForm" property="baseUrl"/>/<bean:write name="image" property="url"/></span>
			</td>
		</tr>
		<tr>
			<th style="vertical-align: top;  width:auto;"><bright:cmsWrite identifier="label-html-code" filter="false"/></th>
			<td style="padding-right:0px;">
				<div style="width:690px; overflow: auto;">
					<form action=""> 
					 	<input value="<bean:write name="image" property="embeddableHtml" />" style="width: auto;" size="130" onclick="javascript:this.focus();this.select();" readonly/>
					</form>
				</div>
			</td>
		</tr>
	</table>
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
			<form action="removeRepurposedImage">
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
		<a href="viewRepurposedImages?id=<bean:write name="repurposingForm" property="assetId"/>">&laquo; <bright:cmsWrite identifier="link-back-to-repurposed-images" filter="false"/></a>
	</logic:notPresent>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>