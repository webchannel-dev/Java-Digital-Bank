<bright:applicationSetting id="noActiveDirectory" settingName="suspend-ad-authentication" />
<bright:applicationSetting id="useDivisions" settingName="users-have-divisions" />
<bright:applicationSetting id="useOrganisation" settingName="users-have-organisation" />
<bright:applicationSetting id="useJobTitle" settingName="users-have-job-title" />
<bright:applicationSetting id="useTitle" settingName="users-have-job-title" />
<bright:applicationSetting id="useStructuredAddress" settingName="users-have-structured-address" />
<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>

<%-- Pass in showAddressFields --%>


	<%-- Hidden fields to preserve non-editable fields --%>
	
		<html:hidden name="userForm" property="user.id"/>
		<html:hidden name="userForm" property="user.refDivision.id"/>
		<html:hidden name="userForm" property="user.username"/>
		<html:hidden name="userForm" property="user.forename"/>
		<html:hidden name="userForm" property="user.surname"/>
		<html:hidden name="userForm" property="user.organisation"/>
		<html:hidden name="userForm" property="user.emailAddress"/>
		<html:hidden name="userForm" property="user.remoteUser"/>
		<html:hidden name="userForm" property="user.registrationInfo"/>
		<html:hidden name="userForm" property="user.displayName"/>
		<html:hidden name="userForm" property="user.jobTitle"/>
		<html:hidden name="userForm" property="user.homeAddress.addressLine1"/>
		<html:hidden name="userForm" property="user.homeAddress.addressLine2"/>
		<html:hidden name="userForm" property="user.homeAddress.town"/>
		<html:hidden name="userForm" property="user.homeAddress.county"/>
		<html:hidden name="userForm" property="user.homeAddress.postcode"/>
		<html:hidden name="userForm" property="user.homeAddress.country.id"/>
		<html:hidden name="userForm" property="user.address"/>
		<html:hidden name="userForm" property="user.title"/>
		<html:hidden name="userForm" property="user.language.id"/>
		
		
		<%-- Add id as hidden field in case there is an error and we have to come back to the form --%>
		<input type="hidden" name="id" value="<c:out value='${userForm.user.id}' />" /> 


			<h3>User Details</h3>
			<table cellspacing="0" class="form" summary="Form for user details">
				<c:if test="${!noActiveDirectory}">
				<tr>
					<th>Authentication:</th>
					<td class="padded">
						<logic:equal name="userForm" property="user.remoteUser" value="true">
							Active Directory
						</logic:equal>
						<logic:equal name="userForm" property="user.remoteUser" value="false">
							Not Active Directory
						</logic:equal>
					</td>
				</tr>
				</c:if>
				<tr>
					<th><bright:cmsWrite identifier="label-username" filter="false"/></th>
					<td class="padded">
						<bean:write name="userForm" property="user.username"/>
					</td>
				</tr>			
				<tr>
					<th><bright:cmsWrite identifier="label-email" filter="false"/></th>
					<td class="padded">
						<bean:write name="userForm" property="user.emailAddress"/>
					</td>
				</tr>			
			<c:if test="${useTitle}">																
				<tr>
					<th><label for="title"><bright:cmsWrite identifier="label-title" filter="false"/></label></th>
					<td class="padded">
						<bean:write name="userForm" property="user.title"/>
					</td>													
				</tr>
			</c:if>
				<tr>
					<th><bright:cmsWrite identifier="label-forename" filter="false"/></th>
					<td class="padded">
						<bean:write name="userForm" property="user.forename"/>
					</td>
				</tr>	
				<tr>
					<th><bright:cmsWrite identifier="label-surname" filter="false"/></th>
					<td class="padded">
						<bean:write name="userForm" property="user.surname"/>
					</td>
				</tr>	
				
			<c:if test="${useOrganisation}">
				<tr>
					<th><bright:cmsWrite identifier="label-organisation" filter="false"/></th>
					<td class="padded">
						<bean:write name="userForm" property="user.organisation"/>
					</td>
				</tr>
			</c:if>
			
			<c:if test="${useDivisions}">
				<tr>
					<th><bright:cmsWrite identifier="label-division" filter="false"/></th>
					<td class="padded">
						<bean:write name="userForm" property="user.refDivision.description"/>
					</td>
				</tr>		
			</c:if>

			<c:if test="${useJobTitle}">																
				<tr>
					<th><bright:cmsWrite identifier="label-job-title" filter="false"/></th>
					<td class="padded">
						<bean:write name="userForm" property="user.jobTitle"/>
					</td>
				</tr>
			</c:if>
			
			<c:if test="${supportMultiLanguage}">																
				<tr>
					<th><bright:cmsWrite identifier="label-language" filter="false"/></th>
					<td class="padded">
						<bean:write name="userForm" property="user.language.name"/>
					</td>
				</tr>
			</c:if>

			<c:if test="${showAddressFields}">
				<c:choose>
					<c:when test="${useStructuredAddress}">
						<tr>
							<th><label for="address1"><bright:cmsWrite identifier="label-address1" filter="false"/></label></th>
							<td class="padded">
								<bean:write name="userForm" property="user.homeAddress.addressLine1"/>
							</td>
						</tr>		
						<tr>
							<th><label for="address2"><bright:cmsWrite identifier="label-address2" filter="false"/></label></th>
							<td class="padded">
								<bean:write name="userForm" property="user.homeAddress.addressLine2"/>
							</td>
						</tr>			
						<tr>
							<th><label for="town"><bright:cmsWrite identifier="label-town" filter="false"/></label></th>
							<td class="padded">
								<bean:write name="userForm" property="user.homeAddress.town"/>
							</td>
						</tr>			
						<tr>
							<th><label for="county"><bright:cmsWrite identifier="label-county" filter="false"/></label></th>
							<td class="padded">
								<bean:write name="userForm" property="user.homeAddress.county"/>
							</td>
						</tr>			
						<tr>
							<th><label for="postcode"><bright:cmsWrite identifier="label-postcode" filter="false"/></label></th>
							<td class="padded">
								<bean:write name="userForm" property="user.homeAddress.postcode"/>
							</td>
						</tr>				
						<tr>
							<th><label for="country"><bright:cmsWrite identifier="label-country" filter="false"/></label></th>
							<td class="padded">
								<bean:write name="userForm" property="user.homeAddress.country.name"/>
							</td>
						</tr>		
					</c:when>
					<c:otherwise>
						<tr>
							<th><label for="address"><bright:cmsWrite identifier="label-address" filter="false"/></label></th>
							<td class="padded">
								<bean:write name="userForm" property="user.address"/>
							</td>
						</tr>						
					</c:otherwise>
				</c:choose>

			</c:if>

			<logic:notEmpty name="userForm" property="customFields">
				<bean:define id="customFields" name="userForm" property="customFields"/>
				<bean:define id="selectedValueSet" name="userForm" property="user.customFieldValues"/>
				<bean:define id="readOnly" value="true"/>
				<c:set var="isCustomFieldSubtype" value="${not userForm.user.isLocalUser || userprofile.isAdmin}"/>
				<%@include file="../inc/custom_fields.jsp"%>
			</logic:notEmpty>
