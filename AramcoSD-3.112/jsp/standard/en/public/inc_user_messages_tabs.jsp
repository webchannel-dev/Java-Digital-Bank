<bright:applicationSetting id="archivePosition" settingName="messages-archive-position"/>

<c:set var="archiveEnabled" value="${archivePosition=='left' || archivePosition=='right'}"/>

<c:if test="${archiveEnabled}">
	<div class="adminTabs">
		<c:choose>
			<c:when test='${mgroup == "CURRENT"}'>
				<h2 class="current"><bright:cmsWrite identifier="tab-messages-current" filter="false"/></h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewUserMessages?mgroup=CURRENT"><bright:cmsWrite identifier="tab-messages-current" filter="false"/></a></h2>
			</c:otherwise>		
		</c:choose>		
		<c:if test="${archiveEnabled}">
			<c:choose>
				<c:when test='${mgroup == "ARCHIVE"}'>
					<h2 class="current"><bright:cmsWrite identifier="tab-messages-archive" filter="false"/></h2>
				</c:when>
				<c:otherwise>
					<h2><a href="viewUserMessages?mgroup=ARCHIVE"><bright:cmsWrite identifier="tab-messages-archive" filter="false"/></a></h2>
				</c:otherwise>		
			</c:choose>
		</c:if>
		<div class="tabClearing">&nbsp;</div>
	</div>
</c:if>
	