<c:if test="${assetForm.asset.extendsCategory.id > 0}">
	<c:set var="browseUrl" value="../action/browseItems" />
	<c:choose>
		<c:when test="${assetForm.asset.extendsCategory.categoryTypeId == 2}">
			<c:set var="returnUrl" value="/action/viewUpdatePermissionCategory?categoryId=${assetForm.asset.extendsCategory.id}&parentId=${assetForm.asset.extendsCategory.parentId}" />
			<c:if test="${categoryExplorerType == 'accesslevels'}">
				<c:set var="browseUrl" value="../action/viewHome" />
			</c:if>
		</c:when>
		<c:otherwise>
			<c:set var="returnUrl" value="/action/viewUpdateCategory?categoryId=${assetForm.asset.extendsCategory.id}&parentId=${assetForm.asset.extendsCategory.parentId}" />
			<c:if test="${categoryExplorerType == 'categories'}">
				<c:set var="browseUrl" value="../action/viewHome" />
			</c:if>
		</c:otherwise>
	</c:choose>
	<c:set var="catName" value="${assetForm.asset.extendsCategory.name}" />
	<c:set var="link" value="${browseUrl}?categoryId=${assetForm.asset.extendsCategory.id}&categoryTypeId=${assetForm.asset.extendsCategory.categoryTypeId}" />
	<c:if test="${userprofile.isAdmin || assetForm.userCanUpdateAsset}">
		
		| <bright:cmsWrite identifier="snippet-asset-is-category-extension" filter="false" replaceVariables="true" />
		
	</c:if>
</c:if>