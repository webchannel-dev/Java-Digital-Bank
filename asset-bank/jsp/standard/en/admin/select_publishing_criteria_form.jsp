<%@include file="../inc/doctype_html.jsp" %>

	<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
	<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
	<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
	<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
	<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
	
	<bright:applicationSetting id="searchBuilderEnabled" settingName="search-builder-enabled"/>
	
	<head>
		
		<title><bright:cmsWrite identifier="company-name" filter="false" /> | Select Criteria</title> 
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
				
				$j('#cancel-link').click(function() {
					window.close();
				})
				
	 		});
		</script>	
		
	</head>

	<body id="uploadPage" class="popup" onload="setDescSelectedCategories(); setPermSelectedCategories(); setCatIdsFields(); ">
		

		
	
				      
				<div id="mainCol" class="clearfix">
		
					<h1 class="underline">Select Criteria.</h1> 
									
					<logic:equal name="searchForm" property="hasErrors" value="true">
						<div class="error">
							<logic:iterate name="searchForm" property="errors" id="error">
								<bright:writeError name="error" />
							</logic:iterate>
						</div>
					</logic:equal>
		
					<html:form action="selectCriteria" method="post">
						<html:hidden name="searchForm" property="selectedCriteriaSessionKey" />
						<c:set var="bIsSearch" value="true" scope="request"/>
						<c:set var="submitButtonText" value="Save and Close" scope="request" />
						<jsp:include page="../inc/search_fields.jsp"/>
						
						<a id="cancel-link" href="cancelSelectCriteria" class="cancelLink">Cancel</a>					
					</html:form>
		
				</div>   <!-- End of mainCol -->

			<div class="clearing"><!--  --></div>

	</body>
</html>