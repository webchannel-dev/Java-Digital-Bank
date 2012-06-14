<c:choose>
	<c:when test="${not empty returnUrl}">
		<p><a href="<c:out value='${returnUrl}' />">&laquo; Back</a></p>
	</c:when>
	<c:otherwise>
		<p><a href="listOrgUnits">&laquo; Back to Organisational Units list</a></p>
	</c:otherwise>
</c:choose>