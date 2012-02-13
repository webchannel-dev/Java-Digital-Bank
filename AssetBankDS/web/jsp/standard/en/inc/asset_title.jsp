<%-- Set the title to the name attribute or ID if it doesn't exist --%>
<c:choose>
	<c:when test="${not empty assetForm.asset.searchName}">
		<c:set var="assetName" value="${assetForm.asset.searchName}"/>
	</c:when>
	<c:otherwise>
		<c:set var="assetName" value="${assetForm.asset.id}"/>
	</c:otherwise>
</c:choose>