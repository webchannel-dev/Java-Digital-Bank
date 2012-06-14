<html:hidden name="assetForm" property="catExtensionCatId" />
<html:hidden name="assetForm" property="catExtensionTreeId" />
<html:hidden name="assetForm" property="catExtensionParentId" />
<html:hidden name="assetForm" property="catExtensionReturnLocation" />
<html:hidden name="assetForm" property="presetName" />
<c:if test="${assetForm.catExtensionCatId > 0}">
	<c:choose>
		<c:when test="${assetForm.catExtensionTreeId == 2}">
			<c:set var="hidePermissionCategories" value="true"  scope="request" />
			<input type="hidden" name="permissionCategoryForm.categoryIds" value="<bean:write name='assetForm' property='catExtensionCatId' />" />
		</c:when>
		<c:otherwise>
			<c:set var="hideDescriptiveCategories" value="true"  scope="request" />
			<input type="hidden" name="descriptiveCategoryForm.categoryIds" value="<bean:write name='assetForm' property='catExtensionCatId' />" />
		</c:otherwise>
	</c:choose>
</c:if>
