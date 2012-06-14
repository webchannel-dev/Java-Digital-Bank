<c:if test="${assetForm.hasErrors}">
	<c:choose>
		<c:when test='${assetForm.hasInfo}'>
			<bean:define id="messages" name="assetForm" property="info"/>
			<bean:define id="messageClass" value="info"/>
		</c:when>
		<c:otherwise>
			<bean:define id="messages" name="assetForm" property="errors"/>
			<bean:define id="messageClass" value="error"/>
		</c:otherwise>
	</c:choose>
	<div class="<bean:write name='messageClass'/>">
		<logic:iterate name="messages" id="errorText">
			<bright:writeError name="errorText" /><br />
		</logic:iterate>
		<c:if test="${assetForm.noUploadFileSpecified && assetForm.tempFileLocation == null}">
			<br /><bright:cmsWrite identifier="snippet-file-selection-warning" filter="false"/>
		</c:if>
	</div>
</c:if>