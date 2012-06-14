<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Matt Stevenson		20-Feb-2009		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="title-email-this-page" filter="false" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<%@include file="../inc/inc_mce_editor.jsp"%>
	<bean:define id="section" value="home"/>
</head>
<body>
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-email-this-page" filter="false" /></h1> 
	<bright:cmsWrite identifier="snippet-email-success" filter="false" />

	<p><a href='<bean:write name="sendEmailForm" property="redirectUrl"/>'><bright:cmsWrite identifier="button-back" filter="false" /></a></p>
		
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>