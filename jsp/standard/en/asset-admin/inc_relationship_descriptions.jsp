<logic:notEmpty name='assetDescriptions'>
	<ul class="assetList">
	
	<bean:size id="assetDescriptionsSize" name="assetDescriptions"/>
	
	<logic:iterate name='assetDescriptions' id="assetDescription" indexId="myIndex">
		<li class="selected <c:if test="${myIndex==0}">first</c:if>">
			<c:set var="relDesIdentifier" value="${relationshipDescriptionForm.sourceAsset.id}:${relationshipId}" />
			<bean:define id="identifier" name="relDesIdentifier" />
			<bean:define id="relationshipDescription" name="assetDescription" property='<%= "object.relationshipDescriptionFromSearchString(" + identifier + ")" %>'/>
			
			<table cellspacing="0" width="100%">
				<tr>
					<td class="image">
						<img src="../servlet/display?file=<bean:write name='assetDescription' property='object.thumbnailImageFile.path' />" alt="<bean:write name='assetDescription' property='object.id' />" /> 
					</td>	
					<td style="width: 48%;">
			
						<c:choose>
							<c:when test="${relationshipId == 1}">
								<c:set var="relationshipType"><bright:cmsWrite identifier="snippet-relationship-name-peer"/></c:set>
							</c:when>
							<c:otherwise>
								<c:set var="relationshipType"><bright:cmsWrite identifier="snippet-relationship-name-child"/></c:set>
							</c:otherwise>
						</c:choose>
						<p><bright:cmsWrite identifier="snippet-relationship-to-asset" replaceVariables="true"/> <bean:write name='assetDescription' property='object.id' /></p>
						<c:set var="identifier" value="${assetDescription.object.id}relationshipDescription${relationshipId}" />
						<label for="<c:out value='${identifier}' />"><bean:write name='assetDescription' property='value' />:</label> <input type="text" class="text" name="<c:out value='${identifier}' />" id="<c:out value='${identifier}' />" size="40" value="<c:out value='${relationshipDescription}' />" /><br />
					</td>
					<td <c:if test="${assetDescriptionsSize gt 1}">class="copyAction"</c:if>>
						<%-- If on the first item and there are more then one then show 'copy to all' link --%>
						<c:if test="${myIndex==0}">
							<c:if test="${assetDescriptionsSize gt 1}">
									<a href="#" id="copyValuesLink">Copy these values to all items &raquo;</a>
							</c:if>	
						</c:if>	
						&nbsp;
					</td>
				</tr>
			</table>	
		</li>

	</logic:iterate>
	
	</ul>
</logic:notEmpty>