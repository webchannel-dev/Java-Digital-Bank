			<logic:notPresent name="verticalWithLabels">
				<bean:define id="verticalWithLabels" value="false" />
			</logic:notPresent>
			
			<tbody>
				<logic:iterate name="orgUnitForm" property="userList" id="user">
					<tr>
						<c:if test="${verticalWithLabels}"><th>Name: </th></c:if>
						<td>
							<logic:notEmpty name="user" property="forename"><bean:write name="user" property="forename" /></logic:notEmpty>
							<logic:notEmpty name="user" property="surname"><bean:write name="user" property="surname" /></logic:notEmpty>&nbsp;
						</td>
						<c:if test="${verticalWithLabels}"></tr><tr><th>Surname: </th></c:if>
						<td>	
							<logic:notEmpty name="user" property="username"><bean:write name="user" property="username" /></logic:notEmpty>&nbsp;
						</td>
						<c:if test="${verticalWithLabels}"></tr><tr><th>Email: </th></c:if>
						<td>	
							<logic:notEmpty name="user" property="emailAddress"><bean:write name="user" property="emailAddress" /></logic:notEmpty>&nbsp;
						</td>
						<c:if test="${verticalWithLabels}"></tr><tr><th>Address: </th></c:if>
						<td>	
							<c:choose>
								<c:when test="${useStructuredAddress}">
									<logic:notEmpty name="user" property="homeAddress.addressLine1"><bean:write name="user" property="homeAddress.addressLine1" /><br /></logic:notEmpty>
									<logic:notEmpty name="user" property="homeAddress.addressLine2"><bean:write name="user" property="homeAddress.addressLine2" /><br /></logic:notEmpty>
									<logic:notEmpty name="user" property="homeAddress.town"><bean:write name="user" property="homeAddress.town" /><br /></logic:notEmpty>
									<logic:notEmpty name="user" property="homeAddress.county"><bean:write name="user" property="homeAddress.county" /><br /></logic:notEmpty>
									<logic:notEmpty name="user" property="homeAddress.postcode"><bean:write name="user" property="homeAddress.postcode" /></logic:notEmpty>
								</c:when>
								<c:otherwise>
									<logic:notEmpty name="user" property="address"><bean:write name="user" property="address" /></logic:notEmpty>
								</c:otherwise>
							</c:choose>
						</td>
						<c:if test="${verticalWithLabels}"></tr><tr><th>Job Title: </th></c:if>
						<td>	
							<logic:notEmpty name="user" property="jobTitle"><bean:write name="user" property="jobTitle" /><br /></logic:notEmpty>
						</td>
						<c:if test="${verticalWithLabels}"></tr><tr><th>Registered: </th></c:if>
						<td>	
							<logic:notEmpty name="user" property="registerDate"><bean:write name="user" property="registerDate" format="dd/MM/yyyy" /></logic:notEmpty>
						</td>	
						<!-- Custom user field values -->
						<logic:notEmpty name="orgUnitForm" property="customFields">
							<bean:define id="customFields" name="orgUnitForm" property="customFields"/>
							<bean:define id="selectedValueSet" name="user" property="customFieldValues"/>
							<bean:define id="readOnly" value="true"/>
							<logic:notEmpty name="customFields">
								<logic:iterate name="customFields" id="field">
									<c:if test="${not field.isSubtype || (field.isSubtype && isCustomFieldSubtype)}">
										<bean:define id="fieldId" name="field" property="id"/> 

										<logic:present name="selectedValueSet">
											<logic:notEmpty name="selectedValueSet">
												<bean:define id="selectedTextValue" name="selectedValueSet" property="<%= \"selectedTextValue(\" + fieldId + \")\" %>"/>
											</logic:notEmpty>
										</logic:present>
										<c:if test="${verticalWithLabels}"></tr><tr><th><bean:write name='field' property='name' />: </th></c:if>
										<td>			
											<c:choose>
												<c:when test="${field.isDropdown}">
														<bean:define name="selectedValueSet" property="<%= \"firstSelectedListValue(\" + fieldId + \")\" %>" id="selectedValue"/>
														<c:if test="${selectedValue.id > 0}">
															<bean:write name="selectedValue" property="value"/>
														</c:if>
												</c:when>
												<c:when test="${field.isCheckboxList}">
													<logic:iterate name="selectedValueSet" property="<%= \"selectedListValues(\" + fieldId + \")\" %>" id="selectedValue" indexId="index">
														<input type="hidden" name="listField:<bean:write name='field' property='id'/>:<bean:write name='index'/>" value="<bean:write name='selectedValue' property='id'/>"/>
														<bean:write name="selectedValue" property="value"/><br/>
													</logic:iterate>
												</c:when>	
												<c:otherwise>
													<bean:write name="selectedTextValue"/>		
												</c:otherwise>
											</c:choose>											
										</td>
									</c:if>
								</logic:iterate>
							</logic:notEmpty>
						</logic:notEmpty>
					</tr>
					<c:if test="${verticalWithLabels}"><tr class="spacer"><td colspan="2"></td></tr></c:if>
				</logic:iterate>
			</tbody>