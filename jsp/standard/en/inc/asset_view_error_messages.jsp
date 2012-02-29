	<%-- Display error message if user has attempted to download a file that they don't have permission to download --%>
	<c:if test="${assetForm.redirectedFromDownloadPage}">
		<div class="warning"><bright:cmsWrite identifier="NoPermissionToDownload"/></div>
	</c:if>