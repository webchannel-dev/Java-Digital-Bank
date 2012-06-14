<%-- Edit client-side button - only show if user can edit --%>
<c:if test="${canBeClientSideCheckedOut || canBeClientSideViewed || canBeClientSideResumed}">

	<c:if test="${assetForm.asset.currentVersionId<=0 || canEditAssetVersions}">
		<div id="pluginPanel" class="well <logic:present name="userFileIsCheckedOutTo">warning noIcon</logic:present>">
			<p><bright:cmsWrite identifier="snippet-source-file"/> <strong>${assetForm.asset.originalFilename}</strong></p>
			
			<c:if test="${not empty params}">
				<c:set var="checkoutReturnUrl" value="${viewUrl}?index=${pos}&${params}" />
			</c:if>
			<c:if test="${empty params}">
				<c:set var="checkoutReturnUrl">viewAsset?id=<bean:write name='assetForm' property='asset.id'/></c:set>
			</c:if>
			
			<c:choose>
				<c:when test="${empty cseEditorName}">
					<c:set var="cseEditButtonLabel"><bright:cmsWrite identifier="snippet-edit-source-file"/></c:set>
				</c:when>
				<c:otherwise>
					<c:set var="cseEditButtonLabel"><bright:cmsWrite identifier="snippet-edit-source-file-in" replaceVariables="true"/></c:set>
				</c:otherwise>
			</c:choose>

			<c:choose>
				<c:when test="${empty cseEditorName}">
					<c:set var="cseViewButtonLabel"><bright:cmsWrite identifier="snippet-view-source-file"/></c:set>
				</c:when>
				<c:otherwise>
					<c:set var="cseViewButtonLabel"><bright:cmsWrite identifier="snippet-view-source-file-in" replaceVariables="true"/></c:set>
				</c:otherwise>
			</c:choose>
			
			
			<c:if test="${canBeClientSideCheckedOut}">
				<form name="updateForm" action="../action/clientSideEditPrepAction" method="get">
					<input type="hidden" name="returnUrl" value="<c:out value='${checkoutReturnUrl}' />" />
					<input type="hidden" name="assetId" value="<bean:write name='assetForm' property='asset.id'/>" />
					<bright:submitButton disabled="${backgroundEditInProgress}" styleId="clientSideEdit" styleClass="button button-alt1" value="${cseEditButtonLabel}"/>
				</form>&nbsp;&nbsp;
			</c:if>
	

			<logic:present name="userFileIsCheckedOutTo">							

				<c:set var="forename" value="${userFileIsCheckedOutTo.forename}" /><c:set var="surname" value="${userFileIsCheckedOutTo.surname}" />
				<p class="warningInline">&nbsp;<bright:cmsWrite identifier="snippet-source-file-checked-out-to" replaceVariables="true"/></p> 
				
				<bright:a disabled="${backgroundEditInProgress}" href="../action/clientSideEditPrepAction?assetId=${assetForm.asset.id}&amp;returnUrl=${checkoutReturnUrl}" styleClass="button">
					<c:choose>
						<c:when test="${canBeClientSideResumed}">
							<bright:cmsWrite identifier="snippet-resume-editing-source-file"/>
						</c:when>
						<c:when test="${canBeClientSideViewed}">
							${cseViewButtonLabel}
						</c:when>
					</c:choose>
				</bright:a>
				<c:if test="${userprofile.isAdmin || userFileIsCheckedOutTo.id == userprofile.userId}">
					<c:url var="confirmCancelAssetCheckoutUrl"
							value="/action/confirmCancelAssetCheckout">
						<c:param name="id" value="${assetForm.asset.id}"/>
						<c:if test='${not empty params}'>
							<c:param name="returnUrl" value="${viewUrl}?index=${pos}&${params}"/>
						</c:if>
					</c:url>
					&nbsp;&nbsp;<bright:a disabled="${backgroundEditInProgress}" href="${confirmCancelAssetCheckoutUrl}"><bright:cmsWrite identifier="title-cancel-checkout"/></bright:a>
				</c:if>
			</logic:present>
			
			<logic:notPresent name="userFileIsCheckedOutTo">
				<c:if test="${canBeClientSideViewed}">

					<a href="../action/clientSideEditPrepAction?assetId=<bean:write name='assetForm' property='asset.id'/>&readonly=true&returnUrl=<c:out value='${checkoutReturnUrl}' />">${cseViewButtonLabel}</a>

				</c:if>
			</logic:notPresent>

		</div> <!-- End of pluginPanel -->
	</c:if>
</c:if>