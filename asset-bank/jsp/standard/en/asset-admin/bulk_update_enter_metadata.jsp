<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Chris Preager		24-May-2004		Imported from Image Manager
	 d2		Martin Wilson		06-Sep-2005		Changed for ftp
	 d3		Ben Browning		17-Feb-2006		HTML/CSS Tidy up
	 d4		Matt Stevenson		15-Mar-2006		Added switch to setup asset links
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="title-bulk-update" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<script src="../js/category.js" type="text/javascript"></script>
	
	<script src="../js/keywordChooser.js" type="text/javascript"></script>
	<script src="../js/agreements.js" type="text/javascript"></script>
	<script src="../js/asset-upload.js" type="text/javascript"></script>
	
	<%-- Set up category javascript and jsp variables --%>
	<c:set var="assetForm" scope="request" value="${bulkUpdateForm}"/>	
	<c:set var="ctrlIsCheckboxControl" scope="request" value="0" />
	<%@include file="../category/inc_asset_category_head_js.jsp"%>
	
	<bean:define id="section" value="batch"/>
	<bean:define id="helpsection" value="batch-bulkupdate"/>
	<script type="text/javascript">
	<!-- 

		//Set up global variables for any map attributes
		var sLat = "";
		var wLng = "";
		var nLat = "";
		var eLng = "";
		
		var $whichAttribute = ""; 			//This is a jquery element that acts as a referenece for which attribute we are dealing with
		
		$j(function(){
			popupMapInit();
			initDatePicker();
		})


	//-->
	</script>
</head>

<body id="importPage" onload="setDescSelectedCategories(); setPermSelectedCategories(); setCatIdsFields(); showHideAgreementType(); syncAgreementPreviewButton();">

	<%@include file="../inc/body_start.jsp"%>
	
	<div id="dataLookupCode"></div>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-bulk-update-run-update" filter="false" /></h1> 
	<c:set var="numForUpdate" value="${userprofile.batchUpdateController.numberSelectedForUpdate}" />
	<bright:cmsWrite identifier="intro-bulk-update-metadata" filter="false" replaceVariables="true" />
	
	<h2><bright:cmsWrite identifier="subhead-enter-metadata" filter="false"/></h2>
	<logic:equal name="bulkUpdateForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="bulkUpdateForm" property="errors" id="errorText">
				<bright:writeError name="errorText" /><br />
			</logic:iterate>		
		</div>
	</logic:equal>

	<html:form action="bulkUpdateStart" enctype="multipart/form-data" method="post" styleId="updateForm">
	
		<c:set var="bIsImport" scope="request" value="true"/>
		<c:set var="bIsBulkUpdate" scope="request" value="true" />
		<jsp:include page="inc_fields.jsp"/>

		<div class="hr"></div>
		
		<input type="submit" class="button flush floated" id="submitButton" value="Start Update" /> 
		<a href="viewManageBulkUpdate" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		
	</html:form>

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>