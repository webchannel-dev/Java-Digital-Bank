<%--  Steve 15-Mar-2010: No longer used by advanced search, so probably doesn't need the refineSearch check --%>

<!-- Note: There are two of these files - believe one is used by advanced search and one by filter.
This one seems to be used by the filter only (see above comment).

Should either be refactored into a single file or renamed and made more specific.
E.g. remove the search handling code from this one and the filter handling code from the other.
-->
<logic:notPresent name="searchForm">
	<bean:define id="refineSearch" value="false"/>
</logic:notPresent>

<logic:present name="searchForm">
	<bean:define id="refineSearch" name="searchForm" property="refineSearch"/>
</logic:present>

<c:choose>
	<c:when test="${refineSearch}">
		<bean:define id="optionId" name="optionValue" property="id"/>
		<bean:define id="hasValue" name="userprofile" property="<%= \"searchCriteria.attributeHasValue(\" + attributeId + \"%%\" + optionId + \")\" %>"/>
	</c:when>
	<c:otherwise>
		<logic:present name="currentFilter">
			<bean:define id="optionId" name="optionValue" property="id"/>
			<bean:define id="hasValue" name="currentFilter" property="<%= \"attributeHasValue(\" + attributeId + \"%%\" + optionId + \")\" %>"/>
		</logic:present>
		<logic:notPresent name="currentFilter">
			<bean:define id="hasValue" value="false"/>
		</logic:notPresent>
	</c:otherwise>
</c:choose>