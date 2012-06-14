<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		05-Oct-2005		Created.
	 d2		Ben Browning		21-Feb-2006		HTML/CSS Tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Request sent</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="profile"/>
	<bean:define id="pagetitle" value="Request Permissions Update"/></head>

<body id="profilePage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 		 
	
	<p>Your request to update your profile has been sent to the administrator.</p>
	
	<p>Please use the left hand menu to continue using the Image Bank.</p>	

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>