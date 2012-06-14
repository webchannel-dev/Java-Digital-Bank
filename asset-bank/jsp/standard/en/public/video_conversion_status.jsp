<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		14-Jan-2008		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="title-video-conversion-status" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="download"/>
	<bean:define id="helpsection" value="downloadVideo"/>

	<logic:equal name="downloadForm" property="conversionInProgress" value="true">
		<meta HTTP-EQUIV="refresh" CONTENT="5;URL=viewVideoConversionStatus<c:if test="${downloadForm.repurpose || downloadForm.email}">?assetId=<bean:write name="downloadForm" property="asset.id"/><c:choose><c:when test="${downloadForm.repurpose}">&repurpose=true</c:when><c:otherwise>&emailAsset=true</c:otherwise></c:choose></c:if>"></meta>
	</logic:equal>
	<logic:equal name="downloadForm" property="conversionInProgress" value="false">
		<meta HTTP-EQUIV="refresh" CONTENT="1;URL=viewVideoConversionStatus?filePath=<bean:write name='downloadForm' property='videoConversionResult.convertedFilename'/>&compressAsset=<bean:write name='downloadForm' property='videoConversionResult.compress'/><c:if test="${downloadForm.repurpose || downloadForm.email}">&assetId=<bean:write name="downloadForm" property="asset.id"/><c:choose><c:when test="${downloadForm.repurpose}">&repurpose=true</c:when><c:otherwise>&emailAsset=true</c:otherwise></c:choose></c:if>">
	</logic:equal>
	
</head>

<body>
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-video-conversion-status" filter="false" /></h1> 

	<logic:equal name="downloadForm" property="conversionInProgress" value="true">
		<div id="loading">
			<div id="flashcontent" style="margin-bottom: 10px;"></div>
			<script type="text/javascript">
				<!-- 
					var flashvars = {};
					var params = {};
					var attributes = {};
					swfobject.embedSWF("../images/standard/misc/loading.swf", "flashcontent", "32", "32", "9.0.0", false, flashvars, params, attributes);
				-->
			</script>
			<bean:parameter id="size" name="size" value="0"/>
			<bright:cmsWrite identifier="snippet-converting-video" filter="false" /><br/><br/>
			<p><strong><bright:cmsWrite identifier="snippet-converted-file-size" filter="false" /> <c:out value="${downloadForm.filesizeInMegabytes}"/>Mb</strong></p>
		</div>
	</logic:equal>
	<logic:equal name="downloadForm" property="conversionInProgress" value="false">
		<c:choose>
			<c:when test="${!downloadForm.email}">
				<c:set var="downloadUrl" value="downloadDeferred?downloadFile=${downloadForm.videoConversionResult.convertedFilename}"/>
				<c:set var="viewAssetUrl" value="viewAsset?id=${downloadForm.videoConversionResult.convertedAsset.id}"/>
				<p><bright:cmsWrite identifier="snippet-media-download-links" filter="false" replaceVariables="true"/></p>
			</c:when>
			<c:otherwise>
				<c:set var="emailUrl" value="viewVideoConversionStatus?filePath=${downloadForm.videoConversionResult.convertedFilename}&compressAsset=${downloadForm.videoConversionResult.compress}&assetId=${downloadForm.asset.id}&emailAsset=true" />
				<p><bright:cmsWrite identifier="snippet-media-email-conversion" filter="false" replaceVariables="true"/></p>
			</c:otherwise>
		</c:choose>
	</logic:equal>
	 
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>