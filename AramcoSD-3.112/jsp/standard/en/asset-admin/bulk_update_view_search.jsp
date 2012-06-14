<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson		06-Jul-20046	Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="searchBuilderEnabled" settingName="search-builder-enabled"/>

<head>
	
	<title><bright:cmsWrite identifier="title-bulk-update" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<script src="../js/category.js" type="text/javascript"></script>
	
	<script src="../js/keywordChooser.js" type="text/javascript"></script>

	<bean:define id="assetForm" name="searchForm" scope="request" />

	<%-- Set up category javascript --%>
	<c:set var="ctrlIsCheckboxControl" value="0" scope="request"/>
	<%@include file="../category/inc_asset_category_head_js.jsp"%>

	<bean:define id="section" value="batch"/>
	<bean:define id="helpsection" value="batch-bulkupdate"/>
	<script type="text/JavaScript">
		// give the keywords field the focus once the dom is ready
		$j(function () {
  			$j('#keywords_field').focus();
 		});


		//Set up global variables for any map attributes
		var sLat = "";
		var wLng = "";
		var nLat = "";
		var eLng = "";
		
		var $whichAttribute = ""; 			//This is a jquery element that acts as a referenece for which attribute we are dealing with
		
		$j(function(){
			searchMapPopupInit();	
			initDatePicker();
			
			// collapsible headings 
			attrHeadings.init('#searchForm');
		})

	</script>	
</head>

<body id="uploadPage" onload="setDescSelectedCategories(); setPermSelectedCategories(); setCatIdsFields(); ">
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-bulk-update-search" filter="false" /></h1> 
	
	<c:if test="${searchBuilderEnabled}">
		<c:set var="url" value="../action/bulkUpdateViewSearch?searchBuilder=true&selectedFilter.id=${searchForm.selectedFilter.id}"/>
		<p id="searchBuildLink" class="js-enabled-show" style="float:right; margin-left: 1em; "><a href="<c:out value="${url}" escapeXml="false"/>"><bright:cmsWrite identifier="link-use-search-builder" filter="false"/> &raquo;</a></p>
	</c:if>
	<logic:equal name="searchForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="searchForm" property="errors" id="error">
				<bean:write name="error" filter="false"/><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<bright:cmsWrite identifier="intro-batch-specify-items" filter="false"/>
	
	<html:form action="createNewBulkUpdateFromSearch" method="post" >
	
		<bean:define id="bIsSearch" value="true" scope="request"/>
		<bean:define id="isBatchOperation" value="true" scope="request"/>
		<jsp:include page="../inc/search_fields.jsp" />
	
	</html:form>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>