<bright:applicationSetting id="attributeTemplateEnabled" settingName="enable-attribute-templates"/>
	
<c:if test="${attributeTemplateEnabled && (userprofile.canUploadWithApproval || userprofile.canUpdateAssets)}">
	<div class="adminTabs">
		<c:choose>
			<c:when test="${tabId == 'profile'}">
				<h2 class="current">Your Profile</h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewChangeProfile">Your Profile</a></h2>
			</c:otherwise>		
		</c:choose>
		<c:if test="${attributeTemplateEnabled}">
			<c:choose>
				<c:when test="${tabId == 'filters'}">
					<h2 class="current">Attribute Templates</h2>		
				</c:when>
				<c:otherwise>
					<h2><a href="viewManageFilters?type=2" >Attribute Templates</a></h2>
				</c:otherwise>		
			</c:choose>
		</c:if>
		<div class="tabClearing">&nbsp;</div>
	</div>
</c:if>
	