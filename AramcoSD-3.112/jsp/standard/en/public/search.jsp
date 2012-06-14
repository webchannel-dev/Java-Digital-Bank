<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		02-Feb-2004		Created.
	 d2		Tamora James	12-May-2005		Integrated with UI design for Demo
     d3     Ben Browning	07-Feb-2006		Tidy up html
	 d4		Matt Stevenson	23-Nov-2007		Added refine your search functionality
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="savedSearchesEnabled" settingName="saved-searches-enabled"/>
<bright:applicationSetting id="searchBuilderEnabled" settingName="search-builder-enabled"/>
<bright:applicationSetting id="rootCatId" settingName="browsing-root-category-id1"/>
<bright:applicationSetting id="autoCompleteQuickSearchEnabled" settingName="auto-complete-quick-search-enabled"/>
<bright:applicationSetting id="batchReleasesEnabled" settingName="batch-releases-enabled"/>
<head>
	
	<title><bright:cmsWrite identifier="title-search" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="search"/>

	<logic:present name="rootCatId">
	<logic:notEmpty name="rootCatId">
		<script type="text/javascript">
			<!--
				var iRootId = <c:out value='${rootCatId}'/>;
			-->
		</script>
	</logic:notEmpty>
	</logic:present>

	<script src="../js/category.js" type="text/javascript"></script>
	
	<script src="../js/keywordChooser.js" type="text/javascript"></script>

	<script type="text/JavaScript">
	
		//Set up global variables for any map attributes
		var sLat = "";
		var wLng = "";
		var nLat = "";
		var eLng = "";

		var $whichAttribute = ""; 			//This is a jquery element that acts as a referenece for which attribute we are dealing with
		
	
		//Initialise autocomplete, give quick search the focus when dom is ready
		$j(function () {
  			$j('#keywords_field').focus();
  			<c:if test="${autoCompleteQuickSearchEnabled}">
				//initialise jquery autocompleter on keywords field: BB-AutoComplete - hide this if quicksearch autocomplete disabled!
				initJQAutocompleter($j('#keywords_field'));
			</c:if>
			
			//Listen for map popups
			searchMapPopupInit();
			initDatePicker();
			
			// collapsible headings 
			attrHeadings.init('#searchForm');


 		});
	</script>	
	<c:set var="assetForm" value="${searchForm}" scope="request" />

	<%-- Set up category javascript --%>
	<c:set var="tabId" value="advancedSearch" scope="request" />
	<c:set var="ctrlIsCheckboxControl" value="0" scope="request" />
	<%@include file="../category/inc_asset_category_head_js.jsp"%>

</head>

<body id="searchPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<c:choose>
		<c:when test="${(savedSearchesEnabled || batchReleasesEnabled) && userprofile.isLoggedIn}">
			<%@include file="inc_search_tabs.jsp"%>
		</c:when>
		<c:otherwise>
			<h1 class="underline">
				<bright:cmsWrite identifier="heading-search" filter="false" />
				<c:if test="${not empty searchForm.relationName}">
					(<bright:cmsWrite identifier="link-find" filter="false"/> <bean:write name="searchForm" property="relationName" filter="false"/>)
				</c:if>
			</h1>
		</c:otherwise>
	</c:choose> 
	
	<c:if test="${searchBuilderEnabled}">
		<c:set var="url" value="../action/viewSearchBuilder?selectedFilter.id=${searchForm.selectedFilter.id}"/>
		<c:if test="${searchForm.entityPreSelected}">
			<c:set var="url"><c:out value="${url}" escapeXml="false"/>&amp;entityPreSelected=<c:out value="${searchForm.entityPreSelected}"/></c:set>
			<logic:iterate name="searchForm" property="selectedEntities" id="entityId">
				<c:set var="url"><c:out value="${url}" escapeXml="false"/>&amp;selectedEntities=<c:out value="${entityId}"/></c:set>
			</logic:iterate>
		</c:if>
		<c:set var="url"><c:out value="${url}" escapeXml="false"/>&amp;selectAssetUrl=<c:out value="${searchForm.selectAssetUrl}"/></c:set>
		<c:set var="url"><c:out value="${url}" escapeXml="false"/>&amp;relationName=<c:out value="${searchForm.relationName}"/></c:set>
		<logic:iterate name="searchForm" property="selectAssetParamNames" id="paramName" indexId="paramIndex">
			<logic:notEmpty name="paramName">
				<c:set var="url"><c:out value="${url}" escapeXml="false"/>&amp;selectAssetParamNames[<c:out value="${paramIndex}"/>]=<c:out value="${searchForm.selectAssetParamNames[paramIndex]}"/></c:set>
				<c:set var="url"><c:out value="${url}" escapeXml="false"/>&amp;selectAssetParamValues[<c:out value="${paramIndex}"/>]=<c:out value="${searchForm.selectAssetParamValues[paramIndex]}"/></c:set>
			</logic:notEmpty>
		</logic:iterate>
		<c:set var="url"><c:out value="${url}" escapeXml="false"/>&amp;advancedSearch=1</c:set>
		<a href="<c:out value="${url}" escapeXml="false"/>" id="searchBuildLink" class="js-enabled-show"><bright:cmsWrite identifier="link-use-search-builder" filter="false"/> &raquo;</a> 
	</c:if>

	<p><bright:cmsWrite identifier="advanced-search-intro" filter="false"/></p>
	
	<logic:equal name="searchForm" property="hasErrors" value="true">
		<div class="clearing"></div>
		<div class="error">
			<logic:iterate name="searchForm" property="errors" id="error">
				<bean:write name="error" filter="false"/><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<c:if test="${!searchForm.refineSearch && searchForm.selectedFilter.id > 0}">
		<c:set var="filterName" value="${searchForm.selectedFilter.name}" scope="request" />
		<bright:cmsWrite identifier="filter-search-text" filter="false" replaceVariables="true" />
		<div class="hr"></div>
	</c:if>
	
	<html:form enctype="multipart/form-data" action="search" method="get">
		<html:hidden name="searchForm" property="selectedFilter.id"/>
		
		<c:if test="${searchForm.entityPreSelected}">
			<html:hidden name="searchForm" property="entityPreSelected"/>
			<logic:iterate name="searchForm" property="selectedEntities" id="entityId">
				<input type="hidden" name="selectedEntities" value="<bean:write name='entityId'/>"/>
			</logic:iterate>
		</c:if>
		
		<html:hidden name="searchForm" property="selectAssetUrl"/>
		<html:hidden name="searchForm" property="relationName"/>
		<logic:iterate name="searchForm" property="selectAssetParamNames" id="paramName" indexId="paramIndex">
			<logic:notEmpty name="paramName">
				<html:hidden name="searchForm" property="selectAssetParamNames[${paramIndex}]"/>
				<html:hidden name="searchForm" property="selectAssetParamValues[${paramIndex}]"/>
			</logic:notEmpty>
		</logic:iterate>
		
		<input type="hidden" name="advancedSearch" value="1"/>
		<c:if test="${searchForm.selectedFilter.id > 0}">
			<logic:notEmpty name="searchForm" property="filters">
				<logic:notEmpty name="searchForm" property="selectedFilter.hiddenSearchAttributeValues">
					<logic:iterate name="searchForm" property="selectedFilter.hiddenSearchAttributeValues" id="attVal">
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
	
		<c:set var="bIsSearch" value="true" scope="request"/>
	   
		<jsp:include page="../inc/search_fields.jsp"/>	

	</html:form>
	
	<c:if test="${not empty searchForm.selectAssetUrl}">
		<form name="cancelForm" action="<bean:write name="searchForm" property="selectAssetUrl"/>" method="get" id="cancelForm">
			<logic:iterate name="searchForm" property="selectAssetParamNames" id="paramName" indexId="paramIndex">
				<logic:notEmpty name="paramName">
					<input type="hidden" name="<c:out value="${searchForm.selectAssetParamNames[paramIndex]}"/>" value="<c:out value="${searchForm.selectAssetParamValues[paramIndex]}"/>"/>
				</logic:notEmpty>
			</logic:iterate>
			<input type="hidden" name="b_cancel" value="<bright:cmsWrite identifier="button-cancel" filter="false"/>" />
			<a href="#" onclick="$j('#cancelForm').submit(); return false;" style="display:none" class="cancelLink js-enabled-show"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
			<input type="submit" name="b_cancel" value="<bright:cmsWrite identifier="button-cancel" filter="false"/>" class="button floated js-enabled-hide" />
		</form>
	</c:if>
								
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>