<bright:applicationSetting id="bulkUpdateAdminOnly" settingName="bulk-update-admin-only"/>

<div class="adminTabs">
	<c:choose>
		<c:when test="${tabId == 'batchupdate'}">
			<h2 class="current"><bright:cmsWrite identifier="tab-batch-update" filter="false"/></h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewManageBatchUpdate"><bright:cmsWrite identifier="tab-batch-update" filter="false"/></a></h2>
		</c:otherwise>		
	</c:choose>
	<c:if test="${!bulkUpdateAdminOnly || userprofile.isAdmin}">
		<c:choose>
			<c:when test="${tabId == 'bulkupdate'}">
				<h2 class="current"><bright:cmsWrite identifier="tab-bulk-update" filter="false"/></h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewManageBulkUpdate"><bright:cmsWrite identifier="tab-bulk-update" filter="false"/></a></h2>
			</c:otherwise>		
		</c:choose>
	</c:if>
	<c:if test="${userprofile.isAdmin || userprofile.isOrgUnitAdmin}">
		<c:choose>
			<c:when test="${tabId == 'metadataimport'}">
				<h2 class="current"><bright:cmsWrite identifier="tab-metadata-import" filter="false"/></h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewManageMetadataImport"><bright:cmsWrite identifier="tab-metadata-import" filter="false"/></a></h2>
			</c:otherwise>		
		</c:choose>
	</c:if>
	<div class="tabClearing">&nbsp;</div>
</div>