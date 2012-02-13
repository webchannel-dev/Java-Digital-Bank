<div class="formFields secondary" >
	
	<logic:iterate name="downloadForm" property="secondaryUsageTypes" id="usageType" indexId="index">
		
		<%-- If this is the selected usage type then hide it --%>	
		<c:if test="${usageType.id != downloadForm.selectedUsageType.id}">
			<label class="wrapping">	
				<logic:empty name="usageType" property="children">
					<input type="checkbox" name="secondary_<c:out value='${usageType.id}'/>" id="secondary_<c:out value='${usageType.id}'/>" value="secondary_<c:out value='${usageType.id}'/>" <c:if test="${usageType.selected}">checked</c:if>/>
				</logic:empty>		
					
				<bean:write name="usageType" property="description" filter="false"/>
			</label>	
		</c:if>
		
		<logic:iterate name="usageType" property="children" id="childUsageType">
			<c:if test="${childUsageType.id != downloadForm.selectedUsageType.id}">
				<label class="wrapping">
					&nbsp;&nbsp;&nbsp;<input type="checkbox" name="secondary_<c:out value='${childUsageType.id}'/>" id="secondary_<c:out value='${childUsageType.id}'/>" value="secondary_<c:out value='${childUsageType.id}'/>" <c:if test="${childUsageType.selected}">checked</c:if>/>
					<bean:write name="childUsageType" property="description" filter="false"/>
				</label>
			</c:if>
		</logic:iterate>
			
	</logic:iterate>	
	
	<bright:cmsWrite identifier="snippet-secondary-usage-note" filter="false"/>
</div>