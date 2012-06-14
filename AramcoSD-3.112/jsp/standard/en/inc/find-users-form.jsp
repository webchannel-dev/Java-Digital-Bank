	<bean:parameter id="orderId" name="orderId" value="1"/>								

	<h3><bright:cmsWrite identifier="subhead-find-users" filter="false"/></h3>
	<table cellspacing="0" style="width:auto; margin-bottom:1em;"  class="admin" summary="Form for searching for users">
		<tr>
			<th><label for="forename"><bright:cmsWrite identifier="label-forename" filter="false"/></label></th>
			<th><label for="surname"><bright:cmsWrite identifier="label-surname" filter="false"/></label></th>
			<th><label for="username"><bright:cmsWrite identifier="label-username" filter="false" replaceVariables="true" /></label></th>
			<th><label for="email"><bright:cmsWrite identifier="label-email" filter="false" replaceVariables="true" /></label></th>
			<c:if test="${usersHaveStructuredAddress}">
				<th><label for="country"><bright:cmsWrite identifier="label-country" filter="false" replaceVariables="true" /></label></th>
			</c:if>
			<c:if test="${!findUsersForm.hideAndPreSelectGroups && userprofile.isAdmin}">
				<th><label for="group"><bright:cmsWrite identifier="label-user-group" filter="false"/></label></th>
			</c:if>
		</tr>
		<tr>
			<td>
				<input type="text" class="text" id="forename" name="searchCriteria.forename" maxlength="50" size="12" value="<bean:write name='findUsersForm' property='searchCriteria.forename' filter='false'/>">
			</td>
			<td>
				<input type="text" class="text" id="surname" name="searchCriteria.surname" maxlength="50" size="12" value="<bean:write name='findUsersForm' property='searchCriteria.surname' filter='false'/>">
			</td>		
			<td>
				<input type="text" class="text" id="username" name="searchCriteria.username" maxlength="50" size="12" value="<bean:write name='findUsersForm' property='searchCriteria.username' filter='false'/>">			
			</td>
			<td>
				<input type="text" class="text" id="email" name="searchCriteria.emailAddress" maxlength="50" size="12" value="<bean:write name='findUsersForm' property='searchCriteria.emailAddress' filter='false'/>">			
			</td>
			<c:if test="${usersHaveStructuredAddress}">
				<td>
					<bean:define id="countries" name="findUsersForm" property="countries"/>
					<html:select styleId="country" name="findUsersForm" property="searchCriteria.countryId" size="1">
						<option value="0">[<bright:cmsWrite identifier="snippet-any" filter="false"/>]</option>
						<html:options collection="countries" property="id" labelProperty="name" filter="false"/>
					</html:select>
				</td>
			</c:if>
			<c:choose>
				<c:when test="${findUsersForm.hideAndPreSelectGroups}">
					<logic:notEmpty name="findUsersForm" property="groups">
						<logic:iterate name="findUsersForm" property="groups" id="group">
							<input type="hidden" name="searchCriteria.groupIdsFromArray" value="<c:out value='${group.id}' />" />
						</logic:iterate>
					</logic:notEmpty>
					<logic:empty name="findUsersForm" property="groups">
						<input type="hidden" name="searchCriteria.groupIdsFromArray" value="-1" />
					</logic:empty>
				</c:when>
				<c:otherwise>
					<c:if test="${userprofile.isAdmin}">
						<td>
							<bean:define name="findUsersForm" property="groups" id="groups"/>
							<html:select name="findUsersForm" property="searchCriteria.groupId" styleId="group">
								<option value="0">[<bright:cmsWrite identifier="snippet-any" filter="false"/>]</option>
								<html:options collection="groups" property="id" labelProperty="nameWithOrgUnit" filter="false"/>
							</html:select>
						</td>
					</c:if>
				</c:otherwise>
			</c:choose>
		</tr>
	</table>	
	
	<c:if test="${userprofile.isAdmin && marketingGroupsEnabled && not empty findUsersForm.marketingGroups}">
		<table cellspacing="0" style="width:auto; margin-bottom:14px;" class="admin" summary="Form for searching for users">
			<tr>
				<th>
					<label for="group">Marketing Group Subscriptions:</label>
				</th>
			</tr>
			<tr>
				<td>
					
					<logic:iterate name="findUsersForm" property="marketingGroups" id="group" indexId="groupIndex">
						<bean:define id="groupId" name="group" property="id" />
						<html:multibox name="findUsersForm" property="searchCriteria.marketingGroupIds" styleClass="checkbox" styleId="<%=\"share_asset_groups_\"+groupId%>">
							<bean:write name="group" property="id" />
						</html:multibox>
						<label for="share_asset_groups_<bean:write name="group" property="id" />"><bean:write name="group" property="name" /></label>
						<br/>
					</logic:iterate>
					
				</td>
			</tr>
		</table>
	</c:if>	