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


<head>
	
	<title><bright:cmsWrite identifier="title-bulk-update" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<script src="../js/category.js" type="text/javascript"></script>
	
	<script src="../js/keywordChooser.js" type="text/javascript"></script>

	<bean:define name="searchBuilderForm" property="assetAttributes" id="attributes"/>
	<bean:define name="searchBuilderForm" property="operators" id="operators"/>
	<bean:size name="searchBuilderForm" property="clauses" id="numClauses"/>
	<bean:define id="assetForm" name="searchBuilderForm" />
	<bean:define id="searchForm" name="searchBuilderForm"/>
	<bean:define id="bIsSearch" value="true"/>

	<%-- Set up category javascript --%>
	<c:set var="ctrlIsCheckboxControl" value="0" scope="request"/>
	<%@include file="../category/inc_asset_category_head_js.jsp"%>

	<%@include file="../inc/search_builder_js.jsp"%>

	<bean:define id="section" value="batch"/>
	<bean:define id="helpsection" value="batch-bulkupdate"/>
	<script type="text/JavaScript">
		$j(function () {
			initDatePicker();
		});
	</script>
</head>

<body id="uploadPage" onload="setDescSelectedCategories(); setPermSelectedCategories(); setCatIdsFields(); ">
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-bulk-update-search" filter="false" /></h1> 
	
	<div style="float:right">
		<p><a href="<c:out value="../action/bulkUpdateViewSearch?searchBuilder=false&selectedFilter.id=${searchForm.selectedFilter.id}"/>"><bright:cmsWrite identifier="link-use-search-form" filter="false"/> &raquo;</a></p>
	</div>
	
	<logic:equal name="searchBuilderForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="searchBuilderForm" property="errors" id="error">
				<bean:write name="error" filter="false"/>
			</logic:iterate>
		</div>
	</logic:equal>
	
	<bright:cmsWrite identifier="intro-batch-specify-items" filter="false"/>
	
	<html:form action="createNewBulkUpdateFromSearchBuilder" method="post">
	
		<bean:define id="bIsSearch" value="true"/>
		<bean:define id="isBatchOperation" value="true"/>
		<%@include file="../inc/search_builder_fields.jsp"%>
	
	</html:form>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>