<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		02-Feb-2004		Created.
	 d2		Tamora James	12-May-2005		Integrated with UI design for Demo
	 d3		Ben Browning	17-Feb-2006		Tidied up HTML/CSS
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<head>
	
	<title><bright:cmsWrite identifier="title-contact" filter="false" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="contact"/>

</head>

<body id="contact">
	<%@include file="../inc/body_start.jsp"%>

	<h1><bright:cmsWrite identifier="heading-contact" filter="false" /></h1> 
	 
	<bright:cmsWrite identifier="copy-contact-success" filter="false"/>
				
	<%@include file="../inc/body_end.jsp"%>
   
</body>
</html>