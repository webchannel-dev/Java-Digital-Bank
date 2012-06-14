<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	06-Sep-2010		Created
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="title-add-empty-related-assets" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<logic:equal name="importForm" property="isImportInProgress" value="true">
		<noscript>
			<!-- meta refresh for non js users -->
			<meta HTTP-EQUIV="refresh" CONTENT="20;URL=viewAddEmptyRelatedAssetsStatus?id=<bean:write name='importForm' property='asset.id'/>"></meta>
		</noscript>	
		
		<script type="text/javascript">


			$j(function() {
				// call the ajax update function every 2 seconds - this periodically reloads (ARG 1 URL) in ajaxUpdateContent, if any text matches (ARG 2 String) then redirect to (ARG 3 URL)
			  setInterval("ajaxUpdate('viewAddEmptyRelatedAssetsProgress?id=<c:out value="${importForm.asset.id}"/>', 'Import Complete', 'viewAddEmptyRelatedAssetsStatus?id=<c:out value="${importForm.asset.id}"/>')",2000);
			
			});
		</script>

	</logic:equal>

	<bean:define id="helpsection" value="add-empty-related-assets"/>		

</head>

<body id="detailsPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-add-empty-related-assets" filter="false"/></h1>

	<logic:equal name="importForm" property="isImportInProgress" value="false">
		<c:set var="link" value="viewAsset?id=${importForm.asset.id}" />
		<div class="confirm"><bright:cmsWrite identifier="snippet-empty-related-assets-complete" filter="false" replaceVariables="true"/></div>
		
	</logic:equal>
	<logic:notEqual name="importForm" property="isImportInProgress" value="false">
		<h3 class="js-enabled-show progressLoader"><img src="../images/standard/misc/ajax_loader.gif" width="24" height="24" alt="loading..." /><bright:cmsWrite identifier="snippet-empty-related-assets-status" filter="false"/></h3>
	</logic:notEqual>
	
	<logic:notEmpty name="importForm" property="messages">
		<div id="ajaxUpdateContent">
			<!-- If Javascript enabled, ajax fetched progress messages will be shown here. -->
		</div>
		<logic:notEqual name="importForm" property="isImportInProgress" value="false">
			<noscript>
		</logic:notEqual>
			<h3><bright:cmsWrite identifier="subhead-import-log" filter="false"/></h3>
			<ul class="normal">
			<logic:iterate name="importForm" property="messages" id="message">
				<li><bean:write name="message" /></li>
			</logic:iterate>
			</ul>

		<logic:notEqual name="importForm" property="isImportInProgress" value="false">
			</noscript>
		</logic:notEqual>
	</logic:notEmpty>

	
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>