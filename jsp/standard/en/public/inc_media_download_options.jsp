<bright:applicationSetting id="showLockAspectRatio" settingName="show-lock-aspect-ratio"/>
	
<logic:notPresent name="isVideo">
	<bean:define id="isVideo" value="false"/>
</logic:notPresent>
<logic:notPresent name="isAudio">
	<bean:define id="isAudio" value="false"/>
</logic:notPresent>

<%-- The user can specify format/height/width etc --%>
<html:hidden name="downloadForm" property="alreadyLoggedUse"/>

<div id="convertPanel">
	<h3><bright:cmsWrite identifier="subhead-convert" filter="false" /></h3>	
	
	<c:choose>
		<c:when test='${isVideo}'>
			<div id="convertBox">
			<label for="fileFormat"><bright:cmsWrite identifier="label-file-format" filter="false"/> </label>
			<html:select name="downloadForm" property="imageFormat" size="1" styleId="fileFormat">
				<html:options collection="downloadFormats" property="fileExtension" labelProperty="description" filter="false"/>
			</html:select>
			<br/>
			</div>
			<label for="bitrate"><bright:cmsWrite identifier="label-video-quality" filter="false"/> </label>
			
			<div id="bitrateDiv">
				<select name="bitrate" id="bitrate" size="1">
					<option value="150k" <c:if test="${downloadForm.videoBitrate == '150k'}">selected</c:if>><bright:cmsWrite identifier="snippet-low" filter="false"/></option>
					<option value="400k" <c:if test="${downloadForm.videoBitrate == '400k'}">selected</c:if>><bright:cmsWrite identifier="snippet-standard" filter="false"/></option>
					<option value="800k" <c:if test="${downloadForm.videoBitrate == '800k'}">selected</c:if>><bright:cmsWrite identifier="snippet-high" filter="false"/></option>
					<option value="1500k" <c:if test="${downloadForm.videoBitrate == '1500k'}">selected</c:if>><bright:cmsWrite identifier="snippet-best" filter="false"/></option>
				</select>
			</div>
			<br />
		</c:when>
		<c:when test='${isAudio}'>
			<input type="hidden" name="imageFormat" value="mp3"/>
		</c:when>
		<c:otherwise>
			<label for="fileFormat"><bright:cmsWrite identifier="label-file-format" filter="false"/> </label>
			<html:select name="downloadForm" property="imageFormat" size="1" styleId="fileFormat">
				<html:options collection="downloadFormats" property="fileExtension" labelProperty="description" filter="false"/>
			</html:select>
			<br />
		</c:otherwise>
	</c:choose>
	
	<c:if test="${downloadForm.asset.format.canConvertIndividualLayers && downloadForm.asset.numPages > 1}">
		<label for="layer"><bright:cmsWrite identifier="label-layer" filter="false"/> </label>
		<html:select name="downloadForm" property="layerToDownload" styleId="layerToConvert">
			<c:forEach begin="1" end="${downloadForm.asset.numPages}" var="layer">
				<option value="<c:out value="${layer}"/>" <c:if test="${layer==downloadForm.layerToDownload}">selected="selected"</c:if>><c:out value="${layer}"/></option>
			</c:forEach>
			<html:option value="0"><bright:cmsWrite identifier="snippet-all" filter="false" /></html:option>
		</html:select>
	</c:if>
</div>
<div class="hr"></div>
<c:choose>
<c:when test="${imagesAreCroppable}">

	<bean:define id="showCancelLink" value="true"/>
	<%@include file="inc_cropper.jsp"%>												
	<div class="hr"></div>

</c:when>
</c:choose>
<c:if test="${!isAudio}">
	<h3><bright:cmsWrite identifier="subhead-resize" filter="false" /></h3>
	<div id="aspectHolder" style="display:none;">
		
		<c:choose>
			<c:when test="${showLockAspectRatio}">
				<label for="lockAspectRatio"><bright:cmsWrite identifier="label-lock-aspect" filter="false"/></label>
				<input type="checkbox" name="lockAspectRatio" checked="checked" class="checkbox" id="lockAspectRatio"/><br />
			</c:when>
			<c:otherwise>
				<input type="hidden" name="lockAspectRatio" value="checked" class="checkbox" id="lockAspectRatio"/>
			</c:otherwise>
		</c:choose>
	
	</div>
	<script type="text/javascript">
		<!--
		//only show lock aspect ratio control if user has javascript enabled
		document.getElementById("aspectHolder").style.display = 'block';
		-->
	</script>

	<label for="width"><bright:cmsWrite identifier="label-pixel-width" filter="false"/> </label>
	<html:text name="downloadForm" property="width" size="8" styleClass="small text"  styleId="width"/>
	<br />
	<label for="height"><bright:cmsWrite identifier="label-pixel-height" filter="false"/></label> 
	<html:text name="downloadForm" property="height" size="8" styleClass="small text" styleId="height"/>
	<br />

	<c:if test="${numTints>0}">
		<div class="hr"></div>
		<h3><bright:cmsWrite identifier="subhead-tint" filter="false" /></h3>
		<%@include file="inc_download_tints.jsp"%>	
	</c:if>

	<div class="hr"></div>
</c:if>

