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


<bright:applicationSetting id="canRegisterWithoutApproval" settingName="users-can-register-without-approval"/>
<bright:applicationSetting id="canExternalUsersRegisterWithoutApproval" settingName="external-users-can-register-without-approval"/>

<head>
	
	<title><bright:cmsWrite identifier="title-register" filter="false"/></title> 
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
					<h1>
						<bright:cmsWrite identifier="heading-register" filter="false" />
						<c:if test="${param.externalUser}">
							<bright:cmsWrite identifier="snippet-as-external-user" filter="false" />
						</c:if>
					</h1> 
					
					<p>
						<bright:cmsWrite identifier="register-success-intro" filter="false" /> 
					</p>


					<c:choose>
						<c:when test="${(not param.externalUser && canRegisterWithoutApproval) || (param.externalUser && canExternalUsersRegisterWithoutApproval)}">
							<p><bright:cmsWrite identifier="register-success-without-approval" filter="false" /></p>
						</c:when>	
						<c:otherwise>
							<p><bright:cmsWrite identifier="register-success-with-approval" filter="false" /></p>
						</c:otherwise>		
					</c:choose>
					
					<p><a href="../action/viewHome"><bright:cmsWrite identifier="link-back-login" filter="false" /></a></p>
				</div>	<!-- end of loginForm -->
	
			</div>   <!-- end of loginPanel -->
		</div>
	</div>
</body>

</body>
</html>