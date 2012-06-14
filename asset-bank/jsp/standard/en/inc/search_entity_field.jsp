<c:if test="${not searchForm.entityPreSelected}">
	<logic:notEmpty name="searchForm" property="entities">
	
	<bright:applicationSetting id="columns" settingName="checkbox-attribute-columns"/>
	<bright:applicationSetting id="limit" settingName="checkbox-attribute-single-list-limit"/>
	
	<bean:size name="searchForm" property="entities" id="totalChecks" />
	<c:set var="rowCount" value="${totalChecks/columns}"/>
	<c:set var="nextBreakPoint" value="${index+rowCount-1}"/>
	
	<tr>
		<th style="padding-top: 8px;">
			<label for="entity_field"><bright:cmsWrite identifier="label-asset-type" filter="false"/></label> 
		</th>

		<td style="padding-top: 6px;">

			<table cellspacing="0" cellpadding="0" class="checklistCol">
				
				<c:if test="${userprofile.isAdmin}">
					<tr>
						<td style="padding:2px 0 0 0; width:15px;">
							<html:multibox name="searchForm" property="selectedEntities" styleClass="checkbox" styleId="entity0" onclick="toggleBoxes();">-999</html:multibox>
						</td>
						<td style="padding: 2px 0 0 2px">
							<em><label for="entity0">All types</label></em>
						</td>
					</tr>
					
					<c:set var="offset" value="1" />
				</c:if>
				
				<logic:iterate name="searchForm" property="entities" id="entity" indexId="checkIndex" >
				
					<bean:define id="entityId" name="entity" property="id" />
					<tr class="entityBoxes">
						<td style="padding:2px 0 0 0; width:15px;">
							<html:multibox name="searchForm" property="selectedEntities" styleClass="checkbox" styleId="<%=\"entity\"+entityId%>">
								<bean:write name="entityId" />
							</html:multibox>
						</td>
						<td style="padding: 2px 0 0 2px">
							<label for="entity<bean:write name='entityId' />"><bean:write name="entity" property="name" /></label>
						</td>
					</tr>
											
					<c:if test="${(totalChecks > limit) && (nextBreakPoint > 0 && (checkIndex + offset) >= nextBreakPoint)}">
						<c:set var="nextBreakPoint" value="${checkIndex+offset+rowCount}"/>
						</table>
						<table cellspacing="0" cellpadding="0" class="checklistCol"> 
					</c:if>
					
				</logic:iterate>
		
			</table>
			
			<script type="text/javascript">
			<!--
				function toggleBoxes ()
				{
					if (document.getElementById('entity0').checked)
					{
						//document.getElementById('entityboxes').style.display = 'none';
						$j('.entityBoxes').hide();
					}
					else
					{
						//document.getElementById('entityboxes').style.display = 'block';
						$j('.entityBoxes').show();
					}
				}
				toggleBoxes();						
			-->
			</script>
			
		</td>
	</tr>
	
	</logic:notEmpty>
</c:if>