	<div class="adminTabs">
		<c:choose>
			<c:when test="${tabId == 'details'}">
				<h2 class="current"><bean:write name="orgUnitForm" property="orgUnit.category.name"/> Details</h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewOrgUnitDetails?ouid=<bean:write name='orgUnitForm' property='orgUnit.id' />"><bean:write name="orgUnitForm" property="orgUnit.category.name"/> Details</a></h2>
			</c:otherwise>		
		</c:choose>
	
		<c:choose>
			<c:when test="${tabId == 'users'}">
				<h2 class="current"><bean:write name="orgUnitForm" property="orgUnit.category.name"/> Users</h2>		
			</c:when>
			<c:otherwise>
				<h2><a href="viewOrgUnitUsers?ouid=<bean:write name='orgUnitForm' property='orgUnit.id' />"><bean:write name="orgUnitForm" property="orgUnit.category.name"/> Users</a></h2>
			</c:otherwise>		
		</c:choose>

		<div class="tabClearing">&nbsp;</div>
	</div>