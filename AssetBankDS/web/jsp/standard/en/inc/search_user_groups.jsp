<bright:applicationSetting id="enableSearchByGroup" settingName="enable-search-by-group"/>

<logic:notPresent name="isBatchOperation">
	<bean:define id="isBatchOperation" value="false"/>
</logic:notPresent>

<c:if test="${userprofile.isAdmin && enableSearchByGroup && bIsSearch && !isBatchOperation}">
	<div class="hr">
		<br />
		<p><b>Assets visible to groups:</b></p>
		<bright:refDataList componentName="UserManager" methodName="getAllGroups" id="groups"/>
		<logic:present name="userprofile" property="searchCriteria">
			<bean:define id="grString" name="userprofile" property="searchCriteria.groupRestrictionString" />
		</logic:present>
		<logic:notPresent name="userprofile" property="searchCriteria">
			<bean:define id="grString" value="" />
		</logic:notPresent>
		<% String sGrString = String.valueOf(pageContext.getAttribute("grString")); %>
		
		<logic:iterate name='groups' id='group' indexId='index'>
			<bean:define id="groupId" name="group" property="id"/>
			<% String sGroupId = String.valueOf(pageContext.getAttribute("groupId")); %>
			<input type="checkbox" name="visibleToGroup<bean:write name='index'/>" id="group<bean:write name='index'/>" value="<bean:write name='groupId'/>" <c:if test='${searchForm.refineSearch}'><% if (sGrString.contains(","+sGroupId+",")) { %>checked<% } %></c:if> /> <label for="group<bean:write name='index'/>"><bean:write name='group' property='name' /></label><br/> 
		</logic:iterate>
	</div>
</c:if>
	