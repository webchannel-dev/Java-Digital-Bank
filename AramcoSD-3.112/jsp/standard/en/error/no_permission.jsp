<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- Developed by bright interactive www.bright-interactive.com 
	d1		Martin Wilson	16-May-2005		Created
	d2		Ben Browning	22-Feb-2006		HTML/CSS Tidy up
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Permissions Error</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="error"/>
	<bean:define id="pagetitle" value="Permission Problem"/>
</head>

<body id="errorPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 

	<p>
		Sorry, you are not permitted to perform this action. <br/>
		You will need to log in as a user with the correct permissions, or use the menu to continue using <bright:cmsWrite identifier="app-name" filter="false" />.<br />
	</p>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>