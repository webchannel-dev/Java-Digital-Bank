<c:choose>
	<c:when test="${downloadForm.selectedUsageType.usageType.highResolution && downloadForm.userMustRequestApprovalForHighRes && !downloadForm.userHasApprovalForHighRes && !downloadForm.highResOptionsDisabled}">
		<html:submit styleClass="button" value="Request approval" property="b_requestApproval" styleId="request_button"/>
	</c:when>
	
	
	<c:otherwise>
		<html:submit styleId="previewButton" styleClass="button flush" property="b_preview"><logic:present name="previewDuration"><bean:write name='previewDuration'/>s </logic:present><bright:cmsWrite identifier="button-preview" filter="false" /></html:submit>
		<c:if test="${!userprofile.isFromCms}">
			<html:submit styleClass="button flush" property="b_download" styleId="submit_button"><bright:cmsWrite identifier="button-download-now" filter="false" /></html:submit>
		</c:if>
		<c:if test="${userprofile.isFromCms}">
			<html:submit styleClass="button" property="b_download" styleId="submit_button"><bright:cmsWrite identifier="button-select-for-cms" filter="false" /></html:submit>
		</c:if>
	</c:otherwise>
</c:choose>	