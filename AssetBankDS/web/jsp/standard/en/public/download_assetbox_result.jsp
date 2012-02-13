<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Chris Preager		24-May-2005		Imported from Image Manager
	 d2 		Ben Browning		17-Feb-2006		HTML/CSS tidy up
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<bright:applicationSetting id="downloadRefreshPeriod" settingName="download-page-refresh-period"/>


<head>
	
	<title><bright:cmsWrite identifier="title-download-lightbox-result" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<logic:empty name="assetboxDownloadFiles">
		<logic:notPresent name="downloadAttempts">
			<bean:define id="downloadAttempts" value="1"/>
		</logic:notPresent>
		<meta HTTP-EQUIV="refresh" CONTENT="<bean:write name='downloadRefreshPeriod'/>;URL=viewDownloadAssetBoxProgress?downloadAttempts=<bean:write name='downloadAttempts'/>"></meta>
	</logic:empty>
	<bean:define id="section" value="download"/>
	<bean:define id="helpsection" value="download_lightbox"/>

	<script>
function startDownload()
{
	<logic:notEmpty name="assetboxDownloadFiles">
		<bean:size name="assetboxDownloadFiles" id="numFiles"/>
		<logic:equal name="numFiles" value="1">
			<c:set var="file" value="${assetboxDownloadFiles[0]}"/>
			document.getElementById('autoDownloadInfo').style.display = 'inline';
			this.window.location.href='downloadAssetBoxFile?file=<bean:write name="file" property="filePath"/>&filename=<bean:write name="file" property="fileName"/>';
		</logic:equal>
	</logic:notEmpty>
}
	</script>
</head>

<body id="downloadLightbox" onLoad="startDownload();">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-preparing" filter="false" /> <bright:cmsWrite identifier="a-lightbox" filter="false" case="mixed" /></h1> 
	
	<logic:notEmpty name="messages">
		<h3><bright:cmsWrite identifier="snippet-progress" filter="false"/></h3>
		<ul class="normal">
		<logic:iterate name="messages" id="message">
			<li><bean:write name="message"/></li>
		</logic:iterate>
		</ul>
	</logic:notEmpty>
	
	<br/>
	
	<logic:notEmpty name="assetboxDownloadFiles">
		<h4><bright:cmsWrite identifier="snippet-lightbox-ready" filter="false"/></h4>
		<ul>
			<logic:iterate name="assetboxDownloadFiles" id="file">
				<li><a href="downloadAssetBoxFile?file=<bean:write name='file' property='filePath'/>&filename=<bean:write name='file' property='fileName'/>"><bean:write name="file" property='fileName'/></a></li>
			</logic:iterate>
		</ul>
		<br/>
		<span id="autoDownloadInfo" style="display: none;"><bright:cmsWrite identifier="snippet-file-downloading" filter="false"/></span>
		<div class="hr"></div>
		<a href="../action/viewAssetBox"><bright:cmsWrite identifier="link-back-to" filter="false"/> <bright:cmsWrite identifier="a-lightbox" filter="false"/></a>
	</logic:notEmpty>						
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>
