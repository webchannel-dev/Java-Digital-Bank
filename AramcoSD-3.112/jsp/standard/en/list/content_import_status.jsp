<%@page import="com.bright.framework.simplelist.form.ContentImportForm"%>
<%@include file="../inc/doctype_html.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Content Import</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="export"/>
	<bean:define id="pagetitle">Content Import</bean:define>
	<bean:define id="helpsection" value="export-import"/>
	<bean:define id="tabId" value="import"/>
</head>

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
   
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
	<%@include file="../public/inc_content_tabs.jsp"%>
	<div class="confirm">Your content has been successfully imported.</div>	
  	
	<a href="viewContentImport">&laquo; Back</a>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>