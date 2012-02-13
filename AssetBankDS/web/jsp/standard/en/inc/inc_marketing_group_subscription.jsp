<bean:size name="marketingGroupForm" property="marketingGroups" id="numGroups"/>
<bright:applicationSetting id="marketingGroupsMandatory" settingName="marketing-groups-mandatory"/>
<c:if test="${numGroups>0}">
	<tr>
		<th style="padding-top: 10px;"><bright:cmsWrite identifier="label-subscriptions" filter="false"/><c:if test="${marketingGroupsMandatory == true}"><span class="required">*</span></c:if></th>
		<td colspan="2">
			<html:hidden name="marketingGroupForm" property="populatedViaReload" value="true"/>
			<table cellpadding="0" cellspacing="0" id="marketingGroups" >
				<bean:size name="marketingGroupForm" property="marketingGroups" id="numGroups"/>
				<logic:iterate name="marketingGroupForm" property="marketingGroups" id="group" indexId="groupIndex">
					<bean:define id="groupId" name="group" property="id" />
					<tr class="
						<c:if test="${marketingGroupForm.marketingGroupsHaveDescriptions}">group</c:if> 
						<c:if test="${marketingGroupForm.marketingGroupsHaveDescriptions && empty group.description && groupIndex==numGroups-1}">lastGroup</c:if>
					">
						<td class="checkbox">
							<html:multibox name="marketingGroupForm" property="marketingGroupIds" styleClass="checkbox" styleId="<%=\"m_groups_\"+groupId%>">
								<bean:write name="group" property="id" />
							</html:multibox>
						</td>
						<td>
							<label for="m_groups_<bean:write name="group" property="id" />"><bean:write name="group" property="name"/></label>
							<c:if test="${marketingGroupForm.marketingGroupsHaveDescriptions && not empty group.description}">
								<br />
								<em><bright:writeWithCR name="group" property="description" filter="false"/></em>
							</c:if>
						</td>
					</tr>

				</logic:iterate>
			</table>
		</td>
	</tr>
</c:if>