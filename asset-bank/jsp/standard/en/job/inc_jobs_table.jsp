
<table cellspacing="0" class="message list highlight"  summary="List of Jobs">
	<thead>
		<tr>
			<th>Job Id</th>
			<th>Client Reference</th>
			<th>Job Type</th>
			<c:if test='${jobgroup == "ACTIVE"}'>	
				<th>Job Status</th>
			</c:if>
			<th>Date Created</th>
			<%-- runTimeSecs property is only ever non-null for jobs with status Running --%>
			<c:if test='${jobgroup == "ACTIVE"}'>
				<th>Runtime/secs</th>
			</c:if>
			<c:if test='${jobgroup == "FAILED"}'>
				<th>Message</th>
			</c:if>	
			<th colspan="2"><bright:cmsWrite identifier="label-actions" filter="false"/></th>	
		</tr>
	</thead>
	<tbody>
		<logic:iterate name="inDesignJobs" id="inDesignJob">
			<tr>
				<td><bean:write name="inDesignJob" property="id"/></td>
				<td><bean:write name="inDesignJob" property="clientReference"/></td>
				<td><bean:write name="inDesignJob" property="jobType"/></td>
				<c:if test='${jobgroup == "ACTIVE"}'>		
					<td><bean:write name="inDesignJob" property="jobStatus"/></td>
				</c:if>
				<td>
					<fmt:formatDate type="both" dateStyle="default" timeStyle="short" value="${inDesignJob.dateCreated}"/>
				</td>
				<c:if test='${jobgroup == "ACTIVE"}'>
					<td>
						<bean:write name="inDesignJob" property="runTimeSecs"/>
					</td>
				</c:if>
				<c:if test='${jobgroup == "FAILED"}'>
					<td><bean:write name="inDesignJob" property="failMessage"/></td>
				</c:if>
				<td class="action">
					<a href="deleteJobInQueue?id=<bean:write name="inDesignJob" property="id"/>&jobgroup=<bean:write name="jobgroup"/>"><bright:cmsWrite identifier="link-delete" filter="false"/></a>
					<c:if test="${ not empty inDesignJob.assetId && inDesignJob.assetId != 0 }">
						| <a href="viewAsset?id=<bean:write name="inDesignJob" property="assetId"/>">view asset</a>
					</c:if>
					<c:if test='${jobgroup == "FAILED"}'>
						| <a href="retryJobInQueue?jobgroup=<bean:write name="jobgroup"/>&jobId=<bean:write name="inDesignJob" property="id"/>">retry</a>
					</c:if>
				</td>
			</tr>
		</logic:iterate>
	</tbody>
</table>