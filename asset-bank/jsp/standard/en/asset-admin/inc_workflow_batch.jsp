<c:if test="${!empty batchAssetForm.transitions}">

<div id="approvalState" class="clearfix">

	<h3><bright:cmsWrite identifier="subhead-change-approval-status" filter="false"/></h3>

	<%--  Signifies to the update actions that this is a batch approval, so no need to validate mandatories --%>
	<input type="hidden" name="isapproval" value="1" />
	<c:set var="onsubmit" value="return doConfirmation(${ais.assetId});" />
	<input type="hidden" name="assetId" value="<c:out value="${ais.assetId}" />" />
	<div class="container">
		<ul class="radioList">
				<li class="clearfix">
					<input type="radio" class="radio" id="skip" name="transition" value="-1" onclick=hideMessages(); <c:if test='${batchAssetForm.selectedTransition==-1}'>checked="checked"</c:if>/>	
					<label for="skip"><bright:cmsWrite identifier="snippet-decide-later" filter="false"/></label>
				</li>	
			<logic:iterate name="batchAssetForm" property="transitions" id="transition" indexId="index4">
				<c:if test="${!transition.hidden}">
					<li class="clearfix">
					
						<input type="hidden" id="text_<c:out value='${ais.assetId}' />_<c:out value='${transition.transitionNumber}' />" value="<c:out value='${transition.confirmationText}' />" />
						
						<c:choose>
							<c:when test="${transition.hasMessage}"><c:set var="onClickAction" value="showMessages()" /></c:when>
							<c:otherwise><c:set var="onClickAction" value="hideMessages()" /></c:otherwise>
						</c:choose>
						<input  type="radio" class="radio" id="radio_<c:out value='${ais.assetId}' />_<c:out value='${transition.transitionNumber}' />" name="transition" value="<c:out value="${transition.transitionNumber}" />" onclick=<c:out value='${onClickAction}'/>; <c:if test='${batchAssetForm.selectedTransition==transition.transitionNumber}'>checked="checked"</c:if>/>	
						
						<label for="radio_<c:out value='${ais.assetId}' />_<c:out value='${transition.transitionNumber}' />">
							<c:out value="${transition.description}" />
							<span><c:if test="${transition.helpText!=null}"><c:out value="${transition.helpText}" /></c:if></span>
							<c:choose>
								<c:when test="${transition.messageMandatory}">
									<span><em>(<bright:cmsWrite identifier="snippet-message-required" />)</em></span><span class="required">*</span>
								</c:when>
								<c:otherwise>
									<c:if test="${transition.hasMessage}"><span><em>(<bright:cmsWrite identifier="snippet-message-optional" />)</em></span></c:if>
								</c:otherwise>
							</c:choose>
						</label>
					</li>	
				</c:if>
			</logic:iterate>
		</ul>
		
		<div id="approvalMessages">
				
			<%--  Only show audit if there is at least one audit entry - show link to view if this is not an owner state --%>												
			<c:if test="${batchAssetForm.auditEntry.username!=null}">
				<c:if test="${!batchAssetForm.state.visibleToOwner}">			
					<a href="../action/viewWorkflowAudit?id=<c:out value='${batchAssetForm.asset.id}'/>" target="_blank" class="viewMessages"  onclick="popupAssetWorkflowAudit(<c:out value='${batchAssetForm.asset.id}' />); return false;" title="View asset workflow audit in a new window"><bright:cmsWrite identifier="link-previous-messages" filter="false"/> &raquo;</a>
				</c:if>
				
				<strong><bright:cmsWrite identifier="snippet-message-from-previous" filter="false"/></strong>
				<p class="approvalMessage">	
					<c:out value="${batchAssetForm.auditEntry.message}" />
					<span class="userDate"><c:out value="${batchAssetForm.auditEntry.name}" /> (<c:out value="${batchAssetForm.auditEntry.username}" />) - <c:out value="${batchAssetForm.auditEntry.dateAdded}" /></span>
				</p>
			</c:if>

			<%--  Show message if any of the transitions have one --%>
			<c:if test="${batchAssetForm.state.transitionsHaveMessages}">
				<label for="newMessage"><bright:cmsWrite identifier="snippet-message-to-next" filter="false"/></label>
				<br />				
				<textarea name="message" rows="3" id="newMessage"></textarea>
			</c:if>
				
		</div>
	</div>
</div>	

</c:if>