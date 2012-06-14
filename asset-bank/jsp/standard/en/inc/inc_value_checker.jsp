<!-- Note: There are two of these files - believe one is used by advanced search and one by filter.
This one seems to be used by the search only.

Should either be refactored into a single file or renamed and made more specific.
E.g. remove the filter handling code from this one and the search handling code from the other.
-->
<bean:define id="optionVal" name="optionValue" property="value"/>
<bean:define id="optionId" name="optionValue" property="id"/>
<c:choose>
	<c:when test="${searchForm.refineSearch}">
		<bean:define id="hasValue" name="userprofile" property="<%= \"searchCriteria.attributeHasValue(\" + attributeId + \"%%\" + optionId + \")\" %>"/>
	</c:when>
	<c:when test="${filterValue != null && filterValue.value != null}">
		<bean:define id="hasValue" name="currentFilter" property="<%= \"attributeHasValue(\" + attributeId + \"%%\" + optionId + \")\" %>"/>
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