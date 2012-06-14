<logic:present name="currentFilter">
	<bean:define id="optionVal" name="optionValue" property="value"/>
	<bean:define id="hasValue" name="currentFilter" property="<%= "attributeHasValue(" + attributeId + "%%" + optionVal + ")" %>"/>
</logic:present>
<logic:notPresent name="currentFilter">
	<bean:define id="hasValue" value="false"/>
</logic:notPresent>