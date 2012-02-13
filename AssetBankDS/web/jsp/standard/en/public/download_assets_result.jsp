<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Chris Preager		24-May-2005		Imported from Image Manager
	 d2 		Ben Browning		17-Feb-2006		HTML/CSS tidy up
	 d3     Matt Woollard       01-Apr-2009     Changed to support downloading of child assets
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<bright:applicationSetting id="downloadRefreshPeriod" settingName="download-page-refresh-period"/>
<bright:applicationSetting id="showRequestOnCd" settingName="show-request-on-cd"/>


<c:choose>
	<c:when test="${downloadingAssetBox}">
		<c:set var="title"><bright:cmsWrite identifier="title-download-lightbox-result" filter="false"/></c:set>
		<c:set var="heading"><bright:cmsWrite identifier="heading-preparing" filter="false" /> <bright:cmsWrite identifier="a-lightbox" filter="false" case="mixed" /></c:set>
		<c:set var="subhead"><bright:cmsWrite identifier="snippet-lightbox-ready" filter="false"/></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="title"><bright:cmsWrite identifier="title-download-child-assets" filter="false"/></c:set>
		<c:set var="heading"><bright:cmsWrite identifier="heading-download-child-assets" filter="false"/></c:set>
	</c:otherwise>
</c:choose>	

<head>
	
	<title><c:out value='${title}'/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<logic:empty name="assetboxDownloadFiles">
		<logic:notPresent name="downloadAttempts">
			<bean:define id="downloadAttempts" value="1"/>
		</logic:notPresent>
		<meta HTTP-EQUIV="refresh" CONTENT="<bean:write name='downloadRefreshPeriod'/>;URL=viewDownloadAssetsProgress?downloadAttempts=<bean:write name='downloadAttempts'/>"></meta>
	</logic:empty>
	<bean:define id="section" value="download"/>
	<bean:define id="helpsection" value="download_lightbox"/>

	<script type="text/javascript">
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
	
	<h1 class="underline"><c:out value='${heading}'/></h1> 
	
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
		
		<c:choose>
			<c:when test="${!fileTooLarge}">
				<h4><c:out value='${subhead}'/></h4>
				<ul>
					<logic:iterate name="assetboxDownloadFiles" id="file">
						<li><a href="downloadAssetBoxFile?file=<bean:write name='file' property='filePath'/>&filename=<bean:write name='file' property='fileName'/>"><bean:write name="file" property='fileName'/></a></li>
					</logic:iterate>
				</ul>
				<br/>
				<span id="autoDownloadInfo" style="display: none;"><bright:cmsWrite identifier="snippet-file-downloading" filter="false"/></span>
				<div class="hr"></div>
			</c:when>
			
			<c:otherwise>
				<p><bright:cmsWrite identifier="snippet-lightbox-too-large" filter="false"/></p>
				<c:if test="${showRequestOnCd}">
					<p><bright:cmsWrite identifier="snippet-lightbox-too-large-request-cd" filter="false"/></p>
				</c:if>				
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="${downloadingAssetBox}">
				<a href="../action/viewAssetBox"><bright:cmsWrite identifier="link-back-to" filter="false"/> <bright:cmsWrite identifier="a-lightbox" filter="false"/></a>
			</c:when>
			<c:otherwise>
				<a href="../action/viewChildAssets?id=<c:out value='${downloadingParentId}'/>"><bright:cmsWrite identifier="link-back-to-children" filter="false"/></a>
			</c:otherwise>
		</c:choose>

	</logic:notEmpty>						
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>
