
<div class="adminTabs">
	<c:if test="${userprofile.canApproveImageUploads || userprofile.isAdmin}">
		<c:choose>
			<c:when test="${tabId == 'approveuploads'}">
				<h2 class="current"><bright:cmsWrite identifier="tab-approve-uploads" filter="false"/></h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewAssetUploadApproval"><bright:cmsWrite identifier="tab-approve-uploads" filter="false"/></a></h2>
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