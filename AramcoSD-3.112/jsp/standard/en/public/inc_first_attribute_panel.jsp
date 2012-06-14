<bean:define name="asset" property="firstAttributeValue" id="firstAttributeValue"/>
<c:if test="${not empty firstAttributeValue && not empty firstAttributeValue.value && firstAttributeValue.attribute.isVisible}">
	<div class="firstAttributePanel-<bean:write name="firstAttributePosition"/>">
		<c:if test="${firstAttributeShowLabel && firstAttributeValue.attribute.isTextarea}">
			<div class="firstAttributePanelLabel-<bean:write name="firstAttributePosition"/>"><bean:write name="firstAttributeValue" property="attribute.label" filter="false"/></div>
		</c:if>
		<c:if test="${firstAttributeShowLabel && !firstAttributeValue.attribute.isTextarea}">
			<span class="firstAttributePanelLabel-<bean:write name="firstAttributePosition"/>"><bean:write name="firstAttributeValue" property="attribute.label" filter="false"/>:</span>
		</c:if>
		<c:choose>
			<c:when test="${firstAttributeValue.additionalValue != null && firstAttributeValue.additionalValue != ''}">
				<bean:write name="firstAttributeValue" property="additionalValue" filter="false"/>
			</c:when>
			<c:otherwise>
				<bean:write name="firstAttributeValue" property="valueHTML" filter="false"/>
			</c:otherwise>
		</c:choose>
	</div>
</c:if>