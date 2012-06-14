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

<bright:applicationSetting id="appUrl" settingName="application-url"/>
<c:if test="${empty appUrl}">
	<c:set var="appUrl"><%= request.getRequestURL().toString().substring(0, request.getRequestURL().toString().indexOf(request.getContextPath())) + request.getContextPath() %></c:set>
</c:if>

<head>
	<title><bright:cmsWrite identifier="title-embed-file" filter="false" /></title> 
	<bean:define id="section" value=""/>
	<%@include file="../inc/head-elements.jsp"%>
	<script src="../js/download-form.js" type="text/javascript"></script>
</head>

<body id="detailsPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-embed-file" filter="false" /></h1>
	
	<logic:present parameter="new">
		<p><bright:cmsWrite identifier="snippet-embed-file-intro" filter="false"/></p>
	</logic:present>
		
	<br/>

	<table cellpadding="0" cellspacing="0" border="0" class="admin">
		<tr>
			<th><bright:cmsWrite identifier="label-file-type" filter="false"/></th>
			<td><bean:write name="assetForm" property="asset.format.description"/> (.<bean:write name="assetForm" property="asset.format.fileExtension"/>)</td>
		</tr>
		<tr>
			<th><bright:cmsWrite identifier="label-file-url" filter="false"/></th>
			<td>
				<span><bean:write name="appUrl"/>/assetfile/<bean:write name="assetForm" property="asset.id"/>.<bean:write name="assetForm" property="asset.format.fileExtension"/></span>
			</td>
		</tr>
		<tr>
			<th style="vertical-align: top;  width:auto;"><bright:cmsWrite identifier="label-html-code" filter="false"/></th>
			<td style="padding-right:0px;">
				<div style="width:690px; overflow: auto;">
					<form action=""> 
					 	<input type="text" size="90" onclick="javascript:this.focus();this.select();" readonly value="<c:out value="<a href='${appUrl}/assetfile/${assetForm.asset.id}.${assetForm.asset.format.fileExtension}'>${assetForm.asset.name}</a>"/>">
					</form>
				</div>
			</td>
		</tr>
	</table>

	<br/>
	<c:if test="${userprofile.isAdmin}">
		<div class="error">
			You may prevent this file from being embedded by clicking on the Reset button below, but be aware that the file may be being used by external websites via http - 
			if so, deleting this version would result in a http 404 error being returned to those websites.
		</div>
		<form action="updateAssetEmbedStatus">
			<input type="hidden" name="id" value="<bean:write name="assetForm" property="asset.id"/>"/>
			<input type="hidden" name="embed" value="false"/>
			<input class="button flush" type="submit" value="<bright:cmsWrite identifier="button-reset"/>" onclick="return confirm('<bright:cmsWrite identifier="js-confirm-reset-embed-status"/>');"/>
		</form>
		<br/>
	</c:if>
	
	<a href="viewAsset?id=<bean:write name="assetForm" property="asset.id"/>"><bright:cmsWrite identifier="link-back-item" filter="false"/></a>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>