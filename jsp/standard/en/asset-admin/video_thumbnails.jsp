<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	01-Mar-2007		Created
	 d2		Matt Stevenson	02-Mar-2007		Moved and continued implementation work
	 d3		Matt Stevenson	05-Mar-2007		Modified update links
	 d4		Matt Stevenson	08-Mar-2007		Changed to use parameters for directory, adding load indicator
	 d5		Matt Stevenson	30-Mar-2007		Changed to use flash indicator
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bean:parameter id="id" name="id" value="0"/>
		


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Video Thumbnail Selection</title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<c:choose>
		<c:when test="${id > 0}">
			<bean:define id="section" value=""/>
		</c:when>
		<c:otherwise>
			<bean:define id="section" value="upload"/>
		</c:otherwise>
	</c:choose>
	<bean:define id="helpsection" value="asset_metadata"/>		
	
	<script src="../js/category.js" type="text/javascript"></script>
	<script src="../js/calendar.js" type="text/javascript"></script>
	<script src="../js/keywordChooser.js" type="text/javascript"></script>

	<script type="text/javascript">
		<!--

			function move (sLink)
			{
				var sUrl = sLink + "&changePreviewStart=" + document.getElementById('changePreviewStart').value;
				location = sUrl;
				return (false);
			}
			
			function loading ()
			{
				document.getElementById('thumbnails').style.display = 'none'; 
				//document.getElementById('loading').style.display = 'block';
				document.getElementById('loading').style.left = "0px";
				document.getElementById('loading').style.top = "0px";
				
			}
		-->
	</script>

</head>

<body id="importPage">

		<%@include file="../inc/body_start.jsp"%>
	
		<h1 class="underline"><bright:cmsWrite identifier="heading-select-video-thumbnail" filter="false" /></h1> 

		<bright:cmsWrite identifier="intro-select-vid-thumb" filter="false"/>

		<bean:parameter id="pageNo" name="pageNo" value="0"/>
		<bean:parameter id="file" name="file" value="0"/>
		<bean:parameter id="originalFilename" name="originalFilename" value="0"/>
		<bean:parameter id="dirName" name="dirName" value="0"/>
		<bean:parameter id="fileIndex" name="index" value="0"/>
		<bean:parameter id="selectedAssetEntityId" name="selectedAssetEntityId" value="-1"/>
		<bright:applicationSetting id="dir" settingName="video-frames-temp-dir"/>
		<bright:applicationSetting id="pageSize" settingName="video-frame-selection-page-size"/>

		<c:set var="start" value="${pageNo * pageSize}"/>
		<c:set var="end" value="${start + (pageSize-1)}"/>

		<bean:parameter name="changePreviewStart" id="changePreviewStart" value="false"/>
		<div id="previewSwitch" style="display: none;">
			<input type="checkbox" id="previewChange" name="previewChange" value="" onclick="if (this.checked) { document.getElementById('changePreviewStart').value = true; } else { document.getElementById('changePreviewStart').value = false; }" <c:if test="${changePreviewStart}">checked</c:if>/> <label for="previewChange"><bright:cmsWrite identifier="label-change-preview-start" filter="false" /></label><br/><br/>
		</div>
		<script type="text/javascript">
			<!--
				document.getElementById('previewSwitch').style.display = 'block';
			-->
		</script>

		<div id="thumbnails">	

			<input type="hidden" name="changePreviewStart" id="changePreviewStart" value="<bean:write name='changePreviewStart'/>"/>

			<logic:iterate name="videoFrameForm" property="frames" id="frame" indexId="index">
				
				<c:set var="frameNo" value="${start + index}"/>
				<a href="updateThumbnailLocation?frame=<bean:write name='frameNo'/>&fileName=<bean:write name='file'/>&id=<bean:write name='id'/>&dirName=<bean:write name='dirName'/>&index=<bean:write name='fileIndex'/>&selectedAssetEntityId=<bean:write name='selectedAssetEntityId'/>&originalFilename=<bean:write name='originalFilename'/>" class="videoFrameLink" onclick="return move(this.href);"><img src="../servlet/display?file=<bean:write name='frame'/>" /></a>
	
			</logic:iterate>

		</div>
		

			<div id="loading" style="position: relative; top: -2000px;">
				<div id="flashcontent" style="margin-bottom: 10px;"></div>
				<script type="text/javascript">
					<!-- 
						var flashvars = {};
						var params = {};
						var attributes = {};
						swfobject.embedSWF("../images/standard/misc/loading.swf", "flashcontent", "32", "32", "9.0.0", false, flashvars, params, attributes);
					-->
				</script>
				<p>Generating thumbnails. This may take a few moments...</p>
			</div>

		
		<div class="clearing"></div>
		<div class="hr"></div>		

		<c:set var="nextPage" value="${pageNo+1}"/>
		<c:set var="prevPage" value="${pageNo-1}"/>

		<c:choose>
			<c:when test="${id > 0}">
				<form name="cancelForm" action="viewUpdateAsset" method="get">
					<input type="hidden" name="id" value="<bean:write name='id'/>"/>
			</c:when>
			<c:otherwise>
				<form name="cancelForm" action="uploadAssetFile" method="get">
					<input type="hidden" name="filePath" value="<bean:write name='file'/>"/>
					<input type="hidden" name="dirName" value="<bean:write name='dirName'/>"/>
					<input type="hidden" name="originalFilename" value="<bean:write name='originalFilename'/>"/>
					<input type="hidden" name="index" value="<bean:write name='fileIndex'/>"/>
			</c:otherwise>
		</c:choose>

			<span style="float: right;">
				<c:if test="${prevPage >= 0}"><a href="viewSelectThumbnail?file=<bean:write name='file'/>&id=<bean:write name='id'/>&pageNo=<bean:write name='prevPage'/>&dirName=<bean:write name='dirName'/>&index=<bean:write name='fileIndex'/>&selectedAssetEntityId=<bean:write name='selectedAssetEntityId'/>" onclick="loading(); return move(this.href);"></c:if><bright:cmsWrite identifier="link-prev" filter="false" /><c:if test="${prevPage >= 0}"></a></c:if>&nbsp;&nbsp;|&nbsp;&nbsp;<c:if test="${!videoFrameForm.last}"><a href="viewSelectThumbnail?file=<bean:write name='file'/>&id=<bean:write name='id'/>&pageNo=<bean:write name='nextPage'/>&dirName=<bean:write name='dirName'/>&index=<bean:write name='fileIndex'/>&selectedAssetEntityId=<bean:write name='selectedAssetEntityId'/>&originalFilename=<bean:write name='originalFilename'/>" onclick="loading(); return move(this.href);"></c:if><bright:cmsWrite identifier="link-next" filter="false" /><c:if test="${!videoFrameForm.last}"></a></c:if>
			</span>
		
			<input type="submit" name="cancel" value="<bright:cmsWrite identifier="button-cancel" filter="false" />" class="button flush" />
		</form>

		

	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>