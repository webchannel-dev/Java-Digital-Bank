<table class="form" cellspacing="0" cellpadding="0" border="0" width="100%">
	<c:if test="${managing}">
		<tr>
			<th><bright:cmsWrite identifier="label-submitted-by" />:</th>
			<td class="padded"><bean:write name="requestDetails" property="requestUser.forename" />&nbsp;<bean:write name="requestDetails" property="requestUser.surname" /></td>
		</tr>
	</c:if>
	<tr>
		<th><bright:cmsWrite identifier="label-status-sans-colon" />:</th>
		<td class="padded">
			<c:choose>
				<c:when test="${requestDetails.workflowInfo.stateName == 'fulfilled'}">
					<span class="status status-success">
				</c:when>
				<c:when test="${requestDetails.workflowInfo.stateName == 'rejected'}">
					<span class="status status-fail">
				</c:when>	
				<c:otherwise>
					<span class="status status-wait">
				</c:otherwise>
			</c:choose>
			<bean:write name="requestDetails" property="workflowInfo.state.description" /></span>
			
			<logic:present name="workflowAuditEntries">
				<logic:iterate name="workflowAuditEntries" id="entry" indexId="entryIndex">
					<c:if test="${ entryIndex == 0 && not empty(entry.message) }" >
						<blockquote>
							<p><span class="quotemark">&ldquo;</span><bean:write name="entry" property="message"/><span class="quotemark">&rdquo;</span></p>
							<small><bean:write name="entry" property="name"/></small>
						</blockquote>
					</c:if>
				</logic:iterate>
			</logic:present>
			
			
		</td>

	</tr>
	<tr>
		<th><bright:cmsWrite identifier="label-description" />:</th>
		<td class="padded">
			<c:choose>
				<c:when test="${editing}">
					<html:textarea name="requestForm" property="description" rows="5" cols="50" />
					<p><span class="required">*</span><bright:cmsWrite identifier="snippet-request-instructions" filter="false" /></p>
				</c:when>
				<c:otherwise>
					<bean:write name="requestDetails" property="description" />
				</c:otherwise>
			</c:choose>
		</td>
	</tr>

	
			<tr>
				<th class="left"><bright:cmsWrite identifier="label-history" />:</th>
				<td class="padded">
					<ul class="history"> 
						<logic:present name="workflowAuditEntries">
							<logic:notEmpty name="workflowAuditEntries">
								<logic:iterate name="workflowAuditEntries" id="entry">
									<li>
										<em><bean:write name="entry" property="dateAdded" format="dd/MM/yyyy"/></em>
										<span class="subtle">-</span>	
										<bean:write name="entry" property="name"/> 
										<span class="subtle">-</span>
										<bean:write name="entry" property="transition" />
									</li>
								</logic:iterate>
							</logic:notEmpty>
						</logic:present>
						<li><em><bean:write name="requestDetails" property="dateAdded" format="dd/MM/yyyy" /></em> <span class="subtle">-</span> <bean:write name="requestDetails" property="requestUser.forename" />&nbsp;<bean:write name="requestDetails" property="requestUser.surname" /> <span class="subtle">-</span> <bright:cmsWrite identifier="snippet-submitted" />
					</ul>
				</td>
			</tr>



	<c:if test="${requestDetails.workflowInfo.stateName == 'fulfilled'}">
		<c:set var="asset" value="${requestDetails.asset}" />
		
		<c:choose>
			<c:when test="${ asset != null}" >				
				<c:set var="thumbnailImagePath" value="../servlet/display?file=${asset.displayThumbnailImageFile.path}" />
			</c:when>
			<c:otherwise>
				<c:set var="thumbnailImagePath" value="../images/standard/imagenotfound.gif" />
			</c:otherwise>
		</c:choose>
		
		<c:if test="${ asset != null && requestDetails.workflowInfo.stateName == 'fulfilled'}">
			<c:choose>
				<c:when test="${ userprofile.user.id == requestDetails.requestUser.id || userprofile.isAdmin || asset.isFullyApproved}">
					<c:set var="linkEnabled" value="true" />
					<c:choose>
						<c:when test="${managing}"><c:set var="linkParams" value="requestId=${requestDetails.id}&managing=${managing}&stateName=${stateName}" /></c:when>
						<c:otherwise><c:set var="linkParams" value="requestId=${requestDetails.id}" /></c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:set var="linkEnabled" value="false" /> 
				</c:otherwise>
			</c:choose>
		</c:if>
		
		<tr>
			<th class="left"><bright:cmsWrite identifier="snippet-asset-requested" />:</th>
			<td class="padded">
				<c:if test="${linkEnabled}"><a href="../action/viewAsset?id=${asset.id}&${linkParams}"></c:if>
				<img class="image" src="${thumbnailImagePath}" >
				<c:if test="${linkEnabled}"></a></c:if>
			</td>
		</tr> 
	</c:if>

</table>