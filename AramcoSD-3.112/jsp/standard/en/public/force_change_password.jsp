<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matthew Woollard		25-Sep-2007		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="allowAutoLoginUsingCookie" settingName="allowAutoLoginUsingCookie"/>
<bright:applicationSetting id="showForgottenPassword" settingName="show-forgotten-password"/>
<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="showConditionsOnLogin" settingName="showConditionsOnLogin"/>
<bright:applicationSetting id="usernameIsEmail" settingName="username-is-emailaddress"/>
<bright:applicationSetting id="forceStrongPassword" settingName="force-strong-password" />

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
					
					<h1><bright:cmsWrite identifier="login-title" filter="false"/> - Password Expired</h1> 
					
					<p>For security reasons your password has expired because it has not been changed recently. Please enter a new password in the box below</p>
					
					<c:if test="${forceStrongPassword}">
						<div class="info">
							<bright:cmsWrite identifier="strong-password-guidance" filter="false" />
						</div>
					</c:if>
					
					<logic:present name="changePasswordForm">
						<logic:equal name="changePasswordForm" property="hasErrors" value="true">
							<div class="error">
								<logic:iterate name="changePasswordForm" property="errors" id="error">
									<bean:write name="error" filter="false"/>
								</logic:iterate>
							</div>
						</logic:equal>
					</logic:present>
					
					<html:form action="/forcePasswordChange" focus="oldPassword" method="post">
						<table cellspacing="0" cellpadding="0" class="form" summary="Login form">
					<th><label for="password">Current Password:</label> </th>
					<td>
					<html:password styleClass="text" styleId="password" property="oldPassword" size="20" maxlength="50" redisplay="false" />
					</td>
					</tr>

					<th><label for="password">New Password:</label> </th>
					<td>
					<html:password styleClass="text" styleId="password" property="newPassword" size="20" maxlength="50" redisplay="false" />
					</td>
					</tr>

					<th><label for="password">Repeat new password:</label> </th>
					<td>
					<html:password styleClass="text" styleId="password" property="confirmNewPassword" size="20" maxlength="50" redisplay="false" />
					</td>
					</tr>	
					
					<input type="hidden" name="forcedToChangePassword" value="true" />				
						
							<tr>
								<td>&nbsp;</td>
								<td class="submit">
									<html:submit styleClass="button flush" property="submit" value="Change password" />
								</td>
							</tr>
						</table>
						
					</html:form>
					
				</div>	<!-- end of loginForm -->
		
			</div>	<!-- end of loginPanel -->
	
		</div>
	</div>

</body>
</html>