<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matthew Woollard		15-Jan-2010	    Created
--%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Access Level Import Complete</title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="permcats"/>
	<bean:define id="helpsection" value="importing_access_levels"/>
	<bean:define id="pagetitle" value="Access Level Import Complete"/>
</head>

<body id="adminPage">
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
	<p>
	<bean:parameter name="numCatsAdded" id="numCatsAdded" value="0"/>
	The access level import has completed. <strong><bean:write name="numCatsAdded"/></strong> access levels were successfully added.
	<br/>
	</p>
	<p><a href="../action/viewPermissionCategories">&laquo; Back to access level admin</a></p>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>