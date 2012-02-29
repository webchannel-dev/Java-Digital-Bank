
	<label for="maxDisplayLen">Max display length?:</label>
	<input type="text" name="displayLengthString" id="maxDisplayLen" value="<c:if test='${displayAttributeForm.displayAttribute.displayLength > 0}'><bean:write name='displayAttributeForm' property='displayAttribute.displayLength'/></c:if>"/>
	<br />

	<label for="showLabel">Show label?:</label>
	<html:checkbox name="displayAttributeForm" property="displayAttribute.showLabel" styleId="showLabel" styleClass="checkbox"/>
	<br />

	<label for="isLink">Is Link?:</label>
	<html:checkbox name="displayAttributeForm" property="displayAttribute.isLink" styleId="isLink" styleClass="checkbox"/>
	<br />

<%--  Only show if enabled and attribute is not static --%> 
<c:if test="${assetEntitiesEnabled && useParentMetadata && !displayAttributeForm.displayAttribute.attribute.static}">
	
	<label for="showOnChild">Show on child?:</label>
	<html:checkbox name="displayAttributeForm" property="displayAttribute.showOnChild" styleClass="checkbox" styleId="showOnChild" />
	<span><em>Note this only takes effect if the Attribute is also marked as 'Show on child' on the Edit Attribute screen. </em></span>
	<br />
</c:if>