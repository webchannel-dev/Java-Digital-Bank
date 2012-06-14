<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson		06-Mar-2004		Created.
	 d2		Matt Stevenson		03-Aug-2004		Added extra search fields tidied presentation
	 d3      Ben Browning   	14-Feb-2006    HTML/CSS tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="searchBuilderEnabled" settingName="search-builder-enabled"/>

<head>
	
	<title><bright:cmsWrite identifier="title-batch-update" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<script src="../js/category.js" type="text/javascript"></script>
	
	<script src="../js/keywordChooser.js" type="text/javascript"></script>

	<bean:define id="assetForm" name="searchForm" />

	<%-- Set up category javascript --%>
	<c:set var="ctrlIsCheckboxControl" value="0" scope="request"/>
	<%@include file="../category/inc_asset_category_head_js.jsp"%>

	<bean:define id="section" value="batch"/>
	<bean:define id="helpsection" value="batch-batchupdate"/>
	<script type="text/JavaScript">
		// give the keywords field the focus once the dom is ready
		$j(function () {
  			$j('#keywords_field').focus();
			
			//Set up global variables for any map attributes
			var sLat = "";
			var wLng = "";
			var nLat = "";
			var eLng = "";

			var $whichAttribute = ""; 			//This is a jquery element that acts as a referenece for which attribute we are dealing with
			
			searchMapPopupInit();
			initDatePicker();
			
			// collapsible headings 
			attrHeadings.init('#searchForm');
			
 		});
	</script>	
	
</head>

<body id="uploadPage" onload="setDescSelectedCategories(); setPermSelectedCategories(); setCatIdsFields(); ">
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-batch-update" filter="false" /></h1> 
	
	<c:if test="${searchBuilderEnabled}">
		<c:set var="url" value="../action/viewBatchUpdate?searchBuilder=true&selectedFilter.id=${searchForm.selectedFilter.id}"/>
		<script type="text/javascript">//<!--
			document.write('<div style="float:right;">');
			document.write('<p><a href="<c:out value="${url}" escapeXml="false"/>"><bright:cmsWrite identifier="link-use-search-builder" filter="false"/> &raquo;</a></p>');
			document.write('</div>');
		//--></script>
	</c:if>
	
	<bright:cmsWrite identifier="intro-batch-specify-items" filter="false"/>
	
	<c:if test="${userprofile.searchCriteria!=null}"><bright:cmsWrite identifier="intro-batch-use-search" filter="false"/></c:if>
	
	<bean:define id="bMore" value="false"/>
	<logic:present name="userprofile" property="batchUpdate">
		<logic:equal name="userprofile" property="batchUpdate.hasNext" value="true">
			<bean:define id="bMore" value="true"/>
		</logic:equal>
	</logic:present>
	<logic:equal name="bMore" value="true">
		<a href="viewUpdateNextImage?resume=true">Return to current update</a>.<br />
	</logic:equal>
	
	<logic:equal name="searchForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="searchForm" property="errors" id="error">
				<bright:writeError name="error" />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<html:form action="createNewBatch" method="post">
		<input type="hidden" name="restart" value="true">
	
		<bean:define id="bIsSearch" value="true"/>
		<bean:define id="isBatchOperation" value="true"/>
		<%@include file="../inc/search_fields.jsp"%>
	
	</html:form>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>