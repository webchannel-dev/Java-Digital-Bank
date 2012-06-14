<c:if test="${userprofile.isAdmin || userprofile.isOrgUnitAdmin}">	
	<div class="adminTabs">
		<c:choose>
			<c:when test="${tabId == 'details'}">
				<h2 class="current"><bean:write name="orgUnitForm" property="orgUnit.category.name"/> Details</h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewOrgUnitDetails?ouid=<bean:write name='orgUnitForm' property='orgUnit.id' />&returnUrl=<c:out value='${returnUrl}' />"><bean:write name="orgUnitForm" property="orgUnit.category.name"/> Details</a></h2>
			</c:otherwise>		
		</c:choose>
	
		<c:choose>
			<c:when test="${tabId == 'users'}">
				<h2 class="current"><bean:write name="orgUnitForm" property="orgUnit.category.name"/> Users</h2>		
			</c:when>
			<c:otherwise>
				<h2><a href="viewOrgUnitUsers?ouid=<bean:write name='orgUnitForm' property='orgUnit.id' />&returnUrl=<c:out value='${returnUrl}' />"><bean:write name="orgUnitForm" property="orgUnit.category.name"/> Users</a></h2>
			</c:otherwise>		
		</c:choose>

		<c:choose>
			<c:when test="${tabId == 'messages'}">
				<h2 class="current"><bean:write name="orgUnitForm" property="orgUnit.category.name"/> Messages</h2>		
			</c:when>
			<c:otherwise>
				<h2><a href="viewOrgUnitMessages?ouid=<bean:write name='orgUnitForm' property='orgUnit.id' />&returnUrl=<c:out value='${returnUrl}' />"><bean:write name="orgUnitForm" property="orgUnit.category.name"/> Messages</a></h2>
			</c:otherwise>		
		</c:choose>

		<div class="tabClearing">&nbsp;</div>
	</div>
</c:if>