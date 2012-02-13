<logic:notPresent name="searchForm">
	<bean:define id="refineSearch" value="false"/>
</logic:notPresent>

<logic:present name="searchForm">
	<bean:define id="refineSearch" name="searchForm" property="refineSearch"/>
</logic:present>

<c:choose>
	<c:when test="${refineSearch}">
		<bean:define id="optionVal" name="optionValue" property="value"/>
		<bean:define id="hasValue" name="userprofile" property="<%= \"searchCriteria.attributeHasValue(\" + attributeId + \"%%\" + optionVal + \")\" %>"/>
	</c:when>
	<c:otherwise>
		<logic:present name="currentFilter">
			<bean:define id="optionVal" name="optionValue" property="value"/>
			<bean:define id="hasValue" name="currentFilter" property="<%= \"attributeHasValue(\" + attributeId + \"%%\" + optionVal + \")\" %>"/>
		</logic:present>
		<logic:notPresent name="currentFilter">
			<bean:define id="hasValue" value="false"/>
		</logic:notPresent>
	</c:otherwise>
</c:choose>