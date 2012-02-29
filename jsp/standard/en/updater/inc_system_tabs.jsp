<bright:applicationSetting id="showUpdates" settingName="application-updater"/>
<bright:applicationSetting id="canPublish" settingName="allow-publishing"/>
<bright:applicationSetting id="storeFilesInDatabase" settingName="store-files-in-database"/>

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
	<c:if test="${!storeFilesInDatabase}">
		<c:choose>
			<c:when test="${tabId == 'storage'}">
				<h2 class="current">Storage Devices</h2>		
			</c:when>
			<c:otherwise>
				<h2><a href="viewStorageDevices" title="View Storage Device Admin">Storage Devices</a></h2>
			</c:otherwise>		
		</c:choose>
	</c:if>
	<div class="tabClearing">&nbsp;</div>
</div>

	