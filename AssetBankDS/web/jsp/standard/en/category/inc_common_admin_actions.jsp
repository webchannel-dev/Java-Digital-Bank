<td class="action">
	[<a href="<c:out value='${openAction}' />?categoryId=<bean:write name='objCategory' property='id' />&ouid=<c:out value='${ouid}' />">open</a>]
</td>				
<td class="action">
	[<a href="<c:out value='${moveAction}' />?categoryId=<bean:write name='categoryAdminForm' property='categoryId' />&amp;catIdToMove=<bean:write name='objCategory' property='id' />&amp;direction=up&ouid=<c:out value='${ouid}' />">up</a>]
</td>
<td class="action">
	[<a href="<c:out value='${moveAction}' />?categoryId=<bean:write name='categoryAdminForm' property='categoryId' />&amp;catIdToMove=<bean:write name='objCategory' property='id' />&amp;direction=down&ouid=<c:out value='${ouid}' />">down</a>]
</td>
<logic:equal name="userprofile" property="isAdmin" value="true">
	<td class="action">
		[<a href="viewChangeCategoryParent?parentId=<bean:write name='categoryAdminForm' property='categoryId' />&amp;categoryIdToMove=<bean:write name='objCategory' property='id' />&amp;categoryTypeId=<bean:write name='categoryAdminForm' property='categoryTreeId'/>"><bright:cmsWrite identifier="link-move" filter="false" /></a>]
	</td>
</logic:equal>
<td class="action" <c:if test="${categoryAdminForm.noOfExtendedCategories <= 0}">style="padding-right:0"</c:if>>
	<c:if test="${catExtensionAssetsEnabled && objCategory.extensionAssetId > 0}">
		[<a href="viewAsset?id=<bean:write name='objCategory' property='extensionAssetId' />">extension asset</a>]
	</c:if>
</td>
<td class="action">
	[<a href="<c:out value='${updateAction}' />?categoryId=<bean:write name='objCategory' property='id' />&ouid=<c:out value='${ouid}' />&parentId=<bean:write name='parentId' />">edit</a>]
</td>
<td class="action">
	<%-- Check cannot be deleted --%>
	<c:if test="${!objCategory.cannotBeDeleted}">
		[<a href="<c:out value='${deleteAction}' />?categoryId=<bean:write name='categoryAdminForm' property='categoryId' />&amp;categoryIdToDelete=<bean:write name='objCategory' property='id' />&amp;ouid=<bean:write name='ouid' />">X</a>]						
	</c:if>
</td>