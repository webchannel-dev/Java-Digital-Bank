	<c:choose>
		<c:when test="${treeId == 1}">
			<bean:define id="formAction" value="importCategories" />
			<bean:define id="descriptor" value="category" />
			<bean:define id="descriptorPlural" value="categories" />
			<bean:define id="exampleFilename" value="example_category_import" />
			<bean:define id="cancelLink" value="viewCategoryAdmin" />
		</c:when>
		<c:when test="${treeId == 2}">
			<bean:define id="formAction" value="importAccessLevels" />
			<bean:define id="descriptor" value="access level" />
			<bean:define id="descriptorPlural" value="access levels" />
			<bean:define id="exampleFilename" value="example_access_level_import" />
			<bean:define id="cancelLink" value="viewPermissionCategories" />
		</c:when>
		<c:otherwise>
			<bean:define id="formAction" value="importKeywords" />
			<bean:define id="descriptor" value="keyword" />
			<bean:define id="descriptorPlural" value="keywords" />
			<bean:define id="exampleFilename" value="example_keyword_import" />
			<bean:define id="cancelLink" value="viewKeywordAdmin?categoryTypeId=${treeId}&attributeId=${metadataImportForm.attribute.id}" />
		</c:otherwise>
	</c:choose>
	
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
	
	<logic:equal name="metadataImportForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="metadataImportForm" property="errors" id="error">
				<bean:write name="error" filter="false"/><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<form enctype="multipart/form-data" action="<c:out value='${formAction}' />" method="post" class="floated">
		
		<input type="hidden" name="treeId" value="<c:out value='${treeId}' />" />
	
		<p>Please select the data file from which you want to import <c:out value='${descriptorPlural}' />. This should be a tab-delimited file, containing the following:
		<ul>
			<li>A header row, containing an identifier for each column. The identifiers can be in any order, and can be either 'Name' or 'Description'.</li><br/>
			<li>The Name column should contain the full names of the <c:out value='${descriptorPlural}' />, in the following format: root<c:out value='${descriptor}' />-name/sub-cat-name/sub-cat-name/etc. Parent <c:out value='${descriptorPlural}' /> will be added if they do not already exist.</li><br/> 
			<li>A row for each <c:out value='${descriptor}' />, containing appropriate data for each column.</li><br/>
			<li>If you have problems formatting the file you can <a href="../tools/import/<c:out value='${exampleFilename}' />.txt" target="_blank">download this example file</a> and modify it.</li><br/>
		</ul>		
		</p>
		<bright:applicationSetting id="extensionAssetsEnabled" settingName="category-extension-assets-enabled"/>	
		<bright:applicationSetting id="batchReleasesEnabled" settingName="batch-releases-enabled"/>	
		
		<c:if test="${extensionAssetsEnabled && (treeId == 1 || treeId == 2) && !batchReleasesEnabled}">
			<p>To automatically create and link empty extension assets to your categories add a column 'ExtensionEntityId' to your data file populated with the entity id's of the asset that should be created for each category. The entity must be one that has been marked as being a category extension type in Admin > Asset Types.</p>
		</c:if>
		
		<br/>
	

		<%-- extra bits for keywords --%>
		<c:if test="${treeId != 1 && treeId != 2}">
			<c:choose>
				<c:when test="${metadataImportForm.attribute.id > 0}">
					<p>
						<strong>Keyword picker:</strong> <c:out value="${metadataImportForm.attribute.label}"/>
						<input type="hidden" name="treeId" value="<c:out value="${treeId}"/>">
						<input type="hidden" name="attributeId" value="<c:out value="${metadataImportForm.attribute.id}"/>">
					</p>
				</c:when>
				<c:otherwise>
					<label for="keywordPicker">Keyword picker:<span class="required">*</span></label>
					
					<bean:define id="keywordPickers" name="metadataImportForm" property="attributes"/>
					<html:select name="metadataImportForm" property="treeId" styleId="keywordPicker">
						<html:options collection="keywordPickers" property="treeId" labelProperty="label"/>
					</html:select>
					<br/>
				</c:otherwise>
			</c:choose>
		</c:if>
	
		<label for="file">Data File:<span class="required">*</span></label>
		<html:file name="metadataImportForm" property="file" styleClass="file" styleId="file" size="35"/>
		<br />

		<div class="hr"></div>
		
		<input type="submit" name="saveButton" class="button flush" id="submitButton" value="Submit">
		<a href="<c:out value='${cancelLink}' />" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</form>