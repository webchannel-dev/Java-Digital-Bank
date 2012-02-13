<c:if test="${not searchForm.entityPreSelected}">
	<logic:notEmpty name="searchForm" property="entities">
		<tr>
			<th style="padding-top: 8px;">
				<label for="entity_field"><bright:cmsWrite identifier="label-asset-type" filter="false"/></label> 
			</th>
			<td style="padding-top: 6px;">
				<c:if test="${userprofile.isAdmin}">
					<html:multibox name="searchForm" property="selectedEntities" styleClass="checkbox" styleId="entity0" onclick="toggleBoxes();">
						-999
					</html:multibox> <label for="entity0">All types</label><br />
				</c:if>
				<div id="entityboxes">
				<logic:iterate name="searchForm" property="entities" id="entity">
					<bean:define id="entityId" name="entity" property="id"/>
					<html:multibox name="searchForm" property="selectedEntities" styleClass="checkbox" styleId="<%=\"entity\"+entityId%>">
						<bean:write name="entityId" />
					</html:multibox>
					<label for="entity<bean:write name='entityId' />"><bean:write name="entity" property="name" /></label><br />
				</logic:iterate>
				</div>
				<script type="text/javascript">
				<!--
					function toggleBoxes ()
					{
						if (document.getElementById('entity0').checked)
						{
							document.getElementById('entityboxes').style.display = 'none';
						}
						else
						{
							document.getElementById('entityboxes').style.display = 'block';
						}
					}
					toggleBoxes();						
				-->
				</script>
			</td>
		</tr>
	</logic:notEmpty>
</c:if>