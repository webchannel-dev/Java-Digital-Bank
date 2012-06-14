<c:if test="${showLength}">
	<label for="maxDisplayLen">Max display length?:</label>
	<input type="text" name="displayLengthString" class="small" id="maxDisplayLen" value="<c:if test='${displayAttributeForm.displayAttribute.displayLength > 0}'><bean:write name='displayAttributeForm' property='displayAttribute.displayLength'/></c:if>"/>
	<br />
</c:if>
	
<c:if test="${showLabel}">
	<label for="showLabel">Show attribute label?:</label><html:checkbox name="displayAttributeForm" property="displayAttribute.showLabel" styleId="showLabel" styleClass="checkbox"/> 
	
	<br />
</c:if>
	
<c:if test="${showLink}">
	<label for="isLink">Is Link?:</label>
	<html:checkbox name="displayAttributeForm" property="displayAttribute.isLink" styleId="isLink" styleClass="checkbox"/>
	<br />
</c:if>

<%--  Only show if enabled (and available for the current groupId) and attribute is not static --%> 
<c:if test="${showOnChild && assetEntitiesEnabled && useParentMetadata && !displayAttributeForm.displayAttribute.attribute.static}">
	
	<label for="showOnChild">Show on child?:</label>
	<html:checkbox name="displayAttributeForm" property="displayAttribute.showOnChild" styleClass="checkbox" styleId="showOnChild" />
	<span><em>Note this only takes effect if the Attribute is also marked as 'Show on child' on the Edit Attribute screen. </em></span>
	<br />
</c:if>