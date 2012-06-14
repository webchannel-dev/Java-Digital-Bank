<logic:notEmpty name="categories">
	<logic:notEmpty name="categories" property="categories">
		<logic:iterate name="categories" property="categories" id="cat">
			<logic:notEqual name="cat" property="depth" value="0">
				<bean:define id="catId" name="cat" property="id"/>
				<bean:define id="hasValue" name="filterForm" property="<%= \"filter.isLinkedToCategory(\" + catId + \")\" %>"/>
				<c:if test="${hasValue}">
					<c:set var="matchedCat" value="true"/>
				</c:if>
				<c:if test="${cat.depth > 1}"><c:forEach begin="2" end="${cat.depth}" step="1">&nbsp;&nbsp;&nbsp;</c:forEach></c:if><input type="checkbox" name="linkedToCat<bean:write name='cat' property='id'/>" id="linkedToCat<bean:write name='cat' property='id'/>" class="checkbox" value="<bean:write name='cat' property='id'/>" <c:if test="${hasValue}">checked</c:if>/><label for="linkedToCat<bean:write name='cat' property='id'/>"><bean:write name="cat" property="name"/></label><br/>
			</logic:notEqual>
		</logic:iterate>
	</logic:notEmpty>
</logic:notEmpty>