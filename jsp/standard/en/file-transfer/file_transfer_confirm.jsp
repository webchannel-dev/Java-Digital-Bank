<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1	Francis Devereux	27-Jan-2009		Created.
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	
	<title><bright:cmsWrite identifier="title-email-sent" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="file-transfer"/>

</head>

<body>

	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-email-sent" filter="false" /></h1> 
	
	<div class="head">
		<a href="../action/viewHome"><bright:cmsWrite identifier="link-back" filter="false" /></a>
	</div>

	<bright:cmsWrite identifier="snippet-email-sent" filter="false"/>
								
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>
