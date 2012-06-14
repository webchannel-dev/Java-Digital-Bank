<bright:applicationSetting id="showLockAspectRatio" settingName="show-lock-aspect-ratio"/>

<script type="text/javascript">
<!--
$j(document).ready(function(){
$j("#optionId").change(function(){

var optFileFormat = $j("#option" + this.value).children(".optFileFormat").html();
var optWidth = $j("#option" + this.value).children(".optWidth").html();
var optHeight = $j("#option" + this.value).children(".optHeight").html();

$j("#selection").children(".imageFormat").val(optFileFormat); 
$j("#selection").children(".width").val(optWidth); 
$j("#selection").children(".height").val(optHeight); 

});

//$("#optionId").change();
});
-->
</script>
	
<%-- The user can specify format/height/width etc --%>
<html:hidden name="downloadForm" property="alreadyLoggedUse"/>

<div id="convertPanel">
	<h3><bright:cmsWrite identifier="subhead-convert" filter="false" /></h3>
		
	<div id="convertBox">
	<label for="option"><bright:cmsWrite identifier="subhead-options" filter="false"/>:</label>
	<select id="optionId" size="1" name="option">
	<c:forEach items ="${downloadForm.videoUserOptions}" var="videoUserOption">
		<option value="<c:out value='${videoUserOption.id}'/>">
			<c:out value="${videoUserOption.name}"/> 
			<c:if test="${videoUserOption.defaultFrameSize.width > 0}"> (<c:out value="${videoUserOption.defaultFrameSize.dimensionsText}"/>)</c:if>
		</option> 
	</c:forEach>
	</select>
	<br/>
	</div>
</div>
<div class="hr"></div>
	<h3><bright:cmsWrite identifier="subhead-resize" filter="false" /></h3>
	<div id="aspectHolder" style="display:none;">		
		<c:choose>
			<c:when test="${showLockAspectRatio}">
				<label for="lockAspectRatio"><bright:cmsWrite identifier="label-lock-aspect" filter="false"/></label>
				<input type="checkbox" name="lockAspectRatio" checked="checked" class="checkbox" id="lockAspectRatio"/ disabled="disabled"><br />
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
	
	<div id="selection">
		<input type="hidden" name="imageFormat" class="imageFormat" value="<c:out value='${downloadForm.videoUserOptions[0].fileFormatExtension}'/>"/>
		<label for="width"><bright:cmsWrite identifier="label-pixel-width" filter="false"/> </label>
		<input type="text" name="width" id="width" size="8" class="small text width" value="<c:out value='${downloadForm.videoUserOptions[0].newFrameWidth}'/>"/>
		<br />
		<label for="height"><bright:cmsWrite identifier="label-pixel-height" filter="false"/></label> 
		<input type="text" name="height" id="height" size="8" class="small text height" value="<c:out value='${downloadForm.videoUserOptions[0].newFrameHeight}'/>"/>
		<c:if test="${!showLockAspectRatio}" >
			<br /><bright:cmsWrite identifier="label-aspect-maintain" filter="false"/>
		</c:if>
	</div>
	
	<div id="useroptions" style="display:none;">
	<c:forEach items ="${downloadForm.videoUserOptions}" var="videoUserOption">
		<div id="option${videoUserOption.id}">
			<p class="optFileFormat">${videoUserOption.fileFormatExtension}</p>
			<c:choose>
				<c:when test="${videoUserOption.newFrameWidth > 0}"><p class="optWidth">${videoUserOption.newFrameWidth}</p></c:when>
				<c:otherwise><p class="optWidth">${downloadForm.asset.width}</p></c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${videoUserOption.newFrameHeight > 0}"><p class="optHeight">${videoUserOption.newFrameHeight}</p></c:when>
				<c:otherwise><p class="optHeight">${downloadForm.asset.displayHeight}</p></c:otherwise>
			</c:choose>
		</div>
	</c:forEach>
	</div>
	
	<br />
	<div class="hr"></div>