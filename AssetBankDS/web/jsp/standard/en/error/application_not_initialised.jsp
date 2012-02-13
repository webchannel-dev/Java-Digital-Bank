<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- Developed by bright interactive www.bright-interactive.com 
	d1			Chris Preager 	16-May-2005		Created
	d2 		Ben Browning	21-Feb-2006		HTML/CSS Tidy Up
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>




<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Application not Initialised</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="error"/>
	<bean:define id="pagetitle" value="Application not Initialised"/>
</head>

<body id="errorPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 

	<p>The application has not been initialised.</p>
	
	<logic:equal name="applicationErrorForm" property="isDatabaseError" value="true">
		<p>There was a problem connecting to the database. Check the connection settings in components.xconf, and restart Tomcat.</p>
	</logic:equal>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>