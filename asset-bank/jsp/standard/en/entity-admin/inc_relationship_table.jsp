



<table class="list highlight" cellspacing="0" style="margin-top:0; margin-left:15px">
	<thead>
		<tr>
			<th class="advanced">Asset type</th>
			<th class="advanced">Default relationship category id</th>
			<c:if test="${!supportMultiLanguage}"><th class="advanced">Relationship description label</th></c:if>
		</tr>
	</thead>	
		
	<logic:iterate name="assetEntityForm" property="entities" id="entity">
		<tr>
			<td>
				<bean:define id="entityId" name="entity" property="id"/>
				<label class="wrapping">
				<c:choose> 
					<c:when test="${relationshipTableType == 'peer'}">
						<html:multibox name="assetEntityForm" property="selectedPeerEntities" styleClass="checkbox" styleId="<%=\"peerEntityCheck\"+entityId%>">
							<bean:write name="entityId" />
						</html:multibox>
						<bean:define id="defaultCatId" name="assetEntityForm" property='<%= "entity.defaultRelationshipCatIdForPeer(" + entityId + ")" %>'/>
						<bean:define id="relationshipDescriptionLabel" name="assetEntityForm" property='<%= "entity.relationshipDescriptionLabelForPeer(" + entityId + ")" %>'/>
					</c:when>
					<c:otherwise>
						<html:multibox name="assetEntityForm" property="selectedChildEntities" styleClass="checkbox" styleId="<%=\"childEntityCheck\"+entityId%>">
							<bean:write name="entityId" />
						</html:multibox>
						<bean:define id="defaultCatId" name="assetEntityForm" property='<%= "entity.defaultRelationshipCatIdForChild(" + entityId + ")" %>'/>
						<bean:define id="relationshipDescriptionLabel" name="assetEntityForm" property='<%= "entity.relationshipDescriptionLabelForChild(" + entityId + ")" %>'/>
					</c:otherwise>
				</c:choose>
				<bean:write name="entity" property="name" />
				</label> 
			</td>
			<td class="advanced">	
				<input type="text" name="<bean:write name='relationshipTableType' />EntityDefaultCategoryId<bean:write name='entityId'/>" size="4" class="vsmall" value="<bean:write name='defaultCatId' />" />
			</td>
			<c:if test="${!supportMultiLanguage}"><td class="advanced">
				<input type="text" name="<bean:write name='relationshipTableType' />RelationshipDescriptionLabel<bean:write name='entityId'/>" size="20" class="medium" value="<bean:write name='relationshipDescriptionLabel' />" />
			</td></c:if>	
		</tr>
	</logic:iterate>
</table>
<a href="#" class="advancedToggle" style="margin-left:15px">Show advanced options</a>
<br />