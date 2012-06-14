<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bright:applicationSetting id="searchPreviousVersions" settingName="allow-search-for-previous-versions"/>
<bright:applicationSetting id="searchPreviousVersionsAdminOnly" settingName="allow-search-for-previous-versions-admin-only"/>
<bright:applicationSetting id="canCreateAssetVersions" settingName="can-create-asset-versions"/>
<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>

<html:hidden name="searchBuilderForm" property="selectedFilter.id"/>
		
<c:if test="${searchForm.entityPreSelected}">
	<html:hidden name="searchBuilderForm" property="entityPreSelected"/>
	<logic:iterate name="searchForm" property="selectedEntities" id="entityId">
		<input type="hidden" name="selectedEntities" value="<bean:write name='entityId'/>"/>
	</logic:iterate>
</c:if>

<html:hidden name="searchBuilderForm" property="selectAssetUrl"/>
<html:hidden name="searchBuilderForm" property="relationName"/>
<logic:iterate name="searchBuilderForm" property="selectAssetParamNames" id="paramName" indexId="paramIndex">
	<logic:notEmpty name="paramName">
		<html:hidden name="searchBuilderForm" property="selectAssetParamNames[${paramIndex}]"/>
		<html:hidden name="searchBuilderForm" property="selectAssetParamValues[${paramIndex}]"/>
	</logic:notEmpty>
</logic:iterate>

<input type="hidden" name="advancedSearch" value="1"/>
<c:if test="${searchForm.selectedFilter.id > 0}">
	<logic:notEmpty name="searchBuilderForm" property="filters">
		<logic:notEmpty name="searchBuilderForm" property="selectedFilter.hiddenSearchBuilderAttributeValues">
			<logic:iterate name="searchBuilderForm" property="selectedFilter.hiddenSearchBuilderAttributeValues" id="attVal">
				<c:choose>
					<c:when test="${attVal.attribute.isTextfield || attVal.attribute.isTextarea || attVal.attribute.isDropdownList || attVal.attribute.isCheckList || attVal.attribute.isOptionList}">
						<input type="hidden" name="attribute_<bean:write name='attVal' property='attribute.id'/>" value="<bean:write name='attVal' property='value' filter='false'/>">
					</c:when>
					<c:when test="${attVal.attribute.isDatepicker || attVal.attribute.isDateTime}">
						<input type="hidden" name="attribute_<bean:write name='attVal' property='attribute.id'/>_lower" value="<bean:write name='attVal' property='value'/>">
						<input type="hidden" name="attribute_<bean:write name='attVal' property='attribute.id'/>_upper" value="<bean:write name='attVal' property='value'/>">
					</c:when>
				</c:choose>
			</logic:iterate>
		</logic:notEmpty>
	</logic:notEmpty>
</c:if>

<ul class="searchBuild">
	<c:set var="firstHidden" value="true"/>
	<logic:iterate name="searchBuilderForm" property="clauses" id="clause" indexId="index">
		<c:choose>
			<c:when test="${clause.isHidden}">
				<input type="hidden" id="clauseHidden<c:out value='${index}'/>" name="clauses[<c:out value="${index}"/>].hidden" value="<c:out value="${clause.isHidden}"/>"/>
				<input type="hidden" id="clauseVisible<c:out value="${index}"/>" name="clauses[<c:out value="${index}"/>].visible" value="<c:out value="${clause.visible}"/>"/>
				<input type="hidden" name="clauses[<c:out value='${index}'/>].field" value="<c:out value='${clause.field}'/>"/>
				<input type="hidden" name="clauses[<c:out value="${index}"/>].delimiterIsSpace" value="<c:out value="${clause.delimiterIsSpace}"/>"/>
				<input type="hidden" name="clauses[<c:out value="${index}"/>].tokenDelimiterRegex" value="<c:out value="${clause.tokenDelimiterRegex}"/>"/>
				<input type="hidden" name="clauses[<c:out value='${index}'/>].operatorId" value="<c:out value='${clause.operatorId}'/>"/>
				<input type="hidden" name="clauses[<c:out value='${index}'/>].value" value="<c:out value="${clause.value}"/>"/>
				<input type="hidden" name="clauses[<c:out value='${index}'/>].orClause"  value="<c:out value='${clause.orClause}'/>"/>
				<c:if test="${firstHidden}">
					<bright:cmsWrite identifier="snippet-hidden-clause" filter="false"/>
				</c:if>
				<c:set var="firstHidden" value="false"/>
			</c:when>
			<c:otherwise>
		<li id="clause<c:out value="${index}"/>" <c:if test="${!clause.visible}">style="display: none;"</c:if>>
	
			<input type="hidden" id="clauseVisible<c:out value="${index}"/>" name="clauses[<c:out value="${index}"/>].visible" value="<c:out value="${clause.visible}"/>"/>
	
			<c:if test="${index>0}">
				<html:select styleId="conjunction${index}" name="searchBuilderForm" property="clauses[${index}].orClause" onchange="checkConjunction(${index});" styleClass="selectOperator">
					<html:option value="false"><bright:cmsWrite identifier="snippet-boolean-and" filter="false"/></html:option>
					<html:option value="true"><bright:cmsWrite identifier="snippet-boolean-or" filter="false"/></html:option>
				</html:select>
				<br />
			</c:if>

			<html:select name="searchBuilderForm" property="clauses[${index}].field" styleId="attributeId${index}" onchange="doAttributeSelection(this,${index});">
							
				<c:choose>
					<c:when test="${not empty attributes}">
						<optgroup label="-----------------------------------------">
							<option value="-1" selected="selected">- <bright:cmsWrite identifier="snippet-please-select" filter="false"/> -</option>
							<html:option value="0"><bright:cmsWrite identifier="snippet-general-keywords" filter="false"/></html:option>
						</optgroup>
						<optgroup label="-----------------------------------------">
							<logic:iterate name="attributes" id="attribute"> 
								<c:choose>
									<c:when test="${attribute.isSpatialArea}">
										<html:option value="${attribute.id}_west"><c:out value="${attribute.label}" />: <bright:cmsWrite identifier="label-spatial-area-west"/></html:option>
										<html:option value="${attribute.id}_south"><c:out value="${attribute.label}" />: <bright:cmsWrite identifier="label-spatial-area-south"/></html:option>
										<html:option value="${attribute.id}_east"><c:out value="${attribute.label}" />: <bright:cmsWrite identifier="label-spatial-area-east"/></html:option>
										<html:option value="${attribute.id}_north"><c:out value="${attribute.label}" />: <bright:cmsWrite identifier="label-spatial-area-north"/></html:option>
									</c:when>
									<c:otherwise>
										<html:option value="${attribute.id}"><c:out value="${attribute.label}" /></html:option>
									</c:otherwise>
								</c:choose>
							</logic:iterate>
						</optgroup>
					</c:when>
					<c:otherwise>
						<html:option value="0"><bright:cmsWrite identifier="snippet-general-keywords" filter="false"/></html:option>
					</c:otherwise>
				</c:choose>
			</html:select>
	
			<html:select styleId="operator${index}" name="searchBuilderForm" property="clauses[${index}].operatorId" styleClass="selectClause">
				<html:optionsCollection name="operators" value="id" label="name"/>
			</html:select>
	
			<span id="valueContainer<c:out value="${index}"/>">
				<input type="text" name="clauses[<c:out value="${index}"/>].value" class="text" value="<c:out value="${clause.value}"/>" size="40"/>
			</span>
			
			<c:if test="${index>0}">
				<span id="removeClause<c:out value="${index}"/>" <c:if test="${index+1==numClauses || searchBuilderForm.clauses[index+1].visible}">style="display:none;"</c:if>><a href="#" onclick="removeClause(); return false;"><bright:cmsWrite identifier="link-remove-lowercase" filter="false"/></a></span>
			</c:if>
	
			<script type="text/javascript">
			//<!--
				doAttributeSelection(document.getElementById('attributeId<c:out value="${index}"/>'),<c:out value="${index}"/>);
			//-->
			</script>
	
		</li>
			</c:otherwise>
		</c:choose>
	
	</logic:iterate>
</ul>

<div id="addClauseDiv">
	<a href="javascript:;" onclick="newClause();"><bright:cmsWrite identifier="link-add-search-builder-clause" filter="false"/> &raquo;</a>
</div>

<br/>

<div class="error" id="conjunctionChangedInfo" style="display: none;">
	<bright:cmsWrite identifier="snippet-search-builder-query-changed" filter="false"/>
</div>

<c:if test="${not empty searchForm.entities}">
	<bean:size id="numEntities" name="searchForm" property="entities"/>
</c:if>
<c:if test="${not empty searchForm.languages}">
	<bean:size name="searchForm" property="languages" id="numLanguages"/>
</c:if>

<c:if test="${not empty searchForm.entityName || (userprofile.isAdmin && supportMultiLanguage && numLanguages>0) || (canCreateAssetVersions && searchPreviousVersions && (!searchPreviousVersionsAdminOnly || userprofile.isAdmin)) || (not searchForm.entityPreSelected && (numEntities>1 || (numEntities==1 && userprofile.isAdmin)))}">
	<div class="hr"></div>
	
	<table class="form" cellspacing="0" cellpadding="0" border="0" summary="Form for search builder">
		<c:if test="${not empty searchForm.entityName}">
			<tr>
				<th style="padding-top: 2px;"><bright:cmsWrite identifier="label-asset-type" filter="false"/></th>
				<td><bean:write name="searchForm" property="entityName"/></td>
			</tr>
		</c:if>
		
		<%-- Asset Entity (type) field --%>
		<%@include file="../inc/search_entity_field.jsp" %>
		
		<c:if test="${userprofile.isAdmin}">
			<%-- Language selection field --%>
			<%@include file="../inc/search_language_field.jsp" %>	
		</c:if>
		
		<c:if test="${canCreateAssetVersions && searchPreviousVersions && (!searchPreviousVersionsAdminOnly || userprofile.isAdmin)}">
			<tr>
				<th>
					<label for="includePrevious"><bright:cmsWrite identifier="label-include-previous-versions" filter="false"/></label>
				</th>
				<td>
					<input type="checkbox" class="checkbox" name="includePreviousVersions" <c:if test="${searchForm.includePreviousVersions || (searchForm.refineSearch && userprofile.searchCriteria.includePreviousVersions)}">checked</c:if> id="includePrevious"/>
				</td>
			</tr>
		</c:if>
	</table>
</c:if>

<%@include file="search_external_filter.jsp"%>

<%@include file="../inc/search_category_fields.jsp"%>

<c:if test="${empty submitButtonText}">
	<c:set var="submitButtonText">
		 <bright:cmsWrite identifier="button-search" filter="false" />
	</c:set>		
</c:if>

<input type="submit" class="button flush floated" id="submitButton" onClick="removeHiddenClauses();" value="${submitButtonText}" />
