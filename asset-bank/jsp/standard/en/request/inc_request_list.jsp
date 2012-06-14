<logic:notPresent name="showRequester">
	<bean:define id="showRequester" value="false" />
</logic:notPresent>

<logic:notPresent name="showStatus">
	<bean:define id="showStatus" value="false" />
</logic:notPresent>

<logic:notPresent name="extraParams">
	<bean:define id="extraParams" value="" />
</logic:notPresent>

<logic:notPresent name="canEdit">
	<bean:define id="canEdit" value="false" />
</logic:notPresent>

<%-- iterate once to determine whether to show the fulfillment column --%>
<c:set var="showFulfiller" value="false" />
<logic:iterate name="requests" id="aRequest">
	<logic:notEmpty name="aRequest" property="fulfillmentUser">
		<c:set var="showFulfiller" value="true" />
	</logic:notEmpty>
</logic:iterate>

<table cellspacing="0" class="list stripey noTopMargin">	
	<thead>
		<tr>
			<th><bright:cmsWrite identifier="label-date-submitted" /></th>
			<c:if test="${showRequester}"><th><bright:cmsWrite identifier="label-submitted-by" /></th></c:if>
			<c:if test="${showFulfiller}"><th><bright:cmsWrite identifier="label-fulfiller" /></th></c:if>
			<th><bright:cmsWrite identifier="label-description" /></th>
			<c:if test="${showStatus}"><th style="width: 120px"><bright:cmsWrite identifier="label-status-sans-colon" /></th></c:if>
			<th style="width: 120px"><bright:cmsWrite identifier="label-actions" /></th>
		</tr>
	</thead>
	<tbody>
		<logic:iterate name="requests" id="aRequest">
			<tr>
				<td><bean:write name="aRequest" property="dateAdded" format="dd/MM/yyyy" /></td>
				<c:if test="${showRequester}"><td><bean:write name="aRequest" property="requestUser.forename" />&nbsp;<bean:write name="aRequest" property="requestUser.surname" /></td></c:if>
				<c:if test="${showFulfiller}"><td><c:choose><c:when test="${not empty aRequest.fulfillmentUser}"><bean:write name="aRequest" property="fulfillmentUser.forename" />&nbsp;<bean:write name="aRequest" property="fulfillmentUser.surname" /></c:when><c:otherwise><bright:cmsWrite identifier="snippet-not-applicable" /></c:otherwise></c:choose></td></c:if>
				<td><bean:write name="aRequest" property="description" /></td>
				<c:if test="${showStatus}">
					<td>
						<c:choose>
							<c:when test="${aRequest.workflowInfo.stateName == 'fulfilled'}">
								<span class="status status-success">
							</c:when>
							<c:when test="${aRequest.workflowInfo.stateName == 'rejected'}">
								<span class="status status-fail">
							</c:when>	
							<c:otherwise>
								<span class="status status-wait">
							</c:otherwise>
						</c:choose>	
						<bean:write name="aRequest" property="workflowInfo.state.description" /></span>
					</td>
				</c:if>
				<td>
					[<a href="viewRequest?id=<c:out value='${aRequest.id}' /><c:out value='${extraParams}'/>" ><bright:cmsWrite identifier="link-view" /></a>]
					<c:if test="${ canEdit || (aRequest.requestUser.id == userprofile.userId && aRequest.workflowInfo.stateName == 'requires-approval') }">
						[<a href="viewEditRequest?id=<c:out value='${aRequest.id}' /><c:out value='${extraParams}'/>" ><bright:cmsWrite identifier="link-edit" /></a>]
					</c:if>
					<c:if test="${userprofile.isAdmin || (aRequest.requestUser.id == userprofile.userId)}">
						[<a href="deleteRequest?id=<c:out value='${aRequest.id}' /><c:out value='${extraParams}'/>" onclick="return confirm('<bright:cmsWrite identifier="snippet-delete-request-confirmation" />');"><bright:cmsWrite identifier="link-delete" /></a>]
					</c:if>
				</td>
			</tr>
		</logic:iterate>
	</tbody>	
</table>