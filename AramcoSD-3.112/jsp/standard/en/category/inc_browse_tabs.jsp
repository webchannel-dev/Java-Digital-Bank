<bright:applicationSetting id="keywordBrowse" settingName="keyword-browse-page-id"/>
<c:if test="${keywordBrowse > 0}">
	<div class="adminTabs">
		<c:choose>
			<c:when test="${tabId == 'browseCategories'}">
				<h2 class="current"><bright:cmsWrite identifier="tab-browse-cats" filter="false"/></h2>
			</c:when>
			<c:otherwise>
				<h2><a href="browseItems?categoryId=-1&categoryTypeId=1"><bright:cmsWrite identifier="tab-browse-cats" filter="false"/></a></h2>
			</c:otherwise>		
		</c:choose>
		<c:choose>
			<c:when test="${tabId == 'browseKeywords'}">
				<h2 class="current"><bright:cmsWrite identifier="tab-browse-keywords" filter="false"/></h2>
			</c:when>
			<c:otherwise>
				<h2><a href="keywordBrowser"><bright:cmsWrite identifier="tab-browse-keywords" filter="false"/></a></h2>
			</c:otherwise>		
		</c:choose>
		<div class="tabClearing">&nbsp;</div>
	</div>
</c:if>