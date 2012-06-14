<%@include file="../inc/doctype_html.jsp" %>


<%-- History:
	 d1		James Home		02-Feb-2004		Created.
	 d2		Tamora James	12-May-2005		Integrated with UI design
	 d3		Ben Browning	22-Feb-2006		HTML/CSS tidy up
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="title-request-cd" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="lightbox"/>

</head>

<body id="contact">

	<%@include file="../inc/body_start.jsp"%>
	<h1 class="underline"><bright:cmsWrite identifier="heading-request-cd" filter="false"/></h1> 


	<p>
		Thank you for your request. We will process your order as soon as possible.
	</p>
	<p>
		Please use the menu on the left to continue. 
	</p>
		
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>