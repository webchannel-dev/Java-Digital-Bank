		<logic:notPresent name="type">
			<bean:define id="type" value="list" />
		</logic:notPresent>

		<logic:notPresent name="showLabel">
			<bean:define id="showLabel" value="true" />
		</logic:notPresent>

		<logic:notEmpty name="extraDescriptions">
			<logic:iterate name="extraDescriptions" id="descriptionContainer">
			
				<%-- get the descriptions from the map --%>
				<logic:notEmpty name="descriptionContainer" property="descriptionsForAsset(${item.id})" >
					<bean:define id="extraAssetDescriptions" name="descriptionContainer" property="descriptionsForAsset(${item.id})" />
						<logic:iterate name="extraAssetDescriptions" id="description" indexId="index">
							<c:choose><c:when test="${type=='table'}"><td></c:when><c:otherwise><li></c:otherwise></c:choose><c:if test="${not empty description.value}"><c:if test="${showLabel}"><bean:write name='description' property='name' />: </c:if><bean:write name='description' property='value' /></c:if><c:choose><c:when test="${type=='table'}"></td></c:when><c:otherwise></li></c:otherwise></c:choose>
						</logic:iterate>
				</logic:notEmpty>
			</logic:iterate>
		</logic:notEmpty>