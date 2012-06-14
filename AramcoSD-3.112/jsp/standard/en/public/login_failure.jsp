<%@include file="../inc/doctype_html.jsp" %>


<%-- History:
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

<body id="loginPage" lang="en">

	<div class="leftShadow">
		<div class="rightShadow">
		   <div id="loginPanel">
	
				<div class="logo">
					<%@include file="../customisation/logo_link.jsp"%>
				</div>
		
				
				<div class="loginForm">
					<h1><bright:cmsWrite identifier="login-title" filter="false"/></h1> 
					
					<p>
						You are not currently able to log in.
					</p>
						
	         	<div class="error">
	         		<logic:iterate name="loginForm" property="errors" id="errorText">
	         			<bean:write name="errorText" filter="false"/><br />
	         		</logic:iterate>
	         	</div>
						
					<p>
						<a href="../action/viewLogin"><bright:cmsWrite identifier="link-back-login" filter="false" /></a>
					</p>
				</div>	<!-- end of loginForm -->
	
			</div>   <!-- end of loginPanel -->
		</div>
	</div>
</body>

</body>
</html>