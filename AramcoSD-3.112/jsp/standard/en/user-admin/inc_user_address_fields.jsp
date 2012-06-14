
	<bright:applicationSetting id="useStructuredAddress" settingName="users-have-structured-address" />
	<bright:applicationSetting id="strictValidationSettingAddress" settingName="users-register-strict-address"/>

	<bright:refDataList id="labelAddress1" componentName="ListManager" methodName="getListItem" argumentValue="label-address1"/>	
	<bright:refDataList id="labelAddress2" componentName="ListManager" methodName="getListItem" argumentValue="label-address2"/>	
	<bright:refDataList id="labelTown" componentName="ListManager" methodName="getListItem" argumentValue="label-town"/>	
	<bright:refDataList id="labelCounty" componentName="ListManager" methodName="getListItem" argumentValue="label-county"/>	
	<bright:refDataList id="labelPostcode" componentName="ListManager" methodName="getListItem" argumentValue="label-postcode"/>	
	
	<%-- Use strictValidation flag if passed in, otherwise use the setting --%>
	<c:if test="${!strictValidation}">
		<c:set var="strictValidationAddress" value="${strictValidationSettingAddress}" />
	</c:if>
	
	<c:if test="${strictValidationSettingAddress}">
		<c:set var="strictValidationAddress" value="${strictValidationSettingAddress}" />
	</c:if>
	
		<c:choose>
		<c:when test="${useStructuredAddress}">
			<html:hidden name="userForm" property="user.homeAddress.id" />
			
		<logic:notEmpty name="labelAddress1">
			<tr>
				<th valign="top"><label for="address1"><bright:write name="labelAddress1" property="body" filter="false"/></label><c:if test="${strictValidationAddress}"><span class="required">*</span></c:if></th>
				<td colspan="2">
					<input type="text" class="text" id="address1" name="user.homeAddress.addressLine1" size="16" maxlength="200" value="<bean:write name="userForm" property="user.homeAddress.addressLine1" filter="false"/>"/>
				</td>
				<c:if test="${strictValidationAddress}"><input type="hidden" name="mandatory_user.homeAddress.addressLine1" value="Please enter a value for <bright:cmsWrite identifier="label-address1" filter="false"/>." /></c:if>
			</tr>		
		</logic:notEmpty>
		<logic:notEmpty name="labelAddress2">			
			<tr>
				<th valign="top"><label for="address2"><bright:write name="labelAddress2" property="body" filter="false"/></label></th>
				<td colspan="2">
					<input type="text" class="text" id="address2" name="user.homeAddress.addressLine2" size="16" maxlength="200" value="<bean:write name="userForm" property="user.homeAddress.addressLine2" filter="false"/>"/>
				</td>
			</tr>			
		</logic:notEmpty>
		<logic:notEmpty name="labelTown">			
			<tr>
				<th valign="top"><label for="town"><bright:write name="labelTown" property="body" filter="false"/></label><c:if test="${strictValidationAddress}"><span class="required">*</span></c:if></th>
				<td colspan="2">
					<input type="text" class="text" id="town" name="user.homeAddress.town" size="16" maxlength="100" value="<bean:write name="userForm" property="user.homeAddress.town" filter="false"/>"/>
				</td>
				<c:if test="${strictValidationAddress}"><input type="hidden" name="mandatory_user.homeAddress.town" value="Please enter a value for <bright:cmsWrite identifier="label-town" filter="false"/>." /></c:if>
			</tr>			
		</logic:notEmpty>
		<logic:notEmpty name="labelCounty">			
			<tr>
				<th valign="top"><label for="county"><bright:write name="labelCounty" property="body" filter="false"/></label><c:if test="${strictValidationAddressCounty}"><span class="required">*</span></c:if></th>
				<td colspan="2">
					<input type="text" class="text" id="county" name="user.homeAddress.county" size="16" maxlength="100" value="<bean:write name="userForm" property="user.homeAddress.county" filter="false"/>"/>
				</td>
				<c:if test="${strictValidationAddressCounty}"><input type="hidden" name="mandatory_user.homeAddress.county" value="Please enter a value for <bright:cmsWrite identifier="label-county" filter="false"/>." /></c:if>
			</tr>			
		</logic:notEmpty>
		<logic:notEmpty name="labelPostcode">			
			<tr>
				<th valign="top"><label for="postcode"><bright:write name="labelPostcode" property="body" filter="false"/></label><c:if test="${strictValidationAddress}"><span class="required">*</span></c:if></th>
				<td colspan="2">
					<input type="text" class="text" id="postcode" name="user.homeAddress.postcode" size="16" maxlength="25" value="<bean:write name="userForm" property="user.homeAddress.postcode" filter="false"/>"/>
				</td>
			</tr>	
			<c:if test="${strictValidationAddress}"><input type="hidden" name="mandatory_user.homeAddress.postcode" value="Please enter a value for <bright:cmsWrite identifier="label-postcode" filter="false"/>." /></c:if>
		</logic:notEmpty>

			<tr>
				<th valign="top"><label for="country"><bright:cmsWrite identifier="label-country" filter="false"/></label><span class="required">*</span></th>
				<td colspan="2">
					<bright:refDataList componentName="AddressManager" methodName="getCountryList" id="countryList"/>
					<html:select name="userForm" property="user.homeAddress.country.id">
						<html:option value="">-- Please select --</html:option>
						<html:optionsCollection name="countryList" value="id" label="name"/>
					</html:select>									
				</td>
				<input type="hidden" name="mandatory_user.homeAddress.country.id" value="Please enter a value for <bright:cmsWrite identifier="label-country" filter="false"/>." />
			</tr>		
			<html:hidden name="userForm" property="user.address" />			
		</c:when>
		<c:otherwise>
			<tr>
				<th valign="top"><label for="address"><bright:cmsWrite identifier="label-address" filter="false"/></label></th>
				<td colspan="2">
					<input type="hidden" name="maxlength_formUser.address" value="2000" />
					<input type="hidden" name="maxlengthmessage_formUser.address" value="The address field has exceeded its 2000 character maximum length." />
					<textarea class="text" id="address" name="user.address" rows="4" cols="40" style="width: 200px;"><bean:write name="userForm" property="user.address" filter="false" /></textarea>
				</td>
			</tr>						
		</c:otherwise>
	</c:choose>