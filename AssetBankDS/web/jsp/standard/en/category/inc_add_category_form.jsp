<h3>Add new sub-<bean:write name='catName' /> to <bean:write name='currentCategoryName' filter="false"/>:</h3>
		
<form action="<bean:write name='addAction' />" method="post" class="floated">	
	<html:hidden name="categoryAdminForm" property="categoryId"/>
	<input type="hidden" name="ouid" value="<c:out value='${ouid}' />" />
	<input type="hidden" name="catExtensionReturnLocation" value="" />
	
	<c:if test="${catExtensionAssetsEnabled}">
		<label>I want to add a...</label>
		<ul class="radioList narrow flush">
			<li><label for="categoryType1" class="after"><html:radio name="categoryAdminForm" property="extendedCategory" value="false" styleId="categoryType1" /> Standard <bean:write name='catName' /></label></li>
			<li><label for="categoryType2" class="after"><html:radio name="categoryAdminForm" property="extendedCategory" value="true" styleId="categoryType2" /> Extended <bean:write name='catName' /></label></li>
		</ul>
	</c:if>
	<c:if test="${categoryAdminForm.categoryTreeId==2}">
	<div id="accessLevelInfoHolder">
		<c:choose>
			<c:when test="${categoryAdminForm.categoryId > 0}">
				<label for="newCatRestrictive">Has permissions:</label>
				<html:checkbox styleClass="checkbox" name="categoryAdminForm" property="newCategory.isRestrictive" styleId="newCatRestrictive"/>
				<em>*If an access level can have permissions, its parent access levels will have permissions also.</em><br />
			</c:when>
			<c:otherwise>
				<input type="hidden" name="newCategory.isRestrictive" value="true"/>
			</c:otherwise>
		</c:choose>
		<label>Always assignable?:</label>
		<label class="wrapping">
		<bright:applicationSetting id="defaultToAssignable" settingName="default-new-accesslevels-to-assignable" />
		<input type="checkbox" class="checkbox" name="newCategory.canAssignIfNotLeaf" id="newCatAssignableIfLeaf" <c:if test='${defaultToAssignable}'>checked</c:if>/>
		This access level can be assigned to <bright:cmsWrite identifier="items" filter="false" /> even when it has sub-levels.</label><br />
	</div>
	</c:if>
	<div id="standardCatHolder">
		<label for="newCatName">New <bean:write name='catName' /> name:</label>
		<html:text styleClass="text" name="categoryAdminForm" property="newCategory.name" value="" maxlength="150" size="32" styleId="newCatName" /><br />

		<logic:notEmpty name="categoryAdminForm" property="newCategory.translations">
			<logic:iterate name="categoryAdminForm" property="newCategory.translations" id="translation" indexId="tIndex">
				<logic:greaterThan name="translation" property="language.id" value="0">
							<label for="newCatName<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label>
							<html:hidden name="categoryAdminForm" property="newCategory.translations[${tIndex}].language.id"/>
							<html:hidden name="categoryAdminForm" property="newCategory.translations[${tIndex}].language.name"/>
							<input type="text" class="text" name="newCategory.translations[<bean:write name='tIndex'/>].name" maxlength="150" size="32" id="newCatName<bean:write name='tIndex'/>" value="<bean:write name="translation" property="name" filter="false"/>"/><br />
						
				</logic:greaterThan>
			</logic:iterate>	
		</logic:notEmpty>
	
		<label>&nbsp;</label>
		<input type="submit" name="addCategory" value="<bright:cmsWrite identifier="button-add" filter="false" />" class="button flush" /><br />
	</div>
	<c:if test="${catExtensionAssetsEnabled}">
		<div id="extendedCatHolder">
			<label>&nbsp;</label>
			<input type="submit" name="addCategory" value="Add..." class="button flush js-enabled-show" /><br />
		</div>
	</c:if>
</form>