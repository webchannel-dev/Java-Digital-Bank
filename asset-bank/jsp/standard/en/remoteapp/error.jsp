<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title>Asset Bank : Remote App Error</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="remoteApp"/>
</head>

<body>
	<%@include file="../inc/body_start.jsp"%>
	
	<h1>Error redirecting to remote app</h1>
	<div class="error"><bean:write name="error" /></div>

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>