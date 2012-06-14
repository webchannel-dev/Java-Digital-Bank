<div class="dialogPanel" id="downloadOriginal">

	<h3><bright:cmsWrite identifier="subhead-download-original" filter="false" /></h3>
	<label class="inline"><bright:cmsWrite identifier="label-file-format" filter="false"/> </label>

	<bean:write name="downloadForm" property="asset.format.name" filter="false"/> 
	
	<br />
	<label><bright:cmsWrite identifier="label-file-size" filter="false"/> </label>

	<c:set var="fileSize" value="${downloadForm.asset.fileSizeInBytes/(1024*1024)}"/>
	<logic:greaterEqual name="fileSize" value="1">
		<bean:write name="fileSize" format="0.00"/>Mb
	</logic:greaterEqual>
	<logic:lessThan name="fileSize" value="1">
		<c:set var="fileSize" value="${fileSize*1024}"/>
		<bean:write name="fileSize" format="0.00"/>Kb
	</logic:lessThan>

	<c:if test="${(asset.isVideo || asset.isAudio) && not empty asset.duration}">
		<br/>
		<label><bright:cmsWrite identifier="label-duration" filter="false"/> </label>
		<bean:define id="duration" name="asset" property="duration" type="java.lang.Long"/>
		<%@include file="../inc/write_duration.jsp" %>
	</c:if>

	<c:if test="${!asset.isAudio}">
		<br />
		<label class="inline"><bright:cmsWrite identifier="label-size" filter="false"/> </label>
	
		<bean:write name="downloadForm" property="asset.width"/> (w) x
		<bean:write name="downloadForm" property="asset.height"/> (h) <bright:cmsWrite identifier="pixels"/>
		<br />
	</c:if>

	<br />
	
	<c:if test="${downloadForm.assetHasDownloadMetadata && userprofile.isAdmin}">
		<html:checkbox name="downloadForm" property="dontEmbedMappedData" styleId="dontEmbedMappedData" styleClass="checkbox" style="margin-right:5px" /><label for="dontEmbedMappedData" class="checkLabel"><bright:cmsWrite identifier="label-do-not-embed-mapped-data" filter="false"/></label><br />
	</c:if>
	
	<html:submit styleClass="button flush" property="b_downloadOriginal" styleId="download_original_button"><bright:cmsWrite identifier="button-download-original" filter="false" /></html:submit>
	<br />

</div>