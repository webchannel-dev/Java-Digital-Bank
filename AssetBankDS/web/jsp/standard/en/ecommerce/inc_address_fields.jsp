


	<c:set var="strictValidation" value="true"/>

	<bright:refDataList id="labelAddress1" componentName="ListManager" methodName="getListItem" argumentValue="label-address1"/>	
	<bright:refDataList id="labelAddress2" componentName="ListManager" methodName="getListItem" argumentValue="label-address2"/>	
	<bright:refDataList id="labelTown" componentName="ListManager" methodName="getListItem" argumentValue="label-town"/>	
	<bright:refDataList id="labelCounty" componentName="ListManager" methodName="getListItem" argumentValue="label-county"/>	
	<bright:refDataList id="labelPostcode" componentName="ListManager" methodName="getListItem" argumentValue="label-postcode"/>	


		<html:hidden name="checkoutForm" property="shippingAddress.id" />
			
		<tr>
			<th valign="top"><label for="shippingname"><bright:cmsWrite identifier="label-name" filter="false"/></label><c:if test="${strictValidation}"><span class="required">*</span></c:if></th>
			<td>
				<html:text styleClass="text" styleId="shippingname" name="checkoutForm" property="shippingUser" size="16" maxlength="200"/>
			</td>
			<c:if test="${strictValidation}"><input type="hidden" name="mandatory_shippingUser" value="<bright:cmsWrite identifier="e-name-validation" filter="false"/>" /></c:if>
		</tr>		

		<logic:notEmpty name="labelAddress1">
			<tr>
				<th valign="top"><label for="address1"><bright:write name="labelAddress1" property="body" filter="false"/></label><c:if test="${strictValidation}"><span class="required">*</span></c:if></th>
				<td>
					<html:text styleClass="text" styleId="address1" name="checkoutForm" property="shippingAddress.addressLine1" size="16" maxlength="200"/>
				</td>
				<c:if test="${strictValidation}"><input type="hidden" name="mandatory_shippingAddress.addressLine1" value="<bright:cmsWrite identifier="failedValidationForField" filter="false"/> <bright:write name="labelAddress1" property="body" filter="false"/>." /></c:if>
			</tr>		
		</logic:notEmpty>
		<logic:notEmpty name="labelAddress2">			
			<tr>
				<th valign="top"><label for="address2"><bright:write name="labelAddress2" property="body" filter="false"/></label></th>
				<td>
					<html:text styleClass="text" styleId="address2" name="checkoutForm" property="shippingAddress.addressLine2" size="16" maxlength="200"/>
				</td>
			</tr>			
		</logic:notEmpty>
		<logic:notEmpty name="labelTown">			
			<tr>
				<th valign="top"><label for="town"><bright:write name="labelTown" property="body" filter="false"/></label><c:if test="${strictValidation}"><span class="required">*</span></c:if></th>
				<td>
					<html:text styleClass="text" styleId="town" name="checkoutForm" property="shippingAddress.town" size="16" maxlength="100"/>
				</td>
				<c:if test="${strictValidation}"><input type="hidden" name="mandatory_shippingAddress.town" value="<bright:cmsWrite identifier="failedValidationForField" filter="false"/> <bright:write name="labelTown" property="body" filter="false"/>." /></c:if>
			</tr>			
		</logic:notEmpty>
		<logic:notEmpty name="labelCounty">			
			<tr>
				<th valign="top"><label for="county"><bright:write name="labelCounty" property="body" filter="false"/></label></th>
				<td>
					<html:text styleClass="text" styleId="county" name="checkoutForm" property="shippingAddress.county" size="16" maxlength="100"/>
				</td>
			</tr>			
		</logic:notEmpty>
		<logic:notEmpty name="labelPostcode">			
			<tr>
				<th valign="top"><label for="postcode"><bright:write name="labelPostcode" property="body" filter="false"/></label><c:if test="${strictValidation}"><span class="required">*</span></c:if></th>
				<td>
					<html:text styleClass="text" styleId="postcode" name="checkoutForm" property="shippingAddress.postcode" size="16" maxlength="25"/>
				</td>
			</tr>	
			<c:if test="${strictValidation}"><input type="hidden" name="mandatory_shippingAddress.postcode" value="<bright:cmsWrite identifier="failedValidationForField" filter="false"/> <bright:write name="labelPostcode" property="body" filter="false"/>." /></c:if>
		</logic:notEmpty>

			<tr>
				<th valign="top"><label for="country"><bright:cmsWrite identifier="label-country" filter="false"/></label><span class="required">*</span></th>
				<td>
					<bright:refDataList componentName="AddressManager" methodName="getCountryList" id="countryList"/>
					<html:select name="checkoutForm" property="shippingAddress.country.id">
						<html:option value=""><bright:cmsWrite identifier="e-select-dropdown" filter="false"/></html:option>
						<c:if test="${userprofile.currentLanguage.default}">
							<html:optionsCollection name="countryList" value="id" label="name"/>
						</c:if>
						<c:if test="${not userprofile.currentLanguage.default}">
							<html:optionsCollection name="countryList" value="id" label="nativeName"/>
						</c:if>
					</html:select>									
				</td>
				<input type="hidden" name="mandatory_shippingAddress.country.id" value="<bright:cmsWrite identifier="e-country-validation" filter="false"/>" />
			</tr>		
			<html:hidden name="checkoutForm" property="user.address" />			
