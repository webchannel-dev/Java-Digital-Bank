
	<bright:applicationSetting id="useDivisions" settingName="users-have-divisions" />
	<bright:applicationSetting id="useOrganisation" settingName="users-have-organisation" />
	<bright:applicationSetting id="useJobTitle" settingName="users-have-job-title" />
	<bright:applicationSetting id="strictValidationSettingJob" settingName="users-register-strict"/>
	<bright:applicationSetting id="mandatoryOrganisation" settingName="users-organisation-mandatory" />

	<%-- Use strictValidation flag if passed in, otherwise use the setting --%>
	<c:if test="${!strictValidation}">
		<c:set var="strictValidationJob" value="${strictValidationSettingJob}" />
	</c:if>

	<%-- Organisation is mandatory if strict or if organisation specifically mandatory --%>
	<c:set var="strictOrganisation" value="${strictValidationJob || mandatoryOrganisation}" />

				
	<c:if test="${useOrganisation}">																
		<tr>

			<th><label for="org"><bright:cmsWrite identifier="label-organisation" filter="false"/></label><c:if test="${strictOrganisation}"><span class="required">*</span></label></c:if></th>

			<td colspan="2">
				<input type="text" class="text" id="org" name="user.organisation" size="16" maxlength="200" value="<bean:write name="userForm" property="user.organisation" filter="false"/>"/>
			</td>													
		</tr>

		<c:if test="${strictOrganisation}"><input type="hidden" name="mandatory_user.organisation" value="Please enter the name of your <bright:cmsWrite identifier="label-organisation" filter="false"/>." /></c:if>

	</c:if>
	
	<c:if test="${useDivisions}">
		<logic:notEmpty 	name="userForm" property="divisionList">
			<bean:define id="divisions" name="userForm" property="divisionList" />
			<tr>						
				<th><label for="division"><bright:cmsWrite identifier="label-division" filter="false"/></label></th>
				<td colspan="2">
					<html:select name="userForm" property="user.refDivision.id" styleId="division" styleClass="text">
						<option value="0">- None -</option>
						<html:options collection="divisions" property="id" labelProperty="description"/>
					</html:select>	
				</td>	
			</tr>				
		</logic:notEmpty>		
	</c:if>
	
	<%-- Job title not strict --%>
	<c:if test="${useJobTitle}">																
		<tr>
			<th><label for="jobtitle"><bright:cmsWrite identifier="label-job-title" filter="false"/></label></th>
			<td colspan="2">
				<input type="text" class="text" id="jobtitle" name="user.jobTitle" size="16" maxlength="200" value="<bean:write name="userForm" property="user.jobTitle" filter="false"/>"/>
			</td>													
		</tr>
	</c:if>

