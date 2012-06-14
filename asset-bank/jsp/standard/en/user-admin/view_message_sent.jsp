<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		James Home		19-Jan-2004		Created.
	d2		Matt Stevenson	06-May-2005		Removed presentation, changes to reflect 
											new user and group implementation
	d3		Tamora James	18-May-2005		Integrated with UI design for Demo
	d4		Ben Browning	21-Feb-2006		HTML/CSS Tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Message Users</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="users"/>
	<bean:define id="pagetitle" value="Users"/>
	<bean:define id="tabId" value="messageUsers"/>
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" /></h1> 
	<%@include file="../user-admin/inc_user_tabs.jsp"%>
	
	<h2>Message Sent</h2>
	<p>
		Your message was sent successfully.
	</p>
	<p><a href="viewMessageUsers">Send another message &raquo;</a></p>

	

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>