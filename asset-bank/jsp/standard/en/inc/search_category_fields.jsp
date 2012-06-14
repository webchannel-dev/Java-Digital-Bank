<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<div class="hr"></div>
   
<c:set var="ctrlHeading"><bright:cmsWrite identifier="subhead-search-by" filter="false" removeLinebreaks="true" /></c:set>
<c:set var="ctrlAvailableSubtitle" scope="request"><bright:cmsWrite identifier="label-available" filter="false" removeLinebreaks="true" /></c:set>
<c:set var="ctrlIncludedSubtitle" scope="request"><bright:cmsWrite identifier="label-included-in-search" filter="false" removeLinebreaks="true" /></c:set>
<c:set var="ctrlValidatePermissionCats" value="0" scope="request"/>
<c:set var="ctrlValidateDescriptiveCats" value="0" scope="request"/>
<c:set var="anyDescriptiveCategories" value="false" scope="request"/>
<c:set var="anyPermissionCategories" value="false" scope="request"/>
<logic:notEmpty name="searchForm" property="descriptiveCategoryForm.topLevelCategories">
	<c:set var="anyDescriptiveCategories" value="true" scope="request"/>
</logic:notEmpty>
<logic:notEmpty name="searchForm" property="permissionCategoryForm.topLevelCategories">
	<bean:size id="numPermissionCats" name="searchForm" property="permissionCategoryForm.topLevelCategories" />
	<c:set var="anyPermissionCategories" value="${numPermissionCats>1}" scope="request" />
</logic:notEmpty>

<!-- Hidden field to contain the ids of the selected categories: -->
<script type="text/javascript">
<!--
	document.write('<html:hidden name="searchForm" property="descriptiveCategoryForm.categoryIds"/>');
	document.write('<html:hidden name="searchForm" property="permissionCategoryForm.categoryIds"/>');
-->
</script>

<bean:define id="assetForm" name="searchForm" toScope="request"/>
<jsp:include page="../category/inc_asset_category_fields.jsp"/>

<c:if test="${anyDescriptiveCategories || anyPermissionCategories}">
	<p style="margin-top:1em;">
		<span class="inline"><bright:cmsWrite identifier="snippet-show-items-in" filter="false"/></span>
		<html:select name="searchForm" property="orCategories">
			<option value="true" <c:if test="${searchForm.refineSearch && userprofile.searchCriteria.orCategories}">selected</c:if>><bright:cmsWrite identifier="snippet-any" filter="false"/></option>
			<option value="false" <c:if test="${searchForm.refineSearch && !userprofile.searchCriteria.orCategories}">selected</c:if>><bright:cmsWrite identifier="snippet-all" filter="false"/></option>
		</html:select> 
		<c:choose>
			<c:when test="${anyPermissionCategories}">
				<span class="inline"><bright:cmsWrite identifier="snippet-of-selected-cats-als" filter="false"/></span>
			</c:when>
			<c:otherwise>
				<span class="inline"><bright:cmsWrite identifier="snippet-of-selected-cats" filter="false"/></span>
			</c:otherwise>	
		</c:choose>
	</p>
	<p>
		<input type="checkbox" name="includeImplicitCategoryMembers" class="checkbox" id="includeImplicitCategoryMembers" <c:if test="${!searchForm.refineSearch || searchForm.refineSearch && userprofile.searchCriteria.includeImplicitCategoryMembers}">checked="checked"</c:if> /><label for="includeImplicitCategoryMembers"><bright:cmsWrite identifier="label-search-subcategories" filter="false"/></label>
	</p>	
</c:if>

<c:if test="${userprofile.isAdmin && anyDescriptiveCategories}">
	<p>
		<input type="checkbox" name="withoutCategory" class="checkbox" id="withoutCategory" <c:if test="${searchForm.refineSearch && userprofile.searchCriteria.withoutCategory}">checked="checked"</c:if> /><label for="withoutCategory"><bright:cmsWrite identifier="label-items-not-in-cat" filter="false"/></label>
	</p>		
</c:if>						

<c:if test="${anyDescriptiveCategories || anyPermissionCategories}">							
	<div class="hr"></div>
</c:if>