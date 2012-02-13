<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matthew Woollard      17-Jul-2009        Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	<title><bright:cmsWrite identifier="title-bulk-delete-confirm" filter="false" replaceVariables="true" /></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="batch"/>
	<bean:define id="helpsection" value="batch-batchupdate"/>
	<script type="text/JavaScript">
	   function confirmDelete() {
			if (document.getElementById('confirmDelete').checked) {
				return true;
			}
			else {
				alert("<bright:cmsWrite identifier="js-bulk-delete-confirm" filter="false"/>");
         	return false;
         }
	   }
	</script>
</head>

<body id="uploadPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-bulk-delete-confirm" filter="false"/></h1> 
	
	<div class="warning">
		<p><strong><bright:cmsWrite identifier="copy-bulk-delete-warning" filter="false"/></strong></p>
		<p class="js-enabled-show"><input type="checkbox" name="confirmDelete" id="confirmDelete" />&nbsp;<label for="confirmDelete"><bright:cmsWrite identifier="label-bulk-delete-confirm" filter="false"/> </label></p>
	</div>
	
	<form action="bulkUpdateStart" onsubmit="return confirmDelete();">
		<input type="hidden" name="deleteAssets" value="true" />
		<input class="button flush floated" type="submit" value="<bright:cmsWrite identifier="button-delete-assets" filter="false"/>" />
	</form>
			
	<a href="../action/viewManageBulkUpdate" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>