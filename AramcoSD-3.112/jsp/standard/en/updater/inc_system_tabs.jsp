<bright:applicationSetting id="showUpdates" settingName="application-updater"/>
<bright:applicationSetting id="canPublish" settingName="allow-publishing"/>
<bright:applicationSetting id="batchReleasesEnabled" settingName="batch-releases-enabled"/>

<div class="adminTabs">
	<c:choose>
		<c:when test="${tabId == 'system'}">
			<h2 class="current">System</h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewSystemAdmin">System</a></h2>
		</c:otherwise>		
	</c:choose>
	<c:if test="${showUpdates}">
		<c:choose>
			<c:when test="${tabId == 'update'}">
				<h2 class="current">Updates</h2>		
			</c:when>
			<c:otherwise>
				<h2><a href="viewApplicationUpdateAdmin" title="View available updates">Updates</a></h2>
			</c:otherwise>		
		</c:choose>
	</c:if>
	<c:choose>
		<c:when test="${tabId == 'developer'}">
			<h2 class="current">Developer</h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewDeveloperAdmin">Developer</a></h2>
		</c:otherwise>		
	</c:choose>		
	<c:if test="${canPublish}">
		<c:choose>
			<c:when test="${tabId == 'synch'}">
				<h2 class="current">Synchronisation</h2>		
			</c:when>
			<c:otherwise>
				<h2><a href="viewSynchAdmin" title="View Syncronisation Admin">Synchronisation</a></h2>
			</c:otherwise>		
		</c:choose>
	</c:if>
	<c:choose>
		<c:when test="${tabId == 'storage'}">
			<h2 class="current">Storage Devices</h2>		
		</c:when>
		<c:otherwise>
			<h2><a href="viewStorageDevices" title="View Storage Device Admin">Storage Devices</a></h2>
		</c:otherwise>		
	</c:choose>
	<c:if test="${batchReleasesEnabled}">
		<c:choose>
			<c:when test="${tabId == 'brJobs'}">
				<h2 class="current">Batch Release Jobs</h2>		
			</c:when>
			<c:otherwise>
				<h2><a href="viewBatchReleaseJobs" title="View Batch Release Jobs">Batch Release Jobs</a></h2>
			</c:otherwise>		
		</c:choose>
	</c:if>
	<c:choose>
		<c:when test="${tabId == 'jobQueue'}">
			<h2 class="current">Job Queue</h2>		
		</c:when>
		<c:otherwise>
			<h2><a href="viewJobQueue?jobgroup=ACTIVE" title="View Job Queue">Job Queue</a></h2>
		</c:otherwise>		
	</c:choose>	
	<c:choose>
		<c:when test="${tabId == 'facebook'}">
			<h2 class="current">Facebook</h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewFacebookAdmin" title="View Facebook Settings">Facebook</a></h2>
		</c:otherwise>	 	
	</c:choose>
	<bright:refDataList id="extraTabItems" componentName="LinkManager" methodName="getExtraLinkItems" argumentValue="system_tabs" passUserprofile="true"/>
	<c:if test="${not empty extraTabItems}">
	<logic:iterate name="extraTabItems" id="extraItem">
		<c:choose>
			<c:when test="${tabId == extraItem.id}">
				<h2 class="current">${extraItem.text}</h2>	
			</c:when>
			<c:otherwise>
				<h2><a href="<bean:write name="extraItem" property="href"/>" title="<bean:write name="extraItem" property="id"/>"><bean:write name="extraItem" property="text"/></a></h2>
			</c:otherwise>
		</c:choose>
	</logic:iterate>
	</c:if>
	<div class="tabClearing">&nbsp;</div>
</div>

	