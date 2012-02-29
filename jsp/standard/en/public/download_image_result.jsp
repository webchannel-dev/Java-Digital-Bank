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
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Download Image</title> 
	<%@include file="../inc/head-elements.jsp"%>

	<logic:notPresent name="assetId">
		<bean:parameter name="id" id="assetId"/>
	</logic:notPresent>
	<logic:notPresent name="filePath">
		<bean:parameter name="filePath" id="filePath"/>
	</logic:notPresent>
	<logic:notPresent name="emailAsset">
		<bean:parameter name="emailAsset" id="emailAsset"/>
	</logic:notPresent>
	<logic:notPresent name="repurposeAsset">
		<bean:parameter name="repurposeAsset" id="repurposeAsset"/>
	</logic:notPresent>
	<logic:notPresent name="downloadFilename">
		<bean:parameter name="downloadFilename" id="downloadFilename"/>
	</logic:notPresent>
	<logic:empty name="downloadFile">
		<logic:notPresent name="downloadAttempts">
			<bean:define id="downloadAttempts" value="1"/>
		</logic:notPresent>
		<logic:notPresent name="emailAsset">
			<bean:define id="emailAsset" value="false"/>
		</logic:notPresent>
		<logic:notPresent name="repurposeAsset">
			<bean:define id="repurposeAsset" value="false"/>
		</logic:notPresent>
		<meta HTTP-EQUIV="refresh" CONTENT="<bean:write name='downloadRefreshPeriod'/>;URL=viewDownloadProgress?id=<bean:write name="assetId"/>&filePath=<bean:write name="filePath"/>&downloadFilename=<bean:write name="downloadFilename"/>&downloadAttempts=<bean:write name='downloadAttempts'/>&emailAsset=<bean:write name='emailAsset'/>&repurposeAsset=<bean:write name='repurposeAsset'/>"></meta>
	</logic:empty>
	<bean:define id="section" value="download"/>
	<bean:define id="helpsection" value="download_lightbox"/>
	<script type="text/javascript">
function startDownload()
{
	<logic:notEmpty name="downloadFile">
		document.getElementById('autoDownloadInfo').style.display = 'inline';
		this.window.location.href='downloadDeferred?downloadFile=<bean:write name="downloadFile"/>&downloadFilename=<bean:write name="downloadFilename"/>';
	</logic:notEmpty>
}
	</script>
</head>

<body id="downloadLightbox" onLoad="startDownload();">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-download-image" filter="false"/></h1>

	<logic:empty name="downloadFile">
		<h2><bright:cmsWrite identifier="subhead-please-wait-image-prepared" filter="false"/></h2>
	</logic:empty>
	<logic:notEmpty name="downloadFile">
		<h2><bright:cmsWrite identifier="subhead-image-ready-download" filter="false"/></h2>
		<p>
		<a href="downloadDeferred?downloadFile=<bean:write name='downloadFile'/>&downloadFilename=<bean:write name='downloadFilename'/>"><bean:write name='downloadFilename'/></a>
		</p>
		<p>
		<span id="autoDownloadInfo" style="display: none;"><bright:cmsWrite identifier="snippet-image-should-downloading" filter="false"/></span>
		</p>
		<div class="hr"></div>
		<a href="../action/viewAsset?id=<bean:write name='assetId'/>"><bright:cmsWrite identifier="link-back-item" filter="false" /></a>
	</logic:notEmpty>	

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>