		<bright:applicationSetting id="catImageWidth" settingName="category-image-width"/>
		<bright:applicationSetting id="catImageHeight" settingName="category-image-height"/>
		<bright:applicationSetting id="canPublish" settingName="allow-publishing"/>

		<logic:notEmpty name="categoryForm" property="category.imageUrl">
			<p>
				Note that you may need to refresh your browser to see changes in the category image if it is changed.
			</p>
		</logic:notEmpty>

		<input type="hidden" name="categoryId" value="<c:out value="${categoryForm.category.id}"/>"/>
		<html:hidden name="categoryForm" property="category.categoryTypeId"/>
		<html:hidden name="categoryForm" property="category.extensionAssetId" />
		<html:hidden name="categoryForm" property="isRestrictive"/>
		<html:hidden name="categoryForm" property="parentId"/>
		
		<table class="form">
		<tr>
			<th><label class="inline" for="categoryName">Name:</label></th>
			<td><c:choose><c:when test="${categoryForm.category.extensionAssetId > 0}"><html:hidden name="categoryForm" property="category.name" /><bean:write name="categoryForm" property="category.name" /> [<a href="viewUpdateAsset?id=<bean:write name='categoryForm' property='category.extensionAssetId' />&setReturn=true">edit name via extension asset</a>]</c:when><c:otherwise><html:text styleId="categoryName" styleClass="text" name="categoryForm" property="category.name" size="30" maxlength="150" /></c:otherwise></c:choose></td>
		</tr>
		<logic:notEmpty name="categoryForm" property="category.translations">
			<logic:iterate name="categoryForm" property="category.translations" id="translation" indexId="tIndex">
				<logic:greaterThan name="translation" property="language.id" value="0">
					<tr>
						<th class="translation">
							<label for="categoryName<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
						</th>
						<td>
							<html:hidden name="categoryForm" property="category.translations[${tIndex}].language.id"/>
							<html:hidden name="categoryForm" property="category.translations[${tIndex}].language.name"/>
							<c:choose><c:when test="${categoryForm.category.extensionAssetId > 0}"><input type="hidden" name="category.translations[<bean:write name='tIndex'/>].name" value="<bean:write name='translation' property='name' />" /><bean:write name="translation" property="name" /></c:when><c:otherwise><input type="text" class="text" name="category.translations[<bean:write name='tIndex'/>].name" maxlength="150" size="30" id="categoryName<bean:write name='tIndex'/>" value="<bean:write name='translation' property='name' />"/></c:otherwise></c:choose>
						</td>
					</tr>
				</logic:greaterThan>
			</logic:iterate>
		</logic:notEmpty>
		<tr>
			<th><label class="inline" for="categoryDesc">Description:</label></th>
			<td>
				<html:textarea styleClass="editor" style="width:80%;" styleId="categoryDesc"  name="categoryForm" property="category.description" cols="90" rows="15"  />
			</td>
		</tr>
		<logic:notEmpty name="categoryForm" property="category.translations">
			<logic:iterate name="categoryForm" property="category.translations" id="translation" indexId="tIndex">
				<logic:greaterThan name="translation" property="language.id" value="0">
					<tr>
						<th class="translation">
							<label for="categoryDesc<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
						</th>
						<td>
							<html:hidden name="categoryForm"  property="category.translations[${tIndex}].language.id"/>
							<html:hidden name="categoryForm"  property="category.translations[${tIndex}].language.name"/>
							<textarea class="editor" style="width:80%;" name="category.translations[<bean:write name='tIndex'/>].description" cols="90" rows="15" id="categoryDesc<bean:write name='tIndex'/>"><bean:write name="translation" property="description" filter="false"/></textarea>
						</td>
					</tr>
				</logic:greaterThan>
			</logic:iterate>
		</logic:notEmpty>
		<bean:define id="uploadImageLabel" value="Add image"/>
		<logic:notEmpty name="categoryForm" property="category.imageUrl">

			<bean:define id="uploadImageLabel" value="New image"/>
			<tr>
				<th><label>Current image:</label></th>
				<td><img src="../<bean:write name='categoryForm' property='category.imageUrl'/>" width="<bean:write name='catImageWidth'/>" height="<bean:write name='catImageHeight'/>" alt="Image thumbnail for category" /></td>
			</tr>
			<tr>
				<th><label for="removeExisting">Remove current image:</label></th>
				<td><html:checkbox styleId="removeExisting" styleClass="checkbox" name="categoryForm" property="removeImage"/></td>
			</tr>
		</logic:notEmpty>
		<tr>
			<th>		
			<label for="categoryImageFile"><bean:write name="uploadImageLabel"/>:</span></th>
			<td><html:file styleId="categoryImageFile" styleClass="file" name="categoryForm" property="imageFile" size="40" /></td>
		</tr>
		<logic:equal name='canPublish'  value='true'>
			<tr>
				<th class="checkbox">
					<label for="synchronised"><logic:equal name='categoryForm' property='category.categoryTypeId' value='2'>Synchronise Assets?</logic:equal><logic:notEqual name='categoryForm' property='category.categoryTypeId' value='2'>Include in synchronisation data?</logic:notEqual></label>
				</th>
				<td>				
					<html:checkbox name='categoryForm' property='category.synchronised' styleClass="checkbox" styleId="synchronised" /> 
				</td>					
			</tr>
		</logic:equal>
		
		<logic:present name='showSelectedOnLoadField'>
			<logic:equal name='showSelectedOnLoadField' value='true'>
				<tr>
					<th class="checkbox">
						<label for="selectedOnLoad">Selected on load? </label>
					</th>
					<td>				
						<html:checkbox name='categoryForm' property='category.selectedOnLoad' styleClass="checkbox" styleId="selectedOnLoad" /> <em>*Determines whether this category is selected by default when someone loads an <bright:cmsWrite identifier="item" filter="false" /> into <bright:cmsWrite identifier="app-name" filter="false"/>.</em>
					</td>					
				</tr>
			</logic:equal>
			<logic:equal name="showSelectedOnLoadField" value="false">
				<html:hidden name="categoryForm" property="category.selectedOnLoad"/>
			</logic:equal>
		</logic:present>
		<logic:notPresent name='showSelectedOnLoadField'>
			<html:hidden name="categoryForm" property="category.selectedOnLoad"/>
		</logic:notPresent>

		<c:if test="${categoryForm.category.categoryTypeId == 1}">
			<tr>
				<th class="checkbox">
					<label for="allowAdvancedOptions">Show advanced download? </label>
				</th>
				<td>				
					<input type="checkbox" name="allowAdvancedOptions" value="1" <c:if test='${categoryForm.category.allowAdvancedOptions}'>checked="checked"</c:if> class="checkbox" id="allowAdvancedOptions"/>
					<em>*Determines whether <bright:cmsWrite identifier="item" filter="false" />s in this category should have the advanced download options.</em>
				</td>					
			</tr>
		</c:if>
		<tr>
			<th class="checkbox">
				<label for="browsable">Is browsable?</label>
			</th>
			<td>				
				<html:checkbox name='categoryForm' property='browsable' styleClass="checkbox" styleId="browsable" /> <em>*Determines whether <bright:cmsWrite identifier="item" filter="false" />s in this category are browsable.</em>
			</td>					
		</tr>
		<bright:refDataList componentName="PluginManager" methodName="getBrowseAssetsPaneller" id="paneller" />
		<c:choose>
			<c:when test="${not empty paneller}">
				<tr>
					<th class="checkbox">
						<label for="browseAsPanels">Display assets in panels?</label>
					</th>
				</th>
					<td>
						<html:checkbox name="categoryForm" property="category.browseAsPanels" styleClass="checkbox" styleId="browseAsPanels"/> <em>*Should the items in this category be displayed in panels when browsing according to the paneller selected in the settings file or without panelling.</em><br />
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<html:hidden name="categoryForm" property="category.browseAsPanels" value="false" />
			</c:otherwise>
		</c:choose>
		<c:if test="${categoryForm.category.categoryTypeId == 2}">
			<c:choose>
				<c:when test="${categoryForm.parentId > 0}">
					<tr>
						<th class="checkbox">
							<label for="newCatRestrictive">Has own permissions:</label>
						</th>
						<td>
							<html:checkbox style="float:left" styleClass="checkbox" name="categoryForm" property="category.isRestrictive" styleId="newCatRestrictive"  onclick="if(this.checked) $j('#workflowName').removeAttr('disabled'); else $j('#workflowName').attr('disabled','disabled');"/>
							<div style="float:left; width: 80%">
							<c:choose>
								<c:when test="${not categoryForm.isRestrictive}">
									<em>*If an access level is changed to have permissions, its parent access levels will be changed to have permissions also.</em>
								</c:when>
								<c:otherwise>
									<em>*If an access level is changed to not have permissions, all its sub-access levels will be changed to not have permissions also.</em>
								</c:otherwise>
							</c:choose>
							<br/>
							<em>&nbsp;<strong>NOTE: You will have to do a full reindex before the permissions are removed/applied to existing <bright:cmsWrite identifier="items" />.</strong></em>
							</div>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<input type="hidden" name="category.isRestrictive" value="true"/>
				</c:otherwise>
			</c:choose>
			<tr>
				<th class="checkbox">
					<label for="newCatAssignableIfLeaf">Always assignable:</label>
				</th>
				<td>
					<html:checkbox styleClass="checkbox" name="categoryForm" property="category.canAssignIfNotLeaf" styleId="newCatAssignableIfLeaf" />
					<em>*If checked, the access level can be assigned to <bright:cmsWrite identifier="items" filter="false" /> even when it has sub-levels.</em>
				</td>
			</tr>
		</c:if>
		<c:if test="${categoryForm.category.categoryTypeId == 2}">
			<bright:refDataList id="workflows" componentName="WorkflowManager" methodName="getWorkflowBeans"/>
			<bean:size id="wfCount" name="workflows"/>
			<c:choose>
				<c:when test="${wfCount == 1}">
					<html:hidden name="categoryForm" property="category.workflowName" />
				</c:when>
				<c:otherwise>
					<tr>
						<th class="checkbox">
							<label for="workflow">Workflow name</label>
						</th>
						<td>				
							<select id="workflowName" name="category.workflowName" size="1">
								<logic:iterate name="workflows" id="workflow">
									<option value="<bean:write name='workflow' property='name'/>" <c:if test='${categoryForm.category.workflowName == workflow.name}'>selected</c:if>><bean:write name='workflow' property='description'/></option>
								</logic:iterate>
							</select>
							<script>
								if(document.getElementById('newCatRestrictive')) 
								{
									if(document.getElementById('newCatRestrictive').checked){ 
										document.getElementById('workflowName').disabled=false; 
									} else  {
										document.getElementById('workflowName').disabled=true;
									}
								}
							</script>
						</td>					
					</tr>
					<c:if test="${categoryForm.category.hasDescendants}">
					<tr>
						<th class="checkbox">
							<label for="updateChildWorkflows">Update child workflows to match?</label>
						</th>
						<td>
							<input type="checkbox" name="updateChildWorkflows" id="updateChildWorkflows" value="1" class="checkbox"/>
						</td>
					</tr>
					</c:if>
				</c:otherwise>
			</c:choose>
		</c:if>
		
		</table>
		<div class="hr"></div>
		<div>
		<input type="submit" name="save" value="<bright:cmsWrite identifier="button-save" filter="false" />" class="button flush floated" />
		</div>
		<bean:parameter id="parentId" name="parentId" value="-1" />
		<bean:parameter id="ouid" name="ouid" value="-1" />
		<logic:notPresent name='returnAction'>
			<bean:define id='returnAction' value='viewCategoryAdmin'/>
		</logic:notPresent>
		<a href="<bean:write name='returnAction'/>?categoryId=<bean:write name='parentId' />&ouid=<bean:write name='ouid'/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
			
