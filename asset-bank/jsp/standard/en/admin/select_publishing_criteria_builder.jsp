<%@include file="../inc/doctype_html.jsp" %>

	<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
	<%-- History:
		 d1		James Home			03-Oct-2008		Created.
	--%>
	<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
	<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
	<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
	<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
	<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

	<head>
		
		<title><bright:cmsWrite identifier="company-name" filter="false" /> | Select Criteria</title>  
		<%@include file="../inc/head-elements.jsp"%>
	
		<script src="../js/category.js" type="text/javascript"></script>
		
	
		<script src="../js/keywordChooser.js" type="text/javascript"></script>
	
		
		<bean:size name="searchBuilderForm" property="clauses" id="numClauses"/>
		
		<bean:define name="searchBuilderForm" property="assetAttributes" id="attributes" toScope="request"/>
		<bean:define name="searchBuilderForm" property="operators" id="operators" toScope="request"/>	
		<bean:define id="assetForm" name="searchBuilderForm" toScope="request"/>
		<bean:define id="searchForm" name="searchBuilderForm" toScope="request"/>
		<bean:define id="bIsSearch" value="true" toScope="request"/>	
	
		<%-- Set up category javascript --%>
		<c:set var="ctrlIsCheckboxControl" value="0" scope="request"/>
		<%@include file="../category/inc_asset_category_head_js.jsp"%>
		
		<%@include file="../inc/search_builder_js.jsp"%>
	
		<bean:define id="section" value="batch"/>
		<bean:define id="helpsection" value="batch-batchupdate"/>
		<script type="text/JavaScript">
			$j(function() {
				initDatePicker();
				
				$j('#cancel-link').click(function() {
					window.close();
				})
			});
		</script>	
	
	</head>

	<body class="popup id="uploadPage" onload="setDescSelectedCategories(); setPermSelectedCategories(); setCatIdsFields(); ">
	

		
			<div id="content">
				      
				<div id="mainCol" class="clearfix">
	
					<h1 class="underline">Select Criteria</h1> 
	
					<div style="float:right">
						<p><a href="<c:out value="../action/viewSelectCriteria?searchBuilder=false&selectedCriteriaSessionKey=${searchForm.selectedCriteriaSessionKey}&selectedFilter.id=${searchForm.selectedFilter.id}"/>"><bright:cmsWrite identifier="link-use-search-form" filter="false"/> &raquo;</a></p>
					</div>
	
					<logic:equal name="searchBuilderForm" property="hasErrors" value="true">
						<div class="error">
						<logic:iterate name="searchBuilderForm" property="errors" id="error">
							<bright:writeError name="error" /><br />
						</logic:iterate>
						</div>
					</logic:equal>
					
					<html:form action="selectCriteriaBuilder" method="post">
						<html:hidden name="searchBuilderForm" property="selectedCriteriaSessionKey" />						
						<c:set var="submitButtonText" value="Save and Close"  scope="request" />						
						<%@include file="../inc/search_builder_fields.jsp"%>	
						
						<a id="cancel-link" href="cancelSelectCriteria" class="cancelLink" >Cancel</a>					
					</html:form>
	
				</div>   <!-- End of mainCol -->
			</div><!-- End of content -->
			<div class="clearing"><!--  --></div>

	</body>
</html>