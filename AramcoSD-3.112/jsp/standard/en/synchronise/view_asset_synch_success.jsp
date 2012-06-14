<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Kevin Bennett		19-Jan-2006		Created.
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<c:set var="pagetitle" value="System" />		



<head>
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | <bean:write name="pagetitle" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="system"/>
	<bean:define id="tabId" value="synch"/>

</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	<%@include file="../updater/inc_system_tabs.jsp"%>
	
	<p>The flagged <bright:cmsWrite identifier="items" filter="false" /> have been published successfully.</p>
					
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>