		
	<html:submit styleId="previewButton" styleClass="button flush" property="b_preview"><logic:present name="previewDuration"><bean:write name='previewDuration'/>s </logic:present><bright:cmsWrite identifier="button-preview" filter="false" /></html:submit>
	
	<c:choose>	
		<c:when test="${!userprofile.isFromCms}">
			<html:submit styleClass="button flush" property="b_download" styleId="submit_button"><bright:cmsWrite identifier="button-download-now" filter="false" /></html:submit>
		</c:when>
		<c:otherwise>
			<html:submit styleClass="button flush" property="b_download" styleId="submit_button"><bright:cmsWrite identifier="button-select-for-cms" filter="false" /></html:submit>
		</c:otherwise>
	</c:choose>
