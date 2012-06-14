<%-- Display error message if user has attempted to download a file that they don't have permission to download --%>
<c:if test="${assetForm.redirectedFromDownloadPage}">
	<div class="warning"><bright:cmsWrite identifier="NoPermissionToDownload"/></div>
</c:if>

<%-- Display a small warning if the asset is incomplete and the user can do something about that (edit it) --%>
<bright:applicationSetting id="highlightIncompleteAssets" settingName="highlight-incomplete-assets"/>
<c:if test="${highlightIncompleteAssets and not assetForm.asset.complete and assetForm.userCanUpdateAsset}">
	<div class="warningInline floatRight" title="<bright:cmsWrite identifier="copy-incomplete-metadata-warning-tooltip"/>"><bright:cmsWrite identifier="copy-incomplete-metadata-warning"/></div>
</c:if>


<c:if test="${backgroundEditInProgress}">
	<script type="text/javascript" charset="utf-8">
		function refreshPage(){
			location.reload();
		}
		setInterval( refreshPage, 8000 ); // Refresh the page every 8 seconds
	</script>

	<div class="info">
		<img src="../images/standard/misc/ajax_loader_info.gif" width="24" height="24" alt="" class="loader" />
		<bright:cmsWrite identifier="asset-background-edit-in-progress" filter="false"/>
	</div>
</c:if>