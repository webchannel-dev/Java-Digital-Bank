<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Ben Browning	17-Jul-2006		Created
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Privacy</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="privacy"/>
	<bean:define id="pagetitle" value="Privacy Policy"/>
</head>

<body>
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 
	
	<!-- This custom copy comes from the database (change in the 'Content' area of Admin) -->
	<bright:cmsWrite identifier="privacy" filter="false"/>

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>