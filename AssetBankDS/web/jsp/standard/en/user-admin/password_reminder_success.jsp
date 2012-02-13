<%@include file="../inc/doctype_html.jsp" %>


<%-- History:
	 d1		Tamora James	29-Jul-2005		Created.
	 d2      Ben Browning   14-Feb-2006    HTML/CSS tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>




<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> : Password Reminder</title> 
	<%@include file="../inc/head-elements.jsp"%>
</head>

<body id="loginPage" lang="en">

<div class="leftShadow">
	<div class="rightShadow">
		   <div id="loginPanel">

				<div class="logo">
					<%@include file="../customisation/logo_link.jsp"%>
				</div>

				
				<div class="loginForm">
					<h1>Password Reminder</h1> 
					
					<p>Your password has been emailed to you, please check your inbox.</p>
					
					<p><a href="../action/viewLogin">Back to login &raquo;</a>
					</p>
				</div>
			
			</div>   <!-- end of loginPanel -->
		</div>
	</div>
</body>
</html>