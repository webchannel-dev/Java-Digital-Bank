<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	11-May-2005		Integrated with UI design for Demo
	 d3      Ben Browning   14-Feb-2006    HTML/CSS tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="showRegisterLink" settingName="showRegisterLink"/>
<bright:applicationSetting id="allowAutoLoginUsingCookie" settingName="allowAutoLoginUsingCookie"/>
<bright:applicationSetting id="showForgottenPassword" settingName="show-forgotten-password"/>
<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>




<head>
	
	<title><bright:cmsWrite identifier="title-forgotten-password" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script type="text/JavaScript">
		// give the username field the focus once the dom is ready
		$j(function () {
  			$j('#username').focus();
 		});
	</script>		
</head>

<body id="loginPage">

<div class="leftShadow">
	<div class="rightShadow">
	   <div id="loginPanel">
	
			<div class="logo">
				<%@include file="../customisation/logo_link.jsp"%>
			</div>

	
			<div class="loginForm">
	
				<h1><bright:cmsWrite identifier="heading-forgotten-password" filter="false" /></h1> 
			
				<logic:present name="loginForm">
					<logic:equal name="loginForm" property="loginFailed" value="true">
						<div class="error">The details you entered are incorrect. Please try again.</div>
					</logic:equal>
				</logic:present>
		
				<html:form action="/passwordReminder"  method="post" styleClass="floated">
				<%-- For password reminder we always need to pass a parameter called 'forgotten' 
						Pass as hidden field in case return key used rather than button click.
						Also, as the login action is used, specify that the Ts&Cs have been accepted in case needed --%> 
				<input type="hidden" name="forgotten" value="Remind me"/>
				<input type="hidden" name="conditionsAccepted" value="true"/>

				<%@include file="../customisation/user/forgotten_pwd_intro.jsp"%>
				
				<logic:present name="loginForm">
					<logic:equal name="loginForm" property="passwordReminderFailed" value="true">
						<div class="error">Sorry, we could not find a user matching the details you entered. Please try again.</div>
					</logic:equal>
					<logic:equal name="loginForm" property="passwordReminderFailedLdapUser" value="true">
						<div class="error">Your password cannot be changed using Asset Bank because your authentication is handled by an LDAP server. Please contact your IT department to find out how to change your username and password.</div>
					</logic:equal>
				</logic:present>
				
				<label for="username">
				<c:choose>
					<c:when test="${ecommerce}">
						<bright:cmsWrite identifier="label-email" filter="false" />
					</c:when>
					<c:otherwise>
						<bright:cmsWrite identifier="label-username-email" filter="false" />
					</c:otherwise>
				</c:choose>
				</label> 
			
				<html:text styleClass="text" property="forgottenUsername" styleId="username" size="20" maxlength="50"/>
				<br />
				
			
				
				<label>&nbsp;</label>
				<input type="submit" class="button flush" name="forgotten" value="<bright:cmsWrite identifier="button-reset-password" filter="false" />" />
				<a href="../action/viewHome" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
				</html:form>
							
	
				
			</div>
	
		</div>
	
	</div>
</div>

</body>
</html>