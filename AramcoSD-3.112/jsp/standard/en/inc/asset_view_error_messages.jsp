<%-- Display error message if user has attempted to download a file that they don't have permission to download --%>
<c:if test="${assetForm.redirectedFromDownloadPage}">
	<div class="warning"><bright:cmsWrite identifier="NoPermissionToDownload"/></div>
</c:if>

<%-- Display a small warning if the asset is incomplete and the user can do something about that (edit it) --%>
<bright:applicationSetting id="highlightIncompleteAssets" settingName="highlight-incomplete-assets"/>
<c:if test="${highlightIncompleteAssets and not assetForm.asset.complete and assetForm.userCanUpdateAsset}">
	<div class="warningInline floatRight" title="<bright:cmsWrite identifier="copy-incomplete-metadata-warning-tooltip"/>"><bright:cmsWrite identifier="copy-incomplete-metadata-warning"/></div>
</c:if>
