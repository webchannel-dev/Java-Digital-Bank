<%-- Show tint options if enabled --%>
<c:if test="${numTints>0}">
	<label for="tint" <c:if test="${!advanced}">class="narrow"</c:if>><bright:cmsWrite identifier="label-tint" filter="false"/></label>
	
	<%--onchange="updateCrops(this.value); clearCrop(); updateLayer();"--%>
	<select name="tint" id="tint">
		<option value="" <c:if test="${tint.name == downloadForm.tint}">selected</c:if>>None</option>
		<logic:iterate name="downloadForm" property="tints" id="tint">
			<option value="<bean:write name='tint' property='name'/>" <c:if test="${tint.name == downloadForm.tint}">selected</c:if>><bean:write name="tint" property="name" filter="false"/></option>
		</logic:iterate> 
	</select>
	<br />
</c:if>