<logic:notEmpty name="customFields">
	<logic:iterate name="customFields" id="field">
		<c:if test="${not field.isSubtype || (field.isSubtype && isCustomFieldSubtype)}">
			<bean:define id="fieldId" name="field" property="id"/> 
	
			<logic:notPresent name="readOnly">
				<bean:define id="readOnly" value="false"/>
			</logic:notPresent>
					
			<logic:present name="selectedValueSet">
				<logic:notEmpty name="selectedValueSet">
					<bean:define id="selectedTextValue" name="selectedValueSet" property="<%= \"selectedTextValue(\" + fieldId + \")\" %>"/>
				</logic:notEmpty>
			</logic:present>
			<logic:notPresent name="selectedValueSet">
				<bean:define id="selectedValue" value="false"/>
			</logic:notPresent>
			<logic:notPresent name="registering">
				<bean:define id="registering" value="false"/>
			</logic:notPresent>
			<tr id="fieldRow_<c:out value='${field.orgUnitId}'/>_<c:out value='${field.id}'/>" <c:if test="${registering && field.orgUnitId>0}">style="display: none"</c:if>>
				<th id="labelcol_<bean:write name='field' property='id'/>" ><label for="field<bean:write name='field' property='id'/>"><bean:write name="field" property="name"/></label>:<c:if test="${field.isRequired && !readOnly}"><span class="required">*</span></c:if></th>
				<td colspan="2">
				
					<c:choose>
					
						<c:when test="${ not empty field.ldapProperty }">
							<bean:write name="selectedTextValue"/>
						</c:when>
						
						<c:otherwise>
						
							<c:choose>
								
								<c:when test="${field.isTextfield}">
									
									<c:choose>
										<c:when test="${readOnly}">
											<input type="hidden" name="field:<bean:write name='field' property='id'/>" value="<bean:write name='selectedTextValue'/>" />
											<bean:write name="selectedTextValue"/>
										</c:when>
										<c:otherwise>
											<input type="text" class="text" name="field:<bean:write name='field' property='id'/>" <logic:present name="selectedTextValue">value="<bean:write name='selectedTextValue'/>"</logic:present> id="field<bean:write name='field' property='id'/>" />
										</c:otherwise>
									</c:choose>
			
								</c:when>
								
								<c:when test="${field.isTextarea}">
									
									<c:choose>
										<c:when test="${readOnly}">
											<input type="hidden" name="field:<bean:write name='field' property='id'/>" value="<bean:write name='selectedTextValue'/>"/>
											<bean:write name="selectedTextValue"/>
										</c:when>
										<c:otherwise>
											<textarea class="text" name="field:<bean:write name='field' property='id'/>" rows="4" style="width: 200px;"><logic:present name="selectedTextValue"><bean:write name='selectedTextValue'/></logic:present></textarea>
										</c:otherwise>
									</c:choose>
			
								</c:when>
								
								<c:when test="${field.isDropdown}">
									
									<c:choose>
										<c:when test="${readOnly}">
												<bean:define name="selectedValueSet" property="<%= \"firstSelectedListValue(\" + fieldId + \")\" %>" id="selectedValue"/>
												<c:if test="${selectedValue.id > 0}">
													<input type="hidden" name="listField:<bean:write name='field' property='id'/>" value="<bean:write name='selectedValue' property='id'/>"/>
													<bean:write name="selectedValue" property="value"/>
												</c:if>
										</c:when>
										<c:otherwise>
											<select name="listField:<bean:write name='field' property='id'/>" class="text" size="1">
												<option value="">[please choose]</option>
												<logic:iterate name="field" property="availableValues" id="fieldValue">
													<logic:present name="selectedValueSet">
														<bean:define id="valueId" name="fieldValue" property="id"/>
														<bean:define id="selectedValue" name="selectedValueSet" property="<%= \"selectedListValue(\" + fieldId + \":\" + valueId + \")\" %>"/>
													</logic:present>
													<option value="<bean:write name='fieldValue' property='id'/>" <c:if test="${selectedValue}">selected</c:if>><bean:write name="fieldValue" property="value"/></option>
												</logic:iterate>
											</select>
										</c:otherwise>
									</c:choose>
			
								</c:when>
								
								<c:when test="${field.isCheckboxList}">
									<c:choose>
										<c:when test="${readOnly}">
											<logic:iterate name="selectedValueSet" property="<%= \"selectedListValues(\" + fieldId + \")\" %>" id="selectedValue" indexId="index">
												<input type="hidden" name="listField:<bean:write name='field' property='id'/>:<bean:write name='index'/>" value="<bean:write name='selectedValue' property='id'/>"/>
												<bean:write name="selectedValue" property="value"/><br/>
											</logic:iterate>
										</c:when>
										<c:otherwise>
											<input type="hidden" name="listField:<bean:write name='field' property='id'/>:control" value=""/>
											<logic:iterate name="field" property="availableValues" id="fieldValue" indexId="index">
											
											<logic:present name="selectedValueSet">
												<bean:define id="valueId" name="fieldValue" property="id"/>
												<bean:define id="selectedValue" name="selectedValueSet" property="<%= \"selectedListValue(\" + fieldId + \":\" + valueId + \")\" %>"/>
											</logic:present>
											
											<input type="checkbox" class="checkbox" name="listField:<bean:write name='field' property='id'/>:<bean:write name='index'/>" id="listField:<bean:write name='field' property='id'/>:<bean:write name='index'/>" value="<bean:write name='fieldValue' property='id'/>" <c:if test="${selectedValue}">checked</c:if>/>&nbsp;<label for="listField:<bean:write name='field' property='id'/>:<bean:write name='index'/>"><bean:write name="fieldValue" property="value"/></label><br/>
												
											</logic:iterate>
										</c:otherwise>
									</c:choose>
								</c:when>
			
							</c:choose>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:if>
	</logic:iterate>
</logic:notEmpty>