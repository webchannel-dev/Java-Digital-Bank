
<bean:define id="optionVal" name="optionValue" property="value"/>

<c:choose>
	<c:when test="${searchForm.refineSearch}">
		<bean:define id="hasValue" name="userprofile" property="<%= \"searchCriteria.attributeHasValue(\" + attributeId + \"%%\" + optionVal + \")\" %>"/>
	</c:when>
	<c:when test="${filterValue != null && filterValue.value != null}">
		<bean:define id="hasValue" name="currentFilter" property="<%= \"attributeHasValue(\" + attributeId + \"%%\" + optionVal + \")\" %>"/>
	</c:when>
	<c:otherwise>
		<%-- set hasValue=true if any param for the fieldname matches optionVal --%>
		<c:set var="hasValue" value="false" />
		<c:set var="name" value="attribute_${attIdString}" />

		<c:forEach var="paramValue" items="${paramValues[name]}">
			<c:if test="${optionVal == paramValue}"><c:set var="hasValue" value="true" /></c:if> 
		</c:forEach>
	</c:otherwise>
</c:choose>