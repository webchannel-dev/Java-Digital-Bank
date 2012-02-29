<c:choose>
	<c:when test="${categoryExplorerType == matchType}">
		<a href="../action/viewHome?categoryId=<bean:write name='linkCategory' property='id'/>&amp;categoryTypeId=<bean:write name='linkCategory' property='categoryTypeId'/>">
	</c:when>
	<c:otherwise>
		<a href="../action/browseItems?categoryId=<bean:write name='linkCategory' property='id'/>&amp;categoryTypeId=<bean:write name='linkCategory' property='categoryTypeId'/>">
	</c:otherwise>
</c:choose>