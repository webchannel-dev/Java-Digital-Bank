<%@include file="../inc/doctype_html.jsp" %>
 
<!-- Developed by bright interactive www.bright-interactive.com -->
<%-- History:
	 d1		Jon Harvey		29-Jun-2011		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="assetAdminJavascriptFile" settingName="asset-admin-javascript-file"/>
<bright:applicationSetting id="canCreateAssetVersions" settingName="can-create-asset-versions"/>
<bright:applicationSetting id="showFlashVideoOnViewDetails" settingName="show-flash-video-on-view-details"/>

<c:set var="pagetitle"><bright:cmsWrite identifier="title-update" filter="false" /> <bright:cmsWrite identifier="video" filter="false" /> <bright:cmsWrite identifier="heading-keywords" filter="false" /></c:set>
<c:set var="pageheading"><bright:cmsWrite identifier="heading-update" filter="false" /> <bright:cmsWrite identifier="video" filter="false" /> <bright:cmsWrite identifier="keyword-nodes" filter="false" /></c:set>
	
<head>	
	<title><bean:write name="pagetitle" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<script src="../js/asset-upload.js" type="text/javascript"></script>

	<c:if test="${not empty assetAdminJavascriptFile}">
		<script src="<bean:write name="assetAdminJavascriptFile" filter="false"/>" type="text/javascript"></script>
	</c:if>
	
	<bean:define id="section" value=""/>
	<bean:define id="helpsection" value="video-keywords"/>
</head>

<body>
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pageheading" filter="false"/></h1>
 
	<p><a href="<c:out value='${param.returnUrl}'></c:out>"><bright:cmsWrite identifier="link-back" filter="false" /></a></p>      
 
 	<h2><bean:write name="assetForm" property="asset.searchName"/></h2>
 		
	<div id="dataLookupCodex"></div>
		
	<bright:refDataList componentName="VideoPreviewManager" methodName="isPreviewBeingGenerated" argumentValue="${assetForm.asset.id}" id="beingGenerated"/>
	<c:if test="${beingGenerated}">
		<div class="info"><bright:cmsWrite identifier="snippet-preview-being-generated"></bright:cmsWrite></div>
	</c:if>

	<bean:define id="resultImgClass" value="image"/>
	<bean:define id="asset" name="assetForm" property="asset"/>
	<bean:define id="ignoreCheckRestrict" value="yes"/>
	<bean:define id="floatFlashPlayerLeft" value="false"/>
	<bean:define id="overideLargeImageView" value="true"/>
	
	<%@include file="../inc/view_preview.jsp"%>
							
	<c:set var="isEdit" value="true" scope="request"/>
	<%@include file="../public/inc_video_keywords_panel.jsp"%>
	
	<p><a href="<c:out value='${param.returnUrl}'></c:out>"><bright:cmsWrite identifier="link-back" filter="false" /></a></p>
	
	<%@include file="../inc/body_end.jsp"%>
	<script type="text/javascript">
		<!--
		prepForm();
		-->
	</script> 
</body>

</html>
