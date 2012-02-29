<c:choose>
	<c:when test="${catForName.categoryTypeId == 1 && catForName.id == -1}">
		<c:set var="catName"><bright:cmsWrite identifier="category-root" filter="false"/></c:set>
	</c:when>
	<c:when test="${catForName.categoryTypeId == 2 && catForName.id == -1}">
		<c:set var="catName"><bright:cmsWrite identifier="access-level-root" filter="false"/></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="catName" value="${catForName.name}"/>
	</c:otherwise>
</c:choose>
