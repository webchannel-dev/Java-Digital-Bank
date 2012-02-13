<h1>
	<bright:cmsWrite identifier="heading-search-section" filter="false" />
	<c:if test="${not empty searchForm.relationName}">
		(<bright:cmsWrite identifier="link-find" filter="false"/> <bean:write name="searchForm" property="relationName" filter="false"/>)
	</c:if>
</h1>		
<div class="adminTabs">
	<c:choose>
		<c:when test="${tabId == 'advancedSearch'}">
			<h2 class="current"><bright:cmsWrite identifier="tab-advanced-search" filter="false"/></h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewLastSearch"><bright:cmsWrite identifier="tab-advanced-search" filter="false"/></a></h2>
		</c:otherwise>		
	</c:choose>
	<c:choose>
		<c:when test="${tabId == 'savedSearches'}">
			<h2 class="current"><bright:cmsWrite identifier="tab-saved-searches" filter="false"/></h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewSavedSearches"><bright:cmsWrite identifier="tab-saved-searches" filter="false"/></a></h2>
		</c:otherwise>		
	</c:choose>
	<div class="tabClearing">&nbsp;</div>
</div>
