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
<bright:applicationSetting id="rootCatId" settingName="browsing-root-category-id1"/>


<bean:define name="searchBuilderForm" property="searchAttributes" id="attributes" toScope="request" />
<bean:define name="searchBuilderForm" property="operators" id="operators" toScope="request" />
<bean:size name="searchBuilderForm" property="clauses" id="numClauses"/>

<bean:define id="bIsSearch" value="true" toScope="request" />

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Search</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="search"/>
	<bean:define id="helpsection" value="search_builder"/>
	<logic:present name="rootCatId">
	<logic:notEmpty name="rootCatId">
		<script type="text/javascript">
			//<!--
				var iRootId = <c:out value='${rootCatId}'/>;
			//-->
		</script>
	</logic:notEmpty>
	</logic:present>

	<script src="../js/category.js" type="text/javascript"></script>
	
	<script src="../js/keywordChooser.js" type="text/javascript"></script>

	<bean:define id="assetForm" name="searchBuilderForm" toScope="request" />
	<bean:define id="searchForm" name="searchBuilderForm" toScope="request" />

	<%-- Set up category javascript --%>
	<c:set var="tabId" value="advancedSearch" />
	<c:set var="ctrlIsCheckboxControl" value="0" scope="request"/>
	<%@include file="../category/inc_asset_category_head_js.jsp"%>
	<%@include file="../inc/search_builder_js.jsp"%>
	<script type="text/javascript" charset="utf-8">
		$j(function(){
			initDatePicker();
		})
	</script>
	
	
</head>

<body id="searchPage" onload="<c:if test='${searchBuilderForm.areCategoriesVisible}'>setDescSelectedCategories();</c:if> <c:if test='${batchAssetForm.areAccessLevelsVisible}'>setPermSelectedCategories();</c:if> setCatIdsFields();">

	<%@include file="../inc/body_start.jsp"%>
	
	<c:choose>
		<c:when test="${savedSearchesEnabled && userprofile.isLoggedIn}">
			<%@include file="inc_search_tabs.jsp"%>
		</c:when>
		<c:otherwise>
			<h1 class="underline">
				<bright:cmsWrite identifier="heading-search" filter="false" />
				<c:if test="${not empty searchBuilderForm.relationName}">
					(<bright:cmsWrite identifier="link-find" filter="false"/> <bean:write name="searchBuilderForm" property="relationName" filter="false"/>)
				</c:if>
			</h1>
		</c:otherwise>
	</c:choose>
	
	<c:set var="url" value="../action/viewSearch?selectedFilter.id=${searchBuilderForm.selectedFilter.id}"/>
	<c:if test="${searchBuilderForm.entityPreSelected}">
		<c:set var="url"><c:out value="${url}" escapeXml="false"/>&amp;entityPreSelected=<c:out value="${searchBuilderForm.entityPreSelected}"/></c:set>
		<logic:iterate name="searchBuilderForm" property="selectedEntities" id="entityId">
			<c:set var="url"><c:out value="${url}" escapeXml="false"/>&amp;selectedEntities=<c:out value="${entityId}"/></c:set>
		</logic:iterate>
	</c:if>
	<c:set var="url"><c:out value="${url}" escapeXml="false"/>&amp;selectAssetUrl=<c:out value="${searchBuilderForm.selectAssetUrl}"/></c:set>
	<c:set var="url"><c:out value="${url}" escapeXml="false"/>&amp;relationName=<c:out value="${searchBuilderForm.relationName}"/></c:set>
	<logic:iterate name="searchBuilderForm" property="selectAssetParamNames" id="paramName" indexId="paramIndex">
		<logic:notEmpty name="paramName">
			<c:set var="url"><c:out value="${url}" escapeXml="false"/>&amp;selectAssetParamNames[<c:out value="${paramIndex}"/>]=<c:out value="${searchBuilderForm.selectAssetParamNames[paramIndex]}"/></c:set>
			<c:set var="url"><c:out value="${url}" escapeXml="false"/>&amp;selectAssetParamValues[<c:out value="${paramIndex}"/>]=<c:out value="${searchBuilderForm.selectAssetParamValues[paramIndex]}"/></c:set>
		</logic:notEmpty>
	</logic:iterate>
	<c:set var="url"><c:out value="${url}" escapeXml="false"/>&amp;advancedSearch=1</c:set>

	<a href="<c:out value="${url}" escapeXml="false"/>" id="searchBuildLink" class="js-enabled-show"><bright:cmsWrite identifier="link-use-search-form" filter="false"/> &raquo;</a>

	<c:set var="introText"><bright:cmsWrite identifier="search-builder-intro" filter="false"/></c:set>
	<c:if test="${not empty introText}">
		<p><bright:cmsWrite identifier="search-builder-intro" filter="false"/></p>
	</c:if>

	<logic:equal name="searchBuilderForm" property="hasErrors" value="true">
		<div class="clearing"></div>
		<div class="error">
			<logic:iterate name="searchBuilderForm" property="errors" id="error">
				<bright:writeError name="error" /><br />
			</logic:iterate>
		</div>
	</logic:equal>

	<html:form enctype="multipart/form-data" action="searchWithBuilder" method="post"> 
		<%@include file="../inc/search_builder_fields.jsp"%>
	</html:form>
	


	<c:if test="${not empty searchBuilderForm.selectAssetUrl}">
		<form name="cancelForm" id="cancelForm" action="<bean:write name="searchBuilderForm" property="selectAssetUrl"/>" method="get">
			<logic:iterate name="searchBuilderForm" property="selectAssetParamNames" id="paramName" indexId="paramIndex">
				<logic:notEmpty name="paramName">
					<input type="hidden" name="<c:out value="${searchBuilderForm.selectAssetParamNames[paramIndex]}"/>" value="<c:out value="${searchBuilderForm.selectAssetParamValues[paramIndex]}"/>"/>
				</logic:notEmpty>
			</logic:iterate>
			<input type="hidden" name="b_cancel" value="<bright:cmsWrite identifier="button-cancel" filter="false"/>" />
			<a href="#" onclick="$j('#cancelForm').submit(); return false;" style="display:none" class="cancelLink js-enabled-show"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
			<input type="submit" name="b_cancel" value="<bright:cmsWrite identifier="button-cancel" filter="false"/>" class="button floated js-enabled-hide" />
		</form>
		
	</c:if>
	<br />							
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>