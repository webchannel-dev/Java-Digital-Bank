<logic:notEmpty name="releases">
	<logic:notPresent name="hideActionLinks">
		<bean:define id="hideActionLinks" value="false" />
	</logic:notPresent>

	<logic:notPresent name="showReleaseDate">
		<bean:define id="showReleaseDate" value="false" />
	</logic:notPresent>

	<logic:notPresent name="extraParams">
		<bean:define id="extraParams" value="" />
	</logic:notPresent>

	<table class="list highlight wide" cellspacing="0">
		<thead>
			<tr>
				<th><bright:cmsWrite identifier="label-name" filter="false" /></th>
				<th><bright:cmsWrite identifier="label-status" filter="false" /></th>
				<th><bright:cmsWrite identifier="label-created-by" filter="false" /></th>
				<th><bright:cmsWrite identifier="label-created-date" filter="false" /></th>
				<c:if test="${showReleaseDate}"><th><bright:cmsWrite identifier="label-release-date" filter="false" /></th></c:if>
				<th><bright:cmsWrite identifier="label-actions" filter="false" />:</th>
			</tr>
		</thead>
		<tbody>
			
				
		<logic:iterate name="releases" id="release">
			<tr>
				<td><a href="viewBatchRelease?brId=<bean:write name='release' property='id' /><logic:notEmpty name='extraParams'>&<bean:write name='extraParams' /></logic:notEmpty>"><bean:write name='release' property='name' /></a> </td>
				<td><bean:define id="releaseInfoHolder" name="release" /><%@include file="inc_release_status.jsp"%></td>
				<bright:refDataList componentName="UserManager" transactionManagerName="DBTransactionManager" methodName="getUser" argumentValue="${release.createdByUserId}" dontPassLanguage="true" id="owner"/>
				<td><bean:write name='owner' property='username' /></td>
				<td><bean:write name='release' property='creationDate' format="dd/MM/yyyy" /></td>
				<c:if test="${showReleaseDate}"><td><bean:write name='release' property='releaseDate' format="dd/MM/yyyy" /></td></c:if>
				<td>
					[<a href="viewBatchRelease?brId=<bean:write name='release' property='id' /><logic:notEmpty name='extraParams'>&<bean:write name='extraParams' /></logic:notEmpty>"><bright:cmsWrite identifier="link-view" filter="false" /></a>]
					<c:if test="${!hideActionLinks && userprofile.canManageBatchReleases}"> 
						[<a href="viewEditBatchRelease?brId=<bean:write name='release' property='id' /><logic:notEmpty name='extraParams'>&<bean:write name='extraParams' /></logic:notEmpty>"><bright:cmsWrite identifier="link-edit" filter="false" /></a>]
						[<a href="deleteBatchRelease?id=<bean:write name='release' property='id' /><logic:notEmpty name='extraParams'>&<bean:write name='extraParams' /></logic:notEmpty>" onclick="return confirm('<bright:cmsWrite identifier='js-confirm-delete-batch-release' filter='false' />');"><bright:cmsWrite identifier="link-delete" filter="false" /></a>]
					</c:if>
				</td>
			</tr>
		</logic:iterate>
		
		</tbody>
	</table>
</logic:notEmpty>

<logic:empty name="releases">
	<p><i><bright:cmsWrite identifier="snippet-no-matching-batch-releases" filter="false" /></i></p>
</logic:empty>