
<%-- History:
	 d1		Martin Wilson	02-Aug-2005		Created
	 d2		Ben Browning	21-Feb-2006		HTML/CSS tidy up
--%>

	<table cellspacing="0" class="form" summary="Form for user permissions">
		
		<%-- Org unit admins cannot make super-users --%>
		<c:if test="${userprofile.isAdmin}">
			<tr>
				<th><label for="isAdmin">Admin User?:</label></th>
				<td>
					<html:checkbox name="userForm" property="user.isAdmin" styleClass="checkbox" styleId="isAdmin" />
				</td>
				<td class="padded">
					*Admin users can manage all aspects of the image bank (including other users and categories) and because an admin user has read/write permission to all categories then a user marked as admin does not need to be placed in groups to define their permissions.
				</td>
			</tr>
			<tr>
				<th><label for="receiveAlerts">Receives alerts:</label></th>
				<td>
					<html:checkbox name="userForm" property="user.receiveAlerts" styleClass="checkbox" styleId="receiveAlerts" />
				</td>
				<td class="padded">
				If this user is an admin user or an approver, check this if you want them to receive alerts.
				</td>
			</tr>		
		</c:if>

		<tr>
			<th><label for="">User in Groups:</label></th>
			<td colspan="2">
				<%-- the 'normal' group is hidden and selected by default --%>
				<input type="hidden" name="group-1" value="1">
						
				<logic:iterate name="userForm" property="groups" id="group" indexId="index">
					<c:choose>
						<c:when test="${group.id==1}">
							<%-- Always shown above so don't show again --%>
							<input type="checkbox" class="checkbox" name="group0" value="1" checked="checked" disabled="disabled" id="group<bean:write name='group' property='id'/>" />
							&nbsp;<bean:write name="group" property="nameWithOrgUnit"/><br />
						</c:when>
						<c:when test="${group.id==2}">
							<%-- the 'public' group cannot be selected --%>
						</c:when>
						<c:otherwise>
							<!-- check if the current group should be selected or not -->
							<logic:match name="userForm" property="user.isInGroup[${group.id}]" value="true">
								<input type="checkbox" class="checkbox" name="group<bean:write name='index'/>" checked="checked" value="<bean:write name='group' property='id'/>" id="group<bean:write name='group' property='id'/>" />
							</logic:match>
							<logic:notMatch name="userForm" property="user.isInGroup[${group.id}]" value="true">
								<logic:equal name="userForm" property="hasErrors" value="true">
									<logic:match name="userForm" property="selectedGroup[${group.id}]" value="true">
										<input type="checkbox" class="checkbox" name="group<bean:write name='index'/>" checked="checked" value="<bean:write name='group' property='id'/>" id="group<bean:write name='group' property='id'/>" />
									</logic:match>
									<logic:notMatch name="userForm" property="selectedGroup[${group.id}]" value="true">
										<input type="checkbox" class="checkbox" name="group<bean:write name='index'/>" value="<bean:write name='group' property='id'/>" id="group<bean:write name='group' property='id'/>" />
									</logic:notMatch>
								</logic:equal>
								<logic:notEqual name="userForm" property="hasErrors" value="true">
									<input type="checkbox" class="checkbox" name="group<bean:write name='index'/>" value="<bean:write name='group' property='id'/>" id="group<bean:write name='group' property='id'/>" />
								</logic:notEqual>
							</logic:notMatch>
					
							&nbsp;<bean:write name="group" property="nameWithOrgUnit"/><br />

						</c:otherwise>
					</c:choose>
				</logic:iterate>
			</td>
		</tr>
	</table>	
