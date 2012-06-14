<bright:applicationSetting id="enableUnsubmittedAssets" settingName="workflow-enable-unsubmitted-assets" />
<bright:applicationSetting id="usersWithUpdateCanAddAssetsToWF" settingName="workflow-users-with-update-can-add-assets-to-workflow"/>
<c:set var="showSubmitted" value="${usersWithUpdateCanAddAssetsToWF || userprofile.isAdmin || userprofile.hasUploadWithApproval}"/>


		<div class="adminTabs">

			<c:if test="${enableUnsubmittedAssets}">
				<c:choose>
					<c:when test="${tabId == 'unsubmitted'}">
						<h2 class="current"><bright:cmsWrite identifier="menu-unsubmitted" filter="false"/></h2>
					</c:when>
					<c:otherwise>
						<h2><a href="../action/viewUnsubmittedAssets"><bright:cmsWrite identifier="menu-unsubmitted" filter="false"/></a></h2>
					</c:otherwise>		
				</c:choose>

			</c:if>
			<c:if test="${showSubmitted}">
				<c:choose>
					<c:when test="${tabId == 'approval'}">
						<h2 class="current"><bright:cmsWrite identifier="tab-awaiting-approval" filter="false"/></h2>
					</c:when>
					<c:otherwise>
						<h2><a href="../action/viewOwnerAssetApproval"><bright:cmsWrite identifier="tab-awaiting-approval" filter="false"/></a></h2>
					</c:otherwise>		
				</c:choose>
						
			</c:if>
			
			<c:choose>
				<c:when test="${tabId == 'approved'}">
					<h2 class="current"><bright:cmsWrite identifier="tab-live" filter="false"/></h2>
				</c:when>
				<c:otherwise>
					<h2><a href="../action/viewMyUploads"><bright:cmsWrite identifier="tab-live" filter="false"/></a></h2>
				</c:otherwise>		
			</c:choose>
			
			<div class="tabClearing">&nbsp;</div>
		</div>
		<bean:define id="tabsPresent" value="true" toScope="request" />
