<%-- 
	Category fields for asset and search forms.
	
	Pass in:
	assetForm - Reference to a AssetForm.

	History:
	 d1		Steve Bryan		   19-Dec-2005		Created.

--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="showSingleAccessLevel" settingName="showSingleAccessLevel"/>

<c:set var="ctrlHideAndSelectSingleCategory" value="1" scope="request" />
<c:choose>
	<c:when test="${assetForm.asset.entity.onlyAllowDefaultCategory}">
		<table class="form">
			<tr>
				<th>
					<bright:cmsWrite identifier="category-node" filter="false" case="mixed" />:
				</th>
				<td></td>
				<td class="padded">
					<c:set var="categoryList" value="${assetForm.asset.descriptiveCategories}" />
				
					<logic:iterate name="categoryList" id="category">
						<bean:write name='category' property='fullName'/>
					</logic:iterate>
					
				</td>
			</tr>
		</table>
	</c:when>
	<c:when test="${!hideDescriptiveCategories}">
		<logic:equal name="assetForm" property="areCategoriesVisible" value="true">
			<logic:equal name="anyDescriptiveCategories" value="true">
				<%-- Descriptive categories --%>
				
				<bright:applicationSetting id="categoriesDropdowns" settingName="categories-dropdowns"/>
				
				<logic:equal name="categoriesDropdowns" value="true">
					<c:set var="ctrlIsCheckboxControl" value="0" />
				</logic:equal>
				<logic:equal name="categoriesDropdowns" value="false">
					<c:set var="ctrlIsCheckboxControl" value="1" />
				</logic:equal>
				
				<c:set var="topLevelCategories" value="${assetForm.descriptiveCategoryForm.topLevelCategories}" />
				<c:set var="subCategoryIterator" value="${assetForm.descriptiveCategoryForm.subCategoryIterator}" />
				<c:set var="flatCategoryList" value="${assetForm.descriptiveCategoryForm.flatCategoryList}" />
				<c:set var="ctrlTree" value="desc" />
				<c:if test="${!bIsBulkUpdate}">
					<c:set var="ctrlTitle"><bright:cmsWrite identifier="category-root" filter="false" /><c:if test="${!categoriesDropdowns && !bIsSearch && ((!sIsImport && bValidateMandatories && categoriesMandatory) ||(sIsImport && bValidateMandatories && categoriesMandatoryBulk))}"> <span class="required">*</span></c:if></c:set>
				</c:if>
				<c:set var="ctrlCategoryForm" value="${assetForm.descriptiveCategoryForm}" />
				<c:set var="ctrlHideAndSelectSingleCategory" value="0" />
				<c:set var="ctrlPropertyName" value="descriptiveCategoryForm.selectedCategories" />
				<c:set var="bShowOrMessage" value="true"/>
				
				<c:choose>
					<c:when test="${bIsBulkUpdate}">
						<h3><bright:cmsWrite identifier="category-root" filter="false" /></h3>
					
						
						<table class="form catUpdate" cellspacing="0">
							<tr id="changeCatsRow">
								<th>
								<label for="changeExistingCats"><bright:cmsWrite identifier="snippet-change-categories" filter="false" replaceVariables="true" /></label>
								</th>
								<td class="middle">
									<select name="update_categories" id="update_categories" style="width:auto;">
										<option value="skip">- <bright:cmsWrite identifier="snippet-skip" filter="false" /> -</option>
										<option value="replace" <logic:equal name='assetForm' property="updateType(categories)" value="replace">selected</logic:equal>><bright:cmsWrite identifier="snippet-replace" filter="false" /></option>
										<option value="append" <logic:equal name='assetForm' property="updateType(categories)" value="append">selected</logic:equal>><bright:cmsWrite identifier="snippet-append" filter="false" /></option>
										<option value="remove" <logic:equal name='assetForm' property="updateType(categories)" value="remove">selected</logic:equal>><bright:cmsWrite identifier="snippet-remove" filter="false" /></option>
									</select>
								</td>
								
								<td>
									<div id="changeCatsControl">
									<%@include file="../category/category_fields.jsp"%>	
									</div>
								</td>	
							</tr>
						</table>
						
					</c:when>
					<c:otherwise>
						<%@include file="../category/category_fields.jsp"%>	
					</c:otherwise>
				</c:choose>	
				
				
				
				
			
				<%-- See if we want to make category selection mandatory --%>
				<logic:notPresent name="ctrlValidateDescriptiveCats">	
					<%-- Determine from the settings file: --%>			
					<c:choose>
						<c:when test="${(sIsImport && categoriesMandatoryBulk) || (!sIsImport && categoriesMandatory)}">
							<c:set var="ctrlValidateDescriptiveCats" value="1"/>
						</c:when>
						<c:otherwise>
							<c:set var="ctrlValidateDescriptiveCats" value="0"/>
						</c:otherwise>
					</c:choose>
				</logic:notPresent>
	
				<logic:equal name="ctrlValidateDescriptiveCats" value="1">
					<logic:equal name="ctrlIsCheckboxControl" value="1">
							<input type="hidden" name="mandatory_descriptiveCategoryForm.selectedCategories" value="<bright:cmsWrite identifier="failedValidationCategories" replaceVariables="true" />">
					</logic:equal>
					<logic:equal name="ctrlIsCheckboxControl" value="0">
						<script type="text/javascript">
							<!--
								document.write('<input type="hidden" name="mandatory_descriptiveCategoryForm.categoryIds" value="<bright:cmsWrite identifier="failedValidationCategories" replaceVariables="true" />">');
							-->
						</script>
					</logic:equal>
				</logic:equal>
			</logic:equal>
		</logic:equal>
	</c:when>
</c:choose>
	
	<c:if test="${!hidePermissionCategories}">

		<%-- There is no test here analagous to <logic:equal name="assetForm" property="areCategoriesVisible" value="true">
			 because this would break some places that include this JSP.  For example when adding an asset there always need to be some access levels set.
			 Therefore only "Edit" actions can actually hide the Access Levels setting the hidePermissionCategoriesOnEdit var.
			 --%>
		<c:choose>
			<c:when test="${!hidePermissionCategoriesOnEdit}">
		
				 <c:set scope="request" var="ctrlAvailableSubtitle"><bright:cmsWrite identifier="available-access-levels-label" filter="false" removeLinebreaks="true" /></c:set>
			
				<%-- Permission categories --%>
				<bright:applicationSetting id="accessLevelsDropdowns" settingName="access-levels-dropdowns"/>
				
				<c:set var="topLevelCategories" value="${assetForm.permissionCategoryForm.topLevelCategories}" />
				<c:set var="subCategoryIterator" value="${assetForm.permissionCategoryForm.subCategoryIterator}" />
				<c:set var="flatCategoryList" value="${assetForm.permissionCategoryForm.flatCategoryList}" />
				<c:set var="ctrlTree" value="perm" />
				<c:if test="${!bIsBulkUpdate}">
					<c:set var="ctrlTitle"><bright:cmsWrite identifier="access-level-root" filter="false" /><c:if test="${!bIsSearch && bValidateMandatories && !accessLevelsDropdowns}"> <span class='required'>*</span></c:if></c:set>
				</c:if>
					
				<c:set var="ctrlCategoryForm" value="${assetForm.permissionCategoryForm}" />
				<c:set var="ctrlHideAndSelectSingleCategory" value="1" />
				<c:set var="ctrlPropertyName" value="permissionCategoryForm.selectedCategories" />
				<c:set var="catForm" value="${assetForm.permissionCategoryForm}"/>
				<bean:size id="numAccessLevels" name="flatCategoryList" />
				
				<%-- Subtract 1 to discount the very top level cat (root Access Level) always include when isAdmin --%>
				<c:if test="${bIsBulkUpdate && userprofile.isAdmin}">
					<c:set var="numAccessLevels" value="${numAccessLevels-1}"/>
				</c:if>
		
				<%-- Set ctrlIsCheckboxControl to 1 for checkboxes or 0 for dropdowns. Gets from a setting --%>
				<%-- Note this may be overridden to a checkbox control later if there is a single category which is hidden --%>
				<logic:equal name="accessLevelsDropdowns" value="true">
					<c:set var="ctrlIsCheckboxControl" value="0" />
				</logic:equal>
				<logic:equal name="accessLevelsDropdowns" value="false">
					<c:set var="ctrlIsCheckboxControl" value="1" />
				</logic:equal>
		
				<c:set var="bShowOrMessage" value="false"/>
				
				<c:if test="${bIsBulkUpdate && numAccessLevels>1}">
		
					<div class="hr"></div>
					<h3><bright:cmsWrite identifier="access-level-root" filter="false" /></h3>
					<p><strong>Note:</strong> all assets must be in at least one access level.</p>
					
					<table class="form catUpdate" cellspacing="0">
						<tr>
							<th>
								<bright:cmsWrite identifier="snippet-change-access-levels" filter="false" replaceVariables="true" />
							</th>
							<td class="middle">
								<select name="update_accessLevels" id="update_accessLevels" style="width:auto;">
									<option value="skip">- <bright:cmsWrite identifier="snippet-skip" filter="false" /> -</option>
									<option value="replace" <logic:equal name='assetForm' property="updateType(accessLevels)" value="replace">selected</logic:equal>><bright:cmsWrite identifier="snippet-replace" filter="false" /></option>
									<option value="append" <logic:equal name='assetForm' property="updateType(accessLevels)" value="append">selected</logic:equal>><bright:cmsWrite identifier="snippet-append" filter="false" /></option>
									<option value="remove" <logic:equal name='assetForm' property="updateType(accessLevels)" value="remove">selected</logic:equal>><bright:cmsWrite identifier="snippet-remove" filter="false" /></option>
								</select>
							</td>	 
							<td>
				</c:if>
				
				<%@include file="../category/category_fields.jsp"%>
				
				<c:if test="${bIsBulkUpdate && numAccessLevels>1}">
							</td>
						</tr>
					</table>
				</c:if>
				
				<c:if test="${bIsBulkUpdate && numAccessLevels==1}">
					<input type="hidden" name="update_accessLevels" id="update_accessLevels" value="replace">
			 	</c:if>
		
				<%-- Make the permission category selection mandatory --%>
				<logic:notPresent name="ctrlValidatePermissionCats">
					<c:set var="ctrlValidatePermissionCats" value="1"/>
				</logic:notPresent>
				
				<logic:equal name="ctrlValidatePermissionCats" value="1">
					<logic:equal name="showSingleAccessLevel" value="false">
						<logic:equal name="ctrlIsCheckboxControl" value="1">
							<input type="hidden" name="mandatory_permissionCategoryForm.selectedCategories" value="<bright:cmsWrite identifier="failedValidationAccessLevels" replaceVariables="true" />">
						</logic:equal>
						<logic:equal name="ctrlIsCheckboxControl" value="0">
							<input type="hidden" name="mandatory_permissionCategoryForm.categoryIds" value="<bright:cmsWrite identifier="failedValidationAccessLevels" replaceVariables="true" />">
						</logic:equal>
					</logic:equal>
				</logic:equal>
		
				<logic:equal name="ctrlIsCheckboxControl" value="1">
					<script type="text/javascript">
					//<!--
						setPermCatsAreCheckboxes(true);
					//-->
					</script>
				</logic:equal>
				
			</c:when>
			
			<c:otherwise>
				<html:hidden name="assetForm" property="permissionCategoryForm.categoryIds"/>
			</c:otherwise>
		</c:choose>
			
	</c:if>
