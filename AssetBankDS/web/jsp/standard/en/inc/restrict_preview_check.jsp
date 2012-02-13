<bright:applicationSetting id="canRestrictAssetPreview" settingName="can-restrict-assets"/>
<bright:applicationSetting id="hideRestrictedAssetImages" settingName="hide-restricted-asset-images"/>
<c:set var="restrict" value="false"/>
<logic:notPresent name="ignoreCheckRestrict">
	<c:if test="${hideRestrictedAssetImages && canRestrictAssetPreview && asset.isRestricted && !userprofile.isAdmin }">
		<c:set var="restrict" value="true"/>
		<logic:present name="assetForm">
			<c:if test="${assetForm.userCanDownloadAsset && asset.id==assetForm.asset.id}">
				<c:set var="restrict" value="false"/>
			</c:if>
		</logic:present>
	</c:if>
</logic:notPresent>

<%-- if this image is restricted - see if it is overridden by the users group permissions --%>
<c:if test="${restrict}">
	<c:if test="${assetForm.userCanViewRestrictedAsset || asset.overrideRestriction}">
		<c:set var="restrict" value="false"/>
	</c:if>
</c:if>
