
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson		06-Jul-2006	Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Import Keywords</title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="attributes"/>
	<bean:define id="helpsection" value="importing_keywords"/>
	<bean:define id="pagetitle" value="Import Keywords"/>
	
</head>

<body id="adminPage">
	
	<%@include file="../inc/body_start.jsp"%>
	
	<%@include file="../category/inc_import_categories.jsp"%>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>