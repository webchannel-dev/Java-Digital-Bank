<bright:applicationSetting settingName="batch-releases-enabled" id="batchReleasesEnabled" />
<div class="adminTabs">
	<c:if test="${userprofile.canApproveImageUploads && !batchReleasesEnabled}">
		<c:choose>
			<c:when test="${tabId == 'approveuploads'}">
				<h2 class="current"><bright:cmsWrite identifier="tab-approve-uploads" filter="false"/></h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewAssetUploadOrEditApproval?approvalType=uploads"><bright:cmsWrite identifier="tab-approve-uploads" filter="false"/></a></h2>
			</c:otherwise>		
		</c:choose>
		<c:choose>
			<c:when test="${tabId == 'approveedits'}">
				<h2 class="current"><bright:cmsWrite identifier="tab-approve-edits" filter="false"/></h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewAssetUploadOrEditApproval?approvalType=edits"><bright:cmsWrite identifier="tab-approve-edits" filter="false"/></a></h2>
			</c:otherwise>		
		</c:choose>
	</c:if>
	
	<c:if test="${userprofile.canApproveImages || userprofile.isAdmin}">
		<c:choose>
			<c:when test="${tabId == 'approvedownloads'}">
				<h2 class="current"><bright:cmsWrite identifier="tab-approve-downloads" filter="false"/></h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewAssetApproval?fromCatId=27&toCatId=1"><bright:cmsWrite identifier="tab-approve-downloads" filter="false"/></a></h2>
			</c:otherwise>		
		</c:choose>
	</c:if>
	
	<div class="tabClearing">&nbsp;</div>
</div>