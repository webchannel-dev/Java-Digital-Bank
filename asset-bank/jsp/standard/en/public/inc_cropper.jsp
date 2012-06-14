<%--suppress XmlUnboundNsPrefix --%>
<%-- If crop to fit is on, then use a fixed aspect ratio in the cropper popup --%>
<c:set var="cropToFit" value="${downloadForm.selectedUsageTypeFormat != null && downloadForm.selectedUsageTypeFormat.cropToFit}"/>
<c:set var="allowCropping" value="${downloadForm.selectedUsageTypeFormat == null || cropToFit}"/>
<c:choose>
	<c:when test="${cropToFit}">
		<input type="hidden" name="fixedRatioX" id="fixedRatioX" value="<c:out value="${downloadForm.selectedUsageTypeFormat.width}"/>"/>
		<input type="hidden" name="fixedRatioY" id="fixedRatioY" value="<c:out value="${downloadForm.selectedUsageTypeFormat.height}"/>"/>
	</c:when>
	<c:otherwise>
		<input type="hidden" name="fixedRatioX" id="fixedRatioX" value="-1"/>
		<input type="hidden" name="fixedRatioY" id="fixedRatioY" value="-1"/>
	</c:otherwise>
</c:choose>
<input type="hidden" name="cropDefinedPrefix" id="cropDefinedPrefix" value="<bright:cmsWrite identifier='snippet-crop-defined-prefix' filter='false' />"/>
<input type="hidden" name="maskDefinedPrefix" id="maskDefinedPrefix" value="Mask position"/>

<logic:notPresent name="showCancelLink">
	<bean:define id="showCancelLink" value="false"/>
</logic:notPresent>


	<%-- parameters used to reload crop values after validation failure or preview --%>
	<%-- if you add more you will also need to add hidden fields to
	     inc_preview_buttons.jsp so they do not get lost after preview --%> 
	<bean:parameter id="valCropX" name="cropX" value=""/>
	<bean:parameter id="valCropY" name="cropY" value=""/>
	<bean:parameter id="valCropWidth" name="cropWidth" value=""/>
	<bean:parameter id="valCropHeight" name="cropHeight" value=""/>
	<bean:parameter id="valCropMask" name="cropMask" value=""/>
	<bean:parameter id="valCropMaskId" name="cropMaskId" value=""/>
	<bean:parameter id="valCropMaskColour" name="cropMaskColour" value=""/>

	<bean:parameter id="valReloadCropX" name="reloadCropX" value=""/>
	<bean:parameter id="valReloadCropY" name="reloadCropY" value=""/>
	<bean:parameter id="valReloadCropWidth" name="reloadCropWidth" value=""/>
	<bean:parameter id="valReloadCropHeight" name="reloadCropHeight" value=""/>

	<bean:parameter id="valReloadMaskX" name="reloadMaskX" value=""/>
	<bean:parameter id="valReloadMaskY" name="reloadMaskY" value=""/>
	<bean:parameter id="valReloadMaskWidth" name="reloadMaskWidth" value=""/>
	<bean:parameter id="valReloadMaskHeight" name="reloadMaskHeight" value=""/>	
	
	<%-- cropping parameters that will contain values to be used by Asset Bank --%>
	<input type="hidden" name="cropX" id="cropX" value="<bean:write name='valCropX'/>"/>
	<input type="hidden" name="cropY" id="cropY" value="<bean:write name='valCropY'/>"/>
	<input type="hidden" name="cropWidth" id="cropWidth" value="<bean:write name='valCropWidth'/>"/>
	<input type="hidden" name="cropHeight" id="cropHeight" value="<bean:write name='valCropHeight'/>"/>


	<%-- parameters for reloading the crop settings (values are different from the heights and widths used by Asset Bank as they are scaled for the popup window) --%>
	<input type="hidden" name="reloadCropX" id="reloadCropX" value="<bean:write name='valReloadCropX'/>"/>
	<input type="hidden" name="reloadCropY" id="reloadCropY" value="<bean:write name='valReloadCropY'/>"/>
	<input type="hidden" name="reloadCropWidth" id="reloadCropWidth" value="<bean:write name='valReloadCropWidth'/>"/>
	<input type="hidden" name="reloadCropHeight" id="reloadCropHeight" value="<bean:write name='valReloadCropHeight'/>"/>

	<%-- parameters for reloading the masks settings (values are different from the heights and widths used by Asset Bank as they are scaled for the popup window) --%>
	<input type="hidden" name="reloadMaskX" id="reloadMaskX" value="<bean:write name='valReloadMaskX'/>"/>
	<input type="hidden" name="reloadMaskY" id="reloadMaskY" value="<bean:write name='valReloadMaskY'/>"/>
	<input type="hidden" name="reloadMaskWidth" id="reloadMaskWidth" value="<bean:write name='valReloadMaskWidth'/>"/>
	<input type="hidden" name="reloadMaskHeight" id="reloadMaskHeight" value="<bean:write name='valReloadMaskHeight'/>"/>

	<%-- parameters that will be copied to hidden fields that let the popup know whether masks and mask colours can be changed --%>
	<c:set var="valCanChooseMask" value="true"/>
	<c:set var="valCanChooseMaskColour" value="true"/>

	<%-- Default to masking (not cropping) on quick download if have masks and allowMasking is true for the usage type --%>
	<c:if test="${allowMasking && downloadForm.selectedUsageTypeFormat != null && downloadForm.selectedUsageTypeFormat.allowMasking}">
		<%-- default to masking (not cropping) - allowMasking = true implies
			 cropToFit = false (this is enforced by the edit usage type
			 page/action) --%>
		<c:set var="valCropMask" value="true"/>

		<c:if test="${downloadForm.selectedUsageTypeFormat.presetMaskId > 0}">
			<%-- a preset mask has been chosen - do not allow the mask to be chosen in the popup --%>
			<c:set var="valCanChooseMask" value="false"/>

			<%-- Set the mask that the popup should use --%>
			<c:set var="valCropMaskId" value="${downloadForm.selectedUsageTypeFormat.presetMaskId}"/>

			<c:if test="${presetMaskColour != null}">
				<%-- a preset colour has been chosen - do not allow the colour to be chosen in the popup --%>
				<c:set var="valCanChooseMaskColour" value="false"/>
				<c:set var="valCropMaskColour" value="${presetMaskColour.hexString}"/>
			</c:if>
		</c:if>
	</c:if>

	<%-- Default to cropping if masking is not allowed. valCropMask will be
		 false already if this is the first time the page has loaded, but it
		 may be true if the page is being redisplayed after the user has chosen
		 a new UsageTypeFormat --%>
	<c:if test="${allowCropping && !allowMasking}">
		<c:set var="valCropMask" value="false"/>
	</c:if>
	
	<c:if test="${allowCropping || allowMasking}">
	<div class="hr"></div>
	<p class="tabHolder clearfix">
		<c:if test="${allowCropping}">
			<a href="#" onclick="switchOnCropper(this); return false;" id="cropTab" <c:if test="${valCropMask != true}">class="active"</c:if>><bright:cmsWrite identifier="subhead-crop"/> </a>
		</c:if>
		<c:if test="${allowMasking}">
			<a href="#" onclick="switchOnMask(this); return false;" id="maskTab" <c:if test="${valCropMask == true}">class="active"</c:if>><bright:cmsWrite identifier="subhead-mask"/></a></strong>
		</c:if>
	</p>
	</c:if>

	<c:if test="${allowCropping || allowMasking}">
	<div id="tabContent">
		
		<%-- Only include this div if masks are allowed for the selected usage
			 type format (or if there is no usage type format because we are
			 doing an advanced download) and there are some masks --%>
		<c:if test="${allowMasking}">
			<div id="maskContent" <c:if test="${valCropMask != true}">style="display:none"</c:if>>

				<input type="hidden" name="cropMask" id="cropMask" value="<bean:write name='valCropMask'/>" />
				<input type="hidden" name="cropMaskId" id="cropMaskId" value="<bean:write name='valCropMaskId'/>"/>
				<input type="hidden" name="cropMaskColour" id="cropMaskColour" value="<bean:write name='valCropMaskColour'/>" />
				<input type="hidden" name="canChooseMask" id="canChooseMask" value="<bean:write name='valCanChooseMask'/>" />
				<input type="hidden" name="canChooseMaskColour" id="canChooseMaskColour" value="<bean:write name='valCanChooseMaskColour'/>" />

			
				<p id="maskMessage" class="normal" style="height: 1%;"><c:out value='${maskUndefined}'/></p>
				<p>
					<a href="#" id="maskLauncher"><bright:cmsWrite identifier="link-configure-mask"/></a>
					<c:if test="${valCanChooseMask}"><span class="normal" id="clearMask" style="display: none;">&nbsp;&nbsp;|&nbsp;&nbsp;<a href="#" onclick="clearMask(true); return false;"><bright:cmsWrite identifier="link-clear-mask"/></a></span></c:if>
				</p>
			</div>

			<script type="text/javascript">
				<!--
					initialiseMasks();
				-->
			</script>
		

		</c:if>

		<c:if test="${allowCropping}">
			<div id="cropperContent" <c:if test="${valCropMask == true}">style="display:none"</c:if>>
				<p id="cropMessage" class="normal" style="height: 1%;"><c:out value='${cropUndefined}'/></p>


				<noscript>
					<label for="cropX" class="narrow"><bright:cmsWrite identifier="label-position" filter="false"/> </label>
					<span><bright:cmsWrite identifier="label-x" filter="false"/></span><input type="text" name="cropX" class="small text" id="cropX"/>
					<span><bright:cmsWrite identifier="label-y" filter="false"/></span><input type="text" name="cropY" class="small text" id="cropY"/> <br />
	
					<label for="cropWidth" class="narrow"><bright:cmsWrite identifier="label-size" filter="false"/> </label>

					<span><bright:cmsWrite identifier="label-w" filter="false"/></span><input type="text" name="cropWidth" class="small text" id="cropWidth"/>
					<span><bright:cmsWrite identifier="label-h" filter="false"/></span><input type="text" name="cropHeight" class="small text" id="cropHeight"/>
				</noscript>



				<p id="cropLauncherDiv" class="js-enabled-show">
					<a id="cropLauncher" href="javascript:;"><span class="normal" id="selectCrop"><bright:cmsWrite identifier="link-select-crop" filter="false" /></span><span class="normal" id="changeCrop" style="display: none;"><bright:cmsWrite identifier="link-change-crop" filter="false" /></span></a><c:if test="${showCancelLink}"><span class="normal" id="clearCrop" style="display: none;">&nbsp;&nbsp;|&nbsp;&nbsp;<a href="#" onclick="clearCrop(); return false;"><bright:cmsWrite identifier="link-clear-crop" filter="false" /></a></span></c:if>
				</p>


				<script type="text/javascript">
					<!-- 
						//otherwise do a normal initialise
						initialiseCropper();
					-->
				</script>
			</div><!-- end of #cropperContent -->
		</c:if>

	</div><!-- end of tabContent -->
	</c:if>

	<bean:parameter name="forceClearCropMask" id="shouldForceCropMaskClear" value="0"/>
	<c:if test="${shouldForceCropMaskClear > 0}">
		<script type="text/javascript">
			<!-- 
				//make sure the crop and mask are cleared...
				clearCrop();
			<c:if test="${allowMasking}">
				clearMask();
			</c:if>
			-->
		</script>
	</c:if>
