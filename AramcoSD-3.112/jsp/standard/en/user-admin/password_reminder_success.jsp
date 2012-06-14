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
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | <bright:cmsWrite identifier="heading-define-security-questions" filter="false" /></title> 
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
					<h1><bright:cmsWrite identifier="heading-password-reset" filter="false" /></h1> 
					
					<bright:cmsWrite identifier="intro-password-reset" filter="false" />
					
					<p><a href="../action/viewLogin"><bright:cmsWrite identifier="link-back-login" filter="false" /></a>
					</p>
				</div>
			
			</div>   <!-- end of loginPanel -->
		</div>
	</div>
</body>
</html>