		
			<bright:applicationSetting id="useTitle" settingName="users-have-title" />
			<bright:applicationSetting id="useTaxNumber" settingName="users-have-tax-number" />
			<bright:applicationSetting id="enableCmsIntegration" settingName="enable-cms-integration" />
			<bright:applicationSetting id="cmsLoginRequiresPassword" settingName="cms-login-requires-password" />
			<bright:applicationSetting id="useStructuredAddress" settingName="users-have-structured-address" />
			<bright:applicationSetting id="useRegMessage" settingName="users-have-reg-message" />
			<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage" />
			<bright:applicationSetting id="remoteUsersCanEditName" settingName="remote-users-can-edit-name"/>
			<bright:applicationSetting id="daysBeforeMonths" settingName="days-before-months" />
			<bright:applicationSetting id="requestsEnabled" settingName="requests-enabled" />


		<c:choose>
			<c:when test="${changeProfilePage}">	
				<bright:applicationSetting id="showForenameOnProfile" settingName="show-forename-on-profile" />
				<bright:applicationSetting id="showSurnameOnProfile" settingName="show-surname-on-profile" />
				<bright:applicationSetting id="showOrganisationOnProfile" settingName="show-organisation-on-profile" />
				<bright:applicationSetting id="showAddressOnProfile" settingName="show-address-on-profile" />
			</c:when>
			<c:otherwise>
				<c:set var="showForenameOnProfile" value="true"/>
				<c:set var="showSurnameOnProfile" value="true"/>
				<c:set var="showOrganisationOnProfile" value="true"/>
				<c:set var="showAddressOnProfile" value="true"/>
			</c:otherwise>
		</c:choose>

			<c:if test="${useTitle}">																
				<tr>
					<th><label for="title"><bright:cmsWrite identifier="label-title" filter="false"/></label></th>
					<td colspan="2">
						<input type="text" class="text" id="title" name="user.title" maxlength="200" size="16" value="<bean:write name="userForm" property="user.title" />"/>
					</td>													
				</tr>
			</c:if>
			
			<c:choose>
				<c:when test="${showForenameOnProfile}">
					<tr>
						<c:choose>
							<c:when test="${!userForm.user.remoteUser || remoteUsersCanEditName}">	
								<th><label for="forename"><bright:cmsWrite identifier="label-forename" filter="false"/><span class="required">*</span></label></th>		
								<td>
									<input type="text" class="text" id="forename" name="user.forename" size="16" maxlength="100" value="<bean:write name="userForm" property="user.forename" />" />
									<input type="hidden" name="mandatory_user.forename" value="Please enter a forename." />
								</td>
								
							</c:when>
							<c:otherwise>
								<th><label for="forename"><bright:cmsWrite identifier="label-forename" filter="false"/></label></th>
								<td class="padded">
									<html:hidden name="userForm" property="user.forename" />
									<bean:write name="userForm" property="user.forename" />
								</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:when>
				<c:otherwise>
					<html:hidden name="userForm" property="user.forename" />
				</c:otherwise>
			</c:choose>
			
			<c:choose>
				<c:when test="${showSurnameOnProfile}">
					<tr>
						<c:choose>
							<c:when test="${!userForm.user.remoteUser || remoteUsersCanEditName}">
								<th><label for="surname"><bright:cmsWrite identifier="label-surname" filter="false"/><span class="required">*</span></label></th>		
								<td>
									<input type="text" class="text" id="surname" name="user.surname" size="16" maxlength="100" value="<bean:write name="userForm" property="user.surname" />"/>
									<input type="hidden" name="mandatory_user.surname" value="Please enter a surname." />
								</td>
								
							</c:when>
							<c:otherwise>
								<th><label for="surname"><bright:cmsWrite identifier="label-surname" filter="false"/></label></th>
								<td class="padded">
									<html:hidden name="userForm" property="user.surname" />
									<bean:write name="userForm" property="user.surname" />
								</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:when>
				<c:otherwise>
					<html:hidden name="userForm" property="user.surname" />
				</c:otherwise>
			</c:choose>
						
			<tr>
				<th>
					<input type="hidden" name="mandatory_user.emailAddress" value="Please enter an email address." />
					<label for="email"><bright:cmsWrite identifier="label-email" filter="false"/><span class="required">*</span></label></th>
				<td>
					<c:choose>
						<c:when test="${userprofile.isAdmin || !orgUnitAllUsers}">	
							<input type="text" class="text" id="email" name="user.emailAddress" size="16" maxlength="255" value="<bean:write name="userForm" property="user.emailAddress" />"/>	
						</c:when>
						<c:otherwise>
							<html:hidden name="userForm" property="user.emailAddress" />
							<bean:write name="userForm" property="user.emailAddress" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			
			
			<%@include file="inc_user_job_fields.jsp"%>
			
			<%@include file="inc_user_address_fields.jsp"%>


			<c:if test="${useTaxNumber}">																
				<tr>
					<th><label for="taxnumber"><bright:cmsWrite identifier="label-tax-number" filter="false"/></label></th>
					<td colspan="2">
						<input type="text" class="text" id="taxnumber" name="user.taxNumber" size="16" maxlength="100" value="<bean:write name="userForm" property="user.taxNumber" />"/>				
					</td>													
				</tr>
			</c:if>
			
			<c:if test="${useRegMessage}">																
				<tr>
					<th><label for="intendedUse"><bright:cmsWrite identifier="label-user-reg-message" filter="false"/></label><span class="required">*</span></th>
					<td colspan="2">
						<textarea name="user.registrationInfo" rows="3" cols="40"><bean:write name="userForm" property="user.registrationInfo" filter="false"/></textarea>
					</td>								
				</tr>
			</c:if>					
			
			<c:choose>
				<c:when test="${userprofile.isAdmin && enableCmsIntegration && !cmsLoginRequiresPassword}">			
					<tr>
						<th><label for="cmsLogin">Can login from CMS:</label></th>
						<td colspan="2">
							<html:checkbox name="userForm" property="user.canLoginFromCms" styleClass="checkbox" styleId="cmsLogin" />
						</td>													
					</tr>
				</c:when>
				<c:otherwise>
					<html:hidden name="userForm" property="user.canLoginFromCms" />
				</c:otherwise>
			</c:choose>

			<logic:notEmpty name="userForm" property="customFields">
				<bean:define id="customFields" name="userForm" property="customFields"/>
				<logic:present name="userForm" property="user.customFieldValues">
					<bean:define id="selectedValueSet" name="userForm" property="user.customFieldValues"/>
				</logic:present>
				<%-- The isCustomFieldSubtype variable should only be set for external users during registration --%>
				<c:set var="isCustomFieldSubtype" value="${(userForm.user.id<=0 && not userForm.user.isLocalUser) || userprofile.isAdmin}"/>
				<%@include file="../inc/custom_fields.jsp"%>
			</logic:notEmpty>
				
			<c:if test="${supportMultiLanguage}">	
				<bean:define name="userForm" property="languages" id="languages"/>
				<tr>
					<th><bright:cmsWrite identifier="label-language" filter="false"/><span class="required">*</span></th>
					<td colspan="2">
						<html:select name="userForm" styleId="language" property="user.language.id" style="width:auto;">
							<option value="0">- <bright:cmsWrite identifier="snippet-please-select" filter="false" /> -</option>
							<html:options collection="languages" property="id" labelProperty="nativeName" filter="false"/>
						</html:select>	
						<c:if test="${reloadOnChangeLanguage }">
							<input type="hidden" id="reloadPage" name="_reloadPage_" value="true"/>
							<html:submit style="margin-left:4px;" styleClass="button" styleId="reloadButton" property="reloadPage"><bright:cmsWrite identifier="button-go" filter="false"/></html:submit>
							<span id="buttonText"><bright:cmsWrite identifier="snippet-refreshes-subscriptions" filter="false"/></span>
							<script type="text/javascript">
								function changeLanguage()
								{
									document.getElementById('reloadPage').name='reloadPage';
									this.form.submit();
								}
								document.getElementById('language').onchange=changeLanguage;
								document.getElementById('language').style.width='';
								document.getElementById('reloadButton').style.display='none';
								document.getElementById('buttonText').style.display='none';
							</script>
							
						</c:if>
					</td>									
				</tr>
				<c:if test="${userprofile.canUpdateAtAll}">
					<tr>
						<th><bright:cmsWrite identifier="label-editable-languages"/>:</th>
						<td colspan="2">
							<c:forEach var="language" items="${languages}">
								<c:choose>
									<c:when test="${!language.suspended}">
										<label class="wrapping">
											<html:checkbox name="userForm" property="editLanguage(${language.id})"  disabled="${language.default}"/>
											<c:out value="${language.nativeName}"/> <c:if test="${language.default}">(<bright:cmsWrite identifier="snippet-default" filter="false"/>)</c:if>
										</label>
									</c:when>
									<c:otherwise>
										<html:hidden name="userForm" property="editLanguage(${language.id})" />
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</td>									
					</tr>
				</c:if>
			</c:if>


			<c:if test="${requestsEnabled}">
				<tr>
					<th><label for="requestFulfiller">Request fulfiller?:</label></th>
					<td colspan="2">
						<c:choose>
						<c:when test="${userprofile.isAdmin || !orgUnitAllUsers}">	
							<html:checkbox name="userForm" property="user.fulfiller" styleClass="checkbox" styleId="requestFulfiller" />
						</c:when>
						<c:otherwise>
							<html:hidden name="userForm" property="user.fulfiller" />
							<c:choose><c:when test="${userForm.user.fulfiller}"><bright:cmsWrite identifier="snippet-yes" /></c:when><c:otherwise><bright:cmsWrite identifier="snippet-no" /></c:otherwise></c:choose>
						</c:otherwise>
					</c:choose>
					</td>	
				</tr>
			</c:if>

