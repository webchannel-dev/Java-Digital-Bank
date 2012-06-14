<div class="adminTabs">
	<c:choose>
		<c:when test="${tabId == 'manageUsers'}">
			<h2 class="current">Manage Users</h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewUserAdmin">Manage Users</a></h2>
		</c:otherwise>		
	</c:choose>
	<c:choose>
		<c:when test="${tabId == 'messageUsers'}">
			<h2 class="current">Message Users</h2>		
		</c:when>
		<c:otherwise>
			<h2><a href="viewMessageUsers" >Message Users</a></h2>
		</c:otherwise>		
	</c:choose>
	
	<logic:equal name="userprofile" property="isAdmin" value="true">
		<c:choose>
			<c:when test="${tabId == 'customFields'}">
				<h2 class="current">User Fields</h2>		
			</c:when>
			<c:otherwise>
				<h2><a href="viewManageCustomFields" >User Fields</a></h2>
			</c:otherwise>		
		</c:choose>
	</logic:equal>
	<div class="tabClearing">&nbsp;</div>
</div>

	