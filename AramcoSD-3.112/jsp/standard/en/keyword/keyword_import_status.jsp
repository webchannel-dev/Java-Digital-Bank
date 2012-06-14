<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Import Status</title> 
	<%@include file="../inc/head-elements.jsp"%>

	<logic:equal name="importForm" property="isImportInProgress" value="true">
		<noscript>
			<!-- meta refresh for non js users -->
			<meta HTTP-EQUIV="refresh" CONTENT="20;URL=viewImportKeywordsStatus"></meta>
		</noscript>	
		
		<script type="text/javascript">
			
			$j(function() {
			  setInterval("ajaxUpdate('viewImportCategoriesProgress', 'Import Complete', 'viewImportKeywordsStatus')",2000);
			});

		</script>

	</logic:equal>
	
	<bean:define id="section" value="attributes"/>
	<bean:define id="helpsection" value="importing_keywords"/>
	<bean:define id="pagetitle" value="Keyword Import"/>
</head>

<body id="adminPage">
	
	<%@include file="../inc/body_start.jsp"%>
	
	<%@include file="../inc/import_status.jsp"%>

	<logic:equal name="importForm" property="isImportInProgress" value="false">
		<br /><p><a href="../action/viewManageAttributes">&laquo; Back to attribute admin</a></p>
	</logic:equal>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>