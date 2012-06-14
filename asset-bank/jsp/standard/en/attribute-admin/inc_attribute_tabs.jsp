<div class="adminTabs">
	<c:choose>
		<c:when test="${tabId == 'manageAttributes'}">
			<h2 class="current">Manage Attributes</h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewManageAttributes">Manage Attributes</a></h2>
		</c:otherwise>		
	</c:choose>
	<c:choose>
		<c:when test="${tabId == 'displayAttributes'}">
			<h2 class="current">Display Attributes</h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewManageDisplayAttributes?daGroupId=8">Display Attributes</a></h2>
		</c:otherwise>		
	</c:choose>	
	<c:choose>
		<c:when test="${tabId == 'attributeSorting'}">
			<h2 class="current">Sorting</h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewManageSortAttributes">Sorting</a></h2>
		</c:otherwise>		
	</c:choose>
	<c:choose>
		<c:when test="${tabId == 'nameAttributes'}">
			<h2 class="current">Name Attributes</h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewManageNameAttributes">Name Attributes</a></h2>
		</c:otherwise>		
	</c:choose>
	<c:choose>
		<c:when test="${tabId == 'embeddedData'}">
			<h2 class="current">Embedded Data Mappings</h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewManageEmbeddedDataMappings">Embedded Data Mappings</a></h2>
		</c:otherwise>		
	</c:choose>
	<c:choose>
		<c:when test="${tabId == 'filters'}">
			<h2 class="current">Filters</h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewManageFilters?type=1">Filters</a></h2>
		</c:otherwise>		
	</c:choose>
	<div class="tabClearing">&nbsp;</div>
</div>