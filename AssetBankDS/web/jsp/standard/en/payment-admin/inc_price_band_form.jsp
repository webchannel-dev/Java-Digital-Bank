

		<%-- Common price band fields --%>
		<html:hidden name="priceBandForm" property="priceBand.id" />

		<%-- Pass thru the type and id in case of validation error --%>
		<input type="hidden" name="priceBand.priceBandType.id" value="<c:out value='${iType}' />" />
		<input type="hidden" name="id" value="<c:out value='${priceBandForm.priceBand.id}' />" />		

		<table cellspacing="0" class="form" summary="Form for price band details">
			
			<tr>												
				<th>
					<label for="assetType">Asset Type:</label>
				</th>
				<td>
					<html:select styleClass="text" styleId="assetType" name="priceBandForm" property="priceBand.assetType.id">
						<html:option value="1">File</html:option>
						<html:option value="2">Image</html:option>
						<html:option value="3">Video</html:option>
						<html:option value="4">Audio</html:option>
					</html:select>
				</td>													
			</tr>			
			<tr>												
				<th>
					<label for="name">Name:</label>
				</th>
				<td>
					<html:text styleClass="text" styleId="name" name="priceBandForm" property="priceBand.name" />
				</td>													
			</tr>
			<logic:notEmpty name="priceBandForm" property="priceBand.translations">
				<logic:iterate name="priceBandForm" property="priceBand.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="priceBand<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
							</th>
							<td>
								<html:hidden name="priceBandForm" property="priceBand.translations[${tIndex}].language.id"/>
								<html:hidden name="priceBandForm" property="priceBand.translations[${tIndex}].language.name"/>
								<input type="text" class="text" name="priceBand.translations[<bean:write name='tIndex'/>].name" maxlength="40" id="priceBand<bean:write name='tIndex'/>" value="<bean:write name="translation" property="name" filter="false"/>"/>
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>
			</logic:notEmpty>
			<tr>												
				<th>
					<label for="description">Description:</label>
				</th>
				<td>
					<html:text styleClass="text" styleId="description" name="priceBandForm" property="priceBand.description" />
				</td>													
			</tr>
			<logic:notEmpty name="priceBandForm" property="priceBand.translations">
				<logic:iterate name="priceBandForm" property="priceBand.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="priceBandDesc<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
							</th>
							<td>
								<input type="text" class="text" name="priceBand.translations[<bean:write name='tIndex'/>].description" maxlength="40" id="priceBandDesc<bean:write name='tIndex'/>" value="<bean:write name="translation" property="description" filter="false"/>"/>
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>
			</logic:notEmpty>
			<tr>												
				<th>
					<label for="baseprice">Base Price:</label>
				</th>
				<td>
					<html:text styleClass="text" styleId="baseprice" name="priceBandForm" property="priceBand.basePrice.formAmount" size="16" maxlength="16"/>
				</td>													
			</tr>
			
			<!-- Unit price, max quantity for print type only -->
			<c:if test="${iType == 2}">
				<tr>												
					<th>
						<label for="unitprice">Unit Price:</label>
					</th>
					<td>
						<html:text styleClass="text" styleId="unitprice" name="priceBandForm" property="priceBand.unitPrice.formAmount" size="16" maxlength="16"/>
					</td>													
				</tr>
				<tr>												
					<th>
						<label for="maxquantity">Max Quantity:</label>
					</th>
					<td>
						<html:text styleClass="text" styleId="maxquantity" name="priceBandForm" property="priceBand.maxQuantity.formNumber" size="16" maxlength="16"/>
					</td>													
				</tr>
			</c:if>			
			
			<%-- Download original and iscommercial for download type only --%>
			<c:if test="${iType == 1}">
				<tr>												
					<th>
						<label for="original">Advanced download?:</label>
					</th>
					<td>
						<html:checkbox name="priceBandForm" property="priceBand.canDownloadOriginal" styleClass="checkbox" styleId="original" />
					</td>													
				</tr>					
				<tr>												
					<th>
						<label for="commercial">Is Commercial?:</label>
					</th>
					<td>
						<html:checkbox name="priceBandForm" property="priceBand.isCommercial" styleClass="checkbox" styleId="commercial" />
					</td>													
				</tr>					
			</c:if>
			
		</table>
		
