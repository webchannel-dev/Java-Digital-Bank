<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	11-May-2005		Integrated with UI design for Demo
	 d3      Ben Browning   14-Feb-2006    HTML/CSS tidy up
	 d4     Matt Woollard   13-Nov-2007    Added suspended account error
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	<title><bright:cmsWrite identifier="title-login-page" filter="false"/></title> 
	<bean:define id="section" value="login"/>
	<%@include file="../inc/head-elements.jsp"%>
</head>

<body id="loginPage">

	<div class="leftShadow">
		<div class="rightShadow">
		
			<div id="loginPanel">
				
				<div class="logo">
					<%@include file="../customisation/logo_link.jsp"%>
				</div>
				
				<div class="loginForm">
					
					<h1><bright:cmsWrite identifier="login-title" filter="false"/></h1> 
					
					<bright:applicationSetting id="email" settingName="webmaster-email"/>
					<bright:cmsWrite identifier="sso-enc-url-failed-copy" replaceVariables="true" filter="false"/>
				
				</div>	<!-- end of loginForm -->
		
			</div>	<!-- end of loginPanel -->
	
		</div>
	</div>

</body>
</html>